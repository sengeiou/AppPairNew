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
import com.lys.protobuf.ProtocolTeach.Request_TeachQuestionByStudent;

// ---------------------- xxxxxxx --------------------------
public class SRequest_TeachQuestionByStudent extends SPTData<Request_TeachQuestionByStudent>
{
	private static final SRequest_TeachQuestionByStudent DefaultInstance = new SRequest_TeachQuestionByStudent();

	public String teachId = null;
	public String userId = null;
	public String targetId = null;
	public String questionMatch = null;
	public String questionDiff = null;
	public String questionGot = null;
	public String questionQuality = null;
	public String questionLike = null;

	public static SRequest_TeachQuestionByStudent create(String teachId, String userId, String targetId, String questionMatch, String questionDiff, String questionGot, String questionQuality, String questionLike)
	{
		SRequest_TeachQuestionByStudent obj = new SRequest_TeachQuestionByStudent();
		obj.teachId = teachId;
		obj.userId = userId;
		obj.targetId = targetId;
		obj.questionMatch = questionMatch;
		obj.questionDiff = questionDiff;
		obj.questionGot = questionGot;
		obj.questionQuality = questionQuality;
		obj.questionLike = questionLike;
		return obj;
	}

	public SRequest_TeachQuestionByStudent clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SRequest_TeachQuestionByStudent _other_)
	{
		this.teachId = _other_.teachId;
		this.userId = _other_.userId;
		this.targetId = _other_.targetId;
		this.questionMatch = _other_.questionMatch;
		this.questionDiff = _other_.questionDiff;
		this.questionGot = _other_.questionGot;
		this.questionQuality = _other_.questionQuality;
		this.questionLike = _other_.questionLike;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("teachId"))
			teachId = _json_.getString("teachId");
		if (_json_.containsKey("userId"))
			userId = _json_.getString("userId");
		if (_json_.containsKey("targetId"))
			targetId = _json_.getString("targetId");
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
	}

	public static SRequest_TeachQuestionByStudent load(String str)
	{
		try
		{
			SRequest_TeachQuestionByStudent obj = new SRequest_TeachQuestionByStudent();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_TeachQuestionByStudent load(JSONObject json)
	{
		try
		{
			SRequest_TeachQuestionByStudent obj = new SRequest_TeachQuestionByStudent();
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
			if (targetId != null)
				_json_.put("targetId", targetId);
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
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SRequest_TeachQuestionByStudent> loadList(JSONArray ja)
	{
		try
		{
			List<SRequest_TeachQuestionByStudent> list = new ArrayList<SRequest_TeachQuestionByStudent>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SRequest_TeachQuestionByStudent item = SRequest_TeachQuestionByStudent.load(jo);
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

	public static JSONArray saveList(List<SRequest_TeachQuestionByStudent> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SRequest_TeachQuestionByStudent item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Request_TeachQuestionByStudent _proto_)
	{
		if (_proto_.hasTeachId())
			teachId = _proto_.getTeachId();
		if (_proto_.hasUserId())
			userId = _proto_.getUserId();
		if (_proto_.hasTargetId())
			targetId = _proto_.getTargetId();
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
	}

	public static SRequest_TeachQuestionByStudent load(byte[] bytes)
	{
		try
		{
			SRequest_TeachQuestionByStudent obj = new SRequest_TeachQuestionByStudent();
			obj.parse(Request_TeachQuestionByStudent.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_TeachQuestionByStudent load(Request_TeachQuestionByStudent proto)
	{
		try
		{
			SRequest_TeachQuestionByStudent obj = new SRequest_TeachQuestionByStudent();
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
	public Request_TeachQuestionByStudent saveToProto()
	{
		Request_TeachQuestionByStudent.Builder _builder_ = Request_TeachQuestionByStudent.newBuilder();
		if (teachId != null && !teachId.equals(Request_TeachQuestionByStudent.getDefaultInstance().getTeachId()))
			_builder_.setTeachId(teachId);
		if (userId != null && !userId.equals(Request_TeachQuestionByStudent.getDefaultInstance().getUserId()))
			_builder_.setUserId(userId);
		if (targetId != null && !targetId.equals(Request_TeachQuestionByStudent.getDefaultInstance().getTargetId()))
			_builder_.setTargetId(targetId);
		if (questionMatch != null && !questionMatch.equals(Request_TeachQuestionByStudent.getDefaultInstance().getQuestionMatch()))
			_builder_.setQuestionMatch(questionMatch);
		if (questionDiff != null && !questionDiff.equals(Request_TeachQuestionByStudent.getDefaultInstance().getQuestionDiff()))
			_builder_.setQuestionDiff(questionDiff);
		if (questionGot != null && !questionGot.equals(Request_TeachQuestionByStudent.getDefaultInstance().getQuestionGot()))
			_builder_.setQuestionGot(questionGot);
		if (questionQuality != null && !questionQuality.equals(Request_TeachQuestionByStudent.getDefaultInstance().getQuestionQuality()))
			_builder_.setQuestionQuality(questionQuality);
		if (questionLike != null && !questionLike.equals(Request_TeachQuestionByStudent.getDefaultInstance().getQuestionLike()))
			_builder_.setQuestionLike(questionLike);
		return _builder_.build();
	}
}
