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
import com.lys.protobuf.ProtocolPair2.Topic;

public class STopic extends SPTData<Topic>
{
	private static final STopic DefaultInstance = new STopic();

	public String id = null;
	public Integer phase = 0;
	public Integer subject = 0;
	public String material = null;
	public String style = null;
	public Integer diff = 0;
	public String area = null;
	public String year = null;
	public List<String> knowledges = new ArrayList<String>();
	public Integer zujuan = 0;
	public Integer zuoda = 0;
	public Float defen = 0F;
	public String nandu = null;
	public List<String> chapters = new ArrayList<String>();
	public String content = null;
	public String answer = null;
	public String parse = null;
	public String contentUrl = null;
	public String analyUrl = null;

	public static STopic create(String id, Integer phase, Integer subject, String material, String style, Integer diff, String area, String year, Integer zujuan, Integer zuoda, Float defen, String nandu, String content, String answer, String parse, String contentUrl, String analyUrl)
	{
		STopic obj = new STopic();
		obj.id = id;
		obj.phase = phase;
		obj.subject = subject;
		obj.material = material;
		obj.style = style;
		obj.diff = diff;
		obj.area = area;
		obj.year = year;
		obj.zujuan = zujuan;
		obj.zuoda = zuoda;
		obj.defen = defen;
		obj.nandu = nandu;
		obj.content = content;
		obj.answer = answer;
		obj.parse = parse;
		obj.contentUrl = contentUrl;
		obj.analyUrl = analyUrl;
		return obj;
	}

