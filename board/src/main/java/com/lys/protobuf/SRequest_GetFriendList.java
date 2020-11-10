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
import com.lys.protobuf.ProtocolPair.Request_GetFriendList;

// ---------------------- 获取好友列表 --------------------------
public class SRequest_GetFriendList extends SPTData<Request_GetFriendList>
{
	private static final SRequest_GetFriendList DefaultInstance = new SRequest_GetFriendList();

	public String userId = null;
	public Boolean checkOwnerId = false;

	public static SRequest_GetFriendList create(String userId, Boolean checkOwnerId)
	{
		SRequest_GetFriendList obj = new SRequest_GetFriendList();
		obj.userId = userId;
		obj.checkOwnerId = checkOwnerId;
		return obj;
	}

	public SRequest_GetFriendList clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SRequest_GetFriendList _other_)
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

	public static SRequest_GetFriendList load(String str)
	{
		try
		{
			SRequest_GetFriendList obj = new SRequest_GetFriendList();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_GetFriendList load(JSONObject json)
	{
		try
		{
			SRequest_GetFriendList obj = new SRequest_GetFriendList();
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

	public static List<SRequest_GetFriendList> loadList(JSONArray ja)
	{
		try
		{
			List<SRequest_GetFriendList> list = new ArrayList<SRequest_GetFriendList>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SRequest_GetFriendList item = SRequest_GetFriendList.load(jo);
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

	public static JSONArray saveList(List<SRequest_GetFriendList> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SRequest_GetFriendList item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Request_GetFriendList _proto_)
	{
		if (_proto_.hasUserId())
			userId = _proto_.getUserId();
		if (_proto_.hasCheckOwnerId())
			checkOwnerId = _proto_.getCheckOwnerId();
	}

	public static SRequest_GetFriendList load(byte[] bytes)
	{
		try
		{
			SRequest_GetFriendList obj = new SRequest_GetFriendList();
			obj.parse(Request_GetFriendList.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_GetFriendList load(Request_GetFriendList proto)
	{
		try
		{
			SRequest_GetFriendList obj = new SRequest_GetFriendList();
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
	public Request_GetFriendList saveToProto()
	{
		Request_GetFriendList.Builder _builder_ = Request_GetFriendList.newBuilder();
		if (userId != null && !userId.equals(Request_GetFriendList.getDefaultInstance().getUserId()))
			_builder_.setUserId(userId);
		if (checkOwnerId != null && !checkOwnerId.equals(Request_GetFriendList.getDefaultInstance().getCheckOwnerId()))
			_builder_.setCheckOwnerId(checkOwnerId);
		return _builder_.build();
	}
}
