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
import com.lys.protobuf.ProtocolPair.Response_UserGroupDelete;

public class SResponse_UserGroupDelete extends SPTData<Response_UserGroupDelete>
{
	private static final SResponse_UserGroupDelete DefaultInstance = new SResponse_UserGroupDelete();


	public static SResponse_UserGroupDelete create()
	{
		SResponse_UserGroupDelete obj = new SResponse_UserGroupDelete();
		return obj;
	}

	public SResponse_UserGroupDelete clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SResponse_UserGroupDelete _other_)
	{
	}

	@Override
	public void parse(JSONObject _json_)
	{
	}

	public static SResponse_UserGroupDelete load(String str)
	{
		try
		{
			SResponse_UserGroupDelete obj = new SResponse_UserGroupDelete();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_UserGroupDelete load(JSONObject json)
	{
		try
		{
			SResponse_UserGroupDelete obj = new SResponse_UserGroupDelete();
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

	public static List<SResponse_UserGroupDelete> loadList(JSONArray ja)
	{
		try
		{
			List<SResponse_UserGroupDelete> list = new ArrayList<SResponse_UserGroupDelete>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SResponse_UserGroupDelete item = SResponse_UserGroupDelete.load(jo);
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

	public static JSONArray saveList(List<SResponse_UserGroupDelete> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SResponse_UserGroupDelete item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Response_UserGroupDelete _proto_)
	{
	}

	public static SResponse_UserGroupDelete load(byte[] bytes)
	{
		try
		{
			SResponse_UserGroupDelete obj = new SResponse_UserGroupDelete();
			obj.parse(Response_UserGroupDelete.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_UserGroupDelete load(Response_UserGroupDelete proto)
	{
		try
		{
			SResponse_UserGroupDelete obj = new SResponse_UserGroupDelete();
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
	public Response_UserGroupDelete saveToProto()
	{
		Response_UserGroupDelete.Builder _builder_ = Response_UserGroupDelete.newBuilder();
		return _builder_.build();
	}
}
