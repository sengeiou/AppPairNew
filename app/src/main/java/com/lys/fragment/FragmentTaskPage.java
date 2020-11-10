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

import com.lys.App;
import com.lys.activity.ActivityTaskBook;
import com.lys.app.R;
import com.lys.base.fragment.BaseFragment;
import com.lys.base.utils.AppDataTool;
import com.lys.base.utils.FsUtils;
import com.lys.base.utils.LOG;
import com.lys.base.utils.SysUtils;
import com.lys.base.utils.VideoLoader;
import com.lys.board.LysBoardView;
import com.lys.board.config.BoardConfig;
import com.lys.board.dawing.LysBoardDrawing;
import com.lys.board.utils.LysBoardMenu;
import com.lys.config.AppConfig;
import com.lys.kit.utils.ImageLoad;
import com.lys.kit.utils.Protocol;
import com.lys.kit.view.BoardToolBar;
import com.lys.kit.view.BoardView;
import com.lys.kit.view.PhotoView;
import com.lys.protobuf.SBoardText;
import com.lys.protobuf.SNotePage;
import com.lys.protobuf.SPTask;
import com.lys.protobuf.SSelectionGroup;
import com.lys.utils.LysIM;
import com.lys.utils.LysUpload;

import java.io.File;
import java.io.Serializable;

public class FragmentTaskPage extends BaseFragment implements View.OnClickListener
{
	private class Holder
	{
		private ViewGroup con;

		private BoardView board;
		private BoardView boardBei;

		private View topMask;
		private View bottomMask;
		private View leftMask;
		private View rightMask;

		private ViewGroup resultCon;

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
		holder.boardBei = view.findViewById(R.id.boardBei);

		holder.topMask = view.findViewById(R.id.topMask);
		holder.bottomMask = view.findViewById(R.id.bottomMask);
		holder.leftMask = view.findViewById(R.id.leftMask);
		holder.rightMask = view.findViewById(R.id.rightMask);

		holder.resultCon = view.findViewById(R.id.resultCon);

//		holder.loading = view.findViewById(R.id.loading);

		holder.controller = view.findViewById(R.id.controller);
		holder.control = view.findViewById(R.id.control);
		holder.pos = view.findViewById(R.id.pos);
		holder.progress = view.findViewById(R.id.progress);
		holder.duration = view.findViewById(R.id.duration);
	}

	private int position;
	private SPTask task;
	private SNotePage page;

	private File dir;
	private File dirBei;

	private ActivityTaskBook getBookActivity()
	{
		return (ActivityTaskBook) this.getActivity();
	}

	public boolean modeIsSync()
	{
		return getBookActivity().modeIsSync();
	}

	public boolean showCommitJob()
	{
		return getBookActivity().showCommitJob();
	}

	public boolean alreadyCommitJob()
	{
		return getBookActivity().alreadyCommitJob();
	}

	public boolean showReadOver()
	{
		return getBookActivity().showReadOver();
	}

