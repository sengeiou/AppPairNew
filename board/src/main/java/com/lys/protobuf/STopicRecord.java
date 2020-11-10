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
import com.lys.protobuf.ProtocolPair2.TopicRecord;

public class STopicRecord extends SPTData<TopicRecord>
{
	private static final STopicRecord DefaultInstance = new STopicRecord();

	public String userId = null;
	public String topicId = null;
	public Integer fav = 0;
	public Integer result = 0;
	public Long time = 0L;

	public static STopicRecord create(String userId, String topicId, Integer fav, Integer result, Long time)
	{
		STopicRecord obj = new STopicRecord();
		obj.userId = userId;
		obj.topicId = topicId;
		obj.fav = fav;
		obj.result = result;
		obj.time = time;
		return obj;
	}

	public STopicRecord clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(STopicRecord _other_)
	{
		this.userId = _other_.userId;
		this.topicId = _other_.topicId;
		this.fav = _other_.fav;
		this.result = _other_.result;
		this.time = _other_.time;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("userId"))
			userId = _json_.getString("userId");
		if (_json_.containsKey("topicId"))
			topicId = _json_.getString("topicId");
		if (_json_.containsKey("fav"))
			fav = _json_.getInteger("fav");
		if (_json_.containsKey("result"))
			result = _json_.getInteger("result");
		if (_json_.containsKey("time"))
			time = _json_.getLong("time");
	}

	public static STopicRecord load(String str)
	{
		try
		{
			STopicRecord obj = new STopicRecord();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static STopicRecord load(JSONObject json)
	{
		try
		{
			STopicRecord obj = new STopicRecord();
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
			if (fav != null)
				_json_.put("fav", fav);
			if (result != null)
				_json_.put("result", result);
			if (time != null)
				_json_.put("time", String.valueOf(time));
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<STopicRecord> loadList(JSONArray ja)
	{
		try
		{
			List<STopicRecord> list = new ArrayList<STopicRecord>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				STopicRecord item = STopicRecord.load(jo);
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

	public static JSONArray saveList(List<STopicRecord> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			STopicRecord item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(TopicRecord _proto_)
	{
		if (_proto_.hasUserId())
			userId = _proto_.getUserId();
		if (_proto_.hasTopicId())
			topicId = _proto_.getTopicId();
		if (_proto_.hasFav())
			fav = _proto_.getFav();
		if (_proto_.hasResult())
			result = _proto_.getResult();
		if (_proto_.hasTime())
			time = _proto_.getTime();
	}

	public static STopicRecord load(byte[] bytes)
	{
		try
		{
			STopicRecord obj = new STopicRecord();
			obj.parse(TopicRecord.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static STopicRecord load(TopicRecord proto)
	{
		try
		{
			STopicRecord obj = new STopicRecord();
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
	public TopicRecord saveToProto()
	{
		TopicRecord.Builder _builder_ = TopicRecord.newBuilder();
		if (userId != null && !userId.equals(TopicRecord.getDefaultInstance().getUserId()))
			_builder_.setUserId(userId);
		if (topicId != null && !topicId.equals(TopicRecord.getDefaultInstance().getTopicId()))
			_builder_.setTopicId(topicId);
		if (fav != null && !fav.equals(TopicRecord.getDefaultInstance().getFav()))
			_builder_.setFav(fav);
		if (result != null && !result.equals(TopicRecord.getDefaultInstance().getResult()))
			_builder_.setResult(result);
		if (time != null && !time.equals(TopicRecord.getDefaultInstance().getTime()))
			_builder_.setTime(time);
		return _builder_.build();
	}
}
