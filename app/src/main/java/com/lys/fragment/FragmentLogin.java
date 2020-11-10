package com.lys.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.lys.activity.ActivityMain;
import com.lys.app.R;
import com.lys.base.fragment.BaseFragment;
import com.lys.base.utils.DrawableFactory;
import com.lys.base.utils.LOG;
import com.lys.config.AppConfig;

public class FragmentLogin extends BaseFragment implements View.OnClickListener
{
	private class Holder
	{
		private EditText account;
		private EditText psw;
	}

	private Holder holder = new Holder();

	private void initHolder(View view)
	{
		holder.account = view.findViewById(R.id.account);
		holder.psw = view.findViewById(R.id.psw);
	}

	private ActivityMain getMainActivity()
	{
		return (ActivityMain) getActivity();
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		getActivity().getWindow().setBackgroundDrawable(DrawableFactory.createColorDrawable(0xff39b3fe));
	}

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fragment_login, container, false);
		initHolder(view);

		holder.account.setText(AppConfig.readAccount());
		holder.psw.setText(AppConfig.readPsw());

		view.findViewById(R.id.login).setOnClickListener(this);

		return view;
	}

	@Override
	public void onDestroyView()
	{
		super.onDestroyView();
	}

	@Override
	public void onClick(View view)
	{
		if (view.getId() == R.id.login)
		{
			login(holder.account.getText().toString().trim(), holder.psw.getText().toString().trim());
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
		getMainActivity().doLogin(account, psw);
	}

}
