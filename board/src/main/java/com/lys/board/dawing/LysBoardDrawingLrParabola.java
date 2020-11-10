package com.lys.board.dawing;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.alibaba.fastjson.JSONObject;
import com.lys.board.utils.LysBoardDrawUtils;
import com.lys.board.utils.LysBoardPoint;
import com.lys.board.utils.LysBoardUtils;
import com.lys.protobuf.SDrawing;
import com.lys.protobuf.SDrawingLrParabola;

public class LysBoardDrawingLrParabola extends LysBoardDrawing
{
	private Paint mPaint = null;

	private LysBoardPoint posStart = null;
	private LysBoardPoint posStop = null;

	public LysBoardDrawingLrParabola()
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
		SDrawingLrParabola lrParabola = proto.lrParabola;
		mPaint.setColor(lrParabola.paintColor);
		mPaint.setStrokeWidth(lrParabola.strokeWidth);
		posStart = new LysBoardPoint(lrParabola.posStart);
		posStop = new LysBoardPoint(lrParabola.posStop);
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

	private float localCalculateX(float y)
	{
		LysBoardPoint p1 = posStart;
		LysBoardPoint p2 = posStop;

		double tmp = (y - p1.y) / Math.abs(p2.y - p1.y);
		double x = Math.pow(tmp, 2) * (p2.x - p1.x) + p1.x;
		return (float) x;
	}

	@Override
	public void draw(Canvas canvas)
	{
		if (isValid())
		{
			LysBoardPoint p1 = posStart;
			LysBoardPoint p2 = posStop;
			LysBoardPoint p3 = LysBoardUtils.calculateSymmetryX(p1, p2);
			LysBoardDrawUtils.drawFormulaX(canvas, mPaint, new LysBoardDrawUtils.LysFormulaX()
			{
				@Override
				public float calculateX(float y)
				{
					return localCalculateX(y);
				}
			}, p2.y, p3.y);
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
		json.put("drawingType", LysBoardDrawingType.LrParabola);
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
		drawing.drawingType = LysBoardDrawingType.LrParabola;
		SDrawingLrParabola lrParabola = new SDrawingLrParabola();
		lrParabola.paintColor = mPaint.getColor();
		lrParabola.strokeWidth = mPaint.getStrokeWidth();
		lrParabola.posStart = posStart.saveToProto();
		lrParabola.posStop = posStop.saveToProto();
		drawing.lrParabola = lrParabola;
		return drawing;
	}
}
