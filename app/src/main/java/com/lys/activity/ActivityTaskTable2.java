package com.lys.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.lys.App;
import com.lys.adapter.AdapterTaskTable2;
import com.lys.app.R;
import com.lys.base.utils.AppDataTool;
import com.lys.base.utils.JsonHelper;
import com.lys.base.utils.LOG;
import com.lys.kit.activity.KitActivity;
import com.lys.kit.utils.Protocol;
import com.lys.protobuf.SHandleId;
import com.lys.protobuf.SPTask;
import com.lys.protobuf.SRequest_GetTaskList;
import com.lys.protobuf.SResponse_GetTaskList;
import com.lys.protobuf.SUser;
import com.lys.utils.UserCacheManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityTaskTable2 extends KitActivity implements View.OnClickListener
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

	private List<String> userIds;

	private RecyclerView recyclerView;
	private AdapterTaskTable2 adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_task_table);
		initHolder();
		requestPermission();
	}

	@Override
	public void permissionSuccess()
	{
		super.permissionSuccess();

		userIds = AppDataTool.loadStringList(JsonHelper.getJSONArray(getIntent().getStringExtra("userIds")));

		findViewById(R.id.flush).setOnClickListener(this);

		recyclerView = findViewById(R.id.recyclerView);
		recyclerView.setLayoutManager(new GridLayoutManager(context, 4));
		adapter = new AdapterTaskTable2(this);
		recyclerView.setAdapter(adapter);

		requestUser();
	}

	@Override
	public void onClick(View view)
	{
		if (view.getId() == R.id.flush)
		{
			requestTask();
		}
	}

	private List<SUser> userList = new ArrayList<>();

	private void requestUser()
	{
		userList.clear();
		requestUser(0);
	}

	private void requestUser(final int index)
	{
		if (index < userIds.size())
		{
			UserCacheManager.instance().getUser(userIds.get(index), new UserCacheManager.OnResult()
			{
				@Override
				public void result(SUser user)
				{
					if (user != null)
					{
						userList.add(user);
						requestUser(index + 1);
					}
					else
					{
						LOG.toast(context, String.format("用户 %s 请求失败", userIds.get(index)));
					}
				}
			});
		}
		else
		{
			requestTask();
		}
	}

	public Map<String, List<SPTask>> taskMap = new HashMap<>();

	private void requestTask()
	{
//		taskMap.clear();
		requestTask(0);
	}

	private void requestTask(final int index)
	{
		if (index < userIds.size())
		{
			SRequest_GetTaskList request = new SRequest_GetTaskList();
			request.userId = userIds.get(index);
			Protocol.doPost(context, App.getApi(), SHandleId.GetTaskList, request.saveToStr(), new Protocol.OnCallback()
			{
				@Override
				public void onResponse(int code, String data, String msg)
				{
					if (code == 200)
					{
						SResponse_GetTaskList response = SResponse_GetTaskList.load(data);
						taskMap.put(userIds.get(index), response.tasks);
						requestTask(index + 1);
					}
				}
			});
		}
		else
		{
			adapter.setData(userList);
			LOG.toast(context, "刷新成功");
		}

//		TaskHelper.requestTaskTree(context, userId, new TaskHelper.onTaskTreeCallback()
//		{
//			@Override
//			public void onResult(List<TaskTreeNode> treeNodes)
//			{
//				if (treeNodes != null)
//					adapter.setData(treeNodes);
//			}
//		});
	}

	public void goinTask(String taskName)
	{
		List<String> userIds = new ArrayList<>();
		List<String> taskIds = new ArrayList<>();
		for (SUser user : userList)
		{
			List<SPTask> taskList = taskMap.get(user.id);
			List<SPTask> findTaskList = new ArrayList<>();
			for (SPTask task : taskList)
			{
				if (task.name.equals(taskName))
					findTaskList.add(task);
			}
			if (findTaskList.size() > 0)
			{
				userIds.add(user.id);
				taskIds.add(findTaskList.get(0).id);
			}
			if (findTaskList.size() > 1)
				LOG.toast(context, String.format("%s 有 %s 个此任务", user.name, findTaskList.size()));
		}
		Intent intent = new Intent(context, ActivityTaskAnalyze.class);
		intent.putExtra("taskName", taskName);
		intent.putExtra("userIds", AppDataTool.saveStringList(userIds).toString());
		intent.putExtra("taskIds", AppDataTool.saveStringList(taskIds).toString());
		startActivity(intent);
	}

//	public void add(final String groupText)
//	{
//		Helper.createTask(context, "创建任务", groupText, null, new Helper.OnCreateTaskListener()
//		{
//			@Override
//			public void onResult(String group, String name)
//			{
//				if (TextUtils.isEmpty(group))
//				{
//					LOG.toast(context, "分组不能为空");
//					return;
//				}
//				if (TextUtils.isEmpty(name))
//				{
//					LOG.toast(context, "名称不能为空");
//					return;
//				}
//				SRequest_CreateTask request = new SRequest_CreateTask();
//				request.userId = userId;
//				request.name = name;
//				request.group = group;
//				Protocol.doPost(context, App.getApi(), SHandleId.CreateTask, request.saveToStr(), new Protocol.OnCallback()
//				{
//					@Override
//					public void onResponse(int code, String data, String msg)
//					{
//						if (code == 200)
//						{
//							SResponse_CreateTask response = SResponse_CreateTask.load(data);
//							TaskHelper.addTaskNode(adapter.treeNodes, response.task);
//							adapter.notifyDataSetChanged();
//						}
//					}
//				});
//			}
//		});
//	}

}
