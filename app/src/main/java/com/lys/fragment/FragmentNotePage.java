package com.lys.fragment;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.lys.activity.ActivityNoteBook;
import com.lys.app.R;
import com.lys.base.fragment.BaseFragment;
import com.lys.base.utils.FsUtils;
import com.lys.base.utils.LOG;
import com.lys.base.utils.VideoLoader;
import com.lys.board.LysBoardView;
import com.lys.board.config.BoardConfig;
import com.lys.board.dawing.LysBoardDrawing;
import com.lys.config.AppConfig;
import com.lys.kit.utils.ImageLoad;
import com.lys.kit.view.BoardToolBar;
import com.lys.kit.view.BoardView;
import com.lys.protobuf.SNoteBook;
import com.lys.protobuf.SNotePage;
import com.lys.utils.LysIM;

import java.io.File;
import java.io.Serializable;

public class FragmentNotePage extends BaseFragment implements View.OnClickListener
{
	private class Holder
	{
		private ViewGroup con;

		private BoardView board;

//		private ProgressBar loading;

		private ViewGroup controller;
		private ImageView control;
		private TextView pos;
		private SeekBar progress;
		private TextView duration;
	}

	private Holder holder = new Holder();

	private void initHolder(View view)
	{
		holder.con = view.findViewById(R.id.con);

		holder.board = view.findViewById(R.id.board);

//		holder.loading = view.findViewById(R.id.loading);

		holder.controller = view.findViewById(R.id.controller);
		holder.control = view.findViewById(R.id.control);
		holder.pos = view.findViewById(R.id.pos);
		holder.progress = view.findViewById(R.id.progress);
		holder.duration = view.findViewById(R.id.duration);
	}

	private int position;
	private SNoteBook book;
	private SNotePage page;

	private File dir;

	private ActivityNoteBook getBookActivity()
	{
		return (ActivityNoteBook) this.getActivity();
	}

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fragment_note_page, container, false);
		initHolder(view);

		position = getArguments().getInt("position");
		book = SNoteBook.load(getArguments().getString("book"));
		page = SNotePage.load(getArguments().getString("page"));

		LOG.v("+++++++++++++++ onCreateView : " + position);

		dir = new File(String.format("%s/%s", AppConfig.getNoteDir(context, book), page.pageDir));

		holder.board.setOperationListener(new LysBoardView.OnOperationListener()
		{
			@Override
			public void onDrawBegin()
			{

			}

			@Override
			public void onDrawOver(LysBoardDrawing currDrawing)
			{
			}
		});
		holder.board.setListener(new BoardView.OnListener()
		{
			@Override
			public void onLockChanged(boolean isLock)
			{
				toolBar.photoLockSetChecked(isLock);
			}

			@Override
			public void onPaste()
			{
				toolBar.onPaste();
				getBookActivity().onPaste();
			}
		});
		holder.board.addOnLayoutChangeListener(new View.OnLayoutChangeListener()
		{
			@Override
			public void onLayoutChange(View view, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom)
			{
				view.removeOnLayoutChangeListener(this);
				holder.board.loadBoard(dir);
			}
		});

		view.findViewById(R.id.control).setOnClickListener(this);

		holder.progress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
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
				seekTo(holder.progress.getProgress() * getDuration() / 1000);
			}
		});

