package com.lys.kit.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.widget.TextView;

import com.lys.kit.R;

public class DialogWait extends Dialog
{
	private class Holder
	{
		private TextView message;
		private TextView info;
	}

	private Holder holder = new Holder();

	private void initHolder()
	{
		holder.message = findViewById(R.id.message);
		holder.info = findViewById(R.id.info);
	}

	private Context context;

	public DialogWait(@NonNull Context context)
	{
		super(context, R.style.Dialog);
		this.context = context;
		setCancelable(false);
		setContentView(R.layout.dialog_wait);
		initHolder();
	}

	@Override
	public void dismiss()
	{
		try
		{
			super.dismiss();
		}
		catch (Exception e)
		{
		}
	}

	public void setMessage(String message)
	{
		holder.message.setText(message);
	}

	public void setInfo(String info)
	{
		holder.info.setText(info);
	}

	//-------------------------------------

	private static DialogWait dialogWait = null;
	private static Handler waitHandler = new Handler();
	private static Runnable waitRunnable = new Runnable()
	{
		@Override
		public void run()
		{
			if (dialogWait != null)
				dialogWait.show();
		}
	};

	public static void message(String message)
	{
		if (dialogWait != null)
		{
			dialogWait.setMessage(message);
		}
	}

	public static void messageWithUIThread(final String message)
	{
		try
		{
			if (dialogWait != null)
			{
//				LOG.v(message + ":" + dialogWait.getContext());
				if (dialogWait.context instanceof Activity)
				{
					Activity activity = (Activity) dialogWait.context;
					activity.runOnUiThread(new Runnable()
					{
						@Override
						public void run()
						{
							try
							{
								dialogWait.setMessage(message);
							}
							catch (Exception e)
							{
								e.printStackTrace();
							}
						}
					});
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void info(String info)
	{
		if (dialogWait != null)
		{
			dialogWait.setInfo(info);
		}
	}

	public static void show(Context context, String message)
	{
		if (dialogWait == null)
		{
			dialogWait = new DialogWait(context);
			dialogWait.setMessage(message);
			waitHandler.postDelayed(waitRunnable, 500);
		}
		else
		{
			dialogWait.setMessage(message);
		}
	}

	public static void close()
	{
		if (dialogWait != null)
		{
			if (dialogWait.isShowing())
				dialogWait.dismiss();
			else
				waitHandler.removeCallbacks(waitRunnable);
			dialogWait = null;
		}
	}
}