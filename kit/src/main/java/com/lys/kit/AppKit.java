package com.lys.kit;

import android.app.ActivityManager;
import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.lys.base.utils.FsUtils;
import com.lys.base.utils.ImageLoader;
import com.lys.base.utils.LOG;
import com.lys.base.utils.SysUtils;
import com.lys.board.dawing.LysBoardDrawingBrush;
import com.lys.kit.config.Config;
import com.lys.kit.manager.CrashHandler;
import com.lys.kit.utils.KitUtils;
import com.lys.protobuf.SResponse_GetConfig;
import com.lys.protobuf.SSex;
import com.lys.protobuf.SUser;
import com.lys.protobuf.SUserType;

public class AppKit extends MultiDexApplication
{
	public static AppKit instance = null;
	public static String OnlyId;

	public static Context getContext()
	{
		return instance.getApplicationContext();
	}

	public static String getCurProcessName(Context context)
	{
		int pid = android.os.Process.myPid();
		ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		for (ActivityManager.RunningAppProcessInfo appProcess : activityManager.getRunningAppProcesses())
		{
			if (appProcess.pid == pid)
			{
				return appProcess.processName;
			}
		}
		return null;
	}

	@Override
	public void onCreate()
	{
		super.onCreate();
		LOG.v("========================================================= start app : " + this);
		if (getPackageName().equals(getCurProcessName(this)))
		{
			LOG.v("init AppKit");

			LOG.v("screenWidth(px):" + SysUtils.screenWidth(this));
			LOG.v("screenWidth(dp):" + SysUtils.px2dp(SysUtils.screenWidth(this)));
			LOG.v("screenWidth(sp):" + SysUtils.px2sp(SysUtils.screenWidth(this)));

			instance = this;
			OnlyId = KitUtils.getOnlyId2(this);
			LOG.v("OnlyId : " + OnlyId);

			new CrashHandler().init(this);

			LOG.logfile = Config.isDebug() ? String.format("%s/log_%s.txt", FsUtils.SD_CARD, getPackageName()) : null;

			Config.regClearDir(this, ImageLoader.getCacheDir(this));

			if (KitUtils.isD7())
				LysBoardDrawingBrush.mSensitivity = 6 * 0.5f;
			else
				LysBoardDrawingBrush.mSensitivity = 2 * 0.5f;

//			ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).build();
//			ImageLoader.getInstance().init(config);
		}
	}

//	public static String getCloudDir(Context context)
//	{
//		return String.format("%s/lys.cloud/%s/%s", FsUtils.SD_CARD, Config.getPersonId(context), context.getPackageName());
//	}
//
//	public static String getCloudDir(Context context, String personId)
//	{
//		return String.format("%s/lys.cloud/%s/%s", FsUtils.SD_CARD, personId, context.getPackageName());
//	}

	//----------------------------------

	private static SResponse_GetConfig config = null;

	public static SResponse_GetConfig getConfig()
	{
		return config;
	}

	public static void setConfig(SResponse_GetConfig config)
	{
		AppKit.config = config;
	}

	public static String getApi()
	{
		return config != null ? config.api : "";
	}

	public static String getUpload()
	{
		return config != null ? config.upload : "";
	}

	//----------------------------------

	private static SUser user = null;

	public static SUser getUser()
	{
		return user;
	}

	public static void setUser(SUser user)
	{
		AppKit.user = user;
	}

	public static String userId()
	{
		return user != null ? user.id : "";
	}

	public static Integer userType()
	{
		return user != null ? user.userType : 0;
	}

	public static Integer sex()
	{
		return user != null ? user.sex : SSex.Girl;
	}

	public static String name()
	{
		return user != null ? user.name : "";
	}

	public static String head()
	{
		return user != null ? user.head : "";
	}

	public static void setHead(String url)
	{
		if (user != null)
			user.head = url;
	}

	public static int grade()
	{
		return user != null ? user.grade : 0;
	}

	public static int vipLevel()
	{
		return user != null ? user.vipLevel : 0;
	}

	public static long vipTime()
	{
		return user != null ? user.vipTime : 0;
	}

	public static String phone()
	{
		return user != null ? user.phone : "";
	}

	public static int score()
	{
		return user != null ? user.score : 0;
	}

	public static String cpId()
	{
		return user != null ? user.cpId : "";
	}

	public static String ownerId()
	{
		return user != null ? user.ownerId : "";
	}

	//----------------------------------

	public static boolean isSupterMaster()
	{
		return userType().equals(SUserType.SupterMaster);
	}

	public static boolean isMaster()
	{
		return userType().equals(SUserType.Master);
	}

	public static boolean isStudent()
	{
		return userType().equals(SUserType.Student);
	}

	public static boolean isTeacher()
	{
		return userType().equals(SUserType.Teacher);
	}

}
