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
import com.lys.protobuf.ProtocolApp.Request_GetAppInfo;

// ---------------------- 获取应用 --------------------------
public class SRequest_GetAppInfo extends SPTData<Request_GetAppInfo>
{
	private static final SRequest_GetAppInfo DefaultInstance = new SRequest_GetAppInfo();

	public String pkgName = null;
	public String channel = null;

	public static SRequest_GetAppInfo create(String pkgName, String channel)
	{
		SRequest_GetAppInfo obj = new SRequest_GetAppInfo();
		obj.pkgName = pkgName;
		obj.channel = channel;
		return obj;
	}

	public SRequest_GetAppInfo clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SRequest_GetAppInfo _other_)
	{
		this.pkgName = _other_.pkgName;
		this.channel = _other_.channel;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("pkgName"))
			pkgName = _json_.getString("pkgName");
		if (_json_.containsKey("channel"))
			channel = _json_.getString("channel");
	}

	public static SRequest_GetAppInfo load(String str)
	{
		try
		{
			SRequest_GetAppInfo obj = new SRequest_GetAppInfo();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_GetAppInfo load(JSONObject json)
	{
		try
		{
			SRequest_GetAppInfo obj = new SRequest_GetAppInfo();
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
			if (pkgName != null)
				_json_.put("pkgName", pkgName);
			if (channel != null)
				_json_.put("channel", channel);
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SRequest_GetAppInfo> loadList(JSONArray ja)
	{
		try
		{
			List<SRequest_GetAppInfo> list = new ArrayList<SRequest_GetAppInfo>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SRequest_GetAppInfo item = SRequest_GetAppInfo.load(jo);
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

	public static JSONArray saveList(List<SRequest_GetAppInfo> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SRequest_GetAppInfo item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Request_GetAppInfo _proto_)
	{
		if (_proto_.hasPkgName())
			pkgName = _proto_.getPkgName();
		if (_proto_.hasChannel())
			channel = _proto_.getChannel();
	}

	public static SRequest_GetAppInfo load(byte[] bytes)
	{
		try
		{
			SRequest_GetAppInfo obj = new SRequest_GetAppInfo();
			obj.parse(Request_GetAppInfo.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_GetAppInfo load(Request_GetAppInfo proto)
	{
		try
		{
			SRequest_GetAppInfo obj = new SRequest_GetAppInfo();
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
	public Request_GetAppInfo saveToProto()
	{
		Request_GetAppInfo.Builder _builder_ = Request_GetAppInfo.newBuilder();
		if (pkgName != null && !pkgName.equals(Request_GetAppInfo.getDefaultInstance().getPkgName()))
			_builder_.setPkgName(pkgName);
		if (channel != null && !channel.equals(Request_GetAppInfo.getDefaultInstance().getChannel()))
			_builder_.setChannel(channel);
		return _builder_.build();
	}
}
