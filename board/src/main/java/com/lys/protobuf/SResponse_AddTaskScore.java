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
import com.lys.protobuf.ProtocolPair.Response_AddTaskScore;

public class SResponse_AddTaskScore extends SPTData<Response_AddTaskScore>
{
	private static final SResponse_AddTaskScore DefaultInstance = new SResponse_AddTaskScore();


	public static SResponse_AddTaskScore create()
	{
		SResponse_AddTaskScore obj = new SResponse_AddTaskScore();
		return obj;
	}

	public SResponse_AddTaskScore clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SResponse_AddTaskScore _other_)
	{
	}

	@Override
	public void parse(JSONObject _json_)
	{
	}

	public static SResponse_AddTaskScore load(String str)
	{
		try
		{
			SResponse_AddTaskScore obj = new SResponse_AddTaskScore();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_AddTaskScore load(JSONObject json)
	{
		try
		{
			SResponse_AddTaskScore obj = new SResponse_AddTaskScore();
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

	public static List<SResponse_AddTaskScore> loadList(JSONArray ja)
	{
		try
		{
			List<SResponse_AddTaskScore> list = new ArrayList<SResponse_AddTaskScore>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SResponse_AddTaskScore item = SResponse_AddTaskScore.load(jo);
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

	public static JSONArray saveList(List<SResponse_AddTaskScore> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SResponse_AddTaskScore item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Response_AddTaskScore _proto_)
	{
	}

	public static SResponse_AddTaskScore load(byte[] bytes)
	{
		try
		{
			SResponse_AddTaskScore obj = new SResponse_AddTaskScore();
			obj.parse(Response_AddTaskScore.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_AddTaskScore load(Response_AddTaskScore proto)
	{
		try
		{
			SResponse_AddTaskScore obj = new SResponse_AddTaskScore();
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
	public Response_AddTaskScore saveToProto()
	{
		Response_AddTaskScore.Builder _builder_ = Response_AddTaskScore.newBuilder();
		return _builder_.build();
	}
}
