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
import com.lys.protobuf.ProtocolPair.Response_CreateTask;

public class SResponse_CreateTask extends SPTData<Response_CreateTask>
{
	private static final SResponse_CreateTask DefaultInstance = new SResponse_CreateTask();

	public SPTask task = null;

	public static SResponse_CreateTask create(SPTask task)
	{
		SResponse_CreateTask obj = new SResponse_CreateTask();
		obj.task = task;
		return obj;
	}

	public SResponse_CreateTask clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SResponse_CreateTask _other_)
	{
		this.task = _other_.task;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("task"))
			task = SPTask.load(_json_.getJSONObject("task"));
	}

	public static SResponse_CreateTask load(String str)
	{
		try
		{
			SResponse_CreateTask obj = new SResponse_CreateTask();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_CreateTask load(JSONObject json)
	{
		try
		{
			SResponse_CreateTask obj = new SResponse_CreateTask();
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
			if (task != null)
				_json_.put("task", task.saveToJson());
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SResponse_CreateTask> loadList(JSONArray ja)
	{
		try
		{
			List<SResponse_CreateTask> list = new ArrayList<SResponse_CreateTask>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SResponse_CreateTask item = SResponse_CreateTask.load(jo);
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

	public static JSONArray saveList(List<SResponse_CreateTask> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SResponse_CreateTask item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Response_CreateTask _proto_)
	{
		if (_proto_.hasTask())
			task = SPTask.load(_proto_.getTask());
	}

	public static SResponse_CreateTask load(byte[] bytes)
	{
		try
		{
			SResponse_CreateTask obj = new SResponse_CreateTask();
			obj.parse(Response_CreateTask.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_CreateTask load(Response_CreateTask proto)
	{
		try
		{
			SResponse_CreateTask obj = new SResponse_CreateTask();
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
	public Response_CreateTask saveToProto()
	{
		Response_CreateTask.Builder _builder_ = Response_CreateTask.newBuilder();
		if (task != null)
			_builder_.setTask(task.saveToProto());
		return _builder_.build();
	}
}
