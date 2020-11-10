package com.lys.base.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SPHelper
{
	public static void remove(Context context, String key)
	{
		SharedPreferences.Editor editor = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE).edit();
		editor.remove(key);
		editor.apply();
	}

	public static void putString(Context context, String key, String value)
	{
		SharedPreferences.Editor editor = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE).edit();
		editor.putString(key, value);
		editor.apply();
	}

	public static void putInt(Context context, String key, int value)
	{
		SharedPreferences.Editor editor = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE).edit();
		editor.putInt(key, value);
		editor.apply();
	}

	public static void putLong(Context context, String key, long value)
	{
		SharedPreferences.Editor editor = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE).edit();
		editor.putLong(key, value);
		editor.apply();
	}

	public static void putFloat(Context context, String key, float value)
	{
		SharedPreferences.Editor editor = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE).edit();
		editor.putFloat(key, value);
		editor.apply();
	}

	public static void putBoolean(Context context, String key, boolean value)
	{
		SharedPreferences.Editor editor = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE).edit();
		editor.putBoolean(key, value);
		editor.apply();
	}

	public static String getString(Context context, String key, String defaultValue)
	{
		SharedPreferences sp = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
		return sp.getString(key, defaultValue);
	}

	public static int getInt(Context context, String key, int defaultValue)
	{
		SharedPreferences sp = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
		return sp.getInt(key, defaultValue);
	}

	public static long getLong(Context context, String key, long defaultValue)
	{
		SharedPreferences sp = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
		return sp.getLong(key, defaultValue);
	}

	public static float getFloat(Context context, String key, float defaultValue)
	{
		SharedPreferences sp = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
		return sp.getFloat(key, defaultValue);
	}

	public static boolean getBoolean(Context context, String key, boolean defaultValue)
	{
		SharedPreferences sp = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
		return sp.getBoolean(key, defaultValue);
	}
}
