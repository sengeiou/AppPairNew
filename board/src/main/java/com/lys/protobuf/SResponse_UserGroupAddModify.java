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
import com.lys.protobuf.ProtocolPair.Response_UserGroupAddModify;

public class SResponse_UserGroupAddModify extends SPTData<Response_UserGroupAddModify>
{
	private static final SResponse_UserGroupAddModify DefaultInstance = new SResponse_UserGroupAddModify();


	public static SResponse_UserGroupAddModify create()
	{
		SResponse_UserGroupAddModify obj = new SResponse_UserGroupAddModify();
		return obj;
	}

	public SResponse_UserGroupAddModify clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SResponse_UserGroupAddModify _other_)
	{
	}

	@Override
	public void parse(JSONObject _json_)
	{
	}

	public static SResponse_UserGroupAddModify load(String str)
	{
		try
		{
			SResponse_UserGroupAddModify obj = new SResponse_UserGroupAddModify();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_UserGroupAddModify load(JSONObject json)
	{
		try
		{
			SResponse_UserGroupAddModify obj = new SResponse_UserGroupAddModify();
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

	public static List<SResponse_UserGroupAddModify> loadList(JSONArray ja)
	{
		try
		{
			List<SResponse_UserGroupAddModify> list = new ArrayList<SResponse_UserGroupAddModify>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SResponse_UserGroupAddModify item = SResponse_UserGroupAddModify.load(jo);
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

	public static JSONArray saveList(List<SResponse_UserGroupAddModify> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SResponse_UserGroupAddModify item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Response_UserGroupAddModify _proto_)
	{
	}

	public static SResponse_UserGroupAddModify load(byte[] bytes)
	{
		try
		{
			SResponse_UserGroupAddModify obj = new SResponse_UserGroupAddModify();
			obj.parse(Response_UserGroupAddModify.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_UserGroupAddModify load(Response_UserGroupAddModify proto)
	{
		try
		{
			SResponse_UserGroupAddModify obj = new SResponse_UserGroupAddModify();
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
	public Response_UserGroupAddModify saveToProto()
	{
		Response_UserGroupAddModify.Builder _builder_ = Response_UserGroupAddModify.newBuilder();
		return _builder_.build();
	}
}
