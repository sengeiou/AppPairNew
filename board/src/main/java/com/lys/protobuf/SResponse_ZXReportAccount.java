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
import com.lys.protobuf.ProtocolZhixue.Response_ZXReportAccount;

public class SResponse_ZXReportAccount extends SPTData<Response_ZXReportAccount>
{
	private static final SResponse_ZXReportAccount DefaultInstance = new SResponse_ZXReportAccount();


	public static SResponse_ZXReportAccount create()
	{
		SResponse_ZXReportAccount obj = new SResponse_ZXReportAccount();
		return obj;
	}

	public SResponse_ZXReportAccount clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SResponse_ZXReportAccount _other_)
	{
	}

	@Override
	public void parse(JSONObject _json_)
	{
	}

	public static SResponse_ZXReportAccount load(String str)
	{
		try
		{
			SResponse_ZXReportAccount obj = new SResponse_ZXReportAccount();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_ZXReportAccount load(JSONObject json)
	{
		try
		{
			SResponse_ZXReportAccount obj = new SResponse_ZXReportAccount();
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

	public static List<SResponse_ZXReportAccount> loadList(JSONArray ja)
	{
		try
		{
			List<SResponse_ZXReportAccount> list = new ArrayList<SResponse_ZXReportAccount>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SResponse_ZXReportAccount item = SResponse_ZXReportAccount.load(jo);
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

	public static JSONArray saveList(List<SResponse_ZXReportAccount> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SResponse_ZXReportAccount item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Response_ZXReportAccount _proto_)
	{
	}

	public static SResponse_ZXReportAccount load(byte[] bytes)
	{
		try
		{
			SResponse_ZXReportAccount obj = new SResponse_ZXReportAccount();
			obj.parse(Response_ZXReportAccount.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_ZXReportAccount load(Response_ZXReportAccount proto)
	{
		try
		{
			SResponse_ZXReportAccount obj = new SResponse_ZXReportAccount();
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
	public Response_ZXReportAccount saveToProto()
	{
		Response_ZXReportAccount.Builder _builder_ = Response_ZXReportAccount.newBuilder();
		return _builder_.build();
	}
}
