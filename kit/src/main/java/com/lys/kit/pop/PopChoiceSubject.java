package com.lys.kit.pop;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.lys.kit.R;
import com.lys.kit.adapter.AdapterChoiceSubject;
import com.lys.protobuf.SSubjectCount;

import java.util.ArrayList;
import java.util.List;

public class PopChoiceSubject extends PopupWindow
{
	public interface OnListener
	{
		void onSelect(SSubjectCount subjectCount);
	}

	private OnListener listener = null;

	private void setListener(OnListener listener)
	{
		this.listener = listener;
	}

	private Context context;

	private RecyclerView recyclerView;
	private AdapterChoiceSubject adapter;

	private PopChoiceSubject(Context context, List<SSubjectCount> subjectCounts)
	{
		super(context);
		this.context = context;
		View view = LayoutInflater.from(context).inflate(R.layout.pop_choice_subject, null);
		setContentView(view);
		setWidth(300);
		setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
		setBackgroundDrawable(new ColorDrawable(0x00000000)); // 点击外面消失
		setFocusable(true);

		recyclerView = view.findViewById(R.id.recyclerView);
		recyclerView.setLayoutManager(new LinearLayoutManager(context));
		adapter = new AdapterChoiceSubject(this);
		recyclerView.setAdapter(adapter);

		adapter.setData(subjectCounts);
	}

	public void select(SSubjectCount subjectCount)
	{
		dismiss();
		if (listener != null)
			listener.onSelect(subjectCount);
	}

	public static void show(Context context, View view, OnListener listener)
	{
		List<SSubjectCount> subjectCounts = new ArrayList<>();
		for (int i = 1; i <= 9; i++)
		{
			SSubjectCount subjectCount = new SSubjectCount();
			subjectCount.subject = i;
			subjectCount.number = 23;
			subjectCounts.add(subjectCount);
		}
		PopChoiceSubject popup = new PopChoiceSubject(context, subjectCounts);
		popup.setListener(listener);
		popup.showAsDropDown(view, -45, 0);
	}
}