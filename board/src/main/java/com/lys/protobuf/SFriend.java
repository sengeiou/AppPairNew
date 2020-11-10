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
import com.lys.protobuf.ProtocolPair.Friend;

public class SFriend extends SPTData<Friend>
{
	private static final SFriend DefaultInstance = new SFriend();

	public String userId = null;
	public String friendId = null;
	public String group = null;

	public static SFriend create(String userId, String friendId, String group)
	{
		SFriend obj = new SFriend();
		obj.userId = userId;
		obj.friendId = friendId;
		obj.group = group;
		return obj;
	}

	public SFriend clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SFriend _other_)
	{
		this.userId = _other_.userId;
		this.friendId = _other_.friendId;
		this.group = _other_.group;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("userId"))
			userId = _json_.getString("userId");
		if (_json_.containsKey("friendId"))
			friendId = _json_.getString("friendId");
		if (_json_.containsKey("group"))
			group = _json_.getString("group");
	}

	public static SFriend load(String str)
	{
		try
		{
			SFriend obj = new SFriend();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SFriend load(JSONObject json)
	{
		try
		{
			SFriend obj = new SFriend();
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
			if (group != null)
				_json_.put("group", group);
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SFriend> loadList(JSONArray ja)
	{
		try
		{
			List<SFriend> list = new ArrayList<SFriend>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SFriend item = SFriend.load(jo);
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

	public static JSONArray saveList(List<SFriend> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SFriend item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Friend _proto_)
	{
		if (_proto_.hasUserId())
			userId = _proto_.getUserId();
		if (_proto_.hasFriendId())
			friendId = _proto_.getFriendId();
		if (_proto_.hasGroup())
			group = _proto_.getGroup();
	}

	public static SFriend load(byte[] bytes)
	{
		try
		{
			SFriend obj = new SFriend();
			obj.parse(Friend.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SFriend load(Friend proto)
	{
		try
		{
			SFriend obj = new SFriend();
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
	public Friend saveToProto()
	{
		Friend.Builder _builder_ = Friend.newBuilder();
		if (userId != null && !userId.equals(Friend.getDefaultInstance().getUserId()))
			_builder_.setUserId(userId);
		if (friendId != null && !friendId.equals(Friend.getDefaultInstance().getFriendId()))
			_builder_.setFriendId(friendId);
		if (group != null && !group.equals(Friend.getDefaultInstance().getGroup()))
			_builder_.setGroup(group);
		return _builder_.build();
	}
}
