package com.lys.view;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

import com.lys.base.utils.LOG;

public class ZhiXueWebView extends WebView
{
	public ZhiXueWebView(Context context)
	{
		super(context);
	}

	public ZhiXueWebView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	public void scrollToBottom()
	{
		LOG.v("getHeight = " + getHeight() + ", getScrollY = " + getScrollY() + ", computeVerticalScrollRange = " + computeVerticalScrollRange());
		int y = computeVerticalScrollRange() - getHeight();
		LOG.v("scrollToBottom : " + y);
		scrollTo(0, y);
	}
}
