package com.lys.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.lys.App;
import com.lys.app.BuildConfig;
import com.lys.app.R;
import com.lys.base.utils.LOG;
import com.lys.base.utils.SysUtils;
import com.lys.config.AppConfig;
import com.lys.dialog.DialogKey;
import com.lys.fragment.FragmentLogin;
import com.lys.fragment.FragmentMain;
import com.lys.kit.config.Config;
import com.lys.kit.dialog.DialogAlert;
import com.lys.kit.manager.CrashHandler;
import com.lys.kit.utils.Protocol;
import com.lys.protobuf.SHandleId;
import com.lys.protobuf.SRequest_GetConfig;
import com.lys.protobuf.SRequest_Test;
import com.lys.protobuf.SRequest_UserLogin;
import com.lys.protobuf.SResponse_GetConfig;
import com.lys.protobuf.SResponse_Test;
import com.lys.protobuf.SResponse_UserLogin;
import com.lys.receiver.AppReceiver;
import com.lys.utils.Helper;
import com.lys.utils.LysIM;
import com.lys.utils.SVNManager;

import io.rong.imlib.RongIMClient;

public class ActivityMain extends AppActivity implements View.OnClickListener
{
	public static final int REQUEST_CODE_GOIN_USER = 0x152;

	public static String Action_modifyHead(Context context)
	{
		return context.getPackageName() + "." + ActivityMain.class.getName() + ".modifyHead";
	}

	private class Holder
	{
		private TextView noNetwork;
		private TextView errorInfo;
	}

	private Holder holder = new Holder();

