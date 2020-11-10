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
import com.lys.protobuf.ProtocolPair.Request_UserLogin;

// ---------------------- 登录用户 --------------------------
public class SRequest_UserLogin extends SPTData<Request_UserLogin>
{
	private static final SRequest_UserLogin DefaultInstance = new SRequest_UserLogin();

	public String userId = null;
	public String psw = null;
	public String deviceId = null;
	public String clientVersion = null;

	public static SRequest_UserLogin create(String userId, String psw, String deviceId, String clientVersion)
	{
		SRequest_UserLogin obj = new SRequest_UserLogin();
		obj.userId = userId;
		obj.psw = psw;
		obj.deviceId = deviceId;
		obj.clientVersion = clientVersion;
		return obj;
	}

	public SRequest_UserLogin clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SRequest_UserLogin _other_)
	{
		this.userId = _other_.userId;
		this.psw = _other_.psw;
		this.deviceId = _other_.deviceId;
		this.clientVersion = _other_.clientVersion;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("userId"))
			userId = _json_.getString("userId");
		if (_json_.containsKey("psw"))
			psw = _json_.getString("psw");
		if (_json_.containsKey("deviceId"))
			deviceId = _json_.getString("deviceId");
		if (_json_.containsKey("clientVersion"))
			clientVersion = _json_.getString("clientVersion");
	}

	public static SRequest_UserLogin load(String str)
	{
		try
		{
			SRequest_UserLogin obj = new SRequest_UserLogin();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_UserLogin load(JSONObject json)
	{
		try
		{
			SRequest_UserLogin obj = new SRequest_UserLogin();
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
			if (deviceId != null)
				_json_.put("deviceId", deviceId);
			if (clientVersion != null)
				_json_.put("clientVersion", clientVersion);
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SRequest_UserLogin> loadList(JSONArray ja)
	{
		try
		{
			List<SRequest_UserLogin> list = new ArrayList<SRequest_UserLogin>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SRequest_UserLogin item = SRequest_UserLogin.load(jo);
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

	public static JSONArray saveList(List<SRequest_UserLogin> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SRequest_UserLogin item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Request_UserLogin _proto_)
	{
		if (_proto_.hasUserId())
			userId = _proto_.getUserId();
		if (_proto_.hasPsw())
			psw = _proto_.getPsw();
		if (_proto_.hasDeviceId())
			deviceId = _proto_.getDeviceId();
		if (_proto_.hasClientVersion())
			clientVersion = _proto_.getClientVersion();
	}

	public static SRequest_UserLogin load(byte[] bytes)
	{
		try
		{
			SRequest_UserLogin obj = new SRequest_UserLogin();
			obj.parse(Request_UserLogin.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_UserLogin load(Request_UserLogin proto)
	{
		try
		{
			SRequest_UserLogin obj = new SRequest_UserLogin();
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
	public Request_UserLogin saveToProto()
	{
		Request_UserLogin.Builder _builder_ = Request_UserLogin.newBuilder();
		if (userId != null && !userId.equals(Request_UserLogin.getDefaultInstance().getUserId()))
			_builder_.setUserId(userId);
		if (psw != null && !psw.equals(Request_UserLogin.getDefaultInstance().getPsw()))
			_builder_.setPsw(psw);
		if (deviceId != null && !deviceId.equals(Request_UserLogin.getDefaultInstance().getDeviceId()))
			_builder_.setDeviceId(deviceId);
		if (clientVersion != null && !clientVersion.equals(Request_UserLogin.getDefaultInstance().getClientVersion()))
			_builder_.setClientVersion(clientVersion);
		return _builder_.build();
	}
}
