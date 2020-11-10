package com.lys.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.lys.app.R;
import com.lys.dialog.DialogSelectSubject;
import com.lys.kit.activity.KitActivity;
import com.lys.kit.config.Config;
import com.lys.kit.pop.PopMenu;
import com.lys.protobuf.SPhase;
import com.lys.protobuf.SSubject;

public class ActivityMainTopic extends KitActivity implements View.OnClickListener
{
	private class Holder
	{
		private TextView phaseText;
		private TextView subjectText;
	}

	private Holder holder = new Holder();

	private void initHolder()
	{
		holder.phaseText = findViewById(R.id.phaseText);
		holder.subjectText = findViewById(R.id.subjectText);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_topic);

		initHolder();

		requestPermission();
	}

	@Override
	public void permissionSuccess()
	{
		super.permissionSuccess();

		findViewById(R.id.phaseCon).setOnClickListener(this);
		findViewById(R.id.subjectCon).setOnClickListener(this);

		findViewById(R.id.chapterCon).setOnClickListener(this);
		findViewById(R.id.errorCon).setOnClickListener(this);
		findViewById(R.id.recordCon).setOnClickListener(this);
		findViewById(R.id.favCon).setOnClickListener(this);

		selectPhase(SPhase.Gao);
		selectSubject(SSubject.Shu);
	}

	@Override
	protected void onResume()
	{
		super.onResume();
	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
	}

	@Override
	public void onClick(View view)
	{
		if (view.getId() == R.id.phaseCon)
		{
			PopMenu.show(context, view, new PopMenu.OnClickListener()
			{
				@Override
				public void onClick(int which)
				{
					switch (which)
					{
					case 0:
						selectPhase(SPhase.Chu);
						break;
					case 1:
						selectPhase(SPhase.Gao);
						break;
					}
				}
			}, Config.getPhaseName(SPhase.Chu), Config.getPhaseName(SPhase.Gao));
		}
		else if (view.getId() == R.id.subjectCon)
		{
			DialogSelectSubject.show(context, new DialogSelectSubject.OnSelectListener()
			{
				@Override
				public void onSelect(int subject)
				{
					selectSubject(subject);
				}
			});
		}
		else if (view.getId() == R.id.chapterCon)
		{
			Intent intent = new Intent(context, ActivityTopicFilter.class);
			intent.putExtra("phase", mPhase);
			intent.putExtra("subject", mSubject);
			startActivity(intent);
		}
		else if (view.getId() == R.id.errorCon)
		{
			Intent intent = new Intent(context, ActivityTopicRecord.class);
			intent.putExtra("type", 3);
			startActivity(intent);
		}
		else if (view.getId() == R.id.recordCon)
		{
			Intent intent = new Intent(context, ActivityTopicRecord.class);
			intent.putExtra("type", 2);
			startActivity(intent);
		}
		else if (view.getId() == R.id.favCon)
		{
			Intent intent = new Intent(context, ActivityTopicRecord.class);
			intent.putExtra("type", 1);
			startActivity(intent);
		}
	}

	private int mPhase;

	private void selectPhase(int phase)
	{
		mPhase = phase;
		holder.phaseText.setText(Config.getPhaseName(phase));
	}

	private int mSubject;

	private void selectSubject(int subject)
	{
		mSubject = subject;
		holder.subjectText.setText(Config.getSubjectName(subject));
	}

}
