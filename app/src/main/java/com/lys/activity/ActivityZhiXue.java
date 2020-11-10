package com.lys.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.media.SoundPool;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.DownloadListener;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.king.zxing.CaptureActivity;
import com.lys.App;
import com.lys.app.R;
import com.lys.base.utils.CommonUtils;
import com.lys.base.utils.FsUtils;
import com.lys.base.utils.HttpUtils;
import com.lys.base.utils.JsonHelper;
import com.lys.base.utils.LOG;
import com.lys.base.utils.LOGJson;
import com.lys.base.utils.SysUtils;
import com.lys.kit.AppKit;
import com.lys.kit.dialog.DialogAlert;
import com.lys.kit.dialog.DialogMenu;
import com.lys.kit.dialog.DialogWait;
import com.lys.kit.utils.Protocol;
import com.lys.plugin.AndroidForJs;
import com.lys.plugin.WebMsgProcess;
import com.lys.protobuf.SHandleId;
import com.lys.protobuf.SRequest_FindTask;
import com.lys.protobuf.SRequest_GetConfig;
import com.lys.protobuf.SRequest_ZXCatchOver;
import com.lys.protobuf.SRequest_ZXCreateTask;
import com.lys.protobuf.SRequest_ZXProcessJuan;
import com.lys.protobuf.SRequest_ZXProcessJuan2;
import com.lys.protobuf.SRequest_ZXPullAccount;
import com.lys.protobuf.SRequest_ZXPullTask;
import com.lys.protobuf.SRequest_ZXReportAccount;
import com.lys.protobuf.SRequest_ZXTickInfo;
import com.lys.protobuf.SResponse_FindTask;
import com.lys.protobuf.SResponse_GetConfig;
import com.lys.protobuf.SResponse_ZXCatchOver;
import com.lys.protobuf.SResponse_ZXCreateTask;
import com.lys.protobuf.SResponse_ZXProcessJuan;
import com.lys.protobuf.SResponse_ZXProcessJuan2;
import com.lys.protobuf.SResponse_ZXPullAccount;
import com.lys.protobuf.SResponse_ZXPullTask;
import com.lys.protobuf.SResponse_ZXReportAccount;
import com.lys.protobuf.SResponse_ZXTickInfo;
import com.lys.protobuf.SSohuIp;
import com.lys.protobuf.SZXChapterTree;
import com.lys.protobuf.SZXKnowledgeTree;
import com.lys.utils.AssetsHelper;
import com.lys.utils.LysUpload;
import com.lys.view.ZhiXueWebView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ActivityZhiXue extends AppActivity implements View.OnClickListener
{
	public static final int ScanForInstall = 0x228;

	private class Holder
	{
		private TextView host;
		private TextView batteryText;
		private TextView tickTime;

		private TextView pullAccount;

//		private LogWatch logWatch;

//		private RadioGroup gradeGroup;
//		private RadioGroup styleGroup;
//		private RadioGroup diffGroup;
	}

	private Holder holder = new Holder();

	private void initHolder()
	{
		holder.host = findViewById(R.id.host);
		holder.batteryText = findViewById(R.id.batteryText);
		holder.tickTime = findViewById(R.id.tickTime);

		holder.pullAccount = findViewById(R.id.pullAccount);

//		holder.logWatch = findViewById(R.id.logWatch);

//		holder.gradeGroup = findViewById(R.id.gradeGroup);
//		holder.styleGroup = findViewById(R.id.styleGroup);
//		holder.diffGroup = findViewById(R.id.diffGroup);
	}

//	private String grade;
//	private String style;
//	private String diff;

	private String logicJs;

	private SoundPool mSoundPool;
	private int ding;

	private String mUrl;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_zhixue_juan);

		initHolder();
		initWebView();

		requestPermission();
	}

	@Override
	public void permissionSuccess()
	{
		super.permissionSuccess();

		findViewById(R.id.openZhiHu).setOnClickListener(this);
		findViewById(R.id.startCatch).setOnClickListener(this);
		findViewById(R.id.srandAccound).setOnClickListener(this);

		findViewById(R.id.setting).setOnClickListener(this);
		findViewById(R.id.start).setOnClickListener(this);
		findViewById(R.id.genTask).setOnClickListener(this);
		findViewById(R.id.catchKnowledge).setOnClickListener(this);
		findViewById(R.id.catchChapter).setOnClickListener(this);
		findViewById(R.id.test).setOnClickListener(this);
		findViewById(R.id.pullAccount).setOnClickListener(this);
		findViewById(R.id.masterAccount).setOnClickListener(this);
		findViewById(R.id.lockAccount).setOnClickListener(this);

		findViewById(R.id.maskBtn).setOnClickListener(this);
		findViewById(R.id.logBtn).setOnClickListener(this);

//		LOG.mListener = new LOG.OnLOGListener()
//		{
//			@Override
//			public void onLog(String msg)
//			{
//				holder.logWatch.addData(msg);
//			}
//		};

		if (!SysUtils.isDebug())
		{
			findViewById(R.id.setting).setVisibility(View.GONE);
			findViewById(R.id.catchKnowledge).setVisibility(View.GONE);
			findViewById(R.id.catchChapter).setVisibility(View.GONE);
		}

//		holder.gradeGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
//		{
//			@Override
//			public void onCheckedChanged(RadioGroup radioGroup, int checkedId)
//			{
//				RadioButton radioButton = radioGroup.findViewById(checkedId);
//				grade = radioButton.getText().toString();
//			}
//		});
//
//		holder.styleGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
//		{
//			@Override
//			public void onCheckedChanged(RadioGroup radioGroup, int checkedId)
//			{
//				RadioButton radioButton = radioGroup.findViewById(checkedId);
//				style = radioButton.getText().toString();
//			}
//		});
//
//		holder.diffGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
//		{
//			@Override
//			public void onCheckedChanged(RadioGroup radioGroup, int checkedId)
//			{
//				RadioButton radioButton = radioGroup.findViewById(checkedId);
//				diff = radioButton.getText().toString();
//			}
//		});

		updateBattery();

		SoundPool.Builder builder = new SoundPool.Builder();
		mSoundPool = builder.build();

		ding = mSoundPool.load(context, R.raw.ding, 1);

		App.requestHost(context, 0, new App.OnHostCallback()
		{
			@Override
			public void onResult(String host)
			{
				if (!TextUtils.isEmpty(host))
				{
					holder.host.setText(host);

					SRequest_GetConfig request = new SRequest_GetConfig();
					Protocol.doPost(context, host, SHandleId.GetConfig, request.saveToStr(), new Protocol.OnCallback()
					{
						@Override
						public void onResponse(int code, String data, String msg)
						{
							if (code == 200)
							{
								SResponse_GetConfig response = SResponse_GetConfig.load(data);
								App.TimeOffset = System.currentTimeMillis() - response.time;
								App.setConfig(response);

								checkUpdate();

								HttpUtils.doHttpGet(context, App.getConfig().root + "/js/zhixue_juan.js", new HttpUtils.OnCallback()
								{
									@Override
									public void onResponse(String strJs)
									{
										logicJs = strJs;
										mWebView.loadUrl("https://www.zhixue.com/login.html");
									}
								});
							}
						}
					});
				}
				else
				{
					LOG.toast(context, "网络错误");
				}
			}
		});

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == ScanForInstall)
		{
			if (resultCode == RESULT_OK)
			{
				Bundle bundle = data.getExtras();
				String url = bundle.getString(CaptureActivity.KEY_RESULT);
				doInstall(url);
			}
		}
	}

	private void doInstall(String url)
	{
		String localPath = String.format("%s/tmp.apk", FsUtils.SD_CARD);
		final File file = new File(localPath);

		final ProgressDialog progressDialog = new ProgressDialog(context);
		progressDialog.setMax(20 * 1024 * 1024);
		progressDialog.setTitle("准备下载。。。");
		progressDialog.setCancelable(false);
		progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		progressDialog.setIndeterminate(false);
		progressDialog.show();
		HttpUtils.download(context, url, file, new HttpUtils.OnDownloadListener()
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
			}
		});
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if (mWebView.canGoBack())
		{
			mWebView.goBack();
			return true;
		}
		else
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
	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		if (mWebView != null)
			mWebView.destroy();
		waitHandler.removeCallbacks(waitRunnable);
		mSoundPool.release();
	}

	private void updateBattery()
	{
		BatteryManager batteryManager = (BatteryManager) getSystemService(BATTERY_SERVICE);
		int capacity = batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);
		holder.batteryText.setText("电量：" + capacity + "%");
	}

	public static void wakeUpAndUnlock(Context context)
	{
		PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
		boolean screenOn = pm.isInteractive();
		LOG.v("screenOn : " + screenOn);
		if (!screenOn)
		{
			PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "bright");
			wl.acquire(10000); // 点亮屏幕
			wl.release(); // 释放

			// 屏幕解锁
//			KeyguardManager keyguardManager = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
//			KeyguardManager.KeyguardLock keyguardLock = keyguardManager.newKeyguardLock("unLock");
//			keyguardLock.reenableKeyguard();
//			keyguardLock.disableKeyguard();
		}
	}

	private long heartTime = 0;

	private int tickCount = 0;

	private boolean keepScreen = false;

	private Handler waitHandler = new Handler();
	private Runnable waitRunnable = new Runnable()
	{
		@Override
		public void run()
		{
			tickCount++;

			BatteryManager batteryManager = (BatteryManager) getSystemService(BATTERY_SERVICE);
			final int capacity = batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);

			long currTime = System.currentTimeMillis();
			final long dtTime = currTime - heartTime;
			holder.tickTime.setText((keepScreen ? "K : " : "") + CommonUtils.formatTime(dtTime) + (App.getConfig().zhixueErrorMode ? " - E" : ""));

			int second = (int) (dtTime / 1000);

			boolean warning = false;

			if (dtTime > 30 * 60 * 1000)
			{
				if (second % 60 == 0)
					mWebView.setKeepScreenOn(false);
			}
			else
			{
				if (tickCount % 30 == 1)
				{
					HttpUtils.doHttpGet(context, "http://pv.sohu.com/cityjson?ie=utf-8", new HttpUtils.OnCallback()
					{
						@Override
						public void onResponse(String jsonStr)
						{
							if (!TextUtils.isEmpty(jsonStr))
							{
								PackageInfo packageInfo = SysUtils.getPackageInfo(context, getPackageName());

								SRequest_ZXTickInfo request = new SRequest_ZXTickInfo();
								request.deviceId = AppKit.OnlyId;
								request.hostIp = SysUtils.getHostIP();
								request.netIp = SSohuIp.load(jsonStr.substring(jsonStr.indexOf("{"), jsonStr.lastIndexOf("}") + 1));
								request.versionCode = packageInfo.versionCode;
								request.capacity = capacity;
								request.dtTime = dtTime;
								Protocol.doPost(context, App.getApi(), SHandleId.ZXTickInfo, request.saveToStr(), new Protocol.OnCallback()
								{
									@Override
									public void onResponse(int code, String data, String msg)
									{
										if (code == 200)
										{
											SResponse_ZXTickInfo response = SResponse_ZXTickInfo.load(data);
											keepScreen = response.keepScreen;
										}
										else
										{
											keepScreen = false;
										}
										LOG.v("----> get keep info is " + keepScreen);
										mWebView.setKeepScreenOn(keepScreen);
										if (keepScreen)
										{
											wakeUpAndUnlock(context);
										}
									}
								});
							}
						}
					});
				}
			}

			if (dtTime > 7 * 60 * 1000)
			{
				holder.tickTime.setTextColor(0xffff0000);
				if (dtTime < 8 * 60 * 1000)
				{
					if (!warning)
					{
						if (second % 3 == 0)
						{
							mSoundPool.play(ding, 1, 1, 0, 0, 1);
							wakeUpAndUnlock(context);
						}
						warning = true;
					}
				}
			}
			else
			{
				holder.tickTime.setTextColor(0xff00ff00);
			}

			holder.batteryText.setText("电量：" + capacity + "%");
			if (capacity < 10)
			{
				holder.batteryText.setTextColor(0xffff0000);
				if (!warning)
				{
					if (second % 6 == 0)
					{
						mSoundPool.play(ding, 1, 1, 0, 0, 1);
						wakeUpAndUnlock(context);
					}
					warning = true;
				}
			}
			else
			{
				holder.batteryText.setTextColor(0xff00ff00);
			}

			waitHandler.postDelayed(waitRunnable, 1000);
		}
	};

	public void tick()
	{
		heartTime = System.currentTimeMillis();
	}

	@Override
	public void onClick(View view)
	{
		if (view.getId() == R.id.startCatch)
		{
			if (mUrl.startsWith("https://www.zhixue.com/paperfresh/dist/#/mylib/paperEdit/"))
			{
				loadJs("MyNnd_GetNamePhase2();", new ValueCallback<String>()
				{
					@Override
					public void onReceiveValue(String resultStr)
					{
						if (TextUtils.isEmpty(resultStr) || resultStr.equals("null"))
							return;

						JSONObject result = JsonHelper.getJSONObject(resultStr);
						final String name = result.getString("name");
						final String phase = result.getString("phase");
						final String subject = result.getString("subject");

						SRequest_FindTask request = new SRequest_FindTask();
						request.group = String.format("%s%s", phase, subject);
						request.name = name;
						Protocol.doPost(context, App.getApi(), SHandleId.FindTask, request.saveToStr(), new Protocol.OnCallback()
						{
							@Override
							public void onResponse(int code, String data, String msg)
							{
								if (code == 200)
								{
									SResponse_FindTask response = SResponse_FindTask.load(data);
									if (response.task != null)
									{
										DialogAlert.show(context, "该卷已经抓取过（可能未抓取成功），是否重新抓取？", null, new DialogAlert.OnClickListener()
										{
											@Override
											public void onClick(int which)
											{
												if (which == 1)
												{
													DialogWait.show(context, "开始抓取。。。");
													loadJs("MyNnd_CatchOnePage2();");
												}
											}
										}, "取消", "重新抓取");
									}
									else
									{
										DialogWait.show(context, "开始抓取。。。");
										loadJs("MyNnd_CatchOnePage2();");
									}
								}
							}
						});
					}
				});
			}
			else
			{
				loadJs("MyNnd_GetNamePhase();", new ValueCallback<String>()
				{
					@Override
					public void onReceiveValue(String resultStr)
					{
						if (TextUtils.isEmpty(resultStr) || resultStr.equals("null"))
							return;

						JSONObject result = JsonHelper.getJSONObject(resultStr);
						final String name = result.getString("name");
						final String phase = result.getString("phase");
						final String subject = result.getString("subject");

						SRequest_FindTask request = new SRequest_FindTask();
						request.group = String.format("%s%s", phase, subject);
						request.name = name;
						Protocol.doPost(context, App.getApi(), SHandleId.FindTask, request.saveToStr(), new Protocol.OnCallback()
						{
							@Override
							public void onResponse(int code, String data, String msg)
							{
								if (code == 200)
								{
									SResponse_FindTask response = SResponse_FindTask.load(data);
									if (response.task != null)
									{
										DialogAlert.show(context, "该卷已经抓取过（可能未抓取成功），是否重新抓取？", null, new DialogAlert.OnClickListener()
										{
											@Override
											public void onClick(int which)
											{
												if (which == 1)
												{
													DialogWait.show(context, "开始抓取。。。");
													loadJs("MyNnd_CatchOnePage();");
												}
											}
										}, "取消", "重新抓取");
									}
									else
									{
										DialogWait.show(context, "开始抓取。。。");
										loadJs("MyNnd_CatchOnePage();");
									}
								}
							}
						});
					}
				});
			}
		}
		else if (view.getId() == R.id.srandAccound)
		{
			SRequest_ZXPullAccount request = new SRequest_ZXPullAccount();
			request.deviceId = AppKit.OnlyId;
			Protocol.doPost(context, App.getApi(), SHandleId.ZXPullAccount, request.saveToStr(), new Protocol.OnCallback()
			{
				@Override
				public void onResponse(int code, String data, String msg)
				{
					if (code == 200)
					{
						SResponse_ZXPullAccount response = SResponse_ZXPullAccount.load(data);
						holder.pullAccount.setText(response.account);
						loadJs(String.format("MyNnd_SetAccount('%s', '%s');", //
								response.account, //
								response.psw));
					}
				}
			});
		}
		else if (view.getId() == R.id.setting)
		{
			SysUtils.openSetting(context);
		}
		else if (view.getId() == R.id.start)
		{
			DialogWait.show(context, "开始抓取。。。");
			loadJs("MyNnd_CatchOnePage();");
		}
		else if (view.getId() == R.id.genTask)
		{
			loadJs("MyNnd_TryCatchProblems();");
		}
		else if (view.getId() == R.id.catchKnowledge)
		{
			loadJs("MyNnd_ClickKnowledge();");
		}
		else if (view.getId() == R.id.catchChapter)
		{
			loadJs("MyNnd_CatchChapter();");
		}
		else if (view.getId() == R.id.test)
		{
//			loadJs("MyNnd_Test('');");
//			loadJs("MyNnd_Test('xxxxx');");
//			loadJs("MyNnd_Test('<!--选修1-1--><!--第1章 常用逻辑用语--><!--1.1 命题及其关系--><!--1.1.1 命题的概念和例子-->');");
//			loadJs("MyNnd_Test('<!--必修第五册--><!--第13章 概率--><!--13.1 试验与事件--><!--13.1.2 事件的运算-->');");
//			loadJs("MyNnd_Test('<!--必修第五册--><!--第13章 概率--><!--13.3 频率与概率-->');");
			if (false)
			{
				HttpUtils.doHttpGet(context, App.getConfig().root + "/js/test.js", new HttpUtils.OnCallback()
				{
					@Override
					public void onResponse(String jsonStr)
					{
						LOG.v(jsonStr);
						loadJs(jsonStr);
					}
				});
			}
			else
			{
				Intent intent = new Intent(context, CaptureActivityLandscape.class);
				startActivityForResult(intent, ScanForInstall);
			}
//			captureViewToFiles(mWebView, String.format("%s/catch_zhixue/章节.%05d", FsUtils.SD_CARD, 1));
		}
		else if (view.getId() == R.id.maskBtn)
		{
			if (findViewById(R.id.mask).getVisibility() == View.VISIBLE)
			{
				hideMask();
			}
			else
			{
				showMask();
			}
		}
		else if (view.getId() == R.id.logBtn)
		{
//			if (holder.logWatch.getVisibility() == View.VISIBLE)
//			{
//				holder.logWatch.setVisibility(View.GONE);
//			}
//			else
//			{
//				holder.logWatch.setVisibility(View.VISIBLE);
//				holder.logWatch.scrollToBottom();
//			}
		}
		else if (view.getId() == R.id.pullAccount)
		{
			SRequest_ZXPullAccount request = new SRequest_ZXPullAccount();
			request.deviceId = AppKit.OnlyId;
			Protocol.doPost(context, App.getApi(), SHandleId.ZXPullAccount, request.saveToStr(), new Protocol.OnCallback()
			{
				@Override
				public void onResponse(int code, String data, String msg)
				{
					if (code == 200)
					{
						SResponse_ZXPullAccount response = SResponse_ZXPullAccount.load(data);
						holder.pullAccount.setText(response.account);
						loadJs(String.format("MyNnd_SetAccount('%s', '%s');", //
								response.account, //
								response.psw));
					}
				}
			});
		}
		else if (view.getId() == R.id.masterAccount)
		{
			reportAccount("master");
		}
		else if (view.getId() == R.id.lockAccount)
		{
			reportAccount("lock");
		}
		else if (view.getId() == R.id.openZhiHu)
		{
			DialogMenu.Builder builder = new DialogMenu.Builder(context);
			builder.setMenu("加载问题", new DialogMenu.OnClickMenuListener()
			{
				@Override
				public void onClick()
				{
					mWebView.loadUrl("https://www.zhihu.com/question/47351966");
				}
			});
			builder.setMenu("保存网页", new DialogMenu.OnClickMenuListener()
			{
				@Override
				public void onClick()
				{
					mWebView.saveWebArchive(FsUtils.SD_CARD + "/aaabbb.mht");
				}
			});
			builder.setMenu("测试", new DialogMenu.OnClickMenuListener()
			{
				@Override
				public void onClick()
				{
					loadJs("MyNnd_ZhiHu();");
				}
			});
			builder.setMenu("scrollToBottom", new DialogMenu.OnClickMenuListener()
			{
				@Override
				public void onClick()
				{
					mWebView.scrollToBottom();
				}
			});
			builder.show();
		}
	}

	private void reportAccount(String state)
	{
		SRequest_ZXReportAccount request = new SRequest_ZXReportAccount();
		request.account = holder.pullAccount.getText().toString();
		request.state = state;
		Protocol.doPost(context, App.getApi(), SHandleId.ZXReportAccount, request.saveToStr(), new Protocol.OnCallback()
		{
			@Override
			public void onResponse(int code, String data, String msg)
			{
				if (code == 200)
				{
					SResponse_ZXReportAccount response = SResponse_ZXReportAccount.load(data);
					LOG.toast(context, "上报成功");
				}
			}
		});
	}

	private boolean starting = false;

	private void start()
	{
		if (!readyToStart)
		{
			LOG.toast(context, "页面未就绪，请稍后重试");
			return;
		}
//		if (TextUtils.isEmpty(grade))
//		{
//			LOG.toast(context, "请选择年级");
//			return;
//		}
//		if (TextUtils.isEmpty(style))
//		{
//			LOG.toast(context, "请选择题型");
//			return;
//		}
//		if (TextUtils.isEmpty(diff))
//		{
//			LOG.toast(context, "请选择难度");
//			return;
//		}
		if (starting)
		{
			LOG.toast(context, "正在启动");
			return;
		}
		starting = true;
		loadJs("MyNnd_GetPhaseSubject();", new ValueCallback<String>()
		{
			@Override
			public void onReceiveValue(String resultStr)
			{
				if (TextUtils.isEmpty(resultStr) || resultStr.equals("null"))
					return;

				JSONObject result = JsonHelper.getJSONObject(resultStr);
				final String phase = result.getString("phase");
				final String subject = result.getString("subject");

				loadJs("MyNnd_GetMaterial();", new ValueCallback<String>()
				{
					@Override
					public void onReceiveValue(String materialStr)
					{
						if (TextUtils.isEmpty(materialStr) || materialStr.equals("null"))
							return;

						JSONObject materialObj = JsonHelper.getJSONObject(materialStr);
						final String material = materialObj.getString("material");

						SRequest_ZXPullTask request = new SRequest_ZXPullTask();
						request.phase = phase;
						request.subject = subject;
						request.material = material;
						request.deviceId = AppKit.OnlyId;
						Protocol.doPost(context, App.getApi(), SHandleId.ZXPullTask, request.saveToStr(), new Protocol.OnCallback()
						{
							@Override
							public void onResponse(int code, String data, String msg)
							{
								if (code == 200)
								{
									SResponse_ZXPullTask response = SResponse_ZXPullTask.load(data);
									if (response.task != null)
									{
										loadJs(String.format("MyNnd_CatchPage('%s', '%s', '%s', '%s', '%s');", //
												response.task.diff, //
												response.task.area, //
												response.task.year, //
												response.task.currChapterPath, //
												response.task.currPage));

										showMask();

//										holder.logWatch.setVisibility(View.VISIBLE);
//										holder.logWatch.scrollToBottom();

										tick();
										waitHandler.removeCallbacks(waitRunnable);
										waitHandler.post(waitRunnable);
									}
									else
									{
										errorOrOver("没有拉取到任务，请检查教材版本是否正确，或切换科目");
									}
									starting = false;
								}
							}
						});
					}
				});

			}
		});
	}

	private void genTask()
	{
		loadJs("MyNnd_GetPhaseSubject();", new ValueCallback<String>()
		{
			@Override
			public void onReceiveValue(String resultStr)
			{
				if (TextUtils.isEmpty(resultStr) || resultStr.equals("null"))
					return;

				JSONObject result = JsonHelper.getJSONObject(resultStr);
				final String phase = result.getString("phase");
				final String subject = result.getString("subject");

				loadJs("MyNnd_GetMaterial();", new ValueCallback<String>()
				{
					@Override
					public void onReceiveValue(final String materialStr)
					{
						if (TextUtils.isEmpty(materialStr) || materialStr.equals("null"))
							return;

						JSONObject materialObj = JsonHelper.getJSONObject(materialStr);
						final String material = materialObj.getString("material");

						loadJs("MyNnd_GetGroup();", new ValueCallback<String>()
						{
							@Override
							public void onReceiveValue(String groupStr)
							{
								if (TextUtils.isEmpty(groupStr) || groupStr.equals("null"))
									return;

//								LOGJson.log(groupStr);

								JSONObject group = JsonHelper.getJSONObject(groupStr);
//								JSONArray styles = group.getJSONArray("styles");
								JSONArray diffs = group.getJSONArray("diffs");
								JSONArray areas = group.getJSONArray("areas");
								JSONArray years = group.getJSONArray("years");

								SRequest_ZXCreateTask request = new SRequest_ZXCreateTask();
								request.phase = phase;
								request.subject = subject;
								request.material = material;

//								for (int i = 0; i < styles.size(); i++)
//								{
//									String text = styles.getString(i);
//									request.styles.add(text);
//								}

								for (int i = 0; i < diffs.size(); i++)
								{
									String text = diffs.getString(i);
									request.diffs.add(text);
								}

								for (int i = 0; i < areas.size(); i++)
								{
									String text = areas.getString(i);
									request.areas.add(text);
								}

								for (int i = 0; i < years.size(); i++)
								{
									String text = years.getString(i);
									request.years.add(text);
								}

								Protocol.doPost(context, App.getApi(), SHandleId.ZXCreateTask, request.saveToStr(), new Protocol.OnCallback()
								{
									@Override
									public void onResponse(int code, String data, String msg)
									{
										if (code == 200)
										{
											SResponse_ZXCreateTask response = SResponse_ZXCreateTask.load(data);
											LOG.toast(context, "构建成功");
										}
									}
								});
							}
						});
					}
				});

			}
		});
	}

	private void errorOrOver(String msg)
	{
		DialogAlert.show(context, null, msg, null, "知道了");
//		hideMask();
	}

	private void showMask()
	{
		findViewById(R.id.mask).setVisibility(View.VISIBLE);
//		mWebView.setKeepScreenOn(true);
	}

	private void hideMask()
	{
		findViewById(R.id.mask).setVisibility(View.GONE);
//		mWebView.setKeepScreenOn(false);
	}

	//---------------------- catch -------------------------

	private ZhiXueWebView mWebView = null;
	private AndroidForJs mAndroidForJs;
	private WebMsgProcess mWebMsgProcess;

	protected void initWebView()
	{
		mWebView = findViewById(R.id.webView);

		mAndroidForJs = new AndroidForJs(this);
		mWebView.addJavascriptInterface(mAndroidForJs, "nnd_native");
		mWebMsgProcess = new WebMsgProcess(this);

		setSettings();
		setListeners();
	}

	private void setSettings()
	{
		mWebView.getSettings().setJavaScriptEnabled(true);
		mWebView.getSettings().setAllowFileAccess(true);
		mWebView.getSettings().setDefaultTextEncodingName("UTF-8");
		mWebView.getSettings().setSupportZoom(true);
		mWebView.getSettings().setAppCacheEnabled(true);
		mWebView.getSettings().setDomStorageEnabled(true);
		mWebView.getSettings().setLoadWithOverviewMode(true);
		mWebView.getSettings().setUseWideViewPort(false);
		mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);

		CookieManager mCookieManager = CookieManager.getInstance();
		mCookieManager.setAcceptCookie(true);
		mCookieManager.setAcceptThirdPartyCookies(mWebView, true);

		mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
		mWebView.getSettings().setBuiltInZoomControls(true);
		mWebView.setHorizontalScrollBarEnabled(true);
		mWebView.getSettings().setLoadsImagesAutomatically(true);

		mWebView.getSettings().setUserAgentString("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36");
	}

	private void setListeners()
	{
		mWebView.setDownloadListener(new DownloadListener()
		{
			@Override
			public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength)
			{
				LOG.v("onDownloadStart : " + url);
//				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//				startActivity(intent);
			}
		});

		mWebView.setWebChromeClient(new WebChromeClient()
		{
			@Override
			public void onReceivedTitle(WebView view, String text)
			{
//				LOG.v(String.format("onReceivedTitle : %s", text));
				super.onReceivedTitle(view, text);
			}

			@Override
			public void onProgressChanged(WebView view, int newProgress)
			{
				super.onProgressChanged(view, newProgress);
			}
		});

		mWebView.setWebViewClient(new WebViewClient()
		{
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url)
			{
//				LOG.v(String.format("shouldOverrideUrlLoading : %s", url));
				if (url.startsWith("tel:") || url.startsWith("sms:") || url.startsWith("nnd:"))
				{
//					Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//					startActivity(intent);
					return true;
				}
				else if (url.startsWith("msg:"))
				{
//					mWebMsgProcess.process(url);
					return true;
				}
				return false;
			}

			@Override
			public void onLoadResource(WebView view, String url)
			{
//				 LOG.v(String.format("onLoadResource : %s", url));
				super.onLoadResource(view, url);
			}

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon)
			{
//				LOG.v(String.format("onPageStarted : %s", url));
				super.onPageStarted(view, url, favicon);
			}

			@Override
			public void onPageFinished(final WebView view, final String url)
			{
				LOG.v(String.format("【>>>】onPageFinished : %s", url));
				super.onPageFinished(view, url);
				mUrl = url;
//				if (url.equals("about:blank"))
//				{
//					new Handler().postDelayed(new Runnable()
//					{
//						public void run()
//						{
//							about_blank_finish();
//						}
//					}, 10);
//				}
//				else
				{
//					int pos1 = url.indexOf("//") + 2;
//					int pos2 = url.indexOf("/", pos1);
//					String host = url.substring(pos1, pos2);
//					LOG.v("host:" + host);
					runPluginCode("logic" + ".js");
//					if (currFunc == Func_searchAll)
//						searchAll.finish(url);
//					else if (currFunc == Func_requestDetail)
//						requestDetail.finish(url);
//					else if (currFunc == Func_requestContent)
//						requestContent.finish(url);
					if (url.equals("https://www.zhixue.com/login.html"))
					{
						ViewGroup.LayoutParams layoutParams = mWebView.getLayoutParams();
						layoutParams.height = 1200;
						mWebView.setLayoutParams(layoutParams);
					}
					else if (url.startsWith("https://www.zhixue.com/container/container/changePwd/index?role="))
					{
						loadJs("MyNnd_ClickIgnore();");
					}
					else if (url.equals("https://www.zhixue.com/htm-vessel/#/teacher"))
					{
						ViewGroup.LayoutParams layoutParams = mWebView.getLayoutParams();
						layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
						mWebView.setLayoutParams(layoutParams);
						readyToStart = false;
						knowledgeCounter = 0;
						loadJs("MyNnd_ClickXtzj();");
					}
					else if (url.equals("https://www.zhixue.com/paperfresh/dist/#//manualGroup/knowledge"))
					{
						readyToStart = false;
						knowledgeCounter = 0;
					}
					else if (url.equals("https://www.zhixue.com/paperfresh/dist/#/other/error566"))
					{
//						hideMask();
//						mWebView.setKeepScreenOn(false);
					}
					else if (url.equals("https://www.zhixue.com/paperfresh/dist/#/manualGroup/knowledge"))
					{
						knowledgeCounter++;
						if (knowledgeCounter > 1)
						{
//							LOG.v("页面加载完成");
//							LOG.toast(context, "页面加载完成");
							loadJs("MyNnd_ClickTbzj();");
						}
					}
					else if (url.equals("https://www.zhixue.com/paperfresh/dist/#/qualityLib/papers/-1"))
					{
						readyToStart = true;
						LOG.v("页面已就绪");
						LOG.toast(context, "页面已就绪，可以开始");
					}
//					else if (url.equals("https://www.zhihu.com/signin?next=%2F"))
//					{
//						ViewGroup.LayoutParams layoutParams = mWebView.getLayoutParams();
//						layoutParams.height = 1200;
//						mWebView.setLayoutParams(layoutParams);
//					}
//					else if (url.equals("https://www.zhihu.com/"))
//					{
//						ViewGroup.LayoutParams layoutParams = mWebView.getLayoutParams();
//						layoutParams.height = 1200;
//						mWebView.setLayoutParams(layoutParams);
////						scrollToBottom();
//					}
				}
			}
		});
	}

	public void scrollToBottom()
	{
		new Handler().postDelayed(new Runnable()
		{
			@Override
			public void run()
			{
				mWebView.scrollToBottom();
				scrollToBottom();
			}
		}, 3000);
	}

	protected boolean readyToStart = false;
	private int knowledgeCounter = 0;

	public void loadJs(String jsCode)
	{
		try
		{
			mWebView.loadUrl("javascript:" + jsCode);
		}
		catch (Exception e)
		{
			// TODO: handle exception
		}
	}

	public void loadJs(String jsCode, ValueCallback<String> resultCallback)
	{
		try
		{
			mWebView.evaluateJavascript("javascript:" + jsCode, resultCallback);
		}
		catch (Exception e)
		{
			// TODO: handle exception
		}
	}

	public void runPluginCode(final String pluginCode)
	{
//		new Handler().postDelayed(new Runnable()
//		{
//			public void run()
//			{
//		String pluginCodeText = AssetsHelper.getAssetsText(context, pluginCode);
		loadJs(AssetsHelper.getJsBaseLib(context) + logicJs);
//			}
//		}, 10);
	}

	//----------------------------------

	public String js_log(String info)
	{
		LOG.v(info);
		return "ok";
	}

	public String js_toast(String info)
	{
		Toast.makeText(context, info, Toast.LENGTH_SHORT).show();
		return "ok";
	}

	public String js_init()
	{
		return "ok";
	}

	public String js_over()
	{
		return "ok";
	}

	public String call()
	{
		return "ok";
	}

	public String callStr(String str)
	{
		return "ok";
	}

	public String callCmd(String cmd)
	{
		ArrayList<String> params = new ArrayList<String>();
		return callCmd(cmd, params);
	}

	public String callCmd1(String cmd, String p1)
	{
		ArrayList<String> params = new ArrayList<String>();
		params.add(p1);
		return callCmd(cmd, params);
	}

	public String callCmd2(String cmd, String p1, String p2)
	{
		ArrayList<String> params = new ArrayList<String>();
		params.add(p1);
		params.add(p2);
		return callCmd(cmd, params);
	}

	public String callCmd3(String cmd, String p1, String p2, String p3)
	{
		ArrayList<String> params = new ArrayList<String>();
		params.add(p1);
		params.add(p2);
		params.add(p3);
		return callCmd(cmd, params);
	}

	public String callCmd4(String cmd, String p1, String p2, String p3, String p4)
	{
		ArrayList<String> params = new ArrayList<String>();
		params.add(p1);
		params.add(p2);
		params.add(p3);
		params.add(p4);
		return callCmd(cmd, params);
	}

	public String callCmd5(String cmd, String p1, String p2, String p3, String p4, String p5)
	{
		ArrayList<String> params = new ArrayList<String>();
		params.add(p1);
		params.add(p2);
		params.add(p3);
		params.add(p4);
		params.add(p5);
		return callCmd(cmd, params);
	}

	public String callCmd6(String cmd, String p1, String p2, String p3, String p4, String p5, String p6)
	{
		ArrayList<String> params = new ArrayList<String>();
		params.add(p1);
		params.add(p2);
		params.add(p3);
		params.add(p4);
		params.add(p5);
		params.add(p6);
		return callCmd(cmd, params);
	}

	public String callCmd7(String cmd, String p1, String p2, String p3, String p4, String p5, String p6, String p7)
	{
		ArrayList<String> params = new ArrayList<String>();
		params.add(p1);
		params.add(p2);
		params.add(p3);
		params.add(p4);
		params.add(p5);
		params.add(p6);
		params.add(p7);
		return callCmd(cmd, params);
	}

	public String callCmd8(String cmd, String p1, String p2, String p3, String p4, String p5, String p6, String p7, String p8)
	{
		ArrayList<String> params = new ArrayList<String>();
		params.add(p1);
		params.add(p2);
		params.add(p3);
		params.add(p4);
		params.add(p5);
		params.add(p6);
		params.add(p7);
		params.add(p8);
		return callCmd(cmd, params);
	}

	public String callCmd9(String cmd, String p1, String p2, String p3, String p4, String p5, String p6, String p7, String p8, String p9)
	{
		ArrayList<String> params = new ArrayList<String>();
		params.add(p1);
		params.add(p2);
		params.add(p3);
		params.add(p4);
		params.add(p5);
		params.add(p6);
		params.add(p7);
		params.add(p8);
		params.add(p9);
		return callCmd(cmd, params);
	}

	public String callCmd(String cmd, final ArrayList<String> params)
	{
		try
		{
			if (!cmd.equals("dom"))
			{
				StringBuilder sb = new StringBuilder();
				sb.append(cmd);
				for (String param : params)
				{
					if (param != null && param.length() > 30)
						sb.append(", " + String.format("[%d chars]", param.length()));
					else
						sb.append(", " + param);
				}
				LOG.v(sb.toString());
			}

			if (cmd.equals("dom"))
			{
				String dom = params.get(0);

				String domfile = String.format("%s/dom_%s.txt", FsUtils.SD_CARD, System.currentTimeMillis());
				LOG.v(domfile);
				FsUtils.writeText(new File(domfile), dom);
			}

			if (cmd.equals("resultKnowledges"))
			{
				String knowledges = params.get(0);

//				LOGJson.log(knowledges);

				final SZXKnowledgeTree tree = SZXKnowledgeTree.load(knowledges);

				final File jsonFile = new File(String.format("%s/catch_zhixue/%s_%s_knowledges.json", FsUtils.SD_CARD, tree.phase, tree.subject));
				FsUtils.createFolder(jsonFile.getParentFile());
				FsUtils.writeText(jsonFile, LOGJson.getStr(knowledges));

				final File jsonRawFile = new File(String.format("%s/catch_zhixue/%s_%s_knowledges.json.raw", FsUtils.SD_CARD, tree.phase, tree.subject));
				FsUtils.writeText(jsonRawFile, knowledges);

				uploadUntilSuccess(jsonFile, new OnUploadUntilSuccessCallback()
				{
					@Override
					public void success()
					{
						uploadUntilSuccess(jsonRawFile, new OnUploadUntilSuccessCallback()
						{
							@Override
							public void success()
							{
								runOnUiThread(new Runnable()
								{
									@Override
									public void run()
									{
										LOG.v("抓取成功：" + jsonFile.toString());
										LOG.toast(context, "抓取成功：" + tree.phase + "," + tree.subject);
									}
								});
							}
						});
					}
				});
			}

			if (cmd.equals("resultChapters"))
			{
				String chapters = params.get(0);
//				final String groupStr = params.get(1);

//				LOGJson.log(chapters);

				final SZXChapterTree tree = SZXChapterTree.load(chapters);

				final File jsonFile = new File(String.format("%s/catch_zhixue/%s_%s_%s/chapters.json", FsUtils.SD_CARD, tree.phase, tree.subject, tree.material));
				FsUtils.createFolder(jsonFile.getParentFile());
				FsUtils.writeText(jsonFile, LOGJson.getStr(chapters));

				final File jsonRawFile = new File(String.format("%s/catch_zhixue/%s_%s_%s/chapters.json.raw", FsUtils.SD_CARD, tree.phase, tree.subject, tree.material));
				FsUtils.writeText(jsonRawFile, chapters);

				uploadUntilSuccess(jsonFile, new OnUploadUntilSuccessCallback()
				{
					@Override
					public void success()
					{
						uploadUntilSuccess(jsonRawFile, new OnUploadUntilSuccessCallback()
						{
							@Override
							public void success()
							{
								runOnUiThread(new Runnable()
								{
									@Override
									public void run()
									{
										LOG.v("抓取成功：" + jsonFile.toString());
										LOG.toast(context, "抓取成功：" + tree.phase + "," + tree.subject);
									}
								});
							}
						});
					}
				});
			}

			if (cmd.equals("resultProblems"))
			{
				String infoStr = params.get(0);
				final int currPage = Integer.valueOf(params.get(1));
				final String problems = params.get(2);
				final String htmlStr = params.get(3);

				JSONObject info = JsonHelper.getJSONObject(infoStr);
				final String phase = info.getString("phase");
				final String subject = info.getString("subject");
				final String material = info.getString("material");
				final String currChapterPath = info.getString("chapterPath");
				final String diff = info.getString("diff");
				final String area = info.getString("area");
				final String year = info.getString("year");

				runOnUiThread(new Runnable()
				{
					@Override
					public void run()
					{
						updateBattery();
						tick();

						final File jsonFile = new File(String.format("%s/juan/%s%s/%s/main.json", FsUtils.SD_CARD, phase, subject, material.replace("/", "").replace("\\", "").replace("*", "").replace(":", "")));
						FsUtils.createFolder(jsonFile.getParentFile());
						FsUtils.writeText(jsonFile, LOGJson.getStr(problems));

						final File jsonRawFile = new File(String.format("%s/juan/%s%s/%s/main.json.raw", FsUtils.SD_CARD, phase, subject, material.replace("/", "").replace("\\", "").replace("*", "").replace(":", "")));
						FsUtils.writeText(jsonRawFile, problems);

						DialogWait.message("上传json");
						uploadUntilSuccess(jsonFile, new OnUploadUntilSuccessCallback()
						{
							@Override
							public void success()
							{
								jsonFile.delete();

								DialogWait.message("上传json.raw");
								uploadUntilSuccess(jsonRawFile, new OnUploadUntilSuccessCallback()
								{
									@Override
									public void success()
									{
										jsonRawFile.delete();

										DialogWait.message("开始抓图（卡住是正常的）");

										new Handler().post(new Runnable()
										{
											@Override
											public void run()
											{
												List<File> pngFiles;
												while (true)
												{
													int viewHeight1 = mWebView.getHeight();
													pngFiles = captureViewToFiles(mWebView, String.format("%s/juan/%s%s/%s/main", FsUtils.SD_CARD, phase, subject, material.replace("/", "").replace("\\", "").replace("*", "").replace(":", "")));
													int viewHeight2 = mWebView.getHeight();
													if (viewHeight1 == viewHeight2)
														break;
												}

												uploadCapturePng(pngFiles, 0, new OnUploadCapturePngCallback()
												{
													@Override
													public void success()
													{
														final File htmlFile = new File(String.format("%s/juan/%s%s/%s/main.html", FsUtils.SD_CARD, phase, subject, material.replace("/", "").replace("\\", "").replace("*", "").replace(":", "")));
														FsUtils.writeText(htmlFile, htmlStr);

														DialogWait.message("上传html");
														uploadUntilSuccess(htmlFile, new OnUploadUntilSuccessCallback()
														{
															@Override
															public void success()
															{
																htmlFile.delete();

//														if (false)
																{
																	DialogWait.message("请求服务器处理");
																	SRequest_ZXProcessJuan request = new SRequest_ZXProcessJuan();
																	request.phase = phase;
																	request.subject = subject;
																	request.material = material;
																	Protocol.doPost(context, App.getApi(), SHandleId.ZXProcessJuan, request.saveToStr(), new Protocol.OnCallback()
																	{
																		@Override
																		public void onResponse(int code, String data, String msg)
																		{
																			DialogWait.close();
																			if (code == 200)
																			{
																				SResponse_ZXProcessJuan response = SResponse_ZXProcessJuan.load(data);
																				DialogAlert.show(context, "处理成功", null, null, "我知道了");
																			}
																			else
																			{
																				DialogAlert.show(context, "处理失败！！！！", null, null, "我知道了");
																			}
																		}
																	});
																}

															}
														});
													}
												});
											}
										});

									}
								});
							}
						});
					}
				});
			}

			if (cmd.equals("resultProblems2"))
			{
				String infoStr = params.get(0);
				final int currPage = Integer.valueOf(params.get(1));
				final String problems = params.get(2);
				final String htmlStr = params.get(3);

				JSONObject info = JsonHelper.getJSONObject(infoStr);
				final String phase = info.getString("phase");
				final String subject = info.getString("subject");
				final String material = info.getString("material");
				final String currChapterPath = info.getString("chapterPath");
				final String diff = info.getString("diff");
				final String area = info.getString("area");
				final String year = info.getString("year");

				runOnUiThread(new Runnable()
				{
					@Override
					public void run()
					{
						updateBattery();
						tick();

						final File jsonFile = new File(String.format("%s/juan/%s%s/%s/main.json", FsUtils.SD_CARD, phase, subject, material.replace("/", "").replace("\\", "").replace("*", "").replace(":", "")));
						FsUtils.createFolder(jsonFile.getParentFile());
						FsUtils.writeText(jsonFile, LOGJson.getStr(problems));

						final File jsonRawFile = new File(String.format("%s/juan/%s%s/%s/main.json.raw", FsUtils.SD_CARD, phase, subject, material.replace("/", "").replace("\\", "").replace("*", "").replace(":", "")));
						FsUtils.writeText(jsonRawFile, problems);

						DialogWait.message("上传json");
						uploadUntilSuccess(jsonFile, new OnUploadUntilSuccessCallback()
						{
							@Override
							public void success()
							{
								jsonFile.delete();

								DialogWait.message("上传json.raw");
								uploadUntilSuccess(jsonRawFile, new OnUploadUntilSuccessCallback()
								{
									@Override
									public void success()
									{
										jsonRawFile.delete();

										DialogWait.message("开始抓图（卡住是正常的）");

										new Handler().post(new Runnable()
										{
											@Override
											public void run()
											{
												List<File> pngFiles;
												while (true)
												{
													int viewHeight1 = mWebView.getHeight();
													pngFiles = captureViewToFiles(mWebView, String.format("%s/juan/%s%s/%s/main", FsUtils.SD_CARD, phase, subject, material.replace("/", "").replace("\\", "").replace("*", "").replace(":", "")));
													int viewHeight2 = mWebView.getHeight();
													if (viewHeight1 == viewHeight2)
														break;
												}

												uploadCapturePng(pngFiles, 0, new OnUploadCapturePngCallback()
												{
													@Override
													public void success()
													{
														final File htmlFile = new File(String.format("%s/juan/%s%s/%s/main.html", FsUtils.SD_CARD, phase, subject, material.replace("/", "").replace("\\", "").replace("*", "").replace(":", "")));
														FsUtils.writeText(htmlFile, htmlStr);

														DialogWait.message("上传html");
														uploadUntilSuccess(htmlFile, new OnUploadUntilSuccessCallback()
														{
															@Override
															public void success()
															{
																htmlFile.delete();

//														if (false)
																{
																	DialogWait.message("请求服务器处理");
																	SRequest_ZXProcessJuan2 request = new SRequest_ZXProcessJuan2();
																	request.phase = phase;
																	request.subject = subject;
																	request.material = material;
																	Protocol.doPost(context, App.getApi(), SHandleId.ZXProcessJuan2, request.saveToStr(), new Protocol.OnCallback()
																	{
																		@Override
																		public void onResponse(int code, String data, String msg)
																		{
																			DialogWait.close();
																			if (code == 200)
																			{
																				SResponse_ZXProcessJuan2 response = SResponse_ZXProcessJuan2.load(data);
																				DialogAlert.show(context, "处理成功", null, null, "我知道了");
																			}
																			else
																			{
																				DialogAlert.show(context, "处理失败！！！！", null, null, "我知道了");
																			}
																		}
																	});
																}

															}
														});
													}
												});
											}
										});

									}
								});
							}
						});
					}
				});
			}

			if (cmd.equals("tick"))
			{
				String infoStr = params.get(0);

				JSONObject info = JsonHelper.getJSONObject(infoStr);
				final String phase = info.getString("phase");
				final String subject = info.getString("subject");
				final String material = info.getString("material");
				final String currChapterPath = info.getString("chapterPath");
				final String diff = info.getString("diff");
				final String area = info.getString("area");
				final String year = info.getString("year");

				runOnUiThread(new Runnable()
				{
					@Override
					public void run()
					{
						updateBattery();
						tick();

						if (App.getConfig().zhixueErrorMode)
						{
							SRequest_ZXCatchOver request = new SRequest_ZXCatchOver();
							request.phase = phase;
							request.subject = subject;
							request.material = material;
							request.diff = diff;
							request.area = area;
							request.year = year;
							request.deviceId = AppKit.OnlyId;
							Protocol.doPost(context, App.getApi(), SHandleId.ZXCatchOver, request.saveToStr(), new Protocol.OnCallback()
							{
								@Override
								public void onResponse(int code, String data, String msg)
								{
									if (code == 200)
									{
										SResponse_ZXCatchOver response = SResponse_ZXCatchOver.load(data);
										start();
									}
								}
							});
						}
					}
				});
			}

			if (cmd.equals("catchOver"))
			{
				String infoStr = params.get(0);

				JSONObject info = JsonHelper.getJSONObject(infoStr);
				final String phase = info.getString("phase");
				final String subject = info.getString("subject");
				final String material = info.getString("material");
				final String diff = info.getString("diff");
				final String area = info.getString("area");
				final String year = info.getString("year");

				runOnUiThread(new Runnable()
				{
					@Override
					public void run()
					{
						SRequest_ZXCatchOver request = new SRequest_ZXCatchOver();
						request.phase = phase;
						request.subject = subject;
						request.material = material;
						request.diff = diff;
						request.area = area;
						request.year = year;
						request.deviceId = AppKit.OnlyId;
						Protocol.doPost(context, App.getApi(), SHandleId.ZXCatchOver, request.saveToStr(), new Protocol.OnCallback()
						{
							@Override
							public void onResponse(int code, String data, String msg)
							{
								if (code == 200)
								{
									SResponse_ZXCatchOver response = SResponse_ZXCatchOver.load(data);
									start();
								}
							}
						});

//						hideMask();
					}
				});
			}

			if (cmd.equals("catchZhiXueOne"))
			{
				String infoStr = params.get(0);

				JSONObject info = JsonHelper.getJSONObject(infoStr);
				final String questionId = info.getString("questionId");
				final String title = info.getString("title").replace("/", "").replace("\\", "").replace("*", "").replace(":", "");
				final String index = info.getString("index");
				final String catchVotersNum = info.getString("catchVotersNum");

				runOnUiThread(new Runnable()
				{
					@Override
					public void run()
					{
						final File mhtFile = new File(String.format("%s/zhihu/%s_%s/%s_%s.mht", FsUtils.SD_CARD, questionId, title, catchVotersNum, index));
						FsUtils.createFolder(mhtFile.getParentFile());
						mWebView.saveWebArchive(mhtFile.toString());
						LOG.v("saveed " + mhtFile);
					}
				});
			}

			if (cmd.equals("scrollToBottom"))
			{
				runOnUiThread(new Runnable()
				{
					@Override
					public void run()
					{
						mWebView.scrollToBottom();
					}
				});
			}

			return "ok";
		}
		catch (Exception e)
		{
			LOG.v("PageWebView callCmd Exception " + e.getMessage());
			e.printStackTrace();
			return "";
		}
	}

	public interface OnUploadCapturePngCallback
	{
		void success();
	}

	private void uploadCapturePng(final List<File> files, final int index, final OnUploadCapturePngCallback callback)
	{
		if (index < files.size())
		{
			final File file = files.get(index);
			DialogWait.message(String.format("上传图像（%s/%s）", index + 1, files.size()));
			uploadUntilSuccess(file, new OnUploadUntilSuccessCallback()
			{
				@Override
				public void success()
				{
					file.delete();
					uploadCapturePng(files, index + 1, callback);
				}
			});
		}
		else
		{
			if (callback != null)
				callback.success();
		}
	}

	public interface OnUploadUntilSuccessCallback
	{
		void success();
	}

	private void uploadUntilSuccess(final File file, final OnUploadUntilSuccessCallback callback)
	{
		LysUpload.doUpload(context, file, file.getAbsolutePath().substring(FsUtils.SD_CARD.length()), new Protocol.OnCallback()
		{
			@Override
			public void onResponse(int code, String data, String msg)
			{
				if (code == 200)
				{
					if (callback != null)
						callback.success();
				}
				else
				{
					uploadUntilSuccess(file, callback);
				}
			}
		});
	}

	public List<File> captureViewToFiles(View view, String path)
	{
		List<File> files = new ArrayList<>();
		int viewHeight = view.getHeight();
		int block = viewHeight / 10000;
		if (viewHeight % 10000 > 0)
			block++;
		int blockHeight = viewHeight / block;
		int pos = 0;
		for (int i = 0; i < block; i++)
		{
			int height = blockHeight;
			if (i == block - 1)
				height = viewHeight - pos;

			File file = new File(String.format("%s.%02d.png", path, i));

			Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), height, Bitmap.Config.ARGB_8888);
			Canvas canvas = new Canvas(bitmap);
			canvas.translate(0, -pos);
			view.draw(canvas);
			CommonUtils.saveBitmap(bitmap, Bitmap.CompressFormat.PNG, file);
			bitmap.recycle();

			files.add(file);

			pos += height;
		}
		return files;
	}
}
