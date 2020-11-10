package com.lys.board.utils;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;

import java.util.Arrays;
import java.util.List;

public class LysBoardDrawUtils
{
	public static void drawPoint(Canvas canvas, Paint paint, LysBoardPoint p)
	{
		canvas.drawPoint(p.x, p.y, paint);
	}

	public static void drawPoints(Canvas canvas, Paint paint, List<LysBoardPoint> ps)
	{
		if (ps == null)
			return;
		for (int i = 0; i < ps.size(); i++)
		{
			LysBoardPoint p = ps.get(i);
			drawPoint(canvas, paint, p);
		}
	}

	public static void drawPoint(Canvas canvas, Paint paint, float strokeWidth, float sensitivity, LysBoardPoint p)
	{
		paint.setStrokeWidth(strokeWidth * p.pressure * sensitivity);
		canvas.drawPoint(p.x, p.y, paint);
	}

	public static void drawPoints(Canvas canvas, Paint paint, float strokeWidth, float sensitivity, List<LysBoardPoint> ps)
	{
		if (ps == null)
			return;
		for (int i = 0; i < ps.size(); i++)
		{
			LysBoardPoint p = ps.get(i);
			drawPoint(canvas, paint, strokeWidth, sensitivity, p);
		}
	}

	private static LysBoardPoint drawSmoothPoint(Canvas canvas, Paint paint, float strokeWidth, float sensitivity, LysBoardPoint pa, LysBoardPoint pb, LysBoardPoint pc, LysBoardPoint pd, double t)
	{
		double a1 = Math.pow((1 - t), 3) / 6;
		double a2 = (3 * Math.pow(t, 3) - 6 * Math.pow(t, 2) + 4) / 6;
		double a3 = (-3 * Math.pow(t, 3) + 3 * Math.pow(t, 2) + 3 * t + 1) / 6;
		double a4 = Math.pow(t, 3) / 6;

		float x = (float) (a1 * pa.x + a2 * pb.x + a3 * pc.x + a4 * pd.x);
		float y = (float) (a1 * pa.y + a2 * pb.y + a3 * pc.y + a4 * pd.y);
		float pressure = (float) (pb.pressure + (pc.pressure - pb.pressure) * t);

		LysBoardPoint p = new LysBoardPoint(x, y, pressure);
		drawPoint(canvas, paint, strokeWidth, sensitivity, p);
		return p;
	}

	private static void drawSmoothMiddle(Canvas canvas, Paint paint, float strokeWidth, float sensitivity, LysBoardPoint pa, LysBoardPoint pb, LysBoardPoint pc, LysBoardPoint pd, LysBoardPoint ps, LysBoardPoint pe, double s, double e)
	{
		double m = (s + e) * 0.5;
		double pressure = pb.pressure + (pc.pressure - pb.pressure) * m;
		double density = strokeWidth * pressure * sensitivity * 0.5;
		if (ps.distance(pe) > density)
		{
			LysBoardPoint pm = drawSmoothPoint(canvas, paint, strokeWidth, sensitivity, pa, pb, pc, pd, m);
			drawSmoothMiddle(canvas, paint, strokeWidth, sensitivity, pa, pb, pc, pd, ps, pm, s, m);
			drawSmoothMiddle(canvas, paint, strokeWidth, sensitivity, pa, pb, pc, pd, pm, pe, m, e);
		}
	}

	public static void drawSmoothPoints(Canvas canvas, Paint paint, float strokeWidth, float sensitivity, List<LysBoardPoint> points, int index)
	{
		LysBoardPoint pa = points.get(index);
		LysBoardPoint pb = points.get(index + 1);
		LysBoardPoint pc = points.get(index + 2);
		LysBoardPoint pd = points.get(index + 3);
		double distance = pb.distance(pc);
		if (distance > 0)
		{
			double s = 0;
			double e = 1;
			LysBoardPoint ps = drawSmoothPoint(canvas, paint, strokeWidth, sensitivity, pa, pb, pc, pd, s);
			LysBoardPoint pe = drawSmoothPoint(canvas, paint, strokeWidth, sensitivity, pa, pb, pc, pd, e);
			drawSmoothMiddle(canvas, paint, strokeWidth, sensitivity, pa, pb, pc, pd, ps, pe, s, e);
		}
	}

