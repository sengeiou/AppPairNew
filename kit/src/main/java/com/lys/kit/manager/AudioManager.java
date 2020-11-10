package com.lys.kit.manager;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.text.TextUtils;

import com.lys.base.utils.LOG;

// 用于MediaList
public class AudioManager
{
	private static AudioManager mInstance = null;

	public static AudioManager instance()
	{
		if (mInstance == null)
		{
			mInstance = new AudioManager();
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
				LOG.v("onPrepared:" + mState);
				if (mState == StateLoading)
				{
					mPlayer.start();
					setState(StatePlaying);
				}
			}
		});
	}

	public void release()
	{
		stop();
		mPlayer.release();
		mInstance = null;
	}

	private Handler mHandler = new Handler();
	private Runnable mTickRunnable = new Runnable()
	{
		@Override
		public void run()
		{
			try
			{
				if (mPlayer.isPlaying())
				{
					if (mCurrListener != null)
						mCurrListener.tick(mPlayer.getCurrentPosition(), mPlayer.getDuration());
				}
			}
			catch (Exception e)
			{
				LOG.v("tick Exception");
				e.printStackTrace();
			}
			mHandler.postDelayed(this, 1000);
		}
	};

	public static final int StateIdle = 0;
	public static final int StateLoading = 1;
	public static final int StatePause = 2;
	public static final int StatePlaying = 3;

	public int getState()
	{
		return mState;
	}

	private void setState(int state)
	{
		if (mState != state)
		{
			mState = state;
			if (mCurrListener != null)
				mCurrListener.stateChange(state);
		}
	}

	private int mState = StateIdle;
	private OnListener mCurrListener = null;

	public void stop()
	{
		if (mState != StateIdle)
		{
			setState(StateIdle);
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
			if (mCurrListener != null)
			{
				mCurrListener.end();
				mHandler.removeCallbacks(mTickRunnable);
			}
		}
	}

	public void play(Context context, String url, OnListener listener)
	{
		stop();
		if (TextUtils.isEmpty(url))
		{
			LOG.toast(context, "url为空，无法播放");
			return;
		}
		mCurrListener = listener;
		if (mCurrListener != null)
		{
			mCurrListener.begin();
			mHandler.post(mTickRunnable);
		}
		try
		{
			mPlayer.reset();
			mPlayer.setDataSource(context, Uri.parse(url));
			mPlayer.prepareAsync();
			setState(StateLoading);
		}
		catch (Exception e)
		{
			LOG.v("play Exception");
			e.printStackTrace();
			stop();
		}
	}

	public void seekTo(int progress)
	{
		if (mState == StatePause || mState == StatePlaying)
		{
			int pos = progress * mPlayer.getDuration() / 1000;
			mPlayer.seekTo(pos);
		}
	}

	public boolean isPlaying()
	{
		return mPlayer.isPlaying();
	}

	public void pause()
	{
		if (mPlayer.isPlaying())
		{
			mPlayer.pause();
			setState(StatePause);
		}
	}

	public void play()
	{
		if (!mPlayer.isPlaying())
		{
			mPlayer.start();
			setState(StatePlaying);
		}
	}

	public int getCurrentPosition()
	{
		if (mState == StatePause || mState == StatePlaying)
			return mPlayer.getCurrentPosition();
		else
			return 0;
	}

	public int getDuration()
	{
		if (mState == StatePause || mState == StatePlaying)
			return mPlayer.getDuration();
		else
			return 0;
	}

	public interface OnListener
	{
		void begin();

		void stateChange(int state);

		void tick(int currentPosition, int duration);

		void error();

		void end();

	}
}
