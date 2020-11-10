package com.lys.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lys.App;
import com.lys.adapter.AdapterTaskAnalyze;
import com.lys.app.R;
import com.lys.base.utils.AppDataTool;
import com.lys.base.utils.CommonUtils;
import com.lys.base.utils.JsonHelper;
import com.lys.base.utils.LOG;
import com.lys.config.AppConfig;
import com.lys.kit.activity.KitActivity;
import com.lys.kit.utils.Protocol;
import com.lys.protobuf.SEvent;
import com.lys.protobuf.SHandleId;
import com.lys.protobuf.SRequest_GetEventList;
import com.lys.protobuf.SResponse_GetEventList;
import com.lys.protobuf.SUser;
import com.lys.utils.UserCacheManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityTaskAnalyze extends KitActivity implements View.OnClickListener
{
	private class Holder
	{
		private TextView title;
		private ViewGroup timeCon;

		private TextView msg;
	}

	private Holder holder = new Holder();

	private void initHolder()
	{
		holder.title = findViewById(R.id.title);
		holder.timeCon = findViewById(R.id.timeCon);

		holder.msg = findViewById(R.id.msg);
	}

	private String taskName;
	private List<String> userIds;
	private List<String> taskIds;

	private RecyclerView recyclerView;
	private AdapterTaskAnalyze adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_task_analyze);
		initHolder();
		requestPermission();
	}

	@Override
	public void permissionSuccess()
	{
		super.permissionSuccess();

		taskName = getIntent().getStringExtra("taskName");
		userIds = AppDataTool.loadStringList(JsonHelper.getJSONArray(getIntent().getStringExtra("userIds")));
		taskIds = AppDataTool.loadStringList(JsonHelper.getJSONArray(getIntent().getStringExtra("taskIds")));

		holder.title.setText(String.format("%s(%s个)", taskName, userIds.size()));

		findViewById(R.id.flush).setOnClickListener(this);

		recyclerView = findViewById(R.id.recyclerView);
//		recyclerView.setLayoutManager(new GridLayoutManager(context, 4));
		recyclerView.setLayoutManager(new LinearLayoutManager(context));
		adapter = new AdapterTaskAnalyze(this);
		recyclerView.setAdapter(adapter);

//		adapter.setData(userIds);
		requestUser();
	}

	@Override
	public void onClick(View view)
	{
		if (view.getId() == R.id.flush)
		{
			requestEvent();
		}
	}

	private List<SUser> userList = new ArrayList<>();

	private void requestUser()
	{
		userList.clear();
		requestUser(0);
	}

	private void requestUser(final int index)
	{
		if (index < userIds.size())
		{
			holder.msg.setText(String.format("加载用户（%s/%s）", index + 1, userIds.size()));
			UserCacheManager.instance().getUser(userIds.get(index), new UserCacheManager.OnResult()
			{
				@Override
				public void result(SUser user)
				{
					if (user != null)
					{
						userList.add(user);
						requestUser(index + 1);
					}
					else
					{
						holder.msg.setText(String.format("加载用户 %s 失败", userIds.get(index)));
					}
				}
			});
		}
		else
		{
			requestEvent();
		}
	}

	public Map<String, List<SEvent>> eventMap = new HashMap<>();

	private void requestEvent()
	{
//		taskMap.clear();
		requestEvent(0);
	}

	private void requestEvent(final int index)
	{
		if (index < userIds.size())
		{
			holder.msg.setText(String.format("加载日志（%s/%s）", index + 1, userIds.size()));
			SRequest_GetEventList request = new SRequest_GetEventList();
			request.userId = userIds.get(index);
			request.actions.add(AppConfig.EventAction_InTask);
			request.actions.add(AppConfig.EventAction_OutTask);
			request.actions.add(AppConfig.EventAction_CommitJob);
			request.targets.add(taskIds.get(index));
			Protocol.doPost(context, App.getApi(), SHandleId.GetEventList, request.saveToStr(), new Protocol.OnCallback()
			{
				@Override
				public void onResponse(int code, String data, String msg)
				{
					if (code == 200)
					{
						SResponse_GetEventList response = SResponse_GetEventList.load(data);
						eventMap.put(userIds.get(index), response.events);
						requestEvent(index + 1);
					}
					else
					{
						holder.msg.setText(String.format("加载日志 %s 失败", userIds.get(index)));
					}
				}
			});
		}
		else
		{
			holder.msg.setText("");
			adapter.setData(userList);
			bindTime();
			LOG.toast(context, "刷新成功");
		}
	}

	private void addSpace(Context context, ViewGroup con, long time)
	{
		{
			View view = new View(context);
			LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
			layoutParams.weight = time;
			view.setLayoutParams(layoutParams);
//			view.setBackgroundColor(Color.YELLOW);
			con.addView(view);
		}
	}

	private static final SimpleDateFormat formatDate = new SimpleDateFormat("HH:mm");

	private void addTime(Context context, ViewGroup con, long timePoint)
	{
		if (true)
		{
			TextView textView = new TextView(context);
			LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			textView.setLayoutParams(layoutParams);
			textView.setTextSize(15);
			textView.setTextColor(0xff202122);
			textView.setText(formatDate.format(new Date(timePoint)));
			con.addView(textView);
		}
		else
		{
			View view = new View(context);
			LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(1, ViewGroup.LayoutParams.MATCH_PARENT);
			view.setLayoutParams(layoutParams);
			view.setBackgroundColor(Color.RED);
			con.addView(view);
		}
	}

	private final long[] spaces = new long[]{ //
			10 * 12 * 30 * 24 * 60 * 60 * 1000L, //
			9 * 12 * 30 * 24 * 60 * 60 * 1000L, //
			8 * 12 * 30 * 24 * 60 * 60 * 1000L, //
			7 * 12 * 30 * 24 * 60 * 60 * 1000L, //
			6 * 12 * 30 * 24 * 60 * 60 * 1000L, //
			5 * 12 * 30 * 24 * 60 * 60 * 1000L, //
			4 * 12 * 30 * 24 * 60 * 60 * 1000L, //
			3 * 12 * 30 * 24 * 60 * 60 * 1000L, //
			2 * 12 * 30 * 24 * 60 * 60 * 1000L, //
			12 * 30 * 24 * 60 * 60 * 1000L, //
			10 * 30 * 24 * 60 * 60 * 1000L, //
			8 * 30 * 24 * 60 * 60 * 1000L, //
			6 * 30 * 24 * 60 * 60 * 1000L, //
			4 * 30 * 24 * 60 * 60 * 1000L, //
			2 * 30 * 24 * 60 * 60 * 1000L, //
			30 * 24 * 60 * 60 * 1000L, //
			20 * 24 * 60 * 60 * 1000L, //
			10 * 24 * 60 * 60 * 1000L, //
			8 * 24 * 60 * 60 * 1000L, //
			4 * 24 * 60 * 60 * 1000L, //
			2 * 24 * 60 * 60 * 1000L, //
			24 * 60 * 60 * 1000L, //
			12 * 60 * 60 * 1000L, //
			6 * 60 * 60 * 1000L, //
			4 * 60 * 60 * 1000L, //
			2 * 60 * 60 * 1000L, //
			1 * 60 * 60 * 1000L, //
			1 * 30 * 60 * 1000L, //
			1 * 20 * 60 * 1000L, //
			1 * 10 * 60 * 1000L, //
			1 * 5 * 60 * 1000L, //
			1 * 3 * 60 * 1000L, //
			1 * 2 * 60 * 1000L, //
			1 * 1 * 60 * 1000L, //
			1 * 1 * 30 * 1000L, //
			1 * 1 * 20 * 1000L, //
			1 * 1 * 10 * 1000L, //
			1 * 1 * 5 * 1000L, //
			1 * 1 * 3 * 1000L, //
			1 * 1 * 2 * 1000L, //
			1 * 1 * 1 * 1000L, //
			1 * 1 * 1 * 500L, //
			1 * 1 * 1 * 300L, //
			1 * 1 * 1 * 200L, //
			1 * 1 * 1 * 100L, //
			1 * 1 * 1 * 50L, //
			1 * 1 * 1 * 30L, //
			1 * 1 * 1 * 20L, //
			1 * 1 * 1 * 10L};

	private void bindTime()
	{
		holder.timeCon.removeAllViews();

		long timeLen = adapter.maxTime - adapter.minTime;
		if (timeLen > 1000)
		{
			final int timeCount = 4;

			LOG.v("adapter.minTime = " + formatDate.format(new Date(adapter.minTime)));
			LOG.v("adapter.maxTime = " + formatDate.format(new Date(adapter.maxTime)));

			LOG.v(String.format("timeLen = %s(%s)", timeLen, CommonUtils.formatTime(timeLen)));

			long space = 0;
			long count = 0;
			for (int i = 0; i < spaces.length; i++)
			{
//				LOG.v("do");
				space = spaces[i];
				count = timeLen / space;
				if (count >= timeCount)
					break;
			}

			LOG.v(String.format("count = %s, space = %s(%s)", count, space, CommonUtils.formatTime(space)));

			long timePos = adapter.minTime;

			long time = timePos + (space / 2);
			time /= space;
			time *= space;

			do
			{
				addSpace(context, holder.timeCon, time - timePos);
				timePos = time;
				addTime(context, holder.timeCon, time);
				LOG.v("addTime = " + formatDate.format(new Date(time)));
				time += space;
			} while (time < adapter.maxTime);

			addSpace(context, holder.timeCon, adapter.maxTime - timePos);
		}
	}

}
