package com.lys.board.dawing;

import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;

import com.alibaba.fastjson.JSONObject;
import com.lys.board.utils.LysBoardDrawUtils;
import com.lys.board.utils.LysBoardPoint;
import com.lys.protobuf.SDrawing;
import com.lys.protobuf.SDrawingBall;

public class LysBoardDrawingBall extends LysBoardDrawing
{
	private Paint mPaint = null;
	protected Paint mPaintImaginary;

	private LysBoardPoint pos = null;
	private Float radius = 0F;

	public LysBoardDrawingBall()
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
		pos = new LysBoardPoint(json.getJSONObject("pos"));
		radius = json.getFloat("radius");
	}

	@Override
	public void init(SDrawing proto)
	{
		SDrawingBall ball = proto.ball;
		mPaint.setColor(ball.paintColor);
		mPaint.setStrokeWidth(ball.strokeWidth);
		mPaintImaginary.setColor(ball.paintColor);
		mPaintImaginary.setStrokeWidth(ball.strokeWidth);
		float strokeWidth = ball.strokeWidth;
		mPaintImaginary.setPathEffect(new DashPathEffect(new float[]{strokeWidth * 3, strokeWidth * 3}, 0));
		pos = new LysBoardPoint(ball.pos);
		radius = ball.radius;
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
		{
			LysBoardDrawUtils.drawCircle(canvas, mPaint, pos, radius);

			LysBoardDrawUtils.drawArc(canvas, mPaint, pos, radius, radius / 4, 0, 180);
			LysBoardDrawUtils.drawArc(canvas, mPaintImaginary, pos, radius, radius / 4, 180, 360);
		}
	}

	@Override
	public void drawGizmo(Canvas canvas)
	{

	}

	@Override
	public JSONObject saveToJson()
	{
		JSONObject json = new JSONObject(true);
		json.put("drawingType", LysBoardDrawingType.Ball);
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
		drawing.drawingType = LysBoardDrawingType.Ball;
		SDrawingBall ball = new SDrawingBall();
		ball.paintColor = mPaint.getColor();
		ball.strokeWidth = mPaint.getStrokeWidth();
		ball.pos = pos.saveToProto();
		ball.radius = radius;
		drawing.ball = ball;
		return drawing;
	}
}
