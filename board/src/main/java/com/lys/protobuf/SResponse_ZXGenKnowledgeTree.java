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
import com.lys.protobuf.ProtocolZhixue.Response_ZXGenKnowledgeTree;

public class SResponse_ZXGenKnowledgeTree extends SPTData<Response_ZXGenKnowledgeTree>
{
	private static final SResponse_ZXGenKnowledgeTree DefaultInstance = new SResponse_ZXGenKnowledgeTree();

	public List<SKnowledge> knowledges = new ArrayList<SKnowledge>();

	public static SResponse_ZXGenKnowledgeTree create()
	{
		SResponse_ZXGenKnowledgeTree obj = new SResponse_ZXGenKnowledgeTree();
		return obj;
	}

	public SResponse_ZXGenKnowledgeTree clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SResponse_ZXGenKnowledgeTree _other_)
	{
		this.knowledges = _other_.knowledges;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("knowledges"))
			knowledges = SKnowledge.loadList(_json_.getJSONArray("knowledges"));
	}

	public static SResponse_ZXGenKnowledgeTree load(String str)
	{
		try
		{
			SResponse_ZXGenKnowledgeTree obj = new SResponse_ZXGenKnowledgeTree();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_ZXGenKnowledgeTree load(JSONObject json)
	{
		try
		{
			SResponse_ZXGenKnowledgeTree obj = new SResponse_ZXGenKnowledgeTree();
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
				_json_.put("knowledges", SKnowledge.saveList(knowledges));
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SResponse_ZXGenKnowledgeTree> loadList(JSONArray ja)
	{
		try
		{
			List<SResponse_ZXGenKnowledgeTree> list = new ArrayList<SResponse_ZXGenKnowledgeTree>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SResponse_ZXGenKnowledgeTree item = SResponse_ZXGenKnowledgeTree.load(jo);
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

	public static JSONArray saveList(List<SResponse_ZXGenKnowledgeTree> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SResponse_ZXGenKnowledgeTree item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Response_ZXGenKnowledgeTree _proto_)
	{
		for (int i = 0; i < _proto_.getKnowledgesCount(); i++)
			knowledges.add(SKnowledge.load(_proto_.getKnowledges(i)));
	}

	public static SResponse_ZXGenKnowledgeTree load(byte[] bytes)
	{
		try
		{
			SResponse_ZXGenKnowledgeTree obj = new SResponse_ZXGenKnowledgeTree();
			obj.parse(Response_ZXGenKnowledgeTree.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_ZXGenKnowledgeTree load(Response_ZXGenKnowledgeTree proto)
	{
		try
		{
			SResponse_ZXGenKnowledgeTree obj = new SResponse_ZXGenKnowledgeTree();
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
	public Response_ZXGenKnowledgeTree saveToProto()
	{
		Response_ZXGenKnowledgeTree.Builder _builder_ = Response_ZXGenKnowledgeTree.newBuilder();
		if (knowledges != null)
			for (SKnowledge _value_ : knowledges)
				_builder_.addKnowledges(_value_.saveToProto());
		return _builder_.build();
	}
}
