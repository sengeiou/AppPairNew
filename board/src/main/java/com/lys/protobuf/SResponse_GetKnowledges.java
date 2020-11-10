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
import com.lys.protobuf.ProtocolPair2.Response_GetKnowledges;

public class SResponse_GetKnowledges extends SPTData<Response_GetKnowledges>
{
	private static final SResponse_GetKnowledges DefaultInstance = new SResponse_GetKnowledges();

	public List<SKnowledge> knowledges = new ArrayList<SKnowledge>();

	public static SResponse_GetKnowledges create()
	{
		SResponse_GetKnowledges obj = new SResponse_GetKnowledges();
		return obj;
	}

	public SResponse_GetKnowledges clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SResponse_GetKnowledges _other_)
	{
		this.knowledges = _other_.knowledges;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("knowledges"))
			knowledges = SKnowledge.loadList(_json_.getJSONArray("knowledges"));
	}

	public static SResponse_GetKnowledges load(String str)
	{
		try
		{
			SResponse_GetKnowledges obj = new SResponse_GetKnowledges();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_GetKnowledges load(JSONObject json)
	{
		try
		{
			SResponse_GetKnowledges obj = new SResponse_GetKnowledges();
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

	public static List<SResponse_GetKnowledges> loadList(JSONArray ja)
	{
		try
		{
			List<SResponse_GetKnowledges> list = new ArrayList<SResponse_GetKnowledges>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SResponse_GetKnowledges item = SResponse_GetKnowledges.load(jo);
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

	public static JSONArray saveList(List<SResponse_GetKnowledges> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SResponse_GetKnowledges item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Response_GetKnowledges _proto_)
	{
		for (int i = 0; i < _proto_.getKnowledgesCount(); i++)
			knowledges.add(SKnowledge.load(_proto_.getKnowledges(i)));
	}

	public static SResponse_GetKnowledges load(byte[] bytes)
	{
		try
		{
			SResponse_GetKnowledges obj = new SResponse_GetKnowledges();
			obj.parse(Response_GetKnowledges.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_GetKnowledges load(Response_GetKnowledges proto)
	{
		try
		{
			SResponse_GetKnowledges obj = new SResponse_GetKnowledges();
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
	public Response_GetKnowledges saveToProto()
	{
		Response_GetKnowledges.Builder _builder_ = Response_GetKnowledges.newBuilder();
		if (knowledges != null)
			for (SKnowledge _value_ : knowledges)
				_builder_.addKnowledges(_value_.saveToProto());
		return _builder_.build();
	}
}
