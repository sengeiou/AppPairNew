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
import com.lys.protobuf.ProtocolZhixue.Response_ZXCatchOver;

public class SResponse_ZXCatchOver extends SPTData<Response_ZXCatchOver>
{
	private static final SResponse_ZXCatchOver DefaultInstance = new SResponse_ZXCatchOver();


	public static SResponse_ZXCatchOver create()
	{
		SResponse_ZXCatchOver obj = new SResponse_ZXCatchOver();
		return obj;
	}

	public SResponse_ZXCatchOver clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SResponse_ZXCatchOver _other_)
	{
	}

	@Override
	public void parse(JSONObject _json_)
	{
	}

	public static SResponse_ZXCatchOver load(String str)
	{
		try
		{
			SResponse_ZXCatchOver obj = new SResponse_ZXCatchOver();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_ZXCatchOver load(JSONObject json)
	{
		try
		{
			SResponse_ZXCatchOver obj = new SResponse_ZXCatchOver();
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

	public static List<SResponse_ZXCatchOver> loadList(JSONArray ja)
	{
		try
		{
			List<SResponse_ZXCatchOver> list = new ArrayList<SResponse_ZXCatchOver>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SResponse_ZXCatchOver item = SResponse_ZXCatchOver.load(jo);
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

	public static JSONArray saveList(List<SResponse_ZXCatchOver> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SResponse_ZXCatchOver item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Response_ZXCatchOver _proto_)
	{
	}

	public static SResponse_ZXCatchOver load(byte[] bytes)
	{
		try
		{
			SResponse_ZXCatchOver obj = new SResponse_ZXCatchOver();
			obj.parse(Response_ZXCatchOver.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_ZXCatchOver load(Response_ZXCatchOver proto)
	{
		try
		{
			SResponse_ZXCatchOver obj = new SResponse_ZXCatchOver();
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
	public Response_ZXCatchOver saveToProto()
	{
		Response_ZXCatchOver.Builder _builder_ = Response_ZXCatchOver.newBuilder();
		return _builder_.build();
	}
}
