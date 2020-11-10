package com.lys.kit.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PointF;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.lys.base.utils.CommonUtils;
import com.lys.base.utils.FsUtils;
import com.lys.base.utils.LOG;
import com.lys.base.utils.SysUtils;
import com.lys.kit.R;
import com.lys.kit.utils.KitUtils;

import java.io.File;

public class ActivityCrop extends KitActivity implements View.OnClickListener
{
	private class Holder
	{
		private ImageView image;
		private View maskLeft;
		private View maskTop;
		private View maskWidth;
		private View maskHeight;
		private View mask;

		private View leftTop;
		private View rightTop;
		private View leftBottom;
		private View rightBottom;
		private View left;
		private View top;
		private View right;
		private View bottom;
	}

	private Holder holder = new Holder();

	private void initHolder()
	{
		holder.image = findViewById(R.id.image);
		holder.maskLeft = findViewById(R.id.maskLeft);
		holder.maskTop = findViewById(R.id.maskTop);
		holder.maskWidth = findViewById(R.id.maskWidth);
		holder.maskHeight = findViewById(R.id.maskHeight);
		holder.mask = findViewById(R.id.mask);

		holder.leftTop = findViewById(R.id.leftTop);
		holder.rightTop = findViewById(R.id.rightTop);
		holder.leftBottom = findViewById(R.id.leftBottom);
		holder.rightBottom = findViewById(R.id.rightBottom);
		holder.left = findViewById(R.id.left);
		holder.top = findViewById(R.id.top);
		holder.right = findViewById(R.id.right);
		holder.bottom = findViewById(R.id.bottom);
	}

