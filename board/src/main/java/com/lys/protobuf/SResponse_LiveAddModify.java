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
import com.lys.protobuf.ProtocolLive.Response_LiveAddModify;

public class SResponse_LiveAddModify extends SPTData<Response_LiveAddModify>
{
	private static final SResponse_LiveAddModify DefaultInstance = new SResponse_LiveAddModify();


	public static SResponse_LiveAddModify create()
	{
		SResponse_LiveAddModify obj = new SResponse_LiveAddModify();
		return obj;
	}

	public SResponse_LiveAddModify clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SResponse_LiveAddModify _other_)
	{
	}

	@Override
	public void parse(JSONObject _json_)
	{
	}

	public static SResponse_LiveAddModify load(String str)
	{
		try
		{
			SResponse_LiveAddModify obj = new SResponse_LiveAddModify();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_LiveAddModify load(JSONObject json)
	{
		try
		{
			SResponse_LiveAddModify obj = new SResponse_LiveAddModify();
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

	public static List<SResponse_LiveAddModify> loadList(JSONArray ja)
	{
		try
		{
			List<SResponse_LiveAddModify> list = new ArrayList<SResponse_LiveAddModify>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SResponse_LiveAddModify item = SResponse_LiveAddModify.load(jo);
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

	public static JSONArray saveList(List<SResponse_LiveAddModify> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SResponse_LiveAddModify item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Response_LiveAddModify _proto_)
	{
	}

	public static SResponse_LiveAddModify load(byte[] bytes)
	{
		try
		{
			SResponse_LiveAddModify obj = new SResponse_LiveAddModify();
			obj.parse(Response_LiveAddModify.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_LiveAddModify load(Response_LiveAddModify proto)
	{
		try
		{
			SResponse_LiveAddModify obj = new SResponse_LiveAddModify();
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
	public Response_LiveAddModify saveToProto()
	{
		Response_LiveAddModify.Builder _builder_ = Response_LiveAddModify.newBuilder();
		return _builder_.build();
	}
}
