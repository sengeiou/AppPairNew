package com.lys.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.lys.App;
import com.lys.adapter.AdapterAppMarket;
import com.lys.app.R;
import com.lys.kit.activity.KitActivity;
import com.lys.kit.utils.Protocol;
import com.lys.protobuf.SHandleId;
import com.lys.protobuf.SRequest_GetAppInfoList;
import com.lys.protobuf.SResponse_GetAppInfoList;
import com.lys.receiver.AppReceiver;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityMarket extends KitActivity implements View.OnClickListener
{
	private class Holder
	{
//		private EditText schoolName;
	}

	private Holder holder = new Holder();

	private void initHolder()
	{
//		holder.schoolName = findViewById(R.id.schoolName);
	}

	private RecyclerView recyclerView;
	private AdapterAppMarket adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_market);
		initHolder();
		requestPermission();
	}

	@Override
	public void permissionSuccess()
	{
		super.permissionSuccess();
//		findViewById(R.id.submit).setOnClickListener(this);

		initInstalledMap();

		recyclerView = findViewById(R.id.recyclerView);
		recyclerView.setLayoutManager(new GridLayoutManager(context, 2));
		adapter = new AdapterAppMarket(this, installedMap);
		recyclerView.setAdapter(adapter);

		IntentFilter filter = new IntentFilter();
		filter.addAction(AppReceiver.Action_installApp(context));
		filter.addAction(AppReceiver.Action_uninstallApp(context));
		registerReceiver(mReceiver, filter);

		request();
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
//		if (view.getId() == R.id.submit)
//		{
//		}
	}

	private Map<String, String> installedMap = new HashMap<>();

	private void initInstalledMap()
	{
		List<PackageInfo> packageInfos = getPackageManager().getInstalledPackages(0);
		for (PackageInfo packageInfo : packageInfos)
		{
			installedMap.put(packageInfo.packageName, packageInfo.versionName + "+" + packageInfo.versionCode);
		}
	}

	private void request()
	{
		SRequest_GetAppInfoList request = new SRequest_GetAppInfoList();
		request.channel = "market";
		Protocol.doPost(context, App.getApi(), SHandleId.GetAppInfoList, request.saveToStr(), new Protocol.OnCallback()
		{
			@Override
			public void onResponse(int code, String data, String msg)
			{
				if (code == 200)
				{
					SResponse_GetAppInfoList response = SResponse_GetAppInfoList.load(data);
					adapter.setData(response.apps);
				}
			}
		});
	}

	private BroadcastReceiver mReceiver = new BroadcastReceiver()
	{
		@Override
		public void onReceive(Context context, Intent intent)
		{
//			LOG.v("Action:" + intent.getAction());
			if (intent.getAction().equals(AppReceiver.Action_installApp(context)))
			{
				String packageName = intent.getStringExtra("packageName");
				try
				{
					PackageInfo packageInfo = getPackageManager().getPackageInfo(packageName, PackageManager.GET_META_DATA);
					installedMap.put(packageInfo.packageName, packageInfo.versionName + "+" + packageInfo.versionCode);
					adapter.flush(packageInfo.packageName);
				}
				catch (PackageManager.NameNotFoundException e)
				{
					e.printStackTrace();
				}
			}
			else if (intent.getAction().equals(AppReceiver.Action_uninstallApp(context)))
			{
				String packageName = intent.getStringExtra("packageName");
				installedMap.remove(packageName);
				adapter.flush(packageName);
			}
		}
	};

}
