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
import com.lys.protobuf.ProtocolPair2.Response_SearchTopics;

public class SResponse_SearchTopics extends SPTData<Response_SearchTopics>
{
	private static final SResponse_SearchTopics DefaultInstance = new SResponse_SearchTopics();

	public Integer totalCount = 0;
	public List<STopic> topics = new ArrayList<STopic>(); // 题目

	public static SResponse_SearchTopics create(Integer totalCount)
	{
		SResponse_SearchTopics obj = new SResponse_SearchTopics();
		obj.totalCount = totalCount;
		return obj;
	}

	public SResponse_SearchTopics clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SResponse_SearchTopics _other_)
	{
		this.totalCount = _other_.totalCount;
		this.topics = _other_.topics;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("totalCount"))
			totalCount = _json_.getInteger("totalCount");
		if (_json_.containsKey("topics"))
			topics = STopic.loadList(_json_.getJSONArray("topics"));
	}

	public static SResponse_SearchTopics load(String str)
	{
		try
		{
			SResponse_SearchTopics obj = new SResponse_SearchTopics();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_SearchTopics load(JSONObject json)
	{
		try
		{
			SResponse_SearchTopics obj = new SResponse_SearchTopics();
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
			if (totalCount != null)
				_json_.put("totalCount", totalCount);
			if (topics != null)
				_json_.put("topics", STopic.saveList(topics));
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SResponse_SearchTopics> loadList(JSONArray ja)
	{
		try
		{
			List<SResponse_SearchTopics> list = new ArrayList<SResponse_SearchTopics>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SResponse_SearchTopics item = SResponse_SearchTopics.load(jo);
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

	public static JSONArray saveList(List<SResponse_SearchTopics> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SResponse_SearchTopics item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Response_SearchTopics _proto_)
	{
		if (_proto_.hasTotalCount())
			totalCount = _proto_.getTotalCount();
		for (int i = 0; i < _proto_.getTopicsCount(); i++)
			topics.add(STopic.load(_proto_.getTopics(i)));
	}

	public static SResponse_SearchTopics load(byte[] bytes)
	{
		try
		{
			SResponse_SearchTopics obj = new SResponse_SearchTopics();
			obj.parse(Response_SearchTopics.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_SearchTopics load(Response_SearchTopics proto)
	{
		try
		{
			SResponse_SearchTopics obj = new SResponse_SearchTopics();
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
	public Response_SearchTopics saveToProto()
	{
		Response_SearchTopics.Builder _builder_ = Response_SearchTopics.newBuilder();
		if (totalCount != null && !totalCount.equals(Response_SearchTopics.getDefaultInstance().getTotalCount()))
			_builder_.setTotalCount(totalCount);
		if (topics != null)
			for (STopic _value_ : topics)
				_builder_.addTopics(_value_.saveToProto());
		return _builder_.build();
	}
}
