package com.lys.utils;

import android.text.TextUtils;

import com.lys.App;
import com.lys.kit.utils.Protocol;
import com.lys.protobuf.SHandleId;
import com.lys.protobuf.SPTask;
import com.lys.protobuf.SRequest_GetTaskList;
import com.lys.protobuf.SResponse_GetTaskList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class TaskCacheManager
{
	private ConcurrentHashMap<String, List<SPTask>> cacheMap = new ConcurrentHashMap<>();

	private ConcurrentHashMap<String, List<OnResult>> tasks = new ConcurrentHashMap<>();

	public List<SPTask> getTasks(final String userId)
	{
		if (cacheMap.containsKey(userId))
			return cacheMap.get(userId);
		return null;
	}

	public void getTasks(final String userId, final OnResult callback)
	{
		if (TextUtils.isEmpty(userId))
		{
			if (callback != null)
				callback.result(null);
			return;
		}

		if (cacheMap.containsKey(userId))
		{
			if (callback != null)
				callback.result(cacheMap.get(userId));
			return;
		}

		synchronized (tasks)
		{
			if (tasks.containsKey(userId))
			{
				tasks.get(userId).add(callback);
			}
			else
			{
				tasks.put(userId, Collections.synchronizedList(new ArrayList<OnResult>()));
				tasks.get(userId).add(callback);
				SRequest_GetTaskList request = new SRequest_GetTaskList();
				request.userId = userId;
				Protocol.doPost(App.getContext(), App.getApi(), SHandleId.GetTaskList, request.saveToStr(), new Protocol.OnCallback()
				{
					@Override
					public void onResponse(int code, String data, String msg)
					{
						if (code == 200)
						{
							SResponse_GetTaskList response = SResponse_GetTaskList.load(data);
							cacheMap.put(userId, response.tasks);
						}
						result(userId);
					}
				});
			}
		}
	}

	private void result(String userId)
	{
		List<SPTask> taskList = null;
		if (cacheMap.containsKey(userId))
			taskList = cacheMap.get(userId);
		synchronized (tasks)
		{
			for (OnResult cb : tasks.remove(userId))
			{
				if (cb != null)
					cb.result(taskList);
			}
		}
	}

	public interface OnResult
	{
		void result(List<SPTask> taskList);
	}
}
