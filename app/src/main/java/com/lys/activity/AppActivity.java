package com.lys.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.text.TextUtils;

import com.lys.App;
import com.lys.base.utils.AppDataTool;
import com.lys.base.utils.CommonUtils;
import com.lys.base.utils.FsUtils;
import com.lys.base.utils.HttpUtils;
import com.lys.base.utils.LOG;
import com.lys.base.utils.SysUtils;
import com.lys.kit.activity.KitActivity;
import com.lys.kit.utils.Protocol;
import com.lys.protobuf.SApp;
import com.lys.protobuf.SHandleId;
import com.lys.protobuf.SRequest_GetAppInfo;
import com.lys.protobuf.SResponse_GetAppInfo;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class AppActivity extends KitActivity
{
	public static final int REQUEST_CODE_SELECT_USER_SINGLE = 0x812; // 选用户
	public static final int REQUEST_CODE_SELECT_USER_MULTI = 0x813; // 选用户

	public void selectUser(OnImageListener listener, Integer... userTypes)
	{
		if (userTypes != null && userTypes.length > 0)
		{
			mImageListener = listener;
			Intent intent = new Intent(context, ActivitySelectUser.class);
			intent.putExtra("multi", false);
			intent.putExtra("userTypes", AppDataTool.saveIntegerList(Arrays.asList(userTypes)).toString());
			startActivityForResult(intent, REQUEST_CODE_SELECT_USER_SINGLE);
		}
	}

	public void selectUsers(OnImageListener listener, List<String> userIds, Integer... userTypes)
	{
		if (userTypes != null && userTypes.length > 0)
		{
			if (userIds == null)
				userIds = new ArrayList<>();
			mImageListener = listener;
			Intent intent = new Intent(context, ActivitySelectUser.class);
			intent.putExtra("multi", true);
			intent.putExtra("userTypes", AppDataTool.saveIntegerList(Arrays.asList(userTypes)).toString());
			intent.putExtra("userIds", AppDataTool.saveStringList(userIds).toString());
			startActivityForResult(intent, REQUEST_CODE_SELECT_USER_MULTI);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, final Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST_CODE_SELECT_USER_SINGLE)
		{
			if (resultCode == RESULT_OK)
			{
				new Handler().postDelayed(new Runnable()
				{
					@Override
					public void run()
					{
						try
						{
							String userStr = data.getStringExtra("userStr");
							LOG.v(userStr);
							if (mImageListener != null)
								mImageListener.onResult(userStr);
						}
						catch (Exception e)
						{
							e.printStackTrace();
						}
					}
				}, 50);
			}
		}
		else if (requestCode == REQUEST_CODE_SELECT_USER_MULTI)
		{
			if (resultCode == RESULT_OK)
			{
				new Handler().postDelayed(new Runnable()
				{
					@Override
					public void run()
					{
						try
						{
							String userStr = data.getStringExtra("userStr");
							LOG.v(userStr);
							if (mImageListener != null)
								mImageListener.onResult(userStr);
						}
						catch (Exception e)
						{
							e.printStackTrace();
						}
					}
				}, 50);
			}
		}
	}

	//------------- 检查更新 ---------------

	public void checkUpdate()
	{
		String channel = null;
		try
		{
			ApplicationInfo appInfo = getPackageManager().getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
			channel = appInfo.metaData.getString("UMENG_CHANNEL");
		}
		catch (PackageManager.NameNotFoundException e)
		{
			e.printStackTrace();
		}
		if (!TextUtils.isEmpty(channel) && !TextUtils.isEmpty(App.getApi()))
		{
			SRequest_GetAppInfo request = new SRequest_GetAppInfo();
			request.pkgName = getPackageName();
			request.channel = channel;
			Protocol.doPost(context, App.getApi(), SHandleId.GetAppInfo, request.saveToStr(), new Protocol.OnCallback()
			{
				@Override
				public void onResponse(int code, String data, String msg)
				{
					if (code == 200)
					{
						SResponse_GetAppInfo response = SResponse_GetAppInfo.load(data);
						if (response.app != null)
						{
							PackageInfo packageInfo = SysUtils.getPackageInfo(context, getPackageName());
							int versionCode = packageInfo.versionCode;
							String versionName = packageInfo.versionName;
							if (!response.app.versionName.equals(versionName) || response.app.versionCode != versionCode)
							{
								if (new Random().nextFloat() < response.app.probability)
									showUpdate(response.app);
							}
						}
					}
				}
			});
		}
	}

	private static AlertDialog mAlertDialog = null;

	private void showUpdate(final SApp app)
	{
		String localPath = String.format("%s/%s_%s.apk", FsUtils.SD_CARD, app.pkgName, app.channel);
		final File file = new File(localPath);
		if (file.exists())
		{
			PackageInfo packageInfo = SysUtils.getApkPackageInfo(context, localPath);
			if (packageInfo != null && packageInfo.versionName.equals(app.versionName) && packageInfo.versionCode == app.versionCode)
			{
				if ((mAlertDialog == null || !mAlertDialog.isShowing()) && !isDownloading(file))
				{
					AlertDialog.Builder builder = new AlertDialog.Builder(context);
					builder.setMessage("新版本已下载完成，是否安装？");
					builder.setNeutralButton("暂不安装", null);
					builder.setPositiveButton("安装", new DialogInterface.OnClickListener()
					{
						@Override
						public void onClick(DialogInterface dialogInterface, int which)
						{
							installApk(file);
//							if (KitUtils.isC5())
//							{
//								DialogWait.show(context, "安装中。。。");
//							}
						}
					});
					mAlertDialog = builder.show();
				}
				return;
			}
		}
		if ((mAlertDialog == null || !mAlertDialog.isShowing()) && !isDownloading(file))
		{
			AlertDialog.Builder builder = new AlertDialog.Builder(context);
			builder.setMessage(String.format("发现新版本（%s），是否更新？（%s）", app.versionCode, CommonUtils.formatSize(app.size)));
			builder.setNeutralButton("暂不更新", null);
			builder.setPositiveButton("更新", new DialogInterface.OnClickListener()
			{
				@Override
				public void onClick(DialogInterface dialogInterface, int which)
				{
					doUpdate(app, file);
				}
			});
			mAlertDialog = builder.show();
		}
	}

	private boolean isDownloading(File file)
	{
		return HttpUtils.getRunningDownloadInfo(file) != null || HttpUtils.getWaitDownloadInfo(file) != null;
	}

	private void doUpdate(final SApp app, final File file)
	{
		final ProgressDialog progressDialog = new ProgressDialog(context);
		progressDialog.setMax((int) (long) app.size);
		progressDialog.setTitle("准备下载。。。");
		progressDialog.setCancelable(false);
		progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		progressDialog.setIndeterminate(false);
		progressDialog.show();
		HttpUtils.download(context, app.apkUrl, file, new HttpUtils.OnDownloadListener()
		{
			@Override
			public void onWait()
			{
				progressDialog.setTitle("等待中。。。");
			}

			@Override
			public void onFail()
			{
				LOG.toast(context, "下载失败");
				progressDialog.dismiss();
			}

			@Override
			public void onProgress(int alreadyDownloadSize)
			{
				progressDialog.setTitle("下载中。。。");
				progressDialog.setProgress(alreadyDownloadSize);
			}

			@Override
			public void onSuccess()
			{
				progressDialog.dismiss();
				installApk(file);
//				if (KitUtils.isC5())
//				{
//					DialogWait.show(context, "安装中。。。");
//				}
			}
		});
	}

}
