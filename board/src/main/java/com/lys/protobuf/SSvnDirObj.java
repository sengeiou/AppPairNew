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
import com.lys.protobuf.ProtocolPair2.SvnDirObj;

public class SSvnDirObj extends SPTData<SvnDirObj>
{
	private static final SSvnDirObj DefaultInstance = new SSvnDirObj();

	public Boolean isDir = false;
	public String name = null;
	public String md5 = null;
	public List<SSvnDirObj> objs = new ArrayList<SSvnDirObj>();

	public static SSvnDirObj create(Boolean isDir, String name, String md5)
	{
		SSvnDirObj obj = new SSvnDirObj();
		obj.isDir = isDir;
		obj.name = name;
		obj.md5 = md5;
		return obj;
	}

	public SSvnDirObj clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SSvnDirObj _other_)
	{
		this.isDir = _other_.isDir;
		this.name = _other_.name;
		this.md5 = _other_.md5;
		this.objs = _other_.objs;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("isDir"))
			isDir = _json_.getBoolean("isDir");
		if (_json_.containsKey("name"))
			name = _json_.getString("name");
		if (_json_.containsKey("md5"))
			md5 = _json_.getString("md5");
		if (_json_.containsKey("objs"))
			objs = SSvnDirObj.loadList(_json_.getJSONArray("objs"));
	}

	public static SSvnDirObj load(String str)
	{
		try
		{
			SSvnDirObj obj = new SSvnDirObj();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SSvnDirObj load(JSONObject json)
	{
		try
		{
			SSvnDirObj obj = new SSvnDirObj();
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
			if (isDir != null)
				_json_.put("isDir", isDir);
			if (name != null)
				_json_.put("name", name);
			if (md5 != null)
				_json_.put("md5", md5);
			if (objs != null)
				_json_.put("objs", SSvnDirObj.saveList(objs));
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SSvnDirObj> loadList(JSONArray ja)
	{
		try
		{
			List<SSvnDirObj> list = new ArrayList<SSvnDirObj>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SSvnDirObj item = SSvnDirObj.load(jo);
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

	public static JSONArray saveList(List<SSvnDirObj> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SSvnDirObj item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(SvnDirObj _proto_)
	{
		if (_proto_.hasIsDir())
			isDir = _proto_.getIsDir();
		if (_proto_.hasName())
			name = _proto_.getName();
		if (_proto_.hasMd5())
			md5 = _proto_.getMd5();
		for (int i = 0; i < _proto_.getObjsCount(); i++)
			objs.add(SSvnDirObj.load(_proto_.getObjs(i)));
	}

	public static SSvnDirObj load(byte[] bytes)
	{
		try
		{
			SSvnDirObj obj = new SSvnDirObj();
			obj.parse(SvnDirObj.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SSvnDirObj load(SvnDirObj proto)
	{
		try
		{
			SSvnDirObj obj = new SSvnDirObj();
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
	public SvnDirObj saveToProto()
	{
		SvnDirObj.Builder _builder_ = SvnDirObj.newBuilder();
		if (isDir != null && !isDir.equals(SvnDirObj.getDefaultInstance().getIsDir()))
			_builder_.setIsDir(isDir);
		if (name != null && !name.equals(SvnDirObj.getDefaultInstance().getName()))
			_builder_.setName(name);
		if (md5 != null && !md5.equals(SvnDirObj.getDefaultInstance().getMd5()))
			_builder_.setMd5(md5);
		if (objs != null)
			for (SSvnDirObj _value_ : objs)
				_builder_.addObjs(_value_.saveToProto());
		return _builder_.build();
	}
}
