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
import com.lys.protobuf.ProtocolPair.Response_ModifyTask;

public class SResponse_ModifyTask extends SPTData<Response_ModifyTask>
{
	private static final SResponse_ModifyTask DefaultInstance = new SResponse_ModifyTask();


	public static SResponse_ModifyTask create()
	{
		SResponse_ModifyTask obj = new SResponse_ModifyTask();
		return obj;
	}

	public SResponse_ModifyTask clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SResponse_ModifyTask _other_)
	{
	}

	@Override
	public void parse(JSONObject _json_)
	{
	}

	public static SResponse_ModifyTask load(String str)
	{
		try
		{
			SResponse_ModifyTask obj = new SResponse_ModifyTask();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_ModifyTask load(JSONObject json)
	{
		try
		{
			SResponse_ModifyTask obj = new SResponse_ModifyTask();
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

	public static List<SResponse_ModifyTask> loadList(JSONArray ja)
	{
		try
		{
			List<SResponse_ModifyTask> list = new ArrayList<SResponse_ModifyTask>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SResponse_ModifyTask item = SResponse_ModifyTask.load(jo);
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

	public static JSONArray saveList(List<SResponse_ModifyTask> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SResponse_ModifyTask item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Response_ModifyTask _proto_)
	{
	}

	public static SResponse_ModifyTask load(byte[] bytes)
	{
		try
		{
			SResponse_ModifyTask obj = new SResponse_ModifyTask();
			obj.parse(Response_ModifyTask.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_ModifyTask load(Response_ModifyTask proto)
	{
		try
		{
			SResponse_ModifyTask obj = new SResponse_ModifyTask();
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
	public Response_ModifyTask saveToProto()
	{
		Response_ModifyTask.Builder _builder_ = Response_ModifyTask.newBuilder();
		return _builder_.build();
	}
}
