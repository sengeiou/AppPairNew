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
import com.lys.protobuf.ProtocolPair.Request_UserGroupDelete;

// ---------------------- xxxxxxxxxxxxx --------------------------
public class SRequest_UserGroupDelete extends SPTData<Request_UserGroupDelete>
{
	private static final SRequest_UserGroupDelete DefaultInstance = new SRequest_UserGroupDelete();

	public String id = null;

	public static SRequest_UserGroupDelete create(String id)
	{
		SRequest_UserGroupDelete obj = new SRequest_UserGroupDelete();
		obj.id = id;
		return obj;
	}

	public SRequest_UserGroupDelete clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SRequest_UserGroupDelete _other_)
	{
		this.id = _other_.id;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("id"))
			id = _json_.getString("id");
	}

	public static SRequest_UserGroupDelete load(String str)
	{
		try
		{
			SRequest_UserGroupDelete obj = new SRequest_UserGroupDelete();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_UserGroupDelete load(JSONObject json)
	{
		try
		{
			SRequest_UserGroupDelete obj = new SRequest_UserGroupDelete();
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

	public static List<SRequest_UserGroupDelete> loadList(JSONArray ja)
	{
		try
		{
			List<SRequest_UserGroupDelete> list = new ArrayList<SRequest_UserGroupDelete>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SRequest_UserGroupDelete item = SRequest_UserGroupDelete.load(jo);
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

	public static JSONArray saveList(List<SRequest_UserGroupDelete> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SRequest_UserGroupDelete item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Request_UserGroupDelete _proto_)
	{
		if (_proto_.hasId())
			id = _proto_.getId();
	}

	public static SRequest_UserGroupDelete load(byte[] bytes)
	{
		try
		{
			SRequest_UserGroupDelete obj = new SRequest_UserGroupDelete();
			obj.parse(Request_UserGroupDelete.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_UserGroupDelete load(Request_UserGroupDelete proto)
	{
		try
		{
			SRequest_UserGroupDelete obj = new SRequest_UserGroupDelete();
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
	public Request_UserGroupDelete saveToProto()
	{
		Request_UserGroupDelete.Builder _builder_ = Request_UserGroupDelete.newBuilder();
		if (id != null && !id.equals(Request_UserGroupDelete.getDefaultInstance().getId()))
			_builder_.setId(id);
		return _builder_.build();
	}
}
