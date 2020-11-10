package com.lys.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.lys.base.utils.LOG;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("AppCompatCustomView")
public class SnowEffect extends FrameLayout
{
	private class Snow
	{
		private ImageView show;
		private float speed;
	}

	public SnowEffect(@NonNull Context context)
	{
		super(context);
	}

	private int showResWidth;
	private int showResHeight;

	private float initPosY;

	private float initPosXStart;
	private float initPosXRange;

	private float initScaleStart = 0.6f;
	private float initScaleRange = 0.9f - initScaleStart;

	private float initRotationStart = 0;
	private float initRotationRange = 360;

	private float initSpeedStart = 20;
	private float initSpeedRange = 10;

	private List<Snow> snowPool = new ArrayList<>();

	public void start(int resId, int count)
	{
		LOG.v("effect con getWidth : " + getWidth());
		LOG.v("effect con getHeight : " + getHeight());

//		setBackgroundColor(0x55ff0000);

		BitmapDrawable drawable = (BitmapDrawable) getResources().getDrawable(resId);
		showResWidth = drawable.getBitmap().getWidth();
		showResHeight = drawable.getBitmap().getHeight();

		initPosY = -showResHeight;

		initPosXStart = -showResWidth;
		initPosXRange = showResWidth + getWidth();

		float initPosYStart = -showResHeight;
		float initPosYRange = showResHeight + getHeight();

		for (int i = 0; i < count; i++)
		{
			Snow snow = genSnow();
			snow.show.setImageResource(resId);
			snowPool.add(snow);
			snow.show.setTranslationY((float) (initPosYStart + Math.random() * initPosYRange));
		}

		lastTime = System.currentTimeMillis();
		waitHandler.postDelayed(waitRunnable, 15);
	}

	@Override
	protected void onDetachedFromWindow()
	{
		super.onDetachedFromWindow();
		LOG.v("destroy snow effect");
		waitHandler.removeCallbacks(waitRunnable);
	}

	private boolean isFree(Snow snow)
	{
		float y = snow.show.getTranslationY();
		if (y > getHeight())
			return true;
		else
			return false;
	}

	private void update(long dtTime)
	{
//		LOG.v("dtTime : " + dtTime);
		for (Snow snow : snowPool)
		{
//			float x = snow.show.getTranslationX();
			float y = snow.show.getTranslationY();
			y += (snow.speed * dtTime * 0.001);
			snow.show.setTranslationY(y);
			if (isFree(snow))
			{
				resetSnow(snow);
			}
		}
	}

	private long lastTime;

	private Handler waitHandler = new Handler();
	private Runnable waitRunnable = new Runnable()
	{
		@Override
		public void run()
		{
			long currTime = System.currentTimeMillis();
			long dtTime = currTime - lastTime;
			update(dtTime);
			lastTime = currTime;
			waitHandler.postDelayed(waitRunnable, 15);
		}
	};

	private void resetSnow(Snow snow)
	{
		snow.show.setTranslationX((float) (initPosXStart + Math.random() * initPosXRange));
		snow.show.setTranslationY(initPosY);

		float scale = (float) (initScaleStart + Math.random() * initScaleRange);
		snow.show.setScaleX(scale);
		snow.show.setScaleY(scale);

		float rotation = (float) (initRotationStart + Math.random() * initRotationRange);
		snow.show.setRotation(rotation);

		snow.speed = (float) (initSpeedStart + Math.random() * initSpeedRange);
	}

	private Snow genSnow()
	{
//		LOG.v("genSnow");

		Snow snow = new Snow();

		snow.show = new ImageView(getContext());
		addView(snow.show, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

		resetSnow(snow);

		return snow;
	}

//	private List<Snow> snowUsedPool = new ArrayList<>();
//	private List<Snow> snowFreePool = new ArrayList<>();
//
//	private Snow getFreeSnow()
//	{
//		Snow snow;
//		if (snowFreePool.size() > 0)
//		{
//			snow = snowFreePool.remove(snowFreePool.size() - 1);
//		}
//		else
//		{
//			snow = genSnow();
//		}
//		snowUsedPool.add(snow);
//		return snow;
//	}

}
