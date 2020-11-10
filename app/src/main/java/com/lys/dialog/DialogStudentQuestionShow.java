package com.lys.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;

import com.lys.app.R;
import com.lys.kit.view.SelectionGroup;
import com.lys.protobuf.SProblemType;
import com.lys.protobuf.SSelectionGroup;
import com.lys.protobuf.STeachRecord;

public class DialogStudentQuestionShow extends Dialog implements View.OnClickListener
{
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

	private DialogStudentQuestionShow(@NonNull Context context, STeachRecord teachRecord)
	{
		super(context, R.style.FullDialog);
//		setCancelable(false);
		setContentView(R.layout.dialog_student_question_show);
		initHolder();

		findViewById(R.id.con).setOnClickListener(this);

		findViewById(R.id.close).setOnClickListener(this);

		initSelectionGroup(holder.selectionGroupMatch, teachRecord.questionMatch, "完全不是", "大部分不是", "一般", "基本上是的", "正是我想学的");
		initSelectionGroup(holder.selectionGroupDiff, teachRecord.questionDiff, "太简单了", "偏简单", "适中", "偏难", "太难了");
		initSelectionGroup(holder.selectionGroupGot, teachRecord.questionGot, "很少", "比较少", "一般", "比较多", "很多");
		initSelectionGroup(holder.selectionGroupQuality, teachRecord.questionQuality, "糟糕", "不太好", "一般", "挺好", "非常棒");
		initSelectionGroup(holder.selectionGroupLike, teachRecord.questionLike, "很不喜欢", "不太喜欢", "一般", "比较喜欢", "非常喜欢");
	}

	private void initSelectionGroup(SelectionGroup selectionGroup, String answer, String... selections)
	{
		SSelectionGroup data = new SSelectionGroup();
		data.problemType = SProblemType.SingleSelect;
		for (String selection : selections)
		{
			data.selections.add(selection);
		}
		if (!TextUtils.isEmpty(answer))
			data.answer.add(answer);
		selectionGroup.lockSelections();
		selectionGroup.setSelectionGroup(data);
		selectionGroup.updateSelections(false);
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
		}
	}

	public static void show(Context context, STeachRecord teachRecord)
	{
		DialogStudentQuestionShow dialog = new DialogStudentQuestionShow(context, teachRecord);
		dialog.show();
	}

}