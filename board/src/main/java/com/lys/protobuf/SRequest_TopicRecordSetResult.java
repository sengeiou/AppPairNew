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
import com.lys.protobuf.ProtocolPair2.Request_TopicRecordSetResult;

// ---------------------- xxxxxxxx --------------------------
public class SRequest_TopicRecordSetResult extends SPTData<Request_TopicRecordSetResult>
{
	private static final SRequest_TopicRecordSetResult DefaultInstance = new SRequest_TopicRecordSetResult();

	public String userId = null;
	public String topicId = null;
	public Integer result = 0;

	public static SRequest_TopicRecordSetResult create(String userId, String topicId, Integer result)
	{
		SRequest_TopicRecordSetResult obj = new SRequest_TopicRecordSetResult();
		obj.userId = userId;
		obj.topicId = topicId;
		obj.result = result;
		return obj;
	}

	public SRequest_TopicRecordSetResult clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SRequest_TopicRecordSetResult _other_)
	{
		this.userId = _other_.userId;
		this.topicId = _other_.topicId;
		this.result = _other_.result;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("userId"))
			userId = _json_.getString("userId");
		if (_json_.containsKey("topicId"))
			topicId = _json_.getString("topicId");
		if (_json_.containsKey("result"))
			result = _json_.getInteger("result");
	}

	public static SRequest_TopicRecordSetResult load(String str)
	{
		try
		{
			SRequest_TopicRecordSetResult obj = new SRequest_TopicRecordSetResult();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_TopicRecordSetResult load(JSONObject json)
	{
		try
		{
			SRequest_TopicRecordSetResult obj = new SRequest_TopicRecordSetResult();
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
			if (result != null)
				_json_.put("result", result);
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SRequest_TopicRecordSetResult> loadList(JSONArray ja)
	{
		try
		{
			List<SRequest_TopicRecordSetResult> list = new ArrayList<SRequest_TopicRecordSetResult>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SRequest_TopicRecordSetResult item = SRequest_TopicRecordSetResult.load(jo);
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

	public static JSONArray saveList(List<SRequest_TopicRecordSetResult> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SRequest_TopicRecordSetResult item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Request_TopicRecordSetResult _proto_)
	{
		if (_proto_.hasUserId())
			userId = _proto_.getUserId();
		if (_proto_.hasTopicId())
			topicId = _proto_.getTopicId();
		if (_proto_.hasResult())
			result = _proto_.getResult();
	}

	public static SRequest_TopicRecordSetResult load(byte[] bytes)
	{
		try
		{
			SRequest_TopicRecordSetResult obj = new SRequest_TopicRecordSetResult();
			obj.parse(Request_TopicRecordSetResult.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_TopicRecordSetResult load(Request_TopicRecordSetResult proto)
	{
		try
		{
			SRequest_TopicRecordSetResult obj = new SRequest_TopicRecordSetResult();
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
	public Request_TopicRecordSetResult saveToProto()
	{
		Request_TopicRecordSetResult.Builder _builder_ = Request_TopicRecordSetResult.newBuilder();
		if (userId != null && !userId.equals(Request_TopicRecordSetResult.getDefaultInstance().getUserId()))
			_builder_.setUserId(userId);
		if (topicId != null && !topicId.equals(Request_TopicRecordSetResult.getDefaultInstance().getTopicId()))
			_builder_.setTopicId(topicId);
		if (result != null && !result.equals(Request_TopicRecordSetResult.getDefaultInstance().getResult()))
			_builder_.setResult(result);
		return _builder_.build();
	}
}
