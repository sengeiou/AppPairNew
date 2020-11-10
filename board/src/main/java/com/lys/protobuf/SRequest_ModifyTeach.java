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
import com.lys.protobuf.ProtocolScore.Request_ModifyTeach;

// ---------------------- xxxxxxxx --------------------------
public class SRequest_ModifyTeach extends SPTData<Request_ModifyTeach>
{
	private static final SRequest_ModifyTeach DefaultInstance = new SRequest_ModifyTeach();

	public List<STeach> teachs = new ArrayList<STeach>();

	public static SRequest_ModifyTeach create()
	{
		SRequest_ModifyTeach obj = new SRequest_ModifyTeach();
		return obj;
	}

	public SRequest_ModifyTeach clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SRequest_ModifyTeach _other_)
	{
		this.teachs = _other_.teachs;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("teachs"))
			teachs = STeach.loadList(_json_.getJSONArray("teachs"));
	}

	public static SRequest_ModifyTeach load(String str)
	{
		try
		{
			SRequest_ModifyTeach obj = new SRequest_ModifyTeach();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_ModifyTeach load(JSONObject json)
	{
		try
		{
			SRequest_ModifyTeach obj = new SRequest_ModifyTeach();
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
			if (teachs != null)
				_json_.put("teachs", STeach.saveList(teachs));
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SRequest_ModifyTeach> loadList(JSONArray ja)
	{
		try
		{
			List<SRequest_ModifyTeach> list = new ArrayList<SRequest_ModifyTeach>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SRequest_ModifyTeach item = SRequest_ModifyTeach.load(jo);
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

	public static JSONArray saveList(List<SRequest_ModifyTeach> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SRequest_ModifyTeach item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Request_ModifyTeach _proto_)
	{
		for (int i = 0; i < _proto_.getTeachsCount(); i++)
			teachs.add(STeach.load(_proto_.getTeachs(i)));
	}

	public static SRequest_ModifyTeach load(byte[] bytes)
	{
		try
		{
			SRequest_ModifyTeach obj = new SRequest_ModifyTeach();
			obj.parse(Request_ModifyTeach.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_ModifyTeach load(Request_ModifyTeach proto)
	{
		try
		{
			SRequest_ModifyTeach obj = new SRequest_ModifyTeach();
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
	public Request_ModifyTeach saveToProto()
	{
		Request_ModifyTeach.Builder _builder_ = Request_ModifyTeach.newBuilder();
		if (teachs != null)
			for (STeach _value_ : teachs)
				_builder_.addTeachs(_value_.saveToProto());
		return _builder_.build();
	}
}
