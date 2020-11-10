package com.lys.kit.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.lys.kit.R;
import com.lys.kit.adapter.AdapterSelectionGroup;
import com.lys.protobuf.SProblemType;
import com.lys.protobuf.SSelectionGroup;

import java.util.ArrayList;
import java.util.List;

public class ActivitySelectSelectionGroup extends KitActivity
{
	private class Holder
	{
//		private TextView nameScreenshot;
	}

	private Holder holder = new Holder();

	private void initHolder()
	{
//		holder.nameScreenshot = findViewById(R.id.nameScreenshot);
	}

	private RecyclerView recyclerView;
	private AdapterSelectionGroup adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_selection_group);

		initHolder();

		recyclerView = findViewById(R.id.recyclerView);
		recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
		adapter = new AdapterSelectionGroup(this);
		recyclerView.setAdapter(adapter);

		request();
	}

	private void request()
	{
		List<SSelectionGroup> selectionGroupList = new ArrayList<>();
		selectionGroupList.add(genSelectionGroup("A", "B"));
		selectionGroupList.add(genSelectionGroup("A", "B", "C"));
		selectionGroupList.add(genSelectionGroup("A", "B", "C", "D"));
		selectionGroupList.add(genSelectionGroup("A", "B", "C", "D", "E"));
		selectionGroupList.add(genSelectionGroup("A", "B", "C", "D", "E", "F"));
		selectionGroupList.add(genSelectionGroup("A", "B", "C", "D", "E", "F", "G"));
		selectionGroupList.add(genSelectionGroup("是", "否"));
		selectionGroupList.add(genSelectionGroup("正确", "错误"));
		selectionGroupList.add(genSelectionGroup("正确", "错误", "半对"));
		adapter.setData(selectionGroupList);
	}

	private SSelectionGroup genSelectionGroup(String... selections)
	{
		SSelectionGroup selectionGroup = new SSelectionGroup();
		selectionGroup.problemType = SProblemType.SingleSelect;
		for (String selection : selections)
			selectionGroup.selections.add(selection);
		return selectionGroup;
	}

	public void select(SSelectionGroup selectionGroup)
	{
		Intent intent = new Intent();
		intent.putExtra("result", selectionGroup.saveToStr());
		setResult(Activity.RESULT_OK, intent);
		finish();
	}
}
