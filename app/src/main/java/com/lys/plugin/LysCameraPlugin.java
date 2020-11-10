package com.lys.plugin;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;

import com.lys.app.R;
import com.lys.base.utils.FsUtils;
import com.lys.fragment.LysConversationFragment;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;

import io.rong.imkit.RongExtension;
import io.rong.imkit.plugin.IPluginModule;
import io.rong.imkit.plugin.IPluginRequestPermissionResultCallback;
import io.rong.imkit.utilities.PermissionCheckUtil;
import io.rong.imlib.model.Conversation;

public class LysCameraPlugin implements IPluginModule, IPluginRequestPermissionResultCallback
{
	private static final int REQUEST_CODE_CAMERA = 121;

	private Conversation.ConversationType conversationType;
	private String targetId;
	private LysConversationFragment fragment;
	private RongExtension extension;

	@Override
	public Drawable obtainDrawable(Context context)
	{
		return ContextCompat.getDrawable(context, R.drawable.plugin_camera_selector);
	}

	@Override
	public String obtainTitle(Context context)
	{
		return "拍照";
	}

	@Override
	public void onClick(Fragment fragment, RongExtension extension)
	{
		this.conversationType = extension.getConversationType();
		this.targetId = extension.getTargetId();
		this.fragment = (LysConversationFragment) fragment;
		this.extension = extension;
		String[] permissions = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
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
		if (requestCode == REQUEST_CODE_CAMERA)
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
							boolean sendOrigin = false;
							LinkedHashMap<String, Integer> linkedHashMap = new LinkedHashMap();
							linkedHashMap.put("file://" + mFilepath, 1);

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

	private String mFilepath;

	public String AUTHORITY(Context context)
	{
		return context.getPackageName() + ".FileProvider";
	}

	private void start(Fragment fragment, RongExtension extension)
	{
		try
		{
			String cameraDir = String.format("%s/DCIM/Camera", FsUtils.SD_CARD);
			new File(cameraDir).mkdirs();

			Date date = new Date();
			for (int i = 0; ; i++)
			{
				if (i == 0)
					mFilepath = String.format("%s/IMAGE_%s.jpg", cameraDir, new SimpleDateFormat("yyyyMMdd_HHmmss").format(date));
				else
					mFilepath = String.format("%s/IMAGE_%s_%d.jpg", cameraDir, new SimpleDateFormat("yyyyMMdd_HHmmss").format(date), i);
				if (!new File(mFilepath).exists())
					break;
			}

			Intent intent = new Intent();
			intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
			{
				intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
				intent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(fragment.getActivity(), AUTHORITY(fragment.getActivity()), new File(mFilepath)));
			}
			else
			{
				intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(mFilepath)));
			}
			intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
			extension.startActivityForPluginResult(intent, REQUEST_CODE_CAMERA, this);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
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
