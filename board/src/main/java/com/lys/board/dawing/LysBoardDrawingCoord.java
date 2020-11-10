package com.lys.board.dawing;

import android.graphics.Canvas;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;

import com.alibaba.fastjson.JSONObject;
import com.lys.board.utils.LysBoardDrawUtils;
import com.lys.board.utils.LysBoardPoint;
import com.lys.protobuf.SDrawing;
import com.lys.protobuf.SDrawingCoord;

public class LysBoardDrawingCoord extends LysBoardDrawing
{
	private Paint mPaint = null;

	private LysBoardPoint posStart = null;
	private LysBoardPoint posStop = null;

	public LysBoardDrawingCoord()
	{
		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setDither(true);
		mPaint.setFilterBitmap(true);
		mPaint.setStrokeCap(Paint.Cap.ROUND);
		mPaint.setStrokeJoin(Paint.Join.ROUND);
		mPaint.setStyle(Paint.Style.STROKE);
		mPaint.setPathEffect(new CornerPathEffect(10));
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
		SDrawingCoord coord = proto.coord;
		mPaint.setColor(coord.paintColor);
		mPaint.setStrokeWidth(coord.strokeWidth);
		posStart = new LysBoardPoint(coord.posStart);
		posStop = new LysBoardPoint(coord.posStop);
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

	@Override
	public void draw(Canvas canvas)
	{
		if (isValid())
		{
			LysBoardPoint p1 = posStart;
			LysBoardPoint p2 = posStop;

			LysBoardPoint pxFrom = new LysBoardPoint(p1.x - (p2.x - p1.x) * 2, p1.y);
			LysBoardPoint pxTo = new LysBoardPoint(p1.x + (p2.x - p1.x) * 2, p1.y);
			LysBoardPoint pyFrom = new LysBoardPoint(p1.x, p1.y - (p2.y - p1.y) * 2);
			LysBoardPoint pyTo = new LysBoardPoint(p1.x, p1.y + (p2.y - p1.y) * 2);

			LysBoardDrawUtils.drawArrowsLine(canvas, mPaint, pxFrom, pxTo);
			LysBoardDrawUtils.drawArrowsLine(canvas, mPaint, pyFrom, pyTo);
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
		json.put("drawingType", LysBoardDrawingType.Coord);
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
		drawing.drawingType = LysBoardDrawingType.Coord;
		SDrawingCoord coord = new SDrawingCoord();
		coord.paintColor = mPaint.getColor();
		coord.strokeWidth = mPaint.getStrokeWidth();
		coord.posStart = posStart.saveToProto();
		coord.posStop = posStop.saveToProto();
		drawing.coord = coord;
		return drawing;
	}
}
