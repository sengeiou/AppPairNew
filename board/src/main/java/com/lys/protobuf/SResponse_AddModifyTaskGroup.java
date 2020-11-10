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
import com.lys.protobuf.ProtocolScore.Response_AddModifyTaskGroup;

public class SResponse_AddModifyTaskGroup extends SPTData<Response_AddModifyTaskGroup>
{
	private static final SResponse_AddModifyTaskGroup DefaultInstance = new SResponse_AddModifyTaskGroup();


	public static SResponse_AddModifyTaskGroup create()
	{
		SResponse_AddModifyTaskGroup obj = new SResponse_AddModifyTaskGroup();
		return obj;
	}

	public SResponse_AddModifyTaskGroup clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SResponse_AddModifyTaskGroup _other_)
	{
	}

	@Override
	public void parse(JSONObject _json_)
	{
	}

	public static SResponse_AddModifyTaskGroup load(String str)
	{
		try
		{
			SResponse_AddModifyTaskGroup obj = new SResponse_AddModifyTaskGroup();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_AddModifyTaskGroup load(JSONObject json)
	{
		try
		{
			SResponse_AddModifyTaskGroup obj = new SResponse_AddModifyTaskGroup();
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

	public static List<SResponse_AddModifyTaskGroup> loadList(JSONArray ja)
	{
		try
		{
			List<SResponse_AddModifyTaskGroup> list = new ArrayList<SResponse_AddModifyTaskGroup>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SResponse_AddModifyTaskGroup item = SResponse_AddModifyTaskGroup.load(jo);
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

	public static JSONArray saveList(List<SResponse_AddModifyTaskGroup> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SResponse_AddModifyTaskGroup item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Response_AddModifyTaskGroup _proto_)
	{
	}

	public static SResponse_AddModifyTaskGroup load(byte[] bytes)
	{
		try
		{
			SResponse_AddModifyTaskGroup obj = new SResponse_AddModifyTaskGroup();
			obj.parse(Response_AddModifyTaskGroup.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_AddModifyTaskGroup load(Response_AddModifyTaskGroup proto)
	{
		try
		{
			SResponse_AddModifyTaskGroup obj = new SResponse_AddModifyTaskGroup();
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
	public Response_AddModifyTaskGroup saveToProto()
	{
		Response_AddModifyTaskGroup.Builder _builder_ = Response_AddModifyTaskGroup.newBuilder();
		return _builder_.build();
	}
}
