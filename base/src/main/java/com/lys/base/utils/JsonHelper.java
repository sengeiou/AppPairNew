package com.lys.base.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;

import java.util.ArrayList;
import java.util.Set;

public class JsonHelper
{
	public static final int JsonTypeValue = 0;
	public static final int JsonTypeObj = 1;
	public static final int JsonTypeArr = 2;

	public static int getJsonType(String json)
	{
		if (getJSONObject(json) != null)
		{
			return JsonTypeObj;
		}
		else if (getJSONArray(json) != null)
		{
			return JsonTypeArr;
		}
		else
		{
			return JsonTypeValue;
		}
	}

	public static JSONObject getJSONObject(String json)
	{
		try
		{
			JSONObject obj = JSONObject.parseObject(json, Feature.OrderedField);
			if (obj != null)
			{
				return obj;
			}
		}
		catch (Exception e)
		{

		}
		return null;
	}

	public static JSONObject getJSONObject(JSONArray array, int index)
	{
		try
		{
			JSONObject obj = array.getJSONObject(index);
			if (obj != null)
			{
				return obj;
			}
		}
		catch (Exception e)
		{

		}
		return null;
	}

	public static JSONArray getJSONArray(String json)
	{
		try
		{
			Object obj = JSONArray.parse(json, Feature.OrderedField);
			if (obj instanceof JSONArray)
			{
				return (JSONArray) obj;
			}
		}
		catch (Exception e)
		{

		}
		return null;
	}

	public static JSONArray getJSONArray(JSONArray array, int index)
	{
		try
		{
			JSONArray arr = array.getJSONArray(index);
			if (arr != null)
			{
				return arr;
			}
		}
		catch (Exception e)
		{

		}
		return null;
	}

	public static ArrayList<String> getKeys(JSONObject obj)
	{
		ArrayList<String> keys = new ArrayList<>();
		Set<String> keySet = obj.keySet();
		if (keySet != null)
		{
			for (String key : keySet)
			{
				keys.add(key);
			}
		}
		return keys;
	}
}
