package com.lys.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.lys.App;
import com.lys.adapter.AdapterTeachPageDetail;
import com.lys.adapter.AdapterTeachRecord;
import com.lys.app.R;
import com.lys.base.utils.LOG;
import com.lys.config.AppConfig;
import com.lys.dialog.DialogSelectDate;
import com.lys.kit.dialog.DialogAlert;
import com.lys.kit.utils.Protocol;
import com.lys.protobuf.SHandleId;
import com.lys.protobuf.SRequest_TeachGetList;
import com.lys.protobuf.SResponse_TeachGetList;
import com.lys.protobuf.STeachRecord;
import com.lys.protobuf.SUser;
import com.lys.protobuf.SUserType;
import com.lys.utils.UserCacheManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class ActivityTeachRecord extends AppActivity implements View.OnClickListener
{
	private class Holder
	{
		private TextView title;
		private TextView fromTime;
		private TextView toTime;
		private TextView search;
	}

	private Holder holder = new Holder();

	private void initHolder()
	{
		holder.title = findViewById(R.id.title);
		holder.fromTime = findViewById(R.id.fromTime);
		holder.toTime = findViewById(R.id.toTime);
		holder.search = findViewById(R.id.search);
	}

	private String userId;

	private RecyclerView recyclerView;
	private AdapterTeachRecord adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_teach_record);
		initHolder();

		userId = getIntent().getStringExtra("userId");

		holder.title.setText("");
		UserCacheManager.instance().getUser(userId, new UserCacheManager.OnResult()
		{
			@Override
			public void result(SUser user)
			{
				if (user != null)
				{
					holder.title.setText(String.format("上课记录（%s）", user.name));
				}
			}
		});

		findViewById(R.id.fromTime).setOnClickListener(this);
		findViewById(R.id.toTime).setOnClickListener(this);
		findViewById(R.id.search).setOnClickListener(this);
		findViewById(R.id.tongji).setOnClickListener(this);

		recyclerView = findViewById(R.id.recyclerView);
		recyclerView.setLayoutManager(new LinearLayoutManager(context));
		adapter = new AdapterTeachRecord(this);
		recyclerView.setAdapter(adapter);

		long currTime = System.currentTimeMillis();
		fromTime = dayBeginTime(currTime) - 7 * 24 * 3600 * 1000;
		toTime = dayEndTime(currTime);

		holder.fromTime.setText(formatDate.format(new Date(fromTime)));
		holder.toTime.setText(formatDate.format(new Date(toTime)));

		request();
	}

	private static final SimpleDateFormat formatDate = new SimpleDateFormat("yyyy年MM月dd日");

	private long fromTime = 0;
	private long toTime = 0;

