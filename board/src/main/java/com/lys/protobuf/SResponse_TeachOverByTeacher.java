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
import com.lys.protobuf.ProtocolTeach.Response_TeachOverByTeacher;

public class SResponse_TeachOverByTeacher extends SPTData<Response_TeachOverByTeacher>
{
	private static final SResponse_TeachOverByTeacher DefaultInstance = new SResponse_TeachOverByTeacher();


	public static SResponse_TeachOverByTeacher create()
	{
		SResponse_TeachOverByTeacher obj = new SResponse_TeachOverByTeacher();
		return obj;
	}

	public SResponse_TeachOverByTeacher clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SResponse_TeachOverByTeacher _other_)
	{
	}

	@Override
	public void parse(JSONObject _json_)
	{
	}

	public static SResponse_TeachOverByTeacher load(String str)
	{
		try
		{
			SResponse_TeachOverByTeacher obj = new SResponse_TeachOverByTeacher();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_TeachOverByTeacher load(JSONObject json)
	{
		try
		{
			SResponse_TeachOverByTeacher obj = new SResponse_TeachOverByTeacher();
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

	public static List<SResponse_TeachOverByTeacher> loadList(JSONArray ja)
	{
		try
		{
			List<SResponse_TeachOverByTeacher> list = new ArrayList<SResponse_TeachOverByTeacher>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SResponse_TeachOverByTeacher item = SResponse_TeachOverByTeacher.load(jo);
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

	public static JSONArray saveList(List<SResponse_TeachOverByTeacher> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SResponse_TeachOverByTeacher item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Response_TeachOverByTeacher _proto_)
	{
	}

	public static SResponse_TeachOverByTeacher load(byte[] bytes)
	{
		try
		{
			SResponse_TeachOverByTeacher obj = new SResponse_TeachOverByTeacher();
			obj.parse(Response_TeachOverByTeacher.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_TeachOverByTeacher load(Response_TeachOverByTeacher proto)
	{
		try
		{
			SResponse_TeachOverByTeacher obj = new SResponse_TeachOverByTeacher();
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
	public Response_TeachOverByTeacher saveToProto()
	{
		Response_TeachOverByTeacher.Builder _builder_ = Response_TeachOverByTeacher.newBuilder();
		return _builder_.build();
	}
}
