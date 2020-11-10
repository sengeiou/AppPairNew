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
import com.lys.protobuf.ProtocolPair2.Response_GetEventList;

public class SResponse_GetEventList extends SPTData<Response_GetEventList>
{
	private static final SResponse_GetEventList DefaultInstance = new SResponse_GetEventList();

	public List<SEvent> events = new ArrayList<SEvent>();

	public static SResponse_GetEventList create()
	{
		SResponse_GetEventList obj = new SResponse_GetEventList();
		return obj;
	}

	public SResponse_GetEventList clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SResponse_GetEventList _other_)
	{
		this.events = _other_.events;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("events"))
			events = SEvent.loadList(_json_.getJSONArray("events"));
	}

	public static SResponse_GetEventList load(String str)
	{
		try
		{
			SResponse_GetEventList obj = new SResponse_GetEventList();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_GetEventList load(JSONObject json)
	{
		try
		{
			SResponse_GetEventList obj = new SResponse_GetEventList();
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
			if (events != null)
				_json_.put("events", SEvent.saveList(events));
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SResponse_GetEventList> loadList(JSONArray ja)
	{
		try
		{
			List<SResponse_GetEventList> list = new ArrayList<SResponse_GetEventList>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SResponse_GetEventList item = SResponse_GetEventList.load(jo);
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

	public static JSONArray saveList(List<SResponse_GetEventList> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SResponse_GetEventList item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Response_GetEventList _proto_)
	{
		for (int i = 0; i < _proto_.getEventsCount(); i++)
			events.add(SEvent.load(_proto_.getEvents(i)));
	}

	public static SResponse_GetEventList load(byte[] bytes)
	{
		try
		{
			SResponse_GetEventList obj = new SResponse_GetEventList();
			obj.parse(Response_GetEventList.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_GetEventList load(Response_GetEventList proto)
	{
		try
		{
			SResponse_GetEventList obj = new SResponse_GetEventList();
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
	public Response_GetEventList saveToProto()
	{
		Response_GetEventList.Builder _builder_ = Response_GetEventList.newBuilder();
		if (events != null)
			for (SEvent _value_ : events)
				_builder_.addEvents(_value_.saveToProto());
		return _builder_.build();
	}
}
