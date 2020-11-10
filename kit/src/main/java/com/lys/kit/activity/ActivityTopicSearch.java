package com.lys.kit.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.lys.base.utils.AppDataTool;
import com.lys.base.utils.CommonUtils;
import com.lys.base.utils.FsUtils;
import com.lys.base.utils.HttpUtils;
import com.lys.base.utils.ImageLoader;
import com.lys.base.utils.JsonHelper;
import com.lys.base.utils.LOG;
import com.lys.base.utils.SysUtils;
import com.lys.kit.R;
import com.lys.kit.adapter.AdapterTopicFilterDiff;
import com.lys.kit.adapter.AdapterTopicFilterKnowledge;
import com.lys.kit.adapter.AdapterTopicFilterStyle;
import com.lys.kit.config.Config;
import com.lys.kit.dialog.DialogWait;
import com.lys.kit.pop.PopChoice;
import com.lys.kit.utils.ImageLoad;
import com.lys.kit.utils.Protocol;
import com.lys.protobuf.SChoiceItem;
import com.lys.protobuf.SHandleId;
import com.lys.protobuf.SKnowledge;
import com.lys.protobuf.SPhase;
import com.lys.protobuf.SProblemDiff;
import com.lys.protobuf.SProblemStyle;
import com.lys.protobuf.SRequest_SearchTopics;
import com.lys.protobuf.SResponse_SearchTopics;
import com.lys.protobuf.SSubject;
import com.lys.protobuf.STopic;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityTopicSearch extends KitActivity implements View.OnClickListener
{
	private class Holder
	{
		private TextView phaseText;
		private TextView subjectText;
		private EditText keyword;

		private TextView pageNum;

		private ScrollView problemScroll;
		private ScrollView parseScroll;

		private ImageView problemImg;
		private ImageView parseImg;

		private TextView number;
		private SeekBar progress;

		private ViewGroup knowledgePage;
		private ViewGroup stylePage;
		private ViewGroup difficultyPage;
	}

	private Holder holder = new Holder();

	private void initHolder()
	{
		holder.phaseText = findViewById(R.id.phaseText);
		holder.subjectText = findViewById(R.id.subjectText);
		holder.keyword = findViewById(R.id.keyword);

		holder.pageNum = findViewById(R.id.pageNum);

		holder.problemScroll = findViewById(R.id.problemScroll);
		holder.parseScroll = findViewById(R.id.parseScroll);

		holder.problemImg = findViewById(R.id.problemImg);
		holder.parseImg = findViewById(R.id.parseImg);

		holder.number = findViewById(R.id.number);
		holder.progress = findViewById(R.id.progress);

		holder.knowledgePage = findViewById(R.id.knowledgePage);
		holder.stylePage = findViewById(R.id.stylePage);
		holder.difficultyPage = findViewById(R.id.difficultyPage);
	}

	private SRequest_SearchTopics topicSearch;

	private RecyclerView recyclerView;
	private AdapterTopicFilterKnowledge adapter;

	private RecyclerView recyclerViewStyle;
	private AdapterTopicFilterStyle adapterStyle;

	private RecyclerView recyclerViewDifficulty;
	private AdapterTopicFilterDiff adapterDifficulty;

	private SRequest_SearchTopics getDefaultTopicSearch()
	{
		SRequest_SearchTopics defaultTopicSearch = new SRequest_SearchTopics();
		defaultTopicSearch.content = "";
		defaultTopicSearch.phase = SPhase.Chu;
		defaultTopicSearch.subject = SSubject.Shu;
		defaultTopicSearch.start = 0;
		defaultTopicSearch.rows = 1;
		return defaultTopicSearch;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_topic_search);

		initHolder();

		holder.pageNum.setText("");

		topicSearch = Config.readTopicSearch();
		if (topicSearch == null)
		{
			topicSearch = getDefaultTopicSearch();
		}
		holder.keyword.setText(topicSearch.content);
		holder.phaseText.setText(Config.getPhaseName(topicSearch.phase));
		holder.subjectText.setText(Config.getSubjectName(topicSearch.subject));

		findViewById(R.id.phaseCon).setOnClickListener(this);
		findViewById(R.id.subjectCon).setOnClickListener(this);

		findViewById(R.id.knowledgeCon).setOnClickListener(this);
		findViewById(R.id.styleCon).setOnClickListener(this);
		findViewById(R.id.difficultyCon).setOnClickListener(this);

		findViewById(R.id.knowledgePage).setOnClickListener(this);
		findViewById(R.id.stylePage).setOnClickListener(this);
		findViewById(R.id.difficultyPage).setOnClickListener(this);

		findViewById(R.id.search).setOnClickListener(this);
		findViewById(R.id.prev).setOnClickListener(this);
		findViewById(R.id.next).setOnClickListener(this);
		findViewById(R.id.result).setOnClickListener(this);

		holder.progress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
		{
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
			{
//				LOG.v("onProgressChanged : " + fromUser);
				holder.number.setText(String.valueOf(holder.progress.getProgress() + 1));
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar)
			{
//				LOG.v("onStartTrackingTouch");
				holder.number.setVisibility(View.VISIBLE);
			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar)
			{
//				LOG.v("onStopTrackingTouch");
				holder.number.setVisibility(View.GONE);
				request(holder.progress.getProgress());
			}
		});

		recyclerView = findViewById(R.id.recyclerView);
		recyclerView.setLayoutManager(new LinearLayoutManager(context));
		adapter = new AdapterTopicFilterKnowledge();
		recyclerView.setAdapter(adapter);

		recyclerViewStyle = findViewById(R.id.recyclerViewStyle);
		recyclerViewStyle.setLayoutManager(new GridLayoutManager(this, 2));
		adapterStyle = new AdapterTopicFilterStyle();
		recyclerViewStyle.setAdapter(adapterStyle);

		recyclerViewDifficulty = findViewById(R.id.recyclerViewDifficulty);
		recyclerViewDifficulty.setLayoutManager(new GridLayoutManager(this, 2));
		adapterDifficulty = new AdapterTopicFilterDiff();
		recyclerViewDifficulty.setAdapter(adapterDifficulty);

		holder.keyword.setOnEditorActionListener(new TextView.OnEditorActionListener()
		{
			@Override
			public boolean onEditorAction(TextView textView, int actionId, KeyEvent event)
			{
				if (actionId == EditorInfo.IME_ACTION_SEARCH)
				{
					search(0);
					SysUtils.hideKeybord(ActivityTopicSearch.this);
					return true;
				}
				return false;
			}
		});

		request(topicSearch.start);

		requestKnowledge();
		requestStyle();
		requestDifficulty();
	}

	@Override
	public void onClick(View view)
	{
		if (view.getId() == R.id.phaseCon)
		{
			PopChoice.showPhase(context, view, new PopChoice.OnListener()
			{
				@Override
				public void onSelect(SChoiceItem choiceItem)
				{
					if (topicSearch.phase != choiceItem.value)
					{
						topicSearch.phase = choiceItem.value;
						topicSearch.content = "";
						topicSearch.start = 0;
						topicSearch.rows = 1;
						topicSearch.knowledges.clear();
						topicSearch.styles.clear();
						topicSearch.diffs.clear();

						holder.keyword.setText(topicSearch.content);
						holder.phaseText.setText(Config.getPhaseName(topicSearch.phase));

						request(topicSearch.start);

						requestKnowledge();
						requestStyle();
						requestDifficulty();
					}
				}
			});
		}
		else if (view.getId() == R.id.subjectCon)
		{
			PopChoice.showSubject(context, view, new PopChoice.OnListener()
			{
				@Override
				public void onSelect(SChoiceItem choiceItem)
				{
					if (topicSearch.subject != choiceItem.value)
					{
						topicSearch.subject = choiceItem.value;
						topicSearch.content = "";
						topicSearch.start = 0;
						topicSearch.rows = 1;
						topicSearch.knowledges.clear();
						topicSearch.styles.clear();
						topicSearch.diffs.clear();

						holder.keyword.setText(topicSearch.content);
						holder.subjectText.setText(Config.getSubjectName(topicSearch.subject));

						request(topicSearch.start);

						requestKnowledge();
						requestStyle();
						requestDifficulty();
					}
				}
			});
		}
		else if (view.getId() == R.id.knowledgeCon)
		{
			holder.knowledgePage.setVisibility(View.VISIBLE);
		}
		else if (view.getId() == R.id.styleCon)
		{
			holder.stylePage.setVisibility(View.VISIBLE);
		}
		else if (view.getId() == R.id.difficultyCon)
		{
			holder.difficultyPage.setVisibility(View.VISIBLE);
		}
		else if (view.getId() == R.id.knowledgePage)
		{
			holder.knowledgePage.setVisibility(View.GONE);
		}
		else if (view.getId() == R.id.stylePage)
		{
			holder.stylePage.setVisibility(View.GONE);
		}
		else if (view.getId() == R.id.difficultyPage)
		{
			holder.difficultyPage.setVisibility(View.GONE);
		}
		else if (view.getId() == R.id.search)
		{
			search(0);
			SysUtils.hideKeybord(this);
		}
		else if (view.getId() == R.id.prev)
		{
			if (topicSearch.start - 1 >= 0)
				request(topicSearch.start - 1);
			else
				LOG.toast(context, "没有上一题");
		}
		else if (view.getId() == R.id.next)
		{
			request(topicSearch.start + 1);
		}
		else if (view.getId() == R.id.result)
		{
			if (topic != null)
			{
				File contentFile = ImageLoader.getCacheFile(context, topic.contentUrl);
				File analyFile = ImageLoader.getCacheFile(context, topic.analyUrl);
				if (contentFile.exists() && analyFile.exists())
				{
					if (true)
					{
						BitmapFactory.Options opts = CommonUtils.readBitmapSize(contentFile.toString());
						List<String> urls = new ArrayList<>();
						urls.add(topic.contentUrl);
						urls.add(String.valueOf(opts.outWidth));
						urls.add(String.valueOf(opts.outHeight));
						urls.add(topic.analyUrl);
						Intent intent = new Intent();
						intent.putExtra("result", AppDataTool.saveStringList(urls).toString());
						setResult(Activity.RESULT_OK, intent);
						finish();
					}
					else
					{
						List<String> paths = new ArrayList<>();
						FsUtils.copy(contentFile, Config.tmpPngFile);
						FsUtils.copy(analyFile, Config.tmpPngFile2);
						paths.add(Config.tmpPngFile.getAbsolutePath());
						paths.add(Config.tmpPngFile2.getAbsolutePath());
						Intent intent = new Intent();
						intent.putExtra("result", AppDataTool.saveStringList(paths).toString());
						setResult(Activity.RESULT_OK, intent);
						finish();
					}
				}
				else
				{
					LOG.toast(context, "图片未加载，无法使用");
				}
			}
			else
			{
				LOG.toast(context, "题目未加载，无法使用");
			}
		}
	}

	private void search(int start)
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

		String content = holder.keyword.getText().toString();