	public boolean isLimitOperation()
	{
		return getBookActivity().isLimitOperation();
	}

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fragment_task_page, container, false);
		initHolder(view);

		position = getArguments().getInt("position");
		task = SPTask.load(getArguments().getString("task"));
		page = SNotePage.load(getArguments().getString("page"));

		LOG.v("+++++++++++++++ onCreateView : " + position);

		dir = new File(String.format("%s/%s", AppConfig.getTaskDir(task), page.pageDir));
		dirBei = new File(String.format("%s/%s/bei", AppConfig.getTaskDir(task), page.pageDir));

		if (showCommitJob())
		{
			holder.board.setShowRightAnswer(false);
			holder.board.setShowParse(false);
			holder.board.hideResult();
		}

		if (showReadOver())
		{
			holder.resultCon.setVisibility(View.VISIBLE);
			view.findViewById(R.id.wrong).setOnClickListener(this);
			view.findViewById(R.id.half).setOnClickListener(this);
			view.findViewById(R.id.right).setOnClickListener(this);
		}

		holder.board.setOperationListener(new LysBoardView.OnOperationListener()
		{
			@Override
			public void onDrawBegin()
			{
			}

			@Override
			public void onDrawOver(LysBoardDrawing currDrawing)
			{
				getBookActivity().syncDrawOver(page.pageDir, currDrawing);
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
			public void onScrollOver(int y)
			{
				getBookActivity().syncScroll(page.pageDir, y);
			}

			@Override
			public void onDeletePhoto(String photoName)
			{
				getBookActivity().syncDeletePhoto(page.pageDir, photoName);
			}

			@Override
			public void onTopPhoto(String photoName)
			{
				getBookActivity().syncTopPhoto(page.pageDir, photoName);
			}

			@Override
			public void onModifyPhoto(String photoName, int photoX, int photoY, int photoRotation, int photoWidth, int photoHeight, boolean hide)
			{
				getBookActivity().syncModifyPhoto(page.pageDir, photoName, photoX, photoY, photoRotation, photoWidth, photoHeight, hide);
			}

			@Override
			public void onModifyPhotoSelections(String photoName, SSelectionGroup selectionGroup)
			{
				getBookActivity().syncModifyPhotoSelections(page.pageDir, photoName, selectionGroup);
				if (showCommitJob())
				{
					PhotoView photoView = holder.board.findPhoto(photoName);
					if (photoView != null)
					{
						if (selectionGroup.answer.size() > 0)
						{
							if (selectionGroup.rightAnswer.size() > 0)
							{
								String answerStr = AppDataTool.saveStringList(selectionGroup.answer).toString();
								String rightAnswerStr = AppDataTool.saveStringList(selectionGroup.rightAnswer).toString();
								if (answerStr.equals(rightAnswerStr))
								{
									holder.board.setResult(BoardConfig.BoardResultRight);
								}
								else
								{
									holder.board.setResult(BoardConfig.BoardResultWrong);
								}
							}
							else
							{
								holder.board.setResult(BoardConfig.BoardResultNormal);
							}
						}
						else
						{
							holder.board.setResult(BoardConfig.BoardResultNormal);
						}
					}
				}
			}

			@Override
			public void onModifyPhotoBoardText(String photoName, SBoardText boardText)
			{
				getBookActivity().syncModifyPhotoBoardText(page.pageDir, photoName, boardText);
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

				float scaleX = 1.0f * SysUtils.screenWidth(getContext()) / AppConfig.BoardStdWidth;
				float scaleY = 1.0f * SysUtils.screenHeight(getContext()) / AppConfig.BoardStdHeight;

				float scale = Math.min(scaleX, scaleY);

				if (false)
				{
					int spaceX = (int) Math.max(0, (SysUtils.screenWidth(getContext()) - AppConfig.BoardStdWidth * scale) / 2);
					{
						ViewGroup.LayoutParams layoutParams = holder.leftMask.getLayoutParams();
						layoutParams.width = spaceX;
						holder.leftMask.setLayoutParams(layoutParams);
					}
					{
						ViewGroup.LayoutParams layoutParams = holder.rightMask.getLayoutParams();
						layoutParams.width = spaceX;
						holder.rightMask.setLayoutParams(layoutParams);
					}

					int spaceY = (int) Math.max(0, (SysUtils.screenHeight(getContext()) - AppConfig.BoardStdHeight * scale) / 2);
					{
						ViewGroup.LayoutParams layoutParams = holder.topMask.getLayoutParams();
						layoutParams.height = spaceY;
						holder.topMask.setLayoutParams(layoutParams);
					}
					{
						ViewGroup.LayoutParams layoutParams = holder.bottomMask.getLayoutParams();
						layoutParams.height = spaceY;
						holder.bottomMask.setLayoutParams(layoutParams);
					}
				}

				holder.board.setScaleX(scale);
				holder.board.setScaleY(scale);

				holder.board.loadBoard(dir, new BoardView.OnLoadBoardCallback()
				{
					@Override
					public void onLoadOver()
					{
						if (getBookActivity() != null && alreadyCommitJob())
						{
							holder.board.lockSelections();
							holder.board.lock();
						}
					}
				});
			}
		});

		holder.boardBei.setOperationListener(new LysBoardView.OnOperationListener()
		{
			@Override
			public void onDrawBegin()
			{
			}

			@Override
			public void onDrawOver(LysBoardDrawing currDrawing)
			{
				getBookActivity().modifyed();
			}
		});
		holder.boardBei.setListener(new BoardView.OnListener()
		{
			@Override
			public void onLockChanged(boolean isLock)
			{
			}

			@Override
			public void onDeletePhoto(String photoName)
			{
				getBookActivity().modifyed();
			}

			@Override
			public void onTopPhoto(String photoName)
			{
				getBookActivity().modifyed();
			}

			@Override
			public void onModifyPhoto(String photoName, int photoX, int photoY, int photoRotation, int photoWidth, int photoHeight, boolean hide)
			{
				getBookActivity().modifyed();
			}

			@Override
			public void onModifyPhotoSelections(String photoName, SSelectionGroup selectionGroup)
			{
				getBookActivity().modifyed();
			}

			@Override
			public void onModifyPhotoBoardText(String photoName, SBoardText boardText)
			{
				getBookActivity().modifyed();
			}

			@Override
			public void onPaste()
			{
				BoardToolBar.onPaste(context, holder.boardBei, new BoardToolBar.OnListener()
				{
					@Override
					public void onAddPhoto(PhotoView photoView, byte[] bitmapData)
					{
						getBookActivity().modifyed();
					}

					@Override
					public void onAddVideo(PhotoView photoView, byte[] bitmapData, byte[] videoData)
					{
						getBookActivity().modifyed();
					}

					@Override
					public void onAddTopic(PhotoView photoView, byte[] bitmapData, byte[] parseData)
					{
						getBookActivity().modifyed();
					}

					@Override
					public void onAddSelectionGroup(PhotoView photoView)
					{
						getBookActivity().modifyed();
					}

					@Override
					public void onAddBoardText(PhotoView photoView)
					{
						getBookActivity().modifyed();
					}
				});
			}
		});
		holder.boardBei.setVisibility(View.INVISIBLE);
//		holder.boardBei.addOnLayoutChangeListener(new View.OnLayoutChangeListener()
//		{
//			@Override
//			public void onLayoutChange(View view, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom)
//			{
//				view.removeOnLayoutChangeListener(this);
//
//				float scaleX = 1.0f * SysUtils.screenWidth(getContext()) / AppConfig.BoardStdWidth;
//				float scaleY = 1.0f * SysUtils.screenHeight(getContext()) / AppConfig.BoardStdHeight;
//
//				float scale = Math.min(scaleX, scaleY);
//
//				holder.boardBei.setScaleX(scale);
//				holder.boardBei.setScaleY(scale);
//			}
//		});
//		holder.boardBei.setOnTouchListener(new View.OnTouchListener()
//		{
//			private PointF initPoint = new PointF();
//			private PointF initPosition = new PointF();
//
//			@Override
//			public boolean onTouch(View view, MotionEvent event)
//			{
//				if (!LysBoardUtils.isPen(event))
//				{
//					ViewGroup parent = (ViewGroup) view.getParent();
//					parent.requestDisallowInterceptTouchEvent(true);
//					if (event.getAction() == MotionEvent.ACTION_DOWN)
//					{
//						initPoint.set(event.getRawX(), event.getRawY());
//						FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) holder.boardBei.getLayoutParams();
//						initPosition.set(layoutParams.leftMargin, layoutParams.topMargin);
//					}
//					else if (event.getAction() == MotionEvent.ACTION_MOVE)
//					{
//						float offsetX = event.getRawX() - initPoint.x;
//						float offsetY = event.getRawY() - initPoint.y;
//
//						int currX = (int) (initPosition.x + offsetX);
//						int currY = (int) (initPosition.y + offsetY);
//
//						currX = Math.max(currX, 100 - holder.boardBei.getWidth());
//						currX = Math.min(currX, SysUtils.screenWidth(context) - 100);
//						currY = Math.max(currY, 100 - holder.boardBei.getHeight());
//						currY = Math.min(currY, SysUtils.screenHeight(context) - 100);
//
//						FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) holder.boardBei.getLayoutParams();
//						layoutParams.leftMargin = currX;
//						layoutParams.topMargin = currY;
//						holder.boardBei.setLayoutParams(layoutParams);
//					}
//					return true;
//				}
//				else
//				{
//					return false;
//				}
//			}
//		});
//		holder.boardBei.scrollLock = true;

		if (isLimitOperation())
		{
			holder.board.scrollLock = true;
		}

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
		else if (view.getId() == R.id.wrong)
		{
			holder.board.setResult(BoardConfig.BoardResultWrong);
			getBookActivity().modifyed();
		}
		else if (view.getId() == R.id.half)
		{
			holder.board.setResult(BoardConfig.BoardResultHalf);
			getBookActivity().modifyed();
		}
		else if (view.getId() == R.id.right)
		{
			holder.board.setResult(BoardConfig.BoardResultRight);
			getBookActivity().modifyed();
		}
	}

	public void switchBeiZhu()
	{
		if (holder.boardBei.getVisibility() == View.VISIBLE)
		{
			holder.boardBei.setVisibility(View.INVISIBLE);
			getBookActivity().setBeiZhuState(hasBeiZhu());
		}
		else
		{
			holder.boardBei.setVisibility(View.VISIBLE);
			holder.boardBei.loadBoard(dirBei, null);
		}
	}

	public boolean hasBeiZhu()
	{
		return BoardView.hasData(dirBei);
	}

	private BoardToolBar toolBar;

	public void bindTool(final BoardToolBar toolBar)
	{
		this.toolBar = toolBar;
		holder.board.setMenu(toolBar);
		holder.boardBei.setMenu(new LysBoardMenu()
		{
			@Override
			public int getDrawingType()
			{
				return toolBar.getDrawingType();
			}

			@Override
			public int getPaintColor()
			{
				return Color.WHITE;
			}

			@Override
			public float getStrokeWidth()
			{
				return toolBar.getStrokeWidth();
			}

			@Override
			public String getAnyParam()
			{
				return toolBar.getAnyParam();
			}

			@Override
			public boolean touchWrite()
			{
				return toolBar.touchWrite();
			}
		});
		toolBar.bindBoard(holder.board);
	}

	public void test()
	{
		holder.board.genAnswer(0);
		final File file = BoardView.getAnswerFile(holder.board.getDir());
		if (file.exists())
		{
			String path = String.format("/check_diff/%s_%s/%s_%s_%s.png", //
					task.name, //
					task.id, //
					App.name(), //
					App.userId(), //
					position);
			LysUpload.doUpload(context, file, path, new Protocol.OnCallback()
			{
				@Override
				public void onResponse(int code, String data, String msg)
				{
					if (code == 200)
					{
						FsUtils.delete(file);
						LOG.toast(context, "已上传");
					}
				}
			});
		}
	}

	public static final int SMALL_WIDTH = 600;

	public void genSmallAndBig(boolean async)
	{
		if (isLimitOperation())
			return;
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
