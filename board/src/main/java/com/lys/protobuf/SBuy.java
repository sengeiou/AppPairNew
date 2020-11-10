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
import com.lys.protobuf.ProtocolShop.Buy;

public class SBuy extends SPTData<Buy>
{
	private static final SBuy DefaultInstance = new SBuy();

	public String userId = null;
	public SMatter matter = null;
	public Float hourBuy = 0F;
	public Float hourGive = 0F;

	public static SBuy create(String userId, SMatter matter, Float hourBuy, Float hourGive)
	{
		SBuy obj = new SBuy();
		obj.userId = userId;
		obj.matter = matter;
		obj.hourBuy = hourBuy;
		obj.hourGive = hourGive;
		return obj;
	}

	public SBuy clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SBuy _other_)
	{
		this.userId = _other_.userId;
		this.matter = _other_.matter;
		this.hourBuy = _other_.hourBuy;
		this.hourGive = _other_.hourGive;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("userId"))
			userId = _json_.getString("userId");
		if (_json_.containsKey("matter"))
			matter = SMatter.load(_json_.getJSONObject("matter"));
		if (_json_.containsKey("hourBuy"))
			hourBuy = _json_.getFloat("hourBuy");
		if (_json_.containsKey("hourGive"))
			hourGive = _json_.getFloat("hourGive");
	}

	public static SBuy load(String str)
	{
		try
		{
			SBuy obj = new SBuy();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SBuy load(JSONObject json)
	{
		try
		{
			SBuy obj = new SBuy();
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
			if (userId != null)
				_json_.put("userId", userId);
			if (matter != null)
				_json_.put("matter", matter.saveToJson());
			if (hourBuy != null)
				_json_.put("hourBuy", hourBuy);
			if (hourGive != null)
				_json_.put("hourGive", hourGive);
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SBuy> loadList(JSONArray ja)
	{
		try
		{
			List<SBuy> list = new ArrayList<SBuy>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SBuy item = SBuy.load(jo);
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

	public static JSONArray saveList(List<SBuy> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SBuy item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Buy _proto_)
	{
		if (_proto_.hasUserId())
			userId = _proto_.getUserId();
		if (_proto_.hasMatter())
			matter = SMatter.load(_proto_.getMatter());
		if (_proto_.hasHourBuy())
			hourBuy = _proto_.getHourBuy();
		if (_proto_.hasHourGive())
			hourGive = _proto_.getHourGive();
	}

	public static SBuy load(byte[] bytes)
	{
		try
		{
			SBuy obj = new SBuy();
			obj.parse(Buy.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SBuy load(Buy proto)
	{
		try
		{
			SBuy obj = new SBuy();
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
	public Buy saveToProto()
	{
		Buy.Builder _builder_ = Buy.newBuilder();
		if (userId != null && !userId.equals(Buy.getDefaultInstance().getUserId()))
			_builder_.setUserId(userId);
		if (matter != null)
			_builder_.setMatter(matter.saveToProto());
		if (hourBuy != null && !hourBuy.equals(Buy.getDefaultInstance().getHourBuy()))
			_builder_.setHourBuy(hourBuy);
		if (hourGive != null && !hourGive.equals(Buy.getDefaultInstance().getHourGive()))
			_builder_.setHourGive(hourGive);
		return _builder_.build();
	}
}
