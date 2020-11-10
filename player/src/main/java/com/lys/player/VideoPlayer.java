package com.lys.player;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.lys.base.utils.LOG;
import com.lys.base.utils.LysPoint;
import com.lys.player.activity.PlayFullActivity;
import com.lys.player.utils.PlayerUtils;

import java.io.IOException;
import java.io.Serializable;

import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;
import tv.danmaku.ijk.media.player.IjkTimedText;

public class VideoPlayer extends RelativeLayout implements View.OnTouchListener, View.OnClickListener, IMediaPlayer.OnBufferingUpdateListener, //
		IMediaPlayer.OnCompletionListener, //
		IMediaPlayer.OnPreparedListener, //
		IMediaPlayer.OnInfoListener, //
		IMediaPlayer.OnVideoSizeChangedListener, //
		IMediaPlayer.OnErrorListener, //
		IMediaPlayer.OnSeekCompleteListener, //
		IMediaPlayer.OnTimedTextListener
{
	public static final int FULL_REQUEST_CODE = 3581;

	private IjkMediaPlayer mediaPlayer = null;

	private SurfaceView surfaceView;
	private boolean surfaceViewReady = false;

	private VideoPlayerListener listener = null;

	public void setListener(VideoPlayerListener listener)
	{
		this.listener = listener;
	}

	private boolean isLock = false;

	private class Holder
	{
		private View touch;
		private ProgressBar loading;
		private ImageView lock;

		private ViewGroup time;
		private TextView timePos;
		private TextView timeDuration;
		private ProgressBar timeProgress;

		private ViewGroup level;
		private ImageView levelIcon;
		private ProgressBar levelProgress;

		private ViewGroup controller;
		private ImageView control;
		private TextView pos;
		private SeekBar progress;
		private TextView duration;
		private TextView speed;
		private ImageView full;

		private ViewGroup speedMenu;
		private TextView speed200;
		private TextView speed150;
		private TextView speed125;
		private TextView speed100;

		private ViewGroup error;
		private TextView errorInfo;
		private Button retry;

		private ImageView replay;
	}

	private Holder ui = new Holder();

	public VideoPlayer(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		setKeepScreenOn(true);
		LayoutInflater.from(context).inflate(R.layout.video_player, this, true);
		surfaceView = findViewById(R.id.surfaceView);
		surfaceView.getHolder().addCallback(new SurfaceHolder.Callback()
		{
			@Override
			public void surfaceCreated(SurfaceHolder surfaceHolder)
			{
//				LOG.v("surfaceCreated");
				surfaceViewReady = true;
			}

			@Override
			public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width, int height)
			{
//				LOG.v("surfaceChanged");
			}

			@Override
			public void surfaceDestroyed(SurfaceHolder surfaceHolder)
			{
//				LOG.v("surfaceDestroyed");
				surfaceViewReady = false;
			}
		});

		ui.touch = findViewById(R.id.touch);
		ui.loading = findViewById(R.id.loading);
		ui.lock = findViewById(R.id.lock);

		ui.time = findViewById(R.id.time);
		ui.timePos = findViewById(R.id.timePos);
		ui.timeDuration = findViewById(R.id.timeDuration);
		ui.timeProgress = findViewById(R.id.timeProgress);

		ui.level = findViewById(R.id.level);
		ui.levelIcon = findViewById(R.id.levelIcon);
		ui.levelProgress = findViewById(R.id.levelProgress);

		ui.controller = findViewById(R.id.controller);
		ui.control = findViewById(R.id.control);
		ui.pos = findViewById(R.id.pos);
		ui.progress = findViewById(R.id.progress);
		ui.duration = findViewById(R.id.duration);
		ui.speed = findViewById(R.id.speed);
		ui.full = findViewById(R.id.full);

		ui.speedMenu = findViewById(R.id.speedMenu);
		ui.speed200 = findViewById(R.id.speed200);
		ui.speed150 = findViewById(R.id.speed150);
		ui.speed125 = findViewById(R.id.speed125);
		ui.speed100 = findViewById(R.id.speed100);

		ui.error = findViewById(R.id.error);
		ui.errorInfo = findViewById(R.id.errorInfo);
		ui.retry = findViewById(R.id.retry);

		ui.replay = findViewById(R.id.replay);

		ui.touch.setOnTouchListener(this);

		ui.lock.setOnClickListener(this);
		ui.control.setOnClickListener(this);
		ui.speed.setOnClickListener(this);
		ui.full.setOnClickListener(this);
		ui.speedMenu.setOnClickListener(this);
		ui.speed200.setOnClickListener(this);
		ui.speed150.setOnClickListener(this);
		ui.speed125.setOnClickListener(this);
		ui.speed100.setOnClickListener(this);
		ui.retry.setOnClickListener(this);
		ui.replay.setOnClickListener(this);

		ui.progress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
		{
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
			{
//				LOG.v("onProgressChanged : " + fromUser);
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar)
			{
//				LOG.v("onStartTrackingTouch");
				mainProcessInControl = true;
			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar)
			{
//				LOG.v("onStopTrackingTouch");
				mainProcessInControl = false;
				lastControlTime = System.currentTimeMillis();

				seekTo(ui.progress.getProgress() * getDuration() / 1000);
			}
		});

		hideAllUI();
		initUIInfo();
	}

	private void hideAllUI()
	{
		ui.touch.setVisibility(View.GONE);
		ui.loading.setVisibility(View.GONE);
		ui.lock.setVisibility(View.GONE);
		ui.time.setVisibility(View.GONE);
		ui.level.setVisibility(View.GONE);
		ui.controller.setVisibility(View.GONE);
		ui.speedMenu.setVisibility(View.GONE);
		ui.error.setVisibility(View.GONE);
		ui.replay.setVisibility(View.GONE);
	}

	private void initUIInfo()
	{
		removeCallbacks(tipHide);
		removeCallbacks(tickRunnable);
		isLock = false;
		ui.lock.setImageResource(isLock ? R.drawable.img_lock : R.drawable.img_unlock);
		ui.speed.setText("1X");
		lastControlTime = 0;
		if (getContext() instanceof PlayFullActivity)
			ui.full.setImageResource(R.drawable.img_to_small);
	}

	public void init()
	{
		//加载so文件
		try
		{
			LOG.v("native_profileBegin");
			IjkMediaPlayer.loadLibrariesOnce(null);
			IjkMediaPlayer.native_profileBegin("libijkplayer.so");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private PlayState savedState = null;

	public void onPause()
	{
		savedState = getPlayState();
		LOG.v("save pos : " + formatTime(savedState.savePos));
		stop();
	}

	public void onResume()
	{
		if (savedState != null)
		{
			retry(savedState);
			savedState = null;
		}
	}

	public void destroy()
	{
		LOG.v("native_profileEnd");
		IjkMediaPlayer.native_profileEnd();
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if (requestCode == FULL_REQUEST_CODE)
		{
			savedState = (PlayState) data.getSerializableExtra("playState");
		}
	}

	public void willFinish()
	{
		LOG.v("willFinish");
		Intent intent = new Intent();
		VideoPlayer.PlayState playState = getPlayState();
		intent.putExtra("playState", playState);
		Activity activity = (Activity) getContext();
		activity.setResult(Activity.RESULT_OK, intent);
	}

	private LysPoint touchBegin = null;
	private boolean hasMove = false;

	private static final int controllerStayTime = 15000; // 控制条停留时间

	private boolean mainProcessInControl = false; // 主进度是否在操控中
	private long lastControlTime = 0; // 最后一次操作controller或lock的时间
	private Runnable tipHide = new Runnable()
	{
		@Override
		public void run()
		{
			if (mainProcessInControl || ui.speedMenu.getVisibility() == View.VISIBLE)
			{
				postDelayed(this, controllerStayTime);
			}
			else if (System.currentTimeMillis() - lastControlTime < controllerStayTime)
			{
				postDelayed(this, controllerStayTime - (System.currentTimeMillis() - lastControlTime));
			}
			else
			{
				ui.lock.setVisibility(View.GONE);
				ui.controller.setVisibility(View.GONE);
			}
		}
	};

	private boolean tipRunnableActive = false;
	private Runnable tipRunnable = new Runnable()
	{
		@Override
		public void run()
		{
			tipRunnableActive = false;
			LOG.v("tip");
			if (isLock)
			{
				if (ui.lock.getVisibility() == View.VISIBLE)
				{
					ui.lock.setVisibility(View.GONE);
					ui.controller.setVisibility(View.GONE);
					removeCallbacks(tipHide);
				}
				else
				{
					ui.lock.setVisibility(View.VISIBLE);
					ui.controller.setVisibility(View.GONE);
					postDelayed(tipHide, controllerStayTime);
				}
			}
			else
			{
				if (ui.lock.getVisibility() == View.VISIBLE)
				{
					ui.lock.setVisibility(View.GONE);
					ui.controller.setVisibility(View.GONE);
					removeCallbacks(tipHide);
				}
				else
				{
					ui.lock.setVisibility(View.VISIBLE);
					ui.controller.setVisibility(View.VISIBLE);
					postDelayed(tipHide, controllerStayTime);
				}
			}
		}
	};

	private static final int MoveType_None = 0;
	private static final int MoveType_Light = 1;
	private static final int MoveType_Sound = 2;
	private static final int MoveType_Time = 3;

	private int moveType = MoveType_None;

	private static final float levelSensitivity = 1f; // [亮度/音量]调节的敏感度
	private int brightnessBegin = 0;
	private int volumeBegin = 0;

	private static final double timeSensitivity = 0.1; // [播放进度]调节的敏感度
	private long timeBegin = 0;

	@Override
	public boolean onTouch(View view, MotionEvent event)
	{
		ViewGroup parent = (ViewGroup) getParent();
		parent.requestDisallowInterceptTouchEvent(true);
		if (event.getAction() == MotionEvent.ACTION_DOWN)
		{
			touchBegin = new LysPoint(event.getX(), event.getY(), event.getEventTime());
			hasMove = false;
			moveType = MoveType_None;
		}
		else if (event.getAction() == MotionEvent.ACTION_MOVE)
		{
			LysPoint touchMove = new LysPoint(event.getX(), event.getY(), event.getEventTime());
			if (hasMove)
			{
				if (!isLock)
				{
//					LOG.v("move " + touchMove.x + "," + touchMove.y);
					if (moveType == MoveType_Light)
					{
						int brightness = brightnessBegin + (int) ((touchBegin.y - touchMove.y) * levelSensitivity * PlayerUtils.MAX_BRIGHTNESS / 1000);
						brightness = Math.max(brightness, 0);
						brightness = Math.min(brightness, PlayerUtils.MAX_BRIGHTNESS);
						PlayerUtils.setAppBrightness(getContext(), brightness);
						ui.levelProgress.setProgress(brightness * 1000 / PlayerUtils.MAX_BRIGHTNESS);
					}
					else if (moveType == MoveType_Sound)
					{
						int maxValue = PlayerUtils.getMusicMaxVolume(getContext());
						int volume = volumeBegin + (int) ((touchBegin.y - touchMove.y) * levelSensitivity * maxValue / 1000);
						volume = Math.max(volume, 0);
						volume = Math.min(volume, maxValue);
						ui.levelIcon.setImageResource(volume == 0 ? R.drawable.img_sound_close : R.drawable.img_sound_open);
						PlayerUtils.setMusicVolume(getContext(), volume);
						ui.levelProgress.setProgress(volume * 1000 / maxValue);
					}
					else if (moveType == MoveType_Time)
					{
						long maxValue = getDuration();
						long newPos = timeBegin + (long) ((touchMove.x - touchBegin.x) * timeSensitivity * maxValue / 1000);
						newPos = Math.max(newPos, 0);
						newPos = Math.min(newPos, maxValue);
						ui.timePos.setText(formatTime(newPos));
						ui.timeProgress.setProgress((int) (newPos * 1000 / maxValue));
					}
				}
			}
			else
			{
				int offsetX = (int) (touchMove.x - touchBegin.x);
				int offsetY = (int) (touchMove.y - touchBegin.y);
				if (!hasMove)
				{
					if (Math.abs(offsetX) > 2 || Math.abs(offsetY) > 2)
					{
						hasMove = true;
					}
				}
				if (hasMove)
				{
					if (!isLock)
					{
						LOG.v(String.format("start move (%.0f, %.0f) ==> (%.0f, %.0f)", touchBegin.x, touchBegin.y, touchMove.x, touchMove.y));
						if (Math.abs(touchBegin.x - touchMove.x) < Math.abs(touchBegin.y - touchMove.y))
						{
							if (touchBegin.x < view.getWidth() / 2)
							{
								LOG.v("open light");
								moveType = MoveType_Light;
								ui.level.setVisibility(View.VISIBLE);
								ui.levelIcon.setImageResource(R.drawable.img_light);
								brightnessBegin = PlayerUtils.getBrightness(getContext());

								int brightness = brightnessBegin + (int) ((touchBegin.y - touchMove.y) * levelSensitivity * PlayerUtils.MAX_BRIGHTNESS / 1000);
								brightness = Math.max(brightness, 0);
								brightness = Math.min(brightness, PlayerUtils.MAX_BRIGHTNESS);
								PlayerUtils.setAppBrightness(getContext(), brightness);
								ui.levelProgress.setProgress(brightness * 1000 / PlayerUtils.MAX_BRIGHTNESS);
							}
							else
							{
								LOG.v("open sound");
								moveType = MoveType_Sound;
								ui.level.setVisibility(View.VISIBLE);
								volumeBegin = PlayerUtils.getMusicVolume(getContext());

								int maxValue = PlayerUtils.getMusicMaxVolume(getContext());
								LOG.v("getMusicMaxVolume : " + maxValue);
								int volume = volumeBegin + (int) ((touchBegin.y - touchMove.y) * levelSensitivity * maxValue / 1000);
								volume = Math.max(volume, 0);
								volume = Math.min(volume, maxValue);
								ui.levelIcon.setImageResource(volume == 0 ? R.drawable.img_sound_close : R.drawable.img_sound_open);
								PlayerUtils.setMusicVolume(getContext(), volume);
								ui.levelProgress.setProgress(volume * 1000 / maxValue);
							}
						}
						else
						{
							LOG.v("open time");
							moveType = MoveType_Time;
							ui.time.setVisibility(View.VISIBLE);
							ui.timeDuration.setText(formatTime(getDuration()));
							timeBegin = getCurrentPosition();

							long maxValue = getDuration();
							long newPos = timeBegin + (long) ((touchMove.x - touchBegin.x) * timeSensitivity * maxValue / 1000);
							newPos = Math.max(newPos, 0);
							newPos = Math.min(newPos, maxValue);
							ui.timePos.setText(formatTime(newPos));
							ui.timeProgress.setProgress((int) (newPos * 1000 / maxValue));
						}
					}
				}
			}
		}
		else if (event.getAction() == MotionEvent.ACTION_UP)
		{
			LysPoint touchEnd = new LysPoint(event.getX(), event.getY(), event.getEventTime());
			if (hasMove)
			{
				if (!isLock)
				{
					LOG.v("end move");
					if (moveType == MoveType_Light)
					{
						ui.level.setVisibility(View.GONE);

						int brightness = brightnessBegin + (int) ((touchBegin.y - touchEnd.y) * levelSensitivity * PlayerUtils.MAX_BRIGHTNESS / 1000);
						brightness = Math.max(brightness, 0);
						brightness = Math.min(brightness, PlayerUtils.MAX_BRIGHTNESS);
						PlayerUtils.setAppBrightness(getContext(), brightness);
						PlayerUtils.setBrightness(getContext(), brightness);
					}
					else if (moveType == MoveType_Sound)
					{
						ui.level.setVisibility(View.GONE);

						int maxValue = PlayerUtils.getMusicMaxVolume(getContext());
						int volume = volumeBegin + (int) ((touchBegin.y - touchEnd.y) * levelSensitivity * maxValue / 1000);
						volume = Math.max(volume, 0);
						volume = Math.min(volume, maxValue);
						PlayerUtils.setMusicVolume(getContext(), volume);
					}
					else if (moveType == MoveType_Time)
					{
						ui.time.setVisibility(View.GONE);

						long maxValue = getDuration();
						long newPos = timeBegin + (long) ((touchEnd.x - touchBegin.x) * timeSensitivity * maxValue / 1000);
						newPos = Math.max(newPos, 0);
						newPos = Math.min(newPos, maxValue);
						seekTo(newPos);
					}
				}
			}
			else
			{
				if (touchEnd.timestamp - touchBegin.timestamp < 200)
				{
					if (tipRunnableActive)
					{
						tipRunnableActive = false;
						removeCallbacks(tipRunnable);
						if (!isLock)
						{
							LOG.v("double tip");
							if (isPlaying())
								pause();
							else
								start();
						}
					}
					else
					{
						tipRunnableActive = true;
						postDelayed(tipRunnable, 300);
					}
				}
			}
		}
		return true;
	}

	@Override
	public void onClick(View view)
	{
		if (view.getId() == R.id.lock)
		{
			lastControlTime = System.currentTimeMillis();
			if (isLock)
			{
				isLock = false;
				ui.controller.setVisibility(View.VISIBLE);
			}
			else
			{
				isLock = true;
				ui.controller.setVisibility(View.GONE);
			}
			ui.lock.setImageResource(isLock ? R.drawable.img_lock : R.drawable.img_unlock);
		}
		else if (view.getId() == R.id.control)
		{
			lastControlTime = System.currentTimeMillis();
			if (isPlaying())
				pause();
			else
				start();
		}
		else if (view.getId() == R.id.speed)
		{
			lastControlTime = System.currentTimeMillis();
			ui.speedMenu.setVisibility(View.VISIBLE);
		}
		else if (view.getId() == R.id.full)
		{
			lastControlTime = System.currentTimeMillis();
			if (getContext() instanceof PlayFullActivity)
			{
				Activity activity = (Activity) getContext();
				activity.finish();
			}
			else
			{
				Intent intent = new Intent(getContext(), PlayFullActivity.class);
				if (dataSourceType == DataSourceType_Path)
				{
					intent.putExtra("path", mPath);
				}
				else if (dataSourceType == DataSourceType_Uri)
				{
					intent.putExtra("uri", mUri);
				}
				PlayState playState = getPlayState();
				intent.putExtra("playState", playState);
				Activity activity = (Activity) getContext();
				activity.startActivityForResult(intent, FULL_REQUEST_CODE);
			}
		}
		else if (view.getId() == R.id.speedMenu)
		{
			lastControlTime = System.currentTimeMillis();
			ui.speedMenu.setVisibility(View.GONE);
		}
		else if (view.getId() == R.id.speed200)
		{
			lastControlTime = System.currentTimeMillis();
			ui.speedMenu.setVisibility(View.GONE);
			setSpeed(2);
		}
		else if (view.getId() == R.id.speed150)
		{
			lastControlTime = System.currentTimeMillis();
			ui.speedMenu.setVisibility(View.GONE);
			setSpeed(1.5f);
		}
		else if (view.getId() == R.id.speed125)
		{
			lastControlTime = System.currentTimeMillis();
			ui.speedMenu.setVisibility(View.GONE);
			setSpeed(1.25f);
		}
		else if (view.getId() == R.id.speed100)
		{
			lastControlTime = System.currentTimeMillis();
			ui.speedMenu.setVisibility(View.GONE);
			setSpeed(1);
		}
		else if (view.getId() == R.id.retry)
		{
			ui.error.setVisibility(View.GONE);
			retry(mPlayState);
		}
		else if (view.getId() == R.id.replay)
		{
			ui.replay.setVisibility(View.GONE);
			ui.touch.setVisibility(View.VISIBLE);
			seekTo(0);
			start();
			post(tickRunnable);
		}
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh)
	{
		super.onSizeChanged(w, h, oldw, oldh);
//		LOG.v(String.format("onSizeChanged (%d, %d) ==> (%d, %d)", oldw, oldh, w, h));
		checkSurfaceViewSize();
	}

	private void reCreatePlayer()
	{
		if (mediaPlayer != null)
		{
			mediaPlayer.stop();
			mediaPlayer.setDisplay(null);
			mediaPlayer.release();
		}

		mediaPlayer = new IjkMediaPlayer();
		mediaPlayer.native_setLogLevel(IjkMediaPlayer.IJK_LOG_DEBUG);
		mediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "soundtouch", 1); // 变速不变调
		mediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "mediacodec", 1); // 开启硬解码

		mediaPlayer.setOnBufferingUpdateListener(this);
		mediaPlayer.setOnCompletionListener(this);
		mediaPlayer.setOnPreparedListener(this);
		mediaPlayer.setOnInfoListener(this);
		mediaPlayer.setOnVideoSizeChangedListener(this);
		mediaPlayer.setOnErrorListener(this);
		mediaPlayer.setOnSeekCompleteListener(this);
	}

	public static class PlayState implements Serializable
	{
		public boolean saveIsPlaying = true;
		public long savePos = 0;
		public float saveSpeed = 1;
	}

	private PlayState getPlayState()
	{
		PlayState state = new PlayState();
		state.saveIsPlaying = isPlaying();
		state.savePos = getCurrentPosition();
		state.saveSpeed = speed;
		return state;
	}

	private static final int DataSourceType_None = 0;
	private static final int DataSourceType_Path = 1;
	private static final int DataSourceType_Uri = 2;

	private int dataSourceType = DataSourceType_None;

	private String mPath = null;
	private Uri mUri = null;
	private PlayState mPlayState = null;

	public void play(String path, PlayState playState)
	{
		if (TextUtils.isEmpty(path))
			return;
		dataSourceType = DataSourceType_Path;
		mPath = path;
		mPlayState = playState;
		if (surfaceViewReady)
		{
			LOG.v("play " + path);
			hideAllUI();
			initUIInfo();
			reCreatePlayer();
			try
			{
				mediaPlayer.setDataSource(path);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			mediaPlayer.setDisplay(surfaceView.getHolder());
			mediaPlayer.prepareAsync();
			ui.loading.setVisibility(View.VISIBLE);
			LOG.v("to play");
		}
		else
		{
//			LOG.v("surfaceView not ready");
			post(new Runnable()
			{
				@Override
				public void run()
				{
//					LOG.v("retry");
					play(mPath, mPlayState);
				}
			});
		}
	}

	public void play(Uri uri, PlayState playState)
	{
		if (uri == null)
			return;
		dataSourceType = DataSourceType_Uri;
		mUri = uri;
		mPlayState = playState;
		if (surfaceViewReady)
		{
			LOG.v("play " + uri);
			hideAllUI();
			initUIInfo();
			reCreatePlayer();
			try
			{
				mediaPlayer.setDataSource(getContext(), uri);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			mediaPlayer.setDisplay(surfaceView.getHolder());
			mediaPlayer.prepareAsync();
			ui.loading.setVisibility(View.VISIBLE);
			LOG.v("to play");
		}
		else
		{
//			LOG.v("surfaceView not ready");
			post(new Runnable()
			{
				@Override
				public void run()
				{
//					LOG.v("retry");
					play(mUri, mPlayState);
				}
			});
		}
	}

	public void retry(PlayState playState)
	{
		if (dataSourceType == DataSourceType_Path)
		{
			play(mPath, playState);
		}
		else if (dataSourceType == DataSourceType_Uri)
		{
			play(mUri, playState);
		}
	}

	private void checkSurfaceViewSize()
	{
		if (mediaPlayer == null)
			return;

		float videoWidth = mediaPlayer.getVideoWidth();
		float videoHeight = mediaPlayer.getVideoHeight();

		if (videoWidth == 0 || videoHeight == 0)
			return;

		float conWidth = getWidth();
		float conHeight = getHeight();

//		LOG.v(String.format("video size (%.0f, %.0f)", videoWidth, videoHeight));
//		LOG.v(String.format("con size (%.0f, %.0f)", conWidth, conHeight));

		float surfaceWidth = 0;
		float surfaceHeight = 0;

		if (videoWidth / videoHeight > conWidth / conHeight)
		{
			surfaceWidth = conWidth;
			surfaceHeight = surfaceWidth * videoHeight / videoWidth;
		}
		else if (videoWidth / videoHeight < conWidth / conHeight)
		{
			surfaceHeight = conHeight;
			surfaceWidth = videoWidth * surfaceHeight / videoHeight;
		}
		else
		{
			surfaceWidth = conWidth;
			surfaceHeight = conHeight;
		}

//		LOG.v(String.format("surface size (%.0f, %.0f)", surfaceWidth, surfaceHeight));

		final int newWidth = (int) surfaceWidth;
		final int newHeight = (int) surfaceHeight;

		post(new Runnable()
		{
			@Override
			public void run()
			{
				ViewGroup.LayoutParams layoutParams = surfaceView.getLayoutParams();
				layoutParams.width = newWidth;
				layoutParams.height = newHeight;
				surfaceView.setLayoutParams(layoutParams);
			}
		});
	}

	private Runnable tickRunnable = new Runnable()
	{
		@Override
		public void run()
		{
//			LOG.v("tick");
			try
			{
				ui.pos.setText(formatTime(getCurrentPosition()));
				if (!mainProcessInControl)
					ui.progress.setProgress((int) (getCurrentPosition() * 1000 / getDuration()));
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			postDelayed(this, 1000);
		}
	};

	//---------------------------------------------------------------------------------

	public void start()
	{
		if (mediaPlayer != null)
		{
			mediaPlayer.start();
			ui.control.setImageResource(R.drawable.img_state_play);
		}
	}

	public void stop()
	{
		removeCallbacks(tickRunnable);
		if (mediaPlayer != null)
		{
			mediaPlayer.stop();
			mediaPlayer.setDisplay(null);
			mediaPlayer.release();
			mediaPlayer = null;
		}
		hideAllUI();
	}

	public void pause()
	{
		if (mediaPlayer != null)
		{
			mediaPlayer.pause();
			ui.control.setImageResource(R.drawable.img_state_pause);
		}
	}

	public boolean isPlaying()
	{
		if (mediaPlayer != null)
			return mediaPlayer.isPlaying();
		else
			return false;
	}

	public void seekTo(long pos)
	{
		if (mediaPlayer != null)
			mediaPlayer.seekTo(pos);
	}

	public long getCurrentPosition()
	{
		if (mediaPlayer != null)
			return mediaPlayer.getCurrentPosition();
		else
			return 0;
	}

	public long getDuration()
	{
		if (mediaPlayer != null)
			return mediaPlayer.getDuration();
		else
			return 0;
	}

//	public void release()
//	{
//		if (mediaPlayer != null)
//			mediaPlayer.release();
//	}

//	public void reset()
//	{
//		if (mediaPlayer != null)
//			mediaPlayer.reset();
//	}

	private float speed = 1;

	public void setSpeed(float speed)
	{
		if (mediaPlayer != null)
		{
			this.speed = speed;
			mediaPlayer.setSpeed(speed);
			if (speed == 1)
				ui.speed.setText("1X");
			else if (speed == 1.25)
				ui.speed.setText("1.25X");
			else if (speed == 1.5)
				ui.speed.setText("1.5X");
			else if (speed == 2)
				ui.speed.setText("2X");
			else
				LOG.v("错误");
		}
	}

//	public float getSpeed()
//	{
//		if (mediaPlayer != null)
//			return mediaPlayer.getSpeed(1);
//		else
//			return 1;
//	}

	//---------------------------------------------------------------------------------

	@Override
	public void onBufferingUpdate(IMediaPlayer iMediaPlayer, int percent)
	{
//		LOG.v(String.format("onBufferingUpdate percent=%d", percent));
		ui.progress.setSecondaryProgress((percent + 1) * 10); // 这里+1是实际测试的结果，不知道原因
		if (listener != null)
			listener.onBufferingUpdate(this, percent);
	}

	@Override
	public void onCompletion(IMediaPlayer iMediaPlayer)
	{
		LOG.v(String.format("onCompletion"));

		removeCallbacks(tickRunnable);

		if (ui.error.getVisibility() != View.VISIBLE)
		{
			hideAllUI();
			ui.replay.setVisibility(View.VISIBLE);
		}

		if (listener != null)
			listener.onCompletion(this);
	}

	@Override
	public boolean onError(IMediaPlayer iMediaPlayer, int what, int extra)
	{
		LOG.v(String.format("onError what=%d, extra=%d", what, extra));

		String info = "未知错误";
		switch (what)
		{
		case IMediaPlayer.MEDIA_ERROR_TIMED_OUT:
			info = "网络连接超时";
			break;
		}
		hideAllUI();
		ui.error.setVisibility(View.VISIBLE);
		ui.errorInfo.setText(info);

		if (listener != null)
			return listener.onError(this, what, extra);

		return false;
	}

	@Override
	public boolean onInfo(IMediaPlayer iMediaPlayer, int what, int extra)
	{
		LOG.v(String.format("onInfo what=%d, extra=%d", what, extra));
		switch (what)
		{
		case IMediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START:
			ui.touch.setVisibility(View.VISIBLE);
			break;
		case IMediaPlayer.MEDIA_INFO_BUFFERING_START:
			ui.loading.setVisibility(View.VISIBLE);
			break;
		case IMediaPlayer.MEDIA_INFO_BUFFERING_END:
			ui.loading.setVisibility(View.GONE);
			break;
		}
		if (listener != null)
			return listener.onInfo(this, what, extra);
		return false;
	}

	@Override
	public void onPrepared(IMediaPlayer iMediaPlayer)
	{
		LOG.v(String.format("onPrepared"));

		ui.loading.setVisibility(View.GONE);

		if (mPlayState != null)
		{
			if (mPlayState.savePos != 0)
				seekTo(mPlayState.savePos);

			if (mPlayState.saveIsPlaying)
				ui.control.setImageResource(R.drawable.img_state_play);
			else
				pause();

			if (mPlayState.saveSpeed != 1)
				setSpeed(mPlayState.saveSpeed);

			mPlayState = null;
		}
		else
		{
			ui.control.setImageResource(R.drawable.img_state_play);
		}

		ui.duration.setText(formatTime(getDuration()));
		post(tickRunnable);

		if (listener != null)
			listener.onPrepared(this);
	}

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

	@Override
	public void onSeekComplete(IMediaPlayer iMediaPlayer)
	{
		LOG.v(String.format("onSeekComplete"));
		if (listener != null)
			listener.onSeekComplete(this);
	}

	@Override
	public void onVideoSizeChanged(IMediaPlayer iMediaPlayer, int width, int height, int sar_num, int sar_den)
	{
//		LOG.v(String.format("onVideoSizeChanged width=%d, height=%d, sar_num=%d, sar_den=%d", width, height, sar_num, sar_den));

		checkSurfaceViewSize();

		if (listener != null)
			listener.onVideoSizeChanged(this, width, height, sar_num, sar_den);
	}

	@Override
	public void onTimedText(IMediaPlayer iMediaPlayer, IjkTimedText timedText)
	{
		LOG.v("onTimedText " + (timedText != null ? timedText.getText() : "null"));
		if (listener != null)
			listener.onTimedText(this, timedText);
	}
}
