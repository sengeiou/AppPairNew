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
import com.lys.protobuf.ProtocolPair.Response_UserPhoneCode;

public class SResponse_UserPhoneCode extends SPTData<Response_UserPhoneCode>
{
	private static final SResponse_UserPhoneCode DefaultInstance = new SResponse_UserPhoneCode();


	public static SResponse_UserPhoneCode create()
	{
		SResponse_UserPhoneCode obj = new SResponse_UserPhoneCode();
		return obj;
	}

	public SResponse_UserPhoneCode clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SResponse_UserPhoneCode _other_)
	{
	}

	@Override
	public void parse(JSONObject _json_)
	{
	}

	public static SResponse_UserPhoneCode load(String str)
	{
		try
		{
			SResponse_UserPhoneCode obj = new SResponse_UserPhoneCode();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_UserPhoneCode load(JSONObject json)
	{
		try
		{
			SResponse_UserPhoneCode obj = new SResponse_UserPhoneCode();
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

	public static List<SResponse_UserPhoneCode> loadList(JSONArray ja)
	{
		try
		{
			List<SResponse_UserPhoneCode> list = new ArrayList<SResponse_UserPhoneCode>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SResponse_UserPhoneCode item = SResponse_UserPhoneCode.load(jo);
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

	public static JSONArray saveList(List<SResponse_UserPhoneCode> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SResponse_UserPhoneCode item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Response_UserPhoneCode _proto_)
	{
	}

	public static SResponse_UserPhoneCode load(byte[] bytes)
	{
		try
		{
			SResponse_UserPhoneCode obj = new SResponse_UserPhoneCode();
			obj.parse(Response_UserPhoneCode.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_UserPhoneCode load(Response_UserPhoneCode proto)
	{
		try
		{
			SResponse_UserPhoneCode obj = new SResponse_UserPhoneCode();
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
	public Response_UserPhoneCode saveToProto()
	{
		Response_UserPhoneCode.Builder _builder_ = Response_UserPhoneCode.newBuilder();
		return _builder_.build();
	}
}
