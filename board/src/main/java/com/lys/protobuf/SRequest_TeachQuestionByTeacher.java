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
import com.lys.protobuf.ProtocolTeach.Request_TeachQuestionByTeacher;

// ---------------------- xxxxxxx --------------------------
public class SRequest_TeachQuestionByTeacher extends SPTData<Request_TeachQuestionByTeacher>
{
	private static final SRequest_TeachQuestionByTeacher DefaultInstance = new SRequest_TeachQuestionByTeacher();

	public String teachId = null;
	public String userId = null;
	public String questionHot = null;
	public String questionMind = null;
	public String questionLogic = null;
	public String questionOther = null;

	public static SRequest_TeachQuestionByTeacher create(String teachId, String userId, String questionHot, String questionMind, String questionLogic, String questionOther)
	{
		SRequest_TeachQuestionByTeacher obj = new SRequest_TeachQuestionByTeacher();
		obj.teachId = teachId;
		obj.userId = userId;
		obj.questionHot = questionHot;
		obj.questionMind = questionMind;
		obj.questionLogic = questionLogic;
		obj.questionOther = questionOther;
		return obj;
	}

	public SRequest_TeachQuestionByTeacher clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SRequest_TeachQuestionByTeacher _other_)
	{
		this.teachId = _other_.teachId;
		this.userId = _other_.userId;
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
		if (_json_.containsKey("questionHot"))
			questionHot = _json_.getString("questionHot");
		if (_json_.containsKey("questionMind"))
			questionMind = _json_.getString("questionMind");
		if (_json_.containsKey("questionLogic"))
			questionLogic = _json_.getString("questionLogic");
		if (_json_.containsKey("questionOther"))
			questionOther = _json_.getString("questionOther");
	}

	public static SRequest_TeachQuestionByTeacher load(String str)
	{
		try
		{
			SRequest_TeachQuestionByTeacher obj = new SRequest_TeachQuestionByTeacher();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_TeachQuestionByTeacher load(JSONObject json)
	{
		try
		{
			SRequest_TeachQuestionByTeacher obj = new SRequest_TeachQuestionByTeacher();
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

	public static List<SRequest_TeachQuestionByTeacher> loadList(JSONArray ja)
	{
		try
		{
			List<SRequest_TeachQuestionByTeacher> list = new ArrayList<SRequest_TeachQuestionByTeacher>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SRequest_TeachQuestionByTeacher item = SRequest_TeachQuestionByTeacher.load(jo);
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

	public static JSONArray saveList(List<SRequest_TeachQuestionByTeacher> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SRequest_TeachQuestionByTeacher item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Request_TeachQuestionByTeacher _proto_)
	{
		if (_proto_.hasTeachId())
			teachId = _proto_.getTeachId();
		if (_proto_.hasUserId())
			userId = _proto_.getUserId();
		if (_proto_.hasQuestionHot())
			questionHot = _proto_.getQuestionHot();
		if (_proto_.hasQuestionMind())
			questionMind = _proto_.getQuestionMind();
		if (_proto_.hasQuestionLogic())
			questionLogic = _proto_.getQuestionLogic();
		if (_proto_.hasQuestionOther())
			questionOther = _proto_.getQuestionOther();
	}

	public static SRequest_TeachQuestionByTeacher load(byte[] bytes)
	{
		try
		{
			SRequest_TeachQuestionByTeacher obj = new SRequest_TeachQuestionByTeacher();
			obj.parse(Request_TeachQuestionByTeacher.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_TeachQuestionByTeacher load(Request_TeachQuestionByTeacher proto)
	{
		try
		{
			SRequest_TeachQuestionByTeacher obj = new SRequest_TeachQuestionByTeacher();
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
	public Request_TeachQuestionByTeacher saveToProto()
	{
		Request_TeachQuestionByTeacher.Builder _builder_ = Request_TeachQuestionByTeacher.newBuilder();
		if (teachId != null && !teachId.equals(Request_TeachQuestionByTeacher.getDefaultInstance().getTeachId()))
			_builder_.setTeachId(teachId);
		if (userId != null && !userId.equals(Request_TeachQuestionByTeacher.getDefaultInstance().getUserId()))
			_builder_.setUserId(userId);
		if (questionHot != null && !questionHot.equals(Request_TeachQuestionByTeacher.getDefaultInstance().getQuestionHot()))
			_builder_.setQuestionHot(questionHot);
		if (questionMind != null && !questionMind.equals(Request_TeachQuestionByTeacher.getDefaultInstance().getQuestionMind()))
			_builder_.setQuestionMind(questionMind);
		if (questionLogic != null && !questionLogic.equals(Request_TeachQuestionByTeacher.getDefaultInstance().getQuestionLogic()))
			_builder_.setQuestionLogic(questionLogic);
		if (questionOther != null && !questionOther.equals(Request_TeachQuestionByTeacher.getDefaultInstance().getQuestionOther()))
			_builder_.setQuestionOther(questionOther);
		return _builder_.build();
	}
}
