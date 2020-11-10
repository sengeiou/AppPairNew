package com.lys.kit.pop;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.PopupWindow;

import com.lys.kit.R;

import java.util.Map;

public class PopWeike extends PopupWindow implements View.OnClickListener
{

	public static final int IconVideoSelect = 1;
	public static final int IconVideoScreen = 2;
	public static final int IconVideoVideo = 3;
	public static final int IconVideoDelete = 4;

	public interface OnClickListener
	{
		void onClickSelect();

		void onClickScreen();

		void onClickVideo();

		void onClickDelete();
	}

	private OnClickListener clickListener = null;

	public void setClickListener(OnClickListener clickListener)
	{
		this.clickListener = clickListener;
	}

	public PopWeike(Context context, Map<Integer, Boolean> iconMap)
	{
		super(context);
		View view = LayoutInflater.from(context).inflate(R.layout.pop_weike, null);
		setContentView(view);
		setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
		setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
		setBackgroundDrawable(new ColorDrawable(0x00000000)); // 点击外面消失
		setFocusable(true);

		view.findViewById(R.id.iconVideoSelect).setOnClickListener(this);
		view.findViewById(R.id.iconVideoScreen).setOnClickListener(this);
		view.findViewById(R.id.iconVideoVideo).setOnClickListener(this);
		view.findViewById(R.id.iconVideoDelete).setOnClickListener(this);

		GridLayout gridLayout = view.findViewById(R.id.gridLayout);
		if (!iconMap.get(IconVideoSelect))
			gridLayout.removeView(view.findViewById(R.id.iconVideoSelect));
		if (!iconMap.get(IconVideoScreen))
			gridLayout.removeView(view.findViewById(R.id.iconVideoScreen));
		if (!iconMap.get(IconVideoVideo))
			gridLayout.removeView(view.findViewById(R.id.iconVideoVideo));
		if (!iconMap.get(IconVideoDelete))
			gridLayout.removeView(view.findViewById(R.id.iconVideoDelete));

	}

	@Override
	public void onClick(View view)
	{
		if (view.getId() == R.id.iconVideoSelect)
		{
			dismiss();
			if (clickListener != null)
				clickListener.onClickSelect();
		}
		else if (view.getId() == R.id.iconVideoScreen)
		{
			dismiss();
			if (clickListener != null)
				clickListener.onClickScreen();
		}
		else if (view.getId() == R.id.iconVideoVideo)
		{
			dismiss();
			if (clickListener != null)
				clickListener.onClickVideo();
		}
		else if (view.getId() == R.id.iconVideoDelete)
		{
			dismiss();
			if (clickListener != null)
				clickListener.onClickDelete();
		}
	}

	public static void show(Context context, View view, OnClickListener clickListener, Map<Integer, Boolean> iconMap)
	{
		PopWeike pop = new PopWeike(context, iconMap);
		pop.setClickListener(clickListener);
		pop.getContentView().measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
		int windowHeight = pop.getContentView().getMeasuredHeight();
		pop.showAsDropDown(view, view.getWidth() + 18, -(view.getHeight() + windowHeight) / 2);
	}

}