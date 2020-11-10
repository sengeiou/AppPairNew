package com.lys.board.utils;

import android.view.MotionEvent;

public class LysBoardUtils
{
	public static boolean isPen(MotionEvent event)
	{
		return event.getToolType(0) == MotionEvent.TOOL_TYPE_STYLUS;
//		return (event.getSource() & InputDevice.SOURCE_STYLUS) == InputDevice.SOURCE_STYLUS;
	}

	// 计算镜像
	public static float calculateSymmetry(float v1, float v2, float xisu)
	{
		return (v1 - xisu * (v2 - v1));
	}

	// 计算镜像
	public static float calculateSymmetry(float v1, float v2)
	{
		return calculateSymmetry(v1, v2, 1);
	}

	// 计算X轴镜像点
	public static LysBoardPoint calculateSymmetryX(LysBoardPoint p1, LysBoardPoint p2, float xisu)
	{
		LysBoardPoint p3 = new LysBoardPoint();
		p3.x = p2.x;
		p3.y = calculateSymmetry(p1.y, p2.y, xisu);
		return p3;
	}

	// 计算X轴对称点
	public static LysBoardPoint calculateSymmetryX(LysBoardPoint p1, LysBoardPoint p2)
	{
		return calculateSymmetryX(p1, p2, 1);
	}

	// 计算Y轴镜像点
	public static LysBoardPoint calculateSymmetryY(LysBoardPoint p1, LysBoardPoint p2, float xisu)
	{
		LysBoardPoint p3 = new LysBoardPoint();
		p3.x = calculateSymmetry(p1.x, p2.x, xisu);
		p3.y = p2.y;
		return p3;
	}

	// 计算Y轴对称点
	public static LysBoardPoint calculateSymmetryY(LysBoardPoint p1, LysBoardPoint p2)
	{
		return calculateSymmetryY(p1, p2, 1);
	}
}
