package com.lys.player.utils;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.provider.Settings;
import android.view.WindowManager;

import com.lys.base.utils.LOG;

public class PlayerUtils
{
	public static final int MAX_BRIGHTNESS = 255;

	public static int getBrightnessMode(Context context)
	{
		int brightnessMode = -1;
		try
		{
			brightnessMode = Settings.System.getInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE);
		}
		catch (Settings.SettingNotFoundException e)
		{
			e.printStackTrace();
		}
//		LOG.v("getBrightnessMode " + brightnessMode);
		return brightnessMode;
	}

	public static void setBrightnessMode(Context context, int brightnessMode)
	{
		try
		{
//			LOG.v("setBrightnessMode " + brightnessMode);
			Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE, brightnessMode);
		}
		catch (Exception e)
		{
			LOG.v("setBrightnessMode error");
			e.printStackTrace();
		}
	}

	// 0-255
	public static int getBrightness(Context context)
	{
		int brightness = 0;
		try
		{
			brightness = Settings.System.getInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
		}
		catch (Settings.SettingNotFoundException e)
		{
			e.printStackTrace();
		}
//		LOG.v("getBrightness " + brightness);
		return brightness;
	}

	public static void setBrightness(Context context, int brightness)
	{
		try
		{
//			LOG.v("setBrightness " + brightness);
			Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, brightness);
		}
		catch (Exception e)
		{
			LOG.v("setBrightness error");
			e.printStackTrace();
		}
	}

	public static void setAppBrightness(Context context, int brightness)
	{
		if (context instanceof Activity)
		{
//			LOG.v("setAppBrightness " + brightness);
			Activity activity = (Activity) context;
			WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
			lp.screenBrightness = brightness / 255f;
			activity.getWindow().setAttributes(lp);
		}
	}

	public static int getMusicMaxVolume(Context context)
	{
		AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
		return audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
	}

	public static int getMusicVolume(Context context)
	{
		AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
		int volume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
//		LOG.v("getMusicVolume " + volume);
		return volume;
	}

	public static void setMusicVolume(Context context, int volume)
	{
//		LOG.v("setMusicVolume " + volume);
		AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
		audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, volume, 0);
	}

}
