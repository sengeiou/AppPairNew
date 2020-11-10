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
import com.lys.protobuf.ProtocolPair.Request_DeleteFriend;

// ---------------------- 删除好友 --------------------------
public class SRequest_DeleteFriend extends SPTData<Request_DeleteFriend>
{
	private static final SRequest_DeleteFriend DefaultInstance = new SRequest_DeleteFriend();

	public String userId = null;
	public String friendId = null;

	public static SRequest_DeleteFriend create(String userId, String friendId)
	{
		SRequest_DeleteFriend obj = new SRequest_DeleteFriend();
		obj.userId = userId;
		obj.friendId = friendId;
		return obj;
	}

	public SRequest_DeleteFriend clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SRequest_DeleteFriend _other_)
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

	public static SRequest_DeleteFriend load(String str)
	{
		try
		{
			SRequest_DeleteFriend obj = new SRequest_DeleteFriend();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_DeleteFriend load(JSONObject json)
	{
		try
		{
			SRequest_DeleteFriend obj = new SRequest_DeleteFriend();
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

	public static List<SRequest_DeleteFriend> loadList(JSONArray ja)
	{
		try
		{
			List<SRequest_DeleteFriend> list = new ArrayList<SRequest_DeleteFriend>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SRequest_DeleteFriend item = SRequest_DeleteFriend.load(jo);
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

	public static JSONArray saveList(List<SRequest_DeleteFriend> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SRequest_DeleteFriend item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Request_DeleteFriend _proto_)
	{
		if (_proto_.hasUserId())
			userId = _proto_.getUserId();
		if (_proto_.hasFriendId())
			friendId = _proto_.getFriendId();
	}

	public static SRequest_DeleteFriend load(byte[] bytes)
	{
		try
		{
			SRequest_DeleteFriend obj = new SRequest_DeleteFriend();
			obj.parse(Request_DeleteFriend.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_DeleteFriend load(Request_DeleteFriend proto)
	{
		try
		{
			SRequest_DeleteFriend obj = new SRequest_DeleteFriend();
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
	public Request_DeleteFriend saveToProto()
	{
		Request_DeleteFriend.Builder _builder_ = Request_DeleteFriend.newBuilder();
		if (userId != null && !userId.equals(Request_DeleteFriend.getDefaultInstance().getUserId()))
			_builder_.setUserId(userId);
		if (friendId != null && !friendId.equals(Request_DeleteFriend.getDefaultInstance().getFriendId()))
			_builder_.setFriendId(friendId);
		return _builder_.build();
	}
}
