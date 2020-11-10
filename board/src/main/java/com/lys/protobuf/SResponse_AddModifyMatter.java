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
import com.lys.protobuf.ProtocolShop.Response_AddModifyMatter;

public class SResponse_AddModifyMatter extends SPTData<Response_AddModifyMatter>
{
	private static final SResponse_AddModifyMatter DefaultInstance = new SResponse_AddModifyMatter();


	public static SResponse_AddModifyMatter create()
	{
		SResponse_AddModifyMatter obj = new SResponse_AddModifyMatter();
		return obj;
	}

	public SResponse_AddModifyMatter clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SResponse_AddModifyMatter _other_)
	{
	}

	@Override
	public void parse(JSONObject _json_)
	{
	}

	public static SResponse_AddModifyMatter load(String str)
	{
		try
		{
			SResponse_AddModifyMatter obj = new SResponse_AddModifyMatter();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_AddModifyMatter load(JSONObject json)
	{
		try
		{
			SResponse_AddModifyMatter obj = new SResponse_AddModifyMatter();
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

	public static List<SResponse_AddModifyMatter> loadList(JSONArray ja)
	{
		try
		{
			List<SResponse_AddModifyMatter> list = new ArrayList<SResponse_AddModifyMatter>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SResponse_AddModifyMatter item = SResponse_AddModifyMatter.load(jo);
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

	public static JSONArray saveList(List<SResponse_AddModifyMatter> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SResponse_AddModifyMatter item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Response_AddModifyMatter _proto_)
	{
	}

	public static SResponse_AddModifyMatter load(byte[] bytes)
	{
		try
		{
			SResponse_AddModifyMatter obj = new SResponse_AddModifyMatter();
			obj.parse(Response_AddModifyMatter.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_AddModifyMatter load(Response_AddModifyMatter proto)
	{
		try
		{
			SResponse_AddModifyMatter obj = new SResponse_AddModifyMatter();
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
	public Response_AddModifyMatter saveToProto()
	{
		Response_AddModifyMatter.Builder _builder_ = Response_AddModifyMatter.newBuilder();
		return _builder_.build();
	}
}
