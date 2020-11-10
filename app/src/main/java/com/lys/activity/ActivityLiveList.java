package com.lys.activity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.lys.App;
import com.lys.adapter.AdapterLiveList;
import com.lys.app.R;
import com.lys.dialog.DialogEditLive;
import com.lys.kit.utils.Protocol;
import com.lys.protobuf.SHandleId;
import com.lys.protobuf.SLiveTask;
import com.lys.protobuf.SRequest_LiveAddModify;
import com.lys.protobuf.SRequest_LiveGetAll;
import com.lys.protobuf.SRequest_LiveGetList;
import com.lys.protobuf.SResponse_LiveGetAll;
import com.lys.protobuf.SResponse_LiveGetList;

public class ActivityLiveList extends AppActivity implements View.OnClickListener
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

	private String userId;

	private RecyclerView recyclerView;
	private AdapterLiveList adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_live_list);
		initHolder();
		requestPermission();
	}

	@Override
	protected void onResume()
	{
		super.onResume();
		adapter.notifyDataSetChanged();
	}

	@Override
	public void permissionSuccess()
	{
		super.permissionSuccess();

		userId = getIntent().getStringExtra("userId");

		findViewById(R.id.add).setVisibility(App.isSupterMaster() ? View.VISIBLE : View.GONE);

		findViewById(R.id.add).setOnClickListener(this);

		recyclerView = findViewById(R.id.recyclerView);
		recyclerView.setLayoutManager(new GridLayoutManager(context, 4));
		adapter = new AdapterLiveList(this);
		recyclerView.setAdapter(adapter);

		request();
	}

	@Override
	public void onClick(View view)
	{
		if (view.getId() == R.id.add)
		{
			DialogEditLive.show(context, null, new DialogEditLive.OnResultListener()
			{
				@Override
				public void onResult(SLiveTask live)
				{
					SRequest_LiveAddModify request = new SRequest_LiveAddModify();
					request.live = live;
					Protocol.doPost(context, App.getApi(), SHandleId.LiveAddModify, request.saveToStr(), new Protocol.OnCallback()
					{
						@Override
						public void onResponse(int code, String data, String msg)
						{
							if (code == 200)
							{
								request();
							}
						}
					});
				}
			});
		}
	}

	public void request()
	{
		if (App.isSupterMaster())
		{
			SRequest_LiveGetAll request = new SRequest_LiveGetAll();
			Protocol.doPost(context, App.getApi(), SHandleId.LiveGetAll, request.saveToStr(), new Protocol.OnCallback()
			{
				@Override
				public void onResponse(int code, String data, String msg)
				{
					if (code == 200)
					{
						SResponse_LiveGetAll response = SResponse_LiveGetAll.load(data);
						adapter.setData(response.lives);
					}
				}
			});
		}
		else
		{
			SRequest_LiveGetList request = new SRequest_LiveGetList();
			request.userId = userId;
			Protocol.doPost(context, App.getApi(), SHandleId.LiveGetList, request.saveToStr(), new Protocol.OnCallback()
			{
				@Override
				public void onResponse(int code, String data, String msg)
				{
					if (code == 200)
					{
						SResponse_LiveGetList response = SResponse_LiveGetList.load(data);
						adapter.setData(response.lives);
					}
				}
			});
		}
	}

}
