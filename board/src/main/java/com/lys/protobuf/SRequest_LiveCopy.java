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
import com.lys.protobuf.ProtocolLive.Request_LiveCopy;

// ---------------------- xxxxxxxxxxxxx --------------------------
public class SRequest_LiveCopy extends SPTData<Request_LiveCopy>
{
	private static final SRequest_LiveCopy DefaultInstance = new SRequest_LiveCopy();

	public String id = null;

	public static SRequest_LiveCopy create(String id)
	{
		SRequest_LiveCopy obj = new SRequest_LiveCopy();
		obj.id = id;
		return obj;
	}

	public SRequest_LiveCopy clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SRequest_LiveCopy _other_)
	{
		this.id = _other_.id;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("id"))
			id = _json_.getString("id");
	}

	public static SRequest_LiveCopy load(String str)
	{
		try
		{
			SRequest_LiveCopy obj = new SRequest_LiveCopy();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_LiveCopy load(JSONObject json)
	{
		try
		{
			SRequest_LiveCopy obj = new SRequest_LiveCopy();
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
			if (id != null)
				_json_.put("id", id);
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SRequest_LiveCopy> loadList(JSONArray ja)
	{
		try
		{
			List<SRequest_LiveCopy> list = new ArrayList<SRequest_LiveCopy>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SRequest_LiveCopy item = SRequest_LiveCopy.load(jo);
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

	public static JSONArray saveList(List<SRequest_LiveCopy> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SRequest_LiveCopy item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Request_LiveCopy _proto_)
	{
		if (_proto_.hasId())
			id = _proto_.getId();
	}

	public static SRequest_LiveCopy load(byte[] bytes)
	{
		try
		{
			SRequest_LiveCopy obj = new SRequest_LiveCopy();
			obj.parse(Request_LiveCopy.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_LiveCopy load(Request_LiveCopy proto)
	{
		try
		{
			SRequest_LiveCopy obj = new SRequest_LiveCopy();
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
	public Request_LiveCopy saveToProto()
	{
		Request_LiveCopy.Builder _builder_ = Request_LiveCopy.newBuilder();
		if (id != null && !id.equals(Request_LiveCopy.getDefaultInstance().getId()))
			_builder_.setId(id);
		return _builder_.build();
	}
}
