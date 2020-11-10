package com.lys.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.text.Html;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.lys.App;
import com.lys.app.R;
import com.lys.kit.utils.Protocol;
import com.lys.kit.view.SelectionGroup;
import com.lys.protobuf.SHandleId;
import com.lys.protobuf.SProblemType;
import com.lys.protobuf.SRequest_TeachQuestionByTeacher;
import com.lys.protobuf.SSelectionGroup;

public class DialogTeacherQuestion extends Dialog implements View.OnClickListener
{
	public interface OnListener
	{
		void onResult();
	}

	private OnListener listener = null;

	private void setListener(OnListener listener)
	{
		this.listener = listener;
	}

	private class Holder
	{
		private TextView text;
		private SelectionGroup selectionGroupHot;
		private SelectionGroup selectionGroupMind;
		private SelectionGroup selectionGroupLogic;
		private EditText inputOther;
	}

	private Holder holder = new Holder();

	private void initHolder()
	{
		holder.text = findViewById(R.id.text);
		holder.selectionGroupHot = findViewById(R.id.selectionGroupHot);
		holder.selectionGroupMind = findViewById(R.id.selectionGroupMind);
		holder.selectionGroupLogic = findViewById(R.id.selectionGroupLogic);
		holder.inputOther = findViewById(R.id.inputOther);
	}

	private String teachId;
	private String userId;

	private DialogTeacherQuestion(@NonNull Context context, String teachId, String userId)
	{
		super(context, R.style.FullDialog);
//		setCancelable(false);
		setContentView(R.layout.dialog_teacher_question);
		initHolder();

		this.teachId = teachId;
		this.userId = userId;

		findViewById(R.id.con).setOnClickListener(this);

		findViewById(R.id.close).setOnClickListener(this);
		findViewById(R.id.commit).setOnClickListener(this);

		holder.text.setText(Html.fromHtml(String.format("&nbsp;&nbsp;&nbsp;&nbsp;<font color='#00ff00'>%s</font> 老师，本次课程已经结束，辛苦了！请您对学生在课堂上的表现进行评价：", App.name())));

		initSelectionGroup(holder.selectionGroupHot, "非常不符合", "比较不符合", "一般", "比较符合", "非常符合");
		initSelectionGroup(holder.selectionGroupMind, "非常不符合", "比较不符合", "一般", "比较符合", "非常符合");
		initSelectionGroup(holder.selectionGroupLogic, "非常不符合", "比较不符合", "一般", "比较符合", "非常符合");

		// 延迟显示，这样输入法不会自动弹出来
		new Handler().postDelayed(new Runnable()
		{
			@Override
			public void run()
			{
				holder.inputOther.setVisibility(View.VISIBLE);
			}
		}, 100);
	}

	private void initSelectionGroup(SelectionGroup selectionGroup, String... selections)
	{
		SSelectionGroup data = new SSelectionGroup();
		data.problemType = SProblemType.SingleSelect;
		for (String selection : selections)
		{
			data.selections.add(selection);
		}
		selectionGroup.unlockSelections();
		selectionGroup.setSelectionGroup(data);
		selectionGroup.updateSelections(false);
	}

	private String getAnswer(SelectionGroup selectionGroup)
	{
		if (selectionGroup.mSelectionGroup.answer.size() > 0)
			return selectionGroup.mSelectionGroup.answer.get(0);
		return "";
	}

	@Override
	public void dismiss()
	{
		super.dismiss();
		if (listener != null)
			listener.onResult();
	}

	@Override
	public void onClick(View view)
	{
		switch (view.getId())
		{
		case R.id.con:
			dismiss();
			break;

		case R.id.close:
			dismiss();
			break;

		case R.id.commit:
			doCommit();
			break;
		}
	}

	public void doCommit()
	{
		SRequest_TeachQuestionByTeacher request = new SRequest_TeachQuestionByTeacher();
		request.teachId = teachId;
		request.userId = userId;
		request.questionHot = getAnswer(holder.selectionGroupHot);
		request.questionMind = getAnswer(holder.selectionGroupMind);
		request.questionLogic = getAnswer(holder.selectionGroupLogic);
		request.questionOther = holder.inputOther.getText().toString().trim();
		Protocol.doPost(getContext(), App.getApi(), SHandleId.TeachQuestionByTeacher, request.saveToStr(), new Protocol.OnCallback()
		{
			@Override
			public void onResponse(int code, String data, String msg)
			{
				if (code == 200)
				{
					dismiss();
				}
			}
		});
	}

	public static void show(Context context, String teachId, String userId, OnListener listener)
	{
		DialogTeacherQuestion dialog = new DialogTeacherQuestion(context, teachId, userId);
		dialog.setListener(listener);
		dialog.show();
	}

}