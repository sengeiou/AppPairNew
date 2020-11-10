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
import com.lys.protobuf.ProtocolPair.Response_ModifyName;

public class SResponse_ModifyName extends SPTData<Response_ModifyName>
{
	private static final SResponse_ModifyName DefaultInstance = new SResponse_ModifyName();


	public static SResponse_ModifyName create()
	{
		SResponse_ModifyName obj = new SResponse_ModifyName();
		return obj;
	}

	public SResponse_ModifyName clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SResponse_ModifyName _other_)
	{
	}

	@Override
	public void parse(JSONObject _json_)
	{
	}

	public static SResponse_ModifyName load(String str)
	{
		try
		{
			SResponse_ModifyName obj = new SResponse_ModifyName();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_ModifyName load(JSONObject json)
	{
		try
		{
			SResponse_ModifyName obj = new SResponse_ModifyName();
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

	public static List<SResponse_ModifyName> loadList(JSONArray ja)
	{
		try
		{
			List<SResponse_ModifyName> list = new ArrayList<SResponse_ModifyName>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SResponse_ModifyName item = SResponse_ModifyName.load(jo);
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

	public static JSONArray saveList(List<SResponse_ModifyName> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SResponse_ModifyName item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Response_ModifyName _proto_)
	{
	}

	public static SResponse_ModifyName load(byte[] bytes)
	{
		try
		{
			SResponse_ModifyName obj = new SResponse_ModifyName();
			obj.parse(Response_ModifyName.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_ModifyName load(Response_ModifyName proto)
	{
		try
		{
			SResponse_ModifyName obj = new SResponse_ModifyName();
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
	public Response_ModifyName saveToProto()
	{
		Response_ModifyName.Builder _builder_ = Response_ModifyName.newBuilder();
		return _builder_.build();
	}
}
