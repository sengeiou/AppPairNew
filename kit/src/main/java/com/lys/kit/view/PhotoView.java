package com.lys.kit.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lys.base.utils.LOG;
import com.lys.base.utils.VideoLoader;
import com.lys.board.utils.LysBoardUtils;
import com.lys.kit.R;
import com.lys.kit.config.Config;
import com.lys.kit.dialog.DialogAddBoardText;
import com.lys.kit.dialog.DialogMenu;
import com.lys.kit.dialog.DialogPic;
import com.lys.kit.module.ModulePlayer;
import com.lys.kit.utils.ImageLoad;
import com.lys.protobuf.SBoardPhoto;
import com.lys.protobuf.SBoardText;
import com.lys.protobuf.SClipboard;
import com.lys.protobuf.SClipboardType;

import java.io.File;

@SuppressLint("AppCompatCustomView")
public class PhotoView extends FrameLayout implements View.OnClickListener
{
	private static final boolean enableRotate = false;

	public static final int maskOffset = 14 * 2;
	public static final int adjustOffset = 5;

	private File dir = null;
	public SBoardPhoto photo = null;

	//---------------------------------------------------------

	private boolean lockPhoto = false;

	public boolean isLockPhoto()
	{
		return lockPhoto;
	}

	public void setLockPhoto(boolean lockPhoto)
	{
		this.lockPhoto = lockPhoto;
		if (lockPhoto)
		{
			hideBtn();
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
		this.showRightAnswer = showRightAnswer;
		if (photo.type == BoardView.Type_SelectionGroup)
			holder.selectionGroup.updateSelections(showRightAnswer);
	}

	//---------------------------------------------------------

	private boolean showParse = true;

	public boolean isShowParse()
	{
		return showParse;
	}

	public void setShowParse(boolean showParse)
	{
		this.showParse = showParse;
		if (photo.type == BoardView.Type_Topic && !photo.hide)
			holder.parse.setVisibility(showParse ? View.VISIBLE : View.GONE);
	}

	//---------------------------------------------------------

	private class Holder
	{
		private ViewGroup con;
		private View mask;
		private ImageView photo;
		private ImageView showFlag;
		private ImageView rotate;
		private ImageView videoPlay;
		private TextView durationText;
		private TextView parse;
		private SelectionGroup selectionGroup;
		private TextView boardText;
	}

	private Holder holder = new Holder();

	private void initHolder()
	{
		holder.con = findViewById(R.id.con);
		holder.mask = findViewById(R.id.mask);
		holder.photo = findViewById(R.id.photo);
		holder.showFlag = findViewById(R.id.showFlag);
		holder.rotate = findViewById(R.id.rotate);
		holder.videoPlay = findViewById(R.id.videoPlay);
		holder.durationText = findViewById(R.id.durationText);
		holder.parse = findViewById(R.id.parse);
		holder.selectionGroup = findViewById(R.id.selectionGroup);
		holder.boardText = findViewById(R.id.boardText);
	}

	public PhotoView(@NonNull Context context)
	{
		super(context);
	}

	public void init(File dir, SBoardPhoto photo)
	{
		LayoutInflater.from(getContext()).inflate(R.layout.view_photo, this, true);

		initHolder();

		holder.con.setOnTouchListener(touchPhoto);
		holder.showFlag.setOnClickListener(this);
		holder.rotate.setOnClickListener(this);
		holder.videoPlay.setOnClickListener(this);
		holder.parse.setOnClickListener(this);

		this.dir = dir;
		this.photo = photo;

		if (photo.type == BoardView.Type_Photo)
		{
			holder.photo.setBackground(null);
			holder.videoPlay.setVisibility(View.GONE);
			holder.durationText.setVisibility(View.GONE);
			holder.parse.setVisibility(View.GONE);
			holder.selectionGroup.setVisibility(View.GONE);
		}
		else if (photo.type == BoardView.Type_Video)
		{
			holder.videoPlay.setVisibility(View.VISIBLE);
			holder.durationText.setVisibility(View.VISIBLE);
			holder.durationText.setText(formatTime(photo.duration));
			holder.parse.setVisibility(View.GONE);
			holder.selectionGroup.setVisibility(View.GONE);
		}
		else if (photo.type == BoardView.Type_Topic)
		{
			holder.photo.setBackground(null);
			holder.videoPlay.setVisibility(View.GONE);
			holder.durationText.setVisibility(View.GONE);
			holder.parse.setVisibility(showParse ? View.VISIBLE : View.GONE);
			holder.selectionGroup.setVisibility(View.GONE);
		}
		else if (photo.type == BoardView.Type_SelectionGroup)
		{
			holder.photo.setBackground(null);
			holder.showFlag.setVisibility(View.GONE);
			holder.videoPlay.setVisibility(View.GONE);
			holder.durationText.setVisibility(View.GONE);
			holder.parse.setVisibility(View.GONE);
			holder.selectionGroup.setVisibility(View.VISIBLE);
			holder.selectionGroup.unlockSelections();
			holder.selectionGroup.setSelectionGroup(photo.selectionGroup);
			holder.selectionGroup.updateSelections(showRightAnswer);
			holder.selectionGroup.setListener(new SelectionGroup.OnListener()
			{
				@Override
				public void onModifySelections()
				{
					if (mListener != null)
						mListener.onModifySelections(PhotoView.this);
				}
			});
		}
		else if (photo.type == BoardView.Type_BoardText)
		{
			holder.photo.setBackground(null);
			holder.showFlag.setVisibility(View.GONE);
			holder.videoPlay.setVisibility(View.GONE);
			holder.durationText.setVisibility(View.GONE);
			holder.parse.setVisibility(View.GONE);
			holder.selectionGroup.setVisibility(View.GONE);
			holder.boardText.setVisibility(View.VISIBLE);

//			SBoardText data = new SBoardText();
//			data.text = "文本测试文本测试文本测试文本测文本测试文本测试文本测试文本测文本测试文本测试文本测试文本测";

			updateBoardText();
		}

		if (photo.notEye)
			holder.showFlag.setVisibility(View.GONE);

		hideBtn();
		checkShowHide();
	}

	public void updateBoardText()
	{
		DialogAddBoardText.applyBoardText(holder.boardText, photo.boardText);
	}

	private String formatTime(long ms)
	{
		int second = (int) (ms / 1000);
		int minute = second / 60;
		second = second % 60;
		int hour = minute / 60;
		minute = minute % 60;
		if (hour == 0)
			return String.format("%d:%02d", minute, second);
		else
			return String.format("%d:%02d:%02d", hour, minute, second);
	}

	public void hideBtn()
	{
		if (holder.mask.getVisibility() != View.GONE)
		{
			holder.mask.setVisibility(View.GONE);
			holder.rotate.setVisibility(View.GONE);
			holder.videoPlay.setClickable(true);
			if (photo.type == BoardView.Type_SelectionGroup)
				holder.selectionGroup.unlockSelections();
		}
	}

	public void showBtn()
	{
		if (holder.mask.getVisibility() != View.VISIBLE)
		{
			holder.mask.setVisibility(View.VISIBLE);
			holder.rotate.setVisibility(View.GONE);
			holder.videoPlay.setClickable(false);
			if (photo.type == BoardView.Type_SelectionGroup)
				holder.selectionGroup.lockSelections();
		}
	}

	private void checkShowHide()
	{
		if (photo.hide)
		{
			holder.showFlag.setImageResource(R.drawable.img_show_flag_open);
			holder.showFlag.setImageAlpha(255);
			if (photo.type == BoardView.Type_Photo)
			{
				holder.photo.setVisibility(View.GONE);
			}
			else if (photo.type == BoardView.Type_Video)
			{
				holder.photo.setVisibility(View.GONE);
				holder.videoPlay.setVisibility(View.GONE);
				holder.durationText.setVisibility(View.GONE);
			}
			else if (photo.type == BoardView.Type_Topic)
			{
				holder.photo.setVisibility(View.GONE);
				holder.parse.setVisibility(View.GONE);
			}
		}
		else
		{
			holder.showFlag.setImageResource(R.drawable.img_show_flag_close);
			holder.showFlag.setImageAlpha(100);
			if (photo.type == BoardView.Type_Photo)
			{
				holder.photo.setVisibility(View.VISIBLE);
			}
			else if (photo.type == BoardView.Type_Video)
			{
				holder.photo.setVisibility(View.VISIBLE);
				holder.videoPlay.setVisibility(View.VISIBLE);
				holder.durationText.setVisibility(View.VISIBLE);
			}
			else if (photo.type == BoardView.Type_Topic)
			{
				holder.photo.setVisibility(View.VISIBLE);
				holder.parse.setVisibility(showParse ? View.VISIBLE : View.GONE);
			}
		}
	}

	@Override
	public void onClick(View view)
	{
		if (view.getId() == R.id.showFlag)
		{
			setHide(!photo.hide);
			if (mListener != null)
				mListener.onModify(this);
		}
		else if (view.getId() == R.id.rotate)
		{
			if (holder.con.getRotation() < 90)
			{
				holder.con.setRotation(90);
			}
			else if (holder.con.getRotation() < 180)
			{
				holder.con.setRotation(180);
			}
			else if (holder.con.getRotation() < 270)
			{
				holder.con.setRotation(270);
			}
			else
			{
				holder.con.setRotation(0);
			}
			checkShowFlagPosition();
			if (mListener != null)
				mListener.onModify(this);
		}
		else if (view.getId() == R.id.videoPlay)
		{
			if (photo.url.startsWith(BoardView.VideoNet))
			{
				String url = ImageLoad.checkUrl(photo.url.substring(BoardView.VideoNet.length()));
				File cacheFile = VideoLoader.getCacheFile(getContext(), url);
				if (cacheFile.exists())
					ModulePlayer.instance().playSimple(getContext(), cacheFile.getAbsolutePath());
				else
					ModulePlayer.instance().playSimple(getContext(), Uri.parse(url));
			}
			else if (photo.url.startsWith(BoardView.VideoLocal))
			{
				if (dir != null)
				{
					File videoFile = new File(String.format("%s/%s.mp4", dir.getAbsolutePath(), photo.name));
					if (videoFile.exists())
						ModulePlayer.instance().playSimple(getContext(), videoFile.getAbsolutePath());
				}
			}
		}
		else if (view.getId() == R.id.parse)
		{
			if (dir != null)
			{
				File parseFile = new File(String.format("%s/%s.parse.png", dir.getAbsolutePath(), photo.name));
				if (parseFile.exists())
				{
					DialogPic.show(getContext(), parseFile.getAbsolutePath());
				}
				else
				{
					if (!TextUtils.isEmpty(photo.url))
						DialogPic.show(getContext(), photo.url);
				}
			}
			else
			{
				if (!TextUtils.isEmpty(photo.url))
					DialogPic.show(getContext(), photo.url);
			}
		}
	}

	private void checkShowFlagPosition()
	{
		RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) holder.showFlag.getLayoutParams();
		layoutParams.removeRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		layoutParams.removeRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		LOG.v("holder.con.getRotation():" + holder.con.getRotation());
		if (holder.con.getRotation() < 90)
		{
			layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
			holder.showFlag.setRotation(0);
		}
		else if (holder.con.getRotation() < 180)
		{
			holder.showFlag.setRotation(270);
		}
		else if (holder.con.getRotation() < 270)
		{
			layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
			holder.showFlag.setRotation(180);
		}
		else
		{
			layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
			layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
			holder.showFlag.setRotation(90);
		}
		holder.showFlag.setLayoutParams(layoutParams);
	}

