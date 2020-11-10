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
import com.lys.protobuf.ProtocolScore.Request_AddModifyGoods;

// ---------------------- xxxxxxxx --------------------------
public class SRequest_AddModifyGoods extends SPTData<Request_AddModifyGoods>
{
	private static final SRequest_AddModifyGoods DefaultInstance = new SRequest_AddModifyGoods();

	public SGoods goods = null;

	public static SRequest_AddModifyGoods create(SGoods goods)
	{
		SRequest_AddModifyGoods obj = new SRequest_AddModifyGoods();
		obj.goods = goods;
		return obj;
	}

	public SRequest_AddModifyGoods clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SRequest_AddModifyGoods _other_)
	{
		this.goods = _other_.goods;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("goods"))
			goods = SGoods.load(_json_.getJSONObject("goods"));
	}

	public static SRequest_AddModifyGoods load(String str)
	{
		try
		{
			SRequest_AddModifyGoods obj = new SRequest_AddModifyGoods();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_AddModifyGoods load(JSONObject json)
	{
		try
		{
			SRequest_AddModifyGoods obj = new SRequest_AddModifyGoods();
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
			if (goods != null)
				_json_.put("goods", goods.saveToJson());
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SRequest_AddModifyGoods> loadList(JSONArray ja)
	{
		try
		{
			List<SRequest_AddModifyGoods> list = new ArrayList<SRequest_AddModifyGoods>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SRequest_AddModifyGoods item = SRequest_AddModifyGoods.load(jo);
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

	public static JSONArray saveList(List<SRequest_AddModifyGoods> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SRequest_AddModifyGoods item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Request_AddModifyGoods _proto_)
	{
		if (_proto_.hasGoods())
			goods = SGoods.load(_proto_.getGoods());
	}

	public static SRequest_AddModifyGoods load(byte[] bytes)
	{
		try
		{
			SRequest_AddModifyGoods obj = new SRequest_AddModifyGoods();
			obj.parse(Request_AddModifyGoods.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_AddModifyGoods load(Request_AddModifyGoods proto)
	{
		try
		{
			SRequest_AddModifyGoods obj = new SRequest_AddModifyGoods();
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
	public Request_AddModifyGoods saveToProto()
	{
		Request_AddModifyGoods.Builder _builder_ = Request_AddModifyGoods.newBuilder();
		if (goods != null)
			_builder_.setGoods(goods.saveToProto());
		return _builder_.build();
	}
}
