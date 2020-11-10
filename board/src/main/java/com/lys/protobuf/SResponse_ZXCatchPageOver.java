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
import com.lys.protobuf.ProtocolZhixue.Response_ZXCatchPageOver;

public class SResponse_ZXCatchPageOver extends SPTData<Response_ZXCatchPageOver>
{
	private static final SResponse_ZXCatchPageOver DefaultInstance = new SResponse_ZXCatchPageOver();


	public static SResponse_ZXCatchPageOver create()
	{
		SResponse_ZXCatchPageOver obj = new SResponse_ZXCatchPageOver();
		return obj;
	}

	public SResponse_ZXCatchPageOver clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SResponse_ZXCatchPageOver _other_)
	{
	}

	@Override
	public void parse(JSONObject _json_)
	{
	}

	public static SResponse_ZXCatchPageOver load(String str)
	{
		try
		{
			SResponse_ZXCatchPageOver obj = new SResponse_ZXCatchPageOver();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_ZXCatchPageOver load(JSONObject json)
	{
		try
		{
			SResponse_ZXCatchPageOver obj = new SResponse_ZXCatchPageOver();
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

	public static List<SResponse_ZXCatchPageOver> loadList(JSONArray ja)
	{
		try
		{
			List<SResponse_ZXCatchPageOver> list = new ArrayList<SResponse_ZXCatchPageOver>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SResponse_ZXCatchPageOver item = SResponse_ZXCatchPageOver.load(jo);
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

	public static JSONArray saveList(List<SResponse_ZXCatchPageOver> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SResponse_ZXCatchPageOver item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Response_ZXCatchPageOver _proto_)
	{
	}

	public static SResponse_ZXCatchPageOver load(byte[] bytes)
	{
		try
		{
			SResponse_ZXCatchPageOver obj = new SResponse_ZXCatchPageOver();
			obj.parse(Response_ZXCatchPageOver.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_ZXCatchPageOver load(Response_ZXCatchPageOver proto)
	{
		try
		{
			SResponse_ZXCatchPageOver obj = new SResponse_ZXCatchPageOver();
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
	public Response_ZXCatchPageOver saveToProto()
	{
		Response_ZXCatchPageOver.Builder _builder_ = Response_ZXCatchPageOver.newBuilder();
		return _builder_.build();
	}
}
