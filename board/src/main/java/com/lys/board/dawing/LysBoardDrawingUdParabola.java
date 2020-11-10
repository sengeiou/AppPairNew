package com.lys.board.dawing;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.alibaba.fastjson.JSONObject;
import com.lys.board.utils.LysBoardDrawUtils;
import com.lys.board.utils.LysBoardPoint;
import com.lys.board.utils.LysBoardUtils;
import com.lys.protobuf.SDrawing;
import com.lys.protobuf.SDrawingUdParabola;

public class LysBoardDrawingUdParabola extends LysBoardDrawing
{
	private Paint mPaint = null;

	private LysBoardPoint posStart = null;
	private LysBoardPoint posStop = null;

	public LysBoardDrawingUdParabola()
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
		SDrawingUdParabola udParabola = proto.udParabola;
		mPaint.setColor(udParabola.paintColor);
		mPaint.setStrokeWidth(udParabola.strokeWidth);
		posStart = new LysBoardPoint(udParabola.posStart);
		posStop = new LysBoardPoint(udParabola.posStop);
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
		y = (float) (Math.pow((x - p1.x) / Math.abs(p2.x - p1.x), 2) * (p2.y - p1.y) + p1.y);
		return y;
	}

	@Override
	public void draw(Canvas canvas)
	{
		if (isValid())
		{
			LysBoardPoint p1 = posStart;
			LysBoardPoint p2 = posStop;
			LysBoardPoint p3 = LysBoardUtils.calculateSymmetryY(p1, p2);
			LysBoardDrawUtils.drawFormulaY(canvas, mPaint, new LysBoardDrawUtils.LysFormulaY()
			{
				@Override
				public float calculateY(float x)
				{
					return localCalculateY(x);
				}
			}, p2.x, p3.x);
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
		json.put("drawingType", LysBoardDrawingType.UdParabola);
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
		drawing.drawingType = LysBoardDrawingType.UdParabola;
		SDrawingUdParabola udParabola = new SDrawingUdParabola();
		udParabola.paintColor = mPaint.getColor();
		udParabola.strokeWidth = mPaint.getStrokeWidth();
		udParabola.posStart = posStart.saveToProto();
		udParabola.posStop = posStop.saveToProto();
		drawing.udParabola = udParabola;
		return drawing;
	}
}
