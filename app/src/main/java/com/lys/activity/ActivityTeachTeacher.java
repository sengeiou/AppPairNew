package com.lys.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.lys.App;
import com.lys.adapter.AdapterTeachTeacher;
import com.lys.app.R;
import com.lys.base.activity.BaseActivity;
import com.lys.kit.utils.Protocol;
import com.lys.protobuf.SHandleId;
import com.lys.protobuf.SRequest_GetTeachList;
import com.lys.protobuf.SResponse_GetTeachList;
import com.lys.protobuf.STeach;
import com.lys.protobuf.STeachBlock;
import com.lys.protobuf.STeachFlag;
import com.lys.protobuf.STeachLine;
import com.lys.protobuf.SUser;
import com.lys.protobuf.SUserType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class ActivityTeachTeacher extends AppActivity implements View.OnClickListener
{
	private class Holder
	{
		private TextView title;
		private ViewGroup timeCon;

		private RadioButton modeNone;
		private RadioButton modeFree;
		private RadioButton modeUse;
		private RadioButton modeOver;

		private TextView student;
		private CheckBox onlyShow;

		private TextView currTime;
	}

	private Holder holder = new Holder();

	private void initHolder()
	{
		holder.title = findViewById(R.id.title);
		holder.timeCon = findViewById(R.id.timeCon);

		holder.modeNone = findViewById(R.id.modeNone);
		holder.modeFree = findViewById(R.id.modeFree);
		holder.modeUse = findViewById(R.id.modeUse);
		holder.modeOver = findViewById(R.id.modeOver);

		holder.student = findViewById(R.id.student);
		holder.onlyShow = findViewById(R.id.onlyShow);

		holder.currTime = findViewById(R.id.currTime);
	}

	public boolean modeIsFree()
	{
		return holder.modeFree.isChecked();
	}

	public boolean modeIsUse()
	{
		return holder.modeUse.isChecked();
	}

	public boolean modeIsOver()
	{
		return holder.modeOver.isChecked();
	}

	public boolean onlyShow()
	{
		return holder.onlyShow.isChecked();
	}

	public SUser teacher = null;

	private RecyclerView recyclerView;
	private AdapterTeachTeacher adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_teach_teacher);
		initHolder();
		requestPermission();
	}

	@Override
	public void permissionSuccess()
	{
		super.permissionSuccess();

		teacher = SUser.load(getIntent().getStringExtra("teacher"));

		findViewById(R.id.currTime).setOnClickListener(this);
		findViewById(R.id.prev).setOnClickListener(this);
		findViewById(R.id.next).setOnClickListener(this);
		findViewById(R.id.student).setOnClickListener(this);

		recyclerView = findViewById(R.id.recyclerView);
		recyclerView.setLayoutManager(new LinearLayoutManager(context));
		adapter = new AdapterTeachTeacher(this);
		recyclerView.setAdapter(adapter);

		holder.onlyShow.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
		{
			@Override
			public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked)
			{
				adapter.notifyDataSetChanged();
			}
		});

		requestTeach();
	}

	public SUser currStudent = null;

	@Override
	public void onClick(View view)
	{
		if (view.getId() == R.id.currTime)
		{
		}
		else if (view.getId() == R.id.prev)
		{
//			if (currTime > 0)
//			{
//				requestTeacher(currTime - 24 * 3600 * 1000);
//			}
		}
		else if (view.getId() == R.id.next)
		{
//			if (currTime > 0)
//			{
//				requestTeacher(currTime + 24 * 3600 * 1000);
//			}
		}
		else if (view.getId() == R.id.student)
		{
			selectUser(new BaseActivity.OnImageListener()
			{
				@Override
				public void onResult(String userStr)
				{
					currStudent = SUser.load(userStr);
					holder.student.setText(currStudent.name);
					adapter.notifyDataSetChanged();
				}
			}, SUserType.Student);
		}
	}

	public static final int blockBegin = 6 * 2;
	public static final int blockEnd = 24 * 2;

	public void requestTeach()
	{
		SRequest_GetTeachList request = new SRequest_GetTeachList();
		request.teacherId = teacher.id;
		Protocol.doPost(context, App.getApi(), SHandleId.GetTeachList, request.saveToStr(), new Protocol.OnCallback()
		{
			@Override
			public void onResponse(int code, String data, String msg)
			{
				if (code == 200)
				{
					SResponse_GetTeachList response = SResponse_GetTeachList.load(data);

					HashMap<String, STeach> mapDate = new HashMap<>();
					for (STeach teach : response.teachs)
					{
						String key = String.format("%04d-%02d-%02d", teach.year, teach.month, teach.day);
						mapDate.put(key, teach);
					}

					ArrayList<String> keyList = new ArrayList<>(mapDate.keySet());
					Collections.sort(keyList, new Comparator<String>()
					{
						@Override
						public int compare(String key1, String key2)
						{
							return key1.compareTo(key2);
						}
					});

					List<STeachLine> teachLines = new ArrayList<>();
					for (String key : keyList)
					{
						STeach teach = mapDate.get(key);

						STeachLine teachLine = new STeachLine();
						teachLine.year = teach.year;
						teachLine.month = teach.month;
						teachLine.day = teach.day;
						for (int i = blockBegin; i < blockEnd; i++)
						{
							STeachBlock block = new STeachBlock();
							block.block = i;
							block.flag = STeachFlag.None;
							block.studentId = "";
							teachLine.blocks.add(block);
						}
						teachLines.add(teachLine);
					}

					HashMap<String, STeachLine> map = new HashMap<>();
					for (STeachLine teachLine : teachLines)
					{
						String key = String.format("%04d-%02d-%02d", teachLine.year, teachLine.month, teachLine.day);
						map.put(key, teachLine);
					}

					for (STeach teach : response.teachs)
					{
						String key = String.format("%04d-%02d-%02d", teach.year, teach.month, teach.day);
						if (map.containsKey(key))
						{
							STeachLine teachLine = map.get(key);
							int index = teach.block - blockBegin;
							if (index >= 0 && index < teachLine.blocks.size())
							{
								STeachBlock block = teachLine.blocks.get(index);
								block.flag = teach.flag;
								block.studentId = teach.studentId;
							}
						}
					}

					adapter.setData(teachLines);
					bindTime();
				}
			}
		});
	}

	private void addTime(Context context, ViewGroup con, int block)
	{
		TextView view = new TextView(context);
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
		layoutParams.weight = 1;
		view.setLayoutParams(layoutParams);
		view.setTextSize(15);
		view.setTextColor(0xff202122);
		view.setText(String.format("%d:00", block / 2 % 12 + 1));
//		view.setText(String.valueOf(block));
		view.setGravity(Gravity.CENTER);
		if (block % 4 == 0)
			view.setBackgroundColor(0xfff5f5dc);
		else
			view.setBackgroundColor(0xfff0ffff);
		con.addView(view);
	}

	private void bindTime()
	{
		holder.currTime.setText(teacher.name);

		holder.timeCon.removeAllViews();

		for (int i = blockBegin; i < blockEnd; i++)
		{
			if (i % 2 == 0)
				addTime(context, holder.timeCon, i);
		}
	}

}
