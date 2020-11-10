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
import com.lys.protobuf.ProtocolPair.Response_ModifySex;

public class SResponse_ModifySex extends SPTData<Response_ModifySex>
{
	private static final SResponse_ModifySex DefaultInstance = new SResponse_ModifySex();


	public static SResponse_ModifySex create()
	{
		SResponse_ModifySex obj = new SResponse_ModifySex();
		return obj;
	}

	public SResponse_ModifySex clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SResponse_ModifySex _other_)
	{
	}

	@Override
	public void parse(JSONObject _json_)
	{
	}

	public static SResponse_ModifySex load(String str)
	{
		try
		{
			SResponse_ModifySex obj = new SResponse_ModifySex();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_ModifySex load(JSONObject json)
	{
		try
		{
			SResponse_ModifySex obj = new SResponse_ModifySex();
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

	public static List<SResponse_ModifySex> loadList(JSONArray ja)
	{
		try
		{
			List<SResponse_ModifySex> list = new ArrayList<SResponse_ModifySex>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SResponse_ModifySex item = SResponse_ModifySex.load(jo);
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

	public static JSONArray saveList(List<SResponse_ModifySex> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SResponse_ModifySex item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Response_ModifySex _proto_)
	{
	}

	public static SResponse_ModifySex load(byte[] bytes)
	{
		try
		{
			SResponse_ModifySex obj = new SResponse_ModifySex();
			obj.parse(Response_ModifySex.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_ModifySex load(Response_ModifySex proto)
	{
		try
		{
			SResponse_ModifySex obj = new SResponse_ModifySex();
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
	public Response_ModifySex saveToProto()
	{
		Response_ModifySex.Builder _builder_ = Response_ModifySex.newBuilder();
		return _builder_.build();
	}
}
