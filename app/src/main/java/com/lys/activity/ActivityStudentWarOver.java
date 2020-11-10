package com.lys.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.lys.App;
import com.lys.adapter.AdapterStudentWar;
import com.lys.app.R;
import com.lys.base.utils.LOG;
import com.lys.kit.activity.KitActivity;
import com.lys.kit.utils.Protocol;
import com.lys.protobuf.SHandleId;
import com.lys.protobuf.SRequest_GetTaskList;
import com.lys.protobuf.SResponse_GetTaskList;

public class ActivityStudentWarOver extends KitActivity
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
		setContentView(R.layout.activity_student_war_over);
		initHolder();
		requestPermission();
	}

	@Override
	public void permissionSuccess()
	{
		super.permissionSuccess();

		userId = getIntent().getStringExtra("userId");

		recyclerView = findViewById(R.id.recyclerView);
		recyclerView.setLayoutManager(new LinearLayoutManager(context));
		adapter = new AdapterStudentWar(true);
		recyclerView.setAdapter(adapter);

		recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener()
		{
			@Override
			public void onScrollStateChanged(RecyclerView recyclerView, int newState)
			{
				super.onScrollStateChanged(recyclerView, newState);
				if (newState == RecyclerView.SCROLL_STATE_IDLE)
				{
					LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
					if (layoutManager.findLastVisibleItemPosition() == recyclerView.getLayoutManager().getItemCount() - 1)
					{
						LOG.v("到底了");
						request();
					}
				}
			}
		});

		request();
	}

	private static final int PageSize = 20;

	public void request()
	{
		if (adapter.state == AdapterStudentWar.State_NotMore || adapter.state == AdapterStudentWar.State_Loading)
			return;
		adapter.setState(AdapterStudentWar.State_Loading, "加载中。。。");
		SRequest_GetTaskList request = new SRequest_GetTaskList();
		request.userId = userId;
		request.overType = 2;
		request.sortType = 1;
		if (adapter.getLast() != null)
			request.overTime = adapter.getLast().overTime;
		request.pageSize = PageSize;
		Protocol.doPost(context, App.getApi(), SHandleId.GetTaskList, request.saveToStr(), new Protocol.OnCallback()
		{
			@Override
			public void onResponse(int code, String data, String msg)
			{
				if (code == 200)
				{
					SResponse_GetTaskList response = SResponse_GetTaskList.load(data);
					if (response.tasks.size() > 0)
					{
						adapter.setState(AdapterStudentWar.State_Normal, "");
						adapter.addData(response.tasks);
					}
					else
					{
						adapter.setState(AdapterStudentWar.State_NotMore, "---已经到底了---");
//						LOG.toast(context, "已经到底了");
					}
				}
				else
				{
					adapter.setState(AdapterStudentWar.State_LoadFail, "加载失败");
					LOG.toast(context, "加载失败");
				}
			}
		});
	}

}
