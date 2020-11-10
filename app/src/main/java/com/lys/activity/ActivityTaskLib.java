package com.lys.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.lys.App;
import com.lys.adapter.AdapterTaskLib;
import com.lys.app.R;
import com.lys.dialog.DialogCreateTask;
import com.lys.kit.activity.KitActivity;
import com.lys.kit.utils.Protocol;
import com.lys.protobuf.SHandleId;
import com.lys.protobuf.SPJobType;
import com.lys.protobuf.SPTaskType;
import com.lys.protobuf.SRequest_CreateTask;
import com.lys.protobuf.SResponse_CreateTask;
import com.lys.utils.TaskHelper;
import com.lys.utils.TaskTreeNode;

import java.util.List;

public class ActivityTaskLib extends KitActivity implements View.OnClickListener
{
	private class Holder
	{
		private EditText keyword;
		private TextView count;
	}

	private Holder holder = new Holder();

	private void initHolder()
	{
		holder.keyword = findViewById(R.id.keyword);
		holder.count = findViewById(R.id.count);
	}

	public String userId;

	private RecyclerView recyclerView;
	private AdapterTaskLib adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_task_lib);
		initHolder();
		requestPermission();
	}

	@Override
	public void permissionSuccess()
	{
		super.permissionSuccess();

		userId = getIntent().getStringExtra("userId");

		findViewById(R.id.add).setOnClickListener(this);

		recyclerView = findViewById(R.id.recyclerView);
		recyclerView.setLayoutManager(new LinearLayoutManager(context));
		adapter = new AdapterTaskLib(this);
		recyclerView.setAdapter(adapter);

		holder.keyword.addTextChangedListener(new TextWatcher()
		{
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after)
			{
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count)
			{
				adapter.setFilterText(s.toString());
				holder.count.setText(String.valueOf(adapter.getShowTaskCount()));
			}

			@Override
			public void afterTextChanged(Editable s)
			{
			}
		});

		request();
	}

	@Override
	public void onClick(View view)
	{
		if (view.getId() == R.id.add)
		{
			add("默认分组");
		}
	}

	public void request()
	{
		TaskHelper.requestTaskTree(context, userId, new TaskHelper.onTaskTreeCallback()
		{
			@Override
			public void onResult(List<TaskTreeNode> treeNodes)
			{
				if (treeNodes != null)
				{
					adapter.setData(treeNodes);
					holder.count.setText(String.valueOf(adapter.getShowTaskCount()));
				}
			}
		});
	}

	public void add(final String groupText)
	{
		DialogCreateTask.show(context, groupText, null, SPTaskType.None, SPJobType.None, new DialogCreateTask.OnListener()
		{
			@Override
			public void onResult(String group, String name, int taskType, int jobType)
			{
				SRequest_CreateTask request = new SRequest_CreateTask();
				request.userId = userId;
				request.name = name;
				request.group = group;
				request.type = taskType;
				request.jobType = jobType;
				Protocol.doPost(context, App.getApi(), SHandleId.CreateTask, request.saveToStr(), new Protocol.OnCallback()
				{
					@Override
					public void onResponse(int code, String data, String msg)
					{
						if (code == 200)
						{
							SResponse_CreateTask response = SResponse_CreateTask.load(data);
							TaskHelper.addTaskNode(adapter.treeNodes, response.task);
							adapter.notifyDataSetChanged();
						}
					}
				});
			}
		});
	}

}
