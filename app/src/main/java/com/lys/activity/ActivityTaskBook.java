package com.lys.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lys.App;
import com.lys.adapter.AdapterRoomUser;
import com.lys.adapter.AdapterTaskPage;
import com.lys.app.R;
import com.lys.base.utils.CommonUtils;
import com.lys.base.utils.FsUtils;
import com.lys.base.utils.GZipUtil;
import com.lys.base.utils.JsonHelper;
import com.lys.base.utils.LOG;
import com.lys.base.utils.LOGJson;
import com.lys.base.utils.SysUtils;
import com.lys.base.utils.VideoLoader;
import com.lys.base.view.MyViewPager;
import com.lys.board.config.BoardConfig;
import com.lys.board.dawing.LysBoardDrawing;
import com.lys.config.AppConfig;
import com.lys.dialog.DialogSelectFriend;
import com.lys.dialog.DialogStudentConfirm;
import com.lys.dialog.DialogStudentQuestion;
import com.lys.dialog.DialogTaskPageGrid;
import com.lys.dialog.DialogTeacherQuestion;
import com.lys.fragment.FragmentTaskPage;
import com.lys.kit.activity.KitActivity;
import com.lys.kit.config.Config;
import com.lys.kit.dialog.DialogAlert;
import com.lys.kit.dialog.DialogTimeWait;
import com.lys.kit.dialog.DialogWait;
import com.lys.kit.pop.PopInsert;
import com.lys.kit.utils.ImageLoad;
import com.lys.kit.utils.Protocol;
import com.lys.kit.view.BoardToolBar;
import com.lys.kit.view.BoardView;
import com.lys.kit.view.PhotoView;
import com.lys.message.BoardMessage;
import com.lys.message.TransMessage;
import com.lys.protobuf.SBoardConfig;
import com.lys.protobuf.SBoardPhoto;
import com.lys.protobuf.SBoardText;
import com.lys.protobuf.SCheckState;
import com.lys.protobuf.SClipboard;
import com.lys.protobuf.SClipboardType;
import com.lys.protobuf.SFilePath;
import com.lys.protobuf.SHandleId;
import com.lys.protobuf.SNotePage;
import com.lys.protobuf.SNotePageSet;
import com.lys.protobuf.SOperation;
import com.lys.protobuf.SOperationType;
import com.lys.protobuf.SPJobType;
import com.lys.protobuf.SPTask;
import com.lys.protobuf.SPTaskState;
import com.lys.protobuf.SPTaskType;
import com.lys.protobuf.SPhotoAddParam;
import com.lys.protobuf.SRequest_FileDelete;
import com.lys.protobuf.SRequest_FileList;
import com.lys.protobuf.SRequest_GetTask;
import com.lys.protobuf.SRequest_SetTaskState;
import com.lys.protobuf.SRequest_TeachOverByStudent;
import com.lys.protobuf.SRequest_TeachOverByTeacher;
import com.lys.protobuf.SRequest_TeachStart;
import com.lys.protobuf.SResponse_FileDelete;
import com.lys.protobuf.SResponse_FileList;
import com.lys.protobuf.SResponse_GetTask;
import com.lys.protobuf.SResponse_SetTaskState;
import com.lys.protobuf.SSelectionGroup;
import com.lys.protobuf.SSvnDirObj;
import com.lys.protobuf.STeachPage;
import com.lys.protobuf.STeachState;
import com.lys.protobuf.SUser;
import com.lys.utils.Helper;
import com.lys.utils.LysIM;
import com.lys.utils.LysUpload;
import com.lys.utils.SVNManager;

import java.io.File;
import java.io.FileFilter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

import cn.rongcloud.rtc.RTCErrorCode;
import cn.rongcloud.rtc.RongRTCEngine;
import cn.rongcloud.rtc.callback.JoinRoomUICallBack;
import cn.rongcloud.rtc.callback.RongRTCResultCallBack;
import cn.rongcloud.rtc.callback.RongRTCResultUICallBack;
import cn.rongcloud.rtc.events.RongRTCEventsListener;
import cn.rongcloud.rtc.room.RongRTCRoom;
import cn.rongcloud.rtc.stream.local.RongRTCCapture;
import cn.rongcloud.rtc.stream.remote.RongRTCAVInputStream;
import cn.rongcloud.rtc.user.RongRTCRemoteUser;
import io.rong.imkit.RongIM;
import io.rong.imlib.IRongCallback;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.message.TextMessage;

public class ActivityTaskBook extends KitActivity implements View.OnClickListener, ViewPager.OnPageChangeListener, RongRTCEventsListener
{
	private static final boolean isLimitGuest = true; // 是否限制宾客操作

	private static final int Mode_None = 0; // 不同步
	private static final int Mode_Sync = 1; // 同步

	public static void goinWithNone(Context context, SPTask task)
	{
		goinWithNone(context, task, false);
	}

	public static void goinWithNone(Context context, SPTask task, boolean cacheVideo)
	{
		Intent intent = new Intent(context, ActivityTaskBook.class);
		intent.putExtra("mode", Mode_None);
		intent.putExtra("task", task.saveToStr());
		intent.putExtra("cacheVideo", cacheVideo);
		context.startActivity(intent);
	}

	public static void goinWithSyncHost(Context context, SPTask task, List<SUser> targets)
	{
		Intent intent = new Intent(context, ActivityTaskBook.class);
		intent.putExtra("mode", Mode_Sync);
		intent.putExtra("task", task.saveToStr());
		intent.putExtra("isHost", true);
		intent.putExtra("targets", SUser.saveList(targets).toString());
		context.startActivity(intent);
	}

	public static void goinWithSyncGuest(Context context, SPTask task, String targetId, String teachInstanceId)
	{
		Intent intent = new Intent(context, ActivityTaskBook.class);
		intent.putExtra("mode", Mode_Sync);
		intent.putExtra("task", task.saveToStr());
		intent.putExtra("isHost", false);
		intent.putExtra("targetId", targetId);
		intent.putExtra("teachInstanceId", teachInstanceId);
		context.startActivity(intent);
	}

	private class Holder
	{
		private BoardToolBar toolBar;

		private View lockWriteMask;

//		private LinearLayout videoContainer;

		private TextView pageNumber;
		private TextView mode;
		private TextView beiZhu;
		private TextView testCommit;
		private TextView commitJob;
		private TextView readOver;
		private TextView startTeach;
		private TextView userList;

		//		private CheckBox muteVideo;
		private CheckBox muteAudio;
		private CheckBox lockWrite;

		private ViewGroup userCon;

		private CheckBox speakerphone;

		private TextView debugInfo;
	}

	private Holder holder = new Holder();

	private void initHolder()
	{
		holder.toolBar = findViewById(R.id.toolBar);

		holder.lockWriteMask = findViewById(R.id.lockWriteMask);

//		holder.videoContainer = findViewById(R.id.videoContainer);

		holder.pageNumber = findViewById(R.id.pageNumber);
		holder.mode = findViewById(R.id.mode);
		holder.beiZhu = findViewById(R.id.beiZhu);
		holder.testCommit = findViewById(R.id.testCommit);
		holder.commitJob = findViewById(R.id.commitJob);
		holder.readOver = findViewById(R.id.readOver);
		holder.startTeach = findViewById(R.id.startTeach);
		holder.userList = findViewById(R.id.userList);

//		holder.muteVideo = findViewById(R.id.muteVideo);
		holder.muteAudio = findViewById(R.id.muteAudio);
		holder.lockWrite = findViewById(R.id.lockWrite);

		holder.userCon = findViewById(R.id.userCon);

		holder.speakerphone = findViewById(R.id.speakerphone);

		holder.debugInfo = findViewById(R.id.debugInfo);
	}

	private boolean hasModify = false;

	public void modifyed()
	{
		hasModify = true;
		holder.testCommit.setEnabled(true);
	}

	private Integer mode = Mode_None;
	private SPTask task;
	private Boolean cacheVideo;
	private Boolean isHost; // 同步模式下，是否是发起人
	private String mTargetId;
	private Map<String, SUser> mTargetMap;
	private String mTeachInstanceId;

	public SUser getTarget(String targetId)
	{
		if (mTargetMap.containsKey(targetId))
			return mTargetMap.get(targetId);
		else
			return null;
	}

//	private long remoteVersion = 0; // 记录的云端时间（时间就是版本）

	private MyViewPager viewPager;
	private AdapterTaskPage adapter;

	private RecyclerView remotesRecyclerView;
	private AdapterRoomUser remotesAdapter;

	// 是否同步画布
	public boolean modeIsSync()
	{
		return mode.equals(Mode_Sync);
	}

	public boolean modeIsNote()
	{
		return task.id.endsWith("-笔记");
	}

	public boolean modeIsWrong()
	{
		return task.id.endsWith("-错题");
	}

	// 是否是自己的任务
	public boolean isSelfTask()
	{
		return task.userId.equals(App.userId());
	}

	public boolean isSender()
	{
		return AppConfig.hasSender(task) && task.sendUser.id.equals(App.userId());
	}

	// 是否是自己的作业
	private boolean isSelfJob()
	{
		return isSelfTask() && App.isStudent() && !modeIsSync() && task.type.equals(SPTaskType.Job);
	}

	// 退出时是否提交
	public boolean shouldCommitAtExit()
	{
		if (isSelfJob())
		{
			if (task.state.equals(SPTaskState.JobOver))
				return false;
			else
				return true;
		}
		else
		{
			if (isSelfTask())
				return true;
			if (isSender())
				return true;
			if (modeIsNote())
				return true;
			if (modeIsWrong())
				return true;
			return false;
		}
	}

	// 是否显示提交作业按钮
	public boolean showCommitJob()
	{
		return isSelfJob() && task.state.equals(SPTaskState.None);
	}

	public boolean alreadyCommitJob()
	{
		return isSelfJob() && task.state.equals(SPTaskState.JobOver);
	}

	// 是否显示批阅完成按钮
	public boolean showReadOver()
	{
		return isSender() && task.state.equals(SPTaskState.JobOver);
	}

	// 是否限制操作
	public boolean isLimitOperation()
	{
		return modeIsSync() && !isHost && isLimitGuest;
	}

	public void setBeiZhuState(boolean hasBeiZhu)
	{
		if (hasBeiZhu)
			holder.beiZhu.setTextColor(Color.GREEN);
		else
			holder.beiZhu.setTextColor(Color.WHITE);
	}

	private List<STeachPage> teachPages = new ArrayList<STeachPage>();
	private SNotePage currPage = null;
	private long goinPageTime = 0;

	private STeachPage getTeachPage(SNotePage page)
	{
		if (page != null)
		{
			for (STeachPage teachPage : teachPages)
			{
				if (teachPage.name.equals(page.pageDir))
					return teachPage;
			}
		}
		return null;
	}

	private void startRecordPage(int position)
	{
		currPage = getNotePage(position);
		if (currPage != null)
		{
			goinPageTime = System.currentTimeMillis();

			STeachPage teachPage = getTeachPage(currPage);
			if (teachPage == null)
			{
				teachPage = new STeachPage();
				teachPage.name = currPage.pageDir;
				teachPage.index = position;
				teachPage.time = 0L;
				teachPages.add(teachPage);
			}
		}
	}

