package com.lys.base.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AppDataTool
{
	public static JSONArray getJSONArray(JSONObject obj, String name)
	{
		return obj.getJSONArray(name);
	}

	public static List<String> loadStringList(JSONArray ja)
	{
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < ja.size(); i++)
		{
			list.add(ja.getString(i));
		}
		return list;
	}

	public static List<Boolean> loadBooleanList(JSONArray ja)
	{
		List<Boolean> list = new ArrayList<Boolean>();
		for (int i = 0; i < ja.size(); i++)
		{
			list.add(ja.getBoolean(i));
		}
		return list;
	}

	public static List<Integer> loadIntegerList(JSONArray ja)
	{
		List<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < ja.size(); i++)
		{
			list.add(ja.getInteger(i));
		}
		return list;
	}

	public static List<Long> loadLongList(JSONArray ja)
	{
		List<Long> list = new ArrayList<Long>();
		for (int i = 0; i < ja.size(); i++)
		{
			list.add(ja.getLong(i));
		}
		return list;
	}

	public static List<Float> loadFloatList(JSONArray ja)
	{
		List<Float> list = new ArrayList<Float>();
		for (int i = 0; i < ja.size(); i++)
		{
			list.add(ja.getFloat(i));
		}
		return list;
	}

	public static List<Double> loadDoubleList(JSONArray ja)
	{
		List<Double> list = new ArrayList<Double>();
		for (int i = 0; i < ja.size(); i++)
		{
			list.add(ja.getDouble(i));
		}
		return list;
	}

	public static List<byte[]> loadBytesList(JSONArray ja)
	{
		List<byte[]> list = new ArrayList<byte[]>();
		for (int i = 0; i < ja.size(); i++)
		{
			list.add(CommonUtils.base64Decode(ja.getString(i)));
		}
		return list;
	}

	public static JSONArray saveStringList(List<String> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			ja.add(list.get(i));
		}
		return ja;
	}

	public static JSONArray saveBooleanList(List<Boolean> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			ja.add(list.get(i));
		}
		return ja;
	}

	public static JSONArray saveIntegerList(List<Integer> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			ja.add(list.get(i));
		}
		return ja;
	}

	public static JSONArray saveLongList(List<Long> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			ja.add(list.get(i));
		}
		return ja;
	}

	public static JSONArray saveFloatList(List<Float> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			ja.add(list.get(i));
		}
		return ja;
	}

	public static JSONArray saveDoubleList(List<Double> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			ja.add(list.get(i));
		}
		return ja;
	}

	public static JSONArray saveBytesList(List<byte[]> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			ja.add(list.get(i));
		}
		return ja;
	}
}
