package com.lys.base.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class LOGJson
{
	public interface TraversalCallback
	{
		void callback(String line);
	}

	private static String getIndentStr(int indent)
	{
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < indent; i++)
		{
			sb.append("\t");
		}
		return sb.toString();
	}

	private static void traversal(String jsonStr, int indent, boolean isEnd, int limit, TraversalCallback traversalCallback)
	{
		try
		{
			int jsonType = JsonHelper.getJsonType(jsonStr);
			if (jsonType == JsonHelper.JsonTypeObj)
			{
				JSONObject obj = JsonHelper.getJSONObject(jsonStr);

				if (indent >= limit)
				{
					traversalCallback.callback(String.format("%s%s", getIndentStr(indent), obj.toString()));
					return;
				}

				traversalCallback.callback(String.format("%s%s", getIndentStr(indent), "{"));

				ArrayList<String> keys = JsonHelper.getKeys(obj);
				for (int i = 0; i < keys.size(); i++)
				{
					String key = keys.get(i);
					String value = obj.getString(key);

					if (JsonHelper.getJsonType(value) == JsonHelper.JsonTypeValue)
					{
						traversalCallback.callback(String.format("%s\"%s\":\"%s\"%s", getIndentStr(indent + 1), key, value, i == keys.size() - 1 ? "" : ","));
					}
					else
					{
						if (indent + 1 >= limit)
						{
							traversalCallback.callback(String.format("%s\"%s\":%s%s", getIndentStr(indent + 1), key, value, i == keys.size() - 1 ? "" : ","));
						}
						else
						{
							traversalCallback.callback(String.format("%s\"%s\":", getIndentStr(indent + 1), key));
							traversal(value, indent + 1, i == keys.size() - 1, limit, traversalCallback);
						}
					}
				}

				traversalCallback.callback(String.format("%s%s%s", getIndentStr(indent), "}", isEnd ? "" : ","));
			}
			else if (jsonType == JsonHelper.JsonTypeArr)
			{
				JSONArray array = JsonHelper.getJSONArray(jsonStr);

				if (indent >= limit)
				{
					traversalCallback.callback(String.format("%s%s", getIndentStr(indent), array.toString()));
					return;
				}

				traversalCallback.callback(String.format("%s%s", getIndentStr(indent), "["));

				for (int i = 0; i < array.size(); i++)
				{
					String value = array.getString(i);

					if (JsonHelper.getJsonType(value) == JsonHelper.JsonTypeValue)
					{
						traversalCallback.callback(String.format("%s\"%s\"%s", getIndentStr(indent + 1), value, i == array.size() - 1 ? "" : ","));
					}
					else
					{
						if (indent + 1 >= limit)
						{
							traversalCallback.callback(String.format("%s%s%s", getIndentStr(indent + 1), value, i == array.size() - 1 ? "" : ","));
						}
						else
						{
							traversal(value, indent + 1, i == array.size() - 1, limit, traversalCallback);
						}
					}
				}

				traversalCallback.callback(String.format("%s%s%s", getIndentStr(indent), "]", isEnd ? "" : ","));
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private static void traversal(String jsonStr, int limit, TraversalCallback traversalCallback)
	{
		traversal(jsonStr, 0, true, limit, traversalCallback);
	}

	public static void log(String jsonStr, int limit)
	{
		traversal(jsonStr, limit, new TraversalCallback()
		{
			@Override
			public void callback(String line)
			{
				LOG.v(line);
			}
		});
	}

	public static void log(String jsonStr)
	{
		log(jsonStr, Integer.MAX_VALUE);
	}

	public static String getStr(String jsonStr, int limit)
	{
		try
		{
			final ByteArrayOutputStream os = new ByteArrayOutputStream();
			traversal(jsonStr, limit, new TraversalCallback()
			{
				@Override
				public void callback(String line)
				{
					os.write(line.getBytes(), 0, line.getBytes().length);
					os.write("\r\n".getBytes(), 0, "\r\n".getBytes().length);
				}
			});
			os.close();
			return new String(os.toByteArray(), "UTF-8");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static String getStr(String jsonStr)
	{
		return getStr(jsonStr, Integer.MAX_VALUE);
	}

}
