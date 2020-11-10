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
import com.lys.protobuf.ProtocolPair.Response_GetUser;

public class SResponse_GetUser extends SPTData<Response_GetUser>
{
	private static final SResponse_GetUser DefaultInstance = new SResponse_GetUser();

	public SUser user = null;

	public static SResponse_GetUser create(SUser user)
	{
		SResponse_GetUser obj = new SResponse_GetUser();
		obj.user = user;
		return obj;
	}

	public SResponse_GetUser clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SResponse_GetUser _other_)
	{
		this.user = _other_.user;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("user"))
			user = SUser.load(_json_.getJSONObject("user"));
	}

	public static SResponse_GetUser load(String str)
	{
		try
		{
			SResponse_GetUser obj = new SResponse_GetUser();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_GetUser load(JSONObject json)
	{
		try
		{
			SResponse_GetUser obj = new SResponse_GetUser();
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
			if (user != null)
				_json_.put("user", user.saveToJson());
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SResponse_GetUser> loadList(JSONArray ja)
	{
		try
		{
			List<SResponse_GetUser> list = new ArrayList<SResponse_GetUser>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SResponse_GetUser item = SResponse_GetUser.load(jo);
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

	public static JSONArray saveList(List<SResponse_GetUser> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SResponse_GetUser item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Response_GetUser _proto_)
	{
		if (_proto_.hasUser())
			user = SUser.load(_proto_.getUser());
	}

	public static SResponse_GetUser load(byte[] bytes)
	{
		try
		{
			SResponse_GetUser obj = new SResponse_GetUser();
			obj.parse(Response_GetUser.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_GetUser load(Response_GetUser proto)
	{
		try
		{
			SResponse_GetUser obj = new SResponse_GetUser();
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
	public Response_GetUser saveToProto()
	{
		Response_GetUser.Builder _builder_ = Response_GetUser.newBuilder();
		if (user != null)
			_builder_.setUser(user.saveToProto());
		return _builder_.build();
	}
}
