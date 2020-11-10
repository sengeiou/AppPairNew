package com.lys.kit.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.lys.kit.R;

import java.util.ArrayList;
import java.util.List;

public class DialogMenu extends Dialog implements View.OnClickListener
{
	public interface OnClickListener
	{
		void onClick(int which);
	}

	private OnClickListener clickListener = null;

	private void setMenus(OnClickListener clickListener, String... menus)
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

	private void initHolder()
	{
		holder.menus.add((TextView) findViewById(R.id.menu0));
		holder.menus.add((TextView) findViewById(R.id.menu1));
		holder.menus.add((TextView) findViewById(R.id.menu2));
		holder.menus.add((TextView) findViewById(R.id.menu3));
		holder.menus.add((TextView) findViewById(R.id.menu4));
		holder.menus.add((TextView) findViewById(R.id.menu5));
		holder.menus.add((TextView) findViewById(R.id.menu6));
		holder.menus.add((TextView) findViewById(R.id.menu7));
		holder.menus.add((TextView) findViewById(R.id.menu8));
		holder.menus.add((TextView) findViewById(R.id.menu9));

		holder.lines.add(findViewById(R.id.line0));
		holder.lines.add(findViewById(R.id.line1));
		holder.lines.add(findViewById(R.id.line2));
		holder.lines.add(findViewById(R.id.line3));
		holder.lines.add(findViewById(R.id.line4));
		holder.lines.add(findViewById(R.id.line5));
		holder.lines.add(findViewById(R.id.line6));
		holder.lines.add(findViewById(R.id.line7));
		holder.lines.add(findViewById(R.id.line8));
	}

	public static int layoutId = R.layout.dialog_menu;

	private DialogMenu(@NonNull Context context)
	{
		super(context, R.style.Dialog);
//		setCancelable(false);
		setContentView(layoutId);
		initHolder();
		for (TextView menu : holder.menus)
		{
			menu.setOnClickListener(this);
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

	public static void show(Context context, OnClickListener clickListener, String... menus)
	{
		DialogMenu dialog = new DialogMenu(context);
		dialog.setMenus(clickListener, menus);
		dialog.show();
	}

	public interface OnClickMenuListener
	{
		void onClick();
	}

	public static class Builder
	{
		private Context context;
		private List<String> menus = new ArrayList<>();
		private List<OnClickMenuListener> clickListeners = new ArrayList<>();

		public Builder(Context context)
		{
			this.context = context;
		}

		public Builder setMenu(String menu, OnClickMenuListener clickListener)
		{
			menus.add(menu);
			clickListeners.add(clickListener);
			return this;
		}

		private DialogMenu build()
		{
			if (menus.size() == 0)
				return null;
			DialogMenu dialog = new DialogMenu(context);
			dialog.setMenus(new OnClickListener()
			{
				@Override
				public void onClick(int which)
				{
					OnClickMenuListener clickListener = clickListeners.get(which);
					if (clickListener != null)
						clickListener.onClick();
				}
			}, menus.toArray(new String[menus.size()]));
			return dialog;
		}

		public DialogMenu show()
		{
			DialogMenu dialog = build();
			if (dialog != null)
				dialog.show();
			return dialog;
		}
	}

}