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
import com.lys.protobuf.ProtocolPair.Response_ModifyHead;

public class SResponse_ModifyHead extends SPTData<Response_ModifyHead>
{
	private static final SResponse_ModifyHead DefaultInstance = new SResponse_ModifyHead();


	public static SResponse_ModifyHead create()
	{
		SResponse_ModifyHead obj = new SResponse_ModifyHead();
		return obj;
	}

	public SResponse_ModifyHead clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SResponse_ModifyHead _other_)
	{
	}

	@Override
	public void parse(JSONObject _json_)
	{
	}

	public static SResponse_ModifyHead load(String str)
	{
		try
		{
			SResponse_ModifyHead obj = new SResponse_ModifyHead();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_ModifyHead load(JSONObject json)
	{
		try
		{
			SResponse_ModifyHead obj = new SResponse_ModifyHead();
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

	public static List<SResponse_ModifyHead> loadList(JSONArray ja)
	{
		try
		{
			List<SResponse_ModifyHead> list = new ArrayList<SResponse_ModifyHead>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SResponse_ModifyHead item = SResponse_ModifyHead.load(jo);
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

	public static JSONArray saveList(List<SResponse_ModifyHead> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SResponse_ModifyHead item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Response_ModifyHead _proto_)
	{
	}

	public static SResponse_ModifyHead load(byte[] bytes)
	{
		try
		{
			SResponse_ModifyHead obj = new SResponse_ModifyHead();
			obj.parse(Response_ModifyHead.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_ModifyHead load(Response_ModifyHead proto)
	{
		try
		{
			SResponse_ModifyHead obj = new SResponse_ModifyHead();
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
	public Response_ModifyHead saveToProto()
	{
		Response_ModifyHead.Builder _builder_ = Response_ModifyHead.newBuilder();
		return _builder_.build();
	}
}
