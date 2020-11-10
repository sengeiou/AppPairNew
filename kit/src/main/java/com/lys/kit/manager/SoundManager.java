package com.lys.kit.manager;

import android.content.Context;
import android.media.SoundPool;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

// 音效管理
public class SoundManager
{
	private static SoundManager mInstance = null;

	public static SoundManager instance()
	{
		if (mInstance == null)
		{
			mInstance = new SoundManager();
			mInstance.init();
		}
		return mInstance;
	}

	private SoundPool mSoundPool = null;
	private Map<Integer, Integer> mMap = new ConcurrentHashMap<>();

	private void init()
	{
		SoundPool.Builder builder = new SoundPool.Builder();
		mSoundPool = builder.build();
		mSoundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener()
		{
			@Override
			public void onLoadComplete(SoundPool soundPool, int sampleId, int status)
			{
				mSoundPool.play(sampleId, 1, 1, 0, 0, 1);
			}
		});
	}

	public void release()
	{
		mSoundPool.release();
		mInstance = null;
	}

	public void play(Context context, int resId)
	{
		if (!mMap.containsKey(resId))
		{
			mMap.put(resId, mSoundPool.load(context, resId, 1));
		}
		else
		{
			mSoundPool.play(mMap.get(resId), 1, 1, 0, 0, 1);
		}
	}
}
