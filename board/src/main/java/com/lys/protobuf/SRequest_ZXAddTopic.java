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
import com.lys.protobuf.ProtocolZhixue.Request_ZXAddTopic;

// ---------------------- XXXXXX --------------------------
public class SRequest_ZXAddTopic extends SPTData<Request_ZXAddTopic>
{
	private static final SRequest_ZXAddTopic DefaultInstance = new SRequest_ZXAddTopic();

	public List<SZXTopic> topics = new ArrayList<SZXTopic>();

	public static SRequest_ZXAddTopic create()
	{
		SRequest_ZXAddTopic obj = new SRequest_ZXAddTopic();
		return obj;
	}

	public SRequest_ZXAddTopic clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SRequest_ZXAddTopic _other_)
	{
		this.topics = _other_.topics;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("topics"))
			topics = SZXTopic.loadList(_json_.getJSONArray("topics"));
	}

	public static SRequest_ZXAddTopic load(String str)
	{
		try
		{
			SRequest_ZXAddTopic obj = new SRequest_ZXAddTopic();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_ZXAddTopic load(JSONObject json)
	{
		try
		{
			SRequest_ZXAddTopic obj = new SRequest_ZXAddTopic();
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
			if (topics != null)
				_json_.put("topics", SZXTopic.saveList(topics));
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SRequest_ZXAddTopic> loadList(JSONArray ja)
	{
		try
		{
			List<SRequest_ZXAddTopic> list = new ArrayList<SRequest_ZXAddTopic>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SRequest_ZXAddTopic item = SRequest_ZXAddTopic.load(jo);
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

	public static JSONArray saveList(List<SRequest_ZXAddTopic> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SRequest_ZXAddTopic item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Request_ZXAddTopic _proto_)
	{
		for (int i = 0; i < _proto_.getTopicsCount(); i++)
			topics.add(SZXTopic.load(_proto_.getTopics(i)));
	}

	public static SRequest_ZXAddTopic load(byte[] bytes)
	{
		try
		{
			SRequest_ZXAddTopic obj = new SRequest_ZXAddTopic();
			obj.parse(Request_ZXAddTopic.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_ZXAddTopic load(Request_ZXAddTopic proto)
	{
		try
		{
			SRequest_ZXAddTopic obj = new SRequest_ZXAddTopic();
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
	public Request_ZXAddTopic saveToProto()
	{
		Request_ZXAddTopic.Builder _builder_ = Request_ZXAddTopic.newBuilder();
		if (topics != null)
			for (SZXTopic _value_ : topics)
				_builder_.addTopics(_value_.saveToProto());
		return _builder_.build();
	}
}
