package com.lys.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.lys.App;
import com.lys.app.R;
import com.lys.base.utils.AppDataTool;
import com.lys.base.utils.CommonUtils;
import com.lys.base.utils.FsUtils;
import com.lys.base.utils.ImageLoader;
import com.lys.base.utils.JsonHelper;
import com.lys.base.utils.LOG;
import com.lys.base.utils.SysUtils;
import com.lys.config.AppConfig;
import com.lys.kit.activity.KitActivity;
import com.lys.kit.dialog.DialogAlert;
import com.lys.kit.pop.PopInsert;
import com.lys.kit.utils.ImageLoad;
import com.lys.kit.utils.Protocol;
import com.lys.kit.view.BoardToolBar;
import com.lys.kit.view.BoardView;
import com.lys.kit.view.PhotoView;
import com.lys.protobuf.SHandleId;
import com.lys.protobuf.SPhotoAddParam;
import com.lys.protobuf.SProblemStyle;
import com.lys.protobuf.SProblemType;
import com.lys.protobuf.SRequest_SearchTopics;
import com.lys.protobuf.SRequest_TopicRecordGet;
import com.lys.protobuf.SRequest_TopicRecordSetFav;
import com.lys.protobuf.SRequest_TopicRecordSetResult;
import com.lys.protobuf.SResponse_SearchTopics;
import com.lys.protobuf.SResponse_TopicRecordGet;
import com.lys.protobuf.SResponse_TopicRecordSetFav;
import com.lys.protobuf.SResponse_TopicRecordSetResult;
import com.lys.protobuf.SSelectionGroup;
import com.lys.protobuf.STopic;
import com.lys.protobuf.STopicRecord;
import com.lys.utils.LysIM;

import java.io.File;
import java.util.List;

public class ActivityTopicAnswer extends KitActivity implements View.OnClickListener
{
	private class Holder
	{
		private BoardToolBar toolBar;

		private BoardView board;

		private TextView number;
		private TextView debugInfo;

		private ViewGroup favCon;
		private ImageView star;
		private TextView favText;

		private ImageView switchParse;
		private ImageView commit;
		private ViewGroup jump;

		private ViewGroup parseCon;
		private ViewGroup knowledgeCon;
		private ScrollView parseScroll;
		private ImageView parseImg;
		private TextView resultText;
		private ImageView resultError;
		private ImageView resultRight;
		private ImageView nextTopic;
	}

	private Holder holder = new Holder();

	private void initHolder()
	{
		holder.toolBar = findViewById(R.id.toolBar);

		holder.board = findViewById(R.id.board);

		holder.number = findViewById(R.id.number);
		holder.debugInfo = findViewById(R.id.debugInfo);

		holder.favCon = findViewById(R.id.favCon);
		holder.star = findViewById(R.id.star);
		holder.favText = findViewById(R.id.favText);

		holder.switchParse = findViewById(R.id.switchParse);
		holder.commit = findViewById(R.id.commit);
		holder.jump = findViewById(R.id.jump);

		holder.parseCon = findViewById(R.id.parseCon);
		holder.knowledgeCon = findViewById(R.id.knowledgeCon);
		holder.parseScroll = findViewById(R.id.parseScroll);
		holder.parseImg = findViewById(R.id.parseImg);
		holder.resultText = findViewById(R.id.resultText);
		holder.resultError = findViewById(R.id.resultError);
		holder.resultRight = findViewById(R.id.resultRight);
		holder.nextTopic = findViewById(R.id.nextTopic);
	}

	private List<SProblemStyle> mStyles;

	private SRequest_SearchTopics topicSearch;

