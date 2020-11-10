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
import com.lys.protobuf.ProtocolScore.Teach;

public class STeach extends SPTData<Teach>
{
	private static final STeach DefaultInstance = new STeach();

	public String teacherId = null;
	public Integer year = 0;
	public Integer month = 0;
	public Integer day = 0;
	public Integer block = 0;
	public /*STeachFlag*/ Integer flag = Teach.getDefaultInstance().getFlag().getNumber();
	public String studentId = null;

	public static STeach create(String teacherId, Integer year, Integer month, Integer day, Integer block, Integer flag, String studentId)
	{
		STeach obj = new STeach();
		obj.teacherId = teacherId;
		obj.year = year;
		obj.month = month;
		obj.day = day;
		obj.block = block;
		obj.flag = flag;
		obj.studentId = studentId;
		return obj;
	}

	public STeach clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(STeach _other_)
	{
		this.teacherId = _other_.teacherId;
		this.year = _other_.year;
		this.month = _other_.month;
		this.day = _other_.day;
		this.block = _other_.block;
		this.flag = _other_.flag;
		this.studentId = _other_.studentId;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("teacherId"))
			teacherId = _json_.getString("teacherId");
		if (_json_.containsKey("year"))
			year = _json_.getInteger("year");
		if (_json_.containsKey("month"))
			month = _json_.getInteger("month");
		if (_json_.containsKey("day"))
			day = _json_.getInteger("day");
		if (_json_.containsKey("block"))
			block = _json_.getInteger("block");
		if (_json_.containsKey("flag"))
			flag = _json_.getInteger("flag");
		if (_json_.containsKey("studentId"))
			studentId = _json_.getString("studentId");
	}

	public static STeach load(String str)
	{
		try
		{
			STeach obj = new STeach();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static STeach load(JSONObject json)
	{
		try
		{
			STeach obj = new STeach();
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
			if (teacherId != null)
				_json_.put("teacherId", teacherId);
			if (year != null)
				_json_.put("year", year);
			if (month != null)
				_json_.put("month", month);
			if (day != null)
				_json_.put("day", day);
			if (block != null)
				_json_.put("block", block);
			if (flag != null)
				_json_.put("flag", flag);
			if (studentId != null)
				_json_.put("studentId", studentId);
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<STeach> loadList(JSONArray ja)
	{
		try
		{
			List<STeach> list = new ArrayList<STeach>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				STeach item = STeach.load(jo);
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

	public static JSONArray saveList(List<STeach> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			STeach item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Teach _proto_)
	{
		if (_proto_.hasTeacherId())
			teacherId = _proto_.getTeacherId();
		if (_proto_.hasYear())
			year = _proto_.getYear();
		if (_proto_.hasMonth())
			month = _proto_.getMonth();
		if (_proto_.hasDay())
			day = _proto_.getDay();
		if (_proto_.hasBlock())
			block = _proto_.getBlock();
		if (_proto_.hasFlag())
			flag = _proto_.getFlag().getNumber();
		if (_proto_.hasStudentId())
			studentId = _proto_.getStudentId();
	}

	public static STeach load(byte[] bytes)
	{
		try
		{
			STeach obj = new STeach();
			obj.parse(Teach.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static STeach load(Teach proto)
	{
		try
		{
			STeach obj = new STeach();
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
	public Teach saveToProto()
	{
		Teach.Builder _builder_ = Teach.newBuilder();
		if (teacherId != null && !teacherId.equals(Teach.getDefaultInstance().getTeacherId()))
			_builder_.setTeacherId(teacherId);
		if (year != null && !year.equals(Teach.getDefaultInstance().getYear()))
			_builder_.setYear(year);
		if (month != null && !month.equals(Teach.getDefaultInstance().getMonth()))
			_builder_.setMonth(month);
		if (day != null && !day.equals(Teach.getDefaultInstance().getDay()))
			_builder_.setDay(day);
		if (block != null && !block.equals(Teach.getDefaultInstance().getBlock()))
			_builder_.setBlock(block);
		if (flag != null && Teach.getDefaultInstance().getFlag().getNumber() != flag)
			_builder_.setFlag(ProtocolScore.TeachFlag.valueOf(flag));
		if (studentId != null && !studentId.equals(Teach.getDefaultInstance().getStudentId()))
			_builder_.setStudentId(studentId);
		return _builder_.build();
	}
}
