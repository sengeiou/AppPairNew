package com.lys.kit.pop;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.lys.base.utils.DrawableFactory;
import com.lys.kit.R;

public class PopColor extends PopupWindow
{
	public static final int Color1 = 0xff2b1603;
	public static final int Color2 = 0xffc42169;
	public static final int Color3 = 0xff8bad21;
	public static final int Color4 = 0xff1e77b9;
	public static final int Color5 = 0xffffffff;
	public static final int Color6 = 0xffb88355;
	public static final int Color7 = 0xff9f70bb;
	public static final int Color8 = 0xfff99c3d;
	public static final int Color9 = 0xffff1e10;

	public interface OnClickListener
	{
		void onClick(int color);
	}

	private OnClickListener clickListener = null;

	public void setClickListener(OnClickListener clickListener)
	{
		this.clickListener = clickListener;
	}

	public PopColor(Context context, int conBgRedId, int... colors)
	{
		super(context);
		View view = LayoutInflater.from(context).inflate(R.layout.pop_color, null);
		setContentView(view);
		setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
		setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
		setBackgroundDrawable(new ColorDrawable(0x00000000)); // 点击外面消失
		setFocusable(true);

		ViewGroup con = view.findViewById(R.id.con);
		con.setBackgroundResource(conBgRedId);

		GridLayout gridLayout = view.findViewById(R.id.gridLayout);

		for (final int color : colors)
		{
			View item = LayoutInflater.from(context).inflate(R.layout.view_color, null);
			ImageView itemColor = item.findViewById(R.id.color);
			if (color == Color.TRANSPARENT)
				itemColor.setImageResource(R.drawable.board_bg_default);
			else
				itemColor.setBackground(DrawableFactory.createCircleFill(color));
			gridLayout.addView(item, 120, 120);
			item.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View view)
				{
					dismiss();
					if (clickListener != null)
						clickListener.onClick(color);
				}
			});
		}

	}

	public static void show(Context context, View view, OnClickListener clickListener)
	{
		PopColor pop = new PopColor(context, R.drawable.img_board_menu_con, Color1, Color2, Color3, Color4, Color5, Color6, Color7, Color8, Color9);
		pop.setClickListener(clickListener);
		pop.getContentView().measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
		int windowHeight = pop.getContentView().getMeasuredHeight();
		pop.showAsDropDown(view, view.getWidth() + 18, -(view.getHeight() + windowHeight) / 2);
	}

	public static void show2(Context context, View view, OnClickListener clickListener)
	{
		PopColor pop = new PopColor(context, R.drawable.img_board_menu_con_top, Color.TRANSPARENT, Color.WHITE, Color.BLACK, Color1, Color2, Color3, Color4, Color5, Color6, Color7, Color8, Color9);
		pop.setClickListener(clickListener);
		pop.getContentView().measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
		int windowWidth = pop.getContentView().getMeasuredWidth();
		pop.showAsDropDown(view, (view.getWidth() - windowWidth) / 2, 0);
	}

}