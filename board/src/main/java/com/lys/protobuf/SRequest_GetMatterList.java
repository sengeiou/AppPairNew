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
import com.lys.protobuf.ProtocolShop.Request_GetMatterList;

// ---------------------- 获取物料列表 --------------------------
public class SRequest_GetMatterList extends SPTData<Request_GetMatterList>
{
	private static final SRequest_GetMatterList DefaultInstance = new SRequest_GetMatterList();

	public /*SMatterListType*/ Integer type = Request_GetMatterList.getDefaultInstance().getType().getNumber();
	public Boolean containInvalid = false; // 是否包含无效
	public Long sort = 0L;
	public Boolean prev = false;
	public Integer pageSize = 0;

	public static SRequest_GetMatterList create(Integer type, Boolean containInvalid, Long sort, Boolean prev, Integer pageSize)
	{
		SRequest_GetMatterList obj = new SRequest_GetMatterList();
		obj.type = type;
		obj.containInvalid = containInvalid;
		obj.sort = sort;
		obj.prev = prev;
		obj.pageSize = pageSize;
		return obj;
	}

	public SRequest_GetMatterList clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SRequest_GetMatterList _other_)
	{
		this.type = _other_.type;
		this.containInvalid = _other_.containInvalid;
		this.sort = _other_.sort;
		this.prev = _other_.prev;
		this.pageSize = _other_.pageSize;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("type"))
			type = _json_.getInteger("type");
		if (_json_.containsKey("containInvalid"))
			containInvalid = _json_.getBoolean("containInvalid");
		if (_json_.containsKey("sort"))
			sort = _json_.getLong("sort");
		if (_json_.containsKey("prev"))
			prev = _json_.getBoolean("prev");
		if (_json_.containsKey("pageSize"))
			pageSize = _json_.getInteger("pageSize");
	}

	public static SRequest_GetMatterList load(String str)
	{
		try
		{
			SRequest_GetMatterList obj = new SRequest_GetMatterList();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_GetMatterList load(JSONObject json)
	{
		try
		{
			SRequest_GetMatterList obj = new SRequest_GetMatterList();
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
			if (type != null)
				_json_.put("type", type);
			if (containInvalid != null)
				_json_.put("containInvalid", containInvalid);
			if (sort != null)
				_json_.put("sort", String.valueOf(sort));
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

	public static List<SRequest_GetMatterList> loadList(JSONArray ja)
	{
		try
		{
			List<SRequest_GetMatterList> list = new ArrayList<SRequest_GetMatterList>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SRequest_GetMatterList item = SRequest_GetMatterList.load(jo);
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

	public static JSONArray saveList(List<SRequest_GetMatterList> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SRequest_GetMatterList item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Request_GetMatterList _proto_)
	{
		if (_proto_.hasType())
			type = _proto_.getType().getNumber();
		if (_proto_.hasContainInvalid())
			containInvalid = _proto_.getContainInvalid();
		if (_proto_.hasSort())
			sort = _proto_.getSort();
		if (_proto_.hasPrev())
			prev = _proto_.getPrev();
		if (_proto_.hasPageSize())
			pageSize = _proto_.getPageSize();
	}

	public static SRequest_GetMatterList load(byte[] bytes)
	{
		try
		{
			SRequest_GetMatterList obj = new SRequest_GetMatterList();
			obj.parse(Request_GetMatterList.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_GetMatterList load(Request_GetMatterList proto)
	{
		try
		{
			SRequest_GetMatterList obj = new SRequest_GetMatterList();
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
	public Request_GetMatterList saveToProto()
	{
		Request_GetMatterList.Builder _builder_ = Request_GetMatterList.newBuilder();
		if (type != null && Request_GetMatterList.getDefaultInstance().getType().getNumber() != type)
			_builder_.setType(ProtocolShop.MatterListType.valueOf(type));
		if (containInvalid != null && !containInvalid.equals(Request_GetMatterList.getDefaultInstance().getContainInvalid()))
			_builder_.setContainInvalid(containInvalid);
		if (sort != null && !sort.equals(Request_GetMatterList.getDefaultInstance().getSort()))
			_builder_.setSort(sort);
		if (prev != null && !prev.equals(Request_GetMatterList.getDefaultInstance().getPrev()))
			_builder_.setPrev(prev);
		if (pageSize != null && !pageSize.equals(Request_GetMatterList.getDefaultInstance().getPageSize()))
			_builder_.setPageSize(pageSize);
		return _builder_.build();
	}
}
