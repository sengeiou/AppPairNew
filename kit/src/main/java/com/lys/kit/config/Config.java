package com.lys.kit.config;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.text.TextUtils;

import com.lys.base.utils.AppDataTool;
import com.lys.base.utils.FsUtils;
import com.lys.base.utils.JsonHelper;
import com.lys.base.utils.LOGJson;
import com.lys.base.utils.SysUtils;
import com.lys.kit.AppKit;
import com.lys.protobuf.SClipboard;
import com.lys.protobuf.SPhase;
import com.lys.protobuf.SRequest_SearchTopics;
import com.lys.protobuf.SResponse_GetConfig;
import com.lys.protobuf.SSubject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Config
{
	public static final File tmpJpgFile = new File(FsUtils.SD_CARD + "/_lys_tmp_.jpg");
	public static final File tmpJpgFile2 = new File(FsUtils.SD_CARD + "/_lys_tmp_2_.jpg");
	public static final File tmpPngFile = new File(FsUtils.SD_CARD + "/_lys_tmp_.png");
	public static final File tmpPngFile2 = new File(FsUtils.SD_CARD + "/_lys_tmp_2_.png");
	public static final File tmpMp4File = new File(FsUtils.SD_CARD + "/_lys_tmp_.mp4");
	public static final File tmpRecordBoardDir = new File(FsUtils.SD_CARD + "/_lys_tmp_record_board_dir_");
	public static final File tmpPagesetFile = new File(FsUtils.SD_CARD + "/_lys_tmp_pageset_.json");

	public static File genTmpFile(String suffix)
	{
		for (int i = 0; ; i++)
		{
			String name = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new Date(System.currentTimeMillis() + i * 1000));
			File file = new File(String.format("%s/__lys_tmp_%s__%s", FsUtils.SD_CARD, name, suffix));
			if (!file.exists())
				return file;
		}
	}

	public static final String key = "0914";

	public static Typeface CurrTypeface = Typeface.DEFAULT;

//	public static final String PackageNameDesktop = "com.lys.app.desktop";
//	public static final String PackageNameTeacher = "com.lys.app.teacher";
//	public static final String PackageNameTask = "com.lys.app.task";
//	public static final String PackageNameTopic = "com.lys.app.topic";
//	public static final String PackageNameNote = "com.lys.app.note";
//	public static final String PackageNameWrong = "com.lys.app.wrong";
//	public static final String PackageNameBook = "com.lys.app.book";
//	public static final String PackageNameMarket = "com.lys.app.market";

	public static File getLysConfigDir()
	{
		File configDir = new File(FsUtils.SD_CARD + "/lys_config");
		FsUtils.createFolder(configDir);
		return configDir;
	}

	public static File getAppConfigDir()
	{
		File dir = new File(FsUtils.SD_CARD + "/" + AppKit.getContext().getPackageName());
		FsUtils.createFolder(dir);
		return dir;
	}

	//------------------------------------

