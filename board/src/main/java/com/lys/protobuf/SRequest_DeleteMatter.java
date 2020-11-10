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
import com.lys.protobuf.ProtocolShop.Request_DeleteMatter;

// ---------------------- xxxxxxxx --------------------------
public class SRequest_DeleteMatter extends SPTData<Request_DeleteMatter>
{
	private static final SRequest_DeleteMatter DefaultInstance = new SRequest_DeleteMatter();

	public String matterId = null;

	public static SRequest_DeleteMatter create(String matterId)
	{
		SRequest_DeleteMatter obj = new SRequest_DeleteMatter();
		obj.matterId = matterId;
		return obj;
	}

	public SRequest_DeleteMatter clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SRequest_DeleteMatter _other_)
	{
		this.matterId = _other_.matterId;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("matterId"))
			matterId = _json_.getString("matterId");
	}

	public static SRequest_DeleteMatter load(String str)
	{
		try
		{
			SRequest_DeleteMatter obj = new SRequest_DeleteMatter();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_DeleteMatter load(JSONObject json)
	{
		try
		{
			SRequest_DeleteMatter obj = new SRequest_DeleteMatter();
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
			if (matterId != null)
				_json_.put("matterId", matterId);
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SRequest_DeleteMatter> loadList(JSONArray ja)
	{
		try
		{
			List<SRequest_DeleteMatter> list = new ArrayList<SRequest_DeleteMatter>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SRequest_DeleteMatter item = SRequest_DeleteMatter.load(jo);
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

	public static JSONArray saveList(List<SRequest_DeleteMatter> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SRequest_DeleteMatter item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Request_DeleteMatter _proto_)
	{
		if (_proto_.hasMatterId())
			matterId = _proto_.getMatterId();
	}

	public static SRequest_DeleteMatter load(byte[] bytes)
	{
		try
		{
			SRequest_DeleteMatter obj = new SRequest_DeleteMatter();
			obj.parse(Request_DeleteMatter.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_DeleteMatter load(Request_DeleteMatter proto)
	{
		try
		{
			SRequest_DeleteMatter obj = new SRequest_DeleteMatter();
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
	public Request_DeleteMatter saveToProto()
	{
		Request_DeleteMatter.Builder _builder_ = Request_DeleteMatter.newBuilder();
		if (matterId != null && !matterId.equals(Request_DeleteMatter.getDefaultInstance().getMatterId()))
			_builder_.setMatterId(matterId);
		return _builder_.build();
	}
}
