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
import com.lys.protobuf.ProtocolTeach.Response_TeachConfirmByStudent;

public class SResponse_TeachConfirmByStudent extends SPTData<Response_TeachConfirmByStudent>
{
	private static final SResponse_TeachConfirmByStudent DefaultInstance = new SResponse_TeachConfirmByStudent();


	public static SResponse_TeachConfirmByStudent create()
	{
		SResponse_TeachConfirmByStudent obj = new SResponse_TeachConfirmByStudent();
		return obj;
	}

	public SResponse_TeachConfirmByStudent clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SResponse_TeachConfirmByStudent _other_)
	{
	}

	@Override
	public void parse(JSONObject _json_)
	{
	}

	public static SResponse_TeachConfirmByStudent load(String str)
	{
		try
		{
			SResponse_TeachConfirmByStudent obj = new SResponse_TeachConfirmByStudent();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_TeachConfirmByStudent load(JSONObject json)
	{
		try
		{
			SResponse_TeachConfirmByStudent obj = new SResponse_TeachConfirmByStudent();
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

	public static List<SResponse_TeachConfirmByStudent> loadList(JSONArray ja)
	{
		try
		{
			List<SResponse_TeachConfirmByStudent> list = new ArrayList<SResponse_TeachConfirmByStudent>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SResponse_TeachConfirmByStudent item = SResponse_TeachConfirmByStudent.load(jo);
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

	public static JSONArray saveList(List<SResponse_TeachConfirmByStudent> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SResponse_TeachConfirmByStudent item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Response_TeachConfirmByStudent _proto_)
	{
	}

	public static SResponse_TeachConfirmByStudent load(byte[] bytes)
	{
		try
		{
			SResponse_TeachConfirmByStudent obj = new SResponse_TeachConfirmByStudent();
			obj.parse(Response_TeachConfirmByStudent.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_TeachConfirmByStudent load(Response_TeachConfirmByStudent proto)
	{
		try
		{
			SResponse_TeachConfirmByStudent obj = new SResponse_TeachConfirmByStudent();
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
	public Response_TeachConfirmByStudent saveToProto()
	{
		Response_TeachConfirmByStudent.Builder _builder_ = Response_TeachConfirmByStudent.newBuilder();
		return _builder_.build();
	}
}
