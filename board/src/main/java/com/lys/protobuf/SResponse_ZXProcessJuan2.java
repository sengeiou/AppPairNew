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
import com.lys.protobuf.ProtocolZhixue.Response_ZXProcessJuan2;

public class SResponse_ZXProcessJuan2 extends SPTData<Response_ZXProcessJuan2>
{
	private static final SResponse_ZXProcessJuan2 DefaultInstance = new SResponse_ZXProcessJuan2();


	public static SResponse_ZXProcessJuan2 create()
	{
		SResponse_ZXProcessJuan2 obj = new SResponse_ZXProcessJuan2();
		return obj;
	}

	public SResponse_ZXProcessJuan2 clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SResponse_ZXProcessJuan2 _other_)
	{
	}

	@Override
	public void parse(JSONObject _json_)
	{
	}

	public static SResponse_ZXProcessJuan2 load(String str)
	{
		try
		{
			SResponse_ZXProcessJuan2 obj = new SResponse_ZXProcessJuan2();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_ZXProcessJuan2 load(JSONObject json)
	{
		try
		{
			SResponse_ZXProcessJuan2 obj = new SResponse_ZXProcessJuan2();
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

	public static List<SResponse_ZXProcessJuan2> loadList(JSONArray ja)
	{
		try
		{
			List<SResponse_ZXProcessJuan2> list = new ArrayList<SResponse_ZXProcessJuan2>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SResponse_ZXProcessJuan2 item = SResponse_ZXProcessJuan2.load(jo);
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

	public static JSONArray saveList(List<SResponse_ZXProcessJuan2> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SResponse_ZXProcessJuan2 item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Response_ZXProcessJuan2 _proto_)
	{
	}

	public static SResponse_ZXProcessJuan2 load(byte[] bytes)
	{
		try
		{
			SResponse_ZXProcessJuan2 obj = new SResponse_ZXProcessJuan2();
			obj.parse(Response_ZXProcessJuan2.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_ZXProcessJuan2 load(Response_ZXProcessJuan2 proto)
	{
		try
		{
			SResponse_ZXProcessJuan2 obj = new SResponse_ZXProcessJuan2();
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
	public Response_ZXProcessJuan2 saveToProto()
	{
		Response_ZXProcessJuan2.Builder _builder_ = Response_ZXProcessJuan2.newBuilder();
		return _builder_.build();
	}
}
