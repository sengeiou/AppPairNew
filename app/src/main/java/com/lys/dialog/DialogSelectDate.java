package com.lys.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TimePicker;

import com.lys.app.R;

import java.util.Date;

public class DialogSelectDate extends Dialog implements View.OnClickListener
{
	public interface OnListener
	{
		void onResult(long time);
	}

	private OnListener listener = null;

	private void setListener(OnListener listener)
	{
		this.listener = listener;
	}

	private class Holder
	{
		private CalendarView calendarView;
		private TimePicker time;
	}

	private Holder holder = new Holder();

	private void initHolder()
	{
		holder.calendarView = findViewById(R.id.calendarView);
		holder.time = findViewById(R.id.time);
	}

	private int mYear = -1, mMonth = -1, mDate = -1, mHour = -1, mMinute = -1;

	private DialogSelectDate(@NonNull Context context, long time)
	{
		super(context, R.style.FullDialog);
//		setCancelable(false);
		setContentView(R.layout.dialog_select_date);
		initHolder();

		findViewById(R.id.con).setOnClickListener(this);

		findViewById(R.id.cancel).setOnClickListener(this);
		findViewById(R.id.ok).setOnClickListener(this);

		if (time == 0)
			time = System.currentTimeMillis();

		Date date = new Date(time);
		mYear = date.getYear() + 1900;
		mMonth = date.getMonth() + 1;
		mDate = date.getDate();
		mHour = date.getHours();
		mMinute = date.getMinutes();

		holder.calendarView.setDate(time);
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

		holder.time.setCurrentHour(mHour);
		holder.time.setCurrentMinute(mMinute);
		holder.time.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener()
		{
			@Override
			public void onTimeChanged(TimePicker view, int hourOfDay, int minute)
			{
				mHour = hourOfDay;
				mMinute = minute;
			}
		});
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
			Date date = new Date();
			date.setYear(mYear - 1900);
			date.setMonth(mMonth - 1);
			date.setDate(mDate);
			date.setHours(mHour);
			date.setMinutes(mMinute);
			date.setSeconds(0);
			long time = date.getTime();
			dismiss();
			if (listener != null)
				listener.onResult(time);
			break;
		}
	}

	public static void show(Context context, long time, OnListener listener)
	{
		DialogSelectDate dialog = new DialogSelectDate(context, time);
		dialog.setListener(listener);
		dialog.show();
	}

}