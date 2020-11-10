package com.lys.utils;

import android.content.Context;

import com.lys.kit.module.ModuleKit;
import com.lys.kit.utils.Protocol;

import java.io.File;

public class CModuleKit extends ModuleKit
{
	private Context context;

	public CModuleKit(Context context)
	{
		this.context = context.getApplicationContext();
	}

	@Override
	protected void destroy()
	{
	}

	@Override
	public void doUpload(Context context, File file, String path, Protocol.OnCallback callback)
	{
		LysUpload.doUpload(context, file, path, callback);
	}

}
