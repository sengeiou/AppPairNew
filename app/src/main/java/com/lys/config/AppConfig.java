package com.lys.config;

import android.content.Context;
import android.text.TextUtils;

import com.lys.App;
import com.lys.base.utils.FsUtils;
import com.lys.base.utils.SysUtils;
import com.lys.protobuf.SNoteBook;
import com.lys.protobuf.SOrder;
import com.lys.protobuf.SPTask;
import com.lys.protobuf.SUserType;

import java.io.File;

public class AppConfig
{
	public static final int BoardStdWidth = 1920;
	public static final int BoardStdHeight = 1200;

	public static final long TeachCallWaitTime = 60 * 1000;

	public static final String TransEvt_FriendChange = "FriendChange";
	public static final String TransEvt_RefreshUserInfo = "RefreshUserInfo";

	public static final String TransEvt_CpCall = "CpCall";
//	public static final String TransEvt_CpRefuse = "CpRefuse";
//	public static final String TransEvt_CpAgree = "CpAgree";

	public static final String TransEvt_TeachCall = "TeachCall";
	public static final String TransEvt_TeachRefuse = "TeachRefuse";
	public static final String TransEvt_TeachAgree = "TeachAgree";
	public static final String TransEvt_TeachReady = "TeachReady";
	public static final String TransEvt_TeachStart = "TeachStart";
	public static final String TransEvt_TeachOver = "TeachOver";
	public static final String TransEvt_TeachQuit = "TeachQuit";

	public static final String TransEvt_TeachNotify_MuteAudio = "TeachNotify_MuteAudio";
	public static final String TransEvt_TeachNotify_LockWrite = "TeachNotify_LockWrite";
	public static final String TransEvt_TeachNotify_Check = "TeachNotify_Check";

//	public static final String TransEvt_TeachSyncRequest = "TeachSyncRequest";
//	public static final String TransEvt_TeachSyncDownload = "TeachSyncDownload";
//	public static final String TransEvt_TeachSyncSuccess = "TeachSyncSuccess";
//	public static final String TransEvt_TeachSyncFail = "TeachSyncFail";

	public static final String defaultHead = "http://file.k12-eco.com/head/default_head.jpg";

//	public static final int noneColor = 0xff888888;
//	public static final int jobColor = 0xff92d9d8;
//	public static final int classColor = 0xffbb36aa;
//	public static final int receiveRes = R.drawable.img_tree_point_school;
//	public static final int createRes = R.drawable.img_tree_point_master;

	public static final String TopicContentName = "topic_content";
	public static final String TopicSelectionGroupName = "topic_selection_group";

	public static final String EventAction_StartApp = "StartApp";

	public static final String EventAction_InTask = "InTask";
	public static final String EventAction_OutTask = "OutTask";
	public static final String EventAction_CommitJob = "CommitJob";
	public static final String EventAction_ReadOver = "ReadOver";

	public static final String EventAction_TeachCall = "TeachCall";
	public static final String EventAction_TeachRefuse = "TeachRefuse";
	public static final String EventAction_TeachAgree = "TeachAgree";
	public static final String EventAction_TeachReady = "TeachReady";
	public static final String EventAction_TeachStart = "TeachStart";
	public static final String EventAction_TeachOver = "TeachOver";
	public static final String EventAction_TeachQuit = "TeachQuit";

	public static final String EventAction_ScanTask = "ScanTask";

	public static final String EventAction_InLive = "InLive";
	public static final String EventAction_OutLive = "OutLive";
	public static final String EventAction_InRecordLive = "InRecordLive";
	public static final String EventAction_OutRecordLive = "OutRecordLive";

	public static boolean hasSender(SPTask task)
	{
		return task.sendUser != null && !TextUtils.isEmpty(task.sendUser.id);
	}

	public static boolean hasGoods(SOrder order)
	{
		return order.goods != null && !TextUtils.isEmpty(order.goods.id);
	}

	public static boolean isSelfTask(SPTask task)
	{
		return task.userId.equals(App.userId());
	}

	//------------------------------------

	public static String getTaskPath(SPTask task)
	{
		return String.format("/lys.tasks/%s/%s", task.userId, task.id);
	}

	public static String getTaskDir(SPTask task)
	{
		return FsUtils.SD_CARD + getTaskPath(task);
	}

	//------------------------------------

	public static String getNotePath(Context context, String bookDir)
	{
		return String.format("/lys.notes/%s", bookDir);
	}

	public static String getNoteDir(Context context, SNoteBook book)
	{
		return FsUtils.SD_CARD + getNotePath(context, book.bookDir);
	}

	public static String getNoteDir(Context context, String bookDir)
	{
		return FsUtils.SD_CARD + getNotePath(context, bookDir);
	}

	public static String getBooksetFile(Context context)
	{
		return String.format("%s/lys.notes/bookset.json", FsUtils.SD_CARD);
	}

	//------------------------------------

	public static String getTopicPath(String topicId)
	{
		return String.format("/lys.topics/%s/%s/%s", App.userId(), topicId.substring(topicId.length() - 2), topicId);
	}

	public static String getTopicDir(String topicId)
	{
		return FsUtils.SD_CARD + getTopicPath(topicId);
	}

	//------------------------------------

//	public static void deleteAccountPsw()
//	{
//		FsUtils.delete(accountFile());
//		FsUtils.delete(pswFile());
//	}

	private static File accountFile()
	{
		if (SysUtils.isDebug())
			return new File(FsUtils.SD_CARD + "/lys_account_d.info");
		else
			return new File(FsUtils.SD_CARD + "/lys_account.info");
	}

	private static File pswFile()
	{
		if (SysUtils.isDebug())
			return new File(FsUtils.SD_CARD + "/lys_psw_d.info");
		else
			return new File(FsUtils.SD_CARD + "/lys_psw.info");
	}

	public static void deletePsw()
	{
		FsUtils.delete(pswFile());
	}

	public static void saveAccountPsw(String account, String psw)
	{
		FsUtils.writeText(accountFile(), account);
		FsUtils.writeText(pswFile(), psw);
	}

	public static String readAccount()
	{
		String account = FsUtils.readText(accountFile());
		if (!TextUtils.isEmpty(account))
			return account;
		else
			return "";
	}

	public static String readPsw()
	{
		String psw = FsUtils.readText(pswFile());
		if (!TextUtils.isEmpty(psw))
			return psw;
		else
			return "";
	}

	//------------------------------------

	public static boolean vipIsPast(long vipTime)
	{
		return System.currentTimeMillis() - App.TimeOffset > vipTime;
	}

	public static String getGradeName(int grade)
	{
		switch (grade)
		{
		case 7:
			return "初一";
		case 8:
			return "初二";
		case 9:
			return "初三";
		case 10:
			return "高一";
		case 11:
			return "高二";
		case 12:
			return "高三";
		}
		return "未知";
	}

	public static String getUserTypeName(int userType)
	{
		switch (userType)
		{
		case SUserType.SupterMaster:
			return "超管";
		case SUserType.Master:
			return "普管";
		case SUserType.Teacher:
			return "老师";
		case SUserType.Student:
			return "学生";
		}
		return "未知";
	}

}
