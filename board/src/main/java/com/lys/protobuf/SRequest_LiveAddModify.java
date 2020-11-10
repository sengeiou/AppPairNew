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
import com.lys.protobuf.ProtocolLive.Request_LiveAddModify;

// ---------------------- xxxxxxxxxxxxx --------------------------
public class SRequest_LiveAddModify extends SPTData<Request_LiveAddModify>
{
	private static final SRequest_LiveAddModify DefaultInstance = new SRequest_LiveAddModify();

	public SLiveTask live = null;

	public static SRequest_LiveAddModify create(SLiveTask live)
	{
		SRequest_LiveAddModify obj = new SRequest_LiveAddModify();
		obj.live = live;
		return obj;
	}

	public SRequest_LiveAddModify clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SRequest_LiveAddModify _other_)
	{
		this.live = _other_.live;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("live"))
			live = SLiveTask.load(_json_.getJSONObject("live"));
	}

	public static SRequest_LiveAddModify load(String str)
	{
		try
		{
			SRequest_LiveAddModify obj = new SRequest_LiveAddModify();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_LiveAddModify load(JSONObject json)
	{
		try
		{
			SRequest_LiveAddModify obj = new SRequest_LiveAddModify();
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
			if (live != null)
				_json_.put("live", live.saveToJson());
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SRequest_LiveAddModify> loadList(JSONArray ja)
	{
		try
		{
			List<SRequest_LiveAddModify> list = new ArrayList<SRequest_LiveAddModify>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SRequest_LiveAddModify item = SRequest_LiveAddModify.load(jo);
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

	public static JSONArray saveList(List<SRequest_LiveAddModify> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SRequest_LiveAddModify item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Request_LiveAddModify _proto_)
	{
		if (_proto_.hasLive())
			live = SLiveTask.load(_proto_.getLive());
	}

	public static SRequest_LiveAddModify load(byte[] bytes)
	{
		try
		{
			SRequest_LiveAddModify obj = new SRequest_LiveAddModify();
			obj.parse(Request_LiveAddModify.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_LiveAddModify load(Request_LiveAddModify proto)
	{
		try
		{
			SRequest_LiveAddModify obj = new SRequest_LiveAddModify();
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
	public Request_LiveAddModify saveToProto()
	{
		Request_LiveAddModify.Builder _builder_ = Request_LiveAddModify.newBuilder();
		if (live != null)
			_builder_.setLive(live.saveToProto());
		return _builder_.build();
	}
}
