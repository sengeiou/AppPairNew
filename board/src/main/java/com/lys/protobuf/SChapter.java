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
import com.lys.protobuf.ProtocolCommon.Chapter;

public class SChapter extends SPTData<Chapter>
{
	private static final SChapter DefaultInstance = new SChapter();

	public String code = null; // 章节编号
	public String name = null; // 章节名称
	public Integer topicCount = 0; // 
	public List<SChapter> nodes = new ArrayList<SChapter>(); // 子节点
	public Integer level = 0; // 前端预留
	public Boolean isOpen = false; // 前端预留
	public SChapter parent = null; // 前端预留
	public Integer state = 0; // 前端预留

	public static SChapter create(String code, String name, Integer topicCount, Integer level, Boolean isOpen, SChapter parent, Integer state)
	{
		SChapter obj = new SChapter();
		obj.code = code;
		obj.name = name;
		obj.topicCount = topicCount;
		obj.level = level;
		obj.isOpen = isOpen;
		obj.parent = parent;
		obj.state = state;
		return obj;
	}

	public SChapter clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SChapter _other_)
	{
		this.code = _other_.code;
		this.name = _other_.name;
		this.topicCount = _other_.topicCount;
		this.nodes = _other_.nodes;
		this.level = _other_.level;
		this.isOpen = _other_.isOpen;
		this.parent = _other_.parent;
		this.state = _other_.state;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("code"))
			code = _json_.getString("code");
		if (_json_.containsKey("name"))
			name = _json_.getString("name");
		if (_json_.containsKey("topicCount"))
			topicCount = _json_.getInteger("topicCount");
		if (_json_.containsKey("nodes"))
			nodes = SChapter.loadList(_json_.getJSONArray("nodes"));
		if (_json_.containsKey("level"))
			level = _json_.getInteger("level");
		if (_json_.containsKey("isOpen"))
			isOpen = _json_.getBoolean("isOpen");
		if (_json_.containsKey("parent"))
			parent = SChapter.load(_json_.getJSONObject("parent"));
		if (_json_.containsKey("state"))
			state = _json_.getInteger("state");
	}

	public static SChapter load(String str)
	{
		try
		{
			SChapter obj = new SChapter();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SChapter load(JSONObject json)
	{
		try
		{
			SChapter obj = new SChapter();
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
			if (code != null)
				_json_.put("code", code);
			if (name != null)
				_json_.put("name", name);
			if (topicCount != null)
				_json_.put("topicCount", topicCount);
			if (nodes != null)
				_json_.put("nodes", SChapter.saveList(nodes));
			if (level != null)
				_json_.put("level", level);
			if (isOpen != null)
				_json_.put("isOpen", isOpen);
			if (parent != null)
				_json_.put("parent", parent.saveToJson());
			if (state != null)
				_json_.put("state", state);
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SChapter> loadList(JSONArray ja)
	{
		try
		{
			List<SChapter> list = new ArrayList<SChapter>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SChapter item = SChapter.load(jo);
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

	public static JSONArray saveList(List<SChapter> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SChapter item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Chapter _proto_)
	{
		if (_proto_.hasCode())
			code = _proto_.getCode();
		if (_proto_.hasName())
			name = _proto_.getName();
		if (_proto_.hasTopicCount())
			topicCount = _proto_.getTopicCount();
		for (int i = 0; i < _proto_.getNodesCount(); i++)
			nodes.add(SChapter.load(_proto_.getNodes(i)));
		if (_proto_.hasLevel())
			level = _proto_.getLevel();
		if (_proto_.hasIsOpen())
			isOpen = _proto_.getIsOpen();
		if (_proto_.hasParent())
			parent = SChapter.load(_proto_.getParent());
		if (_proto_.hasState())
			state = _proto_.getState();
	}

	public static SChapter load(byte[] bytes)
	{
		try
		{
			SChapter obj = new SChapter();
			obj.parse(Chapter.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SChapter load(Chapter proto)
	{
		try
		{
			SChapter obj = new SChapter();
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
	public Chapter saveToProto()
	{
		Chapter.Builder _builder_ = Chapter.newBuilder();
		if (code != null && !code.equals(Chapter.getDefaultInstance().getCode()))
			_builder_.setCode(code);
		if (name != null && !name.equals(Chapter.getDefaultInstance().getName()))
			_builder_.setName(name);
		if (topicCount != null && !topicCount.equals(Chapter.getDefaultInstance().getTopicCount()))
			_builder_.setTopicCount(topicCount);
		if (nodes != null)
			for (SChapter _value_ : nodes)
				_builder_.addNodes(_value_.saveToProto());
		if (level != null && !level.equals(Chapter.getDefaultInstance().getLevel()))
			_builder_.setLevel(level);
		if (isOpen != null && !isOpen.equals(Chapter.getDefaultInstance().getIsOpen()))
			_builder_.setIsOpen(isOpen);
		if (parent != null)
			_builder_.setParent(parent.saveToProto());
		if (state != null && !state.equals(Chapter.getDefaultInstance().getState()))
			_builder_.setState(state);
		return _builder_.build();
	}
}
