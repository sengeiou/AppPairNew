package com.lys.kit.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.lys.base.utils.CodeTool;
import com.lys.base.utils.CommonUtils;
import com.lys.base.utils.FsUtils;
import com.lys.base.utils.LOG;
import com.lys.base.utils.LOGJson;
import com.lys.base.view.MyScrollView;
import com.lys.board.LysBoardView;
import com.lys.board.config.BoardConfig;
import com.lys.board.dawing.LysBoardDrawing;
import com.lys.board.utils.LysBoardMenu;
import com.lys.kit.R;
import com.lys.kit.config.Config;
import com.lys.kit.dialog.DialogMenu;
import com.lys.protobuf.SBoardConfig;
import com.lys.protobuf.SBoardPhoto;
import com.lys.protobuf.SBoardText;
import com.lys.protobuf.SClipboard;
import com.lys.protobuf.SClipboardType;
import com.lys.protobuf.SPhotoAddParam;
import com.lys.protobuf.SSelectionGroup;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressLint("AppCompatCustomView")
public class BoardView extends RelativeLayout implements PhotoView.OnListener
{
	// 画布是否有修改，如果有修改，意味着answer图、small图需要变动
	private boolean hasModify = false;

	public boolean hasModify()
	{
		return hasModify;
	}

	public void setNoModify()
	{
		hasModify = false;
	}

	//---------------------------------------------------------

	private boolean lockPhoto = false;

	public boolean isLockPhoto()
	{
		return lockPhoto;
	}

	public void setLockPhoto(boolean lockPhoto)
	{
		if (this.lockPhoto != lockPhoto)
		{
			this.lockPhoto = lockPhoto;
			for (int i = 0; i < holder.photoContainer.getChildCount(); i++)
			{
				PhotoView photoView = (PhotoView) holder.photoContainer.getChildAt(i);
				photoView.setLockPhoto(lockPhoto);
			}
			if (mListener != null)
				mListener.onLockChanged(lockPhoto);
		}
	}

	//---------------------------------------------------------

	private boolean showRightAnswer = true;

	public boolean isShowRightAnswer()
	{
		return showRightAnswer;
	}

	public void setShowRightAnswer(boolean showRightAnswer)
	{
		if (this.showRightAnswer != showRightAnswer)
		{
			this.showRightAnswer = showRightAnswer;
			for (int i = 0; i < holder.photoContainer.getChildCount(); i++)
			{
				PhotoView photoView = (PhotoView) holder.photoContainer.getChildAt(i);
				photoView.setShowRightAnswer(showRightAnswer);
			}
		}
	}

	public void lockSelections()
	{
		for (int i = 0; i < holder.photoContainer.getChildCount(); i++)
		{
			PhotoView photoView = (PhotoView) holder.photoContainer.getChildAt(i);
			photoView.lockSelections();
		}
	}

	//---------------------------------------------------------

	private boolean showParse = true;

	public boolean isShowParse()
	{
		return showParse;
	}

	public void setShowParse(boolean showParse)
	{
		if (this.showParse != showParse)
		{
			this.showParse = showParse;
			for (int i = 0; i < holder.photoContainer.getChildCount(); i++)
			{
				PhotoView photoView = (PhotoView) holder.photoContainer.getChildAt(i);
				photoView.setShowParse(showParse);
			}
		}
	}

	//---------------------------------------------------------

	public void lock()
	{
		holder.boardSimple.lock();
	}

	//---------------------------------------------------------

	private class Holder
	{
		private MyScrollView scroll;
		private ViewGroup boardBg;
		private ViewGroup boardCon;
		private ImageView answer;
		private ViewGroup photoContainer;
		private LysBoardView boardSimple;
		private ImageView result;
	}

	private Holder holder = new Holder();

	private void initHolder()
	{
		holder.scroll = findViewById(R.id.scroll);
		holder.boardBg = findViewById(R.id.boardBg);
		holder.boardCon = findViewById(R.id.boardCon);
		holder.answer = findViewById(R.id.answer);
		holder.photoContainer = findViewById(R.id.photoContainer);
		holder.boardSimple = findViewById(R.id.boardSimple);
		holder.result = findViewById(R.id.result);
	}