//		if (position % 2 == 0)
//			page.videoUrl = FsUtils.SD_CARD + "/DCIM/Camera/VIDEO_20190625_184946.mp4";
//		else
//			page.videoUrl = null;

		File videoFile = new File(String.format("%s/%s.mp4", dir.getAbsolutePath(), BoardConfig.big_video));
		File videoUrlFile = new File(String.format("%s/%s.txt", dir.getAbsolutePath(), BoardConfig.big_video));
		if (videoFile.exists() || videoUrlFile.exists())
		{
			holder.controller.setVisibility(View.VISIBLE);
		}
		else
		{
			holder.controller.setVisibility(View.GONE);
		}

		return view;
	}

	@Override
	public void onPause()
	{
		super.onPause();
		if (videoView != null)
		{
			savedState = getPlayState();
			LOG.v("save pos : " + formatTime(savedState.savePos));
			stop();
		}
	}

	@Override
	public void onResume()
	{
		super.onResume();
		if (savedState != null)
		{
			play();
		}
	}

	@Override
	public void onDestroyView()
	{
		super.onDestroyView();
		getBookActivity().removeFragmentPage(position);
		stop();
		LOG.v("--------------- onDestroyView : " + position);
	}

	@Override
	public void onClick(View view)
	{
		if (view.getId() == R.id.control)
		{
			if (isPlaying())
				pause();
			else
				start();
		}
	}

	private BoardToolBar toolBar;

	public void bindTool(BoardToolBar toolBar)
	{
		this.toolBar = toolBar;
		holder.board.setMenu(toolBar);
		toolBar.bindBoard(holder.board);
	}

	public static final int SMALL_WIDTH = 600;

	public void genSmallAndBig(boolean async)
	{
		if (async)
		{
			if (holder.board.hasModify())
			{
				new Thread(new Runnable()
				{
					@Override
					public void run()
					{
						holder.board.genSmall(SMALL_WIDTH);
					}
				}).start();
			}
		}
		else
		{
			if (holder.board.hasModify())
				holder.board.genSmall(SMALL_WIDTH);
		}
	}

	public void onSend(String shareUrl)
	{
		LysIM.share(context, holder.board, shareUrl);
	}

	public BoardView getReceiveBoard()
	{
		return holder.board;
	}

	public void onDeleteBigVideoBefore()
	{
		stop();
		savedState = null;
		holder.controller.setVisibility(View.GONE);
	}

	public void onAddBigVideoOver()
	{
		holder.controller.setVisibility(View.VISIBLE);
		play();
	}

	//------------------- 播放相关（开始） --------------------------

	private VideoView videoView = null;

	private boolean mainProcessInControl = false; // 主进度是否在操控中

	private PlayState savedState = null;

	public static class PlayState implements Serializable
	{
		public boolean saveIsPlaying = true;
		public int savePos = 0;

		@Override
		public String toString()
		{
			return String.format("saveIsPlaying=%s, savePos=%s", saveIsPlaying, savePos);
		}
	}

	private PlayState getPlayState()
	{
		PlayState state = new PlayState();
		state.saveIsPlaying = isPlaying();
		state.savePos = getCurrentPosition();
		return state;
	}

	public void startPageScroll()
	{
		if (videoView != null)
		{
			videoView.setBackgroundColor(Color.WHITE);
		}
	}

	public void stopPageScroll()
	{
		if (videoView != null)
		{
			videoView.setBackgroundColor(Color.TRANSPARENT);
		}
	}

	public void play()
	{
		File videoFile = new File(String.format("%s/%s.mp4", dir.getAbsolutePath(), BoardConfig.big_video));
		File videoUrlFile = new File(String.format("%s/%s.txt", dir.getAbsolutePath(), BoardConfig.big_video));
		if (videoFile.exists() || videoUrlFile.exists())
		{
			if (videoView == null)
			{
				videoView = new VideoView(context);

				videoView.setBackgroundColor(Color.WHITE);

				videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener()
				{
					@Override
					public void onCompletion(MediaPlayer mediaPlayer)
					{
						LOG.v("onCompletion");
						videoView.seekTo(0);
					}
				});
				videoView.setOnInfoListener(new MediaPlayer.OnInfoListener()
				{
					@Override
					public boolean onInfo(MediaPlayer mediaPlayer, int what, int extra)
					{
						LOG.v(String.format("onInfo what = %d, extra = %d", what, extra));
						if (what == MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START)
						{
							new Handler().postDelayed(new Runnable()
							{
								@Override
								public void run()
								{
									if (videoView != null)
										videoView.setBackgroundColor(Color.TRANSPARENT);
								}
							}, 200);
						}
						return false;
					}
				});

				RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
				layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
				holder.con.addView(videoView, 0, layoutParams);
			}

			tickHandler.post(tickRunnable);
			if (videoFile.exists())
			{
				videoView.setVideoPath(videoFile.getAbsolutePath());
			}
			else
			{
				String url = ImageLoad.checkUrl(FsUtils.readText(videoUrlFile));
				File cacheFile = VideoLoader.getCacheFile(getContext(), url);
				if (cacheFile.exists())
					videoView.setVideoPath(cacheFile.getAbsolutePath());
				else
					videoView.setVideoURI(Uri.parse(url));
			}

			boolean toPlay = true;
			if (savedState != null)
			{
				if (savedState.savePos != 0)
					seekTo(savedState.savePos);

				toPlay = savedState.saveIsPlaying;

				savedState = null;
			}

			if (toPlay)
				videoView.start();
			LOG.v("to play");
		}
	}

	public void stop()
	{
		if (videoView != null)
		{
			tickHandler.removeCallbacks(tickRunnable);
			videoView.pause();
			videoView.stopPlayback();
			holder.con.removeView(videoView);
			videoView = null;
		}
	}

	private Handler tickHandler = new Handler();
	private Runnable tickRunnable = new Runnable()
	{
		@Override
		public void run()
		{
//			LOG.v("tick " + position);
			try
			{
				if (isPlaying())
					holder.control.setImageResource(R.drawable.img_big_video_play);
				else
					holder.control.setImageResource(R.drawable.img_big_video_pause);
				holder.duration.setText(formatTime(getDuration()));
				holder.pos.setText(formatTime(getCurrentPosition()));
				if (!mainProcessInControl)
					holder.progress.setProgress(getCurrentPosition() * 1000 / getDuration());
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			tickHandler.postDelayed(this, 1000);
		}
	};

	private static String formatTime(long ms)
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

	private void start()
	{
		if (videoView != null)
		{
			videoView.start();
			holder.control.setImageResource(R.drawable.img_big_video_play);
		}
	}

	private void pause()
	{
		if (videoView != null)
		{
			videoView.pause();
			holder.control.setImageResource(R.drawable.img_big_video_pause);
		}
	}

	private boolean isPlaying()
	{
		if (videoView != null)
			return videoView.isPlaying();
		else
			return false;
	}

	private void seekTo(int pos)
	{
		if (videoView != null)
			videoView.seekTo(pos);
	}

	private int getCurrentPosition()
	{
		if (videoView != null)
			return videoView.getCurrentPosition();
		else
			return 0;
	}

	private int getDuration()
	{
		if (videoView != null)
			return videoView.getDuration();
		else
			return 0;
	}

	//------------------- 播放相关（结束） --------------------------

}
