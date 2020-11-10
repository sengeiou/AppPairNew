package com.lys.base.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class MyViewPager extends ViewPager
{
	public MyViewPager(Context context)
	{
		super(context);
	}

	public MyViewPager(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	private boolean isLock = false;

	public void setLock(boolean lock)
	{
		this.isLock = lock;
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev)
	{
		if (isLock)
			return false;
		else
			return super.onInterceptTouchEvent(ev);
	}
}
