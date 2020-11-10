package com.lys.kit.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;

import com.lys.base.activity.BaseActivity;
import com.lys.base.utils.CommonUtils;
import com.lys.base.utils.FsUtils;
import com.lys.base.utils.LOG;
import com.lys.base.utils.LOGJson;
import com.lys.base.utils.SysUtils;
import com.lys.board.config.BoardConfig;
import com.lys.kit.config.Config;
import com.lys.kit.dialog.DialogWait;
import com.lys.kit.manager.CacheManager;
import com.lys.kit.utils.KitUtils;
import com.lys.protobuf.SBoardConfig;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KitActivity extends BaseActivity
{
//	public static final String Action_showKillMask = "com.lys.action.showKillMask";
//	public static final String Action_hideKillMask = "com.lys.action.hideKillMask";

	public static List<KitActivity> mActivityList = new ArrayList<>();

	public static KitActivity getTopActivity()
	{
		if (mActivityList.size() > 0)
			return mActivityList.get(mActivityList.size() - 1);
		return null;
	}

	private void showActivityList()
	{
		StringBuilder sb = new StringBuilder();
		for (KitActivity activity : mActivityList)
		{
			sb.append(String.format("-->%s", activity.getClass().getSimpleName()));
		}
		LOG.v(sb.toString());
	}

//	private boolean shouldHideNavBar()
//	{
//		return getClass().getSimpleName().equals("ActivityNoteBook") || getClass().getSimpleName().equals("ActivityTaskBook") || //
//				getClass().getSimpleName().equals("ActivityRecord") || getClass().getSimpleName().equals("ActivityBoard") || //
//				getClass().getSimpleName().equals("ActivityTopicWatch") || getClass().getSimpleName().equals("ActivityTopicAnswer");
//	}

//	protected boolean shouldShowClose()
//	{
//		return KitUtils.isD7() && shouldHideNavBar();
//	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
//		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		mActivityList.add(this);
		showActivityList();
		if (savedInstanceState != null)
		{
			LOG.v("restart app : " + getPackageName());
//			Intent intent = getPackageManager().getLaunchIntentForPackage(getPackageName());
//			startActivity(intent);
			finish();
		}
		else
		{
//			if (KitUtils.isD7())
//			{
//				if (shouldHideNavBar())
//				{
//					KitUtils.hideNavBar(context);
//					willInit(1200);
//				}
//				else
//				{
//					KitUtils.showNavBar(context);
//					willInit(1128);
//				}
//			}
//			else
			{
				init();
			}
		}
	}

//	private void willInit(final int screenHeight)
//	{
//		new Handler().postDelayed(new Runnable()
//		{
//			@Override
//			public void run()
//			{
//				LOG.v("willInit : " + SysUtils.screenHeight(context));
////				if (SysUtils.screenHeight(context) == screenHeight)
//				init();
////				else
////					willInit(screenHeight);
//			}
//		}, 300);
//	}

	protected void init()
	{
		LOG.v(getClass().getSimpleName() + " init");
	}

	@Override
	protected void onResume()
	{
		super.onResume();
		LOG.v("onResume : " + this);
//		if (!KitUtils.isD7(this))
//		{
//			Intent intent = new Intent();
//			intent.setAction(Action_hideKillMask);
//			sendBroadcast(intent);
//		}
//		if (KitUtils.isD7())
//		{
//			if (shouldHideNavBar())
//			{
//				KitUtils.hideNavBar(context);
//			}
//			else
//			{
//				KitUtils.showNavBar(context);
//			}
//		}
	}

	@Override
	protected void onPause()
	{
		super.onPause();
		LOG.v("onPause : " + this);
//		if (!KitUtils.isD7(this))
//		{
//			Intent intent = new Intent();
//			intent.setAction(Action_showKillMask);
//			sendBroadcast(intent);
//		}
	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		mActivityList.remove(this);
		showActivityList();
		if (mActivityList.size() == 0)
		{
			LOG.v("---------------- kill app --------------------");
			android.os.Process.killProcess(android.os.Process.myPid());
			System.exit(0);
		}
		else
		{
			if (finishAction != null)
				finishAction.onFinish();
		}
	}

	public interface OnFinishAction
	{
		void onFinish();
	}

	private OnFinishAction finishAction = null;

	public void setFinishAction(OnFinishAction finishAction)
	{
		this.finishAction = finishAction;
	}

