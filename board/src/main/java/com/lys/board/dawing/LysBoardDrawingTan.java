package com.lys.board.dawing;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.alibaba.fastjson.JSONObject;
import com.lys.board.utils.LysBoardDrawUtils;
import com.lys.board.utils.LysBoardPoint;
import com.lys.protobuf.SDrawing;
import com.lys.protobuf.SDrawingTan;

public class LysBoardDrawingTan extends LysBoardDrawing
{
	private static final float mMultiple = 4;

	private Paint mPaint = null;
	private Paint mPaintGizmo = null;

	private LysBoardPoint posStart = null;
	private LysBoardPoint posStop = null;

	public LysBoardDrawingTan()
	{
		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setDither(true);
		mPaint.setStrokeCap(Paint.Cap.ROUND);
		mPaint.setStyle(Paint.Style.STROKE);

		mPaintGizmo = new Paint();
		mPaintGizmo.setAntiAlias(true);
		mPaintGizmo.setDither(true);
		mPaintGizmo.setStyle(Paint.Style.STROKE);
		mPaintGizmo.setColor(Color.BLACK);
		mPaintGizmo.setStrokeWidth(1);
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
		SDrawingTan tan = proto.tan;
		mPaint.setColor(tan.paintColor);
		mPaint.setStrokeWidth(tan.strokeWidth);
		posStart = new LysBoardPoint(tan.posStart);
		posStop = new LysBoardPoint(tan.posStop);
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

	protected float localCalculateX(float y)
	{
		LysBoardPoint p1 = posStart;
		LysBoardPoint p2 = posStop;

		double tmp = Math.atan((y - p1.y) / (p2.y - p1.y));
		double x = tmp * (2 * (p2.x - p1.x)) / Math.PI + p1.x;
		return (float) x;
	}

	@Override
	public void draw(Canvas canvas)
	{
		if (isValid())
		{
			LysBoardPoint p1 = posStart;
			LysBoardPoint p2 = posStop;
			float distanceY = p1.distanceY(p2);

			float yFrom = p1.y - 4 * distanceY;
			float yTo = p1.y + 4 * distanceY;

			LysBoardDrawUtils.drawFormulaX(canvas, mPaint, new LysBoardDrawUtils.LysFormulaX()
			{
				@Override
				public float calculateX(float y)
				{
					return localCalculateX(y);
				}
			}, yFrom, yTo);
		}
	}

	@Override
	public void drawGizmo(Canvas canvas)
	{
		if (isValid())
		{
			LysBoardPoint pa = posStart;
			LysBoardPoint pb = posStop;
			float distanceY = Math.abs(pa.y - pb.y);
			float yFrom = pa.y - mMultiple * distanceY;
			float yTo = pa.y + mMultiple * distanceY;
			float x = 2 * pa.x - pb.x;
			LysBoardDrawUtils.drawLine(canvas, mPaintGizmo, new LysBoardPoint(x, yFrom), new LysBoardPoint(x, yTo));
			LysBoardDrawUtils.drawLine(canvas, mPaintGizmo, new LysBoardPoint(pb.x, yFrom), new LysBoardPoint(pb.x, yTo));
		}
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
		json.put("drawingType", LysBoardDrawingType.Tan);
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
		drawing.drawingType = LysBoardDrawingType.Tan;
		SDrawingTan tan = new SDrawingTan();
		tan.paintColor = mPaint.getColor();
		tan.strokeWidth = mPaint.getStrokeWidth();
		tan.posStart = posStart.saveToProto();
		tan.posStop = posStop.saveToProto();
		drawing.tan = tan;
		return drawing;
	}
}
