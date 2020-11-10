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
import com.lys.protobuf.ProtocolPair2.Request_FileCopy;

// ---------------------- xxxxxxxx --------------------------
public class SRequest_FileCopy extends SPTData<Request_FileCopy>
{
	private static final SRequest_FileCopy DefaultInstance = new SRequest_FileCopy();

	public String srcPath = null;
	public String dstPath = null;

	public static SRequest_FileCopy create(String srcPath, String dstPath)
	{
		SRequest_FileCopy obj = new SRequest_FileCopy();
		obj.srcPath = srcPath;
		obj.dstPath = dstPath;
		return obj;
	}

	public SRequest_FileCopy clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SRequest_FileCopy _other_)
	{
		this.srcPath = _other_.srcPath;
		this.dstPath = _other_.dstPath;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("srcPath"))
			srcPath = _json_.getString("srcPath");
		if (_json_.containsKey("dstPath"))
			dstPath = _json_.getString("dstPath");
	}

	public static SRequest_FileCopy load(String str)
	{
		try
		{
			SRequest_FileCopy obj = new SRequest_FileCopy();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_FileCopy load(JSONObject json)
	{
		try
		{
			SRequest_FileCopy obj = new SRequest_FileCopy();
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
			if (srcPath != null)
				_json_.put("srcPath", srcPath);
			if (dstPath != null)
				_json_.put("dstPath", dstPath);
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SRequest_FileCopy> loadList(JSONArray ja)
	{
		try
		{
			List<SRequest_FileCopy> list = new ArrayList<SRequest_FileCopy>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SRequest_FileCopy item = SRequest_FileCopy.load(jo);
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

	public static JSONArray saveList(List<SRequest_FileCopy> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SRequest_FileCopy item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Request_FileCopy _proto_)
	{
		if (_proto_.hasSrcPath())
			srcPath = _proto_.getSrcPath();
		if (_proto_.hasDstPath())
			dstPath = _proto_.getDstPath();
	}

	public static SRequest_FileCopy load(byte[] bytes)
	{
		try
		{
			SRequest_FileCopy obj = new SRequest_FileCopy();
			obj.parse(Request_FileCopy.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_FileCopy load(Request_FileCopy proto)
	{
		try
		{
			SRequest_FileCopy obj = new SRequest_FileCopy();
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
	public Request_FileCopy saveToProto()
	{
		Request_FileCopy.Builder _builder_ = Request_FileCopy.newBuilder();
		if (srcPath != null && !srcPath.equals(Request_FileCopy.getDefaultInstance().getSrcPath()))
			_builder_.setSrcPath(srcPath);
		if (dstPath != null && !dstPath.equals(Request_FileCopy.getDefaultInstance().getDstPath()))
			_builder_.setDstPath(dstPath);
		return _builder_.build();
	}
}
