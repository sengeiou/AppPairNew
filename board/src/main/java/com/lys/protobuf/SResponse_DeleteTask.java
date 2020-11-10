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
import com.lys.protobuf.ProtocolPair.Response_DeleteTask;

public class SResponse_DeleteTask extends SPTData<Response_DeleteTask>
{
	private static final SResponse_DeleteTask DefaultInstance = new SResponse_DeleteTask();


	public static SResponse_DeleteTask create()
	{
		SResponse_DeleteTask obj = new SResponse_DeleteTask();
		return obj;
	}

	public SResponse_DeleteTask clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SResponse_DeleteTask _other_)
	{
	}

	@Override
	public void parse(JSONObject _json_)
	{
	}

	public static SResponse_DeleteTask load(String str)
	{
		try
		{
			SResponse_DeleteTask obj = new SResponse_DeleteTask();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_DeleteTask load(JSONObject json)
	{
		try
		{
			SResponse_DeleteTask obj = new SResponse_DeleteTask();
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

	public static List<SResponse_DeleteTask> loadList(JSONArray ja)
	{
		try
		{
			List<SResponse_DeleteTask> list = new ArrayList<SResponse_DeleteTask>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SResponse_DeleteTask item = SResponse_DeleteTask.load(jo);
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

	public static JSONArray saveList(List<SResponse_DeleteTask> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SResponse_DeleteTask item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Response_DeleteTask _proto_)
	{
	}

	public static SResponse_DeleteTask load(byte[] bytes)
	{
		try
		{
			SResponse_DeleteTask obj = new SResponse_DeleteTask();
			obj.parse(Response_DeleteTask.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_DeleteTask load(Response_DeleteTask proto)
	{
		try
		{
			SResponse_DeleteTask obj = new SResponse_DeleteTask();
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
	public Response_DeleteTask saveToProto()
	{
		Response_DeleteTask.Builder _builder_ = Response_DeleteTask.newBuilder();
		return _builder_.build();
	}
}
