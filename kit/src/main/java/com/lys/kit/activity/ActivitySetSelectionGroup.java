package com.lys.kit.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.lys.kit.R;
import com.lys.kit.view.SelectionGroup;
import com.lys.protobuf.SProblemType;
import com.lys.protobuf.SSelectionGroup;

public class ActivitySetSelectionGroup extends KitActivity implements View.OnClickListener
{
	private class Holder
	{
		private RadioGroup selectGroup;
		private RadioButton singleSelect;
		private RadioButton multiSelect;

		private SelectionGroup selectionGroup;
	}

	private Holder holder = new Holder();

	private void initHolder()
	{
		holder.selectGroup = findViewById(R.id.selectGroup);
		holder.singleSelect = findViewById(R.id.singleSelect);
		holder.multiSelect = findViewById(R.id.multiSelect);

		holder.selectionGroup = findViewById(R.id.selectionGroup);
	}

	private SSelectionGroup mSelectionGroup = null;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set_selection_group);

		initHolder();

		mSelectionGroup = SSelectionGroup.load(getIntent().getStringExtra("selectionGroup"));
		mSelectionGroup.rightAnswer.clear();
		mSelectionGroup.answer.clear();

		findViewById(R.id.btnCancel).setOnClickListener(this);
		findViewById(R.id.btnOk).setOnClickListener(this);

		if (mSelectionGroup.problemType.equals(SProblemType.SingleSelect))
			holder.singleSelect.setChecked(true);
		else if (mSelectionGroup.problemType.equals(SProblemType.MultiSelect))
			holder.multiSelect.setChecked(true);

		holder.selectGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
		{
			@Override
			public void onCheckedChanged(RadioGroup radioGroup, int checked)
			{
				int problemType = SProblemType.SingleSelect;
				if (holder.singleSelect.isChecked())
					problemType = SProblemType.SingleSelect;
				else if (holder.multiSelect.isChecked())
					problemType = SProblemType.MultiSelect;
				if (!mSelectionGroup.problemType.equals(problemType))
				{
					mSelectionGroup.problemType = problemType;
					if (mSelectionGroup.problemType.equals(SProblemType.SingleSelect))
					{
						while (mSelectionGroup.answer.size() > 1)
						{
							mSelectionGroup.answer.remove(mSelectionGroup.answer.size() - 1);
						}
						holder.selectionGroup.updateSelections(true);
					}
				}
			}
		});

		holder.selectionGroup.unlockSelections();
		holder.selectionGroup.setSelectionGroup(mSelectionGroup);
		holder.selectionGroup.updateSelections(true);
	}

	@Override
	public void onClick(View view)
	{
		if (view.getId() == R.id.btnCancel)
		{
			finish();
		}
		else if (view.getId() == R.id.btnOk)
		{
//			if (mSelectionGroup.answer.size() == 0)
//			{
//				LOG.toast(context, "请设置正确答案");
//			}
//			else
			{
				mSelectionGroup.rightAnswer.addAll(mSelectionGroup.answer);
				mSelectionGroup.answer.clear();

				Intent intent = new Intent();
				intent.putExtra("result", mSelectionGroup.saveToStr());
				setResult(Activity.RESULT_OK, intent);
				finish();
			}
		}
	}

}
