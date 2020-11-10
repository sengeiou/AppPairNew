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
import com.lys.protobuf.ProtocolPair.Response_ModifyPsw;

public class SResponse_ModifyPsw extends SPTData<Response_ModifyPsw>
{
	private static final SResponse_ModifyPsw DefaultInstance = new SResponse_ModifyPsw();


	public static SResponse_ModifyPsw create()
	{
		SResponse_ModifyPsw obj = new SResponse_ModifyPsw();
		return obj;
	}

	public SResponse_ModifyPsw clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SResponse_ModifyPsw _other_)
	{
	}

	@Override
	public void parse(JSONObject _json_)
	{
	}

	public static SResponse_ModifyPsw load(String str)
	{
		try
		{
			SResponse_ModifyPsw obj = new SResponse_ModifyPsw();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_ModifyPsw load(JSONObject json)
	{
		try
		{
			SResponse_ModifyPsw obj = new SResponse_ModifyPsw();
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

	public static List<SResponse_ModifyPsw> loadList(JSONArray ja)
	{
		try
		{
			List<SResponse_ModifyPsw> list = new ArrayList<SResponse_ModifyPsw>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SResponse_ModifyPsw item = SResponse_ModifyPsw.load(jo);
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

	public static JSONArray saveList(List<SResponse_ModifyPsw> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SResponse_ModifyPsw item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Response_ModifyPsw _proto_)
	{
	}

	public static SResponse_ModifyPsw load(byte[] bytes)
	{
		try
		{
			SResponse_ModifyPsw obj = new SResponse_ModifyPsw();
			obj.parse(Response_ModifyPsw.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_ModifyPsw load(Response_ModifyPsw proto)
	{
		try
		{
			SResponse_ModifyPsw obj = new SResponse_ModifyPsw();
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
	public Response_ModifyPsw saveToProto()
	{
		Response_ModifyPsw.Builder _builder_ = Response_ModifyPsw.newBuilder();
		return _builder_.build();
	}
}
