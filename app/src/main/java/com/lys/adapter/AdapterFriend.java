package com.lys.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lys.App;
import com.lys.activity.ActivityIM;
import com.lys.activity.ActivityTaskList;
import com.lys.app.R;
import com.lys.base.utils.LOG;
import com.lys.kit.dialog.DialogAlert;
import com.lys.kit.dialog.DialogMenu;
import com.lys.kit.utils.ImageLoad;
import com.lys.protobuf.SUser;
import com.lys.protobuf.SUserType;
import com.lys.utils.UserTreeNode;
import com.lys.utils.UserTreeUtils;

import java.util.List;

import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;

public class AdapterFriend extends RecyclerView.Adapter<AdapterFriend.Holder>
{
	private ActivityIM owner = null;
	public List<UserTreeNode> treeNodes = null;

	public AdapterFriend(ActivityIM owner)
	{
		this.owner = owner;
	}

	public void setData(List<UserTreeNode> treeNodes)
	{
		this.treeNodes = treeNodes;
		flush();
	}

	private boolean flushing = false;

	public void flush()
	{
		if (flushing)
			return;
		flushing = true;
		if (treeNodes != null)
		{
			getMsgCount(0, 0);
		}
		else
		{
			notifyDataSetChanged();
			flushing = false;
		}
	}

	private void getMsgCount(final int groupIndex, final int friendIndex)
	{
		if (groupIndex < treeNodes.size())
		{
			final UserTreeNode group = treeNodes.get(groupIndex);
			if (friendIndex == 0)
				group.msgCount = 0;
			if (friendIndex < group.children.size())
			{
				final UserTreeNode friend = group.children.get(friendIndex);
				RongIMClient.getInstance().getUnreadCount(Conversation.ConversationType.PRIVATE, friend.user.id, new RongIMClient.ResultCallback<Integer>()
				{
					@Override
					public void onSuccess(Integer count)
					{
						friend.msgCount = count;
						group.msgCount += count;
						getMsgCount(groupIndex, friendIndex + 1);
					}

					@Override
					public void onError(RongIMClient.ErrorCode errorCode)
					{
						LOG.toast(owner, "获取消息数错误");
						friend.msgCount = 0;
//						group.msgCount += 0;
						getMsgCount(groupIndex, friendIndex + 1);
					}
				});
			}
			else
			{
				getMsgCount(groupIndex + 1, 0);
			}
		}
		else
		{
			notifyDataSetChanged();
			flushing = false;
		}
	}

	@Override
	public Holder onCreateViewHolder(ViewGroup parent, int viewType)
	{
		return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_friend, parent, false));
	}

	@Override
	public void onBindViewHolder(final Holder holder, int position)
	{
		final UserTreeNode treeNode = UserTreeUtils.getShowNode(treeNodes, position);
		final Context context = holder.itemView.getContext();

		holder.groupCon.setVisibility(View.GONE);
		holder.userCon.setVisibility(View.GONE);

		if (treeNode.isUser)
		{
			holder.userCon.setVisibility(View.VISIBLE);

			final SUser friend = treeNode.user;

			if (owner.isCurrUser(friend))
			{
				holder.selectFlag.setVisibility(View.VISIBLE);
				holder.name.setTextColor(0xff1681e5);
			}
			else
			{
				holder.selectFlag.setVisibility(View.GONE);
				holder.name.setTextColor(0xffffffff);
			}

			ImageLoad.displayImage(context, friend.head, holder.head, R.drawable.img_default_head, null);
			holder.name.setText(friend.name);

			if (treeNode.msgCount > 0)
			{
				holder.msgCount.setVisibility(View.VISIBLE);
				holder.msgCount.setText(String.valueOf(treeNode.msgCount));
			}
			else
			{
				holder.msgCount.setVisibility(View.GONE);
			}

//			holder.msgCount.setVisibility(View.GONE);
//			RongIMClient.getInstance().getUnreadCount(Conversation.ConversationType.PRIVATE, friend.id, new RongIMClient.ResultCallback<Integer>()
//			{
//				@Override
//				public void onSuccess(Integer count)
//				{
//					if (count > 0)
//					{
//						holder.msgCount.setVisibility(View.VISIBLE);
//						holder.msgCount.setText(String.valueOf(count));
//					}
//					else
//					{
//						holder.msgCount.setVisibility(View.GONE);
//					}
//				}
//
//				@Override
//				public void onError(RongIMClient.ErrorCode errorCode)
//				{
//				}
//			});

			holder.itemView.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View view)
				{
					owner.selectConversation(friend);
					flush();
				}
			});

			holder.itemView.setOnLongClickListener(new View.OnLongClickListener()
			{
				@Override
				public boolean onLongClick(View view)
				{
					DialogMenu.Builder builder = new DialogMenu.Builder(context);
					if (!friend.userType.equals(SUserType.SupterMaster) && !friend.userType.equals(SUserType.Master))
					{
						builder.setMenu("删除好友", new DialogMenu.OnClickMenuListener()
						{
							@Override
							public void onClick()
							{
								DialogAlert.show(context, "确定要删除<" + friend.name + ">吗？", null, new DialogAlert.OnClickListener()
								{
									@Override
									public void onClick(int which)
									{
										if (which == 1)
										{
											owner.deleteFriend(friend.id);
										}
									}
								}, "取消", "删除");
							}
						});
					}
					if (!App.isStudent())
					{
						if (friend.userType.equals(SUserType.Student))
						{
							builder.setMenu("他的任务", new DialogMenu.OnClickMenuListener()
							{
								@Override
								public void onClick()
								{
									Intent intent = new Intent(context, ActivityTaskList.class);
									intent.putExtra("userId", friend.id);
									context.startActivity(intent);
								}
							});
						}
					}
					builder.show();
					return true;
				}
			});
		}
		else
		{
			holder.groupCon.setVisibility(View.VISIBLE);

			if (treeNode.isOpen)
				holder.oper.setImageResource(R.drawable.img_tree_down);
			else
				holder.oper.setImageResource(R.drawable.img_tree_up);

			holder.groupName.setText(treeNode.group);
			holder.groupFriendCount.setText(String.format("(%s)", treeNode.children.size()));

			if (treeNode.msgCount > 0)
			{
				holder.groupMsgCount.setVisibility(View.VISIBLE);
				holder.groupMsgCount.setText(String.valueOf(treeNode.msgCount));
			}
			else
			{
				holder.groupMsgCount.setVisibility(View.GONE);
			}

			holder.groupCon.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View view)
				{
					treeNode.isOpen = !treeNode.isOpen;
					notifyDataSetChanged();
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

	protected class Holder extends RecyclerView.ViewHolder
	{
		public ViewGroup groupCon;
		public ImageView oper;
		public TextView groupName;
		public TextView groupFriendCount;
		public TextView groupMsgCount;

		public ViewGroup userCon;
		public ImageView selectFlag;
		public ImageView head;
		public TextView name;
		public TextView msgCount;

		public Holder(View itemView)
		{
			super(itemView);

			groupCon = itemView.findViewById(R.id.groupCon);
			oper = itemView.findViewById(R.id.oper);
			groupName = itemView.findViewById(R.id.groupName);
			groupFriendCount = itemView.findViewById(R.id.groupFriendCount);
			groupMsgCount = itemView.findViewById(R.id.groupMsgCount);

			userCon = itemView.findViewById(R.id.userCon);
			selectFlag = itemView.findViewById(R.id.selectFlag);
			head = itemView.findViewById(R.id.head);
			name = itemView.findViewById(R.id.name);
			msgCount = itemView.findViewById(R.id.msgCount);
		}
	}
}