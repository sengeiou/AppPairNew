package com.lys.base.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.ImageView;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ImageLoader
{
	// 同时执行5个线程，最多200个排队，5秒超时，单位为秒，工作队列大小。
	private static final ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 200, 5, TimeUnit.SECONDS, new ArrayBlockingQueue(5), new ThreadPoolExecutor.DiscardOldestPolicy());

	//	private static final Map<String, SoftReference<Bitmap>> caches = new ConcurrentHashMap<>();
	private static final Map<String, List<OnLoad>> tasks = new ConcurrentHashMap<>();

	public static void displayImage(Context context, String url, final ImageView imageView, final int defaultResId, final OnDisplay callback)
	{
		displayImage(context, url, imageView.getWidth(), imageView, defaultResId, callback);
	}

	public static void displayImage(Context context, String url, int width, final ImageView imageView, final int defaultResId, final OnDisplay callback)
	{
		if (url == null)
			url = "";
		imageView.setTag(url);
//		imageView.setImageBitmap(null);
		ImageLoader.load(context, url, width, new OnLoad()
		{
			@Override
			public void over(Bitmap bitmap, String url)
			{
				if (url.equals(imageView.getTag()))
				{
					if (bitmap != null)
					{
						imageView.setImageBitmap(bitmap);
						if (callback != null)
							callback.success(bitmap, url);
					}
					else
					{
						if (defaultResId > 0)
							imageView.setImageResource(defaultResId);
						else
							imageView.setImageBitmap(null);
					}
				}
			}
		});
	}

	private static String key(String url, int width)
	{
		return width + ":" + url;
	}

	public static void load(final Context context, final String url, final int width, final OnLoad callback)
	{
		if (TextUtils.isEmpty(url))
		{
			if (callback != null)
				callback.over(null, url);
		}
		else
		{
			if (url.startsWith(FsUtils.SD_CARD) || url.startsWith("/data/"))
			{
				if (new File(url).exists())
				{
					Bitmap bitmap = CommonUtils.readBitmap(url, width);
					if (callback != null)
						callback.over(bitmap, url);
				}
				else
				{
					if (callback != null)
						callback.over(null, url);
				}
			}
			else
			{
				File file = getCacheFile(context, url);
				if (file.exists())
				{
					Bitmap bitmap = CommonUtils.readBitmap(file.toString(), width);
					if (callback != null)
						callback.over(bitmap, url);
				}
				else
				{
					final String key = key(url, width);
//					if (caches.containsKey(key) && caches.get(key).get() != null && !caches.get(key).get().isRecycled())
//					{
//						if (callback != null)
//							callback.over(caches.get(key).get(), url);
//					}
//					else
					{
						final Handler handler = new Handler()
						{
							@Override
							public void handleMessage(Message msg)
							{
								synchronized (tasks)
								{
									Bitmap bitmap = (Bitmap) msg.obj;
									for (OnLoad cb : tasks.remove(key))
									{
										if (cb != null)
											cb.over(bitmap, url);
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
										Bitmap bitmap = loadFromUrl(context, url, width);
//										if (bitmap != null && width > 0 && width < bitmap.getWidth())
//										{
//											Bitmap small = CommonUtils.scaleBitmap(bitmap, width);
//											bitmap.recycle();
//											bitmap = small;
//										}
//										if (bitmap != null)
//											caches.put(key, new SoftReference<>(bitmap));
										Message message = handler.obtainMessage(0, bitmap);
										handler.sendMessage(message);
									}
								});
							}
						}
					}
				}
			}
		}
	}

	public static File getCacheDir(Context context)
	{
		return new File(String.format("%s/%s/imagecache", FsUtils.SD_CARD, context.getPackageName()));
	}

	public static File getCacheFile(Context context, String url)
	{
		File cacheDir = getCacheDir(context);
		FsUtils.createFolder(cacheDir);
		return new File(cacheDir, CommonUtils.md5(url));
	}

	private static Bitmap loadFromUrl(Context context, String url, int width)
	{
		File file = getCacheFile(context, url);
		boolean success = HttpUtils.doDownload(context, url, file);
		if (success)
		{
			return CommonUtils.readBitmap(file.toString(), width);
		}
		else
		{
			return null;
		}
	}

	public interface OnDisplay
	{
		void success(Bitmap bitmap, String url);
	}

	public interface OnLoad
	{
		void over(Bitmap bitmap, String url);
	}
}
