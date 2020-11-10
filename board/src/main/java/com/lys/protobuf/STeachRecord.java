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
import com.lys.protobuf.ProtocolTeach.TeachRecord;

public class STeachRecord extends SPTData<TeachRecord>
{
	private static final STeachRecord DefaultInstance = new STeachRecord();

	public String teachId = null;
	public String userId = null;
	public Boolean isHost = false;
	public Integer targetCount = 0;
	public List<String> targetIds = new ArrayList<String>();
	public List<SUser> targets = new ArrayList<SUser>();
	public String taskId = null;
	public SPTask task = null;
	public Long startTime = 0L;
	public Long overTime = 0L;
	public List<STeachPage> teachPages = new ArrayList<STeachPage>();
	public String confirmMsg = null;
	public String questionMatch = null;
	public String questionDiff = null;
	public String questionGot = null;
	public String questionQuality = null;
	public String questionLike = null;
	public String questionHot = null;
	public String questionMind = null;
	public String questionLogic = null;
	public String questionOther = null;

	public static STeachRecord create(String teachId, String userId, Boolean isHost, Integer targetCount, String taskId, SPTask task, Long startTime, Long overTime, String confirmMsg, String questionMatch, String questionDiff, String questionGot, String questionQuality, String questionLike, String questionHot, String questionMind, String questionLogic, String questionOther)
	{
		STeachRecord obj = new STeachRecord();
		obj.teachId = teachId;
		obj.userId = userId;
		obj.isHost = isHost;
		obj.targetCount = targetCount;
		obj.taskId = taskId;
		obj.task = task;
		obj.startTime = startTime;
		obj.overTime = overTime;
		obj.confirmMsg = confirmMsg;
		obj.questionMatch = questionMatch;
		obj.questionDiff = questionDiff;
		obj.questionGot = questionGot;
		obj.questionQuality = questionQuality;
		obj.questionLike = questionLike;
		obj.questionHot = questionHot;
		obj.questionMind = questionMind;
		obj.questionLogic = questionLogic;
		obj.questionOther = questionOther;
		return obj;
	}

