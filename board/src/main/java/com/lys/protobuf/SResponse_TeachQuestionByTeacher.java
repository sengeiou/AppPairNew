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
import com.lys.protobuf.ProtocolTeach.Response_TeachQuestionByTeacher;

public class SResponse_TeachQuestionByTeacher extends SPTData<Response_TeachQuestionByTeacher>
{
	private static final SResponse_TeachQuestionByTeacher DefaultInstance = new SResponse_TeachQuestionByTeacher();


	public static SResponse_TeachQuestionByTeacher create()
	{
		SResponse_TeachQuestionByTeacher obj = new SResponse_TeachQuestionByTeacher();
		return obj;
	}

	public SResponse_TeachQuestionByTeacher clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SResponse_TeachQuestionByTeacher _other_)
	{
	}

	@Override
	public void parse(JSONObject _json_)
	{
	}

	public static SResponse_TeachQuestionByTeacher load(String str)
	{
		try
		{
			SResponse_TeachQuestionByTeacher obj = new SResponse_TeachQuestionByTeacher();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_TeachQuestionByTeacher load(JSONObject json)
	{
		try
		{
			SResponse_TeachQuestionByTeacher obj = new SResponse_TeachQuestionByTeacher();
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

	public static List<SResponse_TeachQuestionByTeacher> loadList(JSONArray ja)
	{
		try
		{
			List<SResponse_TeachQuestionByTeacher> list = new ArrayList<SResponse_TeachQuestionByTeacher>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SResponse_TeachQuestionByTeacher item = SResponse_TeachQuestionByTeacher.load(jo);
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

	public static JSONArray saveList(List<SResponse_TeachQuestionByTeacher> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SResponse_TeachQuestionByTeacher item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Response_TeachQuestionByTeacher _proto_)
	{
	}

	public static SResponse_TeachQuestionByTeacher load(byte[] bytes)
	{
		try
		{
			SResponse_TeachQuestionByTeacher obj = new SResponse_TeachQuestionByTeacher();
			obj.parse(Response_TeachQuestionByTeacher.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_TeachQuestionByTeacher load(Response_TeachQuestionByTeacher proto)
	{
		try
		{
			SResponse_TeachQuestionByTeacher obj = new SResponse_TeachQuestionByTeacher();
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
	public Response_TeachQuestionByTeacher saveToProto()
	{
		Response_TeachQuestionByTeacher.Builder _builder_ = Response_TeachQuestionByTeacher.newBuilder();
		return _builder_.build();
	}
}
