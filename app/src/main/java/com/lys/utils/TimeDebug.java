package com.lys.utils;

import com.lys.base.utils.LOG;

public class TimeDebug
{
	public static long timeInit = 0;
	public static long time1 = 0;

	public static void init()
	{
		timeInit = time1 = System.currentTimeMillis();
		LOG.v("------------- TimeDebug init");
	}

	public static void record(String msg)
	{
		long time2 = System.currentTimeMillis();
		long timeDt = time2 - time1;
		long seconds = timeDt / 1000;
		long minutes = timeDt % 1000;
		LOG.v(String.format("---------------------------------------------------------------------------- TimeDebug %s  %d.%03d 秒", msg, seconds, minutes));
		time1 = time2;
	}

	public static void over(String msg)
	{
		long time2 = System.currentTimeMillis();
		long timeDt = time2 - timeInit;
		long seconds = timeDt / 1000;
		long minutes = timeDt % 1000;
		LOG.v(String.format("---------------------------------------------------------------------------- TimeDebug %s  %d.%03d 秒", msg, seconds, minutes));
	}
}
