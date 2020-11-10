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
import com.lys.protobuf.ProtocolPair2.Response_TopicRecordSetResult;

public class SResponse_TopicRecordSetResult extends SPTData<Response_TopicRecordSetResult>
{
	private static final SResponse_TopicRecordSetResult DefaultInstance = new SResponse_TopicRecordSetResult();

	public STopicRecord topicRecord = null;

	public static SResponse_TopicRecordSetResult create(STopicRecord topicRecord)
	{
		SResponse_TopicRecordSetResult obj = new SResponse_TopicRecordSetResult();
		obj.topicRecord = topicRecord;
		return obj;
	}

	public SResponse_TopicRecordSetResult clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SResponse_TopicRecordSetResult _other_)
	{
		this.topicRecord = _other_.topicRecord;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("topicRecord"))
			topicRecord = STopicRecord.load(_json_.getJSONObject("topicRecord"));
	}

	public static SResponse_TopicRecordSetResult load(String str)
	{
		try
		{
			SResponse_TopicRecordSetResult obj = new SResponse_TopicRecordSetResult();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_TopicRecordSetResult load(JSONObject json)
	{
		try
		{
			SResponse_TopicRecordSetResult obj = new SResponse_TopicRecordSetResult();
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

	public static List<SResponse_TopicRecordSetResult> loadList(JSONArray ja)
	{
		try
		{
			List<SResponse_TopicRecordSetResult> list = new ArrayList<SResponse_TopicRecordSetResult>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SResponse_TopicRecordSetResult item = SResponse_TopicRecordSetResult.load(jo);
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

	public static JSONArray saveList(List<SResponse_TopicRecordSetResult> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SResponse_TopicRecordSetResult item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Response_TopicRecordSetResult _proto_)
	{
		if (_proto_.hasTopicRecord())
			topicRecord = STopicRecord.load(_proto_.getTopicRecord());
	}

	public static SResponse_TopicRecordSetResult load(byte[] bytes)
	{
		try
		{
			SResponse_TopicRecordSetResult obj = new SResponse_TopicRecordSetResult();
			obj.parse(Response_TopicRecordSetResult.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_TopicRecordSetResult load(Response_TopicRecordSetResult proto)
	{
		try
		{
			SResponse_TopicRecordSetResult obj = new SResponse_TopicRecordSetResult();
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
	public Response_TopicRecordSetResult saveToProto()
	{
		Response_TopicRecordSetResult.Builder _builder_ = Response_TopicRecordSetResult.newBuilder();
		if (topicRecord != null)
			_builder_.setTopicRecord(topicRecord.saveToProto());
		return _builder_.build();
	}
}
