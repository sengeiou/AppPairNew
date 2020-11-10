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
import com.lys.protobuf.ProtocolPair2.Response_TopicRecordSetFav;

public class SResponse_TopicRecordSetFav extends SPTData<Response_TopicRecordSetFav>
{
	private static final SResponse_TopicRecordSetFav DefaultInstance = new SResponse_TopicRecordSetFav();

	public STopicRecord topicRecord = null;

	public static SResponse_TopicRecordSetFav create(STopicRecord topicRecord)
	{
		SResponse_TopicRecordSetFav obj = new SResponse_TopicRecordSetFav();
		obj.topicRecord = topicRecord;
		return obj;
	}

	public SResponse_TopicRecordSetFav clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SResponse_TopicRecordSetFav _other_)
	{
		this.topicRecord = _other_.topicRecord;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("topicRecord"))
			topicRecord = STopicRecord.load(_json_.getJSONObject("topicRecord"));
	}

	public static SResponse_TopicRecordSetFav load(String str)
	{
		try
		{
			SResponse_TopicRecordSetFav obj = new SResponse_TopicRecordSetFav();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_TopicRecordSetFav load(JSONObject json)
	{
		try
		{
			SResponse_TopicRecordSetFav obj = new SResponse_TopicRecordSetFav();
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

	public static List<SResponse_TopicRecordSetFav> loadList(JSONArray ja)
	{
		try
		{
			List<SResponse_TopicRecordSetFav> list = new ArrayList<SResponse_TopicRecordSetFav>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SResponse_TopicRecordSetFav item = SResponse_TopicRecordSetFav.load(jo);
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

	public static JSONArray saveList(List<SResponse_TopicRecordSetFav> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SResponse_TopicRecordSetFav item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Response_TopicRecordSetFav _proto_)
	{
		if (_proto_.hasTopicRecord())
			topicRecord = STopicRecord.load(_proto_.getTopicRecord());
	}

	public static SResponse_TopicRecordSetFav load(byte[] bytes)
	{
		try
		{
			SResponse_TopicRecordSetFav obj = new SResponse_TopicRecordSetFav();
			obj.parse(Response_TopicRecordSetFav.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_TopicRecordSetFav load(Response_TopicRecordSetFav proto)
	{
		try
		{
			SResponse_TopicRecordSetFav obj = new SResponse_TopicRecordSetFav();
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
	public Response_TopicRecordSetFav saveToProto()
	{
		Response_TopicRecordSetFav.Builder _builder_ = Response_TopicRecordSetFav.newBuilder();
		if (topicRecord != null)
			_builder_.setTopicRecord(topicRecord.saveToProto());
		return _builder_.build();
	}
}
