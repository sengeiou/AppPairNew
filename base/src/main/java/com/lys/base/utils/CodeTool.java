package com.lys.base.utils;

import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;

public class CodeTool
{
	public interface SetHeightCallback
	{
		void over();
	}

	public static void setViewHeight(View setView, View listenerView, int height, final SetHeightCallback callback)
	{
		if (setView.getHeight() == height)
		{
			if (callback != null)
				callback.over();
		}
		else
		{
			listenerView.addOnLayoutChangeListener(new View.OnLayoutChangeListener()
			{
				@Override
				public void onLayoutChange(View view, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom)
				{
					view.removeOnLayoutChangeListener(this);
					// 做个小异步，避免嵌套调用时出问题
					new Handler().post(new Runnable()
					{
						@Override
						public void run()
						{
							if (callback != null)
								callback.over();
						}
					});
				}
			});
			ViewGroup.LayoutParams layoutParams = setView.getLayoutParams();
			layoutParams.height = height;
			setView.setLayoutParams(layoutParams);
		}
	}

	public static void setViewHeight(View view, int height, SetHeightCallback callback)
	{
		setViewHeight(view, view, height, callback);
	}
}
