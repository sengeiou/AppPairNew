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
import com.lys.protobuf.ProtocolPair.Response_UserGroupGetAll;

public class SResponse_UserGroupGetAll extends SPTData<Response_UserGroupGetAll>
{
	private static final SResponse_UserGroupGetAll DefaultInstance = new SResponse_UserGroupGetAll();

	public List<SUserGroup> userGroups = new ArrayList<SUserGroup>();

	public static SResponse_UserGroupGetAll create()
	{
		SResponse_UserGroupGetAll obj = new SResponse_UserGroupGetAll();
		return obj;
	}

	public SResponse_UserGroupGetAll clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SResponse_UserGroupGetAll _other_)
	{
		this.userGroups = _other_.userGroups;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("userGroups"))
			userGroups = SUserGroup.loadList(_json_.getJSONArray("userGroups"));
	}

	public static SResponse_UserGroupGetAll load(String str)
	{
		try
		{
			SResponse_UserGroupGetAll obj = new SResponse_UserGroupGetAll();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_UserGroupGetAll load(JSONObject json)
	{
		try
		{
			SResponse_UserGroupGetAll obj = new SResponse_UserGroupGetAll();
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
			if (userGroups != null)
				_json_.put("userGroups", SUserGroup.saveList(userGroups));
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SResponse_UserGroupGetAll> loadList(JSONArray ja)
	{
		try
		{
			List<SResponse_UserGroupGetAll> list = new ArrayList<SResponse_UserGroupGetAll>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SResponse_UserGroupGetAll item = SResponse_UserGroupGetAll.load(jo);
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

	public static JSONArray saveList(List<SResponse_UserGroupGetAll> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SResponse_UserGroupGetAll item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Response_UserGroupGetAll _proto_)
	{
		for (int i = 0; i < _proto_.getUserGroupsCount(); i++)
			userGroups.add(SUserGroup.load(_proto_.getUserGroups(i)));
	}

	public static SResponse_UserGroupGetAll load(byte[] bytes)
	{
		try
		{
			SResponse_UserGroupGetAll obj = new SResponse_UserGroupGetAll();
			obj.parse(Response_UserGroupGetAll.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_UserGroupGetAll load(Response_UserGroupGetAll proto)
	{
		try
		{
			SResponse_UserGroupGetAll obj = new SResponse_UserGroupGetAll();
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
	public Response_UserGroupGetAll saveToProto()
	{
		Response_UserGroupGetAll.Builder _builder_ = Response_UserGroupGetAll.newBuilder();
		if (userGroups != null)
			for (SUserGroup _value_ : userGroups)
				_builder_.addUserGroups(_value_.saveToProto());
		return _builder_.build();
	}
}
