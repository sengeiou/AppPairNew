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
import com.lys.protobuf.ProtocolTeach.Response_TeachQuestionByStudent;

public class SResponse_TeachQuestionByStudent extends SPTData<Response_TeachQuestionByStudent>
{
	private static final SResponse_TeachQuestionByStudent DefaultInstance = new SResponse_TeachQuestionByStudent();


	public static SResponse_TeachQuestionByStudent create()
	{
		SResponse_TeachQuestionByStudent obj = new SResponse_TeachQuestionByStudent();
		return obj;
	}

	public SResponse_TeachQuestionByStudent clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SResponse_TeachQuestionByStudent _other_)
	{
	}

	@Override
	public void parse(JSONObject _json_)
	{
	}

	public static SResponse_TeachQuestionByStudent load(String str)
	{
		try
		{
			SResponse_TeachQuestionByStudent obj = new SResponse_TeachQuestionByStudent();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_TeachQuestionByStudent load(JSONObject json)
	{
		try
		{
			SResponse_TeachQuestionByStudent obj = new SResponse_TeachQuestionByStudent();
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

	public static List<SResponse_TeachQuestionByStudent> loadList(JSONArray ja)
	{
		try
		{
			List<SResponse_TeachQuestionByStudent> list = new ArrayList<SResponse_TeachQuestionByStudent>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SResponse_TeachQuestionByStudent item = SResponse_TeachQuestionByStudent.load(jo);
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

	public static JSONArray saveList(List<SResponse_TeachQuestionByStudent> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SResponse_TeachQuestionByStudent item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Response_TeachQuestionByStudent _proto_)
	{
	}

	public static SResponse_TeachQuestionByStudent load(byte[] bytes)
	{
		try
		{
			SResponse_TeachQuestionByStudent obj = new SResponse_TeachQuestionByStudent();
			obj.parse(Response_TeachQuestionByStudent.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_TeachQuestionByStudent load(Response_TeachQuestionByStudent proto)
	{
		try
		{
			SResponse_TeachQuestionByStudent obj = new SResponse_TeachQuestionByStudent();
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
	public Response_TeachQuestionByStudent saveToProto()
	{
		Response_TeachQuestionByStudent.Builder _builder_ = Response_TeachQuestionByStudent.newBuilder();
		return _builder_.build();
	}
}
