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
import com.lys.protobuf.ProtocolApp.Request_SetAppInfo;

// ---------------------- 设置APK信息 --------------------------
public class SRequest_SetAppInfo extends SPTData<Request_SetAppInfo>
{
	private static final SRequest_SetAppInfo DefaultInstance = new SRequest_SetAppInfo();

	public SApp app = null;

	public static SRequest_SetAppInfo create(SApp app)
	{
		SRequest_SetAppInfo obj = new SRequest_SetAppInfo();
		obj.app = app;
		return obj;
	}

	public SRequest_SetAppInfo clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SRequest_SetAppInfo _other_)
	{
		this.app = _other_.app;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("app"))
			app = SApp.load(_json_.getJSONObject("app"));
	}

	public static SRequest_SetAppInfo load(String str)
	{
		try
		{
			SRequest_SetAppInfo obj = new SRequest_SetAppInfo();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_SetAppInfo load(JSONObject json)
	{
		try
		{
			SRequest_SetAppInfo obj = new SRequest_SetAppInfo();
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

	public static List<SRequest_SetAppInfo> loadList(JSONArray ja)
	{
		try
		{
			List<SRequest_SetAppInfo> list = new ArrayList<SRequest_SetAppInfo>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SRequest_SetAppInfo item = SRequest_SetAppInfo.load(jo);
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

	public static JSONArray saveList(List<SRequest_SetAppInfo> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SRequest_SetAppInfo item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Request_SetAppInfo _proto_)
	{
		if (_proto_.hasApp())
			app = SApp.load(_proto_.getApp());
	}

	public static SRequest_SetAppInfo load(byte[] bytes)
	{
		try
		{
			SRequest_SetAppInfo obj = new SRequest_SetAppInfo();
			obj.parse(Request_SetAppInfo.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_SetAppInfo load(Request_SetAppInfo proto)
	{
		try
		{
			SRequest_SetAppInfo obj = new SRequest_SetAppInfo();
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
	public Request_SetAppInfo saveToProto()
	{
		Request_SetAppInfo.Builder _builder_ = Request_SetAppInfo.newBuilder();
		if (app != null)
			_builder_.setApp(app.saveToProto());
		return _builder_.build();
	}
}