	private void resetDimensionAndPos()
	{
//		PointF initCenter = new PointF();
//		initCenter.set(holder.con.getTranslationX() + holder.con.getWidth() / 2, holder.con.getTranslationY() + holder.con.getHeight() / 2);

		ViewGroup.LayoutParams layoutParams = holder.con.getLayoutParams();
		if (photo.type == BoardView.Type_SelectionGroup)
		{
			layoutParams.width = BoardView.SelectionGroupWidth;
			layoutParams.height = BoardView.SelectionGroupHeight;
		}
		else if (photo.type == BoardView.Type_BoardText)
		{
			layoutParams.width = BoardView.BoardTextWidth;
			layoutParams.height = BoardView.BoardTextHeight;
		}
		else
		{
			BitmapDrawable bitmapDrawable = (BitmapDrawable) holder.photo.getDrawable();
			Bitmap bitmap = bitmapDrawable.getBitmap();
			if (bitmap.getWidth() < bitmap.getHeight())
			{
				int width = bitmap.getWidth();
				layoutParams.width = width + PhotoView.maskOffset;
				layoutParams.height = bitmap.getHeight() * (layoutParams.width - PhotoView.maskOffset) / bitmap.getWidth() + PhotoView.maskOffset;
			}
			else
			{
				int height = bitmap.getHeight();
				layoutParams.height = height + PhotoView.maskOffset;
				layoutParams.width = bitmap.getWidth() * (layoutParams.height - PhotoView.maskOffset) / bitmap.getHeight() + PhotoView.maskOffset;
			}
		}
		holder.con.setLayoutParams(layoutParams);

//		float currX = initCenter.x - layoutParams.width / 2;
//		float currY = initCenter.y - layoutParams.height / 2;

//		holder.con.setTranslationX(currX);
//		holder.con.setTranslationY(currY);

		ViewGroup parent = (ViewGroup) getParent();

		holder.con.setTranslationX((parent.getWidth() - layoutParams.width) / 2);
		holder.con.setTranslationY((parent.getHeight() - layoutParams.height) / 2);
	}

