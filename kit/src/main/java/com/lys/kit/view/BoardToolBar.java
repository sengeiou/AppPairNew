package com.lys.kit.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.Handler;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.lys.base.activity.BaseActivity;
import com.lys.base.utils.AppDataTool;
import com.lys.base.utils.CommonUtils;
import com.lys.base.utils.DrawableFactory;
import com.lys.base.utils.FsUtils;
import com.lys.base.utils.JsonHelper;
import com.lys.base.utils.LOG;
import com.lys.board.config.BoardConfig;
import com.lys.board.dawing.LysBoardDrawingType;
import com.lys.board.utils.LysBoardMenu;
import com.lys.kit.AppKit;
import com.lys.kit.R;
import com.lys.kit.activity.ActivitySelectImage;
import com.lys.kit.activity.KitActivity;
import com.lys.kit.config.Config;
import com.lys.kit.dialog.DialogAddBoardText;
import com.lys.kit.dialog.DialogMenu;
import com.lys.kit.dialog.DialogWait;
import com.lys.kit.module.OssHelper;
import com.lys.kit.pop.PopColor;
import com.lys.kit.pop.PopInsert;
import com.lys.kit.pop.PopPen;
import com.lys.kit.pop.PopWeike;
import com.lys.kit.utils.KitUtils;
import com.lys.protobuf.SBoardPhoto;
import com.lys.protobuf.SBoardText;
import com.lys.protobuf.SClipboard;
import com.lys.protobuf.SClipboardType;
import com.lys.protobuf.SNetPicInfo;
import com.lys.protobuf.SSelectionGroup;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BoardToolBar extends RelativeLayout implements View.OnClickListener, LysBoardMenu
{
	private class Holder
	{
		private View mask;
		private ImageView dirBar;

		private ViewGroup dirCon;

		private ImageView drawingPen;
		private ImageView drawingEraser;

		private ImageView iconColor;

		private CheckBox photoLock;
		private CheckBox touchWrite;

//		private CheckBox muteAudio;
	}

	private Holder holder = new Holder();

	private void initHolder()
	{
		holder.mask = findViewById(R.id.mask);
		holder.dirBar = findViewById(R.id.dirBar);

		holder.dirCon = findViewById(R.id.dirCon);

		holder.drawingPen = findViewById(R.id.drawingPen);
		holder.drawingEraser = findViewById(R.id.drawingEraser);

		holder.iconColor = findViewById(R.id.iconColor);

		holder.photoLock = findViewById(R.id.photoLock);
		holder.touchWrite = findViewById(R.id.touchWrite);

//		holder.muteAudio = findViewById(R.id.muteAudio);
	}

	public void hideIconSend()
	{
		findViewById(R.id.iconSend).setVisibility(View.GONE);
	}

	public void hideIconGrid()
	{
		findViewById(R.id.iconGrid).setVisibility(View.GONE);
	}

	public void hideIconAdd()
	{
		findViewById(R.id.iconAdd).setVisibility(View.GONE);
	}

	public void hideIconAddPage()
	{
		findViewById(R.id.iconAddPage).setVisibility(View.GONE);
	}

//	public void showMuteAudio()
//	{
//		holder.muteAudio.setVisibility(View.VISIBLE);
//	}

	private BoardView board = null;

	private Map<Integer, Boolean> insertIconMap = new HashMap<>();
	private Map<Integer, Boolean> weikeIconMap = new HashMap<>();

	public BoardToolBar(Context context)
	{
		super(context);
		init(context);
	}

	public BoardToolBar(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		init(context);
	}

	private void init(Context context)
	{
		LayoutInflater.from(context).inflate(R.layout.view_board_tool_bar, this, true);
		initHolder();

		holder.mask.setOnTouchListener(onTouchMask);
		findViewById(R.id.dirBar).setOnClickListener(this);

		insertIconMap.put(PopInsert.IconImageSelect, true);
		insertIconMap.put(PopInsert.IconImageSelectImage, false);
		insertIconMap.put(PopInsert.IconImageSelectVideo, false);
		insertIconMap.put(PopInsert.IconImageCamera, true);
		insertIconMap.put(PopInsert.IconImageScreen, true);
		insertIconMap.put(PopInsert.IconImageVideo, true);
		insertIconMap.put(PopInsert.IconImageTopic, true);
		insertIconMap.put(PopInsert.IconImageSelectionGroup, true);
		insertIconMap.put(PopInsert.IconImageBoardText, true);
		if (!AppKit.isStudent())
			insertIconMap.put(PopInsert.IconImageNetImg, true);
		else
			insertIconMap.put(PopInsert.IconImageNetImg, false);

		weikeIconMap.put(PopWeike.IconVideoSelect, true);
		weikeIconMap.put(PopWeike.IconVideoScreen, true);
		weikeIconMap.put(PopWeike.IconVideoVideo, false);
		weikeIconMap.put(PopWeike.IconVideoDelete, true);

		setDrawingType(currPenType);
		setPaintColor(PopColor.Color1);

		findViewById(R.id.iconSend).setOnClickListener(this);
		findViewById(R.id.iconGrid).setOnClickListener(this);
		findViewById(R.id.iconDelete).setOnClickListener(this);
		findViewById(R.id.iconClear).setOnClickListener(this);
		findViewById(R.id.iconAdd).setOnClickListener(this);
		findViewById(R.id.iconReduce).setOnClickListener(this);

		findViewById(R.id.drawingPen).setOnClickListener(this);
		findViewById(R.id.drawingEraser).setOnClickListener(this);

		findViewById(R.id.iconColor).setOnClickListener(this);
		findViewById(R.id.iconInsert).setOnClickListener(this);
		findViewById(R.id.iconWeike).setOnClickListener(this);

		findViewById(R.id.iconAddPage).setOnClickListener(this);

		holder.photoLock.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
		{
			@Override
			public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked)
			{
				board.setLockPhoto(isChecked);
			}
		});
	}

	public void bindBoard(BoardView board)
	{
		this.board = board;
		board.setLockPhoto(holder.photoLock.isChecked());
	}

	public void setInsert(boolean show, Integer... filterIcons)
	{
		if (show)
		{
			findViewById(R.id.iconInsert).setVisibility(View.VISIBLE);
			for (Integer icon : filterIcons)
			{
				insertIconMap.put(icon, false);
			}
		}
		else
		{
			findViewById(R.id.iconInsert).setVisibility(View.GONE);
		}
	}

	public void setWeike(boolean show, Integer... filterIcons)
	{
		if (show)
		{
			findViewById(R.id.iconWeike).setVisibility(View.VISIBLE);
			for (Integer icon : filterIcons)
			{
				weikeIconMap.put(icon, false);
			}
		}
		else
		{
			findViewById(R.id.iconWeike).setVisibility(View.GONE);
		}
	}

	public void photoLockSetChecked(boolean checked)
	{
		holder.photoLock.setChecked(checked);
	}

