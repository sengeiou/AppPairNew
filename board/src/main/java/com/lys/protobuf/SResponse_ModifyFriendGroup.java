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
import com.lys.protobuf.ProtocolPair.Response_ModifyFriendGroup;

public class SResponse_ModifyFriendGroup extends SPTData<Response_ModifyFriendGroup>
{
	private static final SResponse_ModifyFriendGroup DefaultInstance = new SResponse_ModifyFriendGroup();


	public static SResponse_ModifyFriendGroup create()
	{
		SResponse_ModifyFriendGroup obj = new SResponse_ModifyFriendGroup();
		return obj;
	}

	public SResponse_ModifyFriendGroup clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SResponse_ModifyFriendGroup _other_)
	{
	}

	@Override
	public void parse(JSONObject _json_)
	{
	}

	public static SResponse_ModifyFriendGroup load(String str)
	{
		try
		{
			SResponse_ModifyFriendGroup obj = new SResponse_ModifyFriendGroup();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_ModifyFriendGroup load(JSONObject json)
	{
		try
		{
			SResponse_ModifyFriendGroup obj = new SResponse_ModifyFriendGroup();
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

	public static List<SResponse_ModifyFriendGroup> loadList(JSONArray ja)
	{
		try
		{
			List<SResponse_ModifyFriendGroup> list = new ArrayList<SResponse_ModifyFriendGroup>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SResponse_ModifyFriendGroup item = SResponse_ModifyFriendGroup.load(jo);
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

	public static JSONArray saveList(List<SResponse_ModifyFriendGroup> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SResponse_ModifyFriendGroup item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Response_ModifyFriendGroup _proto_)
	{
	}

	public static SResponse_ModifyFriendGroup load(byte[] bytes)
	{
		try
		{
			SResponse_ModifyFriendGroup obj = new SResponse_ModifyFriendGroup();
			obj.parse(Response_ModifyFriendGroup.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_ModifyFriendGroup load(Response_ModifyFriendGroup proto)
	{
		try
		{
			SResponse_ModifyFriendGroup obj = new SResponse_ModifyFriendGroup();
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
	public Response_ModifyFriendGroup saveToProto()
	{
		Response_ModifyFriendGroup.Builder _builder_ = Response_ModifyFriendGroup.newBuilder();
		return _builder_.build();
	}
}
