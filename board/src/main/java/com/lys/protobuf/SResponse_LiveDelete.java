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
import com.lys.protobuf.ProtocolLive.Response_LiveDelete;

public class SResponse_LiveDelete extends SPTData<Response_LiveDelete>
{
	private static final SResponse_LiveDelete DefaultInstance = new SResponse_LiveDelete();


	public static SResponse_LiveDelete create()
	{
		SResponse_LiveDelete obj = new SResponse_LiveDelete();
		return obj;
	}

	public SResponse_LiveDelete clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SResponse_LiveDelete _other_)
	{
	}

	@Override
	public void parse(JSONObject _json_)
	{
	}

	public static SResponse_LiveDelete load(String str)
	{
		try
		{
			SResponse_LiveDelete obj = new SResponse_LiveDelete();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_LiveDelete load(JSONObject json)
	{
		try
		{
			SResponse_LiveDelete obj = new SResponse_LiveDelete();
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

	public static List<SResponse_LiveDelete> loadList(JSONArray ja)
	{
		try
		{
			List<SResponse_LiveDelete> list = new ArrayList<SResponse_LiveDelete>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SResponse_LiveDelete item = SResponse_LiveDelete.load(jo);
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

	public static JSONArray saveList(List<SResponse_LiveDelete> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SResponse_LiveDelete item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Response_LiveDelete _proto_)
	{
	}

	public static SResponse_LiveDelete load(byte[] bytes)
	{
		try
		{
			SResponse_LiveDelete obj = new SResponse_LiveDelete();
			obj.parse(Response_LiveDelete.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_LiveDelete load(Response_LiveDelete proto)
	{
		try
		{
			SResponse_LiveDelete obj = new SResponse_LiveDelete();
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
	public Response_LiveDelete saveToProto()
	{
		Response_LiveDelete.Builder _builder_ = Response_LiveDelete.newBuilder();
		return _builder_.build();
	}
}
