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
import com.lys.protobuf.ProtocolPair2.Request_GetOssToken;

// ---------------------- 获取OSS TOKEN --------------------------
public class SRequest_GetOssToken extends SPTData<Request_GetOssToken>
{
	private static final SRequest_GetOssToken DefaultInstance = new SRequest_GetOssToken();

	public String userId = null;

	public static SRequest_GetOssToken create(String userId)
	{
		SRequest_GetOssToken obj = new SRequest_GetOssToken();
		obj.userId = userId;
		return obj;
	}

	public SRequest_GetOssToken clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SRequest_GetOssToken _other_)
	{
		this.userId = _other_.userId;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("userId"))
			userId = _json_.getString("userId");
	}

	public static SRequest_GetOssToken load(String str)
	{
		try
		{
			SRequest_GetOssToken obj = new SRequest_GetOssToken();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_GetOssToken load(JSONObject json)
	{
		try
		{
			SRequest_GetOssToken obj = new SRequest_GetOssToken();
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
			if (userId != null)
				_json_.put("userId", userId);
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SRequest_GetOssToken> loadList(JSONArray ja)
	{
		try
		{
			List<SRequest_GetOssToken> list = new ArrayList<SRequest_GetOssToken>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SRequest_GetOssToken item = SRequest_GetOssToken.load(jo);
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

	public static JSONArray saveList(List<SRequest_GetOssToken> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SRequest_GetOssToken item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Request_GetOssToken _proto_)
	{
		if (_proto_.hasUserId())
			userId = _proto_.getUserId();
	}

	public static SRequest_GetOssToken load(byte[] bytes)
	{
		try
		{
			SRequest_GetOssToken obj = new SRequest_GetOssToken();
			obj.parse(Request_GetOssToken.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_GetOssToken load(Request_GetOssToken proto)
	{
		try
		{
			SRequest_GetOssToken obj = new SRequest_GetOssToken();
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
	public Request_GetOssToken saveToProto()
	{
		Request_GetOssToken.Builder _builder_ = Request_GetOssToken.newBuilder();
		if (userId != null && !userId.equals(Request_GetOssToken.getDefaultInstance().getUserId()))
			_builder_.setUserId(userId);
		return _builder_.build();
	}
}