//	@Override
//	public void finish()
//	{
//		super.finish();
//	}

	@Override
	public void installApk(File file)
	{
		if (KitUtils.isD7())
		{
			Intent intent = new Intent();
			intent.setAction("com.hra.Silence.install");
			intent.putExtra("filePath", file.getAbsolutePath());
			intent.putExtra("open", false);
			context.sendBroadcast(intent);
		}
		else if (KitUtils.isG6())
		{
			DialogWait.show(context, "安装中。。。");
			Intent intent = new Intent();
			intent.setAction("com.hra.Silence.install");
			intent.putExtra("filePath", file.getAbsolutePath());
			intent.putExtra("open", true);
			context.sendBroadcast(intent);
		}
//		else if (KitUtils.isC5())
//		{
//			Intent intent = new Intent();
//			intent.setAction("com.linspirer.edu.silentinstall");
//			intent.putExtra("path", file.getAbsolutePath());
//			context.sendBroadcast(intent);
//		}
		else
		{
			super.installApk(file);
		}
	}

	public void uninstallApk(String packageName)
	{
		if (KitUtils.isD7())
		{
			Intent intent = new Intent();
			intent.setAction("com.hra.Silence.uninstall");
			intent.putExtra("packageName", packageName);
			context.sendBroadcast(intent);
		}
//		else if (KitUtils.isC5())
//		{
//			Intent intent = new Intent();
//			intent.setAction("com.linspirer.edu.silentuninstall");
//			intent.putExtra("packageName", packageName);
//			context.sendBroadcast(intent);
//		}
		else
		{
			SysUtils.uninstallApk(context, packageName);
		}
	}

	//------------- 检查更新 ---------------

//	protected void checkUpdate()
//	{
//		if (false)
//		{
//			SRequest_GetUpdate request = new SRequest_GetUpdate();
//			request.pkgName = getPackageName();
//			PackageInfo packageInfo = SysUtils.getPackageInfo(context, getPackageName());
//			request.versionCode = packageInfo.versionCode;
//			request.versionName = packageInfo.versionName;
//			Protocol.doPost(context, 0, request.saveToStr(), new Protocol.OnCallback()
//			{
//				@Override
//				public void onResponse(int code, String data, String msg)
//				{
//					if (code == 200)
//					{
//						SResponse_GetUpdate response = SResponse_GetUpdate.load(data);
//						if (!TextUtils.isEmpty(response.apkUrl))
//							showUpdate(response);
//					}
//				}
//			});
//		}
//		else
//		{
//			SResponse_GetUpdate response = new SResponse_GetUpdate();
//			response.apkUrl = "http://www.k12-eco.com/app_resource/api/other/com_lys_app_atlas.apk";
//			response.versionCode = 18121310;
//			response.versionName = "1.0";
//			response.size = 5651247L;
//			if (!TextUtils.isEmpty(response.apkUrl))
//				showUpdate(response);
//		}
//	}

