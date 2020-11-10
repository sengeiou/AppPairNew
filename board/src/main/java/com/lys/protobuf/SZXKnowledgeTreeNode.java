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
import com.lys.protobuf.ProtocolZhixue.ZXKnowledgeTreeNode;

public class SZXKnowledgeTreeNode extends SPTData<ZXKnowledgeTreeNode>
{
	private static final SZXKnowledgeTreeNode DefaultInstance = new SZXKnowledgeTreeNode();

	public Integer index = 0;
	public String name = null;
	public List<SZXKnowledgeTreeNode> knowledges = new ArrayList<SZXKnowledgeTreeNode>();

	public static SZXKnowledgeTreeNode create(Integer index, String name)
	{
		SZXKnowledgeTreeNode obj = new SZXKnowledgeTreeNode();
		obj.index = index;
		obj.name = name;
		return obj;
	}

	public SZXKnowledgeTreeNode clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SZXKnowledgeTreeNode _other_)
	{
		this.index = _other_.index;
		this.name = _other_.name;
		this.knowledges = _other_.knowledges;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("index"))
			index = _json_.getInteger("index");
		if (_json_.containsKey("name"))
			name = _json_.getString("name");
		if (_json_.containsKey("knowledges"))
			knowledges = SZXKnowledgeTreeNode.loadList(_json_.getJSONArray("knowledges"));
	}

	public static SZXKnowledgeTreeNode load(String str)
	{
		try
		{
			SZXKnowledgeTreeNode obj = new SZXKnowledgeTreeNode();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SZXKnowledgeTreeNode load(JSONObject json)
	{
		try
		{
			SZXKnowledgeTreeNode obj = new SZXKnowledgeTreeNode();
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
			if (index != null)
				_json_.put("index", index);
			if (name != null)
				_json_.put("name", name);
			if (knowledges != null)
				_json_.put("knowledges", SZXKnowledgeTreeNode.saveList(knowledges));
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SZXKnowledgeTreeNode> loadList(JSONArray ja)
	{
		try
		{
			List<SZXKnowledgeTreeNode> list = new ArrayList<SZXKnowledgeTreeNode>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SZXKnowledgeTreeNode item = SZXKnowledgeTreeNode.load(jo);
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

	public static JSONArray saveList(List<SZXKnowledgeTreeNode> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SZXKnowledgeTreeNode item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(ZXKnowledgeTreeNode _proto_)
	{
		if (_proto_.hasIndex())
			index = _proto_.getIndex();
		if (_proto_.hasName())
			name = _proto_.getName();
		for (int i = 0; i < _proto_.getKnowledgesCount(); i++)
			knowledges.add(SZXKnowledgeTreeNode.load(_proto_.getKnowledges(i)));
	}

	public static SZXKnowledgeTreeNode load(byte[] bytes)
	{
		try
		{
			SZXKnowledgeTreeNode obj = new SZXKnowledgeTreeNode();
			obj.parse(ZXKnowledgeTreeNode.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SZXKnowledgeTreeNode load(ZXKnowledgeTreeNode proto)
	{
		try
		{
			SZXKnowledgeTreeNode obj = new SZXKnowledgeTreeNode();
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
	public ZXKnowledgeTreeNode saveToProto()
	{
		ZXKnowledgeTreeNode.Builder _builder_ = ZXKnowledgeTreeNode.newBuilder();
		if (index != null && !index.equals(ZXKnowledgeTreeNode.getDefaultInstance().getIndex()))
			_builder_.setIndex(index);
		if (name != null && !name.equals(ZXKnowledgeTreeNode.getDefaultInstance().getName()))
			_builder_.setName(name);
		if (knowledges != null)
			for (SZXKnowledgeTreeNode _value_ : knowledges)
				_builder_.addKnowledges(_value_.saveToProto());
		return _builder_.build();
	}
}