	public STeachRecord clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(STeachRecord _other_)
	{
		this.teachId = _other_.teachId;
		this.userId = _other_.userId;
		this.isHost = _other_.isHost;
		this.targetCount = _other_.targetCount;
		this.targetIds = _other_.targetIds;
		this.targets = _other_.targets;
		this.taskId = _other_.taskId;
		this.task = _other_.task;
		this.startTime = _other_.startTime;
		this.overTime = _other_.overTime;
		this.teachPages = _other_.teachPages;
		this.confirmMsg = _other_.confirmMsg;
		this.questionMatch = _other_.questionMatch;
		this.questionDiff = _other_.questionDiff;
		this.questionGot = _other_.questionGot;
		this.questionQuality = _other_.questionQuality;
		this.questionLike = _other_.questionLike;
		this.questionHot = _other_.questionHot;
		this.questionMind = _other_.questionMind;
		this.questionLogic = _other_.questionLogic;
		this.questionOther = _other_.questionOther;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("teachId"))
			teachId = _json_.getString("teachId");
		if (_json_.containsKey("userId"))
			userId = _json_.getString("userId");
		if (_json_.containsKey("isHost"))
			isHost = _json_.getBoolean("isHost");
		if (_json_.containsKey("targetCount"))
			targetCount = _json_.getInteger("targetCount");
		if (_json_.containsKey("targetIds"))
			targetIds = AppDataTool.loadStringList(AppDataTool.getJSONArray(_json_, "targetIds"));
		if (_json_.containsKey("targets"))
			targets = SUser.loadList(_json_.getJSONArray("targets"));
		if (_json_.containsKey("taskId"))
			taskId = _json_.getString("taskId");
		if (_json_.containsKey("task"))
			task = SPTask.load(_json_.getJSONObject("task"));
		if (_json_.containsKey("startTime"))
			startTime = _json_.getLong("startTime");
		if (_json_.containsKey("overTime"))
			overTime = _json_.getLong("overTime");
		if (_json_.containsKey("teachPages"))
			teachPages = STeachPage.loadList(_json_.getJSONArray("teachPages"));
		if (_json_.containsKey("confirmMsg"))
			confirmMsg = _json_.getString("confirmMsg");
		if (_json_.containsKey("questionMatch"))
			questionMatch = _json_.getString("questionMatch");
		if (_json_.containsKey("questionDiff"))
			questionDiff = _json_.getString("questionDiff");
		if (_json_.containsKey("questionGot"))
			questionGot = _json_.getString("questionGot");
		if (_json_.containsKey("questionQuality"))
			questionQuality = _json_.getString("questionQuality");
		if (_json_.containsKey("questionLike"))
			questionLike = _json_.getString("questionLike");
		if (_json_.containsKey("questionHot"))
			questionHot = _json_.getString("questionHot");
		if (_json_.containsKey("questionMind"))
			questionMind = _json_.getString("questionMind");
		if (_json_.containsKey("questionLogic"))
			questionLogic = _json_.getString("questionLogic");
		if (_json_.containsKey("questionOther"))
			questionOther = _json_.getString("questionOther");
	}

	public static STeachRecord load(String str)
	{
		try
		{
			STeachRecord obj = new STeachRecord();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static STeachRecord load(JSONObject json)
	{
		try
		{
			STeachRecord obj = new STeachRecord();
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
			if (isHost != null)
				_json_.put("isHost", isHost);
			if (targetCount != null)
				_json_.put("targetCount", targetCount);
			if (targetIds != null)
				_json_.put("targetIds", AppDataTool.saveStringList(targetIds));
			if (targets != null)
				_json_.put("targets", SUser.saveList(targets));
			if (taskId != null)
				_json_.put("taskId", taskId);
			if (task != null)
				_json_.put("task", task.saveToJson());
			if (startTime != null)
				_json_.put("startTime", String.valueOf(startTime));
			if (overTime != null)
				_json_.put("overTime", String.valueOf(overTime));
			if (teachPages != null)
				_json_.put("teachPages", STeachPage.saveList(teachPages));
			if (confirmMsg != null)
				_json_.put("confirmMsg", confirmMsg);
			if (questionMatch != null)
				_json_.put("questionMatch", questionMatch);
			if (questionDiff != null)
				_json_.put("questionDiff", questionDiff);
			if (questionGot != null)
				_json_.put("questionGot", questionGot);
			if (questionQuality != null)
				_json_.put("questionQuality", questionQuality);
			if (questionLike != null)
				_json_.put("questionLike", questionLike);
			if (questionHot != null)
				_json_.put("questionHot", questionHot);
			if (questionMind != null)
				_json_.put("questionMind", questionMind);
			if (questionLogic != null)
				_json_.put("questionLogic", questionLogic);
			if (questionOther != null)
				_json_.put("questionOther", questionOther);
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<STeachRecord> loadList(JSONArray ja)
	{
		try
		{
			List<STeachRecord> list = new ArrayList<STeachRecord>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				STeachRecord item = STeachRecord.load(jo);
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

	public static JSONArray saveList(List<STeachRecord> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			STeachRecord item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(TeachRecord _proto_)
	{
		if (_proto_.hasTeachId())
			teachId = _proto_.getTeachId();
		if (_proto_.hasUserId())
			userId = _proto_.getUserId();
		if (_proto_.hasIsHost())
			isHost = _proto_.getIsHost();
		if (_proto_.hasTargetCount())
			targetCount = _proto_.getTargetCount();
		for (int i = 0; i < _proto_.getTargetIdsCount(); i++)
			targetIds.add(_proto_.getTargetIds(i));
		for (int i = 0; i < _proto_.getTargetsCount(); i++)
			targets.add(SUser.load(_proto_.getTargets(i)));
		if (_proto_.hasTaskId())
			taskId = _proto_.getTaskId();
		if (_proto_.hasTask())
			task = SPTask.load(_proto_.getTask());
		if (_proto_.hasStartTime())
			startTime = _proto_.getStartTime();
		if (_proto_.hasOverTime())
			overTime = _proto_.getOverTime();
		for (int i = 0; i < _proto_.getTeachPagesCount(); i++)
			teachPages.add(STeachPage.load(_proto_.getTeachPages(i)));
		if (_proto_.hasConfirmMsg())
			confirmMsg = _proto_.getConfirmMsg();
		if (_proto_.hasQuestionMatch())
			questionMatch = _proto_.getQuestionMatch();
		if (_proto_.hasQuestionDiff())
			questionDiff = _proto_.getQuestionDiff();
		if (_proto_.hasQuestionGot())
			questionGot = _proto_.getQuestionGot();
		if (_proto_.hasQuestionQuality())
			questionQuality = _proto_.getQuestionQuality();
		if (_proto_.hasQuestionLike())
			questionLike = _proto_.getQuestionLike();
		if (_proto_.hasQuestionHot())
			questionHot = _proto_.getQuestionHot();
		if (_proto_.hasQuestionMind())
			questionMind = _proto_.getQuestionMind();
		if (_proto_.hasQuestionLogic())
			questionLogic = _proto_.getQuestionLogic();
		if (_proto_.hasQuestionOther())
			questionOther = _proto_.getQuestionOther();
	}

	public static STeachRecord load(byte[] bytes)
	{
		try
		{
			STeachRecord obj = new STeachRecord();
			obj.parse(TeachRecord.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static STeachRecord load(TeachRecord proto)
	{
		try
		{
			STeachRecord obj = new STeachRecord();
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
	public TeachRecord saveToProto()
	{
		TeachRecord.Builder _builder_ = TeachRecord.newBuilder();
		if (teachId != null && !teachId.equals(TeachRecord.getDefaultInstance().getTeachId()))
			_builder_.setTeachId(teachId);
		if (userId != null && !userId.equals(TeachRecord.getDefaultInstance().getUserId()))
			_builder_.setUserId(userId);
		if (isHost != null && !isHost.equals(TeachRecord.getDefaultInstance().getIsHost()))
			_builder_.setIsHost(isHost);
		if (targetCount != null && !targetCount.equals(TeachRecord.getDefaultInstance().getTargetCount()))
			_builder_.setTargetCount(targetCount);
		if (targetIds != null)
			for (String _value_ : targetIds)
				_builder_.addTargetIds(_value_);
		if (targets != null)
			for (SUser _value_ : targets)
				_builder_.addTargets(_value_.saveToProto());
		if (taskId != null && !taskId.equals(TeachRecord.getDefaultInstance().getTaskId()))
			_builder_.setTaskId(taskId);
		if (task != null)
			_builder_.setTask(task.saveToProto());
		if (startTime != null && !startTime.equals(TeachRecord.getDefaultInstance().getStartTime()))
			_builder_.setStartTime(startTime);
		if (overTime != null && !overTime.equals(TeachRecord.getDefaultInstance().getOverTime()))
			_builder_.setOverTime(overTime);
		if (teachPages != null)
			for (STeachPage _value_ : teachPages)
				_builder_.addTeachPages(_value_.saveToProto());
		if (confirmMsg != null && !confirmMsg.equals(TeachRecord.getDefaultInstance().getConfirmMsg()))
			_builder_.setConfirmMsg(confirmMsg);
		if (questionMatch != null && !questionMatch.equals(TeachRecord.getDefaultInstance().getQuestionMatch()))
			_builder_.setQuestionMatch(questionMatch);
		if (questionDiff != null && !questionDiff.equals(TeachRecord.getDefaultInstance().getQuestionDiff()))
			_builder_.setQuestionDiff(questionDiff);
		if (questionGot != null && !questionGot.equals(TeachRecord.getDefaultInstance().getQuestionGot()))
			_builder_.setQuestionGot(questionGot);
		if (questionQuality != null && !questionQuality.equals(TeachRecord.getDefaultInstance().getQuestionQuality()))
			_builder_.setQuestionQuality(questionQuality);
		if (questionLike != null && !questionLike.equals(TeachRecord.getDefaultInstance().getQuestionLike()))
			_builder_.setQuestionLike(questionLike);
		if (questionHot != null && !questionHot.equals(TeachRecord.getDefaultInstance().getQuestionHot()))
			_builder_.setQuestionHot(questionHot);
		if (questionMind != null && !questionMind.equals(TeachRecord.getDefaultInstance().getQuestionMind()))
			_builder_.setQuestionMind(questionMind);
		if (questionLogic != null && !questionLogic.equals(TeachRecord.getDefaultInstance().getQuestionLogic()))
			_builder_.setQuestionLogic(questionLogic);
		if (questionOther != null && !questionOther.equals(TeachRecord.getDefaultInstance().getQuestionOther()))
			_builder_.setQuestionOther(questionOther);
		return _builder_.build();
	}
}
