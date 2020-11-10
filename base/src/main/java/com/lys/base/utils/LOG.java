package com.lys.base.utils;

import android.content.Context;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LOG
{
	public interface OnLOGListener
	{
		void onLog(String msg);
	}

	public static OnLOGListener mListener = null;

	private static final SimpleDateFormat format = new SimpleDateFormat("MM-dd HH:mm:ss");

	public static String logfile = null;

	private static void tryWriteToLogFile(String msg)
	{
		if (!TextUtils.isEmpty(logfile))
		{
			Date date = new Date();
			String time = format.format(date);
			long ms = date.getTime() % 1000;
			long mainThreadId = Looper.getMainLooper().getThread().getId();
			long threadId = Thread.currentThread().getId();
			String pkgMsg = String.format("%s.%03d %04d-%04d : %s\r\n", time, ms, mainThreadId, threadId, msg);
			try
			{
				File file = new File(logfile);
				RandomAccessFile raf = new RandomAccessFile(file, "rw");
				raf.seek(file.length());
				raf.write(pkgMsg.getBytes(Charset.forName("UTF-8")));
				raf.close();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	public static void v(String msg)
	{
		synchronized (LOG.class)
		{
//			if (BuildConfig.DEBUG)
			{
				Log.v("wzt", "" + msg); // 将msg串化，避免为null的时候崩溃
				tryWriteToLogFile("" + msg);
			}
			if (mListener != null)
				mListener.onLog(msg);
		}
	}

	public static void e(String msg)
	{
		synchronized (LOG.class)
		{
			Log.e("wzt", "" + msg);
			tryWriteToLogFile("" + msg);
			if (mListener != null)
				mListener.onLog(msg);
		}
	}

	public static void toast(Context context, String msg)
	{
		LOG.v("toast : " + msg);
		Toast.makeText(context, "" + msg, Toast.LENGTH_SHORT).show();
	}
}
