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
import com.lys.protobuf.ProtocolShop.MatterHour;

public class SMatterHour extends SPTData<MatterHour>
{
	private static final SMatterHour DefaultInstance = new SMatterHour();

	public Float hourBuy = 0F;
	public Float hourGive = 0F;

	public static SMatterHour create(Float hourBuy, Float hourGive)
	{
		SMatterHour obj = new SMatterHour();
		obj.hourBuy = hourBuy;
		obj.hourGive = hourGive;
		return obj;
	}

	public SMatterHour clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SMatterHour _other_)
	{
		this.hourBuy = _other_.hourBuy;
		this.hourGive = _other_.hourGive;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("hourBuy"))
			hourBuy = _json_.getFloat("hourBuy");
		if (_json_.containsKey("hourGive"))
			hourGive = _json_.getFloat("hourGive");
	}

	public static SMatterHour load(String str)
	{
		try
		{
			SMatterHour obj = new SMatterHour();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SMatterHour load(JSONObject json)
	{
		try
		{
			SMatterHour obj = new SMatterHour();
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

	public static List<SMatterHour> loadList(JSONArray ja)
	{
		try
		{
			List<SMatterHour> list = new ArrayList<SMatterHour>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SMatterHour item = SMatterHour.load(jo);
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

	public static JSONArray saveList(List<SMatterHour> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SMatterHour item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(MatterHour _proto_)
	{
		if (_proto_.hasHourBuy())
			hourBuy = _proto_.getHourBuy();
		if (_proto_.hasHourGive())
			hourGive = _proto_.getHourGive();
	}

	public static SMatterHour load(byte[] bytes)
	{
		try
		{
			SMatterHour obj = new SMatterHour();
			obj.parse(MatterHour.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SMatterHour load(MatterHour proto)
	{
		try
		{
			SMatterHour obj = new SMatterHour();
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
	public MatterHour saveToProto()
	{
		MatterHour.Builder _builder_ = MatterHour.newBuilder();
		if (hourBuy != null && !hourBuy.equals(MatterHour.getDefaultInstance().getHourBuy()))
			_builder_.setHourBuy(hourBuy);
		if (hourGive != null && !hourGive.equals(MatterHour.getDefaultInstance().getHourGive()))
			_builder_.setHourGive(hourGive);
		return _builder_.build();
	}
}
