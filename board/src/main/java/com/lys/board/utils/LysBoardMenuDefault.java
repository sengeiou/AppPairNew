package com.lys.board.utils;

import android.graphics.Color;

import com.lys.board.dawing.LysBoardDrawingType;

public class LysBoardMenuDefault implements LysBoardMenu
{
	private int drawingType = LysBoardDrawingType.Stroke;
	private int paintColor = Color.BLACK;
	private float strokeWidth = 2.0f;
	private boolean touchWrite = false;

	@Override
	public int getDrawingType()
	{
		return drawingType;
	}

	public void setDrawingType(int drawingType)
	{
		this.drawingType = drawingType;
	}

	@Override
	public int getPaintColor()
	{
		return paintColor;
	}

	public void setPaintColor(int paintColor)
	{
		this.paintColor = paintColor;
	}

	@Override
	public float getStrokeWidth()
	{
		return strokeWidth;
	}

	public void setStrokeWidth(float strokeWidth)
	{
		this.strokeWidth = strokeWidth;
	}

	@Override
	public String getAnyParam()
	{
		return null;
	}

	@Override
	public boolean touchWrite()
	{
		return touchWrite;
	}

	public void setTouchWrite(boolean touchWrite)
	{
		this.touchWrite = touchWrite;
	}
}
