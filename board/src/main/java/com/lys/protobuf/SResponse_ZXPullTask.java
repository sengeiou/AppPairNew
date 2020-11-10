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
import com.lys.protobuf.ProtocolZhixue.Response_ZXPullTask;

public class SResponse_ZXPullTask extends SPTData<Response_ZXPullTask>
{
	private static final SResponse_ZXPullTask DefaultInstance = new SResponse_ZXPullTask();

	public SZXTask task = null;

	public static SResponse_ZXPullTask create(SZXTask task)
	{
		SResponse_ZXPullTask obj = new SResponse_ZXPullTask();
		obj.task = task;
		return obj;
	}

	public SResponse_ZXPullTask clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SResponse_ZXPullTask _other_)
	{
		this.task = _other_.task;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("task"))
			task = SZXTask.load(_json_.getJSONObject("task"));
	}

	public static SResponse_ZXPullTask load(String str)
	{
		try
		{
			SResponse_ZXPullTask obj = new SResponse_ZXPullTask();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_ZXPullTask load(JSONObject json)
	{
		try
		{
			SResponse_ZXPullTask obj = new SResponse_ZXPullTask();
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

	public static List<SResponse_ZXPullTask> loadList(JSONArray ja)
	{
		try
		{
			List<SResponse_ZXPullTask> list = new ArrayList<SResponse_ZXPullTask>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SResponse_ZXPullTask item = SResponse_ZXPullTask.load(jo);
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

	public static JSONArray saveList(List<SResponse_ZXPullTask> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SResponse_ZXPullTask item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Response_ZXPullTask _proto_)
	{
		if (_proto_.hasTask())
			task = SZXTask.load(_proto_.getTask());
	}

	public static SResponse_ZXPullTask load(byte[] bytes)
	{
		try
		{
			SResponse_ZXPullTask obj = new SResponse_ZXPullTask();
			obj.parse(Response_ZXPullTask.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_ZXPullTask load(Response_ZXPullTask proto)
	{
		try
		{
			SResponse_ZXPullTask obj = new SResponse_ZXPullTask();
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
	public Response_ZXPullTask saveToProto()
	{
		Response_ZXPullTask.Builder _builder_ = Response_ZXPullTask.newBuilder();
		if (task != null)
			_builder_.setTask(task.saveToProto());
		return _builder_.build();
	}
}
