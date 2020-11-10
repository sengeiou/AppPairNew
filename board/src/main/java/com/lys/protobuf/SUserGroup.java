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
import com.lys.protobuf.ProtocolPair.UserGroup;

public class SUserGroup extends SPTData<UserGroup>
{
	private static final SUserGroup DefaultInstance = new SUserGroup();

	public String id = null;
	public String name = null;
	public List<String> userIds = new ArrayList<String>();

	public static SUserGroup create(String id, String name)
	{
		SUserGroup obj = new SUserGroup();
		obj.id = id;
		obj.name = name;
		return obj;
	}

	public SUserGroup clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SUserGroup _other_)
	{
		this.id = _other_.id;
		this.name = _other_.name;
		this.userIds = _other_.userIds;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("id"))
			id = _json_.getString("id");
		if (_json_.containsKey("name"))
			name = _json_.getString("name");
		if (_json_.containsKey("userIds"))
			userIds = AppDataTool.loadStringList(AppDataTool.getJSONArray(_json_, "userIds"));
	}

	public static SUserGroup load(String str)
	{
		try
		{
			SUserGroup obj = new SUserGroup();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SUserGroup load(JSONObject json)
	{
		try
		{
			SUserGroup obj = new SUserGroup();
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
			if (id != null)
				_json_.put("id", id);
			if (name != null)
				_json_.put("name", name);
			if (userIds != null)
				_json_.put("userIds", AppDataTool.saveStringList(userIds));
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SUserGroup> loadList(JSONArray ja)
	{
		try
		{
			List<SUserGroup> list = new ArrayList<SUserGroup>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SUserGroup item = SUserGroup.load(jo);
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

	public static JSONArray saveList(List<SUserGroup> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SUserGroup item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(UserGroup _proto_)
	{
		if (_proto_.hasId())
			id = _proto_.getId();
		if (_proto_.hasName())
			name = _proto_.getName();
		for (int i = 0; i < _proto_.getUserIdsCount(); i++)
			userIds.add(_proto_.getUserIds(i));
	}

	public static SUserGroup load(byte[] bytes)
	{
		try
		{
			SUserGroup obj = new SUserGroup();
			obj.parse(UserGroup.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SUserGroup load(UserGroup proto)
	{
		try
		{
			SUserGroup obj = new SUserGroup();
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
	public UserGroup saveToProto()
	{
		UserGroup.Builder _builder_ = UserGroup.newBuilder();
		if (id != null && !id.equals(UserGroup.getDefaultInstance().getId()))
			_builder_.setId(id);
		if (name != null && !name.equals(UserGroup.getDefaultInstance().getName()))
			_builder_.setName(name);
		if (userIds != null)
			for (String _value_ : userIds)
				_builder_.addUserIds(_value_);
		return _builder_.build();
	}
}
