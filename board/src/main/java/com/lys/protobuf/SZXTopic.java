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
import com.lys.protobuf.ProtocolZhixue.ZXTopic;

public class SZXTopic extends SPTData<ZXTopic>
{
	private static final SZXTopic DefaultInstance = new SZXTopic();

	public String id = null;
	public String content = null;
	public String answer = null;
	public String parse = null;
	public List<String> knowledges = new ArrayList<String>();
	public String style = null;
	public Integer zujuan = 0;
	public Integer zuoda = 0;
	public String defen = null;
	public String nandu = null;
	public String phase = null;
	public String subject = null;
	public String material = null;
	public String chapterPath = null;
	public String diff = null;
	public String area = null;
	public String year = null;
	public String checkResult = null;

	public static SZXTopic create(String id, String content, String answer, String parse, String style, Integer zujuan, Integer zuoda, String defen, String nandu, String phase, String subject, String material, String chapterPath, String diff, String area, String year, String checkResult)
	{
		SZXTopic obj = new SZXTopic();
		obj.id = id;
		obj.content = content;
		obj.answer = answer;
		obj.parse = parse;
		obj.style = style;
		obj.zujuan = zujuan;
		obj.zuoda = zuoda;
		obj.defen = defen;
		obj.nandu = nandu;
		obj.phase = phase;
		obj.subject = subject;
		obj.material = material;
		obj.chapterPath = chapterPath;
		obj.diff = diff;
		obj.area = area;
		obj.year = year;
		obj.checkResult = checkResult;
		return obj;
	}

