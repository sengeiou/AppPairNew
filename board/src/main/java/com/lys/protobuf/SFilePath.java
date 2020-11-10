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
import com.lys.protobuf.ProtocolPair2.FilePath;

public class SFilePath extends SPTData<FilePath>
{
	private static final SFilePath DefaultInstance = new SFilePath();

	public String path = null;
	public String md5 = null;

	public static SFilePath create(String path, String md5)
	{
		SFilePath obj = new SFilePath();
		obj.path = path;
		obj.md5 = md5;
		return obj;
	}

	public SFilePath clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SFilePath _other_)
	{
		this.path = _other_.path;
		this.md5 = _other_.md5;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("path"))
			path = _json_.getString("path");
		if (_json_.containsKey("md5"))
			md5 = _json_.getString("md5");
	}

	public static SFilePath load(String str)
	{
		try
		{
			SFilePath obj = new SFilePath();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SFilePath load(JSONObject json)
	{
		try
		{
			SFilePath obj = new SFilePath();
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
			if (path != null)
				_json_.put("path", path);
			if (md5 != null)
				_json_.put("md5", md5);
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SFilePath> loadList(JSONArray ja)
	{
		try
		{
			List<SFilePath> list = new ArrayList<SFilePath>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SFilePath item = SFilePath.load(jo);
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

	public static JSONArray saveList(List<SFilePath> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SFilePath item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(FilePath _proto_)
	{
		if (_proto_.hasPath())
			path = _proto_.getPath();
		if (_proto_.hasMd5())
			md5 = _proto_.getMd5();
	}

	public static SFilePath load(byte[] bytes)
	{
		try
		{
			SFilePath obj = new SFilePath();
			obj.parse(FilePath.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SFilePath load(FilePath proto)
	{
		try
		{
			SFilePath obj = new SFilePath();
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
	public FilePath saveToProto()
	{
		FilePath.Builder _builder_ = FilePath.newBuilder();
		if (path != null && !path.equals(FilePath.getDefaultInstance().getPath()))
			_builder_.setPath(path);
		if (md5 != null && !md5.equals(FilePath.getDefaultInstance().getMd5()))
			_builder_.setMd5(md5);
		return _builder_.build();
	}
}
