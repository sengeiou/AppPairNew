package com.lys.kit.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter
{
	public List<Fragment> fragments = null;

	public SimpleFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragments)
	{
		super(fm);
		this.fragments = fragments;
	}

	public void setData(List<Fragment> fragments)
	{
		this.fragments = fragments;
		notifyDataSetChanged(); // 这个调用貌似没什么作用
	}

	@Override
	public Fragment getItem(int position)
	{
		return fragments.get(position);
	}

	@Override
	public int getCount()
	{
		if (fragments != null)
			return fragments.size();
		else
			return 0;
	}
}