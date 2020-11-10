package com.lys.board.dawing;

import android.graphics.Canvas;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lys.board.utils.LysBoardDrawUtils;
import com.lys.board.utils.LysBoardPoint;
import com.lys.protobuf.SDrawing;
import com.lys.protobuf.SDrawingWater;

import java.util.ArrayList;

public class LysBoardDrawingWater extends LysBoardDrawing
{
	private static final float bei = 10;

	private Paint mPaint = null;

	private float mStrokeWidth;

	private ArrayList<LysBoardPoint> points = new ArrayList<>();

	public LysBoardDrawingWater()
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
		mStrokeWidth = strokeWidth;
		mPaint.setStrokeWidth(mStrokeWidth * bei);
	}

	@Override
	public void init(JSONObject json)
	{
		mPaint.setColor(json.getInteger("paintColor"));
		mStrokeWidth = json.getFloat("strokeWidth");
		mPaint.setStrokeWidth(mStrokeWidth * bei);
		JSONArray array = json.getJSONArray("points");
		for (int i = 0; i < array.size(); i++)
		{
			points.add(new LysBoardPoint(array.getJSONObject(i)));
		}
	}

	@Override
	public void init(SDrawing proto)
	{
		SDrawingWater water = proto.water;
		mPaint.setColor(water.paintColor);
		mStrokeWidth = water.strokeWidth;
		mPaint.setStrokeWidth(mStrokeWidth * bei);
		for (int i = 0; i < water.points.size(); i++)
		{
			points.add(new LysBoardPoint(water.points.get(i)));
		}
	}

	private void add(LysBoardPoint point)
	{
		if (points.size() > 0)
		{
			LysBoardPoint lastPoint = points.get(points.size() - 1);
			if (!lastPoint.equals(point))
			{
				points.add(point);
			}
		}
		else
		{
			points.add(point);
		}
	}

	@Override
	public void downPoint(LysBoardPoint point)
	{
		add(point);
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
		LysBoardDrawUtils.drawPath(canvas, mPaint, false, points);
	}

	@Override
	public void drawGizmo(Canvas canvas)
	{

	}

	@Override
	public JSONObject saveToJson()
	{
		JSONObject json = new JSONObject(true);
		json.put("drawingType", LysBoardDrawingType.Water);
		json.put("paintColor", mPaint.getColor());
		json.put("strokeWidth", mStrokeWidth);
		JSONArray array = new JSONArray();
		for (LysBoardPoint point : points)
		{
			array.add(point.saveToJson());
		}
		json.put("points", array);
		return json;
	}

	@Override
	public SDrawing saveToProto()
	{
		SDrawing drawing = new SDrawing();
		drawing.drawingType = LysBoardDrawingType.Water;
		SDrawingWater water = new SDrawingWater();
		water.paintColor = mPaint.getColor();
		water.strokeWidth = mStrokeWidth;
		for (LysBoardPoint point : points)
		{
			water.points.add(point.saveToProto());
		}
		drawing.water = water;
		return drawing;
	}
}