	public boolean scrollLock = false;

	private LysBoardView.OnOperationListener mOperationListener = null;

	public void setOperationListener(LysBoardView.OnOperationListener operationListener)
	{
		this.mOperationListener = operationListener;
	}

	public BoardView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		LayoutInflater.from(context).inflate(R.layout.view_board, this, true);

		initHolder();

		holder.boardSimple.setOperationListener(new LysBoardView.OnOperationListener()
		{
			@Override
			public void onDrawBegin()
			{
//				setLockPhoto(true);
				if (mOperationListener != null)
					mOperationListener.onDrawBegin();
			}

			@Override
			public void onDrawOver(LysBoardDrawing currDrawing)
			{
				hasModify = true;
				if (mOperationListener != null)
					mOperationListener.onDrawOver(currDrawing);
			}
		});

		holder.photoContainer.setOnLongClickListener(new View.OnLongClickListener()
		{
			@Override
			public boolean onLongClick(View view)
			{
				LOG.v("onLongClick : " + lockPhoto);
				if (!lockPhoto)
				{
					SClipboard clipboard = Config.readClipboard();
					if (clipboard != null)
					{
						if (clipboard.type.equals(SClipboardType.BoardPhoto) || clipboard.type.equals(SClipboardType.BoardPages))
						{
							DialogMenu.Builder builder = new DialogMenu.Builder(getContext());
							builder.setMenu("粘贴", new DialogMenu.OnClickMenuListener()
							{
								@Override
								public void onClick()
								{
									if (mListener != null)
										mListener.onPaste();
								}
							});
							builder.show();
						}
					}
				}
				return true;
			}
		});

		holder.photoContainer.setOnTouchListener(new OnTouchListener()
		{
			@Override
			public boolean onTouch(View view, MotionEvent event)
			{
				hideTopBtn();
				return false;
			}
		});

		holder.scroll.setOnTouchListener(new OnTouchListener()
		{
			@Override
			public boolean onTouch(View view, MotionEvent motionEvent)
			{
				return scrollLock;
			}
		});

		holder.scroll.setOnScrollChangeListener(new MyScrollView.OnScrollChangeListener()
		{
			private Handler waitHandler = new Handler();
			private Runnable waitRunnable = new Runnable()
			{
				@Override
				public void run()
				{
					LOG.v("scroll over : " + holder.scroll.getScrollY());
					if (mListener != null)
						mListener.onScrollOver(holder.scroll.getScrollY());
				}
			};

			@Override
			public void onScrollChange(MyScrollView view, int x, int y, int oldx, int oldy)
			{
//				LOG.v("scroll " + y);
				waitHandler.removeCallbacks(waitRunnable);
				waitHandler.postDelayed(waitRunnable, 100);
			}
		});

	}

	private void hideTopBtn()
	{
		if (holder.photoContainer.getChildCount() > 0)
		{
			PhotoView topPhotoView = (PhotoView) holder.photoContainer.getChildAt(holder.photoContainer.getChildCount() - 1);
			topPhotoView.hideBtn();
		}
	}

	private void showTopBtn()
	{
		if (holder.photoContainer.getChildCount() > 0)
		{
			PhotoView topPhotoView = (PhotoView) holder.photoContainer.getChildAt(holder.photoContainer.getChildCount() - 1);
			topPhotoView.showBtn();
		}
	}

	public void setMenu(LysBoardMenu menu)
	{
		holder.boardSimple.setMenu(menu);
	}

