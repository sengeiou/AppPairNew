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
import com.lys.protobuf.ProtocolScore.Response_SwapGoods;

public class SResponse_SwapGoods extends SPTData<Response_SwapGoods>
{
	private static final SResponse_SwapGoods DefaultInstance = new SResponse_SwapGoods();


	public static SResponse_SwapGoods create()
	{
		SResponse_SwapGoods obj = new SResponse_SwapGoods();
		return obj;
	}

	public SResponse_SwapGoods clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SResponse_SwapGoods _other_)
	{
	}

	@Override
	public void parse(JSONObject _json_)
	{
	}

	public static SResponse_SwapGoods load(String str)
	{
		try
		{
			SResponse_SwapGoods obj = new SResponse_SwapGoods();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_SwapGoods load(JSONObject json)
	{
		try
		{
			SResponse_SwapGoods obj = new SResponse_SwapGoods();
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

	public static List<SResponse_SwapGoods> loadList(JSONArray ja)
	{
		try
		{
			List<SResponse_SwapGoods> list = new ArrayList<SResponse_SwapGoods>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SResponse_SwapGoods item = SResponse_SwapGoods.load(jo);
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

	public static JSONArray saveList(List<SResponse_SwapGoods> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SResponse_SwapGoods item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Response_SwapGoods _proto_)
	{
	}

	public static SResponse_SwapGoods load(byte[] bytes)
	{
		try
		{
			SResponse_SwapGoods obj = new SResponse_SwapGoods();
			obj.parse(Response_SwapGoods.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_SwapGoods load(Response_SwapGoods proto)
	{
		try
		{
			SResponse_SwapGoods obj = new SResponse_SwapGoods();
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
	public Response_SwapGoods saveToProto()
	{
		Response_SwapGoods.Builder _builder_ = Response_SwapGoods.newBuilder();
		return _builder_.build();
	}
}
