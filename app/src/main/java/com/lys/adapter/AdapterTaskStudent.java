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
import com.lys.activity.ActivityTaskStudent;
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
import java.util.ArrayList;
import java.util.List;

public class AdapterTaskStudent extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
	public static final int Type_TaskGroup = 1;

	private ActivityTaskStudent owner = null;

	private List<List<SPTask>> taskGroups = new ArrayList<>();

	public AdapterTaskStudent(ActivityTaskStudent owner)
	{
		this.owner = owner;
	}

	public void setData(List<SPTask> taskList)
	{
		taskGroups.clear();
		for (SPTask task : taskList)
		{
			if (taskGroups.size() == 0)
			{
				taskGroups.add(new ArrayList<SPTask>());
			}
			List<SPTask> lastGroup = taskGroups.get(taskGroups.size() - 1);
			if (lastGroup.size() < 6)
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

	@NonNull
	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
	{
		switch (viewType)
		{
		case Type_TaskGroup:
			return new HolderTaskGroup(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task_student_group, parent, false));
		}
		return null;
	}

	@Override
	public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int position)
	{
		final Context context = viewHolder.itemView.getContext();
		if (getItemViewType(position) == Type_TaskGroup)
		{
			HolderTaskGroup holderGroup = (HolderTaskGroup) viewHolder;
			List<SPTask> taskGroup = taskGroups.get(position);
			holderGroup.bindView(context, taskGroup);
		}
	}

	@Override
	public int getItemCount()
	{
		return taskGroups.size();
	}

	@Override
	public int getItemViewType(int position)
	{
		return Type_TaskGroup;
	}

	private class HolderTask
	{
		public View itemView;

		public ImageView taskCover;
		public ImageView type;
		public TextView taskName;

		public HolderTask(View itemView)
		{
			this.itemView = itemView;

			taskCover = itemView.findViewById(R.id.taskCover);
			type = itemView.findViewById(R.id.type);
			taskName = itemView.findViewById(R.id.taskName);
		}

		public void bindView(final Context context, final SPTask task)
		{
			taskCover.setImageResource(R.drawable.img_task_default_cover_small);
			File fileSet = new File(String.format("%s/pageset.json", AppConfig.getTaskDir(task)));
			if (fileSet.exists())
			{
				SNotePageSet pageset = SNotePageSet.load(FsUtils.readText(fileSet));
				if (pageset.pages.size() > 0)
				{
					SNotePage page = pageset.pages.get(0);
					File dir = new File(String.format("%s/%s", AppConfig.getTaskDir(task), page.pageDir));
					File file = BoardView.getSmallFile(dir);
					ImageLoad.displayImage(context, file.getAbsolutePath(), taskCover, 0, null);
				}
			}

			if (task.type.equals(SPTaskType.Job))
				type.setImageResource(R.drawable.img_task_student_type_job);
			else if (task.type.equals(SPTaskType.Class))
				type.setImageResource(R.drawable.img_task_student_type_class);
			else
				type.setImageDrawable(null);

			taskName.setText(task.name);

			taskCover.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View view)
				{
					ActivityTaskBook.goinWithNone(context, task);
				}
			});

			taskCover.setOnLongClickListener(new View.OnLongClickListener()
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
		}
	}

	private class HolderTaskGroup extends RecyclerView.ViewHolder
	{
		public List<HolderTask> cards = new ArrayList<>();

		public HolderTaskGroup(View itemView)
		{
			super(itemView);
			cards.add(new HolderTask(itemView.findViewById(R.id.card0)));
			cards.add(new HolderTask(itemView.findViewById(R.id.card1)));
			cards.add(new HolderTask(itemView.findViewById(R.id.card2)));
			cards.add(new HolderTask(itemView.findViewById(R.id.card3)));
			cards.add(new HolderTask(itemView.findViewById(R.id.card4)));
			cards.add(new HolderTask(itemView.findViewById(R.id.card5)));
		}

		public void bindView(Context context, List<SPTask> taskGroup)
		{
			for (int i = 0; i < cards.size(); i++)
			{
				HolderTask holder = cards.get(i);
				if (i < taskGroup.size())
				{
					SPTask task = taskGroup.get(i);
					holder.itemView.setVisibility(View.VISIBLE);
					holder.bindView(context, task);
				}
				else
				{
					holder.itemView.setVisibility(View.INVISIBLE);
				}
			}
		}
	}

}