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
import com.lys.protobuf.ProtocolPair2.Request_TopicRecordGetList;

// ---------------------- xxxxxxxx --------------------------
public class SRequest_TopicRecordGetList extends SPTData<Request_TopicRecordGetList>
{
	private static final SRequest_TopicRecordGetList DefaultInstance = new SRequest_TopicRecordGetList();

	public String userId = null;
	public Integer type = 0; // fav:1  result:2  error:3
	public Long time = 0L;
	public Boolean prev = false;
	public Integer pageSize = 0;

	public static SRequest_TopicRecordGetList create(String userId, Integer type, Long time, Boolean prev, Integer pageSize)
	{
		SRequest_TopicRecordGetList obj = new SRequest_TopicRecordGetList();
		obj.userId = userId;
		obj.type = type;
		obj.time = time;
		obj.prev = prev;
		obj.pageSize = pageSize;
		return obj;
	}

	public SRequest_TopicRecordGetList clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SRequest_TopicRecordGetList _other_)
	{
		this.userId = _other_.userId;
		this.type = _other_.type;
		this.time = _other_.time;
		this.prev = _other_.prev;
		this.pageSize = _other_.pageSize;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("userId"))
			userId = _json_.getString("userId");
		if (_json_.containsKey("type"))
			type = _json_.getInteger("type");
		if (_json_.containsKey("time"))
			time = _json_.getLong("time");
		if (_json_.containsKey("prev"))
			prev = _json_.getBoolean("prev");
		if (_json_.containsKey("pageSize"))
			pageSize = _json_.getInteger("pageSize");
	}

	public static SRequest_TopicRecordGetList load(String str)
	{
		try
		{
			SRequest_TopicRecordGetList obj = new SRequest_TopicRecordGetList();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_TopicRecordGetList load(JSONObject json)
	{
		try
		{
			SRequest_TopicRecordGetList obj = new SRequest_TopicRecordGetList();
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
			if (type != null)
				_json_.put("type", type);
			if (time != null)
				_json_.put("time", String.valueOf(time));
			if (prev != null)
				_json_.put("prev", prev);
			if (pageSize != null)
				_json_.put("pageSize", pageSize);
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SRequest_TopicRecordGetList> loadList(JSONArray ja)
	{
		try
		{
			List<SRequest_TopicRecordGetList> list = new ArrayList<SRequest_TopicRecordGetList>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SRequest_TopicRecordGetList item = SRequest_TopicRecordGetList.load(jo);
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

	public static JSONArray saveList(List<SRequest_TopicRecordGetList> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SRequest_TopicRecordGetList item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Request_TopicRecordGetList _proto_)
	{
		if (_proto_.hasUserId())
			userId = _proto_.getUserId();
		if (_proto_.hasType())
			type = _proto_.getType();
		if (_proto_.hasTime())
			time = _proto_.getTime();
		if (_proto_.hasPrev())
			prev = _proto_.getPrev();
		if (_proto_.hasPageSize())
			pageSize = _proto_.getPageSize();
	}

	public static SRequest_TopicRecordGetList load(byte[] bytes)
	{
		try
		{
			SRequest_TopicRecordGetList obj = new SRequest_TopicRecordGetList();
			obj.parse(Request_TopicRecordGetList.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_TopicRecordGetList load(Request_TopicRecordGetList proto)
	{
		try
		{
			SRequest_TopicRecordGetList obj = new SRequest_TopicRecordGetList();
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
	public Request_TopicRecordGetList saveToProto()
	{
		Request_TopicRecordGetList.Builder _builder_ = Request_TopicRecordGetList.newBuilder();
		if (userId != null && !userId.equals(Request_TopicRecordGetList.getDefaultInstance().getUserId()))
			_builder_.setUserId(userId);
		if (type != null && !type.equals(Request_TopicRecordGetList.getDefaultInstance().getType()))
			_builder_.setType(type);
		if (time != null && !time.equals(Request_TopicRecordGetList.getDefaultInstance().getTime()))
			_builder_.setTime(time);
		if (prev != null && !prev.equals(Request_TopicRecordGetList.getDefaultInstance().getPrev()))
			_builder_.setPrev(prev);
		if (pageSize != null && !pageSize.equals(Request_TopicRecordGetList.getDefaultInstance().getPageSize()))
			_builder_.setPageSize(pageSize);
		return _builder_.build();
	}
}
