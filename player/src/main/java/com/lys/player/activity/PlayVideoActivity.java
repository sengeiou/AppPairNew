package com.lys.player.activity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.VideoView;

import com.lys.base.utils.LOG;
import com.lys.player.R;

public class PlayVideoActivity extends AppCompatActivity implements View.OnClickListener
{
	private class Holder
	{
		private ProgressBar progressBar;
		private ImageView play;
	}

	private Holder holder = new Holder();

	private void initHolder()
	{
		holder.progressBar = findViewById(R.id.progressBar);
		holder.play = findViewById(R.id.play);
	}

	private VideoView videoView;
	private MediaController mController;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_play_video);

		initHolder();

//		findViewById(R.id.close).setOnClickListener(this);

		videoView = findViewById(R.id.videoView);
		videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener()
		{
			@Override
			public void onCompletion(MediaPlayer mediaPlayer)
			{
				LOG.v("onCompletion");
				holder.play.setVisibility(View.VISIBLE);
				videoView.setMediaController(null);
			}
		});
		videoView.setOnInfoListener(new MediaPlayer.OnInfoListener()
		{
			@Override
			public boolean onInfo(MediaPlayer mediaPlayer, int what, int extra)
			{
				LOG.v(String.format("onInfo what = %d, extra = %d", what, extra));
				return false;
			}
		});
		videoView.setOnErrorListener(new MediaPlayer.OnErrorListener()
		{
			@Override
			public boolean onError(MediaPlayer mediaPlayer, int what, int extra)
			{
				LOG.v(String.format("onError what = %d, extra = %d", what, extra));
				LOG.toast(PlayVideoActivity.this, "播放错误");
				return true;
			}
		});
		videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener()
		{
			@Override
			public void onPrepared(MediaPlayer mediaPlayer)
			{
				LOG.v("onPrepared");
				holder.progressBar.setVisibility(View.GONE);
			}
		});

		findViewById(R.id.play).setOnClickListener(this);

		if (getIntent().hasExtra("path"))
			videoView.setVideoPath(getIntent().getStringExtra("path"));
		else if (getIntent().hasExtra("uri"))
			videoView.setVideoURI((Uri) getIntent().getParcelableExtra("uri"));

		mController = new MediaController(this);
		mController.setMediaPlayer(videoView);
		videoView.setMediaController(mController);

		videoView.start();

		holder.progressBar.setVisibility(View.VISIBLE);
		holder.play.setVisibility(View.GONE);
	}

	@Override
	public void onClick(View view)
	{
//		if (view.getId() == R.id.close)
//		{
//			finish();
//		}
		if (view.getId() == R.id.play)
		{
			videoView.start();
			holder.play.setVisibility(View.GONE);
			videoView.setMediaController(mController);
		}
	}

}