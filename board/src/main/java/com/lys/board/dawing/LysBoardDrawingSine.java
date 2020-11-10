package com.lys.board.dawing;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.alibaba.fastjson.JSONObject;
import com.lys.board.utils.LysBoardDrawUtils;
import com.lys.board.utils.LysBoardPoint;
import com.lys.protobuf.SDrawing;
import com.lys.protobuf.SDrawingSine;

public class LysBoardDrawingSine extends LysBoardDrawing
{
	private Paint mPaint = null;

	private LysBoardPoint posStart = null;
	private LysBoardPoint posStop = null;

	public LysBoardDrawingSine()
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
		SDrawingSine sine = proto.sine;
		mPaint.setColor(sine.paintColor);
		mPaint.setStrokeWidth(sine.strokeWidth);
		posStart = new LysBoardPoint(sine.posStart);
		posStop = new LysBoardPoint(sine.posStop);
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

		float y = 0;
		y = (float) ((p2.y - p1.y) * Math.sin(Math.PI / (2 * (p2.x - p1.x)) * (x - p1.x)) + p1.y);
		return y;
	}

	@Override
	public void draw(Canvas canvas)
	{
		if (isValid())
		{
			LysBoardPoint p1 = posStart;
			LysBoardPoint p2 = posStop;
			float xFrom = p1.x - (p2.x - p1.x) * 4;
			float xTo = p1.x + (p2.x - p1.x) * 8;
			LysBoardDrawUtils.drawFormulaY(canvas, mPaint, new LysBoardDrawUtils.LysFormulaY()
			{
				@Override
				public float calculateY(float x)
				{
					return localCalculateY(x);
				}
			}, xFrom, xTo);
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
				posStart.distance(posStop) > 0;
	}

	@Override
	public JSONObject saveToJson()
	{
		JSONObject json = new JSONObject(true);
		json.put("drawingType", LysBoardDrawingType.Sine);
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
		drawing.drawingType = LysBoardDrawingType.Sine;
		SDrawingSine sine = new SDrawingSine();
		sine.paintColor = mPaint.getColor();
		sine.strokeWidth = mPaint.getStrokeWidth();
		sine.posStart = posStart.saveToProto();
		sine.posStop = posStop.saveToProto();
		drawing.sine = sine;
		return drawing;
	}
}
