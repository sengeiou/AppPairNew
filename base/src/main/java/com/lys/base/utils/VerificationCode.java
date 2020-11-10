package com.lys.base.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.Random;

public class VerificationCode
{
	//随机数数组
	private static final char[] CHARS = {'2', '3', '4', '5', '6', '7', '8', '9', //
			'a', 'b', 'c', 'd', 'e', 'f', 'g', //
			'h', 'k', 'm', 'n', //
			'p', 'q', 'r', 's', 't', //
			'u', 'v', 'w', 'x', 'y', 'z', //
			'A', 'B', 'C', 'D', 'E', 'F', 'G', //
			'H', 'J', 'K', 'L', 'M', 'N', //
			'P', 'Q', 'R', 'S', 'T', //
			'U', 'V', 'W', 'X', 'Y', 'Z'};
	private static final Random random = new Random();
	private static String code;

	//验证码图片
	public static Bitmap createBitmap(int with, int height, int chCount, int lineCount, int fontSize, boolean debug)
	{
		Bitmap bitmap = Bitmap.createBitmap(with, height, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		code = createCode(chCount);
		canvas.drawColor(Color.GRAY);//设置画布的颜色
		Paint paint = new Paint();
		paint.setAntiAlias(true);//去除锯齿
		paint.setTextSize(fontSize);
//		paint.setTextAlign(Paint.Align.CENTER);
		paint.setStrokeWidth(1);
		int chWidth = with / chCount;//平均每个字符所占的宽度
		//画线条
		for (int i = 0; i < lineCount; i++)
		{
			randomColor(paint);
			int startX = random.nextInt(with);
			int startY = random.nextInt(height);
			int stopX = random.nextInt(with);
			int stopY = random.nextInt(height);
			canvas.drawLine(startX, startY, stopX, stopY, paint);
		}
		//画验证码
		for (int i = 0; i < code.length(); i++)
		{
			paint.setFakeBoldText(random.nextBoolean()); //true为粗体，false为非粗体
			if (debug)
			{
				float skewX = random.nextBoolean() ? 0 : 0.6f;
				skewX = random.nextBoolean() ? skewX : -skewX;
				paint.setTextSkewX(skewX);//float类型参数，负数表示右斜，正数左斜
			}
			else
			{
				float skewX = random.nextInt(7) / 10.0f;
				skewX = random.nextBoolean() ? skewX : -skewX;
				paint.setTextSkewX(skewX);//float类型参数，负数表示右斜，正数左斜
			}

			String str = code.charAt(i) + "";

			Rect rect = new Rect();
			paint.getTextBounds(str, 0, 1, rect);
			int strWidth = rect.width();
			int strHeight = rect.height();

			int x = i * chWidth + random.nextInt(Math.max(chWidth - strWidth, 1));
			int y = strHeight + random.nextInt(Math.max(height - strHeight, 1));
			if (debug)
			{
				paint.setColor(Color.YELLOW);
				canvas.drawRect(x, y - strHeight, x + strWidth, y, paint);
				canvas.drawLine(i * chWidth, 0, i * chWidth, height, paint);
			}
			randomColor(paint);
			canvas.drawText(str, x, y, paint);
		}
		canvas.save(Canvas.ALL_SAVE_FLAG);//保存
		canvas.restore();
		return bitmap;
	}

	public static String getCode()
	{
		return code;
	}

	//生成验证码
	private static String createCode(int codeLength)
	{
		StringBuilder buffer = new StringBuilder();
		for (int i = 0; i < codeLength; i++)
		{
			buffer.append(CHARS[random.nextInt(CHARS.length)]);
		}
		return buffer.toString();
	}

	private static void randomColor(Paint paint)
	{
		int red = random.nextInt(256);
		int green = random.nextInt(256);
		int blue = random.nextInt(256);
		paint.setColor(Color.rgb(red, green, blue));
	}

}
