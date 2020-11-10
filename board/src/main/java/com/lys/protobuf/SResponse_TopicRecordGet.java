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
import com.lys.protobuf.ProtocolPair2.Response_TopicRecordGet;

public class SResponse_TopicRecordGet extends SPTData<Response_TopicRecordGet>
{
	private static final SResponse_TopicRecordGet DefaultInstance = new SResponse_TopicRecordGet();

	public STopicRecord topicRecord = null;

	public static SResponse_TopicRecordGet create(STopicRecord topicRecord)
	{
		SResponse_TopicRecordGet obj = new SResponse_TopicRecordGet();
		obj.topicRecord = topicRecord;
		return obj;
	}

	public SResponse_TopicRecordGet clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SResponse_TopicRecordGet _other_)
	{
		this.topicRecord = _other_.topicRecord;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("topicRecord"))
			topicRecord = STopicRecord.load(_json_.getJSONObject("topicRecord"));
	}

	public static SResponse_TopicRecordGet load(String str)
	{
		try
		{
			SResponse_TopicRecordGet obj = new SResponse_TopicRecordGet();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_TopicRecordGet load(JSONObject json)
	{
		try
		{
			SResponse_TopicRecordGet obj = new SResponse_TopicRecordGet();
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
			if (topicRecord != null)
				_json_.put("topicRecord", topicRecord.saveToJson());
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SResponse_TopicRecordGet> loadList(JSONArray ja)
	{
		try
		{
			List<SResponse_TopicRecordGet> list = new ArrayList<SResponse_TopicRecordGet>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SResponse_TopicRecordGet item = SResponse_TopicRecordGet.load(jo);
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

	public static JSONArray saveList(List<SResponse_TopicRecordGet> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SResponse_TopicRecordGet item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Response_TopicRecordGet _proto_)
	{
		if (_proto_.hasTopicRecord())
			topicRecord = STopicRecord.load(_proto_.getTopicRecord());
	}

	public static SResponse_TopicRecordGet load(byte[] bytes)
	{
		try
		{
			SResponse_TopicRecordGet obj = new SResponse_TopicRecordGet();
			obj.parse(Response_TopicRecordGet.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_TopicRecordGet load(Response_TopicRecordGet proto)
	{
		try
		{
			SResponse_TopicRecordGet obj = new SResponse_TopicRecordGet();
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
	public Response_TopicRecordGet saveToProto()
	{
		Response_TopicRecordGet.Builder _builder_ = Response_TopicRecordGet.newBuilder();
		if (topicRecord != null)
			_builder_.setTopicRecord(topicRecord.saveToProto());
		return _builder_.build();
	}
}