	private void initHolder()
	{
		holder.noNetwork = findViewById(R.id.noNetwork);
		holder.errorInfo = findViewById(R.id.errorInfo);
	}

//	private boolean isMaster;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initHolder();
		requestPermission();
	}

	@Override
	public void permissionSuccess()
	{
		super.permissionSuccess();

//		isMaster = getIntent().getBooleanExtra("isMaster", false);

		findViewById(R.id.noNetwork).setOnClickListener(this);
		findViewById(R.id.errorInfo).setOnClickListener(this);

		holder.noNetwork.setOnLongClickListener(new View.OnLongClickListener()
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

		holder.errorInfo.setOnLongClickListener(new View.OnLongClickListener()
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

		holder.noNetwork.setVisibility(SysUtils.hasNet(context) ? View.GONE : View.VISIBLE);
		holder.errorInfo.setVisibility(View.GONE);

		findViewById(R.id.test).setVisibility(SysUtils.isDebug() ? View.GONE : View.GONE);
		findViewById(R.id.test).setOnClickListener(this);

		IntentFilter filter = new IntentFilter();
		filter.addAction(AppReceiver.Action_netChange(context));
		filter.addAction(LysIM.Action_CONNECTED(context));
		registerReceiver(mReceiver, filter);

		start();
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
		if (view.getId() == R.id.noNetwork)
		{
			SysUtils.openWifiSetting(context);
		}
		else if (view.getId() == R.id.errorInfo)
		{
			start();
		}
		else if (view.getId() == R.id.test)
		{
			SRequest_Test request = new SRequest_Test();
			Protocol.doPost(context, App.getApi(), SHandleId.Test, request.saveToStr(), new Protocol.OnCallback()
			{
				@Override
				public void onResponse(int code, String data, String msg)
				{
					if (code == 200)
					{
						SResponse_Test response = SResponse_Test.load(data);
					}
				}
			});
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST_CODE_GOIN_USER)
		{
			if (resultCode == ActivityUser.RESULT_CODE_LOGOUT)
			{
				setupNewUser();
			}
		}
	}

	private void start()
	{
		if (SysUtils.hasNet(context))
		{
			if (App.getConfig() == null)
			{
				holder.errorInfo.setVisibility(View.GONE);
				App.requestHost(context, 0, new App.OnHostCallback()
				{
					@Override
					public void onResult(String host)
					{
						if (!TextUtils.isEmpty(host))
						{
							LOG.v("host：" + host);
							SRequest_GetConfig request = new SRequest_GetConfig();
							Protocol.doPost(context, host, SHandleId.GetConfig, request.saveToStr(), new Protocol.OnCallback()
							{
								@Override
								public void onResponse(int code, String data, String msg)
								{
									if (code == 200)
									{
										final SResponse_GetConfig response = SResponse_GetConfig.load(data);
										App.TimeOffset = System.currentTimeMillis() - response.time;
										App.setConfig(response);
										Config.writeAppConfigInfo(response.saveToStr());
//										Config.writeServerInfo(context, response.server);
//										getServerConfig();
										checkUpdate();
										CrashHandler.uploadCrash();
										new Thread(new Runnable()
										{
											@Override
											public void run()
											{
												SVNManager.init(response.svnUrl, response.svnAccount, response.svnPsw);
											}
										}).start();
										doLogin(AppConfig.readAccount(), AppConfig.readPsw());
									}
									else
									{
										holder.errorInfo.setText(String.format("%s，请点击重试", msg));
										holder.errorInfo.setVisibility(View.VISIBLE);
									}
								}
							});
						}
						else
						{
							holder.errorInfo.setText("网络错误");
							holder.errorInfo.setVisibility(View.VISIBLE);
						}
					}
				});
			}
			else
			{
//				getServerConfig();
				doLogin(AppConfig.readAccount(), AppConfig.readPsw());
			}
		}
	}

//	private void getServerConfig()
//	{
//		if (SysUtils.hasNet(context))
//		{
//			if (App.getServerConfig() == null)
//			{
//				holder.errorInfo.setVisibility(View.GONE);
//				SRequest_GetConfigLogic request = new SRequest_GetConfigLogic();
//				Protocol.doPost(context, SHandleId.GetConfigLogic, request.saveToStr(), new Protocol.OnCallback()
//				{
//					@Override
//					public void onResponse(int code, String data, String msg)
//					{
//						if (code == 200)
//						{
//							SResponse_GetConfigLogic response = SResponse_GetConfigLogic.load(data);
//							App.setServerConfig(response);
//							Config.writeConfigInfo(context, response.saveToStr());
//							gotoUser(true);
//						}
//						else
//						{
//							holder.errorInfo.setText(String.format("%s，请点击重试", msg));
//							holder.errorInfo.setVisibility(View.VISIBLE);
//						}
//					}
//				});
//			}
//			else
//			{
//				gotoUser(true);
//			}
//		}
//	}

	private long connectTime = 0;

	public void doLogin(final String account, final String psw)
	{
		if (SysUtils.hasNet(context) && App.getUser() == null)
		{
			holder.errorInfo.setVisibility(View.GONE);
			if (TextUtils.isEmpty(account) || TextUtils.isEmpty(psw))
			{
				setupNewUser();
			}
			else
			{
				SRequest_UserLogin request = new SRequest_UserLogin();
				request.userId = account;
				request.psw = psw;
				request.deviceId = App.OnlyId;
				request.clientVersion = BuildConfig.VERSION_NAME + "+" + BuildConfig.VERSION_CODE;
				Protocol.doPost(context, App.getApi(), SHandleId.UserLogin, request.saveToStr(), new Protocol.OnCallback()
				{
					@Override
					public void onResponse(int code, String data, String msg)
					{
						if (code == 200)
						{
							final SResponse_UserLogin response = SResponse_UserLogin.load(data);
							if (response.user == null) // 未注册
							{
//							if (isMaster)
//							{
//								Intent intent = new Intent(context, ActivityMainMaster.class);
//								startActivity(intent);
//								finish();
//							}
//							else
//							{
//								if (isFirst)
								setupNewUser();
//								else
//									LOG.toast(context, "尚未注册");
//							}
							}
							else
							{
								AppConfig.saveAccountPsw(response.user.id, response.user.psw);
								App.setUser(response.user);
								Helper.addEvent(context, App.userId(), AppConfig.EventAction_StartApp, context.getPackageName(), String.format("版本号：%s，版本名：%s，设备号：%s", BuildConfig.VERSION_CODE, BuildConfig.VERSION_NAME, App.OnlyId));
								connectTime = System.currentTimeMillis();
								LysIM.instance().connect(response.user, new RongIMClient.ConnectCallback()
								{
									@Override
									public void onTokenIncorrect()
									{
//										LOG.toast(context, "连接失效");
//										holder.errorInfo.setText("连接失效，请联系技术人员");
//										holder.errorInfo.setVisibility(View.VISIBLE);
									}

									@Override
									public void onSuccess(String userId)
									{
									}

									@Override
									public void onError(RongIMClient.ErrorCode errorCode)
									{
//										LOG.toast(context, "连接错误：" + errorCode);
//										holder.errorInfo.setText(String.format("%s，请联系技术人员", errorCode.toString()));
//										holder.errorInfo.setVisibility(View.VISIBLE);
									}
								});
							}
						}
						else
						{
							holder.errorInfo.setText(String.format("%s，请点击重试", msg));
							holder.errorInfo.setVisibility(View.VISIBLE);
						}
					}
				});
			}
		}
	}

	private void setupNewUser()
	{
		Fragment fragmentExist = getSupportFragmentManager().findFragmentById(R.id.mainContiner);
		if (fragmentExist instanceof FragmentLogin)
		{
			LOG.v("fragmentExist : " + fragmentExist);
		}
		else
		{
			FragmentLogin fragment = new FragmentLogin();
			setup(fragment);
		}
	}

	private void setupMain()
	{
		Fragment fragmentExist = getSupportFragmentManager().findFragmentById(R.id.mainContiner);
		if (fragmentExist instanceof FragmentMain)
		{
			LOG.v("fragmentExist : " + fragmentExist);
		}
		else
		{
			FragmentMain fragment = new FragmentMain();
			setup(fragment);
		}
	}

	private void setup(Fragment fragment)
	{
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.replace(R.id.mainContiner, fragment);
		ft.commitAllowingStateLoss();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if (keyCode == KeyEvent.KEYCODE_BACK)
		{
//			Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.mainContiner);
//			if (fragment instanceof FragmentMainStudent)
//			{
//				return true;
//			}
			if (getPackageName().equals("com.lys.app.desktop"))
				return true;
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
		return super.onKeyDown(keyCode, event);
	}

	private BroadcastReceiver mReceiver = new BroadcastReceiver()
	{
		@Override
		public void onReceive(Context context, Intent intent)
		{
//			LOG.v("Action:" + intent.getAction());
			if (intent.getAction().equals(AppReceiver.Action_netChange(context)))
			{
				holder.noNetwork.setVisibility(SysUtils.hasNet(context) ? View.GONE : View.VISIBLE);
				start();
			}
			else if (intent.getAction().equals(LysIM.Action_CONNECTED(context)))
			{
				LOG.v("connect onSuccess");
				if (System.currentTimeMillis() - connectTime < 10 * 1000)
				{
					connectTime = 0;
					LOG.v("setupMain");
					setupMain();
				}
			}
		}
	};
}
