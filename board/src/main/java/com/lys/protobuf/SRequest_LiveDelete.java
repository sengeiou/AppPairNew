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
import com.lys.protobuf.ProtocolLive.Request_LiveDelete;

// ---------------------- xxxxxxxxxxxxx --------------------------
public class SRequest_LiveDelete extends SPTData<Request_LiveDelete>
{
	private static final SRequest_LiveDelete DefaultInstance = new SRequest_LiveDelete();

	public String id = null;

	public static SRequest_LiveDelete create(String id)
	{
		SRequest_LiveDelete obj = new SRequest_LiveDelete();
		obj.id = id;
		return obj;
	}

	public SRequest_LiveDelete clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SRequest_LiveDelete _other_)
	{
		this.id = _other_.id;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("id"))
			id = _json_.getString("id");
	}

	public static SRequest_LiveDelete load(String str)
	{
		try
		{
			SRequest_LiveDelete obj = new SRequest_LiveDelete();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_LiveDelete load(JSONObject json)
	{
		try
		{
			SRequest_LiveDelete obj = new SRequest_LiveDelete();
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

	public static List<SRequest_LiveDelete> loadList(JSONArray ja)
	{
		try
		{
			List<SRequest_LiveDelete> list = new ArrayList<SRequest_LiveDelete>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SRequest_LiveDelete item = SRequest_LiveDelete.load(jo);
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

	public static JSONArray saveList(List<SRequest_LiveDelete> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SRequest_LiveDelete item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Request_LiveDelete _proto_)
	{
		if (_proto_.hasId())
			id = _proto_.getId();
	}

	public static SRequest_LiveDelete load(byte[] bytes)
	{
		try
		{
			SRequest_LiveDelete obj = new SRequest_LiveDelete();
			obj.parse(Request_LiveDelete.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_LiveDelete load(Request_LiveDelete proto)
	{
		try
		{
			SRequest_LiveDelete obj = new SRequest_LiveDelete();
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
	public Request_LiveDelete saveToProto()
	{
		Request_LiveDelete.Builder _builder_ = Request_LiveDelete.newBuilder();
		if (id != null && !id.equals(Request_LiveDelete.getDefaultInstance().getId()))
			_builder_.setId(id);
		return _builder_.build();
	}
}
