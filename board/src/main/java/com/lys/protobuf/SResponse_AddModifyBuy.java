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
import com.lys.protobuf.ProtocolShop.Response_AddModifyBuy;

public class SResponse_AddModifyBuy extends SPTData<Response_AddModifyBuy>
{
	private static final SResponse_AddModifyBuy DefaultInstance = new SResponse_AddModifyBuy();

	public List<SBuy> buys = new ArrayList<SBuy>();

	public static SResponse_AddModifyBuy create()
	{
		SResponse_AddModifyBuy obj = new SResponse_AddModifyBuy();
		return obj;
	}

	public SResponse_AddModifyBuy clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SResponse_AddModifyBuy _other_)
	{
		this.buys = _other_.buys;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("buys"))
			buys = SBuy.loadList(_json_.getJSONArray("buys"));
	}

	public static SResponse_AddModifyBuy load(String str)
	{
		try
		{
			SResponse_AddModifyBuy obj = new SResponse_AddModifyBuy();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_AddModifyBuy load(JSONObject json)
	{
		try
		{
			SResponse_AddModifyBuy obj = new SResponse_AddModifyBuy();
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
			if (buys != null)
				_json_.put("buys", SBuy.saveList(buys));
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SResponse_AddModifyBuy> loadList(JSONArray ja)
	{
		try
		{
			List<SResponse_AddModifyBuy> list = new ArrayList<SResponse_AddModifyBuy>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SResponse_AddModifyBuy item = SResponse_AddModifyBuy.load(jo);
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

	public static JSONArray saveList(List<SResponse_AddModifyBuy> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SResponse_AddModifyBuy item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Response_AddModifyBuy _proto_)
	{
		for (int i = 0; i < _proto_.getBuysCount(); i++)
			buys.add(SBuy.load(_proto_.getBuys(i)));
	}

	public static SResponse_AddModifyBuy load(byte[] bytes)
	{
		try
		{
			SResponse_AddModifyBuy obj = new SResponse_AddModifyBuy();
			obj.parse(Response_AddModifyBuy.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_AddModifyBuy load(Response_AddModifyBuy proto)
	{
		try
		{
			SResponse_AddModifyBuy obj = new SResponse_AddModifyBuy();
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
	public Response_AddModifyBuy saveToProto()
	{
		Response_AddModifyBuy.Builder _builder_ = Response_AddModifyBuy.newBuilder();
		if (buys != null)
			for (SBuy _value_ : buys)
				_builder_.addBuys(_value_.saveToProto());
		return _builder_.build();
	}
}
