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
import com.lys.protobuf.ProtocolScore.Request_GetOrderList;

// ---------------------- 获取兑换列表 --------------------------
public class SRequest_GetOrderList extends SPTData<Request_GetOrderList>
{
	private static final SRequest_GetOrderList DefaultInstance = new SRequest_GetOrderList();

	public String userId = null;
	public Integer state = 0; // 0:all   1-3:OrderState
	public Long time = 0L;
	public Boolean prev = false;
	public Integer pageSize = 0;

	public static SRequest_GetOrderList create(String userId, Integer state, Long time, Boolean prev, Integer pageSize)
	{
		SRequest_GetOrderList obj = new SRequest_GetOrderList();
		obj.userId = userId;
		obj.state = state;
		obj.time = time;
		obj.prev = prev;
		obj.pageSize = pageSize;
		return obj;
	}

	public SRequest_GetOrderList clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SRequest_GetOrderList _other_)
	{
		this.userId = _other_.userId;
		this.state = _other_.state;
		this.time = _other_.time;
		this.prev = _other_.prev;
		this.pageSize = _other_.pageSize;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("userId"))
			userId = _json_.getString("userId");
		if (_json_.containsKey("state"))
			state = _json_.getInteger("state");
		if (_json_.containsKey("time"))
			time = _json_.getLong("time");
		if (_json_.containsKey("prev"))
			prev = _json_.getBoolean("prev");
		if (_json_.containsKey("pageSize"))
			pageSize = _json_.getInteger("pageSize");
	}

	public static SRequest_GetOrderList load(String str)
	{
		try
		{
			SRequest_GetOrderList obj = new SRequest_GetOrderList();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_GetOrderList load(JSONObject json)
	{
		try
		{
			SRequest_GetOrderList obj = new SRequest_GetOrderList();
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
			if (state != null)
				_json_.put("state", state);
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

	public static List<SRequest_GetOrderList> loadList(JSONArray ja)
	{
		try
		{
			List<SRequest_GetOrderList> list = new ArrayList<SRequest_GetOrderList>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SRequest_GetOrderList item = SRequest_GetOrderList.load(jo);
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

	public static JSONArray saveList(List<SRequest_GetOrderList> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SRequest_GetOrderList item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Request_GetOrderList _proto_)
	{
		if (_proto_.hasUserId())
			userId = _proto_.getUserId();
		if (_proto_.hasState())
			state = _proto_.getState();
		if (_proto_.hasTime())
			time = _proto_.getTime();
		if (_proto_.hasPrev())
			prev = _proto_.getPrev();
		if (_proto_.hasPageSize())
			pageSize = _proto_.getPageSize();
	}

	public static SRequest_GetOrderList load(byte[] bytes)
	{
		try
		{
			SRequest_GetOrderList obj = new SRequest_GetOrderList();
			obj.parse(Request_GetOrderList.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_GetOrderList load(Request_GetOrderList proto)
	{
		try
		{
			SRequest_GetOrderList obj = new SRequest_GetOrderList();
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
	public Request_GetOrderList saveToProto()
	{
		Request_GetOrderList.Builder _builder_ = Request_GetOrderList.newBuilder();
		if (userId != null && !userId.equals(Request_GetOrderList.getDefaultInstance().getUserId()))
			_builder_.setUserId(userId);
		if (state != null && !state.equals(Request_GetOrderList.getDefaultInstance().getState()))
			_builder_.setState(state);
		if (time != null && !time.equals(Request_GetOrderList.getDefaultInstance().getTime()))
			_builder_.setTime(time);
		if (prev != null && !prev.equals(Request_GetOrderList.getDefaultInstance().getPrev()))
			_builder_.setPrev(prev);
		if (pageSize != null && !pageSize.equals(Request_GetOrderList.getDefaultInstance().getPageSize()))
			_builder_.setPageSize(pageSize);
		return _builder_.build();
	}
}
