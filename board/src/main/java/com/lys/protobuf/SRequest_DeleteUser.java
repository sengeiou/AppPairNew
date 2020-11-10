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
import com.lys.protobuf.ProtocolPair.Request_DeleteUser;

// ---------------------- 删除用户 --------------------------
public class SRequest_DeleteUser extends SPTData<Request_DeleteUser>
{
	private static final SRequest_DeleteUser DefaultInstance = new SRequest_DeleteUser();

	public String userId = null;

	public static SRequest_DeleteUser create(String userId)
	{
		SRequest_DeleteUser obj = new SRequest_DeleteUser();
		obj.userId = userId;
		return obj;
	}

	public SRequest_DeleteUser clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SRequest_DeleteUser _other_)
	{
		this.userId = _other_.userId;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("userId"))
			userId = _json_.getString("userId");
	}

	public static SRequest_DeleteUser load(String str)
	{
		try
		{
			SRequest_DeleteUser obj = new SRequest_DeleteUser();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_DeleteUser load(JSONObject json)
	{
		try
		{
			SRequest_DeleteUser obj = new SRequest_DeleteUser();
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

	public static List<SRequest_DeleteUser> loadList(JSONArray ja)
	{
		try
		{
			List<SRequest_DeleteUser> list = new ArrayList<SRequest_DeleteUser>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SRequest_DeleteUser item = SRequest_DeleteUser.load(jo);
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

	public static JSONArray saveList(List<SRequest_DeleteUser> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SRequest_DeleteUser item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Request_DeleteUser _proto_)
	{
		if (_proto_.hasUserId())
			userId = _proto_.getUserId();
	}

	public static SRequest_DeleteUser load(byte[] bytes)
	{
		try
		{
			SRequest_DeleteUser obj = new SRequest_DeleteUser();
			obj.parse(Request_DeleteUser.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_DeleteUser load(Request_DeleteUser proto)
	{
		try
		{
			SRequest_DeleteUser obj = new SRequest_DeleteUser();
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
	public Request_DeleteUser saveToProto()
	{
		Request_DeleteUser.Builder _builder_ = Request_DeleteUser.newBuilder();
		if (userId != null && !userId.equals(Request_DeleteUser.getDefaultInstance().getUserId()))
			_builder_.setUserId(userId);
		return _builder_.build();
	}
}
