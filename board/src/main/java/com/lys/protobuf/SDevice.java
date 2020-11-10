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
import com.lys.protobuf.ProtocolPair.Device;

public class SDevice extends SPTData<Device>
{
	private static final SDevice DefaultInstance = new SDevice();

	public String deviceId = null;
	public Integer loginCount = 0;

	public static SDevice create(String deviceId, Integer loginCount)
	{
		SDevice obj = new SDevice();
		obj.deviceId = deviceId;
		obj.loginCount = loginCount;
		return obj;
	}

	public SDevice clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SDevice _other_)
	{
		this.deviceId = _other_.deviceId;
		this.loginCount = _other_.loginCount;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("deviceId"))
			deviceId = _json_.getString("deviceId");
		if (_json_.containsKey("loginCount"))
			loginCount = _json_.getInteger("loginCount");
	}

	public static SDevice load(String str)
	{
		try
		{
			SDevice obj = new SDevice();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SDevice load(JSONObject json)
	{
		try
		{
			SDevice obj = new SDevice();
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
			if (loginCount != null)
				_json_.put("loginCount", loginCount);
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SDevice> loadList(JSONArray ja)
	{
		try
		{
			List<SDevice> list = new ArrayList<SDevice>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SDevice item = SDevice.load(jo);
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

	public static JSONArray saveList(List<SDevice> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SDevice item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Device _proto_)
	{
		if (_proto_.hasDeviceId())
			deviceId = _proto_.getDeviceId();
		if (_proto_.hasLoginCount())
			loginCount = _proto_.getLoginCount();
	}

	public static SDevice load(byte[] bytes)
	{
		try
		{
			SDevice obj = new SDevice();
			obj.parse(Device.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SDevice load(Device proto)
	{
		try
		{
			SDevice obj = new SDevice();
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
	public Device saveToProto()
	{
		Device.Builder _builder_ = Device.newBuilder();
		if (deviceId != null && !deviceId.equals(Device.getDefaultInstance().getDeviceId()))
			_builder_.setDeviceId(deviceId);
		if (loginCount != null && !loginCount.equals(Device.getDefaultInstance().getLoginCount()))
			_builder_.setLoginCount(loginCount);
		return _builder_.build();
	}
}
