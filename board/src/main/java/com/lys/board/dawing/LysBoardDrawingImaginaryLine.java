package com.lys.board.dawing;

import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;

import com.alibaba.fastjson.JSONObject;
import com.lys.board.utils.LysBoardDrawUtils;
import com.lys.board.utils.LysBoardPoint;
import com.lys.protobuf.SDrawing;
import com.lys.protobuf.SDrawingImaginaryLine;

public class LysBoardDrawingImaginaryLine extends LysBoardDrawing
{
	private Paint mPaint = null;

	private LysBoardPoint posStart = null;
	private LysBoardPoint posStop = null;

	public LysBoardDrawingImaginaryLine()
	{
		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setDither(true);
		mPaint.setStrokeCap(Paint.Cap.ROUND);
		mPaint.setStyle(Paint.Style.STROKE);
	}

	@Override
	public void init(int paintColor, float strokeWidth)
	{
		mPaint.setColor(paintColor);
		mPaint.setStrokeWidth(strokeWidth);
		mPaint.setPathEffect(new DashPathEffect(new float[]{strokeWidth * 3, strokeWidth * 3}, 0));
	}

	@Override
	public void init(JSONObject json)
	{
		mPaint.setColor(json.getInteger("paintColor"));
		mPaint.setStrokeWidth(json.getFloat("strokeWidth"));
		float strokeWidth = json.getFloat("strokeWidth");
		mPaint.setPathEffect(new DashPathEffect(new float[]{strokeWidth * 3, strokeWidth * 3}, 0));
		posStart = new LysBoardPoint(json.getJSONObject("posStart"));
		posStop = new LysBoardPoint(json.getJSONObject("posStop"));
	}

	@Override
	public void init(SDrawing proto)
	{
		SDrawingImaginaryLine imaginaryLine = proto.imaginaryLine;
		mPaint.setColor(imaginaryLine.paintColor);
		mPaint.setStrokeWidth(imaginaryLine.strokeWidth);
		float strokeWidth = imaginaryLine.strokeWidth;
		mPaint.setPathEffect(new DashPathEffect(new float[]{strokeWidth * 3, strokeWidth * 3}, 0));
		posStart = new LysBoardPoint(imaginaryLine.posStart);
		posStop = new LysBoardPoint(imaginaryLine.posStop);
	}

	@Override
	public void downPoint(LysBoardPoint point)
	{
		posStart = point;
	}

	@Override
	public void movePoint(LysBoardPoint point)
	{
		posStop = point;
	}

	@Override
	public void upPoint(LysBoardPoint point)
	{
		posStop = point;
	}

	@Override
	public void draw(Canvas canvas)
	{
		if (isValid())
		{
			LysBoardDrawUtils.drawLine(canvas, mPaint, posStart, posStop);
		}
	}

	@Override
	public void drawGizmo(Canvas canvas)
	{

	}

	private boolean isValid()
	{
		return posStart != null && posStop != null && //
				posStart.distance(posStop) > 0;
	}

	@Override
	public JSONObject saveToJson()
	{
		JSONObject json = new JSONObject(true);
		json.put("drawingType", LysBoardDrawingType.ImaginaryLine);
		json.put("paintColor", mPaint.getColor());
		json.put("strokeWidth", mPaint.getStrokeWidth());
		json.put("posStart", posStart.saveToJson());
		json.put("posStop", posStop.saveToJson());
		return json;
	}

	@Override
	public SDrawing saveToProto()
	{
		SDrawing drawing = new SDrawing();
		drawing.drawingType = LysBoardDrawingType.ImaginaryLine;
		SDrawingImaginaryLine imaginaryLine = new SDrawingImaginaryLine();
		imaginaryLine.paintColor = mPaint.getColor();
		imaginaryLine.strokeWidth = mPaint.getStrokeWidth();
		imaginaryLine.posStart = posStart.saveToProto();
		imaginaryLine.posStop = posStop.saveToProto();
		drawing.imaginaryLine = imaginaryLine;
		return drawing;
	}
}
