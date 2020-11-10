package com.lys.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.lys.App;
import com.lys.adapter.AdapterShopPair;
import com.lys.app.R;
import com.lys.dialog.DialogEditMatter;
import com.lys.kit.utils.Protocol;
import com.lys.protobuf.SHandleId;
import com.lys.protobuf.SMatter;
import com.lys.protobuf.SMatterListType;
import com.lys.protobuf.SMatterType;
import com.lys.protobuf.SRequest_AddModifyMatter;
import com.lys.protobuf.SRequest_GetMatterList;
import com.lys.protobuf.SResponse_GetMatterList;

public class ActivityShopPair extends AppActivity implements View.OnClickListener
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
	private AdapterShopPair adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shop_pair);
		initHolder();
		requestPermission();
	}

	@Override
	protected void onResume()
	{
		super.onResume();
//		adapter.notifyDataSetChanged();
	}

	@Override
	public void permissionSuccess()
	{
		super.permissionSuccess();

//		userId = getIntent().getStringExtra("userId");

		findViewById(R.id.add).setVisibility(App.isSupterMaster() ? View.VISIBLE : View.GONE);

		findViewById(R.id.add).setOnClickListener(this);

		recyclerView = findViewById(R.id.recyclerView);
		recyclerView.setLayoutManager(new LinearLayoutManager(context));
		adapter = new AdapterShopPair(this);
		recyclerView.setAdapter(adapter);

		request();
	}

	@Override
	public void onClick(View view)
	{
		if (view.getId() == R.id.add)
		{
			SMatter matter = new SMatter();
			matter.type = SMatterType.Pair;
			DialogEditMatter.show(context, matter, new DialogEditMatter.OnResultListener()
			{
				@Override
				public void onResult(SMatter matter)
				{
					SRequest_AddModifyMatter request = new SRequest_AddModifyMatter();
					request.matter = matter;
					Protocol.doPost(context, App.getApi(), SHandleId.AddModifyMatter, request.saveToStr(), new Protocol.OnCallback()
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
		SRequest_GetMatterList request = new SRequest_GetMatterList();
		request.type = SMatterListType.Pair;
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
