package com.lys.kit.view;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.lys.kit.R;
import com.lys.kit.adapter.AdapterLog;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LogWatch extends RelativeLayout implements View.OnClickListener
{
	private class Holder
	{
//		private ImageView doSwitch;
	}

	private Holder holder = new Holder();

	private void initHolder()
	{
//		holder.doSwitch = findViewById(R.id.doSwitch);
	}

	private RecyclerView recyclerView;
	private LinearLayoutManager layoutManager;
	private AdapterLog adapter;

	public LogWatch(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		LayoutInflater.from(context).inflate(R.layout.view_log_watch, this, true);

		initHolder();

		recyclerView = findViewById(R.id.recyclerView);
		layoutManager = new LinearLayoutManager(context);
		recyclerView.setLayoutManager(layoutManager);
		adapter = new AdapterLog();
		recyclerView.setAdapter(adapter);
	}

	@Override
	public void onClick(View view)
	{
//		if (view.getId() == R.id.doSwitch)
//		{
//			doSwitch();
//		}
	}

	private static final SimpleDateFormat format = new SimpleDateFormat("MM-dd HH:mm:ss");

	public void addData(String msg)
	{
		Date date = new Date();
		String time = format.format(date);
		long ms = date.getTime() % 1000;
		long mainThreadId = Looper.getMainLooper().getThread().getId();
		long threadId = Thread.currentThread().getId();
		String pkgMsg = String.format("%s.%03d %04d-%04d : %s", time, ms, mainThreadId, threadId, msg);
		boolean bottom = false;
		try
		{
			bottom = (layoutManager.findLastVisibleItemPosition() == adapter.getItemCount() - 1);
		}
		catch (Exception e)
		{
		}
		final boolean isBottom = bottom;
		adapter.addData(pkgMsg);
		new Handler(Looper.getMainLooper()).post(new Runnable()
		{
			@Override
			public void run()
			{
				adapter.notifyDataSetChanged();
				if (isBottom)
					recyclerView.scrollToPosition(adapter.getItemCount() - 1);
			}
		});
	}

	public void scrollToBottom()
	{
		if (adapter.getItemCount() > 0)
			recyclerView.scrollToPosition(adapter.getItemCount() - 1);
	}
}
