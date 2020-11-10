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
import com.lys.protobuf.ProtocolCommon.ChoiceItem;

public class SChoiceItem extends SPTData<ChoiceItem>
{
	private static final SChoiceItem DefaultInstance = new SChoiceItem();

	public String name = null; // 
	public Integer number = 0; // 计数
	public Integer value = 0; // 

	public static SChoiceItem create(String name, Integer number, Integer value)
	{
		SChoiceItem obj = new SChoiceItem();
		obj.name = name;
		obj.number = number;
		obj.value = value;
		return obj;
	}

	public SChoiceItem clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SChoiceItem _other_)
	{
		this.name = _other_.name;
		this.number = _other_.number;
		this.value = _other_.value;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("name"))
			name = _json_.getString("name");
		if (_json_.containsKey("number"))
			number = _json_.getInteger("number");
		if (_json_.containsKey("value"))
			value = _json_.getInteger("value");
	}

	public static SChoiceItem load(String str)
	{
		try
		{
			SChoiceItem obj = new SChoiceItem();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SChoiceItem load(JSONObject json)
	{
		try
		{
			SChoiceItem obj = new SChoiceItem();
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
			if (number != null)
				_json_.put("number", number);
			if (value != null)
				_json_.put("value", value);
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SChoiceItem> loadList(JSONArray ja)
	{
		try
		{
			List<SChoiceItem> list = new ArrayList<SChoiceItem>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SChoiceItem item = SChoiceItem.load(jo);
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

	public static JSONArray saveList(List<SChoiceItem> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SChoiceItem item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(ChoiceItem _proto_)
	{
		if (_proto_.hasName())
			name = _proto_.getName();
		if (_proto_.hasNumber())
			number = _proto_.getNumber();
		if (_proto_.hasValue())
			value = _proto_.getValue();
	}

	public static SChoiceItem load(byte[] bytes)
	{
		try
		{
			SChoiceItem obj = new SChoiceItem();
			obj.parse(ChoiceItem.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SChoiceItem load(ChoiceItem proto)
	{
		try
		{
			SChoiceItem obj = new SChoiceItem();
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
	public ChoiceItem saveToProto()
	{
		ChoiceItem.Builder _builder_ = ChoiceItem.newBuilder();
		if (name != null && !name.equals(ChoiceItem.getDefaultInstance().getName()))
			_builder_.setName(name);
		if (number != null && !number.equals(ChoiceItem.getDefaultInstance().getNumber()))
			_builder_.setNumber(number);
		if (value != null && !value.equals(ChoiceItem.getDefaultInstance().getValue()))
			_builder_.setValue(value);
		return _builder_.build();
	}
}
