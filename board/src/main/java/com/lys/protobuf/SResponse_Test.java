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
import com.lys.protobuf.ProtocolPair2.Response_Test;

public class SResponse_Test extends SPTData<Response_Test>
{
	private static final SResponse_Test DefaultInstance = new SResponse_Test();


	public static SResponse_Test create()
	{
		SResponse_Test obj = new SResponse_Test();
		return obj;
	}

	public SResponse_Test clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SResponse_Test _other_)
	{
	}

	@Override
	public void parse(JSONObject _json_)
	{
	}

	public static SResponse_Test load(String str)
	{
		try
		{
			SResponse_Test obj = new SResponse_Test();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_Test load(JSONObject json)
	{
		try
		{
			SResponse_Test obj = new SResponse_Test();
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

	public static List<SResponse_Test> loadList(JSONArray ja)
	{
		try
		{
			List<SResponse_Test> list = new ArrayList<SResponse_Test>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SResponse_Test item = SResponse_Test.load(jo);
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

	public static JSONArray saveList(List<SResponse_Test> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SResponse_Test item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Response_Test _proto_)
	{
	}

	public static SResponse_Test load(byte[] bytes)
	{
		try
		{
			SResponse_Test obj = new SResponse_Test();
			obj.parse(Response_Test.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_Test load(Response_Test proto)
	{
		try
		{
			SResponse_Test obj = new SResponse_Test();
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
	public Response_Test saveToProto()
	{
		Response_Test.Builder _builder_ = Response_Test.newBuilder();
		return _builder_.build();
	}
}
