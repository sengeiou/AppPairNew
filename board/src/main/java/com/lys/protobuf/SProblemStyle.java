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
import com.lys.protobuf.ProtocolCommon.ProblemStyle;

public class SProblemStyle extends SPTData<ProblemStyle>
{
	private static final SProblemStyle DefaultInstance = new SProblemStyle();

	public String name = null;
	public Boolean isSelect = false; // 是否是选择题

	public static SProblemStyle create(String name, Boolean isSelect)
	{
		SProblemStyle obj = new SProblemStyle();
		obj.name = name;
		obj.isSelect = isSelect;
		return obj;
	}

	public SProblemStyle clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SProblemStyle _other_)
	{
		this.name = _other_.name;
		this.isSelect = _other_.isSelect;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("name"))
			name = _json_.getString("name");
		if (_json_.containsKey("isSelect"))
			isSelect = _json_.getBoolean("isSelect");
	}

	public static SProblemStyle load(String str)
	{
		try
		{
			SProblemStyle obj = new SProblemStyle();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SProblemStyle load(JSONObject json)
	{
		try
		{
			SProblemStyle obj = new SProblemStyle();
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
			if (isSelect != null)
				_json_.put("isSelect", isSelect);
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SProblemStyle> loadList(JSONArray ja)
	{
		try
		{
			List<SProblemStyle> list = new ArrayList<SProblemStyle>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SProblemStyle item = SProblemStyle.load(jo);
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

	public static JSONArray saveList(List<SProblemStyle> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SProblemStyle item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(ProblemStyle _proto_)
	{
		if (_proto_.hasName())
			name = _proto_.getName();
		if (_proto_.hasIsSelect())
			isSelect = _proto_.getIsSelect();
	}

	public static SProblemStyle load(byte[] bytes)
	{
		try
		{
			SProblemStyle obj = new SProblemStyle();
			obj.parse(ProblemStyle.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SProblemStyle load(ProblemStyle proto)
	{
		try
		{
			SProblemStyle obj = new SProblemStyle();
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
	public ProblemStyle saveToProto()
	{
		ProblemStyle.Builder _builder_ = ProblemStyle.newBuilder();
		if (name != null && !name.equals(ProblemStyle.getDefaultInstance().getName()))
			_builder_.setName(name);
		if (isSelect != null && !isSelect.equals(ProblemStyle.getDefaultInstance().getIsSelect()))
			_builder_.setIsSelect(isSelect);
		return _builder_.build();
	}
}
