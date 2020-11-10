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
import com.lys.protobuf.ProtocolZhixue.ZXDeviceInfo;

public class SZXDeviceInfo extends SPTData<ZXDeviceInfo>
{
	private static final SZXDeviceInfo DefaultInstance = new SZXDeviceInfo();

	public String deviceId = null;
	public String host = null;
	public String addr = null;
	public String xForwardedFor = null;
	public String xForwardedForPound = null;
	public String hostIp = null;
	public SSohuIp netIp = null;
	public Integer versionCode = 0;
	public Long lastTickTime = 0L;
	public Integer capacity = 0;
	public Long dtTime = 0L;

	public static SZXDeviceInfo create(String deviceId, String host, String addr, String xForwardedFor, String xForwardedForPound, String hostIp, SSohuIp netIp, Integer versionCode, Long lastTickTime, Integer capacity, Long dtTime)
	{
		SZXDeviceInfo obj = new SZXDeviceInfo();
		obj.deviceId = deviceId;
		obj.host = host;
		obj.addr = addr;
		obj.xForwardedFor = xForwardedFor;
		obj.xForwardedForPound = xForwardedForPound;
		obj.hostIp = hostIp;
		obj.netIp = netIp;
		obj.versionCode = versionCode;
		obj.lastTickTime = lastTickTime;
		obj.capacity = capacity;
		obj.dtTime = dtTime;
		return obj;
	}

