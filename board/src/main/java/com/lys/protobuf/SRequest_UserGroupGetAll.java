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
import com.lys.protobuf.ProtocolPair.Request_UserGroupGetAll;

// ---------------------- xxxxxxxxxxxxx --------------------------
public class SRequest_UserGroupGetAll extends SPTData<Request_UserGroupGetAll>
{
	private static final SRequest_UserGroupGetAll DefaultInstance = new SRequest_UserGroupGetAll();


	public static SRequest_UserGroupGetAll create()
	{
		SRequest_UserGroupGetAll obj = new SRequest_UserGroupGetAll();
		return obj;
	}

	public SRequest_UserGroupGetAll clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SRequest_UserGroupGetAll _other_)
	{
	}

	@Override
	public void parse(JSONObject _json_)
	{
	}

	public static SRequest_UserGroupGetAll load(String str)
	{
		try
		{
			SRequest_UserGroupGetAll obj = new SRequest_UserGroupGetAll();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_UserGroupGetAll load(JSONObject json)
	{
		try
		{
			SRequest_UserGroupGetAll obj = new SRequest_UserGroupGetAll();
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

	public static List<SRequest_UserGroupGetAll> loadList(JSONArray ja)
	{
		try
		{
			List<SRequest_UserGroupGetAll> list = new ArrayList<SRequest_UserGroupGetAll>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SRequest_UserGroupGetAll item = SRequest_UserGroupGetAll.load(jo);
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

	public static JSONArray saveList(List<SRequest_UserGroupGetAll> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SRequest_UserGroupGetAll item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Request_UserGroupGetAll _proto_)
	{
	}

	public static SRequest_UserGroupGetAll load(byte[] bytes)
	{
		try
		{
			SRequest_UserGroupGetAll obj = new SRequest_UserGroupGetAll();
			obj.parse(Request_UserGroupGetAll.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_UserGroupGetAll load(Request_UserGroupGetAll proto)
	{
		try
		{
			SRequest_UserGroupGetAll obj = new SRequest_UserGroupGetAll();
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
	public Request_UserGroupGetAll saveToProto()
	{
		Request_UserGroupGetAll.Builder _builder_ = Request_UserGroupGetAll.newBuilder();
		return _builder_.build();
	}
}
