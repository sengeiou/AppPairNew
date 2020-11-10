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
import com.lys.protobuf.ProtocolScore.Request_SwapGoods;

// ---------------------- xxxxxxxx --------------------------
public class SRequest_SwapGoods extends SPTData<Request_SwapGoods>
{
	private static final SRequest_SwapGoods DefaultInstance = new SRequest_SwapGoods();

	public SGoods goods1 = null;
	public SGoods goods2 = null;

	public static SRequest_SwapGoods create(SGoods goods1, SGoods goods2)
	{
		SRequest_SwapGoods obj = new SRequest_SwapGoods();
		obj.goods1 = goods1;
		obj.goods2 = goods2;
		return obj;
	}

	public SRequest_SwapGoods clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SRequest_SwapGoods _other_)
	{
		this.goods1 = _other_.goods1;
		this.goods2 = _other_.goods2;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("goods1"))
			goods1 = SGoods.load(_json_.getJSONObject("goods1"));
		if (_json_.containsKey("goods2"))
			goods2 = SGoods.load(_json_.getJSONObject("goods2"));
	}

	public static SRequest_SwapGoods load(String str)
	{
		try
		{
			SRequest_SwapGoods obj = new SRequest_SwapGoods();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_SwapGoods load(JSONObject json)
	{
		try
		{
			SRequest_SwapGoods obj = new SRequest_SwapGoods();
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
			if (goods1 != null)
				_json_.put("goods1", goods1.saveToJson());
			if (goods2 != null)
				_json_.put("goods2", goods2.saveToJson());
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SRequest_SwapGoods> loadList(JSONArray ja)
	{
		try
		{
			List<SRequest_SwapGoods> list = new ArrayList<SRequest_SwapGoods>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SRequest_SwapGoods item = SRequest_SwapGoods.load(jo);
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

	public static JSONArray saveList(List<SRequest_SwapGoods> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SRequest_SwapGoods item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Request_SwapGoods _proto_)
	{
		if (_proto_.hasGoods1())
			goods1 = SGoods.load(_proto_.getGoods1());
		if (_proto_.hasGoods2())
			goods2 = SGoods.load(_proto_.getGoods2());
	}

	public static SRequest_SwapGoods load(byte[] bytes)
	{
		try
		{
			SRequest_SwapGoods obj = new SRequest_SwapGoods();
			obj.parse(Request_SwapGoods.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_SwapGoods load(Request_SwapGoods proto)
	{
		try
		{
			SRequest_SwapGoods obj = new SRequest_SwapGoods();
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
	public Request_SwapGoods saveToProto()
	{
		Request_SwapGoods.Builder _builder_ = Request_SwapGoods.newBuilder();
		if (goods1 != null)
			_builder_.setGoods1(goods1.saveToProto());
		if (goods2 != null)
			_builder_.setGoods2(goods2.saveToProto());
		return _builder_.build();
	}
}
