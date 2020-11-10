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
import com.lys.protobuf.ProtocolZhixue.Request_ZXProcessJuan;

// ---------------------- XXXXXX --------------------------
public class SRequest_ZXProcessJuan extends SPTData<Request_ZXProcessJuan>
{
	private static final SRequest_ZXProcessJuan DefaultInstance = new SRequest_ZXProcessJuan();

	public String phase = null;
	public String subject = null;
	public String material = null;

	public static SRequest_ZXProcessJuan create(String phase, String subject, String material)
	{
		SRequest_ZXProcessJuan obj = new SRequest_ZXProcessJuan();
		obj.phase = phase;
		obj.subject = subject;
		obj.material = material;
		return obj;
	}

	public SRequest_ZXProcessJuan clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SRequest_ZXProcessJuan _other_)
	{
		this.phase = _other_.phase;
		this.subject = _other_.subject;
		this.material = _other_.material;
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
	}

	public static SRequest_ZXProcessJuan load(String str)
	{
		try
		{
			SRequest_ZXProcessJuan obj = new SRequest_ZXProcessJuan();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_ZXProcessJuan load(JSONObject json)
	{
		try
		{
			SRequest_ZXProcessJuan obj = new SRequest_ZXProcessJuan();
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
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SRequest_ZXProcessJuan> loadList(JSONArray ja)
	{
		try
		{
			List<SRequest_ZXProcessJuan> list = new ArrayList<SRequest_ZXProcessJuan>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SRequest_ZXProcessJuan item = SRequest_ZXProcessJuan.load(jo);
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

	public static JSONArray saveList(List<SRequest_ZXProcessJuan> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SRequest_ZXProcessJuan item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Request_ZXProcessJuan _proto_)
	{
		if (_proto_.hasPhase())
			phase = _proto_.getPhase();
		if (_proto_.hasSubject())
			subject = _proto_.getSubject();
		if (_proto_.hasMaterial())
			material = _proto_.getMaterial();
	}

	public static SRequest_ZXProcessJuan load(byte[] bytes)
	{
		try
		{
			SRequest_ZXProcessJuan obj = new SRequest_ZXProcessJuan();
			obj.parse(Request_ZXProcessJuan.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_ZXProcessJuan load(Request_ZXProcessJuan proto)
	{
		try
		{
			SRequest_ZXProcessJuan obj = new SRequest_ZXProcessJuan();
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
	public Request_ZXProcessJuan saveToProto()
	{
		Request_ZXProcessJuan.Builder _builder_ = Request_ZXProcessJuan.newBuilder();
		if (phase != null && !phase.equals(Request_ZXProcessJuan.getDefaultInstance().getPhase()))
			_builder_.setPhase(phase);
		if (subject != null && !subject.equals(Request_ZXProcessJuan.getDefaultInstance().getSubject()))
			_builder_.setSubject(subject);
		if (material != null && !material.equals(Request_ZXProcessJuan.getDefaultInstance().getMaterial()))
			_builder_.setMaterial(material);
		return _builder_.build();
	}
}
