package com.lys.player.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.lys.player.R;
import com.lys.player.VideoPlayer;

public class PlayFullActivity extends AppCompatActivity
{
//	public static final String Action_showKillMask = "com.lys.action.showKillMask";
//	public static final String Action_hideKillMask = "com.lys.action.hideKillMask";

	public VideoPlayer videoPlayer;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		//取消状态栏
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_play_full);

		videoPlayer = findViewById(R.id.videoPlayer);
		videoPlayer.init();

		VideoPlayer.PlayState playState = null;
		if (getIntent().hasExtra("playState"))
			playState = (VideoPlayer.PlayState) getIntent().getSerializableExtra("playState");

		if (getIntent().hasExtra("path"))
			videoPlayer.play(getIntent().getStringExtra("path"), playState);
		else if (getIntent().hasExtra("uri"))
			videoPlayer.play((Uri) getIntent().getParcelableExtra("uri"), playState);
	}

	@Override
	protected void onResume()
	{
		super.onResume();
//		if (KitUtils.isHuaWei(this))
//		{
//			Intent intent = new Intent();
//			intent.setAction(Action_hideKillMask);
//			sendBroadcast(intent);
//		}
		videoPlayer.onResume();
	}

	@Override
	protected void onPause()
	{
		super.onPause();
//		if (KitUtils.isHuaWei(this))
//		{
//			Intent intent = new Intent();
//			intent.setAction(Action_showKillMask);
//			sendBroadcast(intent);
//		}
		videoPlayer.onPause();
	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		videoPlayer.destroy();
	}

	@Override
	public void finish()
	{
		videoPlayer.willFinish();
		super.finish();
	}
}