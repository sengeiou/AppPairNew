package com.lys.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.lys.app.R;
import com.lys.kit.config.Config;

import java.util.ArrayList;
import java.util.List;

public class DialogSelectSubject extends Dialog implements View.OnClickListener
{
	public interface OnSelectListener
	{
		void onSelect(int subject);
	}

	private OnSelectListener listener = null;

	private void setListener(OnSelectListener listener)
	{
		this.listener = listener;
	}

	private class Holder
	{
		private List<TextView> subjects = new ArrayList<>();
	}

	private Holder holder = new Holder();

	private void initHolder()
	{
		holder.subjects.add((TextView) findViewById(R.id.subject0));
		holder.subjects.add((TextView) findViewById(R.id.subject1));
		holder.subjects.add((TextView) findViewById(R.id.subject2));
		holder.subjects.add((TextView) findViewById(R.id.subject3));
		holder.subjects.add((TextView) findViewById(R.id.subject4));
		holder.subjects.add((TextView) findViewById(R.id.subject5));
		holder.subjects.add((TextView) findViewById(R.id.subject6));
		holder.subjects.add((TextView) findViewById(R.id.subject7));
		holder.subjects.add((TextView) findViewById(R.id.subject8));
	}

	private DialogSelectSubject(@NonNull Context context)
	{
		super(context, R.style.Dialog);
//		setCancelable(false);
		setContentView(R.layout.dialog_select_subject);
		initHolder();

		for (int i = 1; i <= 9; i++)
		{
			final int subject = i;
			TextView subjectText = holder.subjects.get(i - 1);
			subjectText.setText(Config.getSubjectName(subject));
			subjectText.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View view)
				{
					dismiss();
					if (listener != null)
						listener.onSelect(subject);
				}
			});
		}

	}

	@Override
	public void dismiss()
	{
		super.dismiss();
	}

	@Override
	public void onClick(View view)
	{
	}

	public static void show(Context context, OnSelectListener listener)
	{
		DialogSelectSubject dialog = new DialogSelectSubject(context);
		dialog.setListener(listener);
		dialog.show();
	}

}