//	public static void setShowBall(Context context, boolean show)
//	{
//		FsUtils.writeText(new File(FsUtils.SD_CARD + "/lys_ball.info"), String.valueOf(show));
//	}
//
//	public static boolean isShowBall(Context context)
//	{
//		String showBall = FsUtils.readText(new File(FsUtils.SD_CARD + "/lys_ball.info"));
//		if (TextUtils.isEmpty(showBall))
//			return true;
//		else
//			return Boolean.valueOf(showBall);
//	}

	//------------------------------------

	public static void setDebug(boolean debug)
	{
		File configFile = new File(getLysConfigDir(), "debug.info");
		FsUtils.writeText(configFile, String.valueOf(debug));
	}

	public static boolean isDebug()
	{
		File configFile = new File(getLysConfigDir(), "debug.info");
		String debug = FsUtils.readText(configFile);
		if (TextUtils.isEmpty(debug))
			return SysUtils.isDebug();
		else
			return Boolean.valueOf(debug);
	}

	//------------------------------------

	public static final int SleepTime_forever = 2147483647;
	public static final int SleepTime_15Seconds = 15000;
	public static final int SleepTime_30Seconds = 30000;
	public static final int SleepTime_1Minutes = 60000;
	public static final int SleepTime_2Minutes = 120000;
	public static final int SleepTime_5Minutes = 300000;
	public static final int SleepTime_10Minutes = 600000;
	public static final int SleepTime_30Minutes = 1800000;

	public static void setSleepTime(Context context, int sleepTime)
	{
		File configFile = new File(getLysConfigDir(), "sleep_time.info");
		FsUtils.writeText(configFile, String.valueOf(sleepTime));
		Intent intent = new Intent();
		intent.setAction("com.hra.setTimeOut");
		intent.putExtra("time", sleepTime);
		context.sendBroadcast(intent);
	}

	public static int getSleepTime(Context context)
	{
		File configFile = new File(getLysConfigDir(), "sleep_time.info");
		String sleepTime = FsUtils.readText(configFile);
		if (TextUtils.isEmpty(sleepTime))
			return SleepTime_5Minutes;
		else
			return Integer.valueOf(sleepTime);
	}

	//------------------------------------

	private static void writeClearDir(Context context, String info)
	{
		File configFile = new File(getLysConfigDir(), "clear_dir.info");
		FsUtils.writeText(configFile, info);
	}

	private static String readClearDir(Context context)
	{
		File configFile = new File(getLysConfigDir(), "clear_dir.info");
		return FsUtils.readText(configFile);
	}

	public static List<String> getClearDir(Context context)
	{
		String info = readClearDir(context);
		List<String> list = null;
		if (!TextUtils.isEmpty(info))
		{
			list = AppDataTool.loadStringList(JsonHelper.getJSONArray(info));
		}
		else
		{
			list = new ArrayList<>();
		}
		return list;
	}

	public static void regClearDir(Context context, File... dirs)
	{
		List<String> list = getClearDir(context);
		boolean modify = false;
		for (File dir : dirs)
		{
			if (!list.contains(dir.getAbsolutePath()))
			{
				list.add(dir.getAbsolutePath());
				modify = true;
			}
		}
		if (modify)
			writeClearDir(context, LOGJson.getStr(AppDataTool.saveStringList(list).toString()));
	}

	//------------------------------------

//	public static void writeServerInfo(Context context, String info)
//	{
//		FsUtils.writeText(new File(FsUtils.SD_CARD + "/lys_server.info"), info);
//	}
//
//	public static String readServerInfo(Context context)
//	{
//		return FsUtils.readText(new File(FsUtils.SD_CARD + "/lys_server.info"));
//	}

	//------------------------------------

//	public static void writeTopicFilter(String info)
//	{
//		FsUtils.writeText(new File(FsUtils.SD_CARD + "/lys_topic_filter.info"), info);
//	}
//
//	public static STopicFilter readTopicFilter()
//	{
//		String info = FsUtils.readText(new File(FsUtils.SD_CARD + "/lys_topic_filter.info"));
//		if (!TextUtils.isEmpty(info))
//		{
//			STopicFilter topicFilter = STopicFilter.load(info);
//			return topicFilter;
//		}
//		return null;
//	}

	//------------------------------------

	public static void writeClipboard(SClipboard clipboard)
	{
		File configFile = new File(getLysConfigDir(), "clipboard.info");
		FsUtils.writeText(configFile, clipboard.saveToStr());
	}

	public static SClipboard readClipboard()
	{
		File configFile = new File(getLysConfigDir(), "clipboard.info");
		String info = FsUtils.readText(configFile);
		if (!TextUtils.isEmpty(info))
		{
			SClipboard clipboard = SClipboard.load(info);
			return clipboard;
		}
		return null;
	}

	//------------------------------------

	public static void writeTopicSearch(String info)
	{
		File configFile = new File(getLysConfigDir(), "topic_search.info");
		FsUtils.writeText(configFile, info);
	}

	public static SRequest_SearchTopics readTopicSearch()
	{
		File configFile = new File(getLysConfigDir(), "topic_search.info");
		String info = FsUtils.readText(configFile);
		if (!TextUtils.isEmpty(info))
		{
			SRequest_SearchTopics topicSearch = SRequest_SearchTopics.load(info);
			return topicSearch;
		}
		return null;
	}

	//------------------------------------

	public static void writeTopicFilter(String info, int phase, int subject)
	{
		File configFile = new File(getLysConfigDir(), String.format("/topic_filter_%s_%s.info", phase, subject));
		FsUtils.writeText(configFile, info);
	}

	public static SRequest_SearchTopics readTopicFilter(int phase, int subject)
	{
		File configFile = new File(getLysConfigDir(), String.format("/topic_filter_%s_%s.info", phase, subject));
		String info = FsUtils.readText(configFile);
		if (!TextUtils.isEmpty(info))
		{
			SRequest_SearchTopics topicSearch = SRequest_SearchTopics.load(info);
			return topicSearch;
		}
		return null;
	}

	//------------------------------------

	public static void writeAppConfigInfo(String info)
	{
		File configFile = new File(getLysConfigDir(), "app_config.info");
		FsUtils.writeText(configFile, info);
	}

	public static SResponse_GetConfig readAppConfigInfo()
	{
		File configFile = new File(getLysConfigDir(), "app_config.info");
		String info = FsUtils.readText(configFile);
		if (!TextUtils.isEmpty(info))
		{
			SResponse_GetConfig configInfo = SResponse_GetConfig.load(info);
			return configInfo;
		}
		else
		{
			return null;
		}
	}

	public static String getApi()
	{
		SResponse_GetConfig config = readAppConfigInfo();
		return config != null ? config.api : "";
	}

	public static String getUrlRoot()
	{
		SResponse_GetConfig config = readAppConfigInfo();
		return config != null ? config.urlRoot : "";
	}

	//------------------------------------

