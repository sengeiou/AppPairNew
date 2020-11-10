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
import com.lys.protobuf.ProtocolLive.Request_LiveGetAll;

// ---------------------- xxxxxxxxxxxxx --------------------------
public class SRequest_LiveGetAll extends SPTData<Request_LiveGetAll>
{
	private static final SRequest_LiveGetAll DefaultInstance = new SRequest_LiveGetAll();


	public static SRequest_LiveGetAll create()
	{
		SRequest_LiveGetAll obj = new SRequest_LiveGetAll();
		return obj;
	}

	public SRequest_LiveGetAll clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SRequest_LiveGetAll _other_)
	{
	}

	@Override
	public void parse(JSONObject _json_)
	{
	}

	public static SRequest_LiveGetAll load(String str)
	{
		try
		{
			SRequest_LiveGetAll obj = new SRequest_LiveGetAll();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_LiveGetAll load(JSONObject json)
	{
		try
		{
			SRequest_LiveGetAll obj = new SRequest_LiveGetAll();
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

	public static List<SRequest_LiveGetAll> loadList(JSONArray ja)
	{
		try
		{
			List<SRequest_LiveGetAll> list = new ArrayList<SRequest_LiveGetAll>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SRequest_LiveGetAll item = SRequest_LiveGetAll.load(jo);
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

	public static JSONArray saveList(List<SRequest_LiveGetAll> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SRequest_LiveGetAll item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Request_LiveGetAll _proto_)
	{
	}

	public static SRequest_LiveGetAll load(byte[] bytes)
	{
		try
		{
			SRequest_LiveGetAll obj = new SRequest_LiveGetAll();
			obj.parse(Request_LiveGetAll.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_LiveGetAll load(Request_LiveGetAll proto)
	{
		try
		{
			SRequest_LiveGetAll obj = new SRequest_LiveGetAll();
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
	public Request_LiveGetAll saveToProto()
	{
		Request_LiveGetAll.Builder _builder_ = Request_LiveGetAll.newBuilder();
		return _builder_.build();
	}
}
