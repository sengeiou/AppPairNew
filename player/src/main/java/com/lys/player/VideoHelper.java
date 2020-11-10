package com.lys.player;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.lys.player.activity.PlayFullActivity;

public class VideoHelper
{
	public static void play(Context context, String path)
	{
		Intent intent = new Intent(context, PlayFullActivity.class);
		intent.putExtra("path", path);
		context.startActivity(intent);
	}

	public static void play(Context context, Uri uri)
	{
		Intent intent = new Intent(context, PlayFullActivity.class);
		intent.putExtra("uri", uri);
		context.startActivity(intent);
	}
}
