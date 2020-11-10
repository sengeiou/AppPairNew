package com.lys.board.dawing;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;

import com.alibaba.fastjson.JSONObject;
import com.lys.board.utils.LysBoardDrawUtils;
import com.lys.board.utils.LysBoardPoint;
import com.lys.protobuf.SDrawing;
import com.lys.protobuf.SDrawingCube;

public class LysBoardDrawingCube extends LysBoardDrawing
{
	private Paint mPaint = null;
	protected Paint mPaintImaginary;

	private LysBoardPoint posStart = null;
	private LysBoardPoint posStop = null;

	public LysBoardDrawingCube()
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
		SDrawingCube cube = proto.cube;
		mPaint.setColor(cube.paintColor);
		mPaint.setStrokeWidth(cube.strokeWidth);
		mPaintImaginary.setColor(cube.paintColor);
		mPaintImaginary.setStrokeWidth(cube.strokeWidth);
		float strokeWidth = cube.strokeWidth;
		mPaintImaginary.setPathEffect(new DashPathEffect(new float[]{strokeWidth * 3, strokeWidth * 3}, 0));
		posStart = new LysBoardPoint(cube.posStart);
		posStop = new LysBoardPoint(cube.posStop);
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

	private LysBoardPoint calculateA1(LysBoardPoint p1, LysBoardPoint p2)
	{
		LysBoardPoint p = new LysBoardPoint();
		p.x = p1.x - (p2.y - p1.y) / 3;
		p.y = p1.y + (p2.y - p1.y) / 3;
		return p;
	}

	private LysBoardPoint calculateA2(LysBoardPoint p1, LysBoardPoint p2)
	{
		LysBoardPoint p = new LysBoardPoint();
		p.x = p2.x;
		p.y = p2.y - (p2.y - p1.y) * 2 / 3;
		return p;
	}

	private LysBoardPoint calculateA3(LysBoardPoint p1, LysBoardPoint p2)
	{
		LysBoardPoint p = new LysBoardPoint();
		p.x = p2.x + (p2.y - p1.y) / 3;
		p.y = p1.y;
		return p;
	}

	private LysBoardPoint calculateC(LysBoardPoint p1, LysBoardPoint p2)
	{
		LysBoardPoint p = new LysBoardPoint();
		p.x = p1.x - (p2.y - p1.y) / 3;
		p.y = p2.y;
		return p;
	}

	private LysBoardPoint calculateD(LysBoardPoint p1, LysBoardPoint p2)
	{
		LysBoardPoint p = new LysBoardPoint();
		p.x = p2.x + (p2.y - p1.y) / 3;
		p.y = p2.y - (p2.y - p1.y) / 3;
		return p;
	}

	private LysBoardPoint calculateE(LysBoardPoint p1, LysBoardPoint p2)
	{
		LysBoardPoint p = new LysBoardPoint();
		p.x = p1.x;
		p.y = p2.y - (p2.y - p1.y) / 3;
		return p;
	}

