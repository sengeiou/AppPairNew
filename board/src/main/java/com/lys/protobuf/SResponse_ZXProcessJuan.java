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
import com.lys.protobuf.ProtocolZhixue.Response_ZXProcessJuan;

public class SResponse_ZXProcessJuan extends SPTData<Response_ZXProcessJuan>
{
	private static final SResponse_ZXProcessJuan DefaultInstance = new SResponse_ZXProcessJuan();


	public static SResponse_ZXProcessJuan create()
	{
		SResponse_ZXProcessJuan obj = new SResponse_ZXProcessJuan();
		return obj;
	}

	public SResponse_ZXProcessJuan clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SResponse_ZXProcessJuan _other_)
	{
	}

	@Override
	public void parse(JSONObject _json_)
	{
	}

	public static SResponse_ZXProcessJuan load(String str)
	{
		try
		{
			SResponse_ZXProcessJuan obj = new SResponse_ZXProcessJuan();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_ZXProcessJuan load(JSONObject json)
	{
		try
		{
			SResponse_ZXProcessJuan obj = new SResponse_ZXProcessJuan();
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

	public static List<SResponse_ZXProcessJuan> loadList(JSONArray ja)
	{
		try
		{
			List<SResponse_ZXProcessJuan> list = new ArrayList<SResponse_ZXProcessJuan>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SResponse_ZXProcessJuan item = SResponse_ZXProcessJuan.load(jo);
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

	public static JSONArray saveList(List<SResponse_ZXProcessJuan> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SResponse_ZXProcessJuan item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Response_ZXProcessJuan _proto_)
	{
	}

	public static SResponse_ZXProcessJuan load(byte[] bytes)
	{
		try
		{
			SResponse_ZXProcessJuan obj = new SResponse_ZXProcessJuan();
			obj.parse(Response_ZXProcessJuan.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_ZXProcessJuan load(Response_ZXProcessJuan proto)
	{
		try
		{
			SResponse_ZXProcessJuan obj = new SResponse_ZXProcessJuan();
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
	public Response_ZXProcessJuan saveToProto()
	{
		Response_ZXProcessJuan.Builder _builder_ = Response_ZXProcessJuan.newBuilder();
		return _builder_.build();
	}
}
