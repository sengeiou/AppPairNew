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
import com.lys.protobuf.ProtocolShop.Request_AddModifyMatter;

// ---------------------- xxxxxxxx --------------------------
public class SRequest_AddModifyMatter extends SPTData<Request_AddModifyMatter>
{
	private static final SRequest_AddModifyMatter DefaultInstance = new SRequest_AddModifyMatter();

	public SMatter matter = null;

	public static SRequest_AddModifyMatter create(SMatter matter)
	{
		SRequest_AddModifyMatter obj = new SRequest_AddModifyMatter();
		obj.matter = matter;
		return obj;
	}

	public SRequest_AddModifyMatter clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SRequest_AddModifyMatter _other_)
	{
		this.matter = _other_.matter;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("matter"))
			matter = SMatter.load(_json_.getJSONObject("matter"));
	}

	public static SRequest_AddModifyMatter load(String str)
	{
		try
		{
			SRequest_AddModifyMatter obj = new SRequest_AddModifyMatter();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_AddModifyMatter load(JSONObject json)
	{
		try
		{
			SRequest_AddModifyMatter obj = new SRequest_AddModifyMatter();
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
			if (matter != null)
				_json_.put("matter", matter.saveToJson());
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SRequest_AddModifyMatter> loadList(JSONArray ja)
	{
		try
		{
			List<SRequest_AddModifyMatter> list = new ArrayList<SRequest_AddModifyMatter>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SRequest_AddModifyMatter item = SRequest_AddModifyMatter.load(jo);
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

	public static JSONArray saveList(List<SRequest_AddModifyMatter> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SRequest_AddModifyMatter item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Request_AddModifyMatter _proto_)
	{
		if (_proto_.hasMatter())
			matter = SMatter.load(_proto_.getMatter());
	}

	public static SRequest_AddModifyMatter load(byte[] bytes)
	{
		try
		{
			SRequest_AddModifyMatter obj = new SRequest_AddModifyMatter();
			obj.parse(Request_AddModifyMatter.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_AddModifyMatter load(Request_AddModifyMatter proto)
	{
		try
		{
			SRequest_AddModifyMatter obj = new SRequest_AddModifyMatter();
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
	public Request_AddModifyMatter saveToProto()
	{
		Request_AddModifyMatter.Builder _builder_ = Request_AddModifyMatter.newBuilder();
		if (matter != null)
			_builder_.setMatter(matter.saveToProto());
		return _builder_.build();
	}
}
