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
import com.lys.protobuf.ProtocolPair2.Response_TopicRecordGetList;

public class SResponse_TopicRecordGetList extends SPTData<Response_TopicRecordGetList>
{
	private static final SResponse_TopicRecordGetList DefaultInstance = new SResponse_TopicRecordGetList();

	public List<STopicRecord> topicRecords = new ArrayList<STopicRecord>();

	public static SResponse_TopicRecordGetList create()
	{
		SResponse_TopicRecordGetList obj = new SResponse_TopicRecordGetList();
		return obj;
	}

	public SResponse_TopicRecordGetList clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SResponse_TopicRecordGetList _other_)
	{
		this.topicRecords = _other_.topicRecords;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("topicRecords"))
			topicRecords = STopicRecord.loadList(_json_.getJSONArray("topicRecords"));
	}

	public static SResponse_TopicRecordGetList load(String str)
	{
		try
		{
			SResponse_TopicRecordGetList obj = new SResponse_TopicRecordGetList();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_TopicRecordGetList load(JSONObject json)
	{
		try
		{
			SResponse_TopicRecordGetList obj = new SResponse_TopicRecordGetList();
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
			if (topicRecords != null)
				_json_.put("topicRecords", STopicRecord.saveList(topicRecords));
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SResponse_TopicRecordGetList> loadList(JSONArray ja)
	{
		try
		{
			List<SResponse_TopicRecordGetList> list = new ArrayList<SResponse_TopicRecordGetList>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SResponse_TopicRecordGetList item = SResponse_TopicRecordGetList.load(jo);
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

	public static JSONArray saveList(List<SResponse_TopicRecordGetList> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SResponse_TopicRecordGetList item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Response_TopicRecordGetList _proto_)
	{
		for (int i = 0; i < _proto_.getTopicRecordsCount(); i++)
			topicRecords.add(STopicRecord.load(_proto_.getTopicRecords(i)));
	}

	public static SResponse_TopicRecordGetList load(byte[] bytes)
	{
		try
		{
			SResponse_TopicRecordGetList obj = new SResponse_TopicRecordGetList();
			obj.parse(Response_TopicRecordGetList.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_TopicRecordGetList load(Response_TopicRecordGetList proto)
	{
		try
		{
			SResponse_TopicRecordGetList obj = new SResponse_TopicRecordGetList();
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
	public Response_TopicRecordGetList saveToProto()
	{
		Response_TopicRecordGetList.Builder _builder_ = Response_TopicRecordGetList.newBuilder();
		if (topicRecords != null)
			for (STopicRecord _value_ : topicRecords)
				_builder_.addTopicRecords(_value_.saveToProto());
		return _builder_.build();
	}
}