//	public void waitSyncOver()
//	{
//		holder.boardSimple.waitSyncOver();
//	}

	public File getDir()
	{
		return dir;
	}

	private File dir = null;
	private SBoardConfig board = null;

	public interface OnLoadBoardCallback
	{
		void onLoadOver();
	}

	public void loadBoard(File dir)
	{
		loadBoard(dir, null);
	}

	public void loadBoard(File dir, OnLoadBoardCallback callback)
	{
		this.dir = dir;
		loadBoard(callback);
	}

	private void loadBoard(final OnLoadBoardCallback callback)
	{
		File file = new File(String.format("%s/board.json", dir.getAbsolutePath()));
		if (file.exists())
		{
			board = SBoardConfig.load(FsUtils.readText(file));
		}
		else
		{
			board = new SBoardConfig();
			board.bg = BoardConfig.BoardBgTransparent;
			board.height = getHeight();
			saveBoard();
		}

		holder.boardSimple.waitSyncOver();

		holder.photoContainer.removeAllViews();
		holder.boardSimple.clear();

		CodeTool.setViewHeight(holder.boardCon, holder.boardSimple, board.height, new CodeTool.SetHeightCallback()
		{
			@Override
			public void over()
			{
				File boardFile = new File(String.format("%s/board.png", dir.getAbsolutePath()));
				holder.boardSimple.setSyncFile(boardFile);
				for (SBoardPhoto photo : board.photos)
				{
					byte[] bitmapData = null;
					if (getPhotoFile(dir, photo.name).exists())
						bitmapData = FsUtils.readBytes(getPhotoFile(dir, photo.name));
					loadPhoto(photo, bitmapData);
				}
				if (callback != null)
					callback.onLoadOver();
			}
		});

		if (board.bg == BoardConfig.BoardBgTransparent)
			holder.boardBg.setBackgroundColor(0x00ffffff);
		else if (board.bg == BoardConfig.BoardBgWhite)
			holder.boardBg.setBackgroundColor(0xffffffff);
		else
			holder.boardBg.setBackgroundResource(BoardConfig.getBoardBgRes(board.bg));

		if (board.result == BoardConfig.BoardResultNormal)
			holder.result.setImageDrawable(null);
		else
			holder.result.setImageResource(BoardConfig.getBoardResultRes(board.result));

		hasModify = false;

		LOG.v("board.height:" + board.height);
	}

	private void saveBoard()
	{
		if (dir != null)
		{
			File file = new File(String.format("%s/board.json", dir.getAbsolutePath()));
			FsUtils.createFolder(file.getParentFile());
			FsUtils.writeText(file, LOGJson.getStr(board.saveToStr()));
		}
	}

	public int getBg()
	{
		if (board != null)
			return board.bg;
		else
			return BoardConfig.BoardBgTransparent;
	}

	public void setBg(int bg)
	{
		if (board != null)
			board.bg = bg;
		saveBoard();

		if (bg == BoardConfig.BoardBgTransparent)
			holder.boardBg.setBackgroundColor(0x00ffffff);
		else if (bg == BoardConfig.BoardBgWhite)
			holder.boardBg.setBackgroundColor(0xffffffff);
		else
			holder.boardBg.setBackgroundResource(BoardConfig.getBoardBgRes(bg));
	}

	public void hideResult()
	{
		holder.result.setVisibility(View.GONE);
	}

	public int getResult()
	{
		if (board != null)
			return board.result;
		else
			return 0;
	}

	public void setResult(int result)
	{
		if (board != null)
			board.result = result;
		saveBoard();

		if (result == BoardConfig.BoardResultNormal)
			holder.result.setImageDrawable(null);
		else
			holder.result.setImageResource(BoardConfig.getBoardResultRes(result));

		hasModify = true;
	}

	public void removeAllPhotos()
	{
		holder.photoContainer.removeAllViews();
	}

	public void clear()
	{
		holder.boardSimple.clear();
		holder.boardSimple.syncFile();
		hasModify = true;
	}

	public void addOperation(LysBoardDrawing operation)
	{
		holder.boardSimple.addOperation(operation);
		holder.boardSimple.syncFile();
		hasModify = true;
	}

	public void drawBitmap(Bitmap bitmap)
	{
		holder.boardSimple.drawBitmap(bitmap);
		holder.boardSimple.syncFile();
		hasModify = true;
	}

	public int getBoardHeight()
	{
		return holder.boardCon.getHeight();
	}

	public void setBoardHeight(int height)
	{
		setBoardHeight(height, null);
	}

	public void setBoardHeight(int height, final CodeTool.SetHeightCallback callback)
	{
		if (board != null)
			board.height = height;
		saveBoard();

		CodeTool.setViewHeight(holder.boardCon, holder.boardSimple, height, new CodeTool.SetHeightCallback()
		{
			@Override
			public void over()
			{
				holder.boardSimple.syncFile();
				if (callback != null)
					callback.over();
			}
		});

		hasModify = true;
	}

	public void setScrollPos(int scrollY)
	{
		holder.scroll.smoothScrollTo(0, scrollY);
	}

	public static final int Type_Photo = 0;
	public static final int Type_Video = 1;
	public static final int Type_Topic = 2;
	public static final int Type_SelectionGroup = 3;
	public static final int Type_BoardText = 4;

	public static final int SelectionGroupWidth = 1216 + 28;
	public static final int SelectionGroupHeight = 220;

	public static final int BoardTextWidth = 960;
	public static final int BoardTextHeight = 600;

	public static final String VideoLocal = "local:";
	public static final String VideoNet = "net:";

	public String genName()
	{
		for (int i = 0; ; i++)
		{
			String name = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new Date(System.currentTimeMillis() + i * 1000));
			if (!getPhotoFile(dir, name).exists())
				return name;
		}
	}

	//-----------------------------------------------------------------

	public static boolean hasData(File dir)
	{
		if (!dir.exists())
			return false;

		File file = new File(String.format("%s/board.json", dir.getAbsolutePath()));
		if (file.exists())
		{
			SBoardConfig board = SBoardConfig.load(FsUtils.readText(file));
			if (board.photos.size() > 0)
				return true;
		}

		File boardFile = new File(String.format("%s/board.png", dir.getAbsolutePath()));
		if (boardFile.exists())
			return true;

		return false;
	}

	public static File getPhotoFile(File dir, String name)
	{
		if (dir != null)
			return new File(String.format("%s/%s.png", dir.getAbsolutePath(), name));
		return null;
	}

	public void writePhotoFile(byte[] bitmapData, String name)
	{
		File file = getPhotoFile(dir, name);
		if (file != null)
			FsUtils.writeBytes(file, bitmapData);
	}

	public static File getVideoFile(File dir, String name)
	{
		if (dir != null)
			return new File(String.format("%s/%s.mp4", dir.getAbsolutePath(), name));
		return null;
	}

	public void copyVideoFile(File compressVideoFile, String name)
	{
		File file = getVideoFile(dir, name);
		if (file != null)
			FsUtils.copy(compressVideoFile, file);
	}

	public void writeVideoFile(byte[] videoData, String name)
	{
		File file = getVideoFile(dir, name);
		if (file != null)
			FsUtils.writeBytes(file, videoData);
	}

	public static File getParseFile(File dir, String name)
	{
		if (dir != null)
			return new File(String.format("%s/%s.parse.png", dir.getAbsolutePath(), name));
		return null;
	}

	public void writeParseFile(byte[] parseData, String name)
	{
		File file = getParseFile(dir, name);
		if (file != null)
			FsUtils.writeBytes(file, parseData);
	}

	//-----------------------------------------------------------------

	public PhotoView addPhoto(String bitmapUrl, int bitmapWidth, int bitmapHeight, SPhotoAddParam param)
	{
		return addPhoto(bitmapUrl, bitmapWidth, bitmapHeight, param, genName());
	}

	public PhotoView addPhoto(String bitmapUrl, int bitmapWidth, int bitmapHeight, SPhotoAddParam param, String name)
	{
		return addPhotoImpl(bitmapUrl, bitmapWidth, bitmapHeight, param, name, Type_Photo, null, 0, null);
	}

	public PhotoView addPhoto(byte[] bitmapData, SPhotoAddParam param)
	{
		return addPhoto(bitmapData, param, genName());
	}

	public PhotoView addPhoto(byte[] bitmapData, SPhotoAddParam param, String name)
	{
		writePhotoFile(bitmapData, name);
		return addPhotoImpl(bitmapData, param, name, Type_Photo, null, 0, null);
	}

	//-----------------------------------------------------------------

	public PhotoView addVideoLocal(byte[] bitmapData, File compressVideoFile, long duration, SPhotoAddParam param)
	{
		if (dir != null)
		{
			String name = genName();
			copyVideoFile(compressVideoFile, name);
			writePhotoFile(bitmapData, name);
			return addPhotoImpl(bitmapData, param, name, Type_Video, BoardView.VideoLocal, duration, null);
		}
		return null;
	}

	public PhotoView addVideoLocal(byte[] bitmapData, byte[] videoData, long duration, SPhotoAddParam param, String name)
	{
		if (dir != null)
		{
			writeVideoFile(videoData, name);
			writePhotoFile(bitmapData, name);
			return addPhotoImpl(bitmapData, param, name, Type_Video, BoardView.VideoLocal, duration, null);
		}
		return null;
	}

	public PhotoView addVideoNet(byte[] bitmapData, String url, long duration, SPhotoAddParam param)
	{
		return addVideoNet(bitmapData, url, duration, param, genName());
	}

	public PhotoView addVideoNet(byte[] bitmapData, String url, long duration, SPhotoAddParam param, String name)
	{
		writePhotoFile(bitmapData, name);
		return addPhotoImpl(bitmapData, param, name, Type_Video, BoardView.VideoNet + url, duration, null);
	}

	//-----------------------------------------------------------------

	public PhotoView addTopic(String bitmapUrl, int bitmapWidth, int bitmapHeight, String parseUrl, SPhotoAddParam param)
	{
		return addTopic(bitmapUrl, bitmapWidth, bitmapHeight, parseUrl, param, genName());
	}

	public PhotoView addTopic(String bitmapUrl, int bitmapWidth, int bitmapHeight, String parseUrl, SPhotoAddParam param, String name)
	{
		return addPhotoImpl(bitmapUrl, bitmapWidth, bitmapHeight, param, name, Type_Topic, parseUrl, 0, null);
	}