	public SZXTopic clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SZXTopic _other_)
	{
		this.id = _other_.id;
		this.content = _other_.content;
		this.answer = _other_.answer;
		this.parse = _other_.parse;
		this.knowledges = _other_.knowledges;
		this.style = _other_.style;
		this.zujuan = _other_.zujuan;
		this.zuoda = _other_.zuoda;
		this.defen = _other_.defen;
		this.nandu = _other_.nandu;
		this.phase = _other_.phase;
		this.subject = _other_.subject;
		this.material = _other_.material;
		this.chapterPath = _other_.chapterPath;
		this.diff = _other_.diff;
		this.area = _other_.area;
		this.year = _other_.year;
		this.checkResult = _other_.checkResult;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("id"))
			id = _json_.getString("id");
		if (_json_.containsKey("content"))
			content = _json_.getString("content");
		if (_json_.containsKey("answer"))
			answer = _json_.getString("answer");
		if (_json_.containsKey("parse"))
			parse = _json_.getString("parse");
		if (_json_.containsKey("knowledges"))
			knowledges = AppDataTool.loadStringList(AppDataTool.getJSONArray(_json_, "knowledges"));
		if (_json_.containsKey("style"))
			style = _json_.getString("style");
		if (_json_.containsKey("zujuan"))
			zujuan = _json_.getInteger("zujuan");
		if (_json_.containsKey("zuoda"))
			zuoda = _json_.getInteger("zuoda");
		if (_json_.containsKey("defen"))
			defen = _json_.getString("defen");
		if (_json_.containsKey("nandu"))
			nandu = _json_.getString("nandu");
		if (_json_.containsKey("phase"))
			phase = _json_.getString("phase");
		if (_json_.containsKey("subject"))
			subject = _json_.getString("subject");
		if (_json_.containsKey("material"))
			material = _json_.getString("material");
		if (_json_.containsKey("chapterPath"))
			chapterPath = _json_.getString("chapterPath");
		if (_json_.containsKey("diff"))
			diff = _json_.getString("diff");
		if (_json_.containsKey("area"))
			area = _json_.getString("area");
		if (_json_.containsKey("year"))
			year = _json_.getString("year");
		if (_json_.containsKey("checkResult"))
			checkResult = _json_.getString("checkResult");
	}

	public static SZXTopic load(String str)
	{
		try
		{
			SZXTopic obj = new SZXTopic();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SZXTopic load(JSONObject json)
	{
		try
		{
			SZXTopic obj = new SZXTopic();
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
			if (content != null)
				_json_.put("content", content);
			if (answer != null)
				_json_.put("answer", answer);
			if (parse != null)
				_json_.put("parse", parse);
			if (knowledges != null)
				_json_.put("knowledges", AppDataTool.saveStringList(knowledges));
			if (style != null)
				_json_.put("style", style);
			if (zujuan != null)
				_json_.put("zujuan", zujuan);
			if (zuoda != null)
				_json_.put("zuoda", zuoda);
			if (defen != null)
				_json_.put("defen", defen);
			if (nandu != null)
				_json_.put("nandu", nandu);
			if (phase != null)
				_json_.put("phase", phase);
			if (subject != null)
				_json_.put("subject", subject);
			if (material != null)
				_json_.put("material", material);
			if (chapterPath != null)
				_json_.put("chapterPath", chapterPath);
			if (diff != null)
				_json_.put("diff", diff);
			if (area != null)
				_json_.put("area", area);
			if (year != null)
				_json_.put("year", year);
			if (checkResult != null)
				_json_.put("checkResult", checkResult);
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SZXTopic> loadList(JSONArray ja)
	{
		try
		{
			List<SZXTopic> list = new ArrayList<SZXTopic>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SZXTopic item = SZXTopic.load(jo);
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

	public static JSONArray saveList(List<SZXTopic> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SZXTopic item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(ZXTopic _proto_)
	{
		if (_proto_.hasId())
			id = _proto_.getId();
		if (_proto_.hasContent())
			content = _proto_.getContent();
		if (_proto_.hasAnswer())
			answer = _proto_.getAnswer();
		if (_proto_.hasParse())
			parse = _proto_.getParse();
		for (int i = 0; i < _proto_.getKnowledgesCount(); i++)
			knowledges.add(_proto_.getKnowledges(i));
		if (_proto_.hasStyle())
			style = _proto_.getStyle();
		if (_proto_.hasZujuan())
			zujuan = _proto_.getZujuan();
		if (_proto_.hasZuoda())
			zuoda = _proto_.getZuoda();
		if (_proto_.hasDefen())
			defen = _proto_.getDefen();
		if (_proto_.hasNandu())
			nandu = _proto_.getNandu();
		if (_proto_.hasPhase())
			phase = _proto_.getPhase();
		if (_proto_.hasSubject())
			subject = _proto_.getSubject();
		if (_proto_.hasMaterial())
			material = _proto_.getMaterial();
		if (_proto_.hasChapterPath())
			chapterPath = _proto_.getChapterPath();
		if (_proto_.hasDiff())
			diff = _proto_.getDiff();
		if (_proto_.hasArea())
			area = _proto_.getArea();
		if (_proto_.hasYear())
			year = _proto_.getYear();
		if (_proto_.hasCheckResult())
			checkResult = _proto_.getCheckResult();
	}

	public static SZXTopic load(byte[] bytes)
	{
		try
		{
			SZXTopic obj = new SZXTopic();
			obj.parse(ZXTopic.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SZXTopic load(ZXTopic proto)
	{
		try
		{
			SZXTopic obj = new SZXTopic();
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
	public ZXTopic saveToProto()
	{
		ZXTopic.Builder _builder_ = ZXTopic.newBuilder();
		if (id != null && !id.equals(ZXTopic.getDefaultInstance().getId()))
			_builder_.setId(id);
		if (content != null && !content.equals(ZXTopic.getDefaultInstance().getContent()))
			_builder_.setContent(content);
		if (answer != null && !answer.equals(ZXTopic.getDefaultInstance().getAnswer()))
			_builder_.setAnswer(answer);
		if (parse != null && !parse.equals(ZXTopic.getDefaultInstance().getParse()))
			_builder_.setParse(parse);
		if (knowledges != null)
			for (String _value_ : knowledges)
				_builder_.addKnowledges(_value_);
		if (style != null && !style.equals(ZXTopic.getDefaultInstance().getStyle()))
			_builder_.setStyle(style);
		if (zujuan != null && !zujuan.equals(ZXTopic.getDefaultInstance().getZujuan()))
			_builder_.setZujuan(zujuan);
		if (zuoda != null && !zuoda.equals(ZXTopic.getDefaultInstance().getZuoda()))
			_builder_.setZuoda(zuoda);
		if (defen != null && !defen.equals(ZXTopic.getDefaultInstance().getDefen()))
			_builder_.setDefen(defen);
		if (nandu != null && !nandu.equals(ZXTopic.getDefaultInstance().getNandu()))
			_builder_.setNandu(nandu);
		if (phase != null && !phase.equals(ZXTopic.getDefaultInstance().getPhase()))
			_builder_.setPhase(phase);
		if (subject != null && !subject.equals(ZXTopic.getDefaultInstance().getSubject()))
			_builder_.setSubject(subject);
		if (material != null && !material.equals(ZXTopic.getDefaultInstance().getMaterial()))
			_builder_.setMaterial(material);
		if (chapterPath != null && !chapterPath.equals(ZXTopic.getDefaultInstance().getChapterPath()))
			_builder_.setChapterPath(chapterPath);
		if (diff != null && !diff.equals(ZXTopic.getDefaultInstance().getDiff()))
			_builder_.setDiff(diff);
		if (area != null && !area.equals(ZXTopic.getDefaultInstance().getArea()))
			_builder_.setArea(area);
		if (year != null && !year.equals(ZXTopic.getDefaultInstance().getYear()))
			_builder_.setYear(year);
		if (checkResult != null && !checkResult.equals(ZXTopic.getDefaultInstance().getCheckResult()))
			_builder_.setCheckResult(checkResult);
		return _builder_.build();
	}
}
