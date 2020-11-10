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
import com.lys.protobuf.ProtocolShop.Request_SwapMatter;

// ---------------------- xxxxxxxx --------------------------
public class SRequest_SwapMatter extends SPTData<Request_SwapMatter>
{
	private static final SRequest_SwapMatter DefaultInstance = new SRequest_SwapMatter();

	public SMatter matter1 = null;
	public SMatter matter2 = null;

	public static SRequest_SwapMatter create(SMatter matter1, SMatter matter2)
	{
		SRequest_SwapMatter obj = new SRequest_SwapMatter();
		obj.matter1 = matter1;
		obj.matter2 = matter2;
		return obj;
	}

	public SRequest_SwapMatter clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SRequest_SwapMatter _other_)
	{
		this.matter1 = _other_.matter1;
		this.matter2 = _other_.matter2;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("matter1"))
			matter1 = SMatter.load(_json_.getJSONObject("matter1"));
		if (_json_.containsKey("matter2"))
			matter2 = SMatter.load(_json_.getJSONObject("matter2"));
	}

	public static SRequest_SwapMatter load(String str)
	{
		try
		{
			SRequest_SwapMatter obj = new SRequest_SwapMatter();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_SwapMatter load(JSONObject json)
	{
		try
		{
			SRequest_SwapMatter obj = new SRequest_SwapMatter();
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
			if (matter1 != null)
				_json_.put("matter1", matter1.saveToJson());
			if (matter2 != null)
				_json_.put("matter2", matter2.saveToJson());
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SRequest_SwapMatter> loadList(JSONArray ja)
	{
		try
		{
			List<SRequest_SwapMatter> list = new ArrayList<SRequest_SwapMatter>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SRequest_SwapMatter item = SRequest_SwapMatter.load(jo);
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

	public static JSONArray saveList(List<SRequest_SwapMatter> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SRequest_SwapMatter item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Request_SwapMatter _proto_)
	{
		if (_proto_.hasMatter1())
			matter1 = SMatter.load(_proto_.getMatter1());
		if (_proto_.hasMatter2())
			matter2 = SMatter.load(_proto_.getMatter2());
	}

	public static SRequest_SwapMatter load(byte[] bytes)
	{
		try
		{
			SRequest_SwapMatter obj = new SRequest_SwapMatter();
			obj.parse(Request_SwapMatter.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_SwapMatter load(Request_SwapMatter proto)
	{
		try
		{
			SRequest_SwapMatter obj = new SRequest_SwapMatter();
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
	public Request_SwapMatter saveToProto()
	{
		Request_SwapMatter.Builder _builder_ = Request_SwapMatter.newBuilder();
		if (matter1 != null)
			_builder_.setMatter1(matter1.saveToProto());
		if (matter2 != null)
			_builder_.setMatter2(matter2.saveToProto());
		return _builder_.build();
	}
}
