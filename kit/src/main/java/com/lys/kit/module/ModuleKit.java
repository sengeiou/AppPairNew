package com.lys.kit.module;

import android.content.Context;

import com.lys.kit.utils.Protocol;

import java.io.File;

public abstract class ModuleKit
{
	private static ModuleKit mInstance = null;

	public static ModuleKit instance()
	{
		return mInstance;
	}

	public static void setup(ModuleKit instance)
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

	public abstract void doUpload(Context context, File file, String path, Protocol.OnCallback callback);

}
