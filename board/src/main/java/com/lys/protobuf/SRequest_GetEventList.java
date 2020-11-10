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
import com.lys.protobuf.ProtocolPair2.Request_GetEventList;

// ---------------------- xxxxxxxx --------------------------
public class SRequest_GetEventList extends SPTData<Request_GetEventList>
{
	private static final SRequest_GetEventList DefaultInstance = new SRequest_GetEventList();

	public String userId = null;
	public List<String> actions = new ArrayList<String>();
	public List<String> targets = new ArrayList<String>();

	public static SRequest_GetEventList create(String userId)
	{
		SRequest_GetEventList obj = new SRequest_GetEventList();
		obj.userId = userId;
		return obj;
	}

	public SRequest_GetEventList clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SRequest_GetEventList _other_)
	{
		this.userId = _other_.userId;
		this.actions = _other_.actions;
		this.targets = _other_.targets;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("userId"))
			userId = _json_.getString("userId");
		if (_json_.containsKey("actions"))
			actions = AppDataTool.loadStringList(AppDataTool.getJSONArray(_json_, "actions"));
		if (_json_.containsKey("targets"))
			targets = AppDataTool.loadStringList(AppDataTool.getJSONArray(_json_, "targets"));
	}

	public static SRequest_GetEventList load(String str)
	{
		try
		{
			SRequest_GetEventList obj = new SRequest_GetEventList();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_GetEventList load(JSONObject json)
	{
		try
		{
			SRequest_GetEventList obj = new SRequest_GetEventList();
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
			if (actions != null)
				_json_.put("actions", AppDataTool.saveStringList(actions));
			if (targets != null)
				_json_.put("targets", AppDataTool.saveStringList(targets));
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SRequest_GetEventList> loadList(JSONArray ja)
	{
		try
		{
			List<SRequest_GetEventList> list = new ArrayList<SRequest_GetEventList>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SRequest_GetEventList item = SRequest_GetEventList.load(jo);
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

	public static JSONArray saveList(List<SRequest_GetEventList> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SRequest_GetEventList item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Request_GetEventList _proto_)
	{
		if (_proto_.hasUserId())
			userId = _proto_.getUserId();
		for (int i = 0; i < _proto_.getActionsCount(); i++)
			actions.add(_proto_.getActions(i));
		for (int i = 0; i < _proto_.getTargetsCount(); i++)
			targets.add(_proto_.getTargets(i));
	}

	public static SRequest_GetEventList load(byte[] bytes)
	{
		try
		{
			SRequest_GetEventList obj = new SRequest_GetEventList();
			obj.parse(Request_GetEventList.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_GetEventList load(Request_GetEventList proto)
	{
		try
		{
			SRequest_GetEventList obj = new SRequest_GetEventList();
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
	public Request_GetEventList saveToProto()
	{
		Request_GetEventList.Builder _builder_ = Request_GetEventList.newBuilder();
		if (userId != null && !userId.equals(Request_GetEventList.getDefaultInstance().getUserId()))
			_builder_.setUserId(userId);
		if (actions != null)
			for (String _value_ : actions)
				_builder_.addActions(_value_);
		if (targets != null)
			for (String _value_ : targets)
				_builder_.addTargets(_value_);
		return _builder_.build();
	}
}
