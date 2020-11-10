package com.lys.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lys.App;
import com.lys.activity.ActivityTaskBook;
import com.lys.activity.ActivityTaskLib;
import com.lys.app.R;
import com.lys.base.utils.LOG;
import com.lys.dialog.DialogCode;
import com.lys.dialog.DialogCreateTask;
import com.lys.dialog.DialogSelectFriendToSendTask;
import com.lys.kit.dialog.DialogAlert;
import com.lys.kit.dialog.DialogMenu;
import com.lys.kit.utils.Protocol;
import com.lys.protobuf.SHandleId;
import com.lys.protobuf.SPJobType;
import com.lys.protobuf.SPTask;
import com.lys.protobuf.SPTaskType;
import com.lys.protobuf.SRequest_DeleteTask;
import com.lys.protobuf.SRequest_ModifyTask;
import com.lys.protobuf.SRequest_SendTask;
import com.lys.protobuf.SRequest_SetTaskNote;
import com.lys.protobuf.SRequest_SetTaskOpen;
import com.lys.protobuf.SResponse_DeleteTask;
import com.lys.protobuf.SResponse_SendTask;
import com.lys.protobuf.SUser;
import com.lys.utils.Helper;
import com.lys.utils.SVNManager;
import com.lys.utils.TaskHelper;
import com.lys.utils.TaskTreeNode;
import com.lys.utils.TaskTreeUtils;

import java.util.List;

public class AdapterTaskLib extends RecyclerView.Adapter<AdapterTaskLib.Holder>
{
	private ActivityTaskLib owner = null;
	public List<TaskTreeNode> treeNodes = null;
	private String filterText = null;

	public AdapterTaskLib(ActivityTaskLib owner)
	{
		this.owner = owner;
	}