//	private void showUpdate(final SResponse_GetUpdate response)
//	{
//		String localPath = FsUtils.SD_CARD + "/" + getPackageName() + ".apk";
//		final File file = new File(localPath);
//		if (file.exists())
//		{
//			PackageInfo packageInfo = SysUtils.getApkPackageInfo(context, localPath);
////			if (packageInfo != null)
////			{
////				LOG.v("packageInfo.versionCode:" + packageInfo.versionCode);
////				LOG.v("packageInfo.versionName:" + packageInfo.versionName);
////				LOG.v("size:" + file.length());
////			}
//			if (packageInfo != null && packageInfo.versionName.equals(response.versionName) && packageInfo.versionCode == response.versionCode)
//			{
//				DialogAlert.show(context, null, "新版本已下载完成，是否安装？", new DialogAlert.OnClickListener()
//				{
//					@Override
//					public void onClick(int which)
//					{
//						if (which == 1)
//						{
//							installApk(file);
//						}
//					}
//				}, "暂不安装", "安装");
//				return;
//			}
//		}
//		DialogAlert.show(context, null, String.format("发现新版本，是否更新？（%s）", CommonUtils.formatSize(response.size)), new DialogAlert.OnClickListener()
//		{
//			@Override
//			public void onClick(int which)
//			{
//				if (which == 1)
//				{
//					doUpdate(response, file);
//				}
//			}
//		}, "暂不更新", "更新");
//	}
//
//	private void doUpdate(final SResponse_GetUpdate response, final File file)
//	{
//		DialogWait.show(context, "准备下载。。。");
//		HttpUtils.download(context, ImageLoad.checkUrl(context, response.apkUrl), file, new HttpUtils.OnDownloadListener()
//		{
//			@Override
//			public void onWait()
//			{
//				DialogWait.message("等待中。。。");
//			}
//
//			@Override
//			public void onFail()
//			{
//				LOG.toast(context, "下载失败");
//				DialogWait.close();
//			}
//
//			@Override
//			public void onProgress(int alreadyDownloadSize)
//			{
//				int progress = (int) (100.0 * alreadyDownloadSize / response.size);
//				DialogWait.message("已下载：" + progress + "%");
//			}
//
//			@Override
//			public void onSuccess()
//			{
//				DialogWait.close();
//				installApk(file);
//			}
//		});
//	}

	//------------- 选择图片 ---------------

	public static final int REQUEST_CODE_BOARD = 0x671; // 画布编辑
	public static final int REQUEST_CODE_RECORD = 0x672; // 画布录制
	public static final int REQUEST_CODE_SELECT_IMAGE_CUSTOM = 0x673; // 选图片(自定义)
	public static final int REQUEST_CODE_SELECT_VIDEO_CUSTOM = 0x674; // 选视频(自定义)
	public static final int REQUEST_CODE_SELECT_IMAGE_VIDEO_CUSTOM = 0x675; // 选图片/视频(自定义)
	public static final int REQUEST_CODE_CROP_CUSTOM = 0x676; // 裁剪图片(自定义)
	public static final int REQUEST_CODE_CROP_HEAD_CUSTOM = 0x677; // 裁剪头像(自定义)
	//	public static final int REQUEST_CODE_TOPIC_FILTER = 0x678; // 题库筛选条件
