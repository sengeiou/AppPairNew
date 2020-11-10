package com.lys.kit.utils;

import android.content.Context;
import android.os.Handler;

import com.lys.base.utils.LOG;
import com.lys.kit.dialog.DialogAlert;
import com.lys.kit.dialog.DialogWait;

public class CodeHelper
{
	public interface OnRetryListener
	{
		OnRetryListener retryListener();

		void start();

		void success();

		void fail(int code, String message);
	}

	public static void testRetry(final Context context)
	{
		new CodeHelper.OnRetryListener()
		{
			@Override
			public CodeHelper.OnRetryListener retryListener()
			{
				return this;
			}

			@Override
			public void start()
			{
				DialogWait.show(context, "加载画布");
				new Handler().postDelayed(new Runnable()
				{
					@Override
					public void run()
					{
						testRetryLoad(context, retryListener());
					}
				}, 1000);
			}

			@Override
			public void success()
			{
				DialogWait.close();
				LOG.toast(context, "加载画布成功");
			}

			@Override
			public void fail(int code, String message)
			{
				DialogWait.close();
				LOG.toast(context, message);

				DialogAlert.show(context, message + "，是否重试？", null, new DialogAlert.OnClickListener()
				{
					@Override
					public void onClick(int which)
					{
						if (which == 0)
						{
//							setResult(RESULT_OK, getIntent());
//							ActivityEdit.super.finish();
						}
						else if (which == 1)
						{
							start();
						}
					}
				}, "退出", "重试");
			}
		}.start();
	}

	private static void testRetryLoad(final Context context, final OnRetryListener retryListener)
	{
		new Handler().postDelayed(new Runnable()
		{
			@Override
			public void run()
			{
				retryListener.fail(0, "加载画布失败");
			}
		}, 3000);
	}

}
