package com.lys.board.dawing;

import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;

import com.alibaba.fastjson.JSONObject;
import com.lys.board.utils.LysBoardDrawUtils;
import com.lys.board.utils.LysBoardPoint;
import com.lys.protobuf.SDrawing;
import com.lys.protobuf.SDrawingCylinder;

public class LysBoardDrawingCylinder extends LysBoardDrawing
{
	private Paint mPaint = null;
	protected Paint mPaintImaginary;

	private LysBoardPoint posStart = null;
	private LysBoardPoint posStop = null;

	public LysBoardDrawingCylinder()
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
		SDrawingCylinder cylinder = proto.cylinder;
		mPaint.setColor(cylinder.paintColor);
		mPaint.setStrokeWidth(cylinder.strokeWidth);
		mPaintImaginary.setColor(cylinder.paintColor);
		mPaintImaginary.setStrokeWidth(cylinder.strokeWidth);
		float strokeWidth = cylinder.strokeWidth;
		mPaintImaginary.setPathEffect(new DashPathEffect(new float[]{strokeWidth * 3, strokeWidth * 3}, 0));
		posStart = new LysBoardPoint(cylinder.posStart);
		posStop = new LysBoardPoint(cylinder.posStop);
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
			LysBoardPoint p3 = new LysBoardPoint(p1.x, p2.y);
			LysBoardPoint p4 = new LysBoardPoint(p2.x, p1.y);
			LysBoardPoint p5 = p2.middle(p3);
			LysBoardPoint p6 = p1.middle(p4);

			float radiusX = p2.distance(p3) / 2;
			float radiusY = radiusX / 2;

			LysBoardDrawUtils.drawLine(canvas, mPaint, p1, p3);
			LysBoardDrawUtils.drawLine(canvas, mPaint, p2, p4);

			if (p2.y > p1.y)
			{
				LysBoardDrawUtils.drawArc(canvas, mPaint, p5, radiusX, radiusY, 0, 180);
				LysBoardDrawUtils.drawArc(canvas, mPaintImaginary, p5, radiusX, radiusY, 180, 360);

				LysBoardDrawUtils.drawOval(canvas, mPaint, p6, radiusX, radiusY);
			}
			else
			{
				LysBoardDrawUtils.drawArc(canvas, mPaint, p6, radiusX, radiusY, 0, 180);
				LysBoardDrawUtils.drawArc(canvas, mPaintImaginary, p6, radiusX, radiusY, 180, 360);

				LysBoardDrawUtils.drawOval(canvas, mPaint, p5, radiusX, radiusY);
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
		json.put("drawingType", LysBoardDrawingType.Cylinder);
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
		drawing.drawingType = LysBoardDrawingType.Cylinder;
		SDrawingCylinder cylinder = new SDrawingCylinder();
		cylinder.paintColor = mPaint.getColor();
		cylinder.strokeWidth = mPaint.getStrokeWidth();
		cylinder.posStart = posStart.saveToProto();
		cylinder.posStop = posStop.saveToProto();
		drawing.cylinder = cylinder;
		return drawing;
	}
}
