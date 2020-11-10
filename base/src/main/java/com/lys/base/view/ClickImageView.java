package com.lys.base.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

@SuppressLint("AppCompatCustomView")
public class ClickImageView extends ImageView
{
	public ClickImageView(Context context, @Nullable AttributeSet attrs)
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