//	public static void writeConfigInfo(Context context, String info)
//	{
//		FsUtils.writeText(new File(FsUtils.SD_CARD + "/lys_config.info"), info);
//	}
//
//	private static String readConfigInfo(Context context)
//	{
//		return FsUtils.readText(new File(FsUtils.SD_CARD + "/lys_config.info"));
//	}
//
//	public static SResponse_GetConfigLogic getConfigInfo(Context context)
//	{
//		String info = readConfigInfo(context);
//		if (!TextUtils.isEmpty(info))
//		{
//			SResponse_GetConfigLogic configInfo = SResponse_GetConfigLogic.load(info);
//			return configInfo;
//		}
//		else
//		{
//			return null;
//		}
//	}
//
//	public static String getNginxUrl(Context context)
//	{
//		String info = readConfigInfo(context);
//		if (!TextUtils.isEmpty(info))
//		{
//			SResponse_GetConfigLogic configInfo = SResponse_GetConfigLogic.load(info);
//			return configInfo.nginxUrl;
//		}
//		else
//		{
//			return "";
//		}
//	}

//	public static int getChunkSize(Context context)
//	{
//		String info = readConfigInfo(context);
//		if (!TextUtils.isEmpty(info))
//		{
//			SResponse_GetConfigLogic configInfo = SResponse_GetConfigLogic.load(info);
//			return configInfo.chunkSize;
//		}
//		else
//		{
//			return 0;
//		}
//	}

//	public static String getUploadFileUrl(Context context)
//	{
//		String info = readConfigInfo(context);
//		if (!TextUtils.isEmpty(info))
//		{
//			SResponse_GetConfigLogic configInfo = SResponse_GetConfigLogic.load(info);
//			return configInfo.uploadFileUrl;
//		}
//		else
//		{
//			return "";
//		}
//	}

//	public static String getCheckFileUrl(Context context)
//	{
//		String info = readConfigInfo(context);
//		if (!TextUtils.isEmpty(info))
//		{
//			SResponse_GetConfigLogic configInfo = SResponse_GetConfigLogic.load(info);
//			return configInfo.checkFileUrl;
//		}
//		else
//		{
//			return "";
//		}
//	}

//	public static String getQuestionUrl(Context context)
//	{
//		String info = readConfigInfo(context);
//		if (!TextUtils.isEmpty(info))
//		{
//			SResponse_GetConfigLogic configInfo = SResponse_GetConfigLogic.load(info);
//			return configInfo.questionUrl;
//		}
//		else
//		{
//			return "";
//		}
//	}

	//------------------------------------

//	public static void writeLoginInfo(Context context, String info)
//	{
//		FsUtils.writeText(new File(FsUtils.SD_CARD + "/lys_login.info"), info);
////		if (context.getPackageName().equals("com.lys.app.desktop"))
////		{
////			SharedPreferences.Editor editor = context.getSharedPreferences(loginInfo, Context.MODE_WORLD_READABLE).edit();
////			editor.putString(loginInfo, info);
////			editor.apply();
////		}
//	}
//
//	private static String readLoginInfo(Context context)
//	{
//		return FsUtils.readText(new File(FsUtils.SD_CARD + "/lys_login.info"));
////		try
////		{
////			Context context = context.createPackageContext("com.lys.app.desktop", Context.CONTEXT_IGNORE_SECURITY);
////			SharedPreferences sp = context.getSharedPreferences(loginInfo, Context.MODE_WORLD_READABLE);
////			return sp.getString(loginInfo, null);
////		}
////		catch (PackageManager.NameNotFoundException e)
////		{
////			e.printStackTrace();
////			return null;
////		}
//	}

