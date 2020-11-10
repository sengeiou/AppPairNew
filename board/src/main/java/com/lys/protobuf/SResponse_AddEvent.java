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
import com.lys.protobuf.ProtocolPair2.Response_AddEvent;

public class SResponse_AddEvent extends SPTData<Response_AddEvent>
{
	private static final SResponse_AddEvent DefaultInstance = new SResponse_AddEvent();


	public static SResponse_AddEvent create()
	{
		SResponse_AddEvent obj = new SResponse_AddEvent();
		return obj;
	}

	public SResponse_AddEvent clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SResponse_AddEvent _other_)
	{
	}

	@Override
	public void parse(JSONObject _json_)
	{
	}

	public static SResponse_AddEvent load(String str)
	{
		try
		{
			SResponse_AddEvent obj = new SResponse_AddEvent();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_AddEvent load(JSONObject json)
	{
		try
		{
			SResponse_AddEvent obj = new SResponse_AddEvent();
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
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SResponse_AddEvent> loadList(JSONArray ja)
	{
		try
		{
			List<SResponse_AddEvent> list = new ArrayList<SResponse_AddEvent>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SResponse_AddEvent item = SResponse_AddEvent.load(jo);
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

	public static JSONArray saveList(List<SResponse_AddEvent> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SResponse_AddEvent item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Response_AddEvent _proto_)
	{
	}

	public static SResponse_AddEvent load(byte[] bytes)
	{
		try
		{
			SResponse_AddEvent obj = new SResponse_AddEvent();
			obj.parse(Response_AddEvent.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_AddEvent load(Response_AddEvent proto)
	{
		try
		{
			SResponse_AddEvent obj = new SResponse_AddEvent();
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
	public Response_AddEvent saveToProto()
	{
		Response_AddEvent.Builder _builder_ = Response_AddEvent.newBuilder();
		return _builder_.build();
	}
}
