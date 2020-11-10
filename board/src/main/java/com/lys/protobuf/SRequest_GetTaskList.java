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
import com.lys.protobuf.ProtocolPair.Request_GetTaskList;

// ---------------------- 获取任务列表 --------------------------
public class SRequest_GetTaskList extends SPTData<Request_GetTaskList>
{
	private static final SRequest_GetTaskList DefaultInstance = new SRequest_GetTaskList();

	public String userId = null;
	public String group = null;
	public Integer overType = 0; // 0:all   1:not over   2:over
	public Integer sortType = 0; // 0:createTime   1:overTime
	public Long createTime = 0L;
	public Long overTime = 0L;
	public Boolean prev = false;
	public Integer pageSize = 0;

	public static SRequest_GetTaskList create(String userId, String group, Integer overType, Integer sortType, Long createTime, Long overTime, Boolean prev, Integer pageSize)
	{
		SRequest_GetTaskList obj = new SRequest_GetTaskList();
		obj.userId = userId;
		obj.group = group;
		obj.overType = overType;
		obj.sortType = sortType;
		obj.createTime = createTime;
		obj.overTime = overTime;
		obj.prev = prev;
		obj.pageSize = pageSize;
		return obj;
	}

	public SRequest_GetTaskList clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SRequest_GetTaskList _other_)
	{
		this.userId = _other_.userId;
		this.group = _other_.group;
		this.overType = _other_.overType;
		this.sortType = _other_.sortType;
		this.createTime = _other_.createTime;
		this.overTime = _other_.overTime;
		this.prev = _other_.prev;
		this.pageSize = _other_.pageSize;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("userId"))
			userId = _json_.getString("userId");
		if (_json_.containsKey("group"))
			group = _json_.getString("group");
		if (_json_.containsKey("overType"))
			overType = _json_.getInteger("overType");
		if (_json_.containsKey("sortType"))
			sortType = _json_.getInteger("sortType");
		if (_json_.containsKey("createTime"))
			createTime = _json_.getLong("createTime");
		if (_json_.containsKey("overTime"))
			overTime = _json_.getLong("overTime");
		if (_json_.containsKey("prev"))
			prev = _json_.getBoolean("prev");
		if (_json_.containsKey("pageSize"))
			pageSize = _json_.getInteger("pageSize");
	}

	public static SRequest_GetTaskList load(String str)
	{
		try
		{
			SRequest_GetTaskList obj = new SRequest_GetTaskList();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_GetTaskList load(JSONObject json)
	{
		try
		{
			SRequest_GetTaskList obj = new SRequest_GetTaskList();
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
			if (group != null)
				_json_.put("group", group);
			if (overType != null)
				_json_.put("overType", overType);
			if (sortType != null)
				_json_.put("sortType", sortType);
			if (createTime != null)
				_json_.put("createTime", String.valueOf(createTime));
			if (overTime != null)
				_json_.put("overTime", String.valueOf(overTime));
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

	public static List<SRequest_GetTaskList> loadList(JSONArray ja)
	{
		try
		{
			List<SRequest_GetTaskList> list = new ArrayList<SRequest_GetTaskList>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SRequest_GetTaskList item = SRequest_GetTaskList.load(jo);
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

	public static JSONArray saveList(List<SRequest_GetTaskList> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SRequest_GetTaskList item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Request_GetTaskList _proto_)
	{
		if (_proto_.hasUserId())
			userId = _proto_.getUserId();
		if (_proto_.hasGroup())
			group = _proto_.getGroup();
		if (_proto_.hasOverType())
			overType = _proto_.getOverType();
		if (_proto_.hasSortType())
			sortType = _proto_.getSortType();
		if (_proto_.hasCreateTime())
			createTime = _proto_.getCreateTime();
		if (_proto_.hasOverTime())
			overTime = _proto_.getOverTime();
		if (_proto_.hasPrev())
			prev = _proto_.getPrev();
		if (_proto_.hasPageSize())
			pageSize = _proto_.getPageSize();
	}

	public static SRequest_GetTaskList load(byte[] bytes)
	{
		try
		{
			SRequest_GetTaskList obj = new SRequest_GetTaskList();
			obj.parse(Request_GetTaskList.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_GetTaskList load(Request_GetTaskList proto)
	{
		try
		{
			SRequest_GetTaskList obj = new SRequest_GetTaskList();
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
	public Request_GetTaskList saveToProto()
	{
		Request_GetTaskList.Builder _builder_ = Request_GetTaskList.newBuilder();
		if (userId != null && !userId.equals(Request_GetTaskList.getDefaultInstance().getUserId()))
			_builder_.setUserId(userId);
		if (group != null && !group.equals(Request_GetTaskList.getDefaultInstance().getGroup()))
			_builder_.setGroup(group);
		if (overType != null && !overType.equals(Request_GetTaskList.getDefaultInstance().getOverType()))
			_builder_.setOverType(overType);
		if (sortType != null && !sortType.equals(Request_GetTaskList.getDefaultInstance().getSortType()))
			_builder_.setSortType(sortType);
		if (createTime != null && !createTime.equals(Request_GetTaskList.getDefaultInstance().getCreateTime()))
			_builder_.setCreateTime(createTime);
		if (overTime != null && !overTime.equals(Request_GetTaskList.getDefaultInstance().getOverTime()))
			_builder_.setOverTime(overTime);
		if (prev != null && !prev.equals(Request_GetTaskList.getDefaultInstance().getPrev()))
			_builder_.setPrev(prev);
		if (pageSize != null && !pageSize.equals(Request_GetTaskList.getDefaultInstance().getPageSize()))
			_builder_.setPageSize(pageSize);
		return _builder_.build();
	}
}
