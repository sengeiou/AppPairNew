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
import com.lys.protobuf.ProtocolPair2.Response_FileCopy;

public class SResponse_FileCopy extends SPTData<Response_FileCopy>
{
	private static final SResponse_FileCopy DefaultInstance = new SResponse_FileCopy();


	public static SResponse_FileCopy create()
	{
		SResponse_FileCopy obj = new SResponse_FileCopy();
		return obj;
	}

	public SResponse_FileCopy clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SResponse_FileCopy _other_)
	{
	}

	@Override
	public void parse(JSONObject _json_)
	{
	}

	public static SResponse_FileCopy load(String str)
	{
		try
		{
			SResponse_FileCopy obj = new SResponse_FileCopy();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_FileCopy load(JSONObject json)
	{
		try
		{
			SResponse_FileCopy obj = new SResponse_FileCopy();
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

	public static List<SResponse_FileCopy> loadList(JSONArray ja)
	{
		try
		{
			List<SResponse_FileCopy> list = new ArrayList<SResponse_FileCopy>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SResponse_FileCopy item = SResponse_FileCopy.load(jo);
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

	public static JSONArray saveList(List<SResponse_FileCopy> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SResponse_FileCopy item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Response_FileCopy _proto_)
	{
	}

	public static SResponse_FileCopy load(byte[] bytes)
	{
		try
		{
			SResponse_FileCopy obj = new SResponse_FileCopy();
			obj.parse(Response_FileCopy.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_FileCopy load(Response_FileCopy proto)
	{
		try
		{
			SResponse_FileCopy obj = new SResponse_FileCopy();
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
	public Response_FileCopy saveToProto()
	{
		Response_FileCopy.Builder _builder_ = Response_FileCopy.newBuilder();
		return _builder_.build();
	}
}
