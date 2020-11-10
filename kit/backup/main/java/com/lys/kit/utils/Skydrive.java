package com.lys.kit.utils;

import android.content.Context;
import android.text.TextUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lys.base.utils.CommonUtils;
import com.lys.base.utils.FsUtils;
import com.lys.base.utils.HttpUtils;
import com.lys.base.utils.JsonHelper;
import com.lys.base.utils.LOG;
import com.lys.kit.config.Config;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class Skydrive
{

	private static void checkFile(final Context context, String personId, final String cloudPath, final OnCheck callback)
	{
		JSONObject obj = new JSONObject(true);
		obj.put("personId", personId);
		obj.put("fileName", cloudPath);
//		LOG.v("上行[checkFile]-----------------");
//		LOGJson.log(obj.toString());
		HttpUtils.doHttpPost(context, Config.getConfigInfo(context).checkSkydriveFileUrl, obj.toString(), new HttpUtils.OnCallback()
		{
			@Override
			public void onResponse(String jsonStr)
			{
				boolean success = false;
				String url = null;
				String fmd5 = null;
				try
				{
					if (!TextUtils.isEmpty(jsonStr))
					{
//						LOG.v("下行[checkFile]-----------------");
//						LOGJson.log(jsonStr);
						JSONObject result = JsonHelper.getJSONObject(jsonStr);
						if (result.getInteger("code") == 200)
						{
							JSONObject data = result.getJSONObject("data");
							if (data != null)
							{
								url = data.getString("url");
								fmd5 = data.getString("fmd5");
								success = true;
							}
						}
					}
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
				if (success)
				{
					if (callback != null)
						callback.result(url, fmd5);
				}
				else
				{
					if (callback != null)
						callback.fail();
				}
			}
		});
	}

	public static void upload(final Context context, final String personId, final File file, final String cloudPath, final OnSkydrive callback)
	{
		checkFile(context, personId, cloudPath, new OnCheck()
		{
			@Override
			public void result(String url, String fmd5)
			{
				if (!TextUtils.isEmpty(url) && !TextUtils.isEmpty(fmd5))
				{
					byte[] bytes = FsUtils.readBytes(file);
					String md5 = CommonUtils.md5(bytes);
					if (md5.toLowerCase().equals(fmd5.toLowerCase()))
					{
						if (callback != null)
							callback.success(url);
					}
					else
					{
						doUpload(context, personId, file, cloudPath, callback);
					}
				}
				else
				{
					doUpload(context, personId, file, cloudPath, callback);
				}
			}

			@Override
			public void fail()
			{
				if (callback != null)
					callback.fail();
			}
		});
	}

	private static void doUpload(Context context, String personId, File file, String cloudPath, final OnSkydrive callback)
	{
		RequestBody fileBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
		MultipartBody.Builder contentBuilder = new MultipartBody.Builder().setType(MultipartBody.FORM);
		contentBuilder.addFormDataPart("personId", personId);
		contentBuilder.addFormDataPart("fileName", cloudPath);
		RequestBody requestBody = contentBuilder.addFormDataPart("file", file.getName(), fileBody).build();
		LOG.v("上传到:" + cloudPath);
		HttpUtils.doHttpPostImpl(context, Config.getConfigInfo(context).uploadSkydriveFileUrl, requestBody, new HttpUtils.OnCallback()
		{
			@Override
			public void onResponse(String jsonStr)
			{
				String url = null;
				try
				{
					if (!TextUtils.isEmpty(jsonStr))
					{
//						LOG.v("下行-----------------");
//						LOGJson.log(jsonStr);
						JSONObject result = JsonHelper.getJSONObject(jsonStr);
						if (result.getInteger("code") == 200)
						{
							JSONObject data = result.getJSONObject("data");
							if (data != null)
								url = data.getString("url");
						}
					}
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}

				if (!TextUtils.isEmpty(url))
				{
					if (callback != null)
						callback.success(url);
				}
				else
				{
					if (callback != null)
						callback.fail();
				}
			}
		});
	}

	public static void download(final Context context, final String personId, final File file, final String cloudPath, final OnSkydrive callback)
	{
		checkFile(context, personId, cloudPath, new OnCheck()
		{
			@Override
			public void result(String url, String fmd5)
			{
				if (!TextUtils.isEmpty(url) && !TextUtils.isEmpty(fmd5))
				{
					if (file.exists())
					{
						byte[] bytes = FsUtils.readBytes(file);
						String md5 = CommonUtils.md5(bytes);
						if (md5.toLowerCase().equals(fmd5.toLowerCase()))
						{
							if (callback != null)
								callback.success(url);
						}
						else
						{
							doDownload(context, file, url, callback);
						}
					}
					else
					{
						doDownload(context, file, url, callback);
					}
				}
				else
				{
					if (callback != null)
						callback.fail();
				}
			}

			@Override
			public void fail()
			{
				if (callback != null)
					callback.fail();
			}
		});
	}

	private static void doDownload(Context context, File file, final String url, final OnSkydrive callback)
	{
		LOG.v("下载:" + url);
		HttpUtils.download(context, url, file, new HttpUtils.OnDownloadListener()
		{
			@Override
			public void onWait()
			{

			}

			@Override
			public void onFail()
			{
				if (callback != null)
					callback.fail();
			}

			@Override
			public void onProgress(int alreadyDownloadSize)
			{

			}

			@Override
			public void onSuccess()
			{
				if (callback != null)
					callback.success(url);
			}
		});
	}

	public static void delete(final Context context, String personId, String cloudPath, final OnSkydrive callback)
	{
		JSONObject obj = new JSONObject(true);
		obj.put("personId", personId);
		obj.put("fileName", cloudPath);
//		LOG.v("上行[deleteFile]-----------------");
//		LOGJson.log(obj.toString());
		HttpUtils.doHttpPost(context, Config.getConfigInfo(context).deleteSkydriveFileUrl, obj.toString(), new HttpUtils.OnCallback()
		{
			@Override
			public void onResponse(String jsonStr)
			{
				boolean success = false;
				try
				{
					if (!TextUtils.isEmpty(jsonStr))
					{
//						LOG.v("下行[deleteFile]-----------------");
//						LOGJson.log(jsonStr);
						JSONObject result = JsonHelper.getJSONObject(jsonStr);
						if (result.getInteger("code") == 200)
						{
							success = true;
						}
					}
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}

				if (success)
				{
					if (callback != null)
						callback.success(null);
				}
				else
				{
					if (callback != null)
						callback.fail();
				}
			}
		});
	}

	public static void list(final Context context, String personId, String cloudPath, final OnSkydriveList callback)
	{
		JSONObject obj = new JSONObject(true);
		obj.put("personId", personId);
		obj.put("fileName", cloudPath);
//		LOG.v("上行[listFiles]-----------------");
//		LOGJson.log(obj.toString());
		HttpUtils.doHttpPost(context, Config.getConfigInfo(context).skydriveSyncDirs, obj.toString(), new HttpUtils.OnCallback()
		{
			@Override
			public void onResponse(String jsonStr)
			{
				boolean success = false;
				JSONArray cloudDirNames = null;
				JSONArray cloudFileNames = null;
				try
				{
					if (!TextUtils.isEmpty(jsonStr))
					{
//						LOG.v("下行[listFiles]-----------------");
//						LOGJson.log(jsonStr);
						JSONObject result = JsonHelper.getJSONObject(jsonStr);
						if (result.getInteger("code") == 200)
						{
							JSONObject data = result.getJSONObject("data");
							if (data != null)
							{
								cloudDirNames = data.getJSONArray("dirNames");
								cloudFileNames = data.getJSONArray("fileNames");
								success = true;
							}
						}
					}
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}

				if (success)
				{
					if (callback != null)
						callback.success(cloudDirNames, cloudFileNames);
				}
				else
				{
					if (callback != null)
						callback.fail();
				}
			}
		});
	}

	private interface OnCheck
	{
		void result(String url, String fmd5);

		void fail();
	}

	public interface OnSkydrive
	{
		void success(String url);

		void fail();
	}

	public interface OnSkydriveList
	{
		void success(JSONArray cloudDirNames, JSONArray cloudFileNames);

		void fail();
	}

}
