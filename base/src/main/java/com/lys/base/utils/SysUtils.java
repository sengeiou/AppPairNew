package com.lys.base.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.MimeTypeMap;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.lys.base.BuildConfig;

import java.io.File;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Locale;

public class SysUtils
{
	public static boolean isDebug()
	{
//		return false;
		return BuildConfig.DEBUG;
	}

	public static String buildType()
	{
//		return "release";
		return BuildConfig.BUILD_TYPE;
	}

	public static boolean hasNet(Context context)
	{
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkINfo = cm.getActiveNetworkInfo();
		return networkINfo != null;
	}

	public static boolean is4GNet(Context context)
	{
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkINfo = cm.getActiveNetworkInfo();
		if (networkINfo != null && networkINfo.getType() == ConnectivityManager.TYPE_MOBILE)
		{
			return true;
		}
		return false;
	}

	public static Signature[] getSignatures(Context context, String packageName)
	{
		PackageInfo packageInfo = null;
		try
		{
			packageInfo = context.getPackageManager().getPackageInfo(packageName, PackageManager.GET_SIGNATURES);
			return packageInfo.signatures;
		}
		catch (PackageManager.NameNotFoundException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static boolean checkSignatures(Context context, String sigMd5)
	{
		if (BuildConfig.DEBUG)
			return true;
		else
		{
			Signature[] signs = getSignatures(context, context.getPackageName());
			for (Signature sig : signs)
			{
				String md5 = CommonUtils.md5(sig.toByteArray());
				if (CommonUtils.md5(md5).equals(sigMd5))
					return true;
//				else
//					Log.v("wzt", "error sig : " + md5);
			}
		}
		return false;
	}

	public static void openSetting(Context context)
	{
		Intent intent = new Intent();
		intent.setAction(Settings.ACTION_SETTINGS);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}

	public static void openWifiSetting(Context context)
	{
		Intent intent = new Intent();
		intent.setAction(Settings.ACTION_WIFI_SETTINGS);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}

	public static int dp2px(float dpValue)
	{
		float scale = Resources.getSystem().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	public static int px2dp(float pxValue)
	{
		float scale = Resources.getSystem().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	public static int sp2px(float spValue)
	{
		final float fontScale = Resources.getSystem().getDisplayMetrics().scaledDensity;
		return (int) (spValue * fontScale + 0.5f);
	}

	public static int px2sp(float pxValue)
	{
		final float fontScale = Resources.getSystem().getDisplayMetrics().scaledDensity;
		return (int) (pxValue / fontScale + 0.5f);
	}

	public static int screenWidth(Context context)
	{
		WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics dm = new DisplayMetrics();
		windowManager.getDefaultDisplay().getMetrics(dm);
//		LOG.v("screenWidth:" + dm.widthPixels);
		return dm.widthPixels;
	}

	public static int screenHeight(Context context)
	{
		WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics dm = new DisplayMetrics();
		windowManager.getDefaultDisplay().getMetrics(dm);
//		LOG.v("screenHeight:" + dm.heightPixels);
		return dm.heightPixels;
	}

	public static int getStatusHeight(Context context)
	{
		int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
		if (resourceId > 0)
		{
			return context.getResources().getDimensionPixelSize(resourceId);
		}
		return 0;
	}

	public static String getAndroidId(Context context)
	{
		return Settings.System.getString(context.getContentResolver(), Settings.System.ANDROID_ID);
	}

	public static String getSerialNumber(Context context)
	{
		return android.os.Build.SERIAL;
	}

	public static String getIMEI(Context context)
	{
		TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED)
			return "";
		return telephonyManager.getDeviceId();
	}

	public static void openFile(Context context, File file)
	{
		String end = file.getName().substring(file.getName().lastIndexOf(".") + 1, file.getName().length()).toLowerCase(Locale.getDefault());
		Intent intent = new Intent();
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setAction(Intent.ACTION_VIEW);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
		{
			Uri uriForFile = FileProvider.getUriForFile(context, context.getPackageName() + ".FileProvider", file);
			intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
			intent.setDataAndType(uriForFile, context.getContentResolver().getType(uriForFile));
		}
		else
		{
			intent.setDataAndType(Uri.fromFile(file), MimeTypeMap.getSingleton().getMimeTypeFromExtension(end));
		}
		context.startActivity(intent);
	}

	public static void uninstallApk(Context context, String packageName)
	{
		Intent intent = new Intent(Intent.ACTION_DELETE, Uri.fromParts("package", packageName, null));
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}

	public static PackageInfo getApkPackageInfo(Context context, String absPath)
	{
		PackageInfo packageInfo = context.getPackageManager().getPackageArchiveInfo(absPath, PackageManager.GET_ACTIVITIES);
		if (packageInfo != null)
		{
			return packageInfo;
		}
		return null;
	}

	public static PackageInfo getPackageInfo(Context context, String packageName)
	{
		try
		{
			return context.getPackageManager().getPackageInfo(packageName, 0);
		}
		catch (PackageManager.NameNotFoundException e)
		{
//			e.printStackTrace();
		}
		return null;
	}

	public static void launchApp(Context context, String packageName)
	{
		if (getPackageInfo(context, packageName) != null)
		{
			Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
			context.startActivity(intent);
		}
		else
		{
			LOG.toast(context, "未安装:" + packageName);
		}
	}

	// 获取内网IP地址
	public static String getHostIP()
	{
		String hostIp = null;
		try
		{
			Enumeration nis = NetworkInterface.getNetworkInterfaces();
			InetAddress ia = null;
			while (nis.hasMoreElements())
			{
				NetworkInterface ni = (NetworkInterface) nis.nextElement();
				Enumeration<InetAddress> ias = ni.getInetAddresses();
				while (ias.hasMoreElements())
				{
					ia = ias.nextElement();
					if (ia instanceof Inet6Address)
					{
						continue; // skip ipv6
					}
					String ip = ia.getHostAddress();
					if (!"127.0.0.1".equals(ip))
					{
						hostIp = ip;
						break;
					}
				}
			}
		}
		catch (SocketException e)
		{
			e.printStackTrace();
		}
		return hostIp;
	}

	// 获取外网IP地址
	public static void getNetIp(Context context, final HttpUtils.OnCallback callback)
	{
		HttpUtils.doHttpGet(context, "http://pv.sohu.com/cityjson?ie=utf-8", new HttpUtils.OnCallback()
		{
			@Override
			public void onResponse(String jsonStr)
			{
				String netIp = null;
				if (!TextUtils.isEmpty(jsonStr))
				{
					LOG.v(jsonStr);
					int start = jsonStr.indexOf("{");
					int end = jsonStr.indexOf("}");
					String json = jsonStr.substring(start, end + 1);
					if (json != null)
					{
						try
						{
							JSONObject jsonObject = JSONObject.parseObject(json, Feature.OrderedField);
							netIp = jsonObject.getString("cip");
						}
						catch (JSONException e)
						{
							e.printStackTrace();
						}
					}
				}
				if (callback != null)
					callback.onResponse(netIp);
			}
		});
	}

	public static void getNetIp2(Context context, final HttpUtils.OnCallback callback)
	{
		HttpUtils.doHttpGet(context, "http://cloud.k12-eco.com/pair/ip", new HttpUtils.OnCallback()
		{
			@Override
			public void onResponse(String jsonStr)
			{
				if (!TextUtils.isEmpty(jsonStr))
				{
					if (callback != null)
						callback.onResponse(jsonStr);
				}
				else
				{
					if (callback != null)
						callback.onResponse(null);
				}
			}
		});
	}

	public static void showKeybord(Context activity, View view)
	{
		InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
		if (imm != null)
			imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
	}

	public static void hideKeybord(Activity activity)
	{
		if (activity.getCurrentFocus() != null)
		{
			InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
			if (imm != null)
				imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
		}
	}
}
