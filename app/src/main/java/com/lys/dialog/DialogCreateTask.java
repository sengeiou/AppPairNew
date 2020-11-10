package com.lys.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

import com.lys.app.R;
import com.lys.base.utils.LOG;
import com.lys.protobuf.SPJobType;
import com.lys.protobuf.SPTaskType;

public class DialogCreateTask extends Dialog implements View.OnClickListener
{
	public interface OnListener
	{
		void onResult(String group, String name, int taskType, int jobType);
	}

	private OnListener listener = null;

	private void setListener(OnListener listener)
	{
		this.listener = listener;
	}

	private class Holder
	{
		private EditText group;
		private EditText name;

		private RadioButton typeJob;
		private RadioButton typeClass;

		private RadioButton jobOnlySelect;
		private RadioButton jobMultTopic;
	}

	private Holder holder = new Holder();

	private void initHolder()
	{
		holder.group = findViewById(R.id.group);
		holder.name = findViewById(R.id.name);

		holder.typeJob = findViewById(R.id.typeJob);
		holder.typeClass = findViewById(R.id.typeClass);

		holder.jobOnlySelect = findViewById(R.id.jobOnlySelect);
		holder.jobMultTopic = findViewById(R.id.jobMultTopic);
	}

	private DialogCreateTask(@NonNull Context context, String group, String name, int taskType, int jobType)
	{
		super(context, R.style.Dialog);
//		setCancelable(false);
		setContentView(R.layout.dialog_create_task);
		initHolder();

		holder.group.setText(group);
		holder.name.setText(name);

		if (taskType == SPTaskType.Job)
			holder.typeJob.setChecked(true);
		else if (taskType == SPTaskType.Class)
			holder.typeClass.setChecked(true);

		if (jobType == SPJobType.OnlySelect)
			holder.jobOnlySelect.setChecked(true);
		else if (jobType == SPJobType.MultTopic)
			holder.jobMultTopic.setChecked(true);

		findViewById(R.id.cancel).setOnClickListener(this);
		findViewById(R.id.ok).setOnClickListener(this);
	}

	@Override
	public void dismiss()
	{
		super.dismiss();
	}

	@Override
	public void onClick(View view)
	{
		switch (view.getId())
		{
		case R.id.cancel:
			dismiss();
			break;

		case R.id.ok:
			ok();
			break;
		}
	}

	private void ok()
	{
		String group = holder.group.getText().toString().trim();
		if (TextUtils.isEmpty(group))
		{
			LOG.toast(getContext(), "请输入组名");
			return;
		}

		String name = holder.name.getText().toString().trim();
		if (TextUtils.isEmpty(name))
		{
			LOG.toast(getContext(), "请输入任务名");
			return;
		}

		int taskType = SPTaskType.None;
		if (holder.typeJob.isChecked())
			taskType = SPTaskType.Job;
		else if (holder.typeClass.isChecked())
			taskType = SPTaskType.Class;
		else
		{
			LOG.toast(getContext(), "请选择任务类型");
			return;
		}

		int jobType = SPJobType.None;
		if (holder.jobOnlySelect.isChecked())
			jobType = SPJobType.OnlySelect;
		else if (holder.jobMultTopic.isChecked())
			jobType = SPJobType.MultTopic;
		else
		{
			if (taskType == SPTaskType.Job)
			{
				LOG.toast(getContext(), "请选择作业类型");
				return;
			}
		}

		dismiss();
		if (listener != null)
			listener.onResult(group, name, taskType, jobType);
	}

	public static void show(Context context, String group, String name, int taskType, int jobType, OnListener listener)
	{
		DialogCreateTask dialog = new DialogCreateTask(context, group, name, taskType, jobType);
		dialog.setListener(listener);
		dialog.show();
	}

}