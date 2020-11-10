package com.lys.kit.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.lys.base.utils.AppDataTool;
import com.lys.base.utils.LOG;
import com.lys.kit.R;
import com.lys.kit.config.Config;
import com.lys.kit.dialog.DialogAlert;
import com.lys.kit.dialog.DialogWait;
import com.lys.kit.manager.AudioManager;
import com.lys.kit.utils.CodeHelper;
import com.lys.kit.utils.Protocol;
import com.lys.kit.view.MediaList;
import com.lys.protobuf.SErrorCode;
import com.lys.protobuf.SHandleId;
import com.lys.protobuf.SRequest_GetTopicProblem;
import com.lys.protobuf.SResponse_GetTopicProblem;
import com.lys.protobuf.STopicFilter;

import java.util.ArrayList;
import java.util.List;

public class ActivityTopicSelect extends KitActivity implements View.OnClickListener
{
	private class Holder
	{
		private ImageView collect;
		private MediaList problemCon;
		private MediaList parseCon;
	}

	private Holder holder = new Holder();

	private void initHolder()
	{
		holder.collect = findViewById(R.id.collect);
		holder.problemCon = findViewById(R.id.problemCon);
		holder.parseCon = findViewById(R.id.parseCon);
	}

	private STopicFilter topicFilter = null;
	private boolean isJpg = false;
	private SResponse_GetTopicProblem detail = null;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_topic_select);

		initHolder();

		topicFilter = STopicFilter.load(getIntent().getStringExtra("filter"));
		isJpg = getIntent().getBooleanExtra("isJpg", false);

		findViewById(R.id.errorReport).setOnClickListener(this);
		findViewById(R.id.collect).setOnClickListener(this);
		findViewById(R.id.next).setOnClickListener(this);
		findViewById(R.id.result).setOnClickListener(this);

		request();
	}

	@Override
	protected void onPause()
	{
		super.onPause();
		AudioManager.instance().release();
	}

	@Override
	public void onClick(View view)
	{
		if (view.getId() == R.id.errorReport)
		{
			if (detail != null)
			{
//				SRequest_SetResourceStateLogic request = new SRequest_SetResourceStateLogic();
//				request.id = detail.problem.id;
//				Protocol.doPost(this, SHandleId.SetResourceStateLogic, request.saveToStr(), new Protocol.OnCallback()
//				{
//					@Override
//					public void onResponse(int code, String data, String msg)
//					{
//						if (code == 200)
//						{
//							LOG.toast(context, "上报成功");
//						}
//						else
//						{
//							LOG.toast(context, "上报失败");
//						}
//					}
//				});
			}
			else
			{
				LOG.toast(this, "数据未加载");
			}
		}
		else if (view.getId() == R.id.collect)
		{
			if (detail != null)
			{
//				final int isCollect = detail.problem.isCollect == 1 ? 0 : 1;
//				SRequest_TopicCollect request = new SRequest_TopicCollect();
//				request.personId = Config.getPersonId(this);
//				request.subject = topicFilter.subject;
//				request.sourceId = detail.problem.id;
//				request.collect = isCollect;
//				Protocol.doPost(this, SHandleId.SetTopicCollectResourceLogic, request.saveToStr(), new Protocol.OnCallback()
//				{
//					@Override
//					public void onResponse(int code, String data, String msg)
//					{
//						if (code == 200)
//						{
//							SResponse_TopicCollect response = SResponse_TopicCollect.load(data);
//							detail.problem.isCollect = isCollect;
//							if (detail.problem.isCollect == 1)
//								holder.collect.setImageResource(R.drawable.img_fav_checked);
//							else
//								holder.collect.setImageResource(R.drawable.img_fav_normal);
//						}
//						else
//						{
//							LOG.toast(context, "收藏失败");
//						}
//					}
//				});
			}
			else
			{
				LOG.toast(this, "数据未加载");
			}
		}
		else if (view.getId() == R.id.next)
		{
			request();
		}
		else if (view.getId() == R.id.result)
		{
			List<String> paths = new ArrayList<>();
			if (isJpg)
			{
				holder.problemCon.captureView(Config.tmpJpgFile, Bitmap.CompressFormat.JPEG);
				holder.parseCon.captureView(Config.tmpJpgFile2, Bitmap.CompressFormat.JPEG);
				paths.add(Config.tmpJpgFile.getAbsolutePath());
				paths.add(Config.tmpJpgFile2.getAbsolutePath());
			}
			else
			{
				holder.problemCon.captureView(Config.tmpPngFile, Bitmap.CompressFormat.PNG);
				holder.parseCon.captureView(Config.tmpPngFile2, Bitmap.CompressFormat.PNG);
				paths.add(Config.tmpPngFile.getAbsolutePath());
				paths.add(Config.tmpPngFile2.getAbsolutePath());
			}
			Intent intent = new Intent();
			intent.putExtra("result", AppDataTool.saveStringList(paths).toString());
			setResult(Activity.RESULT_OK, intent);
			finish();
		}
	}

	private void request()
	{
		new CodeHelper.OnRetryListener()
		{
			@Override
			public CodeHelper.OnRetryListener retryListener()
			{
				return this;
			}

			@Override
			public void start()
			{
				DialogWait.show(context, "加载数据");
				requestImpl(this);
			}

			@Override
			public void success()
			{
				DialogWait.close();
			}

			@Override
			public void fail(int code, String message)
			{
				DialogWait.close();
				LOG.toast(context, message);
				if (code == SErrorCode.getproblemmore_error)
				{
					DialogAlert.show(context, message, null, null, "我知道了");
				}
				else
				{
					DialogAlert.show(context, message + "，是否重试？", null, new DialogAlert.OnClickListener()
					{
						@Override
						public void onClick(int which)
						{
							if (which == 0)
							{
								ActivityTopicSelect.super.finish();
							}
							else if (which == 1)
							{
								start();
							}
						}
					}, "退出", "重试");
				}
			}
		}.start();
	}

	private void requestImpl(final CodeHelper.OnRetryListener retryListener)
	{
		SRequest_GetTopicProblem request = new SRequest_GetTopicProblem();
//		request.personId = Config.getPersonId(this);
		request.subject = topicFilter.subject;
		request.knowledges = topicFilter.knowledgeCodes;
		request.style = topicFilter.style;
		request.diff = topicFilter.diff;
		request.currProblemId = (detail != null ? detail.problem.id : null);
		Protocol.doPost(this, SHandleId.GetTopicRandProblemLogic, request.saveToStr(), new Protocol.OnCallback()
		{
			@Override
			public void onResponse(int code, String data, String msg)
			{
				if (code == 200)
				{
					detail = SResponse_GetTopicProblem.load(data);
					retryListener.success();
					showDetailInit();
				}
				else
				{
					retryListener.fail(code, msg);
				}
			}
		});
	}

	private void showDetailInit()
	{
		if (detail.problem.isCollect == 1)
			holder.collect.setImageResource(R.drawable.img_fav_checked);
		else
			holder.collect.setImageResource(R.drawable.img_fav_normal);

		holder.problemCon.setData(detail.problem.problemMedias, null, null);
		holder.problemCon.scrollToPosition(0);

		holder.parseCon.setData(detail.problem.parseMedias, null, null);
		holder.parseCon.scrollToPosition(0);
	}

}
