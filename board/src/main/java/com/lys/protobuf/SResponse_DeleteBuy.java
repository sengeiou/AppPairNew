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
import com.lys.protobuf.ProtocolShop.Response_DeleteBuy;

public class SResponse_DeleteBuy extends SPTData<Response_DeleteBuy>
{
	private static final SResponse_DeleteBuy DefaultInstance = new SResponse_DeleteBuy();

	public List<SMatter> matters = new ArrayList<SMatter>();

	public static SResponse_DeleteBuy create()
	{
		SResponse_DeleteBuy obj = new SResponse_DeleteBuy();
		return obj;
	}

	public SResponse_DeleteBuy clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SResponse_DeleteBuy _other_)
	{
		this.matters = _other_.matters;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("matters"))
			matters = SMatter.loadList(_json_.getJSONArray("matters"));
	}

	public static SResponse_DeleteBuy load(String str)
	{
		try
		{
			SResponse_DeleteBuy obj = new SResponse_DeleteBuy();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_DeleteBuy load(JSONObject json)
	{
		try
		{
			SResponse_DeleteBuy obj = new SResponse_DeleteBuy();
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
			if (matters != null)
				_json_.put("matters", SMatter.saveList(matters));
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SResponse_DeleteBuy> loadList(JSONArray ja)
	{
		try
		{
			List<SResponse_DeleteBuy> list = new ArrayList<SResponse_DeleteBuy>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SResponse_DeleteBuy item = SResponse_DeleteBuy.load(jo);
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

	public static JSONArray saveList(List<SResponse_DeleteBuy> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SResponse_DeleteBuy item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Response_DeleteBuy _proto_)
	{
		for (int i = 0; i < _proto_.getMattersCount(); i++)
			matters.add(SMatter.load(_proto_.getMatters(i)));
	}

	public static SResponse_DeleteBuy load(byte[] bytes)
	{
		try
		{
			SResponse_DeleteBuy obj = new SResponse_DeleteBuy();
			obj.parse(Response_DeleteBuy.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_DeleteBuy load(Response_DeleteBuy proto)
	{
		try
		{
			SResponse_DeleteBuy obj = new SResponse_DeleteBuy();
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
	public Response_DeleteBuy saveToProto()
	{
		Response_DeleteBuy.Builder _builder_ = Response_DeleteBuy.newBuilder();
		if (matters != null)
			for (SMatter _value_ : matters)
				_builder_.addMatters(_value_.saveToProto());
		return _builder_.build();
	}
}
