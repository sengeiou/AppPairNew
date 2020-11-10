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
import com.lys.protobuf.ProtocolTeach.Response_TeachOverByStudent;

public class SResponse_TeachOverByStudent extends SPTData<Response_TeachOverByStudent>
{
	private static final SResponse_TeachOverByStudent DefaultInstance = new SResponse_TeachOverByStudent();


	public static SResponse_TeachOverByStudent create()
	{
		SResponse_TeachOverByStudent obj = new SResponse_TeachOverByStudent();
		return obj;
	}

	public SResponse_TeachOverByStudent clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SResponse_TeachOverByStudent _other_)
	{
	}

	@Override
	public void parse(JSONObject _json_)
	{
	}

	public static SResponse_TeachOverByStudent load(String str)
	{
		try
		{
			SResponse_TeachOverByStudent obj = new SResponse_TeachOverByStudent();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_TeachOverByStudent load(JSONObject json)
	{
		try
		{
			SResponse_TeachOverByStudent obj = new SResponse_TeachOverByStudent();
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

	public static List<SResponse_TeachOverByStudent> loadList(JSONArray ja)
	{
		try
		{
			List<SResponse_TeachOverByStudent> list = new ArrayList<SResponse_TeachOverByStudent>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SResponse_TeachOverByStudent item = SResponse_TeachOverByStudent.load(jo);
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

	public static JSONArray saveList(List<SResponse_TeachOverByStudent> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SResponse_TeachOverByStudent item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Response_TeachOverByStudent _proto_)
	{
	}

	public static SResponse_TeachOverByStudent load(byte[] bytes)
	{
		try
		{
			SResponse_TeachOverByStudent obj = new SResponse_TeachOverByStudent();
			obj.parse(Response_TeachOverByStudent.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_TeachOverByStudent load(Response_TeachOverByStudent proto)
	{
		try
		{
			SResponse_TeachOverByStudent obj = new SResponse_TeachOverByStudent();
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
	public Response_TeachOverByStudent saveToProto()
	{
		Response_TeachOverByStudent.Builder _builder_ = Response_TeachOverByStudent.newBuilder();
		return _builder_.build();
	}
}
