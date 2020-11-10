package com.lys.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.lys.App;
import com.lys.app.R;
import com.lys.base.utils.FsUtils;
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
import com.lys.protobuf.SRequest_TopicRecordGetList;
import com.lys.protobuf.SRequest_TopicRecordSetFav;
import com.lys.protobuf.SResponse_TopicRecordGetList;
import com.lys.protobuf.SResponse_TopicRecordSetFav;
import com.lys.protobuf.STopic;
import com.lys.protobuf.STopicRecord;
import com.lys.utils.LysIM;

import java.io.File;

public class ActivityTopicWatch extends KitActivity implements View.OnClickListener
{
	private class Holder
	{
		private BoardToolBar toolBar;

		private BoardView board;

		private ViewGroup favCon;
		private ImageView star;
		private TextView favText;

		private ImageView switchParse;

		private ViewGroup parseCon;
		private ViewGroup knowledgeCon;
		private ScrollView parseScroll;
		private ImageView parseImg;
		private TextView resultText;
	}

	private Holder holder = new Holder();

	private void initHolder()
	{
		holder.toolBar = findViewById(R.id.toolBar);

		holder.board = findViewById(R.id.board);

		holder.favCon = findViewById(R.id.favCon);
		holder.star = findViewById(R.id.star);
		holder.favText = findViewById(R.id.favText);

		holder.switchParse = findViewById(R.id.switchParse);

		holder.parseCon = findViewById(R.id.parseCon);
		holder.knowledgeCon = findViewById(R.id.knowledgeCon);
		holder.parseScroll = findViewById(R.id.parseScroll);
		holder.parseImg = findViewById(R.id.parseImg);
		holder.resultText = findViewById(R.id.resultText);
	}

	private int mType;

	private STopic topic = null;
	private STopicRecord topicRecord = null;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void init()
	{
		super.init();
		setContentView(R.layout.activity_topic_watch);

		initHolder();

//		findViewById(R.id.close).setVisibility(shouldShowClose() ? View.VISIBLE : View.GONE);
//		findViewById(R.id.close).setOnClickListener(this);

		holder.parseCon.setVisibility(View.GONE);

//		holder.debugInfo.setVisibility(SysUtils.isDebug() ? View.VISIBLE : View.GONE);

		mType = getIntent().getIntExtra("type", 0);
		topicRecord = STopicRecord.load(getIntent().getStringExtra("topicRecord"));

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
		findViewById(R.id.prev).setOnClickListener(this);
		findViewById(R.id.next).setOnClickListener(this);

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
				loadTopic();
			}
		});

//		holder.board.setBoardHeight(SysUtils.screenHeight(context));
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

		File dir = new File(AppConfig.getTopicDir(topicRecord.topicId));
		if (dir.exists())
		{
			topic = STopic.load(FsUtils.readText(new File(dir, "topic.json.raw")));
			holder.board.loadBoard(dir, new BoardView.OnLoadBoardCallback()
			{
				@Override
				public void onLoadOver()
				{
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
		else
		{
			DialogAlert.show(context, "", "本地文件丢失！", null, "我知道了");
		}
	}

	private void setParseCon()
	{
		if (topicRecord.result != 0)
			setParseConAsResult();
		else
			setParseConAsNewTopic();
	}

	private void setParseConAsNewTopic()
	{
		holder.switchParse.setVisibility(View.GONE);

		holder.parseCon.setVisibility(View.GONE);

		setSelectionGroupIfIsSelectTopic(false);
	}

	private void setParseConAsResult()
	{
		holder.switchParse.setVisibility(View.VISIBLE);
		holder.switchParse.setImageResource(R.drawable.img_topic_parse_show);

		holder.parseCon.setVisibility(View.GONE);

		if (topicRecord != null && topicRecord.result == 1)
		{
			holder.resultText.setText("回答错误，再接再励哦！");
			holder.resultText.setTextColor(0xfff1786e);
		}
		else if (topicRecord != null && topicRecord.result == 3)
		{
			holder.resultText.setText("恭喜你，回答正确！");
			holder.resultText.setTextColor(0xff67c8aa);
		}
		else
		{
			holder.resultText.setText("错误结果：" + topicRecord);
			holder.resultText.setTextColor(0xfff1786e);
		}

		setSelectionGroupIfIsSelectTopic(true);
	}

	private void setSelectionGroupIfIsSelectTopic(boolean showRightAnswer)
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
	}

	private void request(boolean prev)
	{
		SRequest_TopicRecordGetList request = new SRequest_TopicRecordGetList();
		request.userId = App.userId();
		request.type = mType;
		request.time = topicRecord.time;
		request.prev = prev;
		request.pageSize = 1;
		Protocol.doPost(context, App.getApi(), SHandleId.TopicRecordGetList, request.saveToStr(), new Protocol.OnCallback()
		{
			@Override
			public void onResponse(int code, String data, String msg)
			{
				if (code == 200)
				{
					SResponse_TopicRecordGetList response = SResponse_TopicRecordGetList.load(data);
					if (response.topicRecords.size() > 0)
					{
						topicRecord = response.topicRecords.get(0);
						loadTopic();
					}
					else
					{
						DialogAlert.show(context, "", "没有更多题了！", null, "我知道了");
					}
				}
			}
		});
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
			request.topicId = topicRecord.topicId;
			request.fav = (topicRecord != null && topicRecord.fav == 1) ? 0 : 1;
			Protocol.doPost(context, App.getApi(), SHandleId.TopicRecordSetFav, request.saveToStr(), new Protocol.OnCallback()
			{
				@Override
				public void onResponse(int code, String data, String msg)
				{
					if (code == 200)
					{
						SResponse_TopicRecordSetFav response = SResponse_TopicRecordSetFav.load(data);
						topicRecord.fav = (response.topicRecord != null && response.topicRecord.fav == 1) ? 1 : 0;
						updateFav();
					}
				}
			});
		}
		else if (view.getId() == R.id.prev)
		{
			prevTopic();
		}
		else if (view.getId() == R.id.next)
		{
			nextTopic();
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
	}

	private void prevTopic()
	{
		holder.board.waitSyncOver();
		request(true);
	}

	private void nextTopic()
	{
		holder.board.waitSyncOver();
		request(false);
	}

	//------------------- 点击事件处理（结束） --------------------------

}
