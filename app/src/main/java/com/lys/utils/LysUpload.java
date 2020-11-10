package com.lys.utils;

import android.content.Context;
import android.text.TextUtils;

import com.lys.App;
import com.lys.base.utils.CommonUtils;
import com.lys.base.utils.HttpUtils;
import com.lys.base.utils.LOG;
import com.lys.kit.utils.Protocol;
import com.lys.protobuf.SHandleId;
import com.lys.protobuf.SProtocol;
import com.lys.protobuf.SRequest_FileExists;
import com.lys.protobuf.SResponse_FileExists;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class LysUpload
{
//	public interface OnCallback
//	{
//		void onResponse(int code, String data, String msg);
//	}

	public static void doUpload(final Context context, File file, String path, final Protocol.OnCallback callback)
	{
		if (TextUtils.isEmpty(App.getUpload()))
			return;
		MultipartBody.Builder contentBuilder = new MultipartBody.Builder().setType(MultipartBody.FORM);
//		contentBuilder.addFormDataPart("md5", CommonUtils.md5(file));
		contentBuilder.addFormDataPart("path", path);
		contentBuilder.addFormDataPart("file", file.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), file));
		RequestBody requestBody = contentBuilder.build();
		LOG.v("upload : " + file + " --> " + path);
		HttpUtils.doHttpPostImpl(context, App.getUpload(), requestBody, new HttpUtils.OnCallback()
		{
			@Override
			public void onResponse(String jsonStr)
			{
				if (!TextUtils.isEmpty(jsonStr))
				{
					SProtocol trans = null;
					try
					{
						LOG.v("upload result : " + jsonStr);
						trans = SProtocol.load(jsonStr);
					}
					catch (Exception e)
					{
						e.printStackTrace();
						LOG.toast(context, "解析异常");
					}
					if (trans != null)
					{
						if (trans.code == 200)
						{
							if (callback != null)
								callback.onResponse(trans.code, trans.data, trans.msg);
						}
						else
						{
							LOG.toast(context, trans.msg);
							if (callback != null)
								callback.onResponse(trans.code, null, trans.msg);
						}
					}
					else
					{
						LOG.toast(context, "解析失败");
					}
				}
				else
				{
					LOG.v("upload fail : 网络异常");
					LOG.toast(context, "网络异常");
					if (callback != null)
						callback.onResponse(-100, null, "网络异常");
				}
			}
		});
	}

	public static void doUpload(final Context context, final File file, final Protocol.OnCallback callback)
	{
		if (TextUtils.isEmpty(App.getApi()))
			return;
		if (TextUtils.isEmpty(App.getUpload()))
			return;

		int pos = file.getName().lastIndexOf('.');
		String suffix = "";
		if (pos >= 0)
			suffix = file.getName().substring(pos);
		String md5 = CommonUtils.md5(file);
		final String path = String.format("/files/%s%s", md5, suffix);

		SRequest_FileExists request = new SRequest_FileExists();
		request.path = path;
		Protocol.doPost(context, App.getApi(), SHandleId.FileExists, request.saveToStr(), new Protocol.OnCallback()
		{
			@Override
			public void onResponse(int code, String data, String msg)
			{
				if (code == 200)
				{
					SResponse_FileExists response = SResponse_FileExists.load(data);
					if (response.exists)
					{
						if (callback != null)
							callback.onResponse(code, path, msg);
					}
					else
					{
						MultipartBody.Builder contentBuilder = new MultipartBody.Builder().setType(MultipartBody.FORM);
//						contentBuilder.addFormDataPart("md5", md5);
						contentBuilder.addFormDataPart("path", path);
						contentBuilder.addFormDataPart("file", file.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), file));
						RequestBody requestBody = contentBuilder.build();
						LOG.v("upload : " + file + " --> " + path);
						HttpUtils.doHttpPostImpl(context, App.getUpload(), requestBody, new HttpUtils.OnCallback()
						{
							@Override
							public void onResponse(String jsonStr)
							{
								if (!TextUtils.isEmpty(jsonStr))
								{
									SProtocol trans = null;
									try
									{
										LOG.v("upload result : " + jsonStr);
										trans = SProtocol.load(jsonStr);
									}
									catch (Exception e)
									{
										e.printStackTrace();
										LOG.toast(context, "解析异常");
									}
									if (trans != null)
									{
										if (trans.code == 200)
										{
											if (callback != null)
												callback.onResponse(trans.code, trans.data, trans.msg);
										}
										else
										{
											LOG.toast(context, trans.msg);
											if (callback != null)
												callback.onResponse(trans.code, null, trans.msg);
										}
									}
									else
									{
										LOG.toast(context, "解析失败");
									}
								}
								else
								{
									LOG.v("upload fail : 网络异常");
									LOG.toast(context, "网络异常");
									if (callback != null)
										callback.onResponse(-100, null, "网络异常");
								}
							}
						});
					}
				}
				else
				{
					if (callback != null)
						callback.onResponse(code, data, msg);
				}
			}
		});

	}
}
