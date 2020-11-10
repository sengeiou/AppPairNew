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
import com.lys.protobuf.ProtocolZhixue.Request_ZXPullAccount;

// ---------------------- XXXXXX --------------------------
public class SRequest_ZXPullAccount extends SPTData<Request_ZXPullAccount>
{
	private static final SRequest_ZXPullAccount DefaultInstance = new SRequest_ZXPullAccount();

	public String deviceId = null;

	public static SRequest_ZXPullAccount create(String deviceId)
	{
		SRequest_ZXPullAccount obj = new SRequest_ZXPullAccount();
		obj.deviceId = deviceId;
		return obj;
	}

	public SRequest_ZXPullAccount clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SRequest_ZXPullAccount _other_)
	{
		this.deviceId = _other_.deviceId;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("deviceId"))
			deviceId = _json_.getString("deviceId");
	}

	public static SRequest_ZXPullAccount load(String str)
	{
		try
		{
			SRequest_ZXPullAccount obj = new SRequest_ZXPullAccount();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_ZXPullAccount load(JSONObject json)
	{
		try
		{
			SRequest_ZXPullAccount obj = new SRequest_ZXPullAccount();
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

	public static List<SRequest_ZXPullAccount> loadList(JSONArray ja)
	{
		try
		{
			List<SRequest_ZXPullAccount> list = new ArrayList<SRequest_ZXPullAccount>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SRequest_ZXPullAccount item = SRequest_ZXPullAccount.load(jo);
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

	public static JSONArray saveList(List<SRequest_ZXPullAccount> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SRequest_ZXPullAccount item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Request_ZXPullAccount _proto_)
	{
		if (_proto_.hasDeviceId())
			deviceId = _proto_.getDeviceId();
	}

	public static SRequest_ZXPullAccount load(byte[] bytes)
	{
		try
		{
			SRequest_ZXPullAccount obj = new SRequest_ZXPullAccount();
			obj.parse(Request_ZXPullAccount.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_ZXPullAccount load(Request_ZXPullAccount proto)
	{
		try
		{
			SRequest_ZXPullAccount obj = new SRequest_ZXPullAccount();
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
	public Request_ZXPullAccount saveToProto()
	{
		Request_ZXPullAccount.Builder _builder_ = Request_ZXPullAccount.newBuilder();
		if (deviceId != null && !deviceId.equals(Request_ZXPullAccount.getDefaultInstance().getDeviceId()))
			_builder_.setDeviceId(deviceId);
		return _builder_.build();
	}
}
