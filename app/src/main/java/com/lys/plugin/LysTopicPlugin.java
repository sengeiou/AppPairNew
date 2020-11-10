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
import com.lys.base.utils.AppDataTool;
import com.lys.base.utils.ImageLoader;
import com.lys.base.utils.JsonHelper;
import com.lys.fragment.LysConversationFragment;
import com.lys.kit.activity.ActivityTopicSearch;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.List;

import io.rong.imkit.RongExtension;
import io.rong.imkit.plugin.IPluginModule;
import io.rong.imkit.plugin.IPluginRequestPermissionResultCallback;
import io.rong.imkit.utilities.PermissionCheckUtil;
import io.rong.imlib.model.Conversation;

public class LysTopicPlugin implements IPluginModule, IPluginRequestPermissionResultCallback
{
	//	private static final int REQUEST_CODE_TOPIC_FILTER = 124;
//	private static final int REQUEST_CODE_TOPIC_SELECT = 125;
	private static final int REQUEST_CODE_TOPIC_SEARCH = 126;

	private Conversation.ConversationType conversationType;
	private String targetId;
	private LysConversationFragment fragment;
	private RongExtension extension;

	@Override
	public Drawable obtainDrawable(Context context)
	{
		return ContextCompat.getDrawable(context, R.drawable.plugin_topic_selector);
	}

	@Override
	public String obtainTitle(Context context)
	{
		return "题目";
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
//		if (requestCode == REQUEST_CODE_TOPIC_FILTER)
//		{
//			if (resultCode == Activity.RESULT_OK)
//			{
//				new Handler().postDelayed(new Runnable()
//				{
//					@Override
//					public void run()
//					{
//						try
//						{
//							String result = data.getStringExtra("result");
//							select(fragment, extension, result);
//						}
//						catch (Exception e)
//						{
//							e.printStackTrace();
//						}
//					}
//				}, 50);
//			}
//		}
//		else if (requestCode == REQUEST_CODE_TOPIC_SELECT)
//		{
//			if (resultCode == Activity.RESULT_OK)
//			{
//				new Handler().postDelayed(new Runnable()
//				{
//					@Override
//					public void run()
//					{
//						try
//						{
//							String result = data.getStringExtra("result");
//
//							List<String> paths = AppDataTool.loadStringList(JsonHelper.getJSONArray(result));
//
//							boolean sendOrigin = true;
//							LinkedHashMap<String, Integer> linkedHashMap = new LinkedHashMap();
//							linkedHashMap.put("file://" + paths.get(0), 1);
//							linkedHashMap.put("file://" + paths.get(1), 1);
//
//							fragment.onImageResult(linkedHashMap, sendOrigin);
//						}
//						catch (Exception e)
//						{
//							e.printStackTrace();
//						}
//					}
//				}, 50);
//			}
//		}
//		else
		if (requestCode == REQUEST_CODE_TOPIC_SEARCH)
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
							String result = data.getStringExtra("result");

							List<String> paths = AppDataTool.loadStringList(JsonHelper.getJSONArray(result));

							File contentFile = ImageLoader.getCacheFile(fragment.getActivity(), paths.get(0));
							File analyFile = ImageLoader.getCacheFile(fragment.getActivity(), paths.get(3));

							boolean sendOrigin = true;
							LinkedHashMap<String, Integer> linkedHashMap = new LinkedHashMap();
							linkedHashMap.put("file://" + contentFile.toString(), 1);
							linkedHashMap.put("file://" + analyFile.toString(), 1);

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
//		Intent intent = new Intent(fragment.getActivity(), ActivityTopicFilter.class);
//		extension.startActivityForPluginResult(intent, REQUEST_CODE_TOPIC_FILTER, this);
		Intent intent = new Intent(fragment.getActivity(), ActivityTopicSearch.class);
		extension.startActivityForPluginResult(intent, REQUEST_CODE_TOPIC_SEARCH, this);
	}

//	private void select(Fragment fragment, RongExtension extension, String filter)
//	{
//		Intent intent = new Intent(fragment.getActivity(), ActivityTopicSelect.class);
//		intent.putExtra("filter", filter);
////		intent.putExtra("isJpg", true);
//		extension.startActivityForPluginResult(intent, REQUEST_CODE_TOPIC_SELECT, this);
//	}

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
