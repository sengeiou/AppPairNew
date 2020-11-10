package com.lys.base.utils;

import android.content.Context;

import java.io.InputStream;
import java.nio.charset.Charset;

public class AssetsHelper
{
	public static byte[] readBytes(Context context, String assetPath)
	{
		try
		{
			InputStream is = context.getAssets().open(assetPath);
			byte[] buffer = new byte[is.available()];
			is.read(buffer);
			is.close();
			return buffer;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static String readText(Context context, String assetPath, Charset charset)
	{
		byte[] bytes = readBytes(context, assetPath);
		if (bytes != null)
			return new String(bytes, charset);
		return null;
	}

	public static String readText(Context context, String assetPath)
	{
		return readText(context, assetPath, Charset.forName("utf-8"));
	}
}
