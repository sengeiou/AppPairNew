package com.lys.kit.utils;

public class Upload
{

//	private static void checkFile(final Context context, final File file, final OnCheck callback)
//	{
//		byte[] bytes = FsUtils.readBytes(file);
//		String md5 = CommonUtils.md5(bytes);
//
//		LOG.v(String.format("checkFile：fileName=%s, md5=%s", file.getName(), md5));
//
//		JSONObject obj = new JSONObject(true);
//		obj.put("fmd5", md5);
//		LOG.v("上行[checkFile]-----------------");
//		LOGJson.log(obj.toString());
//		HttpUtils.doHttpPost(context, Config.getConfigInfo(context).checkFileUrl, obj.toString(), new HttpUtils.OnCallback()
//		{
//			@Override
//			public void onResponse(String jsonStr)
//			{
//				boolean success = false;
//				String url = null;
//				try
//				{
//					if (!TextUtils.isEmpty(jsonStr))
//					{
//						LOG.v("下行[checkFile]-----------------");
//						LOGJson.log(jsonStr);
//						JSONObject result = JsonHelper.getJSONObject(jsonStr);
//						if (result.getInteger("code") == 200)
//						{
//							JSONObject data = result.getJSONObject("data");
//							if (data != null)
//							{
//								url = data.getString("url");
//								success = true;
//							}
//						}
//					}
//				}
//				catch (Exception e)
//				{
//					e.printStackTrace();
//				}
//				if (success)
//				{
//					if (callback != null)
//						callback.result(url);
//				}
//				else
//				{
//					if (callback != null)
//						callback.fail();
//				}
//			}
//		});
//	}
//
//	public static void upload(final Context context, final File file, final OnUpload callback)
//	{
//		if (true)
//		{
//			checkFile(context, file, new OnCheck()
//			{
//				@Override
//				public void result(String url)
//				{
//					if (!TextUtils.isEmpty(url))
//					{
//						if (callback != null)
//							callback.success(url);
//					}
//					else
//					{
//						doUpload(context, file, callback);
//					}
//				}
//
//				@Override
//				public void fail()
//				{
//					if (callback != null)
//						callback.fail();
//				}
//			});
//		}
//		else
//		{
//			final Handler handler = new Handler()
//			{
//				@Override
//				public void handleMessage(Message msg)
//				{
//					String url = (String) msg.obj;
//					if (!TextUtils.isEmpty(url))
//					{
//						if (callback != null)
//							callback.success(url);
//					}
//					else
//					{
//						if (callback != null)
//							callback.fail();
//					}
//				}
//			};
//			new Thread(new Runnable()
//			{
//				@Override
//				public void run()
//				{
//					String url = uploadToPath(context, file);
//					Message message = handler.obtainMessage(0, url);
//					handler.sendMessage(message);
//				}
//			}).start();
//		}
//	}
//
//	private static String uploadToPath(Context context, File file)
//	{
//		String uploadDir = String.format("%s/upload", FsUtils.SD_CARD);
//		new File(uploadDir).mkdirs();
//
//		String filepath = uploadDir + "/" + CommonUtils.md5(file);
//
//		FsUtils.writeBytes(new File(filepath), FsUtils.readBytes(file));
//
//		return filepath;
//	}
//
//	private static void doUpload(Context context, File file, final OnUpload callback)
//	{
//		final int chunksize = Config.getConfigInfo(context).chunkSize;
//
//		byte[] bytes = FsUtils.readBytes(file);
//		long size = bytes.length;
//		String md5 = CommonUtils.md5(bytes);
//
//		long chunks = size / chunksize;
//		if (size % chunksize > 0)
//			chunks++;
//
//		LOG.v(String.format("准备上传：fileName=%s, md5=%s, size=%d, chunks=%d", file.getName(), md5, size, chunks));
//
//		doUpload(context, file, chunksize, bytes, size, md5, chunks, 0, callback);
//	}
//
//	private static void doUpload(final Context context, final File file, final int chunksize, final byte[] bytes, final long size, final String md5, final long chunks, final int chunk, final OnUpload callback)
//	{
//		if (chunk < chunks)
//		{
//			final File chunkfile;
//			if (chunks > 1)
//			{
//				chunkfile = new File(file.getAbsoluteFile() + "." + chunk + ".chunk");
//				int off = chunk * chunksize;
//				int len = (int) Math.min(size - off, chunksize);
//				FsUtils.writeBytes(chunkfile, bytes, off, len);
//			}
//			else
//			{
//				chunkfile = file;
//			}
//
//			RequestBody fileBody = RequestBody.create(MediaType.parse("multipart/form-data"), chunkfile);
//			MultipartBody.Builder contentBuilder = new MultipartBody.Builder().setType(MultipartBody.FORM);
//			contentBuilder.addFormDataPart("personId", Config.getPersonId(context));
//			contentBuilder.addFormDataPart("chunks", String.valueOf(chunks));
//			contentBuilder.addFormDataPart("chunk", String.valueOf(chunk));
//			contentBuilder.addFormDataPart("fileName", file.getName());
//			contentBuilder.addFormDataPart("fmd5", md5);
//			contentBuilder.addFormDataPart("size", String.valueOf(size));
//			RequestBody requestBody = contentBuilder.addFormDataPart("file", file.getName(), fileBody).build();
//
//			LOG.v("上行-----------------:" + chunk);
//			HttpUtils.doHttpPostImpl(context, Config.getConfigInfo(context).uploadFileUrl, requestBody, new HttpUtils.OnCallback()
//			{
//				@Override
//				public void onResponse(String jsonStr)
//				{
//					if (chunks > 1)
//					{
//						FsUtils.delete(chunkfile);
//					}
//
//					boolean success = false;
//					String url = null;
//					try
//					{
//						if (!TextUtils.isEmpty(jsonStr))
//						{
//							LOG.v("下行-----------------");
//							LOGJson.log(jsonStr);
//							JSONObject result = JsonHelper.getJSONObject(jsonStr);
//							if (result.getInteger("code") == 200)
//							{
//								success = true;
//								if (chunk == chunks - 1)
//								{
//									JSONObject data = result.getJSONObject("data");
//									if (data != null)
//										url = data.getString("url");
//								}
//							}
//						}
//					}
//					catch (Exception e)
//					{
//						e.printStackTrace();
//					}
//
//					if (success)
//					{
//						if (chunk == chunks - 1)
//						{
//							if (!TextUtils.isEmpty(url))
//							{
//								if (callback != null)
//									callback.success(url);
//							}
//							else
//							{
//								if (callback != null)
//									callback.fail();
//							}
//						}
//						else
//						{
//							doUpload(context, file, chunksize, bytes, size, md5, chunks, chunk + 1, callback);
//						}
//					}
//					else
//					{
//						if (callback != null)
//							callback.fail();
//					}
//				}
//			});
//		}
//	}
//
//	private interface OnCheck
//	{
//		void result(String url);
//
//		void fail();
//	}
//
//	public interface OnUpload
//	{
//		void success(String url);
//
//		void fail();
//	}

}
