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
import com.lys.protobuf.ProtocolCommon.Protocol;

public class SProtocol extends SPTData<Protocol>
{
	private static final SProtocol DefaultInstance = new SProtocol();

	public Integer code = 0;
	public Integer handleId = 0;
	public String msg = null;
	public String data = null;
	public String token = null;
	public String deviceId = null;
	public String userId = null;
	public String userName = null;

	public static SProtocol create(Integer code, Integer handleId, String msg, String data, String token, String deviceId, String userId, String userName)
	{
		SProtocol obj = new SProtocol();
		obj.code = code;
		obj.handleId = handleId;
		obj.msg = msg;
		obj.data = data;
		obj.token = token;
		obj.deviceId = deviceId;
		obj.userId = userId;
		obj.userName = userName;
		return obj;
	}

	public SProtocol clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SProtocol _other_)
	{
		this.code = _other_.code;
		this.handleId = _other_.handleId;
		this.msg = _other_.msg;
		this.data = _other_.data;
		this.token = _other_.token;
		this.deviceId = _other_.deviceId;
		this.userId = _other_.userId;
		this.userName = _other_.userName;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("code"))
			code = _json_.getInteger("code");
		if (_json_.containsKey("handleId"))
			handleId = _json_.getInteger("handleId");
		if (_json_.containsKey("msg"))
			msg = _json_.getString("msg");
		if (_json_.containsKey("data"))
			data = _json_.getString("data");
		if (_json_.containsKey("token"))
			token = _json_.getString("token");
		if (_json_.containsKey("deviceId"))
			deviceId = _json_.getString("deviceId");
		if (_json_.containsKey("userId"))
			userId = _json_.getString("userId");
		if (_json_.containsKey("userName"))
			userName = _json_.getString("userName");
	}

	public static SProtocol load(String str)
	{
		try
		{
			SProtocol obj = new SProtocol();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SProtocol load(JSONObject json)
	{
		try
		{
			SProtocol obj = new SProtocol();
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
			if (code != null)
				_json_.put("code", code);
			if (handleId != null)
				_json_.put("handleId", handleId);
			if (msg != null)
				_json_.put("msg", msg);
			if (data != null)
				_json_.put("data", data);
			if (token != null)
				_json_.put("token", token);
			if (deviceId != null)
				_json_.put("deviceId", deviceId);
			if (userId != null)
				_json_.put("userId", userId);
			if (userName != null)
				_json_.put("userName", userName);
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SProtocol> loadList(JSONArray ja)
	{
		try
		{
			List<SProtocol> list = new ArrayList<SProtocol>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SProtocol item = SProtocol.load(jo);
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

	public static JSONArray saveList(List<SProtocol> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SProtocol item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Protocol _proto_)
	{
		if (_proto_.hasCode())
			code = _proto_.getCode();
		if (_proto_.hasHandleId())
			handleId = _proto_.getHandleId();
		if (_proto_.hasMsg())
			msg = _proto_.getMsg();
		if (_proto_.hasData())
			data = _proto_.getData();
		if (_proto_.hasToken())
			token = _proto_.getToken();
		if (_proto_.hasDeviceId())
			deviceId = _proto_.getDeviceId();
		if (_proto_.hasUserId())
			userId = _proto_.getUserId();
		if (_proto_.hasUserName())
			userName = _proto_.getUserName();
	}

	public static SProtocol load(byte[] bytes)
	{
		try
		{
			SProtocol obj = new SProtocol();
			obj.parse(Protocol.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SProtocol load(Protocol proto)
	{
		try
		{
			SProtocol obj = new SProtocol();
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
	public Protocol saveToProto()
	{
		Protocol.Builder _builder_ = Protocol.newBuilder();
		if (code != null && !code.equals(Protocol.getDefaultInstance().getCode()))
			_builder_.setCode(code);
		if (handleId != null && !handleId.equals(Protocol.getDefaultInstance().getHandleId()))
			_builder_.setHandleId(handleId);
		if (msg != null && !msg.equals(Protocol.getDefaultInstance().getMsg()))
			_builder_.setMsg(msg);
		if (data != null && !data.equals(Protocol.getDefaultInstance().getData()))
			_builder_.setData(data);
		if (token != null && !token.equals(Protocol.getDefaultInstance().getToken()))
			_builder_.setToken(token);
		if (deviceId != null && !deviceId.equals(Protocol.getDefaultInstance().getDeviceId()))
			_builder_.setDeviceId(deviceId);
		if (userId != null && !userId.equals(Protocol.getDefaultInstance().getUserId()))
			_builder_.setUserId(userId);
		if (userName != null && !userName.equals(Protocol.getDefaultInstance().getUserName()))
			_builder_.setUserName(userName);
		return _builder_.build();
	}
}