	private STopic topic = null;
	private STopicRecord topicRecord = null;
	private int topicNumber = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void init()
	{
		super.init();
		setContentView(R.layout.activity_topic_answer);

		initHolder();

//		findViewById(R.id.close).setVisibility(shouldShowClose() ? View.VISIBLE : View.GONE);
//		findViewById(R.id.close).setOnClickListener(this);

		holder.parseCon.setVisibility(View.GONE);

		holder.debugInfo.setVisibility(SysUtils.isDebug() ? View.VISIBLE : View.GONE);

		mStyles = SProblemStyle.loadList(JsonHelper.getJSONArray(getIntent().getStringExtra("styles")));
		topicSearch = SRequest_SearchTopics.load(getIntent().getStringExtra("topicSearch"));

		if (App.isStudent())
		{
			holder.toolBar.setInsert(true, PopInsert.IconImageTopic, PopInsert.IconImageSelectionGroup);
		}
		holder.toolBar.setWeike(false);

		holder.toolBar.hideIconGrid();
		holder.toolBar.hideIconAddPage();

		holder.toolBar.justSelectImage();

		holder.toolBar.setListener(toolBarListener);

		findViewById(R.id.favCon).setOnClickListener(this);

		findViewById(R.id.switchParse).setOnClickListener(this);
		findViewById(R.id.commit).setOnClickListener(this);
		findViewById(R.id.jump).setOnClickListener(this);

		findViewById(R.id.resultError).setOnClickListener(this);
		findViewById(R.id.resultRight).setOnClickListener(this);
		findViewById(R.id.nextTopic).setOnClickListener(this);

		holder.board.setMenu(holder.toolBar);
		holder.toolBar.bindBoard(holder.board);
		holder.board.setListener(new BoardView.OnListener()
		{
			@Override
			public void onLockChanged(boolean isLock)
			{
				holder.toolBar.photoLockSetChecked(isLock);
			}

			@Override
			public void onPaste()
			{
				holder.toolBar.onPaste();
			}
		});

		holder.board.addOnLayoutChangeListener(new View.OnLayoutChangeListener()
		{
			@Override
			public void onLayoutChange(View view, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom)
			{
				view.removeOnLayoutChangeListener(this);
				request(null, new Protocol.OnCallback()
				{
					@Override
					public void onResponse(int code, String data, String msg)
					{
						if (code == 200)
						{
							SResponse_SearchTopics response = SResponse_SearchTopics.load(data);
							holder.debugInfo.setText(String.valueOf(response.totalCount));
							if (response.topics.size() > 0)
							{
								topic = response.topics.get(0);
								requestTopicRecord();
							}
							else
							{
								DialogAlert.show(context, "当前条件下没有题", null, new DialogAlert.OnClickListener()
								{
									@Override
									public void onClick(int which)
									{
										if (which == 0)
										{
											finish();
										}
									}
								}, "退出");
							}
						}
					}
				});
			}
		});

//		holder.board.setBoardHeight(SysUtils.screenHeight(context));
	}

	private boolean isSelectTopic()
	{
		for (SProblemStyle style : mStyles)
		{
			if (style.name.equals(topic.style))
				return style.isSelect;
		}
		return false;
	}

	private void requestTopicRecord()
	{
		SRequest_TopicRecordGet request = new SRequest_TopicRecordGet();
		request.userId = App.userId();
		request.topicId = topic.id;
		Protocol.doPost(context, App.getApi(), SHandleId.TopicRecordGet, request.saveToStr(), new Protocol.OnCallback()
		{
			@Override
			public void onResponse(int code, String data, String msg)
			{
				if (code == 200)
				{
					SResponse_TopicRecordGet response = SResponse_TopicRecordGet.load(data);
					topicRecord = response.topicRecord;
					loadTopic();
				}
			}
		});
	}

	private void updateFav()
	{
		if (topicRecord != null && topicRecord.fav == 1)
		{
			holder.star.setImageResource(R.drawable.img_topic_fav_select);
			holder.favText.setText("已收藏");
			holder.favText.setTextColor(0xfffcff00);
		}
		else
		{
			holder.star.setImageResource(R.drawable.img_topic_fav_normal);
			holder.favText.setText("收藏本题");
			holder.favText.setTextColor(0xffffffff);
		}
	}

