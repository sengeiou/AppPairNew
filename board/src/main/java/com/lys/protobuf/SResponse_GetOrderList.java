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
import com.lys.protobuf.ProtocolScore.Response_GetOrderList;

public class SResponse_GetOrderList extends SPTData<Response_GetOrderList>
{
	private static final SResponse_GetOrderList DefaultInstance = new SResponse_GetOrderList();

	public List<SOrder> orders = new ArrayList<SOrder>();

	public static SResponse_GetOrderList create()
	{
		SResponse_GetOrderList obj = new SResponse_GetOrderList();
		return obj;
	}

	public SResponse_GetOrderList clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SResponse_GetOrderList _other_)
	{
		this.orders = _other_.orders;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("orders"))
			orders = SOrder.loadList(_json_.getJSONArray("orders"));
	}

	public static SResponse_GetOrderList load(String str)
	{
		try
		{
			SResponse_GetOrderList obj = new SResponse_GetOrderList();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_GetOrderList load(JSONObject json)
	{
		try
		{
			SResponse_GetOrderList obj = new SResponse_GetOrderList();
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
			if (orders != null)
				_json_.put("orders", SOrder.saveList(orders));
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SResponse_GetOrderList> loadList(JSONArray ja)
	{
		try
		{
			List<SResponse_GetOrderList> list = new ArrayList<SResponse_GetOrderList>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SResponse_GetOrderList item = SResponse_GetOrderList.load(jo);
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

	public static JSONArray saveList(List<SResponse_GetOrderList> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SResponse_GetOrderList item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Response_GetOrderList _proto_)
	{
		for (int i = 0; i < _proto_.getOrdersCount(); i++)
			orders.add(SOrder.load(_proto_.getOrders(i)));
	}

	public static SResponse_GetOrderList load(byte[] bytes)
	{
		try
		{
			SResponse_GetOrderList obj = new SResponse_GetOrderList();
			obj.parse(Response_GetOrderList.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_GetOrderList load(Response_GetOrderList proto)
	{
		try
		{
			SResponse_GetOrderList obj = new SResponse_GetOrderList();
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
	public Response_GetOrderList saveToProto()
	{
		Response_GetOrderList.Builder _builder_ = Response_GetOrderList.newBuilder();
		if (orders != null)
			for (SOrder _value_ : orders)
				_builder_.addOrders(_value_.saveToProto());
		return _builder_.build();
	}
}
