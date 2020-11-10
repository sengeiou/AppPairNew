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
import com.lys.protobuf.ProtocolPair.Response_GetUserList;

public class SResponse_GetUserList extends SPTData<Response_GetUserList>
{
	private static final SResponse_GetUserList DefaultInstance = new SResponse_GetUserList();

	public List<SUser> users = new ArrayList<SUser>();

	public static SResponse_GetUserList create()
	{
		SResponse_GetUserList obj = new SResponse_GetUserList();
		return obj;
	}

	public SResponse_GetUserList clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SResponse_GetUserList _other_)
	{
		this.users = _other_.users;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("users"))
			users = SUser.loadList(_json_.getJSONArray("users"));
	}

	public static SResponse_GetUserList load(String str)
	{
		try
		{
			SResponse_GetUserList obj = new SResponse_GetUserList();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_GetUserList load(JSONObject json)
	{
		try
		{
			SResponse_GetUserList obj = new SResponse_GetUserList();
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
			if (users != null)
				_json_.put("users", SUser.saveList(users));
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SResponse_GetUserList> loadList(JSONArray ja)
	{
		try
		{
			List<SResponse_GetUserList> list = new ArrayList<SResponse_GetUserList>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SResponse_GetUserList item = SResponse_GetUserList.load(jo);
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

	public static JSONArray saveList(List<SResponse_GetUserList> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SResponse_GetUserList item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Response_GetUserList _proto_)
	{
		for (int i = 0; i < _proto_.getUsersCount(); i++)
			users.add(SUser.load(_proto_.getUsers(i)));
	}

	public static SResponse_GetUserList load(byte[] bytes)
	{
		try
		{
			SResponse_GetUserList obj = new SResponse_GetUserList();
			obj.parse(Response_GetUserList.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_GetUserList load(Response_GetUserList proto)
	{
		try
		{
			SResponse_GetUserList obj = new SResponse_GetUserList();
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
	public Response_GetUserList saveToProto()
	{
		Response_GetUserList.Builder _builder_ = Response_GetUserList.newBuilder();
		if (users != null)
			for (SUser _value_ : users)
				_builder_.addUsers(_value_.saveToProto());
		return _builder_.build();
	}
}
