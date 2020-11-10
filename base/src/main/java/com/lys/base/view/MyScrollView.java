package com.lys.base.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

public class MyScrollView extends ScrollView
{
	public MyScrollView(Context context)
	{
		super(context);
	}

	public MyScrollView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	@Override
	protected void onScrollChanged(int x, int y, int oldx, int oldy)
	{
		super.onScrollChanged(x, y, oldx, oldy);
		if (mScrollChangeListener != null)
			mScrollChangeListener.onScrollChange(this, x, y, oldx, oldy);
	}

	public void setOnScrollChangeListener(OnScrollChangeListener listener)
	{
		mScrollChangeListener = listener;
	}

	private OnScrollChangeListener mScrollChangeListener = null;

	public interface OnScrollChangeListener
	{
		void onScrollChange(MyScrollView view, int x, int y, int oldx, int oldy);
	}
}
