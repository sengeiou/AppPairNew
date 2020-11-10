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
import com.lys.protobuf.ProtocolScore.TeachLine;

public class STeachLine extends SPTData<TeachLine>
{
	private static final STeachLine DefaultInstance = new STeachLine();

	public SUser teacher = null;
	public Integer year = 0;
	public Integer month = 0;
	public Integer day = 0;
	public List<STeachBlock> blocks = new ArrayList<STeachBlock>();

	public static STeachLine create(SUser teacher, Integer year, Integer month, Integer day)
	{
		STeachLine obj = new STeachLine();
		obj.teacher = teacher;
		obj.year = year;
		obj.month = month;
		obj.day = day;
		return obj;
	}

	public STeachLine clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(STeachLine _other_)
	{
		this.teacher = _other_.teacher;
		this.year = _other_.year;
		this.month = _other_.month;
		this.day = _other_.day;
		this.blocks = _other_.blocks;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("teacher"))
			teacher = SUser.load(_json_.getJSONObject("teacher"));
		if (_json_.containsKey("year"))
			year = _json_.getInteger("year");
		if (_json_.containsKey("month"))
			month = _json_.getInteger("month");
		if (_json_.containsKey("day"))
			day = _json_.getInteger("day");
		if (_json_.containsKey("blocks"))
			blocks = STeachBlock.loadList(_json_.getJSONArray("blocks"));
	}

	public static STeachLine load(String str)
	{
		try
		{
			STeachLine obj = new STeachLine();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static STeachLine load(JSONObject json)
	{
		try
		{
			STeachLine obj = new STeachLine();
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
			if (teacher != null)
				_json_.put("teacher", teacher.saveToJson());
			if (year != null)
				_json_.put("year", year);
			if (month != null)
				_json_.put("month", month);
			if (day != null)
				_json_.put("day", day);
			if (blocks != null)
				_json_.put("blocks", STeachBlock.saveList(blocks));
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<STeachLine> loadList(JSONArray ja)
	{
		try
		{
			List<STeachLine> list = new ArrayList<STeachLine>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				STeachLine item = STeachLine.load(jo);
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

	public static JSONArray saveList(List<STeachLine> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			STeachLine item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(TeachLine _proto_)
	{
		if (_proto_.hasTeacher())
			teacher = SUser.load(_proto_.getTeacher());
		if (_proto_.hasYear())
			year = _proto_.getYear();
		if (_proto_.hasMonth())
			month = _proto_.getMonth();
		if (_proto_.hasDay())
			day = _proto_.getDay();
		for (int i = 0; i < _proto_.getBlocksCount(); i++)
			blocks.add(STeachBlock.load(_proto_.getBlocks(i)));
	}

	public static STeachLine load(byte[] bytes)
	{
		try
		{
			STeachLine obj = new STeachLine();
			obj.parse(TeachLine.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static STeachLine load(TeachLine proto)
	{
		try
		{
			STeachLine obj = new STeachLine();
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
	public TeachLine saveToProto()
	{
		TeachLine.Builder _builder_ = TeachLine.newBuilder();
		if (teacher != null)
			_builder_.setTeacher(teacher.saveToProto());
		if (year != null && !year.equals(TeachLine.getDefaultInstance().getYear()))
			_builder_.setYear(year);
		if (month != null && !month.equals(TeachLine.getDefaultInstance().getMonth()))
			_builder_.setMonth(month);
		if (day != null && !day.equals(TeachLine.getDefaultInstance().getDay()))
			_builder_.setDay(day);
		if (blocks != null)
			for (STeachBlock _value_ : blocks)
				_builder_.addBlocks(_value_.saveToProto());
		return _builder_.build();
	}
}
