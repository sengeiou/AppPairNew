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
import com.lys.protobuf.ProtocolApp.Response_SetAppInfo;

public class SResponse_SetAppInfo extends SPTData<Response_SetAppInfo>
{
	private static final SResponse_SetAppInfo DefaultInstance = new SResponse_SetAppInfo();


	public static SResponse_SetAppInfo create()
	{
		SResponse_SetAppInfo obj = new SResponse_SetAppInfo();
		return obj;
	}

	public SResponse_SetAppInfo clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SResponse_SetAppInfo _other_)
	{
	}

	@Override
	public void parse(JSONObject _json_)
	{
	}

	public static SResponse_SetAppInfo load(String str)
	{
		try
		{
			SResponse_SetAppInfo obj = new SResponse_SetAppInfo();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_SetAppInfo load(JSONObject json)
	{
		try
		{
			SResponse_SetAppInfo obj = new SResponse_SetAppInfo();
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

	public static List<SResponse_SetAppInfo> loadList(JSONArray ja)
	{
		try
		{
			List<SResponse_SetAppInfo> list = new ArrayList<SResponse_SetAppInfo>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SResponse_SetAppInfo item = SResponse_SetAppInfo.load(jo);
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

	public static JSONArray saveList(List<SResponse_SetAppInfo> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SResponse_SetAppInfo item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Response_SetAppInfo _proto_)
	{
	}

	public static SResponse_SetAppInfo load(byte[] bytes)
	{
		try
		{
			SResponse_SetAppInfo obj = new SResponse_SetAppInfo();
			obj.parse(Response_SetAppInfo.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_SetAppInfo load(Response_SetAppInfo proto)
	{
		try
		{
			SResponse_SetAppInfo obj = new SResponse_SetAppInfo();
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
	public Response_SetAppInfo saveToProto()
	{
		Response_SetAppInfo.Builder _builder_ = Response_SetAppInfo.newBuilder();
		return _builder_.build();
	}
}
