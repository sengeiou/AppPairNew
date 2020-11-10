package com.lys.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.lys.app.R;
import com.lys.base.utils.LOG;

public class DialogLogin extends Dialog implements View.OnClickListener
{
	public interface OnLoginListener
	{
		void onLogin(String account, String psw);
	}

	private OnLoginListener listener = null;

	private void setListener(OnLoginListener listener)
	{
		this.listener = listener;
	}

	private class Holder
	{
		private EditText account;
		private EditText psw;
	}

	private Holder holder = new Holder();

	private void initHolder()
	{
		holder.account = findViewById(R.id.account);
		holder.psw = findViewById(R.id.psw);
	}

	private DialogLogin(@NonNull Context context)
	{
		super(context, R.style.Dialog);
//		setCancelable(false);
		setContentView(R.layout.dialog_login);
		initHolder();
		findViewById(R.id.login).setOnClickListener(this);
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
		case R.id.login:
			login(holder.account.getText().toString().trim(), holder.psw.getText().toString().trim());
			break;
		}
	}

	private void login(String account, String psw)
	{
		if (TextUtils.isEmpty(account))
		{
			LOG.toast(getContext(), "账号不能为空");
			return;
		}
		if (TextUtils.isEmpty(psw))
		{
			LOG.toast(getContext(), "密码不能为空");
			return;
		}
//		App.Account = account;
//		App.Psw = psw;
		dismiss();
		if (listener != null)
			listener.onLogin(account, psw);
	}

	public static void show(Context context, OnLoginListener listener)
	{
		DialogLogin dialog = new DialogLogin(context);
		dialog.setListener(listener);
		dialog.show();
	}

}