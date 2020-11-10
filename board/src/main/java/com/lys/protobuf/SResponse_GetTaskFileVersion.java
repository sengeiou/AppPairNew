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
import com.lys.protobuf.ProtocolPair.Response_GetTaskFileVersion;

public class SResponse_GetTaskFileVersion extends SPTData<Response_GetTaskFileVersion>
{
	private static final SResponse_GetTaskFileVersion DefaultInstance = new SResponse_GetTaskFileVersion();

	public Boolean exists = false;
	public Long lastModifyTime = 0L;

	public static SResponse_GetTaskFileVersion create(Boolean exists, Long lastModifyTime)
	{
		SResponse_GetTaskFileVersion obj = new SResponse_GetTaskFileVersion();
		obj.exists = exists;
		obj.lastModifyTime = lastModifyTime;
		return obj;
	}

	public SResponse_GetTaskFileVersion clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SResponse_GetTaskFileVersion _other_)
	{
		this.exists = _other_.exists;
		this.lastModifyTime = _other_.lastModifyTime;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("exists"))
			exists = _json_.getBoolean("exists");
		if (_json_.containsKey("lastModifyTime"))
			lastModifyTime = _json_.getLong("lastModifyTime");
	}

	public static SResponse_GetTaskFileVersion load(String str)
	{
		try
		{
			SResponse_GetTaskFileVersion obj = new SResponse_GetTaskFileVersion();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_GetTaskFileVersion load(JSONObject json)
	{
		try
		{
			SResponse_GetTaskFileVersion obj = new SResponse_GetTaskFileVersion();
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
			if (exists != null)
				_json_.put("exists", exists);
			if (lastModifyTime != null)
				_json_.put("lastModifyTime", String.valueOf(lastModifyTime));
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SResponse_GetTaskFileVersion> loadList(JSONArray ja)
	{
		try
		{
			List<SResponse_GetTaskFileVersion> list = new ArrayList<SResponse_GetTaskFileVersion>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SResponse_GetTaskFileVersion item = SResponse_GetTaskFileVersion.load(jo);
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

	public static JSONArray saveList(List<SResponse_GetTaskFileVersion> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SResponse_GetTaskFileVersion item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Response_GetTaskFileVersion _proto_)
	{
		if (_proto_.hasExists())
			exists = _proto_.getExists();
		if (_proto_.hasLastModifyTime())
			lastModifyTime = _proto_.getLastModifyTime();
	}

	public static SResponse_GetTaskFileVersion load(byte[] bytes)
	{
		try
		{
			SResponse_GetTaskFileVersion obj = new SResponse_GetTaskFileVersion();
			obj.parse(Response_GetTaskFileVersion.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_GetTaskFileVersion load(Response_GetTaskFileVersion proto)
	{
		try
		{
			SResponse_GetTaskFileVersion obj = new SResponse_GetTaskFileVersion();
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
	public Response_GetTaskFileVersion saveToProto()
	{
		Response_GetTaskFileVersion.Builder _builder_ = Response_GetTaskFileVersion.newBuilder();
		if (exists != null && !exists.equals(Response_GetTaskFileVersion.getDefaultInstance().getExists()))
			_builder_.setExists(exists);
		if (lastModifyTime != null && !lastModifyTime.equals(Response_GetTaskFileVersion.getDefaultInstance().getLastModifyTime()))
			_builder_.setLastModifyTime(lastModifyTime);
		return _builder_.build();
	}
}