	private void loadTopic()
	{
		updateFav();

		holder.parseCon.setVisibility(View.GONE);

		final File dir = new File(AppConfig.getTopicDir(topic.id));
		if (dir.exists()) // 必须要先判断 exists 再 loadBoard （因为 loadBoard 完之后，肯定存在，再判断就没意义了）
		{
			holder.debugInfo.setText(holder.debugInfo.getText() + " : " + "旧题");
			holder.board.loadBoard(dir, new BoardView.OnLoadBoardCallback()
			{
				@Override
				public void onLoadOver()
				{
					FsUtils.writeText(new File(dir, "topic.json.raw"), topic.saveToStr());

					PhotoView photoView = holder.board.findPhoto(AppConfig.TopicContentName);
					if (photoView != null)
					{
						PhotoView photoViewSelection = holder.board.findPhoto(AppConfig.TopicSelectionGroupName);
						if (photoViewSelection != null)
						{
							holder.board.modifyPhoto(photoViewSelection.photo.name, 0, photoView.getDimensionHeight(), photoViewSelection.photo.rotation, BoardView.SelectionGroupWidth, BoardView.SelectionGroupHeight, photoViewSelection.photo.hide);
						}
					}

					setParseCon();
				}
			});
		}
		else
		{
			holder.debugInfo.setText(holder.debugInfo.getText() + " : " + "新题");
			holder.board.loadBoard(dir, new BoardView.OnLoadBoardCallback()
			{
				@Override
				public void onLoadOver()
				{
					FsUtils.writeText(new File(dir, "topic.json.raw"), topic.saveToStr());
					ImageLoad.load(context, topic.contentUrl, new ImageLoader.OnLoad()
					{
						@Override
						public void over(final Bitmap bitmap, String url)
						{
							if (bitmap != null)
							{
								byte[] bitmapData = CommonUtils.saveBitmapToData(bitmap, Bitmap.CompressFormat.PNG, 100);

								SPhotoAddParam param = new SPhotoAddParam();
								param.x = 0;
								param.y = 0;
								param.notEye = true;
								param.lock = true;
								param.doNotActive = true;
								PhotoView photoView = holder.board.addPhoto(bitmapData, param, AppConfig.TopicContentName);
								if (isSelectTopic())
								{
									addSelectionGroup(0, photoView.getDimensionHeight());
								}

								setParseCon();
							}
							else
							{
								LOG.toast(context, "题目加载失败");
							}
						}
					});
				}
			});
		}

		holder.knowledgeCon.removeAllViews();
		for (String knowledge : topic.knowledges)
		{
			if (knowledge.startsWith("#S#"))
				knowledge = knowledge.substring("#S#".length());
			if (knowledge.endsWith("#S#"))
				knowledge = knowledge.substring(0, knowledge.length() - "#S#".length());
			View view = LayoutInflater.from(context).inflate(R.layout.view_knowledge, null);
			TextView text = view.findViewById(R.id.text);
			text.setText(knowledge);
			holder.knowledgeCon.addView(view);
		}

		ImageLoad.displayImage(context, topic.analyUrl, 0, holder.parseImg, R.drawable.img_default, null);
		holder.parseScroll.scrollTo(0, 0);
	}

	private void setParseCon()
	{
		if (topicRecord != null && topicRecord.result != 0)
			setParseConAsResult();
		else
			setParseConAsNewTopic();
	}

	private void setParseConAsNewTopic()
	{
		holder.switchParse.setVisibility(View.GONE);
		holder.commit.setVisibility(View.VISIBLE);
		holder.jump.setVisibility(View.VISIBLE);

		holder.parseCon.setVisibility(View.GONE);

		setSelectionGroupIfIsSelectTopic(false);
	}

	private void setParseConAsEvaluate()
	{
		holder.switchParse.setVisibility(View.VISIBLE);
		holder.switchParse.setImageResource(R.drawable.img_topic_parse_hide);
		holder.commit.setVisibility(View.GONE);
		holder.jump.setVisibility(View.GONE);

		holder.parseCon.setVisibility(View.VISIBLE);

		holder.resultText.setVisibility(View.GONE);

		holder.resultError.setVisibility(View.VISIBLE);
		holder.resultRight.setVisibility(View.VISIBLE);
		holder.nextTopic.setVisibility(View.GONE);

		setSelectionGroupIfIsSelectTopic(true);
	}

	private void setParseConAsResult()
	{
		holder.switchParse.setVisibility(View.VISIBLE);
		holder.switchParse.setImageResource(R.drawable.img_topic_parse_hide);
		holder.commit.setVisibility(View.GONE);
		holder.jump.setVisibility(View.GONE);

		holder.parseCon.setVisibility(View.VISIBLE);

		if (topicRecord != null && topicRecord.result == 1)
		{
			holder.resultText.setVisibility(View.VISIBLE);
			holder.resultText.setText("回答错误，再接再励哦！");
			holder.resultText.setTextColor(0xfff1786e);
		}
		else if (topicRecord != null && topicRecord.result == 3)
		{
			holder.resultText.setVisibility(View.VISIBLE);
			holder.resultText.setText("恭喜你，回答正确！");
			holder.resultText.setTextColor(0xff67c8aa);
		}
		else
		{
			holder.resultText.setVisibility(View.VISIBLE);
			holder.resultText.setText("错误结果：" + topicRecord);
			holder.resultText.setTextColor(0xfff1786e);
		}

		holder.resultError.setVisibility(View.GONE);
		holder.resultRight.setVisibility(View.GONE);
		holder.nextTopic.setVisibility(View.VISIBLE);

		setSelectionGroupIfIsSelectTopic(true);
	}

