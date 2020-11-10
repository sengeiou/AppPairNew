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
import com.lys.protobuf.ProtocolPair.Request_ModifyTask;

// ---------------------- 修改任务 --------------------------
public class SRequest_ModifyTask extends SPTData<Request_ModifyTask>
{
	private static final SRequest_ModifyTask DefaultInstance = new SRequest_ModifyTask();

	public String taskId = null;
	public String group = null;
	public String name = null;
	public /*SPTaskType*/ Integer type = Request_ModifyTask.getDefaultInstance().getType().getNumber();
	public /*SPJobType*/ Integer jobType = Request_ModifyTask.getDefaultInstance().getJobType().getNumber();

	public static SRequest_ModifyTask create(String taskId, String group, String name, Integer type, Integer jobType)
	{
		SRequest_ModifyTask obj = new SRequest_ModifyTask();
		obj.taskId = taskId;
		obj.group = group;
		obj.name = name;
		obj.type = type;
		obj.jobType = jobType;
		return obj;
	}

	public SRequest_ModifyTask clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SRequest_ModifyTask _other_)
	{
		this.taskId = _other_.taskId;
		this.group = _other_.group;
		this.name = _other_.name;
		this.type = _other_.type;
		this.jobType = _other_.jobType;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("taskId"))
			taskId = _json_.getString("taskId");
		if (_json_.containsKey("group"))
			group = _json_.getString("group");
		if (_json_.containsKey("name"))
			name = _json_.getString("name");
		if (_json_.containsKey("type"))
			type = _json_.getInteger("type");
		if (_json_.containsKey("jobType"))
			jobType = _json_.getInteger("jobType");
	}

	public static SRequest_ModifyTask load(String str)
	{
		try
		{
			SRequest_ModifyTask obj = new SRequest_ModifyTask();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_ModifyTask load(JSONObject json)
	{
		try
		{
			SRequest_ModifyTask obj = new SRequest_ModifyTask();
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
			if (taskId != null)
				_json_.put("taskId", taskId);
			if (group != null)
				_json_.put("group", group);
			if (name != null)
				_json_.put("name", name);
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

	public static List<SRequest_ModifyTask> loadList(JSONArray ja)
	{
		try
		{
			List<SRequest_ModifyTask> list = new ArrayList<SRequest_ModifyTask>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SRequest_ModifyTask item = SRequest_ModifyTask.load(jo);
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

	public static JSONArray saveList(List<SRequest_ModifyTask> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SRequest_ModifyTask item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Request_ModifyTask _proto_)
	{
		if (_proto_.hasTaskId())
			taskId = _proto_.getTaskId();
		if (_proto_.hasGroup())
			group = _proto_.getGroup();
		if (_proto_.hasName())
			name = _proto_.getName();
		if (_proto_.hasType())
			type = _proto_.getType().getNumber();
		if (_proto_.hasJobType())
			jobType = _proto_.getJobType().getNumber();
	}

	public static SRequest_ModifyTask load(byte[] bytes)
	{
		try
		{
			SRequest_ModifyTask obj = new SRequest_ModifyTask();
			obj.parse(Request_ModifyTask.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_ModifyTask load(Request_ModifyTask proto)
	{
		try
		{
			SRequest_ModifyTask obj = new SRequest_ModifyTask();
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
	public Request_ModifyTask saveToProto()
	{
		Request_ModifyTask.Builder _builder_ = Request_ModifyTask.newBuilder();
		if (taskId != null && !taskId.equals(Request_ModifyTask.getDefaultInstance().getTaskId()))
			_builder_.setTaskId(taskId);
		if (group != null && !group.equals(Request_ModifyTask.getDefaultInstance().getGroup()))
			_builder_.setGroup(group);
		if (name != null && !name.equals(Request_ModifyTask.getDefaultInstance().getName()))
			_builder_.setName(name);
		if (type != null && Request_ModifyTask.getDefaultInstance().getType().getNumber() != type)
			_builder_.setType(ProtocolPair.PTaskType.valueOf(type));
		if (jobType != null && Request_ModifyTask.getDefaultInstance().getJobType().getNumber() != jobType)
			_builder_.setJobType(ProtocolPair.PJobType.valueOf(jobType));
		return _builder_.build();
	}
}
