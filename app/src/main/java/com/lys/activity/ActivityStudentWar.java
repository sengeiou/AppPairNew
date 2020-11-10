package com.lys.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.lys.App;
import com.lys.adapter.AdapterStudentWar;
import com.lys.app.R;
import com.lys.kit.activity.KitActivity;
import com.lys.kit.utils.Protocol;
import com.lys.protobuf.SHandleId;
import com.lys.protobuf.SRequest_GetTaskList;
import com.lys.protobuf.SResponse_GetTaskList;

public class ActivityStudentWar extends KitActivity implements View.OnClickListener
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
	private AdapterStudentWar adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_student_war);
		initHolder();
		requestPermission();
	}

	@Override
	public void permissionSuccess()
	{
		super.permissionSuccess();

		userId = getIntent().getStringExtra("userId");

		findViewById(R.id.history).setOnClickListener(this);

		recyclerView = findViewById(R.id.recyclerView);
		recyclerView.setLayoutManager(new LinearLayoutManager(context));
		adapter = new AdapterStudentWar(false);
		recyclerView.setAdapter(adapter);

		requestTaskList();
	}

	@Override
	public void onClick(View view)
	{
		if (view.getId() == R.id.history)
		{
			Intent intent = new Intent(context, ActivityStudentWarOver.class);
			intent.putExtra("userId", userId);
			startActivity(intent);
		}
	}

	private void requestTaskList()
	{
		SRequest_GetTaskList request = new SRequest_GetTaskList();
		request.userId = userId;
		request.overType = 1;
		Protocol.doPost(context, App.getApi(), SHandleId.GetTaskList, request.saveToStr(), new Protocol.OnCallback()
		{
			@Override
			public void onResponse(int code, String data, String msg)
			{
				if (code == 200)
				{
					SResponse_GetTaskList response = SResponse_GetTaskList.load(data);
					adapter.setData(response.tasks);
				}
			}
		});
	}

}