	private void setSelectionGroupIfIsSelectTopic(boolean showRightAnswer)
	{
		if (isSelectTopic())
		{
			PhotoView photoView = holder.board.findPhoto(AppConfig.TopicSelectionGroupName);
			if (photoView != null)
			{
				photoView.setShowRightAnswer(showRightAnswer);
				if (showRightAnswer)
					photoView.lockSelections();
				else
					photoView.unlockSelections();
			}
			else
			{
				LOG.toast(context, "未找到选项组！");
			}
		}
	}

	private SSelectionGroup genSelectionGroupABCD()
	{
		SSelectionGroup selectionGroup = new SSelectionGroup();
		selectionGroup.selections.add("A");
		selectionGroup.selections.add("B");
		selectionGroup.selections.add("C");
		selectionGroup.selections.add("D");
		return selectionGroup;
	}

	private SSelectionGroup parseSelectionGroup()
	{
		if ("A".equals(topic.answer) || "AA".equals(topic.answer))
		{
			SSelectionGroup selectionGroup = genSelectionGroupABCD();
			selectionGroup.problemType = SProblemType.SingleSelect;
			selectionGroup.rightAnswer.add("A");
			return selectionGroup;
		}
		else if ("B".equals(topic.answer) || "BB".equals(topic.answer))
		{
			SSelectionGroup selectionGroup = genSelectionGroupABCD();
			selectionGroup.problemType = SProblemType.SingleSelect;
			selectionGroup.rightAnswer.add("B");
			return selectionGroup;
		}
		else if ("C".equals(topic.answer) || "CC".equals(topic.answer))
		{
			SSelectionGroup selectionGroup = genSelectionGroupABCD();
			selectionGroup.problemType = SProblemType.SingleSelect;
			selectionGroup.rightAnswer.add("C");
			return selectionGroup;
		}
		else if ("D".equals(topic.answer) || "DD".equals(topic.answer))
		{
			SSelectionGroup selectionGroup = genSelectionGroupABCD();
			selectionGroup.problemType = SProblemType.SingleSelect;
			selectionGroup.rightAnswer.add("D");
			return selectionGroup;
		}
		else
		{
			LOG.v("parse fail : " + topic.answer);
			SSelectionGroup selectionGroup = genSelectionGroupABCD();
			selectionGroup.problemType = SProblemType.MultiSelect;
			return selectionGroup;
		}
	}

	private void addSelectionGroup(int x, int y)
	{
		SSelectionGroup selectionGroup = parseSelectionGroup();

		SPhotoAddParam param = new SPhotoAddParam();
		param.x = x;
		param.y = y;
		param.lock = true;
		param.doNotActive = true;

		PhotoView photoView = holder.board.addSelectionGroup(selectionGroup, param, AppConfig.TopicSelectionGroupName);
	}

	private void request(String excludeId, Protocol.OnCallback callback)
	{
		SRequest_SearchTopics request = new SRequest_SearchTopics();
		request.content = topicSearch.content;
		request.phase = topicSearch.phase;
		request.subject = topicSearch.subject;
		request.start = topicSearch.start;
		request.rows = topicSearch.rows;
		request.chapters = topicSearch.chapters;
		request.styles = topicSearch.styles;
		request.diffs = topicSearch.diffs;
		request.rand = true;
		request.excludeId = excludeId;
		Protocol.doPost(context, App.getApi(), SHandleId.SearchTopics, request.saveToStr(), callback);
	}

	//------------------- 点击事件处理（开始） --------------------------

	private BoardToolBar.OnListener toolBarListener = new BoardToolBar.OnListener()
	{
		@Override
		public void onIconSend()
		{
			LysIM.share(context, holder.board, null);
		}
	};

