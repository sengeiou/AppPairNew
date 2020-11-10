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
import com.lys.protobuf.ProtocolScore.Response_GetGoodsList;

public class SResponse_GetGoodsList extends SPTData<Response_GetGoodsList>
{
	private static final SResponse_GetGoodsList DefaultInstance = new SResponse_GetGoodsList();

	public List<SGoods> goodsList = new ArrayList<SGoods>();

	public static SResponse_GetGoodsList create()
	{
		SResponse_GetGoodsList obj = new SResponse_GetGoodsList();
		return obj;
	}

	public SResponse_GetGoodsList clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SResponse_GetGoodsList _other_)
	{
		this.goodsList = _other_.goodsList;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("goodsList"))
			goodsList = SGoods.loadList(_json_.getJSONArray("goodsList"));
	}

	public static SResponse_GetGoodsList load(String str)
	{
		try
		{
			SResponse_GetGoodsList obj = new SResponse_GetGoodsList();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_GetGoodsList load(JSONObject json)
	{
		try
		{
			SResponse_GetGoodsList obj = new SResponse_GetGoodsList();
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
			if (goodsList != null)
				_json_.put("goodsList", SGoods.saveList(goodsList));
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SResponse_GetGoodsList> loadList(JSONArray ja)
	{
		try
		{
			List<SResponse_GetGoodsList> list = new ArrayList<SResponse_GetGoodsList>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SResponse_GetGoodsList item = SResponse_GetGoodsList.load(jo);
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

	public static JSONArray saveList(List<SResponse_GetGoodsList> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SResponse_GetGoodsList item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Response_GetGoodsList _proto_)
	{
		for (int i = 0; i < _proto_.getGoodsListCount(); i++)
			goodsList.add(SGoods.load(_proto_.getGoodsList(i)));
	}

	public static SResponse_GetGoodsList load(byte[] bytes)
	{
		try
		{
			SResponse_GetGoodsList obj = new SResponse_GetGoodsList();
			obj.parse(Response_GetGoodsList.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_GetGoodsList load(Response_GetGoodsList proto)
	{
		try
		{
			SResponse_GetGoodsList obj = new SResponse_GetGoodsList();
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
	public Response_GetGoodsList saveToProto()
	{
		Response_GetGoodsList.Builder _builder_ = Response_GetGoodsList.newBuilder();
		if (goodsList != null)
			for (SGoods _value_ : goodsList)
				_builder_.addGoodsList(_value_.saveToProto());
		return _builder_.build();
	}
}
