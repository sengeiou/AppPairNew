package com.lys.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lys.App;
import com.lys.activity.ActivityFriendManager;
import com.lys.activity.ActivityTaskGroup;
import com.lys.activity.ActivityTaskLib;
import com.lys.activity.ActivityTaskList;
import com.lys.activity.ActivityTaskTable;
import com.lys.activity.ActivityTeachRecord;
import com.lys.app.R;
import com.lys.base.utils.AppDataTool;
import com.lys.base.utils.LOG;
import com.lys.config.AppConfig;
import com.lys.dialog.DialogCode;
import com.lys.dialog.DialogSelectTask;
import com.lys.dialog.DialogSetVip;
import com.lys.kit.dialog.DialogAlert;
import com.lys.kit.dialog.DialogMenu;
import com.lys.kit.utils.ImageLoad;
import com.lys.kit.utils.Protocol;
import com.lys.message.TaskMessage;
import com.lys.message.TransMessage;
import com.lys.protobuf.SHandleId;
import com.lys.protobuf.SPTask;
import com.lys.protobuf.SRequest_ModifyFriendGroup;
import com.lys.protobuf.SRequest_SendTask;
import com.lys.protobuf.SRequest_SetVip;
import com.lys.protobuf.SResponse_SendTask;
import com.lys.protobuf.SResponse_SetVip;
import com.lys.protobuf.SSex;
import com.lys.protobuf.SUser;
import com.lys.protobuf.SUserType;
import com.lys.utils.UserCacheManager;
import com.lys.utils.UserTreeNode;
import com.lys.utils.UserTreeUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AdapterFriendManager extends RecyclerView.Adapter<AdapterFriendManager.Holder>
{
	private ActivityFriendManager owner = null;
	public List<UserTreeNode> treeNodes = null;
	private String filterText = null;

	public AdapterFriendManager(ActivityFriendManager owner)
	{
		this.owner = owner;
	}

	public void setData(List<UserTreeNode> treeNodes)
	{
		this.treeNodes = treeNodes;
		flush();
	}

	public void setFilterText(String filterText)
	{
		this.filterText = filterText;
		flush();
	}

	private void flush()
	{
		if (treeNodes != null && treeNodes.size() > 0)
		{
			if (!TextUtils.isEmpty(filterText))
			{
				for (UserTreeNode treeNode : treeNodes)
				{
					int showCount = 0;
					for (UserTreeNode child : treeNode.children)
					{
						if (child.user.name.contains(filterText) || child.user.id.contains(filterText))
						{
							child.isShow = true;
							showCount++;
						}
						else
						{
							child.isShow = false;
						}
					}
					treeNode.isShow = showCount > 0;
				}
			}
			else
			{
				for (UserTreeNode treeNode : treeNodes)
				{
					treeNode.isShow = true;
					for (UserTreeNode child : treeNode.children)
					{
						child.isShow = true;
					}
				}
			}
		}
		notifyDataSetChanged();
	}

	public int getShowTaskCount()
	{
		int count = 0;
		if (treeNodes != null)
		{
			for (UserTreeNode treeNode : treeNodes)
			{
				if (treeNode.isShow)
				{
					for (UserTreeNode child : treeNode.children)
					{
						if (child.isShow)
							count++;
					}
				}
			}
		}
		return count;
	}

	@Override
	public Holder onCreateViewHolder(ViewGroup parent, int viewType)
	{
		return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_friend_manager, parent, false));
	}

	private static final SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");

	@Override
	public void onBindViewHolder(final Holder holder, int position)
	{
		final UserTreeNode treeNode = UserTreeUtils.getShowNode(treeNodes, position);
		final Context context = holder.itemView.getContext();

		LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) holder.oper.getLayoutParams();
		layoutParams.leftMargin = 30 + UserTreeUtils.getLevel(treeNode) * 50;
		holder.oper.setLayoutParams(layoutParams);

		holder.groupCon.setVisibility(View.GONE);
		holder.userCon.setVisibility(View.GONE);
		holder.cpCon.setVisibility(View.GONE);

		if (treeNode.isUser)
		{
			final SUser user = treeNode.user;

			holder.oper.setImageDrawable(null);

			holder.userCon.setVisibility(View.VISIBLE);
			holder.cpCon.setVisibility(View.VISIBLE);

			bindUser(context, holder.user, user);

			holder.userCon.setOnLongClickListener(new View.OnLongClickListener()
			{
				@Override
				public boolean onLongClick(View view)
				{
					DialogMenu.Builder builder = new DialogMenu.Builder(context);
					builder.setMenu("好友码", new DialogMenu.OnClickMenuListener()
					{
						@Override
						public void onClick()
						{
							DialogCode.show(context, user.id);
						}
					});
					builder.setMenu("设置VIP", new DialogMenu.OnClickMenuListener()
					{
						@Override
						public void onClick()
						{
							DialogSetVip.show(context, user.vipLevel, user.vipTime, new DialogSetVip.OnListener()
							{
								@Override
								public void onResult(final int vipLevel, final long vipTime)
								{
									SRequest_SetVip request = new SRequest_SetVip();
									request.userId = user.id;
									request.vipLevel = vipLevel;
									request.vipTime = vipTime;
									Protocol.doPost(context, App.getApi(), SHandleId.SetVip, request.saveToStr(), new Protocol.OnCallback()
									{
										@Override
										public void onResponse(int code, String data, String msg)
										{
											if (code == 200)
											{
												SResponse_SetVip response = SResponse_SetVip.load(data);
												user.vipLevel = vipLevel;
												user.vipTime = vipTime;
												notifyDataSetChanged();
												TransMessage.send(user.id, TransMessage.obtain(AppConfig.TransEvt_RefreshUserInfo, null), null);
												LOG.toast(context, "设置成功");
											}
										}
									});
								}
							});
						}
					});
					builder.show();
					return true;
				}
			});

			boolean isMaster = (App.isSupterMaster() || App.isMaster());

			holder.teachRecord.setVisibility(isMaster ? View.VISIBLE : View.GONE);
			holder.teachRecord.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View view)
				{
					Intent intent = new Intent(context, ActivityTeachRecord.class);
					intent.putExtra("userId", user.id);
					context.startActivity(intent);
				}
			});

			holder.send.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View view)
				{
					DialogSelectTask.show(context, owner.userId, new DialogSelectTask.OnListener()
					{
						@Override
						public void onSelect(List<SPTask> selectedList, String taskText)
						{
							if (selectedList.size() > 0)
							{
								SRequest_SendTask request = new SRequest_SendTask();
								request.userIds.add(user.id);
								if (!TextUtils.isEmpty(user.cpId))
									request.userIds.add(user.cpId);
								for (SPTask task : selectedList)
									request.taskIds.add(task.id);
								request.text = taskText;
								Protocol.doPost(context, App.getApi(), SHandleId.SendTask, request.saveToStr(), new Protocol.OnCallback()
								{
									@Override
									public void onResponse(int code, String data, String msg)
									{
										if (code == 200)
										{
											SResponse_SendTask response = SResponse_SendTask.load(data);
											TaskMessage.sendTasks(response.tasks);
											LOG.toast(context, "发送成功");
										}
									}
								});
							}
						}
					});
				}
			});

			holder.group.setText(user.group);
			holder.group.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View view)
				{
					DialogAlert.showInput(context, "请输入分组：", user.group, new DialogAlert.OnInputListener()
					{
						@Override
						public void onInput(final String text)
						{
							SRequest_ModifyFriendGroup request = new SRequest_ModifyFriendGroup();
							request.userId = owner.userId;
							request.friendId = user.id;
							request.group = text;
							Protocol.doPost(context, App.getApi(), SHandleId.ModifyFriendGroup, request.saveToStr(), new Protocol.OnCallback()
							{
								@Override
								public void onResponse(int code, String data, String msg)
								{
									if (code == 200)
									{
										user.group = text;
										notifyDataSetChanged();
									}
								}
							});
						}
					});
				}
			});

			if (TextUtils.isEmpty(user.cpId))
			{
				holder.cpCon.setVisibility(View.GONE);
			}
			else
			{
				holder.cpCon.setVisibility(View.VISIBLE);
				holder.cpCon.setTag(user.cpId);
				UserCacheManager.instance().getUser(user.cpId, new UserCacheManager.OnResult()
				{
					@Override
					public void result(final SUser cp)
					{
						if (user.cpId.equals(holder.cpCon.getTag()))
						{
							bindUser(context, holder.cp, cp);
							if (cp != null)
							{
								holder.cpCon.setOnLongClickListener(new View.OnLongClickListener()
								{
									@Override
									public boolean onLongClick(View view)
									{
										DialogMenu.Builder builder = new DialogMenu.Builder(context);
										builder.setMenu("好友码", new DialogMenu.OnClickMenuListener()
										{
											@Override
											public void onClick()
											{
												DialogCode.show(context, cp.id);
											}
										});
										builder.show();
										return true;
									}
								});
							}
							else
							{
								holder.cpCon.setOnLongClickListener(null);
							}
						}
					}
				});
			}
		}
		else
		{
			if (treeNode.isOpen)
				holder.oper.setImageResource(R.drawable.img_tree_down);
			else
				holder.oper.setImageResource(R.drawable.img_tree_up);

			holder.groupCon.setVisibility(View.VISIBLE);

			holder.groupName.setText(String.format("%s(%s)", treeNode.group, UserTreeUtils.getShowCount(treeNode.children)));

			holder.groupCon.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View view)
				{
					treeNode.isOpen = !treeNode.isOpen;
					notifyDataSetChanged();
				}
			});

			holder.taskTable.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View view)
				{
					List<String> userIds = new ArrayList<>();
					for (UserTreeNode child : treeNode.children)
					{
						userIds.add(child.user.id);
					}
					Intent intent = new Intent(context, ActivityTaskTable.class);
					intent.putExtra("groupName", treeNode.group);
					intent.putExtra("userIds", AppDataTool.saveStringList(userIds).toString());
					context.startActivity(intent);
				}
			});
		}
	}

	private void bindUser(final Context context, HolderUser holder, final SUser user)
	{
		if (user == null)
		{
			holder.head.setImageResource(R.drawable.img_default_head);
			holder.name.setText("加载失败");
			holder.info.setText("");
			holder.task.setOnClickListener(null);
			holder.war.setOnClickListener(null);
		}
		else
		{
			ImageLoad.displayImage(context, user.head, holder.head, R.drawable.img_default_head, null);

			holder.name.setText(String.format("%s（%s）  %s（%s）  %s", user.name, AppConfig.getGradeName(user.grade), "VIP：" + user.vipLevel, formatDate.format(new Date(user.vipTime)), "积分：" + user.score));
			holder.info.setText(String.format("%s（%s）  %s", user.id, user.sex.equals(SSex.Girl) ? "女" : "男", "手机：" + user.phone));

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

			holder.war.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View view)
				{
					Intent intent = new Intent(context, ActivityTaskGroup.class);
					intent.putExtra("userId", user.id);
					context.startActivity(intent);
				}
			});
		}
	}

	@Override
	public int getItemCount()
	{
		if (treeNodes != null)
			return UserTreeUtils.getShowCount(treeNodes);
		else
			return 0;
	}

	protected class HolderUser
	{
		public ImageView head;
		public TextView name;
		public TextView info;
		public TextView task;
		public TextView war;
	}

	protected class Holder extends RecyclerView.ViewHolder
	{
		public ImageView oper;

		public ViewGroup groupCon;
		public TextView groupName;
		public TextView taskTable;

		public ViewGroup userCon;
		public HolderUser user = new HolderUser();

		public ViewGroup cpCon;
		public HolderUser cp = new HolderUser();

		public TextView teachRecord;
		public TextView send;
		public TextView group;

		public Holder(View itemView)
		{
			super(itemView);

			oper = itemView.findViewById(R.id.oper);

			groupCon = itemView.findViewById(R.id.groupCon);
			groupName = itemView.findViewById(R.id.groupName);
			taskTable = itemView.findViewById(R.id.taskTable);

			userCon = itemView.findViewById(R.id.userCon);
			user.head = itemView.findViewById(R.id.head);
			user.name = itemView.findViewById(R.id.name);
			user.info = itemView.findViewById(R.id.info);
			user.task = itemView.findViewById(R.id.task);
			user.war = itemView.findViewById(R.id.war);

			cpCon = itemView.findViewById(R.id.cpCon);
			cp.head = itemView.findViewById(R.id.cpHead);
			cp.name = itemView.findViewById(R.id.cpName);
			cp.info = itemView.findViewById(R.id.cpInfo);
			cp.task = itemView.findViewById(R.id.cpTask);
			cp.war = itemView.findViewById(R.id.cpWar);

			teachRecord = itemView.findViewById(R.id.teachRecord);
			send = itemView.findViewById(R.id.send);
			group = itemView.findViewById(R.id.group);
		}
	}
}