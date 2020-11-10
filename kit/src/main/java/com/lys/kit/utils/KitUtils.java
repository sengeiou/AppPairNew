package com.lys.kit.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.LruCache;
import android.view.View;

import com.lys.base.utils.CommonUtils;
import com.lys.base.utils.SysUtils;
import com.lys.board.dawing.LysBoardDrawingType;

import java.io.File;

public class KitUtils
{
	public static boolean isD7()
	{
		return "D7".equals(Build.MODEL);
	}

	public static boolean isG6()
	{
		return "G6".equals(Build.MODEL);
	}

	public static boolean isC5()
	{
		return "BZT-AL00".equals(Build.MODEL);
	}

	public static boolean isM2()
	{
		return "HUAWEI M2-A01W".equals(Build.MODEL);
	}

	public static int getPenType()
	{
		if (isM2())
			return LysBoardDrawingType.Brush;
		else
			return LysBoardDrawingType.Stroke;
	}

	public static String getOnlyId(Context context)
	{
		if (KitUtils.isG6())
			return SysUtils.getIMEI(context);
		else
			return SysUtils.getSerialNumber(context);
	}

	public static String getOnlyId2(Context context)
	{
		return SysUtils.getIMEI(context) + "_" + SysUtils.getSerialNumber(context);
	}

	public static void disableHome(Context context)
	{
		if (isC5())
		{
			Intent intent = new Intent();
			intent.setAction("com.linspirer.edu.disablehome");
			context.sendBroadcast(intent);
		}
		if (isG6())
		{
			Intent intent = new Intent();
			intent.setAction("com.hra.hideHome");
			context.sendBroadcast(intent);
		}
	}

	public static void enableHome(Context context)
	{
		if (isC5())
		{
			Intent intent = new Intent();
			intent.setAction("com.linspirer.edu.enablehome");
			context.sendBroadcast(intent);
		}
		if (isG6())
		{
			Intent intent = new Intent();
			intent.setAction("com.hra.showHome");
			context.sendBroadcast(intent);
		}
	}

	public static void disableRecent(Context context)
	{
		if (isC5())
		{
			Intent intent = new Intent();
			intent.setAction("com.linspirer.edu.disablerecent");
			context.sendBroadcast(intent);
		}
	}

	public static void enableRecent(Context context)
	{
		if (isC5())
		{
			Intent intent = new Intent();
			intent.setAction("com.linspirer.edu.enablerecent");
			context.sendBroadcast(intent);
		}
	}

	public static void showStatusBar(Context context)
	{
		Intent intent = new Intent();
		intent.setAction("com.hra.enableDrop");
		context.sendBroadcast(intent);
	}

	public static void hideStatusBar(Context context)
	{
		Intent intent = new Intent();
		intent.setAction("com.hra.disableDrop");
		context.sendBroadcast(intent);
	}

	public static void showNavBar(Context context)
	{
		Intent intent = new Intent();
		intent.setAction("com.hra.statusbar");
		intent.putExtra("hide", false);
		context.sendBroadcast(intent);
	}

	public static void hideNavBar(Context context)
	{
		Intent intent = new Intent();
		intent.setAction("com.hra.statusbar");
		intent.putExtra("hide", true);
		context.sendBroadcast(intent);
	}

	public static void screenshot(Context context)
	{
		Intent intent = new Intent();
		intent.setAction("android.intent.action.SCREENSHOT");
		context.sendBroadcast(intent);
	}

	public static void captureRecyclerView(RecyclerView view, File file)
	{
		Bitmap bitmap = captureRecyclerView(view);
		CommonUtils.saveBitmap(bitmap, Bitmap.CompressFormat.PNG, file);
		bitmap.recycle();
	}

	public static Bitmap captureRecyclerView(RecyclerView view)
	{
		RecyclerView.Adapter adapter = view.getAdapter();
		Bitmap bigBitmap = null;
		if (adapter != null)
		{
			int size = adapter.getItemCount();
			int height = 0;
			Paint paint = new Paint();
			int iHeight = 0;
			final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
			// Use 1/8th of the available memory for this memory cache.
			final int cacheSize = maxMemory / 8;
			LruCache<String, Bitmap> bitmaCache = new LruCache<>(cacheSize);
			for (int i = 0; i < size; i++)
			{
				RecyclerView.ViewHolder holder = adapter.createViewHolder(view, adapter.getItemViewType(i));
				adapter.onBindViewHolder(holder, i);
				holder.itemView.measure(View.MeasureSpec.makeMeasureSpec(view.getWidth(), View.MeasureSpec.EXACTLY), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
				holder.itemView.layout(0, 0, holder.itemView.getMeasuredWidth(), holder.itemView.getMeasuredHeight());
				holder.itemView.setDrawingCacheEnabled(true);
				holder.itemView.buildDrawingCache();
				Bitmap drawingCache = holder.itemView.getDrawingCache();
				if (drawingCache != null)
				{
					bitmaCache.put(String.valueOf(i), drawingCache);
				}
				height += holder.itemView.getMeasuredHeight();
			}

			bigBitmap = Bitmap.createBitmap(view.getMeasuredWidth(), height, Bitmap.Config.ARGB_8888);
			Canvas bigCanvas = new Canvas(bigBitmap);
			Drawable lBackground = view.getBackground();
			if (lBackground instanceof ColorDrawable)
			{
				ColorDrawable lColorDrawable = (ColorDrawable) lBackground;
				int lColor = lColorDrawable.getColor();
				bigCanvas.drawColor(lColor);
			}

			for (int i = 0; i < size; i++)
			{
				Bitmap bitmap = bitmaCache.get(String.valueOf(i));
				bigCanvas.drawBitmap(bitmap, 0f, iHeight, paint);
				iHeight += bitmap.getHeight();
				bitmap.recycle();
			}
		}
		return bigBitmap;
	}

}