	@Override
	public void onClick(View view)
	{
//		if (view.getId() == R.id.close)
//		{
//			finish();
//		}
		if (view.getId() == R.id.favCon)
		{
			SRequest_TopicRecordSetFav request = new SRequest_TopicRecordSetFav();
			request.userId = App.userId();
			request.topicId = topic.id;
			request.fav = (topicRecord != null && topicRecord.fav == 1) ? 0 : 1;
			Protocol.doPost(context, App.getApi(), SHandleId.TopicRecordSetFav, request.saveToStr(), new Protocol.OnCallback()
			{
				@Override
				public void onResponse(int code, String data, String msg)
				{
					if (code == 200)
					{
						SResponse_TopicRecordSetFav response = SResponse_TopicRecordSetFav.load(data);
						topicRecord = response.topicRecord;
						updateFav();
					}
				}
			});
		}
		else if (view.getId() == R.id.switchParse)
		{
			if (holder.parseCon.getVisibility() == View.VISIBLE)
			{
				holder.parseCon.setVisibility(View.GONE);
				holder.switchParse.setImageResource(R.drawable.img_topic_parse_show);
			}
			else
			{
				holder.parseCon.setVisibility(View.VISIBLE);
				holder.switchParse.setImageResource(R.drawable.img_topic_parse_hide);
			}
		}
		else if (view.getId() == R.id.commit)
		{
			doCommit();
		}
		else if (view.getId() == R.id.jump)
		{
			nextTopic();
		}
		else if (view.getId() == R.id.resultError)
		{
			doResult(1);
		}
		else if (view.getId() == R.id.resultRight)
		{
			doResult(3);
		}
		else if (view.getId() == R.id.nextTopic)
		{
			nextTopic();
		}
	}

	private void doCommit()
	{
		holder.board.waitSyncOver();
		if (isSelectTopic())
		{
			PhotoView photoView = holder.board.findPhoto(AppConfig.TopicSelectionGroupName);
			if (photoView != null)
			{
				SSelectionGroup selectionGroup = photoView.photo.selectionGroup;
				if (selectionGroup.answer.size() > 0)
				{
					if (selectionGroup.rightAnswer.size() > 0)
					{
						String answerStr = AppDataTool.saveStringList(selectionGroup.answer).toString();
						String rightAnswerStr = AppDataTool.saveStringList(selectionGroup.rightAnswer).toString();
						if (answerStr.equals(rightAnswerStr))
						{
							doResult(3);
						}
						else
						{
							doResult(1);
						}
					}
					else
					{
						setParseConAsEvaluate();
					}
				}
				else
				{
					LOG.toast(context, "你还没有作答！");
				}
			}
			else
			{
				LOG.toast(context, "未找到选项组！");
			}
		}
		else
		{
			setParseConAsEvaluate();
		}
	}

	private void doResult(int result)
	{
		holder.board.waitSyncOver();
		SRequest_TopicRecordSetResult request = new SRequest_TopicRecordSetResult();
		request.userId = App.userId();
		request.topicId = topic.id;
		request.result = result;
		Protocol.doPost(context, App.getApi(), SHandleId.TopicRecordSetResult, request.saveToStr(), new Protocol.OnCallback()
		{
			@Override
			public void onResponse(int code, String data, String msg)
			{
				if (code == 200)
				{
					SResponse_TopicRecordSetResult response = SResponse_TopicRecordSetResult.load(data);
					topicRecord = response.topicRecord;
					topicNumber++;
					holder.number.setText(String.format("已刷 %d 题", topicNumber));
					setParseConAsResult();
				}
			}
		});
	}

	private void nextTopic()
	{
		holder.board.waitSyncOver();
		request(topic.id, new Protocol.OnCallback()
		{
			@Override
			public void onResponse(int code, String data, String msg)
			{
				if (code == 200)
				{
					SResponse_SearchTopics response = SResponse_SearchTopics.load(data);
					holder.debugInfo.setText(String.valueOf(response.totalCount));
					if (response.topics.size() > 0)
					{
						topic = response.topics.get(0);
						requestTopicRecord();
					}
					else
					{
						DialogAlert.show(context, "", "没有更多题了！", null, "我知道了");
					}
				}
			}
		});
	}

	//------------------- 点击事件处理（结束） --------------------------

}
