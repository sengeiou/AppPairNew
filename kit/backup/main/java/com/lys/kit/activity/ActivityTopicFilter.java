package com.lys.kit.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lys.base.utils.LOG;
import com.lys.kit.R;
import com.lys.kit.adapter.AdapterTopicFilter;
import com.lys.kit.adapter.AdapterTopicFilterKnowledge;
import com.lys.kit.config.Config;
import com.lys.kit.pop.PopChoiceSubject;
import com.lys.kit.utils.KnowledgeTreeUtils;
import com.lys.kit.utils.Protocol;
import com.lys.protobuf.SHandleId;
import com.lys.protobuf.SKnowledge;
import com.lys.protobuf.SProblemStyle;
import com.lys.protobuf.SRequest_GetKnowledgeTreeLogic;
import com.lys.protobuf.SRequest_GetProblemType;
import com.lys.protobuf.SResponse_GetKnowledgeTreeLogic;
import com.lys.protobuf.SResponse_GetProblemType;
import com.lys.protobuf.SSubject;
import com.lys.protobuf.SSubjectCount;
import com.lys.protobuf.STopicFilter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityTopicFilter extends KitActivity implements View.OnClickListener
{
	private class Holder
	{
		private TextView subjectText;
		private ViewGroup filterCon;
	}

	private Holder holder = new Holder();

	private void initHolder()
	{
		holder.subjectText = findViewById(R.id.subjectText);
		holder.filterCon = findViewById(R.id.filterCon);
	}

	private int subject = SSubject.Shu;

	private RecyclerView recyclerView;
	private AdapterTopicFilterKnowledge adapter;

	private RecyclerView recyclerViewDifficulty;
	private AdapterTopicFilter adapterDifficulty;

	private RecyclerView recyclerViewStyle;
	private AdapterTopicFilter adapterStyle;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_topic_filter);

		initHolder();

