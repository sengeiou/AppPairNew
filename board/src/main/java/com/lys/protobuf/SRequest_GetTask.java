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
import com.lys.protobuf.ProtocolPair.Request_GetTask;

// ---------------------- 获取任务 --------------------------
public class SRequest_GetTask extends SPTData<Request_GetTask>
{
	private static final SRequest_GetTask DefaultInstance = new SRequest_GetTask();

	public String taskId = null;

	public static SRequest_GetTask create(String taskId)
	{
		SRequest_GetTask obj = new SRequest_GetTask();
		obj.taskId = taskId;
		return obj;
	}

	public SRequest_GetTask clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SRequest_GetTask _other_)
	{
		this.taskId = _other_.taskId;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("taskId"))
			taskId = _json_.getString("taskId");
	}

	public static SRequest_GetTask load(String str)
	{
		try
		{
			SRequest_GetTask obj = new SRequest_GetTask();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_GetTask load(JSONObject json)
	{
		try
		{
			SRequest_GetTask obj = new SRequest_GetTask();
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
			if (taskId != null)
				_json_.put("taskId", taskId);
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SRequest_GetTask> loadList(JSONArray ja)
	{
		try
		{
			List<SRequest_GetTask> list = new ArrayList<SRequest_GetTask>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SRequest_GetTask item = SRequest_GetTask.load(jo);
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

	public static JSONArray saveList(List<SRequest_GetTask> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SRequest_GetTask item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Request_GetTask _proto_)
	{
		if (_proto_.hasTaskId())
			taskId = _proto_.getTaskId();
	}

	public static SRequest_GetTask load(byte[] bytes)
	{
		try
		{
			SRequest_GetTask obj = new SRequest_GetTask();
			obj.parse(Request_GetTask.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_GetTask load(Request_GetTask proto)
	{
		try
		{
			SRequest_GetTask obj = new SRequest_GetTask();
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
	public Request_GetTask saveToProto()
	{
		Request_GetTask.Builder _builder_ = Request_GetTask.newBuilder();
		if (taskId != null && !taskId.equals(Request_GetTask.getDefaultInstance().getTaskId()))
			_builder_.setTaskId(taskId);
		return _builder_.build();
	}
}
