package com.lys.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

import com.lys.base.utils.LOG;

public class AppReceiver extends BroadcastReceiver
{
	public static String Action_installApp(Context context)
	{
		return context.getPackageName() + "." + AppReceiver.class.getName() + ".installApp";
	}

	public static String Action_uninstallApp(Context context)
	{
		return context.getPackageName() + "." + AppReceiver.class.getName() + ".uninstallApp";
	}

	public static String Action_netChange(Context context)
	{
		return context.getPackageName() + "." + AppReceiver.class.getName() + ".netChange";
	}

	@Override
	public void onReceive(Context context, Intent intent)
	{
		String action = intent.getAction();
		if (action.equals(Intent.ACTION_PACKAGE_ADDED))
		{
			String packageName = intent.getData().getSchemeSpecificPart();
			LOG.v("安装了：" + packageName);
			Intent i = new Intent();
			i.setAction(Action_installApp(context));
			i.putExtra("packageName", packageName);
			context.sendBroadcast(i);
		}
		else if (action.equals(Intent.ACTION_PACKAGE_REMOVED))
		{
			String packageName = intent.getData().getSchemeSpecificPart();
			LOG.v("卸载了：" + packageName);
			Intent i = new Intent();
			i.setAction(Action_uninstallApp(context));
			i.putExtra("packageName", packageName);
			context.sendBroadcast(i);
		}
		else if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION))
		{
			LOG.v("网络发生了变化");
			Intent i = new Intent();
			i.setAction(Action_netChange(context));
			context.sendBroadcast(i);
		}
	}

}
