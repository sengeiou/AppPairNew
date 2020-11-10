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
import com.lys.protobuf.ProtocolPair.Request_UserGroupAddModify;

// ---------------------- xxxxxxxxxxxxx --------------------------
public class SRequest_UserGroupAddModify extends SPTData<Request_UserGroupAddModify>
{
	private static final SRequest_UserGroupAddModify DefaultInstance = new SRequest_UserGroupAddModify();

	public SUserGroup userGroup = null;

	public static SRequest_UserGroupAddModify create(SUserGroup userGroup)
	{
		SRequest_UserGroupAddModify obj = new SRequest_UserGroupAddModify();
		obj.userGroup = userGroup;
		return obj;
	}

	public SRequest_UserGroupAddModify clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SRequest_UserGroupAddModify _other_)
	{
		this.userGroup = _other_.userGroup;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("userGroup"))
			userGroup = SUserGroup.load(_json_.getJSONObject("userGroup"));
	}

	public static SRequest_UserGroupAddModify load(String str)
	{
		try
		{
			SRequest_UserGroupAddModify obj = new SRequest_UserGroupAddModify();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_UserGroupAddModify load(JSONObject json)
	{
		try
		{
			SRequest_UserGroupAddModify obj = new SRequest_UserGroupAddModify();
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
			if (userGroup != null)
				_json_.put("userGroup", userGroup.saveToJson());
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SRequest_UserGroupAddModify> loadList(JSONArray ja)
	{
		try
		{
			List<SRequest_UserGroupAddModify> list = new ArrayList<SRequest_UserGroupAddModify>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SRequest_UserGroupAddModify item = SRequest_UserGroupAddModify.load(jo);
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

	public static JSONArray saveList(List<SRequest_UserGroupAddModify> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SRequest_UserGroupAddModify item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Request_UserGroupAddModify _proto_)
	{
		if (_proto_.hasUserGroup())
			userGroup = SUserGroup.load(_proto_.getUserGroup());
	}

	public static SRequest_UserGroupAddModify load(byte[] bytes)
	{
		try
		{
			SRequest_UserGroupAddModify obj = new SRequest_UserGroupAddModify();
			obj.parse(Request_UserGroupAddModify.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_UserGroupAddModify load(Request_UserGroupAddModify proto)
	{
		try
		{
			SRequest_UserGroupAddModify obj = new SRequest_UserGroupAddModify();
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
	public Request_UserGroupAddModify saveToProto()
	{
		Request_UserGroupAddModify.Builder _builder_ = Request_UserGroupAddModify.newBuilder();
		if (userGroup != null)
			_builder_.setUserGroup(userGroup.saveToProto());
		return _builder_.build();
	}
}
