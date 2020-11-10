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
import com.lys.protobuf.ProtocolPair.Request_SetTaskOpen;

// ---------------------- 设置任务开放状态 --------------------------
public class SRequest_SetTaskOpen extends SPTData<Request_SetTaskOpen>
{
	private static final SRequest_SetTaskOpen DefaultInstance = new SRequest_SetTaskOpen();

	public String taskId = null;
	public Integer open = 0;

	public static SRequest_SetTaskOpen create(String taskId, Integer open)
	{
		SRequest_SetTaskOpen obj = new SRequest_SetTaskOpen();
		obj.taskId = taskId;
		obj.open = open;
		return obj;
	}

	public SRequest_SetTaskOpen clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SRequest_SetTaskOpen _other_)
	{
		this.taskId = _other_.taskId;
		this.open = _other_.open;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("taskId"))
			taskId = _json_.getString("taskId");
		if (_json_.containsKey("open"))
			open = _json_.getInteger("open");
	}

	public static SRequest_SetTaskOpen load(String str)
	{
		try
		{
			SRequest_SetTaskOpen obj = new SRequest_SetTaskOpen();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_SetTaskOpen load(JSONObject json)
	{
		try
		{
			SRequest_SetTaskOpen obj = new SRequest_SetTaskOpen();
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
			if (open != null)
				_json_.put("open", open);
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SRequest_SetTaskOpen> loadList(JSONArray ja)
	{
		try
		{
			List<SRequest_SetTaskOpen> list = new ArrayList<SRequest_SetTaskOpen>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SRequest_SetTaskOpen item = SRequest_SetTaskOpen.load(jo);
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

	public static JSONArray saveList(List<SRequest_SetTaskOpen> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SRequest_SetTaskOpen item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Request_SetTaskOpen _proto_)
	{
		if (_proto_.hasTaskId())
			taskId = _proto_.getTaskId();
		if (_proto_.hasOpen())
			open = _proto_.getOpen();
	}

	public static SRequest_SetTaskOpen load(byte[] bytes)
	{
		try
		{
			SRequest_SetTaskOpen obj = new SRequest_SetTaskOpen();
			obj.parse(Request_SetTaskOpen.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_SetTaskOpen load(Request_SetTaskOpen proto)
	{
		try
		{
			SRequest_SetTaskOpen obj = new SRequest_SetTaskOpen();
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
	public Request_SetTaskOpen saveToProto()
	{
		Request_SetTaskOpen.Builder _builder_ = Request_SetTaskOpen.newBuilder();
		if (taskId != null && !taskId.equals(Request_SetTaskOpen.getDefaultInstance().getTaskId()))
			_builder_.setTaskId(taskId);
		if (open != null && !open.equals(Request_SetTaskOpen.getDefaultInstance().getOpen()))
			_builder_.setOpen(open);
		return _builder_.build();
	}
}
