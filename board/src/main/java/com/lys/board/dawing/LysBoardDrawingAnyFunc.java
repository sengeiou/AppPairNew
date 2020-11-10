package com.lys.board.dawing;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.text.TextUtils;

import com.alibaba.fastjson.JSONObject;
import com.lys.board.utils.LysBoardDrawUtils;
import com.lys.board.utils.LysBoardPoint;
import com.lys.protobuf.SDrawing;
import com.lys.protobuf.SDrawingAnyFunc;

public class LysBoardDrawingAnyFunc extends LysBoardDrawing
{
	public interface CalculateDelegate
	{
		float calculateY(String expression, float x);

		float calculateX(String expression, float y);
	}

	private static CalculateDelegate sCalculateDelegate = new CalculateDelegate()
	{
		@Override
		public float calculateY(String expression, float x)
		{
			return x;
		}

		@Override
		public float calculateX(String expression, float y)
		{
			return y;
		}
	};

	public static void setCalculateDelegate(CalculateDelegate calculateDelegate)
	{
		sCalculateDelegate = calculateDelegate;
	}

	private Paint mPaint = null;
	private Paint mPaintCoord = null;
	private Paint mPaintText = null;

	private LysBoardPoint posStart = null;
	private LysBoardPoint posStop = null;
	private String formula = "y=x";
	private String conditionFrom = "-∞";
	private String conditionTo = "+∞";
	private boolean containFrom = true;
	private boolean containTo = true;
	private float scaleX = 30;
	private float scaleY = 30;

	public LysBoardDrawingAnyFunc()
	{
		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setDither(true);
		mPaint.setFilterBitmap(true);
		mPaint.setStrokeCap(Paint.Cap.ROUND);
		mPaint.setStrokeJoin(Paint.Join.ROUND);
		mPaint.setStyle(Paint.Style.STROKE);
		mPaint.setPathEffect(new CornerPathEffect(10));

		mPaintCoord = new Paint();
		mPaintCoord.setAntiAlias(true);
		mPaintCoord.setDither(true);
		mPaintCoord.setFilterBitmap(true);
		mPaintCoord.setStrokeCap(Paint.Cap.ROUND);
		mPaintCoord.setStrokeJoin(Paint.Join.ROUND);
		mPaintCoord.setStyle(Paint.Style.STROKE);
		mPaintCoord.setPathEffect(new CornerPathEffect(10));
		mPaintCoord.setColor(Color.BLACK);
		mPaintCoord.setStrokeWidth(2);

		mPaintText = new Paint();
		mPaintText.setAntiAlias(true);
		mPaintText.setDither(true);
		mPaintText.setFilterBitmap(true);
		mPaintText.setStrokeCap(Paint.Cap.ROUND);
		mPaintText.setStrokeJoin(Paint.Join.ROUND);
		mPaintText.setStyle(Paint.Style.FILL);
		mPaintText.setPathEffect(new CornerPathEffect(1));
		mPaintText.setColor(Color.BLACK);
		mPaintText.setStrokeWidth(1);
	}

	@Override
	public void init(int paintColor, float strokeWidth)
	{
		mPaint.setColor(paintColor);
		mPaint.setStrokeWidth(strokeWidth);
	}

