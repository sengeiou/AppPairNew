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
import com.lys.protobuf.ProtocolPair.Response_SendTask;

public class SResponse_SendTask extends SPTData<Response_SendTask>
{
	private static final SResponse_SendTask DefaultInstance = new SResponse_SendTask();

	public List<SPTask> tasks = new ArrayList<SPTask>();

	public static SResponse_SendTask create()
	{
		SResponse_SendTask obj = new SResponse_SendTask();
		return obj;
	}

	public SResponse_SendTask clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SResponse_SendTask _other_)
	{
		this.tasks = _other_.tasks;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("tasks"))
			tasks = SPTask.loadList(_json_.getJSONArray("tasks"));
	}

	public static SResponse_SendTask load(String str)
	{
		try
		{
			SResponse_SendTask obj = new SResponse_SendTask();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_SendTask load(JSONObject json)
	{
		try
		{
			SResponse_SendTask obj = new SResponse_SendTask();
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
			if (tasks != null)
				_json_.put("tasks", SPTask.saveList(tasks));
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SResponse_SendTask> loadList(JSONArray ja)
	{
		try
		{
			List<SResponse_SendTask> list = new ArrayList<SResponse_SendTask>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SResponse_SendTask item = SResponse_SendTask.load(jo);
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

	public static JSONArray saveList(List<SResponse_SendTask> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SResponse_SendTask item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Response_SendTask _proto_)
	{
		for (int i = 0; i < _proto_.getTasksCount(); i++)
			tasks.add(SPTask.load(_proto_.getTasks(i)));
	}

	public static SResponse_SendTask load(byte[] bytes)
	{
		try
		{
			SResponse_SendTask obj = new SResponse_SendTask();
			obj.parse(Response_SendTask.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_SendTask load(Response_SendTask proto)
	{
		try
		{
			SResponse_SendTask obj = new SResponse_SendTask();
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
	public Response_SendTask saveToProto()
	{
		Response_SendTask.Builder _builder_ = Response_SendTask.newBuilder();
		if (tasks != null)
			for (SPTask _value_ : tasks)
				_builder_.addTasks(_value_.saveToProto());
		return _builder_.build();
	}
}
