package com.lys.kit.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lys.base.utils.FsUtils;
import com.lys.base.utils.SPHelper;
import com.lys.kit.R;
import com.lys.kit.adapter.SimpleFragmentPagerAdapter;
import com.lys.kit.fragment.FragmentSelectImage;
import com.lys.kit.utils.KitUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ActivitySelectImage extends KitActivity implements ViewPager.OnPageChangeListener
{
	public static final String SP_Key_selectImageIndex = "selectImageIndex";

	public static boolean isMovie(String path)
	{
		return path.toLowerCase().endsWith(".mp4");
	}

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
		setContentView(R.layout.activity_select_image);

		initHolder();

		types = getIntent().getStringExtra("types");

//		fragments.add(genFragment(String.format("%s/Screenshots", FsUtils.SD_CARD))); // isHra

		List<Fragment> fragments = new ArrayList<>();
		if (KitUtils.isD7())
			fragments.add(genFragment(String.format("%s/Screenshots", FsUtils.SD_CARD), "截图"));
		else
			fragments.add(genFragment(String.format("%s/Pictures/Screenshots", FsUtils.SD_CARD), "截图"));
		fragments.add(genFragment(String.format("%s/DCIM/Camera", FsUtils.SD_CARD), "相册"));
//		fragments.add(genFragment(String.format("%s/Blackboard", FsUtils.SD_CARD), "黑板"));
		fragments.add(genFragment(String.format("%s/tencent/MicroMsg/WeChat", FsUtils.SD_CARD), "WeChat"));
		fragments.add(genFragment(String.format("%s/tencent/MicroMsg/WeiXin", FsUtils.SD_CARD), "WeiXin"));

		viewPager = findViewById(R.id.viewPager);
		adapter = new SimpleFragmentPagerAdapter(getSupportFragmentManager(), fragments);
		viewPager.setAdapter(adapter);

		viewPager.addOnPageChangeListener(this);

		int index = SPHelper.getInt(context, SP_Key_selectImageIndex, 1);
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
		fragment = new FragmentSelectImage();
		Bundle bundle = new Bundle();
		bundle.putString("root", root);
		bundle.putString("types", types);
		fragment.setArguments(bundle);
		return fragment;
	}

	private void setSelectedFlag(int index)
	{
		SPHelper.putInt(context, SP_Key_selectImageIndex, index);

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

	public void select(File image)
	{
		Intent intent = new Intent();
		intent.putExtra("path", image.getAbsolutePath());
		setResult(Activity.RESULT_OK, intent);
		finish();
	}
}
