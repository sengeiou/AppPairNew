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
import com.lys.protobuf.ProtocolLive.LiveTask;

public class SLiveTask extends SPTData<LiveTask>
{
	private static final SLiveTask DefaultInstance = new SLiveTask();

	public String id = null;
	public String actorId = null;
	public String name = null;
	public String des = null;
	public String cover = null;
	public String video = null;
	public Integer duration = 0;
	public String taskId = null;
	public /*SLiveType*/ Integer type = LiveTask.getDefaultInstance().getType().getNumber();
	public List<String> userIds = new ArrayList<String>();
	public Long startTime = 0L;

	public static SLiveTask create(String id, String actorId, String name, String des, String cover, String video, Integer duration, String taskId, Integer type, Long startTime)
	{
		SLiveTask obj = new SLiveTask();
		obj.id = id;
		obj.actorId = actorId;
		obj.name = name;
		obj.des = des;
		obj.cover = cover;
		obj.video = video;
		obj.duration = duration;
		obj.taskId = taskId;
		obj.type = type;
		obj.startTime = startTime;
		return obj;
	}

	public SLiveTask clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SLiveTask _other_)
	{
		this.id = _other_.id;
		this.actorId = _other_.actorId;
		this.name = _other_.name;
		this.des = _other_.des;
		this.cover = _other_.cover;
		this.video = _other_.video;
		this.duration = _other_.duration;
		this.taskId = _other_.taskId;
		this.type = _other_.type;
		this.userIds = _other_.userIds;
		this.startTime = _other_.startTime;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("id"))
			id = _json_.getString("id");
		if (_json_.containsKey("actorId"))
			actorId = _json_.getString("actorId");
		if (_json_.containsKey("name"))
			name = _json_.getString("name");
		if (_json_.containsKey("des"))
			des = _json_.getString("des");
		if (_json_.containsKey("cover"))
			cover = _json_.getString("cover");
		if (_json_.containsKey("video"))
			video = _json_.getString("video");
		if (_json_.containsKey("duration"))
			duration = _json_.getInteger("duration");
		if (_json_.containsKey("taskId"))
			taskId = _json_.getString("taskId");
		if (_json_.containsKey("type"))
			type = _json_.getInteger("type");
		if (_json_.containsKey("userIds"))
			userIds = AppDataTool.loadStringList(AppDataTool.getJSONArray(_json_, "userIds"));
		if (_json_.containsKey("startTime"))
			startTime = _json_.getLong("startTime");
	}

	public static SLiveTask load(String str)
	{
		try
		{
			SLiveTask obj = new SLiveTask();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SLiveTask load(JSONObject json)
	{
		try
		{
			SLiveTask obj = new SLiveTask();
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
			if (id != null)
				_json_.put("id", id);
			if (actorId != null)
				_json_.put("actorId", actorId);
			if (name != null)
				_json_.put("name", name);
			if (des != null)
				_json_.put("des", des);
			if (cover != null)
				_json_.put("cover", cover);
			if (video != null)
				_json_.put("video", video);
			if (duration != null)
				_json_.put("duration", duration);
			if (taskId != null)
				_json_.put("taskId", taskId);
			if (type != null)
				_json_.put("type", type);
			if (userIds != null)
				_json_.put("userIds", AppDataTool.saveStringList(userIds));
			if (startTime != null)
				_json_.put("startTime", String.valueOf(startTime));
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SLiveTask> loadList(JSONArray ja)
	{
		try
		{
			List<SLiveTask> list = new ArrayList<SLiveTask>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SLiveTask item = SLiveTask.load(jo);
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

	public static JSONArray saveList(List<SLiveTask> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SLiveTask item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(LiveTask _proto_)
	{
		if (_proto_.hasId())
			id = _proto_.getId();
		if (_proto_.hasActorId())
			actorId = _proto_.getActorId();
		if (_proto_.hasName())
			name = _proto_.getName();
		if (_proto_.hasDes())
			des = _proto_.getDes();
		if (_proto_.hasCover())
			cover = _proto_.getCover();
		if (_proto_.hasVideo())
			video = _proto_.getVideo();
		if (_proto_.hasDuration())
			duration = _proto_.getDuration();
		if (_proto_.hasTaskId())
			taskId = _proto_.getTaskId();
		if (_proto_.hasType())
			type = _proto_.getType().getNumber();
		for (int i = 0; i < _proto_.getUserIdsCount(); i++)
			userIds.add(_proto_.getUserIds(i));
		if (_proto_.hasStartTime())
			startTime = _proto_.getStartTime();
	}

	public static SLiveTask load(byte[] bytes)
	{
		try
		{
			SLiveTask obj = new SLiveTask();
			obj.parse(LiveTask.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SLiveTask load(LiveTask proto)
	{
		try
		{
			SLiveTask obj = new SLiveTask();
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
	public LiveTask saveToProto()
	{
		LiveTask.Builder _builder_ = LiveTask.newBuilder();
		if (id != null && !id.equals(LiveTask.getDefaultInstance().getId()))
			_builder_.setId(id);
		if (actorId != null && !actorId.equals(LiveTask.getDefaultInstance().getActorId()))
			_builder_.setActorId(actorId);
		if (name != null && !name.equals(LiveTask.getDefaultInstance().getName()))
			_builder_.setName(name);
		if (des != null && !des.equals(LiveTask.getDefaultInstance().getDes()))
			_builder_.setDes(des);
		if (cover != null && !cover.equals(LiveTask.getDefaultInstance().getCover()))
			_builder_.setCover(cover);
		if (video != null && !video.equals(LiveTask.getDefaultInstance().getVideo()))
			_builder_.setVideo(video);
		if (duration != null && !duration.equals(LiveTask.getDefaultInstance().getDuration()))
			_builder_.setDuration(duration);
		if (taskId != null && !taskId.equals(LiveTask.getDefaultInstance().getTaskId()))
			_builder_.setTaskId(taskId);
		if (type != null && LiveTask.getDefaultInstance().getType().getNumber() != type)
			_builder_.setType(ProtocolLive.LiveType.valueOf(type));
		if (userIds != null)
			for (String _value_ : userIds)
				_builder_.addUserIds(_value_);
		if (startTime != null && !startTime.equals(LiveTask.getDefaultInstance().getStartTime()))
			_builder_.setStartTime(startTime);
		return _builder_.build();
	}
}
