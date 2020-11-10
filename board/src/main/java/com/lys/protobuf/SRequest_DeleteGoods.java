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
import com.lys.protobuf.ProtocolScore.Request_DeleteGoods;

// ---------------------- xxxxxxxx --------------------------
public class SRequest_DeleteGoods extends SPTData<Request_DeleteGoods>
{
	private static final SRequest_DeleteGoods DefaultInstance = new SRequest_DeleteGoods();

	public String goodsId = null;

	public static SRequest_DeleteGoods create(String goodsId)
	{
		SRequest_DeleteGoods obj = new SRequest_DeleteGoods();
		obj.goodsId = goodsId;
		return obj;
	}

	public SRequest_DeleteGoods clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SRequest_DeleteGoods _other_)
	{
		this.goodsId = _other_.goodsId;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("goodsId"))
			goodsId = _json_.getString("goodsId");
	}

	public static SRequest_DeleteGoods load(String str)
	{
		try
		{
			SRequest_DeleteGoods obj = new SRequest_DeleteGoods();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_DeleteGoods load(JSONObject json)
	{
		try
		{
			SRequest_DeleteGoods obj = new SRequest_DeleteGoods();
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
			if (goodsId != null)
				_json_.put("goodsId", goodsId);
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SRequest_DeleteGoods> loadList(JSONArray ja)
	{
		try
		{
			List<SRequest_DeleteGoods> list = new ArrayList<SRequest_DeleteGoods>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SRequest_DeleteGoods item = SRequest_DeleteGoods.load(jo);
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

	public static JSONArray saveList(List<SRequest_DeleteGoods> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SRequest_DeleteGoods item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Request_DeleteGoods _proto_)
	{
		if (_proto_.hasGoodsId())
			goodsId = _proto_.getGoodsId();
	}

	public static SRequest_DeleteGoods load(byte[] bytes)
	{
		try
		{
			SRequest_DeleteGoods obj = new SRequest_DeleteGoods();
			obj.parse(Request_DeleteGoods.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_DeleteGoods load(Request_DeleteGoods proto)
	{
		try
		{
			SRequest_DeleteGoods obj = new SRequest_DeleteGoods();
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
	public Request_DeleteGoods saveToProto()
	{
		Request_DeleteGoods.Builder _builder_ = Request_DeleteGoods.newBuilder();
		if (goodsId != null && !goodsId.equals(Request_DeleteGoods.getDefaultInstance().getGoodsId()))
			_builder_.setGoodsId(goodsId);
		return _builder_.build();
	}
}
