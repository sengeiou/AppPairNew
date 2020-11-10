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
import com.lys.protobuf.ProtocolPair.Response_GetTaskList;

public class SResponse_GetTaskList extends SPTData<Response_GetTaskList>
{
	private static final SResponse_GetTaskList DefaultInstance = new SResponse_GetTaskList();

	public List<SPTask> tasks = new ArrayList<SPTask>();

	public static SResponse_GetTaskList create()
	{
		SResponse_GetTaskList obj = new SResponse_GetTaskList();
		return obj;
	}

	public SResponse_GetTaskList clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SResponse_GetTaskList _other_)
	{
		this.tasks = _other_.tasks;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("tasks"))
			tasks = SPTask.loadList(_json_.getJSONArray("tasks"));
	}

	public static SResponse_GetTaskList load(String str)
	{
		try
		{
			SResponse_GetTaskList obj = new SResponse_GetTaskList();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_GetTaskList load(JSONObject json)
	{
		try
		{
			SResponse_GetTaskList obj = new SResponse_GetTaskList();
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

	public static List<SResponse_GetTaskList> loadList(JSONArray ja)
	{
		try
		{
			List<SResponse_GetTaskList> list = new ArrayList<SResponse_GetTaskList>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SResponse_GetTaskList item = SResponse_GetTaskList.load(jo);
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

	public static JSONArray saveList(List<SResponse_GetTaskList> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SResponse_GetTaskList item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Response_GetTaskList _proto_)
	{
		for (int i = 0; i < _proto_.getTasksCount(); i++)
			tasks.add(SPTask.load(_proto_.getTasks(i)));
	}

	public static SResponse_GetTaskList load(byte[] bytes)
	{
		try
		{
			SResponse_GetTaskList obj = new SResponse_GetTaskList();
			obj.parse(Response_GetTaskList.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_GetTaskList load(Response_GetTaskList proto)
	{
		try
		{
			SResponse_GetTaskList obj = new SResponse_GetTaskList();
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
	public Response_GetTaskList saveToProto()
	{
		Response_GetTaskList.Builder _builder_ = Response_GetTaskList.newBuilder();
		if (tasks != null)
			for (SPTask _value_ : tasks)
				_builder_.addTasks(_value_.saveToProto());
		return _builder_.build();
	}
}
