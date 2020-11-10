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
import com.lys.protobuf.ProtocolPair.Request_RandomOpenTask;

// ---------------------- xxxxx --------------------------
public class SRequest_RandomOpenTask extends SPTData<Request_RandomOpenTask>
{
	private static final SRequest_RandomOpenTask DefaultInstance = new SRequest_RandomOpenTask();

	public Integer count = 0;

	public static SRequest_RandomOpenTask create(Integer count)
	{
		SRequest_RandomOpenTask obj = new SRequest_RandomOpenTask();
		obj.count = count;
		return obj;
	}

	public SRequest_RandomOpenTask clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SRequest_RandomOpenTask _other_)
	{
		this.count = _other_.count;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("count"))
			count = _json_.getInteger("count");
	}

	public static SRequest_RandomOpenTask load(String str)
	{
		try
		{
			SRequest_RandomOpenTask obj = new SRequest_RandomOpenTask();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_RandomOpenTask load(JSONObject json)
	{
		try
		{
			SRequest_RandomOpenTask obj = new SRequest_RandomOpenTask();
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
			if (count != null)
				_json_.put("count", count);
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SRequest_RandomOpenTask> loadList(JSONArray ja)
	{
		try
		{
			List<SRequest_RandomOpenTask> list = new ArrayList<SRequest_RandomOpenTask>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SRequest_RandomOpenTask item = SRequest_RandomOpenTask.load(jo);
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

	public static JSONArray saveList(List<SRequest_RandomOpenTask> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SRequest_RandomOpenTask item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Request_RandomOpenTask _proto_)
	{
		if (_proto_.hasCount())
			count = _proto_.getCount();
	}

	public static SRequest_RandomOpenTask load(byte[] bytes)
	{
		try
		{
			SRequest_RandomOpenTask obj = new SRequest_RandomOpenTask();
			obj.parse(Request_RandomOpenTask.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_RandomOpenTask load(Request_RandomOpenTask proto)
	{
		try
		{
			SRequest_RandomOpenTask obj = new SRequest_RandomOpenTask();
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
	public Request_RandomOpenTask saveToProto()
	{
		Request_RandomOpenTask.Builder _builder_ = Request_RandomOpenTask.newBuilder();
		if (count != null && !count.equals(Request_RandomOpenTask.getDefaultInstance().getCount()))
			_builder_.setCount(count);
		return _builder_.build();
	}
}
