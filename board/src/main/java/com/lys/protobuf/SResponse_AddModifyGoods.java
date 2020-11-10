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
import com.lys.protobuf.ProtocolScore.Response_AddModifyGoods;

public class SResponse_AddModifyGoods extends SPTData<Response_AddModifyGoods>
{
	private static final SResponse_AddModifyGoods DefaultInstance = new SResponse_AddModifyGoods();


	public static SResponse_AddModifyGoods create()
	{
		SResponse_AddModifyGoods obj = new SResponse_AddModifyGoods();
		return obj;
	}

	public SResponse_AddModifyGoods clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SResponse_AddModifyGoods _other_)
	{
	}

	@Override
	public void parse(JSONObject _json_)
	{
	}

	public static SResponse_AddModifyGoods load(String str)
	{
		try
		{
			SResponse_AddModifyGoods obj = new SResponse_AddModifyGoods();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_AddModifyGoods load(JSONObject json)
	{
		try
		{
			SResponse_AddModifyGoods obj = new SResponse_AddModifyGoods();
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

	public static List<SResponse_AddModifyGoods> loadList(JSONArray ja)
	{
		try
		{
			List<SResponse_AddModifyGoods> list = new ArrayList<SResponse_AddModifyGoods>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SResponse_AddModifyGoods item = SResponse_AddModifyGoods.load(jo);
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

	public static JSONArray saveList(List<SResponse_AddModifyGoods> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SResponse_AddModifyGoods item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Response_AddModifyGoods _proto_)
	{
	}

	public static SResponse_AddModifyGoods load(byte[] bytes)
	{
		try
		{
			SResponse_AddModifyGoods obj = new SResponse_AddModifyGoods();
			obj.parse(Response_AddModifyGoods.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_AddModifyGoods load(Response_AddModifyGoods proto)
	{
		try
		{
			SResponse_AddModifyGoods obj = new SResponse_AddModifyGoods();
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
	public Response_AddModifyGoods saveToProto()
	{
		Response_AddModifyGoods.Builder _builder_ = Response_AddModifyGoods.newBuilder();
		return _builder_.build();
	}
}
