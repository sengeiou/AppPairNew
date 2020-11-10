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
import com.lys.protobuf.ProtocolScore.Response_SwapTaskGroup;

public class SResponse_SwapTaskGroup extends SPTData<Response_SwapTaskGroup>
{
	private static final SResponse_SwapTaskGroup DefaultInstance = new SResponse_SwapTaskGroup();


	public static SResponse_SwapTaskGroup create()
	{
		SResponse_SwapTaskGroup obj = new SResponse_SwapTaskGroup();
		return obj;
	}

	public SResponse_SwapTaskGroup clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SResponse_SwapTaskGroup _other_)
	{
	}

	@Override
	public void parse(JSONObject _json_)
	{
	}

	public static SResponse_SwapTaskGroup load(String str)
	{
		try
		{
			SResponse_SwapTaskGroup obj = new SResponse_SwapTaskGroup();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_SwapTaskGroup load(JSONObject json)
	{
		try
		{
			SResponse_SwapTaskGroup obj = new SResponse_SwapTaskGroup();
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

	public static List<SResponse_SwapTaskGroup> loadList(JSONArray ja)
	{
		try
		{
			List<SResponse_SwapTaskGroup> list = new ArrayList<SResponse_SwapTaskGroup>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SResponse_SwapTaskGroup item = SResponse_SwapTaskGroup.load(jo);
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

	public static JSONArray saveList(List<SResponse_SwapTaskGroup> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SResponse_SwapTaskGroup item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Response_SwapTaskGroup _proto_)
	{
	}

	public static SResponse_SwapTaskGroup load(byte[] bytes)
	{
		try
		{
			SResponse_SwapTaskGroup obj = new SResponse_SwapTaskGroup();
			obj.parse(Response_SwapTaskGroup.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_SwapTaskGroup load(Response_SwapTaskGroup proto)
	{
		try
		{
			SResponse_SwapTaskGroup obj = new SResponse_SwapTaskGroup();
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
	public Response_SwapTaskGroup saveToProto()
	{
		Response_SwapTaskGroup.Builder _builder_ = Response_SwapTaskGroup.newBuilder();
		return _builder_.build();
	}
}
