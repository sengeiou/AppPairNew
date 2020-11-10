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
import com.lys.protobuf.ProtocolPair.Response_AddUser;

public class SResponse_AddUser extends SPTData<Response_AddUser>
{
	private static final SResponse_AddUser DefaultInstance = new SResponse_AddUser();

	public String userId = null;
	public String psw = null;

	public static SResponse_AddUser create(String userId, String psw)
	{
		SResponse_AddUser obj = new SResponse_AddUser();
		obj.userId = userId;
		obj.psw = psw;
		return obj;
	}

	public SResponse_AddUser clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SResponse_AddUser _other_)
	{
		this.userId = _other_.userId;
		this.psw = _other_.psw;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("userId"))
			userId = _json_.getString("userId");
		if (_json_.containsKey("psw"))
			psw = _json_.getString("psw");
	}

	public static SResponse_AddUser load(String str)
	{
		try
		{
			SResponse_AddUser obj = new SResponse_AddUser();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_AddUser load(JSONObject json)
	{
		try
		{
			SResponse_AddUser obj = new SResponse_AddUser();
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
			if (psw != null)
				_json_.put("psw", psw);
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SResponse_AddUser> loadList(JSONArray ja)
	{
		try
		{
			List<SResponse_AddUser> list = new ArrayList<SResponse_AddUser>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SResponse_AddUser item = SResponse_AddUser.load(jo);
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

	public static JSONArray saveList(List<SResponse_AddUser> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SResponse_AddUser item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Response_AddUser _proto_)
	{
		if (_proto_.hasUserId())
			userId = _proto_.getUserId();
		if (_proto_.hasPsw())
			psw = _proto_.getPsw();
	}

	public static SResponse_AddUser load(byte[] bytes)
	{
		try
		{
			SResponse_AddUser obj = new SResponse_AddUser();
			obj.parse(Response_AddUser.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_AddUser load(Response_AddUser proto)
	{
		try
		{
			SResponse_AddUser obj = new SResponse_AddUser();
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
	public Response_AddUser saveToProto()
	{
		Response_AddUser.Builder _builder_ = Response_AddUser.newBuilder();
		if (userId != null && !userId.equals(Response_AddUser.getDefaultInstance().getUserId()))
			_builder_.setUserId(userId);
		if (psw != null && !psw.equals(Response_AddUser.getDefaultInstance().getPsw()))
			_builder_.setPsw(psw);
		return _builder_.build();
	}
}
