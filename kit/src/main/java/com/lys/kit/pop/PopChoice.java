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
import com.lys.kit.adapter.AdapterChoice;
import com.lys.kit.config.Config;
import com.lys.protobuf.SChoiceItem;

import java.util.ArrayList;
import java.util.List;

public class PopChoice extends PopupWindow
{
	public interface OnListener
	{
		void onSelect(SChoiceItem choiceItem);
	}

	private OnListener listener = null;

	private void setListener(OnListener listener)
	{
		this.listener = listener;
	}

	private Context context;

	private RecyclerView recyclerView;
	private AdapterChoice adapter;

	private PopChoice(Context context, List<SChoiceItem> choiceItems)
	{
		super(context);
		this.context = context;
		View view = LayoutInflater.from(context).inflate(R.layout.pop_choice, null);
		setContentView(view);
		setWidth(300);
		setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
		setBackgroundDrawable(new ColorDrawable(0x00000000)); // 点击外面消失
		setFocusable(true);

		recyclerView = view.findViewById(R.id.recyclerView);
		recyclerView.setLayoutManager(new LinearLayoutManager(context));
		adapter = new AdapterChoice(this);
		recyclerView.setAdapter(adapter);

		adapter.setData(choiceItems);
	}

	public void select(SChoiceItem choiceItem)
	{
		dismiss();
		if (listener != null)
			listener.onSelect(choiceItem);
	}

	public static void show(Context context, View view, List<SChoiceItem> choiceItems, OnListener listener)
	{
		PopChoice popup = new PopChoice(context, choiceItems);
		popup.setListener(listener);
		popup.showAsDropDown(view, -45, 0);
	}

	public static void showPhase(Context context, View view, OnListener listener)
	{
		List<SChoiceItem> choiceItems = new ArrayList<>();
		for (int i = 2; i <= 3; i++)
		{
			SChoiceItem choiceItem = new SChoiceItem();
			choiceItem.name = Config.getPhaseName(i);
			choiceItem.value = i;
			choiceItem.number = 0;
			choiceItems.add(choiceItem);
		}
		show(context, view, choiceItems, listener);
	}

	public static void showSubject(Context context, View view, OnListener listener)
	{
		List<SChoiceItem> choiceItems = new ArrayList<>();
		for (int i = 1; i <= 9; i++)
		{
			SChoiceItem choiceItem = new SChoiceItem();
			choiceItem.name = Config.getSubjectName(i);
			choiceItem.value = i;
			choiceItem.number = 0;
			choiceItems.add(choiceItem);
		}
		show(context, view, choiceItems, listener);
	}
}