	@Override
	public void draw(Canvas canvas)
	{
		if (isValid())
		{
			LysBoardPoint p1 = posStart;
			LysBoardPoint p2 = posStop;
			LysBoardPoint a1 = calculateA1(p1, p2);
			LysBoardPoint a2 = calculateA2(p1, p2);
			LysBoardPoint a3 = calculateA3(p1, p2);
			LysBoardPoint c = calculateC(p1, p2);
			LysBoardPoint d = calculateD(p1, p2);
			LysBoardPoint e = calculateE(p1, p2);

			if (p2.x > p1.x)
			{
				if (p2.y > p1.y)
				{
					// 右下方向
					LysBoardDrawUtils.drawLine(canvas, mPaint, p1, a1);
					LysBoardDrawUtils.drawLine(canvas, mPaint, a1, a2);
					LysBoardDrawUtils.drawLine(canvas, mPaint, a2, a3);
					LysBoardDrawUtils.drawLine(canvas, mPaint, a3, p1);

					LysBoardDrawUtils.drawLine(canvas, mPaint, p2, d);
					LysBoardDrawUtils.drawLine(canvas, mPaintImaginary, d, e);
					LysBoardDrawUtils.drawLine(canvas, mPaintImaginary, e, c);
					LysBoardDrawUtils.drawLine(canvas, mPaint, c, p2);

					LysBoardDrawUtils.drawLine(canvas, mPaintImaginary, p1, e);
					LysBoardDrawUtils.drawLine(canvas, mPaint, a1, c);
					LysBoardDrawUtils.drawLine(canvas, mPaint, a2, p2);
					LysBoardDrawUtils.drawLine(canvas, mPaint, a3, d);
				}
				else
				{
					// 右上方向
					LysBoardDrawUtils.drawLine(canvas, mPaintImaginary, p1, a1);
					LysBoardDrawUtils.drawLine(canvas, mPaintImaginary, a1, a2);
					LysBoardDrawUtils.drawLine(canvas, mPaint, a2, a3);
					LysBoardDrawUtils.drawLine(canvas, mPaint, a3, p1);

					LysBoardDrawUtils.drawLine(canvas, mPaint, p2, d);
					LysBoardDrawUtils.drawLine(canvas, mPaint, d, e);
					LysBoardDrawUtils.drawLine(canvas, mPaint, e, c);
					LysBoardDrawUtils.drawLine(canvas, mPaint, c, p2);

					LysBoardDrawUtils.drawLine(canvas, mPaint, p1, e);
					LysBoardDrawUtils.drawLine(canvas, mPaintImaginary, a1, c);
					LysBoardDrawUtils.drawLine(canvas, mPaint, a2, p2);
					LysBoardDrawUtils.drawLine(canvas, mPaint, a3, d);
				}
			}
			else
			{
				if (p2.y > p1.y)
				{
					// 左下方向
					LysBoardDrawUtils.drawLine(canvas, mPaint, p1, a1);
					LysBoardDrawUtils.drawLine(canvas, mPaint, a1, a2);
					LysBoardDrawUtils.drawLine(canvas, mPaint, a2, a3);
					LysBoardDrawUtils.drawLine(canvas, mPaint, a3, p1);

					LysBoardDrawUtils.drawLine(canvas, mPaintImaginary, p2, d);
					LysBoardDrawUtils.drawLine(canvas, mPaintImaginary, d, e);
					LysBoardDrawUtils.drawLine(canvas, mPaint, e, c);
					LysBoardDrawUtils.drawLine(canvas, mPaint, c, p2);

					LysBoardDrawUtils.drawLine(canvas, mPaint, p1, e);
					LysBoardDrawUtils.drawLine(canvas, mPaint, a1, c);
					LysBoardDrawUtils.drawLine(canvas, mPaint, a2, p2);
					LysBoardDrawUtils.drawLine(canvas, mPaintImaginary, a3, d);
				}
				else
				{
					// 左上方向
					LysBoardDrawUtils.drawLine(canvas, mPaint, p1, a1);
					LysBoardDrawUtils.drawLine(canvas, mPaintImaginary, a1, a2);
					LysBoardDrawUtils.drawLine(canvas, mPaintImaginary, a2, a3);
					LysBoardDrawUtils.drawLine(canvas, mPaint, a3, p1);

					LysBoardDrawUtils.drawLine(canvas, mPaint, p2, d);
					LysBoardDrawUtils.drawLine(canvas, mPaint, d, e);
					LysBoardDrawUtils.drawLine(canvas, mPaint, e, c);
					LysBoardDrawUtils.drawLine(canvas, mPaint, c, p2);

					LysBoardDrawUtils.drawLine(canvas, mPaint, p1, e);
					LysBoardDrawUtils.drawLine(canvas, mPaint, a1, c);
					LysBoardDrawUtils.drawLine(canvas, mPaintImaginary, a2, p2);
					LysBoardDrawUtils.drawLine(canvas, mPaint, a3, d);
				}
			}

			if (false) // debug
			{
				Paint paint = new Paint();
				paint.setAntiAlias(true);
				paint.setDither(true);
				paint.setStrokeCap(Paint.Cap.ROUND);
				paint.setStyle(Paint.Style.STROKE);
				paint.setStrokeWidth(10);

				paint.setColor(Color.RED);
				LysBoardDrawUtils.drawPoint(canvas, paint, a1);

				paint.setColor(Color.GREEN);
				LysBoardDrawUtils.drawPoint(canvas, paint, a2);

				paint.setColor(Color.BLUE);
				LysBoardDrawUtils.drawPoint(canvas, paint, a3);

				paint.setColor(Color.RED);
				LysBoardDrawUtils.drawPoint(canvas, paint, c);

				paint.setColor(Color.GREEN);
				LysBoardDrawUtils.drawPoint(canvas, paint, d);

				paint.setColor(Color.BLUE);
				LysBoardDrawUtils.drawPoint(canvas, paint, e);
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
		json.put("drawingType", LysBoardDrawingType.Cube);
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
		drawing.drawingType = LysBoardDrawingType.Cube;
		SDrawingCube cube = new SDrawingCube();
		cube.paintColor = mPaint.getColor();
		cube.strokeWidth = mPaint.getStrokeWidth();
		cube.posStart = posStart.saveToProto();
		cube.posStop = posStop.saveToProto();
		drawing.cube = cube;
		return drawing;
	}
}
