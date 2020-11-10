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
import com.lys.protobuf.ProtocolPair.Request_ModifyGrade;

// ---------------------- 修改XX --------------------------
public class SRequest_ModifyGrade extends SPTData<Request_ModifyGrade>
{
	private static final SRequest_ModifyGrade DefaultInstance = new SRequest_ModifyGrade();

	public String userId = null;
	public Integer grade = 0;

	public static SRequest_ModifyGrade create(String userId, Integer grade)
	{
		SRequest_ModifyGrade obj = new SRequest_ModifyGrade();
		obj.userId = userId;
		obj.grade = grade;
		return obj;
	}

	public SRequest_ModifyGrade clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SRequest_ModifyGrade _other_)
	{
		this.userId = _other_.userId;
		this.grade = _other_.grade;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("userId"))
			userId = _json_.getString("userId");
		if (_json_.containsKey("grade"))
			grade = _json_.getInteger("grade");
	}

	public static SRequest_ModifyGrade load(String str)
	{
		try
		{
			SRequest_ModifyGrade obj = new SRequest_ModifyGrade();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_ModifyGrade load(JSONObject json)
	{
		try
		{
			SRequest_ModifyGrade obj = new SRequest_ModifyGrade();
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
			if (grade != null)
				_json_.put("grade", grade);
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SRequest_ModifyGrade> loadList(JSONArray ja)
	{
		try
		{
			List<SRequest_ModifyGrade> list = new ArrayList<SRequest_ModifyGrade>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SRequest_ModifyGrade item = SRequest_ModifyGrade.load(jo);
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

	public static JSONArray saveList(List<SRequest_ModifyGrade> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SRequest_ModifyGrade item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Request_ModifyGrade _proto_)
	{
		if (_proto_.hasUserId())
			userId = _proto_.getUserId();
		if (_proto_.hasGrade())
			grade = _proto_.getGrade();
	}

	public static SRequest_ModifyGrade load(byte[] bytes)
	{
		try
		{
			SRequest_ModifyGrade obj = new SRequest_ModifyGrade();
			obj.parse(Request_ModifyGrade.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_ModifyGrade load(Request_ModifyGrade proto)
	{
		try
		{
			SRequest_ModifyGrade obj = new SRequest_ModifyGrade();
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
	public Request_ModifyGrade saveToProto()
	{
		Request_ModifyGrade.Builder _builder_ = Request_ModifyGrade.newBuilder();
		if (userId != null && !userId.equals(Request_ModifyGrade.getDefaultInstance().getUserId()))
			_builder_.setUserId(userId);
		if (grade != null && !grade.equals(Request_ModifyGrade.getDefaultInstance().getGrade()))
			_builder_.setGrade(grade);
		return _builder_.build();
	}
}
