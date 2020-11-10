package com.lys.board.dawing;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.alibaba.fastjson.JSONObject;
import com.lys.board.utils.LysBoardDrawUtils;
import com.lys.board.utils.LysBoardPoint;
import com.lys.protobuf.SDrawing;
import com.lys.protobuf.SDrawingCircle;

public class LysBoardDrawingCircle extends LysBoardDrawing
{
	private Paint mPaint = null;

	private LysBoardPoint pos = null;
	private Float radius = 0F;

	public LysBoardDrawingCircle()
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
	}

	@Override
	public void init(JSONObject json)
	{
		mPaint.setColor(json.getInteger("paintColor"));
		mPaint.setStrokeWidth(json.getFloat("strokeWidth"));
		pos = new LysBoardPoint(json.getJSONObject("pos"));
		radius = json.getFloat("radius");
	}

	@Override
	public void init(SDrawing proto)
	{
		SDrawingCircle circle = proto.circle;
		mPaint.setColor(circle.paintColor);
		mPaint.setStrokeWidth(circle.strokeWidth);
		pos = new LysBoardPoint(circle.pos);
		radius = circle.radius;
	}

	@Override
	public void downPoint(LysBoardPoint point)
	{
		pos = point;
	}

	@Override
	public void movePoint(LysBoardPoint point)
	{
		radius = (float) pos.distance(point);
	}

	@Override
	public void upPoint(LysBoardPoint point)
	{
		radius = (float) pos.distance(point);
	}

	@Override
	public void draw(Canvas canvas)
	{
		if (pos != null && radius > 0)
			LysBoardDrawUtils.drawCircle(canvas, mPaint, pos, radius);
	}

	@Override
	public void drawGizmo(Canvas canvas)
	{

	}

	@Override
	public JSONObject saveToJson()
	{
		JSONObject json = new JSONObject(true);
		json.put("drawingType", LysBoardDrawingType.Circle);
		json.put("paintColor", mPaint.getColor());
		json.put("strokeWidth", mPaint.getStrokeWidth());
		json.put("pos", pos.saveToJson());
		json.put("radius", radius);
		return json;
	}

	@Override
	public SDrawing saveToProto()
	{
		SDrawing drawing = new SDrawing();
		drawing.drawingType = LysBoardDrawingType.Circle;
		SDrawingCircle circle = new SDrawingCircle();
		circle.paintColor = mPaint.getColor();
		circle.strokeWidth = mPaint.getStrokeWidth();
		circle.pos = pos.saveToProto();
		circle.radius = radius;
		drawing.circle = circle;
		return drawing;
	}
}
