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
import com.lys.config.AppConfig;
import com.lys.kit.utils.ImageLoad;
import com.lys.protobuf.SUser;
import com.lys.utils.UserTreeNode;
import com.lys.utils.UserTreeUtils;

import java.util.ArrayList;
import java.util.List;

public class AdapterSelectFriend extends RecyclerView.Adapter<AdapterSelectFriend.Holder>
{
	public interface OnChangeListener
	{
		void onChange();
	}

	private OnChangeListener listener = null;
	public List<UserTreeNode> treeNodes = null;
	private String filterText = null;
//	public Map<String, SUser> selectedMap = new HashMap<>();

	public AdapterSelectFriend(OnChangeListener listener)
	{
		this.listener = listener;
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

	public boolean isReady()
	{
		return treeNodes != null;
	}

	private void getSelectedUsers(List<UserTreeNode> treeNodes, List<SUser> selectedList)
	{
		for (UserTreeNode treeNode : treeNodes)
		{
			if (treeNode.isUser && treeNode.state == UserTreeUtils.StateYes)
				selectedList.add(treeNode.user);
			getSelectedUsers(treeNode.children, selectedList);
		}
	}

	public List<SUser> getSelectedUsers()
	{
		List<SUser> selectedList = new ArrayList<>();
		getSelectedUsers(treeNodes, selectedList);
		return selectedList;
	}

	@Override
	public Holder onCreateViewHolder(ViewGroup parent, int viewType)
	{
		return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_select_friend, parent, false));
	}

	@Override
	public void onBindViewHolder(final Holder holder, int position)
	{
		final UserTreeNode treeNode = UserTreeUtils.getShowNode(treeNodes, position);
		final Context context = holder.itemView.getContext();

		LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) holder.oper.getLayoutParams();
		layoutParams.leftMargin = 30 + UserTreeUtils.getLevel(treeNode) * 50;
		holder.oper.setLayoutParams(layoutParams);

		if (treeNode.isUser)
		{
			final SUser user = treeNode.user;

			holder.oper.setImageDrawable(null);

			holder.head.setVisibility(View.VISIBLE);
			ImageLoad.displayImage(context, user.head, holder.head, R.drawable.img_default_head, null);

			holder.name.setText(String.format("%s（%s）", user.name, AppConfig.getGradeName(user.grade)));
			holder.name.setTextColor(0xff000000);

			holder.info.setVisibility(View.VISIBLE);
			holder.info.setText(user.id);

			holder.itemView.setClickable(false);

//			if (selectedMap.containsKey(user.id))
//				holder.check.setImageResource(R.drawable.img_select_yes);
//			else
//				holder.check.setImageResource(R.drawable.img_select_no);

//			holder.itemView.setOnClickListener(new View.OnClickListener()
//			{
//				@Override
//				public void onClick(View view)
//				{
//					if (isMulti)
//					{
//						if (selectedMap.containsKey(user.id))
//							selectedMap.remove(user.id);
//						else
//							selectedMap.put(user.id, user);
//
//						if (selectedMap.containsKey(user.id))
//							holder.check.setImageResource(R.drawable.img_select_yes);
//						else
//							holder.check.setImageResource(R.drawable.img_select_no);
//					}
//					else
//					{
//						selectedMap.clear();
//						selectedMap.put(user.id, user);
//						notifyDataSetChanged();
//					}
//				}
//			});
		}
		else
		{
			if (treeNode.isOpen)
				holder.oper.setImageResource(R.drawable.img_tree_down);
			else
				holder.oper.setImageResource(R.drawable.img_tree_up);

			holder.head.setVisibility(View.GONE);

			holder.name.setText(String.format("%s(%s)", treeNode.group, UserTreeUtils.getShowCount(treeNode.children)));
			holder.name.setTextColor(0xff99d2f9);

			holder.info.setVisibility(View.GONE);

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
		case UserTreeUtils.StateNo:
			holder.check.setImageResource(R.drawable.img_select_no);
			break;
		case UserTreeUtils.StateHalf:
			holder.check.setImageResource(R.drawable.img_select_half);
			break;
		case UserTreeUtils.StateYes:
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
				case UserTreeUtils.StateNo:
					UserTreeUtils.setState(treeNode, UserTreeUtils.StateYes);
					break;
				case UserTreeUtils.StateHalf:
					UserTreeUtils.setState(treeNode, UserTreeUtils.StateYes);
					break;
				case UserTreeUtils.StateYes:
					UserTreeUtils.setState(treeNode, UserTreeUtils.StateNo);
					break;
				}
				UserTreeUtils.checkParentState(treeNode);
				notifyDataSetChanged();
				if (listener != null)
					listener.onChange();
			}
		});
	}

	@Override
	public int getItemCount()
	{
		if (treeNodes != null)
			return UserTreeUtils.getShowCount(treeNodes);
		else
			return 0;
	}

	protected class Holder extends RecyclerView.ViewHolder
	{
		public ImageView oper;
		public ImageView head;
		public TextView name;
		public TextView info;
		public ImageView check;

		public Holder(View itemView)
		{
			super(itemView);
			oper = itemView.findViewById(R.id.oper);
			head = itemView.findViewById(R.id.head);
			name = itemView.findViewById(R.id.name);
			info = itemView.findViewById(R.id.info);
			check = itemView.findViewById(R.id.check);
		}
	}
}