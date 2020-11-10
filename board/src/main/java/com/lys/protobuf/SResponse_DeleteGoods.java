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
import com.lys.protobuf.ProtocolScore.Response_DeleteGoods;

public class SResponse_DeleteGoods extends SPTData<Response_DeleteGoods>
{
	private static final SResponse_DeleteGoods DefaultInstance = new SResponse_DeleteGoods();


	public static SResponse_DeleteGoods create()
	{
		SResponse_DeleteGoods obj = new SResponse_DeleteGoods();
		return obj;
	}

	public SResponse_DeleteGoods clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SResponse_DeleteGoods _other_)
	{
	}

	@Override
	public void parse(JSONObject _json_)
	{
	}

	public static SResponse_DeleteGoods load(String str)
	{
		try
		{
			SResponse_DeleteGoods obj = new SResponse_DeleteGoods();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_DeleteGoods load(JSONObject json)
	{
		try
		{
			SResponse_DeleteGoods obj = new SResponse_DeleteGoods();
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

	public static List<SResponse_DeleteGoods> loadList(JSONArray ja)
	{
		try
		{
			List<SResponse_DeleteGoods> list = new ArrayList<SResponse_DeleteGoods>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SResponse_DeleteGoods item = SResponse_DeleteGoods.load(jo);
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

	public static JSONArray saveList(List<SResponse_DeleteGoods> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SResponse_DeleteGoods item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Response_DeleteGoods _proto_)
	{
	}

	public static SResponse_DeleteGoods load(byte[] bytes)
	{
		try
		{
			SResponse_DeleteGoods obj = new SResponse_DeleteGoods();
			obj.parse(Response_DeleteGoods.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_DeleteGoods load(Response_DeleteGoods proto)
	{
		try
		{
			SResponse_DeleteGoods obj = new SResponse_DeleteGoods();
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
	public Response_DeleteGoods saveToProto()
	{
		Response_DeleteGoods.Builder _builder_ = Response_DeleteGoods.newBuilder();
		return _builder_.build();
	}
}
