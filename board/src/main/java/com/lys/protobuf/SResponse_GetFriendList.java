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
import com.lys.protobuf.ProtocolPair.Response_GetFriendList;

public class SResponse_GetFriendList extends SPTData<Response_GetFriendList>
{
	private static final SResponse_GetFriendList DefaultInstance = new SResponse_GetFriendList();

	public List<SUser> friends = new ArrayList<SUser>();

	public static SResponse_GetFriendList create()
	{
		SResponse_GetFriendList obj = new SResponse_GetFriendList();
		return obj;
	}

	public SResponse_GetFriendList clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SResponse_GetFriendList _other_)
	{
		this.friends = _other_.friends;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("friends"))
			friends = SUser.loadList(_json_.getJSONArray("friends"));
	}

	public static SResponse_GetFriendList load(String str)
	{
		try
		{
			SResponse_GetFriendList obj = new SResponse_GetFriendList();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_GetFriendList load(JSONObject json)
	{
		try
		{
			SResponse_GetFriendList obj = new SResponse_GetFriendList();
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
			if (friends != null)
				_json_.put("friends", SUser.saveList(friends));
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SResponse_GetFriendList> loadList(JSONArray ja)
	{
		try
		{
			List<SResponse_GetFriendList> list = new ArrayList<SResponse_GetFriendList>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SResponse_GetFriendList item = SResponse_GetFriendList.load(jo);
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

	public static JSONArray saveList(List<SResponse_GetFriendList> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SResponse_GetFriendList item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Response_GetFriendList _proto_)
	{
		for (int i = 0; i < _proto_.getFriendsCount(); i++)
			friends.add(SUser.load(_proto_.getFriends(i)));
	}

	public static SResponse_GetFriendList load(byte[] bytes)
	{
		try
		{
			SResponse_GetFriendList obj = new SResponse_GetFriendList();
			obj.parse(Response_GetFriendList.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_GetFriendList load(Response_GetFriendList proto)
	{
		try
		{
			SResponse_GetFriendList obj = new SResponse_GetFriendList();
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
	public Response_GetFriendList saveToProto()
	{
		Response_GetFriendList.Builder _builder_ = Response_GetFriendList.newBuilder();
		if (friends != null)
			for (SUser _value_ : friends)
				_builder_.addFriends(_value_.saveToProto());
		return _builder_.build();
	}
}
