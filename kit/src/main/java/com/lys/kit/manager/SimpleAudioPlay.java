package com.lys.kit.manager;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.text.TextUtils;

import com.lys.base.utils.LOG;

// 用于背单词
public class SimpleAudioPlay
{
	private static SimpleAudioPlay mInstance = null;

	public static SimpleAudioPlay instance()
	{
		if (mInstance == null)
		{
			mInstance = new SimpleAudioPlay();
			mInstance.init();
		}
		return mInstance;
	}

	private MediaPlayer mPlayer = null;

	private void init()
	{
		mPlayer = new MediaPlayer();
		mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener()
		{
			@Override
			public void onCompletion(MediaPlayer mediaPlayer)
			{
				LOG.v("onCompletion");
				stop();
			}
		});
		mPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener()
		{
			@Override
			public boolean onError(MediaPlayer mediaPlayer, int what, int extra)
			{
				LOG.v(String.format("onError what = %d, extra = %d", what, extra));
				LOG.toast(mCurrContext, "播放错误");
				if (mCurrListener != null)
					mCurrListener.error();
				stop();
				return true;
			}
		});
		mPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener()
		{
			@Override
			public void onPrepared(MediaPlayer mediaPlayer)
			{
				LOG.v("onPrepared");
				mPlayer.start();
			}
		});
	}

	public void release()
	{
		stop();
		mPlayer.release();
		mInstance = null;
	}

	private Context mCurrContext = null;
	private OnListener mCurrListener = null;

	public void stop()
	{
		try
		{
			mPlayer.stop();
			mPlayer.reset();
		}
		catch (Exception e)
		{
			LOG.v("stop Exception");
			e.printStackTrace();
		}
	}

	public void play(Context context, String url, OnListener listener)
	{
		LOG.v("play:" + url);
		stop();
		if (TextUtils.isEmpty(url))
		{
			LOG.toast(context, "url为空，无法播放");
			return;
		}
		mCurrContext = context;
		mCurrListener = listener;
		try
		{
			mPlayer.reset();
			mPlayer.setDataSource(context, Uri.parse(url));
			mPlayer.prepareAsync();
		}
		catch (Exception e)
		{
			LOG.v("play Exception");
			e.printStackTrace();
			stop();
		}
	}

	public interface OnListener
	{
		void error();
	}
}