	private void goinPage(int position)
	{
		try
		{
			stopRecordPage();
			startRecordPage(position);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private void stopRecordPage()
	{
		STeachPage teachPage = getTeachPage(currPage);
		if (teachPage != null)
		{
			long dtTime = System.currentTimeMillis() - goinPageTime;
			teachPage.time += dtTime;
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void init()
	{
		super.init();
		setContentView(R.layout.activity_task_book);

		initHolder();

		if (true)
		{
			viewPager = findViewById(R.id.viewPager);

			float scaleX = 1.0f * SysUtils.screenWidth(context) / AppConfig.BoardStdWidth;
			float scaleY = 1.0f * SysUtils.screenHeight(context) / AppConfig.BoardStdHeight;

			float scale = Math.min(scaleX, scaleY);

			int spaceX = (int) Math.max(0, (SysUtils.screenWidth(context) - AppConfig.BoardStdWidth * scale) / 2);
			int spaceY = (int) Math.max(0, (SysUtils.screenHeight(context) - AppConfig.BoardStdHeight * scale) / 2);

			RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) viewPager.getLayoutParams();
			layoutParams.leftMargin = spaceX;
			layoutParams.rightMargin = spaceX;
			layoutParams.topMargin = spaceY;
			layoutParams.bottomMargin = spaceY;
			viewPager.setLayoutParams(layoutParams);
		}

//		findViewById(R.id.close).setVisibility(shouldShowClose() ? View.VISIBLE : View.GONE);
//		findViewById(R.id.close).setOnClickListener(this);

		mode = getIntent().getIntExtra("mode", Mode_None);
		task = SPTask.load(getIntent().getStringExtra("task"));
		cacheVideo = getIntent().getBooleanExtra("cacheVideo", false);
		if (getIntent().hasExtra("isHost"))
			isHost = getIntent().getBooleanExtra("isHost", false);
		if (getIntent().hasExtra("targetId"))
			mTargetId = getIntent().getStringExtra("targetId");
		if (getIntent().hasExtra("targets"))
		{
			List<SUser> targets = SUser.loadList(JsonHelper.getJSONArray(getIntent().getStringExtra("targets")));
			mTargetMap = new HashMap<>();
			for (SUser target : targets)
			{
				mTargetMap.put(target.id, target);
			}
		}
		if (getIntent().hasExtra("teachInstanceId"))
			mTeachInstanceId = getIntent().getStringExtra("teachInstanceId");

		holder.debugInfo.setText(String.format("%s(%s)", App.userId(), App.name()));

		findViewById(R.id.beiZhu).setOnClickListener(this);
		findViewById(R.id.testCommit).setOnClickListener(this);
		findViewById(R.id.commitJob).setOnClickListener(this);
		findViewById(R.id.readOver).setOnClickListener(this);
		findViewById(R.id.startTeach).setOnClickListener(this);
		findViewById(R.id.userList).setOnClickListener(this);

		findViewById(R.id.userCon).setOnClickListener(this);

		findViewById(R.id.allVoiceClose).setOnClickListener(this);
		findViewById(R.id.allVoiceOpen).setOnClickListener(this);
		findViewById(R.id.allPenClose).setOnClickListener(this);
		findViewById(R.id.allPenOpen).setOnClickListener(this);
		findViewById(R.id.allCheck).setOnClickListener(this);

		if (App.isStudent())
		{
			holder.toolBar.setInsert(true, PopInsert.IconImageTopic, PopInsert.IconImageSelectionGroup);
			holder.toolBar.setWeike(false);
		}
		else
		{
			holder.beiZhu.setVisibility(View.VISIBLE);
		}

		holder.toolBar.setListener(toolBarListener);

		if (modeIsNote())
		{
			Helper.addEvent(context, App.userId(), AppConfig.EventAction_InTask, task.id, String.format("进入《%s》", task.name));
			initialize();
		}
		else if (modeIsWrong())
		{
			Helper.addEvent(context, App.userId(), AppConfig.EventAction_InTask, task.id, String.format("进入《%s》", task.name));
			initialize();
		}
		else
		{
			SRequest_GetTask request = new SRequest_GetTask();
			request.taskId = task.id;
			Protocol.doPost(context, App.getApi(), SHandleId.GetTask, request.saveToStr(), new Protocol.OnCallback()
			{
				@Override
				public void onResponse(int code, String data, String msg)
				{
					if (code == 200)
					{
						SResponse_GetTask response = SResponse_GetTask.load(data);
						task = response.task;
						Helper.addEvent(context, App.userId(), AppConfig.EventAction_InTask, task.id, String.format("进入《%s》", task.name));
						initialize();
					}
					else
					{
						LOG.toast(context, "获取任务数据失败");
					}
				}
			});
		}

	}

	private void initialize()
	{
		viewPager = findViewById(R.id.viewPager);
		adapter = new AdapterTaskPage(getSupportFragmentManager(), this, task);
		viewPager.setAdapter(adapter);

		viewPager.addOnPageChangeListener(this);

//		try
//		{
//			LOG.v("native_profileBegin");
//			IjkMediaPlayer.loadLibrariesOnce(null);
//			IjkMediaPlayer.native_profileBegin("libijkplayer.so");
//		}
//		catch (Exception e)
//		{
//			e.printStackTrace();
//		}

		if (isLimitOperation())
		{
			holder.toolBar.hideIconGrid();
			holder.toolBar.hideIconAdd();
			holder.toolBar.hideIconAddPage();
			viewPager.setLock(true);
		}

//		if (SysUtils.isDebug())
//			holder.testCommit.setVisibility(View.VISIBLE);
//
//		if (showCommitJob())
//			holder.commitJob.setVisibility(View.VISIBLE);
//
//		if (showReadOver())
//			holder.readOver.setVisibility(View.VISIBLE);

		// 不让打开缩略图的原因是缩略图可能有选项的正确答案
		if (showCommitJob())
			holder.toolBar.hideIconGrid();

		if (alreadyCommitJob())
			holder.toolBar.setVisibility(View.GONE);

		if (!App.isStudent() && !modeIsSync() && (isSelfTask() || isSender() || modeIsNote() || modeIsWrong()))
			holder.startTeach.setVisibility(View.VISIBLE);

		if (modeIsSync())
		{
			holder.mode.setText(String.format("上课模式（%s）", App.name()));

//			holder.toolBar.showMuteAudio();

			holder.toolBar.setKeepScreenOn(true);

			holder.toolBar.setInsert(true, PopInsert.IconImageScreen, PopInsert.IconImageVideo);
			holder.toolBar.setWeike(false);

			remotesRecyclerView = findViewById(R.id.remotes);
			remotesRecyclerView.setLayoutManager(new LinearLayoutManager(context));
			remotesAdapter = new AdapterRoomUser(this);
			remotesRecyclerView.setAdapter(remotesAdapter);

			if (isHost)
			{
				holder.userList.setVisibility(View.VISIBLE);
//				holder.muteVideo.setVisibility(View.VISIBLE);
				holder.muteAudio.setVisibility(View.VISIBLE);
				holder.lockWrite.setVisibility(View.GONE);
			}
			else
			{
				holder.userList.setVisibility(View.GONE);
//				holder.muteVideo.setVisibility(View.VISIBLE);
				holder.muteAudio.setVisibility(View.VISIBLE);
				holder.lockWrite.setVisibility(View.VISIBLE);
			}

			IntentFilter filter = new IntentFilter();

			filter.addAction(LysIM.ActionTrans(context, AppConfig.TransEvt_TeachRefuse));
			filter.addAction(LysIM.ActionTrans(context, AppConfig.TransEvt_TeachAgree));
			filter.addAction(LysIM.ActionTrans(context, AppConfig.TransEvt_TeachReady));
			filter.addAction(LysIM.ActionTrans(context, AppConfig.TransEvt_TeachStart));
			filter.addAction(LysIM.ActionTrans(context, AppConfig.TransEvt_TeachOver));
			filter.addAction(LysIM.ActionTrans(context, AppConfig.TransEvt_TeachQuit));

			filter.addAction(LysIM.ActionTrans(context, AppConfig.TransEvt_TeachNotify_MuteAudio));
			filter.addAction(LysIM.ActionTrans(context, AppConfig.TransEvt_TeachNotify_LockWrite));
			filter.addAction(LysIM.ActionTrans(context, AppConfig.TransEvt_TeachNotify_Check));

//			filter.addAction(LysIM.ActionTrans(context, AppConfig.TransEvt_TeachSyncRequest));
//			filter.addAction(LysIM.ActionTrans(context, AppConfig.TransEvt_TeachSyncDownload));
//			filter.addAction(LysIM.ActionTrans(context, AppConfig.TransEvt_TeachSyncSuccess));
//			filter.addAction(LysIM.ActionTrans(context, AppConfig.TransEvt_TeachSyncFail));

			registerReceiver(mReceiver, filter);
		}
		else
		{
			holder.mode.setText("");
		}

		if (modeIsSync() && !isHost)
		{
			downloadAtBeginWithSync();
		}
		else
		{
			checkTaskFileExistsAtBegin();
		}
	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		if (modeIsSync())
		{
			try
			{
				unregisterReceiver(mReceiver);
			}
			catch (Exception e)
			{
				LOG.v("mReceiver 未注册");
			}
		}
//		LOG.v("native_profileEnd");
//		IjkMediaPlayer.native_profileEnd();
		quitRoom();
	}

	@Override
	public void finish()
	{
//		if (getCurrFragmentPage() != null)
//			getCurrFragmentPage().genSmallAndBig(false);
		if (task != null)
			Helper.addEvent(context, App.userId(), AppConfig.EventAction_OutTask, task.id, String.format("退出《%s》", task.name));
		if (modeIsSync())
		{
			if (isHost)
			{
				stopRecordPage();
				teacherTeachOver();
			}
			else
			{
				studentTeachOver();
			}
		}
		else
		{
			super.finish();
		}
	}

	private void teacherTeachOver()
	{
		SRequest_TeachOverByTeacher request = new SRequest_TeachOverByTeacher();
		request.teachId = mTeachInstanceId;
		request.userId = App.userId();
		request.teachPages = teachPages;
		Protocol.doPost(context, App.getApi(), SHandleId.TeachOverByTeacher, request.saveToStr(), new Protocol.OnCallback()
		{
			@Override
			public void onResponse(int code, String data, String msg)
			{
				if (code == 200)
				{
					DialogTeacherQuestion.show(context, mTeachInstanceId, App.userId(), new DialogTeacherQuestion.OnListener()
					{
						@Override
						public void onResult()
						{
							ActivityTaskBook.super.finish();
						}
					});
				}
				else
				{
					DialogAlert.show(context, "", "课程结束失败，请点击重试！", new DialogAlert.OnClickListener()
					{
						@Override
						public void onClick(int which)
						{
							teacherTeachOver();
						}
					}, "重试");
				}
			}
		});
	}

	private void studentTeachOver()
	{
		SRequest_TeachOverByStudent request = new SRequest_TeachOverByStudent();
		request.teachId = mTeachInstanceId;
		request.userId = App.userId();
		request.targetId = mTargetId;
		Protocol.doPost(context, App.getApi(), SHandleId.TeachOverByStudent, request.saveToStr(), new Protocol.OnCallback()
		{
			@Override
			public void onResponse(int code, String data, String msg)
			{
				if (code == 200)
				{
					DialogStudentConfirm.show(context, mTeachInstanceId, App.userId(), mTargetId, mStartTime, new DialogStudentConfirm.OnListener()
					{
						@Override
						public void onResult()
						{
							DialogStudentQuestion.show(context, mTeachInstanceId, App.userId(), mTargetId, new DialogStudentQuestion.OnListener()
							{
								@Override
								public void onResult()
								{
									ActivityTaskBook.super.finish();
								}
							});
						}
					});
				}
				else
				{
					DialogAlert.show(context, "", "课程结束失败，请点击重试！", new DialogAlert.OnClickListener()
					{
						@Override
						public void onClick(int which)
						{
							studentTeachOver();
						}
					}, "重试");
				}
			}
		});
	}

	private boolean toClose()
	{
		if (pageset != null)
		{
			close(false);
			return true;
		}
		return false;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if (keyCode == KeyEvent.KEYCODE_BACK)
		{
			if (toClose())
				return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	// 同步模式下的关闭逻辑
	private void doClose()
	{
		if (isHost)
		{
			for (SUser target : mTargetMap.values())
			{
				if (target.teachState.equals(STeachState.Ready))
				{
					TransMessage.send(target.id, TransMessage.obtain(AppConfig.TransEvt_TeachOver, mTeachInstanceId), null);
				}
			}
			Helper.addEvent(context, App.userId(), AppConfig.EventAction_TeachOver, mTeachInstanceId, String.format("课程主动结束！耗时：%s", CommonUtils.formatTime(System.currentTimeMillis() - mStartTime)));
			if (getCurrFragmentPage() != null)
				getCurrFragmentPage().genSmallAndBig(false);
			checkTaskFileExistsAtEnd();
		}
		else
		{
			TransMessage.send(mTargetId, TransMessage.obtain(AppConfig.TransEvt_TeachQuit, mTeachInstanceId), null);
			Helper.addEvent(context, App.userId(), AppConfig.EventAction_TeachQuit, mTeachInstanceId, String.format("我主动退出了课程！耗时：%s", CommonUtils.formatTime(System.currentTimeMillis() - mStartTime)));
			if (getCurrFragmentPage() != null)
				getCurrFragmentPage().genSmallAndBig(false);
			finish();
		}
	}

	// force为true时，跳过询问直接关闭
	public void close(boolean force)
	{
		if (modeIsSync())
		{
			if (force)
			{
				doClose();
			}
			else
			{
				DialogAlert.show(context, isHost ? "是否结束课程？" : "是否退出课程？", null, new DialogAlert.OnClickListener()
				{
					@Override
					public void onClick(int which)
					{
						if (which == 1)
						{
							doClose();
						}
					}
				}, "否", isHost ? "结束" : "退出");
			}
		}
		else
		{
			if (shouldCommitAtExit() && hasModify)
			{
				if (force)
				{
					if (getCurrFragmentPage() != null)
						getCurrFragmentPage().genSmallAndBig(false);
					checkTaskFileExistsAtEnd();
				}
				else
				{
					DialogAlert.show(context, "是否提交修改并退出？", null, new DialogAlert.OnClickListener()
					{
						@Override
						public void onClick(int which)
						{
							if (which == 1)
							{
								if (getCurrFragmentPage() != null)
									getCurrFragmentPage().genSmallAndBig(false);
								checkTaskFileExistsAtEnd();
							}
						}
					}, "否", "保存并退出");
				}
			}
			else
			{
				if (getCurrFragmentPage() != null)
					getCurrFragmentPage().genSmallAndBig(false);
				finish();
			}
		}
	}

	private SNotePage getCurrNotePage()
	{
		return getNotePage(viewPager.getCurrentItem());
	}

	private SNotePage getNotePage(int position)
	{
		return pageset.pages.get(position);
	}

	private FragmentTaskPage getCurrFragmentPage()
	{
		return getFragmentPage(viewPager.getCurrentItem());
	}

	private FragmentTaskPage getFragmentPage(int position)
	{
		return adapter.fragmentMap.get(position);
	}

	public void removeFragmentPage(int position)
	{
		adapter.fragmentMap.remove(position);
	}

	private String genPageDir()
	{
		for (int i = 0; ; i++)
		{
			String pageDir = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new Date(System.currentTimeMillis() + i * 1000));
			File dir = new File(String.format("%s/%s", AppConfig.getTaskDir(task), pageDir));
			if (!dir.exists())
				return pageDir;
		}
	}

	private String addPage(int currPosition, String pageDir, File srcDir)
	{
		if (TextUtils.isEmpty(pageDir))
			pageDir = genPageDir();
		File dir = new File(String.format("%s/%s", AppConfig.getTaskDir(task), pageDir));
		dir.mkdirs();

		if (srcDir != null && srcDir.exists())
			FsUtils.copyPath(srcDir, dir, null);

		SNotePage page = new SNotePage();
		page.pageDir = pageDir;
		pageset.pages.add(currPosition + 1, page);

		savePageSet();

//		for (FragmentTaskPage fragmentPage : adapter.fragmentMap.values())
//		{
//			fragmentPage.genSmall(true);
//		}
		adapter.fragmentMap.clear();
		lastPosition = -1;
		adapter.setData(pageset.pages);
		viewPager.setAdapter(adapter);
		showPage(currPosition + 1);

		modifyed();

		return pageDir;
	}

	private void deletePageImpl(Map<String, SNotePage> deleteMap)
	{
		int currPosition = viewPager.getCurrentItem();
		String selectPageDir = null;
		if (TextUtils.isEmpty(selectPageDir))
		{
			for (int i = currPosition; i < pageset.pages.size(); i++)
			{
				SNotePage page = pageset.pages.get(i);
				if (!deleteMap.containsKey(page.pageDir))
				{
					selectPageDir = page.pageDir;
					break;
				}
			}
		}
		if (TextUtils.isEmpty(selectPageDir))
		{
			for (int i = currPosition - 1; i >= 0; i--)
			{
				SNotePage page = pageset.pages.get(i);
				if (!deleteMap.containsKey(page.pageDir))
				{
					selectPageDir = page.pageDir;
					break;
				}
			}
		}
		if (!TextUtils.isEmpty(selectPageDir))
		{
			for (SNotePage page : deleteMap.values())
			{
				File dir = new File(String.format("%s/%s", AppConfig.getTaskDir(task), page.pageDir));
				FsUtils.delete(dir);
				pageset.pages.remove(page);
			}

			savePageSet();
			adapter.fragmentMap.clear();
			lastPosition = -1;
			adapter.setData(pageset.pages);
			viewPager.setAdapter(adapter);
			showPage(selectPageDir);
		}
		else
		{
			LOG.v("删除页异常");
		}
	}

	private SNotePageSet pageset = null;

	private void loadPageSet()
	{
		File file = new File(String.format("%s/pageset.json", AppConfig.getTaskDir(task)));
		if (file.exists())
		{
			pageset = SNotePageSet.load(FsUtils.readText(file));
			adapter.setData(pageset.pages);
			showPage(0);
		}
		else
		{
			if (modeIsSync())
			{
				// 同步的情况下，不能自主创建页面，否则 pageDir 不一致，会导致同步逻辑出现问题
				DialogAlert.show(context, "页面为空，无法同步！！", null, new DialogAlert.OnClickListener()
				{
					@Override
					public void onClick(int which)
					{
						finish();
					}
				}, "退出");
			}
			else
			{
				pageset = new SNotePageSet();
				addPage(-1, null, null);
			}
		}
	}

	// 记录最后一次修改时间，只要有操作就调用
//	public void recordLastModifyTime()
//	{
//		File file = new File(String.format("%s/lastModifyTime.txt", AppConfig.getTaskDir(task)));
//		FsUtils.writeText(file, String.valueOf(System.currentTimeMillis() - App.TimeOffset));
//	}

//	public long getLastModifyTime()
//	{
//		File file = new File(String.format("%s/lastModifyTime.txt", AppConfig.getTaskDir(task)));
//		if (file.exists())
//			return Long.valueOf(FsUtils.readText(file));
//		else
//			return 0;
//	}

	private void savePageSet()
	{
		File file = new File(String.format("%s/pageset.json", AppConfig.getTaskDir(task)));
		FsUtils.writeText(file, LOGJson.getStr(pageset.saveToStr()));
	}

	private boolean showPage(String pageDir)
	{
		for (int i = 0; i < pageset.pages.size(); i++)
		{
			SNotePage page = pageset.pages.get(i);
			if (pageDir.equals(page.pageDir))
			{
				showPage(i);
				return true;
			}
		}
		return false;
	}

	private void showPage(int index)
	{
		viewPager.setCurrentItem(index);
		pageSelected(index); // 如果只有一页的时候，OnPageChangeListener不会触发，所以这里需要手动调用一次
	}

	//------------------- 点击事件处理（开始） --------------------------

	private BoardToolBar.OnListener toolBarListener = new BoardToolBar.OnListener()
	{
		@Override
		public void onIconSend()
		{
			if (App.isStudent())
			{
				getCurrFragmentPage().onSend(null);
			}
			else
			{
				String shareUrl = String.format("%s/td/%s_%s", App.getConfig().root, task.id, getCurrNotePage().pageDir);
				getCurrFragmentPage().onSend(shareUrl);
			}
		}

		@Override
		public void onIconGrid()
		{
			openGrid();
		}

		@Override
		public void onIconDelete()
		{
			deletePage(viewPager.getCurrentItem());
		}

		@Override
		public void onIconClearOver()
		{
			syncClear(getCurrNotePage().pageDir);
		}

		@Override
		public void onIconAddOver(int newHeight)
		{
			syncHeight(getCurrNotePage().pageDir, newHeight);
		}

		@Override
		public void onIconReduceOver(int newHeight)
		{
			syncHeight(getCurrNotePage().pageDir, newHeight);
		}

		@Override
		public void onAddPhoto(PhotoView photoView, byte[] bitmapData)
		{
			syncAddImage(getCurrNotePage().pageDir, photoView.photo, bitmapData);
		}

		@Override
		public void onAddVideo(PhotoView photoView, byte[] bitmapData, byte[] videoData)
		{
			syncAddVideoNet(getCurrNotePage().pageDir, photoView.photo, bitmapData, videoData);
		}

		@Override
		public void onAddTopic(PhotoView photoView, byte[] bitmapData, byte[] parseData)
		{
			syncAddTopic(getCurrNotePage().pageDir, photoView.photo, bitmapData, parseData);
		}

		@Override
		public void onAddSelectionGroup(PhotoView photoView)
		{
			syncAddSelectionGroup(getCurrNotePage().pageDir, photoView.photo);
		}

		@Override
		public void onAddBoardText(PhotoView photoView)
		{
			syncAddBoardText(getCurrNotePage().pageDir, photoView.photo);
		}

		@Override
		public void onDeleteBigVideoBefore()
		{
			modifyed();
			getCurrFragmentPage().onDeleteBigVideoBefore();
		}

		@Override
		public void onAddBigVideoOver()
		{
			modifyed();
			getCurrFragmentPage().onAddBigVideoOver();
		}

		@Override
		public void onIconAddPage()
		{
			getCurrFragmentPage().genSmallAndBig(true);
			String newPageDir = genPageDir();
			// 要先同步再添加，因为添加的时候会有 TurnPage 同步动作，这里要保证顺序
			syncAddPage(getCurrNotePage().pageDir, newPageDir);
			addPage(viewPager.getCurrentItem(), newPageDir, null);
		}
	};

	public void onPaste()
	{
		SClipboard clipboard = Config.readClipboard();
		if (clipboard != null && clipboard.type.equals(SClipboardType.BoardPages))
		{
			File srcTaskDir = new File(clipboard.data1);
			List<SNotePage> selectPages = SNotePage.loadList(JsonHelper.getJSONArray(clipboard.data2));
			for (SNotePage selectPage : selectPages)
			{
				modifyed();
				addPage(viewPager.getCurrentItem(), null, new File(srcTaskDir, selectPage.pageDir));
			}
		}
	}

	@Override
	public void onClick(View view)
	{
		switch (view.getId())
		{
//		case R.id.close:
//			if (!toClose())
//				finish();
//			break;
		case R.id.beiZhu:
			if (pageset != null)
			{
				if (getCurrFragmentPage() != null)
					getCurrFragmentPage().switchBeiZhu();
			}
			break;
		case R.id.testCommit:
			if (pageset != null)
			{
				if (getCurrFragmentPage() != null)
					getCurrFragmentPage().genSmallAndBig(false);
				doCommit(null);
			}
			break;
		case R.id.commitJob:
			if (pageset != null)
			{
				if (getCurrFragmentPage() != null)
					getCurrFragmentPage().genSmallAndBig(false);
				doCommit(new OnCallback()
				{
					@Override
					public void over(SVNManager.SvnTaskResult result)
					{
						if (result.resultCode == SVNManager.ResultCode_Success)
						{
							if (task.jobType.equals(SPJobType.OnlySelect))
							{
								SRequest_SetTaskState request = new SRequest_SetTaskState();
								request.taskId = task.id;
								request.state = SPTaskState.ReadOver;
								Protocol.doPost(context, App.getApi(), SHandleId.SetTaskState, request.saveToStr(), new Protocol.OnCallback()
								{
									@Override
									public void onResponse(int code, String data, String msg)
									{
										if (code == 200)
										{
											SResponse_SetTaskState response = SResponse_SetTaskState.load(data);
											Helper.addEvent(context, task.userId, AppConfig.EventAction_CommitJob, task.id, String.format("交作业《%s》", task.name));
											Helper.addEvent(context, task.userId, AppConfig.EventAction_ReadOver, task.id, String.format("已批阅《%s》", task.name));
											List<String> pageDirs = getWrongPages();
											if (pageDirs.size() > 0)
											{
												doCopyWrong(pageDirs, new OnCallback()
												{
													@Override
													public void over(SVNManager.SvnTaskResult result)
													{
														if (result.resultCode == SVNManager.ResultCode_Success)
														{
															Message message = Message.obtain(task.sendUser.id, Conversation.ConversationType.PRIVATE, TextMessage.obtain(String.format("我完成了《%s》", task.name)));
															RongIM.getInstance().sendMessage(message, null, null, new IRongCallback.ISendMessageCallback()
															{
																@Override
																public void onAttached(Message msg)
																{
																}

																@Override
																public void onSuccess(Message msg)
																{
																	LOG.v("提交成功");
																	DialogAlert.show(context, "提示", "系统已完成批阅，请退出并重新进入查看。", new DialogAlert.OnClickListener()
																	{
																		@Override
																		public void onClick(int which)
																		{
																			finish();
																		}
																	}, "我知道了");
																}

																@Override
																public void onError(Message msg, RongIMClient.ErrorCode errorCode)
																{
																	LOG.toast(context, "通知失败：" + errorCode);
																}
															});
														}
													}
												});
											}
											else
											{
												Message message = Message.obtain(task.sendUser.id, Conversation.ConversationType.PRIVATE, TextMessage.obtain(String.format("我完成了《%s》", task.name)));
												RongIM.getInstance().sendMessage(message, null, null, new IRongCallback.ISendMessageCallback()
												{
													@Override
													public void onAttached(Message msg)
													{
													}

													@Override
													public void onSuccess(Message msg)
													{
														LOG.v("提交成功");
														finish();
													}

													@Override
													public void onError(Message msg, RongIMClient.ErrorCode errorCode)
													{
														LOG.toast(context, "通知失败：" + errorCode);
													}
												});
											}
										}
									}
								});
							}
							else
							{
								SRequest_SetTaskState request = new SRequest_SetTaskState();
								request.taskId = task.id;
								request.state = SPTaskState.JobOver;
								Protocol.doPost(context, App.getApi(), SHandleId.SetTaskState, request.saveToStr(), new Protocol.OnCallback()
								{
									@Override
									public void onResponse(int code, String data, String msg)
									{
										if (code == 200)
										{
											SResponse_SetTaskState response = SResponse_SetTaskState.load(data);
											Helper.addEvent(context, task.userId, AppConfig.EventAction_CommitJob, task.id, String.format("交作业《%s》", task.name));
											Message message = Message.obtain(task.sendUser.id, Conversation.ConversationType.PRIVATE, TextMessage.obtain(String.format("我完成了《%s》", task.name)));
											RongIM.getInstance().sendMessage(message, null, null, new IRongCallback.ISendMessageCallback()
											{
												@Override
												public void onAttached(Message msg)
												{
												}

												@Override
												public void onSuccess(Message msg)
												{
													LOG.v("提交成功");
													finish();
												}

												@Override
												public void onError(Message msg, RongIMClient.ErrorCode errorCode)
												{
													LOG.toast(context, "通知失败：" + errorCode);
												}
											});
										}
									}
								});
							}
						}
						else
						{
							LOG.toast(context, "提交失败");
						}
					}
				});
			}
			break;
		case R.id.readOver:
			if (pageset != null)
			{
				if (getCurrFragmentPage() != null)
					getCurrFragmentPage().genSmallAndBig(false);
				doCommit(new OnCallback()
				{
					@Override
					public void over(SVNManager.SvnTaskResult result)
					{
						if (result.resultCode == SVNManager.ResultCode_Success)
						{
							SRequest_SetTaskState request = new SRequest_SetTaskState();
							request.taskId = task.id;
							request.state = SPTaskState.ReadOver;
							Protocol.doPost(context, App.getApi(), SHandleId.SetTaskState, request.saveToStr(), new Protocol.OnCallback()
							{
								@Override
								public void onResponse(int code, String data, String msg)
								{
									if (code == 200)
									{
										SResponse_SetTaskState response = SResponse_SetTaskState.load(data);
										Helper.addEvent(context, task.userId, AppConfig.EventAction_ReadOver, task.id, String.format("已批阅《%s》", task.name));
										List<String> pageDirs = getWrongPages();
										if (pageDirs.size() > 0)
										{
											doCopyWrong(pageDirs, new OnCallback()
											{
												@Override
												public void over(SVNManager.SvnTaskResult result)
												{
													if (result.resultCode == SVNManager.ResultCode_Success)
													{
														Message message = Message.obtain(task.userId, Conversation.ConversationType.PRIVATE, TextMessage.obtain(String.format("《%s》已批阅", task.name)));
														RongIM.getInstance().sendMessage(message, null, null, new IRongCallback.ISendMessageCallback()
														{
															@Override
															public void onAttached(Message msg)
															{
															}

															@Override
															public void onSuccess(Message msg)
															{
																LOG.v("批阅成功");
																finish();
															}

															@Override
															public void onError(Message msg, RongIMClient.ErrorCode errorCode)
															{
																LOG.toast(context, "通知失败：" + errorCode);
															}
														});
													}
												}
											});
										}
										else
										{
											Message message = Message.obtain(task.userId, Conversation.ConversationType.PRIVATE, TextMessage.obtain(String.format("《%s》已批阅", task.name)));
											RongIM.getInstance().sendMessage(message, null, null, new IRongCallback.ISendMessageCallback()
											{
												@Override
												public void onAttached(Message msg)
												{
												}

												@Override
												public void onSuccess(Message msg)
												{
													LOG.v("批阅成功");
													finish();
												}

												@Override
												public void onError(Message msg, RongIMClient.ErrorCode errorCode)
												{
													LOG.toast(context, "通知失败：" + errorCode);
												}
											});
										}
									}
								}
							});
						}
						else
						{
							LOG.toast(context, "提交失败");
						}
					}
				});
			}
			break;
		case R.id.startTeach:
			if (pageset != null)
			{
				if (isSelfTask())
				{
					DialogAlert.show(context, "建议使用学生的课程开始上课，否则学生看不到课堂笔记，是否继续？", null, new DialogAlert.OnClickListener()
					{
						@Override
						public void onClick(int which)
						{
							if (which == 1)
							{
								willStartTeach();
							}
						}
					}, "取消", "继续");
				}
				else
				{
					willStartTeach();
				}
			}
			break;
		case R.id.userList:
			if (pageset != null)
			{
				if (holder.userCon.getVisibility() == View.VISIBLE)
					holder.userCon.setVisibility(View.GONE);
				else
					holder.userCon.setVisibility(View.VISIBLE);
			}
			break;
		case R.id.userCon:
			if (pageset != null)
			{
				holder.userCon.setVisibility(View.GONE);
			}
			break;
		case R.id.allVoiceClose:
			if (pageset != null)
			{
				for (String targetId : remotesAdapter.roomUsers)
				{
					final SUser user = getTarget(targetId);
					if (user != null)
					{
						TransMessage.send(targetId, TransMessage.obtain(AppConfig.TransEvt_TeachNotify_MuteAudio, String.valueOf(true)), null);
					}
				}
			}
			break;
		case R.id.allVoiceOpen:
			if (pageset != null)
			{
				for (String targetId : remotesAdapter.roomUsers)
				{
					final SUser user = getTarget(targetId);
					if (user != null)
					{
						TransMessage.send(targetId, TransMessage.obtain(AppConfig.TransEvt_TeachNotify_MuteAudio, String.valueOf(false)), null);
					}
				}
			}
			break;
		case R.id.allPenClose:
			if (pageset != null)
			{
				for (String targetId : remotesAdapter.roomUsers)
				{
					final SUser user = getTarget(targetId);
					if (user != null)
					{
						TransMessage.send(targetId, TransMessage.obtain(AppConfig.TransEvt_TeachNotify_LockWrite, String.valueOf(true)), null);
					}
				}
			}
			break;
		case R.id.allPenOpen:
			if (pageset != null)
			{
				for (String targetId : remotesAdapter.roomUsers)
				{
					final SUser user = getTarget(targetId);
					if (user != null)
					{
						TransMessage.send(targetId, TransMessage.obtain(AppConfig.TransEvt_TeachNotify_LockWrite, String.valueOf(false)), null);
					}
				}
			}
			break;
		case R.id.allCheck:
			if (pageset != null)
			{
				readyCheckDir();
				for (String targetId : remotesAdapter.roomUsers)
				{
					final SUser user = getTarget(targetId);
					if (user != null)
					{
						user.checkState = SCheckState.Refresh;
						TransMessage.send(targetId, TransMessage.obtain(AppConfig.TransEvt_TeachNotify_Check, null), null);
					}
				}
				remotesAdapter.notifyDataSetChanged();
			}
			break;

		}
	}

	private void getCheckDir(File dir, final List<SSvnDirObj> objs)
	{
		if (dir.exists())
		{
			dir.listFiles(new FileFilter()
			{
				@Override
				public boolean accept(File file)
				{
					if (file.isDirectory())
					{
						if (!file.getName().equals(".svn"))
						{
							SSvnDirObj obj = new SSvnDirObj();
							obj.isDir = true;
							obj.name = file.getName();
							objs.add(obj);
							getCheckDir(file, obj.objs);
						}
					}
					else
					{
						if (!file.getName().equals("small.jpg") && !file.getName().equals("answer.png"))
						{
							SSvnDirObj obj = new SSvnDirObj();
							obj.isDir = false;
							obj.name = file.getName();
							obj.md5 = CommonUtils.md5(file);
							objs.add(obj);
						}
					}
					return false;
				}
			});
			Collections.sort(objs, new Comparator<SSvnDirObj>()
			{
				@Override
				public int compare(SSvnDirObj obj1, SSvnDirObj obj2)
				{
					int ret = obj1.isDir.compareTo(obj2.isDir);
					if (ret == 0)
						ret = obj1.name.compareTo(obj2.name);
					return ret;
				}
			});
		}
	}

	private List<SSvnDirObj> getCheckDir()
	{
		List<SSvnDirObj> objs = new ArrayList<>();
		getCheckDir(new File(AppConfig.getTaskDir(task)), objs);
		return objs;
	}

	private String mCheckStr = null;

	public void readyCheckDir()
	{
		List<SSvnDirObj> objs = getCheckDir();
		mCheckStr = SSvnDirObj.saveList(objs).toString();
	}

//	private TransMessage currCallMessage = null; // 当前通知消息

	private void willStartTeach()
	{
		DialogSelectFriend.show(context, App.userId(), new DialogSelectFriend.OnListener()
		{
			@Override
			public void onSelect(List<SUser> selectedList)
			{
				if (selectedList.size() > 0)
				{
					willTeach(selectedList);
				}
			}
		});
	}

	private void willTeach(final List<SUser> targets)
	{
		setFinishAction(new OnFinishAction()
		{
			@Override
			public void onFinish()
			{
				goinWithSyncHost(context, task, targets);
			}
		});
		close(true);
	}

	private void modifyState(String targetId, Integer teachState, boolean flush)
	{
		if (mTargetMap.containsKey(targetId))
		{
			SUser target = mTargetMap.get(targetId);
			target.teachState = teachState;
			if (flush)
				flushStateInfo();
			flushReadyUser();
		}
	}

	private void flushReadyUser()
	{
		List<String> roomUsers = new ArrayList<>();
		for (SUser target : mTargetMap.values())
		{
			if (target.teachState.equals(STeachState.Ready))
			{
				roomUsers.add(target.id);
			}
		}
		LOG.v("flushReadyUser : " + roomUsers.size());
		remotesAdapter.setData(roomUsers);
	}

	private int getReadyCount()
	{
		int readyCount = 0;
		for (SUser target : mTargetMap.values())
		{
			if (target.teachState.equals(STeachState.Ready))
			{
				readyCount++;
			}
		}
		return readyCount;
	}

	private boolean mAlreadyStart = false;
	private long mStartTime = 0;

	private void flushStateInfo()
	{
		int callCount = 0;
		int refuseCount = 0;
		int agreeCount = 0;
		int timeoutCount = 0;
		int readyCount = 0;
		StringBuilder sb = new StringBuilder();
		for (SUser target : mTargetMap.values())
		{
			String stateDes = null;
			if (target.teachState.equals(STeachState.Call))
			{
				stateDes = "邀请中。。。";
				callCount++;
			}
			else if (target.teachState.equals(STeachState.Refuse))
			{
				stateDes = "拒绝进入";
				refuseCount++;
			}
			else if (target.teachState.equals(STeachState.Agree))
			{
				stateDes = "正在进入。。。";
				agreeCount++;
			}
			else if (target.teachState.equals(STeachState.Timeout))
			{
				stateDes = "邀请超时";
				timeoutCount++;
			}
			else if (target.teachState.equals(STeachState.Ready))
			{
				stateDes = "已就绪";
				readyCount++;
			}
			sb.append(String.format("%s -- %s\n", target.name, stateDes));
		}
		DialogTimeWait.message(sb.toString());
		if (callCount > 0)
			return;
		long downTime = AppConfig.TeachCallWaitTime - DialogTimeWait.timeDt();
		if (downTime > 0 && agreeCount > 0)
			return;
		DialogTimeWait.stop();
		if (agreeCount > 0)
		{
			DialogTimeWait.wait(true);
			DialogTimeWait.info(String.format("还有 %s 个人未就绪，请稍等一下！", agreeCount));
		}
		else
		{
			DialogTimeWait.wait(false);
			StringBuilder sb2 = new StringBuilder();
			if (timeoutCount > 0)
				sb2.append(String.format(" %s 个超时 ", timeoutCount));
			if (refuseCount > 0)
				sb2.append(String.format(" %s 个拒绝 ", refuseCount));
			if (readyCount > 0)
				sb2.append(String.format(" %s 个就绪 ", readyCount));
			DialogTimeWait.info(sb2.toString());
		}
		if (timeoutCount > 0)
		{
			DialogTimeWait.leftListener("超时重新邀请", new DialogTimeWait.OnClickListener()
			{
				@Override
				public void onClick(DialogTimeWait dialog)
				{
					DialogTimeWait.wait(true);
					DialogTimeWait.start();
					DialogTimeWait.leftListener(null, null);
					DialogTimeWait.rightListener(null, null);
					for (SUser target : mTargetMap.values())
					{
						if (target.teachState.equals(STeachState.Timeout))
						{
							call(target.id);
							modifyState(target.id, STeachState.Call, false);
						}
					}
					flushStateInfo();
				}
			});
		}
		if (readyCount > 0)
		{
			DialogTimeWait.rightListener("开始上课", new DialogTimeWait.OnClickListener()
			{
				@Override
				public void onClick(final DialogTimeWait dialog)
				{
					SRequest_TeachStart request = new SRequest_TeachStart();
					request.teachId = mTeachInstanceId;
					request.userId = App.userId();
					for (SUser target : mTargetMap.values())
					{
						if (target.teachState.equals(STeachState.Ready))
						{
							request.targetIds.add(target.id);
						}
					}
					request.taskId = task.id;
					Protocol.doPost(context, App.getApi(), SHandleId.TeachStart, request.saveToStr(), new Protocol.OnCallback()
					{
						@Override
						public void onResponse(int code, String data, String msg)
						{
							if (code == 200)
							{
								dialog.dismiss();
								mAlreadyStart = true;
								mStartTime = System.currentTimeMillis();
//								holder.muteVideo.setChecked(!holder.muteVideo.isChecked());
//								resetVideoContainerPos();
								startRecordPage(viewPager.getCurrentItem());
								for (SUser target : mTargetMap.values())
								{
									if (target.teachState.equals(STeachState.Ready))
									{
										TransMessage.send(target.id, TransMessage.obtain(AppConfig.TransEvt_TeachStart, mTeachInstanceId), null);
									}
								}

								StringBuilder sb = new StringBuilder();
								sb.append(String.format("开始上课《%s》%s\r\n", task.name, isSelfTask() ? "老师任务" : "学生任务"));
								sb.append(String.format("任务ID：%s\r\n", task.id));
								for (SUser target : mTargetMap.values())
								{
									if (target.teachState.equals(STeachState.Ready))
									{
										sb.append(String.format("就绪对象：%s（%s）\r\n", target.name, target.id));
									}
								}
								Helper.addEvent(context, App.userId(), AppConfig.EventAction_TeachStart, mTeachInstanceId, sb.toString());
							}
						}
					});
				}
			});
		}
		else
		{
			DialogTimeWait.rightListener("退出", new DialogTimeWait.OnClickListener()
			{
				@Override
				public void onClick(DialogTimeWait dialog)
				{
					dialog.dismiss();
					finish();
				}
			});
		}
	}

	private void startTeach()
	{
		DialogTimeWait.create(context).showWait(true).setMessage("准备邀请学生。。。").setTickListener(new DialogTimeWait.OnTickListener()
		{
			@Override
			public void onTick(DialogTimeWait dialog, long timeDt)
			{
				long downTime = AppConfig.TeachCallWaitTime - timeDt;
				if (downTime > 0)
				{
					dialog.setInfo(String.format("（%s）", downTime / 1000));
				}
				else
				{
//					startTeachFail("通知超时");
					dialog.setInfo(null);
					for (SUser target : mTargetMap.values())
					{
						if (target.teachState.equals(STeachState.Call))
							target.teachState = STeachState.Timeout;
					}
					flushStateInfo();
				}
			}
		}).startTick().show();
		for (String targetId : mTargetMap.keySet())
		{
			call(targetId);
			modifyState(targetId, STeachState.Call, false);
		}
		flushStateInfo();

		StringBuilder sb = new StringBuilder();
		sb.append(String.format("邀请上课《%s》%s\r\n", task.name, isSelfTask() ? "老师任务" : "学生任务"));
		sb.append(String.format("任务ID：%s\r\n", task.id));
		for (SUser target : mTargetMap.values())
		{
			sb.append(String.format("邀请对象：%s（%s）\r\n", target.name, target.id));
		}
		Helper.addEvent(context, App.userId(), AppConfig.EventAction_TeachCall, mTeachInstanceId, sb.toString());
	}

	private void call(String targetId)
	{
		TransMessage callMessage = TransMessage.obtain(AppConfig.TransEvt_TeachCall, task.saveToStr());
		callMessage.setValidTime(AppConfig.TeachCallWaitTime);
		callMessage.addParam(App.userId());
		callMessage.addParam(App.name());
		callMessage.addParam(mTeachInstanceId);
		TransMessage.send(targetId, callMessage, null);
	}

//	private void startTeachFail(String failMsg)
//	{
//		if (currCallMessage != null)
//		{
//			LOG.toast(context, failMsg);
//			DialogTimeWait.close();
//			currCallMessage = null;
//			DialogAlert.show(context, failMsg, null, new DialogAlert.OnClickListener()
//			{
//				@Override
//				public void onClick(int which)
//				{
//					if (which == 0)
//					{
//						finish();
//					}
//					else if (which == 1)
//					{
//						startTeach();
//					}
//				}
//			}, "退出", "重新呼叫");
//		}
//	}

	private void openGrid()
	{
		if (getCurrFragmentPage() != null)
			getCurrFragmentPage().genSmallAndBig(false);

		DialogTaskPageGrid dialogPageGrid = new DialogTaskPageGrid(context, task, pageset.pages, modeIsSync());
		dialogPageGrid.setListener(new DialogTaskPageGrid.OnListener()
		{
			@Override
			public void onSelect(int index)
			{
				showPage(index);
			}

			@Override
			public void onDelete(Map<String, SNotePage> deleteMap)
			{
				syncDeletePages(getCurrNotePage().pageDir, deleteMap);
				deletePageImpl(deleteMap);
			}

			@Override
			public boolean onMove(Map<String, SNotePage> moveMap, boolean isNext)
			{
//				if (moveMap.size() != 1)
//				{
//					DialogAlert.show(context, "", "只能移动一页！", null, "我知道了");
//					return false;
//				}

				String selectPageDir = getCurrNotePage().pageDir;

				boolean hasMove = false;

				if (isNext)
				{
					for (int i = pageset.pages.size() - 2; i >= 0; i--)
					{
						SNotePage page = pageset.pages.get(i);
						if (moveMap.containsKey(page.pageDir))
						{
							pageset.pages.remove(page);
							pageset.pages.add(i + 1, page);
							hasMove = true;
						}
					}
				}
				else
				{
					for (int i = 1; i < pageset.pages.size(); i++)
					{
						SNotePage page = pageset.pages.get(i);
						if (moveMap.containsKey(page.pageDir))
						{
							pageset.pages.remove(page);
							pageset.pages.add(i - 1, page);
							hasMove = true;
						}
					}
				}

				if (hasMove)
				{
					modifyed();
					savePageSet();
					adapter.fragmentMap.clear();
					lastPosition = -1;
					adapter.setData(pageset.pages);
					viewPager.setAdapter(adapter);
					showPage(selectPageDir);
				}

				return hasMove;
			}
		});
		dialogPageGrid.show();
	}

	private void deletePage(final int currPosition)
	{
		if (pageset.pages.size() == 1)
		{
			DialogAlert.show(context, "", "至少要保留一页！", null, "我知道了");
		}
		else
		{
			DialogAlert.show(context, "确定要删除当前页吗？", "删除后不可恢复！！！", new DialogAlert.OnClickListener()
			{
				@Override
				public void onClick(int which)
				{
					if (which == 1)
					{
						Map<String, SNotePage> deleteMap = new HashMap<>();
						SNotePage page = getCurrNotePage();
						deleteMap.put(page.pageDir, page);
						syncDeletePages(getCurrNotePage().pageDir, deleteMap);
						deletePageImpl(deleteMap);
					}
				}
			}, "取消", "删除");
		}
	}

	//------------------- 点击事件处理（结束） --------------------------

	//------------------- 进入时同步检查（开始） --------------------------

	private void checkTaskFileExistsAtBegin()
	{
		downloadAtBeginWithSync();
	}

	private void downloadAtBeginWithSync()
	{
		doUpdate(new OnCallback()
		{
			@Override
			public void over(SVNManager.SvnTaskResult result)
			{
				load();
			}
		});
	}

	// 检查云端的任务文件是否存在
//	private void checkTaskFileExistsAtBegin()
//	{
//		SRequest_GetTaskFileVersion request = new SRequest_GetTaskFileVersion();
//		request.task = task;
//		Protocol.doPost(context, App.getApi(), SHandleId.GetTaskFileVersion, request.saveToStr(), new Protocol.OnCallback()
//		{
//			@Override
//			public void onResponse(int code, String data, String msg)
//			{
//				if (code == 200)
//				{
//					SResponse_GetTaskFileVersion response = SResponse_GetTaskFileVersion.load(data);
//					if (response.exists)
//					{
//						remoteVersion = response.lastModifyTime;
//						checkNeedDownload(response.lastModifyTime);
//					}
//					else
//					{
//						load();
//					}
//				}
//				else
//				{
//					DialogAlert.show(context, "网络异常，是否重试？", "提示：不推荐直接进入！！", new DialogAlert.OnClickListener()
//					{
//						@Override
//						public void onClick(int which)
//						{
//							if (which == 0)
//							{
//								load();
//							}
//							else if (which == 1)
//							{
//								checkTaskFileExistsAtBegin();
//							}
//						}
//					}, "直接进入", "重试");
//				}
//			}
//		});
//	}

	// 检查是否需要下载
//	private void checkNeedDownload(final long serverLastModifyTime)
//	{
//		buildPathMap(new OnPathMapCallback()
//		{
//			@Override
//			public void result(String root, Map<String, SFilePath> pathMap)
//			{
//				if (pathMap != null)
//				{
//					Map<String, File> fileMap = buildFileMap();
//					List<File> deleteFiles = buildDeleteFiles(pathMap, fileMap, true);
//					List<SFilePath> downloadPaths = buildDownloadPaths(pathMap, fileMap, true);
//					if (deleteFiles.size() > 0 || downloadPaths.size() > 0)
//					{
//						if (serverLastModifyTime < getLastModifyTime())
//						{
//							// 本地文件是新的，提示下载（出现这种情况，有可能是编辑过程中异常退出）
//							DialogAlert.show(context, "本地文件较新，是否覆盖下载？", null, new DialogAlert.OnClickListener()
//							{
//								@Override
//								public void onClick(int which)
//								{
//									if (which == 0)
//									{
//										downloadAtBegin();
//									}
//									else if (which == 1)
//									{
//										load();
//									}
//								}
//							}, "下载", "不下载");
//						}
//						else
//						{
//							// 云端文件是新的，直接下载
//							downloadAtBegin();
//						}
//					}
//					else
//					{
//						load();
//					}
//				}
//				else
//				{
//					DialogAlert.show(context, "网络异常，是否重试？", "提示：不推荐直接进入！！", new DialogAlert.OnClickListener()
//					{
//						@Override
//						public void onClick(int which)
//						{
//							if (which == 0)
//							{
//								load();
//							}
//							else if (which == 1)
//							{
//								checkNeedDownload(serverLastModifyTime);
//							}
//						}
//					}, "直接进入", "重试");
//				}
//			}
//		});
//	}

	// 开始时候的更新
//	private void downloadAtBegin()
//	{
//		doUpdate(new OnCallback()
//		{
//			@Override
//			public void over(boolean success)
//			{
//				if (success)
//				{
//					load();
//				}
//				else
//				{
//					DialogAlert.show(context, "更新失败，是否重试？", "提示：不推荐直接进入！！", new DialogAlert.OnClickListener()
//					{
//						@Override
//						public void onClick(int which)
//						{
//							if (which == 0)
//							{
//								load();
//							}
//							else if (which == 1)
//							{
//								downloadAtBegin();
//							}
//						}
//					}, "直接进入", "重试");
//				}
//			}
//		});
//	}

	// 开始时候的更新（学生被拉进来时的同步模式）
//	private void downloadAtBeginWithSync()
//	{
//		doUpdate(new OnCallback()
//		{
//			@Override
//			public void over(boolean success)
//			{
//				if (success)
//				{
//					load();
//				}
//				else
//				{
//					DialogAlert.show(context, "更新失败，是否重试？", "提示：不推荐直接进入！！", new DialogAlert.OnClickListener()
//					{
//						@Override
//						public void onClick(int which)
//						{
//							if (which == 0)
//							{
//								load();
//							}
//							else if (which == 1)
//							{
//								downloadAtBeginWithSync();
//							}
//						}
//					}, "直接进入", "重试");
//				}
//			}
//		});
//	}

	private void load()
	{
		loadPageSet();
		joinRoom(true);
		if (cacheVideo)
		{
			List<String> urls = new ArrayList<>();

			for (SNotePage page : pageset.pages)
			{
				File dir = new File(String.format("%s/%s", AppConfig.getTaskDir(task), page.pageDir));

				File boardFile = new File(String.format("%s/board.json", dir));
				if (boardFile.exists())
				{
					SBoardConfig board = SBoardConfig.load(FsUtils.readText(boardFile));
					for (SBoardPhoto photo : board.photos)
					{
						if (photo.type == BoardView.Type_Video)
						{
							if (photo.url.startsWith(BoardView.VideoNet))
							{
								urls.add(ImageLoad.checkUrl(photo.url.substring(BoardView.VideoNet.length())));
							}
						}
					}
				}

				File videoUrlFile = new File(String.format("%s/%s.txt", dir.getAbsolutePath(), BoardConfig.big_video));
				if (videoUrlFile.exists())
				{
					urls.add(ImageLoad.checkUrl(FsUtils.readText(videoUrlFile)));
				}
			}

			if (urls.size() > 0)
			{
				doCacheVideo(urls, 0);
			}
		}
	}

	private void doCacheVideo(final List<String> urls, final int index)
	{
		if (index < urls.size())
		{
			DialogWait.show(context, String.format("正在缓存：%s/%s", index + 1, urls.size()));
			VideoLoader.load(context, urls.get(index), new VideoLoader.OnLoad()
			{
				@Override
				public void over(File file, String url)
				{
					if (file != null)
					{
						doCacheVideo(urls, index + 1);
					}
					else
					{
						DialogWait.close();
						DialogAlert.show(context, "", String.format("缓存失败：%s/%s", index + 1, urls.size()), new DialogAlert.OnClickListener()
						{
							@Override
							public void onClick(int which)
							{
								if (which == 1)
								{
									doCacheVideo(urls, index);
								}
							}
						}, "取消", "重试");
					}
				}
			});
		}
		else
		{
			DialogWait.close();
			DialogAlert.show(context, "", String.format("缓存完成：%s", urls.size()), null, "我知道了");
		}
	}

	private void joinRoom(final boolean first)
	{
		if (modeIsSync())
		{
			RongRTCEngine.getInstance().joinRoom(task.id, new JoinRoomUICallBack()
			{
				@Override
				protected void onUiSuccess(RongRTCRoom rongRTCRoom)
				{
					LOG.v("onUiSuccess");
					joinSuccess(rongRTCRoom, first);
					if (first)
					{
						if (isHost)
						{
							mTeachInstanceId = CommonUtils.uuid();
							startTeach();
						}
						else
						{
							TransMessage.send(mTargetId, TransMessage.obtain(AppConfig.TransEvt_TeachReady, mTeachInstanceId), null);
							Helper.addEvent(context, App.userId(), AppConfig.EventAction_TeachReady, mTeachInstanceId, String.format("我已经就绪"));
							TransMessage.send(mTargetId, TransMessage.obtain(AppConfig.TransEvt_TeachNotify_MuteAudio, String.valueOf(isMuteAudio)), null);
							TransMessage.send(mTargetId, TransMessage.obtain(AppConfig.TransEvt_TeachNotify_LockWrite, String.valueOf(isLockWrite)), null);
							DialogWait.show(context, "等待老师开始上课。。。");
						}
					}
				}

				@Override
				protected void onUiFailed(RTCErrorCode rtcErrorCode)
				{
					LOG.v("onUiFailed : " + rtcErrorCode);
					DialogAlert.show(context, "加入房间失败，请检查网络并重试？", null, new DialogAlert.OnClickListener()
					{
						@Override
						public void onClick(int which)
						{
							if (which == 0)
							{
								// 加入失败一般就是没网，此时直接退出，再尝试上传逻辑没有意义
								finish();
							}
							else if (which == 1)
							{
								joinRoom(first);
							}
						}
					}, "退出", "重试");
				}
			});
		}
	}

	//------------------- 进入时同步检查（结束） --------------------------

	//------------------- 退出时同步检查（开始） --------------------------

	private void checkTaskFileExistsAtEnd()
	{
		uploadAtEnd();
	}

	private void uploadAtEnd()
	{
		doCommit(new OnCallback()
		{
			@Override
			public void over(SVNManager.SvnTaskResult result)
			{
				finish();
			}
		});
	}

	// 检查云端的任务文件是否存在
//	private void checkTaskFileExistsAtEnd()
//	{
//		SRequest_GetTaskFileVersion request = new SRequest_GetTaskFileVersion();
//		request.task = task;
//		Protocol.doPost(context, App.getApi(), SHandleId.GetTaskFileVersion, request.saveToStr(), new Protocol.OnCallback()
//		{
//			@Override
//			public void onResponse(int code, String data, String msg)
//			{
//				if (code == 200)
//				{
//					SResponse_GetTaskFileVersion response = SResponse_GetTaskFileVersion.load(data);
//					if (response.exists)
//					{
//						checkNeedUpload(response.lastModifyTime);
//					}
//					else
//					{
//						// 云端没有文件，直接上传
//						uploadAtEnd();
//					}
//				}
//				else
//				{
//					DialogAlert.show(context, "检查上传失败，是否重试？", null, new DialogAlert.OnClickListener()
//					{
//						@Override
//						public void onClick(int which)
//						{
//							if (which == 0)
//							{
//								finish();
//							}
//							else if (which == 1)
//							{
//								checkTaskFileExistsAtEnd();
//							}
//						}
//					}, "退出", "重试");
//				}
//			}
//		});
//	}

	// 检查是否需要上传
//	private void checkNeedUpload(final long serverLastModifyTime)
//	{
//		buildPathMap(new OnPathMapCallback()
//		{
//			@Override
//			public void result(String root, Map<String, SFilePath> pathMap)
//			{
//				if (pathMap != null)
//				{
//					Map<String, File> fileMap = buildFileMap();
//					List<String> deletePaths = buildDeletePaths(pathMap, fileMap, true);
//					List<File> uploadFiles = buildUploadFiles(pathMap, fileMap, true);
//					if (deletePaths.size() > 0 || uploadFiles.size() > 0)
//					{
//						if (serverLastModifyTime > getLastModifyTime())
//						{
//							// 云端文件是新的，提示上传（出现这种情况，有可能是别人已经上传过）
//							DialogAlert.show(context, "云端文件较新，是否覆盖上传？", null, new DialogAlert.OnClickListener()
//							{
//								@Override
//								public void onClick(int which)
//								{
//									if (which == 0)
//									{
//										uploadAtEnd();
//									}
//									else if (which == 1)
//									{
//										finish();
//									}
//								}
//							}, "上传", "不上传");
//						}
//						else
//						{
//							if (remoteVersion != serverLastModifyTime)
//							{
//								// 虽然本地文件是新的，但是这段时间有可能有人上传了之前做过的修改（云端时间是以修改时的本机时间为准，而不是以上传时间为准），所以也需要提示上传
//								DialogAlert.show(context, "云端文件有可能被别人修改，是否覆盖上传？", null, new DialogAlert.OnClickListener()
//								{
//									@Override
//									public void onClick(int which)
//									{
//										if (which == 0)
//										{
//											uploadAtEnd();
//										}
//										else if (which == 1)
//										{
//											finish();
//										}
//									}
//								}, "上传", "不上传");
//							}
//							else
//							{
//								// 本地文件是新的，并且这段时间没有人修改云端文件，直接上传
//								uploadAtEnd();
//							}
//						}
//					}
//					else
//					{
//						finish();
//					}
//				}
//				else
//				{
//					DialogAlert.show(context, "检查上传失败，是否重试？", null, new DialogAlert.OnClickListener()
//					{
//						@Override
//						public void onClick(int which)
//						{
//							if (which == 0)
//							{
//								finish();
//							}
//							else if (which == 1)
//							{
//								checkNeedUpload(serverLastModifyTime);
//							}
//						}
//					}, "退出", "重试");
//				}
//			}
//		});
//	}

	// 结束时候的上传
//	private void uploadAtEnd()
//	{
//		doCommit(new OnCallback()
//		{
//			@Override
//			public void over(boolean success)
//			{
//				if (success)
//				{
//					finish();
//				}
//				else
//				{
//					DialogAlert.show(context, "提交失败，是否重试？", null, new DialogAlert.OnClickListener()
//					{
//						@Override
//						public void onClick(int which)
//						{
//							if (which == 0)
//							{
//								finish();
//							}
//							else if (which == 1)
//							{
//								uploadAtEnd();
//							}
//						}
//					}, "退出", "重试");
//				}
//			}
//		});
//	}

	//------------------- 退出时同步检查（结束） --------------------------

	//------------------- 上传下载FILE（开始） --------------------------

	public interface OnSyncCallback
	{
		void over(boolean success);
	}

	public interface OnPathMapCallback
	{
		void result(String root, Map<String, SFilePath> pathMap);
	}

	// 构建云端文件Map
	private static void buildPathMap(Context context, SPTask task, final OnPathMapCallback callback)
	{
		DialogWait.message("构建云端树");
		String taskPath = "/report" + AppConfig.getTaskPath(task);
		SRequest_FileList request = new SRequest_FileList();
		request.path = taskPath;
		Protocol.doPost(context, App.getApi(), SHandleId.FileList, request.saveToStr(), new Protocol.OnCallback()
		{
			@Override
			public void onResponse(int code, String data, String msg)
			{
				if (code == 200)
				{
					SResponse_FileList response = SResponse_FileList.load(data);
					Map<String, SFilePath> pathMap = new HashMap<>();
					for (SFilePath path : response.paths)
					{
						pathMap.put(path.path, path);
					}
					if (callback != null)
						callback.result(response.root, pathMap);
				}
				else
				{
					if (callback != null)
						callback.result(null, null);
				}
			}
		});
	}

	// 构建本地文件Map
	private static Map<String, File> buildFileMap(SPTask task)
	{
		String taskDir = AppConfig.getTaskDir(task);
		List<File> files = FsUtils.searchFiles(new File(taskDir));
		Map<String, File> fileMap = new HashMap<>();
		for (File file : files)
		{
			fileMap.put(file.getAbsolutePath().substring(FsUtils.SD_CARD.length()), file);
		}
		return fileMap;
	}

	// 构建云端删除列表
	private static List<String> buildDeletePaths(Map<String, SFilePath> pathMap, Map<String, File> fileMap)
	{
		List<String> deletePaths = new ArrayList<>();
		for (Map.Entry<String, SFilePath> entry : pathMap.entrySet())
		{
			if (!fileMap.containsKey(entry.getKey().substring("/report".length())))
				deletePaths.add(entry.getValue().path);
		}
		return deletePaths;
	}

	// 构建上传列表
	private static List<File> buildUploadFiles(Map<String, SFilePath> pathMap, Map<String, File> fileMap)
	{
		List<File> uploadFiles = new ArrayList<>();
		for (Map.Entry<String, File> entry : fileMap.entrySet())
		{
			if (pathMap.containsKey("/report" + entry.getKey()))
			{
				String md5 = CommonUtils.md5(entry.getValue());
				if (!md5.equals(pathMap.get("/report" + entry.getKey()).md5))
					uploadFiles.add(entry.getValue());
			}
			else
			{
				uploadFiles.add(entry.getValue());
			}
		}
		return uploadFiles;
	}

	public static void doUploadFile(final Context context, final SPTask task, final OnSyncCallback callback)
	{
		DialogWait.show(context, "上传中。。。");
		new Handler().post(new Runnable()
		{
			@Override
			public void run()
			{
				buildPathMap(context, task, new OnPathMapCallback()
				{
					@Override
					public void result(String root, Map<String, SFilePath> pathMap)
					{
						if (pathMap != null)
						{
							Map<String, File> fileMap = buildFileMap(task);
							List<String> deletePaths = buildDeletePaths(pathMap, fileMap);
							final List<File> uploadFiles = buildUploadFiles(pathMap, fileMap);
							if (deletePaths.size() > 0)
							{
								DialogWait.message("清理云端文件");
								SRequest_FileDelete request = new SRequest_FileDelete();
								request.paths = deletePaths;
								Protocol.doPost(context, App.getApi(), SHandleId.FileDelete, request.saveToStr(), new Protocol.OnCallback()
								{
									@Override
									public void onResponse(int code, String data, String msg)
									{
										if (code == 200)
										{
											SResponse_FileDelete response = SResponse_FileDelete.load(data);
											doUploadFileImpl(context, uploadFiles, 0, callback);
										}
										else
										{
											DialogWait.close();
											if (callback != null)
												callback.over(false);
										}
									}
								});
							}
							else
							{
								doUploadFileImpl(context, uploadFiles, 0, callback);
							}
						}
						else
						{
							DialogWait.close();
							if (callback != null)
								callback.over(false);
						}
					}
				});
			}
		});
	}

	private static void doUploadFileImpl(final Context context, final List<File> uploadFiles, final int index, final OnSyncCallback callback)
	{
		if (index < uploadFiles.size())
		{
			File file = uploadFiles.get(index);
			String path = file.getAbsolutePath().substring(FsUtils.SD_CARD.length());
			DialogWait.message("上传：" + path);
			LysUpload.doUpload(context, file, "/report" + path, new Protocol.OnCallback()
			{
				@Override
				public void onResponse(int code, String data, String msg)
				{
					if (code == 200)
					{
						doUploadFileImpl(context, uploadFiles, index + 1, callback);
					}
					else
					{
						DialogWait.close();
						if (callback != null)
							callback.over(false);
					}
				}
			});
		}
		else
		{
			DialogWait.close();
			LOG.toast(context, "上传成功");
			if (callback != null)
				callback.over(true);
		}
	}

	//------------------- 上传下载FILE（结束） --------------------------

	//------------------- 上传下载（开始） --------------------------

	public interface OnCallback
	{
		void over(SVNManager.SvnTaskResult result);
	}

	private void doUpdate(final OnCallback callback)
	{
		DialogWait.show(context, "更新中。。。");
		new Handler().post(new Runnable()
		{
			@Override
			public void run()
			{
				if (getCurrFragmentPage() != null)
					getCurrFragmentPage().genSmallAndBig(false);
				doUpdateImpl(false, callback);
			}
		});
	}

	private void doUpdateImpl(boolean force, final OnCallback callback)
	{
		SVNManager.updateTask(force, task.userId, task.id, new SVNManager.OnCallback()
		{
			@Override
			public void over(final SVNManager.SvnTaskResult result)
			{
				runOnUiThread(new Runnable()
				{
					@Override
					public void run()
					{
						if (result.resultCode == SVNManager.ResultCode_Success)
						{
							DialogWait.close();
							if (callback != null)
								callback.over(result);
						}
						else if (result.resultCode == SVNManager.ResultCode_NetError)
						{
							DialogWait.close();
							DialogAlert.show(context, null, result.errorMsg, new DialogAlert.OnClickListener()
							{
								@Override
								public void onClick(int which)
								{
									if (which == 0)
									{
										if (callback != null)
											callback.over(result);
									}
									else if (which == 1)
									{
										DialogWait.show(context, "更新中。。。");
										doUpdateImpl(false, callback);
									}
								}
							}, "放弃了", "重试");
						}
						else if (result.resultCode == SVNManager.ResultCode_Relocate)
						{
							LOG.toast(context, result.errorMsg);
							doUpdateImpl(false, callback);
						}
						else if (result.resultCode == SVNManager.ResultCode_Locked)
						{
							LOG.toast(context, result.errorMsg);
							SVNManager.deleteLocalTaskSvnDir(task.userId, task.id);
							doUpdateImpl(false, callback);
						}
						else
						{
							DialogWait.close();
							DialogAlert.show(context, null, result.errorMsg, new DialogAlert.OnClickListener()
							{
								@Override
								public void onClick(int which)
								{
									if (which == 0)
									{
										if (callback != null)
											callback.over(result);
									}
									else if (which == 1)
									{
										DialogWait.show(context, "更新中。。。");
										SVNManager.deleteLocalTaskSvnDir(task.userId, task.id);
										doUpdateImpl(false, callback);
									}
								}
							}, "放弃了", "重新更新");
						}
					}
				});
			}
		});
	}

	private void doCommit(final OnCallback callback)
	{
		if (hasModify)
		{
			DialogWait.show(context, "提交中。。。");
			new Handler().post(new Runnable()
			{
				@Override
				public void run()
				{
					if (getCurrFragmentPage() != null)
						getCurrFragmentPage().genSmallAndBig(false);
					doCommitImpl(false, callback);
				}
			});
		}
		else
		{
			if (callback != null)
				callback.over(new SVNManager.SvnTaskResult(SVNManager.ResultCode_Success));
		}
	}

	private void doCommitImpl(boolean force, final OnCallback callback)
	{
		SVNManager.commitTask(force, task.userId, task.id, AppConfig.readAccount(), new SVNManager.OnCallback()
		{
			@Override
			public void over(final SVNManager.SvnTaskResult result)
			{
				runOnUiThread(new Runnable()
				{
					@Override
					public void run()
					{
						if (result.resultCode == SVNManager.ResultCode_Success)
						{
							DialogWait.close();
							hasModify = false;
							holder.testCommit.setEnabled(false);
							if (callback != null)
								callback.over(result);
						}
						else if (result.resultCode == SVNManager.ResultCode_NetError)
						{
							DialogWait.close();
							DialogAlert.show(context, null, result.errorMsg, new DialogAlert.OnClickListener()
							{
								@Override
								public void onClick(int which)
								{
									if (which == 0)
									{
										if (callback != null)
											callback.over(result);
									}
									else if (which == 1)
									{
										DialogWait.show(context, "提交中。。。");
										doCommitImpl(false, callback);
									}
								}
							}, "放弃了", "重试");
						}
						else if (result.resultCode == SVNManager.ResultCode_NotNewestVision)
						{
							DialogWait.close();
							DialogAlert.show(context, null, "当前不是最新版本，有可能别人先提交了一个版本！", new DialogAlert.OnClickListener()
							{
								@Override
								public void onClick(int which)
								{
									if (which == 0)
									{
										if (callback != null)
											callback.over(result);
									}
									else if (which == 1)
									{
										DialogWait.show(context, "提交中。。。");
										doCommitImpl(true, callback);
									}
								}
							}, "不提交", "强制提交");
						}
						else if (result.resultCode == SVNManager.ResultCode_Conflict)
						{
							LOG.toast(context, result.errorMsg);
							SVNManager.deleteLocalTaskSvnDir(task.userId, task.id);
							doCommitImpl(false, callback);
						}
						else
						{
							DialogWait.close();
							DialogAlert.show(context, null, result.errorMsg, new DialogAlert.OnClickListener()
							{
								@Override
								public void onClick(int which)
								{
									if (which == 0)
									{
										if (callback != null)
											callback.over(result);
									}
									else if (which == 1)
									{
										DialogWait.show(context, "提交中。。。");
										SVNManager.deleteLocalTaskSvnDir(task.userId, task.id);
										doCommitImpl(false, callback);
									}
								}
							}, "放弃了", "重新提交");
						}
					}
				});
			}
		});
	}

	private List<String> getWrongPages()
	{
		List<String> pageDirs = new ArrayList<>();
		for (SNotePage page : pageset.pages)
		{
			File dir = new File(String.format("%s/%s", AppConfig.getTaskDir(task), page.pageDir));
			File boardFile = new File(String.format("%s/board.json", dir));
			if (boardFile.exists())
			{
				SBoardConfig board = SBoardConfig.load(FsUtils.readText(boardFile));
				if (board.result == BoardConfig.BoardResultWrong || board.result == BoardConfig.BoardResultHalf)
					pageDirs.add(page.pageDir);
			}
		}
		return pageDirs;
	}

	private void doCopyWrong(final List<String> pageDirs, final OnCallback callback)
	{
		DialogWait.show(context, "错题收录中。。。");
		new Handler().post(new Runnable()
		{
			@Override
			public void run()
			{
				doCopyWrongImpl(pageDirs, callback);
			}
		});
	}

	private void doCopyWrongImpl(final List<String> pageDirs, final OnCallback callback)
	{
		SVNManager.copyWrong(task, pageDirs, AppConfig.readAccount(), new SVNManager.OnCallback()
		{
			@Override
			public void over(final SVNManager.SvnTaskResult result)
			{
				runOnUiThread(new Runnable()
				{
					@Override
					public void run()
					{
						if (result.resultCode == SVNManager.ResultCode_Success)
						{
							DialogWait.close();
							if (callback != null)
								callback.over(result);
						}
						else
						{
							DialogWait.close();
							DialogAlert.show(context, null, result.errorMsg, new DialogAlert.OnClickListener()
							{
								@Override
								public void onClick(int which)
								{
									if (which == 0)
									{
										DialogWait.show(context, "错题收录中。。。");
										doCopyWrongImpl(pageDirs, callback);
									}
								}
							}, "重试");
						}
					}
				});
			}
		});
	}

//	public interface OnPathMapCallback
//	{
//		void result(String root, Map<String, SFilePath> pathMap);
//	}

	// 构建云端文件Map
//	private void buildPathMap(final OnPathMapCallback callback)
//	{
//		String taskPath = AppConfig.getTaskPath(task);
//		SRequest_FileList request = new SRequest_FileList();
//		request.path = taskPath;
//		Protocol.doPost(context, App.getApi(), SHandleId.FileList, request.saveToStr(), new Protocol.OnCallback()
//		{
//			@Override
//			public void onResponse(int code, String data, String msg)
//			{
//				if (code == 200)
//				{
//					SResponse_FileList response = SResponse_FileList.load(data);
//					Map<String, SFilePath> pathMap = new HashMap<>();
//					for (SFilePath path : response.paths)
//					{
//						pathMap.put(path.path, path);
//					}
//					if (callback != null)
//						callback.result(response.root, pathMap);
//				}
//				else
//				{
//					if (callback != null)
//						callback.result(null, null);
//				}
//			}
//		});
//	}

	// 构建本地文件Map
//	private Map<String, File> buildFileMap()
//	{
//		String taskDir = AppConfig.getTaskDir(task);
//		List<File> files = FsUtils.searchFiles(new File(taskDir));
//		Map<String, File> fileMap = new HashMap<>();
//		for (File file : files)
//		{
//			fileMap.put(file.getAbsolutePath().substring(FsUtils.SD_CARD.length()), file);
//		}
//		return fileMap;
//	}

	// 构建云端删除列表
//	private List<String> buildDeletePaths(Map<String, SFilePath> pathMap, Map<String, File> fileMap, boolean filterLastModifyTime)
//	{
//		List<String> deletePaths = new ArrayList<>();
//		for (Map.Entry<String, SFilePath> entry : pathMap.entrySet())
//		{
//			if (filterLastModifyTime && entry.getValue().path.endsWith("/lastModifyTime.txt"))
//				continue;
//			if (!fileMap.containsKey(entry.getKey()))
//				deletePaths.add(entry.getValue().path);
//		}
//		return deletePaths;
//	}

	// 构建上传列表
//	private List<File> buildUploadFiles(Map<String, SFilePath> pathMap, Map<String, File> fileMap, boolean filterLastModifyTime)
//	{
//		List<File> uploadFiles = new ArrayList<>();
//		for (Map.Entry<String, File> entry : fileMap.entrySet())
//		{
//			if (filterLastModifyTime && entry.getValue().getName().equals("lastModifyTime.txt"))
//				continue;
//			if (pathMap.containsKey(entry.getKey()))
//			{
//				String md5 = CommonUtils.md5(entry.getValue());
//				if (!md5.equals(pathMap.get(entry.getKey()).md5))
//					uploadFiles.add(entry.getValue());
//			}
//			else
//			{
//				uploadFiles.add(entry.getValue());
//			}
//		}
//		return uploadFiles;
//	}

//	private void doCommit(final OnCallback callback)
//	{
//		DialogWait.show(context, "提交中。。。");
//		new Handler().post(new Runnable()
//		{
//			@Override
//			public void run()
//			{
//				if (getCurrFragmentPage() != null)
//					getCurrFragmentPage().genSmallAndBig(false);
//				buildPathMap(new OnPathMapCallback()
//				{
//					@Override
//					public void result(String root, Map<String, SFilePath> pathMap)
//					{
//						if (pathMap != null)
//						{
//							Map<String, File> fileMap = buildFileMap();
//							List<String> deletePaths = buildDeletePaths(pathMap, fileMap, false);
//							final List<File> uploadFiles = buildUploadFiles(pathMap, fileMap, false);
//							if (deletePaths.size() > 0)
//							{
//								SRequest_FileDelete request = new SRequest_FileDelete();
//								request.paths = deletePaths;
//								Protocol.doPost(context, App.getApi(), SHandleId.FileDelete, request.saveToStr(), new Protocol.OnCallback()
//								{
//									@Override
//									public void onResponse(int code, String data, String msg)
//									{
//										if (code == 200)
//										{
//											SResponse_FileDelete response = SResponse_FileDelete.load(data);
//											doCommitImpl(uploadFiles, 0, callback);
//										}
//										else
//										{
//											DialogWait.close();
//											if (callback != null)
//												callback.over(false);
//										}
//									}
//								});
//							}
//							else
//							{
//								doCommitImpl(uploadFiles, 0, callback);
//							}
//						}
//						else
//						{
//							DialogWait.close();
//							if (callback != null)
//								callback.over(false);
//						}
//					}
//				});
//			}
//		});
//	}

//	private void doCommitImpl(final List<File> uploadFiles, final int index, final OnCallback callback)
//	{
//		if (index < uploadFiles.size())
//		{
//			File file = uploadFiles.get(index);
//			String path = file.getAbsolutePath().substring(FsUtils.SD_CARD.length());
//			LysUpload.doCommit(context, file, path, new Protocol.OnCallback()
//			{
//				@Override
//				public void onResponse(int code, String data, String msg)
//				{
//					if (code == 200)
//					{
//						doCommitImpl(uploadFiles, index + 1, callback);
//					}
//					else
//					{
//						DialogWait.close();
//						if (callback != null)
//							callback.over(false);
//					}
//				}
//			});
//		}
//		else
//		{
//			DialogWait.close();
//			LOG.toast(context, "上传成功");
//			remoteVersion = getLastModifyTime();
//			if (callback != null)
//				callback.over(true);
//		}
//	}

	// 构建本地删除列表
//	private List<File> buildDeleteFiles(Map<String, SFilePath> pathMap, Map<String, File> fileMap, boolean filterLastModifyTime)
//	{
//		List<File> deleteFiles = new ArrayList<>();
//		for (Map.Entry<String, File> entry : fileMap.entrySet())
//		{
//			if (filterLastModifyTime && entry.getValue().getName().equals("lastModifyTime.txt"))
//				continue;
//			if (!pathMap.containsKey(entry.getKey()))
//				deleteFiles.add(entry.getValue());
//		}
//		return deleteFiles;
//	}

	// 构建下载列表
//	private List<SFilePath> buildDownloadPaths(Map<String, SFilePath> pathMap, Map<String, File> fileMap, boolean filterLastModifyTime)
//	{
//		List<SFilePath> downloadPaths = new ArrayList<>();
//		for (Map.Entry<String, SFilePath> entry : pathMap.entrySet())
//		{
//			if (filterLastModifyTime && entry.getValue().path.endsWith("/lastModifyTime.txt"))
//				continue;
//			if (fileMap.containsKey(entry.getKey()))
//			{
//				String md5 = CommonUtils.md5(fileMap.get(entry.getKey()));
//				if (!md5.equals(entry.getValue().md5))
//					downloadPaths.add(entry.getValue());
//			}
//			else
//			{
//				downloadPaths.add(entry.getValue());
//			}
//		}
//		return downloadPaths;
//	}

//	private void doUpdate(final OnCallback callback)
//	{
//		DialogWait.show(context, "更新中。。。");
//		new Handler().post(new Runnable()
//		{
//			@Override
//			public void run()
//			{
//				if (getCurrFragmentPage() != null)
//					getCurrFragmentPage().genSmallAndBig(false);
//				buildPathMap(new OnPathMapCallback()
//				{
//					@Override
//					public void result(String root, Map<String, SFilePath> pathMap)
//					{
//						if (pathMap != null)
//						{
//							Map<String, File> fileMap = buildFileMap();
//							List<File> deleteFiles = buildDeleteFiles(pathMap, fileMap, false);
//							List<SFilePath> downloadPaths = buildDownloadPaths(pathMap, fileMap, false);
//							for (File file : deleteFiles)
//							{
//								FsUtils.delete(file);
//								FsUtils.deleteDirectoryIfEmpty(file.getParentFile());
//							}
//							doUpdateImpl(root, downloadPaths, 0, callback);
//						}
//						else
//						{
//							DialogWait.close();
//							if (callback != null)
//								callback.over(false);
//						}
//					}
//				});
//			}
//		});
//	}

//	private void doUpdateImpl(final String root, final List<SFilePath> downloadPaths, final int index, final OnCallback callback)
//	{
//		if (index < downloadPaths.size())
//		{
//			SFilePath path = downloadPaths.get(index);
//			File file = new File(FsUtils.SD_CARD + path.path);
//			LOG.v("download : " + root + path.path);
//			HttpUtils.download(context, root + path.path, file, new HttpUtils.OnDownloadListener()
//			{
//				@Override
//				public void onWait()
//				{
//				}
//
//				@Override
//				public void onFail()
//				{
//					DialogWait.close();
//					LOG.toast(context, "更新失败");
//					if (callback != null)
//						callback.over(false);
//				}
//
//				@Override
//				public void onProgress(int alreadyDownloadSize)
//				{
//				}
//
//				@Override
//				public void onSuccess()
//				{
//					doUpdateImpl(root, downloadPaths, index + 1, callback);
//				}
//			});
//		}
//		else
//		{
//			DialogWait.close();
//			LOG.toast(context, "更新完成");
//			remoteVersion = getLastModifyTime();
//			if (callback != null)
//				callback.over(true);
//		}
//	}

	//------------------- 上传下载（结束） --------------------------

	//------------------- ViewPager监听（开始） --------------------------

	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
	{
	}

	private int lastPosition = -1;

	public boolean pageSelected(final int position)
	{
		if (lastPosition != position)
		{
			FragmentTaskPage lastFragmentPage = getFragmentPage(lastPosition);
			if (lastFragmentPage != null)
			{
				LOG.v("---------------------- to gen small : " + lastPosition);
				lastFragmentPage.genSmallAndBig(true);
				lastFragmentPage.stop();
			}
			LOG.v(String.format("-------- %d ---> %d", lastPosition, position));
			lastPosition = position;
			holder.pageNumber.setText(String.format("%d / %d", position + 1, pageset.pages.size()));
			bindPage(position);
			return true;
		}
		return false;
	}

	private void bindPage(final int position)
	{
		new Handler().post(new Runnable()
		{
			@Override
			public void run()
			{
				FragmentTaskPage fragmentPage = getFragmentPage(position);
				if (fragmentPage != null)
				{
					LOG.v("bindPage success " + position);

					if (showCommitJob() && (position == pageset.pages.size() - 1))
						holder.commitJob.setVisibility(View.VISIBLE);
					else
						holder.commitJob.setVisibility(View.GONE);

					if (showReadOver() && (position == pageset.pages.size() - 1))
						holder.readOver.setVisibility(View.VISIBLE);
					else
						holder.readOver.setVisibility(View.GONE);

					setBeiZhuState(fragmentPage.hasBeiZhu());

					fragmentPage.bindTool(holder.toolBar);
					fragmentPage.play();
				}
				else
				{
					LOG.v("bindPage is null and will retry ...");
					bindPage(position);
				}
			}
		});
	}

	@Override
	public void onPageSelected(int position)
	{
		if (pageSelected(position))
		{
			syncTurnPage(position);
			goinPage(position);
		}
	}

	private int startScrollPos;

	@Override
	public void onPageScrollStateChanged(int state)
	{
		if (state == ViewPager.SCROLL_STATE_DRAGGING)
		{
			FragmentTaskPage fragmentPage = getCurrFragmentPage();
			startScrollPos = viewPager.getCurrentItem();
			fragmentPage.startPageScroll();
		}
		else if (state == ViewPager.SCROLL_STATE_IDLE)
		{
			if (viewPager.getCurrentItem() == startScrollPos)
			{
				FragmentTaskPage fragmentPage = getCurrFragmentPage();
				fragmentPage.stopPageScroll();
			}
		}
	}

	//------------------- ViewPager监听（结束） --------------------------

	//------------------- 语音通信（开始） --------------------------

	private RongRTCRoom mRongRTCRoom = null;
//	private RongRTCLocalUser mLocalUser = null;

	//	private boolean isMuteVideo;
	private boolean isMuteAudio = false;
	private boolean isLockWrite = false;
	private boolean isEnableSpeakerphone = true;

//	private class HolderRtcVideo
//	{
//		private View view;
//		private RelativeLayout con;
//		private TextView name;
//		private RongRTCVideoView videoView;
//	}
//
//	private HolderRtcVideo newRtcVideo(final String userId)
//	{
//		final HolderRtcVideo hd = new HolderRtcVideo();
//		hd.view = LayoutInflater.from(context).inflate(R.layout.view_rtc_video, null);
//		hd.con = hd.view.findViewById(R.id.con);
//		hd.name = hd.view.findViewById(R.id.name);
//		hd.videoView = RongRTCEngine.getInstance().createVideoView(context);
//		hd.videoView.setTag(hd);
//		hd.con.addView(hd.videoView, new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//		if (!TextUtils.isEmpty(userId))
//		{
//			hd.name.setText("");
//			UserCacheManager.instance().getUser(userId, new UserCacheManager.OnResult()
//			{
//				@Override
//				public void result(SUser user)
//				{
//					if (user != null)
//						hd.name.setText(user.name);
//					else
//						hd.name.setText(userId);
//				}
//			});
//		}
//		else
//		{
//			hd.name.setText("我");
//		}
//		return hd;
//	}
//
//	private HolderRtcVideo localVideo;
//
//	private void setMuteVideo(boolean value)
//	{
//		isMuteVideo = value;
//		if (isMuteVideo)
//		{
//			RongRTCCapture.getInstance().stopCameraCapture();
//			holder.videoContainer.removeView(localVideo.view);
//			checkVideoContainerPos();
//		}
//		else
//		{
//			RongRTCCapture.getInstance().startCameraCapture();
//			holder.videoContainer.addView(localVideo.view, 0, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//			checkVideoContainerPos();
//		}
//		RongRTCCapture.getInstance().muteLocalVideo(isMuteVideo); // 禁视频
//	}
//
//	private boolean alreadyResetVideoContainerPos = false;
//
//	private void resetVideoContainerPos()
//	{
//		new Handler().postDelayed(new Runnable()
//		{
//			@Override
//			public void run()
//			{
//				FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) holder.videoContainer.getLayoutParams();
//				layoutParams.leftMargin = SysUtils.screenWidth(context) - 330;
//				layoutParams.topMargin = 120;
//				holder.videoContainer.setLayoutParams(layoutParams);
//				alreadyResetVideoContainerPos = true;
//			}
//		}, 1000);
//	}
//
//	private void checkVideoContainerPos()
//	{
//		if (alreadyResetVideoContainerPos)
//		{
//			new Handler().post(new Runnable()
//			{
//				@Override
//				public void run()
//				{
//					FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) holder.videoContainer.getLayoutParams();
//					int currX = layoutParams.leftMargin;
//					int currY = layoutParams.topMargin;
//					currX = Math.max(currX, 100 - holder.videoContainer.getWidth());
//					currX = Math.min(currX, SysUtils.screenWidth(context) - 100);
//					currY = Math.max(currY, 100 - holder.videoContainer.getHeight());
//					currY = Math.min(currY, SysUtils.screenHeight(context) - 100);
//					layoutParams.leftMargin = currX;
//					layoutParams.topMargin = currY;
//					holder.videoContainer.setLayoutParams(layoutParams);
//				}
//			});
//		}
//	}

	private void joinSuccess(RongRTCRoom rongRTCRoom, boolean first)
	{
		mRongRTCRoom = rongRTCRoom;
//		mLocalUser = rongRTCRoom.getLocalUser();

//		if (first && SysUtils.isDebug())
//		isMuteAudio = true;

//		holder.videoContainer.setOnTouchListener(new View.OnTouchListener()
//		{
//			private PointF initPoint = new PointF();
//			private PointF initPosition = new PointF();
//
//			@Override
//			public boolean onTouch(View view, MotionEvent event)
//			{
//				if (event.getAction() == MotionEvent.ACTION_DOWN)
//				{
//					initPoint.set(event.getRawX(), event.getRawY());
//					FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) holder.videoContainer.getLayoutParams();
//					initPosition.set(layoutParams.leftMargin, layoutParams.topMargin);
//				}
//				else if (event.getAction() == MotionEvent.ACTION_MOVE)
//				{
//					float offsetX = event.getRawX() - initPoint.x;
//					float offsetY = event.getRawY() - initPoint.y;
//
//					int currX = (int) (initPosition.x + offsetX);
//					int currY = (int) (initPosition.y + offsetY);
//
//					currX = Math.max(currX, 100 - holder.videoContainer.getWidth());
//					currX = Math.min(currX, SysUtils.screenWidth(context) - 100);
//					currY = Math.max(currY, 100 - holder.videoContainer.getHeight());
//					currY = Math.min(currY, SysUtils.screenHeight(context) - 100);
//
//					FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) holder.videoContainer.getLayoutParams();
//					layoutParams.leftMargin = currX;
//					layoutParams.topMargin = currY;
//					holder.videoContainer.setLayoutParams(layoutParams);
//				}
//				return true;
//			}
//		});

		AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
		audioManager.setMode(AudioManager.MODE_IN_COMMUNICATION);

//		localVideo = newRtcVideo(null);
//		RongRTCCapture.getInstance().setRongRTCVideoView(localVideo.videoView);
//		setMuteVideo(false);

		RongRTCCapture.getInstance().stopCameraCapture();
		RongRTCCapture.getInstance().muteLocalVideo(true); // 禁视频

		RongRTCCapture.getInstance().muteMicrophone(isMuteAudio); // 静音
		RongRTCCapture.getInstance().setEnableSpeakerphone(isEnableSpeakerphone); // 扬声器

//		holder.muteVideo.setChecked(isMuteVideo);
//		holder.muteVideo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
//		{
//			@Override
//			public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked)
//			{
//				setMuteVideo(isChecked);
//			}
//		});

		holder.muteAudio.setChecked(isMuteAudio);
		if (isHost)
		{
			holder.muteAudio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
			{
				@Override
				public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked)
				{
					isMuteAudio = isChecked;
					RongRTCCapture.getInstance().muteMicrophone(isMuteAudio);
				}
			});
		}
		else
		{
			if (false) // 学生不能操作喇叭
			{
				holder.muteAudio.setClickable(false);
			}
			else
			{
				holder.muteAudio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
				{
					@Override
					public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked)
					{
						LOG.v("student muteAudio : " + isChecked);
						isMuteAudio = isChecked;
						RongRTCCapture.getInstance().muteMicrophone(isMuteAudio);
						TransMessage.send(mTargetId, TransMessage.obtain(AppConfig.TransEvt_TeachNotify_MuteAudio, String.valueOf(isMuteAudio)), null);
					}
				});
			}
		}

		holder.lockWrite.setChecked(isLockWrite);
		holder.lockWrite.setClickable(false);

		holder.speakerphone.setChecked(isEnableSpeakerphone);
		holder.speakerphone.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
		{
			@Override
			public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked)
			{
				isEnableSpeakerphone = isChecked;
				RongRTCCapture.getInstance().setEnableSpeakerphone(isEnableSpeakerphone);
			}
		});

		// 设置监听
		mRongRTCRoom.registerEventsListener(this);

		refreshRoomUsers();

