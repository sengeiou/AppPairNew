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
import com.lys.protobuf.ProtocolZhixue.Request_ZXCreateTask;

// ---------------------- XXXXXX --------------------------
public class SRequest_ZXCreateTask extends SPTData<Request_ZXCreateTask>
{
	private static final SRequest_ZXCreateTask DefaultInstance = new SRequest_ZXCreateTask();

	public String phase = null;
	public String subject = null;
	public String material = null;
	public List<String> diffs = new ArrayList<String>();
	public List<String> areas = new ArrayList<String>();
	public List<String> years = new ArrayList<String>();

	public static SRequest_ZXCreateTask create(String phase, String subject, String material)
	{
		SRequest_ZXCreateTask obj = new SRequest_ZXCreateTask();
		obj.phase = phase;
		obj.subject = subject;
		obj.material = material;
		return obj;
	}

	public SRequest_ZXCreateTask clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SRequest_ZXCreateTask _other_)
	{
		this.phase = _other_.phase;
		this.subject = _other_.subject;
		this.material = _other_.material;
		this.diffs = _other_.diffs;
		this.areas = _other_.areas;
		this.years = _other_.years;
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
		if (_json_.containsKey("diffs"))
			diffs = AppDataTool.loadStringList(AppDataTool.getJSONArray(_json_, "diffs"));
		if (_json_.containsKey("areas"))
			areas = AppDataTool.loadStringList(AppDataTool.getJSONArray(_json_, "areas"));
		if (_json_.containsKey("years"))
			years = AppDataTool.loadStringList(AppDataTool.getJSONArray(_json_, "years"));
	}

	public static SRequest_ZXCreateTask load(String str)
	{
		try
		{
			SRequest_ZXCreateTask obj = new SRequest_ZXCreateTask();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_ZXCreateTask load(JSONObject json)
	{
		try
		{
			SRequest_ZXCreateTask obj = new SRequest_ZXCreateTask();
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
			if (diffs != null)
				_json_.put("diffs", AppDataTool.saveStringList(diffs));
			if (areas != null)
				_json_.put("areas", AppDataTool.saveStringList(areas));
			if (years != null)
				_json_.put("years", AppDataTool.saveStringList(years));
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SRequest_ZXCreateTask> loadList(JSONArray ja)
	{
		try
		{
			List<SRequest_ZXCreateTask> list = new ArrayList<SRequest_ZXCreateTask>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SRequest_ZXCreateTask item = SRequest_ZXCreateTask.load(jo);
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

	public static JSONArray saveList(List<SRequest_ZXCreateTask> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SRequest_ZXCreateTask item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Request_ZXCreateTask _proto_)
	{
		if (_proto_.hasPhase())
			phase = _proto_.getPhase();
		if (_proto_.hasSubject())
			subject = _proto_.getSubject();
		if (_proto_.hasMaterial())
			material = _proto_.getMaterial();
		for (int i = 0; i < _proto_.getDiffsCount(); i++)
			diffs.add(_proto_.getDiffs(i));
		for (int i = 0; i < _proto_.getAreasCount(); i++)
			areas.add(_proto_.getAreas(i));
		for (int i = 0; i < _proto_.getYearsCount(); i++)
			years.add(_proto_.getYears(i));
	}

	public static SRequest_ZXCreateTask load(byte[] bytes)
	{
		try
		{
			SRequest_ZXCreateTask obj = new SRequest_ZXCreateTask();
			obj.parse(Request_ZXCreateTask.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_ZXCreateTask load(Request_ZXCreateTask proto)
	{
		try
		{
			SRequest_ZXCreateTask obj = new SRequest_ZXCreateTask();
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
	public Request_ZXCreateTask saveToProto()
	{
		Request_ZXCreateTask.Builder _builder_ = Request_ZXCreateTask.newBuilder();
		if (phase != null && !phase.equals(Request_ZXCreateTask.getDefaultInstance().getPhase()))
			_builder_.setPhase(phase);
		if (subject != null && !subject.equals(Request_ZXCreateTask.getDefaultInstance().getSubject()))
			_builder_.setSubject(subject);
		if (material != null && !material.equals(Request_ZXCreateTask.getDefaultInstance().getMaterial()))
			_builder_.setMaterial(material);
		if (diffs != null)
			for (String _value_ : diffs)
				_builder_.addDiffs(_value_);
		if (areas != null)
			for (String _value_ : areas)
				_builder_.addAreas(_value_);
		if (years != null)
			for (String _value_ : years)
				_builder_.addYears(_value_);
		return _builder_.build();
	}
}
