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
import com.lys.protobuf.ProtocolTeach.Request_TeachStart;

// ---------------------- xxxxxxx --------------------------
public class SRequest_TeachStart extends SPTData<Request_TeachStart>
{
	private static final SRequest_TeachStart DefaultInstance = new SRequest_TeachStart();

	public String teachId = null;
	public String userId = null;
	public List<String> targetIds = new ArrayList<String>();
	public String taskId = null;

	public static SRequest_TeachStart create(String teachId, String userId, String taskId)
	{
		SRequest_TeachStart obj = new SRequest_TeachStart();
		obj.teachId = teachId;
		obj.userId = userId;
		obj.taskId = taskId;
		return obj;
	}

	public SRequest_TeachStart clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SRequest_TeachStart _other_)
	{
		this.teachId = _other_.teachId;
		this.userId = _other_.userId;
		this.targetIds = _other_.targetIds;
		this.taskId = _other_.taskId;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("teachId"))
			teachId = _json_.getString("teachId");
		if (_json_.containsKey("userId"))
			userId = _json_.getString("userId");
		if (_json_.containsKey("targetIds"))
			targetIds = AppDataTool.loadStringList(AppDataTool.getJSONArray(_json_, "targetIds"));
		if (_json_.containsKey("taskId"))
			taskId = _json_.getString("taskId");
	}

	public static SRequest_TeachStart load(String str)
	{
		try
		{
			SRequest_TeachStart obj = new SRequest_TeachStart();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_TeachStart load(JSONObject json)
	{
		try
		{
			SRequest_TeachStart obj = new SRequest_TeachStart();
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
			if (teachId != null)
				_json_.put("teachId", teachId);
			if (userId != null)
				_json_.put("userId", userId);
			if (targetIds != null)
				_json_.put("targetIds", AppDataTool.saveStringList(targetIds));
			if (taskId != null)
				_json_.put("taskId", taskId);
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SRequest_TeachStart> loadList(JSONArray ja)
	{
		try
		{
			List<SRequest_TeachStart> list = new ArrayList<SRequest_TeachStart>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SRequest_TeachStart item = SRequest_TeachStart.load(jo);
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

	public static JSONArray saveList(List<SRequest_TeachStart> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SRequest_TeachStart item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Request_TeachStart _proto_)
	{
		if (_proto_.hasTeachId())
			teachId = _proto_.getTeachId();
		if (_proto_.hasUserId())
			userId = _proto_.getUserId();
		for (int i = 0; i < _proto_.getTargetIdsCount(); i++)
			targetIds.add(_proto_.getTargetIds(i));
		if (_proto_.hasTaskId())
			taskId = _proto_.getTaskId();
	}

	public static SRequest_TeachStart load(byte[] bytes)
	{
		try
		{
			SRequest_TeachStart obj = new SRequest_TeachStart();
			obj.parse(Request_TeachStart.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_TeachStart load(Request_TeachStart proto)
	{
		try
		{
			SRequest_TeachStart obj = new SRequest_TeachStart();
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
	public Request_TeachStart saveToProto()
	{
		Request_TeachStart.Builder _builder_ = Request_TeachStart.newBuilder();
		if (teachId != null && !teachId.equals(Request_TeachStart.getDefaultInstance().getTeachId()))
			_builder_.setTeachId(teachId);
		if (userId != null && !userId.equals(Request_TeachStart.getDefaultInstance().getUserId()))
			_builder_.setUserId(userId);
		if (targetIds != null)
			for (String _value_ : targetIds)
				_builder_.addTargetIds(_value_);
		if (taskId != null && !taskId.equals(Request_TeachStart.getDefaultInstance().getTaskId()))
			_builder_.setTaskId(taskId);
		return _builder_.build();
	}
}
