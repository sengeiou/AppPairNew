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
import com.lys.protobuf.ProtocolTeach.Request_TeachOverByTeacher;

// ---------------------- xxxxxxx --------------------------
public class SRequest_TeachOverByTeacher extends SPTData<Request_TeachOverByTeacher>
{
	private static final SRequest_TeachOverByTeacher DefaultInstance = new SRequest_TeachOverByTeacher();

	public String teachId = null;
	public String userId = null;
	public List<STeachPage> teachPages = new ArrayList<STeachPage>();

	public static SRequest_TeachOverByTeacher create(String teachId, String userId)
	{
		SRequest_TeachOverByTeacher obj = new SRequest_TeachOverByTeacher();
		obj.teachId = teachId;
		obj.userId = userId;
		return obj;
	}

	public SRequest_TeachOverByTeacher clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SRequest_TeachOverByTeacher _other_)
	{
		this.teachId = _other_.teachId;
		this.userId = _other_.userId;
		this.teachPages = _other_.teachPages;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("teachId"))
			teachId = _json_.getString("teachId");
		if (_json_.containsKey("userId"))
			userId = _json_.getString("userId");
		if (_json_.containsKey("teachPages"))
			teachPages = STeachPage.loadList(_json_.getJSONArray("teachPages"));
	}

	public static SRequest_TeachOverByTeacher load(String str)
	{
		try
		{
			SRequest_TeachOverByTeacher obj = new SRequest_TeachOverByTeacher();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_TeachOverByTeacher load(JSONObject json)
	{
		try
		{
			SRequest_TeachOverByTeacher obj = new SRequest_TeachOverByTeacher();
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
			if (teachId != null)
				_json_.put("teachId", teachId);
			if (userId != null)
				_json_.put("userId", userId);
			if (teachPages != null)
				_json_.put("teachPages", STeachPage.saveList(teachPages));
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SRequest_TeachOverByTeacher> loadList(JSONArray ja)
	{
		try
		{
			List<SRequest_TeachOverByTeacher> list = new ArrayList<SRequest_TeachOverByTeacher>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SRequest_TeachOverByTeacher item = SRequest_TeachOverByTeacher.load(jo);
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

	public static JSONArray saveList(List<SRequest_TeachOverByTeacher> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SRequest_TeachOverByTeacher item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Request_TeachOverByTeacher _proto_)
	{
		if (_proto_.hasTeachId())
			teachId = _proto_.getTeachId();
		if (_proto_.hasUserId())
			userId = _proto_.getUserId();
		for (int i = 0; i < _proto_.getTeachPagesCount(); i++)
			teachPages.add(STeachPage.load(_proto_.getTeachPages(i)));
	}

	public static SRequest_TeachOverByTeacher load(byte[] bytes)
	{
		try
		{
			SRequest_TeachOverByTeacher obj = new SRequest_TeachOverByTeacher();
			obj.parse(Request_TeachOverByTeacher.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_TeachOverByTeacher load(Request_TeachOverByTeacher proto)
	{
		try
		{
			SRequest_TeachOverByTeacher obj = new SRequest_TeachOverByTeacher();
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
	public Request_TeachOverByTeacher saveToProto()
	{
		Request_TeachOverByTeacher.Builder _builder_ = Request_TeachOverByTeacher.newBuilder();
		if (teachId != null && !teachId.equals(Request_TeachOverByTeacher.getDefaultInstance().getTeachId()))
			_builder_.setTeachId(teachId);
		if (userId != null && !userId.equals(Request_TeachOverByTeacher.getDefaultInstance().getUserId()))
			_builder_.setUserId(userId);
		if (teachPages != null)
			for (STeachPage _value_ : teachPages)
				_builder_.addTeachPages(_value_.saveToProto());
		return _builder_.build();
	}
}
