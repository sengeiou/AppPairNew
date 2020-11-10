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
import com.lys.protobuf.ProtocolPair.Request_DeleteTask;

// ---------------------- 删除任务 --------------------------
public class SRequest_DeleteTask extends SPTData<Request_DeleteTask>
{
	private static final SRequest_DeleteTask DefaultInstance = new SRequest_DeleteTask();

	public String taskId = null;

	public static SRequest_DeleteTask create(String taskId)
	{
		SRequest_DeleteTask obj = new SRequest_DeleteTask();
		obj.taskId = taskId;
		return obj;
	}

	public SRequest_DeleteTask clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SRequest_DeleteTask _other_)
	{
		this.taskId = _other_.taskId;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("taskId"))
			taskId = _json_.getString("taskId");
	}

	public static SRequest_DeleteTask load(String str)
	{
		try
		{
			SRequest_DeleteTask obj = new SRequest_DeleteTask();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_DeleteTask load(JSONObject json)
	{
		try
		{
			SRequest_DeleteTask obj = new SRequest_DeleteTask();
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

	public static List<SRequest_DeleteTask> loadList(JSONArray ja)
	{
		try
		{
			List<SRequest_DeleteTask> list = new ArrayList<SRequest_DeleteTask>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SRequest_DeleteTask item = SRequest_DeleteTask.load(jo);
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

	public static JSONArray saveList(List<SRequest_DeleteTask> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SRequest_DeleteTask item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Request_DeleteTask _proto_)
	{
		if (_proto_.hasTaskId())
			taskId = _proto_.getTaskId();
	}

	public static SRequest_DeleteTask load(byte[] bytes)
	{
		try
		{
			SRequest_DeleteTask obj = new SRequest_DeleteTask();
			obj.parse(Request_DeleteTask.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_DeleteTask load(Request_DeleteTask proto)
	{
		try
		{
			SRequest_DeleteTask obj = new SRequest_DeleteTask();
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
	public Request_DeleteTask saveToProto()
	{
		Request_DeleteTask.Builder _builder_ = Request_DeleteTask.newBuilder();
		if (taskId != null && !taskId.equals(Request_DeleteTask.getDefaultInstance().getTaskId()))
			_builder_.setTaskId(taskId);
		return _builder_.build();
	}
}