//		for (RongRTCRemoteUser remoteUser : mRongRTCRoom.getRemoteUsers().values())
//		{
//			for (RongRTCAVInputStream inputStream : remoteUser.getRemoteAVStreams())
//			{
//				if (inputStream.getMediaType() == MediaType.VIDEO)
//				{
//					HolderRtcVideo hdRtc = newRtcVideo(remoteUser.getUserId());
//					holder.videoContainer.addView(hdRtc.view, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//					checkVideoContainerPos();
//					inputStream.setRongRTCVideoView(hdRtc.videoView);
//				}
//			}
//		}

		// 订阅资源
		for (RongRTCRemoteUser remoteUser : mRongRTCRoom.getRemoteUsers().values())
		{
			remoteUser.subscribeAVStream(remoteUser.getRemoteAVStreams(), new RongRTCResultUICallBack()
			{
				@Override
				public void onUiSuccess()
				{
					LOG.v("订阅资源成功");
				}

				@Override
				public void onUiFailed(RTCErrorCode rtcErrorCode)
				{
					LOG.v("订阅资源失败：" + rtcErrorCode);
				}
			});
		}

		// 发布资源
		mRongRTCRoom.getLocalUser().publishDefaultAVStream(new RongRTCResultUICallBack()
		{
			@Override
			public void onUiSuccess()
			{
				LOG.v("发布资源成功");
			}

			@Override
			public void onUiFailed(RTCErrorCode rtcErrorCode)
			{
				LOG.v("发布资源失败：" + rtcErrorCode);
			}
		});
	}

	private void printStreams(RongRTCRemoteUser remoteUser)
	{
		LOG.v("printStreams size : " + remoteUser.getRemoteAVStreams().size());
		for (RongRTCAVInputStream inputStream : remoteUser.getRemoteAVStreams())
		{
			LOG.v("getMediaType : " + inputStream.getMediaType());
		}
	}

	private void quitRoom()
	{
		if (mRongRTCRoom != null)
		{
			mRongRTCRoom.unregisterEventsListener(this);
			RongRTCEngine.getInstance().quitRoom(mRongRTCRoom.getRoomId(), new RongRTCResultUICallBack()
			{
				@Override
				public void onUiSuccess()
				{
					LOG.v("离开房间成功");
				}

				@Override
				public void onUiFailed(RTCErrorCode rtcErrorCode)
				{
					LOG.v("离开房间失败：" + rtcErrorCode);
				}
			});
			mRongRTCRoom = null;
//			mLocalUser = null;

			AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
			audioManager.setMode(AudioManager.MODE_NORMAL);
		}
	}

	private void refreshRoomUsers()
	{
		new Handler().postDelayed(new Runnable()
		{
			@Override
			public void run()
			{
				if (mRongRTCRoom != null)
				{
					List<String> roomUsers = new ArrayList<>();
					for (RongRTCRemoteUser remoteUser : mRongRTCRoom.getRemoteUsers().values())
					{
						if (remoteUser.getRemoteAVStreams().size() > 0)
							roomUsers.add(remoteUser.getUserId());
					}
					LOG.v("refreshRoomUsers : " + roomUsers.size());
//					remotesAdapter.setData(roomUsers);
				}
			}
		}, 100);
	}

	//------------------- 语音通信（结束） --------------------------

	//------------------ registerEventsListener（开始） -------------------

	// 远端用户：发布资源
	@Override
	public void onRemoteUserPublishResource(RongRTCRemoteUser remoteUser, List<RongRTCAVInputStream> publishResource)
	{
		LOG.v("onRemoteUserPublishResource");
		refreshRoomUsers();
//		for (RongRTCAVInputStream inputStream : remoteUser.getRemoteAVStreams())
//		{
//			if (inputStream.getMediaType() == MediaType.VIDEO)
//			{
//				HolderRtcVideo hdRtc = newRtcVideo(remoteUser.getUserId());
//				holder.videoContainer.addView(hdRtc.view, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//				checkVideoContainerPos();
//				inputStream.setRongRTCVideoView(hdRtc.videoView);
//			}
//		}
		remoteUser.subscribeAVStream(remoteUser.getRemoteAVStreams(), new RongRTCResultUICallBack()
		{
			@Override
			public void onUiSuccess()
			{
				LOG.v("订阅资源成功");
			}

			@Override
			public void onUiFailed(RTCErrorCode rtcErrorCode)
			{
				LOG.v("订阅资源失败：" + rtcErrorCode);
			}
		});
	}

	// 远端用户：静音或取消静音
	@Override
	public void onRemoteUserAudioStreamMute(RongRTCRemoteUser remoteUser, RongRTCAVInputStream avInputStream, boolean mute)
	{
		LOG.v("onRemoteUserAudioStreamMute : " + mute);
	}

	// 远端用户：开启或关闭摄像头
	@Override
	public void onRemoteUserVideoStreamEnabled(RongRTCRemoteUser remoteUser, RongRTCAVInputStream avInputStream, boolean enable)
	{
		LOG.v("onRemoteUserVideoStreamEnabled : " + enable);
//		if (enable)
//		{
//			for (RongRTCAVInputStream inputStream : remoteUser.getRemoteAVStreams())
//			{
//				if (inputStream.getMediaType() == MediaType.VIDEO)
//				{
//					HolderRtcVideo hdRtc = newRtcVideo(remoteUser.getUserId());
//					holder.videoContainer.addView(hdRtc.view, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//					checkVideoContainerPos();
//					inputStream.setRongRTCVideoView(hdRtc.videoView);
//				}
//			}
//		}
//		else
//		{
//			for (RongRTCAVInputStream inputStream : remoteUser.getRemoteAVStreams())
//			{
//				if (inputStream.getMediaType() == MediaType.VIDEO)
//				{
//					HolderRtcVideo hdRtc = (HolderRtcVideo) inputStream.getRongRTCVideoView().getTag();
//					holder.videoContainer.removeView(hdRtc.view);
//					checkVideoContainerPos();
//				}
//			}
//		}
	}

	// 远端用户：取消发布资源
	@Override
	public void onRemoteUserUnpublishResource(RongRTCRemoteUser remoteUser, List<RongRTCAVInputStream> unPublishResource)
	{
		LOG.v("onRemoteUserUnPublishResource");
	}

	// 用户加入房间
	@Override
	public void onUserJoined(RongRTCRemoteUser remoteUser)
	{
		LOG.v("onUserJoined");
		refreshRoomUsers();
//		if (currCallMessage != null)
//		{
//			LOG.toast(context, "学生已进入");
//			DialogTimeWait.close();
//			currCallMessage = null;
//		}
//		else
//		{
//			LOG.toast(context, "对方已上线");
//			DialogTimeWait.close();
//			if (isHost)
//			{
//				DialogWait.show(context, "同步中。。。");
//				TransMessage.send(targetId, TransMessage.obtain(AppConfig.TransEvt_TeachSyncRequest, null), null);
//				syncUpload();
//			}
//			else
//			{
//				DialogWait.show(context, "同步中。。。");
//				TransMessage.send(targetId, TransMessage.obtain(AppConfig.TransEvt_TeachSyncRequest, null), null);
//			}
//		}
	}

