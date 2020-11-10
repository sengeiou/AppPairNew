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
import com.lys.protobuf.ProtocolCommon.Knowledge;

public class SKnowledge extends SPTData<Knowledge>
{
	private static final SKnowledge DefaultInstance = new SKnowledge();

	public String code = null; // 知识点编号
	public String name = null; // 知识点名称
	public Integer topicCount = 0; // 
	public List<SKnowledge> nodes = new ArrayList<SKnowledge>(); // 子节点
	public Integer level = 0; // 前端预留
	public Boolean isOpen = false; // 前端预留
	public SKnowledge parent = null; // 前端预留
	public Integer state = 0; // 前端预留

	public static SKnowledge create(String code, String name, Integer topicCount, Integer level, Boolean isOpen, SKnowledge parent, Integer state)
	{
		SKnowledge obj = new SKnowledge();
		obj.code = code;
		obj.name = name;
		obj.topicCount = topicCount;
		obj.level = level;
		obj.isOpen = isOpen;
		obj.parent = parent;
		obj.state = state;
		return obj;
	}

	public SKnowledge clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SKnowledge _other_)
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
			nodes = SKnowledge.loadList(_json_.getJSONArray("nodes"));
		if (_json_.containsKey("level"))
			level = _json_.getInteger("level");
		if (_json_.containsKey("isOpen"))
			isOpen = _json_.getBoolean("isOpen");
		if (_json_.containsKey("parent"))
			parent = SKnowledge.load(_json_.getJSONObject("parent"));
		if (_json_.containsKey("state"))
			state = _json_.getInteger("state");
	}

	public static SKnowledge load(String str)
	{
		try
		{
			SKnowledge obj = new SKnowledge();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SKnowledge load(JSONObject json)
	{
		try
		{
			SKnowledge obj = new SKnowledge();
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
				_json_.put("nodes", SKnowledge.saveList(nodes));
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

	public static List<SKnowledge> loadList(JSONArray ja)
	{
		try
		{
			List<SKnowledge> list = new ArrayList<SKnowledge>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SKnowledge item = SKnowledge.load(jo);
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

	public static JSONArray saveList(List<SKnowledge> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SKnowledge item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Knowledge _proto_)
	{
		if (_proto_.hasCode())
			code = _proto_.getCode();
		if (_proto_.hasName())
			name = _proto_.getName();
		if (_proto_.hasTopicCount())
			topicCount = _proto_.getTopicCount();
		for (int i = 0; i < _proto_.getNodesCount(); i++)
			nodes.add(SKnowledge.load(_proto_.getNodes(i)));
		if (_proto_.hasLevel())
			level = _proto_.getLevel();
		if (_proto_.hasIsOpen())
			isOpen = _proto_.getIsOpen();
		if (_proto_.hasParent())
			parent = SKnowledge.load(_proto_.getParent());
		if (_proto_.hasState())
			state = _proto_.getState();
	}

	public static SKnowledge load(byte[] bytes)
	{
		try
		{
			SKnowledge obj = new SKnowledge();
			obj.parse(Knowledge.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SKnowledge load(Knowledge proto)
	{
		try
		{
			SKnowledge obj = new SKnowledge();
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
	public Knowledge saveToProto()
	{
		Knowledge.Builder _builder_ = Knowledge.newBuilder();
		if (code != null && !code.equals(Knowledge.getDefaultInstance().getCode()))
			_builder_.setCode(code);
		if (name != null && !name.equals(Knowledge.getDefaultInstance().getName()))
			_builder_.setName(name);
		if (topicCount != null && !topicCount.equals(Knowledge.getDefaultInstance().getTopicCount()))
			_builder_.setTopicCount(topicCount);
		if (nodes != null)
			for (SKnowledge _value_ : nodes)
				_builder_.addNodes(_value_.saveToProto());
		if (level != null && !level.equals(Knowledge.getDefaultInstance().getLevel()))
			_builder_.setLevel(level);
		if (isOpen != null && !isOpen.equals(Knowledge.getDefaultInstance().getIsOpen()))
			_builder_.setIsOpen(isOpen);
		if (parent != null)
			_builder_.setParent(parent.saveToProto());
		if (state != null && !state.equals(Knowledge.getDefaultInstance().getState()))
			_builder_.setState(state);
		return _builder_.build();
	}
}