	public static void drawLine(Canvas canvas, Paint paint, LysBoardPoint p1, LysBoardPoint p2)
	{
		Path path = new Path();
		path.moveTo(p1.x, p1.y);
		path.lineTo(p2.x, p2.y);
		canvas.drawPath(path, paint);
	}

	// heigth和bottom分别为三角形的高与底的一半,调节三角形大小
	private static Path getArrows(float fromX, float fromY, float toX, float toY, float heigth, float bottom)
	{
		float juli = (float) Math.sqrt((toX - fromX) * (toX - fromX) + (toY - fromY) * (toY - fromY)); // 获取线段距离
		float juliX = toX - fromX; // 有正负，不要取绝对值
		float juliY = toY - fromY; // 有正负，不要取绝对值
		float dianX = toX - (heigth / juli * juliX);
		float dianY = toY - (heigth / juli * juliY);
		float dian2X = fromX + (heigth / juli * juliX);
		float dian2Y = fromY + (heigth / juli * juliY);
		Path path = new Path();
		path.moveTo(toX, toY); // 此点为三边形的起点
		path.lineTo(dianX + (bottom / juli * juliY), dianY - (bottom / juli * juliX));
		path.lineTo(dianX - (bottom / juli * juliY), dianY + (bottom / juli * juliX));
		path.close(); // 使这些点构成封闭的三边形
		return path;
	}

	private static Point getStopPos(float fromX, float fromY, float toX, float toY, float distance)
	{
		Point ret = new Point();
		float a = toY - fromY;
		float b = toX - fromX;
		float alpha = (float) Math.atan(a / b);
		if (toX >= fromX)
		{
			ret.y = (int) (toY - Math.sin(alpha) * distance);
			ret.x = (int) (toX - Math.cos(alpha) * distance);
		}
		else
		{
			ret.y = (int) (toY + Math.sin(alpha) * distance);
			ret.x = (int) (toX + Math.cos(alpha) * distance);
		}
		return ret;
	}

	public static void drawArrowsLine(Canvas canvas, Paint paint, LysBoardPoint p1, LysBoardPoint p2)
	{
		float strokeWidth = paint.getStrokeWidth();
		float bottom = strokeWidth * 2;
		float heigth = bottom * 4;

		Paint.Style style = paint.getStyle();
		paint.setStyle(Paint.Style.FILL);
		canvas.drawPath(getArrows(p1.x, p1.y, p2.x, p2.y, heigth, bottom), paint);

		paint.setStyle(style);
		Point stopPos = getStopPos(p1.x, p1.y, p2.x, p2.y, heigth / 2);
		canvas.drawLine(p1.x, p1.y, stopPos.x, stopPos.y, paint);
	}

	public static void drawCircle(Canvas canvas, Paint paint, LysBoardPoint p, float r)
	{
		canvas.drawCircle(p.x, p.y, r, paint);
	}

	public static void drawOval(Canvas canvas, Paint paint, LysBoardPoint p, float rx, float ry)
	{
		canvas.drawOval(p.x - rx, p.y - ry, p.x + rx, p.y + ry, paint);
	}

	public static void drawArc(Canvas canvas, Paint paint, LysBoardPoint p, float rx, float ry, float startAngle, float sweepAngle)
	{
		canvas.drawArc(new RectF(p.x - rx, p.y - ry, p.x + rx, p.y + ry), startAngle, sweepAngle, false, paint);
	}

	public static void drawPath(Canvas canvas, Paint paint, boolean closePath, LysBoardPoint... ps)
	{
		if (ps != null && ps.length > 0)
			drawPath(canvas, paint, closePath, Arrays.asList(ps));
	}

	public static void drawPath(Canvas canvas, Paint paint, boolean closePath, List<LysBoardPoint> ps)
	{
		if (ps == null || ps.size() < 3)
			return;
		Path path = new Path();
		for (int i = 0; i < ps.size(); i++)
		{
			LysBoardPoint p = ps.get(i);
			if (i == 0)
				path.moveTo(p.x, p.y);
			else
				path.lineTo(p.x, p.y);
		}
		if (closePath)
			path.close();
		canvas.drawPath(path, paint);
	}

