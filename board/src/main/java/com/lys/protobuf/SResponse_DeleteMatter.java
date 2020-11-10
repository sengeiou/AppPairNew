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
import com.lys.protobuf.ProtocolShop.Response_DeleteMatter;

public class SResponse_DeleteMatter extends SPTData<Response_DeleteMatter>
{
	private static final SResponse_DeleteMatter DefaultInstance = new SResponse_DeleteMatter();


	public static SResponse_DeleteMatter create()
	{
		SResponse_DeleteMatter obj = new SResponse_DeleteMatter();
		return obj;
	}

	public SResponse_DeleteMatter clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SResponse_DeleteMatter _other_)
	{
	}

	@Override
	public void parse(JSONObject _json_)
	{
	}

	public static SResponse_DeleteMatter load(String str)
	{
		try
		{
			SResponse_DeleteMatter obj = new SResponse_DeleteMatter();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_DeleteMatter load(JSONObject json)
	{
		try
		{
			SResponse_DeleteMatter obj = new SResponse_DeleteMatter();
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

	public static List<SResponse_DeleteMatter> loadList(JSONArray ja)
	{
		try
		{
			List<SResponse_DeleteMatter> list = new ArrayList<SResponse_DeleteMatter>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SResponse_DeleteMatter item = SResponse_DeleteMatter.load(jo);
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

	public static JSONArray saveList(List<SResponse_DeleteMatter> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SResponse_DeleteMatter item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Response_DeleteMatter _proto_)
	{
	}

	public static SResponse_DeleteMatter load(byte[] bytes)
	{
		try
		{
			SResponse_DeleteMatter obj = new SResponse_DeleteMatter();
			obj.parse(Response_DeleteMatter.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_DeleteMatter load(Response_DeleteMatter proto)
	{
		try
		{
			SResponse_DeleteMatter obj = new SResponse_DeleteMatter();
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
	public Response_DeleteMatter saveToProto()
	{
		Response_DeleteMatter.Builder _builder_ = Response_DeleteMatter.newBuilder();
		return _builder_.build();
	}
}
