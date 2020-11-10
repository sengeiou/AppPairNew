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
import com.lys.protobuf.ProtocolLive.Request_LiveGetList;

// ---------------------- xxxxxxxxxxxxx --------------------------
public class SRequest_LiveGetList extends SPTData<Request_LiveGetList>
{
	private static final SRequest_LiveGetList DefaultInstance = new SRequest_LiveGetList();

	public String userId = null;

	public static SRequest_LiveGetList create(String userId)
	{
		SRequest_LiveGetList obj = new SRequest_LiveGetList();
		obj.userId = userId;
		return obj;
	}

	public SRequest_LiveGetList clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SRequest_LiveGetList _other_)
	{
		this.userId = _other_.userId;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("userId"))
			userId = _json_.getString("userId");
	}

	public static SRequest_LiveGetList load(String str)
	{
		try
		{
			SRequest_LiveGetList obj = new SRequest_LiveGetList();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_LiveGetList load(JSONObject json)
	{
		try
		{
			SRequest_LiveGetList obj = new SRequest_LiveGetList();
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
			if (userId != null)
				_json_.put("userId", userId);
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SRequest_LiveGetList> loadList(JSONArray ja)
	{
		try
		{
			List<SRequest_LiveGetList> list = new ArrayList<SRequest_LiveGetList>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SRequest_LiveGetList item = SRequest_LiveGetList.load(jo);
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

	public static JSONArray saveList(List<SRequest_LiveGetList> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SRequest_LiveGetList item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Request_LiveGetList _proto_)
	{
		if (_proto_.hasUserId())
			userId = _proto_.getUserId();
	}

	public static SRequest_LiveGetList load(byte[] bytes)
	{
		try
		{
			SRequest_LiveGetList obj = new SRequest_LiveGetList();
			obj.parse(Request_LiveGetList.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_LiveGetList load(Request_LiveGetList proto)
	{
		try
		{
			SRequest_LiveGetList obj = new SRequest_LiveGetList();
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
	public Request_LiveGetList saveToProto()
	{
		Request_LiveGetList.Builder _builder_ = Request_LiveGetList.newBuilder();
		if (userId != null && !userId.equals(Request_LiveGetList.getDefaultInstance().getUserId()))
			_builder_.setUserId(userId);
		return _builder_.build();
	}
}
