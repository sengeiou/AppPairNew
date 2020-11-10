package com.lys.board.dawing;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lys.base.utils.LOG;
import com.lys.board.utils.LysBoardDrawUtils;
import com.lys.board.utils.LysBoardPoint;
import com.lys.protobuf.SDrawing;
import com.lys.protobuf.SDrawingBrush;

import java.util.ArrayList;
import java.util.List;

public class LysBoardDrawingBrush extends LysBoardDrawing
{
	public static float mSensitivity = 2 * 0.5f;

	private Paint mPaint = null;
	private float mStrokeWidth;

	private Paint mPaintDebug = null;

	private ArrayList<LysBoardPoint> points = new ArrayList<>();

	public LysBoardDrawingBrush()
	{
		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setDither(true);
		mPaint.setFilterBitmap(true);
		mPaint.setStrokeCap(Paint.Cap.ROUND);
		mPaint.setStrokeJoin(Paint.Join.ROUND);
//		mPaint.setStyle(Paint.Style.STROKE);
		mPaint.setStyle(Paint.Style.FILL_AND_STROKE);

		mPaintDebug = new Paint();
		mPaintDebug.setAntiAlias(true);
		mPaintDebug.setDither(true);
		mPaintDebug.setStyle(Paint.Style.FILL);
		mPaintDebug.setStrokeWidth(4);
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
		SDrawingBrush brush = proto.brush;
		mPaint.setColor(brush.paintColor);
		mStrokeWidth = brush.strokeWidth;
		for (int i = 0; i < brush.points.size(); i++)
		{
			points.add(new LysBoardPoint(brush.points.get(i)));
		}
	}

	public class PointSYM
	{
		public LysBoardPoint pv;
		public LysBoardPoint pm;
		public LysBoardPoint pn;
	}

	public boolean isCross(LysBoardPoint a, LysBoardPoint b, LysBoardPoint c, LysBoardPoint d)
	{
		if (!(Math.min(a.x, b.x) <= Math.max(c.x, d.x) && Math.min(c.y, d.y) <= Math.max(a.y, b.y) && Math.min(c.x, d.x) <= Math.max(a.x, b.x) && Math.min(a.y, b.y) <= Math.max(c.y, d.y)))
			return false;
		double u, v, w, z;
		u = (c.x - a.x) * (b.y - a.y) - (b.x - a.x) * (c.y - a.y);
		v = (d.x - a.x) * (b.y - a.y) - (b.x - a.x) * (d.y - a.y);
		w = (a.x - c.x) * (d.y - c.y) - (d.x - c.x) * (a.y - c.y);
		z = (b.x - c.x) * (d.y - c.y) - (d.x - c.x) * (b.y - c.y);
		return (u * v <= 0.00000001 && w * z <= 0.00000001);
	}

