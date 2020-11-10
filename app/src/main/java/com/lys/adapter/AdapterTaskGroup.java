package com.lys.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.lys.activity.ActivityTaskGroup;
import com.lys.activity.ActivityTaskStudent;
import com.lys.app.R;
import com.lys.kit.utils.ImageLoad;
import com.lys.protobuf.STaskGroup;

import java.util.List;

public class AdapterTaskGroup extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
	public static final int Type_TaskGroup = 1;

	private ActivityTaskGroup owner = null;

	private List<STaskGroup> taskGroupList = null;

	public AdapterTaskGroup(ActivityTaskGroup owner)
	{
		this.owner = owner;
	}

	public void setData(List<STaskGroup> taskGroupList)
	{
		this.taskGroupList = taskGroupList;
		notifyDataSetChanged();
	}

	@NonNull
	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
	{
		switch (viewType)
		{
		case Type_TaskGroup:
			return new HolderTaskGroup(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task_group, parent, false));
		}
		return null;
	}

	@Override
	public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int position)
	{
		final Context context = viewHolder.itemView.getContext();
		final STaskGroup taskGroup = taskGroupList.get(position);
		if (getItemViewType(position) == Type_TaskGroup)
		{
			HolderTaskGroup holder = (HolderTaskGroup) viewHolder;

			holder.name.setText(taskGroup.name);
			holder.name.setText(String.format("%s(%s)", taskGroup.name, taskGroup.allCount));
			if (taskGroup.newCount > 0)
			{
				holder.newCount.setVisibility(View.VISIBLE);
				holder.newCount.setText(String.format("%s", taskGroup.newCount));
			}
			else
			{
				holder.newCount.setVisibility(View.GONE);
			}
			holder.important.setRating(taskGroup.important);
			holder.difficulty.setRating(taskGroup.difficulty);
			ImageLoad.displayImage(context, taskGroup.cover, holder.cover, R.drawable.img_default, null);

			holder.itemView.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View view)
				{
					Intent intent = new Intent(context, ActivityTaskStudent.class);
					intent.putExtra("userId", owner.userId);
					intent.putExtra("group", taskGroup.name);
					context.startActivity(intent);
				}
			});
		}
	}

	@Override
	public int getItemCount()
	{
		if (taskGroupList != null)
			return taskGroupList.size();
		else
			return 0;
	}

	@Override
	public int getItemViewType(int position)
	{
		return Type_TaskGroup;
	}

	public class HolderTaskGroup extends RecyclerView.ViewHolder
	{
		public TextView name;
		public TextView newCount;
		public RatingBar important;
		public RatingBar difficulty;
		public ImageView cover;

		public HolderTaskGroup(View itemView)
		{
			super(itemView);
			name = itemView.findViewById(R.id.name);
			newCount = itemView.findViewById(R.id.newCount);
			important = itemView.findViewById(R.id.important);
			difficulty = itemView.findViewById(R.id.difficulty);
			cover = itemView.findViewById(R.id.cover);
		}
	}

}