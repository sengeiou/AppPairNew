package com.lys.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.lys.App;
import com.lys.adapter.AdapterTaskGroup;
import com.lys.app.R;
import com.lys.config.AppConfig;
import com.lys.kit.activity.KitActivity;
import com.lys.kit.utils.Protocol;
import com.lys.protobuf.SHandleId;
import com.lys.protobuf.SPTask;
import com.lys.protobuf.SRequest_GetTaskGroupList;
import com.lys.protobuf.SRequest_GetTaskList;
import com.lys.protobuf.SResponse_GetTaskGroupList;
import com.lys.protobuf.SResponse_GetTaskList;
import com.lys.protobuf.STaskGroup;
import com.lys.protobuf.SUser;
import com.lys.utils.UserCacheManager;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class ActivityTaskGroup extends KitActivity implements View.OnClickListener
{
	private class Holder
	{
		private TextView title;
		private TextView notWar;
	}

	private Holder holder = new Holder();

	private void initHolder()
	{
		holder.title = findViewById(R.id.title);
		holder.notWar = findViewById(R.id.notWar);
	}

	public String userId;

	private RecyclerView recyclerView;
	private AdapterTaskGroup adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_task_group);
		initHolder();
		requestPermission();
	}

	@Override
	public void permissionSuccess()
	{
		super.permissionSuccess();

		userId = getIntent().getStringExtra("userId");

		UserCacheManager.instance().getUser(userId, new UserCacheManager.OnResult()
		{
			@Override
			public void result(SUser user)
			{
				if (user != null)
					holder.title.setText(user.name);
			}
		});

		recyclerView = findViewById(R.id.recyclerView);
		recyclerView.setLayoutManager(new LinearLayoutManager(context));
		adapter = new AdapterTaskGroup(this);
		recyclerView.setAdapter(adapter);

		holder.notWar.setVisibility(View.GONE);

		request();
	}

	@Override
	public void onClick(View view)
	{
//		if (view.getId() == R.id.history)
//		{
//			Intent intent = new Intent(context, ActivityStudentWarOver.class);
//			intent.putExtra("userId", userId);
//			startActivity(intent);
//		}
	}

	public void request()
	{
		SRequest_GetTaskGroupList request = new SRequest_GetTaskGroupList();
		Protocol.doPost(context, App.getApi(), SHandleId.GetTaskGroupList, request.saveToStr(), new Protocol.OnCallback()
		{
			@Override
			public void onResponse(int code, String data, String msg)
			{
				if (code == 200)
				{
					SResponse_GetTaskGroupList response = SResponse_GetTaskGroupList.load(data);
					requestTaskList(response.taskGroupList);
				}
			}
		});
	}

	private void requestTaskList(final List<STaskGroup> taskGroupList)
	{
		SRequest_GetTaskList request = new SRequest_GetTaskList();
		request.userId = userId;
		request.prev = true;
		Protocol.doPost(context, App.getApi(), SHandleId.GetTaskList, request.saveToStr(), new Protocol.OnCallback()
		{
			@Override
			public void onResponse(int code, String data, String msg)
			{
				if (code == 200)
				{
					SResponse_GetTaskList response = SResponse_GetTaskList.load(data);

					HashMap<String, Integer> mapCount = new HashMap<>();
					for (SPTask task : response.tasks)
					{
						if (mapCount.containsKey(task.group))
							mapCount.put(task.group, mapCount.get(task.group) + 1);
						else
							mapCount.put(task.group, 1);
					}

					HashMap<String, Integer> mapNew = new HashMap<>();
					for (SPTask task : response.tasks)
					{
						File file = new File(AppConfig.getTaskDir(task));
						if (!file.exists())
						{
							if (mapNew.containsKey(task.group))
								mapNew.put(task.group, mapNew.get(task.group) + 1);
							else
								mapNew.put(task.group, 1);
						}
					}

					List<STaskGroup> filterTaskGroupList = new ArrayList<>();
					for (STaskGroup taskGroup : taskGroupList)
					{
						if (mapCount.containsKey(taskGroup.name))
						{
							taskGroup.allCount = mapCount.get(taskGroup.name);
							filterTaskGroupList.add(taskGroup);
							if (mapNew.containsKey(taskGroup.name))
								taskGroup.newCount = mapNew.get(taskGroup.name);
						}
					}

					HashMap<String, Boolean> mapGroup = new HashMap<>();
					for (STaskGroup taskGroup : filterTaskGroupList)
					{
						mapGroup.put(taskGroup.name, true);
					}

					ArrayList<String> keyList = new ArrayList<>(mapCount.keySet());
					Collections.sort(keyList, new Comparator<String>()
					{
						@Override
						public int compare(String key1, String key2)
						{
							return key1.compareTo(key2);
						}
					});
					for (String key : keyList)
					{
						if (!mapGroup.containsKey(key))
						{
							STaskGroup taskGroup = new STaskGroup();

							taskGroup.name = key;
							taskGroup.important = 3;
							taskGroup.difficulty = 3;
							taskGroup.cover = "http://file.k12-eco.com/UI/default_math_cover.png";

							taskGroup.allCount = mapCount.get(taskGroup.name);
							filterTaskGroupList.add(taskGroup);
							if (mapNew.containsKey(taskGroup.name))
								taskGroup.newCount = mapNew.get(taskGroup.name);
						}
					}

					if (filterTaskGroupList.size() > 0)
						holder.notWar.setVisibility(View.GONE);
					else
						holder.notWar.setVisibility(View.VISIBLE);
					adapter.setData(filterTaskGroupList);
				}
			}
		});
	}

}
