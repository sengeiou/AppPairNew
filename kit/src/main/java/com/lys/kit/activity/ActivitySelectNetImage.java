package com.lys.kit.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lys.base.utils.AppDataTool;
import com.lys.base.utils.HttpUtils;
import com.lys.base.utils.JsonHelper;
import com.lys.base.utils.LOG;
import com.lys.base.utils.SPHelper;
import com.lys.kit.R;
import com.lys.kit.adapter.SimpleFragmentPagerAdapter;
import com.lys.kit.fragment.FragmentSelectImageNet;
import com.lys.protobuf.SNetPicInfo;

import java.util.ArrayList;
import java.util.List;

public class ActivitySelectNetImage extends KitActivity implements ViewPager.OnPageChangeListener
{
	public static final String SP_Key_selectNetImageIndex = "selectNetImageIndex";

	private class Holder
	{
		private ViewGroup tabCon;
	}

	private Holder holder = new Holder();

	private void initHolder()
	{
		holder.tabCon = findViewById(R.id.tabCon);
	}

	private String types;

	private ViewPager viewPager;
	private SimpleFragmentPagerAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_net_image);
		initHolder();
		types = getIntent().getStringExtra("types");
		request();
	}

	private void request()
	{
		HttpUtils.doHttpGet(context, "http://zjyk-file.oss-cn-huhehaote.aliyuncs.com/插图/group.json.raw", new HttpUtils.OnCallback()
		{
			@Override
			public void onResponse(String text)
			{
				if (!TextUtils.isEmpty(text))
				{
					List<String> groups = AppDataTool.loadStringList(JsonHelper.getJSONArray(text));
					load(groups);
				}
				else
				{
					LOG.toast(context, "获取组失败");
				}
			}
		});
	}

	private void load(List<String> groups)
	{
		List<Fragment> fragments = new ArrayList<>();
		for (String group : groups)
		{
			fragments.add(genFragment("http://zjyk-file.oss-cn-huhehaote.aliyuncs.com/插图/" + group + "/list.json.raw", group));
		}

		viewPager = findViewById(R.id.viewPager);
		adapter = new SimpleFragmentPagerAdapter(getSupportFragmentManager(), fragments);
		viewPager.setAdapter(adapter);

		viewPager.addOnPageChangeListener(this);

		int index = SPHelper.getInt(context, SP_Key_selectNetImageIndex, groups.size() / 2);
		index = Math.max(index, 0);
		index = Math.min(index, fragments.size() - 1);
		if (index == 0)
			setSelectedFlag(0);
		else
			viewPager.setCurrentItem(index);
	}

	private Fragment genFragment(String root, String title)
	{
		final int index = holder.tabCon.getChildCount();

		View tab = LayoutInflater.from(context).inflate(R.layout.view_select_image_tab, null);
		holder.tabCon.addView(tab, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));

		TextView nameTab = tab.findViewById(R.id.nameTab);
		nameTab.setText(title);

		tab.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				viewPager.setCurrentItem(index);
			}
		});

		Fragment fragment;
		fragment = new FragmentSelectImageNet();
		Bundle bundle = new Bundle();
		bundle.putString("root", root);
		bundle.putString("types", types);
		fragment.setArguments(bundle);
		return fragment;
	}

	private void setSelectedFlag(int index)
	{
		SPHelper.putInt(context, SP_Key_selectNetImageIndex, index);

		for (int i = 0; i < holder.tabCon.getChildCount(); i++)
		{
			View tab = holder.tabCon.getChildAt(i);
			TextView nameTab = tab.findViewById(R.id.nameTab);
			View flagTab = tab.findViewById(R.id.flagTab);

			nameTab.setTextColor(0xff757575);
			nameTab.setTextSize(14);
			flagTab.setVisibility(View.INVISIBLE);

			if (i == index)
			{
				nameTab.setTextColor(0xff000000);
				nameTab.setTextSize(18);
				flagTab.setVisibility(View.VISIBLE);
			}
		}
	}

	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
	{
	}

	@Override
	public void onPageSelected(int position)
	{
		setSelectedFlag(position);
	}

	@Override
	public void onPageScrollStateChanged(int state)
	{
	}

	public void selectNetPic(SNetPicInfo netPic)
	{
		Intent intent = new Intent();
		intent.putExtra("netPicStr", netPic.saveToStr());
		setResult(Activity.RESULT_OK, intent);
		finish();
	}
}
