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
import com.lys.protobuf.ProtocolCommon.TopicFilter;

public class STopicFilter extends SPTData<TopicFilter>
{
	private static final STopicFilter DefaultInstance = new STopicFilter();

	public Integer subject = 0; // 科目
	public List<String> knowledgeCodes = new ArrayList<String>(); // 
	public Integer style = 0; // 
	public Integer diff = 0; // 

	public static STopicFilter create(Integer subject, Integer style, Integer diff)
	{
		STopicFilter obj = new STopicFilter();
		obj.subject = subject;
		obj.style = style;
		obj.diff = diff;
		return obj;
	}

	public STopicFilter clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(STopicFilter _other_)
	{
		this.subject = _other_.subject;
		this.knowledgeCodes = _other_.knowledgeCodes;
		this.style = _other_.style;
		this.diff = _other_.diff;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("subject"))
			subject = _json_.getInteger("subject");
		if (_json_.containsKey("knowledgeCodes"))
			knowledgeCodes = AppDataTool.loadStringList(AppDataTool.getJSONArray(_json_, "knowledgeCodes"));
		if (_json_.containsKey("style"))
			style = _json_.getInteger("style");
		if (_json_.containsKey("diff"))
			diff = _json_.getInteger("diff");
	}

	public static STopicFilter load(String str)
	{
		try
		{
			STopicFilter obj = new STopicFilter();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static STopicFilter load(JSONObject json)
	{
		try
		{
			STopicFilter obj = new STopicFilter();
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
			if (subject != null)
				_json_.put("subject", subject);
			if (knowledgeCodes != null)
				_json_.put("knowledgeCodes", AppDataTool.saveStringList(knowledgeCodes));
			if (style != null)
				_json_.put("style", style);
			if (diff != null)
				_json_.put("diff", diff);
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<STopicFilter> loadList(JSONArray ja)
	{
		try
		{
			List<STopicFilter> list = new ArrayList<STopicFilter>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				STopicFilter item = STopicFilter.load(jo);
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

	public static JSONArray saveList(List<STopicFilter> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			STopicFilter item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(TopicFilter _proto_)
	{
		if (_proto_.hasSubject())
			subject = _proto_.getSubject();
		for (int i = 0; i < _proto_.getKnowledgeCodesCount(); i++)
			knowledgeCodes.add(_proto_.getKnowledgeCodes(i));
		if (_proto_.hasStyle())
			style = _proto_.getStyle();
		if (_proto_.hasDiff())
			diff = _proto_.getDiff();
	}

	public static STopicFilter load(byte[] bytes)
	{
		try
		{
			STopicFilter obj = new STopicFilter();
			obj.parse(TopicFilter.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static STopicFilter load(TopicFilter proto)
	{
		try
		{
			STopicFilter obj = new STopicFilter();
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
	public TopicFilter saveToProto()
	{
		TopicFilter.Builder _builder_ = TopicFilter.newBuilder();
		if (subject != null && !subject.equals(TopicFilter.getDefaultInstance().getSubject()))
			_builder_.setSubject(subject);
		if (knowledgeCodes != null)
			for (String _value_ : knowledgeCodes)
				_builder_.addKnowledgeCodes(_value_);
		if (style != null && !style.equals(TopicFilter.getDefaultInstance().getStyle()))
			_builder_.setStyle(style);
		if (diff != null && !diff.equals(TopicFilter.getDefaultInstance().getDiff()))
			_builder_.setDiff(diff);
		return _builder_.build();
	}
}
