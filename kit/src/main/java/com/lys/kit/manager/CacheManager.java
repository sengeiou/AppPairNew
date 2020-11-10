package com.lys.kit.manager;

import com.lys.base.utils.CommonUtils;

import java.util.concurrent.ConcurrentHashMap;

public class CacheManager
{
	private static CacheManager mInstance = null;

	public static CacheManager instance()
	{
		if (mInstance == null)
		{
			mInstance = new CacheManager();
			mInstance.init();
		}
		return mInstance;
	}

	private ConcurrentHashMap<String, Object> cacheMap = new ConcurrentHashMap<String, Object>();

	private void init()
	{
	}

	public void release()
	{
		mInstance = null;
	}

	public String putCache(Object obj)
	{
		String id = CommonUtils.uuid();
		cacheMap.put(id, obj);
		return id;
	}

	private <T> T getCache(String id)
	{
		if (cacheMap.containsKey(id))
			return (T) cacheMap.get(id);
		else
			return null;
	}

	public <T> T getAndRemoveCache(String id)
	{
		if (cacheMap.containsKey(id))
			return (T) cacheMap.remove(id);
		else
			return null;
	}
}
