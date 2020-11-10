package com.lys.utils;

import android.content.Context;

import java.io.FileOutputStream;
import java.io.InputStream;

public class AssetsHelper
{
	private static String GetAssertRelativeUrl(String url)
	{
		if (url.startsWith("file:///android_asset/"))
			return url.substring("file:///android_asset/".length());
		else
			return url;
	}

	public static boolean assetsFileExist(Context context, String assetsFilepath)
	{
		try
		{
			InputStream is = context.getAssets().open(assetsFilepath);
			return is != null;
		}
		catch (Exception e)
		{

		}
		return false;
	}

	public static InputStream getAssetsInputStream(Context context, String path)
	{
		path = GetAssertRelativeUrl(path);

		try
		{
			InputStream is = context.getAssets().open(path);
			return is;
		}
		catch (Exception e)
		{

		}
		return null;
	}

	public static void copyAssetsFile(Context context, String assetsFilepath, String SDCardFilepath)
	{
		assetsFilepath = GetAssertRelativeUrl(assetsFilepath);

		try
		{
			InputStream is = context.getAssets().open(assetsFilepath);
			FileOutputStream os = new FileOutputStream(SDCardFilepath);

			byte[] buff = new byte[1024];
			int hasRead = 0;
			while ((hasRead = is.read(buff)) > 0)
			{
				os.write(buff, 0, hasRead);
			}
			os.close();
			is.close();
		}
		catch (Exception e)
		{

		}
	}

	public static String getAssetsText(Context context, String path)
	{
		path = GetAssertRelativeUrl(path);

		try
		{
			InputStream is = context.getAssets().open(path);
			return StreamHelper.readTextBuffer(is);
		}
		catch (Exception e)
		{

		}
		return "";
	}

	public static byte[] getAssetsBinary(Context context, String path)
	{
		path = GetAssertRelativeUrl(path);

		try
		{
			InputStream is = context.getAssets().open(path);
			return StreamHelper.readBinaryBuffer(is);
		}
		catch (Exception e)
		{

		}
		return null;
	}

	public static String getJsBaseLib(Context context)
	{
		return getAssetsText(context, "nnd_web/nnd_base_lib_base64.js") + //
				getAssetsText(context, "nnd_web/nnd_base_lib_native.js") + //
				getAssetsText(context, "nnd_web/nnd_base_lib_utils.js") + //
				getAssetsText(context, "nnd_web/nnd_base_lib_dom.js");
	}

	public static boolean isAssertUrl(String url)
	{
		return url.startsWith("file:///android_asset/");
	}

}
