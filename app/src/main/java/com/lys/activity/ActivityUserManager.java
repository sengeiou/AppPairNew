package com.lys.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lys.App;
import com.lys.app.R;
import com.lys.base.utils.LOG;
import com.lys.dialog.DialogAddUser;
import com.lys.fragment.FragmentUserManager;
import com.lys.kit.activity.KitActivity;
import com.lys.kit.adapter.SimpleFragmentPagerAdapter;
import com.lys.kit.utils.Protocol;
import com.lys.protobuf.SHandleId;
import com.lys.protobuf.SRequest_AddUser;
import com.lys.protobuf.SResponse_AddUser;
import com.lys.protobuf.SUserType;

import java.util.ArrayList;
import java.util.List;

public class ActivityUserManager extends KitActivity implements View.OnClickListener, ViewPager.OnPageChangeListener
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
		setContentView(R.layout.activity_user_manager);
		initHolder();
		requestPermission();
	}

	@Override
	public void permissionSuccess()
	{
		super.permissionSuccess();

		findViewById(R.id.tools).setOnClickListener(this);
		findViewById(R.id.teach).setOnClickListener(this);
		findViewById(R.id.addUser).setOnClickListener(this);

		List<Fragment> fragments = new ArrayList<>();
		fragments.add(genFragment(SUserType.Master, "管理员"));
		fragments.add(genFragment(SUserType.Teacher, "老师"));
		fragments.add(genFragment(SUserType.Student, "学生"));

		viewPager = findViewById(R.id.viewPager);
		adapter = new SimpleFragmentPagerAdapter(getSupportFragmentManager(), fragments);
		viewPager.setAdapter(adapter);

		viewPager.addOnPageChangeListener(this);

		viewPager.setCurrentItem(0);
		setSelectedFlag(0);
	}

	@Override
	public void onClick(View view)
	{
		if (view.getId() == R.id.tools)
		{
			Intent intent = new Intent(context, ActivityMasterTools.class);
			startActivity(intent);
		}
		else if (view.getId() == R.id.teach)
		{
			Intent intent = new Intent(context, ActivityTeach.class);
			startActivity(intent);
		}
		else if (view.getId() == R.id.addUser)
		{
			addUser();
		}
	}

	private void addUser()
	{
		DialogAddUser.show(context, new DialogAddUser.OnAddUserListener()
		{
			@Override
			public void onResult(final SRequest_AddUser request)
			{
				Protocol.doPost(context, App.getApi(), SHandleId.AddUser, request.saveToStr(), new Protocol.OnCallback()
				{
					@Override
					public void onResponse(int code, String data, String msg)
					{
						if (code == 200)
						{
							SResponse_AddUser response = SResponse_AddUser.load(data);
							LOG.toast(context, "添加成功");
							if (request.userType.equals(SUserType.Master))
							{
								FragmentUserManager fragment = (FragmentUserManager) adapter.fragments.get(0);
								fragment.request();
							}
							else if (request.userType.equals(SUserType.Teacher))
							{
								FragmentUserManager fragment = (FragmentUserManager) adapter.fragments.get(1);
								fragment.request();
							}
							else if (request.userType.equals(SUserType.Student))
							{
								FragmentUserManager fragment = (FragmentUserManager) adapter.fragments.get(2);
								fragment.request();
							}
						}
					}
				});
			}
		});
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

		FragmentUserManager fragment = new FragmentUserManager();
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

}
