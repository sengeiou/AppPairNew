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
import com.lys.protobuf.ProtocolPair.Response_ModifyGrade;

public class SResponse_ModifyGrade extends SPTData<Response_ModifyGrade>
{
	private static final SResponse_ModifyGrade DefaultInstance = new SResponse_ModifyGrade();


	public static SResponse_ModifyGrade create()
	{
		SResponse_ModifyGrade obj = new SResponse_ModifyGrade();
		return obj;
	}

	public SResponse_ModifyGrade clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SResponse_ModifyGrade _other_)
	{
	}

	@Override
	public void parse(JSONObject _json_)
	{
	}

	public static SResponse_ModifyGrade load(String str)
	{
		try
		{
			SResponse_ModifyGrade obj = new SResponse_ModifyGrade();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_ModifyGrade load(JSONObject json)
	{
		try
		{
			SResponse_ModifyGrade obj = new SResponse_ModifyGrade();
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

	public static List<SResponse_ModifyGrade> loadList(JSONArray ja)
	{
		try
		{
			List<SResponse_ModifyGrade> list = new ArrayList<SResponse_ModifyGrade>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SResponse_ModifyGrade item = SResponse_ModifyGrade.load(jo);
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

	public static JSONArray saveList(List<SResponse_ModifyGrade> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SResponse_ModifyGrade item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Response_ModifyGrade _proto_)
	{
	}

	public static SResponse_ModifyGrade load(byte[] bytes)
	{
		try
		{
			SResponse_ModifyGrade obj = new SResponse_ModifyGrade();
			obj.parse(Response_ModifyGrade.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_ModifyGrade load(Response_ModifyGrade proto)
	{
		try
		{
			SResponse_ModifyGrade obj = new SResponse_ModifyGrade();
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
	public Response_ModifyGrade saveToProto()
	{
		Response_ModifyGrade.Builder _builder_ = Response_ModifyGrade.newBuilder();
		return _builder_.build();
	}
}
