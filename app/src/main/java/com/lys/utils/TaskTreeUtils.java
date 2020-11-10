package com.lys.utils;

import java.util.List;

public class TaskTreeUtils
{
	public static final int StateNo = 0;
	public static final int StateHalf = 1;
	public static final int StateYes = 2;

	// 设置选择状态
	public static void setState(TaskTreeNode treeNode, int state)
	{
		if (treeNode.isShow)
		{
			treeNode.state = state;
			for (TaskTreeNode child : treeNode.children)
			{
				setState(child, state);
			}
		}
	}

	// 探测父级状态，内部调用
	private static int testState(List<TaskTreeNode> treeNodes, int currState)
	{
		for (TaskTreeNode treeNode : treeNodes)
		{
			if (treeNode.state == StateHalf)
				return StateHalf;
			switch (currState)
			{
			case StateNo:
				if (treeNode.state == StateYes)
					return StateHalf;
				break;
			case StateYes:
				if (treeNode.state == StateNo)
					return StateHalf;
				break;
			}
		}
		return currState;
	}

	// 校正父级状态
	public static void checkParentState(TaskTreeNode treeNode)
	{
		if (treeNode.parent != null)
		{
			if (treeNode.state == StateHalf)
				treeNode.parent.state = StateHalf;
			else
				treeNode.parent.state = testState(treeNode.parent.children, treeNode.state);
			checkParentState(treeNode.parent);
		}
	}

	// 获取缩进
	public static int getLevel(TaskTreeNode treeNode)
	{
		if (treeNode.parent != null)
			return getLevel(treeNode.parent) + 1;
		return 0;
	}

	private static int getShowCount(TaskTreeNode treeNode)
	{
		if (treeNode.isShow)
		{
			int count = 1;
			if (treeNode.isOpen)
			{
				count += getShowCount(treeNode.children);
			}
			return count;
		}
		return 0;
	}

	// 获取可见节点数
	public static int getShowCount(List<TaskTreeNode> treeNodes)
	{
		int count = 0;
		for (TaskTreeNode treeNode : treeNodes)
		{
			count += getShowCount(treeNode);
		}
		return count;
	}

	private static TaskTreeNode getShowNode(TaskTreeNode treeNode, int position)
	{
		if (treeNode.isShow)
		{
			if (position == 0)
			{
				return treeNode;
			}
			else
			{
				if (treeNode.isOpen)
				{
					position--;
					return getShowNode(treeNode.children, position);
				}
			}
		}
		return null;
	}

	// 获取可见的第N个节点
	public static TaskTreeNode getShowNode(List<TaskTreeNode> treeNodes, int position)
	{
		for (TaskTreeNode treeNode : treeNodes)
		{
			int count = getShowCount(treeNode);
			if (position < count)
			{
				return getShowNode(treeNode, position);
			}
			else
			{
				position -= count;
			}
		}
		return null;
	}

	//---------------------------------------

//	private static void checkTeachs(List<TaskTreeNode> treeNodes, TaskTreeNode parent, boolean openAll)
//	{
//		for (TaskTreeNode treeNode : treeNodes)
//		{
//			treeNode.state = 0;
//			treeNode.parent = parent;
//			if (openAll)
//			{
//				treeNode.isOpen = true;
//			}
//			else
//			{
//				if (parent == null) // 第一级不展开，其它都展开
//					treeNode.isOpen = false;
//				else
//					treeNode.isOpen = true;
//			}
//			checkTeachs(treeNode.children, treeNode, openAll);
//		}
//	}
//
//	public static void checkTeachs(List<TaskTreeNode> treeNodes, boolean openAll)
//	{
//		checkTeachs(treeNodes, null, openAll);
//	}
//
//	private static List<TaskTreeNode> buildTeachTreeImpl(List<SNodeTree> children, List<TaskTreeNode> teachCourses)
//	{
//		List<TaskTreeNode> treeNodes = new ArrayList<>();
//		if (children != null && children.size() > 0)
//		{
//			for (SNodeTree child : children)
//			{
//				TaskTreeNode teachType = new TaskTreeNode();
//				teachType.name = child.name;
//				teachType.children = buildTeachTreeImpl(child.children, child.teachCourses);
//				treeNodes.add(teachType);
//			}
//		}
//		if (teachCourses != null && teachCourses.size() > 0)
//		{
//			for (TaskTreeNode treeNode : teachCourses)
//			{
//				treeNodes.add(treeNode);
//			}
//		}
//		return treeNodes;
//	}
//
//	public static List<TaskTreeNode> buildTeachTree(List<SNodeTree> nodeTrees, List<TaskTreeNode> freeTeachs)
//	{
//		List<TaskTreeNode> treeNodes = buildTeachTreeImpl(nodeTrees, null);
//		if (freeTeachs != null && freeTeachs.size() > 0)
//		{
//			TaskTreeNode teachType = new TaskTreeNode();
//			teachType.name = "自由课程";
//			for (TaskTreeNode treeNode : freeTeachs)
//			{
//				teachType.children.add(treeNode);
//			}
//			treeNodes.add(teachType);
//		}
//		return treeNodes;
//	}
//
//	private static void openNode(TaskTreeNode treeNode)
//	{
//		if (treeNode.parent != null)
//		{
//			treeNode.parent.isOpen = true;
//			openNode(treeNode.parent);
//		}
//	}
//
//	public static void checkState(List<TaskTreeNode> treeNodes, Map<String, TaskTreeNode> map)
//	{
//		for (TaskTreeNode treeNode : treeNodes)
//		{
//			if (isTeach(treeNode))
//			{
//				if (map.containsKey(treeNode.id))
//				{
//					switch (treeNode.state)
//					{
//					case StateNo:
//						setState(treeNode, StateYes);
//						break;
//					case StateHalf:
//						setState(treeNode, StateYes);
//						break;
//					case StateYes:
//						setState(treeNode, StateNo);
//						break;
//					}
//					checkParentState(treeNode);
//					openNode(treeNode);
//				}
//			}
//			else
//			{
//				checkState(treeNode.children, map);
//			}
//		}
//	}

}
