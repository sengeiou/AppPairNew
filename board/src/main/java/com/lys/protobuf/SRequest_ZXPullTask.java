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
import com.lys.protobuf.ProtocolZhixue.Request_ZXPullTask;

// ---------------------- XXXXXX --------------------------
public class SRequest_ZXPullTask extends SPTData<Request_ZXPullTask>
{
	private static final SRequest_ZXPullTask DefaultInstance = new SRequest_ZXPullTask();

	public String phase = null;
	public String subject = null;
	public String material = null;
	public String deviceId = null;

	public static SRequest_ZXPullTask create(String phase, String subject, String material, String deviceId)
	{
		SRequest_ZXPullTask obj = new SRequest_ZXPullTask();
		obj.phase = phase;
		obj.subject = subject;
		obj.material = material;
		obj.deviceId = deviceId;
		return obj;
	}

	public SRequest_ZXPullTask clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SRequest_ZXPullTask _other_)
	{
		this.phase = _other_.phase;
		this.subject = _other_.subject;
		this.material = _other_.material;
		this.deviceId = _other_.deviceId;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("phase"))
			phase = _json_.getString("phase");
		if (_json_.containsKey("subject"))
			subject = _json_.getString("subject");
		if (_json_.containsKey("material"))
			material = _json_.getString("material");
		if (_json_.containsKey("deviceId"))
			deviceId = _json_.getString("deviceId");
	}

	public static SRequest_ZXPullTask load(String str)
	{
		try
		{
			SRequest_ZXPullTask obj = new SRequest_ZXPullTask();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_ZXPullTask load(JSONObject json)
	{
		try
		{
			SRequest_ZXPullTask obj = new SRequest_ZXPullTask();
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
			if (phase != null)
				_json_.put("phase", phase);
			if (subject != null)
				_json_.put("subject", subject);
			if (material != null)
				_json_.put("material", material);
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

	public static List<SRequest_ZXPullTask> loadList(JSONArray ja)
	{
		try
		{
			List<SRequest_ZXPullTask> list = new ArrayList<SRequest_ZXPullTask>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SRequest_ZXPullTask item = SRequest_ZXPullTask.load(jo);
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

	public static JSONArray saveList(List<SRequest_ZXPullTask> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SRequest_ZXPullTask item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Request_ZXPullTask _proto_)
	{
		if (_proto_.hasPhase())
			phase = _proto_.getPhase();
		if (_proto_.hasSubject())
			subject = _proto_.getSubject();
		if (_proto_.hasMaterial())
			material = _proto_.getMaterial();
		if (_proto_.hasDeviceId())
			deviceId = _proto_.getDeviceId();
	}

	public static SRequest_ZXPullTask load(byte[] bytes)
	{
		try
		{
			SRequest_ZXPullTask obj = new SRequest_ZXPullTask();
			obj.parse(Request_ZXPullTask.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_ZXPullTask load(Request_ZXPullTask proto)
	{
		try
		{
			SRequest_ZXPullTask obj = new SRequest_ZXPullTask();
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
	public Request_ZXPullTask saveToProto()
	{
		Request_ZXPullTask.Builder _builder_ = Request_ZXPullTask.newBuilder();
		if (phase != null && !phase.equals(Request_ZXPullTask.getDefaultInstance().getPhase()))
			_builder_.setPhase(phase);
		if (subject != null && !subject.equals(Request_ZXPullTask.getDefaultInstance().getSubject()))
			_builder_.setSubject(subject);
		if (material != null && !material.equals(Request_ZXPullTask.getDefaultInstance().getMaterial()))
			_builder_.setMaterial(material);
		if (deviceId != null && !deviceId.equals(Request_ZXPullTask.getDefaultInstance().getDeviceId()))
			_builder_.setDeviceId(deviceId);
		return _builder_.build();
	}
}
