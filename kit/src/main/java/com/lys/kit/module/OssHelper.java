package com.lys.kit.module;

import com.lys.base.utils.SysUtils;

import java.io.File;

public abstract class OssHelper
{
	private static OssHelper mInstance = null;

	public static OssHelper instance()
	{
		return mInstance;
	}

	public static void setup(OssHelper instance)
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

	public static final String ZjykFile = "zjyk-file";

	public static String DirCover()
	{
		return SysUtils.isDebug() ? "d_cover/" : "cover/";
	}

	public static String DirImage()
	{
		return SysUtils.isDebug() ? "d_image/" : "image/";
	}

	public static String DirMatter()
	{
		return SysUtils.isDebug() ? "d_matter/" : "matter/";
	}

	public static String DirLive()
	{
		return SysUtils.isDebug() ? "d_live/" : "live/";
	}

	public static String DirGoods()
	{
		return SysUtils.isDebug() ? "d_goods/" : "goods/";
	}

	public static String DirHead()
	{
		return SysUtils.isDebug() ? "d_head/" : "head/";
	}

	public static String DirVideo()
	{
		return SysUtils.isDebug() ? "d_video/" : "video/";
	}

	public interface OnProgressListener
	{
		void onProgress(long currentSize, long totalSize);

		void onSuccess(String url);

		void onFail();
	}

	public abstract void doUploadWithProgress(String bucketName, File file, String path, OnProgressListener listener);

	public abstract void doUploadMd5FileWithProgress(String bucketName, File file, String dir, OnProgressListener listener);
}
