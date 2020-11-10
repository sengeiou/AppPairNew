package com.lys.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lys.App;
import com.lys.activity.ActivityFriendManager;
import com.lys.activity.ActivityTaskLib;
import com.lys.activity.ActivityTaskList;
import com.lys.activity.ActivityTeachRecord;
import com.lys.app.R;
import com.lys.dialog.DialogCode;
import com.lys.fragment.FragmentUserManager;
import com.lys.kit.dialog.DialogAlert;
import com.lys.kit.dialog.DialogMenu;
import com.lys.kit.utils.ImageLoad;
import com.lys.kit.utils.Protocol;
import com.lys.protobuf.SHandleId;
import com.lys.protobuf.SRequest_DeleteUser;
import com.lys.protobuf.SRequest_ModifyName;
import com.lys.protobuf.SSex;
import com.lys.protobuf.SUser;
import com.lys.protobuf.SUserType;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class AdapterUserManager extends RecyclerView.Adapter<AdapterUserManager.Holder>
{
	private FragmentUserManager owner = null;
	private List<SUser> rawUsers = null;
	private List<SUser> filterUsers = null;
	private String filterText = null;

	public AdapterUserManager(FragmentUserManager owner)
	{
		this.owner = owner;
	}

	public void setData(List<SUser> users)
	{
		this.rawUsers = users;
		flush();
	}

	public void setFilterText(String filterText)
	{
		this.filterText = filterText;
		flush();
	}

	private void flush()
	{
		if (rawUsers != null && rawUsers.size() > 0 && !TextUtils.isEmpty(filterText))
		{
			List<SUser> filterUsers = new ArrayList<>();
			for (SUser user : rawUsers)
			{
				if (user.name.contains(filterText) || user.id.contains(filterText))
				{
					filterUsers.add(user);
				}
			}
			this.filterUsers = filterUsers;
		}
		else
		{
			this.filterUsers = rawUsers;
		}
		notifyDataSetChanged();
	}

	@Override
	public Holder onCreateViewHolder(ViewGroup parent, int viewType)
	{
		return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_manager, parent, false));
	}

	private static final SimpleDateFormat formatDate = new SimpleDateFormat("MM-dd HH:mm");

	@Override
	public void onBindViewHolder(Holder holder, int position)
	{
		final SUser user = filterUsers.get(position);
		final Context context = holder.itemView.getContext();

		holder.number.setText(String.format("%04d", position + 1));

		ImageLoad.displayImage(context, user.head, holder.head, R.drawable.img_default_head, null);

		holder.name.setText(user.name);
		holder.sex.setText(user.sex.equals(SSex.Girl) ? "女" : "男");
		holder.account.setText(user.id);
		holder.phone.setText(user.phone);

		holder.itemView.setOnLongClickListener(new View.OnLongClickListener()
		{
			@Override
			public boolean onLongClick(View view)
			{
				DialogMenu.Builder builder = new DialogMenu.Builder(context);
				builder.setMenu("删除", new DialogMenu.OnClickMenuListener()
				{
					@Override
					public void onClick()
					{
						DialogAlert.show(context, "确定要删除<" + user.name + ">吗？", null, new DialogAlert.OnClickListener()
						{
							@Override
							public void onClick(int which)
							{
								if (which == 1)
								{
									SRequest_DeleteUser request = new SRequest_DeleteUser();
									request.userId = user.id;
									Protocol.doPost(context, App.getApi(), SHandleId.DeleteUser, request.saveToStr(), new Protocol.OnCallback()
									{
										@Override
										public void onResponse(int code, String data, String msg)
										{
											if (code == 200)
											{
												owner.request();
											}
										}
									});
								}
							}
						}, "取消", "删除");
					}
				});
				builder.setMenu("好友码", new DialogMenu.OnClickMenuListener()
				{
					@Override
					public void onClick()
					{
						DialogCode.show(context, user.id);
					}
				});
				builder.setMenu("修改名字", new DialogMenu.OnClickMenuListener()
				{
					@Override
					public void onClick()
					{
						DialogAlert.showInput(context, "修改名字：", user.name, new DialogAlert.OnInputListener()
						{
							@Override
							public void onInput(String text)
							{
								final String name = text.trim();
								if (!TextUtils.isEmpty(name) && name.length() > 0 && name.length() <= 10)
								{
									SRequest_ModifyName request = new SRequest_ModifyName();
									request.userId = user.id;
									request.name = name;
									Protocol.doPost(context, App.getApi(), SHandleId.ModifyName, request.saveToStr(), new Protocol.OnCallback()
									{
										@Override
										public void onResponse(int code, String data, String msg)
										{
											if (code == 200)
											{
												user.name = name;
												notifyDataSetChanged();
											}
										}
									});
								}
							}
						});
					}
				});
				builder.show();
				return true;
			}
		});

		holder.teachRecord.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				if (true)
				{
					Intent intent = new Intent(context, ActivityTeachRecord.class);
					intent.putExtra("userId", user.id);
					context.startActivity(intent);
				}
				else
				{
//					SRequest_GetEventList request = new SRequest_GetEventList();
//					request.userId = user.id;
//					request.actions.add(AppConfig.EventAction_TeachCall);
//					request.actions.add(AppConfig.EventAction_TeachRefuse);
//					request.actions.add(AppConfig.EventAction_TeachAgree);
//					request.actions.add(AppConfig.EventAction_TeachReady);
//					request.actions.add(AppConfig.EventAction_TeachStart);
//					request.actions.add(AppConfig.EventAction_TeachOver);
//					request.actions.add(AppConfig.EventAction_TeachQuit);
//					Protocol.doPost(context, App.getApi(), SHandleId.GetEventList, request.saveToStr(), new Protocol.OnCallback()
//					{
//						@Override
//						public void onResponse(int code, String data, String msg)
//						{
//							if (code == 200)
//							{
//								SResponse_GetEventList response = SResponse_GetEventList.load(data);
//								String lastTargetId = null;
//								StringBuilder sb = new StringBuilder();
//								for (int i = 0; i < response.events.size(); i++)
//								{
//									SEvent event = response.events.get(i);
//									String str = event.des;
//									if (str.endsWith("\r\n"))
//										str = str.substring(0, str.length() - "\r\n".length());
//									str = str.replace("\r\n", "\r\n\t\t\t\t\t\t\t");
//									if (!event.target.equals(lastTargetId))
//										sb.append(String.format("%s--------【%s】\r\n", TextUtils.isEmpty(lastTargetId) ? "" : "\r\n", event.target));
//									sb.append(String.format("%s\t%s\r\n", formatDate.format(new Date(event.time)), str));
//									lastTargetId = event.target;
//								}
//								String text = sb.toString();
//								if (!TextUtils.isEmpty(text))
//								{
//									String[] lines = text.split("\r\n");
//									if (lines.length > 1)
//									{
//										StringBuilder sb2 = new StringBuilder();
//										for (int i = lines.length - 1; i >= 0; i--)
//										{
//											sb2.append(lines[i]);
//											sb2.append("\r\n");
//										}
//										text = sb2.toString();
//									}
//								}
//								DialogAlert.show(context, user.name, text, null);
//							}
//						}
//					});
				}
			}
		});

		holder.friendManager.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				Intent intent = new Intent(context, ActivityFriendManager.class);
				intent.putExtra("userId", user.id);
				context.startActivity(intent);
			}
		});

		holder.task.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				if (user.userType.equals(SUserType.Student))
				{
					Intent intent = new Intent(context, ActivityTaskList.class);
					intent.putExtra("userId", user.id);
					context.startActivity(intent);
				}
				else
				{
					Intent intent = new Intent(context, ActivityTaskLib.class);
					intent.putExtra("userId", user.id);
					context.startActivity(intent);
				}
			}
		});

	}

	@Override
	public int getItemCount()
	{
		if (filterUsers != null)
			return filterUsers.size();
		else
			return 0;
	}

	protected class Holder extends RecyclerView.ViewHolder
	{
		public ViewGroup con;
		public TextView number;
		public ImageView head;
		public TextView name;
		public TextView sex;
		public TextView account;
		public TextView phone;
		public TextView teachRecord;
		public TextView friendManager;
		public TextView task;

		public Holder(View itemView)
		{
			super(itemView);
			con = itemView.findViewById(R.id.con);
			number = itemView.findViewById(R.id.number);
			head = itemView.findViewById(R.id.head);
			name = itemView.findViewById(R.id.name);
			sex = itemView.findViewById(R.id.sex);
			account = itemView.findViewById(R.id.account);
			phone = itemView.findViewById(R.id.phone);
			teachRecord = itemView.findViewById(R.id.teachRecord);
			friendManager = itemView.findViewById(R.id.friendManager);
			task = itemView.findViewById(R.id.task);
		}
	}
}