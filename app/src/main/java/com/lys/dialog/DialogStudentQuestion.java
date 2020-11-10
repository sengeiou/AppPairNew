package com.lys.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;

import com.lys.App;
import com.lys.app.R;
import com.lys.kit.utils.Protocol;
import com.lys.kit.view.SelectionGroup;
import com.lys.protobuf.SHandleId;
import com.lys.protobuf.SProblemType;
import com.lys.protobuf.SRequest_TeachQuestionByStudent;
import com.lys.protobuf.SSelectionGroup;

public class DialogStudentQuestion extends Dialog implements View.OnClickListener
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
		private SelectionGroup selectionGroupMatch;
		private SelectionGroup selectionGroupDiff;
		private SelectionGroup selectionGroupGot;
		private SelectionGroup selectionGroupQuality;
		private SelectionGroup selectionGroupLike;
	}

	private Holder holder = new Holder();

	private void initHolder()
	{
		holder.selectionGroupMatch = findViewById(R.id.selectionGroupMatch);
		holder.selectionGroupDiff = findViewById(R.id.selectionGroupDiff);
		holder.selectionGroupGot = findViewById(R.id.selectionGroupGot);
		holder.selectionGroupQuality = findViewById(R.id.selectionGroupQuality);
		holder.selectionGroupLike = findViewById(R.id.selectionGroupLike);
	}

	private String teachId;
	private String userId;
	private String targetId;

	private DialogStudentQuestion(@NonNull Context context, String teachId, String userId, String targetId)
	{
		super(context, R.style.FullDialog);
//		setCancelable(false);
		setContentView(R.layout.dialog_student_question);
		initHolder();

		this.teachId = teachId;
		this.userId = userId;
		this.targetId = targetId;

		findViewById(R.id.con).setOnClickListener(this);

		findViewById(R.id.close).setOnClickListener(this);
		findViewById(R.id.commit).setOnClickListener(this);

		initSelectionGroup(holder.selectionGroupMatch, "完全不是", "大部分不是", "一般", "基本上是的", "正是我想学的");
		initSelectionGroup(holder.selectionGroupDiff, "太简单了", "偏简单", "适中", "偏难", "太难了");
		initSelectionGroup(holder.selectionGroupGot, "很少", "比较少", "一般", "比较多", "很多");
		initSelectionGroup(holder.selectionGroupQuality, "糟糕", "不太好", "一般", "挺好", "非常棒");
		initSelectionGroup(holder.selectionGroupLike, "很不喜欢", "不太喜欢", "一般", "比较喜欢", "非常喜欢");
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
		SRequest_TeachQuestionByStudent request = new SRequest_TeachQuestionByStudent();
		request.teachId = teachId;
		request.userId = userId;
		request.targetId = targetId;
		request.questionMatch = getAnswer(holder.selectionGroupMatch);
		request.questionDiff = getAnswer(holder.selectionGroupDiff);
		request.questionGot = getAnswer(holder.selectionGroupGot);
		request.questionQuality = getAnswer(holder.selectionGroupQuality);
		request.questionLike = getAnswer(holder.selectionGroupLike);
		Protocol.doPost(getContext(), App.getApi(), SHandleId.TeachQuestionByStudent, request.saveToStr(), new Protocol.OnCallback()
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

	public static void show(Context context, String teachId, String userId, String targetId, OnListener listener)
	{
		DialogStudentQuestion dialog = new DialogStudentQuestion(context, teachId, userId, targetId);
		dialog.setListener(listener);
		dialog.show();
	}

}