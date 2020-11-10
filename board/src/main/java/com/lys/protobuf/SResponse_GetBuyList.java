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
import com.lys.protobuf.ProtocolShop.Response_GetBuyList;

public class SResponse_GetBuyList extends SPTData<Response_GetBuyList>
{
	private static final SResponse_GetBuyList DefaultInstance = new SResponse_GetBuyList();

	public List<SBuy> buys = new ArrayList<SBuy>();

	public static SResponse_GetBuyList create()
	{
		SResponse_GetBuyList obj = new SResponse_GetBuyList();
		return obj;
	}

	public SResponse_GetBuyList clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SResponse_GetBuyList _other_)
	{
		this.buys = _other_.buys;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("buys"))
			buys = SBuy.loadList(_json_.getJSONArray("buys"));
	}

	public static SResponse_GetBuyList load(String str)
	{
		try
		{
			SResponse_GetBuyList obj = new SResponse_GetBuyList();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_GetBuyList load(JSONObject json)
	{
		try
		{
			SResponse_GetBuyList obj = new SResponse_GetBuyList();
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

	public static List<SResponse_GetBuyList> loadList(JSONArray ja)
	{
		try
		{
			List<SResponse_GetBuyList> list = new ArrayList<SResponse_GetBuyList>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SResponse_GetBuyList item = SResponse_GetBuyList.load(jo);
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

	public static JSONArray saveList(List<SResponse_GetBuyList> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SResponse_GetBuyList item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Response_GetBuyList _proto_)
	{
		for (int i = 0; i < _proto_.getBuysCount(); i++)
			buys.add(SBuy.load(_proto_.getBuys(i)));
	}

	public static SResponse_GetBuyList load(byte[] bytes)
	{
		try
		{
			SResponse_GetBuyList obj = new SResponse_GetBuyList();
			obj.parse(Response_GetBuyList.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_GetBuyList load(Response_GetBuyList proto)
	{
		try
		{
			SResponse_GetBuyList obj = new SResponse_GetBuyList();
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
	public Response_GetBuyList saveToProto()
	{
		Response_GetBuyList.Builder _builder_ = Response_GetBuyList.newBuilder();
		if (buys != null)
			for (SBuy _value_ : buys)
				_builder_.addBuys(_value_.saveToProto());
		return _builder_.build();
	}
}