	private OnTouchListener touchPhoto = new OnTouchListener()
	{
		private static final int NONE = 0;
		private static final int DRAG = 1;
		private static final int ZOOM = 2;

		private int mode = NONE;

		private PointF initPoint = new PointF();
		private PointF initPosition = new PointF();

		private float initDistanceX;
		private float initDistanceY;
		private float initDistance;
		private float initWidth;
		private float initHeight;
		private PointF initCenter = new PointF();

		private float initAngle;
		private float initRotation;
		private boolean hasInit;

		private Runnable longClick = new Runnable()
		{
			@Override
			public void run()
			{
				DialogMenu.Builder builder = new DialogMenu.Builder(getContext());
				builder.setMenu("删除", new DialogMenu.OnClickMenuListener()
				{
					@Override
					public void onClick()
					{
						if (mListener != null)
							mListener.onDelete(PhotoView.this);
					}
				});
				builder.setMenu("拷贝", new DialogMenu.OnClickMenuListener()
				{
					@Override
					public void onClick()
					{
						SClipboard clipboard = new SClipboard();
						clipboard.type = SClipboardType.BoardPhoto;
						clipboard.data1 = dir != null ? dir.toString() : null;
						clipboard.data2 = photo.saveToStr();
						Config.writeClipboard(clipboard);
					}
				});
				builder.setMenu("旋转", new DialogMenu.OnClickMenuListener()
				{
					@Override
					public void onClick()
					{
						if (holder.con.getRotation() < 90)
						{
							holder.con.setRotation(90);
						}
						else if (holder.con.getRotation() < 180)
						{
							holder.con.setRotation(180);
						}
						else if (holder.con.getRotation() < 270)
						{
							holder.con.setRotation(270);
						}
						else
						{
							holder.con.setRotation(0);
						}
						checkShowFlagPosition();
						if (mListener != null)
							mListener.onModify(PhotoView.this);
					}
				});
				builder.setMenu("重置", new DialogMenu.OnClickMenuListener()
				{
					@Override
					public void onClick()
					{
						resetDimensionAndPos();
						if (mListener != null)
							mListener.onModify(PhotoView.this);
					}
				});
				builder.setMenu("锁定", new DialogMenu.OnClickMenuListener()
				{
					@Override
					public void onClick()
					{
						setLockPhoto(true);
					}
				});
				if (photo.type == BoardView.Type_BoardText)
				{
					builder.setMenu("编辑", new DialogMenu.OnClickMenuListener()
					{
						@Override
						public void onClick()
						{
							DialogAddBoardText.show(getContext(), photo.boardText, new DialogAddBoardText.OnAddTextListener()
							{
								@Override
								public void onResult(SBoardText boardText)
								{
									photo.boardText = boardText;
									updateBoardText();
									if (mListener != null)
										mListener.onModifyBoardText(PhotoView.this);
								}
							});
						}
					});
				}
				builder.show();
			}
		};

		private Point lastPoint = null;

		@Override
		public boolean onTouch(View view, MotionEvent event)
		{
			if (!photo.lock && !lockPhoto && !LysBoardUtils.isPen(event))
			{
				ViewGroup parent = (ViewGroup) getParent();
				parent.requestDisallowInterceptTouchEvent(true);
				int action = (event.getAction() & MotionEvent.ACTION_MASK);

				if (action == MotionEvent.ACTION_DOWN)
				{
					lastPoint = new Point(Math.round(event.getRawX()), Math.round(event.getRawY()));
				}
				else if (action == MotionEvent.ACTION_MOVE)
				{
					Point point = new Point(Math.round(event.getRawX()), Math.round(event.getRawY()));
					if (Math.abs(point.x - lastPoint.x) < 4 && Math.abs(point.y - lastPoint.y) < 4)
//					if (point.equals(lastPoint))
						return true;
					lastPoint = point;
				}

				LOG.v(String.format("------------%s : %s, %s", action, Math.round(event.getRawX()), Math.round(event.getRawY())));

				removeCallbacks(longClick);
				if (action == MotionEvent.ACTION_DOWN)
					postDelayed(longClick, 500);
				switch (action)
				{
				case MotionEvent.ACTION_DOWN:
//					holder.photo.setAlpha(0.8f);

					PhotoView topPhotoView = (PhotoView) parent.getChildAt(parent.getChildCount() - 1);
					topPhotoView.hideBtn();

					PhotoView.this.showBtn();

					parent.removeView(PhotoView.this);
					parent.addView(PhotoView.this);
					if (mListener != null)
						mListener.onTop(PhotoView.this);

					initPoint.set(event.getRawX(), event.getRawY());
					initPosition.set(holder.con.getTranslationX(), holder.con.getTranslationY());
					mode = DRAG;
					break;
				case MotionEvent.ACTION_POINTER_DOWN:
					initDistanceX = distanceX(event);
					initDistanceY = distanceY(event);
					initDistance = distance(event);
					initWidth = holder.con.getWidth();
					initHeight = holder.con.getHeight();
					initCenter.set(holder.con.getTranslationX() + holder.con.getWidth() / 2, holder.con.getTranslationY() + holder.con.getHeight() / 2);

					initAngle = angle(event);
					initRotation = holder.con.getRotation();
					hasInit = true;

					mode = ZOOM;
					break;
				case MotionEvent.ACTION_MOVE:
					if (mode == DRAG)
					{
						float offsetX = event.getRawX() - initPoint.x;
						float offsetY = event.getRawY() - initPoint.y;

						float currX = initPosition.x + offsetX;
						float currY = initPosition.y + offsetY;

						currX = Math.max(currX, -holder.con.getWidth() / 2);
						currX = Math.min(currX, getWidth() - holder.con.getWidth() / 2);
						currY = Math.max(currY, -holder.con.getHeight() / 2);
						currY = Math.min(currY, getHeight() - holder.con.getHeight() / 2);

						holder.con.setTranslationX(currX);
						holder.con.setTranslationY(currY);
					}
					else if (mode == ZOOM)
					{
						ViewGroup.LayoutParams layoutParams = holder.con.getLayoutParams();
						if (photo.type == BoardView.Type_SelectionGroup || photo.type == BoardView.Type_BoardText)
						{
							float offsetX = distanceX(event) - initDistanceX;
							float offsetY = distanceY(event) - initDistanceY;
							int width = (int) (initWidth + offsetX);
							int height = (int) (initHeight + offsetY);
//							width = Math.max(width, 300);
//							width = Math.min(width, 1920);
//							height = Math.max(height, 210);
//							height = Math.min(height, 1200);
							layoutParams.width = width;
							layoutParams.height = height;
						}
						else
						{
							float offsetScale = distance(event) / initDistance;
							BitmapDrawable bitmapDrawable = (BitmapDrawable) holder.photo.getDrawable();
							if (bitmapDrawable != null)
							{
								Bitmap bitmap = bitmapDrawable.getBitmap();
								if (bitmap.getWidth() < bitmap.getHeight())
								{
									int width = (int) (initWidth * offsetScale);
//									width = Math.max(width, Math.max(bitmap.getWidth() * 500 / bitmap.getHeight(), 120));
//									width = Math.min(width, 1920 * 3);
									layoutParams.width = width;
									layoutParams.height = bitmap.getHeight() * (layoutParams.width - maskOffset) / bitmap.getWidth() + maskOffset;
								}
								else
								{
									int height = (int) (initHeight * offsetScale);
//									height = Math.max(height, Math.max(bitmap.getHeight() * 500 / bitmap.getWidth(), 120));
//									height = Math.min(height, 1200 * 3);
									layoutParams.height = height;
									layoutParams.width = bitmap.getWidth() * (layoutParams.height - maskOffset) / bitmap.getHeight() + maskOffset;
								}
							}
						}
						holder.con.setLayoutParams(layoutParams);

						float currX = initCenter.x - layoutParams.width / 2;
						float currY = initCenter.y - layoutParams.height / 2;

//						currX = Math.max(currX, -layoutParams.width / 2);
//						currX = Math.min(currX, getWidth() - layoutParams.width / 2);
//						currY = Math.max(currY, -layoutParams.height / 2);
//						currY = Math.min(currY, getHeight() - layoutParams.height / 2);

						holder.con.setTranslationX(currX);
						holder.con.setTranslationY(currY);

//						LOG.v(String.format("ZOOM:%d,%d", layoutParams.width, layoutParams.height));

						if (enableRotate)
						{
							if (hasInit)
							{
								float currAngle = angle(event);
								float offsetAngle = currAngle - initAngle;
								offsetAngle = (float) (offsetAngle * 180 / Math.PI);
								offsetAngle *= 2; // 为丢失触摸点做个2倍修正，不然旋转会明显感觉慢
								if (offsetAngle < 0)
									offsetAngle += 360;
								if (offsetAngle >= 360)
									offsetAngle -= 360;
								float targetRotation = initRotation + offsetAngle;
								if (targetRotation < 0)
									targetRotation += 360;
								if (targetRotation >= 360)
									targetRotation -= 360;
								boolean doRotate = false;
								if (near0(initRotation))
								{
									if (!near0(targetRotation))
										doRotate = true;
								}
								else if (near90(initRotation))
								{
									if (!near90(targetRotation))
										doRotate = true;
								}
								else if (near180(initRotation))
								{
									if (!near180(targetRotation))
										doRotate = true;
								}
								else if (near270(initRotation))
								{
									if (!near270(targetRotation))
										doRotate = true;
								}
								else
								{
									if (near0(targetRotation))
									{
										targetRotation = 0;
										doRotate = true;
									}
									else if (near90(targetRotation))
									{
										targetRotation = 90;
										doRotate = true;
									}
									else if (near180(targetRotation))
									{
										targetRotation = 180;
										doRotate = true;
									}
									else if (near270(targetRotation))
									{
										targetRotation = 270;
										doRotate = true;
									}
									else
									{
										doRotate = true;
									}
								}
								if (doRotate)
								{
									LOG.v(String.format("initRotation = %f, targetRotation = %f", initRotation, targetRotation));
									holder.con.setRotation(targetRotation);
									checkShowFlagPosition();
									hasInit = false;
								}
							}
							else
							{
								// 因为旋转会带来局部坐标系变化，所以每间隔一次需要重新初始化，但这样会丢失触摸点
								initAngle = angle(event);
								initRotation = holder.con.getRotation();
								hasInit = true;
							}
						}
					}
					break;
				case MotionEvent.ACTION_POINTER_UP:
					mode = NONE;
					break;
				case MotionEvent.ACTION_UP:
//					holder.photo.setAlpha(1f);
					mode = NONE;
					if (mListener != null)
						mListener.onModify(PhotoView.this);
					break;
				}
				return true;
			}
			else
			{
				return false;
			}
		}
	};