//	public static final int REQUEST_CODE_TOPIC_SELECT = 0x679; // 题库选择
	public static final int REQUEST_CODE_TOPIC_SEARCH = 0x67a; // 搜索题库
	public static final int REQUEST_CODE_SELECT_SELECTION_GROUP = 0x67b; // 选择选项组
	public static final int REQUEST_CODE_SET_SELECTION_GROUP = 0x67c; // 设置选项组
	public static final int REQUEST_CODE_SELECT_NET_IMAGE_CUSTOM = 0x67d; // 选网络图片(自定义)
	public static final int REQUEST_CODE_SELECT_NET_VIDEO_CUSTOM = 0x67e; // 选网络视频(自定义)
	public static final int REQUEST_CODE_SELECT_NET_IMAGE_VIDEO_CUSTOM = 0x67f; // 选网络图片/视频(自定义)

	private void setupBoard(Bitmap bitmap)
	{
		if (bitmap != null)
		{
			File dir = Config.tmpRecordBoardDir;
			FsUtils.createFolder(dir);

			Bitmap boardBitmap = null;
			if (bitmap.getWidth() < SysUtils.screenWidth(context))
			{
				int dstHeight = Math.max(bitmap.getHeight(), SysUtils.screenHeight(context));
				boardBitmap = Bitmap.createBitmap(SysUtils.screenWidth(context), dstHeight, Bitmap.Config.ARGB_8888);
				Canvas canvas = new Canvas(boardBitmap);
				Paint paint = new Paint();
				paint.setAntiAlias(true);
				paint.setFilterBitmap(true);
				canvas.drawBitmap(bitmap, 0, 0, paint);
				bitmap.recycle();
			}
			else if (bitmap.getWidth() > SysUtils.screenWidth(context))
			{
				int dstHeight = SysUtils.screenWidth(context) * bitmap.getHeight() / bitmap.getWidth();
				boardBitmap = Bitmap.createBitmap(SysUtils.screenWidth(context), Math.max(dstHeight, SysUtils.screenHeight(context)), Bitmap.Config.ARGB_8888);
				Canvas canvas = new Canvas(boardBitmap);
				Paint paint = new Paint();
				paint.setAntiAlias(true);
				paint.setFilterBitmap(true);
				canvas.drawBitmap(bitmap, new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight()), new Rect(0, 0, boardBitmap.getWidth(), dstHeight), paint);
				bitmap.recycle();
			}
			else
			{
				if (bitmap.getHeight() < SysUtils.screenHeight(context))
				{
					boardBitmap = Bitmap.createBitmap(SysUtils.screenWidth(context), SysUtils.screenHeight(context), Bitmap.Config.ARGB_8888);
					Canvas canvas = new Canvas(boardBitmap);
					Paint paint = new Paint();
					paint.setAntiAlias(true);
					paint.setFilterBitmap(true);
					canvas.drawBitmap(bitmap, 0, 0, paint);
					bitmap.recycle();
				}
				else
				{
					boardBitmap = bitmap;
				}
			}
			File boardFile = new File(String.format("%s/board.png", dir.getAbsolutePath()));
			CommonUtils.saveBitmap(boardBitmap, Bitmap.CompressFormat.PNG, boardFile);

			File file = new File(String.format("%s/board.json", dir.getAbsolutePath()));
			SBoardConfig board = new SBoardConfig();
			board.bg = BoardConfig.BoardBgTransparent;
			board.height = boardBitmap.getHeight();
			FsUtils.writeText(file, LOGJson.getStr(board.saveToStr()));

			boardBitmap.recycle();
		}
	}

	public void board(OnImageListener listener)
	{
		mImageListener = listener;
		FsUtils.delete(Config.tmpRecordBoardDir);
		Intent intent = new Intent(context, ActivityBoard.class);
		startActivityForResult(intent, REQUEST_CODE_BOARD);
	}

	public void board(Bitmap bitmap, String btnText, OnImageListener listener)
	{
		mImageListener = listener;
		FsUtils.delete(Config.tmpRecordBoardDir);
//		setupBoard(bitmap);
		Intent intent = new Intent(context, ActivityBoard.class);
		intent.putExtra("btnText", btnText);
		byte[] bitmapData = CommonUtils.saveBitmapToData(bitmap, Bitmap.CompressFormat.PNG, 100);
		intent.putExtra("bitmapData", CacheManager.instance().putCache(bitmapData));
		startActivityForResult(intent, REQUEST_CODE_BOARD);
	}

	public void record(OnImageListener listener)
	{
		mImageListener = listener;
		FsUtils.delete(Config.tmpRecordBoardDir);
		Intent intent = new Intent(context, ActivityRecord.class);
		startActivityForResult(intent, REQUEST_CODE_RECORD);
	}

	public void record(Bitmap bitmap, OnImageListener listener)
	{
		mImageListener = listener;
		FsUtils.delete(Config.tmpRecordBoardDir);
//		setupBoard(bitmap);
		Intent intent = new Intent(context, ActivityRecord.class);
		byte[] bitmapData = CommonUtils.saveBitmapToData(bitmap, Bitmap.CompressFormat.PNG, 100);
		intent.putExtra("bitmapData", CacheManager.instance().putCache(bitmapData));
		startActivityForResult(intent, REQUEST_CODE_RECORD);
	}

	public void record(File dir, OnImageListener listener)
	{
		mImageListener = listener;
		FsUtils.delete(Config.tmpRecordBoardDir);
		if (dir != null)
		{
			Map<String, File> filterMap = new HashMap<>();
			File photoFile = new File(String.format("%s/%s.jpg", dir.getAbsolutePath(), BoardConfig.big_video));
			File videoFile = new File(String.format("%s/%s.mp4", dir.getAbsolutePath(), BoardConfig.big_video));
			File videoUrlFile = new File(String.format("%s/%s.txt", dir.getAbsolutePath(), BoardConfig.big_video));
			filterMap.put(photoFile.getName(), photoFile);
			filterMap.put(videoFile.getName(), videoFile);
			filterMap.put(videoUrlFile.getName(), videoUrlFile);
			FsUtils.copyPath(dir, Config.tmpRecordBoardDir, filterMap);
		}
		Intent intent = new Intent(context, ActivityRecord.class);
		startActivityForResult(intent, REQUEST_CODE_RECORD);
	}

	public void selectCustomImage(OnImageListener listener)
	{
		mImageListener = listener;
		Intent intent = new Intent(context, ActivitySelectImage.class);
		intent.putExtra("types", ".png;.jpg;.jpeg");
		startActivityForResult(intent, REQUEST_CODE_SELECT_IMAGE_CUSTOM);
	}

	public void selectCustomVideo(OnImageListener listener)
	{
		mImageListener = listener;
		Intent intent = new Intent(context, ActivitySelectImage.class);
		intent.putExtra("types", ".mp4");
		startActivityForResult(intent, REQUEST_CODE_SELECT_VIDEO_CUSTOM);
	}

	public void selectImageVideo(OnImageListener listener)
	{
		mImageListener = listener;
		Intent intent = new Intent(context, ActivitySelectImage.class);
		intent.putExtra("types", ".mp4;.png;.jpg;.jpeg");
		startActivityForResult(intent, REQUEST_CODE_SELECT_IMAGE_VIDEO_CUSTOM);
	}

	public void selectNetImage(OnImageListener listener)
	{
		mImageListener = listener;
		Intent intent = new Intent(context, ActivitySelectNetImage.class);
		intent.putExtra("types", ".png;.jpg;.jpeg");
		startActivityForResult(intent, REQUEST_CODE_SELECT_NET_IMAGE_CUSTOM);
	}

	public void selectNetVideo(OnImageListener listener)
	{
		mImageListener = listener;
		Intent intent = new Intent(context, ActivitySelectNetImage.class);
		intent.putExtra("types", ".mp4");
		startActivityForResult(intent, REQUEST_CODE_SELECT_NET_VIDEO_CUSTOM);
	}

	public void selectNetImageVideo(OnImageListener listener)
	{
		mImageListener = listener;
		Intent intent = new Intent(context, ActivitySelectNetImage.class);
		intent.putExtra("types", ".mp4;.png;.jpg;.jpeg");
		startActivityForResult(intent, REQUEST_CODE_SELECT_NET_IMAGE_VIDEO_CUSTOM);
	}

	public void cropCustomPic(String filepath, OnImageListener listener)
	{
		mImageListener = listener;
		Intent intent = new Intent(context, ActivityCrop.class);
		intent.putExtra("path", filepath);
		startActivityForResult(intent, REQUEST_CODE_CROP_CUSTOM);
	}

	public void cropCustomHead(String filepath, OnImageListener listener)
	{
		mImageListener = listener;
		Intent intent = new Intent(context, ActivityCropHead.class);
		intent.putExtra("path", filepath);
		startActivityForResult(intent, REQUEST_CODE_CROP_HEAD_CUSTOM);
	}

