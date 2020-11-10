package com.lys.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lys.app.R;
import com.lys.dialog.DialogSelectTask;
import com.lys.protobuf.SPTask;
import com.lys.utils.TaskTreeNode;
import com.lys.utils.TaskTreeUtils;

import java.util.ArrayList;
import java.util.List;

public class AdapterSelectTask extends RecyclerView.Adapter<AdapterSelectTask.Holder>
{
	private DialogSelectTask owner = null;
	public List<TaskTreeNode> treeNodes = null;

	public AdapterSelectTask(DialogSelectTask owner)
	{
		this.owner = owner;
	}

	public void setData(List<TaskTreeNode> treeNodes)
	{
		this.treeNodes = treeNodes;
		notifyDataSetChanged();
	}

	public boolean isReady()
	{
		return treeNodes != null;
	}

	private void getSelectedTasks(List<TaskTreeNode> treeNodes, List<SPTask> selectedList)
	{
		for (TaskTreeNode treeNode : treeNodes)
		{
			if (treeNode.isTask && treeNode.state == TaskTreeUtils.StateYes)
				selectedList.add(treeNode.task);
			getSelectedTasks(treeNode.children, selectedList);
		}
	}

	public List<SPTask> getSelectedTasks()
	{
		List<SPTask> selectedList = new ArrayList<>();
		getSelectedTasks(treeNodes, selectedList);
		return selectedList;
	}

	@Override
	public Holder onCreateViewHolder(ViewGroup parent, int viewType)
	{
		return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_select_task, parent, false));
	}

	@Override
	public void onBindViewHolder(final Holder holder, int position)
	{
		final TaskTreeNode treeNode = TaskTreeUtils.getShowNode(treeNodes, position);
		final Context context = holder.itemView.getContext();

		LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) holder.oper.getLayoutParams();
		layoutParams.leftMargin = 70 + TaskTreeUtils.getLevel(treeNode) * 40;
		holder.oper.setLayoutParams(layoutParams);

		holder.original.setVisibility(View.GONE);

		if (treeNode.isTask)
		{
			final SPTask task = treeNode.task;
			holder.name.setText(task.name);
			holder.name.setTextColor(0xff000000);

			holder.oper.setImageDrawable(null);

			if (task.sendUser == null || TextUtils.isEmpty(task.sendUser.id))
				holder.original.setVisibility(View.VISIBLE);

//			if (task.sendUser != null)
//				holder.oper.setImageResource(AppConfig.receiveRes);
//			else
//				holder.oper.setImageResource(AppConfig.createRes);
//
//			if (task.type.equals(SPTaskType.None))
//				holder.con.setBackgroundColor(AppConfig.noneColor);
//			else if (task.type.equals(SPTaskType.Job))
//				holder.con.setBackgroundColor(AppConfig.jobColor);
//			else if (task.type.equals(SPTaskType.Class))
//				holder.con.setBackgroundColor(AppConfig.classColor);

			holder.itemView.setClickable(false);
		}
		else
		{
			holder.name.setText(String.format("%s(%s)", treeNode.group, treeNode.children.size()));
			holder.name.setTextColor(0xff99d2f9);

			if (treeNode.isOpen)
				holder.oper.setImageResource(R.drawable.img_tree_down);
			else
				holder.oper.setImageResource(R.drawable.img_tree_up);

//			if (treeNode.isOpen)
//				holder.oper.setImageResource(R.drawable.img_tree_sub);
//			else
//				holder.oper.setImageResource(R.drawable.img_tree_add);

//			holder.con.setBackgroundColor(0xffffffff);

			holder.itemView.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View view)
				{
					treeNode.isOpen = !treeNode.isOpen;
					notifyDataSetChanged();
				}
			});
		}

		switch (treeNode.state)
		{
		case TaskTreeUtils.StateNo:
			holder.check.setImageResource(R.drawable.img_select_no);
			break;
		case TaskTreeUtils.StateHalf:
			holder.check.setImageResource(R.drawable.img_select_half);
			break;
		case TaskTreeUtils.StateYes:
			holder.check.setImageResource(R.drawable.img_select_yes);
			break;
		}
		holder.check.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				switch (treeNode.state)
				{
				case TaskTreeUtils.StateNo:
					TaskTreeUtils.setState(treeNode, TaskTreeUtils.StateYes);
					break;
				case TaskTreeUtils.StateHalf:
					TaskTreeUtils.setState(treeNode, TaskTreeUtils.StateYes);
					break;
				case TaskTreeUtils.StateYes:
					TaskTreeUtils.setState(treeNode, TaskTreeUtils.StateNo);
					break;
				}
				TaskTreeUtils.checkParentState(treeNode);
				notifyDataSetChanged();
			}
		});

	}

	@Override
	public int getItemCount()
	{
		if (treeNodes != null)
			return TaskTreeUtils.getShowCount(treeNodes);
		else
			return 0;
	}

	protected class Holder extends RecyclerView.ViewHolder
	{
		public ImageView oper;
		public TextView name;
		public ImageView original;
		public ImageView check;

		public Holder(View itemView)
		{
			super(itemView);
			oper = itemView.findViewById(R.id.oper);
			name = itemView.findViewById(R.id.name);
			original = itemView.findViewById(R.id.original);
			check = itemView.findViewById(R.id.check);
		}
	}

}