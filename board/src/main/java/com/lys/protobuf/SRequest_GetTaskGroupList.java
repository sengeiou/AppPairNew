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
import com.lys.protobuf.ProtocolScore.Request_GetTaskGroupList;

// ---------------------- xxxxxxxx --------------------------
public class SRequest_GetTaskGroupList extends SPTData<Request_GetTaskGroupList>
{
	private static final SRequest_GetTaskGroupList DefaultInstance = new SRequest_GetTaskGroupList();


	public static SRequest_GetTaskGroupList create()
	{
		SRequest_GetTaskGroupList obj = new SRequest_GetTaskGroupList();
		return obj;
	}

	public SRequest_GetTaskGroupList clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SRequest_GetTaskGroupList _other_)
	{
	}

	@Override
	public void parse(JSONObject _json_)
	{
	}

	public static SRequest_GetTaskGroupList load(String str)
	{
		try
		{
			SRequest_GetTaskGroupList obj = new SRequest_GetTaskGroupList();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_GetTaskGroupList load(JSONObject json)
	{
		try
		{
			SRequest_GetTaskGroupList obj = new SRequest_GetTaskGroupList();
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

	public static List<SRequest_GetTaskGroupList> loadList(JSONArray ja)
	{
		try
		{
			List<SRequest_GetTaskGroupList> list = new ArrayList<SRequest_GetTaskGroupList>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SRequest_GetTaskGroupList item = SRequest_GetTaskGroupList.load(jo);
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

	public static JSONArray saveList(List<SRequest_GetTaskGroupList> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SRequest_GetTaskGroupList item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Request_GetTaskGroupList _proto_)
	{
	}

	public static SRequest_GetTaskGroupList load(byte[] bytes)
	{
		try
		{
			SRequest_GetTaskGroupList obj = new SRequest_GetTaskGroupList();
			obj.parse(Request_GetTaskGroupList.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_GetTaskGroupList load(Request_GetTaskGroupList proto)
	{
		try
		{
			SRequest_GetTaskGroupList obj = new SRequest_GetTaskGroupList();
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
	public Request_GetTaskGroupList saveToProto()
	{
		Request_GetTaskGroupList.Builder _builder_ = Request_GetTaskGroupList.newBuilder();
		return _builder_.build();
	}
}
