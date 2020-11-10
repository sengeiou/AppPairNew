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
import com.lys.protobuf.ProtocolScore.Response_ModifyTeach;

public class SResponse_ModifyTeach extends SPTData<Response_ModifyTeach>
{
	private static final SResponse_ModifyTeach DefaultInstance = new SResponse_ModifyTeach();


	public static SResponse_ModifyTeach create()
	{
		SResponse_ModifyTeach obj = new SResponse_ModifyTeach();
		return obj;
	}

	public SResponse_ModifyTeach clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SResponse_ModifyTeach _other_)
	{
	}

	@Override
	public void parse(JSONObject _json_)
	{
	}

	public static SResponse_ModifyTeach load(String str)
	{
		try
		{
			SResponse_ModifyTeach obj = new SResponse_ModifyTeach();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_ModifyTeach load(JSONObject json)
	{
		try
		{
			SResponse_ModifyTeach obj = new SResponse_ModifyTeach();
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

	public static List<SResponse_ModifyTeach> loadList(JSONArray ja)
	{
		try
		{
			List<SResponse_ModifyTeach> list = new ArrayList<SResponse_ModifyTeach>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SResponse_ModifyTeach item = SResponse_ModifyTeach.load(jo);
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

	public static JSONArray saveList(List<SResponse_ModifyTeach> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SResponse_ModifyTeach item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Response_ModifyTeach _proto_)
	{
	}

	public static SResponse_ModifyTeach load(byte[] bytes)
	{
		try
		{
			SResponse_ModifyTeach obj = new SResponse_ModifyTeach();
			obj.parse(Response_ModifyTeach.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_ModifyTeach load(Response_ModifyTeach proto)
	{
		try
		{
			SResponse_ModifyTeach obj = new SResponse_ModifyTeach();
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
	public Response_ModifyTeach saveToProto()
	{
		Response_ModifyTeach.Builder _builder_ = Response_ModifyTeach.newBuilder();
		return _builder_.build();
	}
}
