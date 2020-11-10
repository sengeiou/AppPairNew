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
import com.lys.protobuf.ProtocolPair2.DynamicConfig;

public class SDynamicConfig extends SPTData<DynamicConfig>
{
	private static final SDynamicConfig DefaultInstance = new SDynamicConfig();

	public Boolean showSnow = false;
	public Integer snowCount = 200;

	public static SDynamicConfig create(Boolean showSnow, Integer snowCount)
	{
		SDynamicConfig obj = new SDynamicConfig();
		obj.showSnow = showSnow;
		obj.snowCount = snowCount;
		return obj;
	}

	public SDynamicConfig clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SDynamicConfig _other_)
	{
		this.showSnow = _other_.showSnow;
		this.snowCount = _other_.snowCount;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("showSnow"))
			showSnow = _json_.getBoolean("showSnow");
		if (_json_.containsKey("snowCount"))
			snowCount = _json_.getInteger("snowCount");
	}

	public static SDynamicConfig load(String str)
	{
		try
		{
			SDynamicConfig obj = new SDynamicConfig();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SDynamicConfig load(JSONObject json)
	{
		try
		{
			SDynamicConfig obj = new SDynamicConfig();
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
			if (showSnow != null)
				_json_.put("showSnow", showSnow);
			if (snowCount != null)
				_json_.put("snowCount", snowCount);
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SDynamicConfig> loadList(JSONArray ja)
	{
		try
		{
			List<SDynamicConfig> list = new ArrayList<SDynamicConfig>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SDynamicConfig item = SDynamicConfig.load(jo);
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

	public static JSONArray saveList(List<SDynamicConfig> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SDynamicConfig item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(DynamicConfig _proto_)
	{
		if (_proto_.hasShowSnow())
			showSnow = _proto_.getShowSnow();
		if (_proto_.hasSnowCount())
			snowCount = _proto_.getSnowCount();
	}

	public static SDynamicConfig load(byte[] bytes)
	{
		try
		{
			SDynamicConfig obj = new SDynamicConfig();
			obj.parse(DynamicConfig.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SDynamicConfig load(DynamicConfig proto)
	{
		try
		{
			SDynamicConfig obj = new SDynamicConfig();
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
	public DynamicConfig saveToProto()
	{
		DynamicConfig.Builder _builder_ = DynamicConfig.newBuilder();
		if (showSnow != null && !showSnow.equals(DynamicConfig.getDefaultInstance().getShowSnow()))
			_builder_.setShowSnow(showSnow);
		if (snowCount != null && !snowCount.equals(DynamicConfig.getDefaultInstance().getSnowCount()))
			_builder_.setSnowCount(snowCount);
		return _builder_.build();
	}
}
