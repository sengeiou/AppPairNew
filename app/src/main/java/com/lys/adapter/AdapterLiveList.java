package com.lys.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lys.App;
import com.lys.activity.ActivityLiveList;
import com.lys.activity.ActivityLivePlay;
import com.lys.app.R;
import com.lys.base.utils.LOG;
import com.lys.config.AppConfig;
import com.lys.dialog.DialogEditLive;
import com.lys.kit.dialog.DialogAlert;
import com.lys.kit.dialog.DialogMenu;
import com.lys.kit.module.ModulePlayer;
import com.lys.kit.utils.ImageLoad;
import com.lys.kit.utils.Protocol;
import com.lys.protobuf.SHandleId;
import com.lys.protobuf.SLiveTask;
import com.lys.protobuf.SLiveType;
import com.lys.protobuf.SRequest_LiveAddModify;
import com.lys.protobuf.SRequest_LiveCopy;
import com.lys.protobuf.SRequest_LiveDelete;
import com.lys.protobuf.SUser;
import com.lys.utils.Helper;
import com.lys.utils.UserCacheManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AdapterLiveList extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
	public static final int Type_Live = 1;

	private ActivityLiveList owner = null;
	private List<SLiveTask> lives = null;

	public AdapterLiveList(ActivityLiveList owner)
	{
		this.owner = owner;
	}

	public void setData(List<SLiveTask> lives)
	{
		this.lives = lives;
		notifyDataSetChanged();
	}

	@NonNull
	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
	{
		switch (viewType)
		{
		case Type_Live:
			return new HolderLive(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_live_list_one, parent, false));
		}
		return null;
	}

	private static final SimpleDateFormat formatDate = new SimpleDateFormat("MM-dd HH:mm");

	@Override
	public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int position)
	{
		final Context context = viewHolder.itemView.getContext();
		final SLiveTask live = lives.get(position);
		if (getItemViewType(position) == Type_Live)
		{
			final HolderLive holder = (HolderLive) viewHolder;

			ImageLoad.displayImage(context, live.cover, holder.cover, R.drawable.img_default, null);
			if (App.currentTimeMillis() < live.startTime)
			{
				holder.state.setText("尚未开始");
				holder.state.setTextColor(Color.RED);
			}
			else if (App.currentTimeMillis() > live.startTime + live.duration)
			{
				if (live.type == SLiveType.PrivateRecord || live.type == SLiveType.PublicRecord)
				{
					holder.state.setText("点击回放");
					holder.state.setTextColor(Color.WHITE);
				}
				else
				{
					holder.state.setText("已结束");
					holder.state.setTextColor(Color.WHITE);
				}
			}
			else
			{
				holder.state.setText("进行中");
				holder.state.setTextColor(Color.GREEN);
			}
			holder.name.setText(live.name);
			holder.startTime.setText(formatDate.format(new Date(live.startTime)));
			UserCacheManager.instance().getUser(live.actorId, new UserCacheManager.OnResult()
			{
				@Override
				public void result(SUser user)
				{
					if (user != null)
						holder.actorId.setText(String.format("主讲：%s", user.name));
					else
						holder.actorId.setText(String.format("主讲：%s", "未知"));
				}
			});

			holder.cover.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View view)
				{
					if (App.currentTimeMillis() < live.startTime)
					{
						LOG.toast(context, "尚未开始");
					}
					else if (App.currentTimeMillis() > live.startTime + live.duration)
					{
						if (live.type == SLiveType.PrivateRecord || live.type == SLiveType.PublicRecord)
						{
							Helper.addEvent(context, App.userId(), AppConfig.EventAction_InRecordLive, live.id, String.format("进入直播回放《%s》", live.name));
							ModulePlayer.instance().playSimple(context, Uri.parse(ImageLoad.checkUrl(live.video)));
						}
						else
						{
							LOG.toast(context, "已结束");
						}
					}
					else
					{
						Intent intent = new Intent(context, ActivityLivePlay.class);
						intent.putExtra("live", live.saveToStr());
						intent.putExtra("url", ImageLoad.checkUrl(live.video));
						intent.putExtra("startTime", live.startTime);
						context.startActivity(intent);
					}
				}
			});

			if (App.isSupterMaster())
			{
				holder.cover.setOnLongClickListener(new View.OnLongClickListener()
				{
					@Override
					public boolean onLongClick(View view)
					{
						DialogMenu.Builder builder = new DialogMenu.Builder(context);
						builder.setMenu("编辑", new DialogMenu.OnClickMenuListener()
						{
							@Override
							public void onClick()
							{
								DialogEditLive.show(context, live, new DialogEditLive.OnResultListener()
								{
									@Override
									public void onResult(SLiveTask live)
									{
										SRequest_LiveAddModify request = new SRequest_LiveAddModify();
										request.live = live;
										Protocol.doPost(context, App.getApi(), SHandleId.LiveAddModify, request.saveToStr(), new Protocol.OnCallback()
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
								});
							}
						});
						builder.setMenu("删除", new DialogMenu.OnClickMenuListener()
						{
							@Override
							public void onClick()
							{
								DialogAlert.show(context, "确定要删除吗？", null, new DialogAlert.OnClickListener()
								{
									@Override
									public void onClick(int which)
									{
										if (which == 1)
										{
											SRequest_LiveDelete request = new SRequest_LiveDelete();
											request.id = live.id;
											Protocol.doPost(context, App.getApi(), SHandleId.LiveDelete, request.saveToStr(), new Protocol.OnCallback()
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
						builder.setMenu("拷贝", new DialogMenu.OnClickMenuListener()
						{
							@Override
							public void onClick()
							{
								SRequest_LiveCopy request = new SRequest_LiveCopy();
								request.id = live.id;
								Protocol.doPost(context, App.getApi(), SHandleId.LiveCopy, request.saveToStr(), new Protocol.OnCallback()
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
						});
						builder.show();
						return true;
					}
				});
			}
		}
	}

	@Override
	public int getItemCount()
	{
		if (lives != null)
			return lives.size();
		else
			return 0;
	}

	@Override
	public int getItemViewType(int position)
	{
		return Type_Live;
	}

	protected class HolderLive extends RecyclerView.ViewHolder
	{
		public ImageView cover;
		public TextView state;
		public TextView name;
		public TextView startTime;
		public TextView actorId;

		public HolderLive(View itemView)
		{
			super(itemView);
			cover = itemView.findViewById(R.id.cover);
			state = itemView.findViewById(R.id.state);
			name = itemView.findViewById(R.id.name);
			startTime = itemView.findViewById(R.id.startTime);
			actorId = itemView.findViewById(R.id.actorId);
		}
	}

}