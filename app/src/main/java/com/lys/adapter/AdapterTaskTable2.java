package com.lys.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lys.activity.ActivityTaskTable2;
import com.lys.app.R;
import com.lys.protobuf.SPTask;
import com.lys.protobuf.SPTaskType;
import com.lys.protobuf.SUser;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AdapterTaskTable2 extends RecyclerView.Adapter<AdapterTaskTable2.Holder>
{
	private ActivityTaskTable2 owner = null;
	private List<SUser> userList = null;

	public AdapterTaskTable2(ActivityTaskTable2 owner)
	{
		this.owner = owner;
	}

	public void setData(List<SUser> userList)
	{
		this.userList = userList;
		notifyDataSetChanged();
	}

	@Override
	public Holder onCreateViewHolder(ViewGroup parent, int viewType)
	{
		return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task_table, parent, false));
	}

	@Override
	public void onBindViewHolder(final Holder holder, int position)
	{
		final SUser user = userList.get(position);
		final Context context = holder.itemView.getContext();

		List<SPTask> taskList = owner.taskMap.get(user.id);

		holder.number.setText(String.format("[%s]", position + 1));
		holder.name.setText("  " + user.name + "   " + user.id);
		holder.taskCount.setText(String.valueOf(taskList.size()));

		for (int i = 0; i < holder.tasks.size(); i++)
		{
			HolderTask holderTask = holder.tasks.get(i);
			if (i < taskList.size())
			{
				holderTask.itemView.setVisibility(View.VISIBLE);

				final SPTask task = taskList.get(i);

				Date dateNow = new Date(System.currentTimeMillis());
				Date dateTask = new Date(task.createTime);

				int dayNow = dateNow.getYear() * 360 + dateNow.getMonth() * 30 + dateNow.getDate();
				int dayTask = dateTask.getYear() * 360 + dateTask.getMonth() * 30 + dateTask.getDate();

				int dayDt = dayNow - dayTask;

				String time;
				if (dayDt == 0)
				{
					time = "（今天）";
				}
//				else if (dayDt == 1)
//				{
//					time = "（昨天）";
//				}
//				else if (dayDt == 2)
//				{
//					time = "（前天）";
//				}
				else if (dayDt < 30)
				{
					time = "（" + dayDt + " 天前）";
				}
				else
				{
					int monthDt = dayDt / 30;
					if (monthDt < 12)
					{
						time = "（" + monthDt + " 个月前）";
					}
					else
					{
						int yearDt = dayDt / 12;
						time = "（" + yearDt + " 年前）";
					}
				}

				if (dayDt == 0)
					holderTask.itemView.setBackgroundColor(0xffffff00);
				else
					holderTask.itemView.setBackgroundColor(Color.TRANSPARENT);

				holderTask.taskName.setText(time + task.name);
				if (task.type.equals(SPTaskType.Job))
					holderTask.taskName.setTextColor(0xff0ed0cf);
				else if (task.type.equals(SPTaskType.Class))
					holderTask.taskName.setTextColor(0xff3fa9e6);
				else
					holderTask.taskName.setTextColor(0xff898989);

				holderTask.taskName.setOnClickListener(new View.OnClickListener()
				{
					@Override
					public void onClick(View view)
					{
						owner.goinTask(task.name);
					}
				});
			}
			else
			{
				holderTask.itemView.setVisibility(View.INVISIBLE);
			}
		}
	}

	@Override
	public int getItemCount()
	{
		if (userList != null)
			return userList.size();
		else
			return 0;
	}

	protected class HolderTask
	{
		public View itemView;

		public TextView taskName;

		public HolderTask(View itemView)
		{
			this.itemView = itemView;

			taskName = itemView.findViewById(R.id.taskName);
		}
	}

	protected class Holder extends RecyclerView.ViewHolder
	{
		public TextView number;
		public TextView name;
		public TextView taskCount;

		public List<HolderTask> tasks = new ArrayList<>();

		public Holder(View itemView)
		{
			super(itemView);

			number = itemView.findViewById(R.id.number);
			name = itemView.findViewById(R.id.name);
			taskCount = itemView.findViewById(R.id.taskCount);

			tasks.add(new HolderTask(itemView.findViewById(R.id.task0)));
			tasks.add(new HolderTask(itemView.findViewById(R.id.task1)));
			tasks.add(new HolderTask(itemView.findViewById(R.id.task2)));
			tasks.add(new HolderTask(itemView.findViewById(R.id.task3)));
			tasks.add(new HolderTask(itemView.findViewById(R.id.task4)));
			tasks.add(new HolderTask(itemView.findViewById(R.id.task5)));
//			tasks.add(new HolderTask(itemView.findViewById(R.id.task6)));
//			tasks.add(new HolderTask(itemView.findViewById(R.id.task7)));
//			tasks.add(new HolderTask(itemView.findViewById(R.id.task8)));
//			tasks.add(new HolderTask(itemView.findViewById(R.id.task9)));
//			tasks.add(new HolderTask(itemView.findViewById(R.id.task10)));
//			tasks.add(new HolderTask(itemView.findViewById(R.id.task11)));
//			tasks.add(new HolderTask(itemView.findViewById(R.id.task12)));
		}
	}

}