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
import com.lys.protobuf.ProtocolPair.Request_AddFriend;

// ---------------------- 添加好友 --------------------------
public class SRequest_AddFriend extends SPTData<Request_AddFriend>
{
	private static final SRequest_AddFriend DefaultInstance = new SRequest_AddFriend();

	public String userId = null;
	public String friendId = null;

	public static SRequest_AddFriend create(String userId, String friendId)
	{
		SRequest_AddFriend obj = new SRequest_AddFriend();
		obj.userId = userId;
		obj.friendId = friendId;
		return obj;
	}

	public SRequest_AddFriend clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SRequest_AddFriend _other_)
	{
		this.userId = _other_.userId;
		this.friendId = _other_.friendId;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("userId"))
			userId = _json_.getString("userId");
		if (_json_.containsKey("friendId"))
			friendId = _json_.getString("friendId");
	}

	public static SRequest_AddFriend load(String str)
	{
		try
		{
			SRequest_AddFriend obj = new SRequest_AddFriend();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_AddFriend load(JSONObject json)
	{
		try
		{
			SRequest_AddFriend obj = new SRequest_AddFriend();
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
			if (friendId != null)
				_json_.put("friendId", friendId);
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SRequest_AddFriend> loadList(JSONArray ja)
	{
		try
		{
			List<SRequest_AddFriend> list = new ArrayList<SRequest_AddFriend>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SRequest_AddFriend item = SRequest_AddFriend.load(jo);
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

	public static JSONArray saveList(List<SRequest_AddFriend> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SRequest_AddFriend item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Request_AddFriend _proto_)
	{
		if (_proto_.hasUserId())
			userId = _proto_.getUserId();
		if (_proto_.hasFriendId())
			friendId = _proto_.getFriendId();
	}

	public static SRequest_AddFriend load(byte[] bytes)
	{
		try
		{
			SRequest_AddFriend obj = new SRequest_AddFriend();
			obj.parse(Request_AddFriend.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_AddFriend load(Request_AddFriend proto)
	{
		try
		{
			SRequest_AddFriend obj = new SRequest_AddFriend();
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
	public Request_AddFriend saveToProto()
	{
		Request_AddFriend.Builder _builder_ = Request_AddFriend.newBuilder();
		if (userId != null && !userId.equals(Request_AddFriend.getDefaultInstance().getUserId()))
			_builder_.setUserId(userId);
		if (friendId != null && !friendId.equals(Request_AddFriend.getDefaultInstance().getFriendId()))
			_builder_.setFriendId(friendId);
		return _builder_.build();
	}
}
