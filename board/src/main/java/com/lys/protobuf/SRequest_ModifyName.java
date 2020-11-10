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
import com.lys.protobuf.ProtocolPair.Request_ModifyName;

// ---------------------- 修改XX --------------------------
public class SRequest_ModifyName extends SPTData<Request_ModifyName>
{
	private static final SRequest_ModifyName DefaultInstance = new SRequest_ModifyName();

	public String userId = null;
	public String name = null;

	public static SRequest_ModifyName create(String userId, String name)
	{
		SRequest_ModifyName obj = new SRequest_ModifyName();
		obj.userId = userId;
		obj.name = name;
		return obj;
	}

	public SRequest_ModifyName clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SRequest_ModifyName _other_)
	{
		this.userId = _other_.userId;
		this.name = _other_.name;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("userId"))
			userId = _json_.getString("userId");
		if (_json_.containsKey("name"))
			name = _json_.getString("name");
	}

	public static SRequest_ModifyName load(String str)
	{
		try
		{
			SRequest_ModifyName obj = new SRequest_ModifyName();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_ModifyName load(JSONObject json)
	{
		try
		{
			SRequest_ModifyName obj = new SRequest_ModifyName();
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
			if (name != null)
				_json_.put("name", name);
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SRequest_ModifyName> loadList(JSONArray ja)
	{
		try
		{
			List<SRequest_ModifyName> list = new ArrayList<SRequest_ModifyName>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SRequest_ModifyName item = SRequest_ModifyName.load(jo);
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

	public static JSONArray saveList(List<SRequest_ModifyName> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SRequest_ModifyName item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Request_ModifyName _proto_)
	{
		if (_proto_.hasUserId())
			userId = _proto_.getUserId();
		if (_proto_.hasName())
			name = _proto_.getName();
	}

	public static SRequest_ModifyName load(byte[] bytes)
	{
		try
		{
			SRequest_ModifyName obj = new SRequest_ModifyName();
			obj.parse(Request_ModifyName.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_ModifyName load(Request_ModifyName proto)
	{
		try
		{
			SRequest_ModifyName obj = new SRequest_ModifyName();
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
	public Request_ModifyName saveToProto()
	{
		Request_ModifyName.Builder _builder_ = Request_ModifyName.newBuilder();
		if (userId != null && !userId.equals(Request_ModifyName.getDefaultInstance().getUserId()))
			_builder_.setUserId(userId);
		if (name != null && !name.equals(Request_ModifyName.getDefaultInstance().getName()))
			_builder_.setName(name);
		return _builder_.build();
	}
}