//	public CheckBox getMuteAudio()
//	{
//		return holder.muteAudio;
//	}

	public void onPaste()
	{
		onPaste(getContext(), board, mListener);
	}

	public static void onPaste(Context context, BoardView board, OnListener listener)
	{
		SClipboard clipboard = Config.readClipboard();
		if (clipboard != null && clipboard.type.equals(SClipboardType.BoardPhoto))
		{
			SBoardPhoto photo = SBoardPhoto.load(clipboard.data2);
			if (photo.type == BoardView.Type_SelectionGroup)
			{
				String name = board.genName();
				PhotoView photoView = board.addPhotoImpl(null, photo, null, name, true);
				if (listener != null)
					listener.onAddSelectionGroup(photoView);
			}
			else if (photo.type == BoardView.Type_BoardText)
			{
				String name = board.genName();
				PhotoView photoView = board.addPhotoImpl(null, photo, null, name, true);
				if (listener != null)
					listener.onAddBoardText(photoView);
			}
			else
			{
				if (photo.type == BoardView.Type_Photo)
				{
					if (!TextUtils.isEmpty(photo.cover))
					{
						String name = board.genName();
						PhotoView photoView = board.addPhotoImpl(null, photo, null, name, true);
						if (listener != null)
							listener.onAddPhoto(photoView, null);
					}
					else
					{
						if (!TextUtils.isEmpty(clipboard.data1))
						{
							File dir = new File(clipboard.data1);
							File photoFile = BoardView.getPhotoFile(dir, photo.name);
							if (photoFile.exists())
							{
								byte[] bitmapData = FsUtils.readBytes(photoFile);
								String name = board.genName();
								board.writePhotoFile(bitmapData, name);
								PhotoView photoView = board.addPhotoImpl(bitmapData, photo, null, name, true);
								if (listener != null)
									listener.onAddPhoto(photoView, bitmapData);
							}
							else
							{
								LOG.toast(context, "粘贴失败，源不存在！");
							}
						}
						else
						{
							LOG.toast(context, "粘贴失败，源不存在！");
						}
					}
				}
				else if (photo.type == BoardView.Type_Video)
				{
					if (!TextUtils.isEmpty(photo.cover))
					{
						String name = board.genName();
						PhotoView photoView = board.addPhotoImpl(null, photo, null, name, true);
						if (listener != null)
							listener.onAddVideo(photoView, null, null);
					}
					else
					{
						if (!TextUtils.isEmpty(clipboard.data1))
						{
							File dir = new File(clipboard.data1);
							File photoFile = BoardView.getPhotoFile(dir, photo.name);
							if (photoFile.exists())
							{
								byte[] bitmapData = FsUtils.readBytes(photoFile);
								String name = board.genName();
								board.writePhotoFile(bitmapData, name);
								if (photo.url.startsWith(BoardView.VideoNet))
								{
									PhotoView photoView = board.addPhotoImpl(bitmapData, photo, null, name, true);
									if (listener != null)
										listener.onAddVideo(photoView, bitmapData, null);
								}
								else if (photo.url.startsWith(BoardView.VideoLocal))
								{
									File videoFile = BoardView.getVideoFile(dir, photo.name);
									if (videoFile.exists())
									{
										byte[] videoData = FsUtils.readBytes(videoFile);
										board.writeVideoFile(videoData, name);
										PhotoView photoView = board.addPhotoImpl(bitmapData, photo, null, name, true);
										if (listener != null)
											listener.onAddVideo(photoView, bitmapData, videoData);
									}
									else
									{
										LOG.toast(context, "粘贴失败，源不存在！");
									}
								}
							}
							else
							{
								LOG.toast(context, "粘贴失败，源不存在！");
							}
						}
						else
						{
							LOG.toast(context, "粘贴失败，源不存在！");
						}
					}
				}
				else if (photo.type == BoardView.Type_Topic)
				{
					if (!TextUtils.isEmpty(photo.cover))
					{
						String name = board.genName();
						PhotoView photoView = board.addPhotoImpl(null, photo, null, name, true);
						if (listener != null)
							listener.onAddTopic(photoView, null, null);
					}
					else
					{
						if (!TextUtils.isEmpty(clipboard.data1))
						{
							File dir = new File(clipboard.data1);
							File photoFile = BoardView.getPhotoFile(dir, photo.name);
							File parseFile = BoardView.getParseFile(dir, photo.name);
							if (photoFile.exists() && parseFile.exists())
							{
								byte[] bitmapData = FsUtils.readBytes(photoFile);
								byte[] parseData = FsUtils.readBytes(parseFile);
								String name = board.genName();
								board.writePhotoFile(bitmapData, name);
								board.writeParseFile(parseData, name);
								PhotoView photoView = board.addPhotoImpl(bitmapData, photo, null, name, true);
								if (listener != null)
									listener.onAddTopic(photoView, bitmapData, parseData);
							}
							else
							{
								LOG.toast(context, "粘贴失败，源不存在！");
							}
						}
						else
						{
							LOG.toast(context, "粘贴失败，源不存在！");
						}
					}
				}
			}
		}
	}

	//------------------- 伸缩菜单逻辑（开始） --------------------------

	private View.OnTouchListener onTouchMask = new View.OnTouchListener()
	{
		@Override
		public boolean onTouch(View view, MotionEvent motionEvent)
		{
			closeDir();
			return false;
		}
	};

	private boolean dirIsOpen = false;

	private void openDir()
	{
		if (!dirIsOpen)
		{
			RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) holder.dirCon.getLayoutParams();
			layoutParams.leftMargin = 0;
			holder.dirCon.setLayoutParams(layoutParams);

			Animation anim = AnimationUtils.loadAnimation(getContext(), R.anim.board_menu_open);
			holder.dirCon.startAnimation(anim);

			dirIsOpen = !dirIsOpen;
			holder.dirBar.setAlpha(0f);
		}
	}

	public void closeDir()
	{
		if (dirIsOpen)
		{
			RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) holder.dirCon.getLayoutParams();
			layoutParams.leftMargin = -134;
			holder.dirCon.setLayoutParams(layoutParams);

			Animation anim = AnimationUtils.loadAnimation(getContext(), R.anim.board_menu_close);
			holder.dirCon.startAnimation(anim);

			dirIsOpen = !dirIsOpen;
			holder.dirBar.setAlpha(dirBarAlpha);
		}
	}

	private float dirBarAlpha = 1f;

	public void transDirBar()
	{
		dirBarAlpha = 0f;
		holder.dirBar.setAlpha(dirBarAlpha);
	}

	//------------------- 伸缩菜单逻辑（结束） --------------------------

	//------------------- 画布菜单（开始） --------------------------

	private int currPenType = KitUtils.getPenType();
	private int currDrawingType;

	private void setDrawingType(int drawingType)
	{
		currDrawingType = drawingType;

		holder.drawingPen.setBackground(null);
		holder.drawingPen.setColorFilter(0x00000000);
		holder.drawingEraser.setBackground(null);
		holder.drawingEraser.setColorFilter(0x00000000);

		if (currDrawingType == LysBoardDrawingType.Eraser)
		{
			holder.drawingEraser.setBackground(DrawableFactory.createCircleFill(0xff595959));
			holder.drawingEraser.setColorFilter(0xffffffff);
		}
		else
		{
			holder.drawingPen.setBackground(DrawableFactory.createCircleFill(0xff595959));
			holder.drawingPen.setColorFilter(0xffffffff);
			currPenType = drawingType;
			holder.drawingPen.setImageResource(PopPen.getPenRes(drawingType));
		}
	}

	@Override
	public int getDrawingType()
	{
		return currDrawingType;
	}

	private int currPaintColor;

	private void setPaintColor(int paintColor)
	{
		currPaintColor = paintColor;
		holder.iconColor.setBackground(DrawableFactory.createCircleFill(paintColor));
	}

	@Override
	public int getPaintColor()
	{
		return currPaintColor;
	}

	@Override
	public float getStrokeWidth()
	{
		return 2.0f;
	}

	@Override
	public String getAnyParam()
	{
		return null;
	}

	@Override
	public boolean touchWrite()
	{
		return holder.touchWrite.isChecked();
	}

	//------------------- 画布菜单（结束） --------------------------

	private KitActivity getKitActivity()
	{
		return (KitActivity) getContext();
	}

	// ------------ 中间层视频或图（开始）

	private boolean isJustSelectImage = false;

	public void justSelectImage()
	{
		isJustSelectImage = true;
	}

	private void onImageSelect()
	{
		if (isJustSelectImage)
		{
			getKitActivity().selectCustomImage(new BaseActivity.OnImageListener()
			{
				@Override
				public void onResult(String filepath)
				{
					gotImage(filepath);
				}
			});
		}
		else
		{
			getKitActivity().selectImageVideo(new BaseActivity.OnImageListener()
			{
				@Override
				public void onResult(String filepath)
				{
					if (ActivitySelectImage.isMovie(filepath))
						gotVideo(filepath);
					else
						gotImage(filepath);
				}
			});
		}
	}

	private void onImageSelectImage()
	{
		getKitActivity().selectCustomImage(new BaseActivity.OnImageListener()
		{
			@Override
			public void onResult(String filepath)
			{
				gotImage(filepath);
			}
		});
	}

	private void onImageSelectVideo()
	{
		getKitActivity().selectCustomVideo(new BaseActivity.OnImageListener()
		{
			@Override
			public void onResult(String filepath)
			{
				gotVideo(filepath);
			}
		});
	}

	private void onImageCamera()
	{
		getKitActivity().camera(new BaseActivity.OnImageListener()
		{
			@Override
			public void onResult(String filepath)
			{
				gotImage(filepath);
			}
		});
	}

	private void onImageScreen()
	{
		DialogMenu.Builder builder = new DialogMenu.Builder(getContext());
		builder.setMenu("复制当前画布内容录制", new DialogMenu.OnClickMenuListener()
		{
			@Override
			public void onClick()
			{
				getKitActivity().record(board.getDir(), new BaseActivity.OnImageListener()
				{
					@Override
					public void onResult(String filepath)
					{
						gotVideo(filepath);
					}
				});
			}
		});
		builder.setMenu("空白画布录制", new DialogMenu.OnClickMenuListener()
		{
			@Override
			public void onClick()
			{
				getKitActivity().record(new BaseActivity.OnImageListener()
				{
					@Override
					public void onResult(String filepath)
					{
						gotVideo(filepath);
					}
				});
			}
		});
		builder.show();
	}

	private void onImageVideo()
	{
		getKitActivity().video(new BaseActivity.OnImageListener()
		{
			@Override
			public void onResult(String filepath)
			{
				gotVideo(filepath);
			}
		});
	}

	private void onImageTopic()
	{
		getKitActivity().topicSearch(new BaseActivity.OnImageListener()
		{
			@Override
			public void onResult(String result)
			{
				if (true)
				{
					List<String> urls = AppDataTool.loadStringList(JsonHelper.getJSONArray(result));
					gotTopic(urls.get(0), Integer.valueOf(urls.get(1)), Integer.valueOf(urls.get(2)), urls.get(3));
				}
//				else
//				{
//					List<String> paths = AppDataTool.loadStringList(JsonHelper.getJSONArray(result));
//					gotTopic(paths.get(0), paths.get(1));
//				}
			}
		});
	}

	private void onImageSelectionGroup()
	{
		getKitActivity().selectionGroup(new BaseActivity.OnImageListener()
		{
			@Override
			public void onResult(String result)
			{
				SSelectionGroup selectionGroup = SSelectionGroup.load(result);
				gotSelectionGroup(selectionGroup);
			}
		});
	}

	private void onImageBoardText()
	{
		DialogAddBoardText.show(getContext(), null, new DialogAddBoardText.OnAddTextListener()
		{
			@Override
			public void onResult(SBoardText boardText)
			{
				gotBoardText(boardText);
			}
		});
	}

	private void onImageNetImg()
	{
		if (isJustSelectImage)
		{
			getKitActivity().selectNetImage(new BaseActivity.OnImageListener()
			{
				@Override
				public void onResult(String netPicStr)
				{
					SNetPicInfo netPic = SNetPicInfo.load(netPicStr);
					PhotoView photoView = board.addPhoto(netPic.bigUrl, netPic.bigWidth, netPic.bigHeight, null);
					mListener.onAddPhoto(photoView, null);
				}
			});
		}
		else
		{
			getKitActivity().selectNetImageVideo(new BaseActivity.OnImageListener()
			{
				@Override
				public void onResult(String netPicStr)
				{
					SNetPicInfo netPic = SNetPicInfo.load(netPicStr);
					if (netPic.isMovie)
					{
						LOG.toast(getContext(), "暂未支持");
					}
					else
					{
						PhotoView photoView = board.addPhoto(netPic.bigUrl, netPic.bigWidth, netPic.bigHeight, null);
						mListener.onAddPhoto(photoView, null);
					}
				}
			});
		}
	}

	private void gotImage(String filepath)
	{
		getKitActivity().cropCustomPic(filepath, new BaseActivity.OnImageListener()
		{
			@Override
			public void onResult(final String filepath)
			{
//				byte[] bitmapData = FsUtils.readBytes(new File(filepath));
//				PhotoView photoView = board.addPhoto(bitmapData, null);
//				mListener.onAddPhoto(photoView, bitmapData);
				DialogWait.show(getContext(), "添加中。。。");
				// 做个异步，避免DialogWait卡住
				new Handler().post(new Runnable()
				{
					@Override
					public void run()
					{
						Bitmap bitmap = CommonUtils.readBitmap(filepath, 1200);
						CommonUtils.saveBitmap(bitmap, Config.tmpPngFile);
						final int bitmapWidth = bitmap.getWidth();
						final int bitmapHeight = bitmap.getHeight();
						bitmap.recycle();
						OssHelper.instance().doUploadMd5FileWithProgress(OssHelper.ZjykFile, Config.tmpPngFile, OssHelper.DirImage(), new OssHelper.OnProgressListener()
						{
							@Override
							public void onProgress(long currentSize, long totalSize)
							{
								int percent = (int) (currentSize * 100.0 / totalSize);
								DialogWait.message(String.format("上传中：%s%%（%s/%s）", percent, CommonUtils.formatSize2(currentSize), CommonUtils.formatSize2(totalSize)));
							}

							@Override
							public void onSuccess(String url)
							{
								Config.tmpPngFile.delete();
								PhotoView photoView = board.addPhoto(url, bitmapWidth, bitmapHeight, null);
								mListener.onAddPhoto(photoView, null);
								DialogWait.close();
							}

							@Override
							public void onFail()
							{
								Config.tmpPngFile.delete();
								DialogWait.close();
								LOG.toast(getContext(), "视频上传失败");
							}
						});
					}
				});
			}
		});
	}

	private void gotVideo(final String filepathSrc)
	{
		DialogWait.show(getContext(), "添加中。。。");
		// 做个异步，避免DialogWait卡住
		new Handler().post(new Runnable()
		{
			@Override
			public void run()
			{
				try
				{
					MediaMetadataRetriever mmr = new MediaMetadataRetriever();
					mmr.setDataSource(filepathSrc);
					final long duration = Long.valueOf(mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION));
//				final int bitrate = Integer.valueOf(mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_BITRATE));
//				final int videoWidth = Integer.valueOf(mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH));
//				final int videoHeight = Integer.valueOf(mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT));
					final Bitmap bitmap = mmr.getFrameAtTime((duration - 1000) * 1000, MediaMetadataRetriever.OPTION_CLOSEST);
					mmr.release();

					if (bitmap != null)
					{
						if (true)
						{
//						if (duration < 20 * 1000) // 20 秒以下用文件
//						{
//							byte[] bitmapData = CommonUtils.saveBitmapToData(bitmap, Bitmap.CompressFormat.PNG, 100);
//							String name = holder.board.addVideoLocal(bitmapData, new File(filepathSrc), duration);
//							if (!TextUtils.isEmpty(name))
//								getBookActivity().syncAddVideoLocal(page.pageDir, name, bitmapData, FsUtils.readBytes(new File(filepathSrc)), duration);
//							DialogWait.close();
//						}
//						else
							{
								OssHelper.instance().doUploadMd5FileWithProgress(OssHelper.ZjykFile, new File(filepathSrc), OssHelper.DirVideo(), new OssHelper.OnProgressListener()
								{
									@Override
									public void onProgress(long currentSize, long totalSize)
									{
										int percent = (int) (currentSize * 100.0 / totalSize);
										DialogWait.message(String.format("上传中：%s%%（%s/%s）", percent, CommonUtils.formatSize2(currentSize), CommonUtils.formatSize2(totalSize)));
									}

									@Override
									public void onSuccess(String url)
									{
										byte[] bitmapData = CommonUtils.saveBitmapToData(bitmap, Bitmap.CompressFormat.PNG, 100);
										PhotoView photoView = board.addVideoNet(bitmapData, url, duration, null);
										mListener.onAddVideo(photoView, bitmapData, null);
										DialogWait.close();
									}

									@Override
									public void onFail()
									{
										DialogWait.close();
										LOG.toast(getContext(), "视频上传失败");
									}
								});
							}
						}
//					else
//					{
//						ModuleCompressor.instance().compressVideo(new File(filepathSrc), Config.tmpMp4File, new ModuleCompressor.CompressListener()
//						{
//							@Override
//							public void onStart()
//							{
//								DialogWait.message("开始压缩。。。");
//							}
//
//							@Override
//							public void onSuccess()
//							{
//								if (duration < 20 * 1000) // 20 秒以下用文件
//								{
//									byte[] bitmapData = CommonUtils.saveBitmapToData(bitmap, Bitmap.CompressFormat.PNG, 100);
//									String name = holder.board.addVideoLocal(bitmapData, Config.tmpMp4File, duration);
//									if (!TextUtils.isEmpty(name))
//										getBookActivity().syncAddVideoLocal(page.pageDir, name, bitmapData, FsUtils.readBytes(Config.tmpMp4File), duration);
//									Config.tmpMp4File.delete();
//									DialogWait.close();
//								}
//								else
//								{
//									OssUtils.doUploadMd5FileWithProgress(OssUtils.ZjykFile, Config.tmpMp4File, OssUtils.DirVideo(), new OssUtils.OnProgressListener()
//									{
//										@Override
//										public void onProgress(long currentSize, long totalSize)
//										{
//											int percent = (int) (currentSize * 100.0 / totalSize);
//											DialogWait.message(String.format("上传中：%s%%（%s/%s）", percent, CommonUtils.formatSize(currentSize), CommonUtils.formatSize(totalSize)));
//										}
//
//										@Override
//										public void onSuccess(String url)
//										{
//											Config.tmpMp4File.delete();
//											byte[] bitmapData = CommonUtils.saveBitmapToData(bitmap, Bitmap.CompressFormat.PNG, 100);
//											String name = holder.board.addVideoNet(bitmapData, url, duration);
//											if (!TextUtils.isEmpty(name))
//												getBookActivity().syncAddVideoNet(page.pageDir, name, bitmapData, url, duration);
//											DialogWait.close();
//										}
//
//										@Override
//										public void onFail()
//										{
//											Config.tmpMp4File.delete();
//											DialogWait.close();
//											LOG.toast(context, "视频上传失败");
//										}
//									});
//								}
//							}
//
//							@Override
//							public void onFail()
//							{
//								DialogWait.close();
//								LOG.toast(context, "视频压缩失败");
//							}
//
//							@Override
//							public void onProgress(float percent)
//							{
//								DialogWait.message(String.format("压缩中：%s%%", percent));
//							}
//						});
//					}
					}
					else
					{
						DialogWait.close();
						LOG.toast(getContext(), "视频解析失败");
					}
				}
				catch (Exception e)
				{
					e.printStackTrace();
					DialogWait.close();
					LOG.toast(getContext(), "添加失败");
				}
			}
		});
	}

	private void gotTopic(String problemUrl, int problemWidth, int problemHeight, String parseUrl)
	{
		PhotoView photoView = board.addTopic(problemUrl, problemWidth, problemHeight, parseUrl, null);
		mListener.onAddTopic(photoView, null, null);
	}

