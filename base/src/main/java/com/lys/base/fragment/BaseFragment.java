package com.lys.base.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.lys.base.activity.BaseActivity;
import com.lys.base.utils.LOG;

public class BaseFragment extends Fragment
{
	protected Context context;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		context = this.getActivity();
		LOG.v("onCreateFragment : " + this);
	}

	@Override
	public void onDestroy()
	{
		super.onDestroy();
		LOG.v("onDestroyFragment : " + this);
	}

	public BaseActivity getBaseActivity()
	{
		return (BaseActivity) this.getActivity();
	}
}
