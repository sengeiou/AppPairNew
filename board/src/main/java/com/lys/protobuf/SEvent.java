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
import com.lys.protobuf.ProtocolPair2.Event;

public class SEvent extends SPTData<Event>
{
	private static final SEvent DefaultInstance = new SEvent();

	public String userId = null;
	public SUser user = null;
	public String action = null;
	public String target = null;
	public String des = null;
	public Long time = 0L;

	public static SEvent create(String userId, SUser user, String action, String target, String des, Long time)
	{
		SEvent obj = new SEvent();
		obj.userId = userId;
		obj.user = user;
		obj.action = action;
		obj.target = target;
		obj.des = des;
		obj.time = time;
		return obj;
	}

	public SEvent clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SEvent _other_)
	{
		this.userId = _other_.userId;
		this.user = _other_.user;
		this.action = _other_.action;
		this.target = _other_.target;
		this.des = _other_.des;
		this.time = _other_.time;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("userId"))
			userId = _json_.getString("userId");
		if (_json_.containsKey("user"))
			user = SUser.load(_json_.getJSONObject("user"));
		if (_json_.containsKey("action"))
			action = _json_.getString("action");
		if (_json_.containsKey("target"))
			target = _json_.getString("target");
		if (_json_.containsKey("des"))
			des = _json_.getString("des");
		if (_json_.containsKey("time"))
			time = _json_.getLong("time");
	}

	public static SEvent load(String str)
	{
		try
		{
			SEvent obj = new SEvent();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SEvent load(JSONObject json)
	{
		try
		{
			SEvent obj = new SEvent();
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
			if (user != null)
				_json_.put("user", user.saveToJson());
			if (action != null)
				_json_.put("action", action);
			if (target != null)
				_json_.put("target", target);
			if (des != null)
				_json_.put("des", des);
			if (time != null)
				_json_.put("time", String.valueOf(time));
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SEvent> loadList(JSONArray ja)
	{
		try
		{
			List<SEvent> list = new ArrayList<SEvent>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SEvent item = SEvent.load(jo);
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

	public static JSONArray saveList(List<SEvent> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SEvent item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Event _proto_)
	{
		if (_proto_.hasUserId())
			userId = _proto_.getUserId();
		if (_proto_.hasUser())
			user = SUser.load(_proto_.getUser());
		if (_proto_.hasAction())
			action = _proto_.getAction();
		if (_proto_.hasTarget())
			target = _proto_.getTarget();
		if (_proto_.hasDes())
			des = _proto_.getDes();
		if (_proto_.hasTime())
			time = _proto_.getTime();
	}

	public static SEvent load(byte[] bytes)
	{
		try
		{
			SEvent obj = new SEvent();
			obj.parse(Event.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SEvent load(Event proto)
	{
		try
		{
			SEvent obj = new SEvent();
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
	public Event saveToProto()
	{
		Event.Builder _builder_ = Event.newBuilder();
		if (userId != null && !userId.equals(Event.getDefaultInstance().getUserId()))
			_builder_.setUserId(userId);
		if (user != null)
			_builder_.setUser(user.saveToProto());
		if (action != null && !action.equals(Event.getDefaultInstance().getAction()))
			_builder_.setAction(action);
		if (target != null && !target.equals(Event.getDefaultInstance().getTarget()))
			_builder_.setTarget(target);
		if (des != null && !des.equals(Event.getDefaultInstance().getDes()))
			_builder_.setDes(des);
		if (time != null && !time.equals(Event.getDefaultInstance().getTime()))
			_builder_.setTime(time);
		return _builder_.build();
	}
}
