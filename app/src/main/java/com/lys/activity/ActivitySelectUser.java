package com.lys.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lys.app.R;
import com.lys.base.utils.AppDataTool;
import com.lys.base.utils.JsonHelper;
import com.lys.fragment.FragmentSelectUser;
import com.lys.kit.activity.KitActivity;
import com.lys.kit.adapter.SimpleFragmentPagerAdapter;
import com.lys.protobuf.SUser;
import com.lys.protobuf.SUserType;

import java.util.ArrayList;
import java.util.List;

public class ActivitySelectUser extends KitActivity implements View.OnClickListener, ViewPager.OnPageChangeListener
{
	private class Holder
	{
		private ViewGroup tabCon;
	}

	private Holder holder = new Holder();

	private void initHolder()
	{
		holder.tabCon = findViewById(R.id.tabCon);
	}

	private ViewPager viewPager;
	private SimpleFragmentPagerAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_user);
		initHolder();
		requestPermission();
	}

	public boolean multi;
	public List<String> userIds;

	@Override
	public void permissionSuccess()
	{
		super.permissionSuccess();

		multi = getIntent().getBooleanExtra("multi", false);
		List<Integer> userTypes = AppDataTool.loadIntegerList(JsonHelper.getJSONArray(getIntent().getStringExtra("userTypes")));
		if (multi)
		{
			userIds = AppDataTool.loadStringList(JsonHelper.getJSONArray(getIntent().getStringExtra("userIds")));
		}

		findViewById(R.id.ok).setVisibility(multi ? View.VISIBLE : View.GONE);
		findViewById(R.id.ok).setOnClickListener(this);

		List<Fragment> fragments = new ArrayList<>();
		if (userTypes.contains(SUserType.Master))
			fragments.add(genFragment(SUserType.Master, "管理员"));
		if (userTypes.contains(SUserType.Teacher))
			fragments.add(genFragment(SUserType.Teacher, "老师"));
		if (userTypes.contains(SUserType.Student))
			fragments.add(genFragment(SUserType.Student, "学生"));

		viewPager = findViewById(R.id.viewPager);
		adapter = new SimpleFragmentPagerAdapter(getSupportFragmentManager(), fragments);
		viewPager.setAdapter(adapter);

		viewPager.addOnPageChangeListener(this);

		viewPager.setCurrentItem(1);
	}

	@Override
	public void onClick(View view)
	{
		if (view.getId() == R.id.ok)
		{
			Intent intent = new Intent();
			intent.putExtra("userStr", AppDataTool.saveStringList(userIds).toString());
			setResult(Activity.RESULT_OK, intent);
			finish();
		}
	}

	private Fragment genFragment(int userType, String title)
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

		FragmentSelectUser fragment = new FragmentSelectUser();
		fragment.owner = this;
		Bundle bundle = new Bundle();
		bundle.putInt("userType", userType);
		fragment.setArguments(bundle);
		return fragment;
	}

	private void setSelectedFlag(int index)
	{
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

	public void select(SUser user)
	{
		Intent intent = new Intent();
		intent.putExtra("userStr", user.saveToStr());
		setResult(Activity.RESULT_OK, intent);
		finish();
	}

}
