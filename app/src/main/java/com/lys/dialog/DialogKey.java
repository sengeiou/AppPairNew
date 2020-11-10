package com.lys.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.lys.app.R;

public class DialogKey extends Dialog implements View.OnClickListener
{
	public interface OnKeyListener
	{
		void onKey(String key);
	}

	private OnKeyListener listener = null;

	private void setListener(OnKeyListener listener)
	{
		this.listener = listener;
	}

	private class Holder
	{
		private EditText key;
	}

	private Holder holder = new Holder();

	private void initHolder()
	{
		holder.key = findViewById(R.id.key);
	}

	private DialogKey(@NonNull Context context)
	{
		super(context, R.style.Dialog);
//		setCancelable(false);
		setContentView(R.layout.dialog_key);
		initHolder();
		findViewById(R.id.cast).setOnClickListener(this);
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
		case R.id.cast:
			cast(holder.key.getText().toString());
			break;
		}
	}

	private void cast(String key)
	{
		if (TextUtils.isEmpty(key))
		{
			Toast.makeText(getContext(), "key不能为空", Toast.LENGTH_SHORT).show();
			return;
		}
		dismiss();
		if (listener != null)
			listener.onKey(key);
	}

	public static void show(Context context, OnKeyListener listener)
	{
		DialogKey dialog = new DialogKey(context);
		dialog.setListener(listener);
		dialog.show();
	}

}