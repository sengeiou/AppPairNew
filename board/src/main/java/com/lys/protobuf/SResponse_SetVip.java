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
import com.lys.protobuf.ProtocolPair.Response_SetVip;

public class SResponse_SetVip extends SPTData<Response_SetVip>
{
	private static final SResponse_SetVip DefaultInstance = new SResponse_SetVip();


	public static SResponse_SetVip create()
	{
		SResponse_SetVip obj = new SResponse_SetVip();
		return obj;
	}

	public SResponse_SetVip clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SResponse_SetVip _other_)
	{
	}

	@Override
	public void parse(JSONObject _json_)
	{
	}

	public static SResponse_SetVip load(String str)
	{
		try
		{
			SResponse_SetVip obj = new SResponse_SetVip();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_SetVip load(JSONObject json)
	{
		try
		{
			SResponse_SetVip obj = new SResponse_SetVip();
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

	public static List<SResponse_SetVip> loadList(JSONArray ja)
	{
		try
		{
			List<SResponse_SetVip> list = new ArrayList<SResponse_SetVip>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SResponse_SetVip item = SResponse_SetVip.load(jo);
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

	public static JSONArray saveList(List<SResponse_SetVip> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SResponse_SetVip item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Response_SetVip _proto_)
	{
	}

	public static SResponse_SetVip load(byte[] bytes)
	{
		try
		{
			SResponse_SetVip obj = new SResponse_SetVip();
			obj.parse(Response_SetVip.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_SetVip load(Response_SetVip proto)
	{
		try
		{
			SResponse_SetVip obj = new SResponse_SetVip();
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
	public Response_SetVip saveToProto()
	{
		Response_SetVip.Builder _builder_ = Response_SetVip.newBuilder();
		return _builder_.build();
	}
}
