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
import com.lys.protobuf.ProtocolScore.Request_AddModifyTaskGroup;

// ---------------------- xxxxxxxx --------------------------
public class SRequest_AddModifyTaskGroup extends SPTData<Request_AddModifyTaskGroup>
{
	private static final SRequest_AddModifyTaskGroup DefaultInstance = new SRequest_AddModifyTaskGroup();

	public STaskGroup taskGroup = null;

	public static SRequest_AddModifyTaskGroup create(STaskGroup taskGroup)
	{
		SRequest_AddModifyTaskGroup obj = new SRequest_AddModifyTaskGroup();
		obj.taskGroup = taskGroup;
		return obj;
	}

	public SRequest_AddModifyTaskGroup clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SRequest_AddModifyTaskGroup _other_)
	{
		this.taskGroup = _other_.taskGroup;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("taskGroup"))
			taskGroup = STaskGroup.load(_json_.getJSONObject("taskGroup"));
	}

	public static SRequest_AddModifyTaskGroup load(String str)
	{
		try
		{
			SRequest_AddModifyTaskGroup obj = new SRequest_AddModifyTaskGroup();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_AddModifyTaskGroup load(JSONObject json)
	{
		try
		{
			SRequest_AddModifyTaskGroup obj = new SRequest_AddModifyTaskGroup();
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
			if (taskGroup != null)
				_json_.put("taskGroup", taskGroup.saveToJson());
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SRequest_AddModifyTaskGroup> loadList(JSONArray ja)
	{
		try
		{
			List<SRequest_AddModifyTaskGroup> list = new ArrayList<SRequest_AddModifyTaskGroup>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SRequest_AddModifyTaskGroup item = SRequest_AddModifyTaskGroup.load(jo);
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

	public static JSONArray saveList(List<SRequest_AddModifyTaskGroup> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SRequest_AddModifyTaskGroup item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Request_AddModifyTaskGroup _proto_)
	{
		if (_proto_.hasTaskGroup())
			taskGroup = STaskGroup.load(_proto_.getTaskGroup());
	}

	public static SRequest_AddModifyTaskGroup load(byte[] bytes)
	{
		try
		{
			SRequest_AddModifyTaskGroup obj = new SRequest_AddModifyTaskGroup();
			obj.parse(Request_AddModifyTaskGroup.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_AddModifyTaskGroup load(Request_AddModifyTaskGroup proto)
	{
		try
		{
			SRequest_AddModifyTaskGroup obj = new SRequest_AddModifyTaskGroup();
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
	public Request_AddModifyTaskGroup saveToProto()
	{
		Request_AddModifyTaskGroup.Builder _builder_ = Request_AddModifyTaskGroup.newBuilder();
		if (taskGroup != null)
			_builder_.setTaskGroup(taskGroup.saveToProto());
		return _builder_.build();
	}
}
