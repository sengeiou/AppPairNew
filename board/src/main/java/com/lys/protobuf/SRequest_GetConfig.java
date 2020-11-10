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
import com.lys.protobuf.ProtocolPair2.Request_GetConfig;

// ---------------------- 获取配置 --------------------------
public class SRequest_GetConfig extends SPTData<Request_GetConfig>
{
	private static final SRequest_GetConfig DefaultInstance = new SRequest_GetConfig();


	public static SRequest_GetConfig create()
	{
		SRequest_GetConfig obj = new SRequest_GetConfig();
		return obj;
	}

	public SRequest_GetConfig clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SRequest_GetConfig _other_)
	{
	}

	@Override
	public void parse(JSONObject _json_)
	{
	}

	public static SRequest_GetConfig load(String str)
	{
		try
		{
			SRequest_GetConfig obj = new SRequest_GetConfig();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_GetConfig load(JSONObject json)
	{
		try
		{
			SRequest_GetConfig obj = new SRequest_GetConfig();
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

	public static List<SRequest_GetConfig> loadList(JSONArray ja)
	{
		try
		{
			List<SRequest_GetConfig> list = new ArrayList<SRequest_GetConfig>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SRequest_GetConfig item = SRequest_GetConfig.load(jo);
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

	public static JSONArray saveList(List<SRequest_GetConfig> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SRequest_GetConfig item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Request_GetConfig _proto_)
	{
	}

	public static SRequest_GetConfig load(byte[] bytes)
	{
		try
		{
			SRequest_GetConfig obj = new SRequest_GetConfig();
			obj.parse(Request_GetConfig.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_GetConfig load(Request_GetConfig proto)
	{
		try
		{
			SRequest_GetConfig obj = new SRequest_GetConfig();
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
	public Request_GetConfig saveToProto()
	{
		Request_GetConfig.Builder _builder_ = Request_GetConfig.newBuilder();
		return _builder_.build();
	}
}