//	public SUser currStudent = null;

	private long dayBeginTime(long time)
	{
		Date dateSrc = new Date(time);
		Date date = new Date();
		date.setYear(dateSrc.getYear());
		date.setMonth(dateSrc.getMonth());
		date.setDate(dateSrc.getDate());
		date.setHours(0);
		date.setMinutes(0);
		date.setSeconds(0);
		return date.getTime();
	}

	private long dayEndTime(long time)
	{
		Date dateSrc = new Date(time);
		Date date = new Date();
		date.setYear(dateSrc.getYear());
		date.setMonth(dateSrc.getMonth());
		date.setDate(dateSrc.getDate());
		date.setHours(23);
		date.setMinutes(59);
		date.setSeconds(59);
		return date.getTime();
	}

	@Override
	public void onClick(View view)
	{
		if (view.getId() == R.id.fromTime)
		{
			DialogSelectDate.show(context, fromTime, new DialogSelectDate.OnListener()
			{
				@Override
				public void onResult(long time)
				{
					fromTime = dayBeginTime(time);
					holder.fromTime.setText(formatDate.format(new Date(fromTime)));
					request();
				}
			});
		}
		else if (view.getId() == R.id.toTime)
		{
			DialogSelectDate.show(context, toTime, new DialogSelectDate.OnListener()
			{
				@Override
				public void onResult(long time)
				{
					toTime = dayEndTime(time);
					holder.toTime.setText(formatDate.format(new Date(toTime)));
					request();
				}
			});
		}
		else if (view.getId() == R.id.search)
		{
			request();
		}
		else if (view.getId() == R.id.tongji)
		{
			UserCacheManager.instance().getUser(userId, new UserCacheManager.OnResult()
			{
				@Override
				public void result(SUser user)
				{
					if (user != null)
					{
						showTongji(user);
					}
				}
			});
		}
	}

	private void request()
	{
		SRequest_TeachGetList request = new SRequest_TeachGetList();
		request.userId = userId;
		request.fromTime = fromTime;
		request.toTime = toTime;
		Protocol.doPost(context, App.getApi(), SHandleId.TeachGetList, request.saveToStr(), new Protocol.OnCallback()
		{
			@Override
			public void onResponse(int code, String data, String msg)
			{
				if (code == 200)
				{
					SResponse_TeachGetList response = SResponse_TeachGetList.load(data);
					adapter.setData(response.teachRecords);
				}
			}
		});
	}

	//-------------------------------------- 统计（开始） --------------------------------------

	public interface OnLoadTargetOver
	{
		void over(List<String> failIds);
	}

	private void loadTargets(final List<STeachRecord> teachRecords, final OnLoadTargetOver callback)
	{
		List<String> failIds = new ArrayList<>();
		loadTargets(teachRecords, failIds, 0, callback);
	}

	private void loadTargets(final List<STeachRecord> teachRecords, final List<String> failIds, final int index, final OnLoadTargetOver callback)
	{
		if (index < teachRecords.size())
		{
			STeachRecord teachRecord = teachRecords.get(index);
			teachRecord.targets.clear();
			loadTargets(teachRecord, failIds, 0, new OnLoadTargetOver()
			{
				@Override
				public void over(List<String> failIds)
				{
					loadTargets(teachRecords, failIds, index + 1, callback);
				}
			});
		}
		else
		{
			if (callback != null)
				callback.over(failIds);
		}
	}

	private void loadTargets(final STeachRecord teachRecord, final List<String> failIds, final int index, final OnLoadTargetOver callback)
	{
		if (index < teachRecord.targetIds.size())
		{
			UserCacheManager.instance().getUser(teachRecord.targetIds.get(index), new UserCacheManager.OnResult()
			{
				@Override
				public void result(SUser user)
				{
					if (user != null)
						teachRecord.targets.add(user);
					else
						failIds.add(teachRecord.targetIds.get(index));
					loadTargets(teachRecord, failIds, index + 1, callback);
				}
			});
		}
		else
		{
			if (callback != null)
				callback.over(failIds);
		}
	}

	private void showTongji(final SUser user)
	{
		if (adapter.teachRecords == null)
		{
			LOG.toast(context, "数据未加载");
			return;
		}

		long dtTime = dayBeginTime(toTime) - dayBeginTime(fromTime);
		if (dtTime < 0)
		{
			LOG.toast(context, "选择的日期不正确");
			return;
		}
		final int dayCount = (int) (dtTime / (24 * 3600 * 1000)) + 1;

		loadTargets(adapter.teachRecords, new OnLoadTargetOver()
		{
			@Override
			public void over(List<String> failIds)
			{
				if (failIds.size() > 0)
					LOG.toast(context, String.format("%d 个用户信息加载失败", failIds.size()));
				showTongjiImpl(user, dayCount);
			}
		});
	}

	private void catchError(StringBuilder sbError, STeachRecord teachRecord, String errorInfo)
	{
		String targetNames = "";
		for (SUser target : teachRecord.targets)
		{
			if (!TextUtils.isEmpty(targetNames))
				targetNames += "，";
			targetNames += target.name;
		}
		String taskName = "";
		if (teachRecord.task != null)
			taskName = teachRecord.task.name;
		else
			taskName = "[任务不存在]";
		sbError.append(String.format("%s(%s)%s\r\n", targetNames, taskName, errorInfo));
	}

	private boolean hasError(StringBuilder sbError, STeachRecord teachRecord)
	{
		if (teachRecord.startTime <= 0 || teachRecord.overTime <= 0)
		{
			catchError(sbError, teachRecord, "统计时间异常！");
			return true;
		}
		if (teachRecord.targetIds.size() == 0)
		{
			catchError(sbError, teachRecord, "目标用户为空！");
			return true;
		}
		if (teachRecord.targetIds.size() != teachRecord.targets.size())
		{
			catchError(sbError, teachRecord, "获取目标用户失败！");
			return true;
		}
		return false;
	}

	private void showTongjiImpl(SUser user, int dayCount)
	{
		if (user.userType.equals(SUserType.Student))
		{
			showTongjiStudent(user, dayCount);
		}
		else
		{
			showTongjiTeacher(user, dayCount);
		}
	}

	private void showTongjiStudent(SUser user, int dayCount)
	{
		StringBuilder sbInfo = new StringBuilder();
		StringBuilder sbError = new StringBuilder();

		long allTimeLen = 0;
		HashMap<String, Integer> targetMap = new HashMap<>();
		HashMap<Integer, HashMap<String, Integer>> userTypeMap = new HashMap<>();

		for (STeachRecord teachRecord : adapter.teachRecords)
		{
			if (!teachRecord.isHost)
			{
				if (!hasError(sbError, teachRecord))
				{
					long timeLen = teachRecord.overTime - teachRecord.startTime;
					allTimeLen += timeLen;
					for (SUser target : teachRecord.targets)
					{
						if (targetMap.containsKey(target.id))
							targetMap.put(target.id, targetMap.get(target.id) + 1);
						else
							targetMap.put(target.id, 1);

						if (userTypeMap.containsKey(target.userType))
						{
							HashMap<String, Integer> userMap = userTypeMap.get(target.userType);
							userMap.put(target.id, 1);
						}
						else
						{
							HashMap<String, Integer> userMap = new HashMap<>();
							userMap.put(target.id, 1);
							userTypeMap.put(target.userType, userMap);
						}
					}
				}
			}
		}

		sbInfo.append(String.format("总课时：%s\r\n", AdapterTeachPageDetail.formatTime(allTimeLen)));
		sbInfo.append(String.format("\r\n"));

		sbInfo.append(String.format("天数：%s\r\n", dayCount));
		if (dayCount > 0)
			sbInfo.append(String.format("日均课时：%s\r\n", AdapterTeachPageDetail.formatTime(allTimeLen / dayCount)));
		sbInfo.append(String.format("\r\n"));

		sbInfo.append(String.format("老师人数：%s\r\n", targetMap.size()));
		if (targetMap.size() > 0)
			sbInfo.append(String.format("人均课时：%s\r\n", AdapterTeachPageDetail.formatTime(allTimeLen / targetMap.size())));
		sbInfo.append(String.format("\r\n"));

		if (userTypeMap.size() > 0)
		{
			sbInfo.append(String.format("用戶类型分布\r\n"));
			ArrayList<Integer> keyList = new ArrayList<>(userTypeMap.keySet());
			Collections.sort(keyList, new Comparator<Integer>()
			{
				@Override
				public int compare(Integer key1, Integer key2)
				{
					return key1.compareTo(key2);
				}
			});
			for (Integer key : keyList)
			{
				sbInfo.append(String.format("        %s ： %s 人\r\n", AppConfig.getUserTypeName(key), userTypeMap.get(key).size()));
			}
			sbInfo.append(String.format("\r\n"));
		}

		if (sbError.length() > 0)
		{
			sbInfo.append(String.format("错误信息：\r\n"));
			sbInfo.append(sbError);
		}

		DialogAlert.show(context, "统计结果", sbInfo.toString(), null);
	}

	private void showTongjiTeacher(SUser user, int dayCount)
	{
		StringBuilder sbInfo = new StringBuilder();
		StringBuilder sbError = new StringBuilder();

		long allTimeLen = 0;
		HashMap<String, Integer> targetMap = new HashMap<>();
		HashMap<Integer, HashMap<String, Integer>> gradeMap = new HashMap<>();
		HashMap<Integer, HashMap<String, Integer>> userTypeMap = new HashMap<>();

		for (STeachRecord teachRecord : adapter.teachRecords)
		{
			if (teachRecord.isHost)
			{
				if (!hasError(sbError, teachRecord))
				{
					long timeLen = teachRecord.overTime - teachRecord.startTime;
					allTimeLen += timeLen;
					for (SUser target : teachRecord.targets)
					{
						if (targetMap.containsKey(target.id))
							targetMap.put(target.id, targetMap.get(target.id) + 1);
						else
							targetMap.put(target.id, 1);

						if (gradeMap.containsKey(target.grade))
						{
							HashMap<String, Integer> userMap = gradeMap.get(target.grade);
							userMap.put(target.id, 1);
						}
						else
						{
							HashMap<String, Integer> userMap = new HashMap<>();
							userMap.put(target.id, 1);
							gradeMap.put(target.grade, userMap);
						}

						if (userTypeMap.containsKey(target.userType))
						{
							HashMap<String, Integer> userMap = userTypeMap.get(target.userType);
							userMap.put(target.id, 1);
						}
						else
						{
							HashMap<String, Integer> userMap = new HashMap<>();
							userMap.put(target.id, 1);
							userTypeMap.put(target.userType, userMap);
						}
					}
				}
			}
		}

		sbInfo.append(String.format("总课时：%s\r\n", AdapterTeachPageDetail.formatTime(allTimeLen)));
		sbInfo.append(String.format("\r\n"));

		sbInfo.append(String.format("天数：%s\r\n", dayCount));
		if (dayCount > 0)
			sbInfo.append(String.format("日均课时：%s\r\n", AdapterTeachPageDetail.formatTime(allTimeLen / dayCount)));
		sbInfo.append(String.format("\r\n"));

		sbInfo.append(String.format("学生人数：%s\r\n", targetMap.size()));
		if (targetMap.size() > 0)
			sbInfo.append(String.format("人均课时：%s\r\n", AdapterTeachPageDetail.formatTime(allTimeLen / targetMap.size())));
		sbInfo.append(String.format("\r\n"));

		if (gradeMap.size() > 0)
		{
			sbInfo.append(String.format("学生年级分布\r\n"));
			ArrayList<Integer> keyList = new ArrayList<>(gradeMap.keySet());
			Collections.sort(keyList, new Comparator<Integer>()
			{
				@Override
				public int compare(Integer key1, Integer key2)
				{
					return key1.compareTo(key2);
				}
			});
			for (Integer key : keyList)
			{
				sbInfo.append(String.format("        %s ： %s 人\r\n", AppConfig.getGradeName(key), gradeMap.get(key).size()));
			}
			sbInfo.append(String.format("\r\n"));
		}

		if (userTypeMap.size() > 0)
		{
			sbInfo.append(String.format("用戶类型分布\r\n"));
			ArrayList<Integer> keyList = new ArrayList<>(userTypeMap.keySet());
			Collections.sort(keyList, new Comparator<Integer>()
			{
				@Override
				public int compare(Integer key1, Integer key2)
				{
					return key1.compareTo(key2);
				}
			});
			for (Integer key : keyList)
			{
				sbInfo.append(String.format("        %s ： %s 人\r\n", AppConfig.getUserTypeName(key), userTypeMap.get(key).size()));
			}
			sbInfo.append(String.format("\r\n"));
		}

		if (sbError.length() > 0)
		{
			sbInfo.append(String.format("错误信息：\r\n"));
			sbInfo.append(sbError);
		}

		DialogAlert.show(context, "统计结果", sbInfo.toString(), null);
	}

	//-------------------------------------- 统计（结束） --------------------------------------

}
