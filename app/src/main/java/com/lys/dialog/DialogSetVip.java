package com.lys.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.CalendarView;
import android.widget.RadioButton;

import com.lys.app.R;

import java.util.Date;

public class DialogSetVip extends Dialog implements View.OnClickListener
{
	public interface OnListener
	{
		void onResult(int vipLevel, long vipTime);
	}

	private OnListener listener = null;

	private void setListener(OnListener listener)
	{
		this.listener = listener;
	}

	private class Holder
	{
		private CalendarView calendarView;
		private RadioButton vip0;
		private RadioButton vip1;
		private RadioButton vip2;
		private RadioButton vip3;
		private RadioButton vip4;
	}

	private Holder holder = new Holder();

	private void initHolder()
	{
		holder.calendarView = findViewById(R.id.calendarView);
		holder.vip0 = findViewById(R.id.vip0);
		holder.vip1 = findViewById(R.id.vip1);
		holder.vip2 = findViewById(R.id.vip2);
		holder.vip3 = findViewById(R.id.vip3);
		holder.vip4 = findViewById(R.id.vip4);
	}

	private int mYear = -1, mMonth = -1, mDate = -1;

	private DialogSetVip(@NonNull Context context, int vipLevel, long vipTime)
	{
		super(context, R.style.FullDialog);
//		setCancelable(false);
		setContentView(R.layout.dialog_set_vip);
		initHolder();

		findViewById(R.id.con).setOnClickListener(this);

		findViewById(R.id.cancel).setOnClickListener(this);
		findViewById(R.id.ok).setOnClickListener(this);

		if (vipTime == 0)
			vipTime = System.currentTimeMillis();

		Date date = new Date(vipTime);
		mYear = date.getYear() + 1900;
		mMonth = date.getMonth() + 1;
		mDate = date.getDate();

		holder.calendarView.setDate(vipTime);

		holder.calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener()
		{
			@Override
			public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int date)
			{
				mYear = year;
				mMonth = month + 1;
				mDate = date;
			}
		});

		if (vipLevel == 0)
			holder.vip0.setChecked(true);
		else if (vipLevel == 1)
			holder.vip1.setChecked(true);
		else if (vipLevel == 2)
			holder.vip2.setChecked(true);
		else if (vipLevel == 3)
			holder.vip3.setChecked(true);
		else if (vipLevel == 4)
			holder.vip4.setChecked(true);
	}

	@Override
	public void dismiss()
	{
		super.dismiss();
	}

	@Override
	public void onClick(View view)
	{
		switch (view.getId())
		{
		case R.id.con:
			dismiss();
			break;

		case R.id.cancel:
			dismiss();
			break;

		case R.id.ok:
			int vipLevel = 0;
			if (holder.vip0.isChecked())
				vipLevel = 0;
			else if (holder.vip1.isChecked())
				vipLevel = 1;
			else if (holder.vip2.isChecked())
				vipLevel = 2;
			else if (holder.vip3.isChecked())
				vipLevel = 3;
			else if (holder.vip4.isChecked())
				vipLevel = 4;

			Date date = new Date();
			date.setYear(mYear - 1900);
			date.setMonth(mMonth - 1);
			date.setDate(mDate);
			date.setHours(23);
			date.setMinutes(59);
			date.setSeconds(59);
			long vipTime = date.getTime();

			dismiss();
			if (listener != null)
				listener.onResult(vipLevel, vipTime);
			break;
		}
	}

	public static void show(Context context, int vipLevel, long vipTime, OnListener listener)
	{
		DialogSetVip dialog = new DialogSetVip(context, vipLevel, vipTime);
		dialog.setListener(listener);
		dialog.show();
	}

}