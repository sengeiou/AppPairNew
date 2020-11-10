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
import com.lys.protobuf.ProtocolPair2.Request_TopicRecordGet;

// ---------------------- xxxxxxxx --------------------------
public class SRequest_TopicRecordGet extends SPTData<Request_TopicRecordGet>
{
	private static final SRequest_TopicRecordGet DefaultInstance = new SRequest_TopicRecordGet();

	public String userId = null;
	public String topicId = null;

	public static SRequest_TopicRecordGet create(String userId, String topicId)
	{
		SRequest_TopicRecordGet obj = new SRequest_TopicRecordGet();
		obj.userId = userId;
		obj.topicId = topicId;
		return obj;
	}

	public SRequest_TopicRecordGet clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SRequest_TopicRecordGet _other_)
	{
		this.userId = _other_.userId;
		this.topicId = _other_.topicId;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("userId"))
			userId = _json_.getString("userId");
		if (_json_.containsKey("topicId"))
			topicId = _json_.getString("topicId");
	}

	public static SRequest_TopicRecordGet load(String str)
	{
		try
		{
			SRequest_TopicRecordGet obj = new SRequest_TopicRecordGet();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_TopicRecordGet load(JSONObject json)
	{
		try
		{
			SRequest_TopicRecordGet obj = new SRequest_TopicRecordGet();
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
			if (userId != null)
				_json_.put("userId", userId);
			if (topicId != null)
				_json_.put("topicId", topicId);
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SRequest_TopicRecordGet> loadList(JSONArray ja)
	{
		try
		{
			List<SRequest_TopicRecordGet> list = new ArrayList<SRequest_TopicRecordGet>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SRequest_TopicRecordGet item = SRequest_TopicRecordGet.load(jo);
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

	public static JSONArray saveList(List<SRequest_TopicRecordGet> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SRequest_TopicRecordGet item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Request_TopicRecordGet _proto_)
	{
		if (_proto_.hasUserId())
			userId = _proto_.getUserId();
		if (_proto_.hasTopicId())
			topicId = _proto_.getTopicId();
	}

	public static SRequest_TopicRecordGet load(byte[] bytes)
	{
		try
		{
			SRequest_TopicRecordGet obj = new SRequest_TopicRecordGet();
			obj.parse(Request_TopicRecordGet.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_TopicRecordGet load(Request_TopicRecordGet proto)
	{
		try
		{
			SRequest_TopicRecordGet obj = new SRequest_TopicRecordGet();
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
	public Request_TopicRecordGet saveToProto()
	{
		Request_TopicRecordGet.Builder _builder_ = Request_TopicRecordGet.newBuilder();
		if (userId != null && !userId.equals(Request_TopicRecordGet.getDefaultInstance().getUserId()))
			_builder_.setUserId(userId);
		if (topicId != null && !topicId.equals(Request_TopicRecordGet.getDefaultInstance().getTopicId()))
			_builder_.setTopicId(topicId);
		return _builder_.build();
	}
}
