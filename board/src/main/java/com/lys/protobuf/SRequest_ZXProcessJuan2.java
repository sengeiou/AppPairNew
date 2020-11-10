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
import com.lys.protobuf.ProtocolZhixue.Request_ZXProcessJuan2;

// ---------------------- XXXXXX --------------------------
public class SRequest_ZXProcessJuan2 extends SPTData<Request_ZXProcessJuan2>
{
	private static final SRequest_ZXProcessJuan2 DefaultInstance = new SRequest_ZXProcessJuan2();

	public String phase = null;
	public String subject = null;
	public String material = null;

	public static SRequest_ZXProcessJuan2 create(String phase, String subject, String material)
	{
		SRequest_ZXProcessJuan2 obj = new SRequest_ZXProcessJuan2();
		obj.phase = phase;
		obj.subject = subject;
		obj.material = material;
		return obj;
	}

	public SRequest_ZXProcessJuan2 clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SRequest_ZXProcessJuan2 _other_)
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

	public static SRequest_ZXProcessJuan2 load(String str)
	{
		try
		{
			SRequest_ZXProcessJuan2 obj = new SRequest_ZXProcessJuan2();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_ZXProcessJuan2 load(JSONObject json)
	{
		try
		{
			SRequest_ZXProcessJuan2 obj = new SRequest_ZXProcessJuan2();
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

	public static List<SRequest_ZXProcessJuan2> loadList(JSONArray ja)
	{
		try
		{
			List<SRequest_ZXProcessJuan2> list = new ArrayList<SRequest_ZXProcessJuan2>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SRequest_ZXProcessJuan2 item = SRequest_ZXProcessJuan2.load(jo);
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

	public static JSONArray saveList(List<SRequest_ZXProcessJuan2> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SRequest_ZXProcessJuan2 item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Request_ZXProcessJuan2 _proto_)
	{
		if (_proto_.hasPhase())
			phase = _proto_.getPhase();
		if (_proto_.hasSubject())
			subject = _proto_.getSubject();
		if (_proto_.hasMaterial())
			material = _proto_.getMaterial();
	}

	public static SRequest_ZXProcessJuan2 load(byte[] bytes)
	{
		try
		{
			SRequest_ZXProcessJuan2 obj = new SRequest_ZXProcessJuan2();
			obj.parse(Request_ZXProcessJuan2.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_ZXProcessJuan2 load(Request_ZXProcessJuan2 proto)
	{
		try
		{
			SRequest_ZXProcessJuan2 obj = new SRequest_ZXProcessJuan2();
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
	public Request_ZXProcessJuan2 saveToProto()
	{
		Request_ZXProcessJuan2.Builder _builder_ = Request_ZXProcessJuan2.newBuilder();
		if (phase != null && !phase.equals(Request_ZXProcessJuan2.getDefaultInstance().getPhase()))
			_builder_.setPhase(phase);
		if (subject != null && !subject.equals(Request_ZXProcessJuan2.getDefaultInstance().getSubject()))
			_builder_.setSubject(subject);
		if (material != null && !material.equals(Request_ZXProcessJuan2.getDefaultInstance().getMaterial()))
			_builder_.setMaterial(material);
		return _builder_.build();
	}
}
