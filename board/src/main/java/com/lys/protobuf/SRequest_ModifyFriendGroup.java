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
import com.lys.protobuf.ProtocolPair.Request_ModifyFriendGroup;

// ---------------------- xxxxxx --------------------------
public class SRequest_ModifyFriendGroup extends SPTData<Request_ModifyFriendGroup>
{
	private static final SRequest_ModifyFriendGroup DefaultInstance = new SRequest_ModifyFriendGroup();

	public String userId = null;
	public String friendId = null;
	public String group = null;

	public static SRequest_ModifyFriendGroup create(String userId, String friendId, String group)
	{
		SRequest_ModifyFriendGroup obj = new SRequest_ModifyFriendGroup();
		obj.userId = userId;
		obj.friendId = friendId;
		obj.group = group;
		return obj;
	}

	public SRequest_ModifyFriendGroup clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SRequest_ModifyFriendGroup _other_)
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

	public static SRequest_ModifyFriendGroup load(String str)
	{
		try
		{
			SRequest_ModifyFriendGroup obj = new SRequest_ModifyFriendGroup();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_ModifyFriendGroup load(JSONObject json)
	{
		try
		{
			SRequest_ModifyFriendGroup obj = new SRequest_ModifyFriendGroup();
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

	public static List<SRequest_ModifyFriendGroup> loadList(JSONArray ja)
	{
		try
		{
			List<SRequest_ModifyFriendGroup> list = new ArrayList<SRequest_ModifyFriendGroup>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SRequest_ModifyFriendGroup item = SRequest_ModifyFriendGroup.load(jo);
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

	public static JSONArray saveList(List<SRequest_ModifyFriendGroup> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SRequest_ModifyFriendGroup item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Request_ModifyFriendGroup _proto_)
	{
		if (_proto_.hasUserId())
			userId = _proto_.getUserId();
		if (_proto_.hasFriendId())
			friendId = _proto_.getFriendId();
		if (_proto_.hasGroup())
			group = _proto_.getGroup();
	}

	public static SRequest_ModifyFriendGroup load(byte[] bytes)
	{
		try
		{
			SRequest_ModifyFriendGroup obj = new SRequest_ModifyFriendGroup();
			obj.parse(Request_ModifyFriendGroup.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_ModifyFriendGroup load(Request_ModifyFriendGroup proto)
	{
		try
		{
			SRequest_ModifyFriendGroup obj = new SRequest_ModifyFriendGroup();
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
	public Request_ModifyFriendGroup saveToProto()
	{
		Request_ModifyFriendGroup.Builder _builder_ = Request_ModifyFriendGroup.newBuilder();
		if (userId != null && !userId.equals(Request_ModifyFriendGroup.getDefaultInstance().getUserId()))
			_builder_.setUserId(userId);
		if (friendId != null && !friendId.equals(Request_ModifyFriendGroup.getDefaultInstance().getFriendId()))
			_builder_.setFriendId(friendId);
		if (group != null && !group.equals(Request_ModifyFriendGroup.getDefaultInstance().getGroup()))
			_builder_.setGroup(group);
		return _builder_.build();
	}
}
