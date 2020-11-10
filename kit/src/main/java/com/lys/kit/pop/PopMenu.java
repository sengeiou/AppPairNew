package com.lys.kit.pop;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.lys.kit.R;

import java.util.ArrayList;

public class PopMenu extends PopupWindow implements View.OnClickListener
{
	public interface OnClickListener
	{
		void onClick(int which);
	}

	private OnClickListener clickListener = null;

	public void setMenus(OnClickListener clickListener, String... menus)
	{
		this.clickListener = clickListener;

		holder.menus.get(0).setText(menus[0]);

		for (int i = 1; i < holder.menus.size(); i++)
		{
			if (i < menus.length)
			{
				holder.menus.get(i).setVisibility(View.VISIBLE);
				holder.lines.get(i - 1).setVisibility(View.VISIBLE);
				holder.menus.get(i).setText(menus[i]);
			}
			else
			{
				holder.menus.get(i).setVisibility(View.GONE);
				holder.lines.get(i - 1).setVisibility(View.GONE);
			}
		}
	}

	private class Holder
	{
		private ArrayList<TextView> menus = new ArrayList<>();
		private ArrayList<View> lines = new ArrayList<>();
	}

	private Holder holder = new Holder();

	private void initHolder(View view)
	{
		holder.menus.add((TextView) view.findViewById(R.id.menu0));
		holder.menus.add((TextView) view.findViewById(R.id.menu1));
		holder.menus.add((TextView) view.findViewById(R.id.menu2));
		holder.menus.add((TextView) view.findViewById(R.id.menu3));
		holder.menus.add((TextView) view.findViewById(R.id.menu4));
		holder.menus.add((TextView) view.findViewById(R.id.menu5));
		holder.menus.add((TextView) view.findViewById(R.id.menu6));
		holder.menus.add((TextView) view.findViewById(R.id.menu7));
		holder.menus.add((TextView) view.findViewById(R.id.menu8));
		holder.menus.add((TextView) view.findViewById(R.id.menu9));

		holder.lines.add(view.findViewById(R.id.line0));
		holder.lines.add(view.findViewById(R.id.line1));
		holder.lines.add(view.findViewById(R.id.line2));
		holder.lines.add(view.findViewById(R.id.line3));
		holder.lines.add(view.findViewById(R.id.line4));
		holder.lines.add(view.findViewById(R.id.line5));
		holder.lines.add(view.findViewById(R.id.line6));
		holder.lines.add(view.findViewById(R.id.line7));
		holder.lines.add(view.findViewById(R.id.line8));
	}

	private static final int MenuWidth = 200;

	public PopMenu(Context context)
	{
		super(context);
		View view = LayoutInflater.from(context).inflate(R.layout.pop_menu, null);
		setContentView(view);
		initHolder(view);
		setWidth(MenuWidth);
		setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
		setBackgroundDrawable(new ColorDrawable(0x00000000)); // 点击外面消失
		setFocusable(true);
		for (TextView menu : holder.menus)
		{
			menu.setOnClickListener(this);
		}
	}

	@Override
	public void onClick(View view)
	{
		for (int i = 0; i < holder.menus.size(); i++)
		{
			if (view.getId() == holder.menus.get(i).getId())
			{
				dismiss();
				if (clickListener != null)
					clickListener.onClick(i);
				break;
			}
		}
	}

	public static void show(Context context, View view, OnClickListener clickListener, String... menus)
	{
		PopMenu popMenu = new PopMenu(context);
		popMenu.setMenus(clickListener, menus);
		popMenu.showAsDropDown(view, -(MenuWidth - view.getWidth()) / 2, 0);
	}

}