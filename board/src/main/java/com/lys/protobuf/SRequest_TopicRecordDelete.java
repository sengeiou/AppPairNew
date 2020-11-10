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
import com.lys.protobuf.ProtocolPair2.Request_TopicRecordDelete;

// ---------------------- xxxxxxxx --------------------------
public class SRequest_TopicRecordDelete extends SPTData<Request_TopicRecordDelete>
{
	private static final SRequest_TopicRecordDelete DefaultInstance = new SRequest_TopicRecordDelete();

	public String userId = null;
	public String topicId = null;

	public static SRequest_TopicRecordDelete create(String userId, String topicId)
	{
		SRequest_TopicRecordDelete obj = new SRequest_TopicRecordDelete();
		obj.userId = userId;
		obj.topicId = topicId;
		return obj;
	}

	public SRequest_TopicRecordDelete clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SRequest_TopicRecordDelete _other_)
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

	public static SRequest_TopicRecordDelete load(String str)
	{
		try
		{
			SRequest_TopicRecordDelete obj = new SRequest_TopicRecordDelete();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_TopicRecordDelete load(JSONObject json)
	{
		try
		{
			SRequest_TopicRecordDelete obj = new SRequest_TopicRecordDelete();
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

	public static List<SRequest_TopicRecordDelete> loadList(JSONArray ja)
	{
		try
		{
			List<SRequest_TopicRecordDelete> list = new ArrayList<SRequest_TopicRecordDelete>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SRequest_TopicRecordDelete item = SRequest_TopicRecordDelete.load(jo);
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

	public static JSONArray saveList(List<SRequest_TopicRecordDelete> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SRequest_TopicRecordDelete item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Request_TopicRecordDelete _proto_)
	{
		if (_proto_.hasUserId())
			userId = _proto_.getUserId();
		if (_proto_.hasTopicId())
			topicId = _proto_.getTopicId();
	}

	public static SRequest_TopicRecordDelete load(byte[] bytes)
	{
		try
		{
			SRequest_TopicRecordDelete obj = new SRequest_TopicRecordDelete();
			obj.parse(Request_TopicRecordDelete.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_TopicRecordDelete load(Request_TopicRecordDelete proto)
	{
		try
		{
			SRequest_TopicRecordDelete obj = new SRequest_TopicRecordDelete();
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
	public Request_TopicRecordDelete saveToProto()
	{
		Request_TopicRecordDelete.Builder _builder_ = Request_TopicRecordDelete.newBuilder();
		if (userId != null && !userId.equals(Request_TopicRecordDelete.getDefaultInstance().getUserId()))
			_builder_.setUserId(userId);
		if (topicId != null && !topicId.equals(Request_TopicRecordDelete.getDefaultInstance().getTopicId()))
			_builder_.setTopicId(topicId);
		return _builder_.build();
	}
}
