package com.lys.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.lys.activity.ActivityNoteBook;
import com.lys.base.utils.LOG;
import com.lys.fragment.FragmentNotePage;
import com.lys.protobuf.SNoteBook;
import com.lys.protobuf.SNotePage;

import java.util.HashMap;
import java.util.List;

public class AdapterNotePage extends FragmentStatePagerAdapter
{
	private ActivityNoteBook owner;
	private SNoteBook book;

	public AdapterNotePage(FragmentManager fm, ActivityNoteBook owner, SNoteBook book)
	{
		super(fm);
		this.owner = owner;
		this.book = book;
	}

	private List<SNotePage> pages = null;

	public void setData(List<SNotePage> pages)
	{
		this.pages = pages;
		notifyDataSetChanged(); // 这个调用貌似没什么作用
	}

	public HashMap<Integer, FragmentNotePage> fragmentMap = new HashMap<>();

	@Override
	public Fragment getItem(int position)
	{
		LOG.v("getItem:" + position);
		SNotePage page = pages.get(position);
		FragmentNotePage fragment = new FragmentNotePage();
		Bundle bundle = new Bundle();
		bundle.putInt("position", position);
		bundle.putString("book", book.saveToStr());
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