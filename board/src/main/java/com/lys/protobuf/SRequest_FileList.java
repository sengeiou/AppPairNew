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
import com.lys.protobuf.ProtocolPair2.Request_FileList;

// ---------------------- xxxxxxxx --------------------------
public class SRequest_FileList extends SPTData<Request_FileList>
{
	private static final SRequest_FileList DefaultInstance = new SRequest_FileList();

	public String path = null;

	public static SRequest_FileList create(String path)
	{
		SRequest_FileList obj = new SRequest_FileList();
		obj.path = path;
		return obj;
	}

	public SRequest_FileList clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SRequest_FileList _other_)
	{
		this.path = _other_.path;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("path"))
			path = _json_.getString("path");
	}

	public static SRequest_FileList load(String str)
	{
		try
		{
			SRequest_FileList obj = new SRequest_FileList();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_FileList load(JSONObject json)
	{
		try
		{
			SRequest_FileList obj = new SRequest_FileList();
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

	public static List<SRequest_FileList> loadList(JSONArray ja)
	{
		try
		{
			List<SRequest_FileList> list = new ArrayList<SRequest_FileList>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SRequest_FileList item = SRequest_FileList.load(jo);
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

	public static JSONArray saveList(List<SRequest_FileList> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SRequest_FileList item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Request_FileList _proto_)
	{
		if (_proto_.hasPath())
			path = _proto_.getPath();
	}

	public static SRequest_FileList load(byte[] bytes)
	{
		try
		{
			SRequest_FileList obj = new SRequest_FileList();
			obj.parse(Request_FileList.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_FileList load(Request_FileList proto)
	{
		try
		{
			SRequest_FileList obj = new SRequest_FileList();
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
	public Request_FileList saveToProto()
	{
		Request_FileList.Builder _builder_ = Request_FileList.newBuilder();
		if (path != null && !path.equals(Request_FileList.getDefaultInstance().getPath()))
			_builder_.setPath(path);
		return _builder_.build();
	}
}
