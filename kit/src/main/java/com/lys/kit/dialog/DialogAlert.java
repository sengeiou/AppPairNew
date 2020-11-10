package com.lys.kit.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.lys.base.utils.LOG;
import com.lys.kit.R;

public class DialogAlert
{
	public interface OnClickListener
	{
		void onClick(int which);
	}

	public static void show(Context context, String title, String message, final OnClickListener clickListener, String... btns)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		if (!TextUtils.isEmpty(title))
			builder.setTitle(title);
		if (!TextUtils.isEmpty(message))
			builder.setMessage(message);
		if (btns.length == 1)
		{
			builder.setPositiveButton(btns[0], new DialogInterface.OnClickListener()
			{
				@Override
				public void onClick(DialogInterface dialogInterface, int which)
				{
					if (clickListener != null)
						clickListener.onClick(0);
				}
			});
		}
		else if (btns.length == 2)
		{
			builder.setNeutralButton(btns[0], new DialogInterface.OnClickListener()
			{
				@Override
				public void onClick(DialogInterface dialogInterface, int i)
				{
					if (clickListener != null)
						clickListener.onClick(0);
				}
			});
			builder.setPositiveButton(btns[1], new DialogInterface.OnClickListener()
			{
				@Override
				public void onClick(DialogInterface dialogInterface, int which)
				{
					if (clickListener != null)
						clickListener.onClick(1);
				}
			});
		}
		else if (btns.length == 3)
		{
			builder.setNeutralButton(btns[0], new DialogInterface.OnClickListener()
			{
				@Override
				public void onClick(DialogInterface dialogInterface, int i)
				{
					if (clickListener != null)
						clickListener.onClick(0);
				}
			});
			builder.setNegativeButton(btns[1], new DialogInterface.OnClickListener()
			{
				@Override
				public void onClick(DialogInterface dialogInterface, int i)
				{
					if (clickListener != null)
						clickListener.onClick(1);
				}
			});
			builder.setPositiveButton(btns[2], new DialogInterface.OnClickListener()
			{
				@Override
				public void onClick(DialogInterface dialogInterface, int which)
				{
					if (clickListener != null)
						clickListener.onClick(2);
				}
			});
		}
		try
		{
			builder.show();
		}
		catch (Exception e)
		{
			LOG.v("activity 可能已关闭");
		}
	}

	public interface OnInputListener
	{
		void onInput(String text);
	}

	public static void showInput(Context context, String message, String text, final OnInputListener inputListener)
	{
		final View view = LayoutInflater.from(context).inflate(R.layout.dialog_input, null);
		final EditText input = view.findViewById(R.id.input);
		input.setText(text);
		input.selectAll();
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setMessage(message);
		builder.setView(view);
		builder.setNeutralButton("取消", null);
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialogInterface, int which)
			{
				if (inputListener != null)
					inputListener.onInput(input.getText().toString());
			}
		});
		builder.show();
	}

	public static void showInputNumber(final Context context, String message, int number, final OnInputListener inputListener)
	{
		final View view = LayoutInflater.from(context).inflate(R.layout.dialog_input_number, null);
		final EditText input = view.findViewById(R.id.input);
		input.setText(String.valueOf(number));
		input.selectAll();
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setMessage(message);
		builder.setView(view);
		builder.setNeutralButton("取消", null);
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialogInterface, int which)
			{
				int num = -1;
				try
				{
					num = Integer.valueOf(input.getText().toString());
				}
				catch (Exception e)
				{
					LOG.toast(context, "输入的数字不合法");
					e.printStackTrace();
				}
				if (num >= 0)
				{
					if (inputListener != null)
						inputListener.onInput(String.valueOf(num));
				}
				else
				{
					LOG.toast(context, "输入的数字不正确");
				}
			}
		});
		builder.show();
	}

	public static void showMultiInput(Context context, String message, String text, final OnInputListener inputListener)
	{
		if (text == null)
			text = "";
		final View view = LayoutInflater.from(context).inflate(R.layout.dialog_multi_input, null);
		final EditText input = view.findViewById(R.id.input);
		input.setText(text);
		input.setSelection(text.length());
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setMessage(message);
		builder.setView(view);
		builder.setNeutralButton("取消", null);
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialogInterface, int which)
			{
				if (inputListener != null)
					inputListener.onInput(input.getText().toString());
			}
		});
		builder.show();
	}
}