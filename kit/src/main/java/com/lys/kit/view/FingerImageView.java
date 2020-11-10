package com.lys.kit.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

import com.lys.board.utils.LysBoardUtils;

@SuppressLint("AppCompatCustomView")
public class FingerImageView extends ImageView
{
	public FingerImageView(Context context, @Nullable AttributeSet attrs)
	{
		super(context, attrs);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		if (LysBoardUtils.isPen(event))
			return false;
		else
			return super.onTouchEvent(event);
	}

}
