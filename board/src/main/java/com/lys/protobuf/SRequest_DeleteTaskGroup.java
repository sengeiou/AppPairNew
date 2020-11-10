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
import com.lys.protobuf.ProtocolScore.Request_DeleteTaskGroup;

// ---------------------- xxxxxxxx --------------------------
public class SRequest_DeleteTaskGroup extends SPTData<Request_DeleteTaskGroup>
{
	private static final SRequest_DeleteTaskGroup DefaultInstance = new SRequest_DeleteTaskGroup();

	public String taskGroupId = null;

	public static SRequest_DeleteTaskGroup create(String taskGroupId)
	{
		SRequest_DeleteTaskGroup obj = new SRequest_DeleteTaskGroup();
		obj.taskGroupId = taskGroupId;
		return obj;
	}

	public SRequest_DeleteTaskGroup clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SRequest_DeleteTaskGroup _other_)
	{
		this.taskGroupId = _other_.taskGroupId;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("taskGroupId"))
			taskGroupId = _json_.getString("taskGroupId");
	}

	public static SRequest_DeleteTaskGroup load(String str)
	{
		try
		{
			SRequest_DeleteTaskGroup obj = new SRequest_DeleteTaskGroup();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_DeleteTaskGroup load(JSONObject json)
	{
		try
		{
			SRequest_DeleteTaskGroup obj = new SRequest_DeleteTaskGroup();
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
			if (taskGroupId != null)
				_json_.put("taskGroupId", taskGroupId);
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SRequest_DeleteTaskGroup> loadList(JSONArray ja)
	{
		try
		{
			List<SRequest_DeleteTaskGroup> list = new ArrayList<SRequest_DeleteTaskGroup>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SRequest_DeleteTaskGroup item = SRequest_DeleteTaskGroup.load(jo);
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

	public static JSONArray saveList(List<SRequest_DeleteTaskGroup> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SRequest_DeleteTaskGroup item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Request_DeleteTaskGroup _proto_)
	{
		if (_proto_.hasTaskGroupId())
			taskGroupId = _proto_.getTaskGroupId();
	}

	public static SRequest_DeleteTaskGroup load(byte[] bytes)
	{
		try
		{
			SRequest_DeleteTaskGroup obj = new SRequest_DeleteTaskGroup();
			obj.parse(Request_DeleteTaskGroup.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_DeleteTaskGroup load(Request_DeleteTaskGroup proto)
	{
		try
		{
			SRequest_DeleteTaskGroup obj = new SRequest_DeleteTaskGroup();
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
	public Request_DeleteTaskGroup saveToProto()
	{
		Request_DeleteTaskGroup.Builder _builder_ = Request_DeleteTaskGroup.newBuilder();
		if (taskGroupId != null && !taskGroupId.equals(Request_DeleteTaskGroup.getDefaultInstance().getTaskGroupId()))
			_builder_.setTaskGroupId(taskGroupId);
		return _builder_.build();
	}
}