	public static void drawBezier2(Canvas canvas, Paint paint, boolean closePath, LysBoardPoint... ps)
	{
		if (ps != null && ps.length > 0)
			drawBezier2(canvas, paint, closePath, Arrays.asList(ps));
	}

	public static void drawBezier2(Canvas canvas, Paint paint, boolean closePath, List<LysBoardPoint> ps)
	{
		if (ps == null || ps.size() < 2)
			return;
		Path path = new Path();
		path.moveTo(ps.get(0).x, ps.get(0).y);
		for (int i = 1; i < ps.size() - 1; i += 2)
		{
			LysBoardPoint a = ps.get(i + 0);
			LysBoardPoint b = ps.get(i + 1);
			path.quadTo(a.x, a.y, b.x, b.y);
		}
		if (closePath)
			path.close();
		canvas.drawPath(path, paint);
	}

	public static void drawBezier3(Canvas canvas, Paint paint, boolean closePath, LysBoardPoint... ps)
	{
		if (ps != null && ps.length > 0)
			drawBezier3(canvas, paint, closePath, Arrays.asList(ps));
	}

	public static void drawBezier3(Canvas canvas, Paint paint, boolean closePath, List<LysBoardPoint> ps)
	{
		if (ps == null || ps.size() < 3)
			return;
		Path path = new Path();
		path.moveTo(ps.get(0).x, ps.get(0).y);
		for (int i = 0; i < ps.size() - 2; i += 3)
		{
			LysBoardPoint a = ps.get(i + 0);
			LysBoardPoint b = ps.get(i + 1);
			LysBoardPoint c = ps.get(i + 2);
			path.cubicTo(a.x, a.y, b.x, b.y, c.x, c.y);
		}
		if (closePath)
			path.close();
		canvas.drawPath(path, paint);
	}

	public interface LysFormulaX
	{
		float calculateX(float y);
	}

	public interface LysFormulaY
	{
		float calculateY(float x);
	}

	public static void drawFormulaX(Canvas canvas, Paint paint, LysFormulaX formula, float from, float to)
	{
		if (from == to)
			return;
		float step = paint.getStrokeWidth();
		if (from > to)
		{
			float tmp = from;
			from = to;
			to = tmp;
		}

		int count = (int) ((to - from) / step) + 1;
		count = count - count % 3;
		if (count < 90)
			count = 90;
		step = ((to - from) / (count - 1));

		Path path = new Path();
		for (int i = 0; i < count; i += 3)
		{
			float ka = from + (i + 0) * step;
			float kb = from + (i + 1) * step;
			float kc = from + (i + 2) * step;
			if (ka > to)
				ka = to;
			if (kb > to)
				kb = to;
			if (kc > to)
				kc = to;
			float va = formula.calculateX(ka);
			float vb = formula.calculateX(kb);
			float vc = formula.calculateX(kc);
			if (i == 0)
			{
				path.moveTo(va, ka);
			}
			path.cubicTo(va, ka, vb, kb, vc, kc);
		}
		canvas.drawPath(path, paint);
	}

	public static void drawFormulaY(Canvas canvas, Paint paint, LysFormulaY formula, float from, float to)
	{
		if (from == to)
			return;
		float step = paint.getStrokeWidth();
		if (from > to)
		{
			float tmp = from;
			from = to;
			to = tmp;
		}

		int count = (int) ((to - from) / step) + 1;
		count = count - count % 3;
		if (count < 90)
			count = 90;
		step = ((to - from) / (count - 1));

		Path path = new Path();
		for (int i = 0; i < count; i += 3)
		{
			float ka = from + (i + 0) * step;
			float kb = from + (i + 1) * step;
			float kc = from + (i + 2) * step;
			if (ka > to)
				ka = to;
			if (kb > to)
				kb = to;
			if (kc > to)
				kc = to;
			float va = formula.calculateY(ka);
			float vb = formula.calculateY(kb);
			float vc = formula.calculateY(kc);
			if (i == 0)
			{
				path.moveTo(ka, va);
			}
			path.cubicTo(ka, va, kb, vb, kc, vc);
		}
		canvas.drawPath(path, paint);
	}

}
