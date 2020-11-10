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
import com.lys.protobuf.ProtocolScore.Response_DeleteTaskGroup;

public class SResponse_DeleteTaskGroup extends SPTData<Response_DeleteTaskGroup>
{
	private static final SResponse_DeleteTaskGroup DefaultInstance = new SResponse_DeleteTaskGroup();


	public static SResponse_DeleteTaskGroup create()
	{
		SResponse_DeleteTaskGroup obj = new SResponse_DeleteTaskGroup();
		return obj;
	}

	public SResponse_DeleteTaskGroup clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SResponse_DeleteTaskGroup _other_)
	{
	}

	@Override
	public void parse(JSONObject _json_)
	{
	}

	public static SResponse_DeleteTaskGroup load(String str)
	{
		try
		{
			SResponse_DeleteTaskGroup obj = new SResponse_DeleteTaskGroup();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_DeleteTaskGroup load(JSONObject json)
	{
		try
		{
			SResponse_DeleteTaskGroup obj = new SResponse_DeleteTaskGroup();
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

	public static List<SResponse_DeleteTaskGroup> loadList(JSONArray ja)
	{
		try
		{
			List<SResponse_DeleteTaskGroup> list = new ArrayList<SResponse_DeleteTaskGroup>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SResponse_DeleteTaskGroup item = SResponse_DeleteTaskGroup.load(jo);
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

	public static JSONArray saveList(List<SResponse_DeleteTaskGroup> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SResponse_DeleteTaskGroup item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Response_DeleteTaskGroup _proto_)
	{
	}

	public static SResponse_DeleteTaskGroup load(byte[] bytes)
	{
		try
		{
			SResponse_DeleteTaskGroup obj = new SResponse_DeleteTaskGroup();
			obj.parse(Response_DeleteTaskGroup.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_DeleteTaskGroup load(Response_DeleteTaskGroup proto)
	{
		try
		{
			SResponse_DeleteTaskGroup obj = new SResponse_DeleteTaskGroup();
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
	public Response_DeleteTaskGroup saveToProto()
	{
		Response_DeleteTaskGroup.Builder _builder_ = Response_DeleteTaskGroup.newBuilder();
		return _builder_.build();
	}
}
