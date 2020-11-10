package com.lys.kit.module;

import java.io.File;

public abstract class ModuleCompressor
{
	private static ModuleCompressor mInstance = null;

	public static ModuleCompressor instance()
	{
		return mInstance;
	}

	public static void setup(ModuleCompressor instance)
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

	public abstract void compressVideo(File srcVideoFile, File dstVideoFile, CompressListener listener);

	public interface CompressListener
	{
		void onStart();

		void onSuccess();

		void onFail();

		void onProgress(float percent);
	}
}