//	private void gotTopic(String problemPath, String parsePath)
//	{
//		byte[] bitmapData = FsUtils.readBytes(new File(problemPath));
//		byte[] parseData = FsUtils.readBytes(new File(parsePath));
//		PhotoView photoView = board.addTopic(bitmapData, parseData, null);
//		mListener.onAddTopic(photoView, bitmapData, parseData);
//	}

	private void gotSelectionGroup(SSelectionGroup selectionGroup)
	{
		PhotoView photoView = board.addSelectionGroup(selectionGroup, null);
		mListener.onAddSelectionGroup(photoView);
	}

	private void gotBoardText(SBoardText boardText)
	{
		PhotoView photoView = board.addBoardText(boardText, null);
		mListener.onAddBoardText(photoView);
	}

	// ------------ 中间层视频或图（结束）

	// ------------ 大背景视频（开始）

	private void onVideoSelect()
	{
		getKitActivity().selectCustomVideo(new BaseActivity.OnImageListener()
		{
			@Override
			public void onResult(String filepath)
			{
				if (filepath.startsWith(FsUtils.SD_CARD))
					gotBigVideo(filepath);
				else
					LOG.toast(getContext(), "暂未支持");
			}
		});
	}

	private void onVideoScreen()
	{
		DialogMenu.Builder builder = new DialogMenu.Builder(getContext());
		builder.setMenu("复制当前画布内容录制", new DialogMenu.OnClickMenuListener()
		{
			@Override
			public void onClick()
			{
				getKitActivity().record(board.getDir(), new BaseActivity.OnImageListener()
				{
					@Override
					public void onResult(String filepath)
					{
						gotBigVideo(filepath);
					}
				});
			}
		});
		builder.setMenu("空白画布录制", new DialogMenu.OnClickMenuListener()
		{
			@Override
			public void onClick()
			{
				getKitActivity().record(new BaseActivity.OnImageListener()
				{
					@Override
					public void onResult(String filepath)
					{
						gotBigVideo(filepath);
					}
				});
			}
		});
		builder.show();
	}

	private void onVideoVideo()
	{
		getKitActivity().video(new BaseActivity.OnImageListener()
		{
			@Override
			public void onResult(String filepath)
			{
				gotBigVideo(filepath);
			}
		});
	}

	private void onVideoDelete()
	{
		if (board.getDir() != null)
		{
			LOG.v("onVideoDelete");
			mListener.onDeleteBigVideoBefore();
			File photoFile = new File(String.format("%s/%s.jpg", board.getDir().getAbsolutePath(), BoardConfig.big_video));
			File videoFile = new File(String.format("%s/%s.mp4", board.getDir().getAbsolutePath(), BoardConfig.big_video));
			File videoUrlFile = new File(String.format("%s/%s.txt", board.getDir().getAbsolutePath(), BoardConfig.big_video));
			FsUtils.delete(photoFile);
			FsUtils.delete(videoFile);
			FsUtils.delete(videoUrlFile);
		}
	}

	private void gotBigVideo(final String filepathSrc)
	{
		DialogWait.show(getContext(), "添加中。。。");
		onVideoDelete();
		// 做个异步，避免DialogWait卡住
		new Handler().post(new Runnable()
		{
			@Override
			public void run()
			{
				try
				{
					MediaMetadataRetriever mmr = new MediaMetadataRetriever();
					mmr.setDataSource(filepathSrc);
					final long duration = Long.valueOf(mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION));
//				final int bitrate = Integer.valueOf(mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_BITRATE));
//				final int videoWidth = Integer.valueOf(mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH));
//				final int videoHeight = Integer.valueOf(mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT));
					final Bitmap bitmap = mmr.getFrameAtTime(0, MediaMetadataRetriever.OPTION_CLOSEST);
					mmr.release();

					if (bitmap != null)
					{
						if (true)
						{
//						if (duration < 20 * 1000) // 20 秒以下用文件
//						{
//							addVideoLocal(bitmap, new File(filepathSrc), duration);
//							DialogWait.close();
//						}
//						else
							{
								OssHelper.instance().doUploadMd5FileWithProgress(OssHelper.ZjykFile, new File(filepathSrc), OssHelper.DirVideo(), new OssHelper.OnProgressListener()
								{
									@Override
									public void onProgress(long currentSize, long totalSize)
									{
										int percent = (int) (currentSize * 100.0 / totalSize);
										DialogWait.message(String.format("上传中：%s%%（%s/%s）", percent, CommonUtils.formatSize2(currentSize), CommonUtils.formatSize2(totalSize)));
									}

									@Override
									public void onSuccess(String url)
									{
										addVideoNet(bitmap, url, duration);
										DialogWait.close();
									}

									@Override
									public void onFail()
									{
										DialogWait.close();
										LOG.toast(getContext(), "视频上传失败");
									}
								});
							}
						}
//					else
//					{
//						ModuleCompressor.instance().compressVideo(new File(filepathSrc), Config.tmpMp4File, new ModuleCompressor.CompressListener()
//						{
//							@Override
//							public void onStart()
//							{
//							}
//
//							@Override
//							public void onSuccess()
//							{
//								addVideoLocal(bitmap, Config.tmpMp4File, duration);
//								Config.tmpMp4File.delete();
//								DialogWait.close();
//							}
//
//							@Override
//							public void onFail()
//							{
//								DialogWait.close();
//								LOG.toast(context, "视频压缩失败");
//							}
//
//							@Override
//							public void onProgress(float percent)
//							{
//							}
//						});
//					}
					}
					else
					{
						DialogWait.close();
						LOG.toast(getContext(), "视频解析失败");
					}
				}
				catch (Exception e)
				{
					e.printStackTrace();
					DialogWait.close();
					LOG.toast(getContext(), "添加失败");
				}
			}
		});
	}

	private void addVideoLocal(Bitmap bitmap, File compressVideoFile, long duration)
	{
		if (board.getDir() != null)
		{
			File photoFile = new File(String.format("%s/%s.jpg", board.getDir().getAbsolutePath(), BoardConfig.big_video));
			File videoFile = new File(String.format("%s/%s.mp4", board.getDir().getAbsolutePath(), BoardConfig.big_video));
			FsUtils.copy(compressVideoFile, videoFile);
			CommonUtils.saveBitmap(bitmap, Bitmap.CompressFormat.JPEG, photoFile);
			mListener.onAddBigVideoOver();
		}
	}

	private void addVideoNet(Bitmap bitmap, String url, long duration)
	{
		if (board.getDir() != null)
		{
			File photoFile = new File(String.format("%s/%s.jpg", board.getDir().getAbsolutePath(), BoardConfig.big_video));
			File videoUrlFile = new File(String.format("%s/%s.txt", board.getDir().getAbsolutePath(), BoardConfig.big_video));
			FsUtils.writeText(videoUrlFile, url);
			CommonUtils.saveBitmap(bitmap, Bitmap.CompressFormat.JPEG, photoFile);
			mListener.onAddBigVideoOver();
		}
	}

	// ------------ 大背景视频（结束）

	private void onClear()
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
		builder.setTitle("确定要清除画板吗？");
		builder.setMessage("清除后不可恢复！");
		builder.setNeutralButton("取消", null);
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialogInterface, int which)
			{
				board.clear();
				LOG.toast(getContext(), "已清除");
				mListener.onIconClearOver();
			}
		});
		builder.show();
	}

	private static final int min_height = 1200;
	private static final int step_height = 600;
	private static final int max_height = min_height + 2 * step_height;

	private void onAdd()
	{
		if (board.getBoardHeight() < max_height)
		{
			int newHeight = Math.min(board.getBoardHeight() + step_height, max_height);
			board.setBoardHeight(newHeight);
			LOG.toast(getContext(), "已增加到：" + newHeight);
			mListener.onIconAddOver(newHeight);
		}
		else
		{
			LOG.toast(getContext(), "不可再增加");
		}
	}

	private void onReduce()
	{
		if (board.getBoardHeight() > min_height)
		{
			final int newHeight = Math.max(board.getBoardHeight() - step_height, min_height);
			AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
			builder.setTitle("确定要减少画板吗？");
			builder.setMessage("因减少画板丢失的内容不可找回！");
			builder.setNeutralButton("取消", null);
			builder.setPositiveButton("确定", new DialogInterface.OnClickListener()
			{
				@Override
				public void onClick(DialogInterface dialogInterface, int which)
				{
					board.setBoardHeight(newHeight);
					LOG.toast(getContext(), "已减少到：" + newHeight);
					mListener.onIconReduceOver(newHeight);
				}
			});
			builder.show();
		}
		else
		{
			LOG.toast(getContext(), "不可再减少");
		}
	}

	@Override
	public void onClick(View view)
	{
		if (view.getId() == R.id.dirBar)
		{
			if (dirIsOpen)
				closeDir();
			else
				openDir();
		}
		else if (view.getId() == R.id.iconSend)
		{
			closeDir();
			mListener.onIconSend();
		}
		else if (view.getId() == R.id.iconGrid)
		{
			closeDir();
			mListener.onIconGrid();
		}
		else if (view.getId() == R.id.iconDelete)
		{
			closeDir();
			mListener.onIconDelete();
		}
		else if (view.getId() == R.id.iconClear)
		{
			closeDir();
			onClear();
		}
		else if (view.getId() == R.id.iconAdd)
		{
			closeDir();
			onAdd();
		}
		else if (view.getId() == R.id.iconReduce)
		{
			closeDir();
			onReduce();
		}
		else if (view.getId() == R.id.drawingPen)
		{
			if (currDrawingType == LysBoardDrawingType.Eraser)
			{
				setDrawingType(currPenType);
			}
			else
			{
				PopPen.show(getContext(), view, new PopPen.OnClickListener()
				{
					@Override
					public void onClick(int penType)
					{
						setDrawingType(penType);
					}
				});
			}
		}
		else if (view.getId() == R.id.drawingEraser)
		{
			setDrawingType(LysBoardDrawingType.Eraser);
		}
		else if (view.getId() == R.id.iconColor)
		{
			PopColor.show(getContext(), view, new PopColor.OnClickListener()
			{
				@Override
				public void onClick(int color)
				{
					setPaintColor(color);
				}
			});
		}
		else if (view.getId() == R.id.iconInsert)
		{
			PopInsert.show(getContext(), view, new PopInsert.OnClickListener()
			{
				@Override
				public void onClickSelect()
				{
					closeDir();
					onImageSelect();
				}

				@Override
				public void onClickSelectImage()
				{
					closeDir();
					onImageSelectImage();
				}

				@Override
				public void onClickSelectVideo()
				{
					closeDir();
					onImageSelectVideo();
				}

				@Override
				public void onClickCamera()
				{
					closeDir();
					onImageCamera();
				}

				@Override
				public void onClickScreen()
				{
					closeDir();
					onImageScreen();
				}

				@Override
				public void onClickVideo()
				{
					closeDir();
					onImageVideo();
				}

				@Override
				public void onClickTopic()
				{
					closeDir();
					onImageTopic();
				}

				@Override
				public void onClickSelectionGroup()
				{
					closeDir();
					onImageSelectionGroup();
				}

				@Override
				public void onClickBoardText()
				{
					closeDir();
					onImageBoardText();
				}

				@Override
				public void onClickNetImg()
				{
					closeDir();
					onImageNetImg();
				}
			}, insertIconMap);
		}
		else if (view.getId() == R.id.iconWeike)
		{
			PopWeike.show(getContext(), view, new PopWeike.OnClickListener()
			{
				@Override
				public void onClickSelect()
				{
					closeDir();
					onVideoSelect();
				}

				@Override
				public void onClickScreen()
				{
					closeDir();
					onVideoScreen();
				}

				@Override
				public void onClickVideo()
				{
					closeDir();
					onVideoVideo();
				}

				@Override
				public void onClickDelete()
				{
					closeDir();
					onVideoDelete();
				}
			}, weikeIconMap);
		}
		else if (view.getId() == R.id.iconAddPage)
		{
			mListener.onIconAddPage();
		}
	}

	private OnListener mListener = null;

	public void setListener(OnListener listener)
	{
		this.mListener = listener;
	}

	public abstract static class OnListener
	{
		public void onIconSend() {}

		public void onIconGrid() {}

		public void onIconDelete() {}

		public void onIconClearOver() {}

		public void onIconAddOver(int newHeight) {}

		public void onIconReduceOver(int newHeight) {}

		public void onAddPhoto(PhotoView photoView, byte[] bitmapData) {}

		public void onAddVideo(PhotoView photoView, byte[] bitmapData, byte[] videoData) {}

		public void onAddTopic(PhotoView photoView, byte[] bitmapData, byte[] parseData) {}

		public void onAddSelectionGroup(PhotoView photoView) {}

		public void onAddBoardText(PhotoView photoView) {}

		public void onDeleteBigVideoBefore() {}

		public void onAddBigVideoOver() {}

		public void onIconAddPage() {}
	}
}