//	public void topic(final OnImageListener listener)
//	{
//		topicFilter(new OnImageListener()
//		{
//			@Override
//			public void onResult(String filter)
//			{
//				topicSelect(filter, listener);
//			}
//		});
//	}
//
//	private void topicFilter(OnImageListener listener)
//	{
//		mImageListener = listener;
//		Intent intent = new Intent(context, ActivityTopicFilter.class);
//		startActivityForResult(intent, REQUEST_CODE_TOPIC_FILTER);
//	}
//
//	private void topicSelect(String filter, OnImageListener listener)
//	{
//		mImageListener = listener;
//		Intent intent = new Intent(context, ActivityTopicSelect.class);
//		intent.putExtra("filter", filter);
//		startActivityForResult(intent, REQUEST_CODE_TOPIC_SELECT);
//	}

	public void topicSearch(OnImageListener listener)
	{
		mImageListener = listener;
		Intent intent = new Intent(context, ActivityTopicSearch.class);
		startActivityForResult(intent, REQUEST_CODE_TOPIC_SEARCH);
	}

	public void selectionGroup(final OnImageListener listener)
	{
		selectSelectionGroup(new OnImageListener()
		{
			@Override
			public void onResult(String selectionGroup)
			{
				setSelectionGroup(selectionGroup, listener);
			}
		});
	}

	private void selectSelectionGroup(OnImageListener listener)
	{
		mImageListener = listener;
		Intent intent = new Intent(context, ActivitySelectSelectionGroup.class);
		startActivityForResult(intent, REQUEST_CODE_SELECT_SELECTION_GROUP);
	}

	private void setSelectionGroup(String selectionGroup, OnImageListener listener)
	{
		mImageListener = listener;
		Intent intent = new Intent(context, ActivitySetSelectionGroup.class);
		intent.putExtra("selectionGroup", selectionGroup);
		startActivityForResult(intent, REQUEST_CODE_SET_SELECTION_GROUP);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, final Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST_CODE_BOARD)
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
							String imagePath = data.getStringExtra("path");
							LOG.v(imagePath);
							if (mImageListener != null)
								mImageListener.onResult(imagePath);
						}
						catch (Exception e)
						{
							e.printStackTrace();
						}
					}
				}, 50);
			}
		}
		else if (requestCode == REQUEST_CODE_RECORD)
		{
			if (resultCode == RESULT_OK)
			{
//				new Handler().postDelayed(new Runnable()
//				{
//					@Override
//					public void run()
//					{
//						try
//						{
				String videoPath = data.getStringExtra("path");
				LOG.v(videoPath);
				if (mImageListener != null)
					mImageListener.onResult(videoPath);
//						}
//						catch (Exception e)
//						{
//							e.printStackTrace();
//						}
//					}
//				}, 50);
			}
		}
		else if (requestCode == REQUEST_CODE_SELECT_IMAGE_CUSTOM || requestCode == REQUEST_CODE_SELECT_VIDEO_CUSTOM || requestCode == REQUEST_CODE_SELECT_IMAGE_VIDEO_CUSTOM)
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
							String path = data.getStringExtra("path");
							LOG.v(path);
							if (mImageListener != null)
								mImageListener.onResult(path);
						}
						catch (Exception e)
						{
							e.printStackTrace();
						}
					}
				}, 50);
			}
		}
		else if (requestCode == REQUEST_CODE_SELECT_NET_IMAGE_CUSTOM || requestCode == REQUEST_CODE_SELECT_NET_VIDEO_CUSTOM || requestCode == REQUEST_CODE_SELECT_NET_IMAGE_VIDEO_CUSTOM)
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
							String netPicStr = data.getStringExtra("netPicStr");
							LOG.v(netPicStr);
							if (mImageListener != null)
								mImageListener.onResult(netPicStr);
						}
						catch (Exception e)
						{
							e.printStackTrace();
						}
					}
				}, 50);
			}
		}
		else if (requestCode == REQUEST_CODE_CROP_CUSTOM)
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
							String imagePath = data.getStringExtra("path");
							if (mImageListener != null)
								mImageListener.onResult(imagePath);
						}
						catch (Exception e)
						{
							e.printStackTrace();
						}
					}
				}, 50);
			}
		}
		else if (requestCode == REQUEST_CODE_CROP_HEAD_CUSTOM)
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
							String imagePath = data.getStringExtra("path");
							if (mImageListener != null)
								mImageListener.onResult(imagePath);
						}
						catch (Exception e)
						{
							e.printStackTrace();
						}
					}
				}, 50);
			}
		}
