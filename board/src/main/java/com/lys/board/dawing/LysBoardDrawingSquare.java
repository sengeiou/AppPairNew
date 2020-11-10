package com.lys.board.dawing;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.alibaba.fastjson.JSONObject;
import com.lys.board.utils.LysBoardPoint;
import com.lys.protobuf.SDrawing;
import com.lys.protobuf.SDrawingSquare;

public class LysBoardDrawingSquare extends LysBoardDrawing
{
	private Paint mPaint = null;

	private LysBoardPoint posStart = null;
	private LysBoardPoint posStop = null;

	public LysBoardDrawingSquare()
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
		SDrawingSquare square = proto.square;
		mPaint.setColor(square.paintColor);
		mPaint.setStrokeWidth(square.strokeWidth);
		posStart = new LysBoardPoint(square.posStart);
		posStop = new LysBoardPoint(square.posStop);
	}

	@Override
	public void downPoint(LysBoardPoint point)
	{
		posStart = point;
	}

	public void add(LysBoardPoint point)
	{
		posStop = point;
		if (isValid())
		{
			float distanceX = Math.abs(posStart.x - posStop.x);
			float distanceY = Math.abs(posStart.y - posStop.y);
			if (distanceX > distanceY)
			{
				if (posStop.y < posStart.y)
					posStop.y = posStart.y - distanceX;
				else
					posStop.y = posStart.y + distanceX;
			}
			else
			{
				if (posStop.x < posStart.x)
					posStop.x = posStart.x - distanceY;
				else
					posStop.x = posStart.x + distanceY;
			}
		}
	}

	@Override
	public void movePoint(LysBoardPoint point)
	{
		add(point);
	}

	@Override
	public void upPoint(LysBoardPoint point)
	{
		add(point);
	}

	@Override
	public void draw(Canvas canvas)
	{
		if (isValid())
		{
			LysBoardPoint pa = posStart;
			LysBoardPoint pb = posStop;
			float left = Math.min(pa.x, pb.x);
			float top = Math.min(pa.y, pb.y);
			float right = Math.max(pa.x, pb.x);
			float bottom = Math.max(pa.y, pb.y);
			canvas.drawRect(left, top, right, bottom, mPaint);
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
		json.put("drawingType", LysBoardDrawingType.Square);
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
		drawing.drawingType = LysBoardDrawingType.Square;
		SDrawingSquare square = new SDrawingSquare();
		square.paintColor = mPaint.getColor();
		square.strokeWidth = mPaint.getStrokeWidth();
		square.posStart = posStart.saveToProto();
		square.posStop = posStop.saveToProto();
		drawing.square = square;
		return drawing;
	}
}
