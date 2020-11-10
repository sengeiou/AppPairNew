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
import com.lys.protobuf.ProtocolPair.Request_GetUser;

// ---------------------- 获取用户信息 --------------------------
public class SRequest_GetUser extends SPTData<Request_GetUser>
{
	private static final SRequest_GetUser DefaultInstance = new SRequest_GetUser();

	public String userId = null;
	public Boolean checkOwnerId = false;

	public static SRequest_GetUser create(String userId, Boolean checkOwnerId)
	{
		SRequest_GetUser obj = new SRequest_GetUser();
		obj.userId = userId;
		obj.checkOwnerId = checkOwnerId;
		return obj;
	}

	public SRequest_GetUser clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SRequest_GetUser _other_)
	{
		this.userId = _other_.userId;
		this.checkOwnerId = _other_.checkOwnerId;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("userId"))
			userId = _json_.getString("userId");
		if (_json_.containsKey("checkOwnerId"))
			checkOwnerId = _json_.getBoolean("checkOwnerId");
	}

	public static SRequest_GetUser load(String str)
	{
		try
		{
			SRequest_GetUser obj = new SRequest_GetUser();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_GetUser load(JSONObject json)
	{
		try
		{
			SRequest_GetUser obj = new SRequest_GetUser();
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
			if (checkOwnerId != null)
				_json_.put("checkOwnerId", checkOwnerId);
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SRequest_GetUser> loadList(JSONArray ja)
	{
		try
		{
			List<SRequest_GetUser> list = new ArrayList<SRequest_GetUser>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SRequest_GetUser item = SRequest_GetUser.load(jo);
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

	public static JSONArray saveList(List<SRequest_GetUser> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SRequest_GetUser item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Request_GetUser _proto_)
	{
		if (_proto_.hasUserId())
			userId = _proto_.getUserId();
		if (_proto_.hasCheckOwnerId())
			checkOwnerId = _proto_.getCheckOwnerId();
	}

	public static SRequest_GetUser load(byte[] bytes)
	{
		try
		{
			SRequest_GetUser obj = new SRequest_GetUser();
			obj.parse(Request_GetUser.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_GetUser load(Request_GetUser proto)
	{
		try
		{
			SRequest_GetUser obj = new SRequest_GetUser();
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
	public Request_GetUser saveToProto()
	{
		Request_GetUser.Builder _builder_ = Request_GetUser.newBuilder();
		if (userId != null && !userId.equals(Request_GetUser.getDefaultInstance().getUserId()))
			_builder_.setUserId(userId);
		if (checkOwnerId != null && !checkOwnerId.equals(Request_GetUser.getDefaultInstance().getCheckOwnerId()))
			_builder_.setCheckOwnerId(checkOwnerId);
		return _builder_.build();
	}
}
