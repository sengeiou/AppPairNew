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
import com.lys.protobuf.ProtocolPair.Response_DeleteFriend;

public class SResponse_DeleteFriend extends SPTData<Response_DeleteFriend>
{
	private static final SResponse_DeleteFriend DefaultInstance = new SResponse_DeleteFriend();


	public static SResponse_DeleteFriend create()
	{
		SResponse_DeleteFriend obj = new SResponse_DeleteFriend();
		return obj;
	}

	public SResponse_DeleteFriend clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SResponse_DeleteFriend _other_)
	{
	}

	@Override
	public void parse(JSONObject _json_)
	{
	}

	public static SResponse_DeleteFriend load(String str)
	{
		try
		{
			SResponse_DeleteFriend obj = new SResponse_DeleteFriend();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_DeleteFriend load(JSONObject json)
	{
		try
		{
			SResponse_DeleteFriend obj = new SResponse_DeleteFriend();
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

	public static List<SResponse_DeleteFriend> loadList(JSONArray ja)
	{
		try
		{
			List<SResponse_DeleteFriend> list = new ArrayList<SResponse_DeleteFriend>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SResponse_DeleteFriend item = SResponse_DeleteFriend.load(jo);
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

	public static JSONArray saveList(List<SResponse_DeleteFriend> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SResponse_DeleteFriend item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Response_DeleteFriend _proto_)
	{
	}

	public static SResponse_DeleteFriend load(byte[] bytes)
	{
		try
		{
			SResponse_DeleteFriend obj = new SResponse_DeleteFriend();
			obj.parse(Response_DeleteFriend.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_DeleteFriend load(Response_DeleteFriend proto)
	{
		try
		{
			SResponse_DeleteFriend obj = new SResponse_DeleteFriend();
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
	public Response_DeleteFriend saveToProto()
	{
		Response_DeleteFriend.Builder _builder_ = Response_DeleteFriend.newBuilder();
		return _builder_.build();
	}
}
