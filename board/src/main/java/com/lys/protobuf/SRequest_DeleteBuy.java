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
import com.lys.protobuf.ProtocolShop.Request_DeleteBuy;

// ---------------------- xxxxxxxx --------------------------
public class SRequest_DeleteBuy extends SPTData<Request_DeleteBuy>
{
	private static final SRequest_DeleteBuy DefaultInstance = new SRequest_DeleteBuy();

	public String userId = null;
	public String matterId = null;

	public static SRequest_DeleteBuy create(String userId, String matterId)
	{
		SRequest_DeleteBuy obj = new SRequest_DeleteBuy();
		obj.userId = userId;
		obj.matterId = matterId;
		return obj;
	}

	public SRequest_DeleteBuy clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SRequest_DeleteBuy _other_)
	{
		this.userId = _other_.userId;
		this.matterId = _other_.matterId;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("userId"))
			userId = _json_.getString("userId");
		if (_json_.containsKey("matterId"))
			matterId = _json_.getString("matterId");
	}

	public static SRequest_DeleteBuy load(String str)
	{
		try
		{
			SRequest_DeleteBuy obj = new SRequest_DeleteBuy();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_DeleteBuy load(JSONObject json)
	{
		try
		{
			SRequest_DeleteBuy obj = new SRequest_DeleteBuy();
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

	public static List<SRequest_DeleteBuy> loadList(JSONArray ja)
	{
		try
		{
			List<SRequest_DeleteBuy> list = new ArrayList<SRequest_DeleteBuy>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SRequest_DeleteBuy item = SRequest_DeleteBuy.load(jo);
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

	public static JSONArray saveList(List<SRequest_DeleteBuy> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SRequest_DeleteBuy item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Request_DeleteBuy _proto_)
	{
		if (_proto_.hasUserId())
			userId = _proto_.getUserId();
		if (_proto_.hasMatterId())
			matterId = _proto_.getMatterId();
	}

	public static SRequest_DeleteBuy load(byte[] bytes)
	{
		try
		{
			SRequest_DeleteBuy obj = new SRequest_DeleteBuy();
			obj.parse(Request_DeleteBuy.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_DeleteBuy load(Request_DeleteBuy proto)
	{
		try
		{
			SRequest_DeleteBuy obj = new SRequest_DeleteBuy();
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
	public Request_DeleteBuy saveToProto()
	{
		Request_DeleteBuy.Builder _builder_ = Request_DeleteBuy.newBuilder();
		if (userId != null && !userId.equals(Request_DeleteBuy.getDefaultInstance().getUserId()))
			_builder_.setUserId(userId);
		if (matterId != null && !matterId.equals(Request_DeleteBuy.getDefaultInstance().getMatterId()))
			_builder_.setMatterId(matterId);
		return _builder_.build();
	}
}
