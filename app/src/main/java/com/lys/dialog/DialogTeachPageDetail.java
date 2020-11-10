package com.lys.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.lys.adapter.AdapterTeachPageDetail;
import com.lys.app.R;
import com.lys.protobuf.STeachPage;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DialogTeachPageDetail extends Dialog implements View.OnClickListener
{
	private class Holder
	{
//		private SelectionGroup selectionGroupMatch;
	}

	private Holder holder = new Holder();

	private void initHolder()
	{
//		holder.selectionGroupMatch = findViewById(R.id.selectionGroupMatch);
	}

	private RecyclerView recyclerView;
	private AdapterTeachPageDetail adapter;

	private DialogTeachPageDetail(@NonNull Context context, List<STeachPage> teachPages)
	{
		super(context, R.style.FullDialog);
//		setCancelable(false);
		setContentView(R.layout.dialog_teach_page_detail);
		initHolder();

		findViewById(R.id.con).setOnClickListener(this);

		findViewById(R.id.close).setOnClickListener(this);

		long maxTime = 0;
		for (STeachPage teachPage : teachPages)
		{
			maxTime = Math.max(maxTime, teachPage.time);
		}

		Collections.sort(teachPages, new Comparator<STeachPage>()
		{
			@Override
			public int compare(STeachPage obj1, STeachPage obj2)
			{
				return obj1.index.compareTo(obj2.index);
			}
		});

		recyclerView = findViewById(R.id.recyclerView);
		recyclerView.setLayoutManager(new LinearLayoutManager(context));
		adapter = new AdapterTeachPageDetail(teachPages, maxTime);
		recyclerView.setAdapter(adapter);
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

	public static void show(Context context, List<STeachPage> teachPages)
	{
		DialogTeachPageDetail dialog = new DialogTeachPageDetail(context, teachPages);
		dialog.show();
	}

}