	public void setData(List<TaskTreeNode> treeNodes)
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
				for (TaskTreeNode treeNode : treeNodes)
				{
					int showCount = 0;
					for (TaskTreeNode child : treeNode.children)
					{
						if (child.task.name.contains(filterText) || child.task.id.contains(filterText))
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
				for (TaskTreeNode treeNode : treeNodes)
				{
					treeNode.isShow = true;
					for (TaskTreeNode child : treeNode.children)
					{
						child.isShow = true;
					}
				}
			}
		}
		notifyDataSetChanged();
	}

	public boolean isReady()
	{
		return treeNodes != null;
	}

	public int getShowTaskCount()
	{
		int count = 0;
		if (treeNodes != null)
		{
			for (TaskTreeNode treeNode : treeNodes)
			{
				if (treeNode.isShow)
				{
					for (TaskTreeNode child : treeNode.children)
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
		return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task_lib, parent, false));
	}

	@Override
	public void onBindViewHolder(final Holder holder, int position)
	{
		final TaskTreeNode treeNode = TaskTreeUtils.getShowNode(treeNodes, position);
		final Context context = holder.itemView.getContext();

		LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) holder.oper.getLayoutParams();
		layoutParams.leftMargin = 70 + TaskTreeUtils.getLevel(treeNode) * 40;
		holder.oper.setLayoutParams(layoutParams);

		holder.note.setVisibility(View.GONE);
		holder.open.setVisibility(View.GONE);
		holder.share.setVisibility(View.GONE);
		holder.send.setVisibility(View.GONE);
		holder.add.setVisibility(View.GONE);

		holder.original.setVisibility(View.GONE);
		holder.taskType.setVisibility(View.GONE);
		holder.jobType.setVisibility(View.GONE);

		holder.taskId.setVisibility(App.isSupterMaster() ? View.VISIBLE : View.GONE);

		if (treeNode.isTask)
		{
			final SPTask task = treeNode.task;
			holder.name.setText(task.name);
			holder.name.setTextColor(0xff000000);

			holder.count.setText(String.format("分享浏览 %s 次", task.timesForWeb));
			holder.taskId.setText(String.format("ID：%s", task.id));

			holder.oper.setImageDrawable(null);

			if (task.sendUser == null || TextUtils.isEmpty(task.sendUser.id))
				holder.original.setVisibility(View.VISIBLE);

			if (task.type.equals(SPTaskType.Job))
			{
				holder.taskType.setVisibility(View.VISIBLE);
				holder.taskType.setImageResource(R.drawable.img_flag_job);
				if (task.jobType.equals(SPJobType.OnlySelect))
					holder.jobType.setVisibility(View.VISIBLE);
			}
			else if (task.type.equals(SPTaskType.Class))
			{
				holder.taskType.setVisibility(View.VISIBLE);
				holder.taskType.setImageResource(R.drawable.img_flag_class);
			}

			holder.itemView.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View view)
				{
					ActivityTaskBook.goinWithNone(context, task);
				}
			});

			holder.itemView.setOnLongClickListener(new View.OnLongClickListener()
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
					builder.setMenu("修改", new DialogMenu.OnClickMenuListener()
					{
						@Override
						public void onClick()
						{
							DialogCreateTask.show(context, task.group, task.name, task.type, task.jobType, new DialogCreateTask.OnListener()
							{
								@Override
								public void onResult(String group, String name, int taskType, int jobType)
								{
									SRequest_ModifyTask request = new SRequest_ModifyTask();
									request.taskId = task.id;
									request.group = group;
									request.name = name;
									request.type = taskType;
									request.jobType = jobType;
									Protocol.doPost(context, App.getApi(), SHandleId.ModifyTask, request.saveToStr(), new Protocol.OnCallback()
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
					if (task.userId.equals(App.userId()))
					{
						builder.setMenu("拷贝任务", new DialogMenu.OnClickMenuListener()
						{
							@Override
							public void onClick()
							{
								SRequest_SendTask request = new SRequest_SendTask();
								request.userIds.add(App.userId());
								request.taskIds.add(task.id);
								Protocol.doPost(context, App.getApi(), SHandleId.SendTask, request.saveToStr(), new Protocol.OnCallback()
								{
									@Override
									public void onResponse(int code, String data, String msg)
									{
										if (code == 200)
										{
											SResponse_SendTask response = SResponse_SendTask.load(data);
											TaskHelper.addTaskNode(treeNodes, response.tasks.get(0));
											notifyDataSetChanged();
											LOG.toast(context, "拷贝任务成功");
										}
									}
								});
							}
						});
					}
					builder.setMenu("删除", new DialogMenu.OnClickMenuListener()
					{
						@Override
						public void onClick()
						{
							DialogAlert.show(context, "确定要删除<" + task.name + ">吗？", null, new DialogAlert.OnClickListener()
							{
								@Override
								public void onClick(int which)
								{
									if (which == 1)
									{
										SRequest_DeleteTask request = new SRequest_DeleteTask();
										request.taskId = task.id;
										Protocol.doPost(context, App.getApi(), SHandleId.DeleteTask, request.saveToStr(), new Protocol.OnCallback()
										{
											@Override
											public void onResponse(int code, String data, String msg)
											{
												if (code == 200)
												{
													SResponse_DeleteTask response = SResponse_DeleteTask.load(data);
													treeNode.parent.children.remove(treeNode);
													if (treeNode.parent.children.size() == 0)
														treeNodes.remove(treeNode.parent);
													notifyDataSetChanged();
												}
											}
										});
									}
								}
							}, "取消", "删除");
						}
					});
					builder.show();
					return true;
				}
			});

			holder.note.setVisibility(View.VISIBLE);
			holder.open.setVisibility(View.VISIBLE);
			holder.share.setVisibility(View.VISIBLE);
			holder.send.setVisibility(View.VISIBLE);

			holder.note.setText("备注：" + (task.note != null ? task.note.length() : 0));

			holder.note.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View view)
				{
					DialogAlert.showMultiInput(context, "备注", task.note, new DialogAlert.OnInputListener()
					{
						@Override
						public void onInput(final String text)
						{
							SRequest_SetTaskNote request = new SRequest_SetTaskNote();
							request.taskId = task.id;
							request.note = text;
							Protocol.doPost(context, App.getApi(), SHandleId.SetTaskNote, request.saveToStr(), new Protocol.OnCallback()
							{
								@Override
								public void onResponse(int code, String data, String msg)
								{
									if (code == 200)
									{
										task.note = text;
										notifyDataSetChanged();
									}
								}
							});
						}
					});
				}
			});

			if (task.open == 1)
			{
				holder.open.setText("开放");
				holder.open.setTextColor(Color.GREEN);
			}
			else
			{
				holder.open.setText("不开放");
				holder.open.setTextColor(0xff13121a);
			}

			holder.open.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View view)
				{
					final int open = (task.open == 1 ? 0 : 1);
					SRequest_SetTaskOpen request = new SRequest_SetTaskOpen();
					request.taskId = task.id;
					request.open = open;
					Protocol.doPost(context, App.getApi(), SHandleId.SetTaskOpen, request.saveToStr(), new Protocol.OnCallback()
					{
						@Override
						public void onResponse(int code, String data, String msg)
						{
							if (code == 200)
							{
								task.open = open;
								notifyDataSetChanged();
							}
						}
					});
				}
			});

			holder.share.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View view)
				{
					String shareUrl = String.format("%s/td/%s", App.getConfig().root, task.id);
					DialogCode.show(context, shareUrl);
				}
			});

			holder.send.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View view)
				{
					DialogSelectFriendToSendTask.show(context, App.userId(), new DialogSelectFriendToSendTask.OnListener()
					{
						@Override
						public void onSelect(List<SUser> selectedList, String taskText)
						{
							Helper.sendTask(context, selectedList, task.id, taskText, App.userId());
						}
					});
				}
			});
		}
		else
		{
			holder.name.setText(String.format("%s(%s)", treeNode.group, TaskTreeUtils.getShowCount(treeNode.children)));
			holder.name.setTextColor(0xff99d2f9);

			holder.count.setText("");
			holder.taskId.setText("");

			if (treeNode.isOpen)
				holder.oper.setImageResource(R.drawable.img_tree_down);
			else
				holder.oper.setImageResource(R.drawable.img_tree_up);

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

			holder.itemView.setOnLongClickListener(null);

			holder.add.setVisibility(View.VISIBLE);

			holder.add.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View view)
				{
					owner.add(treeNode.group);
				}
			});
		}
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
		public ImageView taskType;
		public ImageView jobType;
		public TextView count;
		public TextView taskId;
		public TextView note;
		public TextView open;
		public TextView share;
		public TextView send;
		public ImageView add;

		public Holder(View itemView)
		{
			super(itemView);
			oper = itemView.findViewById(R.id.oper);
			name = itemView.findViewById(R.id.name);
			original = itemView.findViewById(R.id.original);
			taskType = itemView.findViewById(R.id.taskType);
			jobType = itemView.findViewById(R.id.jobType);
			count = itemView.findViewById(R.id.count);
			taskId = itemView.findViewById(R.id.taskId);
			note = itemView.findViewById(R.id.note);
			open = itemView.findViewById(R.id.open);
			share = itemView.findViewById(R.id.share);
			send = itemView.findViewById(R.id.send);
			add = itemView.findViewById(R.id.add);
		}
	}

}