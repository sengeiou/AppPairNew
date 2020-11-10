package com.lys.activity;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.Settings;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import com.lys.App;
import com.lys.app.R;
import com.lys.base.activity.BaseActivity;
import com.lys.base.utils.CommonUtils;
import com.lys.base.utils.FsUtils;
import com.lys.base.utils.ImageLoader;
import com.lys.base.utils.LOG;
import com.lys.base.utils.SysUtils;
import com.lys.base.utils.VideoLoader;
import com.lys.config.AppConfig;
import com.lys.dialog.DialogKey;
import com.lys.kit.activity.KitActivity;
import com.lys.kit.config.Config;
import com.lys.kit.dialog.DialogMenu;
import com.lys.kit.dialog.DialogWait;
import com.lys.kit.module.OssHelper;
import com.lys.kit.utils.ImageLoad;
import com.lys.kit.utils.KitUtils;
import com.lys.kit.utils.Protocol;
import com.lys.player.utils.PlayerUtils;
import com.lys.protobuf.SHandleId;
import com.lys.protobuf.SRequest_ModifyHead;
import com.lys.protobuf.SResponse_ModifyHead;
import com.lys.protobuf.SUser;
import com.lys.receiver.AppReceiver;
import com.lys.utils.Helper;
import com.lys.utils.LysIM;

import java.util.List;

import io.rong.imkit.RongIM;
import io.rong.imlib.model.Conversation;

public class ActivityUser extends KitActivity implements View.OnClickListener
{
	public static final int RESULT_CODE_LOGOUT = 0x62;

	private class Holder
	{
		private ImageView head;
		private TextView name;
		private TextView schoolText;
		private TextView classText;
		private TextView accountText;
		private TextView wifiName;
		private TextView sleepTime;
		private SeekBar lightSeekBar;
		private Switch lightAutoSwitch;
		private TextView cacheSize;
		private TextView space;
		private TextView deviceNumber;
		private TextView versionCode;
		private Switch debugSwitch;
	}

	private Holder holder = new Holder();

