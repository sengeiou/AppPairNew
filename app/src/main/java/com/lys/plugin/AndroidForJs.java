package com.lys.plugin;

import android.webkit.JavascriptInterface;

import com.lys.activity.ActivityZhiXue;

public class AndroidForJs
{
	private ActivityZhiXue mBrowserActivity;

	public AndroidForJs(ActivityZhiXue browserActivity)
	{
		this.mBrowserActivity = browserActivity;
	}

	@JavascriptInterface
	public String getNative()
	{
		return "nnd_native";
	}

	@JavascriptInterface
	public String log(String info)
	{
		return mBrowserActivity.js_log("js_log : " + info);
	}

	@JavascriptInterface
	public String toast(String info)
	{
		return mBrowserActivity.js_toast(info);
	}

	@JavascriptInterface
	public String init()
	{
		return mBrowserActivity.js_init();
	}

	@JavascriptInterface
	public String over()
	{
		return mBrowserActivity.js_over();
	}

	@JavascriptInterface
	public String call()
	{
		return mBrowserActivity.call();
	}

	@JavascriptInterface
	public String callStr(String str)
	{
		return mBrowserActivity.callStr(str);
	}

	@JavascriptInterface
	public String callCmd(String cmd)
	{
		return mBrowserActivity.callCmd(cmd);
	}

	@JavascriptInterface
	public String callCmd1(String cmd, String p1)
	{
		return mBrowserActivity.callCmd1(cmd, p1);
	}

	@JavascriptInterface
	public String callCmd2(String cmd, String p1, String p2)
	{
		return mBrowserActivity.callCmd2(cmd, p1, p2);
	}

	@JavascriptInterface
	public String callCmd3(String cmd, String p1, String p2, String p3)
	{
		return mBrowserActivity.callCmd3(cmd, p1, p2, p3);
	}

	@JavascriptInterface
	public String callCmd4(String cmd, String p1, String p2, String p3, String p4)
	{
		return mBrowserActivity.callCmd4(cmd, p1, p2, p3, p4);
	}

	@JavascriptInterface
	public String callCmd5(String cmd, String p1, String p2, String p3, String p4, String p5)
	{
		return mBrowserActivity.callCmd5(cmd, p1, p2, p3, p4, p5);
	}

	@JavascriptInterface
	public String callCmd6(String cmd, String p1, String p2, String p3, String p4, String p5, String p6)
	{
		return mBrowserActivity.callCmd6(cmd, p1, p2, p3, p4, p5, p6);
	}

	@JavascriptInterface
	public String callCmd7(String cmd, String p1, String p2, String p3, String p4, String p5, String p6, String p7)
	{
		return mBrowserActivity.callCmd7(cmd, p1, p2, p3, p4, p5, p6, p7);
	}

	@JavascriptInterface
	public String callCmd8(String cmd, String p1, String p2, String p3, String p4, String p5, String p6, String p7, String p8)
	{
		return mBrowserActivity.callCmd8(cmd, p1, p2, p3, p4, p5, p6, p7, p8);
	}

	@JavascriptInterface
	public String callCmd9(String cmd, String p1, String p2, String p3, String p4, String p5, String p6, String p7, String p8, String p9)
	{
		return mBrowserActivity.callCmd9(cmd, p1, p2, p3, p4, p5, p6, p7, p8, p9);
	}
}
