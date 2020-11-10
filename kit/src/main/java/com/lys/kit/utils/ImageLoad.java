package com.lys.kit.utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.lys.base.utils.FsUtils;
import com.lys.base.utils.ImageLoader;
import com.lys.kit.config.Config;

public class ImageLoad
{
	private static String checkUrlImpl(String url)
	{
		if (TextUtils.isEmpty(url))
		{
			return url;
		}
		else
		{
			if (url.startsWith(FsUtils.SD_CARD))
			{
				return url;
			}
			else if (url.startsWith("http://") || url.startsWith("https://"))
			{
				return url;
			}
			else
			{
				return Config.getUrlRoot() + url;
			}
		}
	}

	public static String checkUrl(String url)
	{
		url = checkUrlImpl(url);
		if (!TextUtils.isEmpty(url))
		{
			url = url.replace("file.k12-eco.com", "zjyk-file.oss-cn-huhehaote.aliyuncs.com");
			url = url.replace("topic.k12-eco.com", "zjyk-topic.oss-cn-huhehaote.aliyuncs.com");
			if (url.startsWith("http://zjyk-file.oss-cn-huhehaote.aliyuncs.com/") && url.endsWith(".mp4"))
				url = url.replace("http://", "https://");
		}
		return url;
	}

	public static void displayImage(Context context, String url, int width, ImageView imageView, int defaultResId, ImageLoader.OnDisplay callback)
	{
		ImageLoader.displayImage(context, checkUrl(url), width, imageView, defaultResId, callback);
	}

	public static void displayImage(Context context, String url, ImageView imageView, int defaultResId, ImageLoader.OnDisplay callback)
	{
		ImageLoader.displayImage(context, checkUrl(url), imageView.getWidth(), imageView, defaultResId, callback);
	}

	public static void load(Context context, String url, int width, ImageLoader.OnLoad callback)
	{
		ImageLoader.load(context, checkUrl(url), width, callback);
	}

	public static void load(Context context, String url, ImageLoader.OnLoad callback)
	{
		ImageLoader.load(context, checkUrl(url), 0, callback);
	}
}
