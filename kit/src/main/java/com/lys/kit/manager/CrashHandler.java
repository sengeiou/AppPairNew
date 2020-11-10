package com.lys.kit.manager;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;

import com.lys.base.utils.CommonUtils;
import com.lys.base.utils.FsUtils;
import com.lys.base.utils.SysUtils;
import com.lys.kit.AppKit;
import com.lys.kit.module.ModuleKit;
import com.lys.kit.utils.Protocol;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

// 崩溃处理
public class CrashHandler implements Thread.UncaughtExceptionHandler
{
	private Context mContext;
	private Thread.UncaughtExceptionHandler mDefaultHandler;

	public void init(Context context)
	{
		mContext = context.getApplicationContext();

		// 获取默认异常处理器
		mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();

		// 将此类设为默认异常处理器
		Thread.setDefaultUncaughtExceptionHandler(this);
	}

	@Override
	public void uncaughtException(Thread thread, Throwable throwable)
	{
		handleException(throwable);
		if (mDefaultHandler != null)
		{
			mDefaultHandler.uncaughtException(thread, throwable);
		}
	}

	// 是否人为捕获异常
	private void handleException(Throwable throwable)
	{
		if (throwable == null)
		{
			return;
		}

//		new Thread(new Runnable()
//		{
//			@Override
//			public void run()
//			{
//				Looper.prepare();
//				Toast.makeText(mContext, "捕获到异常", Toast.LENGTH_SHORT).show();
//				Looper.loop();
//			}
//		}).start();

		Writer writer = new StringWriter();
		PrintWriter pw = new PrintWriter(writer);
		throwable.printStackTrace(pw);
		Throwable cause = throwable.getCause();
		// 循环取出Cause
		while (cause != null)
		{
			cause.printStackTrace(pw);
			cause = cause.getCause();
		}
		pw.close();
		String result = writer.toString();

		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
		{
			PackageInfo packageInfo = null;
			try
			{
				packageInfo = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0);

				String time = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());
				String fileName = String.format("%s_%s[%s]_%s.txt", time, mContext.getPackageName(), packageInfo.versionCode, AppKit.userId());

				String path = Environment.getExternalStorageDirectory().getPath() + "/crash";
				File dir = new File(path);
				if (!dir.exists())
					dir.mkdirs();

				FileOutputStream fos = null;
				try
				{
					fos = new FileOutputStream(path + "/" + fileName);
					fos.write(result.getBytes());
					fos.write(getDeviceInfo().getBytes());
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
				finally
				{
					if (fos != null)
					{
						try
						{
							fos.close();
						}
						catch (IOException e1)
						{
							e1.printStackTrace();
						}
					}
				}
			}
			catch (PackageManager.NameNotFoundException e)
			{
				e.printStackTrace();
			}
		}
	}

	private static String getDeviceInfo()
	{
		Context context = AppKit.getContext();

		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < 6; i++)
			sb.append("\n");

		sb.append("userId：" + AppKit.userId() + "\n");
		sb.append("userName：" + AppKit.name() + "\n");

		sb.append("\n");

		sb.append(String.format("屏幕大小：(%d, %d)\n", SysUtils.screenWidth(context), SysUtils.screenHeight(context)));
		sb.append("screenWidth(dp)：" + SysUtils.px2dp(SysUtils.screenWidth(context)) + "\n");
		sb.append("screenWidth(sp)：" + SysUtils.px2sp(SysUtils.screenWidth(context)) + "\n");

		sb.append("\n");

		long total = Environment.getExternalStorageDirectory().getTotalSpace();
		long free = Environment.getExternalStorageDirectory().getFreeSpace();
		sb.append(String.format("存储空间：总共：%s，剩余：%s\n", CommonUtils.formatSize(total), CommonUtils.formatSize(free)));

		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
		am.getMemoryInfo(memoryInfo);
		sb.append(String.format("内存：总共：%s，剩余：%s\n", CommonUtils.formatSize(memoryInfo.totalMem, 1000), CommonUtils.formatSize(memoryInfo.availMem, 1000)));

		sb.append("\n");

		sb.append("厂商：" + Build.BRAND + "\n");
		sb.append("型号：" + Build.MODEL + "\n");
		sb.append("版本号：" + Build.DISPLAY + "\n");
		sb.append("Android 版本：" + Build.VERSION.RELEASE + "\n");
		sb.append("SDK 版本：" + Build.VERSION.SDK_INT + "\n");

		sb.append("Serial(设备号)：" + Build.SERIAL + "\n");
		sb.append("IMEI：" + SysUtils.getIMEI(context) + "\n");
		sb.append("ANDROID_ID：" + SysUtils.getAndroidId(context) + "\n");

		sb.append("\n");

		collectBuildInfo(sb);

		return sb.toString();
	}

	private static void collectBuildInfo(StringBuilder sb)
	{
		Field[] fields = Build.class.getFields();
		if (fields != null && fields.length > 0)
		{
			for (Field field : fields)
			{
				field.setAccessible(true);
				try
				{
					sb.append(field.getName()).append(" = ").append(field.get(null).toString()).append("\n");
				}
				catch (IllegalAccessException e)
				{
					sb.append(field.getName()).append(" = ").append("???").append("\n");
				}
			}
		}
	}

	private static void uploadCrashImpl(final Context context, final String dir)
	{
		List<String> files = FsUtils.searchFiles(new File(FsUtils.SD_CARD, "crash"), "*.log;*.txt");
		if (files.size() > 0)
		{
			final File file = new File(files.get(0));
			ModuleKit.instance().doUpload(context, file, String.format("%s/%s", dir, file.getName()), new Protocol.OnCallback()
			{
				@Override
				public void onResponse(int code, String data, String msg)
				{
					if (code == 200)
					{
						file.delete();
						uploadCrashImpl(context, dir);
					}
				}
			});
		}
	}

	public static void uploadCrash()
	{
		final Context context = AppKit.getContext();
		long total = Environment.getExternalStorageDirectory().getTotalSpace();
		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
		am.getMemoryInfo(memoryInfo);
		String dir = String.format("/crash/%s_%s_%s_%s_%s_%s_%s_%s_%s_%s_%s", //
				Build.BRAND, //
				Build.MODEL, //
				Build.VERSION.RELEASE, //
				Build.VERSION.SDK_INT, //
				Build.SERIAL,//
				SysUtils.getIMEI(context),//
				SysUtils.getAndroidId(context),//
				SysUtils.screenWidth(context), //
				SysUtils.screenHeight(context), //
				CommonUtils.formatSize(total), //
				CommonUtils.formatSize(memoryInfo.totalMem, 1000));
		uploadCrashImpl(context, dir);
	}
}
