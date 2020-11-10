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
import com.lys.protobuf.ProtocolScore.Response_AddOrder;

public class SResponse_AddOrder extends SPTData<Response_AddOrder>
{
	private static final SResponse_AddOrder DefaultInstance = new SResponse_AddOrder();


	public static SResponse_AddOrder create()
	{
		SResponse_AddOrder obj = new SResponse_AddOrder();
		return obj;
	}

	public SResponse_AddOrder clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SResponse_AddOrder _other_)
	{
	}

	@Override
	public void parse(JSONObject _json_)
	{
	}

	public static SResponse_AddOrder load(String str)
	{
		try
		{
			SResponse_AddOrder obj = new SResponse_AddOrder();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_AddOrder load(JSONObject json)
	{
		try
		{
			SResponse_AddOrder obj = new SResponse_AddOrder();
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

	public static List<SResponse_AddOrder> loadList(JSONArray ja)
	{
		try
		{
			List<SResponse_AddOrder> list = new ArrayList<SResponse_AddOrder>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SResponse_AddOrder item = SResponse_AddOrder.load(jo);
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

	public static JSONArray saveList(List<SResponse_AddOrder> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SResponse_AddOrder item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Response_AddOrder _proto_)
	{
	}

	public static SResponse_AddOrder load(byte[] bytes)
	{
		try
		{
			SResponse_AddOrder obj = new SResponse_AddOrder();
			obj.parse(Response_AddOrder.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_AddOrder load(Response_AddOrder proto)
	{
		try
		{
			SResponse_AddOrder obj = new SResponse_AddOrder();
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
	public Response_AddOrder saveToProto()
	{
		Response_AddOrder.Builder _builder_ = Response_AddOrder.newBuilder();
		return _builder_.build();
	}
}
