package com.lys.board.dawing;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.alibaba.fastjson.JSONObject;
import com.lys.board.utils.LysBoardDrawUtils;
import com.lys.board.utils.LysBoardPoint;
import com.lys.protobuf.SDrawing;
import com.lys.protobuf.SDrawingHyperbola;

public class LysBoardDrawingHyperbola extends LysBoardDrawing
{
	private Paint mPaint = null;

	private LysBoardPoint posStart = null;
	private LysBoardPoint posStop = null;

	public LysBoardDrawingHyperbola()
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
		SDrawingHyperbola hyperbola = proto.hyperbola;
		mPaint.setColor(hyperbola.paintColor);
		mPaint.setStrokeWidth(hyperbola.strokeWidth);
		posStart = new LysBoardPoint(hyperbola.posStart);
		posStop = new LysBoardPoint(hyperbola.posStop);
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

		double tmp = (p2.x - p1.x) * (p2.y - p1.y);
		double y = tmp / (x - p1.x) + p1.y;
		return (float) y;
	}

	private float localCalculateX(float y)
	{
		LysBoardPoint p1 = posStart;
		LysBoardPoint p2 = posStop;

		double tmp = (p2.x - p1.x) * (p2.y - p1.y);
		double x = tmp / (y - p1.y) + p1.x;
		return (float) x;
	}

	@Override
	public void draw(Canvas canvas)
	{
		if (isValid())
		{
			LysBoardPoint p1 = posStart;
			LysBoardPoint p2 = posStop;

			float minDistance = Math.min(p1.distanceX(p2), p1.distanceY(p2));
			float maxDistance = Math.max(p1.distanceX(p2), p1.distanceY(p2));

			LysBoardDrawUtils.drawFormulaY(canvas, mPaint, new LysBoardDrawUtils.LysFormulaY()
			{
				@Override
				public float calculateY(float x)
				{
					return localCalculateY(x);
				}
			}, p1.x + minDistance, p1.x + maxDistance * 3);

			LysBoardDrawUtils.drawFormulaY(canvas, mPaint, new LysBoardDrawUtils.LysFormulaY()
			{
				@Override
				public float calculateY(float x)
				{
					return localCalculateY(x);
				}
			}, p1.x - minDistance, p1.x - maxDistance * 3);

			LysBoardDrawUtils.drawFormulaX(canvas, mPaint, new LysBoardDrawUtils.LysFormulaX()
			{
				@Override
				public float calculateX(float y)
				{
					return localCalculateX(y);
				}
			}, p1.y - minDistance, p1.y - maxDistance * 3);

			LysBoardDrawUtils.drawFormulaX(canvas, mPaint, new LysBoardDrawUtils.LysFormulaX()
			{
				@Override
				public float calculateX(float y)
				{
					return localCalculateX(y);
				}
			}, p1.y + minDistance, p1.y + maxDistance * 3);

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
		json.put("drawingType", LysBoardDrawingType.Hyperbola);
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
		drawing.drawingType = LysBoardDrawingType.Hyperbola;
		SDrawingHyperbola hyperbola = new SDrawingHyperbola();
		hyperbola.paintColor = mPaint.getColor();
		hyperbola.strokeWidth = mPaint.getStrokeWidth();
		hyperbola.posStart = posStart.saveToProto();
		hyperbola.posStop = posStop.saveToProto();
		drawing.hyperbola = hyperbola;
		return drawing;
	}
}
