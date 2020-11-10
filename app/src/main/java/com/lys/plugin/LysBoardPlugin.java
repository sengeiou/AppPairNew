package com.lys.plugin;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

import com.lys.app.R;
import com.lys.base.utils.FsUtils;
import com.lys.fragment.LysConversationFragment;
import com.lys.kit.activity.ActivityBoard;
import com.lys.kit.config.Config;

import java.util.LinkedHashMap;

import io.rong.imkit.RongExtension;
import io.rong.imkit.plugin.IPluginModule;
import io.rong.imkit.plugin.IPluginRequestPermissionResultCallback;
import io.rong.imkit.utilities.PermissionCheckUtil;
import io.rong.imlib.model.Conversation;

public class LysBoardPlugin implements IPluginModule, IPluginRequestPermissionResultCallback
{
	private static final int REQUEST_CODE_BODRD = 120;

	private Conversation.ConversationType conversationType;
	private String targetId;
	private LysConversationFragment fragment;
	private RongExtension extension;

	@Override
	public Drawable obtainDrawable(Context context)
	{
		return ContextCompat.getDrawable(context, R.drawable.plugin_board_selector);
	}

	@Override
	public String obtainTitle(Context context)
	{
		return "画布";
	}

	@Override
	public void onClick(Fragment fragment, RongExtension extension)
	{
		this.conversationType = extension.getConversationType();
		this.targetId = extension.getTargetId();
		this.fragment = (LysConversationFragment) fragment;
		this.extension = extension;
		String[] permissions = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
		if (PermissionCheckUtil.checkPermissions(fragment.getContext(), permissions))
		{
			start(fragment, extension);
		}
		else
		{
			extension.requestPermissionForPluginResult(permissions, IPluginRequestPermissionResultCallback.REQUEST_CODE_PERMISSION_PLUGIN, this);
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, final Intent data)
	{
		if (requestCode == REQUEST_CODE_BODRD)
		{
			if (resultCode == Activity.RESULT_OK)
			{
				new Handler().postDelayed(new Runnable()
				{
					@Override
					public void run()
					{
						try
						{
							String filepath = data.getStringExtra("path");

							boolean sendOrigin = true;
							LinkedHashMap<String, Integer> linkedHashMap = new LinkedHashMap();
							linkedHashMap.put("file://" + filepath, 1);

							fragment.onImageResult(linkedHashMap, sendOrigin);
						}
						catch (Exception e)
						{
							e.printStackTrace();
						}
					}
				}, 50);
			}
		}
	}

	private void start(Fragment fragment, RongExtension extension)
	{
		FsUtils.delete(Config.tmpRecordBoardDir);
		Intent intent = new Intent(fragment.getActivity(), ActivityBoard.class);
		extension.startActivityForPluginResult(intent, REQUEST_CODE_BODRD, this);
	}

	@Override
	public boolean onRequestPermissionResult(Fragment fragment, RongExtension extension, int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
	{
		if (PermissionCheckUtil.checkPermissions(fragment.getActivity(), permissions))
		{
			start(fragment, extension);
		}
		else
		{
			extension.showRequestPermissionFailedAlter(PermissionCheckUtil.getNotGrantedPermissionMsg(fragment.getActivity(), permissions, grantResults));
		}
		return true;
	}
}
