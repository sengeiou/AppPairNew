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
import com.lys.protobuf.ProtocolShop.Response_SwapMatter;

public class SResponse_SwapMatter extends SPTData<Response_SwapMatter>
{
	private static final SResponse_SwapMatter DefaultInstance = new SResponse_SwapMatter();


	public static SResponse_SwapMatter create()
	{
		SResponse_SwapMatter obj = new SResponse_SwapMatter();
		return obj;
	}

	public SResponse_SwapMatter clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SResponse_SwapMatter _other_)
	{
	}

	@Override
	public void parse(JSONObject _json_)
	{
	}

	public static SResponse_SwapMatter load(String str)
	{
		try
		{
			SResponse_SwapMatter obj = new SResponse_SwapMatter();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_SwapMatter load(JSONObject json)
	{
		try
		{
			SResponse_SwapMatter obj = new SResponse_SwapMatter();
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

	public static List<SResponse_SwapMatter> loadList(JSONArray ja)
	{
		try
		{
			List<SResponse_SwapMatter> list = new ArrayList<SResponse_SwapMatter>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SResponse_SwapMatter item = SResponse_SwapMatter.load(jo);
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

	public static JSONArray saveList(List<SResponse_SwapMatter> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SResponse_SwapMatter item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Response_SwapMatter _proto_)
	{
	}

	public static SResponse_SwapMatter load(byte[] bytes)
	{
		try
		{
			SResponse_SwapMatter obj = new SResponse_SwapMatter();
			obj.parse(Response_SwapMatter.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_SwapMatter load(Response_SwapMatter proto)
	{
		try
		{
			SResponse_SwapMatter obj = new SResponse_SwapMatter();
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
	public Response_SwapMatter saveToProto()
	{
		Response_SwapMatter.Builder _builder_ = Response_SwapMatter.newBuilder();
		return _builder_.build();
	}
}
