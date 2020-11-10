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
import com.lys.protobuf.ProtocolPair2.Request_SvnGetDir;

// ---------------------- 获取SVN目录 --------------------------
public class SRequest_SvnGetDir extends SPTData<Request_SvnGetDir>
{
	private static final SRequest_SvnGetDir DefaultInstance = new SRequest_SvnGetDir();

	public String path = null;

	public static SRequest_SvnGetDir create(String path)
	{
		SRequest_SvnGetDir obj = new SRequest_SvnGetDir();
		obj.path = path;
		return obj;
	}

	public SRequest_SvnGetDir clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SRequest_SvnGetDir _other_)
	{
		this.path = _other_.path;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("path"))
			path = _json_.getString("path");
	}

	public static SRequest_SvnGetDir load(String str)
	{
		try
		{
			SRequest_SvnGetDir obj = new SRequest_SvnGetDir();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_SvnGetDir load(JSONObject json)
	{
		try
		{
			SRequest_SvnGetDir obj = new SRequest_SvnGetDir();
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
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SRequest_SvnGetDir> loadList(JSONArray ja)
	{
		try
		{
			List<SRequest_SvnGetDir> list = new ArrayList<SRequest_SvnGetDir>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SRequest_SvnGetDir item = SRequest_SvnGetDir.load(jo);
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

	public static JSONArray saveList(List<SRequest_SvnGetDir> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SRequest_SvnGetDir item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Request_SvnGetDir _proto_)
	{
		if (_proto_.hasPath())
			path = _proto_.getPath();
	}

	public static SRequest_SvnGetDir load(byte[] bytes)
	{
		try
		{
			SRequest_SvnGetDir obj = new SRequest_SvnGetDir();
			obj.parse(Request_SvnGetDir.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_SvnGetDir load(Request_SvnGetDir proto)
	{
		try
		{
			SRequest_SvnGetDir obj = new SRequest_SvnGetDir();
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
	public Request_SvnGetDir saveToProto()
	{
		Request_SvnGetDir.Builder _builder_ = Request_SvnGetDir.newBuilder();
		if (path != null && !path.equals(Request_SvnGetDir.getDefaultInstance().getPath()))
			_builder_.setPath(path);
		return _builder_.build();
	}
}
