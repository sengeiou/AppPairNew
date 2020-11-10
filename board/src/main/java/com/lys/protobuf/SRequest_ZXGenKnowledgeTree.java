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
import com.lys.protobuf.ProtocolZhixue.Request_ZXGenKnowledgeTree;

// ---------------------- XXXXXX --------------------------
public class SRequest_ZXGenKnowledgeTree extends SPTData<Request_ZXGenKnowledgeTree>
{
	private static final SRequest_ZXGenKnowledgeTree DefaultInstance = new SRequest_ZXGenKnowledgeTree();

	public SZXKnowledgeTree knowledgeTree = null;

	public static SRequest_ZXGenKnowledgeTree create(SZXKnowledgeTree knowledgeTree)
	{
		SRequest_ZXGenKnowledgeTree obj = new SRequest_ZXGenKnowledgeTree();
		obj.knowledgeTree = knowledgeTree;
		return obj;
	}

	public SRequest_ZXGenKnowledgeTree clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SRequest_ZXGenKnowledgeTree _other_)
	{
		this.knowledgeTree = _other_.knowledgeTree;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("knowledgeTree"))
			knowledgeTree = SZXKnowledgeTree.load(_json_.getJSONObject("knowledgeTree"));
	}

	public static SRequest_ZXGenKnowledgeTree load(String str)
	{
		try
		{
			SRequest_ZXGenKnowledgeTree obj = new SRequest_ZXGenKnowledgeTree();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_ZXGenKnowledgeTree load(JSONObject json)
	{
		try
		{
			SRequest_ZXGenKnowledgeTree obj = new SRequest_ZXGenKnowledgeTree();
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
			if (knowledgeTree != null)
				_json_.put("knowledgeTree", knowledgeTree.saveToJson());
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SRequest_ZXGenKnowledgeTree> loadList(JSONArray ja)
	{
		try
		{
			List<SRequest_ZXGenKnowledgeTree> list = new ArrayList<SRequest_ZXGenKnowledgeTree>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SRequest_ZXGenKnowledgeTree item = SRequest_ZXGenKnowledgeTree.load(jo);
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

	public static JSONArray saveList(List<SRequest_ZXGenKnowledgeTree> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SRequest_ZXGenKnowledgeTree item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Request_ZXGenKnowledgeTree _proto_)
	{
		if (_proto_.hasKnowledgeTree())
			knowledgeTree = SZXKnowledgeTree.load(_proto_.getKnowledgeTree());
	}

	public static SRequest_ZXGenKnowledgeTree load(byte[] bytes)
	{
		try
		{
			SRequest_ZXGenKnowledgeTree obj = new SRequest_ZXGenKnowledgeTree();
			obj.parse(Request_ZXGenKnowledgeTree.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_ZXGenKnowledgeTree load(Request_ZXGenKnowledgeTree proto)
	{
		try
		{
			SRequest_ZXGenKnowledgeTree obj = new SRequest_ZXGenKnowledgeTree();
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
	public Request_ZXGenKnowledgeTree saveToProto()
	{
		Request_ZXGenKnowledgeTree.Builder _builder_ = Request_ZXGenKnowledgeTree.newBuilder();
		if (knowledgeTree != null)
			_builder_.setKnowledgeTree(knowledgeTree.saveToProto());
		return _builder_.build();
	}
}
