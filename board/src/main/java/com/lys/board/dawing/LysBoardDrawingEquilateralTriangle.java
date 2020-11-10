package com.lys.board.dawing;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.alibaba.fastjson.JSONObject;
import com.lys.board.utils.LysBoardDrawUtils;
import com.lys.board.utils.LysBoardPoint;
import com.lys.protobuf.SDrawing;
import com.lys.protobuf.SDrawingEquilateralTriangle;

import java.util.ArrayList;
import java.util.List;

public class LysBoardDrawingEquilateralTriangle extends LysBoardDrawing
{
	private Paint mPaint = null;

	private LysBoardPoint posStart = null;
	private LysBoardPoint posStop = null;

	public LysBoardDrawingEquilateralTriangle()
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
		SDrawingEquilateralTriangle equilateralTriangle = proto.equilateralTriangle;
		mPaint.setColor(equilateralTriangle.paintColor);
		mPaint.setStrokeWidth(equilateralTriangle.strokeWidth);
		posStart = new LysBoardPoint(equilateralTriangle.posStart);
		posStop = new LysBoardPoint(equilateralTriangle.posStop);
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

	private LysBoardPoint calculateP3(LysBoardPoint p1, LysBoardPoint p2)
	{
		LysBoardPoint p3 = new LysBoardPoint();

		double tmp = Math.atan((p2.y - p1.y) / (p2.x - p1.x));

		double k1 = Math.tan(tmp - Math.toRadians(60));
		double k2 = Math.tan(tmp + Math.toRadians(60));

		double x = ((p2.y - p1.y) + k1 * p1.x - k2 * p2.x) / (k1 - k2);
		double y = k1 * (x - p1.x) + p1.y;

		p3.x = (float) x;
		p3.y = (float) y;
		return p3;
	}

	@Override
	public void draw(Canvas canvas)
	{
		if (isValid())
		{
			LysBoardPoint p1 = posStart;
			LysBoardPoint p2 = posStop;
			LysBoardPoint p3 = calculateP3(p1, p2);

			List<LysBoardPoint> ps = new ArrayList<>();
			ps.add(p1);
			ps.add(p2);
			ps.add(p3);

			LysBoardDrawUtils.drawPath(canvas, mPaint, true, ps);
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
		json.put("drawingType", LysBoardDrawingType.EquilateralTriangle);
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
		drawing.drawingType = LysBoardDrawingType.EquilateralTriangle;
		SDrawingEquilateralTriangle equilateralTriangle = new SDrawingEquilateralTriangle();
		equilateralTriangle.paintColor = mPaint.getColor();
		equilateralTriangle.strokeWidth = mPaint.getStrokeWidth();
		equilateralTriangle.posStart = posStart.saveToProto();
		equilateralTriangle.posStop = posStop.saveToProto();
		drawing.equilateralTriangle = equilateralTriangle;
		return drawing;
	}
}