//		else if (requestCode == REQUEST_CODE_TOPIC_FILTER)
//		{
//			if (resultCode == RESULT_OK)
//			{
//				new Handler().postDelayed(new Runnable()
//				{
//					@Override
//					public void run()
//					{
//						try
//						{
//							String result = data.getStringExtra("result");
//							if (mImageListener != null)
//								mImageListener.onResult(result);
//						}
//						catch (Exception e)
//						{
//							e.printStackTrace();
//						}
//					}
//				}, 50);
//			}
//		}
//		else if (requestCode == REQUEST_CODE_TOPIC_SELECT)
//		{
//			if (resultCode == RESULT_OK)
//			{
//				new Handler().postDelayed(new Runnable()
//				{
//					@Override
//					public void run()
//					{
//						try
//						{
//							String result = data.getStringExtra("result");
//							if (mImageListener != null)
//								mImageListener.onResult(result);
//						}
//						catch (Exception e)
//						{
//							e.printStackTrace();
//						}
//					}
//				}, 50);
//			}
//		}
		else if (requestCode == REQUEST_CODE_TOPIC_SEARCH)
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
							String result = data.getStringExtra("result");
							if (mImageListener != null)
								mImageListener.onResult(result);
						}
						catch (Exception e)
						{
							e.printStackTrace();
						}
					}
				}, 50);
			}
		}
		else if (requestCode == REQUEST_CODE_SELECT_SELECTION_GROUP)
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
							String result = data.getStringExtra("result");
							if (mImageListener != null)
								mImageListener.onResult(result);
						}
						catch (Exception e)
						{
							e.printStackTrace();
						}
					}
				}, 50);
			}
		}
		else if (requestCode == REQUEST_CODE_SET_SELECTION_GROUP)
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
							String result = data.getStringExtra("result");
							if (mImageListener != null)
								mImageListener.onResult(result);
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
}
