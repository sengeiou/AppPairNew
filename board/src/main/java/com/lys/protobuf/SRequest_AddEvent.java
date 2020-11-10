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
import com.lys.protobuf.ProtocolPair2.Request_AddEvent;

// ---------------------- xxxxxxxx --------------------------
public class SRequest_AddEvent extends SPTData<Request_AddEvent>
{
	private static final SRequest_AddEvent DefaultInstance = new SRequest_AddEvent();

	public SEvent event = null;

	public static SRequest_AddEvent create(SEvent event)
	{
		SRequest_AddEvent obj = new SRequest_AddEvent();
		obj.event = event;
		return obj;
	}

	public SRequest_AddEvent clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SRequest_AddEvent _other_)
	{
		this.event = _other_.event;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("event"))
			event = SEvent.load(_json_.getJSONObject("event"));
	}

	public static SRequest_AddEvent load(String str)
	{
		try
		{
			SRequest_AddEvent obj = new SRequest_AddEvent();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_AddEvent load(JSONObject json)
	{
		try
		{
			SRequest_AddEvent obj = new SRequest_AddEvent();
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
			if (event != null)
				_json_.put("event", event.saveToJson());
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SRequest_AddEvent> loadList(JSONArray ja)
	{
		try
		{
			List<SRequest_AddEvent> list = new ArrayList<SRequest_AddEvent>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SRequest_AddEvent item = SRequest_AddEvent.load(jo);
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

	public static JSONArray saveList(List<SRequest_AddEvent> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SRequest_AddEvent item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Request_AddEvent _proto_)
	{
		if (_proto_.hasEvent())
			event = SEvent.load(_proto_.getEvent());
	}

	public static SRequest_AddEvent load(byte[] bytes)
	{
		try
		{
			SRequest_AddEvent obj = new SRequest_AddEvent();
			obj.parse(Request_AddEvent.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_AddEvent load(Request_AddEvent proto)
	{
		try
		{
			SRequest_AddEvent obj = new SRequest_AddEvent();
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
	public Request_AddEvent saveToProto()
	{
		Request_AddEvent.Builder _builder_ = Request_AddEvent.newBuilder();
		if (event != null)
			_builder_.setEvent(event.saveToProto());
		return _builder_.build();
	}
}
