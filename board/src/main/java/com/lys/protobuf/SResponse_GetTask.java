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
import com.lys.protobuf.ProtocolPair.Response_GetTask;

public class SResponse_GetTask extends SPTData<Response_GetTask>
{
	private static final SResponse_GetTask DefaultInstance = new SResponse_GetTask();

	public SPTask task = null;

	public static SResponse_GetTask create(SPTask task)
	{
		SResponse_GetTask obj = new SResponse_GetTask();
		obj.task = task;
		return obj;
	}

	public SResponse_GetTask clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SResponse_GetTask _other_)
	{
		this.task = _other_.task;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("task"))
			task = SPTask.load(_json_.getJSONObject("task"));
	}

	public static SResponse_GetTask load(String str)
	{
		try
		{
			SResponse_GetTask obj = new SResponse_GetTask();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_GetTask load(JSONObject json)
	{
		try
		{
			SResponse_GetTask obj = new SResponse_GetTask();
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

	public static List<SResponse_GetTask> loadList(JSONArray ja)
	{
		try
		{
			List<SResponse_GetTask> list = new ArrayList<SResponse_GetTask>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SResponse_GetTask item = SResponse_GetTask.load(jo);
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

	public static JSONArray saveList(List<SResponse_GetTask> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SResponse_GetTask item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Response_GetTask _proto_)
	{
		if (_proto_.hasTask())
			task = SPTask.load(_proto_.getTask());
	}

	public static SResponse_GetTask load(byte[] bytes)
	{
		try
		{
			SResponse_GetTask obj = new SResponse_GetTask();
			obj.parse(Response_GetTask.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_GetTask load(Response_GetTask proto)
	{
		try
		{
			SResponse_GetTask obj = new SResponse_GetTask();
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
	public Response_GetTask saveToProto()
	{
		Response_GetTask.Builder _builder_ = Response_GetTask.newBuilder();
		if (task != null)
			_builder_.setTask(task.saveToProto());
		return _builder_.build();
	}
}
