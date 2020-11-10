package com.lys.board.dawing;

import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;

import com.alibaba.fastjson.JSONObject;
import com.lys.board.utils.LysBoardDrawUtils;
import com.lys.board.utils.LysBoardPoint;
import com.lys.board.utils.LysBoardUtils;
import com.lys.protobuf.SDrawing;
import com.lys.protobuf.SDrawingCone;

public class LysBoardDrawingCone extends LysBoardDrawing
{
	private Paint mPaint = null;
	protected Paint mPaintImaginary;

	private LysBoardPoint posStart = null;
	private LysBoardPoint posStop = null;

	public LysBoardDrawingCone()
	{
		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setDither(true);
		mPaint.setStrokeCap(Paint.Cap.ROUND);
		mPaint.setStyle(Paint.Style.STROKE);

		mPaintImaginary = new Paint();
		mPaintImaginary.setAntiAlias(true);
		mPaintImaginary.setDither(true);
		mPaintImaginary.setStrokeCap(Paint.Cap.ROUND);
		mPaintImaginary.setStyle(Paint.Style.STROKE);
	}

	@Override
	public void init(int paintColor, float strokeWidth)
	{
		mPaint.setColor(paintColor);
		mPaint.setStrokeWidth(strokeWidth);
		mPaintImaginary.setColor(paintColor);
		mPaintImaginary.setStrokeWidth(strokeWidth);
		mPaintImaginary.setPathEffect(new DashPathEffect(new float[]{strokeWidth * 3, strokeWidth * 3}, 0));
	}

	@Override
	public void init(JSONObject json)
	{
		mPaint.setColor(json.getInteger("paintColor"));
		mPaint.setStrokeWidth(json.getFloat("strokeWidth"));
		mPaintImaginary.setColor(json.getInteger("paintColor"));
		mPaintImaginary.setStrokeWidth(json.getFloat("strokeWidth"));
		float strokeWidth = json.getFloat("strokeWidth");
		mPaintImaginary.setPathEffect(new DashPathEffect(new float[]{strokeWidth * 3, strokeWidth * 3}, 0));
		posStart = new LysBoardPoint(json.getJSONObject("posStart"));
		posStop = new LysBoardPoint(json.getJSONObject("posStop"));
	}

	@Override
	public void init(SDrawing proto)
	{
		SDrawingCone cone = proto.cone;
		mPaint.setColor(cone.paintColor);
		mPaint.setStrokeWidth(cone.strokeWidth);
		mPaintImaginary.setColor(cone.paintColor);
		mPaintImaginary.setStrokeWidth(cone.strokeWidth);
		float strokeWidth = cone.strokeWidth;
		mPaintImaginary.setPathEffect(new DashPathEffect(new float[]{strokeWidth * 3, strokeWidth * 3}, 0));
		posStart = new LysBoardPoint(cone.posStart);
		posStop = new LysBoardPoint(cone.posStop);
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
			LysBoardPoint p1 = posStart;
			LysBoardPoint p2 = posStop;
			LysBoardPoint p3 = LysBoardUtils.calculateSymmetryY(p1, p2);
			LysBoardPoint p4 = p2.middle(p3);

			float radiusX = p2.distance(p3) / 2;
			float radiusY = radiusX / 2;

			LysBoardDrawUtils.drawLine(canvas, mPaint, p1, p2);
			LysBoardDrawUtils.drawLine(canvas, mPaint, p1, p3);

			if (p2.y > p1.y)
			{
				LysBoardPoint p5 = p4.clone();
				p5.y -= radiusY;
				LysBoardPoint p6 = p4.clone();
				p6.y += radiusY;

				LysBoardDrawUtils.drawBezier3(canvas, mPaintImaginary, false, p2, p5, p3);
				LysBoardDrawUtils.drawBezier3(canvas, mPaint, false, p2, p6, p3);
			}
			else
			{
				LysBoardPoint p5 = p4.clone();
				p5.y -= radiusY;
				LysBoardPoint p6 = p4.clone();
				p6.y += radiusY;

				LysBoardDrawUtils.drawBezier3(canvas, mPaint, false, p2, p5, p3);
				LysBoardDrawUtils.drawBezier3(canvas, mPaint, false, p2, p6, p3);
			}
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
		json.put("drawingType", LysBoardDrawingType.Cone);
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
		drawing.drawingType = LysBoardDrawingType.Cone;
		SDrawingCone cone = new SDrawingCone();
		cone.paintColor = mPaint.getColor();
		cone.strokeWidth = mPaint.getStrokeWidth();
		cone.posStart = posStart.saveToProto();
		cone.posStop = posStop.saveToProto();
		drawing.cone = cone;
		return drawing;
	}
}
