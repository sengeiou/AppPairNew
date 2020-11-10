package com.lys.board.dawing;

import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;

import com.alibaba.fastjson.JSONObject;
import com.lys.board.utils.LysBoardDrawUtils;
import com.lys.board.utils.LysBoardPoint;
import com.lys.board.utils.LysBoardUtils;
import com.lys.protobuf.SDrawing;
import com.lys.protobuf.SDrawingPyramid;

public class LysBoardDrawingPyramid extends LysBoardDrawing
{
	private Paint mPaint = null;
	protected Paint mPaintImaginary;

	private LysBoardPoint posStart = null;
	private LysBoardPoint posStop = null;

	public LysBoardDrawingPyramid()
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
		SDrawingPyramid pyramid = proto.pyramid;
		mPaint.setColor(pyramid.paintColor);
		mPaint.setStrokeWidth(pyramid.strokeWidth);
		mPaintImaginary.setColor(pyramid.paintColor);
		mPaintImaginary.setStrokeWidth(pyramid.strokeWidth);
		float strokeWidth = pyramid.strokeWidth;
		mPaintImaginary.setPathEffect(new DashPathEffect(new float[]{strokeWidth * 3, strokeWidth * 3}, 0));
		posStart = new LysBoardPoint(pyramid.posStart);
		posStop = new LysBoardPoint(pyramid.posStop);
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

	private LysBoardPoint calculateP4(LysBoardPoint p1, LysBoardPoint p2)
	{
		LysBoardPoint p4 = new LysBoardPoint();
		p4.x = (p2.x - (p2.x - p1.x));
		p4.y = p2.y + (p2.x - p1.x) / 2;
		return p4;
	}

	@Override
	public void draw(Canvas canvas)
	{
		if (isValid())
		{
			LysBoardPoint p1 = posStart;
			LysBoardPoint p2 = posStop;
			LysBoardPoint p3 = LysBoardUtils.calculateSymmetryY(p1, p2);
			LysBoardPoint p4 = calculateP4(p1, p2);

			if (p2.y > p1.y)
			{
				LysBoardDrawUtils.drawLine(canvas, mPaintImaginary, p2, p3);
				LysBoardDrawUtils.drawPath(canvas, mPaint, true, p1, p2, p4, p3);
				LysBoardDrawUtils.drawLine(canvas, mPaint, p1, p4);
			}
			else
			{
				LysBoardDrawUtils.drawLine(canvas, mPaint, p2, p3);
				LysBoardDrawUtils.drawPath(canvas, mPaint, true, p1, p2, p4, p3);
				LysBoardDrawUtils.drawLine(canvas, mPaint, p1, p4);
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
		json.put("drawingType", LysBoardDrawingType.Pyramid);
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
		drawing.drawingType = LysBoardDrawingType.Pyramid;
		SDrawingPyramid pyramid = new SDrawingPyramid();
		pyramid.paintColor = mPaint.getColor();
		pyramid.strokeWidth = mPaint.getStrokeWidth();
		pyramid.posStart = posStart.saveToProto();
		pyramid.posStop = posStop.saveToProto();
		drawing.pyramid = pyramid;
		return drawing;
	}
}
