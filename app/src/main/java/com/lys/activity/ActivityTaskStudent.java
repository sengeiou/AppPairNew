package com.lys.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.lys.App;
import com.lys.adapter.AdapterTaskStudent;
import com.lys.app.R;
import com.lys.base.utils.LOG;
import com.lys.kit.utils.Protocol;
import com.lys.protobuf.SHandleId;
import com.lys.protobuf.SPTask;
import com.lys.protobuf.SRequest_GetTaskList;
import com.lys.protobuf.SResponse_GetTaskList;
import com.lys.protobuf.SUser;
import com.lys.utils.UserCacheManager;

public class ActivityTaskStudent extends AppActivity implements View.OnClickListener
{
	private class Holder
	{
		private TextView title;
	}

	private Holder holder = new Holder();

	private void initHolder()
	{
		holder.title = findViewById(R.id.title);
	}

	private String userId;
	private String group;

	private RecyclerView recyclerView;
	private AdapterTaskStudent adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setStatusBarColor(0xfff3f3f3, false);
		setContentView(R.layout.activity_task_student);
		initHolder();
		requestPermission();
	}

	@Override
	public void permissionSuccess()
	{
		super.permissionSuccess();
		start();
	}

	@Override
	public void permissionFail()
	{
		super.permissionFail();
		start();
	}

	private void start()
	{
		userId = getIntent().getStringExtra("userId");
		group = getIntent().getStringExtra("group");

		UserCacheManager.instance().getUser(userId, new UserCacheManager.OnResult()
		{
			@Override
			public void result(SUser user)
			{
				if (user != null)
					holder.title.setText(String.format("%s -- %s", user.name, group));
			}
		});

		findViewById(R.id.note).setOnClickListener(this);
		findViewById(R.id.wrong).setOnClickListener(this);

		recyclerView = findViewById(R.id.recyclerView);
		recyclerView.setLayoutManager(new LinearLayoutManager(context));
		adapter = new AdapterTaskStudent(this);
		recyclerView.setAdapter(adapter);

		request();
	}

	@Override
	protected void onResume()
	{
		super.onResume();
		if (adapter != null)
			adapter.notifyDataSetChanged();
	}

	@Override
	public void onClick(View view)
	{
		if (view.getId() == R.id.note)
		{
			SPTask task = new SPTask();
			task.id = String.format("%s-笔记", group);
			task.userId = userId;
			task.group = group;
			task.name = String.format("%s-笔记", group);
			ActivityTaskBook.goinWithNone(context, task);
		}
		else if (view.getId() == R.id.wrong)
		{
			SPTask task = new SPTask();
			task.id = String.format("%s-错题", group);
			task.userId = userId;
			task.group = group;
			task.name = String.format("%s-错题", group);
			ActivityTaskBook.goinWithNone(context, task);
		}
	}

	private void request()
	{
		SRequest_GetTaskList request = new SRequest_GetTaskList();
		request.userId = userId;
		request.group = group;
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
						adapter.setData(response.tasks);
					}
				}
				else
				{
					LOG.toast(context, "加载失败");
				}
			}
		});
	}

}
