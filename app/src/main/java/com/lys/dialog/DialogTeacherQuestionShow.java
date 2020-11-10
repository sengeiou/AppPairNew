package com.lys.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.lys.app.R;
import com.lys.kit.view.SelectionGroup;
import com.lys.protobuf.SProblemType;
import com.lys.protobuf.SSelectionGroup;
import com.lys.protobuf.STeachRecord;

public class DialogTeacherQuestionShow extends Dialog implements View.OnClickListener
{
	private class Holder
	{
		private SelectionGroup selectionGroupHot;
		private SelectionGroup selectionGroupMind;
		private SelectionGroup selectionGroupLogic;
		private EditText inputOther;
	}

	private Holder holder = new Holder();

	private void initHolder()
	{
		holder.selectionGroupHot = findViewById(R.id.selectionGroupHot);
		holder.selectionGroupMind = findViewById(R.id.selectionGroupMind);
		holder.selectionGroupLogic = findViewById(R.id.selectionGroupLogic);
		holder.inputOther = findViewById(R.id.inputOther);
	}

	private DialogTeacherQuestionShow(@NonNull Context context, STeachRecord teachRecord)
	{
		super(context, R.style.FullDialog);
//		setCancelable(false);
		setContentView(R.layout.dialog_teacher_question_show);
		initHolder();

		findViewById(R.id.con).setOnClickListener(this);

		findViewById(R.id.close).setOnClickListener(this);

		initSelectionGroup(holder.selectionGroupHot, teachRecord.questionHot, "非常不符合", "比较不符合", "一般", "比较符合", "非常符合");
		initSelectionGroup(holder.selectionGroupMind, teachRecord.questionMind, "非常不符合", "比较不符合", "一般", "比较符合", "非常符合");
		initSelectionGroup(holder.selectionGroupLogic, teachRecord.questionLogic, "非常不符合", "比较不符合", "一般", "比较符合", "非常符合");

		if (!TextUtils.isEmpty(teachRecord.questionOther))
			holder.inputOther.setText(teachRecord.questionOther);
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
		DialogTeacherQuestionShow dialog = new DialogTeacherQuestionShow(context, teachRecord);
		dialog.show();
	}

}