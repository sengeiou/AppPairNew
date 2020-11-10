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
import com.lys.protobuf.ProtocolPair2.EnglishWordExample;

public class SEnglishWordExample extends SPTData<EnglishWordExample>
{
	private static final SEnglishWordExample DefaultInstance = new SEnglishWordExample();

	public String english = null;
	public String explain = null;

	public static SEnglishWordExample create(String english, String explain)
	{
		SEnglishWordExample obj = new SEnglishWordExample();
		obj.english = english;
		obj.explain = explain;
		return obj;
	}

	public SEnglishWordExample clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SEnglishWordExample _other_)
	{
		this.english = _other_.english;
		this.explain = _other_.explain;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("english"))
			english = _json_.getString("english");
		if (_json_.containsKey("explain"))
			explain = _json_.getString("explain");
	}

	public static SEnglishWordExample load(String str)
	{
		try
		{
			SEnglishWordExample obj = new SEnglishWordExample();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SEnglishWordExample load(JSONObject json)
	{
		try
		{
			SEnglishWordExample obj = new SEnglishWordExample();
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
			if (english != null)
				_json_.put("english", english);
			if (explain != null)
				_json_.put("explain", explain);
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SEnglishWordExample> loadList(JSONArray ja)
	{
		try
		{
			List<SEnglishWordExample> list = new ArrayList<SEnglishWordExample>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SEnglishWordExample item = SEnglishWordExample.load(jo);
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

	public static JSONArray saveList(List<SEnglishWordExample> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SEnglishWordExample item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(EnglishWordExample _proto_)
	{
		if (_proto_.hasEnglish())
			english = _proto_.getEnglish();
		if (_proto_.hasExplain())
			explain = _proto_.getExplain();
	}

	public static SEnglishWordExample load(byte[] bytes)
	{
		try
		{
			SEnglishWordExample obj = new SEnglishWordExample();
			obj.parse(EnglishWordExample.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SEnglishWordExample load(EnglishWordExample proto)
	{
		try
		{
			SEnglishWordExample obj = new SEnglishWordExample();
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
	public EnglishWordExample saveToProto()
	{
		EnglishWordExample.Builder _builder_ = EnglishWordExample.newBuilder();
		if (english != null && !english.equals(EnglishWordExample.getDefaultInstance().getEnglish()))
			_builder_.setEnglish(english);
		if (explain != null && !explain.equals(EnglishWordExample.getDefaultInstance().getExplain()))
			_builder_.setExplain(explain);
		return _builder_.build();
	}
}
