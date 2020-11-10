package com.lys.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.lys.App;
import com.lys.adapter.AdapterShopHome;
import com.lys.app.R;
import com.lys.kit.utils.Protocol;
import com.lys.protobuf.SHandleId;
import com.lys.protobuf.SMatterListType;
import com.lys.protobuf.SRequest_GetMatterList;
import com.lys.protobuf.SResponse_GetMatterList;

public class ActivityShopHome extends AppActivity
{
	private class Holder
	{
//		private TextView title;
	}

	private Holder holder = new Holder();

	private void initHolder()
	{
//		holder.title = findViewById(R.id.title);
	}

//	private String userId;

	private RecyclerView recyclerView;
	private AdapterShopHome adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shop_home);
		initHolder();
		requestPermission();
	}

	@Override
	protected void onResume()
	{
		super.onResume();
		if (App.isSupterMaster())
			request();
	}

	@Override
	public void permissionSuccess()
	{
		super.permissionSuccess();

//		userId = getIntent().getStringExtra("userId");

		recyclerView = findViewById(R.id.recyclerView);
		recyclerView.setLayoutManager(new LinearLayoutManager(context));
		adapter = new AdapterShopHome(this);
		recyclerView.setAdapter(adapter);

		request();
	}

	public void request()
	{
		SRequest_GetMatterList request = new SRequest_GetMatterList();
		request.type = SMatterListType.Home;
		request.containInvalid = App.isSupterMaster();
		Protocol.doPost(context, App.getApi(), SHandleId.GetMatterList, request.saveToStr(), new Protocol.OnCallback()
		{
			@Override
			public void onResponse(int code, String data, String msg)
			{
				if (code == 200)
				{
					SResponse_GetMatterList response = SResponse_GetMatterList.load(data);
					adapter.setData(response.matters);
				}
			}
		});
	}

}
