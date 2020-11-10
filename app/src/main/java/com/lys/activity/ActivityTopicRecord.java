package com.lys.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.lys.App;
import com.lys.adapter.AdapterTopicRecord;
import com.lys.app.R;
import com.lys.base.utils.LOG;
import com.lys.kit.activity.KitActivity;
import com.lys.kit.utils.Protocol;
import com.lys.protobuf.SHandleId;
import com.lys.protobuf.SRequest_TopicRecordGetList;
import com.lys.protobuf.SResponse_TopicRecordGetList;
import com.lys.protobuf.STopicRecord;

public class ActivityTopicRecord extends KitActivity
{
	private class Holder
	{
		private TextView title;
	}

	private Holder holder = new Holder();

	private void initHolder()
	{
		holder.title = findViewById(R.id.title);
	}

	private int mType;

	private RecyclerView recyclerView;
	private AdapterTopicRecord adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_topic_record);
		initHolder();
		requestPermission();
	}

	@Override
	public void permissionSuccess()
	{
		super.permissionSuccess();

		mType = getIntent().getIntExtra("type", 0);

		if (mType == 1)
			holder.title.setText("作答收藏");
		else if (mType == 2)
			holder.title.setText("作答记录");
		else if (mType == 3)
			holder.title.setText("错题本");

		recyclerView = findViewById(R.id.recyclerView);
		recyclerView.setLayoutManager(new LinearLayoutManager(context));
		adapter = new AdapterTopicRecord(this);
		recyclerView.setAdapter(adapter);

		recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener()
		{
			@Override
			public void onScrollStateChanged(RecyclerView recyclerView, int newState)
			{
				super.onScrollStateChanged(recyclerView, newState);
				if (newState == RecyclerView.SCROLL_STATE_IDLE)
				{
					LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
					if (layoutManager.findLastVisibleItemPosition() == recyclerView.getLayoutManager().getItemCount() - 1)
					{
						LOG.v("到底了");
						request();
					}
				}
			}
		});

		request();
	}

	private static final int PageSize = 10;

	public void request()
	{
		if (adapter.state == AdapterTopicRecord.State_NotMore || adapter.state == AdapterTopicRecord.State_Loading)
			return;
		adapter.setState(AdapterTopicRecord.State_Loading, "加载中。。。");
		SRequest_TopicRecordGetList request = new SRequest_TopicRecordGetList();
		request.userId = App.userId();
		request.type = mType;
		if (adapter.getLast() != null)
			request.time = adapter.getLast().time;
		request.pageSize = PageSize;
		Protocol.doPost(context, App.getApi(), SHandleId.TopicRecordGetList, request.saveToStr(), new Protocol.OnCallback()
		{
			@Override
			public void onResponse(int code, String data, String msg)
			{
				if (code == 200)
				{
					SResponse_TopicRecordGetList response = SResponse_TopicRecordGetList.load(data);
					if (response.topicRecords.size() > 0)
					{
						adapter.setState(AdapterTopicRecord.State_Normal, "");
						adapter.addData(response.topicRecords);
					}
					else
					{
						adapter.setState(AdapterTopicRecord.State_NotMore, "---已经到底了---");
//						LOG.toast(context, "已经到底了");
					}
				}
				else
				{
					adapter.setState(AdapterTopicRecord.State_LoadFail, "加载失败");
					LOG.toast(context, "加载失败");
				}
			}
		});
	}

	public void goin(STopicRecord topicRecord)
	{
		Intent intent = new Intent(context, ActivityTopicWatch.class);
		intent.putExtra("type", mType);
		intent.putExtra("topicRecord", topicRecord.saveToStr());
		context.startActivity(intent);
	}

}
