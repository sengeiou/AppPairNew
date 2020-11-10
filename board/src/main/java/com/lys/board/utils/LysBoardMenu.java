package com.lys.board.utils;

public interface LysBoardMenu
{
	int getDrawingType();

	int getPaintColor();

	float getStrokeWidth();

	String getAnyParam();

	// 手指是否可写
	boolean touchWrite();
}
