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
import com.lys.protobuf.ProtocolPair.PTask;

public class SPTask extends SPTData<PTask>
{
	private static final SPTask DefaultInstance = new SPTask();

	public String id = null;
	public String userId = null;
	public SUser user = null;
	public String sendUserId = null;
	public SUser sendUser = null;
	public /*SPTaskType*/ Integer type = PTask.getDefaultInstance().getType().getNumber();
	public /*SPJobType*/ Integer jobType = PTask.getDefaultInstance().getJobType().getNumber();
	public String group = null;
	public String name = null;
	public String note = null;
	public Long createTime = 0L;
	public /*SPTaskState*/ Integer state = PTask.getDefaultInstance().getState().getNumber();
	public String text = null;
	public String comment = null;
	public Long overTime = 0L;
	public Integer score = 0;
	public Integer open = 0;
	public Integer timesForWeb = 0;
	public Long lastModifyTime = 0L;
	public String cover = null;

	public static SPTask create(String id, String userId, SUser user, String sendUserId, SUser sendUser, Integer type, Integer jobType, String group, String name, String note, Long createTime, Integer state, String text, String comment, Long overTime, Integer score, Integer open, Integer timesForWeb, Long lastModifyTime, String cover)
	{
		SPTask obj = new SPTask();
		obj.id = id;
		obj.userId = userId;
		obj.user = user;
		obj.sendUserId = sendUserId;
		obj.sendUser = sendUser;
		obj.type = type;
		obj.jobType = jobType;
		obj.group = group;
		obj.name = name;
		obj.note = note;
		obj.createTime = createTime;
		obj.state = state;
		obj.text = text;
		obj.comment = comment;
		obj.overTime = overTime;
		obj.score = score;
		obj.open = open;
		obj.timesForWeb = timesForWeb;
		obj.lastModifyTime = lastModifyTime;
		obj.cover = cover;
		return obj;
	}

