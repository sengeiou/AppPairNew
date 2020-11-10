package com.lys.utils;

import android.text.TextUtils;

import com.lys.App;
import com.lys.kit.utils.Protocol;
import com.lys.protobuf.SHandleId;
import com.lys.protobuf.SRequest_GetUser;
import com.lys.protobuf.SResponse_GetUser;
import com.lys.protobuf.SUser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class UserCacheManager
{
	private static UserCacheManager mInstance = null;

	public static UserCacheManager instance()
	{
		if (mInstance == null)
		{
			mInstance = new UserCacheManager();
			mInstance.init();
		}
		return mInstance;
	}

	private ConcurrentHashMap<String, SUser> cacheMap = new ConcurrentHashMap<>();

	private ConcurrentHashMap<String, List<OnResult>> tasks = new ConcurrentHashMap<>();

	private void init()
	{
	}

	public void release()
	{
		mInstance = null;
	}

	private void getUsersImpl(final List<String> userIds, final int index, final List<SUser> users, final OnResults callback)
	{
		if (index < userIds.size())
		{
			getUser(userIds.get(index), new UserCacheManager.OnResult()
			{
				@Override
				public void result(SUser user)
				{
					users.add(user);
					getUsersImpl(userIds, index + 1, users, callback);
				}
			});
		}
		else
		{
			if (callback != null)
				callback.result(users);
		}
	}

	public void getUsers(final List<String> userIds, final OnResults callback)
	{
		List<SUser> users = new ArrayList<>();
		getUsersImpl(userIds, 0, users, callback);
	}

	public void getUser(final String userId, final OnResult callback)
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
				SRequest_GetUser request = new SRequest_GetUser();
				request.userId = userId;
				Protocol.doPost(App.getContext(), App.getApi(), SHandleId.GetUser, request.saveToStr(), new Protocol.OnCallback()
				{
					@Override
					public void onResponse(int code, String data, String msg)
					{
						if (code == 200)
						{
							SResponse_GetUser response = SResponse_GetUser.load(data);
							if (response.user != null)
								cacheMap.put(userId, response.user);
						}
						result(userId);
					}
				});
			}
		}
	}

	private void result(String userId)
	{
		SUser user = null;
		if (cacheMap.containsKey(userId))
			user = cacheMap.get(userId);
		synchronized (tasks)
		{
			for (OnResult cb : tasks.remove(userId))
			{
				if (cb != null)
					cb.result(user);
			}
		}
	}

	public interface OnResult
	{
		void result(SUser user);
	}

	public interface OnResults
	{
		void result(List<SUser> users);
	}
}
