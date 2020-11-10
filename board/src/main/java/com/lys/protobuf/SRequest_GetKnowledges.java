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
import com.lys.protobuf.ProtocolPair2.Request_GetKnowledges;

// ---------------------- xxxxxxxx --------------------------
public class SRequest_GetKnowledges extends SPTData<Request_GetKnowledges>
{
	private static final SRequest_GetKnowledges DefaultInstance = new SRequest_GetKnowledges();

	public Integer phase = 0;
	public Integer subject = 0;

	public static SRequest_GetKnowledges create(Integer phase, Integer subject)
	{
		SRequest_GetKnowledges obj = new SRequest_GetKnowledges();
		obj.phase = phase;
		obj.subject = subject;
		return obj;
	}

	public SRequest_GetKnowledges clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SRequest_GetKnowledges _other_)
	{
		this.phase = _other_.phase;
		this.subject = _other_.subject;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("phase"))
			phase = _json_.getInteger("phase");
		if (_json_.containsKey("subject"))
			subject = _json_.getInteger("subject");
	}

	public static SRequest_GetKnowledges load(String str)
	{
		try
		{
			SRequest_GetKnowledges obj = new SRequest_GetKnowledges();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_GetKnowledges load(JSONObject json)
	{
		try
		{
			SRequest_GetKnowledges obj = new SRequest_GetKnowledges();
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
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SRequest_GetKnowledges> loadList(JSONArray ja)
	{
		try
		{
			List<SRequest_GetKnowledges> list = new ArrayList<SRequest_GetKnowledges>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SRequest_GetKnowledges item = SRequest_GetKnowledges.load(jo);
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

	public static JSONArray saveList(List<SRequest_GetKnowledges> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SRequest_GetKnowledges item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Request_GetKnowledges _proto_)
	{
		if (_proto_.hasPhase())
			phase = _proto_.getPhase();
		if (_proto_.hasSubject())
			subject = _proto_.getSubject();
	}

	public static SRequest_GetKnowledges load(byte[] bytes)
	{
		try
		{
			SRequest_GetKnowledges obj = new SRequest_GetKnowledges();
			obj.parse(Request_GetKnowledges.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_GetKnowledges load(Request_GetKnowledges proto)
	{
		try
		{
			SRequest_GetKnowledges obj = new SRequest_GetKnowledges();
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
	public Request_GetKnowledges saveToProto()
	{
		Request_GetKnowledges.Builder _builder_ = Request_GetKnowledges.newBuilder();
		if (phase != null && !phase.equals(Request_GetKnowledges.getDefaultInstance().getPhase()))
			_builder_.setPhase(phase);
		if (subject != null && !subject.equals(Request_GetKnowledges.getDefaultInstance().getSubject()))
			_builder_.setSubject(subject);
		return _builder_.build();
	}
}