//		if (TextUtils.isEmpty(content))
//			return;
		topicSearch.content = content;

		topicSearch.knowledges = new ArrayList<>();
		for (SKnowledge knowledge : adapter.getSelectedKnowledges())
		{
			topicSearch.knowledges.add(SKnowledge.create(knowledge.code, "#S#" + knowledge.name + "#S#", 0, 0, false, null, 0));
		}
		topicSearch.styles = adapterStyle.getSelectedStyles();
		topicSearch.diffs = adapterDifficulty.getSelectedDiffs();

		request(start);
	}

	private STopic topic = null;

	private void request(final int start)
	{
//		if (TextUtils.isEmpty(topicSearch.content))
//			return;

		DialogWait.show(context, "加载中。。。");

		SRequest_SearchTopics request = new SRequest_SearchTopics();
		request.content = topicSearch.content;
		request.phase = topicSearch.phase;
		request.subject = topicSearch.subject;
		request.start = start;
		request.rows = topicSearch.rows;
		request.knowledges = topicSearch.knowledges;
		request.styles = topicSearch.styles;
		request.diffs = topicSearch.diffs;
		Protocol.doPost(context, Config.getApi(), SHandleId.SearchTopics, request.saveToStr(), new Protocol.OnCallback()
		{
			@Override
			public void onResponse(int code, String data, String msg)
			{
				DialogWait.close();
				if (code == 200)
				{
					SResponse_SearchTopics response = SResponse_SearchTopics.load(data);
					if (response.topics.size() > 0)
					{
						topic = response.topics.get(0);

						topicSearch.start = start;
						Config.writeTopicSearch(topicSearch.saveToStr());

						holder.pageNum.setText(String.format("%s / %s", start + 1, response.totalCount));
						holder.progress.setMax(response.totalCount - 1);
						holder.progress.setProgress(start);

						ImageLoad.displayImage(context, topic.contentUrl, 0, holder.problemImg, R.drawable.img_default, null);
						ImageLoad.displayImage(context, topic.analyUrl, 0, holder.parseImg, R.drawable.img_default, null);

						holder.problemScroll.scrollTo(0, 0);
						holder.parseScroll.scrollTo(0, 0);
					}
					else
					{
						LOG.toast(context, "没有更多题了");
					}
				}
			}
		});
	}

	private void requestKnowledge()
	{
		adapter.setData(null, null);

		String url = String.format("http://zjyk-file.oss-cn-huhehaote.aliyuncs.com/fixed/knowledge_%s_%s.json.raw", topicSearch.phase, topicSearch.subject);
		HttpUtils.doHttpGet(context, url, new HttpUtils.OnCallback()
		{
			@Override
			public void onResponse(String text)
			{
				if (!TextUtils.isEmpty(text))
				{
					List<SKnowledge> knowledges = SKnowledge.loadList(JsonHelper.getJSONArray(text));
					adapter.setData(knowledges, topicSearch.knowledges);
				}
				else
				{
					LOG.toast(context, "获取知识点失败");
				}
			}
		});

	}

	private void requestStyle()
	{
		adapterStyle.setData(null, null);

		String url = String.format("http://zjyk-file.oss-cn-huhehaote.aliyuncs.com/fixed/topic_style_%s_%s.json.raw", topicSearch.phase, topicSearch.subject);
		HttpUtils.doHttpGet(context, url, new HttpUtils.OnCallback()
		{
			@Override
			public void onResponse(String text)
			{
				if (!TextUtils.isEmpty(text))
				{
					List<SProblemStyle> styles = SProblemStyle.loadList(JsonHelper.getJSONArray(text));

					Map<String, SProblemStyle> allMap = new HashMap<>();
					for (int i = 0; i < styles.size(); i++)
					{
						SProblemStyle style = styles.get(i);
						allMap.put(style.name, style);
					}

					Map<String, SProblemStyle> selectedMap = new HashMap<>();
					for (int i = 0; i < topicSearch.styles.size(); i++)
					{
						String styleName = topicSearch.styles.get(i);
						if (allMap.containsKey(styleName))
						{
							SProblemStyle style = allMap.get(styleName);
							selectedMap.put(style.name, style);
						}
					}

					adapterStyle.setData(styles, selectedMap);
				}
				else
				{
					LOG.toast(context, "获取题型失败");
				}
			}
		});

	}

	// 难度是写死的，没有请求，这里只是为了保持代码工整（另外，难度也利用一下题型的逻辑）
	private void requestDifficulty()
	{
		adapterDifficulty.setData(null, null);

		final int[] difficultys = {Config.Difficulty1, Config.Difficulty2, Config.Difficulty3, Config.Difficulty4, Config.Difficulty5};
		final String[] names = {"容易", "较易", "一般", "较难", "困难"};

		List<SProblemDiff> diffs = new ArrayList<>();
		for (int i = 0; i < difficultys.length; i++)
		{
			SProblemDiff diff = new SProblemDiff();
			diff.diff = difficultys[i];
			diff.name = names[i];
			diffs.add(diff);
		}

		Map<Integer, SProblemDiff> allMap = new HashMap<>();
		for (int i = 0; i < diffs.size(); i++)
		{
			SProblemDiff diff = diffs.get(i);
			allMap.put(diff.diff, diff);
		}

		Map<Integer, SProblemDiff> selectedMap = new HashMap<>();
		for (int i = 0; i < topicSearch.diffs.size(); i++)
		{
			int diffValue = topicSearch.diffs.get(i);
			if (allMap.containsKey(diffValue))
			{
				SProblemDiff diff = allMap.get(diffValue);
				selectedMap.put(diff.diff, diff);
			}
		}

		adapterDifficulty.setData(diffs, selectedMap);
	}

}
