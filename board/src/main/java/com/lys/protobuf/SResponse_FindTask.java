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
import com.lys.protobuf.ProtocolPair.Response_FindTask;

public class SResponse_FindTask extends SPTData<Response_FindTask>
{
	private static final SResponse_FindTask DefaultInstance = new SResponse_FindTask();

	public SPTask task = null;

	public static SResponse_FindTask create(SPTask task)
	{
		SResponse_FindTask obj = new SResponse_FindTask();
		obj.task = task;
		return obj;
	}

	public SResponse_FindTask clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SResponse_FindTask _other_)
	{
		this.task = _other_.task;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("task"))
			task = SPTask.load(_json_.getJSONObject("task"));
	}

	public static SResponse_FindTask load(String str)
	{
		try
		{
			SResponse_FindTask obj = new SResponse_FindTask();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_FindTask load(JSONObject json)
	{
		try
		{
			SResponse_FindTask obj = new SResponse_FindTask();
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

	public static List<SResponse_FindTask> loadList(JSONArray ja)
	{
		try
		{
			List<SResponse_FindTask> list = new ArrayList<SResponse_FindTask>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SResponse_FindTask item = SResponse_FindTask.load(jo);
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

	public static JSONArray saveList(List<SResponse_FindTask> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SResponse_FindTask item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Response_FindTask _proto_)
	{
		if (_proto_.hasTask())
			task = SPTask.load(_proto_.getTask());
	}

	public static SResponse_FindTask load(byte[] bytes)
	{
		try
		{
			SResponse_FindTask obj = new SResponse_FindTask();
			obj.parse(Response_FindTask.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_FindTask load(Response_FindTask proto)
	{
		try
		{
			SResponse_FindTask obj = new SResponse_FindTask();
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
	public Response_FindTask saveToProto()
	{
		Response_FindTask.Builder _builder_ = Response_FindTask.newBuilder();
		if (task != null)
			_builder_.setTask(task.saveToProto());
		return _builder_.build();
	}
}
