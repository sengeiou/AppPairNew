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
import com.lys.protobuf.ProtocolShop.Request_GetCommentList;

// ---------------------- xxxxxxxx --------------------------
public class SRequest_GetCommentList extends SPTData<Request_GetCommentList>
{
	private static final SRequest_GetCommentList DefaultInstance = new SRequest_GetCommentList();

	public String matterId = null;
	public Boolean containAll = false; // 包含所有（尚未通过的）
	public Long time = 0L;
	public Boolean prev = false;
	public Integer pageSize = 0;

	public static SRequest_GetCommentList create(String matterId, Boolean containAll, Long time, Boolean prev, Integer pageSize)
	{
		SRequest_GetCommentList obj = new SRequest_GetCommentList();
		obj.matterId = matterId;
		obj.containAll = containAll;
		obj.time = time;
		obj.prev = prev;
		obj.pageSize = pageSize;
		return obj;
	}

	public SRequest_GetCommentList clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SRequest_GetCommentList _other_)
	{
		this.matterId = _other_.matterId;
		this.containAll = _other_.containAll;
		this.time = _other_.time;
		this.prev = _other_.prev;
		this.pageSize = _other_.pageSize;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("matterId"))
			matterId = _json_.getString("matterId");
		if (_json_.containsKey("containAll"))
			containAll = _json_.getBoolean("containAll");
		if (_json_.containsKey("time"))
			time = _json_.getLong("time");
		if (_json_.containsKey("prev"))
			prev = _json_.getBoolean("prev");
		if (_json_.containsKey("pageSize"))
			pageSize = _json_.getInteger("pageSize");
	}

	public static SRequest_GetCommentList load(String str)
	{
		try
		{
			SRequest_GetCommentList obj = new SRequest_GetCommentList();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_GetCommentList load(JSONObject json)
	{
		try
		{
			SRequest_GetCommentList obj = new SRequest_GetCommentList();
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
			if (matterId != null)
				_json_.put("matterId", matterId);
			if (containAll != null)
				_json_.put("containAll", containAll);
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

	public static List<SRequest_GetCommentList> loadList(JSONArray ja)
	{
		try
		{
			List<SRequest_GetCommentList> list = new ArrayList<SRequest_GetCommentList>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SRequest_GetCommentList item = SRequest_GetCommentList.load(jo);
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

	public static JSONArray saveList(List<SRequest_GetCommentList> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SRequest_GetCommentList item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Request_GetCommentList _proto_)
	{
		if (_proto_.hasMatterId())
			matterId = _proto_.getMatterId();
		if (_proto_.hasContainAll())
			containAll = _proto_.getContainAll();
		if (_proto_.hasTime())
			time = _proto_.getTime();
		if (_proto_.hasPrev())
			prev = _proto_.getPrev();
		if (_proto_.hasPageSize())
			pageSize = _proto_.getPageSize();
	}

	public static SRequest_GetCommentList load(byte[] bytes)
	{
		try
		{
			SRequest_GetCommentList obj = new SRequest_GetCommentList();
			obj.parse(Request_GetCommentList.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_GetCommentList load(Request_GetCommentList proto)
	{
		try
		{
			SRequest_GetCommentList obj = new SRequest_GetCommentList();
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
	public Request_GetCommentList saveToProto()
	{
		Request_GetCommentList.Builder _builder_ = Request_GetCommentList.newBuilder();
		if (matterId != null && !matterId.equals(Request_GetCommentList.getDefaultInstance().getMatterId()))
			_builder_.setMatterId(matterId);
		if (containAll != null && !containAll.equals(Request_GetCommentList.getDefaultInstance().getContainAll()))
			_builder_.setContainAll(containAll);
		if (time != null && !time.equals(Request_GetCommentList.getDefaultInstance().getTime()))
			_builder_.setTime(time);
		if (prev != null && !prev.equals(Request_GetCommentList.getDefaultInstance().getPrev()))
			_builder_.setPrev(prev);
		if (pageSize != null && !pageSize.equals(Request_GetCommentList.getDefaultInstance().getPageSize()))
			_builder_.setPageSize(pageSize);
		return _builder_.build();
	}
}
