package com.lys.kit.utils;

import android.content.Context;
import android.text.TextUtils;

import com.lys.base.utils.HttpUtils;
import com.lys.base.utils.LOG;
import com.lys.base.utils.LOGJson;
import com.lys.kit.AppKit;
import com.lys.kit.config.Config;
import com.lys.kit.dialog.DialogAlert;
import com.lys.protobuf.SHandleId;
import com.lys.protobuf.SProtocol;

public class Protocol
{
//	private static final String API_URL = "http://cloud.k12-eco.com:8070/api";
//	private static final String API_URL = "http://192.168.0.113:8070/api";
//	private static final String API_URL = "http://192.168.0.107:8080/LysServer/ServletBook";

//	private static String API_URL(Context context)
//	{
//		return Config.readServerInfo(context);
//	}

	public interface OnCallback
	{
		void onResponse(int code, String data, String msg);
	}

//	public static void doPost(final Context context, final int handleId, String data, final OnCallback callback)
//	{
//		String api_url = API_URL(context);
//		if (TextUtils.isEmpty(api_url))
//		{
//			DialogAlert.show(context, null, "没有配置服务器！", null, "我知道了");
//		}
//		else
//		{
//			doPost(context, api_url, handleId, data, callback);
//		}
//	}

	public static void doPost(final Context context, String api, final int handleId, String data, final OnCallback callback)
	{
		if (TextUtils.isEmpty(api))
		{
			DialogAlert.show(context, null, "接口没有配置！", null, "我知道了");
		}
		else
		{
			SProtocol trans = new SProtocol();
			trans.handleId = handleId;
			trans.data = data;
//			trans.token = Config.getToken(context);
//			trans.deviceId = AppKit.OnlyId;
			trans.userId = AppKit.userId();
			trans.userName = AppKit.name();
			LOG.v("上行--------" + SHandleId.name(handleId) + "---------" + handleId + "---------" + data.length() + "---------");
			if (Config.isDebug())
				LOGJson.log(trans.saveToStr());
			HttpUtils.doHttpPost(context, api, trans.saveToStr(), new HttpUtils.OnCallback()
			{
				@Override
				public void onResponse(String jsonStr)
				{
					if (!TextUtils.isEmpty(jsonStr))
					{
						SProtocol trans = null;
						try
						{
							LOG.v("下行--------" + SHandleId.name(handleId) + "---------" + handleId + "---------" + jsonStr.length() + "---------");
							if (Config.isDebug() && handleId != SHandleId.GetKnowledges)
								LOGJson.log(jsonStr);
							trans = SProtocol.load(jsonStr);
						}
						catch (Exception e)
						{
							e.printStackTrace();
							LOG.toast(context, handleId + ":" + "解析异常");
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
								LOG.toast(context, "" + trans.msg);
								if (callback != null)
									callback.onResponse(trans.code, null, trans.msg);
							}
						}
						else
						{
							LOG.toast(context, handleId + ":" + "解析失败");
						}
					}
					else
					{
						LOG.toast(context, handleId + ":" + "网络异常");
						if (callback != null)
							callback.onResponse(-100, null, "网络异常");
					}
				}
			});
		}
	}

	public static SProtocol doPost(String api, int handleId, String data)
	{
		if (!TextUtils.isEmpty(api))
		{
			SProtocol transSend = new SProtocol();
			transSend.handleId = handleId;
			transSend.data = data;
			transSend.userId = AppKit.userId();
			transSend.userName = AppKit.name();
			LOG.v("上行--------" + SHandleId.name(handleId) + "---------" + handleId + "---------");
			if (Config.isDebug())
				LOGJson.log(transSend.saveToStr());
			String jsonStr = HttpUtils.doHttpPost(api, transSend.saveToStr());
			if (!TextUtils.isEmpty(jsonStr))
			{
				SProtocol transRcv = null;
				try
				{
					LOG.v("下行--------" + SHandleId.name(handleId) + "---------" + handleId + "---------");
					if (Config.isDebug() && handleId != SHandleId.GetKnowledges)
						LOGJson.log(jsonStr);
					transRcv = SProtocol.load(jsonStr);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
				return transRcv;
			}
		}
		return null;
	}

//	public static void doSearch(final Context context, SRequest_GetResources filter, int start, final OnCallback callback)
//	{
//		SResponse_GetConfig configInfo = Config.getAppConfigInfo();
//		if (configInfo != null && !TextUtils.isEmpty(configInfo.search))
//		{
//			doSearch(context, configInfo.search, filter, start, callback);
//		}
//		else
//		{
//			DialogAlert.show(context, null, "没有搜索接口！", null, "我知道了");
//		}
//	}
//
//	private static void doSearch(final Context context, String api, SRequest_GetResources filter, int start, final OnCallback callback)
//	{
//		if (TextUtils.isEmpty(api))
//		{
//			DialogAlert.show(context, null, "没有搜索接口！", null, "我知道了");
//		}
//		else
//		{
//			SRequest_GetResources request = new SRequest_GetResources();
//			request.content = filter.content;
//			request.recommend = 0;
//			request.share = 0;
//			request.subject = filter.subject;
//			request.width = 960;
//			request.searchSort = false;
//			request.start = start;
//			request.rows = filter.rows;
//			request.knowledgeCodes = filter.knowledgeCodes;
//			request.style = filter.style;
//			request.diff = filter.diff;
//			LOG.v("搜索：" + request.saveToStr());
//			HttpUtils.doHttpPost(context, api, request.saveToStr(), new HttpUtils.OnCallback()
//			{
//				@Override
//				public void onResponse(String jsonStr)
//				{
//					if (!TextUtils.isEmpty(jsonStr))
//					{
//						SProtocol trans = null;
//						try
//						{
//							LOG.v("搜索返回：");
//							LOGJson.log(jsonStr);
//							trans = SProtocol.load(jsonStr);
//						}
//						catch (Exception e)
//						{
//							e.printStackTrace();
//							LOG.toast(context, "解析异常");
//						}
//						if (trans != null)
//						{
//							if (trans.code == 200)
//							{
//								if (callback != null)
//									callback.onResponse(trans.code, trans.data, trans.msg);
//							}
//							else
//							{
//								LOG.toast(context, trans.msg);
//								if (callback != null)
//									callback.onResponse(trans.code, null, trans.msg);
//							}
//						}
//						else
//						{
//							LOG.toast(context, "解析失败");
//						}
//					}
//					else
//					{
//						LOG.toast(context, "网络异常");
//						if (callback != null)
//							callback.onResponse(-100, null, "网络异常");
//					}
//				}
//			});
//		}
//	}

}
