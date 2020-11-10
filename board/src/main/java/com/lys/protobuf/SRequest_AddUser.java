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
import com.lys.protobuf.ProtocolPair.Request_AddUser;

// ---------------------- 添加用户 --------------------------
public class SRequest_AddUser extends SPTData<Request_AddUser>
{
	private static final SRequest_AddUser DefaultInstance = new SRequest_AddUser();

	public String userId = null;
	public /*SUserType*/ Integer userType = Request_AddUser.getDefaultInstance().getUserType().getNumber();
	public String psw = null;
	public String name = null;
	public String head = null;
	public /*SSex*/ Integer sex = Request_AddUser.getDefaultInstance().getSex().getNumber();
	public Integer grade = 0;
	public Integer from = 0; // 0:pad   1:phone

	public static SRequest_AddUser create(String userId, Integer userType, String psw, String name, String head, Integer sex, Integer grade, Integer from)
	{
		SRequest_AddUser obj = new SRequest_AddUser();
		obj.userId = userId;
		obj.userType = userType;
		obj.psw = psw;
		obj.name = name;
		obj.head = head;
		obj.sex = sex;
		obj.grade = grade;
		obj.from = from;
		return obj;
	}

	public SRequest_AddUser clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SRequest_AddUser _other_)
	{
		this.userId = _other_.userId;
		this.userType = _other_.userType;
		this.psw = _other_.psw;
		this.name = _other_.name;
		this.head = _other_.head;
		this.sex = _other_.sex;
		this.grade = _other_.grade;
		this.from = _other_.from;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("userId"))
			userId = _json_.getString("userId");
		if (_json_.containsKey("userType"))
			userType = _json_.getInteger("userType");
		if (_json_.containsKey("psw"))
			psw = _json_.getString("psw");
		if (_json_.containsKey("name"))
			name = _json_.getString("name");
		if (_json_.containsKey("head"))
			head = _json_.getString("head");
		if (_json_.containsKey("sex"))
			sex = _json_.getInteger("sex");
		if (_json_.containsKey("grade"))
			grade = _json_.getInteger("grade");
		if (_json_.containsKey("from"))
			from = _json_.getInteger("from");
	}

	public static SRequest_AddUser load(String str)
	{
		try
		{
			SRequest_AddUser obj = new SRequest_AddUser();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_AddUser load(JSONObject json)
	{
		try
		{
			SRequest_AddUser obj = new SRequest_AddUser();
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
			if (userType != null)
				_json_.put("userType", userType);
			if (psw != null)
				_json_.put("psw", psw);
			if (name != null)
				_json_.put("name", name);
			if (head != null)
				_json_.put("head", head);
			if (sex != null)
				_json_.put("sex", sex);
			if (grade != null)
				_json_.put("grade", grade);
			if (from != null)
				_json_.put("from", from);
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SRequest_AddUser> loadList(JSONArray ja)
	{
		try
		{
			List<SRequest_AddUser> list = new ArrayList<SRequest_AddUser>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SRequest_AddUser item = SRequest_AddUser.load(jo);
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

	public static JSONArray saveList(List<SRequest_AddUser> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SRequest_AddUser item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Request_AddUser _proto_)
	{
		if (_proto_.hasUserId())
			userId = _proto_.getUserId();
		if (_proto_.hasUserType())
			userType = _proto_.getUserType().getNumber();
		if (_proto_.hasPsw())
			psw = _proto_.getPsw();
		if (_proto_.hasName())
			name = _proto_.getName();
		if (_proto_.hasHead())
			head = _proto_.getHead();
		if (_proto_.hasSex())
			sex = _proto_.getSex().getNumber();
		if (_proto_.hasGrade())
			grade = _proto_.getGrade();
		if (_proto_.hasFrom())
			from = _proto_.getFrom();
	}

	public static SRequest_AddUser load(byte[] bytes)
	{
		try
		{
			SRequest_AddUser obj = new SRequest_AddUser();
			obj.parse(Request_AddUser.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_AddUser load(Request_AddUser proto)
	{
		try
		{
			SRequest_AddUser obj = new SRequest_AddUser();
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
	public Request_AddUser saveToProto()
	{
		Request_AddUser.Builder _builder_ = Request_AddUser.newBuilder();
		if (userId != null && !userId.equals(Request_AddUser.getDefaultInstance().getUserId()))
			_builder_.setUserId(userId);
		if (userType != null && Request_AddUser.getDefaultInstance().getUserType().getNumber() != userType)
			_builder_.setUserType(ProtocolPair.UserType.valueOf(userType));
		if (psw != null && !psw.equals(Request_AddUser.getDefaultInstance().getPsw()))
			_builder_.setPsw(psw);
		if (name != null && !name.equals(Request_AddUser.getDefaultInstance().getName()))
			_builder_.setName(name);
		if (head != null && !head.equals(Request_AddUser.getDefaultInstance().getHead()))
			_builder_.setHead(head);
		if (sex != null && Request_AddUser.getDefaultInstance().getSex().getNumber() != sex)
			_builder_.setSex(ProtocolPair.Sex.valueOf(sex));
		if (grade != null && !grade.equals(Request_AddUser.getDefaultInstance().getGrade()))
			_builder_.setGrade(grade);
		if (from != null && !from.equals(Request_AddUser.getDefaultInstance().getFrom()))
			_builder_.setFrom(from);
		return _builder_.build();
	}
}