//	public PhotoView addTopic(byte[] bitmapData, byte[] parseData, SPhotoAddParam param)
//	{
//		if (dir != null)
//		{
//			return addTopic(bitmapData, parseData, param, genName());
//		}
//		return null;
//	}
//
//	public PhotoView addTopic(byte[] bitmapData, byte[] parseData, SPhotoAddParam param, String name)
//	{
//		if (dir != null)
//		{
//			writeParseFile(parseData, name);
//			writePhotoFile(bitmapData, name);
//			return addPhotoImpl(bitmapData, param, name, Type_Topic, null, 0, null);
//		}
//		return null;
//	}

	//-----------------------------------------------------------------

	public PhotoView addSelectionGroup(SSelectionGroup selectionGroup, SPhotoAddParam param)
	{
		return addSelectionGroup(selectionGroup, param, genName());
	}

	public PhotoView addSelectionGroup(SSelectionGroup selectionGroup, SPhotoAddParam param, String name)
	{
		return addPhotoImpl(null, 0, 0, param, name, Type_SelectionGroup, null, 0, selectionGroup);
	}

	//-----------------------------------------------------------------

	public PhotoView addBoardText(SBoardText boardText, SPhotoAddParam param)
	{
		return addBoardText(boardText, param, genName());
	}

	public PhotoView addBoardText(SBoardText boardText, SPhotoAddParam param, String name)
	{
		return addPhotoImpl(null, 0, 0, param, name, Type_BoardText, null, 0, boardText);
	}

	//-----------------------------------------------------------------

	private PhotoView addPhotoImpl(String cover, int width, int height, SPhotoAddParam param, String name, int type, String url, long duration, Object obj1)
	{
		return addPhotoImpl(null, cover, width, height, param, name, type, url, duration, obj1);
	}

	private PhotoView addPhotoImpl(byte[] bitmapData, SPhotoAddParam param, String name, int type, String url, long duration, Object obj1)
	{
		BitmapFactory.Options opts = CommonUtils.readBitmapSize(bitmapData);
		return addPhotoImpl(bitmapData, null, opts.outWidth, opts.outHeight, param, name, type, url, duration, obj1);
	}

	private PhotoView addPhotoImpl(byte[] bitmapData, String cover, int width, int height, SPhotoAddParam param, String name, int type, String url, long duration, Object obj1)
	{
//		if (param == null)
//			param = new SPhotoAddParam();

		SBoardPhoto photo = new SBoardPhoto();
		photo.type = type;
//		photo.name = name;
		photo.rotation = 0;
		photo.cover = cover;
		photo.url = url;
		photo.duration = duration;
		if (param != null)
			photo.notEye = param.notEye;
		if (param != null)
			photo.lock = param.lock;

		if (photo.type == BoardView.Type_SelectionGroup)
		{
			photo.width = SelectionGroupWidth;
			photo.height = SelectionGroupHeight;
			photo.selectionGroup = (SSelectionGroup) obj1;
		}
		else if (photo.type == BoardView.Type_BoardText)
		{
			photo.boardText = (SBoardText) obj1;
			// TODO 计算宽高
			photo.width = BoardTextWidth;
			photo.height = BoardTextHeight;
		}
		else
		{
			photo.width = width + PhotoView.maskOffset;
			photo.height = height + PhotoView.maskOffset;
		}

		return addPhotoImpl(bitmapData, photo, param, name, true);
	}

	public PhotoView addPhotoImpl(byte[] bitmapData, SBoardPhoto photo, SPhotoAddParam param, String name, boolean centerPos)
	{
		photo.name = name;

		if (centerPos)
		{
			int centerY;
			if (holder.boardCon.getHeight() > getHeight())
			{
				centerY = getHeight() / 2 + holder.scroll.getScrollY();
			}
			else
			{
				centerY = holder.boardCon.getHeight() / 2;
			}

			if (param != null && param.x != -1)
				photo.x = param.x;
			else
				photo.x = (holder.boardCon.getWidth() - photo.width) / 2;

			if (param != null && param.y != -1)
				photo.y = param.y;
			else
				photo.y = centerY - photo.height / 2;
		}

		if (board != null)
			board.photos.add(photo);

		saveBoard();

		if (param == null || !param.doNotActive)
			setLockPhoto(false);

//		if (param == null || !param.doNotActive)
		hideTopBtn();
		PhotoView photoView = loadPhoto(photo, bitmapData);
		if (param == null || !param.doNotActive)
			showTopBtn();

		hasModify = true;

		return photoView;
	}

	//-----------------------------------------------------------------

	private PhotoView loadPhoto(SBoardPhoto photo, byte[] bitmapData)
	{
		PhotoView photoView = new PhotoView(getContext());
		photoView.init(dir, photo);
		photoView.setLockPhoto(lockPhoto);
		photoView.setShowRightAnswer(showRightAnswer);
		photoView.setShowParse(showParse);
		photoView.setListener(this);
		holder.photoContainer.addView(photoView);

		photoView.setPosition(photo.x, photo.y);
		photoView.setAngle(photo.rotation);
		photoView.setDimension(photo.width, photo.height);

		if (bitmapData != null)
			photoView.setPhotoBitmap(CommonUtils.readBitmap(bitmapData, photo.width));
		else
			photoView.setPhotoBitmap(photo.cover);
		return photoView;
	}

	public PhotoView findPhoto(String photoName)
	{
		for (int i = 0; i < holder.photoContainer.getChildCount(); i++)
		{
			PhotoView photoView = (PhotoView) holder.photoContainer.getChildAt(i);
			if (photoView.photo.name.equals(photoName))
				return photoView;
		}
		return null;
	}

	public PhotoView findTopic()
	{
		for (int i = 0; i < holder.photoContainer.getChildCount(); i++)
		{
			PhotoView photoView = (PhotoView) holder.photoContainer.getChildAt(i);
			if (photoView.photo.type == BoardView.Type_Topic)
				return photoView;
		}
		return null;
	}

	@Override
	public void onTop(PhotoView photoView)
	{
		doTopPhoto(photoView);
		if (mListener != null)
			mListener.onTopPhoto(photoView.photo.name);
	}

	public void topPhoto(String photoName)
	{
		PhotoView photoView = findPhoto(photoName);
		if (photoView != null)
		{
			ViewGroup parent = (ViewGroup) photoView.getParent();
			parent.removeView(photoView);
			parent.addView(photoView);
			doTopPhoto(photoView);
		}
	}

	private void doTopPhoto(PhotoView photoView)
	{
		SBoardPhoto photo = photoView.photo;
		if (board != null)
		{
			board.photos.remove(photo);
			board.photos.add(photo);
		}
		saveBoard();
		hasModify = true;
	}

	@Override
	public void onModify(PhotoView photoView)
	{
		doModifyPhoto(photoView);
		if (mListener != null)
			mListener.onModifyPhoto(photoView.photo.name, photoView.photo.x, photoView.photo.y, photoView.photo.rotation, photoView.photo.width, photoView.photo.height, photoView.photo.hide);
	}

	public void modifyPhoto(String photoName, int photoX, int photoY, int photoRotation, int photoWidth, int photoHeight, boolean hide)
	{
		PhotoView photoView = findPhoto(photoName);
		if (photoView != null)
		{
			photoView.setPosition(photoX, photoY);
			photoView.setAngle(photoRotation);
			photoView.setDimension(photoWidth, photoHeight);
			photoView.setHide(hide);
			doModifyPhoto(photoView);
		}
	}

	private void doModifyPhoto(PhotoView photoView)
	{
		SBoardPhoto photo = photoView.photo;
		photo.x = photoView.getPositionX();
		photo.y = photoView.getPositionY();
		photo.rotation = photoView.getAngle();
		photo.width = photoView.getDimensionWidth();
		photo.height = photoView.getDimensionHeight();
		photo.hide = photoView.getHide();
		saveBoard();
		hasModify = true;
	}

	@Override
	public void onDelete(PhotoView photoView)
	{
		doDeletePhoto(photoView);
		if (mListener != null)
			mListener.onDeletePhoto(photoView.photo.name);
	}

	public void deletePhoto(String photoName)
	{
		PhotoView photoView = findPhoto(photoName);
		if (photoView != null)
		{
			doDeletePhoto(photoView);
		}
	}

	private void doDeletePhoto(PhotoView photoView)
	{
		holder.photoContainer.removeView(photoView);

		SBoardPhoto photo = photoView.photo;
		if (board != null)
			board.photos.remove(photo);
		saveBoard();

		if (dir != null)
		{
			FsUtils.delete(getPhotoFile(dir, photo.name));
			FsUtils.delete(getVideoFile(dir, photo.name));
			FsUtils.delete(getParseFile(dir, photo.name));
		}

		hasModify = true;
	}

	@Override
	public void onModifySelections(PhotoView photoView)
	{
		doModifySelections(photoView);
		if (mListener != null)
			mListener.onModifyPhotoSelections(photoView.photo.name, photoView.photo.selectionGroup);
	}

	public void modifySelections(String photoName, SSelectionGroup selectionGroup)
	{
		PhotoView photoView = findPhoto(photoName);
		if (photoView != null)
		{
			SBoardPhoto photo = photoView.photo;
			photo.selectionGroup.answer = selectionGroup.answer;
			photoView.updateSelections();
			doModifySelections(photoView);
		}
	}

	private void doModifySelections(PhotoView photoView)
	{
		saveBoard();
		hasModify = true;
	}

	@Override
	public void onModifyBoardText(PhotoView photoView)
	{
		doModifyBoardText(photoView);
		if (mListener != null)
			mListener.onModifyPhotoBoardText(photoView.photo.name, photoView.photo.boardText);
	}

	public void modifyBoardText(String photoName, SBoardText boardText)
	{
		PhotoView photoView = findPhoto(photoName);
		if (photoView != null)
		{
			SBoardPhoto photo = photoView.photo;
			photo.boardText = boardText;
			photoView.updateBoardText();
			doModifyBoardText(photoView);
		}
	}

	private void doModifyBoardText(PhotoView photoView)
	{
		saveBoard();
		hasModify = true;
	}

	public void waitSyncOver()
	{
		holder.boardSimple.waitSyncOver();
	}

	public static void clearPhotos(File dir)
	{
		File file = new File(String.format("%s/board.json", dir.getAbsolutePath()));
		if (file.exists())
		{
			SBoardConfig board = SBoardConfig.load(FsUtils.readText(file));

			for (SBoardPhoto photo : board.photos)
			{
				FsUtils.delete(getPhotoFile(dir, photo.name));
				FsUtils.delete(getVideoFile(dir, photo.name));
				FsUtils.delete(getParseFile(dir, photo.name));
			}
			board.photos.clear();

			FsUtils.writeText(file, LOGJson.getStr(board.saveToStr()));
		}
	}

	public static void clearBoard(File dir)
	{
		File boardFile = new File(String.format("%s/board.png", dir.getAbsolutePath()));
		FsUtils.delete(boardFile);
	}

	public static void clearAnswer(File dir)
	{
		File answerFile = new File(String.format("%s/answer.png", dir.getAbsolutePath()));
		FsUtils.delete(answerFile);
	}

	public static File getAnswerFile(File dir)
	{
		return new File(String.format("%s/answer.png", dir.getAbsolutePath()));
	}

	public Bitmap packAnswer(int color)
	{
		if (color == 0)
			return CommonUtils.captureView(holder.boardCon);
		else
			return CommonUtils.captureView(holder.boardCon, color);
	}

	public void genAnswer(int color)
	{
		LOG.v("genAnswer");

		holder.boardSimple.waitSyncOver();

		if (dir != null)
		{
			Bitmap bitmap = packAnswer(color);
			CommonUtils.saveBitmap(bitmap, Bitmap.CompressFormat.PNG, getAnswerFile(dir));
			bitmap.recycle();
		}

//		hasModify = false;
	}

	public static File getSmallFile(File dir)
	{
		return new File(String.format("%s/small.jpg", dir.getAbsolutePath()));
	}

	public Bitmap packSmall(int dstWidth)
	{
		Bitmap bitmap = CommonUtils.captureView(holder.boardCon, 0xffffffff);
		Bitmap small = CommonUtils.scaleBitmap(bitmap, dstWidth);
		bitmap.recycle();
		return small;
	}

	public void genSmall(int dstWidth)
	{
		LOG.v("genSmall");

		holder.boardSimple.waitSyncOver();

		if (dir != null)
		{
			Bitmap bitmap = packSmall(dstWidth);
			CommonUtils.saveBitmap(bitmap, Bitmap.CompressFormat.JPEG, getSmallFile(dir));
			bitmap.recycle();
		}

		hasModify = false;
	}

	public ImageView getAnswerImage()
	{
		return holder.answer;
	}

	private OnListener mListener = null;

	public void setListener(OnListener listener)
	{
		this.mListener = listener;
	}

	public abstract static class OnListener
	{
		public abstract void onLockChanged(boolean isLock);

		public void onScrollOver(int y) {}

		public void onDeletePhoto(String photoName) {}

		public void onTopPhoto(String photoName) {}

		public void onModifyPhoto(String photoName, int photoX, int photoY, int photoRotation, int photoWidth, int photoHeight, boolean hide) {}

		public void onModifyPhotoSelections(String photoName, SSelectionGroup selectionGroup) {}

		public void onModifyPhotoBoardText(String photoName, SBoardText boardText) {}

		public abstract void onPaste();
	}
}
