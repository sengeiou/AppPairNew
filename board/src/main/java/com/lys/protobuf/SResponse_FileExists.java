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
import com.lys.protobuf.ProtocolPair2.Response_FileExists;

public class SResponse_FileExists extends SPTData<Response_FileExists>
{
	private static final SResponse_FileExists DefaultInstance = new SResponse_FileExists();

	public Boolean exists = false;

	public static SResponse_FileExists create(Boolean exists)
	{
		SResponse_FileExists obj = new SResponse_FileExists();
		obj.exists = exists;
		return obj;
	}

	public SResponse_FileExists clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SResponse_FileExists _other_)
	{
		this.exists = _other_.exists;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("exists"))
			exists = _json_.getBoolean("exists");
	}

	public static SResponse_FileExists load(String str)
	{
		try
		{
			SResponse_FileExists obj = new SResponse_FileExists();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_FileExists load(JSONObject json)
	{
		try
		{
			SResponse_FileExists obj = new SResponse_FileExists();
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
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SResponse_FileExists> loadList(JSONArray ja)
	{
		try
		{
			List<SResponse_FileExists> list = new ArrayList<SResponse_FileExists>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SResponse_FileExists item = SResponse_FileExists.load(jo);
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

	public static JSONArray saveList(List<SResponse_FileExists> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SResponse_FileExists item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Response_FileExists _proto_)
	{
		if (_proto_.hasExists())
			exists = _proto_.getExists();
	}

	public static SResponse_FileExists load(byte[] bytes)
	{
		try
		{
			SResponse_FileExists obj = new SResponse_FileExists();
			obj.parse(Response_FileExists.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_FileExists load(Response_FileExists proto)
	{
		try
		{
			SResponse_FileExists obj = new SResponse_FileExists();
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
	public Response_FileExists saveToProto()
	{
		Response_FileExists.Builder _builder_ = Response_FileExists.newBuilder();
		if (exists != null && !exists.equals(Response_FileExists.getDefaultInstance().getExists()))
			_builder_.setExists(exists);
		return _builder_.build();
	}
}
