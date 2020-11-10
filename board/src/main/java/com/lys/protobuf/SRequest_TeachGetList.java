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
import com.lys.protobuf.ProtocolTeach.Request_TeachGetList;

// ---------------------- xxxxxxx --------------------------
public class SRequest_TeachGetList extends SPTData<Request_TeachGetList>
{
	private static final SRequest_TeachGetList DefaultInstance = new SRequest_TeachGetList();

	public String userId = null;
	public Long fromTime = 0L;
	public Long toTime = 0L;

	public static SRequest_TeachGetList create(String userId, Long fromTime, Long toTime)
	{
		SRequest_TeachGetList obj = new SRequest_TeachGetList();
		obj.userId = userId;
		obj.fromTime = fromTime;
		obj.toTime = toTime;
		return obj;
	}

	public SRequest_TeachGetList clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SRequest_TeachGetList _other_)
	{
		this.userId = _other_.userId;
		this.fromTime = _other_.fromTime;
		this.toTime = _other_.toTime;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("userId"))
			userId = _json_.getString("userId");
		if (_json_.containsKey("fromTime"))
			fromTime = _json_.getLong("fromTime");
		if (_json_.containsKey("toTime"))
			toTime = _json_.getLong("toTime");
	}

	public static SRequest_TeachGetList load(String str)
	{
		try
		{
			SRequest_TeachGetList obj = new SRequest_TeachGetList();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_TeachGetList load(JSONObject json)
	{
		try
		{
			SRequest_TeachGetList obj = new SRequest_TeachGetList();
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
			if (userId != null)
				_json_.put("userId", userId);
			if (fromTime != null)
				_json_.put("fromTime", String.valueOf(fromTime));
			if (toTime != null)
				_json_.put("toTime", String.valueOf(toTime));
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SRequest_TeachGetList> loadList(JSONArray ja)
	{
		try
		{
			List<SRequest_TeachGetList> list = new ArrayList<SRequest_TeachGetList>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SRequest_TeachGetList item = SRequest_TeachGetList.load(jo);
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

	public static JSONArray saveList(List<SRequest_TeachGetList> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SRequest_TeachGetList item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Request_TeachGetList _proto_)
	{
		if (_proto_.hasUserId())
			userId = _proto_.getUserId();
		if (_proto_.hasFromTime())
			fromTime = _proto_.getFromTime();
		if (_proto_.hasToTime())
			toTime = _proto_.getToTime();
	}

	public static SRequest_TeachGetList load(byte[] bytes)
	{
		try
		{
			SRequest_TeachGetList obj = new SRequest_TeachGetList();
			obj.parse(Request_TeachGetList.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_TeachGetList load(Request_TeachGetList proto)
	{
		try
		{
			SRequest_TeachGetList obj = new SRequest_TeachGetList();
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
	public Request_TeachGetList saveToProto()
	{
		Request_TeachGetList.Builder _builder_ = Request_TeachGetList.newBuilder();
		if (userId != null && !userId.equals(Request_TeachGetList.getDefaultInstance().getUserId()))
			_builder_.setUserId(userId);
		if (fromTime != null && !fromTime.equals(Request_TeachGetList.getDefaultInstance().getFromTime()))
			_builder_.setFromTime(fromTime);
		if (toTime != null && !toTime.equals(Request_TeachGetList.getDefaultInstance().getToTime()))
			_builder_.setToTime(toTime);
		return _builder_.build();
	}
}