	private void add(LysBoardPoint point)
	{
		if (points.size() > 0)
		{
			double radius = mStrokeWidth * point.pressure * mSensitivity;

			LysBoardPoint point1 = points.get(points.size() - 1);
			double radius1 = mStrokeWidth * point1.pressure * mSensitivity;
			if (point1.distance(point) > radius1 + radius + 2) // 加2
//			if (!lastPoint.equals(point))
			{
				if (points.size() > 1)
				{
					LysBoardPoint point2 = points.get(points.size() - 2);
					double radius2 = mStrokeWidth * point2.pressure * mSensitivity;
					int dir = 0;
					while (true)
					{
						float distance = point2.distance(point);
						if (distance > radius2 + radius + 2) // 加2的原因是：如果两个点离的太近，isCross检测可能会有问题，从而导致交叉效果。
						{
							points.add(point);
							break;
						}
						else
						{
							if (dir == 0)
							{
								LysBoardPoint pointTmp = point.rotate(2, point1);
								float distanceTmp = point2.distance(pointTmp);
								if (distanceTmp > distance)
									dir = 1;
								else
									dir = -1;
							}
							point = point.rotate(4 * dir, point1);
						}
					}
				}
				else
				{
					points.add(point);
				}
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

	private void drawPoint(Canvas canvas, Paint paint, float strokeWidth, float sensitivity, LysBoardPoint point)
	{
		float radius = strokeWidth * point.pressure * sensitivity;
		canvas.drawCircle(point.x, point.y, radius, paint);
		mPaintDebug.setColor(Color.BLACK);
		canvas.drawPoint(point.x, point.y, mPaintDebug);
	}

	private void drawDebugPoint(Canvas canvas, LysBoardPoint point, int color)
	{
		mPaintDebug.setColor(color);
		canvas.drawPoint(point.x, point.y, mPaintDebug);
	}

	private void drawBrush(Canvas canvas, Paint paint, float strokeWidth, float sensitivity, List<LysBoardPoint> points)
	{
		if (points == null || points.size() < 3)
			return;
		List<PointSYM> syms = new ArrayList<>();
		for (int i = 0; i < points.size() - 2; i++)
		{
			LysBoardPoint pa = points.get(i);
			LysBoardPoint pb = points.get(i + 1);
			LysBoardPoint pc = points.get(i + 2);
			float ra = strokeWidth * pa.pressure * sensitivity;
			float rb = strokeWidth * pb.pressure * sensitivity;
			float rc = strokeWidth * pc.pressure * sensitivity;

			float distance1 = pa.distance(pb);
			float distance2 = pc.distance(pb);
			if (distance1 == 0 || distance2 == 0)
				LOG.v("error : distance1 or distance2 is zero");

			// 处理第一个点
			if (i == 0)
			{
				PointSYM sym = new PointSYM();
				sym.pv = pb.scale(-ra / distance1, pa);
				sym.pm = sym.pv.rotate(90, pa);
				sym.pn = sym.pv.rotate(-90, pa);
				syms.add(sym);
			}

			LysBoardPoint pm;
			if (distance1 < distance2)
			{
				pm = pa.scale(distance2 / distance1, pb).scale(0.5f, pc);
			}
			else
			{
				pm = pc.scale(distance1 / distance2, pb).scale(0.5f, pa);
			}

			float distance = pm.distance(pb);
			if (distance == 0) // 三个点在一条直线上
			{
				PointSYM sym = new PointSYM();
				LysBoardPoint pv = pa.scale(rb / distance1, pb);
				sym.pm = pv.rotate(90, pb);
				sym.pn = pv.rotate(-90, pb);
				syms.add(sym);
			}
			else
			{
				PointSYM sym = new PointSYM();
				sym.pm = pm.scale(rb / distance, pb);
				sym.pn = pm.scale(-rb / distance, pb);
				syms.add(sym);
			}

			// 处理最后一个点
			if (i == points.size() - 3)
			{
				PointSYM sym = new PointSYM();
				sym.pv = pb.scale(-rc / distance2, pc);
				sym.pm = sym.pv.rotate(90, pc);
				sym.pn = sym.pv.rotate(-90, pc);
				syms.add(sym);
			}
		}

		// 交叉检测
		for (int i = 1; i < syms.size(); i++)
		{
			PointSYM last = syms.get(i - 1);
			PointSYM sym = syms.get(i);
			if (isCross(last.pm, sym.pm, last.pn, sym.pn))
			{
				LysBoardPoint tmp = sym.pm;
				sym.pm = sym.pn;
				sym.pn = tmp;
			}
		}

		// 整理path点
		List<LysBoardPoint> ps = new ArrayList<>();
		ps.add(syms.get(0).pv);
		for (int i = 1; i < syms.size() - 1; i++)
		{
			PointSYM sym = syms.get(i);
			ps.add(sym.pm);
		}
		ps.add(syms.get(syms.size() - 1).pv);
		for (int i = syms.size() - 2; i >= 1; i--)
		{
			PointSYM sym = syms.get(i);
			ps.add(sym.pn);
		}

//		for (LysBoardPoint p : ps)
//		{
//			drawDebugPoint(canvas, p, Color.RED);
//		}

		LysBoardDrawUtils.drawPath(canvas, mPaint, true, ps);
	}

	@Override
	public void draw(Canvas canvas)
	{
		mPaint.setPathEffect(new CornerPathEffect(mStrokeWidth * mSensitivity));
//		drawPoints(canvas, mPaint, mStrokeWidth, mSensitivity, points);
		drawBrush(canvas, mPaint, mStrokeWidth, mSensitivity, points);
	}

	@Override
	public void drawGizmo(Canvas canvas)
	{

	}

	@Override
	public JSONObject saveToJson()
	{
		JSONObject json = new JSONObject(true);
		json.put("drawingType", LysBoardDrawingType.Brush);
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
		drawing.drawingType = LysBoardDrawingType.Brush;
		SDrawingBrush brush = new SDrawingBrush();
		brush.paintColor = mPaint.getColor();
		brush.strokeWidth = mStrokeWidth;
		for (LysBoardPoint point : points)
		{
			brush.points.add(point.saveToProto());
		}
		drawing.brush = brush;
		return drawing;
	}
}
