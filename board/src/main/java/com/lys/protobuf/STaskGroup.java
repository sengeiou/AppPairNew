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
import com.lys.protobuf.ProtocolScore.TaskGroup;

public class STaskGroup extends SPTData<TaskGroup>
{
	private static final STaskGroup DefaultInstance = new STaskGroup();

	public String id = null;
	public String name = null;
	public Integer important = 0;
	public Integer difficulty = 0;
	public String cover = null;
	public Long sort = 0L;
	public Integer allCount = 0;
	public Integer newCount = 0;

	public static STaskGroup create(String id, String name, Integer important, Integer difficulty, String cover, Long sort, Integer allCount, Integer newCount)
	{
		STaskGroup obj = new STaskGroup();
		obj.id = id;
		obj.name = name;
		obj.important = important;
		obj.difficulty = difficulty;
		obj.cover = cover;
		obj.sort = sort;
		obj.allCount = allCount;
		obj.newCount = newCount;
		return obj;
	}

	public STaskGroup clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(STaskGroup _other_)
	{
		this.id = _other_.id;
		this.name = _other_.name;
		this.important = _other_.important;
		this.difficulty = _other_.difficulty;
		this.cover = _other_.cover;
		this.sort = _other_.sort;
		this.allCount = _other_.allCount;
		this.newCount = _other_.newCount;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("id"))
			id = _json_.getString("id");
		if (_json_.containsKey("name"))
			name = _json_.getString("name");
		if (_json_.containsKey("important"))
			important = _json_.getInteger("important");
		if (_json_.containsKey("difficulty"))
			difficulty = _json_.getInteger("difficulty");
		if (_json_.containsKey("cover"))
			cover = _json_.getString("cover");
		if (_json_.containsKey("sort"))
			sort = _json_.getLong("sort");
		if (_json_.containsKey("allCount"))
			allCount = _json_.getInteger("allCount");
		if (_json_.containsKey("newCount"))
			newCount = _json_.getInteger("newCount");
	}

	public static STaskGroup load(String str)
	{
		try
		{
			STaskGroup obj = new STaskGroup();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static STaskGroup load(JSONObject json)
	{
		try
		{
			STaskGroup obj = new STaskGroup();
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
			if (id != null)
				_json_.put("id", id);
			if (name != null)
				_json_.put("name", name);
			if (important != null)
				_json_.put("important", important);
			if (difficulty != null)
				_json_.put("difficulty", difficulty);
			if (cover != null)
				_json_.put("cover", cover);
			if (sort != null)
				_json_.put("sort", String.valueOf(sort));
			if (allCount != null)
				_json_.put("allCount", allCount);
			if (newCount != null)
				_json_.put("newCount", newCount);
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<STaskGroup> loadList(JSONArray ja)
	{
		try
		{
			List<STaskGroup> list = new ArrayList<STaskGroup>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				STaskGroup item = STaskGroup.load(jo);
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

	public static JSONArray saveList(List<STaskGroup> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			STaskGroup item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(TaskGroup _proto_)
	{
		if (_proto_.hasId())
			id = _proto_.getId();
		if (_proto_.hasName())
			name = _proto_.getName();
		if (_proto_.hasImportant())
			important = _proto_.getImportant();
		if (_proto_.hasDifficulty())
			difficulty = _proto_.getDifficulty();
		if (_proto_.hasCover())
			cover = _proto_.getCover();
		if (_proto_.hasSort())
			sort = _proto_.getSort();
		if (_proto_.hasAllCount())
			allCount = _proto_.getAllCount();
		if (_proto_.hasNewCount())
			newCount = _proto_.getNewCount();
	}

	public static STaskGroup load(byte[] bytes)
	{
		try
		{
			STaskGroup obj = new STaskGroup();
			obj.parse(TaskGroup.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static STaskGroup load(TaskGroup proto)
	{
		try
		{
			STaskGroup obj = new STaskGroup();
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
	public TaskGroup saveToProto()
	{
		TaskGroup.Builder _builder_ = TaskGroup.newBuilder();
		if (id != null && !id.equals(TaskGroup.getDefaultInstance().getId()))
			_builder_.setId(id);
		if (name != null && !name.equals(TaskGroup.getDefaultInstance().getName()))
			_builder_.setName(name);
		if (important != null && !important.equals(TaskGroup.getDefaultInstance().getImportant()))
			_builder_.setImportant(important);
		if (difficulty != null && !difficulty.equals(TaskGroup.getDefaultInstance().getDifficulty()))
			_builder_.setDifficulty(difficulty);
		if (cover != null && !cover.equals(TaskGroup.getDefaultInstance().getCover()))
			_builder_.setCover(cover);
		if (sort != null && !sort.equals(TaskGroup.getDefaultInstance().getSort()))
			_builder_.setSort(sort);
		if (allCount != null && !allCount.equals(TaskGroup.getDefaultInstance().getAllCount()))
			_builder_.setAllCount(allCount);
		if (newCount != null && !newCount.equals(TaskGroup.getDefaultInstance().getNewCount()))
			_builder_.setNewCount(newCount);
		return _builder_.build();
	}
}
