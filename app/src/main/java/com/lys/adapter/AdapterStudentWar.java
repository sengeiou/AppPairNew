package com.lys.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lys.App;
import com.lys.activity.ActivityTaskBook;
import com.lys.app.R;
import com.lys.base.utils.LOG;
import com.lys.config.AppConfig;
import com.lys.kit.dialog.DialogAlert;
import com.lys.kit.utils.ImageLoad;
import com.lys.kit.utils.Protocol;
import com.lys.message.TransMessage;
import com.lys.protobuf.SHandleId;
import com.lys.protobuf.SPTask;
import com.lys.protobuf.SRequest_AddTaskScore;
import com.lys.protobuf.SRequest_ModifyTaskComment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AdapterStudentWar extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
	public static final int Type_LoadInfo = 1;
	public static final int Type_Task = 2;

	private boolean isOverTime;
	private List<SPTask> tasks = new ArrayList<>();

	public AdapterStudentWar(boolean isOverTime)
	{
		this.isOverTime = isOverTime;
	}

	public void setData(List<SPTask> tasks)
	{
		this.tasks = tasks;
		notifyDataSetChanged();
	}

	public void addData(List<SPTask> tasks)
	{
		this.tasks.addAll(tasks);
		notifyDataSetChanged();
	}

	public SPTask getLast()
	{
		if (tasks.size() > 0)
			return tasks.get(tasks.size() - 1);
		else
			return null;
	}

	public static final int State_Normal = 1;
	public static final int State_Loading = 2;
	public static final int State_LoadFail = 3;
	public static final int State_NotMore = 4;

	public int state = State_Normal;
	private String msg = "";

	public void setState(int state, String msg)
	{
		this.state = state;
		this.msg = msg;
		notifyDataSetChanged();
	}

	@NonNull
	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
	{
		switch (viewType)
		{
		case Type_Task:
			return new HolderWarTask(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_student_war, parent, false));
		case Type_LoadInfo:
			return new HolderLoadInfo(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_load_info, parent, false));
		}
		return null;
	}

	private static final SimpleDateFormat formatDate = new SimpleDateFormat("yyyy - MM - dd   HH : mm");

	@Override
	public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int position)
	{
		final Context context = viewHolder.itemView.getContext();
		if (getItemViewType(position) == Type_Task)
		{
			final SPTask task = tasks.get(position);
			HolderWarTask holder = (HolderWarTask) viewHolder;

			holder.text.setText(task.text);

			ImageLoad.displayImage(context, AppConfig.hasSender(task) ? task.sendUser.head : null, holder.sendHead, R.drawable.img_default_head, null);
			holder.sendName.setText(AppConfig.hasSender(task) ? task.sendUser.name : "未知");

			if (isOverTime)
				holder.sendTime.setText(formatDate.format(new Date(task.overTime)));
			else
				holder.sendTime.setText(formatDate.format(new Date(task.createTime)));

			if (!TextUtils.isEmpty(task.comment) || task.score > 0)
			{
				holder.commentAndScoreCon.setVisibility(View.VISIBLE);

				if (!TextUtils.isEmpty(task.comment))
				{
					holder.comment.setVisibility(View.VISIBLE);
					holder.comment.setText(task.comment);
				}
				else
				{
					holder.comment.setVisibility(View.GONE);
				}

				if (task.score > 0)
				{
					holder.scoreCon.setVisibility(View.VISIBLE);
					holder.score.setText(String.valueOf(task.score));
				}
				else
				{
					holder.scoreCon.setVisibility(View.GONE);
				}
			}
			else
			{
				holder.commentAndScoreCon.setVisibility(View.GONE);
			}

			holder.con.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View view)
				{
					ActivityTaskBook.goinWithNone(context, task);
				}
			});

			holder.doComment.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View view)
				{
					DialogAlert.showMultiInput(context, "点评", task.comment, new DialogAlert.OnInputListener()
					{
						@Override
						public void onInput(final String text)
						{
							SRequest_ModifyTaskComment request = new SRequest_ModifyTaskComment();
							request.taskId = task.id;
							request.comment = text;
							Protocol.doPost(context, App.getApi(), SHandleId.ModifyTaskComment, request.saveToStr(), new Protocol.OnCallback()
							{
								@Override
								public void onResponse(int code, String data, String msg)
								{
									if (code == 200)
									{
										task.comment = text;
										notifyDataSetChanged();
									}
								}
							});
						}
					});
				}
			});

			if (isOverTime)
			{
				holder.doScore.setVisibility(View.GONE);
			}
			else
			{
				holder.doScore.setVisibility(View.VISIBLE);
				holder.doScore.setOnClickListener(new View.OnClickListener()
				{
					@Override
					public void onClick(View view)
					{
						DialogAlert.showInputNumber(context, "加积分", task.score, new DialogAlert.OnInputListener()
						{
							@Override
							public void onInput(String text)
							{
								final int score = Integer.valueOf(text);
								if (score <= 10)
								{
									SRequest_AddTaskScore request = new SRequest_AddTaskScore();
									request.taskId = task.id;
									request.score = score;
									Protocol.doPost(context, App.getApi(), SHandleId.AddTaskScore, request.saveToStr(), new Protocol.OnCallback()
									{
										@Override
										public void onResponse(int code, String data, String msg)
										{
											if (code == 200)
											{
												task.score = score;
												notifyDataSetChanged();
												TransMessage.send(task.userId, TransMessage.obtain(AppConfig.TransEvt_RefreshUserInfo, null), null);
											}
										}
									});
								}
								else
								{
									LOG.toast(context, "分值过大");
								}
							}
						});
					}
				});
			}
		}
		else if (getItemViewType(position) == Type_LoadInfo)
		{
			HolderLoadInfo holder = (HolderLoadInfo) viewHolder;
			holder.info.setText(msg);
		}
	}

	@Override
	public int getItemCount()
	{
		if (tasks != null)
			return tasks.size() + 1;
		else
			return 0 + 1;
	}

	@Override
	public int getItemViewType(int position)
	{
		if (position == getItemCount() - 1)
			return Type_LoadInfo;
		else
			return Type_Task;
	}

	protected class HolderWarTask extends RecyclerView.ViewHolder
	{
		public ViewGroup con;
		public TextView text;
		public ImageView sendHead;
		public TextView sendName;
		public TextView sendTime;
		public ViewGroup commentAndScoreCon;
		public TextView comment;
		public ViewGroup scoreCon;
		public TextView score;

		public TextView doComment;
		public TextView doScore;

		public HolderWarTask(View itemView)
		{
			super(itemView);
			con = itemView.findViewById(R.id.con);
			text = itemView.findViewById(R.id.text);
			sendHead = itemView.findViewById(R.id.sendHead);
			sendName = itemView.findViewById(R.id.sendName);
			sendTime = itemView.findViewById(R.id.sendTime);
			commentAndScoreCon = itemView.findViewById(R.id.commentAndScoreCon);
			comment = itemView.findViewById(R.id.comment);
			scoreCon = itemView.findViewById(R.id.scoreCon);
			score = itemView.findViewById(R.id.score);

			doComment = itemView.findViewById(R.id.doComment);
			doScore = itemView.findViewById(R.id.doScore);
		}
	}

	protected class HolderLoadInfo extends RecyclerView.ViewHolder
	{
		public TextView info;

		public HolderLoadInfo(View itemView)
		{
			super(itemView);
			info = itemView.findViewById(R.id.info);
		}
	}

}