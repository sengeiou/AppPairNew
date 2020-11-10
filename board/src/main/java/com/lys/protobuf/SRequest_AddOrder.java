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
import com.lys.protobuf.ProtocolScore.Request_AddOrder;

// ---------------------- xxxxxxxx --------------------------
public class SRequest_AddOrder extends SPTData<Request_AddOrder>
{
	private static final SRequest_AddOrder DefaultInstance = new SRequest_AddOrder();

	public SOrder order = null;

	public static SRequest_AddOrder create(SOrder order)
	{
		SRequest_AddOrder obj = new SRequest_AddOrder();
		obj.order = order;
		return obj;
	}

	public SRequest_AddOrder clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SRequest_AddOrder _other_)
	{
		this.order = _other_.order;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("order"))
			order = SOrder.load(_json_.getJSONObject("order"));
	}

	public static SRequest_AddOrder load(String str)
	{
		try
		{
			SRequest_AddOrder obj = new SRequest_AddOrder();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_AddOrder load(JSONObject json)
	{
		try
		{
			SRequest_AddOrder obj = new SRequest_AddOrder();
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
			if (order != null)
				_json_.put("order", order.saveToJson());
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SRequest_AddOrder> loadList(JSONArray ja)
	{
		try
		{
			List<SRequest_AddOrder> list = new ArrayList<SRequest_AddOrder>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SRequest_AddOrder item = SRequest_AddOrder.load(jo);
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

	public static JSONArray saveList(List<SRequest_AddOrder> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SRequest_AddOrder item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Request_AddOrder _proto_)
	{
		if (_proto_.hasOrder())
			order = SOrder.load(_proto_.getOrder());
	}

	public static SRequest_AddOrder load(byte[] bytes)
	{
		try
		{
			SRequest_AddOrder obj = new SRequest_AddOrder();
			obj.parse(Request_AddOrder.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_AddOrder load(Request_AddOrder proto)
	{
		try
		{
			SRequest_AddOrder obj = new SRequest_AddOrder();
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
	public Request_AddOrder saveToProto()
	{
		Request_AddOrder.Builder _builder_ = Request_AddOrder.newBuilder();
		if (order != null)
			_builder_.setOrder(order.saveToProto());
		return _builder_.build();
	}
}