	private String filepath;
	private int pointWidth, pointHeight;
	private int spaceLeft, spaceTop, spaceRight, spaceBottom;
	private int showLeft, showTop, showRight, showBottom;
	private int cutLeft, cutTop, cutRight, cutBottom;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_crop);

		initHolder();

		filepath = getIntent().getStringExtra("path");

		findViewById(R.id.cancel).setOnClickListener(this);
		findViewById(R.id.ok).setOnClickListener(this);

		holder.leftTop.setOnTouchListener(touchPoint);
		holder.rightTop.setOnTouchListener(touchPoint);
		holder.leftBottom.setOnTouchListener(touchPoint);
		holder.rightBottom.setOnTouchListener(touchPoint);
		holder.left.setOnTouchListener(touchPoint);
		holder.top.setOnTouchListener(touchPoint);
		holder.right.setOnTouchListener(touchPoint);
		holder.bottom.setOnTouchListener(touchPoint);

		holder.mask.setOnTouchListener(touchMask);

		holder.leftTop.addOnLayoutChangeListener(new View.OnLayoutChangeListener()
		{
			@Override
			public void onLayoutChange(View view, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom)
			{
				view.removeOnLayoutChangeListener(this);
				pointWidth = holder.leftTop.getWidth() / 2;
				pointHeight = holder.leftTop.getHeight() / 2;
				initialize();
			}
		});
	}

	private int conWidth()
	{
		return SysUtils.screenWidth(context) - spaceLeft - spaceRight;
	}

	private int conHeight()
	{
		return SysUtils.screenHeight(context) - spaceTop - spaceBottom;
	}

	private void initialize()
	{
		// 加30的偏移是为了解决控制点跟菜单重合的问题
		spaceLeft = pointWidth / 2 + 30;
		if (KitUtils.isD7() || KitUtils.isG6())
			spaceTop = pointHeight / 2 + 30;
		else
			spaceTop = pointHeight / 2 + SysUtils.getStatusHeight(context) + 30;
		spaceRight = pointWidth / 2 + 30;
		spaceBottom = pointHeight / 2;

		RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) holder.image.getLayoutParams();
		layoutParams.leftMargin = spaceLeft;
		layoutParams.topMargin = spaceTop;
		layoutParams.rightMargin = spaceRight;
		layoutParams.bottomMargin = spaceBottom;
		holder.image.setLayoutParams(layoutParams);

		Bitmap bitmap = CommonUtils.readBitmap(filepath, conWidth());
		LOG.v(String.format("getWidth=%d, getHeight=%d", bitmap.getWidth(), bitmap.getHeight()));
		holder.image.setImageBitmap(bitmap);

		int ratioValue = conWidth() * bitmap.getHeight() - conHeight() * bitmap.getWidth();
		if (ratioValue > 0) // 左右留边
		{
			int showWidth = bitmap.getWidth() * conHeight() / bitmap.getHeight();
			int offset = (conWidth() - showWidth) / 2;
			showLeft = offset;
			showTop = 0;
			showRight = conWidth() - offset;
			showBottom = conHeight();
		}
		else if (ratioValue < 0) // 上下留边
		{
			int showHeight = bitmap.getHeight() * conWidth() / bitmap.getWidth();
			int offset = (conHeight() - showHeight) / 2;
			showLeft = 0;
			showTop = offset;
			showRight = conWidth();
			showBottom = conHeight() - offset;
		}
		else // 宽高比相同
		{
			showLeft = 0;
			showTop = 0;
			showRight = conWidth();
			showBottom = conHeight();
		}

		showLeft += spaceLeft;
		showTop += spaceTop;
		showRight += spaceLeft;
		showBottom += spaceTop;

		cutLeft = showLeft;
		cutTop = showTop;
		cutRight = showRight;
		cutBottom = showBottom;

		updatePoints();
		updateMask();
	}

	private void updatePoints()
	{
		{
			FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) holder.leftTop.getLayoutParams();
			layoutParams.leftMargin = cutLeft - pointWidth / 2;
			layoutParams.topMargin = cutTop - pointHeight / 2;
			layoutParams.width = pointWidth;
			layoutParams.height = pointHeight;
			holder.leftTop.setLayoutParams(layoutParams);
		}
		{
			FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) holder.rightTop.getLayoutParams();
			layoutParams.leftMargin = cutRight - pointWidth / 2;
			layoutParams.topMargin = cutTop - pointHeight / 2;
			layoutParams.width = pointWidth;
			layoutParams.height = pointHeight;
			holder.rightTop.setLayoutParams(layoutParams);
		}
		{
			FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) holder.leftBottom.getLayoutParams();
			layoutParams.leftMargin = cutLeft - pointWidth / 2;
			layoutParams.topMargin = cutBottom - pointHeight / 2;
			layoutParams.width = pointWidth;
			layoutParams.height = pointHeight;
			holder.leftBottom.setLayoutParams(layoutParams);
		}
		{
			FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) holder.rightBottom.getLayoutParams();
			layoutParams.leftMargin = cutRight - pointWidth / 2;
			layoutParams.topMargin = cutBottom - pointHeight / 2;
			layoutParams.width = pointWidth;
			layoutParams.height = pointHeight;
			holder.rightBottom.setLayoutParams(layoutParams);
		}
		{
			FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) holder.left.getLayoutParams();
			layoutParams.leftMargin = cutLeft - pointWidth / 2;
			layoutParams.topMargin = (cutTop + cutBottom) / 2 - pointHeight / 2;
			layoutParams.width = pointWidth;
			layoutParams.height = pointHeight;
			holder.left.setLayoutParams(layoutParams);
		}
		{
			FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) holder.top.getLayoutParams();
			layoutParams.leftMargin = (cutLeft + cutRight) / 2 - pointWidth / 2;
			layoutParams.topMargin = cutTop - pointHeight / 2;
			layoutParams.width = pointWidth;
			layoutParams.height = pointHeight;
			holder.top.setLayoutParams(layoutParams);
		}
		{
			FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) holder.right.getLayoutParams();
			layoutParams.leftMargin = cutRight - pointWidth / 2;
			layoutParams.topMargin = (cutTop + cutBottom) / 2 - pointHeight / 2;
			layoutParams.width = pointWidth;
			layoutParams.height = pointHeight;
			holder.right.setLayoutParams(layoutParams);
		}
		{
			FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) holder.bottom.getLayoutParams();
			layoutParams.leftMargin = (cutLeft + cutRight) / 2 - pointWidth / 2;
			layoutParams.topMargin = cutBottom - pointHeight / 2;
			layoutParams.width = pointWidth;
			layoutParams.height = pointHeight;
			holder.bottom.setLayoutParams(layoutParams);
		}
	}

	private void updateMask()
	{
		{
			ViewGroup.LayoutParams layoutParams = holder.maskLeft.getLayoutParams();
			layoutParams.width = cutLeft;
			holder.maskLeft.setLayoutParams(layoutParams);
		}
		{
			ViewGroup.LayoutParams layoutParams = holder.maskTop.getLayoutParams();
			layoutParams.height = cutTop;
			holder.maskTop.setLayoutParams(layoutParams);
		}
		{
			ViewGroup.LayoutParams layoutParams = holder.maskWidth.getLayoutParams();
			layoutParams.width = cutRight - cutLeft;
			holder.maskWidth.setLayoutParams(layoutParams);
		}
		{
			ViewGroup.LayoutParams layoutParams = holder.maskHeight.getLayoutParams();
			layoutParams.height = cutBottom - cutTop;
			holder.maskHeight.setLayoutParams(layoutParams);
		}
		{
			RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) holder.mask.getLayoutParams();
			layoutParams.leftMargin = cutLeft;
			layoutParams.topMargin = cutTop;
			layoutParams.width = cutRight - cutLeft;
			layoutParams.height = cutBottom - cutTop;
			holder.mask.setLayoutParams(layoutParams);
		}
	}

	private View.OnTouchListener touchPoint = new View.OnTouchListener()
	{
		private PointF initPoint = new PointF();
		private PointF initPosition = new PointF();

		@Override
		public boolean onTouch(View view, MotionEvent event)
		{
			switch (event.getAction())
			{
			case MotionEvent.ACTION_DOWN:
			{
				initPoint.set(event.getRawX(), event.getRawY());
				FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) view.getLayoutParams();
				initPosition.set(layoutParams.leftMargin, layoutParams.topMargin);
			}
			break;
			case MotionEvent.ACTION_MOVE:
			{
				float offsetX = event.getRawX() - initPoint.x;
				float offsetY = event.getRawY() - initPoint.y;

				int currX = (int) (initPosition.x + offsetX);
				int currY = (int) (initPosition.y + offsetY);

				if (view == holder.leftTop)
				{
					currX = Math.max(currX, showLeft - pointWidth / 2);
					currX = Math.min(currX, cutRight - 2 * pointWidth - pointWidth / 2);
					currY = Math.max(currY, showTop - pointHeight / 2);
					currY = Math.min(currY, cutBottom - 2 * pointHeight - pointHeight / 2);
					cutLeft = currX + pointWidth / 2;
					cutTop = currY + pointHeight / 2;
				}
				else if (view == holder.rightTop)
				{
					currX = Math.max(currX, cutLeft + 2 * pointWidth - pointWidth / 2);
					currX = Math.min(currX, showRight - pointWidth / 2);
					currY = Math.max(currY, showTop - pointHeight / 2);
					currY = Math.min(currY, cutBottom - 2 * pointHeight - pointHeight / 2);
					cutRight = currX + pointWidth / 2;
					cutTop = currY + pointHeight / 2;
				}
				else if (view == holder.leftBottom)
				{
					currX = Math.max(currX, showLeft - pointWidth / 2);
					currX = Math.min(currX, cutRight - 2 * pointWidth - pointWidth / 2);
					currY = Math.max(currY, cutTop + 2 * pointHeight - pointHeight / 2);
					currY = Math.min(currY, showBottom - pointHeight / 2);
					cutLeft = currX + pointWidth / 2;
					cutBottom = currY + pointHeight / 2;
				}
				else if (view == holder.rightBottom)
				{
					currX = Math.max(currX, cutLeft + 2 * pointWidth - pointWidth / 2);
					currX = Math.min(currX, showRight - pointWidth / 2);
					currY = Math.max(currY, cutTop + 2 * pointHeight - pointHeight / 2);
					currY = Math.min(currY, showBottom - pointHeight / 2);
					cutRight = currX + pointWidth / 2;
					cutBottom = currY + pointHeight / 2;
				}
				else if (view == holder.left)
				{
					currX = Math.max(currX, showLeft - pointWidth / 2);
					currX = Math.min(currX, cutRight - 2 * pointWidth - pointWidth / 2);
					currY = (int) initPosition.y;
					cutLeft = currX + pointWidth / 2;
				}
				else if (view == holder.top)
				{
					currX = (int) initPosition.x;
					currY = Math.max(currY, showTop - pointHeight / 2);
					currY = Math.min(currY, cutBottom - 2 * pointHeight - pointHeight / 2);
					cutTop = currY + pointHeight / 2;
				}
				else if (view == holder.right)
				{
					currX = Math.max(currX, cutLeft + 2 * pointWidth - pointWidth / 2);
					currX = Math.min(currX, showRight - pointWidth / 2);
					currY = (int) initPosition.y;
					cutRight = currX + pointWidth / 2;
				}
				else if (view == holder.bottom)
				{
					currX = (int) initPosition.x;
					currY = Math.max(currY, cutTop + 2 * pointHeight - pointHeight / 2);
					currY = Math.min(currY, showBottom - pointHeight / 2);
					cutBottom = currY + pointHeight / 2;
				}

				updatePoints();
				updateMask();
			}
			break;
			case MotionEvent.ACTION_UP:
				break;
			}
			return true;
		}
	};

	private View.OnTouchListener touchMask = new View.OnTouchListener()
	{
		private PointF initPoint = new PointF();
		private PointF initPosition = new PointF();
		private int initWidth;
		private int initHeight;

		@Override
		public boolean onTouch(View view, MotionEvent event)
		{
			switch (event.getAction())
			{
			case MotionEvent.ACTION_DOWN:
			{
				initPoint.set(event.getRawX(), event.getRawY());
				RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
				initPosition.set(layoutParams.leftMargin, layoutParams.topMargin);
				initWidth = cutRight - cutLeft;
				initHeight = cutBottom - cutTop;
			}
			break;
			case MotionEvent.ACTION_MOVE:
			{
				float offsetX = event.getRawX() - initPoint.x;
				float offsetY = event.getRawY() - initPoint.y;

				int currX = (int) (initPosition.x + offsetX);
				int currY = (int) (initPosition.y + offsetY);

				currX = Math.max(currX, showLeft);
				currX = Math.min(currX, showRight - initWidth);
				currY = Math.max(currY, showTop);
				currY = Math.min(currY, showBottom - initHeight);

				cutLeft = currX;
				cutTop = currY;
				cutRight = currX + initWidth;
				cutBottom = currY + initHeight;

				updatePoints();
				updateMask();
			}
			break;
			case MotionEvent.ACTION_UP:
				break;
			}
			return true;
		}
	};

	@Override
	public void onClick(View view)
	{
		if (view.getId() == R.id.cancel)
		{
			finish();
		}
		else if (view.getId() == R.id.ok)
		{
			try
			{
				String outpath = FsUtils.SD_CARD + "/crop.png";

				Bitmap bitmap = BitmapFactory.decodeFile(filepath);
				int left = (cutLeft - showLeft) * bitmap.getWidth() / (showRight - showLeft);
				int top = (cutTop - showTop) * bitmap.getHeight() / (showBottom - showTop);
				int width = (cutRight - cutLeft) * bitmap.getWidth() / (showRight - showLeft);
				int height = (cutBottom - cutTop) * bitmap.getHeight() / (showBottom - showTop);
				Bitmap cutBitmap = Bitmap.createBitmap(bitmap, left, top, width, height);
				CommonUtils.saveBitmap(cutBitmap, Bitmap.CompressFormat.JPEG, new File(outpath));
				cutBitmap.recycle();
				bitmap.recycle();

				Intent intent = new Intent();
				intent.putExtra("path", outpath);
				setResult(Activity.RESULT_OK, intent);
				finish();
			}
			catch (OutOfMemoryError e)
			{
				LOG.toast(context, "内存不足");
				finish();
			}
		}
	}
}
