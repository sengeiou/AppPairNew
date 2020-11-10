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
import com.lys.protobuf.ProtocolTeach.TeachPage;

public class STeachPage extends SPTData<TeachPage>
{
	private static final STeachPage DefaultInstance = new STeachPage();

	public String name = null;
	public Integer index = 0;
	public Long time = 0L;

	public static STeachPage create(String name, Integer index, Long time)
	{
		STeachPage obj = new STeachPage();
		obj.name = name;
		obj.index = index;
		obj.time = time;
		return obj;
	}

	public STeachPage clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(STeachPage _other_)
	{
		this.name = _other_.name;
		this.index = _other_.index;
		this.time = _other_.time;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("name"))
			name = _json_.getString("name");
		if (_json_.containsKey("index"))
			index = _json_.getInteger("index");
		if (_json_.containsKey("time"))
			time = _json_.getLong("time");
	}

	public static STeachPage load(String str)
	{
		try
		{
			STeachPage obj = new STeachPage();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static STeachPage load(JSONObject json)
	{
		try
		{
			STeachPage obj = new STeachPage();
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
			if (name != null)
				_json_.put("name", name);
			if (index != null)
				_json_.put("index", index);
			if (time != null)
				_json_.put("time", String.valueOf(time));
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<STeachPage> loadList(JSONArray ja)
	{
		try
		{
			List<STeachPage> list = new ArrayList<STeachPage>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				STeachPage item = STeachPage.load(jo);
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

	public static JSONArray saveList(List<STeachPage> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			STeachPage item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(TeachPage _proto_)
	{
		if (_proto_.hasName())
			name = _proto_.getName();
		if (_proto_.hasIndex())
			index = _proto_.getIndex();
		if (_proto_.hasTime())
			time = _proto_.getTime();
	}

	public static STeachPage load(byte[] bytes)
	{
		try
		{
			STeachPage obj = new STeachPage();
			obj.parse(TeachPage.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static STeachPage load(TeachPage proto)
	{
		try
		{
			STeachPage obj = new STeachPage();
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
	public TeachPage saveToProto()
	{
		TeachPage.Builder _builder_ = TeachPage.newBuilder();
		if (name != null && !name.equals(TeachPage.getDefaultInstance().getName()))
			_builder_.setName(name);
		if (index != null && !index.equals(TeachPage.getDefaultInstance().getIndex()))
			_builder_.setIndex(index);
		if (time != null && !time.equals(TeachPage.getDefaultInstance().getTime()))
			_builder_.setTime(time);
		return _builder_.build();
	}
}
