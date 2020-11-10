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

import com.github.promeg.pinyinhelper.Pinyin;
import com.lys.App;
import com.lys.adapter.AdapterTeach;
import com.lys.app.R;
import com.lys.base.activity.BaseActivity;
import com.lys.dialog.DialogSelectDate;
import com.lys.kit.utils.Protocol;
import com.lys.protobuf.SHandleId;
import com.lys.protobuf.SRequest_GetTeachList;
import com.lys.protobuf.SRequest_GetUserList;
import com.lys.protobuf.SResponse_GetTeachList;
import com.lys.protobuf.SResponse_GetUserList;
import com.lys.protobuf.STeach;
import com.lys.protobuf.STeachBlock;
import com.lys.protobuf.STeachFlag;
import com.lys.protobuf.STeachLine;
import com.lys.protobuf.SUser;
import com.lys.protobuf.SUserType;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class ActivityTeach extends AppActivity implements View.OnClickListener
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

	private RecyclerView recyclerView;
	private AdapterTeach adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_teach);
		initHolder();
		requestPermission();
	}

	@Override
	public void permissionSuccess()
	{
		super.permissionSuccess();

		findViewById(R.id.currTime).setOnClickListener(this);
		findViewById(R.id.prev).setOnClickListener(this);
		findViewById(R.id.next).setOnClickListener(this);
		findViewById(R.id.student).setOnClickListener(this);

		recyclerView = findViewById(R.id.recyclerView);
		recyclerView.setLayoutManager(new LinearLayoutManager(context));
		adapter = new AdapterTeach(this);
		recyclerView.setAdapter(adapter);

		holder.onlyShow.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
		{
			@Override
			public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked)
			{
				adapter.notifyDataSetChanged();
			}
		});

		currTime = System.currentTimeMillis();
	}

	@Override
	protected void onResume()
	{
		super.onResume();
		requestTeacher(currTime);
	}

	public SUser currStudent = null;

	@Override
	public void onClick(View view)
	{
		if (view.getId() == R.id.currTime)
		{
			DialogSelectDate.show(context, currTime, new DialogSelectDate.OnListener()
			{
				@Override
				public void onResult(long time)
				{
					requestTeacher(time);
				}
			});
		}
		else if (view.getId() == R.id.prev)
		{
			if (currTime > 0)
			{
				requestTeacher(currTime - 24 * 3600 * 1000);
			}
		}
		else if (view.getId() == R.id.next)
		{
			if (currTime > 0)
			{
				requestTeacher(currTime + 24 * 3600 * 1000);
			}
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

	public long currTime = 0;

	public static final int blockBegin = 6 * 2;
	public static final int blockEnd = 24 * 2;

	public void requestTeacher(final long time)
	{
		SRequest_GetUserList request = new SRequest_GetUserList();
		request.userType = SUserType.Teacher;
		Protocol.doPost(context, App.getApi(), SHandleId.GetUserList, request.saveToStr(), new Protocol.OnCallback()
		{
			@Override
			public void onResponse(int code, String data, String msg)
			{
				if (code == 200)
				{
					SResponse_GetUserList response = SResponse_GetUserList.load(data);
					Collections.sort(response.users, new Comparator<SUser>()
					{
						@Override
						public int compare(SUser user1, SUser user2)
						{
							String pinyin1 = Pinyin.toPinyin(user1.name, "");
							String pinyin2 = Pinyin.toPinyin(user2.name, "");
							return pinyin1.compareTo(pinyin2);
						}
					});
					List<STeachLine> teachLines = new ArrayList<>();
					for (SUser user : response.users)
					{
						STeachLine teachLine = new STeachLine();
						teachLine.teacher = user;
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
					requestTeach(time, teachLines);
				}
			}
		});
	}

	private void requestTeach(final long time, final List<STeachLine> teachLines)
	{
		Date date = new Date(time);
		SRequest_GetTeachList request = new SRequest_GetTeachList();
		request.year = date.getYear() + 1900;
		request.month = date.getMonth() + 1;
		request.day = date.getDate();
		Protocol.doPost(context, App.getApi(), SHandleId.GetTeachList, request.saveToStr(), new Protocol.OnCallback()
		{
			@Override
			public void onResponse(int code, String data, String msg)
			{
				if (code == 200)
				{
					SResponse_GetTeachList response = SResponse_GetTeachList.load(data);

					HashMap<String, STeachLine> map = new HashMap<>();
					for (STeachLine teachLine : teachLines)
					{
						map.put(teachLine.teacher.id, teachLine);
					}

					for (STeach teach : response.teachs)
					{
						if (map.containsKey(teach.teacherId))
						{
							STeachLine teachLine = map.get(teach.teacherId);
							int index = teach.block - blockBegin;
							if (index >= 0 && index < teachLine.blocks.size())
							{
								STeachBlock block = teachLine.blocks.get(index);
								block.flag = teach.flag;
								block.studentId = teach.studentId;
							}
						}
					}

					currTime = time;

					adapter.setData(teachLines);
					bindTime();
				}
			}
		});
	}

	private static final SimpleDateFormat formatDate = new SimpleDateFormat("MM月dd日");

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
		holder.currTime.setText(formatDate.format(new Date(currTime)));

		holder.timeCon.removeAllViews();

		for (int i = blockBegin; i < blockEnd; i++)
		{
			if (i % 2 == 0)
				addTime(context, holder.timeCon, i);
		}
	}

}
