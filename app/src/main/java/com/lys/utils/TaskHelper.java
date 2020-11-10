package com.lys.utils;

import android.content.Context;

import com.lys.App;
import com.lys.kit.utils.Protocol;
import com.lys.protobuf.SHandleId;
import com.lys.protobuf.SPTask;
import com.lys.protobuf.SRequest_GetTaskList;
import com.lys.protobuf.SResponse_GetTaskList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskHelper
{

	//-----------------------------------

	public interface onTaskTreeCallback
	{
		void onResult(List<TaskTreeNode> treeNodes);
	}

	// 构建目录节点
	private static TaskTreeNode genTreeNode(String group)
	{
		TaskTreeNode treeNode = new TaskTreeNode();
		treeNode.isTask = false;
		treeNode.group = group;
		treeNode.isOpen = false;
		return treeNode;
	}

	// 包装任务节点
	private static TaskTreeNode packTreeNode(SPTask task)
	{
		TaskTreeNode treeNode = new TaskTreeNode();
		treeNode.isTask = true;
		treeNode.task = task;
		return treeNode;
	}

	// 构建任务树
	private static List<TaskTreeNode> buildTaskTree(List<SPTask> tasks)
	{
		Map<String, TaskTreeNode> treeNodeMap = new HashMap<>();
		List<TaskTreeNode> treeNodes = new ArrayList<>();
		for (SPTask task : tasks)
		{
			if (!treeNodeMap.containsKey(task.group))
			{
				TaskTreeNode treeNode = genTreeNode(task.group);
				treeNodeMap.put(task.group, treeNode);
				treeNodes.add(treeNode);
			}
			TaskTreeNode parent = treeNodeMap.get(task.group);
			TaskTreeNode taskNode = packTreeNode(task);
			parent.children.add(taskNode);
			taskNode.parent = parent;
		}

		Collections.sort(treeNodes, new Comparator<TaskTreeNode>()
		{
			@Override
			public int compare(TaskTreeNode treeNode1, TaskTreeNode treeNode2)
			{
				return treeNode1.group.compareTo(treeNode2.group);
			}
		});

//		for (TaskTreeNode treeNode : treeNodes)
//		{
//			Collections.sort(treeNode.children, new Comparator<TaskTreeNode>()
//			{
//				@Override
//				public int compare(TaskTreeNode treeNode1, TaskTreeNode treeNode2)
//				{
//					return treeNode1.task.createTime.compareTo(treeNode2.task.createTime);
//				}
//			});
//		}

		return treeNodes;
	}

	// 在现有树上添加一个任务
	public static void addTaskNode(List<TaskTreeNode> treeNodes, SPTask task)
	{
		TaskTreeNode parent = null;
		for (TaskTreeNode treeNode : treeNodes)
		{
			if (treeNode.group.equals(task.group))
			{
				parent = treeNode;
				break;
			}
		}
		if (parent == null)
		{
			parent = genTreeNode(task.group);
			treeNodes.add(parent);
			Collections.sort(treeNodes, new Comparator<TaskTreeNode>()
			{
				@Override
				public int compare(TaskTreeNode treeNode1, TaskTreeNode treeNode2)
				{
					return treeNode1.group.compareTo(treeNode2.group);
				}
			});
		}
		TaskTreeNode taskNode = packTreeNode(task);
		parent.children.add(taskNode);
		taskNode.parent = parent;
	}

	// 请求并构建任务树
	public static void requestTaskTree(Context context, String userId, final onTaskTreeCallback callback)
	{
		SRequest_GetTaskList request = new SRequest_GetTaskList();
		request.userId = userId;
		request.prev = true;
		Protocol.doPost(context, App.getApi(), SHandleId.GetTaskList, request.saveToStr(), new Protocol.OnCallback()
		{
			@Override
			public void onResponse(int code, String data, String msg)
			{
				if (code == 200)
				{
					SResponse_GetTaskList response = SResponse_GetTaskList.load(data);
					List<TaskTreeNode> treeNodes = buildTaskTree(response.tasks);
					if (callback != null)
						callback.onResult(treeNodes);
				}
				else
				{
					if (callback != null)
						callback.onResult(null);
				}
			}
		});
	}

	//-----------------------------------

}
