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
import com.lys.protobuf.ProtocolPair2.EnglishWord;

public class SEnglishWord extends SPTData<EnglishWord>
{
	private static final SEnglishWord DefaultInstance = new SEnglishWord();

	public String word = null;
	public String mark = null;
	public String type = null;
	public String mean = null;
	public String think = null;
	public String memory = null;
	public List<SEnglishWordExample> examples = new ArrayList<SEnglishWordExample>();

	public static SEnglishWord create(String word, String mark, String type, String mean, String think, String memory)
	{
		SEnglishWord obj = new SEnglishWord();
		obj.word = word;
		obj.mark = mark;
		obj.type = type;
		obj.mean = mean;
		obj.think = think;
		obj.memory = memory;
		return obj;
	}

	public SEnglishWord clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SEnglishWord _other_)
	{
		this.word = _other_.word;
		this.mark = _other_.mark;
		this.type = _other_.type;
		this.mean = _other_.mean;
		this.think = _other_.think;
		this.memory = _other_.memory;
		this.examples = _other_.examples;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("word"))
			word = _json_.getString("word");
		if (_json_.containsKey("mark"))
			mark = _json_.getString("mark");
		if (_json_.containsKey("type"))
			type = _json_.getString("type");
		if (_json_.containsKey("mean"))
			mean = _json_.getString("mean");
		if (_json_.containsKey("think"))
			think = _json_.getString("think");
		if (_json_.containsKey("memory"))
			memory = _json_.getString("memory");
		if (_json_.containsKey("examples"))
			examples = SEnglishWordExample.loadList(_json_.getJSONArray("examples"));
	}

	public static SEnglishWord load(String str)
	{
		try
		{
			SEnglishWord obj = new SEnglishWord();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SEnglishWord load(JSONObject json)
	{
		try
		{
			SEnglishWord obj = new SEnglishWord();
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
			if (word != null)
				_json_.put("word", word);
			if (mark != null)
				_json_.put("mark", mark);
			if (type != null)
				_json_.put("type", type);
			if (mean != null)
				_json_.put("mean", mean);
			if (think != null)
				_json_.put("think", think);
			if (memory != null)
				_json_.put("memory", memory);
			if (examples != null)
				_json_.put("examples", SEnglishWordExample.saveList(examples));
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SEnglishWord> loadList(JSONArray ja)
	{
		try
		{
			List<SEnglishWord> list = new ArrayList<SEnglishWord>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SEnglishWord item = SEnglishWord.load(jo);
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

	public static JSONArray saveList(List<SEnglishWord> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SEnglishWord item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(EnglishWord _proto_)
	{
		if (_proto_.hasWord())
			word = _proto_.getWord();
		if (_proto_.hasMark())
			mark = _proto_.getMark();
		if (_proto_.hasType())
			type = _proto_.getType();
		if (_proto_.hasMean())
			mean = _proto_.getMean();
		if (_proto_.hasThink())
			think = _proto_.getThink();
		if (_proto_.hasMemory())
			memory = _proto_.getMemory();
		for (int i = 0; i < _proto_.getExamplesCount(); i++)
			examples.add(SEnglishWordExample.load(_proto_.getExamples(i)));
	}

	public static SEnglishWord load(byte[] bytes)
	{
		try
		{
			SEnglishWord obj = new SEnglishWord();
			obj.parse(EnglishWord.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SEnglishWord load(EnglishWord proto)
	{
		try
		{
			SEnglishWord obj = new SEnglishWord();
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
	public EnglishWord saveToProto()
	{
		EnglishWord.Builder _builder_ = EnglishWord.newBuilder();
		if (word != null && !word.equals(EnglishWord.getDefaultInstance().getWord()))
			_builder_.setWord(word);
		if (mark != null && !mark.equals(EnglishWord.getDefaultInstance().getMark()))
			_builder_.setMark(mark);
		if (type != null && !type.equals(EnglishWord.getDefaultInstance().getType()))
			_builder_.setType(type);
		if (mean != null && !mean.equals(EnglishWord.getDefaultInstance().getMean()))
			_builder_.setMean(mean);
		if (think != null && !think.equals(EnglishWord.getDefaultInstance().getThink()))
			_builder_.setThink(think);
		if (memory != null && !memory.equals(EnglishWord.getDefaultInstance().getMemory()))
			_builder_.setMemory(memory);
		if (examples != null)
			for (SEnglishWordExample _value_ : examples)
				_builder_.addExamples(_value_.saveToProto());
		return _builder_.build();
	}
}