	public SPTask clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SPTask _other_)
	{
		this.id = _other_.id;
		this.userId = _other_.userId;
		this.user = _other_.user;
		this.sendUserId = _other_.sendUserId;
		this.sendUser = _other_.sendUser;
		this.type = _other_.type;
		this.jobType = _other_.jobType;
		this.group = _other_.group;
		this.name = _other_.name;
		this.note = _other_.note;
		this.createTime = _other_.createTime;
		this.state = _other_.state;
		this.text = _other_.text;
		this.comment = _other_.comment;
		this.overTime = _other_.overTime;
		this.score = _other_.score;
		this.open = _other_.open;
		this.timesForWeb = _other_.timesForWeb;
		this.lastModifyTime = _other_.lastModifyTime;
		this.cover = _other_.cover;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("id"))
			id = _json_.getString("id");
		if (_json_.containsKey("userId"))
			userId = _json_.getString("userId");
		if (_json_.containsKey("user"))
			user = SUser.load(_json_.getJSONObject("user"));
		if (_json_.containsKey("sendUserId"))
			sendUserId = _json_.getString("sendUserId");
		if (_json_.containsKey("sendUser"))
			sendUser = SUser.load(_json_.getJSONObject("sendUser"));
		if (_json_.containsKey("type"))
			type = _json_.getInteger("type");
		if (_json_.containsKey("jobType"))
			jobType = _json_.getInteger("jobType");
		if (_json_.containsKey("group"))
			group = _json_.getString("group");
		if (_json_.containsKey("name"))
			name = _json_.getString("name");
		if (_json_.containsKey("note"))
			note = _json_.getString("note");
		if (_json_.containsKey("createTime"))
			createTime = _json_.getLong("createTime");
		if (_json_.containsKey("state"))
			state = _json_.getInteger("state");
		if (_json_.containsKey("text"))
			text = _json_.getString("text");
		if (_json_.containsKey("comment"))
			comment = _json_.getString("comment");
		if (_json_.containsKey("overTime"))
			overTime = _json_.getLong("overTime");
		if (_json_.containsKey("score"))
			score = _json_.getInteger("score");
		if (_json_.containsKey("open"))
			open = _json_.getInteger("open");
		if (_json_.containsKey("timesForWeb"))
			timesForWeb = _json_.getInteger("timesForWeb");
		if (_json_.containsKey("lastModifyTime"))
			lastModifyTime = _json_.getLong("lastModifyTime");
		if (_json_.containsKey("cover"))
			cover = _json_.getString("cover");
	}

	public static SPTask load(String str)
	{
		try
		{
			SPTask obj = new SPTask();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SPTask load(JSONObject json)
	{
		try
		{
			SPTask obj = new SPTask();
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
			if (userId != null)
				_json_.put("userId", userId);
			if (user != null)
				_json_.put("user", user.saveToJson());
			if (sendUserId != null)
				_json_.put("sendUserId", sendUserId);
			if (sendUser != null)
				_json_.put("sendUser", sendUser.saveToJson());
			if (type != null)
				_json_.put("type", type);
			if (jobType != null)
				_json_.put("jobType", jobType);
			if (group != null)
				_json_.put("group", group);
			if (name != null)
				_json_.put("name", name);
			if (note != null)
				_json_.put("note", note);
			if (createTime != null)
				_json_.put("createTime", String.valueOf(createTime));
			if (state != null)
				_json_.put("state", state);
			if (text != null)
				_json_.put("text", text);
			if (comment != null)
				_json_.put("comment", comment);
			if (overTime != null)
				_json_.put("overTime", String.valueOf(overTime));
			if (score != null)
				_json_.put("score", score);
			if (open != null)
				_json_.put("open", open);
			if (timesForWeb != null)
				_json_.put("timesForWeb", timesForWeb);
			if (lastModifyTime != null)
				_json_.put("lastModifyTime", String.valueOf(lastModifyTime));
			if (cover != null)
				_json_.put("cover", cover);
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SPTask> loadList(JSONArray ja)
	{
		try
		{
			List<SPTask> list = new ArrayList<SPTask>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SPTask item = SPTask.load(jo);
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

	public static JSONArray saveList(List<SPTask> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SPTask item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(PTask _proto_)
	{
		if (_proto_.hasId())
			id = _proto_.getId();
		if (_proto_.hasUserId())
			userId = _proto_.getUserId();
		if (_proto_.hasUser())
			user = SUser.load(_proto_.getUser());
		if (_proto_.hasSendUserId())
			sendUserId = _proto_.getSendUserId();
		if (_proto_.hasSendUser())
			sendUser = SUser.load(_proto_.getSendUser());
		if (_proto_.hasType())
			type = _proto_.getType().getNumber();
		if (_proto_.hasJobType())
			jobType = _proto_.getJobType().getNumber();
		if (_proto_.hasGroup())
			group = _proto_.getGroup();
		if (_proto_.hasName())
			name = _proto_.getName();
		if (_proto_.hasNote())
			note = _proto_.getNote();
		if (_proto_.hasCreateTime())
			createTime = _proto_.getCreateTime();
		if (_proto_.hasState())
			state = _proto_.getState().getNumber();
		if (_proto_.hasText())
			text = _proto_.getText();
		if (_proto_.hasComment())
			comment = _proto_.getComment();
		if (_proto_.hasOverTime())
			overTime = _proto_.getOverTime();
		if (_proto_.hasScore())
			score = _proto_.getScore();
		if (_proto_.hasOpen())
			open = _proto_.getOpen();
		if (_proto_.hasTimesForWeb())
			timesForWeb = _proto_.getTimesForWeb();
		if (_proto_.hasLastModifyTime())
			lastModifyTime = _proto_.getLastModifyTime();
		if (_proto_.hasCover())
			cover = _proto_.getCover();
	}

	public static SPTask load(byte[] bytes)
	{
		try
		{
			SPTask obj = new SPTask();
			obj.parse(PTask.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SPTask load(PTask proto)
	{
		try
		{
			SPTask obj = new SPTask();
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
	public PTask saveToProto()
	{
		PTask.Builder _builder_ = PTask.newBuilder();
		if (id != null && !id.equals(PTask.getDefaultInstance().getId()))
			_builder_.setId(id);
		if (userId != null && !userId.equals(PTask.getDefaultInstance().getUserId()))
			_builder_.setUserId(userId);
		if (user != null)
			_builder_.setUser(user.saveToProto());
		if (sendUserId != null && !sendUserId.equals(PTask.getDefaultInstance().getSendUserId()))
			_builder_.setSendUserId(sendUserId);
		if (sendUser != null)
			_builder_.setSendUser(sendUser.saveToProto());
		if (type != null && PTask.getDefaultInstance().getType().getNumber() != type)
			_builder_.setType(ProtocolPair.PTaskType.valueOf(type));
		if (jobType != null && PTask.getDefaultInstance().getJobType().getNumber() != jobType)
			_builder_.setJobType(ProtocolPair.PJobType.valueOf(jobType));
		if (group != null && !group.equals(PTask.getDefaultInstance().getGroup()))
			_builder_.setGroup(group);
		if (name != null && !name.equals(PTask.getDefaultInstance().getName()))
			_builder_.setName(name);
		if (note != null && !note.equals(PTask.getDefaultInstance().getNote()))
			_builder_.setNote(note);
		if (createTime != null && !createTime.equals(PTask.getDefaultInstance().getCreateTime()))
			_builder_.setCreateTime(createTime);
		if (state != null && PTask.getDefaultInstance().getState().getNumber() != state)
			_builder_.setState(ProtocolPair.PTaskState.valueOf(state));
		if (text != null && !text.equals(PTask.getDefaultInstance().getText()))
			_builder_.setText(text);
		if (comment != null && !comment.equals(PTask.getDefaultInstance().getComment()))
			_builder_.setComment(comment);
		if (overTime != null && !overTime.equals(PTask.getDefaultInstance().getOverTime()))
			_builder_.setOverTime(overTime);
		if (score != null && !score.equals(PTask.getDefaultInstance().getScore()))
			_builder_.setScore(score);
		if (open != null && !open.equals(PTask.getDefaultInstance().getOpen()))
			_builder_.setOpen(open);
		if (timesForWeb != null && !timesForWeb.equals(PTask.getDefaultInstance().getTimesForWeb()))
			_builder_.setTimesForWeb(timesForWeb);
		if (lastModifyTime != null && !lastModifyTime.equals(PTask.getDefaultInstance().getLastModifyTime()))
			_builder_.setLastModifyTime(lastModifyTime);
		if (cover != null && !cover.equals(PTask.getDefaultInstance().getCover()))
			_builder_.setCover(cover);
		return _builder_.build();
	}
}
