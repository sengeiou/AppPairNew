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
import com.lys.protobuf.ProtocolLive.Response_LiveCopy;

public class SResponse_LiveCopy extends SPTData<Response_LiveCopy>
{
	private static final SResponse_LiveCopy DefaultInstance = new SResponse_LiveCopy();


	public static SResponse_LiveCopy create()
	{
		SResponse_LiveCopy obj = new SResponse_LiveCopy();
		return obj;
	}

	public SResponse_LiveCopy clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SResponse_LiveCopy _other_)
	{
	}

	@Override
	public void parse(JSONObject _json_)
	{
	}

	public static SResponse_LiveCopy load(String str)
	{
		try
		{
			SResponse_LiveCopy obj = new SResponse_LiveCopy();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_LiveCopy load(JSONObject json)
	{
		try
		{
			SResponse_LiveCopy obj = new SResponse_LiveCopy();
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

	public static List<SResponse_LiveCopy> loadList(JSONArray ja)
	{
		try
		{
			List<SResponse_LiveCopy> list = new ArrayList<SResponse_LiveCopy>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SResponse_LiveCopy item = SResponse_LiveCopy.load(jo);
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

	public static JSONArray saveList(List<SResponse_LiveCopy> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SResponse_LiveCopy item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Response_LiveCopy _proto_)
	{
	}

	public static SResponse_LiveCopy load(byte[] bytes)
	{
		try
		{
			SResponse_LiveCopy obj = new SResponse_LiveCopy();
			obj.parse(Response_LiveCopy.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_LiveCopy load(Response_LiveCopy proto)
	{
		try
		{
			SResponse_LiveCopy obj = new SResponse_LiveCopy();
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
	public Response_LiveCopy saveToProto()
	{
		Response_LiveCopy.Builder _builder_ = Response_LiveCopy.newBuilder();
		return _builder_.build();
	}
}
