package com.lys.kit.utils;

import com.lys.protobuf.SKnowledge;

import java.util.List;
import java.util.Map;

public class KnowledgeTreeUtils
{
	public static final int StateNo = 0;
	public static final int StateHalf = 1;
	public static final int StateYes = 2;

	public static void setState(SKnowledge knowledge, int state)
	{
		knowledge.state = state;
		for (SKnowledge node : knowledge.nodes)
		{
			setState(node, state);
		}
	}

	private static int testState(List<SKnowledge> knowledges, int currState)
	{
		for (SKnowledge knowledge : knowledges)
		{
			if (knowledge.state == StateHalf)
				return StateHalf;
			switch (currState)
			{
			case StateNo:
				if (knowledge.state == StateYes)
					return StateHalf;
				break;
			case StateYes:
				if (knowledge.state == StateNo)
					return StateHalf;
				break;
			}
		}
		return currState;
	}

	public static void checkParentState(SKnowledge knowledge)
	{
		if (knowledge.parent != null)
		{
			if (knowledge.state == StateHalf)
				knowledge.parent.state = StateHalf;
			else
				knowledge.parent.state = testState(knowledge.parent.nodes, knowledge.state);
			checkParentState(knowledge.parent);
		}
	}

	public static boolean isLeaf(SKnowledge knowledge)
	{
		return knowledge.nodes.size() == 0;
	}

	public static int getLevel(SKnowledge knowledge)
	{
		if (knowledge.parent != null)
			return getLevel(knowledge.parent) + 1;
		return 0;
	}

	public static int getShowCount(SKnowledge knowledge)
	{
		int count = 1;
		if (knowledge.isOpen)
		{
			count += getShowCount(knowledge.nodes);
		}
		return count;
	}

	public static int getShowCount(List<SKnowledge> knowledges)
	{
		int count = 0;
		for (SKnowledge knowledge : knowledges)
		{
			count += getShowCount(knowledge);
		}
		return count;
	}

	public static SKnowledge getShowNode(SKnowledge knowledge, int position)
	{
		if (position == 0)
		{
			return knowledge;
		}
		else
		{
			if (knowledge.isOpen)
			{
				position--;
				return getShowNode(knowledge.nodes, position);
			}
		}
		return null;
	}

	public static SKnowledge getShowNode(List<SKnowledge> knowledges, int position)
	{
		for (SKnowledge knowledge : knowledges)
		{
			int count = getShowCount(knowledge);
			if (position < count)
			{
				return getShowNode(knowledge, position);
			}
			else
			{
				position -= count;
			}
		}
		return null;
	}

	//---------------------------------------

	private static void checkKnowledges(List<SKnowledge> knowledges, SKnowledge parent)
	{
		for (SKnowledge knowledge : knowledges)
		{
			knowledge.state = 0;
			knowledge.parent = parent;
			knowledge.isOpen = false;
			checkKnowledges(knowledge.nodes, knowledge);
		}
	}

	public static void checkKnowledges(List<SKnowledge> knowledges)
	{
		checkKnowledges(knowledges, null);
	}

	private static void openNode(SKnowledge knowledge)
	{
		if (knowledge.parent != null)
		{
			knowledge.parent.isOpen = true;
			openNode(knowledge.parent);
		}
	}

	public static void checkState(List<SKnowledge> knowledges, Map<String, SKnowledge> map)
	{
		for (SKnowledge knowledge : knowledges)
		{
			if (map.containsKey(knowledge.code))
			{
				setState(knowledge, StateYes);
				checkParentState(knowledge);
//				openNode(knowledge);
			}
			else
			{
				if (!isLeaf(knowledge))
				{
					checkState(knowledge.nodes, map);
				}
			}
		}
	}
}
