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
import com.lys.protobuf.ProtocolPair.Request_GetTaskFileVersion;

// ---------------------- 获取任务文件版本 --------------------------
public class SRequest_GetTaskFileVersion extends SPTData<Request_GetTaskFileVersion>
{
	private static final SRequest_GetTaskFileVersion DefaultInstance = new SRequest_GetTaskFileVersion();

	public SPTask task = null;

	public static SRequest_GetTaskFileVersion create(SPTask task)
	{
		SRequest_GetTaskFileVersion obj = new SRequest_GetTaskFileVersion();
		obj.task = task;
		return obj;
	}

	public SRequest_GetTaskFileVersion clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SRequest_GetTaskFileVersion _other_)
	{
		this.task = _other_.task;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("task"))
			task = SPTask.load(_json_.getJSONObject("task"));
	}

	public static SRequest_GetTaskFileVersion load(String str)
	{
		try
		{
			SRequest_GetTaskFileVersion obj = new SRequest_GetTaskFileVersion();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_GetTaskFileVersion load(JSONObject json)
	{
		try
		{
			SRequest_GetTaskFileVersion obj = new SRequest_GetTaskFileVersion();
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

	public static List<SRequest_GetTaskFileVersion> loadList(JSONArray ja)
	{
		try
		{
			List<SRequest_GetTaskFileVersion> list = new ArrayList<SRequest_GetTaskFileVersion>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SRequest_GetTaskFileVersion item = SRequest_GetTaskFileVersion.load(jo);
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

	public static JSONArray saveList(List<SRequest_GetTaskFileVersion> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SRequest_GetTaskFileVersion item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Request_GetTaskFileVersion _proto_)
	{
		if (_proto_.hasTask())
			task = SPTask.load(_proto_.getTask());
	}

	public static SRequest_GetTaskFileVersion load(byte[] bytes)
	{
		try
		{
			SRequest_GetTaskFileVersion obj = new SRequest_GetTaskFileVersion();
			obj.parse(Request_GetTaskFileVersion.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_GetTaskFileVersion load(Request_GetTaskFileVersion proto)
	{
		try
		{
			SRequest_GetTaskFileVersion obj = new SRequest_GetTaskFileVersion();
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
	public Request_GetTaskFileVersion saveToProto()
	{
		Request_GetTaskFileVersion.Builder _builder_ = Request_GetTaskFileVersion.newBuilder();
		if (task != null)
			_builder_.setTask(task.saveToProto());
		return _builder_.build();
	}
}
