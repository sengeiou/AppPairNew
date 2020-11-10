package io.rong.imkit;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import io.rong.imkit.plugin.image.HackyViewPager;

public class LysHackyViewPager extends HackyViewPager
{
	public LysHackyViewPager(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	@Override
	public void addView(View child)
	{
		super.addView(child);
		if (mAddViewListener != null)
			mAddViewListener.onAddView(child);
	}

	public void setOnAddViewListener(OnAddViewListener listener)
	{
		mAddViewListener = listener;
	}

	private OnAddViewListener mAddViewListener = null;

	public interface OnAddViewListener
	{
		void onAddView(View child);
	}
}
