package com.lys.protobuf;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.lys.base.utils.AppDataTool;
import com.lys.base.utils.JsonHelper;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.google.protobuf.ByteString;

import com.lys.base.utils.SPTData;
import com.lys.protobuf.ProtocolApp.Response_GetAppInfo;

public class SResponse_GetAppInfo extends SPTData<Response_GetAppInfo>
{
	private static final SResponse_GetAppInfo DefaultInstance = new SResponse_GetAppInfo();

	public SApp app = null;

	public static SResponse_GetAppInfo create(SApp app)
	{
		SResponse_GetAppInfo obj = new SResponse_GetAppInfo();
		obj.app = app;
		return obj;
	}

	public SResponse_GetAppInfo clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SResponse_GetAppInfo _other_)
	{
		this.app = _other_.app;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("app"))
			app = SApp.load(_json_.getJSONObject("app"));
	}

	public static SResponse_GetAppInfo load(String str)
	{
		try
		{
			SResponse_GetAppInfo obj = new SResponse_GetAppInfo();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_GetAppInfo load(JSONObject json)
	{
		try
		{
			SResponse_GetAppInfo obj = new SResponse_GetAppInfo();
			obj.parse(json);
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public JSONObject saveToJson()
	{
		try
		{
			JSONObject _json_ = new JSONObject(true);
			if (app != null)
				_json_.put("app", app.saveToJson());
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SResponse_GetAppInfo> loadList(JSONArray ja)
	{
		try
		{
			List<SResponse_GetAppInfo> list = new ArrayList<SResponse_GetAppInfo>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SResponse_GetAppInfo item = SResponse_GetAppInfo.load(jo);
				if (item == null)
					return null;
				list.add(item);
			}
			return list;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static JSONArray saveList(List<SResponse_GetAppInfo> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SResponse_GetAppInfo item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Response_GetAppInfo _proto_)
	{
		if (_proto_.hasApp())
			app = SApp.load(_proto_.getApp());
	}

	public static SResponse_GetAppInfo load(byte[] bytes)
	{
		try
		{
			SResponse_GetAppInfo obj = new SResponse_GetAppInfo();
			obj.parse(Response_GetAppInfo.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_GetAppInfo load(Response_GetAppInfo proto)
	{
		try
		{
			SResponse_GetAppInfo obj = new SResponse_GetAppInfo();
			obj.parse(proto);
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Response_GetAppInfo saveToProto()
	{
		Response_GetAppInfo.Builder _builder_ = Response_GetAppInfo.newBuilder();
		if (app != null)
			_builder_.setApp(app.saveToProto());
		return _builder_.build();
	}
}
