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
import com.lys.protobuf.ProtocolZhixue.ZXKnowledgeTree;

public class SZXKnowledgeTree extends SPTData<ZXKnowledgeTree>
{
	private static final SZXKnowledgeTree DefaultInstance = new SZXKnowledgeTree();

	public List<SZXKnowledgeTreeNode> knowledges = new ArrayList<SZXKnowledgeTreeNode>();
	public String phase = null;
	public String subject = null;

	public static SZXKnowledgeTree create(String phase, String subject)
	{
		SZXKnowledgeTree obj = new SZXKnowledgeTree();
		obj.phase = phase;
		obj.subject = subject;
		return obj;
	}

	public SZXKnowledgeTree clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SZXKnowledgeTree _other_)
	{
		this.knowledges = _other_.knowledges;
		this.phase = _other_.phase;
		this.subject = _other_.subject;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("knowledges"))
			knowledges = SZXKnowledgeTreeNode.loadList(_json_.getJSONArray("knowledges"));
		if (_json_.containsKey("phase"))
			phase = _json_.getString("phase");
		if (_json_.containsKey("subject"))
			subject = _json_.getString("subject");
	}

	public static SZXKnowledgeTree load(String str)
	{
		try
		{
			SZXKnowledgeTree obj = new SZXKnowledgeTree();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SZXKnowledgeTree load(JSONObject json)
	{
		try
		{
			SZXKnowledgeTree obj = new SZXKnowledgeTree();
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
			if (knowledges != null)
				_json_.put("knowledges", SZXKnowledgeTreeNode.saveList(knowledges));
			if (phase != null)
				_json_.put("phase", phase);
			if (subject != null)
				_json_.put("subject", subject);
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SZXKnowledgeTree> loadList(JSONArray ja)
	{
		try
		{
			List<SZXKnowledgeTree> list = new ArrayList<SZXKnowledgeTree>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SZXKnowledgeTree item = SZXKnowledgeTree.load(jo);
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

	public static JSONArray saveList(List<SZXKnowledgeTree> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SZXKnowledgeTree item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(ZXKnowledgeTree _proto_)
	{
		for (int i = 0; i < _proto_.getKnowledgesCount(); i++)
			knowledges.add(SZXKnowledgeTreeNode.load(_proto_.getKnowledges(i)));
		if (_proto_.hasPhase())
			phase = _proto_.getPhase();
		if (_proto_.hasSubject())
			subject = _proto_.getSubject();
	}

	public static SZXKnowledgeTree load(byte[] bytes)
	{
		try
		{
			SZXKnowledgeTree obj = new SZXKnowledgeTree();
			obj.parse(ZXKnowledgeTree.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SZXKnowledgeTree load(ZXKnowledgeTree proto)
	{
		try
		{
			SZXKnowledgeTree obj = new SZXKnowledgeTree();
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
	public ZXKnowledgeTree saveToProto()
	{
		ZXKnowledgeTree.Builder _builder_ = ZXKnowledgeTree.newBuilder();
		if (knowledges != null)
			for (SZXKnowledgeTreeNode _value_ : knowledges)
				_builder_.addKnowledges(_value_.saveToProto());
		if (phase != null && !phase.equals(ZXKnowledgeTree.getDefaultInstance().getPhase()))
			_builder_.setPhase(phase);
		if (subject != null && !subject.equals(ZXKnowledgeTree.getDefaultInstance().getSubject()))
			_builder_.setSubject(subject);
		return _builder_.build();
	}
}
