package com.lys.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.lys.app.R;
import com.lys.base.utils.HttpUtils;
import com.lys.base.utils.JsonHelper;
import com.lys.base.utils.LOG;
import com.lys.kit.activity.KitActivity;
import com.lys.kit.adapter.AdapterTopicFilterChapter;
import com.lys.kit.config.Config;
import com.lys.kit.dialog.DialogAlert;
import com.lys.protobuf.SChapter;
import com.lys.protobuf.SProblemStyle;
import com.lys.protobuf.SRequest_SearchTopics;

import java.util.ArrayList;
import java.util.List;

public class ActivityTopicFilter extends KitActivity implements View.OnClickListener
{
	private class Holder
	{
		private ImageView allTopicStates;
		private ImageView selectTopicStates;

		private ImageView diffLowStates;
		private ImageView diffMiddleStates;
		private ImageView diffHighStates;

		private ImageView selectAllStates;
	}

	private Holder holder = new Holder();

	private void initHolder()
	{
		holder.allTopicStates = findViewById(R.id.allTopicStates);
		holder.selectTopicStates = findViewById(R.id.selectTopicStates);

		holder.diffLowStates = findViewById(R.id.diffLowStates);
		holder.diffMiddleStates = findViewById(R.id.diffMiddleStates);
		holder.diffHighStates = findViewById(R.id.diffHighStates);

		holder.selectAllStates = findViewById(R.id.selectAllStates);
	}

	private int mPhase;
	private int mSubject;

	private List<SProblemStyle> mStyles;

	private SRequest_SearchTopics topicSearch;

