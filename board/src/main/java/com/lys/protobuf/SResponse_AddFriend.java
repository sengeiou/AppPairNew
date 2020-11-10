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
import com.lys.protobuf.ProtocolPair.Response_AddFriend;

public class SResponse_AddFriend extends SPTData<Response_AddFriend>
{
	private static final SResponse_AddFriend DefaultInstance = new SResponse_AddFriend();


	public static SResponse_AddFriend create()
	{
		SResponse_AddFriend obj = new SResponse_AddFriend();
		return obj;
	}

	public SResponse_AddFriend clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SResponse_AddFriend _other_)
	{
	}

	@Override
	public void parse(JSONObject _json_)
	{
	}

	public static SResponse_AddFriend load(String str)
	{
		try
		{
			SResponse_AddFriend obj = new SResponse_AddFriend();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_AddFriend load(JSONObject json)
	{
		try
		{
			SResponse_AddFriend obj = new SResponse_AddFriend();
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

	public static List<SResponse_AddFriend> loadList(JSONArray ja)
	{
		try
		{
			List<SResponse_AddFriend> list = new ArrayList<SResponse_AddFriend>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SResponse_AddFriend item = SResponse_AddFriend.load(jo);
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

	public static JSONArray saveList(List<SResponse_AddFriend> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SResponse_AddFriend item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Response_AddFriend _proto_)
	{
	}

	public static SResponse_AddFriend load(byte[] bytes)
	{
		try
		{
			SResponse_AddFriend obj = new SResponse_AddFriend();
			obj.parse(Response_AddFriend.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_AddFriend load(Response_AddFriend proto)
	{
		try
		{
			SResponse_AddFriend obj = new SResponse_AddFriend();
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
	public Response_AddFriend saveToProto()
	{
		Response_AddFriend.Builder _builder_ = Response_AddFriend.newBuilder();
		return _builder_.build();
	}
}
