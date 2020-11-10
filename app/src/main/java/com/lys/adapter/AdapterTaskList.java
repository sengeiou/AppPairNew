package com.lys.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lys.activity.ActivityTaskBook;
import com.lys.activity.ActivityTaskList;
import com.lys.app.R;
import com.lys.base.utils.FsUtils;
import com.lys.base.utils.LOG;
import com.lys.config.AppConfig;
import com.lys.kit.dialog.DialogAlert;
import com.lys.kit.dialog.DialogMenu;
import com.lys.kit.utils.ImageLoad;
import com.lys.kit.view.BoardView;
import com.lys.protobuf.SNotePage;
import com.lys.protobuf.SNotePageSet;
import com.lys.protobuf.SPTask;
import com.lys.protobuf.SPTaskType;
import com.lys.utils.SVNManager;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AdapterTaskList extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
	public static final int Type_LoadInfo = 1;
	public static final int Type_TaskGroup = 2;
	public static final int Type_TaskTime = 3;

	private ActivityTaskList owner = null;
	private List<List<SPTask>> taskGroups = new ArrayList<>();

	public AdapterTaskList(ActivityTaskList owner)
	{
		this.owner = owner;
	}

	public static boolean isSameDay(long ms1, long ms2)
	{
		Date date1 = new Date(ms1);
		Date date2 = new Date(ms2);
		return date1.getYear() == date2.getYear() && date1.getMonth() == date2.getMonth() && date1.getDate() == date2.getDate();
	}

	public void addData(List<SPTask> tasks)
	{
		for (SPTask task : tasks)
		{
			if (taskGroups.size() == 0)
			{
				taskGroups.add(null);
				taskGroups.add(new ArrayList<SPTask>());
			}
			List<SPTask> lastGroup = taskGroups.get(taskGroups.size() - 1);
			if (lastGroup.size() == 0)
			{
				lastGroup.add(task);
			}
			else
			{
				SPTask lastTask = lastGroup.get(lastGroup.size() - 1);
				if (isSameDay(lastTask.createTime, task.createTime))
				{
					if (lastGroup.size() < 4)
					{
						lastGroup.add(task);
					}
					else
					{
						taskGroups.add(new ArrayList<SPTask>());
						lastGroup = taskGroups.get(taskGroups.size() - 1);
						lastGroup.add(task);
					}
				}
				else
				{
					taskGroups.add(null);
					taskGroups.add(new ArrayList<SPTask>());
					lastGroup = taskGroups.get(taskGroups.size() - 1);
					lastGroup.add(task);
				}
			}
		}
		notifyDataSetChanged();
	}

	public SPTask getLast()
	{
		if (taskGroups.size() > 0)
		{
			List<SPTask> lastGroup = taskGroups.get(taskGroups.size() - 1);
			return lastGroup.get(lastGroup.size() - 1);
		}
		else
		{
			return null;
		}
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
		case Type_TaskGroup:
			return new HolderTaskGroup(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task_list_group, parent, false));
		case Type_TaskTime:
			return new HolderTaskTime(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task_time, parent, false));
		case Type_LoadInfo:
			return new HolderLoadInfo(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_load_info, parent, false));
		}
		return null;
	}

	private static final SimpleDateFormat formatDate1 = new SimpleDateFormat("yyyy - MM - dd");
	private static final SimpleDateFormat formatDate2 = new SimpleDateFormat("MM-dd HH:mm");

	@Override
	public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int position)
	{
		final Context context = viewHolder.itemView.getContext();
		if (getItemViewType(position) == Type_TaskGroup)
		{
			List<SPTask> taskGroup = taskGroups.get(position);
			HolderTaskGroup holderGroup = (HolderTaskGroup) viewHolder;

			for (int i = 0; i < holderGroup.cards.size(); i++)
			{
				HolderTask holder = holderGroup.cards.get(i);
				if (i < taskGroup.size())
				{
					final SPTask task = taskGroup.get(i);
					holder.itemView.setVisibility(View.VISIBLE);

					holder.name.setText(task.name);
					holder.sendName.setText(String.format("来源：%s", task.sendUser != null ? task.sendUser.name : "未知"));
					holder.date.setText(formatDate2.format(new Date(task.createTime)));

					holder.small.setImageResource(R.drawable.img_task_default_cover_small);
					File fileSet = new File(String.format("%s/pageset.json", AppConfig.getTaskDir(task)));
					if (fileSet.exists())
					{
						SNotePageSet pageset = SNotePageSet.load(FsUtils.readText(fileSet));
						if (pageset.pages.size() > 0)
						{
							SNotePage page = pageset.pages.get(0);
							File dir = new File(String.format("%s/%s", AppConfig.getTaskDir(task), page.pageDir));
							File file = BoardView.getSmallFile(dir);
							ImageLoad.displayImage(context, file.getAbsolutePath(), holder.small, 0, null);
						}
					}

//					holder.small.setOnLongClickListener(new View.OnLongClickListener()
//					{
//						@Override
//						public boolean onLongClick(View view)
//						{
//							DialogMenu.Builder builder = new DialogMenu.Builder(context);
//							if (!App.isStudent())
//							{
//								builder.setMenu("发送", new DialogMenu.OnClickMenuListener()
//								{
//									@Override
//									public void onClick()
//									{
//										DialogSelectFriendToSendTask.show(context, App.userId(), true, new DialogSelectFriendToSendTask.OnListener()
//										{
//											@Override
//											public void onSelect(Map<String, SUser> selectedMap, Integer taskType)
//											{
//												if (selectedMap.size() > 0)
//												{
//													SRequest_SendTask request = new SRequest_SendTask();
//													for (String userId : selectedMap.keySet())
//														request.userIds.add(userId);
//													request.taskIds.add(task.id);
//													request.type = taskType;
//													request.sendUserId = App.userId();
//													Protocol.doPost(context, App.getApi(), SHandleId.SendTask, request.saveToStr(), new Protocol.OnCallback()
//													{
//														@Override
//														public void onResponse(int code, String data, String msg)
//														{
//															if (code == 200)
//															{
//																SResponse_SendTask response = SResponse_SendTask.load(data);
//																TaskMessage.sendTasks(response.tasks);
//																LOG.toast(context, "发送成功");
//															}
//														}
//													});
//												}
//											}
//										});
//									}
//								});
//							}
//							builder.show();
//							return true;
//						}
//					});

					holder.small.setOnClickListener(new View.OnClickListener()
					{
						@Override
						public void onClick(View view)
						{
							ActivityTaskBook.goinWithNone(context, task);
						}
					});

					holder.small.setOnLongClickListener(new View.OnLongClickListener()
					{
						@Override
						public boolean onLongClick(View view)
						{
							DialogMenu.Builder builder = new DialogMenu.Builder(context);
							builder.setMenu("缓存模式进入", new DialogMenu.OnClickMenuListener()
							{
								@Override
								public void onClick()
								{
									ActivityTaskBook.goinWithNone(context, task, true);
								}
							});
							builder.setMenu("上报", new DialogMenu.OnClickMenuListener()
							{
								@Override
								public void onClick()
								{
									ActivityTaskBook.doUploadFile(context, task, null);
								}
							});
							builder.setMenu("删除本地数据", new DialogMenu.OnClickMenuListener()
							{
								@Override
								public void onClick()
								{
									DialogAlert.show(context, "确定要删除本地数据吗？", "请在管理员指导下操作！！！", new DialogAlert.OnClickListener()
									{
										@Override
										public void onClick(int which)
										{
											if (which == 1)
											{
												SVNManager.deleteLocalTaskDir(task.userId, task.id);
												LOG.toast(context, "已删除");
											}
										}
									}, "取消", "删除");
								}
							});
							builder.show();
							return true;
						}
					});

					if (task.type.equals(SPTaskType.Job))
					{
						holder.con.setBackgroundResource(R.drawable.task_student_one_job_bg);
						holder.type.setImageResource(R.drawable.img_task_student_type_job);
					}
					else if (task.type.equals(SPTaskType.Class))
					{
						holder.con.setBackgroundResource(R.drawable.task_student_one_class_bg);
						holder.type.setImageResource(R.drawable.img_task_student_type_class);
					}
					else
					{
						holder.type.setImageDrawable(null);
					}
				}
				else
				{
					holder.itemView.setVisibility(View.INVISIBLE);
				}
			}
		}
		else if (getItemViewType(position) == Type_TaskTime)
		{
			HolderTaskTime holder = (HolderTaskTime) viewHolder;
			List<SPTask> taskGroup = taskGroups.get(position + 1);
			SPTask task = taskGroup.get(0);

			holder.date.setText(formatDate1.format(new Date(task.createTime)));

			int dayDt;
			if (true)
			{
				final long dayMs = 24 * 3600 * 1000;
				final long hour8Ms = 8 * 3600 * 1000;

				long nowDayAlign = (System.currentTimeMillis() + hour8Ms) / dayMs;
				long taskDayAlign = (task.createTime + hour8Ms) / dayMs;

				dayDt = (int) (nowDayAlign - taskDayAlign);
			}
			else
			{
				Date dateNow = new Date(System.currentTimeMillis());
				Date dateTask = new Date(task.createTime);

				int dayNow = dateNow.getYear() * 360 + dateNow.getMonth() * 30 + dateNow.getDate();
				int dayTask = dateTask.getYear() * 360 + dateTask.getMonth() * 30 + dateTask.getDate();

				dayDt = dayNow - dayTask;
			}

			if (dayDt == 0)
			{
				holder.time.setText("（今天）");
			}
			else if (dayDt == 1)
			{
				holder.time.setText("（昨天）");
			}
			else if (dayDt == 2)
			{
				holder.time.setText("（前天）");
			}
			else if (dayDt < 30)
			{
				holder.time.setText("（" + dayDt + " 天前）");
			}
			else
			{
				int monthDt = dayDt / 30;
				if (monthDt < 12)
				{
					holder.time.setText("（" + monthDt + " 个月前）");
				}
				else
				{
					int yearDt = dayDt / 12;
					holder.time.setText("（" + yearDt + " 年前）");
				}
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
		if (taskGroups != null)
			return taskGroups.size() + 1;
		else
			return 0 + 1;
	}

	@Override
	public int getItemViewType(int position)
	{
		if (position == getItemCount() - 1)
			return Type_LoadInfo;
		else if (taskGroups.get(position) != null)
			return Type_TaskGroup;
		else
			return Type_TaskTime;
	}

	protected class HolderTask
	{
		public View itemView;

		public ViewGroup con;
		public ImageView small;
		public TextView name;
		public TextView date;
		public TextView sendName;
		public ImageView type;

		public HolderTask(View itemView)
		{
			this.itemView = itemView;

			con = itemView.findViewById(R.id.con);
			small = itemView.findViewById(R.id.small);
			name = itemView.findViewById(R.id.name);
			date = itemView.findViewById(R.id.date);
			sendName = itemView.findViewById(R.id.sendName);
			type = itemView.findViewById(R.id.type);
		}
	}

	protected class HolderTaskGroup extends RecyclerView.ViewHolder
	{
		public List<HolderTask> cards = new ArrayList<>();

		public HolderTaskGroup(View itemView)
		{
			super(itemView);
			cards.add(new HolderTask(itemView.findViewById(R.id.card0)));
			cards.add(new HolderTask(itemView.findViewById(R.id.card1)));
			cards.add(new HolderTask(itemView.findViewById(R.id.card2)));
			cards.add(new HolderTask(itemView.findViewById(R.id.card3)));
		}
	}

	protected class HolderTaskTime extends RecyclerView.ViewHolder
	{
		public TextView date;
		public TextView time;

		public HolderTaskTime(View itemView)
		{
			super(itemView);
			date = itemView.findViewById(R.id.date);
			time = itemView.findViewById(R.id.time);
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