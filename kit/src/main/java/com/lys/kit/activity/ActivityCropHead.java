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
import android.widget.ImageView;

import com.lys.base.utils.CommonUtils;
import com.lys.base.utils.FsUtils;
import com.lys.base.utils.LOG;
import com.lys.base.utils.SysUtils;
import com.lys.kit.R;

import java.io.File;

public class ActivityCropHead extends KitActivity implements View.OnClickListener
{
	private class Holder
	{
		private ImageView image;
		private View maskLeft;
		private View maskTop;
		private View maskWidth;
		private View maskHeight;
	}

	private Holder holder = new Holder();

	private void initHolder()
	{
		holder.image = findViewById(R.id.image);
		holder.maskLeft = findViewById(R.id.maskLeft);
		holder.maskTop = findViewById(R.id.maskTop);
		holder.maskWidth = findViewById(R.id.maskWidth);
		holder.maskHeight = findViewById(R.id.maskHeight);
	}

	private String filepath;
	private Bitmap bitmap;
	private int side;
	private int cutLeft, cutTop, cutRight, cutBottom;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_crop_head);

		initHolder();

		filepath = getIntent().getStringExtra("path");

		findViewById(R.id.cancel).setOnClickListener(this);
		findViewById(R.id.ok).setOnClickListener(this);

		findViewById(R.id.imageCon).setOnTouchListener(touchImage);

		bitmap = BitmapFactory.decodeFile(filepath);
		LOG.v(String.format("getWidth=%d, getHeight=%d", bitmap.getWidth(), bitmap.getHeight()));
		if (bitmap.getWidth() > SysUtils.screenWidth(context))
			holder.image.setImageBitmap(CommonUtils.scaleBitmap(bitmap, SysUtils.screenWidth(context)));
		else
			holder.image.setImageBitmap(bitmap);

		side = Math.min(conWidth(), conHeight()) * 6 / 8;

		cutLeft = (conWidth() - side) / 2;
		cutTop = (conHeight() - side) / 2;
		cutRight = cutLeft + side;
		cutBottom = cutTop + side;

		initMask();

		int width, height;

		int b = Math.min(bitmap.getWidth(), bitmap.getHeight());
		if (b < side)
		{
			if (bitmap.getWidth() < bitmap.getHeight())
			{
				width = side;
				height = width * bitmap.getHeight() / bitmap.getWidth();
			}
			else
			{
				height = side;
				width = height * bitmap.getWidth() / bitmap.getHeight();
			}
		}
		else
		{
			width = bitmap.getWidth();
			height = bitmap.getHeight();
		}

		ViewGroup.LayoutParams layoutParams = holder.image.getLayoutParams();
		layoutParams.width = width;
		layoutParams.height = height;
		holder.image.setLayoutParams(layoutParams);

		holder.image.setTranslationX((conWidth() - width) / 2);
		holder.image.setTranslationY((conHeight() - height) / 2);
	}

	private int conWidth()
	{
		return SysUtils.screenWidth(context);
	}

	private int conHeight()
	{
		return SysUtils.screenHeight(context);
	}

	private void initMask()
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
	}

	private View.OnTouchListener touchImage = new View.OnTouchListener()
	{
		private static final int NONE = 0;
		private static final int DRAG = 1;
		private static final int ZOOM = 2;

		private int mode = NONE;

		private PointF initPoint = new PointF();
		private PointF initPosition = new PointF();

		private float initDistance;
		private float initWidth;
		private float initHeight;
		private PointF initCenter = new PointF();

		@Override
		public boolean onTouch(View view, MotionEvent event)
		{
			switch (event.getAction() & MotionEvent.ACTION_MASK)
			{
			case MotionEvent.ACTION_DOWN:
				initPoint.set(event.getRawX(), event.getRawY());
				initPosition.set(holder.image.getTranslationX(), holder.image.getTranslationY());
				mode = DRAG;
				break;
			case MotionEvent.ACTION_POINTER_DOWN:
				initDistance = distance(event);
				initWidth = holder.image.getWidth();
				initHeight = holder.image.getHeight();
				initCenter.set(holder.image.getTranslationX() + holder.image.getWidth() / 2, holder.image.getTranslationY() + holder.image.getHeight() / 2);
				mode = ZOOM;
				break;
			case MotionEvent.ACTION_MOVE:
				if (mode == DRAG)
				{
					float offsetX = event.getRawX() - initPoint.x;
					float offsetY = event.getRawY() - initPoint.y;

					float currX = initPosition.x + offsetX;
					float currY = initPosition.y + offsetY;

					currX = Math.max(currX, cutRight - holder.image.getWidth());
					currX = Math.min(currX, cutLeft);
					currY = Math.max(currY, cutBottom - holder.image.getHeight());
					currY = Math.min(currY, cutTop);

					holder.image.setTranslationX(currX);
					holder.image.setTranslationY(currY);
				}
				else if (mode == ZOOM)
				{
					float offsetScale = distance(event) / initDistance;

					ViewGroup.LayoutParams layoutParams = holder.image.getLayoutParams();
					if (bitmap.getWidth() < bitmap.getHeight())
					{
						int width = (int) (initWidth * offsetScale);

						// 不能调换顺序，用于处理bitmap.getWidth()<side的情况
						width = Math.min(width, bitmap.getWidth());
						width = Math.max(width, side);

						layoutParams.width = width;
						layoutParams.height = bitmap.getHeight() * layoutParams.width / bitmap.getWidth();
					}
					else
					{
						int height = (int) (initHeight * offsetScale);

						// 不能调换顺序，用于处理bitmap.getHeight()<side的情况
						height = Math.min(height, bitmap.getHeight());
						height = Math.max(height, side);

						layoutParams.height = height;
						layoutParams.width = bitmap.getWidth() * layoutParams.height / bitmap.getHeight();
					}
					holder.image.setLayoutParams(layoutParams);

					float currX = initCenter.x - layoutParams.width / 2;
					float currY = initCenter.y - layoutParams.height / 2;

					currX = Math.max(currX, cutRight - layoutParams.width);
					currX = Math.min(currX, cutLeft);
					currY = Math.max(currY, cutBottom - layoutParams.height);
					currY = Math.min(currY, cutTop);

					holder.image.setTranslationX(currX);
					holder.image.setTranslationY(currY);
				}
				break;
			case MotionEvent.ACTION_POINTER_UP:
				mode = NONE;
				break;
			case MotionEvent.ACTION_UP:
				mode = NONE;
				break;
			}
			return true;
		}
	};

	// 计算两个触摸点之间的距离
	private float distance(MotionEvent event)
	{
		float x = event.getX(0) - event.getX(1);
		float y = event.getY(0) - event.getY(1);
		return (float) Math.sqrt(x * x + y * y);
	}

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
				String outpath = FsUtils.SD_CARD + "/crop.tmp";

				int left = (cutLeft - (int) holder.image.getTranslationX()) * bitmap.getWidth() / holder.image.getWidth();
				int top = (cutTop - (int) holder.image.getTranslationY()) * bitmap.getHeight() / holder.image.getHeight();
				int width = side * bitmap.getWidth() / holder.image.getWidth();
				int height = side * bitmap.getHeight() / holder.image.getHeight();
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
