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
import com.lys.protobuf.ProtocolPair2.Request_FileExists;

// ---------------------- xxxxxxxx --------------------------
public class SRequest_FileExists extends SPTData<Request_FileExists>
{
	private static final SRequest_FileExists DefaultInstance = new SRequest_FileExists();

	public String path = null;

	public static SRequest_FileExists create(String path)
	{
		SRequest_FileExists obj = new SRequest_FileExists();
		obj.path = path;
		return obj;
	}

	public SRequest_FileExists clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SRequest_FileExists _other_)
	{
		this.path = _other_.path;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("path"))
			path = _json_.getString("path");
	}

	public static SRequest_FileExists load(String str)
	{
		try
		{
			SRequest_FileExists obj = new SRequest_FileExists();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_FileExists load(JSONObject json)
	{
		try
		{
			SRequest_FileExists obj = new SRequest_FileExists();
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

	public static List<SRequest_FileExists> loadList(JSONArray ja)
	{
		try
		{
			List<SRequest_FileExists> list = new ArrayList<SRequest_FileExists>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SRequest_FileExists item = SRequest_FileExists.load(jo);
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

	public static JSONArray saveList(List<SRequest_FileExists> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SRequest_FileExists item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Request_FileExists _proto_)
	{
		if (_proto_.hasPath())
			path = _proto_.getPath();
	}

	public static SRequest_FileExists load(byte[] bytes)
	{
		try
		{
			SRequest_FileExists obj = new SRequest_FileExists();
			obj.parse(Request_FileExists.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_FileExists load(Request_FileExists proto)
	{
		try
		{
			SRequest_FileExists obj = new SRequest_FileExists();
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
	public Request_FileExists saveToProto()
	{
		Request_FileExists.Builder _builder_ = Request_FileExists.newBuilder();
		if (path != null && !path.equals(Request_FileExists.getDefaultInstance().getPath()))
			_builder_.setPath(path);
		return _builder_.build();
	}
}
