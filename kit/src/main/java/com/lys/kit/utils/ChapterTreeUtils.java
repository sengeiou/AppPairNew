package com.lys.kit.utils;

import com.lys.protobuf.SChapter;

import java.util.List;
import java.util.Map;

public class ChapterTreeUtils
{
	public static final int StateNo = 0;
	public static final int StateHalf = 1;
	public static final int StateYes = 2;

	public static void setState(SChapter chapter, int state)
	{
		chapter.state = state;
		for (SChapter node : chapter.nodes)
		{
			setState(node, state);
		}
	}

	private static int testState(List<SChapter> chapters, int currState)
	{
		for (SChapter chapter : chapters)
		{
			if (chapter.state == StateHalf)
				return StateHalf;
			switch (currState)
			{
			case StateNo:
				if (chapter.state == StateYes)
					return StateHalf;
				break;
			case StateYes:
				if (chapter.state == StateNo)
					return StateHalf;
				break;
			}
		}
		return currState;
	}

	public static void checkParentState(SChapter chapter)
	{
		if (chapter.parent != null)
		{
			if (chapter.state == StateHalf)
				chapter.parent.state = StateHalf;
			else
				chapter.parent.state = testState(chapter.parent.nodes, chapter.state);
			checkParentState(chapter.parent);
		}
	}

	public static boolean isLeaf(SChapter chapter)
	{
		return chapter.nodes.size() == 0;
	}

	public static int getLevel(SChapter chapter)
	{
		if (chapter.parent != null)
			return getLevel(chapter.parent) + 1;
		return 0;
	}

	public static int getShowCount(SChapter chapter)
	{
		int count = 1;
		if (chapter.isOpen)
		{
			count += getShowCount(chapter.nodes);
		}
		return count;
	}

	public static int getShowCount(List<SChapter> chapters)
	{
		int count = 0;
		for (SChapter chapter : chapters)
		{
			count += getShowCount(chapter);
		}
		return count;
	}

	public static SChapter getShowNode(SChapter chapter, int position)
	{
		if (position == 0)
		{
			return chapter;
		}
		else
		{
			if (chapter.isOpen)
			{
				position--;
				return getShowNode(chapter.nodes, position);
			}
		}
		return null;
	}

	public static SChapter getShowNode(List<SChapter> chapters, int position)
	{
		for (SChapter chapter : chapters)
		{
			int count = getShowCount(chapter);
			if (position < count)
			{
				return getShowNode(chapter, position);
			}
			else
			{
				position -= count;
			}
		}
		return null;
	}

	//---------------------------------------

	private static void checkChapters(List<SChapter> chapters, SChapter parent)
	{
		for (SChapter chapter : chapters)
		{
			chapter.state = 0;
			chapter.parent = parent;
			chapter.isOpen = false;
			checkChapters(chapter.nodes, chapter);
		}
	}

	public static void checkChapters(List<SChapter> chapters)
	{
		checkChapters(chapters, null);
	}

	private static void openNode(SChapter chapter)
	{
		if (chapter.parent != null)
		{
			chapter.parent.isOpen = true;
			openNode(chapter.parent);
		}
	}

	public static void checkState(List<SChapter> chapters, Map<String, SChapter> map)
	{
		for (SChapter chapter : chapters)
		{
			if (map.containsKey(chapter.code))
			{
				setState(chapter, StateYes);
				checkParentState(chapter);
//				openNode(chapter);
			}
			else
			{
				if (!isLeaf(chapter))
				{
					checkState(chapter.nodes, map);
				}
			}
		}
	}
}