	public STopic clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(STopic _other_)
	{
		this.id = _other_.id;
		this.phase = _other_.phase;
		this.subject = _other_.subject;
		this.material = _other_.material;
		this.style = _other_.style;
		this.diff = _other_.diff;
		this.area = _other_.area;
		this.year = _other_.year;
		this.knowledges = _other_.knowledges;
		this.zujuan = _other_.zujuan;
		this.zuoda = _other_.zuoda;
		this.defen = _other_.defen;
		this.nandu = _other_.nandu;
		this.chapters = _other_.chapters;
		this.content = _other_.content;
		this.answer = _other_.answer;
		this.parse = _other_.parse;
		this.contentUrl = _other_.contentUrl;
		this.analyUrl = _other_.analyUrl;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("id"))
			id = _json_.getString("id");
		if (_json_.containsKey("phase"))
			phase = _json_.getInteger("phase");
		if (_json_.containsKey("subject"))
			subject = _json_.getInteger("subject");
		if (_json_.containsKey("material"))
			material = _json_.getString("material");
		if (_json_.containsKey("style"))
			style = _json_.getString("style");
		if (_json_.containsKey("diff"))
			diff = _json_.getInteger("diff");
		if (_json_.containsKey("area"))
			area = _json_.getString("area");
		if (_json_.containsKey("year"))
			year = _json_.getString("year");
		if (_json_.containsKey("knowledges"))
			knowledges = AppDataTool.loadStringList(AppDataTool.getJSONArray(_json_, "knowledges"));
		if (_json_.containsKey("zujuan"))
			zujuan = _json_.getInteger("zujuan");
		if (_json_.containsKey("zuoda"))
			zuoda = _json_.getInteger("zuoda");
		if (_json_.containsKey("defen"))
			defen = _json_.getFloat("defen");
		if (_json_.containsKey("nandu"))
			nandu = _json_.getString("nandu");
		if (_json_.containsKey("chapters"))
			chapters = AppDataTool.loadStringList(AppDataTool.getJSONArray(_json_, "chapters"));
		if (_json_.containsKey("content"))
			content = _json_.getString("content");
		if (_json_.containsKey("answer"))
			answer = _json_.getString("answer");
		if (_json_.containsKey("parse"))
			parse = _json_.getString("parse");
		if (_json_.containsKey("contentUrl"))
			contentUrl = _json_.getString("contentUrl");
		if (_json_.containsKey("analyUrl"))
			analyUrl = _json_.getString("analyUrl");
	}

	public static STopic load(String str)
	{
		try
		{
			STopic obj = new STopic();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static STopic load(JSONObject json)
	{
		try
		{
			STopic obj = new STopic();
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
			if (phase != null)
				_json_.put("phase", phase);
			if (subject != null)
				_json_.put("subject", subject);
			if (material != null)
				_json_.put("material", material);
			if (style != null)
				_json_.put("style", style);
			if (diff != null)
				_json_.put("diff", diff);
			if (area != null)
				_json_.put("area", area);
			if (year != null)
				_json_.put("year", year);
			if (knowledges != null)
				_json_.put("knowledges", AppDataTool.saveStringList(knowledges));
			if (zujuan != null)
				_json_.put("zujuan", zujuan);
			if (zuoda != null)
				_json_.put("zuoda", zuoda);
			if (defen != null)
				_json_.put("defen", defen);
			if (nandu != null)
				_json_.put("nandu", nandu);
			if (chapters != null)
				_json_.put("chapters", AppDataTool.saveStringList(chapters));
			if (content != null)
				_json_.put("content", content);
			if (answer != null)
				_json_.put("answer", answer);
			if (parse != null)
				_json_.put("parse", parse);
			if (contentUrl != null)
				_json_.put("contentUrl", contentUrl);
			if (analyUrl != null)
				_json_.put("analyUrl", analyUrl);
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<STopic> loadList(JSONArray ja)
	{
		try
		{
			List<STopic> list = new ArrayList<STopic>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				STopic item = STopic.load(jo);
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

	public static JSONArray saveList(List<STopic> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			STopic item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Topic _proto_)
	{
		if (_proto_.hasId())
			id = _proto_.getId();
		if (_proto_.hasPhase())
			phase = _proto_.getPhase();
		if (_proto_.hasSubject())
			subject = _proto_.getSubject();
		if (_proto_.hasMaterial())
			material = _proto_.getMaterial();
		if (_proto_.hasStyle())
			style = _proto_.getStyle();
		if (_proto_.hasDiff())
			diff = _proto_.getDiff();
		if (_proto_.hasArea())
			area = _proto_.getArea();
		if (_proto_.hasYear())
			year = _proto_.getYear();
		for (int i = 0; i < _proto_.getKnowledgesCount(); i++)
			knowledges.add(_proto_.getKnowledges(i));
		if (_proto_.hasZujuan())
			zujuan = _proto_.getZujuan();
		if (_proto_.hasZuoda())
			zuoda = _proto_.getZuoda();
		if (_proto_.hasDefen())
			defen = _proto_.getDefen();
		if (_proto_.hasNandu())
			nandu = _proto_.getNandu();
		for (int i = 0; i < _proto_.getChaptersCount(); i++)
			chapters.add(_proto_.getChapters(i));
		if (_proto_.hasContent())
			content = _proto_.getContent();
		if (_proto_.hasAnswer())
			answer = _proto_.getAnswer();
		if (_proto_.hasParse())
			parse = _proto_.getParse();
		if (_proto_.hasContentUrl())
			contentUrl = _proto_.getContentUrl();
		if (_proto_.hasAnalyUrl())
			analyUrl = _proto_.getAnalyUrl();
	}

	public static STopic load(byte[] bytes)
	{
		try
		{
			STopic obj = new STopic();
			obj.parse(Topic.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static STopic load(Topic proto)
	{
		try
		{
			STopic obj = new STopic();
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
	public Topic saveToProto()
	{
		Topic.Builder _builder_ = Topic.newBuilder();
		if (id != null && !id.equals(Topic.getDefaultInstance().getId()))
			_builder_.setId(id);
		if (phase != null && !phase.equals(Topic.getDefaultInstance().getPhase()))
			_builder_.setPhase(phase);
		if (subject != null && !subject.equals(Topic.getDefaultInstance().getSubject()))
			_builder_.setSubject(subject);
		if (material != null && !material.equals(Topic.getDefaultInstance().getMaterial()))
			_builder_.setMaterial(material);
		if (style != null && !style.equals(Topic.getDefaultInstance().getStyle()))
			_builder_.setStyle(style);
		if (diff != null && !diff.equals(Topic.getDefaultInstance().getDiff()))
			_builder_.setDiff(diff);
		if (area != null && !area.equals(Topic.getDefaultInstance().getArea()))
			_builder_.setArea(area);
		if (year != null && !year.equals(Topic.getDefaultInstance().getYear()))
			_builder_.setYear(year);
		if (knowledges != null)
			for (String _value_ : knowledges)
				_builder_.addKnowledges(_value_);
		if (zujuan != null && !zujuan.equals(Topic.getDefaultInstance().getZujuan()))
			_builder_.setZujuan(zujuan);
		if (zuoda != null && !zuoda.equals(Topic.getDefaultInstance().getZuoda()))
			_builder_.setZuoda(zuoda);
		if (defen != null && !defen.equals(Topic.getDefaultInstance().getDefen()))
			_builder_.setDefen(defen);
		if (nandu != null && !nandu.equals(Topic.getDefaultInstance().getNandu()))
			_builder_.setNandu(nandu);
		if (chapters != null)
			for (String _value_ : chapters)
				_builder_.addChapters(_value_);
		if (content != null && !content.equals(Topic.getDefaultInstance().getContent()))
			_builder_.setContent(content);
		if (answer != null && !answer.equals(Topic.getDefaultInstance().getAnswer()))
			_builder_.setAnswer(answer);
		if (parse != null && !parse.equals(Topic.getDefaultInstance().getParse()))
			_builder_.setParse(parse);
		if (contentUrl != null && !contentUrl.equals(Topic.getDefaultInstance().getContentUrl()))
			_builder_.setContentUrl(contentUrl);
		if (analyUrl != null && !analyUrl.equals(Topic.getDefaultInstance().getAnalyUrl()))
			_builder_.setAnalyUrl(analyUrl);
		return _builder_.build();
	}
}
