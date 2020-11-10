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
import com.lys.protobuf.ProtocolZhixue.Response_ZXCreateTask;

public class SResponse_ZXCreateTask extends SPTData<Response_ZXCreateTask>
{
	private static final SResponse_ZXCreateTask DefaultInstance = new SResponse_ZXCreateTask();


	public static SResponse_ZXCreateTask create()
	{
		SResponse_ZXCreateTask obj = new SResponse_ZXCreateTask();
		return obj;
	}

	public SResponse_ZXCreateTask clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SResponse_ZXCreateTask _other_)
	{
	}

	@Override
	public void parse(JSONObject _json_)
	{
	}

	public static SResponse_ZXCreateTask load(String str)
	{
		try
		{
			SResponse_ZXCreateTask obj = new SResponse_ZXCreateTask();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_ZXCreateTask load(JSONObject json)
	{
		try
		{
			SResponse_ZXCreateTask obj = new SResponse_ZXCreateTask();
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

	public static List<SResponse_ZXCreateTask> loadList(JSONArray ja)
	{
		try
		{
			List<SResponse_ZXCreateTask> list = new ArrayList<SResponse_ZXCreateTask>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SResponse_ZXCreateTask item = SResponse_ZXCreateTask.load(jo);
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

	public static JSONArray saveList(List<SResponse_ZXCreateTask> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SResponse_ZXCreateTask item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Response_ZXCreateTask _proto_)
	{
	}

	public static SResponse_ZXCreateTask load(byte[] bytes)
	{
		try
		{
			SResponse_ZXCreateTask obj = new SResponse_ZXCreateTask();
			obj.parse(Response_ZXCreateTask.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_ZXCreateTask load(Response_ZXCreateTask proto)
	{
		try
		{
			SResponse_ZXCreateTask obj = new SResponse_ZXCreateTask();
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
	public Response_ZXCreateTask saveToProto()
	{
		Response_ZXCreateTask.Builder _builder_ = Response_ZXCreateTask.newBuilder();
		return _builder_.build();
	}
}
