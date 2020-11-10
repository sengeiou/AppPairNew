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
import com.lys.protobuf.ProtocolTeach.Request_TeachConfirmByStudent;

// ---------------------- xxxxxxx --------------------------
public class SRequest_TeachConfirmByStudent extends SPTData<Request_TeachConfirmByStudent>
{
	private static final SRequest_TeachConfirmByStudent DefaultInstance = new SRequest_TeachConfirmByStudent();

	public String teachId = null;
	public String userId = null;
	public String targetId = null;
	public String confirmMsg = null;

	public static SRequest_TeachConfirmByStudent create(String teachId, String userId, String targetId, String confirmMsg)
	{
		SRequest_TeachConfirmByStudent obj = new SRequest_TeachConfirmByStudent();
		obj.teachId = teachId;
		obj.userId = userId;
		obj.targetId = targetId;
		obj.confirmMsg = confirmMsg;
		return obj;
	}

	public SRequest_TeachConfirmByStudent clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SRequest_TeachConfirmByStudent _other_)
	{
		this.teachId = _other_.teachId;
		this.userId = _other_.userId;
		this.targetId = _other_.targetId;
		this.confirmMsg = _other_.confirmMsg;
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
		if (_json_.containsKey("confirmMsg"))
			confirmMsg = _json_.getString("confirmMsg");
	}

	public static SRequest_TeachConfirmByStudent load(String str)
	{
		try
		{
			SRequest_TeachConfirmByStudent obj = new SRequest_TeachConfirmByStudent();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_TeachConfirmByStudent load(JSONObject json)
	{
		try
		{
			SRequest_TeachConfirmByStudent obj = new SRequest_TeachConfirmByStudent();
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
			if (confirmMsg != null)
				_json_.put("confirmMsg", confirmMsg);
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SRequest_TeachConfirmByStudent> loadList(JSONArray ja)
	{
		try
		{
			List<SRequest_TeachConfirmByStudent> list = new ArrayList<SRequest_TeachConfirmByStudent>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SRequest_TeachConfirmByStudent item = SRequest_TeachConfirmByStudent.load(jo);
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

	public static JSONArray saveList(List<SRequest_TeachConfirmByStudent> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SRequest_TeachConfirmByStudent item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Request_TeachConfirmByStudent _proto_)
	{
		if (_proto_.hasTeachId())
			teachId = _proto_.getTeachId();
		if (_proto_.hasUserId())
			userId = _proto_.getUserId();
		if (_proto_.hasTargetId())
			targetId = _proto_.getTargetId();
		if (_proto_.hasConfirmMsg())
			confirmMsg = _proto_.getConfirmMsg();
	}

	public static SRequest_TeachConfirmByStudent load(byte[] bytes)
	{
		try
		{
			SRequest_TeachConfirmByStudent obj = new SRequest_TeachConfirmByStudent();
			obj.parse(Request_TeachConfirmByStudent.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_TeachConfirmByStudent load(Request_TeachConfirmByStudent proto)
	{
		try
		{
			SRequest_TeachConfirmByStudent obj = new SRequest_TeachConfirmByStudent();
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
	public Request_TeachConfirmByStudent saveToProto()
	{
		Request_TeachConfirmByStudent.Builder _builder_ = Request_TeachConfirmByStudent.newBuilder();
		if (teachId != null && !teachId.equals(Request_TeachConfirmByStudent.getDefaultInstance().getTeachId()))
			_builder_.setTeachId(teachId);
		if (userId != null && !userId.equals(Request_TeachConfirmByStudent.getDefaultInstance().getUserId()))
			_builder_.setUserId(userId);
		if (targetId != null && !targetId.equals(Request_TeachConfirmByStudent.getDefaultInstance().getTargetId()))
			_builder_.setTargetId(targetId);
		if (confirmMsg != null && !confirmMsg.equals(Request_TeachConfirmByStudent.getDefaultInstance().getConfirmMsg()))
			_builder_.setConfirmMsg(confirmMsg);
		return _builder_.build();
	}
}
