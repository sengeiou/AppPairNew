package com.lys.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.lys.activity.ActivityTaskBook;
import com.lys.base.utils.LOG;
import com.lys.fragment.FragmentTaskPage;
import com.lys.protobuf.SNotePage;
import com.lys.protobuf.SPTask;

import java.util.HashMap;
import java.util.List;

public class AdapterTaskPage extends FragmentStatePagerAdapter
{
	private ActivityTaskBook owner;
	private SPTask task;

	public AdapterTaskPage(FragmentManager fm, ActivityTaskBook owner, SPTask task)
	{
		super(fm);
		this.owner = owner;
		this.task = task;
	}

	private List<SNotePage> pages = null;

	public void setData(List<SNotePage> pages)
	{
		this.pages = pages;
		notifyDataSetChanged(); // 这个调用貌似没什么作用
	}

	public HashMap<Integer, FragmentTaskPage> fragmentMap = new HashMap<>();

	@Override
	public Fragment getItem(int position)
	{
		LOG.v("getItem:" + position);
		SNotePage page = pages.get(position);
		FragmentTaskPage fragment = new FragmentTaskPage();
		Bundle bundle = new Bundle();
		bundle.putInt("position", position);
		bundle.putString("task", task.saveToStr());
		bundle.putString("page", page.saveToStr());
		fragment.setArguments(bundle);
		fragmentMap.put(position, fragment);
		return fragment;
	}

	@Override
	public int getCount()
	{
		if (pages != null)
			return pages.size();
		else
			return 0;
	}
}