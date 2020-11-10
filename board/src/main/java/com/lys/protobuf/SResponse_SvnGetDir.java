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
import com.lys.protobuf.ProtocolPair2.Response_SvnGetDir;

public class SResponse_SvnGetDir extends SPTData<Response_SvnGetDir>
{
	private static final SResponse_SvnGetDir DefaultInstance = new SResponse_SvnGetDir();

	public List<SSvnDirObj> objs = new ArrayList<SSvnDirObj>();

	public static SResponse_SvnGetDir create()
	{
		SResponse_SvnGetDir obj = new SResponse_SvnGetDir();
		return obj;
	}

	public SResponse_SvnGetDir clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SResponse_SvnGetDir _other_)
	{
		this.objs = _other_.objs;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("objs"))
			objs = SSvnDirObj.loadList(_json_.getJSONArray("objs"));
	}

	public static SResponse_SvnGetDir load(String str)
	{
		try
		{
			SResponse_SvnGetDir obj = new SResponse_SvnGetDir();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_SvnGetDir load(JSONObject json)
	{
		try
		{
			SResponse_SvnGetDir obj = new SResponse_SvnGetDir();
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

	public static List<SResponse_SvnGetDir> loadList(JSONArray ja)
	{
		try
		{
			List<SResponse_SvnGetDir> list = new ArrayList<SResponse_SvnGetDir>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SResponse_SvnGetDir item = SResponse_SvnGetDir.load(jo);
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

	public static JSONArray saveList(List<SResponse_SvnGetDir> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SResponse_SvnGetDir item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Response_SvnGetDir _proto_)
	{
		for (int i = 0; i < _proto_.getObjsCount(); i++)
			objs.add(SSvnDirObj.load(_proto_.getObjs(i)));
	}

	public static SResponse_SvnGetDir load(byte[] bytes)
	{
		try
		{
			SResponse_SvnGetDir obj = new SResponse_SvnGetDir();
			obj.parse(Response_SvnGetDir.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_SvnGetDir load(Response_SvnGetDir proto)
	{
		try
		{
			SResponse_SvnGetDir obj = new SResponse_SvnGetDir();
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
	public Response_SvnGetDir saveToProto()
	{
		Response_SvnGetDir.Builder _builder_ = Response_SvnGetDir.newBuilder();
		if (objs != null)
			for (SSvnDirObj _value_ : objs)
				_builder_.addObjs(_value_.saveToProto());
		return _builder_.build();
	}
}
