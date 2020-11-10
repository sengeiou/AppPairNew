package com.lys.base.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpUtils
{
	private static OkHttpClient httpClient = null;

	// 将OkHttpClient对象设置为单例，避免不共用线程池导致的OutOfMemoryError
	private static OkHttpClient getHttpClient()
	{
		synchronized (HttpUtils.class)
		{
			if (httpClient == null)
			{
				OkHttpClient.Builder httpBuilder = new OkHttpClient.Builder();
				httpBuilder.connectTimeout(10, TimeUnit.SECONDS);
				httpBuilder.writeTimeout(60, TimeUnit.SECONDS);
				httpBuilder.readTimeout(60, TimeUnit.SECONDS);
//				httpBuilder.cookieJar(new CookieJar()
//				{
//					@Override
//					public void saveFromResponse(HttpUrl url, List<Cookie> cookies)
//					{
//
//					}
//
//					@Override
//					public List<Cookie> loadForRequest(HttpUrl url)
//					{
//						CookieManager cookieManager = CookieManager.getInstance();
//						String strCookies = cookieManager.getCookie(url.host());
//						LOG.v(strCookies);
//						List<Cookie> cookies = new ArrayList<>();
//						if (!TextUtils.isEmpty(strCookies))
//						{
//							String arrayCookies[] = strCookies.split(";");
//							if (arrayCookies != null && arrayCookies.length > 0)
//							{
//								for (String cookieStr : arrayCookies)
//								{
//									String pairs[] = cookieStr.split("=");
//									if (pairs.length == 2)
//									{
//										Cookie cookie = new Cookie.Builder().name(pairs[0].trim()).value(pairs[1].trim()).domain(url.host()).build();
//										cookies.add(cookie);
//									}
//									else
//									{
//										LOG.e("error pairs.length  : "+pairs.length );
//									}
//								}
//							}
//						}
//						return cookies;
//					}
//				});
				httpClient = httpBuilder.build();
			}
			return httpClient;
		}
	}

	public interface OnCallback
	{
		void onResponse(String jsonStr);
	}

	public static void doHttpGet(Context context, String httpUrl, final OnCallback callback)
	{
		final Handler handler = new Handler()
		{
			public void handleMessage(Message message)
			{
				if (callback != null)
					callback.onResponse((String) message.obj);
			}
		};

		try
		{
			OkHttpClient client = getHttpClient();
			Request.Builder builder = new Request.Builder();
			builder.url(httpUrl);
			Request request = builder.build();
			client.newCall(request).enqueue(new Callback()
			{
				@Override
				public void onFailure(Call call, IOException e)
				{
					Message message = handler.obtainMessage(0, null);
					handler.sendMessage(message);
				}

				@Override
				public void onResponse(Call call, Response response) throws IOException
				{
					if (response != null && response.isSuccessful())
					{
						Message message = handler.obtainMessage(0, response.body().string());
						handler.sendMessage(message);
					}
					else
					{
						Message message = handler.obtainMessage(0, null);
						handler.sendMessage(message);
					}
				}
			});
		}
		catch (Exception e)
		{
			e.printStackTrace();
			Message message = handler.obtainMessage(0, null);
			handler.sendMessage(message);
		}
	}

	public static void doHttpPostImpl(Context context, String httpUrl, RequestBody requestBody, final OnCallback callback)
	{
		final Handler handler = new Handler()
		{
			public void handleMessage(Message message)
			{
				if (callback != null)
					callback.onResponse((String) message.obj);
			}
		};

		try
		{
			OkHttpClient client = getHttpClient();
			Request.Builder builder = new Request.Builder();
			builder.url(httpUrl);
			if (requestBody != null)
				builder.post(requestBody);
			Request request = builder.build();
			client.newCall(request).enqueue(new Callback()
			{
				@Override
				public void onFailure(Call call, IOException e)
				{
					Message message = handler.obtainMessage(0, null);
					handler.sendMessage(message);
				}

				@Override
				public void onResponse(Call call, Response response) throws IOException
				{
					if (response != null && response.isSuccessful())
					{
						Message message = handler.obtainMessage(0, response.body().string());
						handler.sendMessage(message);
					}
					else
					{
						Message message = handler.obtainMessage(0, null);
						handler.sendMessage(message);
					}
				}
			});
		}
		catch (Exception e)
		{
			e.printStackTrace();
			Message message = handler.obtainMessage(0, null);
			handler.sendMessage(message);
		}
	}

	public static void doHttpPost(Context context, String httpUrl, String str, final OnCallback callback)
	{
		doHttpPostImpl(context, httpUrl, RequestBody.create(MediaType.parse("application/json;charset=utf-8"), str), callback);
	}

	public static void doHttpPost(Context context, String httpUrl, Map<String, String> map, final OnCallback callback)
	{
		if (map != null && map.size() > 0)
		{
			FormBody.Builder builder = new FormBody.Builder();
			for (Map.Entry<String, String> entry : map.entrySet())
			{
				String key = entry.getKey();
				String value = entry.getValue();
				if (value == null)
					value = "";
				builder.add(key, value);
			}
			doHttpPostImpl(context, httpUrl, builder.build(), callback);
		}
		else
		{
			doHttpPostImpl(context, httpUrl, null, callback);
		}
	}

	public static String doHttpPostImpl(String httpUrl, RequestBody requestBody)
	{
		String str = null;
		Response response = null;
		try
		{
			OkHttpClient client = getHttpClient();
			Request.Builder builder = new Request.Builder();
			builder.url(httpUrl);
			if (requestBody != null)
				builder.post(requestBody);
			Request request = builder.build();
			response = client.newCall(request).execute();
			if (response != null && response.isSuccessful())
			{
				str = response.body().string();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if (response != null)
					response.close();
			}
			catch (Exception e2)
			{
				e2.printStackTrace();
			}
		}
		return str;
	}

	public static String doHttpPost(String httpUrl, String str)
	{
		return doHttpPostImpl(httpUrl, RequestBody.create(MediaType.parse("application/json;charset=utf-8"), str));
	}

	//----------------------------------------------------

	private static final int maxDownloadCount = 2;

	public static class DownloadInfo
	{
		private String httpUrl;
		private File file;
		private OnDownloadListener listener;

		private DownloadInfo(String httpUrl, File file, OnDownloadListener listener)
		{
			this.httpUrl = httpUrl;
			this.file = file;
			this.listener = listener;
		}
	}

	private static final ConcurrentLinkedQueue<DownloadInfo> mDownloadWaitQueue = new ConcurrentLinkedQueue<>();
	private static final List<DownloadInfo> mDownloadRunningList = new ArrayList<>();

	private static void downloadOther(Context context)
	{
		DownloadInfo info = mDownloadWaitQueue.poll();
		if (info != null)
		{
			downloadImpl(context, info);
		}
	}

	private static void downloadImpl(final Context context, final DownloadInfo info)
	{
		mDownloadRunningList.add(info);

		if (info.listener != null)
			info.listener.onProgress(0);

		final Handler handler = new Handler(Looper.getMainLooper())
		{
			public void handleMessage(Message message)
			{
				if (message.what == 0)
				{
					info.file.delete();
					mDownloadRunningList.remove(info);
					downloadOther(context);
					if (info.listener != null)
						info.listener.onFail();
				}
				else if (message.what == 1)
				{
					if (info.listener != null)
						info.listener.onProgress((int) message.obj);
				}
				else if (message.what == 2)
				{
					mDownloadRunningList.remove(info);
					downloadOther(context);
					if (info.listener != null)
						info.listener.onSuccess();
				}
			}
		};

		try
		{
			OkHttpClient client = getHttpClient();
			Request.Builder builder = new Request.Builder();
			builder.url(info.httpUrl);
			Request request = builder.build();
			client.newCall(request).enqueue(new Callback()
			{
				@Override
				public void onFailure(Call call, IOException e)
				{
					handler.sendMessage(handler.obtainMessage(0));
				}

				@Override
				public void onResponse(Call call, Response response) throws IOException
				{
					if (response != null && response.isSuccessful())
					{
						InputStream is = null;
						FileOutputStream fos = null;
						try
						{
							int alreadyDownloadSize = 0;
							is = response.body().byteStream();
							FsUtils.createFolder(info.file.getParentFile());
							fos = new FileOutputStream(info.file);
							byte[] buffer = new byte[1024];
							int hasRead = 0;
							while ((hasRead = is.read(buffer)) > 0)
							{
								fos.write(buffer, 0, hasRead);
								alreadyDownloadSize += hasRead;
								handler.sendMessage(handler.obtainMessage(1, alreadyDownloadSize));
							}
							handler.sendMessage(handler.obtainMessage(2));
						}
						catch (Exception e)
						{
							e.printStackTrace();
							handler.sendMessage(handler.obtainMessage(0));
						}
						finally
						{
							try
							{
								if (fos != null)
									fos.close();
								if (is != null)
									is.close();
							}
							catch (Exception e)
							{
								e.printStackTrace();
							}
						}
					}
					else
					{
						handler.sendMessage(handler.obtainMessage(0));
					}
				}
			});
		}
		catch (Exception e)
		{
			e.printStackTrace();
			handler.sendMessage(handler.obtainMessage(0));
		}
	}

	public static void download(Context context, String httpUrl, final File file, final OnDownloadListener listener)
	{
		DownloadInfo info = new DownloadInfo(httpUrl, file, listener);
		if (mDownloadRunningList.size() >= maxDownloadCount)
		{
			mDownloadWaitQueue.offer(info);
			if (listener != null)
				listener.onWait();
		}
		else
		{
			downloadImpl(context, info);
		}
	}

	public static DownloadInfo getWaitDownloadInfo(File file)
	{
		for (DownloadInfo info : mDownloadWaitQueue)
		{
			if (info.file.getAbsolutePath().equals(file.getAbsolutePath()))
			{
				return info;
			}
		}
		return null;
	}

	public static DownloadInfo getRunningDownloadInfo(File file)
	{
		for (DownloadInfo info : mDownloadRunningList)
		{
			if (info.file.getAbsolutePath().equals(file.getAbsolutePath()))
			{
				return info;
			}
		}
		return null;
	}

	public static void downloadSetListener(DownloadInfo info, OnDownloadListener listener)
	{
		info.listener = listener;
	}

	public interface OnDownloadListener
	{
		void onWait();

		void onFail();

		void onProgress(int alreadyDownloadSize);

		void onSuccess();
	}

	//------------------------------------------

	public static boolean doDownload(Context context, String httpUrl, File file)
	{
		boolean success = false;
		LOG.v("download : " + httpUrl);
		file.delete();
		File tmpFile = new File(file.getAbsolutePath() + ".dld");
		tmpFile.delete();
		Response response = null;
		InputStream is = null;
		FileOutputStream fos = null;
		try
		{
			OkHttpClient client = getHttpClient();
			Request.Builder builder = new Request.Builder();
			builder.url(httpUrl);
			Request request = builder.build();
			response = client.newCall(request).execute();
			if (response != null && response.isSuccessful())
			{
				is = response.body().byteStream();
				fos = new FileOutputStream(tmpFile);
				byte[] buffer = new byte[1024];
				int hasRead = 0;
				while ((hasRead = is.read(buffer)) > 0)
				{
					fos.write(buffer, 0, hasRead);
				}
				success = true;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if (fos != null)
					fos.close();
				if (is != null)
					is.close();
				if (response != null)
					response.close();
			}
			catch (Exception e2)
			{
				e2.printStackTrace();
			}
		}
		if (success)
			tmpFile.renameTo(file);
		else
			tmpFile.delete();
		LOG.v("success : " + success);
		return success;
	}

}
