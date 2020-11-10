package com.lys.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.lys.adapter.AdapterSelectTask;
import com.lys.app.R;
import com.lys.base.utils.LOG;
import com.lys.protobuf.SPTask;
import com.lys.utils.TaskHelper;
import com.lys.utils.TaskTreeNode;

import java.util.List;

public class DialogSelectTask extends Dialog implements View.OnClickListener
{
	public interface OnListener
	{
		void onSelect(List<SPTask> selectedList, String taskText);
	}

	private OnListener listener = null;

	private void setListener(OnListener listener)
	{
		this.listener = listener;
	}

	private class Holder
	{
		private EditText editText;
	}

	private Holder holder = new Holder();

	private void initHolder()
	{
		holder.editText = findViewById(R.id.editText);
	}

//	private String schoolId;
//	private int subject;

	private RecyclerView recyclerView;
	private AdapterSelectTask adapter;

	private DialogSelectTask(@NonNull Context context, String userId)
	{
		super(context, R.style.FullDialog);
//		setCancelable(false);
		setContentView(R.layout.dialog_select_task);
		initHolder();

//		this.schoolId = schoolId;

//		subject = SPHelper.getInt(context, AppConfig.SP_Key_SelectSubject, SSubject.Shu);
//		holder.subjectText.setText(Config.getSubjectName(subject));

		findViewById(R.id.con).setOnClickListener(this);
		findViewById(R.id.ok).setOnClickListener(this);

		recyclerView = findViewById(R.id.recyclerView);
		recyclerView.setLayoutManager(new LinearLayoutManager(context));
		adapter = new AdapterSelectTask(this);
		recyclerView.setAdapter(adapter);

		request(userId);
	}

	@Override
	public void dismiss()
	{
		super.dismiss();
	}

	@Override
	public void onClick(View view)
	{
		if (view.getId() == R.id.con)
		{
			dismiss();
		}
		else if (view.getId() == R.id.ok)
		{
			if (adapter.isReady())
			{
				if (adapter.getSelectedTasks().size() > 0)
				{
					dismiss();
					if (listener != null)
						listener.onSelect(adapter.getSelectedTasks(), holder.editText.getText().toString());
				}
				else
				{
					LOG.toast(getContext(), "未选择任务");
				}
			}
			else
			{
				LOG.toast(getContext(), "数据未加载");
			}
		}
	}

	private void request(String userId)
	{
		TaskHelper.requestTaskTree(getContext(), userId, new TaskHelper.onTaskTreeCallback()
		{
			@Override
			public void onResult(List<TaskTreeNode> treeNodes)
			{
				if (treeNodes != null)
					adapter.setData(treeNodes);
			}
		});
	}

	public static void show(Context context, String userId, OnListener listener)
	{
		DialogSelectTask dialog = new DialogSelectTask(context, userId);
		dialog.setListener(listener);
		dialog.show();
	}

}