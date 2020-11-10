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
import com.lys.protobuf.ProtocolTeach.Request_TeachOverByStudent;

// ---------------------- xxxxxxx --------------------------
public class SRequest_TeachOverByStudent extends SPTData<Request_TeachOverByStudent>
{
	private static final SRequest_TeachOverByStudent DefaultInstance = new SRequest_TeachOverByStudent();

	public String teachId = null;
	public String userId = null;
	public String targetId = null;

	public static SRequest_TeachOverByStudent create(String teachId, String userId, String targetId)
	{
		SRequest_TeachOverByStudent obj = new SRequest_TeachOverByStudent();
		obj.teachId = teachId;
		obj.userId = userId;
		obj.targetId = targetId;
		return obj;
	}

	public SRequest_TeachOverByStudent clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SRequest_TeachOverByStudent _other_)
	{
		this.teachId = _other_.teachId;
		this.userId = _other_.userId;
		this.targetId = _other_.targetId;
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
	}

	public static SRequest_TeachOverByStudent load(String str)
	{
		try
		{
			SRequest_TeachOverByStudent obj = new SRequest_TeachOverByStudent();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_TeachOverByStudent load(JSONObject json)
	{
		try
		{
			SRequest_TeachOverByStudent obj = new SRequest_TeachOverByStudent();
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
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SRequest_TeachOverByStudent> loadList(JSONArray ja)
	{
		try
		{
			List<SRequest_TeachOverByStudent> list = new ArrayList<SRequest_TeachOverByStudent>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SRequest_TeachOverByStudent item = SRequest_TeachOverByStudent.load(jo);
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

	public static JSONArray saveList(List<SRequest_TeachOverByStudent> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SRequest_TeachOverByStudent item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Request_TeachOverByStudent _proto_)
	{
		if (_proto_.hasTeachId())
			teachId = _proto_.getTeachId();
		if (_proto_.hasUserId())
			userId = _proto_.getUserId();
		if (_proto_.hasTargetId())
			targetId = _proto_.getTargetId();
	}

	public static SRequest_TeachOverByStudent load(byte[] bytes)
	{
		try
		{
			SRequest_TeachOverByStudent obj = new SRequest_TeachOverByStudent();
			obj.parse(Request_TeachOverByStudent.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_TeachOverByStudent load(Request_TeachOverByStudent proto)
	{
		try
		{
			SRequest_TeachOverByStudent obj = new SRequest_TeachOverByStudent();
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
	public Request_TeachOverByStudent saveToProto()
	{
		Request_TeachOverByStudent.Builder _builder_ = Request_TeachOverByStudent.newBuilder();
		if (teachId != null && !teachId.equals(Request_TeachOverByStudent.getDefaultInstance().getTeachId()))
			_builder_.setTeachId(teachId);
		if (userId != null && !userId.equals(Request_TeachOverByStudent.getDefaultInstance().getUserId()))
			_builder_.setUserId(userId);
		if (targetId != null && !targetId.equals(Request_TeachOverByStudent.getDefaultInstance().getTargetId()))
			_builder_.setTargetId(targetId);
		return _builder_.build();
	}
}
