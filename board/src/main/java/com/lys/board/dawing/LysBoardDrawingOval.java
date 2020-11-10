package com.lys.board.dawing;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.alibaba.fastjson.JSONObject;
import com.lys.board.utils.LysBoardPoint;
import com.lys.protobuf.SDrawing;
import com.lys.protobuf.SDrawingOval;

public class LysBoardDrawingOval extends LysBoardDrawing
{
	private Paint mPaint = null;

	private LysBoardPoint posStart = null;
	private LysBoardPoint posStop = null;

	public LysBoardDrawingOval()
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
		posStart = new LysBoardPoint(json.getJSONObject("posStart"));
		posStop = new LysBoardPoint(json.getJSONObject("posStop"));
	}

	@Override
	public void init(SDrawing proto)
	{
		SDrawingOval oval = proto.oval;
		mPaint.setColor(oval.paintColor);
		mPaint.setStrokeWidth(oval.strokeWidth);
		posStart = new LysBoardPoint(oval.posStart);
		posStop = new LysBoardPoint(oval.posStop);
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
			LysBoardPoint pa = posStart;
			LysBoardPoint pb = posStop;
			float left = Math.min(pa.x, pb.x);
			float top = Math.min(pa.y, pb.y);
			float right = Math.max(pa.x, pb.x);
			float bottom = Math.max(pa.y, pb.y);
			canvas.drawOval(left, top, right, bottom, mPaint);
		}
	}

	@Override
	public void drawGizmo(Canvas canvas)
	{

	}

	private boolean isValid()
	{
		return posStart != null && posStop != null && //
				!posStart.x.equals(posStop.x) && //
				!posStart.y.equals(posStop.y) && //
				posStart.distance(posStop) > 0;
	}

	@Override
	public JSONObject saveToJson()
	{
		JSONObject json = new JSONObject(true);
		json.put("drawingType", LysBoardDrawingType.Oval);
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
		drawing.drawingType = LysBoardDrawingType.Oval;
		SDrawingOval oval = new SDrawingOval();
		oval.paintColor = mPaint.getColor();
		oval.strokeWidth = mPaint.getStrokeWidth();
		oval.posStart = posStart.saveToProto();
		oval.posStop = posStop.saveToProto();
		drawing.oval = oval;
		return drawing;
	}
}