//	// 主人同步提交
//	private void syncUpload()
//	{
//		if (getCurrFragmentPage() != null)
//			getCurrFragmentPage().genSmallAndBig(false);
//		doCommit(new OnCallback()
//		{
//			@Override
//			public void over(SVNManager.SvnTaskResult result)
//			{
//				if (result.resultCode == SVNManager.ResultCode_Success)
//				{
//					TransMessage.send(targetId, TransMessage.obtain(AppConfig.TransEvt_TeachSyncDownload, getCurrNotePage().pageDir), null);
//				}
//				else
//				{
//					TransMessage.send(targetId, TransMessage.obtain(AppConfig.TransEvt_TeachSyncFail, null), null);
//					LOG.toast(context, "同步提交失败");
//					DialogWait.close();
//				}
//			}
//		});
//	}
//
//	// 客人同步更新
//	private void syncDownload(final String pageDir)
//	{
//		if (getCurrFragmentPage() != null)
//			getCurrFragmentPage().genSmallAndBig(false);
//		doUpdate(new OnCallback()
//		{
//			@Override
//			public void over(SVNManager.SvnTaskResult result)
//			{
//				if (result.resultCode == SVNManager.ResultCode_Success)
//				{
//					TransMessage.send(targetId, TransMessage.obtain(AppConfig.TransEvt_TeachSyncSuccess, null), null);
//					LOG.toast(context, "同步成功");
//					DialogWait.close();
//
//					File file = new File(String.format("%s/pageset.json", AppConfig.getTaskDir(task)));
//					if (file.exists())
//					{
//						pageset = SNotePageSet.load(FsUtils.readText(file));
//
//						adapter.fragmentMap.clear();
//						lastPosition = -1;
//						adapter.setData(pageset.pages);
//						viewPager.setAdapter(adapter);
//
//						showPage(pageDir);
//					}
//				}
//				else
//				{
//					TransMessage.send(targetId, TransMessage.obtain(AppConfig.TransEvt_TeachSyncFail, null), null);
//					LOG.toast(context, "同步更新失败：" + result.errorMsg);
//					DialogWait.close();
//				}
//			}
//		});
//	}

	// 用户离开房间
	@Override
	public void onUserLeft(RongRTCRemoteUser remoteUser)
	{
		LOG.v("onUserLeft");
		refreshRoomUsers();
//		for (RongRTCAVInputStream inputStream : remoteUser.getRemoteAVStreams())
//		{
//			if (inputStream.getMediaType() == MediaType.VIDEO)
//			{
//				HolderRtcVideo hdRtc = (HolderRtcVideo) inputStream.getRongRTCVideoView().getTag();
//				holder.videoContainer.removeView(hdRtc.view);
//				checkVideoContainerPos();
//			}
//		}
	}

	// 用户离线
	@Override
	public void onUserOffline(RongRTCRemoteUser remoteUser)
	{
		LOG.v("onUserOffline");
		refreshRoomUsers();
//		for (RongRTCAVInputStream inputStream : remoteUser.getRemoteAVStreams())
//		{
//			if (inputStream.getMediaType() == MediaType.VIDEO)
//			{
//				HolderRtcVideo hdRtc = (HolderRtcVideo) inputStream.getRongRTCVideoView().getTag();
//				holder.videoContainer.removeView(hdRtc.view);
//				checkVideoContainerPos();
//			}
//		}
//		DialogTimeWait.create(context).showWait(true).setMessage("对方已离线，等待中。。。").setTickListener(new DialogTimeWait.OnTickListener()
//		{
//			@Override
//			public void onTick(DialogTimeWait dialog, long timeDt)
//			{
//				dialog.setInfo(String.format("（%s）", timeDt / 1000));
//			}
//		}).setRightListener("结束课程", new DialogTimeWait.OnClickListener()
//		{
//			@Override
//			public void onClick(DialogTimeWait dialog)
//			{
//				dialog.dismiss();
//				if (isHost)
//				{
//					if (getCurrFragmentPage() != null)
//						getCurrFragmentPage().genSmallAndBig(false);
//					checkTaskFileExistsAtEnd();
//				}
//				else
//				{
//					finish();
//				}
//			}
//		}).show();
	}

	// 远端用户发布视频资源，订阅成功后，视频流通道建立成功的通知
	@Override
	public void onVideoTrackAdd(String userId, String tag)
	{
		LOG.v("onVideoTrackAdd");
	}

	// 远端用户发布视频资源，订阅成功后，绘制视频第一帧的通知
	@Override
	public void onFirstFrameDraw(String userId, String tag)
	{
		LOG.v("onFirstFrameDraw");
	}

	// 自己退出房间，例如断网退出等
	@Override
	public void onLeaveRoom()
	{
		LOG.v("onLeaveRoom");
		refreshRoomUsers();
//		joinRoom(false);
	}

	private String currReceiveId = null;
	private List<BoardMessage> mReceiveCache = new ArrayList<>();

	// 收到IM消息
	@Override
	public void onReceiveMessage(Message message)
	{
		LOG.v("onReceiveMessage");
		if (message.getContent() instanceof BoardMessage)
		{
			BoardMessage boardMessage = (BoardMessage) message.getContent();
			LOG.v("receive from " + message.getSenderUserId() + " : " + boardMessage.toString());
			if (boardMessage.getCount() == 1)
			{
//				SOperation operation = SOperation.load(boardMessage.getContent());
//				SOperation operation = SOperation.load(CommonUtils.base64Decode(boardMessage.getContent()));
				SOperation operation = SOperation.load(GZipUtil.decompress(CommonUtils.base64Decode(boardMessage.getContent())));
				onReceiveOperation(operation);
			}
			else
			{
				if (!boardMessage.getId().equals(currReceiveId))
				{
					mReceiveCache.clear();
					currReceiveId = boardMessage.getId();
				}
				mReceiveCache.add(boardMessage);
				if (boardMessage.getIndex() == boardMessage.getCount() - 1)
				{
					if (mReceiveCache.size() == boardMessage.getCount())
					{
						StringBuilder sb = new StringBuilder();
						for (BoardMessage boardMsg : mReceiveCache)
						{
							sb.append(boardMsg.getContent());
						}
//						SOperation operation = SOperation.load(sb.toString());
//						SOperation operation = SOperation.load(CommonUtils.base64Decode(sb.toString()));
						SOperation operation = SOperation.load(GZipUtil.decompress(CommonUtils.base64Decode(sb.toString())));
						onReceiveOperation(operation);
					}
					currReceiveId = null;
					mReceiveCache.clear();
				}
			}
		}
	}

	@Override
	public void onKickedByServer()
	{

	}

	//------------------ registerEventsListener（结束） -------------------

	//------------------- 画布同步（开始） --------------------------

	private ConcurrentLinkedQueue<BoardMessage> mSendQueue = new ConcurrentLinkedQueue<>();
	private boolean isSending = false;

	private void send()
	{
		final BoardMessage boardMessage = mSendQueue.peek(); // 获取但不移除
		if (mRongRTCRoom == null)
			return;
		LOG.v("send : " + boardMessage.toString());
		mRongRTCRoom.getLocalUser().setAttributeValue("z", "z", boardMessage, new RongRTCResultCallBack()
		{
			@Override
			public void onSuccess()
			{
//				LOG.v("onSuccess");
				mSendQueue.remove(boardMessage);
				if (!mSendQueue.isEmpty())
					send();
				else
					isSending = false;
			}

			@Override
			public void onFailed(RTCErrorCode rtcErrorCode)
			{
				LOG.v("onFailed " + rtcErrorCode);
				new Handler().postDelayed(new Runnable()
				{
					@Override
					public void run()
					{
						send();
					}
				}, 100);
			}
		});
	}

	private void startSend()
	{
		if (!isSending && !mSendQueue.isEmpty())
		{
			isSending = true;
			send();
		}
	}

	private void sendOperation(SOperation operation)
	{
//		String text1 = operation.saveToStr();
//		String text2 = CommonUtils.base64Encode(operation.saveToBytes());
		String text3 = CommonUtils.base64Encode(GZipUtil.compress(operation.saveToBytes()));
//		LOG.v(String.format("text1=%s, text2=%s, text3=%s", text1.length(), text2.length(), text3.length()));
		List<BoardMessage> boardMessages = BoardMessage.obtain(text3);
		for (BoardMessage boardMessage : boardMessages)
		{
			mSendQueue.offer(boardMessage);
		}
		startSend();
	}

	// 绘制同步
	public void syncDrawOver(String pageDir, LysBoardDrawing currDrawing)
	{
		modifyed();
		if (mRongRTCRoom != null)
		{
			SOperation operation = new SOperation();
			operation.operationType = SOperationType.Draw;
			operation.pageDir = pageDir;
			operation.drawing = currDrawing.saveToProto();
			sendOperation(operation);
		}
	}

	// 清屏同步
	public void syncClear(String pageDir)
	{
		modifyed();
		if (mRongRTCRoom != null)
		{
			SOperation operation = new SOperation();
			operation.operationType = SOperationType.Clear;
			operation.pageDir = pageDir;
			sendOperation(operation);
		}
	}

	// 高度同步
	public void syncHeight(String pageDir, int height)
	{
		modifyed();
		if (mRongRTCRoom != null)
		{
			SOperation operation = new SOperation();
			operation.operationType = SOperationType.Height;
			operation.pageDir = pageDir;
			operation.height = height;
			sendOperation(operation);
		}
	}

	public void syncScroll(String pageDir, int scrollY)
	{
		if (mRongRTCRoom != null && !isLimitOperation())
		{
			SOperation operation = new SOperation();
			operation.operationType = SOperationType.Scroll;
			operation.pageDir = pageDir;
			operation.scrollY = scrollY;
			sendOperation(operation);
		}
	}

	public void syncTurnPage(int newPageIndex)
	{
		if (mRongRTCRoom != null && !isLimitOperation())
		{
			SOperation operation = new SOperation();
			operation.operationType = SOperationType.TurnPage;
			operation.newPageIndex = newPageIndex;
			sendOperation(operation);
		}
	}

	// 加页同步
	public void syncAddPage(String pageDir, String newPageDir)
	{
		modifyed();
		if (mRongRTCRoom != null)
		{
			SOperation operation = new SOperation();
			operation.operationType = SOperationType.AddPage;
			operation.pageDir = pageDir;
			operation.newPageDir = newPageDir;
			sendOperation(operation);
		}
	}

	// 删页同步
	public void syncDeletePages(String pageDir, Map<String, SNotePage> deleteMap)
	{
		modifyed();
		if (mRongRTCRoom != null)
		{
			SOperation operation = new SOperation();
			operation.operationType = SOperationType.DeletePages;
			operation.pageDir = pageDir;
			operation.deletePageDirs.addAll(deleteMap.keySet());
			sendOperation(operation);
		}
	}

	// 添加图片
	public void syncAddImage(String pageDir, SBoardPhoto photo, byte[] bitmapData)
	{
		modifyed();
		if (mRongRTCRoom != null)
		{
			SOperation operation = new SOperation();
			operation.operationType = SOperationType.AddImage;
			operation.pageDir = pageDir;
			operation.photo = photo;
			operation.bitmapData = bitmapData;
			sendOperation(operation);
		}
	}

	// 添加网络视频
	public void syncAddVideoNet(String pageDir, SBoardPhoto photo, byte[] bitmapData, byte[] videoData)
	{
		modifyed();
		if (mRongRTCRoom != null)
		{
			SOperation operation = new SOperation();
			operation.operationType = SOperationType.AddVideo;
			operation.pageDir = pageDir;
			operation.photo = photo;
			operation.bitmapData = bitmapData;
			operation.videoData = videoData;
			sendOperation(operation);
		}
	}

	// 添加题目
	public void syncAddTopic(String pageDir, SBoardPhoto photo, byte[] bitmapData, byte[] parseData)
	{
		modifyed();
		if (mRongRTCRoom != null)
		{
			SOperation operation = new SOperation();
			operation.operationType = SOperationType.AddTopic;
			operation.pageDir = pageDir;
			operation.photo = photo;
			operation.bitmapData = bitmapData;
			operation.parseData = parseData;
			sendOperation(operation);
		}
	}

	// 添加选项
	public void syncAddSelectionGroup(String pageDir, SBoardPhoto photo)
	{
		modifyed();
		if (mRongRTCRoom != null)
		{
			SOperation operation = new SOperation();
			operation.operationType = SOperationType.AddSelectionGroup;
			operation.pageDir = pageDir;
			operation.photo = photo;
			sendOperation(operation);
		}
	}

	// 添加文本
	public void syncAddBoardText(String pageDir, SBoardPhoto photo)
	{
		modifyed();
		if (mRongRTCRoom != null)
		{
			SOperation operation = new SOperation();
			operation.operationType = SOperationType.AddBordText;
			operation.pageDir = pageDir;
			operation.photo = photo;
			sendOperation(operation);
		}
	}

	// 清除中间层组件
	public void syncDeletePhoto(String pageDir, String photoName)
	{
		modifyed();
		if (mRongRTCRoom != null)
		{
			SOperation operation = new SOperation();
			operation.operationType = SOperationType.DeletePhoto;
			operation.pageDir = pageDir;
			operation.photoName = photoName;
			sendOperation(operation);
		}
	}

	// 置顶
	public void syncTopPhoto(String pageDir, String photoName)
	{
		modifyed();
		if (mRongRTCRoom != null)
		{
			SOperation operation = new SOperation();
			operation.operationType = SOperationType.TopPhoto;
			operation.pageDir = pageDir;
			operation.photoName = photoName;
			sendOperation(operation);
		}
	}

	// 修改中间层组件（大小，位置，角度）
	public void syncModifyPhoto(String pageDir, String photoName, int photoX, int photoY, int photoRotation, int photoWidth, int photoHeight, boolean hide)
	{
		modifyed();
		if (mRongRTCRoom != null)
		{
			SOperation operation = new SOperation();
			operation.operationType = SOperationType.ModifyPhoto;
			operation.pageDir = pageDir;
			operation.photoName = photoName;
			operation.photoX = photoX;
			operation.photoY = photoY;
			operation.photoRotation = photoRotation;
			operation.photoWidth = photoWidth;
			operation.photoHeight = photoHeight;
			operation.hide = hide;
			sendOperation(operation);
		}
	}

	// 修改选项
	public void syncModifyPhotoSelections(String pageDir, String photoName, SSelectionGroup selectionGroup)
	{
		modifyed();
		if (mRongRTCRoom != null)
		{
			SOperation operation = new SOperation();
			operation.operationType = SOperationType.ModifySelections;
			operation.pageDir = pageDir;
			operation.photoName = photoName;
			operation.selectionGroup = selectionGroup;
			sendOperation(operation);
		}
	}

	// 修改文本
	public void syncModifyPhotoBoardText(String pageDir, String photoName, SBoardText boardText)
	{
		modifyed();
		if (mRongRTCRoom != null)
		{
			SOperation operation = new SOperation();
			operation.operationType = SOperationType.ModifyBoardText;
			operation.pageDir = pageDir;
			operation.photoName = photoName;
			operation.boardText = boardText;
			sendOperation(operation);
		}
	}

	public void onReceiveOperation(SOperation operation)
	{
		if (operation.operationType.equals(SOperationType.TurnPage))
		{
			showPage(operation.newPageIndex);
		}
		else
		{
			if (!getCurrNotePage().pageDir.equals(operation.pageDir))
			{
				if (!showPage(operation.pageDir))
					return;
			}
			if (getCurrFragmentPage() == null)
				return;
			if (operation.operationType.equals(SOperationType.Draw))
			{
				modifyed();
				LysBoardDrawing newDrawing = LysBoardDrawing.create(operation.drawing);
				getCurrFragmentPage().getReceiveBoard().addOperation(newDrawing);
			}
			else if (operation.operationType.equals(SOperationType.Clear))
			{
				modifyed();
				getCurrFragmentPage().getReceiveBoard().clear();
			}
			else if (operation.operationType.equals(SOperationType.Height))
			{
				modifyed();
				getCurrFragmentPage().getReceiveBoard().setBoardHeight(operation.height);
			}
			else if (operation.operationType.equals(SOperationType.Scroll))
			{
				getCurrFragmentPage().getReceiveBoard().setScrollPos(operation.scrollY);
			}
			else if (operation.operationType.equals(SOperationType.AddPage))
			{
				modifyed();
				getCurrFragmentPage().genSmallAndBig(true);
				addPage(viewPager.getCurrentItem(), operation.newPageDir, null);
			}
			else if (operation.operationType.equals(SOperationType.DeletePages))
			{
				modifyed();
				Map<String, SNotePage> deleteMap = new HashMap<>();
				for (String key : operation.deletePageDirs)
				{
					deleteMap.put(key, null);
				}
				for (int i = 0; i < pageset.pages.size(); i++)
				{
					SNotePage page = pageset.pages.get(i);
					if (deleteMap.containsKey(page.pageDir))
					{
						deleteMap.put(page.pageDir, page);
					}
				}
				deletePageImpl(deleteMap);
			}
			else if (operation.operationType.equals(SOperationType.AddImage))
			{
				modifyed();
				SPhotoAddParam param = new SPhotoAddParam();
				param.doNotActive = true;
				if (operation.bitmapData != null)
					getCurrFragmentPage().getReceiveBoard().writePhotoFile(operation.bitmapData, operation.photo.name);
				getCurrFragmentPage().getReceiveBoard().addPhotoImpl(operation.bitmapData, operation.photo, param, operation.photo.name, false);
			}
			else if (operation.operationType.equals(SOperationType.AddVideo))
			{
				modifyed();
				SPhotoAddParam param = new SPhotoAddParam();
				param.doNotActive = true;
				if (operation.bitmapData != null)
					getCurrFragmentPage().getReceiveBoard().writePhotoFile(operation.bitmapData, operation.photo.name);
				if (operation.videoData != null)
					getCurrFragmentPage().getReceiveBoard().writeVideoFile(operation.videoData, operation.photo.name);
				getCurrFragmentPage().getReceiveBoard().addPhotoImpl(operation.bitmapData, operation.photo, param, operation.photo.name, false);
			}
			else if (operation.operationType.equals(SOperationType.AddTopic))
			{
				modifyed();
				SPhotoAddParam param = new SPhotoAddParam();
				param.doNotActive = true;
				if (operation.bitmapData != null)
					getCurrFragmentPage().getReceiveBoard().writePhotoFile(operation.bitmapData, operation.photo.name);
				if (operation.parseData != null)
					getCurrFragmentPage().getReceiveBoard().writeParseFile(operation.parseData, operation.photo.name);
				getCurrFragmentPage().getReceiveBoard().addPhotoImpl(operation.bitmapData, operation.photo, param, operation.photo.name, false);
			}
			else if (operation.operationType.equals(SOperationType.AddSelectionGroup))
			{
				modifyed();
				SPhotoAddParam param = new SPhotoAddParam();
				param.doNotActive = true;
				getCurrFragmentPage().getReceiveBoard().addPhotoImpl(operation.bitmapData, operation.photo, param, operation.photo.name, false);
			}
			else if (operation.operationType.equals(SOperationType.AddBordText))
			{
				modifyed();
				SPhotoAddParam param = new SPhotoAddParam();
				param.doNotActive = true;
				getCurrFragmentPage().getReceiveBoard().addPhotoImpl(operation.bitmapData, operation.photo, param, operation.photo.name, false);
			}
			else if (operation.operationType.equals(SOperationType.DeletePhoto))
			{
				modifyed();
				getCurrFragmentPage().getReceiveBoard().deletePhoto(operation.photoName);
			}
			else if (operation.operationType.equals(SOperationType.TopPhoto))
			{
				modifyed();
				getCurrFragmentPage().getReceiveBoard().topPhoto(operation.photoName);
			}
			else if (operation.operationType.equals(SOperationType.ModifyPhoto))
			{
				modifyed();
				getCurrFragmentPage().getReceiveBoard().modifyPhoto(operation.photoName, operation.photoX, operation.photoY, operation.photoRotation, operation.photoWidth, operation.photoHeight, operation.hide);
			}
			else if (operation.operationType.equals(SOperationType.ModifySelections))
			{
				modifyed();
				getCurrFragmentPage().getReceiveBoard().modifySelections(operation.photoName, operation.selectionGroup);
			}
			else if (operation.operationType.equals(SOperationType.ModifyBoardText))
			{
				modifyed();
				getCurrFragmentPage().getReceiveBoard().modifyBoardText(operation.photoName, operation.boardText);
			}
		}
	}

	//------------------- 画布同步（结束） --------------------------

	private BroadcastReceiver mReceiver = new BroadcastReceiver()
	{
		@Override
		public void onReceive(final Context context, Intent intent)
		{
			LOG.v("Action:" + intent.getAction());
			if (intent.getAction().equals(LysIM.ActionTrans(context, AppConfig.TransEvt_TeachRefuse)))
			{
				Message message = intent.getParcelableExtra("message");
				TransMessage transMessage = (TransMessage) message.getContent();
				String teachInstanceId = transMessage.getMsg();
				if (modeIsSync() && teachInstanceId.equals(mTeachInstanceId))
				{
//					startTeachFail("该学生拒绝进入");
					modifyState(message.getSenderUserId(), STeachState.Refuse, true);
					SUser user = getTarget(message.getSenderUserId());
					if (user != null)
						Helper.addEvent(context, App.userId(), AppConfig.EventAction_TeachRefuse, mTeachInstanceId, String.format("学生%s（%s）拒绝进入！！！", user.name, user.id));
				}
			}
			else if (intent.getAction().equals(LysIM.ActionTrans(context, AppConfig.TransEvt_TeachAgree)))
			{
				Message message = intent.getParcelableExtra("message");
				TransMessage transMessage = (TransMessage) message.getContent();
				String teachInstanceId = transMessage.getMsg();
				if (modeIsSync() && teachInstanceId.equals(mTeachInstanceId))
				{
					modifyState(message.getSenderUserId(), STeachState.Agree, true);
					SUser user = getTarget(message.getSenderUserId());
					if (user != null)
						Helper.addEvent(context, App.userId(), AppConfig.EventAction_TeachAgree, mTeachInstanceId, String.format("学生%s（%s）同意进入", user.name, user.id));
				}
			}
			else if (intent.getAction().equals(LysIM.ActionTrans(context, AppConfig.TransEvt_TeachReady)))
			{
				Message message = intent.getParcelableExtra("message");
				TransMessage transMessage = (TransMessage) message.getContent();
				String teachInstanceId = transMessage.getMsg();
				if (modeIsSync() && teachInstanceId.equals(mTeachInstanceId))
				{
					if (mAlreadyStart)
					{
						modifyState(message.getSenderUserId(), STeachState.Ready, false);
						TransMessage.send(message.getSenderUserId(), TransMessage.obtain(AppConfig.TransEvt_TeachStart, mTeachInstanceId), null);
					}
					else
					{
						modifyState(message.getSenderUserId(), STeachState.Ready, true);
					}
					SUser user = getTarget(message.getSenderUserId());
					if (user != null)
						Helper.addEvent(context, App.userId(), AppConfig.EventAction_TeachReady, mTeachInstanceId, String.format("学生%s（%s）已就绪%s", user.name, user.id, mAlreadyStart ? "，此时已开始上课" : ""));
				}
			}
			else if (intent.getAction().equals(LysIM.ActionTrans(context, AppConfig.TransEvt_TeachStart)))
			{
				Message message = intent.getParcelableExtra("message");
				TransMessage transMessage = (TransMessage) message.getContent();
				String teachInstanceId = transMessage.getMsg();
				if (modeIsSync() && teachInstanceId.equals(mTeachInstanceId))
				{
					DialogWait.close();
					mStartTime = System.currentTimeMillis();
//					holder.muteVideo.setChecked(!holder.muteVideo.isChecked());
//					resetVideoContainerPos();
					Helper.addEvent(context, App.userId(), AppConfig.EventAction_TeachStart, mTeachInstanceId, String.format("开始上课"));
				}
			}
			else if (intent.getAction().equals(LysIM.ActionTrans(context, AppConfig.TransEvt_TeachOver)))
			{
				Message message = intent.getParcelableExtra("message");
				TransMessage transMessage = (TransMessage) message.getContent();
				String teachInstanceId = transMessage.getMsg();
				if (modeIsSync() && teachInstanceId.equals(mTeachInstanceId))
				{
					Helper.addEvent(context, App.userId(), AppConfig.EventAction_TeachOver, mTeachInstanceId, String.format("课程已结束！耗时：%s", CommonUtils.formatTime(System.currentTimeMillis() - mStartTime)));
//					DialogAlert.show(context, "课程已结束！", null, new DialogAlert.OnClickListener()
//					{
//						@Override
//						public void onClick(int which)
//						{
//							if (which == 0)
//							{
					if (getCurrFragmentPage() != null)
						getCurrFragmentPage().genSmallAndBig(false);
					finish();
//							}
//						}
//					}, "退出");
				}
			}
			else if (intent.getAction().equals(LysIM.ActionTrans(context, AppConfig.TransEvt_TeachQuit)))
			{
				Message message = intent.getParcelableExtra("message");
				TransMessage transMessage = (TransMessage) message.getContent();
				String teachInstanceId = transMessage.getMsg();
				if (modeIsSync() && teachInstanceId.equals(mTeachInstanceId))
				{
					modifyState(message.getSenderUserId(), STeachState.Quit, false);

					if (mTargetMap.containsKey(message.getSenderUserId()))
					{
						SUser target = mTargetMap.get(message.getSenderUserId());
						LOG.toast(context, String.format("%s 退出了课程", target.name));
						Helper.addEvent(context, App.userId(), AppConfig.EventAction_TeachQuit, mTeachInstanceId, String.format("学生%s（%s）退出了课程", target.name, target.id));
					}

					if (getReadyCount() == 0)
					{
						Helper.addEvent(context, App.userId(), AppConfig.EventAction_TeachOver, mTeachInstanceId, String.format("课程被动结束！耗时：%s", CommonUtils.formatTime(System.currentTimeMillis() - mStartTime)));
//						DialogAlert.show(context, "课程已结束！", null, new DialogAlert.OnClickListener()
//						{
//							@Override
//							public void onClick(int which)
//							{
//								if (which == 0)
//								{
						if (getCurrFragmentPage() != null)
							getCurrFragmentPage().genSmallAndBig(false);
						checkTaskFileExistsAtEnd();
//								}
//							}
//						}, "退出");
					}
				}
			}
			else if (intent.getAction().equals(LysIM.ActionTrans(context, AppConfig.TransEvt_TeachNotify_MuteAudio)))
			{
				if (modeIsSync())
				{
					Message message = intent.getParcelableExtra("message");
					TransMessage transMessage = (TransMessage) message.getContent();
					boolean value = Boolean.valueOf(transMessage.getMsg());
					if (isHost)
					{
						String targetId = message.getSenderUserId();
						SUser user = getTarget(targetId);
						if (user != null)
						{
							user.isMuteAudio = value;
							remotesAdapter.notifyDataSetChanged();
						}
					}
					else
					{
						isMuteAudio = value;
						holder.muteAudio.setChecked(isMuteAudio);
						RongRTCCapture.getInstance().muteMicrophone(isMuteAudio);
						TransMessage.send(mTargetId, TransMessage.obtain(AppConfig.TransEvt_TeachNotify_MuteAudio, String.valueOf(isMuteAudio)), null);
					}
				}
			}
			else if (intent.getAction().equals(LysIM.ActionTrans(context, AppConfig.TransEvt_TeachNotify_LockWrite)))
			{
				if (modeIsSync())
				{
					Message message = intent.getParcelableExtra("message");
					TransMessage transMessage = (TransMessage) message.getContent();
					boolean value = Boolean.valueOf(transMessage.getMsg());
					if (isHost)
					{
						String targetId = message.getSenderUserId();
						SUser user = getTarget(targetId);
						if (user != null)
						{
							user.isLockWrite = value;
							remotesAdapter.notifyDataSetChanged();
						}
					}
					else
					{
						isLockWrite = value;
						holder.lockWrite.setChecked(isLockWrite);
						holder.lockWriteMask.setVisibility(isLockWrite ? View.VISIBLE : View.GONE);
						TransMessage.send(mTargetId, TransMessage.obtain(AppConfig.TransEvt_TeachNotify_LockWrite, String.valueOf(isLockWrite)), null);
					}
				}
			}
			else if (intent.getAction().equals(LysIM.ActionTrans(context, AppConfig.TransEvt_TeachNotify_Check)))
			{
				if (modeIsSync())
				{
					if (isHost)
					{
						Message message = intent.getParcelableExtra("message");
						TransMessage transMessage = (TransMessage) message.getContent();
						String str = transMessage.getMsg();
						String targetId = message.getSenderUserId();
						SUser user = getTarget(targetId);
						if (user != null)
						{
							if (str.equals(mCheckStr))
							{
								user.checkState = SCheckState.Equal;
							}
							else
							{
								user.checkState = SCheckState.Diff;
								{
									String path = String.format("/check_diff/%s_%s/%s_%s_%s.txt", //
											task.name, //
											task.id, //
											"host", //
											App.name(), //
											App.userId());
									File file = new File(FsUtils.SD_CARD, path);
									FsUtils.createFolder(file.getParentFile());
									FsUtils.writeText(file, LOGJson.getStr(mCheckStr));
									LysUpload.doUpload(context, file, path, null);
								}
								{
									String path = String.format("/check_diff/%s_%s/%s_%s_%s.txt", //
											task.name, //
											task.id, //
											"guest", //
											user.name, //
											user.id);
									File file = new File(FsUtils.SD_CARD, path);
									FsUtils.createFolder(file.getParentFile());
									FsUtils.writeText(file, LOGJson.getStr(str));
									LysUpload.doUpload(context, file, path, null);
								}
							}
							remotesAdapter.notifyDataSetChanged();
						}
					}
					else
					{
						List<SSvnDirObj> objs = getCheckDir();
						String str = SSvnDirObj.saveList(objs).toString();
						TransMessage.send(mTargetId, TransMessage.obtain(AppConfig.TransEvt_TeachNotify_Check, str), null);
					}
				}
			}
//			else if (intent.getAction().equals(LysIM.ActionTrans(context, AppConfig.TransEvt_TeachSyncRequest)))
//			{
//				if (modeIsSync())
//				{
//					DialogWait.show(context, "同步中。。。");
//					if (isHost)
//					{
//						syncUpload();
//					}
//				}
//			}
//			else if (intent.getAction().equals(LysIM.ActionTrans(context, AppConfig.TransEvt_TeachSyncDownload)))
//			{
//				if (modeIsSync())
//				{
//					Message message = intent.getParcelableExtra("message");
//					TransMessage transMessage = (TransMessage) message.getContent();
//					syncDownload(transMessage.getMsg());
//				}
//			}
//			else if (intent.getAction().equals(LysIM.ActionTrans(context, AppConfig.TransEvt_TeachSyncSuccess)))
//			{
//				if (modeIsSync())
//				{
//					LOG.toast(context, "同步成功");
//					DialogWait.close();
//				}
//			}
//			else if (intent.getAction().equals(LysIM.ActionTrans(context, AppConfig.TransEvt_TeachSyncFail)))
//			{
//				if (modeIsSync())
//				{
//					LOG.toast(context, "同步失败");
//					DialogWait.close();
//				}
//			}
		}
	};

}
