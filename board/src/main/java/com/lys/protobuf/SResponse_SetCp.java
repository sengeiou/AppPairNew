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
import com.lys.protobuf.ProtocolPair.Response_SetCp;

public class SResponse_SetCp extends SPTData<Response_SetCp>
{
	private static final SResponse_SetCp DefaultInstance = new SResponse_SetCp();


	public static SResponse_SetCp create()
	{
		SResponse_SetCp obj = new SResponse_SetCp();
		return obj;
	}

	public SResponse_SetCp clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SResponse_SetCp _other_)
	{
	}

	@Override
	public void parse(JSONObject _json_)
	{
	}

	public static SResponse_SetCp load(String str)
	{
		try
		{
			SResponse_SetCp obj = new SResponse_SetCp();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_SetCp load(JSONObject json)
	{
		try
		{
			SResponse_SetCp obj = new SResponse_SetCp();
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

	public static List<SResponse_SetCp> loadList(JSONArray ja)
	{
		try
		{
			List<SResponse_SetCp> list = new ArrayList<SResponse_SetCp>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SResponse_SetCp item = SResponse_SetCp.load(jo);
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

	public static JSONArray saveList(List<SResponse_SetCp> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SResponse_SetCp item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Response_SetCp _proto_)
	{
	}

	public static SResponse_SetCp load(byte[] bytes)
	{
		try
		{
			SResponse_SetCp obj = new SResponse_SetCp();
			obj.parse(Response_SetCp.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_SetCp load(Response_SetCp proto)
	{
		try
		{
			SResponse_SetCp obj = new SResponse_SetCp();
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
	public Response_SetCp saveToProto()
	{
		Response_SetCp.Builder _builder_ = Response_SetCp.newBuilder();
		return _builder_.build();
	}
}
