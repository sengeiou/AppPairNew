package com.lys.plugin;

import android.text.TextUtils;
import android.util.Base64;

import com.lys.activity.ActivityZhiXue;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;

public class WebMsgProcess
{
	private ActivityZhiXue mBrowserActivity;

	public WebMsgProcess(ActivityZhiXue browserActivity)
	{
		this.mBrowserActivity = browserActivity;
	}

	private ArrayList<String> parseParams(String params)
	{
		ArrayList<String> paramList = new ArrayList<String>();

		String[] paramsArray = params.split("&");
		for (int i = 0; i < paramsArray.length; i++)
		{
			String param = paramsArray[i];
			if (!TextUtils.isEmpty(param))
			{
				String[] paramArray = param.split("=");
				String key = paramArray[0];
				String value = paramArray[1];
				paramList.add(value);
			}
		}

		return paramList;
	}

	private ArrayList<String> parseUrl(String url)
	{
		int index = url.indexOf('?');
		if (index >= 0)
		{
			String params = url.substring(index + 1);
			return parseParams(params);
		}
		return null;
	}

	public String urlDecode(String str)
	{
		try
		{
			return URLDecoder.decode(str, "UTF-8");
		}
		catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		return "";
	}

	public static String Base64Encode(String str)
	{
		return Base64.encodeToString(str.getBytes(), Base64.DEFAULT);
	}

	public static String Base64Decode(String str)
	{
		return new String(Base64.decode(str, Base64.DEFAULT));
	}

	public String EncodeStr(String str)
	{
		return Base64Encode(str).replaceAll("=", "#").replaceAll("\\+", "!");
	}

	public String DecodeStr(String str)
	{
		return Base64Decode(str.replaceAll("#", "=").replaceAll("!", "+"));
	}

	public void process(String url)
	{
		ArrayList<String> paramList = parseUrl(url);
		if (paramList != null && paramList.size() > 0)
		{
			String ret = "ok";

			String func = paramList.get(0);
			if (func.equals("log"))
			{
				ret = mBrowserActivity.js_log("msg_log : " + DecodeStr(urlDecode(paramList.get(1))));
			}
			else if (func.equals("toast"))
			{
				ret = mBrowserActivity.js_toast(DecodeStr(urlDecode(paramList.get(1))));
			}
			else if (func.equals("init"))
			{
				ret = mBrowserActivity.js_init();
			}
			else if (func.equals("over"))
			{
				ret = mBrowserActivity.js_over();
			}
			else if (func.equals("call"))
			{
				ret = mBrowserActivity.call();
			}
			else if (func.equals("callStr"))
			{
				ret = mBrowserActivity.callStr(DecodeStr(urlDecode(paramList.get(1))));
			}
			else if (func.equals("callCmd"))
			{
				ret = mBrowserActivity.callCmd(DecodeStr(urlDecode(paramList.get(1))));
			}
			else if (func.equals("callCmd1"))
			{
				ret = mBrowserActivity.callCmd1(DecodeStr(urlDecode(paramList.get(1))), DecodeStr(urlDecode(paramList.get(2))));
			}
			else if (func.equals("callCmd2"))
			{
				ret = mBrowserActivity.callCmd2(DecodeStr(urlDecode(paramList.get(1))), DecodeStr(urlDecode(paramList.get(2))), DecodeStr(urlDecode(paramList.get(3))));
			}
			else if (func.equals("callCmd3"))
			{
				ret = mBrowserActivity.callCmd3(DecodeStr(urlDecode(paramList.get(1))), DecodeStr(urlDecode(paramList.get(2))), DecodeStr(urlDecode(paramList.get(3))), DecodeStr(urlDecode(paramList.get(4))));
			}
			else if (func.equals("callCmd4"))
			{
				ret = mBrowserActivity.callCmd4(DecodeStr(urlDecode(paramList.get(1))), DecodeStr(urlDecode(paramList.get(2))), DecodeStr(urlDecode(paramList.get(3))), DecodeStr(urlDecode(paramList.get(4))), DecodeStr(urlDecode(paramList.get(5))));
			}
			else if (func.equals("callCmd5"))
			{
				ret = mBrowserActivity.callCmd5(DecodeStr(urlDecode(paramList.get(1))), DecodeStr(urlDecode(paramList.get(2))), DecodeStr(urlDecode(paramList.get(3))), DecodeStr(urlDecode(paramList.get(4))), DecodeStr(urlDecode(paramList.get(5))), DecodeStr(urlDecode(paramList.get(6))));
			}
			else if (func.equals("callCmd6"))
			{
				ret = mBrowserActivity.callCmd6(DecodeStr(urlDecode(paramList.get(1))), DecodeStr(urlDecode(paramList.get(2))), DecodeStr(urlDecode(paramList.get(3))), DecodeStr(urlDecode(paramList.get(4))), DecodeStr(urlDecode(paramList.get(5))), DecodeStr(urlDecode(paramList.get(6))), DecodeStr(urlDecode(paramList.get(7))));
			}
			else if (func.equals("callCmd7"))
			{
				ret = mBrowserActivity.callCmd7(DecodeStr(urlDecode(paramList.get(1))), DecodeStr(urlDecode(paramList.get(2))), DecodeStr(urlDecode(paramList.get(3))), DecodeStr(urlDecode(paramList.get(4))), DecodeStr(urlDecode(paramList.get(5))), DecodeStr(urlDecode(paramList.get(6))), DecodeStr(urlDecode(paramList.get(7))), DecodeStr(urlDecode(paramList.get(8))));
			}
			else if (func.equals("callCmd8"))
			{
				ret = mBrowserActivity.callCmd8(DecodeStr(urlDecode(paramList.get(1))), DecodeStr(urlDecode(paramList.get(2))), DecodeStr(urlDecode(paramList.get(3))), DecodeStr(urlDecode(paramList.get(4))), DecodeStr(urlDecode(paramList.get(5))), DecodeStr(urlDecode(paramList.get(6))), DecodeStr(urlDecode(paramList.get(7))), DecodeStr(urlDecode(paramList.get(8))), DecodeStr(urlDecode(paramList.get(9))));
			}
			else if (func.equals("callCmd9"))
			{
				ret = mBrowserActivity.callCmd9(DecodeStr(urlDecode(paramList.get(1))), DecodeStr(urlDecode(paramList.get(2))), DecodeStr(urlDecode(paramList.get(3))), DecodeStr(urlDecode(paramList.get(4))), DecodeStr(urlDecode(paramList.get(5))), DecodeStr(urlDecode(paramList.get(6))), DecodeStr(urlDecode(paramList.get(7))), DecodeStr(urlDecode(paramList.get(8))), DecodeStr(urlDecode(paramList.get(9))), DecodeStr(urlDecode(paramList.get(10))));
			}

			mBrowserActivity.loadJs(String.format("MyNnd_MsgQueue.feedback(\"%s\");", ret.replace("\"", "\\\"")));
		}
	}
}
