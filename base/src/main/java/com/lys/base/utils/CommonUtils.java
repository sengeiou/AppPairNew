package com.lys.base.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.ContentFrameLayout;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;
import java.util.zip.CRC32;

public class CommonUtils
{
	public static boolean strEqual(String str1, String str2)
	{
		boolean isEmpty1 = TextUtils.isEmpty(str1);
		boolean isEmpty2 = TextUtils.isEmpty(str2);
		if (isEmpty1 && isEmpty2)
			return true;
		if (isEmpty1 != isEmpty2)
			return false;
		return str1.equals(str2);
	}

	public static Date genDate(int year, int month, int day, int hour, int minute, int second)
	{
		Date date = new Date();
		date.setYear(year - 1900);
		date.setMonth(month - 1);
		date.setDate(day);
		date.setHours(hour);
		date.setMinutes(minute);
		date.setSeconds(second);
		return date;
	}

	public static String getIndentStr(int indent)
	{
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < indent; i++)
		{
			sb.append("\t");
		}
		return sb.toString();
	}

	public static View getRootView(View view)
	{
		if (view.getParent() != null && !(view.getParent() instanceof ContentFrameLayout))
		{
			ViewGroup viewGroup = (ViewGroup) view.getParent();
			LOG.v(viewGroup.toString());
			return getRootView(viewGroup);
		}
		else
		{
			LOG.v("root:" + view.toString());
			return view;
		}
	}

	public static void showViewTree(View view)
	{
		LOG.v("---------------showViewTree---------------");
		showViewTree(view, 0);
	}

	public static void showViewTree(View view, int indent)
	{
		LOG.v(CommonUtils.getIndentStr(indent) + view.toString());
		if (view instanceof ViewGroup)
		{
			ViewGroup viewGroup = (ViewGroup) view;
			for (int i = 0; i < viewGroup.getChildCount(); i++)
			{
				View child = viewGroup.getChildAt(i);
				showViewTree(child, indent + 1);
			}
		}
	}

	public static String formatTime(long ms)
	{
		long second = ms / 1000;
		long minute = second / 60;
		second = second % 60;
		long hour = minute / 60;
		minute = minute % 60;
		if (hour == 0)
			return String.format("%02d:%02d", minute, second);
		else
			return String.format("%02d:%02d:%02d", hour, minute, second);
	}

	public static String formatTime2(long ms)
	{
		int second = (int) (ms / 1000);
		if (second < 60)
		{
			return "刚刚";
		}
		else
		{
			int minute = second / 60;
			if (minute < 60)
			{
				return minute + " 分钟前";
			}
			else
			{
				int hour = minute / 60;
				if (hour < 24)
				{
					return hour + " 小时前";
				}
				else
				{
					int day = hour / 24;
					if (day < 30)
					{
						return day + " 天前";
					}
					else
					{
						int month = day / 30;
						if (month < 12)
						{
							return month + " 个月前";
						}
						else
						{
							int year = month / 12;
							return year + " 年前";
						}
					}
				}
			}
		}
	}

	public static String formatSize(double size)
	{
		return formatSize(size, 1024);
	}

	public static String formatSize(double size, long block)
	{
		if (size < block)
		{
			return (int) size + " 字节";
		}
		else if (size < block * block)
		{
			String str = String.format("%.1f", size / block);
			if (str.endsWith(".0"))
				str = str.substring(0, str.length() - 2);
			return str + " KB";
		}
		else if (size < block * block * block)
		{
			String str = String.format("%.1f", size / (block * block));
			if (str.endsWith(".0"))
				str = str.substring(0, str.length() - 2);
			return str + " MB";
		}
		else
		{
			String str = String.format("%.1f", size / (block * block * block));
			if (str.endsWith(".0"))
				str = str.substring(0, str.length() - 2);
			return str + " GB";
		}
	}

	public static String formatSize2(double size)
	{
		if (size < 1024)
		{
			return (int) size + " 字节";
		}
		else if (size < 1024 * 1024)
		{
			String str = String.format("%.0f", size / 1024);
			return str + " KB";
		}
		else if (size < 1024 * 1024 * 1024)
		{
			String str = String.format("%.0f", size / (1024 * 1024));
			return str + " MB";
		}
		else
		{
			String str = String.format("%.0f", size / (1024 * 1024 * 1024));
			return str + " GB";
		}
	}

	public static String base64Encode(byte[] bytes)
	{
		return Base64.encodeToString(bytes, Base64.DEFAULT);
	}

	public static byte[] base64Decode(String str)
	{
		return Base64.decode(str, Base64.DEFAULT);
	}

	public static String getGbkStr(String str)
	{
		return new String(str.getBytes(Charset.forName("gbk")), Charset.forName("gbk"));
	}

	public static String getUtf8Str(String str)
	{
		return new String(str.getBytes(Charset.forName("utf-8")), Charset.forName("utf-8"));
	}

	// utf-8,gbk
	public static String urlDecode(String str, String enc)
	{
		try
		{
			return URLDecoder.decode(str, enc);
		}
		catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		return str;
	}

	public static String urlEncode(String str, String enc)
	{
		try
		{
			return URLEncoder.encode(str, enc);
		}
		catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		return str;
	}

	public static String urlDecode(String str)
	{
		try
		{
			return URLDecoder.decode(str, "utf-8");
		}
		catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		return str;
	}

	public static String urlEncode(String str)
	{
		try
		{
			return URLEncoder.encode(str, "utf-8");
		}
		catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		return str;
	}

	public static String uuid()
	{
		return UUID.randomUUID().toString();
	}

	public static long uid()
	{
		return System.currentTimeMillis() * 1000000 + (int) (Math.random() * 1000000);
	}

	public static long crc32(String str)
	{
		CRC32 crc32 = new CRC32();
		crc32.update(str.getBytes());
		return crc32.getValue();
	}

	public static String md5(byte[] bytes)
	{
		try
		{
			MessageDigest md5 = MessageDigest.getInstance("MD5"); // MD5,SHA1,SHA256
			md5.update(bytes);
			byte[] data = md5.digest();
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < data.length; i++)
			{
				int d = data[i];
				if (d < 0)
					d += 256;
				if (d < 16)
					sb.append("0");
				sb.append(Integer.toHexString(d));
			}
			return sb.toString();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return "";
	}

	public static String md5(String text)
	{
		byte[] bytes = text.getBytes(Charset.forName("utf-8"));
		return md5(bytes);
	}

	public static String md5(File file)
	{
		try
		{
			MessageDigest md5 = MessageDigest.getInstance("MD5"); // MD5,SHA1,SHA256

			FileInputStream fis = new FileInputStream(file);
			byte[] buffer = new byte[1024 * 16];
			int len = 0;
			while ((len = fis.read(buffer)) > 0)
			{
				md5.update(buffer, 0, len);
			}
			fis.close();

			byte[] data = md5.digest();
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < data.length; i++)
			{
				int d = data[i];
				if (d < 0)
					d += 256;
				if (d < 16)
					sb.append("0");
				sb.append(Integer.toHexString(d));
			}
			return sb.toString();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return "";
	}

	public static Bitmap scaleBitmap(Bitmap bitmap, int dstWidth)
	{
		int dstHeight = dstWidth * bitmap.getHeight() / bitmap.getWidth();
		Bitmap dstBitmap = Bitmap.createBitmap(dstWidth, dstHeight, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(dstBitmap);
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setFilterBitmap(true);
		canvas.drawBitmap(bitmap, new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight()), new Rect(0, 0, dstBitmap.getWidth(), dstBitmap.getHeight()), paint);
		return dstBitmap;
//		return Bitmap.createScaledBitmap(bitmap, dstWidth, dstHeight, false); // 这个方法产生的图像有点模糊
	}

	public static Bitmap captureView(View view)
	{
		Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		view.draw(canvas);
		return bitmap;
	}

	public static Bitmap captureView(View view, int color)
	{
		Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		canvas.drawColor(color);
		view.draw(canvas);
		return bitmap;
	}

	public static void captureView(View view, File file, Bitmap.CompressFormat format)
	{
		Bitmap bitmap;
		if (format.equals(Bitmap.CompressFormat.PNG))
			bitmap = captureView(view);
		else
			bitmap = captureView(view, 0xffffffff);
		saveBitmap(bitmap, format, file);
		bitmap.recycle();
	}

	public static BitmapFactory.Options readBitmapSize(String filepath)
	{
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filepath, opts);
		return opts;
	}

	public static BitmapFactory.Options readBitmapSize(byte[] bitmapData)
	{
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inJustDecodeBounds = true;
		BitmapFactory.decodeByteArray(bitmapData, 0, bitmapData.length, opts);
		return opts;
	}

	public static Bitmap readBitmap(byte[] bitmapData, int width)
	{
		if (width > 0)
		{
			BitmapFactory.Options opts = new BitmapFactory.Options();
			opts.inJustDecodeBounds = true;
			BitmapFactory.decodeByteArray(bitmapData, 0, bitmapData.length, opts);
			opts.inJustDecodeBounds = false;
			int rate = opts.outWidth / width;
//			LOG.v(String.format("outWidth=%d, width=%d, rate=%d", opts.outWidth, width, rate));
			rate = Math.max(rate, 1);
			opts.inSampleSize = rate;
			return BitmapFactory.decodeByteArray(bitmapData, 0, bitmapData.length);
		}
		else
		{
			return BitmapFactory.decodeByteArray(bitmapData, 0, bitmapData.length);
		}
	}

	public static Bitmap readBitmap(String filepath, int width)
	{
		if (width > 0)
		{
			BitmapFactory.Options opts = new BitmapFactory.Options();
			opts.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(filepath, opts);
			opts.inJustDecodeBounds = false;
			int rate = opts.outWidth / width;
//			LOG.v(String.format("outWidth=%d, width=%d, rate=%d", opts.outWidth, width, rate));
			rate = Math.max(rate, 1);
			opts.inSampleSize = rate;
			return BitmapFactory.decodeFile(filepath, opts);
		}
		else
		{
			return BitmapFactory.decodeFile(filepath);
		}
	}

	// Bitmap.CompressFormat.PNG
	// Bitmap.CompressFormat.JPEG
	public static byte[] saveBitmapToData(Bitmap bitmap, Bitmap.CompressFormat format, int quality)
	{
		byte[] data = null;
		try
		{
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			bitmap.compress(format, quality, os);
			data = os.toByteArray();
			os.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return data;
	}

	// Bitmap.CompressFormat.PNG
	// Bitmap.CompressFormat.JPEG
	public static void saveBitmap(Bitmap bitmap, Bitmap.CompressFormat format, File file)
	{
		try
		{
			FileOutputStream os = new FileOutputStream(file);
			bitmap.compress(format, 100, os);
			os.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static Bitmap.CompressFormat getBitmapFormat(File file)
	{
		if (file.getName().toLowerCase().endsWith(".png"))
			return Bitmap.CompressFormat.PNG;
		else
			return Bitmap.CompressFormat.JPEG;
	}

	public static void saveBitmap(Bitmap bitmap, File file)
	{
		saveBitmap(bitmap, getBitmapFormat(file), file);
	}

	public static int findViewPos(View view)
	{
		ViewGroup parent = (ViewGroup) view.getParent();
		for (int i = 0; i < parent.getChildCount(); i++)
		{
			if (parent.getChildAt(i) == view)
				return i;
		}
		return -1;
	}

	public static void removeView(View view)
	{
		ViewGroup parent = (ViewGroup) view.getParent();
		parent.removeView(view);
	}

	public static void moveUpView(View view)
	{
		ViewGroup parent = (ViewGroup) view.getParent();
		int pos = findViewPos(view);
		if (pos > 0)
		{
			parent.removeView(view);
			parent.addView(view, pos - 1);
		}
	}

	public static void moveDownView(View view)
	{
		ViewGroup parent = (ViewGroup) view.getParent();
		int pos = findViewPos(view);
		if (pos < parent.getChildCount() - 1)
		{
			parent.removeView(view);
			parent.addView(view, pos + 1);
		}
	}

	public static float[] rgb2hsb(int rgbR, int rgbG, int rgbB)
	{
		assert 0 <= rgbR && rgbR <= 255;
		assert 0 <= rgbG && rgbG <= 255;
		assert 0 <= rgbB && rgbB <= 255;
		int[] rgb = new int[]{rgbR, rgbG, rgbB};
		Arrays.sort(rgb);
		int max = rgb[2];
		int min = rgb[0];

		float hsbB = max / 255.0f;
		float hsbS = max == 0 ? 0 : (max - min) / (float) max;

		float hsbH = 0;
		if (max == rgbR && rgbG >= rgbB)
		{
			hsbH = (rgbG - rgbB) * 60f / (max - min) + 0;
		}
		else if (max == rgbR && rgbG < rgbB)
		{
			hsbH = (rgbG - rgbB) * 60f / (max - min) + 360;
		}
		else if (max == rgbG)
		{
			hsbH = (rgbB - rgbR) * 60f / (max - min) + 120;
		}
		else if (max == rgbB)
		{
			hsbH = (rgbR - rgbG) * 60f / (max - min) + 240;
		}

		return new float[]{hsbH, hsbS, hsbB};
	}

	public static int[] hsb2rgb(float h, float s, float v)
	{
		assert Float.compare(h, 0.0f) >= 0 && Float.compare(h, 360.0f) <= 0;
		assert Float.compare(s, 0.0f) >= 0 && Float.compare(s, 1.0f) <= 0;
		assert Float.compare(v, 0.0f) >= 0 && Float.compare(v, 1.0f) <= 0;

		float r = 0, g = 0, b = 0;
		int i = (int) ((h / 60) % 6);
		float f = (h / 60) - i;
		float p = v * (1 - s);
		float q = v * (1 - f * s);
		float t = v * (1 - (1 - f) * s);
		switch (i)
		{
		case 0:
			r = v;
			g = t;
			b = p;
			break;
		case 1:
			r = q;
			g = v;
			b = p;
			break;
		case 2:
			r = p;
			g = v;
			b = t;
			break;
		case 3:
			r = p;
			g = q;
			b = v;
			break;
		case 4:
			r = t;
			g = p;
			b = v;
			break;
		case 5:
			r = v;
			g = p;
			b = q;
			break;
		default:
			break;
		}
		return new int[]{(int) (r * 255.0), (int) (g * 255.0), (int) (b * 255.0)};
	}

}
