package com.lys.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSStsTokenCredentialProvider;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.lys.App;
import com.lys.base.utils.CommonUtils;
import com.lys.base.utils.LOG;
import com.lys.kit.module.OssHelper;
import com.lys.kit.utils.Protocol;
import com.lys.protobuf.SHandleId;
import com.lys.protobuf.SRequest_GetOssToken;
import com.lys.protobuf.SResponse_GetOssToken;

import java.io.File;

public class COssHelper extends OssHelper
{
	private Context context;

	public COssHelper(Context context)
	{
		this.context = context.getApplicationContext();
	}

	@Override
	protected void destroy()
	{
	}

	private static final String endpoint = "http://oss-cn-huhehaote.aliyuncs.com";

	private static final String ERROR = "error";

	private static String getHost(String bucketName)
	{
//		if (bucketName.equals(ZjykFile))
//			return "http://file.k12-eco.com/";
//		else
		return "http://" + bucketName + ".oss-cn-huhehaote.aliyuncs.com/";
	}

	private interface OnCreateOSSListener
	{
		void onSuccess(OSS oss);

		void onFail();
	}

	private static void createOSS(final OnCreateOSSListener listener)
	{
		SRequest_GetOssToken request = new SRequest_GetOssToken();
		if (TextUtils.isEmpty(App.userId()))
			request.userId = "guest";
		else
			request.userId = App.userId();
		Protocol.doPost(App.getContext(), App.getApi(), SHandleId.GetOssToken, request.saveToStr(), new Protocol.OnCallback()
		{
			@Override
			public void onResponse(int code, String data, String msg)
			{
				if (code == 200)
				{
					SResponse_GetOssToken response = SResponse_GetOssToken.load(data);

					OSSCredentialProvider credentialProvider = new OSSStsTokenCredentialProvider(response.accessKeyId, response.accessKeySecret, response.securityToken);

					ClientConfiguration conf = new ClientConfiguration();
					conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒。
					conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒。
					conf.setMaxConcurrentRequest(5); // 最大并发请求书，默认5个。
					conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次。

					OSS oss = new OSSClient(App.getContext(), endpoint, credentialProvider, conf);

					if (listener != null)
						listener.onSuccess(oss);
				}
				else
				{
					if (listener != null)
						listener.onFail();
				}
			}
		});
	}

	private static void doUploadWithProgressImpl(OSS oss, final String bucketName, File file, final String path, final OnProgressListener listener)
	{
		final Handler handler = new Handler(Looper.getMainLooper())
		{
			public void handleMessage(Message message)
			{
				if (message.what == 0)
				{
					if (listener != null)
						listener.onFail();
				}
				else if (message.what == 1)
				{
					String param = (String) message.obj;
					String[] params = param.split("#");
					if (listener != null)
						listener.onProgress(Integer.valueOf(params[0]), Integer.valueOf(params[1]));
				}
				else if (message.what == 2)
				{
					if (listener != null)
						listener.onSuccess((String) message.obj);
				}
			}
		};
		PutObjectRequest put = new PutObjectRequest(bucketName, path, file.toString());
		put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>()
		{
			@Override
			public void onProgress(PutObjectRequest request, long currentSize, long totalSize)
			{
				handler.sendMessage(handler.obtainMessage(1, currentSize + "#" + totalSize));
			}
		});
		OSSAsyncTask task = oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>()
		{
			@Override
			public void onSuccess(PutObjectRequest request, PutObjectResult result)
			{
//				LOG.v(result.getETag());
//				LOG.v(result.getRequestId());
				handler.sendMessage(handler.obtainMessage(2, getHost(bucketName) + path));
			}

			@Override
			public void onFailure(PutObjectRequest request, ClientException clientException, ServiceException serviceException)
			{
				if (serviceException != null)
				{
					LOG.v(serviceException.getErrorCode());
					LOG.v(serviceException.getRequestId());
					LOG.v(serviceException.getHostId());
					LOG.v(serviceException.getRawMessage());
				}
				handler.sendMessage(handler.obtainMessage(0));
			}
		});
	}

	@Override
	public void doUploadWithProgress(final String bucketName, final File file, final String path, final OnProgressListener listener)
	{
		createOSS(new OnCreateOSSListener()
		{
			@Override
			public void onSuccess(OSS oss)
			{
				doUploadWithProgressImpl(oss, bucketName, file, path, listener);
			}

			@Override
			public void onFail()
			{
				if (listener != null)
					listener.onFail();
			}
		});
	}

	@Override
	public void doUploadMd5FileWithProgress(final String bucketName, final File file, final String dir, final OnProgressListener listener)
	{
		int pos = file.getName().lastIndexOf('.');
		String suffix = "";
		if (pos >= 0)
			suffix = file.getName().substring(pos);
		String md5 = CommonUtils.md5(file);
		final String path = String.format("%s%s/%s%s", dir, md5.substring(md5.length() - 2), md5, suffix);
		createOSS(new OnCreateOSSListener()
		{
			@Override
			public void onSuccess(OSS oss)
			{
				String existResult = fileExist(oss, bucketName, path);
				if (ERROR.equals(existResult))
				{
					if (listener != null)
						listener.onFail();
				}
				else if (TextUtils.isEmpty(existResult))
				{
					doUploadWithProgressImpl(oss, bucketName, file, path, listener);
				}
				else
				{
					if (listener != null)
						listener.onSuccess(existResult);
				}
			}

			@Override
			public void onFail()
			{
				if (listener != null)
					listener.onFail();
			}
		});
	}

	private static String fileExist(OSS oss, String bucketName, String path)
	{
		try
		{
			boolean exist = oss.doesObjectExist(bucketName, path);
			if (exist)
				return getHost(bucketName) + path;
			else
				return null;
		}
		catch (Exception e)
		{
			return ERROR;
		}
	}
}
