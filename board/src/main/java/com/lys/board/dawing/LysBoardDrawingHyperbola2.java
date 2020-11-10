package com.lys.board.dawing;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.alibaba.fastjson.JSONObject;
import com.lys.board.utils.LysBoardDrawUtils;
import com.lys.board.utils.LysBoardPoint;
import com.lys.board.utils.LysBoardUtils;
import com.lys.protobuf.SDrawing;
import com.lys.protobuf.SDrawingHyperbola2;

public class LysBoardDrawingHyperbola2 extends LysBoardDrawing
{
	private Paint mPaint = null;

	private LysBoardPoint posStart = null;
	private LysBoardPoint posStop = null;

	public LysBoardDrawingHyperbola2()
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
		SDrawingHyperbola2 hyperbola2 = proto.hyperbola2;
		mPaint.setColor(hyperbola2.paintColor);
		mPaint.setStrokeWidth(hyperbola2.strokeWidth);
		posStart = new LysBoardPoint(hyperbola2.posStart);
		posStop = new LysBoardPoint(hyperbola2.posStop);
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

	private float localCalculateY(float x)
	{
		LysBoardPoint p1 = posStart;
		LysBoardPoint p2 = posStop;

		double y = p1.y + Math.sqrt(Math.pow(x - p1.x, 2) / Math.pow(p2.x - p1.x, 2) - 1) * Math.abs(p2.y - p1.y);
		return (float) y;
	}

	private float localCalculateYSymmetry(float x)
	{
		LysBoardPoint p1 = posStart;

		float y = localCalculateY(x);
		y = LysBoardUtils.calculateSymmetry(p1.y, y);
		return y;
	}

	private float localCalculateX(float y)
	{
		LysBoardPoint p1 = posStart;
		LysBoardPoint p2 = posStop;

		double x = p1.x + Math.sqrt(Math.pow(y - p1.y, 2) / Math.pow(p2.y - p1.y, 2) - 1) * Math.abs(p2.x - p1.x);
		return (float) x;
	}

	private float localCalculateXSymmetry(float y)
	{
		LysBoardPoint p1 = posStart;

		float x = localCalculateX(y);
		x = LysBoardUtils.calculateSymmetry(p1.x, x);
		return x;
	}

	@Override
	public void draw(Canvas canvas)
	{
		if (isValid())
		{
			LysBoardPoint p1 = posStart;
			LysBoardPoint p2 = posStop;

			float distanceX = p1.distanceX(p2);
			float distanceY = p1.distanceY(p2);

			if (p2.x < p1.x)
			{
				LysBoardDrawUtils.drawFormulaX(canvas, mPaint, new LysBoardDrawUtils.LysFormulaX()
				{
					@Override
					public float calculateX(float y)
					{
						return localCalculateX(y);
					}
				}, p1.y + distanceY, p1.y + distanceY * 2);

				LysBoardDrawUtils.drawFormulaX(canvas, mPaint, new LysBoardDrawUtils.LysFormulaX()
				{
					@Override
					public float calculateX(float y)
					{
						return localCalculateXSymmetry(y);
					}
				}, p1.y + distanceY, p1.y + distanceY * 2);

				LysBoardDrawUtils.drawFormulaX(canvas, mPaint, new LysBoardDrawUtils.LysFormulaX()
				{
					@Override
					public float calculateX(float y)
					{
						return localCalculateX(y);
					}
				}, p1.y - distanceY, p1.y - distanceY * 2);

				LysBoardDrawUtils.drawFormulaX(canvas, mPaint, new LysBoardDrawUtils.LysFormulaX()
				{
					@Override
					public float calculateX(float y)
					{
						return localCalculateXSymmetry(y);

					}
				}, p1.y - distanceY, p1.y - distanceY * 2);
			}
			else
			{
				LysBoardDrawUtils.drawFormulaY(canvas, mPaint, new LysBoardDrawUtils.LysFormulaY()
				{
					@Override
					public float calculateY(float x)
					{
						return localCalculateY(x);
					}
				}, p1.x + distanceX, p1.x + distanceX * 2);

				LysBoardDrawUtils.drawFormulaY(canvas, mPaint, new LysBoardDrawUtils.LysFormulaY()
				{
					@Override
					public float calculateY(float x)
					{
						return localCalculateYSymmetry(x);
					}
				}, p1.x + distanceX, p1.x + distanceX * 2);

				LysBoardDrawUtils.drawFormulaY(canvas, mPaint, new LysBoardDrawUtils.LysFormulaY()
				{
					@Override
					public float calculateY(float x)
					{
						return localCalculateY(x);
					}
				}, p1.x - distanceX, p1.x - distanceX * 2);

				LysBoardDrawUtils.drawFormulaY(canvas, mPaint, new LysBoardDrawUtils.LysFormulaY()
				{
					@Override
					public float calculateY(float x)
					{
						return localCalculateYSymmetry(x);
					}
				}, p1.x - distanceX, p1.x - distanceX * 2);
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
		json.put("drawingType", LysBoardDrawingType.Hyperbola2);
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
		drawing.drawingType = LysBoardDrawingType.Hyperbola2;
		SDrawingHyperbola2 hyperbola2 = new SDrawingHyperbola2();
		hyperbola2.paintColor = mPaint.getColor();
		hyperbola2.strokeWidth = mPaint.getStrokeWidth();
		hyperbola2.posStart = posStart.saveToProto();
		hyperbola2.posStop = posStop.saveToProto();
		drawing.hyperbola2 = hyperbola2;
		return drawing;
	}
}
