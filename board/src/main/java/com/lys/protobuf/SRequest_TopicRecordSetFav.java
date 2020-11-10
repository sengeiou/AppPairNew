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
import com.lys.protobuf.ProtocolPair2.Request_TopicRecordSetFav;

// ---------------------- xxxxxxxx --------------------------
public class SRequest_TopicRecordSetFav extends SPTData<Request_TopicRecordSetFav>
{
	private static final SRequest_TopicRecordSetFav DefaultInstance = new SRequest_TopicRecordSetFav();

	public String userId = null;
	public String topicId = null;
	public Integer fav = 0;

	public static SRequest_TopicRecordSetFav create(String userId, String topicId, Integer fav)
	{
		SRequest_TopicRecordSetFav obj = new SRequest_TopicRecordSetFav();
		obj.userId = userId;
		obj.topicId = topicId;
		obj.fav = fav;
		return obj;
	}

	public SRequest_TopicRecordSetFav clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SRequest_TopicRecordSetFav _other_)
	{
		this.userId = _other_.userId;
		this.topicId = _other_.topicId;
		this.fav = _other_.fav;
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
	}

	public static SRequest_TopicRecordSetFav load(String str)
	{
		try
		{
			SRequest_TopicRecordSetFav obj = new SRequest_TopicRecordSetFav();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_TopicRecordSetFav load(JSONObject json)
	{
		try
		{
			SRequest_TopicRecordSetFav obj = new SRequest_TopicRecordSetFav();
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
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SRequest_TopicRecordSetFav> loadList(JSONArray ja)
	{
		try
		{
			List<SRequest_TopicRecordSetFav> list = new ArrayList<SRequest_TopicRecordSetFav>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SRequest_TopicRecordSetFav item = SRequest_TopicRecordSetFav.load(jo);
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

	public static JSONArray saveList(List<SRequest_TopicRecordSetFav> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SRequest_TopicRecordSetFav item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Request_TopicRecordSetFav _proto_)
	{
		if (_proto_.hasUserId())
			userId = _proto_.getUserId();
		if (_proto_.hasTopicId())
			topicId = _proto_.getTopicId();
		if (_proto_.hasFav())
			fav = _proto_.getFav();
	}

	public static SRequest_TopicRecordSetFav load(byte[] bytes)
	{
		try
		{
			SRequest_TopicRecordSetFav obj = new SRequest_TopicRecordSetFav();
			obj.parse(Request_TopicRecordSetFav.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_TopicRecordSetFav load(Request_TopicRecordSetFav proto)
	{
		try
		{
			SRequest_TopicRecordSetFav obj = new SRequest_TopicRecordSetFav();
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
	public Request_TopicRecordSetFav saveToProto()
	{
		Request_TopicRecordSetFav.Builder _builder_ = Request_TopicRecordSetFav.newBuilder();
		if (userId != null && !userId.equals(Request_TopicRecordSetFav.getDefaultInstance().getUserId()))
			_builder_.setUserId(userId);
		if (topicId != null && !topicId.equals(Request_TopicRecordSetFav.getDefaultInstance().getTopicId()))
			_builder_.setTopicId(topicId);
		if (fav != null && !fav.equals(Request_TopicRecordSetFav.getDefaultInstance().getFav()))
			_builder_.setFav(fav);
		return _builder_.build();
	}
}
