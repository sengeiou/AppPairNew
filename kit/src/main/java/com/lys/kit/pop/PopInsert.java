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

public class PopInsert extends PopupWindow implements View.OnClickListener
{
	public static final int IconImageSelect = 1;
	public static final int IconImageSelectImage = 9;
	public static final int IconImageSelectVideo = 10;
	public static final int IconImageCamera = 2;
	public static final int IconImageScreen = 3;
	public static final int IconImageVideo = 4;
	public static final int IconImageTopic = 5;
	public static final int IconImageSelectionGroup = 6;
	public static final int IconImageBoardText = 7;
	public static final int IconImageNetImg = 8;

	public interface OnClickListener
	{
		void onClickSelect();

		void onClickSelectImage();

		void onClickSelectVideo();

		void onClickCamera();

		void onClickScreen();

		void onClickVideo();

		void onClickTopic();

		void onClickSelectionGroup();

		void onClickBoardText();

		void onClickNetImg();
	}

	private OnClickListener clickListener = null;

	public void setClickListener(OnClickListener clickListener)
	{
		this.clickListener = clickListener;
	}

	public PopInsert(Context context, Map<Integer, Boolean> iconMap)
	{
		super(context);
		View view = LayoutInflater.from(context).inflate(R.layout.pop_insert, null);
		setContentView(view);
		setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
		setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
		setBackgroundDrawable(new ColorDrawable(0x00000000)); // 点击外面消失
		setFocusable(true);

		view.findViewById(R.id.iconImageSelect).setOnClickListener(this);
		view.findViewById(R.id.iconImageSelectImage).setOnClickListener(this);
		view.findViewById(R.id.iconImageSelectVideo).setOnClickListener(this);
		view.findViewById(R.id.iconImageCamera).setOnClickListener(this);
		view.findViewById(R.id.iconImageScreen).setOnClickListener(this);
		view.findViewById(R.id.iconImageVideo).setOnClickListener(this);
		view.findViewById(R.id.iconImageTopic).setOnClickListener(this);
		view.findViewById(R.id.iconImageSelectionGroup).setOnClickListener(this);
		view.findViewById(R.id.iconImageBoardText).setOnClickListener(this);
		view.findViewById(R.id.iconImageNetImg).setOnClickListener(this);

		GridLayout gridLayout = view.findViewById(R.id.gridLayout);
		if (!iconMap.get(IconImageSelect))
			gridLayout.removeView(view.findViewById(R.id.iconImageSelect));
		if (!iconMap.get(IconImageSelectImage))
			gridLayout.removeView(view.findViewById(R.id.iconImageSelectImage));
		if (!iconMap.get(IconImageSelectVideo))
			gridLayout.removeView(view.findViewById(R.id.iconImageSelectVideo));
		if (!iconMap.get(IconImageCamera))
			gridLayout.removeView(view.findViewById(R.id.iconImageCamera));
		if (!iconMap.get(IconImageScreen))
			gridLayout.removeView(view.findViewById(R.id.iconImageScreen));
		if (!iconMap.get(IconImageVideo))
			gridLayout.removeView(view.findViewById(R.id.iconImageVideo));
		if (!iconMap.get(IconImageTopic))
			gridLayout.removeView(view.findViewById(R.id.iconImageTopic));
		if (!iconMap.get(IconImageSelectionGroup))
			gridLayout.removeView(view.findViewById(R.id.iconImageSelectionGroup));
		if (!iconMap.get(IconImageBoardText))
			gridLayout.removeView(view.findViewById(R.id.iconImageBoardText));
		if (!iconMap.get(IconImageNetImg))
			gridLayout.removeView(view.findViewById(R.id.iconImageNetImg));

	}

	@Override
	public void onClick(View view)
	{
		if (view.getId() == R.id.iconImageSelect)
		{
			dismiss();
			if (clickListener != null)
				clickListener.onClickSelect();
		}
		else if (view.getId() == R.id.iconImageSelectImage)
		{
			dismiss();
			if (clickListener != null)
				clickListener.onClickSelectImage();
		}
		else if (view.getId() == R.id.iconImageSelectVideo)
		{
			dismiss();
			if (clickListener != null)
				clickListener.onClickSelectVideo();
		}
		else if (view.getId() == R.id.iconImageCamera)
		{
			dismiss();
			if (clickListener != null)
				clickListener.onClickCamera();
		}
		else if (view.getId() == R.id.iconImageScreen)
		{
			dismiss();
			if (clickListener != null)
				clickListener.onClickScreen();
		}
		else if (view.getId() == R.id.iconImageVideo)
		{
			dismiss();
			if (clickListener != null)
				clickListener.onClickVideo();
		}
		else if (view.getId() == R.id.iconImageTopic)
		{
			dismiss();
			if (clickListener != null)
				clickListener.onClickTopic();
		}
		else if (view.getId() == R.id.iconImageSelectionGroup)
		{
			dismiss();
			if (clickListener != null)
				clickListener.onClickSelectionGroup();
		}
		else if (view.getId() == R.id.iconImageBoardText)
		{
			dismiss();
			if (clickListener != null)
				clickListener.onClickBoardText();
		}
		else if (view.getId() == R.id.iconImageNetImg)
		{
			dismiss();
			if (clickListener != null)
				clickListener.onClickNetImg();
		}
	}

	public static void show(Context context, View view, OnClickListener clickListener, Map<Integer, Boolean> iconMap)
	{
		PopInsert pop = new PopInsert(context, iconMap);
		pop.setClickListener(clickListener);
		pop.getContentView().measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
		int windowHeight = pop.getContentView().getMeasuredHeight();
		pop.showAsDropDown(view, view.getWidth() + 18, -(view.getHeight() + windowHeight) / 2);
	}

}