	private boolean near0(float rotation)
	{
		return rotation < adjustOffset || rotation > 360 - adjustOffset;
	}

	private boolean near90(float rotation)
	{
		return Math.abs(rotation - 90) < adjustOffset;
	}

	private boolean near180(float rotation)
	{
		return Math.abs(rotation - 180) < adjustOffset;
	}

	private boolean near270(float rotation)
	{
		return Math.abs(rotation - 270) < adjustOffset;
	}

	private float angle(float x1, float y1, float x2, float y2)
	{
		if (x1 == x2 && y1 == y2)
			return 0;
		if (x1 < x2 && y1 == y2)
		{
//			LOG.v("右");
			return 0;
		}
		if (x1 == x2 && y1 < y2)
		{
//			LOG.v("下");
			return (float) (Math.PI / 2);
		}
		if (x1 > x2 && y1 == y2)
		{
//			LOG.v("左");
			return (float) Math.PI;
		}
		if (x1 == x2 && y1 > y2)
		{
//			LOG.v("上");
			return (float) (Math.PI * 3 / 2);
		}
		if (x1 < x2 && y1 < y2)
		{
//			LOG.v("右下");
			float offx = Math.abs(x2 - x1);
			float offy = Math.abs(y2 - y1);
			return (float) Math.atan(offy / offx);
		}
		if (x1 > x2 && y1 < y2)
		{
//			LOG.v("左下");
			float offx = Math.abs(x2 - x1);
			float offy = Math.abs(y2 - y1);
			return (float) (Math.PI - Math.atan(offy / offx));
		}
		if (x1 < x2 && y1 > y2)
		{
//			LOG.v("右上");
			float offx = Math.abs(x2 - x1);
			float offy = Math.abs(y2 - y1);
			return (float) (Math.PI * 2 - Math.atan(offy / offx));
		}
		if (x1 > x2 && y1 > y2)
		{
//			LOG.v("左上");
			float offx = Math.abs(x2 - x1);
			float offy = Math.abs(y2 - y1);
			return (float) (Math.PI + Math.atan(offy / offx));
		}
		return 0;
	}

