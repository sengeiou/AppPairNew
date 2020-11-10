package com.lys.kit.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lys.kit.R;

public class DialogTimeWait extends Dialog
{
	public interface OnTickListener
	{
		void onTick(DialogTimeWait dialog, long timeDt);
	}

	public interface OnClickListener
	{
		void onClick(DialogTimeWait dialog);
	}

	private class Holder
	{
		private ProgressBar wait;

		private TextView message;
		private TextView info;

		private TextView btnLeft;
		private TextView btnCenter;
		private TextView btnRight;
	}

	private Holder holder = new Holder();

	private void initHolder()
	{
		holder.wait = findViewById(R.id.wait);

		holder.message = findViewById(R.id.message);
		holder.info = findViewById(R.id.info);

		holder.btnLeft = findViewById(R.id.btnLeft);
		holder.btnCenter = findViewById(R.id.btnCenter);
		holder.btnRight = findViewById(R.id.btnRight);
	}

	private OnTickListener mTickListener = null;
//	private OnClickListener mLeftClickListener = null;
//	private OnClickListener mCenterClickListener = null;
//	private OnClickListener mRightClickListener = null;

	private long timeBegin;
	private boolean ticking = false;

	private Handler waitHandler = new Handler();
	private Runnable waitRunnable = new Runnable()
	{
		@Override
		public void run()
		{
			if (ticking)
			{
				long timeDt = System.currentTimeMillis() - timeBegin;
				if (mTickListener != null)
					mTickListener.onTick(DialogTimeWait.this, timeDt);
			}
			if (DialogTimeWait.this.isShowing())
				waitHandler.postDelayed(waitRunnable, 1000);
		}
	};

	public static int layoutId = R.layout.dialog_time_wait;

	private DialogTimeWait(@NonNull Context context)
	{
		super(context, R.style.Dialog);
		setCancelable(false);
		setContentView(layoutId);
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
		waitHandler.removeCallbacks(waitRunnable);
	}

	@Override
	public void show()
	{
		super.show();
		waitHandler.post(waitRunnable);
	}

	public long getTimeDt()
	{
		return System.currentTimeMillis() - timeBegin;
	}

	public DialogTimeWait showWait(boolean showWait)
	{
		holder.wait.setVisibility(showWait ? View.VISIBLE : View.GONE);
		return this;
	}

	public DialogTimeWait setTickListener(OnTickListener listener)
	{
		mTickListener = listener;
		return this;
	}

	public DialogTimeWait startTick()
	{
		timeBegin = System.currentTimeMillis();
		ticking = true;
		return this;
	}

	public DialogTimeWait stopTick()
	{
		ticking = false;
		return this;
	}

	public DialogTimeWait setMessage(String message)
	{
		if (TextUtils.isEmpty(message))
		{
			holder.message.setVisibility(View.GONE);
		}
		else
		{
			holder.message.setVisibility(View.VISIBLE);
			holder.message.setText(message);
		}
		return this;
	}

	public DialogTimeWait setInfo(String info)
	{
		if (TextUtils.isEmpty(info))
		{
			holder.info.setVisibility(View.GONE);
		}
		else
		{
			holder.info.setVisibility(View.VISIBLE);
			holder.info.setText(info);
		}
		return this;
	}

	// 1 : right
	// 2 : left right
	// 3 : left center right

	public DialogTimeWait setLeftListener(String btn, final OnClickListener listener)
	{
		if (TextUtils.isEmpty(btn))
		{
			holder.btnLeft.setVisibility(View.GONE);
		}
		else
		{
			holder.btnLeft.setVisibility(View.VISIBLE);
			holder.btnLeft.setText(btn);
			holder.btnLeft.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View view)
				{
//					dismiss();
					if (listener != null)
						listener.onClick(DialogTimeWait.this);
				}
			});
		}
		return this;
	}

	public DialogTimeWait setCenterListener(String btn, final OnClickListener listener)
	{
		if (TextUtils.isEmpty(btn))
		{
			holder.btnCenter.setVisibility(View.GONE);
		}
		else
		{
			holder.btnCenter.setVisibility(View.VISIBLE);
			holder.btnCenter.setText(btn);
			holder.btnCenter.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View view)
				{
//					dismiss();
					if (listener != null)
						listener.onClick(DialogTimeWait.this);
				}
			});
		}
		return this;
	}

	public DialogTimeWait setRightListener(String btn, final OnClickListener listener)
	{
		if (TextUtils.isEmpty(btn))
		{
			holder.btnRight.setVisibility(View.GONE);
		}
		else
		{
			holder.btnRight.setVisibility(View.VISIBLE);
			holder.btnRight.setText(btn);
			holder.btnRight.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View view)
				{
//					dismiss();
					if (listener != null)
						listener.onClick(DialogTimeWait.this);
				}
			});
		}
		return this;
	}

	//-------------------------------------

	private static DialogTimeWait dialogWait = null;

	public static long timeDt()
	{
		if (dialogWait != null && dialogWait.isShowing())
		{
			return dialogWait.getTimeDt();
		}
		return 0;
	}

	public static void wait(boolean showWait)
	{
		if (dialogWait != null && dialogWait.isShowing())
		{
			dialogWait.showWait(showWait);
		}
	}

	public static void tickListener(OnTickListener listener)
	{
		if (dialogWait != null && dialogWait.isShowing())
		{
			dialogWait.setTickListener(listener);
		}
	}

	public static void start()
	{
		if (dialogWait != null && dialogWait.isShowing())
		{
			dialogWait.startTick();
		}
	}

	public static void stop()
	{
		if (dialogWait != null && dialogWait.isShowing())
		{
			dialogWait.stopTick();
		}
	}

	public static void message(String message)
	{
		if (dialogWait != null && dialogWait.isShowing())
		{
			dialogWait.setMessage(message);
		}
	}

	public static void info(String info)
	{
		if (dialogWait != null && dialogWait.isShowing())
		{
			dialogWait.setInfo(info);
		}
	}

	public static void leftListener(String btn, final OnClickListener listener)
	{
		if (dialogWait != null && dialogWait.isShowing())
		{
			dialogWait.setLeftListener(btn, listener);
		}
	}

	public static void centerListener(String btn, final OnClickListener listener)
	{
		if (dialogWait != null && dialogWait.isShowing())
		{
			dialogWait.setCenterListener(btn, listener);
		}
	}

	public static void rightListener(String btn, final OnClickListener listener)
	{
		if (dialogWait != null && dialogWait.isShowing())
		{
			dialogWait.setRightListener(btn, listener);
		}
	}

	public static void close()
	{
		if (dialogWait != null && dialogWait.isShowing())
		{
			dialogWait.dismiss();
			dialogWait = null;
		}
	}

	public static DialogTimeWait create(Context context)
	{
		close();
		dialogWait = new DialogTimeWait(context);
		return dialogWait;
	}

}