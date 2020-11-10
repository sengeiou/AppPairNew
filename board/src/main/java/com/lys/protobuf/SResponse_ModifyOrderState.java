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
import com.lys.protobuf.ProtocolScore.Response_ModifyOrderState;

public class SResponse_ModifyOrderState extends SPTData<Response_ModifyOrderState>
{
	private static final SResponse_ModifyOrderState DefaultInstance = new SResponse_ModifyOrderState();


	public static SResponse_ModifyOrderState create()
	{
		SResponse_ModifyOrderState obj = new SResponse_ModifyOrderState();
		return obj;
	}

	public SResponse_ModifyOrderState clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SResponse_ModifyOrderState _other_)
	{
	}

	@Override
	public void parse(JSONObject _json_)
	{
	}

	public static SResponse_ModifyOrderState load(String str)
	{
		try
		{
			SResponse_ModifyOrderState obj = new SResponse_ModifyOrderState();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_ModifyOrderState load(JSONObject json)
	{
		try
		{
			SResponse_ModifyOrderState obj = new SResponse_ModifyOrderState();
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

	public static List<SResponse_ModifyOrderState> loadList(JSONArray ja)
	{
		try
		{
			List<SResponse_ModifyOrderState> list = new ArrayList<SResponse_ModifyOrderState>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SResponse_ModifyOrderState item = SResponse_ModifyOrderState.load(jo);
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

	public static JSONArray saveList(List<SResponse_ModifyOrderState> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SResponse_ModifyOrderState item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Response_ModifyOrderState _proto_)
	{
	}

	public static SResponse_ModifyOrderState load(byte[] bytes)
	{
		try
		{
			SResponse_ModifyOrderState obj = new SResponse_ModifyOrderState();
			obj.parse(Response_ModifyOrderState.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_ModifyOrderState load(Response_ModifyOrderState proto)
	{
		try
		{
			SResponse_ModifyOrderState obj = new SResponse_ModifyOrderState();
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
	public Response_ModifyOrderState saveToProto()
	{
		Response_ModifyOrderState.Builder _builder_ = Response_ModifyOrderState.newBuilder();
		return _builder_.build();
	}
}
