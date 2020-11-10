package com.lys.base.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

public class ClickLinearLayout extends LinearLayout
{
	public ClickLinearLayout(Context context)
	{
		super(context);
	}

	public ClickLinearLayout(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		if (isClickable() || isLongClickable())
		{
			switch (event.getAction())
			{
			case MotionEvent.ACTION_DOWN:
			case MotionEvent.ACTION_MOVE:
				setAlpha(0.9f);
				break;
			case MotionEvent.ACTION_UP:
			case MotionEvent.ACTION_CANCEL:
				setAlpha(1f);
				break;
			}
			return super.onTouchEvent(event);
		}
		else
		{
			setAlpha(1f);
			return false;
		}
	}
}