	@Override
	public void setAnyParam(String anyParam)
	{
		String param = anyParam;

//		String param  = "x=sin(y);   v∈[-∞,+∞];   scale=(30,30)";

//		String param = "y=tan(x);   v∈(-1,1);   scale=(80,80)";
//		String param  = "y=sin(x);   v∈[-∞,+∞];   scale=(30,30)";
//		String param  = "y=sin(radians(x));   v∈[-∞,+∞];   scale=(0.5,30)";
//		String param  = "y=sin(pi/2*x);   v∈[-∞,+∞];   scale=(30,30)";
//		String param = "y=50*sin(x/30);   v∈[-∞,+∞];   scale=(1,1)";
//		String param = "y=100*x;   v∈[-∞,+∞];   scale=(1000,10)";

		try
		{
			param = param.replace(" ", "");

			String[] params = param.split(";");

			formula = params[0];

			String condition = params[1];
			condition = condition.substring(2);
			containFrom = condition.startsWith("[");
			containTo = condition.endsWith("]");
			condition = condition.substring(1, condition.length() - 1);
			String[] conditions = condition.split(",");
			conditionFrom = conditions[0];
			conditionTo = conditions[1];

			String scaleStr = params[2];
			scaleStr = scaleStr.substring(scaleStr.indexOf('(') + 1, scaleStr.indexOf(')'));
			String[] scaleStrs = scaleStr.split(",");
			scaleX = Float.valueOf(scaleStrs[0]);
			scaleY = Float.valueOf(scaleStrs[1]);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void init(JSONObject json)
	{
		mPaint.setColor(json.getInteger("paintColor"));
		mPaint.setStrokeWidth(json.getFloat("strokeWidth"));
		posStart = new LysBoardPoint(json.getJSONObject("posStart"));
		posStop = new LysBoardPoint(json.getJSONObject("posStop"));
		formula = json.getString("formula");
		conditionFrom = json.getString("conditionFrom");
		conditionTo = json.getString("conditionTo");
		containFrom = json.getBoolean("containFrom");
		containTo = json.getBoolean("containTo");
		scaleX = json.getFloat("scaleX");
		scaleY = json.getFloat("scaleY");
	}

	@Override
	public void init(SDrawing proto)
	{
		SDrawingAnyFunc anyFunc = proto.anyFunc;
		mPaint.setColor(anyFunc.paintColor);
		mPaint.setStrokeWidth(anyFunc.strokeWidth);
		posStart = new LysBoardPoint(anyFunc.posStart);
		posStop = new LysBoardPoint(anyFunc.posStop);
		formula = anyFunc.formula;
		conditionFrom = anyFunc.conditionFrom;
		conditionTo = anyFunc.conditionTo;
		containFrom = anyFunc.containFrom;
		containTo = anyFunc.containTo;
		scaleX = anyFunc.scaleX;
		scaleY = anyFunc.scaleY;
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

	public static void drawFormulaX(Canvas canvas, Paint paint, LysBoardDrawUtils.LysFormulaX formula, float from, boolean containFrom, float to, boolean containTo)
	{
		if (from == to)
		{
			float va = formula.calculateX(from);
			canvas.drawPoint(va, from, paint);
		}
		else
		{
			if (from > to)
			{
				float tmp = from;
				from = to;
				to = tmp;
			}

			float step = paint.getStrokeWidth();
			int count = (int) ((to - from) / step) + 1;
			if (count < 4)
				count = 4;
			step = ((to - from) / (count - 1));

			int begin = containTo ? 0 : 1;
			int end = containFrom ? count : count - 1;

//			Path path = new Path();
//			for (int i = begin; i < end; i++)
//			{
//				float ka = from + i * step;
//				if (ka > to)
//					ka = to;
//				float va = formula.calculateX(ka);
//				if (i == 0)
//					path.moveTo(va, ka);
//				else
//					path.lineTo(va, ka);
//			}
//			canvas.drawPath(path, paint);

			for (int i = begin; i < end; i++)
			{
				float ka = from + i * step;
				if (ka > to)
					ka = to;
				float va = formula.calculateX(ka);
				canvas.drawPoint(va, ka, paint);
			}
		}
	}

	public static void drawFormulaY(Canvas canvas, Paint paint, LysBoardDrawUtils.LysFormulaY formula, float from, boolean containFrom, float to, boolean containTo)
	{
		if (from == to)
		{
			float va = formula.calculateY(from);
			canvas.drawPoint(from, va, paint);
		}
		else
		{
			if (from > to)
			{
				float tmp = from;
				from = to;
				to = tmp;
			}

			float step = paint.getStrokeWidth();
			int count = (int) ((to - from) / step) + 1;
			if (count < 4)
				count = 4;
			step = ((to - from) / (count - 1));

			int begin = containFrom ? 0 : 1;
			int end = containTo ? count : count - 1;

//			Path path = new Path();
//			for (int i = begin; i < end; i++)
//			{
//				float ka = from + i * step;
//				if (ka > to)
//					ka = to;
//				float va = formula.calculateY(ka);
//				if (i == 0)
//					path.moveTo(ka, va);
//				else
//					path.lineTo(ka, va);
//			}
//			canvas.drawPath(path, paint);

			for (int i = begin; i < end; i++)
			{
				float ka = from + i * step;
				if (ka > to)
					ka = to;
				float va = formula.calculateY(ka);
				canvas.drawPoint(ka, va, paint);
			}
		}
	}

	public static String strFloat(float value)
	{
		String str = String.format("%f", value);
		String ret = str.replaceAll("0+$", "");
		if (ret.endsWith("."))
			ret = ret.substring(0, ret.length() - 1);
		return ret;
	}

	@Override
	public void draw(Canvas canvas)
	{
		if (isValid())
		{
			try
			{
				// 绘制坐标
				LysBoardPoint p1 = posStart;
				LysBoardPoint p2 = posStop;

				float len = mPaintCoord.getStrokeWidth() * 8; // 箭头长度

				float x1 = p1.x - (Math.abs(p2.x - p1.x) + len * 2);
				float x2 = p1.x + (Math.abs(p2.x - p1.x) + len * 2);
				LysBoardPoint pxFrom = new LysBoardPoint(x1 + len, p1.y);
				LysBoardPoint pxTo = new LysBoardPoint(x2, p1.y);
				float y1 = p1.y - (Math.abs(p2.y - p1.y) + len * 2);
				float y2 = p1.y + (Math.abs(p2.y - p1.y) + len * 2);
				LysBoardPoint pyFrom = new LysBoardPoint(p1.x, y2 - len);
				LysBoardPoint pyTo = new LysBoardPoint(p1.x, y1);

				mPaintText.setTextSize(20);
				mPaintText.setTextAlign(Paint.Align.CENTER);

				LysBoardDrawUtils.drawArrowsLine(canvas, mPaintCoord, pxFrom, pxTo);
				float bei = 1;
				while (scaleX * bei < 10)
					bei *= 10;
				while (scaleX * bei >= 100)
					bei *= 0.1;
				float scale = scaleX * bei;
				int midu;
				if (scale > 50)
					midu = 2;
				else if (scale > 20)
					midu = 5;
				else
					midu = 10;
				for (int i = 1; ; i++)
				{
					float x = (x1 + x2) / 2 + i * scale;
					if (x > x2 - len - 10)
						break;
					float rule = (i % 5 == 0 ? 4 : 2);
					LysBoardDrawUtils.drawLine(canvas, mPaintCoord, new LysBoardPoint(x, p1.y), new LysBoardPoint(x, p1.y - mPaintCoord.getStrokeWidth() * rule));
					if (i % midu == 0)
						canvas.drawText(strFloat(i * bei), x, p1.y + 21, mPaintText);
					x = p1.x * 2 - x;
					LysBoardDrawUtils.drawLine(canvas, mPaintCoord, new LysBoardPoint(x, p1.y), new LysBoardPoint(x, p1.y - mPaintCoord.getStrokeWidth() * rule));
					if (i % midu == 0)
						canvas.drawText(strFloat(i * bei), x, p1.y + 21, mPaintText);
				}

				mPaintText.setTextAlign(Paint.Align.RIGHT);

				LysBoardDrawUtils.drawArrowsLine(canvas, mPaintCoord, pyFrom, pyTo);
				bei = 1;
				while (scaleY * bei < 10)
					bei *= 10;
				while (scaleY * bei >= 100)
					bei *= 0.1;
				scale = scaleY * bei;
				if (scale > 50)
					midu = 2;
				else if (scale > 20)
					midu = 5;
				else
					midu = 10;
				for (int i = 1; ; i++)
				{
					float y = (y1 + y2) / 2 + i * scale;
					if (y > y2 - len - 10)
						break;
					float rule = (i % 5 == 0 ? 4 : 2);
					LysBoardDrawUtils.drawLine(canvas, mPaintCoord, new LysBoardPoint(p1.x, y), new LysBoardPoint(p1.x + mPaintCoord.getStrokeWidth() * rule, y));
					if (i % midu == 0)
						canvas.drawText(strFloat(i * bei), p1.x - 6, y + 6, mPaintText);
					y = p1.y * 2 - y;
					LysBoardDrawUtils.drawLine(canvas, mPaintCoord, new LysBoardPoint(p1.x, y), new LysBoardPoint(p1.x + mPaintCoord.getStrokeWidth() * rule, y));
					if (i % midu == 0)
						canvas.drawText(strFloat(i * bei), p1.x - 6, y + 6, mPaintText);
				}

				mPaintText.setTextSize(30);
				mPaintText.setTextAlign(Paint.Align.CENTER);
				canvas.drawText("x", pxTo.x + 5, pxTo.y + 25, mPaintText);
				canvas.drawText("y", pyTo.x - 15, pyTo.y + 3, mPaintText);

				// 绘制函数图像
				if (formula.startsWith("y="))
				{
					final String expression = formula.substring(2);
					float xFrom = 2 * posStart.x - posStop.x;
					float xTo = posStop.x;
					if (xFrom > xTo)
					{
						float tmp = xFrom;
						xFrom = xTo;
						xTo = tmp;
					}
					if (!conditionFrom.equals("-∞"))
						xFrom = Math.max(xFrom, Float.valueOf(conditionFrom) * scaleX + posStart.x);
					if (!conditionTo.equals("+∞"))
						xTo = Math.min(xTo, Float.valueOf(conditionTo) * scaleX + posStart.x);
					if (xFrom < xTo)
					{
						drawFormulaY(canvas, mPaint, new LysBoardDrawUtils.LysFormulaY()
						{
							@Override
							public float calculateY(float x)
							{
								try
								{
									return posStart.y - sCalculateDelegate.calculateY(expression, (x - posStart.x) / scaleX) * scaleY;
								}
								catch (Exception e)
								{
									e.printStackTrace();
									return 0;
								}
							}
						}, xFrom, containFrom, xTo, containTo);
					}
				}
				else
				{
					final String expression = formula.substring(2);
					float yFrom = 2 * posStart.y - posStop.y;
					float yTo = posStop.y;
					if (yFrom > yTo)
					{
						float tmp = yFrom;
						yFrom = yTo;
						yTo = tmp;
					}
					if (!conditionFrom.equals("-∞"))
						yFrom = Math.max(yFrom, posStart.y - Float.valueOf(conditionTo) * scaleY);
					if (!conditionTo.equals("+∞"))
						yTo = Math.min(yTo, posStart.y - Float.valueOf(conditionFrom) * scaleY);
					if (yFrom < yTo)
					{
						drawFormulaX(canvas, mPaint, new LysBoardDrawUtils.LysFormulaX()
						{
							@Override
							public float calculateX(float y)
							{
								try
								{
									return sCalculateDelegate.calculateX(expression, (posStart.y - y) / scaleY) * scaleX + posStart.x;
								}
								catch (Exception e)
								{
									e.printStackTrace();
									return 0;
								}
							}
						}, yFrom, containFrom, yTo, containTo);
					}
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	@Override
	public void drawGizmo(Canvas canvas)
	{

	}

	private boolean isValid()
	{
		return !TextUtils.isEmpty(formula) && //
				posStart != null && posStop != null && //
				!posStart.x.equals(posStop.x) && //
				!posStart.y.equals(posStop.y) && //
				posStart.distance(posStop) > 0;
	}

	@Override
	public JSONObject saveToJson()
	{
		JSONObject json = new JSONObject(true);
		json.put("drawingType", LysBoardDrawingType.AnyFunc);
		json.put("paintColor", mPaint.getColor());
		json.put("strokeWidth", mPaint.getStrokeWidth());
		json.put("posStart", posStart.saveToJson());
		json.put("posStop", posStop.saveToJson());
		json.put("formula", formula);
		json.put("conditionFrom", conditionFrom);
		json.put("conditionTo", conditionTo);
		json.put("containFrom", containFrom);
		json.put("containTo", containTo);
		json.put("scaleX", scaleX);
		json.put("scaleY", scaleY);
		return json;
	}

	@Override
	public SDrawing saveToProto()
	{
		SDrawing drawing = new SDrawing();
		drawing.drawingType = LysBoardDrawingType.AnyFunc;
		SDrawingAnyFunc anyFunc = new SDrawingAnyFunc();
		anyFunc.paintColor = mPaint.getColor();
		anyFunc.strokeWidth = mPaint.getStrokeWidth();
		anyFunc.posStart = posStart.saveToProto();
		anyFunc.posStop = posStop.saveToProto();
		anyFunc.formula = formula;
		anyFunc.conditionFrom = conditionFrom;
		anyFunc.conditionTo = conditionTo;
		anyFunc.containFrom = containFrom;
		anyFunc.containTo = containTo;
		anyFunc.scaleX = scaleX;
		anyFunc.scaleY = scaleY;
		drawing.anyFunc = anyFunc;
		return drawing;
	}
}
