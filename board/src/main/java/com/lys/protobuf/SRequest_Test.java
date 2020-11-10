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
import com.lys.protobuf.ProtocolPair2.Request_Test;

// ---------------------- 测试 --------------------------
public class SRequest_Test extends SPTData<Request_Test>
{
	private static final SRequest_Test DefaultInstance = new SRequest_Test();


	public static SRequest_Test create()
	{
		SRequest_Test obj = new SRequest_Test();
		return obj;
	}

	public SRequest_Test clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SRequest_Test _other_)
	{
	}

	@Override
	public void parse(JSONObject _json_)
	{
	}

	public static SRequest_Test load(String str)
	{
		try
		{
			SRequest_Test obj = new SRequest_Test();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_Test load(JSONObject json)
	{
		try
		{
			SRequest_Test obj = new SRequest_Test();
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

	public static List<SRequest_Test> loadList(JSONArray ja)
	{
		try
		{
			List<SRequest_Test> list = new ArrayList<SRequest_Test>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SRequest_Test item = SRequest_Test.load(jo);
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

	public static JSONArray saveList(List<SRequest_Test> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SRequest_Test item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Request_Test _proto_)
	{
	}

	public static SRequest_Test load(byte[] bytes)
	{
		try
		{
			SRequest_Test obj = new SRequest_Test();
			obj.parse(Request_Test.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_Test load(Request_Test proto)
	{
		try
		{
			SRequest_Test obj = new SRequest_Test();
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
	public Request_Test saveToProto()
	{
		Request_Test.Builder _builder_ = Request_Test.newBuilder();
		return _builder_.build();
	}
}
