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
import com.lys.protobuf.ProtocolZhixue.ZXAccount;

public class SZXAccount extends SPTData<ZXAccount>
{
	private static final SZXAccount DefaultInstance = new SZXAccount();

	public String account = null;
	public String psw = null;
	public String state = null;
	public String deviceId = null;

	public static SZXAccount create(String account, String psw, String state, String deviceId)
	{
		SZXAccount obj = new SZXAccount();
		obj.account = account;
		obj.psw = psw;
		obj.state = state;
		obj.deviceId = deviceId;
		return obj;
	}

	public SZXAccount clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SZXAccount _other_)
	{
		this.account = _other_.account;
		this.psw = _other_.psw;
		this.state = _other_.state;
		this.deviceId = _other_.deviceId;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("account"))
			account = _json_.getString("account");
		if (_json_.containsKey("psw"))
			psw = _json_.getString("psw");
		if (_json_.containsKey("state"))
			state = _json_.getString("state");
		if (_json_.containsKey("deviceId"))
			deviceId = _json_.getString("deviceId");
	}

	public static SZXAccount load(String str)
	{
		try
		{
			SZXAccount obj = new SZXAccount();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SZXAccount load(JSONObject json)
	{
		try
		{
			SZXAccount obj = new SZXAccount();
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
			if (account != null)
				_json_.put("account", account);
			if (psw != null)
				_json_.put("psw", psw);
			if (state != null)
				_json_.put("state", state);
			if (deviceId != null)
				_json_.put("deviceId", deviceId);
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SZXAccount> loadList(JSONArray ja)
	{
		try
		{
			List<SZXAccount> list = new ArrayList<SZXAccount>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SZXAccount item = SZXAccount.load(jo);
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

	public static JSONArray saveList(List<SZXAccount> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SZXAccount item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(ZXAccount _proto_)
	{
		if (_proto_.hasAccount())
			account = _proto_.getAccount();
		if (_proto_.hasPsw())
			psw = _proto_.getPsw();
		if (_proto_.hasState())
			state = _proto_.getState();
		if (_proto_.hasDeviceId())
			deviceId = _proto_.getDeviceId();
	}

	public static SZXAccount load(byte[] bytes)
	{
		try
		{
			SZXAccount obj = new SZXAccount();
			obj.parse(ZXAccount.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SZXAccount load(ZXAccount proto)
	{
		try
		{
			SZXAccount obj = new SZXAccount();
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
	public ZXAccount saveToProto()
	{
		ZXAccount.Builder _builder_ = ZXAccount.newBuilder();
		if (account != null && !account.equals(ZXAccount.getDefaultInstance().getAccount()))
			_builder_.setAccount(account);
		if (psw != null && !psw.equals(ZXAccount.getDefaultInstance().getPsw()))
			_builder_.setPsw(psw);
		if (state != null && !state.equals(ZXAccount.getDefaultInstance().getState()))
			_builder_.setState(state);
		if (deviceId != null && !deviceId.equals(ZXAccount.getDefaultInstance().getDeviceId()))
			_builder_.setDeviceId(deviceId);
		return _builder_.build();
	}
}
