package com.lys.board.dawing;

import android.graphics.Canvas;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lys.board.utils.LysBoardDrawUtils;
import com.lys.board.utils.LysBoardPoint;
import com.lys.protobuf.SDrawing;
import com.lys.protobuf.SDrawingStroke;

import java.util.ArrayList;
import java.util.List;

public class LysBoardDrawingStroke extends LysBoardDrawing
{
	private Paint mPaint = null;

	private ArrayList<LysBoardPoint> points = new ArrayList<>();

	public LysBoardDrawingStroke()
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
		JSONArray array = json.getJSONArray("points");
		for (int i = 0; i < array.size(); i++)
		{
			points.add(new LysBoardPoint(array.getJSONObject(i)));
		}
	}

	@Override
	public void init(SDrawing proto)
	{
		SDrawingStroke stroke = proto.stroke;
		mPaint.setColor(stroke.paintColor);
		mPaint.setStrokeWidth(stroke.strokeWidth);
		for (int i = 0; i < stroke.points.size(); i++)
		{
			points.add(new LysBoardPoint(stroke.points.get(i)));
		}
	}

	private void add(LysBoardPoint point)
	{
		if (points.size() > 0)
		{
			LysBoardPoint lastPoint = points.get(points.size() - 1);
//			if (!lastPoint.equals(point))
			if (lastPoint.distance(point) > 4)
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

	// 3次B样条
	public List<LysBoardPoint> convert3b(List<LysBoardPoint> points)
	{
		List<LysBoardPoint> newPnts = new ArrayList<>();
		int pntLen = points.size();
		newPnts.add(new LysBoardPoint(points.get(0).x, points.get(0).y));
		for (int n = 0; n < pntLen; n++)
		{
			if (n <= pntLen - 4)
			{
				for (double t = 0.0; t <= 1.0; t += 0.1)
				{
					double a1 = Math.pow((1 - t), 3) / 6;
					double a2 = (3 * Math.pow(t, 3) - 6 * Math.pow(t, 2) + 4) / 6;
					double a3 = (-3 * Math.pow(t, 3) + 3 * Math.pow(t, 2) + 3 * t + 1) / 6;
					double a4 = Math.pow(t, 3) / 6;

					double x = a1 * points.get(n).x + a2 * points.get(n + 1).x + a3 * points.get(n + 2).x + a4 * points.get(n + 3).x;
					double y = a1 * points.get(n).y + a2 * points.get(n + 1).y + a3 * points.get(n + 2).y + a4 * points.get(n + 3).y;

					LysBoardPoint point = new LysBoardPoint((float) x, (float) y);

					LysBoardPoint lastPoint = newPnts.get(newPnts.size() - 1);
					if (lastPoint.distance(point) > 4)
					{
						newPnts.add(point);
					}
				}
			}
		}
		newPnts.add(new LysBoardPoint(points.get(pntLen - 1).x, points.get(pntLen - 1).y));
//		LOG.v(points.size() + " -- " + newPnts.size());
		return newPnts;
	}

	@Override
	public void draw(Canvas canvas)
	{
		LysBoardDrawUtils.drawPath(canvas, mPaint, false, convert3b(points));

		if (false)
		{
			final int offset = 200;
			int pos = 0;

			pos += offset;
			canvas.translate(0, pos);
			LysBoardDrawUtils.drawPath(canvas, mPaint, false, points);
			canvas.translate(0, -pos);

			pos += offset;
			canvas.translate(0, pos);
			LysBoardDrawUtils.drawPoints(canvas, mPaint, points);
			canvas.translate(0, -pos);
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
		json.put("drawingType", LysBoardDrawingType.Stroke);
		json.put("paintColor", mPaint.getColor());
		json.put("strokeWidth", mPaint.getStrokeWidth());
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
		drawing.drawingType = LysBoardDrawingType.Stroke;
		SDrawingStroke stroke = new SDrawingStroke();
		stroke.paintColor = mPaint.getColor();
		stroke.strokeWidth = mPaint.getStrokeWidth();
		for (LysBoardPoint point : points)
		{
			stroke.points.add(point.saveToProto());
		}
		drawing.stroke = stroke;
		return drawing;
	}
}
