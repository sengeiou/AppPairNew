package com.lys.kit.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.media.MediaRecorder;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.lys.base.utils.FsUtils;
import com.lys.base.utils.LOG;
import com.lys.kit.AppKit;
import com.lys.kit.R;
import com.lys.kit.config.Config;
import com.lys.kit.dialog.DialogAlert;
import com.lys.kit.manager.CacheManager;
import com.lys.kit.pop.PopInsert;
import com.lys.kit.utils.KitUtils;
import com.lys.kit.view.BoardToolBar;
import com.lys.kit.view.BoardView;
import com.lys.protobuf.SPhotoAddParam;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ActivityRecord extends KitActivity implements View.OnClickListener
{
	private class Holder
	{
		private BoardToolBar toolBar;
		private BoardView board;
		private TextView startRecord;
//		private TextView result;
	}

	private Holder holder = new Holder();

	private void initHolder()
	{
		holder.toolBar = findViewById(R.id.toolBar);
		holder.board = findViewById(R.id.board);
		holder.startRecord = findViewById(R.id.startRecord);
//		holder.result = findViewById(R.id.result);
	}

	private File dir;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void init()
	{
		super.init();
		setContentView(R.layout.activity_record);

		initHolder();

//		findViewById(R.id.close).setVisibility(shouldShowClose() ? View.VISIBLE : View.GONE);
//		findViewById(R.id.close).setOnClickListener(this);

		holder.toolBar.transDirBar();

		if (AppKit.isStudent())
		{
			holder.toolBar.setInsert(true, PopInsert.IconImageScreen, PopInsert.IconImageVideo, PopInsert.IconImageTopic, PopInsert.IconImageSelectionGroup);
		}
		else
		{
			holder.toolBar.setInsert(true, PopInsert.IconImageScreen, PopInsert.IconImageVideo);
		}
		holder.toolBar.setWeike(false);

		holder.toolBar.hideIconSend();
		holder.toolBar.hideIconGrid();
		holder.toolBar.hideIconAddPage();

		holder.toolBar.justSelectImage();

		holder.toolBar.setListener(toolBarListener);

		dir = Config.tmpRecordBoardDir;

		findViewById(R.id.startRecord).setOnClickListener(this);
//		findViewById(R.id.result).setOnClickListener(this);

		holder.board.setMenu(holder.toolBar);
		holder.toolBar.bindBoard(holder.board);
		holder.board.setListener(new BoardView.OnListener()
		{
			@Override
			public void onLockChanged(boolean isLock)
			{
				holder.toolBar.photoLockSetChecked(isLock);
			}

			@Override
			public void onPaste()
			{
				holder.toolBar.onPaste();
			}
		});

		holder.board.addOnLayoutChangeListener(new View.OnLayoutChangeListener()
		{
			@Override
			public void onLayoutChange(View view, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom)
			{
				view.removeOnLayoutChangeListener(this);
				holder.board.loadBoard(dir, new BoardView.OnLoadBoardCallback()
				{
					@Override
					public void onLoadOver()
					{
						if (getIntent().hasExtra("bitmapData"))
						{
							byte[] bitmapData = CacheManager.instance().getAndRemoveCache(getIntent().getStringExtra("bitmapData"));
							SPhotoAddParam param = new SPhotoAddParam();
							param.notEye = true;
							param.doNotActive = true;
							holder.board.addPhoto(bitmapData, param);
							holder.board.setNoModify();
						}
					}
				});
			}
		});

//		holder.board.setBoardHeight(SysUtils.screenHeight(context));
	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		stopRecord();
	}

	private boolean toClose()
	{
		if (isRecording())
		{
			stopRecord();
			DialogAlert.show(context, "录制结束，是否使用此内容？", null, new DialogAlert.OnClickListener()
			{
				@Override
				public void onClick(int which)
				{
					if (which == 0)
					{
						finish();
					}
					else if (which == 1)
					{
						holder.startRecord.setText("重新录制");
						holder.startRecord.setVisibility(View.VISIBLE);
					}
					else if (which == 2)
					{
						String filepath;

						String cameraDir = String.format("%s/DCIM/Camera", FsUtils.SD_CARD);
						new File(cameraDir).mkdirs();

						Date date = new Date();
						for (int i = 0; ; i++)
						{
							if (i == 0)
								filepath = String.format("%s/RECORD_%s.mp4", cameraDir, new SimpleDateFormat("yyyyMMdd_HHmmss").format(date));
							else
								filepath = String.format("%s/RECORD_%s_%d.mp4", cameraDir, new SimpleDateFormat("yyyyMMdd_HHmmss").format(date), i);
							if (!new File(filepath).exists())
								break;
						}

						FsUtils.copy(Config.tmpMp4File, new File(filepath));

						Intent intent = new Intent();
						intent.putExtra("path", filepath);
						setResult(Activity.RESULT_OK, intent);
						finish();
					}
				}
			}, "丢弃并退出", "丢弃", "使用");
			return true;
		}
		else if (holder.board.hasModify())
		{
			DialogAlert.show(context, "是否退出？", null, new DialogAlert.OnClickListener()
			{
				@Override
				public void onClick(int which)
				{
					if (which == 1)
					{
						finish();
					}
				}
			}, "否", "退出");
			return true;
		}
		return false;
	}

	// 解决返回时会录到前一个activity的问题
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if (keyCode == KeyEvent.KEYCODE_BACK)
		{
			if (toClose())
				return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void finish()
	{
//		getCurrFragmentPage().genSmall(false);
		super.finish();
	}

	//------------------- 点击事件处理（开始） --------------------------

	private BoardToolBar.OnListener toolBarListener = new BoardToolBar.OnListener() {};

	@Override
	public void onClick(View view)
	{
//		if (view.getId() == R.id.close)
//		{
//			if (!toClose())
//				finish();
//		}
		if (view.getId() == R.id.startRecord)
		{
			startRecord();
			holder.startRecord.setVisibility(View.GONE);
//			if (isRecording())
//				stopRecord();
//			else
//				startRecord();
		}
//		else if (view.getId() == R.id.result)
//		{
//			String filepath;
//
//			String cameraDir = String.format("%s/DCIM/Camera", FsUtils.SD_CARD);
//			new File(cameraDir).mkdirs();
//
//			Date date = new Date();
//			for (int i = 0; ; i++)
//			{
//				if (i == 0)
//					filepath = String.format("%s/RECORD_%s.mp4", cameraDir, new SimpleDateFormat("yyyyMMdd_HHmmss").format(date));
//				else
//					filepath = String.format("%s/RECORD_%s_%d.mp4", cameraDir, new SimpleDateFormat("yyyyMMdd_HHmmss").format(date), i);
//				if (!new File(filepath).exists())
//					break;
//			}
//
//			FsUtils.copy(Config.tmpMp4File, new File(filepath));
//
//			Intent intent = new Intent();
//			intent.putExtra("path", filepath);
//			setResult(Activity.RESULT_OK, intent);
//			finish();
//		}
	}

	//------------------- 点击事件处理（结束） --------------------------

	//------------------- 录制相关（开始） --------------------------

	public static final int CAPTURE_CODE = 0x123;

	private MediaProjection mediaProjection = null;
	private VirtualDisplay virtualDisplay = null;
	private MediaRecorder mMediaRecorder = null;

	private boolean isRecording()
	{
		return mediaProjection != null;
	}

	private void startRecord()
	{
		MediaProjectionManager projectionManager = (MediaProjectionManager) getSystemService(Context.MEDIA_PROJECTION_SERVICE);
		Intent intent = projectionManager.createScreenCaptureIntent();
		startActivityForResult(intent, CAPTURE_CODE);
	}

	private void stopRecord()
	{
		if (isRecording())
		{
			try
			{
				if (virtualDisplay != null)
				{
					virtualDisplay.release();
					virtualDisplay = null;
				}
				if (mMediaRecorder != null)
				{
					mMediaRecorder.stop();
					mMediaRecorder.release();
					mMediaRecorder = null;
				}
				if (mediaProjection != null)
				{
					mediaProjection.stop();
					mediaProjection = null;
				}
			}
			catch (Exception e)
			{
				LOG.v("停止录制异常");
				e.printStackTrace();
				LOG.toast(context, "停止录制异常");
			}
//			holder.startRecord.removeCallbacks(tickRunnable);
//			holder.startRecord.setText("重新录制");
//			holder.result.setVisibility(View.VISIBLE);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == CAPTURE_CODE)
		{
			if (resultCode == RESULT_OK)
			{
				MediaProjectionManager projectionManager = (MediaProjectionManager) getSystemService(Context.MEDIA_PROJECTION_SERVICE);
				mediaProjection = projectionManager.getMediaProjection(resultCode, data);
				if (mediaProjection != null)
				{
					FsUtils.delete(Config.tmpMp4File);

					DisplayMetrics metrics = new DisplayMetrics();
					getWindowManager().getDefaultDisplay().getMetrics(metrics);

					LOG.v("metrics.widthPixels : " + metrics.widthPixels);
					LOG.v("metrics.heightPixels : " + metrics.heightPixels);
					LOG.v("metrics.densityDpi : " + metrics.densityDpi);

					int width = metrics.widthPixels;
					int height = metrics.heightPixels;
					if (KitUtils.isG6())
					{
						width = 1728;
						height = 1080;
					}
					if (AppKit.OnlyId.equals("358520085983364_ce10171a39a04c3c0b7e"))
					{
						width = 2220;
						height = 1080;
					}

					mMediaRecorder = new MediaRecorder();

					mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.SURFACE);
					mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
					mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);

					mMediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
					mMediaRecorder.setVideoEncodingBitRate(6000000);
					mMediaRecorder.setVideoFrameRate(60);
					mMediaRecorder.setVideoSize(width, height);

					mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
					mMediaRecorder.setAudioEncodingBitRate(96000);
					mMediaRecorder.setAudioSamplingRate(44100);

					mMediaRecorder.setOutputFile(Config.tmpMp4File.getAbsolutePath());
					try
					{
						mMediaRecorder.prepare();
					}
					catch (IOException e)
					{
						e.printStackTrace();
					}
					virtualDisplay = mediaProjection.createVirtualDisplay(ActivityRecord.class.getSimpleName(), width, height, metrics.densityDpi, DisplayManager.VIRTUAL_DISPLAY_FLAG_PUBLIC, mMediaRecorder.getSurface(), null, null);
					mMediaRecorder.start();
//					timeBegin = System.currentTimeMillis();
//					holder.startRecord.post(tickRunnable);
//					holder.result.setVisibility(View.INVISIBLE);
				}
			}
		}
	}

//	private long getDuration()
//	{
//		return System.currentTimeMillis() - timeBegin;
//	}

//	private static String formatTime(long ms)
//	{
//		int second = (int) (ms / 1000);
//		int minute = second / 60;
//		second = second % 60;
//		int hour = minute / 60;
//		minute = minute % 60;
//		if (hour == 0)
//			return String.format("%02d:%02d", minute, second);
//		else
//			return String.format("%02d:%02d:%02d", hour, minute, second);
//	}

//	private long timeBegin;

//	private Runnable tickRunnable = new Runnable()
//	{
//		@Override
//		public void run()
//		{
////			LOG.v("tick");
//			try
//			{
//				holder.startRecord.setText(formatTime(getDuration()));
//			}
//			catch (Exception e)
//			{
//				e.printStackTrace();
//			}
//			holder.startRecord.postDelayed(this, 1000);
//		}
//	};

	//------------------- 录制相关（结束） --------------------------

}