//		subject = getIntent().getIntExtra("subject", 0);

		STopicFilter topicFilter = Config.readTopicFilter();
		if (topicFilter != null)
		{
			subject = topicFilter.subject;
		}

		findViewById(R.id.subjectCon).setOnClickListener(this);
		findViewById(R.id.reset).setOnClickListener(this);
		findViewById(R.id.closeFilter).setOnClickListener(this);
		findViewById(R.id.openFilter).setOnClickListener(this);
		findViewById(R.id.startTopic).setOnClickListener(this);

		holder.filterCon.setVisibility(View.GONE);

		recyclerView = findViewById(R.id.recyclerView);
		recyclerView.setLayoutManager(new LinearLayoutManager(context));
		adapter = new AdapterTopicFilterKnowledge();
		recyclerView.setAdapter(adapter);

		recyclerViewDifficulty = findViewById(R.id.recyclerViewDifficulty);
		recyclerViewDifficulty.setLayoutManager(new GridLayoutManager(this, 2));
		adapterDifficulty = new AdapterTopicFilter();
		recyclerViewDifficulty.setAdapter(adapterDifficulty);

		recyclerViewStyle = findViewById(R.id.recyclerViewStyle);
		recyclerViewStyle.setLayoutManager(new GridLayoutManager(this, 2));
		adapterStyle = new AdapterTopicFilter();
		recyclerViewStyle.setAdapter(adapterStyle);

		holder.subjectText.setText(Config.getSubjectName(subject));

		requestKnowledge();
		requestDifficulty();
		requestStyle();
	}

	@Override
	public void onClick(View view)
	{
		if (view.getId() == R.id.subjectCon)
		{
			PopChoiceSubject.show(context, view, new PopChoiceSubject.OnListener()
			{
				@Override
				public void onSelect(SSubjectCount subjectCount)
				{
					if (subject != subjectCount.subject)
					{
						subject = subjectCount.subject;
						holder.subjectText.setText(Config.getSubjectName(subject));

						STopicFilter topicFilter = new STopicFilter();
						topicFilter.subject = subject;
						topicFilter.style = 0;
						topicFilter.diff = 0;

						Config.writeTopicFilter(topicFilter.saveToStr());

						requestKnowledge();
						requestDifficulty();
						requestStyle();
					}
				}
			});
		}
		else if (view.getId() == R.id.reset)
		{
			STopicFilter topicFilter = new STopicFilter();
			topicFilter.subject = subject;
			topicFilter.style = 0;
			topicFilter.diff = 0;

			Config.writeTopicFilter(topicFilter.saveToStr());

			adapter.reset();
			adapterDifficulty.reset();
			adapterStyle.reset();
		}
		else if (view.getId() == R.id.closeFilter)
		{
			holder.filterCon.setVisibility(View.GONE);
		}
		else if (view.getId() == R.id.openFilter)
		{
			holder.filterCon.setVisibility(View.VISIBLE);
		}
		else if (view.getId() == R.id.startTopic)
		{
			startTopic();
		}
	}

	private void startTopic()
	{
		if (!adapter.isReady())
		{
			LOG.toast(context, "知识点数据未就位！");
			return;
		}

		if (!adapterStyle.isReady())
		{
			LOG.toast(context, "题型数据未就位！");
			return;
		}

		List<SKnowledge> selectedKnowledges = adapter.getSelectedKnowledges();
		List<String> knowledgeCodes = new ArrayList<>();
		for (SKnowledge knowledge : selectedKnowledges)
		{
			knowledgeCodes.add(knowledge.code);
		}

		STopicFilter topicFilter = new STopicFilter();
		topicFilter.subject = subject;
		topicFilter.knowledgeCodes = knowledgeCodes;
		topicFilter.style = adapterStyle.getStyleId();
		topicFilter.diff = adapterDifficulty.getStyleId();

		Config.writeTopicFilter(topicFilter.saveToStr());

		Intent intent = new Intent();
		intent.putExtra("result", topicFilter.saveToStr());
		setResult(Activity.RESULT_OK, intent);
		finish();
	}

	private void requestKnowledge()
	{
		adapter.setData(null);
		SRequest_GetKnowledgeTreeLogic request = new SRequest_GetKnowledgeTreeLogic();
//		request.personId = Config.getPersonId(context);
		request.subject = subject;
		Protocol.doPost(context, SHandleId.GetKnowledgeTreeLogic, request.saveToStr(), new Protocol.OnCallback()
		{
			@Override
			public void onResponse(int code, String data, String msg)
			{
				if (code == 200)
				{
					SResponse_GetKnowledgeTreeLogic response = SResponse_GetKnowledgeTreeLogic.load(data);
					KnowledgeTreeUtils.checkKnowledges(response.nodes);

					STopicFilter topicFilter = Config.readTopicFilter();
					if (topicFilter != null && topicFilter.knowledgeCodes.size() > 0)
					{
						Map<String, String> knowledgeCodeMap = new HashMap<>();
						for (String knowledgeCode : topicFilter.knowledgeCodes)
						{
							knowledgeCodeMap.put(knowledgeCode, knowledgeCode);
						}
						KnowledgeTreeUtils.checkState(response.nodes, knowledgeCodeMap);
					}

					adapter.setData(response.nodes);
				}
			}
		});
	}

	// 难度是写死的，没有请求，这里只是为了保持代码工整（另外，难度也利用一下题型的逻辑）
	private void requestDifficulty()
	{
		adapterDifficulty.setData(null, 0);
		final int[] difficultys = {0, Config.Difficulty1, Config.Difficulty2, Config.Difficulty3, Config.Difficulty4, Config.Difficulty5};
		final String[] names = {"全部", "简单", "容易", "一般", "较难", "困难"};
		SResponse_GetProblemType response = new SResponse_GetProblemType();
		for (int i = 0; i < difficultys.length; i++)
		{
			SProblemStyle style = new SProblemStyle();
			style.style = difficultys[i];
			style.name = names[i];
			response.styles.add(style);
		}

		int currPosition = 0;
		STopicFilter topicFilter = Config.readTopicFilter();
		if (topicFilter != null)
		{
			for (int i = 0; i < response.styles.size(); i++)
			{
				SProblemStyle style = response.styles.get(i);
				if (style.style.equals(topicFilter.diff))
				{
					currPosition = i;
					break;
				}
			}
		}

		adapterDifficulty.setData(response.styles, currPosition);
	}

	private void requestStyle()
	{
		adapterStyle.setData(null, 0);
		SRequest_GetProblemType request = new SRequest_GetProblemType();
		request.subject = subject;
		Protocol.doPost(context, SHandleId.GetProblemStylesLogic, request.saveToStr(), new Protocol.OnCallback()
		{
			@Override
			public void onResponse(int code, String data, String msg)
			{
				if (code == 200)
				{
					SResponse_GetProblemType response = SResponse_GetProblemType.load(data);

					{
						SProblemStyle style = new SProblemStyle();
						style.style = 0;
						style.name = "全部";
						response.styles.add(0, style);
					}

					int currPosition = 0;
					STopicFilter topicFilter = Config.readTopicFilter();
					if (topicFilter != null)
					{
						for (int i = 0; i < response.styles.size(); i++)
						{
							SProblemStyle style = response.styles.get(i);
							if (style.style.equals(topicFilter.style))
							{
								currPosition = i;
								break;
							}
						}
					}

					adapterStyle.setData(response.styles, currPosition);
				}
			}
		});
	}

}
