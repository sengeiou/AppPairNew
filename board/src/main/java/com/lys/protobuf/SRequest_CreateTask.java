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
import com.lys.protobuf.ProtocolPair.Request_CreateTask;

// ---------------------- 创建任务 --------------------------
public class SRequest_CreateTask extends SPTData<Request_CreateTask>
{
	private static final SRequest_CreateTask DefaultInstance = new SRequest_CreateTask();

	public String userId = null;
	public String name = null;
	public String group = null;
	public /*SPTaskType*/ Integer type = Request_CreateTask.getDefaultInstance().getType().getNumber();
	public /*SPJobType*/ Integer jobType = Request_CreateTask.getDefaultInstance().getJobType().getNumber();

	public static SRequest_CreateTask create(String userId, String name, String group, Integer type, Integer jobType)
	{
		SRequest_CreateTask obj = new SRequest_CreateTask();
		obj.userId = userId;
		obj.name = name;
		obj.group = group;
		obj.type = type;
		obj.jobType = jobType;
		return obj;
	}

	public SRequest_CreateTask clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SRequest_CreateTask _other_)
	{
		this.userId = _other_.userId;
		this.name = _other_.name;
		this.group = _other_.group;
		this.type = _other_.type;
		this.jobType = _other_.jobType;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("userId"))
			userId = _json_.getString("userId");
		if (_json_.containsKey("name"))
			name = _json_.getString("name");
		if (_json_.containsKey("group"))
			group = _json_.getString("group");
		if (_json_.containsKey("type"))
			type = _json_.getInteger("type");
		if (_json_.containsKey("jobType"))
			jobType = _json_.getInteger("jobType");
	}

	public static SRequest_CreateTask load(String str)
	{
		try
		{
			SRequest_CreateTask obj = new SRequest_CreateTask();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_CreateTask load(JSONObject json)
	{
		try
		{
			SRequest_CreateTask obj = new SRequest_CreateTask();
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
			if (name != null)
				_json_.put("name", name);
			if (group != null)
				_json_.put("group", group);
			if (type != null)
				_json_.put("type", type);
			if (jobType != null)
				_json_.put("jobType", jobType);
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SRequest_CreateTask> loadList(JSONArray ja)
	{
		try
		{
			List<SRequest_CreateTask> list = new ArrayList<SRequest_CreateTask>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SRequest_CreateTask item = SRequest_CreateTask.load(jo);
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

	public static JSONArray saveList(List<SRequest_CreateTask> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SRequest_CreateTask item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Request_CreateTask _proto_)
	{
		if (_proto_.hasUserId())
			userId = _proto_.getUserId();
		if (_proto_.hasName())
			name = _proto_.getName();
		if (_proto_.hasGroup())
			group = _proto_.getGroup();
		if (_proto_.hasType())
			type = _proto_.getType().getNumber();
		if (_proto_.hasJobType())
			jobType = _proto_.getJobType().getNumber();
	}

	public static SRequest_CreateTask load(byte[] bytes)
	{
		try
		{
			SRequest_CreateTask obj = new SRequest_CreateTask();
			obj.parse(Request_CreateTask.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_CreateTask load(Request_CreateTask proto)
	{
		try
		{
			SRequest_CreateTask obj = new SRequest_CreateTask();
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
	public Request_CreateTask saveToProto()
	{
		Request_CreateTask.Builder _builder_ = Request_CreateTask.newBuilder();
		if (userId != null && !userId.equals(Request_CreateTask.getDefaultInstance().getUserId()))
			_builder_.setUserId(userId);
		if (name != null && !name.equals(Request_CreateTask.getDefaultInstance().getName()))
			_builder_.setName(name);
		if (group != null && !group.equals(Request_CreateTask.getDefaultInstance().getGroup()))
			_builder_.setGroup(group);
		if (type != null && Request_CreateTask.getDefaultInstance().getType().getNumber() != type)
			_builder_.setType(ProtocolPair.PTaskType.valueOf(type));
		if (jobType != null && Request_CreateTask.getDefaultInstance().getJobType().getNumber() != jobType)
			_builder_.setJobType(ProtocolPair.PJobType.valueOf(jobType));
		return _builder_.build();
	}
}
