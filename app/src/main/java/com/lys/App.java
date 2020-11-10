package com.lys;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.LinkProperties;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.os.Build;
import android.text.TextUtils;

import com.lys.base.utils.HttpUtils;
import com.lys.base.utils.LOG;
import com.lys.base.utils.SysUtils;
import com.lys.kit.AppKit;
import com.lys.kit.module.ModuleKit;
import com.lys.kit.module.ModulePlayer;
import com.lys.kit.module.OssHelper;
import com.lys.kit.utils.KitUtils;
import com.lys.player.CModulePlayer;
import com.lys.receiver.AppReceiver;
import com.lys.utils.CModuleKit;
import com.lys.utils.COssHelper;
import com.lys.utils.LysIM;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;

public class App extends AppKit
{
//	public static final String HOST_INNER = "http://192.168.31.197:8080/pair/api";
//	public static final String HOST_WAI = "http://47.96.82.69/pair/api";
//	public static final String HOST_WAI = "http://cloud.k12-eco.com:8080/pair/api";

//	public static String HOST = HOST_INNER;

//	public static String Account;
//	public static String Psw;

	public static long TimeOffset = 0;

	public static long currentTimeMillis()
	{
		return System.currentTimeMillis() - TimeOffset;
	}

	/*
	/www/server/apache-tomcat-8.5.43/webapps/ROOT/files/host/
	 */
	private static String hostFileAddress(Context context, int index)
	{
		if (index == 0)
			return String.format("http://zjyk-file.oss-cn-huhehaote.aliyuncs.com/host/%s--%s.txt", context.getPackageName(), SysUtils.buildType());
//		else if (index == 1)
//			return String.format("http://cloud.k12-eco.com/files/host/%s--%s.txt", context.getPackageName(), SysUtils.buildType());
//		else if (index == 2)
//			return String.format("http://47.96.82.69/files/host/%s--%s.txt", context.getPackageName(), SysUtils.buildType());
//		else if (index == 3)
//			return String.format("http://39.104.58.109/files/host/%s--%s.txt", context.getPackageName(), SysUtils.buildType());
		else
			return null;
	}

	public interface OnHostCallback
	{
		void onResult(String host);
	}

	public static void requestHost(final Context context, final int index, final OnHostCallback callback)
	{
		String url = App.hostFileAddress(context, index);
		if (!TextUtils.isEmpty(url))
		{
			LOG.v("requestHost : " + url);
			HttpUtils.doHttpGet(context, url, new HttpUtils.OnCallback()
			{
				@Override
				public void onResponse(String host)
				{
					if (!TextUtils.isEmpty(host))
					{
						if (callback != null)
							callback.onResult(host);
					}
					else
					{
						requestHost(context, index + 1, callback);
					}
				}
			});
		}
		else
		{
			if (callback != null)
				callback.onResult(null);
		}
	}

	@Override
	public void onCreate()
	{
		super.onCreate();
		if (getPackageName().equals(getCurProcessName(this)))
		{
			UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE, "");
			MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO);

//			Config.CurrTypeface = Typeface.createFromAsset(getAssets(), "msyhl.ttc");

//			if (BuildConfig.DEBUG)
//				HOST = HOST_INNER;
//			else
//				HOST = HOST_WAI;

//			Account = OnlyId;
//			Psw = "";

			LysIM.init(this);

			OssHelper.setup(new COssHelper(this));
			ModulePlayer.setup(new CModulePlayer(this));
			ModuleKit.setup(new CModuleKit(this));

			KitUtils.disableHome(this);

			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
			{
				ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
				connectivityManager.requestNetwork(new NetworkRequest.Builder().build(), new ConnectivityManager.NetworkCallback()
				{
					@Override
					public void onAvailable(Network network)
					{
						super.onAvailable(network);
						LOG.v("网络发生了变化:onAvailable");
						Intent i = new Intent();
						i.setAction(AppReceiver.Action_netChange(getContext()));
						sendBroadcast(i);
					}

					@Override
					public void onLosing(Network network, int maxMsToLive)
					{
						super.onLosing(network, maxMsToLive);
						LOG.v("网络发生了变化:onLosing");
					}

					@Override
					public void onLost(Network network)
					{
						super.onLost(network);
						LOG.v("网络发生了变化:onLost");
						Intent i = new Intent();
						i.setAction(AppReceiver.Action_netChange(getContext()));
						sendBroadcast(i);
					}

					@Override
					public void onUnavailable()
					{
						super.onUnavailable();
						LOG.v("网络发生了变化:onUnavailable");
					}

					@Override
					public void onCapabilitiesChanged(Network network, NetworkCapabilities networkCapabilities)
					{
						super.onCapabilitiesChanged(network, networkCapabilities);
						LOG.v("网络发生了变化:onCapabilitiesChanged");
					}

					@Override
					public void onLinkPropertiesChanged(Network network, LinkProperties linkProperties)
					{
						super.onLinkPropertiesChanged(network, linkProperties);
						LOG.v("网络发生了变化:onLinkPropertiesChanged");
					}
				});
			}
		}
	}

}
