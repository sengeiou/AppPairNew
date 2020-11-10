package com.lys.activity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.lys.App;
import com.lys.app.BuildConfig;
import com.lys.app.R;
import com.lys.base.utils.LOG;
import com.lys.config.AppConfig;
import com.lys.kit.activity.KitActivity;
import com.lys.kit.utils.ImageLoad;
import com.lys.protobuf.SLiveTask;
import com.lys.utils.Helper;

public class ActivityLivePlay extends KitActivity implements View.OnClickListener
{
	private class Holder
	{
		private TextView info;
		private ProgressBar loading;

		private TextView pos;
		private SeekBar progress;
		private TextView duration;
	}

	private Holder holder = new Holder();

	private void initHolder()
	{
		holder.info = findViewById(R.id.info);
		holder.loading = findViewById(R.id.loading);

		holder.pos = findViewById(R.id.pos);
		holder.progress = findViewById(R.id.progress);
		holder.duration = findViewById(R.id.duration);
	}

	private SLiveTask live;
	private String url;
	private Long startTime;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_live_play);
		initHolder();
		start();
	}

	private void start()
	{
		live = SLiveTask.load(getIntent().getStringExtra("live"));
		url = getIntent().getStringExtra("url");
		startTime = getIntent().getLongExtra("startTime", 0);

//		startTime = App.currentTimeMillis() - (9 * 60 + 30) * 1000;

		holder.loading.setVisibility(View.GONE);
		holder.info.setVisibility(View.GONE);
		if (App.currentTimeMillis() < startTime)
		{
			holder.info.setVisibility(View.VISIBLE);
			holder.info.setText("直播尚未开始");
		}
		else
		{
			initVideo();
			playVideo(ImageLoad.checkUrl(url));
		}

		if (App.isSupterMaster())
			findViewById(R.id.controller).setVisibility(View.VISIBLE);
		else
			findViewById(R.id.controller).setVisibility(View.GONE);

		Helper.addEvent(context, App.userId(), AppConfig.EventAction_InLive, live.id, String.format("进入直播《%s》", live.name));
	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		if (App.isSupterMaster())
			tickHandler.removeCallbacks(tickRunnable);
	}

	@Override
	public void finish()
	{
		super.finish();
		Helper.addEvent(context, App.userId(), AppConfig.EventAction_OutLive, live.id, String.format("退出直播《%s》", live.name));
	}

	@Override
	public void onClick(View view)
	{
	}

	//------------------- 播放相关 --------------------------

	private VideoView videoView;

	private boolean isPrepared = false;

	private void initVideo()
	{
		videoView = findViewById(R.id.videoView);

		videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener()
		{
			@Override
			public void onPrepared(MediaPlayer mp)
			{
				LOG.v("onPrepared " + mp.getDuration());
				isPrepared = true;
				holder.loading.setVisibility(View.GONE);
				if (App.currentTimeMillis() > startTime + mp.getDuration())
				{
					stopVideo();
				}
				else
				{
					videoView.seekTo((int) (App.currentTimeMillis() - startTime));
					mp.setOnSeekCompleteListener(new MediaPlayer.OnSeekCompleteListener()
					{
						@Override
						public void onSeekComplete(MediaPlayer mp)
						{
							videoView.start();
						}
					});
				}
			}
		});

		videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener()
		{
			@Override
			public void onCompletion(MediaPlayer mp)
			{
				LOG.v("onCompletion");
				stopVideo();
			}
		});

		videoView.setOnErrorListener(new MediaPlayer.OnErrorListener()
		{
			@Override
			public boolean onError(MediaPlayer mp, int what, int extra)
			{
				LOG.v(String.format("setOnErrorListener what = %s, extra = %s", what, extra));
				return false;
			}
		});

		videoView.setOnInfoListener(new MediaPlayer.OnInfoListener()
		{
			@Override
			public boolean onInfo(MediaPlayer mp, int what, int extra)
			{
				LOG.v(String.format("setOnInfoListener what = %s, extra = %s", what, extra));
				return false;
			}
		});
	}

	public void playVideo(String url)
	{
		holder.loading.setVisibility(View.VISIBLE);

		videoView.setVideoURI(Uri.parse(url));

		if (App.isSupterMaster())
			tickHandler.post(tickRunnable);
	}

	public void stopVideo()
	{
		if (App.isSupterMaster())
			tickHandler.removeCallbacks(tickRunnable);

		videoView.pause();

		holder.info.setVisibility(View.VISIBLE);
		holder.info.setText("直播已结束");
	}

	private Handler tickHandler = new Handler();
	private Runnable tickRunnable = new Runnable()
	{
		@Override
		public void run()
		{
			try
			{
				holder.pos.setText(formatTime(videoView.getCurrentPosition()));
				holder.duration.setText(formatTime(videoView.getDuration()));
				holder.progress.setProgress((int) (videoView.getCurrentPosition() * 1000L / videoView.getDuration()));
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			tickHandler.postDelayed(this, 1000);
		}
	};

	public static String formatTime(long ms)
	{
		int second = (int) (ms / 1000);
		int minute = second / 60;
		second = second % 60;
		int hour = minute / 60;
		minute = minute % 60;
		if (hour == 0)
			return String.format("%02d:%02d", minute, second);
		else
			return String.format("%02d:%02d:%02d", hour, minute, second);
	}

}