	// 计算两个触摸点之间的角度
	private float angle(MotionEvent event)
	{
		return angle(event.getX(0), event.getY(0), event.getX(1), event.getY(1));
	}

	private float distanceX(MotionEvent event)
	{
		float x = event.getX(0) - event.getX(1);
		return Math.abs(x);
	}

	private float distanceY(MotionEvent event)
	{
		float y = event.getY(0) - event.getY(1);
		return Math.abs(y);
	}

	// 计算两个触摸点之间的距离
	private float distance(MotionEvent event)
	{
		float x = event.getX(0) - event.getX(1);
		float y = event.getY(0) - event.getY(1);
		return (float) Math.sqrt(x * x + y * y);
	}

	public int getPositionX()
	{
		return (int) holder.con.getTranslationX();
	}

	public int getPositionY()
	{
		return (int) holder.con.getTranslationY();
	}

	public void setPosition(int x, int y)
	{
		holder.con.setTranslationX(x);
		holder.con.setTranslationY(y);
	}

	public int getAngle()
	{
		return (int) holder.con.getRotation();
	}

	public void setAngle(int angle)
	{
		holder.con.setRotation(angle);
		checkShowFlagPosition();
	}

	public int getDimensionWidth()
	{
		ViewGroup.LayoutParams layoutParams = holder.con.getLayoutParams();
		return layoutParams.width;
	}

