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
import com.lys.protobuf.ProtocolZhixue.Request_ZXCatchOver;

// ---------------------- XXXXXX --------------------------
public class SRequest_ZXCatchOver extends SPTData<Request_ZXCatchOver>
{
	private static final SRequest_ZXCatchOver DefaultInstance = new SRequest_ZXCatchOver();

	public String phase = null;
	public String subject = null;
	public String material = null;
	public String diff = null;
	public String area = null;
	public String year = null;
	public String deviceId = null;

	public static SRequest_ZXCatchOver create(String phase, String subject, String material, String diff, String area, String year, String deviceId)
	{
		SRequest_ZXCatchOver obj = new SRequest_ZXCatchOver();
		obj.phase = phase;
		obj.subject = subject;
		obj.material = material;
		obj.diff = diff;
		obj.area = area;
		obj.year = year;
		obj.deviceId = deviceId;
		return obj;
	}

	public SRequest_ZXCatchOver clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SRequest_ZXCatchOver _other_)
	{
		this.phase = _other_.phase;
		this.subject = _other_.subject;
		this.material = _other_.material;
		this.diff = _other_.diff;
		this.area = _other_.area;
		this.year = _other_.year;
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
		if (_json_.containsKey("diff"))
			diff = _json_.getString("diff");
		if (_json_.containsKey("area"))
			area = _json_.getString("area");
		if (_json_.containsKey("year"))
			year = _json_.getString("year");
		if (_json_.containsKey("deviceId"))
			deviceId = _json_.getString("deviceId");
	}

	public static SRequest_ZXCatchOver load(String str)
	{
		try
		{
			SRequest_ZXCatchOver obj = new SRequest_ZXCatchOver();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_ZXCatchOver load(JSONObject json)
	{
		try
		{
			SRequest_ZXCatchOver obj = new SRequest_ZXCatchOver();
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
			if (diff != null)
				_json_.put("diff", diff);
			if (area != null)
				_json_.put("area", area);
			if (year != null)
				_json_.put("year", year);
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

	public static List<SRequest_ZXCatchOver> loadList(JSONArray ja)
	{
		try
		{
			List<SRequest_ZXCatchOver> list = new ArrayList<SRequest_ZXCatchOver>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SRequest_ZXCatchOver item = SRequest_ZXCatchOver.load(jo);
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

	public static JSONArray saveList(List<SRequest_ZXCatchOver> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SRequest_ZXCatchOver item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Request_ZXCatchOver _proto_)
	{
		if (_proto_.hasPhase())
			phase = _proto_.getPhase();
		if (_proto_.hasSubject())
			subject = _proto_.getSubject();
		if (_proto_.hasMaterial())
			material = _proto_.getMaterial();
		if (_proto_.hasDiff())
			diff = _proto_.getDiff();
		if (_proto_.hasArea())
			area = _proto_.getArea();
		if (_proto_.hasYear())
			year = _proto_.getYear();
		if (_proto_.hasDeviceId())
			deviceId = _proto_.getDeviceId();
	}

	public static SRequest_ZXCatchOver load(byte[] bytes)
	{
		try
		{
			SRequest_ZXCatchOver obj = new SRequest_ZXCatchOver();
			obj.parse(Request_ZXCatchOver.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_ZXCatchOver load(Request_ZXCatchOver proto)
	{
		try
		{
			SRequest_ZXCatchOver obj = new SRequest_ZXCatchOver();
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
	public Request_ZXCatchOver saveToProto()
	{
		Request_ZXCatchOver.Builder _builder_ = Request_ZXCatchOver.newBuilder();
		if (phase != null && !phase.equals(Request_ZXCatchOver.getDefaultInstance().getPhase()))
			_builder_.setPhase(phase);
		if (subject != null && !subject.equals(Request_ZXCatchOver.getDefaultInstance().getSubject()))
			_builder_.setSubject(subject);
		if (material != null && !material.equals(Request_ZXCatchOver.getDefaultInstance().getMaterial()))
			_builder_.setMaterial(material);
		if (diff != null && !diff.equals(Request_ZXCatchOver.getDefaultInstance().getDiff()))
			_builder_.setDiff(diff);
		if (area != null && !area.equals(Request_ZXCatchOver.getDefaultInstance().getArea()))
			_builder_.setArea(area);
		if (year != null && !year.equals(Request_ZXCatchOver.getDefaultInstance().getYear()))
			_builder_.setYear(year);
		if (deviceId != null && !deviceId.equals(Request_ZXCatchOver.getDefaultInstance().getDeviceId()))
			_builder_.setDeviceId(deviceId);
		return _builder_.build();
	}
}
