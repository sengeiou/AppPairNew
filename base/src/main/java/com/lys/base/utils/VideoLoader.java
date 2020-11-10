package com.lys.base.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class VideoLoader
{
	// 同时执行1个线程，最多200个排队，20秒超时，单位为秒，工作队列大小。
	private static final ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 200, 20, TimeUnit.SECONDS, new ArrayBlockingQueue(5), new ThreadPoolExecutor.DiscardOldestPolicy());

	private static final Map<String, List<OnLoad>> tasks = new ConcurrentHashMap<>();

	private static String key(String url)
	{
		return url;
	}

	public static void load(final Context context, final String url, final OnLoad callback)
	{
		if (TextUtils.isEmpty(url))
		{
			if (callback != null)
				callback.over(null, url);
		}
		else
		{
			File file = getCacheFile(context, url);
			if (file.exists())
			{
				if (callback != null)
					callback.over(file, url);
			}
			else
			{
				final String key = key(url);
				final Handler handler = new Handler()
				{
					@Override
					public void handleMessage(Message msg)
					{
						synchronized (tasks)
						{
							File file = (File) msg.obj;
							for (OnLoad cb : tasks.remove(key))
							{
								if (cb != null)
									cb.over(file, url);
							}
						}
					}
				};
				synchronized (tasks)
				{
					if (tasks.containsKey(key))
					{
						tasks.get(key).add(callback);
					}
					else
					{
						tasks.put(key, Collections.synchronizedList(new ArrayList<OnLoad>()));
						tasks.get(key).add(callback);
						executor.execute(new Runnable()
						{
							@Override
							public void run()
							{
								File file = loadFromUrl(context, url);
								Message message = handler.obtainMessage(0, file);
								handler.sendMessage(message);
							}
						});
					}
				}
			}
		}
	}

	public static File getCacheDir(Context context)
	{
		return new File(String.format("%s/%s/videocache", FsUtils.SD_CARD, context.getPackageName()));
	}

	public static File getCacheFile(Context context, String url)
	{
		File cacheDir = getCacheDir(context);
		FsUtils.createFolder(cacheDir);
		return new File(cacheDir, CommonUtils.md5(url));
	}

	private static File loadFromUrl(Context context, String url)
	{
		File file = getCacheFile(context, url);
		boolean success = HttpUtils.doDownload(context, url, file);
		if (success)
		{
			return file;
		}
		else
		{
			return null;
		}
	}

	public interface OnLoad
	{
		void over(File file, String url);
	}
}
