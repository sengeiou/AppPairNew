package com.lys.board.dawing;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lys.base.utils.LOG;
import com.lys.board.utils.LysBoardDrawUtils;
import com.lys.board.utils.LysBoardPoint;
import com.lys.board.utils.LysBoardPointRadius;
import com.lys.protobuf.SDrawing;
import com.lys.protobuf.SDrawingEraser;
import com.lys.protobuf.SFullArea;

import java.util.ArrayList;
import java.util.List;

public class LysBoardDrawingEraser extends LysBoardDrawing
{
	private static final float mInitR = 8;
	private static final float mSensitivity = 12;

	private Paint mPaint = null;
	private Paint mPaintGizmo = null;

	private ArrayList<LysBoardPointRadius> points = new ArrayList<>();
	private ArrayList<ArrayList<LysBoardPoint>> fullAreas = new ArrayList<>();

	public LysBoardDrawingEraser()
	{
		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setDither(true);
		mPaint.setStyle(Paint.Style.FILL);
		mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));

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

	}

	@Override
	public void init(JSONObject json)
	{
		{
			JSONArray array = json.getJSONArray("points");
			for (int i = 0; i < array.size(); i++)
			{
				points.add(new LysBoardPointRadius(array.getJSONObject(i)));
			}
		}
		{
			JSONArray array = json.getJSONArray("fullAreas");
			for (int i = 0; i < array.size(); i++)
			{
				JSONArray arr = array.getJSONArray(i);
				ArrayList<LysBoardPoint> fullArea = new ArrayList<>();
				for (int j = 0; j < arr.size(); j++)
				{
					fullArea.add(new LysBoardPoint(arr.getJSONObject(j)));
				}
				fullAreas.add(fullArea);
			}
		}
	}

	@Override
	public void init(SDrawing proto)
	{
		SDrawingEraser eraser = proto.eraser;
		for (int i = 0; i < eraser.points.size(); i++)
		{
			points.add(new LysBoardPointRadius(eraser.points.get(i)));
		}
		for (int i = 0; i < eraser.fullAreas.size(); i++)
		{
			SFullArea area = eraser.fullAreas.get(i);
			ArrayList<LysBoardPoint> fullArea = new ArrayList<>();
			for (int j = 0; j < area.points.size(); j++)
			{
				fullArea.add(new LysBoardPoint(area.points.get(j)));
			}
			fullAreas.add(fullArea);
		}
	}

	private void add(LysBoardPoint point)
	{
		if (points.size() > 0)
		{
			LysBoardPointRadius lastPoint = points.get(points.size() - 1);
			if (!lastPoint.equals(point))
			{
				double time = point.timestamp - lastPoint.timestamp;
				if (time > 4)
				{
					double radius = lastPoint.distance(point) / time * mSensitivity;
					radius = Math.max(radius, mInitR);
					ArrayList<LysBoardPoint> fullArea = calculateFullArea(lastPoint, lastPoint.radius, point, radius);
					if (fullArea != null)
					{
						points.add(new LysBoardPointRadius(point, (float) radius));
						fullAreas.add(fullArea);
					}
				}
			}
		}
		else
		{
			points.add(new LysBoardPointRadius(point, mInitR));
		}
	}

	private ArrayList<LysBoardPoint> calculateFullArea(LysBoardPoint p1, double r1, LysBoardPoint p2, double r2)
	{
		if (p1.distance(p2) <= Math.abs(r1 - r2))
			return null;

		double om1 = 0;
		double om2 = 0;

		double rx1 = 0;
		double ry1 = 0;
		double rx2 = 0;
		double ry2 = 0;
		double rx3 = 0;
		double ry3 = 0;
		double rx4 = 0;
		double ry4 = 0;

		if (p2.rightTopOf(p1) || p2.leftBottomOf(p1))
		{
			om1 = Math.acos(Math.abs(r2 - r1) / Math.sqrt(Math.pow((p1.x - p2.x), 2) + Math.pow((p1.y - p2.y), 2))) - Math.atan(Math.abs((p2.y - p1.y) / (p2.x - p1.x)));
			om2 = Math.acos(Math.abs(r2 - r1) / Math.sqrt(Math.pow((p1.x - p2.x), 2) + Math.pow((p1.y - p2.y), 2))) + Math.atan(Math.abs((p2.y - p1.y) / (p2.x - p1.x)));
		}
		else
		{
			om1 = Math.acos(Math.abs(r2 - r1) / Math.sqrt(Math.pow((p1.x - p2.x), 2) + Math.pow((p1.y - p2.y), 2))) + Math.atan(Math.abs((p2.y - p1.y) / (p2.x - p1.x)));
			om2 = Math.acos(Math.abs(r2 - r1) / Math.sqrt(Math.pow((p1.x - p2.x), 2) + Math.pow((p1.y - p2.y), 2))) - Math.atan(Math.abs((p2.y - p1.y) / (p2.x - p1.x)));
		}

		if (r1 < r2)
		{
			if (p2.rightTopOf(p1) || p2.rightBottomOf(p1) || p2.bottomOf(p1) || p2.rightOf(p1))
			{
				rx1 = p1.x - r1 * Math.cos(om1);
				ry1 = p1.y - r1 * Math.sin(om1);

				rx2 = p2.x - r2 * Math.cos(om1);
				ry2 = p2.y - r2 * Math.sin(om1);

				rx3 = p2.x - r2 * Math.cos(om2);
				ry3 = p2.y + r2 * Math.sin(om2);

				rx4 = p1.x - r1 * Math.cos(om2);
				ry4 = p1.y + r1 * Math.sin(om2);
			}
			else
			{
				rx1 = p1.x + r1 * Math.cos(om1);
				ry1 = p1.y + r1 * Math.sin(om1);

				rx2 = p2.x + r2 * Math.cos(om1);
				ry2 = p2.y + r2 * Math.sin(om1);

				rx3 = p2.x + r2 * Math.cos(om2);
				ry3 = p2.y - r2 * Math.sin(om2);

				rx4 = p1.x + r1 * Math.cos(om2);
				ry4 = p1.y - r1 * Math.sin(om2);
			}
		}
		else
		{
			if (p2.rightTopOf(p1) || p2.rightBottomOf(p1) || p2.bottomOf(p1) || p2.rightOf(p1))
			{
				rx1 = p1.x + r1 * Math.cos(om1);
				ry1 = p1.y + r1 * Math.sin(om1);

				rx2 = p2.x + r2 * Math.cos(om1);
				ry2 = p2.y + r2 * Math.sin(om1);

				rx3 = p2.x + r2 * Math.cos(om2);
				ry3 = p2.y - r2 * Math.sin(om2);

				rx4 = p1.x + r1 * Math.cos(om2);
				ry4 = p1.y - r1 * Math.sin(om2);
			}
			else
			{
				rx1 = p1.x - r1 * Math.cos(om1);
				ry1 = p1.y - r1 * Math.sin(om1);

				rx2 = p2.x - r2 * Math.cos(om1);
				ry2 = p2.y - r2 * Math.sin(om1);

				rx3 = p2.x - r2 * Math.cos(om2);
				ry3 = p2.y + r2 * Math.sin(om2);

				rx4 = p1.x - r1 * Math.cos(om2);
				ry4 = p1.y + r1 * Math.sin(om2);
			}
		}

		LysBoardPoint point1 = new LysBoardPoint((int) rx1, (int) ry1);
		LysBoardPoint point2 = new LysBoardPoint((int) rx2, (int) ry2);
		LysBoardPoint point3 = new LysBoardPoint((int) rx3, (int) ry3);
		LysBoardPoint point4 = new LysBoardPoint((int) rx4, (int) ry4);

		if (point1.equals(point2) || point1.equals(point3) || point1.equals(point4) || point2.equals(point3) || point2.equals(point4) || point3.equals(point4))
		{
			LOG.v(String.format("calculateFullArea fail p1(%f, %f) p2(%f, %f)", p1.x, p1.y, p2.x, p2.y));
			return null;
		}

		ArrayList<LysBoardPoint> fullArea = new ArrayList<>();
		fullArea.add(point1);
		fullArea.add(point2);
		fullArea.add(point3);
		fullArea.add(point4);
		return fullArea;
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
	private int mIndexArea = 0;

	@Override
	public void draw(Canvas canvas)
	{
		for (; mIndexPoint < points.size(); mIndexPoint++)
		{
			LysBoardPointRadius point = points.get(mIndexPoint);
			LysBoardDrawUtils.drawCircle(canvas, mPaint, point, point.radius);
		}
		for (; mIndexArea < fullAreas.size(); mIndexArea++)
		{
			List<LysBoardPoint> fullArea = fullAreas.get(mIndexArea);
			LysBoardDrawUtils.drawPath(canvas, mPaint, true, fullArea);
		}
	}

	@Override
	public void drawGizmo(Canvas canvas)
	{
		if (points.size() > 0)
		{
			LysBoardPointRadius lastPoint = points.get(points.size() - 1);
			LysBoardDrawUtils.drawCircle(canvas, mPaintGizmo, lastPoint, lastPoint.radius);
		}
	}

	@Override
	public JSONObject saveToJson()
	{
		JSONObject json = new JSONObject(true);
		json.put("drawingType", LysBoardDrawingType.Eraser);
		{
			JSONArray array = new JSONArray();
			for (LysBoardPointRadius point : points)
			{
				array.add(point.saveToJson());
			}
			json.put("points", array);
		}
		{
			JSONArray array = new JSONArray();
			for (ArrayList<LysBoardPoint> fullArea : fullAreas)
			{
				JSONArray arr = new JSONArray();
				for (LysBoardPoint point : fullArea)
				{
					arr.add(point.saveToJson());
				}
				array.add(arr);
			}
			json.put("fullAreas", array);
		}
		return json;
	}

	@Override
	public SDrawing saveToProto()
	{
		SDrawing drawing = new SDrawing();
		drawing.drawingType = LysBoardDrawingType.Eraser;
		SDrawingEraser eraser = new SDrawingEraser();
		for (LysBoardPointRadius point : points)
		{
			eraser.points.add(point.saveToProto2());
		}
		for (ArrayList<LysBoardPoint> fullArea : fullAreas)
		{
			SFullArea area = new SFullArea();
			for (LysBoardPoint point : fullArea)
			{
				area.points.add(point.saveToProto());
			}
			eraser.fullAreas.add(area);
		}
		drawing.eraser = eraser;
		return drawing;
	}
}