	private void initHolder()
	{
		holder.head = findViewById(R.id.head);
		holder.name = findViewById(R.id.name);
		holder.schoolText = findViewById(R.id.schoolText);
		holder.classText = findViewById(R.id.classText);
		holder.accountText = findViewById(R.id.accountText);
		holder.wifiName = findViewById(R.id.wifiName);
		holder.sleepTime = findViewById(R.id.sleepTime);
		holder.lightSeekBar = findViewById(R.id.lightSeekBar);
		holder.lightAutoSwitch = findViewById(R.id.lightAutoSwitch);
		holder.cacheSize = findViewById(R.id.cacheSize);
		holder.space = findViewById(R.id.space);
		holder.deviceNumber = findViewById(R.id.deviceNumber);
		holder.versionCode = findViewById(R.id.versionCode);
		holder.debugSwitch = findViewById(R.id.debugSwitch);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user);
		initHolder();
		requestPermission();
	}

	@Override
	public void permissionSuccess()
	{
		super.permissionSuccess();

		findViewById(R.id.head).setOnClickListener(this);
		findViewById(R.id.modifyPswCon).setOnClickListener(this);
		findViewById(R.id.wifiCon).setOnClickListener(this);
		findViewById(R.id.sleepCon).setOnClickListener(this);
		findViewById(R.id.clearCon).setOnClickListener(this);
		findViewById(R.id.optimizeCon).setOnClickListener(this);
		findViewById(R.id.clearAllMsgCon).setOnClickListener(this);
		findViewById(R.id.logoutCon).setOnClickListener(this);

//		if (App.isStudent())
//		{
//			findViewById(R.id.logoutCon).setVisibility(View.GONE);
//			findViewById(R.id.logoutLine).setVisibility(View.GONE);
//		}

		holder.head.setOnLongClickListener(new View.OnLongClickListener()
		{
			@Override
			public boolean onLongClick(View view)
			{
				DialogKey.show(context, new DialogKey.OnKeyListener()
				{
					@Override
					public void onKey(String key)
					{
						if (key.equals("89178246"))
							SysUtils.openSetting(context);
						else
							LOG.toast(context, "key错误");
					}
				});
				return true;
			}
		});

		ImageLoad.displayImage(context, App.head(), holder.head, R.drawable.img_default_head, null);
		holder.name.setText(App.name());

		holder.schoolText.setText("xxxxxxxxxxxxx");
		holder.classText.setText("");
		holder.accountText.setText(AppConfig.readAccount());

		holder.cacheSize.setText(String.format("%s", CommonUtils.formatSize(FsUtils.getSize(ImageLoader.getCacheDir(context)) + FsUtils.getSize(VideoLoader.getCacheDir(context)))));

		if (KitUtils.isD7())
		{
			findViewById(R.id.sleepCon).setVisibility(View.VISIBLE);
			findViewById(R.id.sleepLine).setVisibility(View.VISIBLE);
			updateSleepTime();
		}
		else
		{
			findViewById(R.id.sleepCon).setVisibility(View.GONE);
			findViewById(R.id.sleepLine).setVisibility(View.GONE);
		}

		holder.lightSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
		{
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
			{
				if (fromUser)
				{
					PlayerUtils.setBrightness(context, progress * PlayerUtils.MAX_BRIGHTNESS / 1000);
				}
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar)
			{

			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar)
			{
				int brightness = holder.lightSeekBar.getProgress() * PlayerUtils.MAX_BRIGHTNESS / 1000;
				LOG.v("onStopTrackingTouch brightness=" + brightness);
				PlayerUtils.setBrightness(context, brightness);
			}
		});

		holder.lightAutoSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
		{
			@Override
			public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked)
			{
				if (isChecked)
					PlayerUtils.setBrightnessMode(context, Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC);
				else
					PlayerUtils.setBrightnessMode(context, Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
			}
		});

		holder.debugSwitch.setChecked(Config.isDebug());
		holder.debugSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
		{
			@Override
			public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked)
			{
				Config.setDebug(isChecked);
				LOG.logfile = Config.isDebug() ? String.format("%s/log_%s.txt", FsUtils.SD_CARD, getPackageName()) : null;
			}
		});

		IntentFilter filter = new IntentFilter();
		filter.addAction(AppReceiver.Action_netChange(context));
		registerReceiver(mReceiver, filter);

		long total = Environment.getExternalStorageDirectory().getTotalSpace();
		long free = Environment.getExternalStorageDirectory().getFreeSpace();
		holder.space.setText(String.format("总共：%s，剩余：%s", CommonUtils.formatSize(total), CommonUtils.formatSize(free)));

		holder.deviceNumber.setText(App.OnlyId);

		PackageInfo packageInfo = SysUtils.getPackageInfo(context, getPackageName());
		holder.versionCode.setText(String.format("%s（%s）%s", packageInfo.versionName, packageInfo.versionCode, SysUtils.isDebug() ? "（开发版）" : ""));

		updateNetState();
	}

	@Override
	protected void onResume()
	{
		super.onResume();

		holder.lightAutoSwitch.setChecked(PlayerUtils.getBrightnessMode(context) == Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC);

		LOG.v("getBrightness=" + PlayerUtils.getBrightness(context));
		holder.lightSeekBar.setProgress(PlayerUtils.getBrightness(context) * 1000 / PlayerUtils.MAX_BRIGHTNESS);
	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		unregisterReceiver(mReceiver);
	}

	@Override
	public void onClick(View view)
	{
		if (view.getId() == R.id.head)
		{
			DialogMenu.show(context, new DialogMenu.OnClickListener()
			{
				@Override
				public void onClick(int which)
				{
					switch (which)
					{
					case 0:
						camera(new BaseActivity.OnImageListener()
						{
							@Override
							public void onResult(String filepath)
							{
								gotImage(filepath);
							}
						});
						break;
					case 1:
						selectCustomImage(new BaseActivity.OnImageListener()
						{
							@Override
							public void onResult(String filepath)
							{
								if (filepath.startsWith(FsUtils.SD_CARD))
									gotImage(filepath);
								else
									LOG.toast(context, "不支持插图");
							}
						});
						break;
					}
				}
			}, "拍照", "选择");
		}
		else if (view.getId() == R.id.modifyPswCon)
		{
		}
		else if (view.getId() == R.id.wifiCon)
		{
			SysUtils.openWifiSetting(context);
		}
		else if (view.getId() == R.id.sleepCon)
		{
			openSleepDialog();
		}
		else if (view.getId() == R.id.clearCon)
		{
			doClear();
		}
		else if (view.getId() == R.id.optimizeCon)
		{
			doOptimize();
		}
		else if (view.getId() == R.id.clearAllMsgCon)
		{
			doClearAllMsgCon();
		}
		else if (view.getId() == R.id.logoutCon)
		{
			if (App.isStudent())
			{
				DialogKey.show(context, new DialogKey.OnKeyListener()
				{
					@Override
					public void onKey(String key)
					{
						if (key.equals("89178246"))
							doLogout();
						else
							LOG.toast(context, "key错误");
					}
				});
			}
			else
			{
				doLogout();
			}
		}
//		if (view.getId() == R.id.clearSysMsg)
//		{
//			RongIM.getInstance().clearMessages(Conversation.ConversationType.SYSTEM, "root", new RongIMClient.ResultCallback<Boolean>()
//			{
//				@Override
//				public void onSuccess(Boolean success)
//				{
//					LOG.toast(context, "删除成功");
//				}
//
//				@Override
//				public void onError(RongIMClient.ErrorCode errorCode)
//				{
//					LOG.toast(context, "删除失败：" + errorCode);
//				}
//			});
//		}
//		else if (view.getId() == R.id.getSysMsg)
//		{
//			RongIM.getInstance().getHistoryMessages(Conversation.ConversationType.SYSTEM, "root", -1, 10, new RongIMClient.ResultCallback<List<Message>>()
//			{
//				@Override
//				public void onSuccess(List<Message> messages)
//				{
//					LOG.v("messages.size() = " + messages.size());
//				}
//
//				@Override
//				public void onError(RongIMClient.ErrorCode errorCode)
//				{
//					LOG.toast(context, "获取失败：" + errorCode);
//				}
//			});
//		}
	}

	private void doLogout()
	{
		AppConfig.deletePsw();
		App.setUser(null);
		LysIM.instance().logout();
		Intent intent = new Intent();
		setResult(RESULT_CODE_LOGOUT, intent);
		finish();
//		android.os.Process.killProcess(android.os.Process.myPid());
//		System.exit(0);
	}

	private void gotImage(String filepath)
	{
		cropCustomHead(filepath, new BaseActivity.OnImageListener()
		{
			@Override
			public void onResult(String filepath)
			{
				Bitmap bitmap = CommonUtils.readBitmap(filepath, 300);
				CommonUtils.saveBitmap(bitmap, Config.tmpJpgFile);
				bitmap.recycle();
				OssHelper.instance().doUploadMd5FileWithProgress(OssHelper.ZjykFile, Config.tmpJpgFile, OssHelper.DirHead(), new OssHelper.OnProgressListener()
				{
					@Override
					public void onProgress(long currentSize, long totalSize)
					{
						LOG.v(String.format("currentSize = %s, totalSize = %s", currentSize, totalSize));
					}

					@Override
					public void onSuccess(final String url)
					{
						Config.tmpJpgFile.delete();

						SRequest_ModifyHead request = new SRequest_ModifyHead();
						request.userId = App.userId();
						request.head = url;
						Protocol.doPost(context, App.getApi(), SHandleId.ModifyHead, request.saveToStr(), new Protocol.OnCallback()
						{
							@Override
							public void onResponse(int code, String data, String msg)
							{
								if (code == 200)
								{
									SResponse_ModifyHead response = SResponse_ModifyHead.load(data);
									ImageLoad.displayImage(context, url, holder.head, R.drawable.img_default_head, new ImageLoader.OnDisplay()
									{
										@Override
										public void success(Bitmap bitmap, String url)
										{
											App.setHead(url);

											Intent intent = new Intent();
											intent.setAction(ActivityMain.Action_modifyHead(context));
											sendBroadcast(intent);

											LysIM.instance().refreshUserInfoCache(App.getUser());

											LOG.toast(context, "设置成功");
										}
									});
								}
							}
						});
					}

					@Override
					public void onFail()
					{
						Config.tmpJpgFile.delete();
						LOG.toast(context, "上传失败");
					}
				});
			}
		});
	}

	private void updateSleepTime()
	{
		switch (Config.getSleepTime(context))
		{
		case Config.SleepTime_forever:
			holder.sleepTime.setText("永不休眠");
			break;
		case Config.SleepTime_15Seconds:
			holder.sleepTime.setText("无操作15秒后");
			break;
		case Config.SleepTime_30Seconds:
			holder.sleepTime.setText("无操作30秒后");
			break;
		case Config.SleepTime_1Minutes:
			holder.sleepTime.setText("无操作1分钟后");
			break;
		case Config.SleepTime_2Minutes:
			holder.sleepTime.setText("无操作2分钟后");
			break;
		case Config.SleepTime_5Minutes:
			holder.sleepTime.setText("无操作5分钟后");
			break;
		case Config.SleepTime_10Minutes:
			holder.sleepTime.setText("无操作10分钟后");
			break;
		case Config.SleepTime_30Minutes:
			holder.sleepTime.setText("无操作30分钟后");
			break;
		}
	}

	private void openSleepDialog()
	{
		DialogMenu.show(context, new DialogMenu.OnClickListener()
		{
			@Override
			public void onClick(int which)
			{
				switch (which)
				{
				case 0:
					Config.setSleepTime(context, Config.SleepTime_forever);
					break;
				case 1:
					Config.setSleepTime(context, Config.SleepTime_15Seconds);
					break;
				case 2:
					Config.setSleepTime(context, Config.SleepTime_30Seconds);
					break;
				case 3:
					Config.setSleepTime(context, Config.SleepTime_1Minutes);
					break;
				case 4:
					Config.setSleepTime(context, Config.SleepTime_2Minutes);
					break;
				case 5:
					Config.setSleepTime(context, Config.SleepTime_5Minutes);
					break;
				case 6:
					Config.setSleepTime(context, Config.SleepTime_10Minutes);
					break;
				case 7:
					Config.setSleepTime(context, Config.SleepTime_30Minutes);
					break;
				}
				updateSleepTime();
			}
		}, "永不休眠", "15秒", "30秒", "1分钟", "2分钟", "5分钟", "10分钟", "30分钟");
	}

	private void doClear()
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("确定要清除缓存吗？");
		builder.setNeutralButton("取消", null);
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialogInterface, int which)
			{
				DialogWait.show(context, "清除中。。。");
				final Handler handler = new Handler()
				{
					public void handleMessage(android.os.Message message)
					{
						holder.cacheSize.setText(String.format("%s", CommonUtils.formatSize(FsUtils.getSize(ImageLoader.getCacheDir(context)) + FsUtils.getSize(VideoLoader.getCacheDir(context)))));
						LOG.toast(context, "清除完成！");
					}
				};
				new Thread(new Runnable()
				{
					@Override
					public void run()
					{
//						List<String> list = Config.getClearDir(context);
//						for (String dir : list)
//						{
//							DialogWait.message("清除：" + dir.substring(FsUtils.SD_CARD.length()));
//							FsUtils.delete(new File(dir));
//						}
						FsUtils.delete(ImageLoader.getCacheDir(context));
						FsUtils.delete(VideoLoader.getCacheDir(context));
						DialogWait.close();
						handler.sendEmptyMessage(0);
					}
				}).start();
			}
		});
		builder.show();
	}

	private void doOptimize()
	{
		DialogKey.show(context, new DialogKey.OnKeyListener()
		{
			@Override
			public void onKey(String key)
			{
				if (key.equals(Config.key))
				{
//					Intent intent = new Intent(context, ActivityOptimize.class);
//					startActivity(intent);
				}
				else
				{
					LOG.toast(context, "key错误");
				}
			}
		});
	}

	private void doClearAllMsgCon()
	{
		DialogKey.show(context, new DialogKey.OnKeyListener()
		{
			@Override
			public void onKey(String key)
			{
				if (key.equals(Config.key))
				{
					RongIM.getInstance().clearMessages(Conversation.ConversationType.SYSTEM, "root", null);
					Helper.requestFriendList(context, App.userId(), new Helper.OnUserListCallback()
					{
						@Override
						public void onResult(List<SUser> users)
						{
							for (SUser user : users)
							{
								RongIM.getInstance().clearMessages(Conversation.ConversationType.PRIVATE, user.id, null);
							}
							LOG.toast(context, "清除完成");
						}
					});
				}
				else
				{
					LOG.toast(context, "key错误");
				}
			}
		});
	}

	// 更新网络状态
	private void updateNetState()
	{
		ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		LOG.v("networkInfo:" + networkInfo);
		if (networkInfo.isConnected())
			holder.wifiName.setText(networkInfo.getExtraInfo());
		else
			holder.wifiName.setText("未连接");
	}

	private BroadcastReceiver mReceiver = new BroadcastReceiver()
	{
		@Override
		public void onReceive(Context context, Intent intent)
		{
			LOG.v("Action:" + intent.getAction());
			if (intent.getAction().equals(AppReceiver.Action_netChange(context)))
			{
				updateNetState();
			}
		}
	};

}