//	public static boolean logined(Context context, String pkgName, int versionCode, String versionName)
//	{
//		String info = readLoginInfo(context);
//		if (!TextUtils.isEmpty(info))
//		{
//			SResponse_LoginLogic loginInfo = SResponse_LoginLogic.load(info);
//			if (pkgName.equals(loginInfo.pkgName) && versionCode == loginInfo.versionCode && versionName.equals(loginInfo.versionName))
//				return true;
//		}
//		return false;
//	}

//	public static SResponse_LoginLogic getLoginInfo(Context context)
//	{
//		String info = readLoginInfo(context);
//		if (!TextUtils.isEmpty(info))
//		{
//			SResponse_LoginLogic loginInfo = SResponse_LoginLogic.load(info);
//			return loginInfo;
//		}
//		else
//		{
//			return null;
//		}
//	}

//	public static String getPersonId(Context context)
//	{
//		String info = readLoginInfo(context);
//		if (!TextUtils.isEmpty(info))
//		{
//			SResponse_LoginLogic loginInfo = SResponse_LoginLogic.load(info);
//			return loginInfo.personId;
//		}
//		else
//		{
//			return "";
//		}
//	}

//	public static String getToken(Context context)
//	{
//		String info = readLoginInfo(context);
//		if (!TextUtils.isEmpty(info))
//		{
//			SResponse_LoginLogic loginInfo = SResponse_LoginLogic.load(info);
//			return loginInfo.token;
//		}
//		else
//		{
//			return null;
//		}
//	}

//	public interface OnUserInfoCallback
//	{
//		void onResult(SResponse_GetUserInfo userInfo);
//	}
//
//	public static SResponse_GetUserInfo userInfo = null;
//
//	public static void getUserInfo(Context context, final OnUserInfoCallback callback)
//	{
//		SRequest_GetUserInfo request = new SRequest_GetUserInfo();
//		request.personId = Config.getPersonId(context);
//		Protocol.doPost(context, SHandleId.GetPersonLogic, request.saveToStr(), new Protocol.OnCallback()
//		{
//			@Override
//			public void onResponse(int code, String data, String msg)
//			{
//				if (code == 200)
//				{
//					userInfo = SResponse_GetUserInfo.load(data);
//					if (callback != null)
//						callback.onResult(userInfo);
//				}
//				else
//				{
//					if (callback != null)
//						callback.onResult(null);
//				}
//			}
//		});
//	}

	//------------------------------------

	public static final int Difficulty1 = 1;
	public static final int Difficulty2 = 2;
	public static final int Difficulty3 = 3;
	public static final int Difficulty4 = 4;
	public static final int Difficulty5 = 5;

	public static boolean subjectIsSure(int subject)
	{
		return subject == SSubject.Yu || //
				subject == SSubject.Shu || //
				subject == SSubject.Wai || //
				subject == SSubject.Li || //
				subject == SSubject.Hua || //
				subject == SSubject.Shen || //
				subject == SSubject.Zhen || //
				subject == SSubject.Shi || //
				subject == SSubject.Di;
	}

	public static String getPhaseName(int phase)
	{
		switch (phase)
		{
		case SPhase.Chu:
			return "初中";
		case SPhase.Gao:
			return "高中";
		}
		return "未知";
	}

	public static String getSubjectName(int subject)
	{
		switch (subject)
		{
		case SSubject.All:
			return "全部";
		case SSubject.Any:
			return "未分科";
		case SSubject.Yu:
			return "语文";
		case SSubject.Shu:
			return "数学";
		case SSubject.Wai:
			return "英语";
		case SSubject.Li:
			return "物理";
		case SSubject.Hua:
			return "化学";
		case SSubject.Shen:
			return "生物";
		case SSubject.Zhen:
			return "政治";
		case SSubject.Shi:
			return "历史";
		case SSubject.Di:
			return "地理";
		case SSubject.WenHua:
			return "文化";
		}
		return "未知科目";
	}

//	public static SChapter chapterNull()
//	{
//		SChapter chapter = new SChapter();
//		chapter.id = "";
//		chapter.unit = "全部章节";
//		chapter.level = 0;
//		return chapter;
//	}

	public static String getGradeDes(int grade)
	{
		switch (grade)
		{
		case 1:
			return "一年级";
		case 2:
			return "二年级";
		case 3:
			return "三年级";
		case 4:
			return "四年级";
		case 5:
			return "五年级";
		case 6:
			return "六年级";
		case 7:
			return "七年级";
		case 8:
			return "八年级";
		case 9:
			return "九年级";
		case 10:
			return "高一";
		case 11:
			return "高二";
		case 12:
			return "高三";
		}
		return "未知年级";
	}

}
