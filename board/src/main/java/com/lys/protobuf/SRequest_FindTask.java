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
import com.lys.protobuf.ProtocolPair.Request_FindTask;

// ---------------------- xxxxx --------------------------
public class SRequest_FindTask extends SPTData<Request_FindTask>
{
	private static final SRequest_FindTask DefaultInstance = new SRequest_FindTask();

	public String group = null;
	public String name = null;

	public static SRequest_FindTask create(String group, String name)
	{
		SRequest_FindTask obj = new SRequest_FindTask();
		obj.group = group;
		obj.name = name;
		return obj;
	}

	public SRequest_FindTask clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SRequest_FindTask _other_)
	{
		this.group = _other_.group;
		this.name = _other_.name;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("group"))
			group = _json_.getString("group");
		if (_json_.containsKey("name"))
			name = _json_.getString("name");
	}

	public static SRequest_FindTask load(String str)
	{
		try
		{
			SRequest_FindTask obj = new SRequest_FindTask();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_FindTask load(JSONObject json)
	{
		try
		{
			SRequest_FindTask obj = new SRequest_FindTask();
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
			if (group != null)
				_json_.put("group", group);
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

	public static List<SRequest_FindTask> loadList(JSONArray ja)
	{
		try
		{
			List<SRequest_FindTask> list = new ArrayList<SRequest_FindTask>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SRequest_FindTask item = SRequest_FindTask.load(jo);
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

	public static JSONArray saveList(List<SRequest_FindTask> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SRequest_FindTask item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Request_FindTask _proto_)
	{
		if (_proto_.hasGroup())
			group = _proto_.getGroup();
		if (_proto_.hasName())
			name = _proto_.getName();
	}

	public static SRequest_FindTask load(byte[] bytes)
	{
		try
		{
			SRequest_FindTask obj = new SRequest_FindTask();
			obj.parse(Request_FindTask.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_FindTask load(Request_FindTask proto)
	{
		try
		{
			SRequest_FindTask obj = new SRequest_FindTask();
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
	public Request_FindTask saveToProto()
	{
		Request_FindTask.Builder _builder_ = Request_FindTask.newBuilder();
		if (group != null && !group.equals(Request_FindTask.getDefaultInstance().getGroup()))
			_builder_.setGroup(group);
		if (name != null && !name.equals(Request_FindTask.getDefaultInstance().getName()))
			_builder_.setName(name);
		return _builder_.build();
	}
}
