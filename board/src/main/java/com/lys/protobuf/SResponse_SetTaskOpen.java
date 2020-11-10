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
import com.lys.protobuf.ProtocolPair.Response_SetTaskOpen;

public class SResponse_SetTaskOpen extends SPTData<Response_SetTaskOpen>
{
	private static final SResponse_SetTaskOpen DefaultInstance = new SResponse_SetTaskOpen();


	public static SResponse_SetTaskOpen create()
	{
		SResponse_SetTaskOpen obj = new SResponse_SetTaskOpen();
		return obj;
	}

	public SResponse_SetTaskOpen clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SResponse_SetTaskOpen _other_)
	{
	}

	@Override
	public void parse(JSONObject _json_)
	{
	}

	public static SResponse_SetTaskOpen load(String str)
	{
		try
		{
			SResponse_SetTaskOpen obj = new SResponse_SetTaskOpen();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_SetTaskOpen load(JSONObject json)
	{
		try
		{
			SResponse_SetTaskOpen obj = new SResponse_SetTaskOpen();
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

	public static List<SResponse_SetTaskOpen> loadList(JSONArray ja)
	{
		try
		{
			List<SResponse_SetTaskOpen> list = new ArrayList<SResponse_SetTaskOpen>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SResponse_SetTaskOpen item = SResponse_SetTaskOpen.load(jo);
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

	public static JSONArray saveList(List<SResponse_SetTaskOpen> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SResponse_SetTaskOpen item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Response_SetTaskOpen _proto_)
	{
	}

	public static SResponse_SetTaskOpen load(byte[] bytes)
	{
		try
		{
			SResponse_SetTaskOpen obj = new SResponse_SetTaskOpen();
			obj.parse(Response_SetTaskOpen.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_SetTaskOpen load(Response_SetTaskOpen proto)
	{
		try
		{
			SResponse_SetTaskOpen obj = new SResponse_SetTaskOpen();
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
	public Response_SetTaskOpen saveToProto()
	{
		Response_SetTaskOpen.Builder _builder_ = Response_SetTaskOpen.newBuilder();
		return _builder_.build();
	}
}
