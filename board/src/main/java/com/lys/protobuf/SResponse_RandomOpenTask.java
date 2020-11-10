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
import com.lys.protobuf.ProtocolPair.Response_RandomOpenTask;

public class SResponse_RandomOpenTask extends SPTData<Response_RandomOpenTask>
{
	private static final SResponse_RandomOpenTask DefaultInstance = new SResponse_RandomOpenTask();

	public List<SPTask> tasks = new ArrayList<SPTask>();

	public static SResponse_RandomOpenTask create()
	{
		SResponse_RandomOpenTask obj = new SResponse_RandomOpenTask();
		return obj;
	}

	public SResponse_RandomOpenTask clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SResponse_RandomOpenTask _other_)
	{
		this.tasks = _other_.tasks;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("tasks"))
			tasks = SPTask.loadList(_json_.getJSONArray("tasks"));
	}

	public static SResponse_RandomOpenTask load(String str)
	{
		try
		{
			SResponse_RandomOpenTask obj = new SResponse_RandomOpenTask();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_RandomOpenTask load(JSONObject json)
	{
		try
		{
			SResponse_RandomOpenTask obj = new SResponse_RandomOpenTask();
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

	public static List<SResponse_RandomOpenTask> loadList(JSONArray ja)
	{
		try
		{
			List<SResponse_RandomOpenTask> list = new ArrayList<SResponse_RandomOpenTask>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SResponse_RandomOpenTask item = SResponse_RandomOpenTask.load(jo);
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

	public static JSONArray saveList(List<SResponse_RandomOpenTask> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SResponse_RandomOpenTask item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Response_RandomOpenTask _proto_)
	{
		for (int i = 0; i < _proto_.getTasksCount(); i++)
			tasks.add(SPTask.load(_proto_.getTasks(i)));
	}

	public static SResponse_RandomOpenTask load(byte[] bytes)
	{
		try
		{
			SResponse_RandomOpenTask obj = new SResponse_RandomOpenTask();
			obj.parse(Response_RandomOpenTask.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_RandomOpenTask load(Response_RandomOpenTask proto)
	{
		try
		{
			SResponse_RandomOpenTask obj = new SResponse_RandomOpenTask();
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
	public Response_RandomOpenTask saveToProto()
	{
		Response_RandomOpenTask.Builder _builder_ = Response_RandomOpenTask.newBuilder();
		if (tasks != null)
			for (SPTask _value_ : tasks)
				_builder_.addTasks(_value_.saveToProto());
		return _builder_.build();
	}
}
