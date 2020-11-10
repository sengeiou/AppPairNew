package com.lys.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.lys.App;
import com.lys.adapter.AdapterTaskTable;
import com.lys.app.R;
import com.lys.base.utils.AppDataTool;
import com.lys.base.utils.CommonUtils;
import com.lys.base.utils.JsonHelper;
import com.lys.base.utils.LOG;
import com.lys.config.AppConfig;
import com.lys.kit.activity.KitActivity;
import com.lys.kit.dialog.DialogAlert;
import com.lys.kit.dialog.DialogWait;
import com.lys.kit.utils.Protocol;
import com.lys.protobuf.SEvent;
import com.lys.protobuf.SHandleId;
import com.lys.protobuf.SPTask;
import com.lys.protobuf.SRequest_GetEventList;
import com.lys.protobuf.SResponse_GetEventList;
import com.lys.utils.TaskCacheManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityTaskTable extends KitActivity implements View.OnClickListener
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

	private List<String> mUserIds;

	private RecyclerView recyclerView;
	private AdapterTaskTable adapter;

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

		String groupName = getIntent().getStringExtra("groupName");
		mUserIds = AppDataTool.loadStringList(JsonHelper.getJSONArray(getIntent().getStringExtra("userIds")));

		holder.title.setText(String.format("任务表 -- %s(%s)", groupName, mUserIds.size()));

		findViewById(R.id.flush).setOnClickListener(this);

		recyclerView = findViewById(R.id.recyclerView);
		recyclerView.setLayoutManager(new GridLayoutManager(context, 4));
		adapter = new AdapterTaskTable(this);
		recyclerView.setAdapter(adapter);

		adapter.setData(mUserIds);
	}

	@Override
	public void onClick(View view)
	{
		if (view.getId() == R.id.flush)
		{
		}
	}

	public TaskCacheManager taskCache = new TaskCacheManager();

	private void goinAnalyze(final int index, final String taskName)
	{
		if (index < mUserIds.size())
		{
			DialogWait.message(String.format("正在分析（%s/%s）", index + 1, mUserIds.size()));
			taskCache.getTasks(mUserIds.get(index), new TaskCacheManager.OnResult()
			{
				@Override
				public void result(List<SPTask> taskList)
				{
					if (taskList != null)
					{
						goinAnalyze(index + 1, taskName);
					}
					else
					{
						DialogWait.close();
						LOG.toast(context, "分析失败");
					}
				}
			});
		}
		else
		{
			DialogWait.close();
			List<String> userIds = new ArrayList<>();
			List<String> taskIds = new ArrayList<>();
			for (String userId : mUserIds)
			{
				List<SPTask> taskList = taskCache.getTasks(userId);
				List<SPTask> findTaskList = new ArrayList<>();
				for (SPTask task : taskList)
				{
					if (task.name.equals(taskName))
						findTaskList.add(task);
				}
				if (findTaskList.size() > 0)
				{
					userIds.add(userId);
					taskIds.add(findTaskList.get(0).id);
				}
				if (findTaskList.size() > 1)
					LOG.toast(context, String.format("%s 有 %s 个此任务", userId, findTaskList.size()));
			}
			Intent intent = new Intent(context, ActivityTaskAnalyze.class);
			intent.putExtra("taskName", taskName);
			intent.putExtra("userIds", AppDataTool.saveStringList(userIds).toString());
			intent.putExtra("taskIds", AppDataTool.saveStringList(taskIds).toString());
			startActivity(intent);
		}
	}

	public void goinAnalyze(String taskName)
	{
		DialogWait.show(context, "分析中。。。");
		goinAnalyze(0, taskName);
	}

	private void goinTongji(final int index, final String taskName)
	{
		if (index < mUserIds.size())
		{
			DialogWait.message(String.format("获取任务（%s/%s）", index + 1, mUserIds.size()));
			taskCache.getTasks(mUserIds.get(index), new TaskCacheManager.OnResult()
			{
				@Override
				public void result(List<SPTask> taskList)
				{
					if (taskList != null)
					{
						goinTongji(index + 1, taskName);
					}
					else
					{
						DialogWait.close();
						LOG.toast(context, "统计失败");
					}
				}
			});
		}
		else
		{
			List<String> userIds = new ArrayList<>();
			List<String> taskIds = new ArrayList<>();
			for (String userId : mUserIds)
			{
				List<SPTask> taskList = taskCache.getTasks(userId);
				List<SPTask> findTaskList = new ArrayList<>();
				for (SPTask task : taskList)
				{
					if (task.name.equals(taskName))
						findTaskList.add(task);
				}
				if (findTaskList.size() > 0)
				{
					userIds.add(userId);
					taskIds.add(findTaskList.get(0).id);
				}
				if (findTaskList.size() > 1)
					LOG.toast(context, String.format("%s 有 %s 个此任务", userId, findTaskList.size()));
			}

			Map<String, List<SEvent>> eventMap = new HashMap<>();
			requestEvent(0, eventMap, taskName, userIds, taskIds);
		}
	}

	private void requestEvent(final int index, final Map<String, List<SEvent>> eventMap, final String taskName, final List<String> userIds, final List<String> taskIds)
	{
		if (index < userIds.size())
		{
			DialogWait.message(String.format("获取日志（%s/%s）", index + 1, userIds.size()));
			SRequest_GetEventList request = new SRequest_GetEventList();
			request.userId = userIds.get(index);
			request.actions.add(AppConfig.EventAction_InTask);
			request.actions.add(AppConfig.EventAction_OutTask);
			request.actions.add(AppConfig.EventAction_CommitJob);
			request.targets.add(taskIds.get(index));
			Protocol.doPost(context, App.getApi(), SHandleId.GetEventList, request.saveToStr(), new Protocol.OnCallback()
			{
				@Override
				public void onResponse(int code, String data, String msg)
				{
					if (code == 200)
					{
						SResponse_GetEventList response = SResponse_GetEventList.load(data);
						eventMap.put(userIds.get(index), response.events);
						requestEvent(index + 1, eventMap, taskName, userIds, taskIds);
					}
					else
					{
						DialogWait.close();
						LOG.toast(context, "统计失败");
					}
				}
			});
		}
		else
		{
			DialogWait.close();

			int openCount = 0;
			long openAllTime = 0;
			int commitCount = 0;

			for (String userId : userIds)
			{
				List<SEvent> events = eventMap.get(userId);

				if (isOpenTask(events))
					openCount++;

				openAllTime += getOpenTime(events);

				if (isCommitJob(events))
					commitCount++;
			}

			StringBuilder sb = new StringBuilder();
			sb.append(String.format("总人数：%s, 任务数：%s\r\n", mUserIds.size(), userIds.size()));
			sb.append(String.format("打开数：%s, 打开率：%s%%\r\n", openCount, openCount * 100 / userIds.size()));
			sb.append(String.format("打开总时长：%s, 打开平均时长：%s\r\n", CommonUtils.formatTime(openAllTime), CommonUtils.formatTime(openAllTime / openCount)));
			sb.append(String.format("提交数：%s, 提交率：%s%%\r\n", commitCount, commitCount * 100 / userIds.size()));
			DialogAlert.show(context, taskName, sb.toString(), null);
		}
	}

	private boolean isCommitJob(List<SEvent> events)
	{
		for (int i = 0; i < events.size(); i++)
		{
			SEvent event = events.get(i);
			if (event.action.equals(AppConfig.EventAction_CommitJob))
			{
				return true;
			}
		}
		return false;
	}

	private boolean isOpenTask(List<SEvent> events)
	{
		for (int i = 0; i < events.size(); i++)
		{
			SEvent event = events.get(i);
			if (event.action.equals(AppConfig.EventAction_InTask))
			{
				return true;
			}
		}
		return false;
	}

	private long getOpenTime(List<SEvent> events)
	{
		long allTime = 0;
		long lastInTime = -1;
		for (int i = 0; i < events.size(); i++)
		{
			SEvent event = events.get(i);
			if (event.action.equals(AppConfig.EventAction_InTask))
			{
				lastInTime = event.time;
			}
			else if (event.action.equals(AppConfig.EventAction_OutTask))
			{
				if (lastInTime != -1)
				{
					long dtTime = event.time - lastInTime;
					allTime += dtTime;
				}
				lastInTime = -1;
			}
		}
		return allTime;
	}

	public void goinTongji(String taskName)
	{
		DialogWait.show(context, "统计中。。。");
		goinTongji(0, taskName);
	}

}