	private RecyclerView recyclerView;
	private AdapterTopicFilterChapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_topic_filter);

		initHolder();

		requestPermission();
	}

	@Override
	public void permissionSuccess()
	{
		super.permissionSuccess();

		mPhase = getIntent().getIntExtra("phase", 0);
		mSubject = getIntent().getIntExtra("subject", 0);

		requestStyle();
	}

	private void requestStyle()
	{
		String url = String.format("http://zjyk-file.oss-cn-huhehaote.aliyuncs.com/fixed/topic_style_%s_%s.json.raw", mPhase, mSubject);
		HttpUtils.doHttpGet(context, url, new HttpUtils.OnCallback()
		{
			@Override
			public void onResponse(String text)
			{
				if (!TextUtils.isEmpty(text))
				{
					mStyles = SProblemStyle.loadList(JsonHelper.getJSONArray(text));
					initialize();
				}
				else
				{
					DialogAlert.show(context, "获取题型失败", null, new DialogAlert.OnClickListener()
					{
						@Override
						public void onClick(int which)
						{
							if (which == 0)
							{
								finish();
							}
							else if (which == 1)
							{
								requestStyle();
							}
						}
					}, "退出", "重试");
				}
			}
		});
	}

	private SRequest_SearchTopics getDefaultTopicSearch()
	{
		SRequest_SearchTopics defaultTopicSearch = new SRequest_SearchTopics();
		defaultTopicSearch.content = "";
		defaultTopicSearch.phase = mPhase;
		defaultTopicSearch.subject = mSubject;
		defaultTopicSearch.start = 0;
		defaultTopicSearch.rows = 1;
		defaultTopicSearch.diffs.add(Config.Difficulty2);
		defaultTopicSearch.diffs.add(Config.Difficulty3);
		defaultTopicSearch.diffs.add(Config.Difficulty4);
		return defaultTopicSearch;
	}

	private void initialize()
	{
		topicSearch = Config.readTopicFilter(mPhase, mSubject);
		if (topicSearch == null)
		{
			topicSearch = getDefaultTopicSearch();
		}

		findViewById(R.id.allTopicCon).setOnClickListener(this);
		findViewById(R.id.selectTopicCon).setOnClickListener(this);

		findViewById(R.id.diffLowCon).setOnClickListener(this);
		findViewById(R.id.diffMiddleCon).setOnClickListener(this);
		findViewById(R.id.diffHighCon).setOnClickListener(this);

		findViewById(R.id.selectAllCon).setOnClickListener(this);

		findViewById(R.id.reset).setOnClickListener(this);
		findViewById(R.id.start).setOnClickListener(this);

		updateStyle();
		updateDiff();

		recyclerView = findViewById(R.id.recyclerView);
		recyclerView.setLayoutManager(new LinearLayoutManager(context));
		adapter = new AdapterTopicFilterChapter();
		recyclerView.setAdapter(adapter);

		requestChapter();
	}

	private void updateStyle()
	{
		holder.allTopicStates.setImageResource(R.drawable.img_check_normal);
		holder.selectTopicStates.setImageResource(R.drawable.img_check_normal);

		if (topicSearch.styles.size() == 0)
		{
			holder.allTopicStates.setImageResource(R.drawable.img_check_light);
		}
		else
		{
			holder.selectTopicStates.setImageResource(R.drawable.img_check_light);
		}
	}

	private void updateDiff()
	{
		holder.diffLowStates.setImageResource(R.drawable.img_check_normal);
		holder.diffMiddleStates.setImageResource(R.drawable.img_check_normal);
		holder.diffHighStates.setImageResource(R.drawable.img_check_normal);

		if (topicSearch.diffs.size() == 2 //
				&& topicSearch.diffs.get(0) == Config.Difficulty1 //
				&& topicSearch.diffs.get(1) == Config.Difficulty2)
		{
			holder.diffLowStates.setImageResource(R.drawable.img_check_light);
		}
		else if (topicSearch.diffs.size() == 3 //
				&& topicSearch.diffs.get(0) == Config.Difficulty2 //
				&& topicSearch.diffs.get(1) == Config.Difficulty3 //
				&& topicSearch.diffs.get(2) == Config.Difficulty4)
		{
			holder.diffMiddleStates.setImageResource(R.drawable.img_check_light);
		}
		else if (topicSearch.diffs.size() == 2 //
				&& topicSearch.diffs.get(0) == Config.Difficulty4 //
				&& topicSearch.diffs.get(1) == Config.Difficulty5)
		{
			holder.diffHighStates.setImageResource(R.drawable.img_check_light);
		}
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
		if (view.getId() == R.id.allTopicCon)
		{
			topicSearch.styles.clear();
			updateStyle();
		}
		else if (view.getId() == R.id.selectTopicCon)
		{
			topicSearch.styles.clear();
			for (SProblemStyle style : mStyles)
			{
				if (style.isSelect)
					topicSearch.styles.add(style.name);
			}
			updateStyle();
		}
		else if (view.getId() == R.id.diffLowCon)
		{
			topicSearch.diffs.clear();
			topicSearch.diffs.add(Config.Difficulty1);
			topicSearch.diffs.add(Config.Difficulty2);
			updateDiff();
		}
		else if (view.getId() == R.id.diffMiddleCon)
		{
			topicSearch.diffs.clear();
			topicSearch.diffs.add(Config.Difficulty2);
			topicSearch.diffs.add(Config.Difficulty3);
			topicSearch.diffs.add(Config.Difficulty4);
			updateDiff();
		}
		else if (view.getId() == R.id.diffHighCon)
		{
			topicSearch.diffs.clear();
			topicSearch.diffs.add(Config.Difficulty4);
			topicSearch.diffs.add(Config.Difficulty5);
			updateDiff();
		}
		else if (view.getId() == R.id.selectAllCon)
		{
		}
		else if (view.getId() == R.id.reset)
		{
			topicSearch = getDefaultTopicSearch();
			updateStyle();
			updateDiff();
			adapter.updateData(topicSearch.chapters);
		}
		else if (view.getId() == R.id.start)
		{
			if (!adapter.isReady())
			{
				LOG.toast(context, "章节数据未就位！");
				return;
			}

			topicSearch.chapters = new ArrayList<>();
			for (SChapter chapter : adapter.getSelectedChapters())
			{
				topicSearch.chapters.add(SChapter.create(chapter.code, getFullChapter(chapter), 0, 0, false, null, 0));
			}

			Config.writeTopicFilter(topicSearch.saveToStr(), mPhase, mSubject);

			Intent intent = new Intent(context, ActivityTopicAnswer.class);
			intent.putExtra("styles", SProblemStyle.saveList(mStyles).toString());
			intent.putExtra("topicSearch", topicSearch.saveToStr());
			startActivity(intent);
		}
	}

	private String getFullChapter(SChapter chapter)
	{
		if (chapter.parent != null)
			return getFullChapter(chapter.parent) + "#S#" + chapter.name + "#S#";
		else
			return "#S#" + chapter.name + "#S#";
	}

	private void requestChapter()
	{
		String url = String.format("http://zjyk-file.oss-cn-huhehaote.aliyuncs.com/fixed/chapter_%s_%s.json.raw", topicSearch.phase, topicSearch.subject);
		HttpUtils.doHttpGet(context, url, new HttpUtils.OnCallback()
		{
			@Override
			public void onResponse(String text)
			{
				if (!TextUtils.isEmpty(text))
				{
					List<SChapter> chapters = SChapter.loadList(JsonHelper.getJSONArray(text));
					adapter.setData(chapters, topicSearch.chapters);
				}
				else
				{
					LOG.toast(context, "获取章节失败");
				}
			}
		});

	}

}