	public SZXDeviceInfo clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SZXDeviceInfo _other_)
	{
		this.deviceId = _other_.deviceId;
		this.host = _other_.host;
		this.addr = _other_.addr;
		this.xForwardedFor = _other_.xForwardedFor;
		this.xForwardedForPound = _other_.xForwardedForPound;
		this.hostIp = _other_.hostIp;
		this.netIp = _other_.netIp;
		this.versionCode = _other_.versionCode;
		this.lastTickTime = _other_.lastTickTime;
		this.capacity = _other_.capacity;
		this.dtTime = _other_.dtTime;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("deviceId"))
			deviceId = _json_.getString("deviceId");
		if (_json_.containsKey("host"))
			host = _json_.getString("host");
		if (_json_.containsKey("addr"))
			addr = _json_.getString("addr");
		if (_json_.containsKey("xForwardedFor"))
			xForwardedFor = _json_.getString("xForwardedFor");
		if (_json_.containsKey("xForwardedForPound"))
			xForwardedForPound = _json_.getString("xForwardedForPound");
		if (_json_.containsKey("hostIp"))
			hostIp = _json_.getString("hostIp");
		if (_json_.containsKey("netIp"))
			netIp = SSohuIp.load(_json_.getJSONObject("netIp"));
		if (_json_.containsKey("versionCode"))
			versionCode = _json_.getInteger("versionCode");
		if (_json_.containsKey("lastTickTime"))
			lastTickTime = _json_.getLong("lastTickTime");
		if (_json_.containsKey("capacity"))
			capacity = _json_.getInteger("capacity");
		if (_json_.containsKey("dtTime"))
			dtTime = _json_.getLong("dtTime");
	}

	public static SZXDeviceInfo load(String str)
	{
		try
		{
			SZXDeviceInfo obj = new SZXDeviceInfo();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SZXDeviceInfo load(JSONObject json)
	{
		try
		{
			SZXDeviceInfo obj = new SZXDeviceInfo();
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
			if (host != null)
				_json_.put("host", host);
			if (addr != null)
				_json_.put("addr", addr);
			if (xForwardedFor != null)
				_json_.put("xForwardedFor", xForwardedFor);
			if (xForwardedForPound != null)
				_json_.put("xForwardedForPound", xForwardedForPound);
			if (hostIp != null)
				_json_.put("hostIp", hostIp);
			if (netIp != null)
				_json_.put("netIp", netIp.saveToJson());
			if (versionCode != null)
				_json_.put("versionCode", versionCode);
			if (lastTickTime != null)
				_json_.put("lastTickTime", String.valueOf(lastTickTime));
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

	public static List<SZXDeviceInfo> loadList(JSONArray ja)
	{
		try
		{
			List<SZXDeviceInfo> list = new ArrayList<SZXDeviceInfo>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SZXDeviceInfo item = SZXDeviceInfo.load(jo);
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

	public static JSONArray saveList(List<SZXDeviceInfo> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SZXDeviceInfo item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(ZXDeviceInfo _proto_)
	{
		if (_proto_.hasDeviceId())
			deviceId = _proto_.getDeviceId();
		if (_proto_.hasHost())
			host = _proto_.getHost();
		if (_proto_.hasAddr())
			addr = _proto_.getAddr();
		if (_proto_.hasXForwardedFor())
			xForwardedFor = _proto_.getXForwardedFor();
		if (_proto_.hasXForwardedForPound())
			xForwardedForPound = _proto_.getXForwardedForPound();
		if (_proto_.hasHostIp())
			hostIp = _proto_.getHostIp();
		if (_proto_.hasNetIp())
			netIp = SSohuIp.load(_proto_.getNetIp());
		if (_proto_.hasVersionCode())
			versionCode = _proto_.getVersionCode();
		if (_proto_.hasLastTickTime())
			lastTickTime = _proto_.getLastTickTime();
		if (_proto_.hasCapacity())
			capacity = _proto_.getCapacity();
		if (_proto_.hasDtTime())
			dtTime = _proto_.getDtTime();
	}

	public static SZXDeviceInfo load(byte[] bytes)
	{
		try
		{
			SZXDeviceInfo obj = new SZXDeviceInfo();
			obj.parse(ZXDeviceInfo.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SZXDeviceInfo load(ZXDeviceInfo proto)
	{
		try
		{
			SZXDeviceInfo obj = new SZXDeviceInfo();
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
	public ZXDeviceInfo saveToProto()
	{
		ZXDeviceInfo.Builder _builder_ = ZXDeviceInfo.newBuilder();
		if (deviceId != null && !deviceId.equals(ZXDeviceInfo.getDefaultInstance().getDeviceId()))
			_builder_.setDeviceId(deviceId);
		if (host != null && !host.equals(ZXDeviceInfo.getDefaultInstance().getHost()))
			_builder_.setHost(host);
		if (addr != null && !addr.equals(ZXDeviceInfo.getDefaultInstance().getAddr()))
			_builder_.setAddr(addr);
		if (xForwardedFor != null && !xForwardedFor.equals(ZXDeviceInfo.getDefaultInstance().getXForwardedFor()))
			_builder_.setXForwardedFor(xForwardedFor);
		if (xForwardedForPound != null && !xForwardedForPound.equals(ZXDeviceInfo.getDefaultInstance().getXForwardedForPound()))
			_builder_.setXForwardedForPound(xForwardedForPound);
		if (hostIp != null && !hostIp.equals(ZXDeviceInfo.getDefaultInstance().getHostIp()))
			_builder_.setHostIp(hostIp);
		if (netIp != null)
			_builder_.setNetIp(netIp.saveToProto());
		if (versionCode != null && !versionCode.equals(ZXDeviceInfo.getDefaultInstance().getVersionCode()))
			_builder_.setVersionCode(versionCode);
		if (lastTickTime != null && !lastTickTime.equals(ZXDeviceInfo.getDefaultInstance().getLastTickTime()))
			_builder_.setLastTickTime(lastTickTime);
		if (capacity != null && !capacity.equals(ZXDeviceInfo.getDefaultInstance().getCapacity()))
			_builder_.setCapacity(capacity);
		if (dtTime != null && !dtTime.equals(ZXDeviceInfo.getDefaultInstance().getDtTime()))
			_builder_.setDtTime(dtTime);
		return _builder_.build();
	}
}
