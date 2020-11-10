package com.lys.player;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.lys.kit.module.ModulePlayer;
import com.lys.player.activity.PlayFullActivity;
import com.lys.player.activity.PlayVideoActivity;

public class CModulePlayer extends ModulePlayer
{
	private Context context;

	public CModulePlayer(Context context)
	{
		this.context = context.getApplicationContext();
	}

	@Override
	protected void destroy()
	{
	}

	@Override
	public void play(Context context, String path)
	{
		Intent intent = new Intent(context, PlayFullActivity.class);
		intent.putExtra("path", path);
		context.startActivity(intent);
	}

	@Override
	public void play(Context context, Uri uri)
	{
		Intent intent = new Intent(context, PlayFullActivity.class);
		intent.putExtra("uri", uri);
		context.startActivity(intent);
	}

	@Override
	public void playSimple(Context context, String path)
	{
		Activity activity = (Activity) context;
		Intent intent = new Intent(context, PlayVideoActivity.class);
		intent.putExtra("path", path);
		activity.startActivityForResult(intent, 0); // 这里如果不用startActivityForResult，聊天页面无法恢复，原因未知
	}

	@Override
	public void playSimple(Context context, Uri uri)
	{
		Activity activity = (Activity) context;
		Intent intent = new Intent(context, PlayVideoActivity.class);
		intent.putExtra("uri", uri);
		activity.startActivityForResult(intent, 0); // 这里如果不用startActivityForResult，聊天页面无法恢复，原因未知
	}
}
