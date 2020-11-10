package com.lys.board.dawing;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lys.board.utils.LysBoardDrawUtils;
import com.lys.board.utils.LysBoardPoint;
import com.lys.protobuf.SDrawing;
import com.lys.protobuf.SDrawingStress;

import java.util.ArrayList;

public class LysBoardDrawingStress extends LysBoardDrawing
{
	private static final float mSensitivity = 1.5f;

	private Paint mPaint = null;
	private float mStrokeWidth;

	private ArrayList<LysBoardPoint> points = new ArrayList<>();

	public LysBoardDrawingStress()
	{
		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setDither(true);
		mPaint.setFilterBitmap(true);
		mPaint.setStrokeCap(Paint.Cap.ROUND);
		mPaint.setStrokeJoin(Paint.Join.ROUND);
		mPaint.setStyle(Paint.Style.STROKE);
	}

	@Override
	public void init(int paintColor, float strokeWidth)
	{
		mPaint.setColor(paintColor);
		mStrokeWidth = strokeWidth;
	}

	@Override
	public void init(JSONObject json)
	{
		mPaint.setColor(json.getInteger("paintColor"));
		mStrokeWidth = json.getFloat("strokeWidth");
		JSONArray array = json.getJSONArray("points");
		for (int i = 0; i < array.size(); i++)
		{
			points.add(new LysBoardPoint(array.getJSONObject(i)));
		}
	}

	@Override
	public void init(SDrawing proto)
	{
		SDrawingStress stress = proto.stress;
		mPaint.setColor(stress.paintColor);
		mStrokeWidth = stress.strokeWidth;
		for (int i = 0; i < stress.points.size(); i++)
		{
			points.add(new LysBoardPoint(stress.points.get(i)));
		}
	}

	private void add(LysBoardPoint point)
	{
		if (points.size() > 0)
		{
			LysBoardPoint lastPoint = points.get(points.size() - 1);
			double density = mStrokeWidth * mSensitivity;
			if (lastPoint.distance(point) > density)
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

	private int mIndexPoint = 0;

	@Override
	public void draw(Canvas canvas)
	{
		for (; mIndexPoint < points.size() - 3; mIndexPoint++)
		{
			LysBoardDrawUtils.drawSmoothPoints(canvas, mPaint, mStrokeWidth, mSensitivity, points, mIndexPoint);
		}
	}

	@Override
	public void drawGizmo(Canvas canvas)
	{

	}

	@Override
	public JSONObject saveToJson()
	{
		JSONObject json = new JSONObject(true);
		json.put("drawingType", LysBoardDrawingType.Stress);
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
		drawing.drawingType = LysBoardDrawingType.Stress;
		SDrawingStress stress = new SDrawingStress();
		stress.paintColor = mPaint.getColor();
		stress.strokeWidth = mStrokeWidth;
		for (LysBoardPoint point : points)
		{
			stress.points.add(point.saveToProto());
		}
		drawing.stress = stress;
		return drawing;
	}
}
