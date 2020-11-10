package com.lys.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.lys.App;
import com.lys.adapter.AdapterShopDetail;
import com.lys.app.R;
import com.lys.kit.utils.Protocol;
import com.lys.protobuf.SHandleId;
import com.lys.protobuf.SMatter;
import com.lys.protobuf.SRequest_GetCommentList;
import com.lys.protobuf.SResponse_GetCommentList;

public class ActivityShopDetail extends AppActivity implements View.OnClickListener
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

	public SMatter matter;

	private RecyclerView recyclerView;
	private AdapterShopDetail adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shop_detail);
		initHolder();
		requestPermission();
	}

	@Override
	public void permissionSuccess()
	{
		super.permissionSuccess();

		matter = SMatter.load(getIntent().getStringExtra("matter"));

		findViewById(R.id.im).setOnClickListener(this);
		findViewById(R.id.buy).setOnClickListener(this);

		recyclerView = findViewById(R.id.recyclerView);
		recyclerView.setLayoutManager(new LinearLayoutManager(context));
		adapter = new AdapterShopDetail(this);
		recyclerView.setAdapter(adapter);

		adapter.setDetailData(matter.details);

		request();
	}

	@Override
	public void onClick(View view)
	{
		if (view.getId() == R.id.im)
		{

		}
		else if (view.getId() == R.id.buy)
		{

		}
	}

	public void request()
	{
		SRequest_GetCommentList request = new SRequest_GetCommentList();
		request.matterId = matter.id;
		request.containAll = !App.isStudent();
		Protocol.doPost(context, App.getApi(), SHandleId.GetCommentList, request.saveToStr(), new Protocol.OnCallback()
		{
			@Override
			public void onResponse(int code, String data, String msg)
			{
				if (code == 200)
				{
					SResponse_GetCommentList response = SResponse_GetCommentList.load(data);
					adapter.setCommentData(response.comments);
				}
			}
		});
	}

}
