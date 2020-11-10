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
import com.lys.protobuf.ProtocolPair2.Request_SearchTopics;

// ---------------------- 搜索题库 --------------------------
public class SRequest_SearchTopics extends SPTData<Request_SearchTopics>
{
	private static final SRequest_SearchTopics DefaultInstance = new SRequest_SearchTopics();

	public String content = null; // 搜索内容
	public Integer phase = 0;
	public Integer subject = 0;
	public Integer start = 0;
	public Integer rows = 0;
	public List<SKnowledge> knowledges = new ArrayList<SKnowledge>(); // 知识点列表
	public List<SChapter> chapters = new ArrayList<SChapter>(); // 章节列表
	public List<String> styles = new ArrayList<String>(); // 题型
	public List<Integer> diffs = new ArrayList<Integer>(); // 难度
	public Boolean rand = false; // 是否随机
	public String excludeId = null; // 排除ID

	public static SRequest_SearchTopics create(String content, Integer phase, Integer subject, Integer start, Integer rows, Boolean rand, String excludeId)
	{
		SRequest_SearchTopics obj = new SRequest_SearchTopics();
		obj.content = content;
		obj.phase = phase;
		obj.subject = subject;
		obj.start = start;
		obj.rows = rows;
		obj.rand = rand;
		obj.excludeId = excludeId;
		return obj;
	}

	public SRequest_SearchTopics clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SRequest_SearchTopics _other_)
	{
		this.content = _other_.content;
		this.phase = _other_.phase;
		this.subject = _other_.subject;
		this.start = _other_.start;
		this.rows = _other_.rows;
		this.knowledges = _other_.knowledges;
		this.chapters = _other_.chapters;
		this.styles = _other_.styles;
		this.diffs = _other_.diffs;
		this.rand = _other_.rand;
		this.excludeId = _other_.excludeId;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("content"))
			content = _json_.getString("content");
		if (_json_.containsKey("phase"))
			phase = _json_.getInteger("phase");
		if (_json_.containsKey("subject"))
			subject = _json_.getInteger("subject");
		if (_json_.containsKey("start"))
			start = _json_.getInteger("start");
		if (_json_.containsKey("rows"))
			rows = _json_.getInteger("rows");
		if (_json_.containsKey("knowledges"))
			knowledges = SKnowledge.loadList(_json_.getJSONArray("knowledges"));
		if (_json_.containsKey("chapters"))
			chapters = SChapter.loadList(_json_.getJSONArray("chapters"));
		if (_json_.containsKey("styles"))
			styles = AppDataTool.loadStringList(AppDataTool.getJSONArray(_json_, "styles"));
		if (_json_.containsKey("diffs"))
			diffs = AppDataTool.loadIntegerList(AppDataTool.getJSONArray(_json_, "diffs"));
		if (_json_.containsKey("rand"))
			rand = _json_.getBoolean("rand");
		if (_json_.containsKey("excludeId"))
			excludeId = _json_.getString("excludeId");
	}

	public static SRequest_SearchTopics load(String str)
	{
		try
		{
			SRequest_SearchTopics obj = new SRequest_SearchTopics();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_SearchTopics load(JSONObject json)
	{
		try
		{
			SRequest_SearchTopics obj = new SRequest_SearchTopics();
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
			if (content != null)
				_json_.put("content", content);
			if (phase != null)
				_json_.put("phase", phase);
			if (subject != null)
				_json_.put("subject", subject);
			if (start != null)
				_json_.put("start", start);
			if (rows != null)
				_json_.put("rows", rows);
			if (knowledges != null)
				_json_.put("knowledges", SKnowledge.saveList(knowledges));
			if (chapters != null)
				_json_.put("chapters", SChapter.saveList(chapters));
			if (styles != null)
				_json_.put("styles", AppDataTool.saveStringList(styles));
			if (diffs != null)
				_json_.put("diffs", AppDataTool.saveIntegerList(diffs));
			if (rand != null)
				_json_.put("rand", rand);
			if (excludeId != null)
				_json_.put("excludeId", excludeId);
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SRequest_SearchTopics> loadList(JSONArray ja)
	{
		try
		{
			List<SRequest_SearchTopics> list = new ArrayList<SRequest_SearchTopics>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SRequest_SearchTopics item = SRequest_SearchTopics.load(jo);
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

	public static JSONArray saveList(List<SRequest_SearchTopics> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SRequest_SearchTopics item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Request_SearchTopics _proto_)
	{
		if (_proto_.hasContent())
			content = _proto_.getContent();
		if (_proto_.hasPhase())
			phase = _proto_.getPhase();
		if (_proto_.hasSubject())
			subject = _proto_.getSubject();
		if (_proto_.hasStart())
			start = _proto_.getStart();
		if (_proto_.hasRows())
			rows = _proto_.getRows();
		for (int i = 0; i < _proto_.getKnowledgesCount(); i++)
			knowledges.add(SKnowledge.load(_proto_.getKnowledges(i)));
		for (int i = 0; i < _proto_.getChaptersCount(); i++)
			chapters.add(SChapter.load(_proto_.getChapters(i)));
		for (int i = 0; i < _proto_.getStylesCount(); i++)
			styles.add(_proto_.getStyles(i));
		for (int i = 0; i < _proto_.getDiffsCount(); i++)
			diffs.add(_proto_.getDiffs(i));
		if (_proto_.hasRand())
			rand = _proto_.getRand();
		if (_proto_.hasExcludeId())
			excludeId = _proto_.getExcludeId();
	}

	public static SRequest_SearchTopics load(byte[] bytes)
	{
		try
		{
			SRequest_SearchTopics obj = new SRequest_SearchTopics();
			obj.parse(Request_SearchTopics.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_SearchTopics load(Request_SearchTopics proto)
	{
		try
		{
			SRequest_SearchTopics obj = new SRequest_SearchTopics();
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
	public Request_SearchTopics saveToProto()
	{
		Request_SearchTopics.Builder _builder_ = Request_SearchTopics.newBuilder();
		if (content != null && !content.equals(Request_SearchTopics.getDefaultInstance().getContent()))
			_builder_.setContent(content);
		if (phase != null && !phase.equals(Request_SearchTopics.getDefaultInstance().getPhase()))
			_builder_.setPhase(phase);
		if (subject != null && !subject.equals(Request_SearchTopics.getDefaultInstance().getSubject()))
			_builder_.setSubject(subject);
		if (start != null && !start.equals(Request_SearchTopics.getDefaultInstance().getStart()))
			_builder_.setStart(start);
		if (rows != null && !rows.equals(Request_SearchTopics.getDefaultInstance().getRows()))
			_builder_.setRows(rows);
		if (knowledges != null)
			for (SKnowledge _value_ : knowledges)
				_builder_.addKnowledges(_value_.saveToProto());
		if (chapters != null)
			for (SChapter _value_ : chapters)
				_builder_.addChapters(_value_.saveToProto());
		if (styles != null)
			for (String _value_ : styles)
				_builder_.addStyles(_value_);
		if (diffs != null)
			for (Integer _value_ : diffs)
				_builder_.addDiffs(_value_);
		if (rand != null && !rand.equals(Request_SearchTopics.getDefaultInstance().getRand()))
			_builder_.setRand(rand);
		if (excludeId != null && !excludeId.equals(Request_SearchTopics.getDefaultInstance().getExcludeId()))
			_builder_.setExcludeId(excludeId);
		return _builder_.build();
	}
}
