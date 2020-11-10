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
import com.lys.protobuf.ProtocolPair2.Request_GetTopicStyles;

// ---------------------- xxxxxxxx --------------------------
public class SRequest_GetTopicStyles extends SPTData<Request_GetTopicStyles>
{
	private static final SRequest_GetTopicStyles DefaultInstance = new SRequest_GetTopicStyles();

	public Integer phase = 0;
	public Integer subject = 0;

	public static SRequest_GetTopicStyles create(Integer phase, Integer subject)
	{
		SRequest_GetTopicStyles obj = new SRequest_GetTopicStyles();
		obj.phase = phase;
		obj.subject = subject;
		return obj;
	}

	public SRequest_GetTopicStyles clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SRequest_GetTopicStyles _other_)
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

	public static SRequest_GetTopicStyles load(String str)
	{
		try
		{
			SRequest_GetTopicStyles obj = new SRequest_GetTopicStyles();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_GetTopicStyles load(JSONObject json)
	{
		try
		{
			SRequest_GetTopicStyles obj = new SRequest_GetTopicStyles();
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

	public static List<SRequest_GetTopicStyles> loadList(JSONArray ja)
	{
		try
		{
			List<SRequest_GetTopicStyles> list = new ArrayList<SRequest_GetTopicStyles>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SRequest_GetTopicStyles item = SRequest_GetTopicStyles.load(jo);
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

	public static JSONArray saveList(List<SRequest_GetTopicStyles> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SRequest_GetTopicStyles item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Request_GetTopicStyles _proto_)
	{
		if (_proto_.hasPhase())
			phase = _proto_.getPhase();
		if (_proto_.hasSubject())
			subject = _proto_.getSubject();
	}

	public static SRequest_GetTopicStyles load(byte[] bytes)
	{
		try
		{
			SRequest_GetTopicStyles obj = new SRequest_GetTopicStyles();
			obj.parse(Request_GetTopicStyles.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_GetTopicStyles load(Request_GetTopicStyles proto)
	{
		try
		{
			SRequest_GetTopicStyles obj = new SRequest_GetTopicStyles();
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
	public Request_GetTopicStyles saveToProto()
	{
		Request_GetTopicStyles.Builder _builder_ = Request_GetTopicStyles.newBuilder();
		if (phase != null && !phase.equals(Request_GetTopicStyles.getDefaultInstance().getPhase()))
			_builder_.setPhase(phase);
		if (subject != null && !subject.equals(Request_GetTopicStyles.getDefaultInstance().getSubject()))
			_builder_.setSubject(subject);
		return _builder_.build();
	}
}
