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
import com.lys.protobuf.ProtocolScore.Request_GetTeachList;

// ---------------------- xxxxxxxx --------------------------
public class SRequest_GetTeachList extends SPTData<Request_GetTeachList>
{
	private static final SRequest_GetTeachList DefaultInstance = new SRequest_GetTeachList();

	public String teacherId = null;
	public Integer year = 0;
	public Integer month = 0;
	public Integer day = 0;

	public static SRequest_GetTeachList create(String teacherId, Integer year, Integer month, Integer day)
	{
		SRequest_GetTeachList obj = new SRequest_GetTeachList();
		obj.teacherId = teacherId;
		obj.year = year;
		obj.month = month;
		obj.day = day;
		return obj;
	}

	public SRequest_GetTeachList clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SRequest_GetTeachList _other_)
	{
		this.teacherId = _other_.teacherId;
		this.year = _other_.year;
		this.month = _other_.month;
		this.day = _other_.day;
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
	}

	public static SRequest_GetTeachList load(String str)
	{
		try
		{
			SRequest_GetTeachList obj = new SRequest_GetTeachList();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_GetTeachList load(JSONObject json)
	{
		try
		{
			SRequest_GetTeachList obj = new SRequest_GetTeachList();
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
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SRequest_GetTeachList> loadList(JSONArray ja)
	{
		try
		{
			List<SRequest_GetTeachList> list = new ArrayList<SRequest_GetTeachList>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SRequest_GetTeachList item = SRequest_GetTeachList.load(jo);
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

	public static JSONArray saveList(List<SRequest_GetTeachList> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SRequest_GetTeachList item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Request_GetTeachList _proto_)
	{
		if (_proto_.hasTeacherId())
			teacherId = _proto_.getTeacherId();
		if (_proto_.hasYear())
			year = _proto_.getYear();
		if (_proto_.hasMonth())
			month = _proto_.getMonth();
		if (_proto_.hasDay())
			day = _proto_.getDay();
	}

	public static SRequest_GetTeachList load(byte[] bytes)
	{
		try
		{
			SRequest_GetTeachList obj = new SRequest_GetTeachList();
			obj.parse(Request_GetTeachList.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_GetTeachList load(Request_GetTeachList proto)
	{
		try
		{
			SRequest_GetTeachList obj = new SRequest_GetTeachList();
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
	public Request_GetTeachList saveToProto()
	{
		Request_GetTeachList.Builder _builder_ = Request_GetTeachList.newBuilder();
		if (teacherId != null && !teacherId.equals(Request_GetTeachList.getDefaultInstance().getTeacherId()))
			_builder_.setTeacherId(teacherId);
		if (year != null && !year.equals(Request_GetTeachList.getDefaultInstance().getYear()))
			_builder_.setYear(year);
		if (month != null && !month.equals(Request_GetTeachList.getDefaultInstance().getMonth()))
			_builder_.setMonth(month);
		if (day != null && !day.equals(Request_GetTeachList.getDefaultInstance().getDay()))
			_builder_.setDay(day);
		return _builder_.build();
	}
}
