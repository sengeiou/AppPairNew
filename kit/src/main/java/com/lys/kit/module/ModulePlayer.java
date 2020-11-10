package com.lys.kit.module;

import android.content.Context;
import android.net.Uri;

public abstract class ModulePlayer
{
	private static ModulePlayer mInstance = null;

	public static ModulePlayer instance()
	{
		return mInstance;
	}

	public static void setup(ModulePlayer instance)
	{
		mInstance = instance;
	}

	public static void release()
	{
		mInstance.destroy();
		mInstance = null;
	}

	//-------------------------------

	protected abstract void destroy();

	public abstract void play(Context context, String path);

	public abstract void play(Context context, Uri uri);

	public abstract void playSimple(Context context, String path);

	public abstract void playSimple(Context context, Uri uri);

}