	public int getDimensionHeight()
	{
		ViewGroup.LayoutParams layoutParams = holder.con.getLayoutParams();
		return layoutParams.height;
	}

	public void setDimension(int width, int height)
	{
		ViewGroup.LayoutParams layoutParams = holder.con.getLayoutParams();
		layoutParams.width = width;
		layoutParams.height = height;
		holder.con.setLayoutParams(layoutParams);
	}

	public boolean getHide()
	{
		return photo.hide;
	}

	public void setHide(boolean hide)
	{
		photo.hide = hide;
		checkShowHide();
	}

	public void setPhotoBitmap(String cover)
	{
		if (!TextUtils.isEmpty(cover))
			ImageLoad.displayImage(getContext(), cover, holder.photo, R.drawable.img_default, null);
		else
			setPhotoBitmapNone();
	}

	public void setPhotoBitmap(Bitmap bitmap)
	{
		if (bitmap != null)
			holder.photo.setImageBitmap(bitmap);
		else
			setPhotoBitmapNone();
	}

	private void setPhotoBitmapNone()
	{
		if (photo.type == BoardView.Type_SelectionGroup)
			holder.photo.setImageBitmap(null);
		else if (photo.type == BoardView.Type_BoardText)
			holder.photo.setImageBitmap(null);
		else
			holder.photo.setImageResource(R.drawable.img_default);
	}

	public void updateSelections()
	{
		if (photo.type == BoardView.Type_SelectionGroup)
			holder.selectionGroup.updateSelections(showRightAnswer);
	}

	public void lockSelections()
	{
		if (photo.type == BoardView.Type_SelectionGroup)
			holder.selectionGroup.lockSelections();
	}

	public void unlockSelections()
	{
		if (photo.type == BoardView.Type_SelectionGroup)
			holder.selectionGroup.unlockSelections();
	}

	private OnListener mListener = null;

	public void setListener(OnListener listener)
	{
		this.mListener = listener;
	}

	public interface OnListener
	{
		void onTop(PhotoView photoView);

		void onModify(PhotoView photoView);

		void onDelete(PhotoView photoView);

		void onModifySelections(PhotoView photoView);

		void onModifyBoardText(PhotoView photoView);
	}
}
