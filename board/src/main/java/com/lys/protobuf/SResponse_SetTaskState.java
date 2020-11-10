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
import com.lys.protobuf.ProtocolPair.Response_SetTaskState;

public class SResponse_SetTaskState extends SPTData<Response_SetTaskState>
{
	private static final SResponse_SetTaskState DefaultInstance = new SResponse_SetTaskState();


	public static SResponse_SetTaskState create()
	{
		SResponse_SetTaskState obj = new SResponse_SetTaskState();
		return obj;
	}

	public SResponse_SetTaskState clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SResponse_SetTaskState _other_)
	{
	}

	@Override
	public void parse(JSONObject _json_)
	{
	}

	public static SResponse_SetTaskState load(String str)
	{
		try
		{
			SResponse_SetTaskState obj = new SResponse_SetTaskState();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_SetTaskState load(JSONObject json)
	{
		try
		{
			SResponse_SetTaskState obj = new SResponse_SetTaskState();
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

	public static List<SResponse_SetTaskState> loadList(JSONArray ja)
	{
		try
		{
			List<SResponse_SetTaskState> list = new ArrayList<SResponse_SetTaskState>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SResponse_SetTaskState item = SResponse_SetTaskState.load(jo);
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

	public static JSONArray saveList(List<SResponse_SetTaskState> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SResponse_SetTaskState item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Response_SetTaskState _proto_)
	{
	}

	public static SResponse_SetTaskState load(byte[] bytes)
	{
		try
		{
			SResponse_SetTaskState obj = new SResponse_SetTaskState();
			obj.parse(Response_SetTaskState.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_SetTaskState load(Response_SetTaskState proto)
	{
		try
		{
			SResponse_SetTaskState obj = new SResponse_SetTaskState();
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
	public Response_SetTaskState saveToProto()
	{
		Response_SetTaskState.Builder _builder_ = Response_SetTaskState.newBuilder();
		return _builder_.build();
	}
}
