package com.lys.kit.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.TextView;

import com.lys.kit.R;

public class DialogMask extends Dialog
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

	public DialogMask(@NonNull Context context)
	{
		super(context, R.style.FullDialog);
		setCancelable(false);
		setContentView(R.layout.dialog_mask);
		initHolder();
	}

	@Override
	public void dismiss()
	{
		super.dismiss();
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

	private static DialogMask dialogWait = null;
//	private static Handler waitHandler = new Handler();
//	private static Runnable waitRunnable = new Runnable()
//	{
//		@Override
//		public void run()
//		{
//			if (dialogWait != null)
//				dialogWait.show();
//		}
//	};

	public static void message(String message)
	{
		if (dialogWait != null)
		{
			dialogWait.setMessage(message);
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
			dialogWait = new DialogMask(context);
			dialogWait.setMessage(message);
//			waitHandler.postDelayed(waitRunnable, 500);
			dialogWait.show();
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
//			else
//				waitHandler.removeCallbacks(waitRunnable);
			dialogWait = null;
		}
	}
}