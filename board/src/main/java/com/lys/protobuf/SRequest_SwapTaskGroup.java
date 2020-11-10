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
import com.lys.protobuf.ProtocolScore.Request_SwapTaskGroup;

// ---------------------- xxxxxxxx --------------------------
public class SRequest_SwapTaskGroup extends SPTData<Request_SwapTaskGroup>
{
	private static final SRequest_SwapTaskGroup DefaultInstance = new SRequest_SwapTaskGroup();

	public STaskGroup taskGroup1 = null;
	public STaskGroup taskGroup2 = null;

	public static SRequest_SwapTaskGroup create(STaskGroup taskGroup1, STaskGroup taskGroup2)
	{
		SRequest_SwapTaskGroup obj = new SRequest_SwapTaskGroup();
		obj.taskGroup1 = taskGroup1;
		obj.taskGroup2 = taskGroup2;
		return obj;
	}

	public SRequest_SwapTaskGroup clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SRequest_SwapTaskGroup _other_)
	{
		this.taskGroup1 = _other_.taskGroup1;
		this.taskGroup2 = _other_.taskGroup2;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("taskGroup1"))
			taskGroup1 = STaskGroup.load(_json_.getJSONObject("taskGroup1"));
		if (_json_.containsKey("taskGroup2"))
			taskGroup2 = STaskGroup.load(_json_.getJSONObject("taskGroup2"));
	}

	public static SRequest_SwapTaskGroup load(String str)
	{
		try
		{
			SRequest_SwapTaskGroup obj = new SRequest_SwapTaskGroup();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_SwapTaskGroup load(JSONObject json)
	{
		try
		{
			SRequest_SwapTaskGroup obj = new SRequest_SwapTaskGroup();
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
			if (taskGroup1 != null)
				_json_.put("taskGroup1", taskGroup1.saveToJson());
			if (taskGroup2 != null)
				_json_.put("taskGroup2", taskGroup2.saveToJson());
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SRequest_SwapTaskGroup> loadList(JSONArray ja)
	{
		try
		{
			List<SRequest_SwapTaskGroup> list = new ArrayList<SRequest_SwapTaskGroup>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SRequest_SwapTaskGroup item = SRequest_SwapTaskGroup.load(jo);
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

	public static JSONArray saveList(List<SRequest_SwapTaskGroup> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SRequest_SwapTaskGroup item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Request_SwapTaskGroup _proto_)
	{
		if (_proto_.hasTaskGroup1())
			taskGroup1 = STaskGroup.load(_proto_.getTaskGroup1());
		if (_proto_.hasTaskGroup2())
			taskGroup2 = STaskGroup.load(_proto_.getTaskGroup2());
	}

	public static SRequest_SwapTaskGroup load(byte[] bytes)
	{
		try
		{
			SRequest_SwapTaskGroup obj = new SRequest_SwapTaskGroup();
			obj.parse(Request_SwapTaskGroup.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_SwapTaskGroup load(Request_SwapTaskGroup proto)
	{
		try
		{
			SRequest_SwapTaskGroup obj = new SRequest_SwapTaskGroup();
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
	public Request_SwapTaskGroup saveToProto()
	{
		Request_SwapTaskGroup.Builder _builder_ = Request_SwapTaskGroup.newBuilder();
		if (taskGroup1 != null)
			_builder_.setTaskGroup1(taskGroup1.saveToProto());
		if (taskGroup2 != null)
			_builder_.setTaskGroup2(taskGroup2.saveToProto());
		return _builder_.build();
	}
}
