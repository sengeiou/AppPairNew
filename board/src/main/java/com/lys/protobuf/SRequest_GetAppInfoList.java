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
import com.lys.protobuf.ProtocolApp.Request_GetAppInfoList;

// ---------------------- 获取应用列表 --------------------------
public class SRequest_GetAppInfoList extends SPTData<Request_GetAppInfoList>
{
	private static final SRequest_GetAppInfoList DefaultInstance = new SRequest_GetAppInfoList();

	public String channel = null;

	public static SRequest_GetAppInfoList create(String channel)
	{
		SRequest_GetAppInfoList obj = new SRequest_GetAppInfoList();
		obj.channel = channel;
		return obj;
	}

	public SRequest_GetAppInfoList clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SRequest_GetAppInfoList _other_)
	{
		this.channel = _other_.channel;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("channel"))
			channel = _json_.getString("channel");
	}

	public static SRequest_GetAppInfoList load(String str)
	{
		try
		{
			SRequest_GetAppInfoList obj = new SRequest_GetAppInfoList();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_GetAppInfoList load(JSONObject json)
	{
		try
		{
			SRequest_GetAppInfoList obj = new SRequest_GetAppInfoList();
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
			if (channel != null)
				_json_.put("channel", channel);
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SRequest_GetAppInfoList> loadList(JSONArray ja)
	{
		try
		{
			List<SRequest_GetAppInfoList> list = new ArrayList<SRequest_GetAppInfoList>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SRequest_GetAppInfoList item = SRequest_GetAppInfoList.load(jo);
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

	public static JSONArray saveList(List<SRequest_GetAppInfoList> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SRequest_GetAppInfoList item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Request_GetAppInfoList _proto_)
	{
		if (_proto_.hasChannel())
			channel = _proto_.getChannel();
	}

	public static SRequest_GetAppInfoList load(byte[] bytes)
	{
		try
		{
			SRequest_GetAppInfoList obj = new SRequest_GetAppInfoList();
			obj.parse(Request_GetAppInfoList.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_GetAppInfoList load(Request_GetAppInfoList proto)
	{
		try
		{
			SRequest_GetAppInfoList obj = new SRequest_GetAppInfoList();
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
	public Request_GetAppInfoList saveToProto()
	{
		Request_GetAppInfoList.Builder _builder_ = Request_GetAppInfoList.newBuilder();
		if (channel != null && !channel.equals(Request_GetAppInfoList.getDefaultInstance().getChannel()))
			_builder_.setChannel(channel);
		return _builder_.build();
	}
}
