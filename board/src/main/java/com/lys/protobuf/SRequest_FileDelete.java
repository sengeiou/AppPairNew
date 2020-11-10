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
import com.lys.protobuf.ProtocolPair2.Request_FileDelete;

// ---------------------- xxxxxxxx --------------------------
public class SRequest_FileDelete extends SPTData<Request_FileDelete>
{
	private static final SRequest_FileDelete DefaultInstance = new SRequest_FileDelete();

	public List<String> paths = new ArrayList<String>();

	public static SRequest_FileDelete create()
	{
		SRequest_FileDelete obj = new SRequest_FileDelete();
		return obj;
	}

	public SRequest_FileDelete clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SRequest_FileDelete _other_)
	{
		this.paths = _other_.paths;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("paths"))
			paths = AppDataTool.loadStringList(AppDataTool.getJSONArray(_json_, "paths"));
	}

	public static SRequest_FileDelete load(String str)
	{
		try
		{
			SRequest_FileDelete obj = new SRequest_FileDelete();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_FileDelete load(JSONObject json)
	{
		try
		{
			SRequest_FileDelete obj = new SRequest_FileDelete();
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
			if (paths != null)
				_json_.put("paths", AppDataTool.saveStringList(paths));
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SRequest_FileDelete> loadList(JSONArray ja)
	{
		try
		{
			List<SRequest_FileDelete> list = new ArrayList<SRequest_FileDelete>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SRequest_FileDelete item = SRequest_FileDelete.load(jo);
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

	public static JSONArray saveList(List<SRequest_FileDelete> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SRequest_FileDelete item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Request_FileDelete _proto_)
	{
		for (int i = 0; i < _proto_.getPathsCount(); i++)
			paths.add(_proto_.getPaths(i));
	}

	public static SRequest_FileDelete load(byte[] bytes)
	{
		try
		{
			SRequest_FileDelete obj = new SRequest_FileDelete();
			obj.parse(Request_FileDelete.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_FileDelete load(Request_FileDelete proto)
	{
		try
		{
			SRequest_FileDelete obj = new SRequest_FileDelete();
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
	public Request_FileDelete saveToProto()
	{
		Request_FileDelete.Builder _builder_ = Request_FileDelete.newBuilder();
		if (paths != null)
			for (String _value_ : paths)
				_builder_.addPaths(_value_);
		return _builder_.build();
	}
}
