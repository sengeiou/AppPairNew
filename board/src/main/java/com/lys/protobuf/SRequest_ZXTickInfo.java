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
import com.lys.protobuf.ProtocolZhixue.Request_ZXTickInfo;

// ---------------------- XXXXXX --------------------------
public class SRequest_ZXTickInfo extends SPTData<Request_ZXTickInfo>
{
	private static final SRequest_ZXTickInfo DefaultInstance = new SRequest_ZXTickInfo();

	public String deviceId = null;
	public String hostIp = null;
	public SSohuIp netIp = null;
	public Integer versionCode = 0;
	public Integer capacity = 0;
	public Long dtTime = 0L;

	public static SRequest_ZXTickInfo create(String deviceId, String hostIp, SSohuIp netIp, Integer versionCode, Integer capacity, Long dtTime)
	{
		SRequest_ZXTickInfo obj = new SRequest_ZXTickInfo();
		obj.deviceId = deviceId;
		obj.hostIp = hostIp;
		obj.netIp = netIp;
		obj.versionCode = versionCode;
		obj.capacity = capacity;
		obj.dtTime = dtTime;
		return obj;
	}

	public SRequest_ZXTickInfo clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SRequest_ZXTickInfo _other_)
	{
		this.deviceId = _other_.deviceId;
		this.hostIp = _other_.hostIp;
		this.netIp = _other_.netIp;
		this.versionCode = _other_.versionCode;
		this.capacity = _other_.capacity;
		this.dtTime = _other_.dtTime;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("deviceId"))
			deviceId = _json_.getString("deviceId");
		if (_json_.containsKey("hostIp"))
			hostIp = _json_.getString("hostIp");
		if (_json_.containsKey("netIp"))
			netIp = SSohuIp.load(_json_.getJSONObject("netIp"));
		if (_json_.containsKey("versionCode"))
			versionCode = _json_.getInteger("versionCode");
		if (_json_.containsKey("capacity"))
			capacity = _json_.getInteger("capacity");
		if (_json_.containsKey("dtTime"))
			dtTime = _json_.getLong("dtTime");
	}

	public static SRequest_ZXTickInfo load(String str)
	{
		try
		{
			SRequest_ZXTickInfo obj = new SRequest_ZXTickInfo();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_ZXTickInfo load(JSONObject json)
	{
		try
		{
			SRequest_ZXTickInfo obj = new SRequest_ZXTickInfo();
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
			if (hostIp != null)
				_json_.put("hostIp", hostIp);
			if (netIp != null)
				_json_.put("netIp", netIp.saveToJson());
			if (versionCode != null)
				_json_.put("versionCode", versionCode);
			if (capacity != null)
				_json_.put("capacity", capacity);
			if (dtTime != null)
				_json_.put("dtTime", String.valueOf(dtTime));
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SRequest_ZXTickInfo> loadList(JSONArray ja)
	{
		try
		{
			List<SRequest_ZXTickInfo> list = new ArrayList<SRequest_ZXTickInfo>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SRequest_ZXTickInfo item = SRequest_ZXTickInfo.load(jo);
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

	public static JSONArray saveList(List<SRequest_ZXTickInfo> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SRequest_ZXTickInfo item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Request_ZXTickInfo _proto_)
	{
		if (_proto_.hasDeviceId())
			deviceId = _proto_.getDeviceId();
		if (_proto_.hasHostIp())
			hostIp = _proto_.getHostIp();
		if (_proto_.hasNetIp())
			netIp = SSohuIp.load(_proto_.getNetIp());
		if (_proto_.hasVersionCode())
			versionCode = _proto_.getVersionCode();
		if (_proto_.hasCapacity())
			capacity = _proto_.getCapacity();
		if (_proto_.hasDtTime())
			dtTime = _proto_.getDtTime();
	}

	public static SRequest_ZXTickInfo load(byte[] bytes)
	{
		try
		{
			SRequest_ZXTickInfo obj = new SRequest_ZXTickInfo();
			obj.parse(Request_ZXTickInfo.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_ZXTickInfo load(Request_ZXTickInfo proto)
	{
		try
		{
			SRequest_ZXTickInfo obj = new SRequest_ZXTickInfo();
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
	public Request_ZXTickInfo saveToProto()
	{
		Request_ZXTickInfo.Builder _builder_ = Request_ZXTickInfo.newBuilder();
		if (deviceId != null && !deviceId.equals(Request_ZXTickInfo.getDefaultInstance().getDeviceId()))
			_builder_.setDeviceId(deviceId);
		if (hostIp != null && !hostIp.equals(Request_ZXTickInfo.getDefaultInstance().getHostIp()))
			_builder_.setHostIp(hostIp);
		if (netIp != null)
			_builder_.setNetIp(netIp.saveToProto());
		if (versionCode != null && !versionCode.equals(Request_ZXTickInfo.getDefaultInstance().getVersionCode()))
			_builder_.setVersionCode(versionCode);
		if (capacity != null && !capacity.equals(Request_ZXTickInfo.getDefaultInstance().getCapacity()))
			_builder_.setCapacity(capacity);
		if (dtTime != null && !dtTime.equals(Request_ZXTickInfo.getDefaultInstance().getDtTime()))
			_builder_.setDtTime(dtTime);
		return _builder_.build();
	}
}
