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
import com.lys.protobuf.ProtocolShop.Request_AddModifyBuy;

// ---------------------- xxxxxxxx --------------------------
public class SRequest_AddModifyBuy extends SPTData<Request_AddModifyBuy>
{
	private static final SRequest_AddModifyBuy DefaultInstance = new SRequest_AddModifyBuy();

	public String userId = null;
	public String matterId = null;
	public Float hourBuy = 0F;
	public Float hourGive = 0F;

	public static SRequest_AddModifyBuy create(String userId, String matterId, Float hourBuy, Float hourGive)
	{
		SRequest_AddModifyBuy obj = new SRequest_AddModifyBuy();
		obj.userId = userId;
		obj.matterId = matterId;
		obj.hourBuy = hourBuy;
		obj.hourGive = hourGive;
		return obj;
	}

	public SRequest_AddModifyBuy clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SRequest_AddModifyBuy _other_)
	{
		this.userId = _other_.userId;
		this.matterId = _other_.matterId;
		this.hourBuy = _other_.hourBuy;
		this.hourGive = _other_.hourGive;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("userId"))
			userId = _json_.getString("userId");
		if (_json_.containsKey("matterId"))
			matterId = _json_.getString("matterId");
		if (_json_.containsKey("hourBuy"))
			hourBuy = _json_.getFloat("hourBuy");
		if (_json_.containsKey("hourGive"))
			hourGive = _json_.getFloat("hourGive");
	}

	public static SRequest_AddModifyBuy load(String str)
	{
		try
		{
			SRequest_AddModifyBuy obj = new SRequest_AddModifyBuy();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_AddModifyBuy load(JSONObject json)
	{
		try
		{
			SRequest_AddModifyBuy obj = new SRequest_AddModifyBuy();
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
			if (hourBuy != null)
				_json_.put("hourBuy", hourBuy);
			if (hourGive != null)
				_json_.put("hourGive", hourGive);
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SRequest_AddModifyBuy> loadList(JSONArray ja)
	{
		try
		{
			List<SRequest_AddModifyBuy> list = new ArrayList<SRequest_AddModifyBuy>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SRequest_AddModifyBuy item = SRequest_AddModifyBuy.load(jo);
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

	public static JSONArray saveList(List<SRequest_AddModifyBuy> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SRequest_AddModifyBuy item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Request_AddModifyBuy _proto_)
	{
		if (_proto_.hasUserId())
			userId = _proto_.getUserId();
		if (_proto_.hasMatterId())
			matterId = _proto_.getMatterId();
		if (_proto_.hasHourBuy())
			hourBuy = _proto_.getHourBuy();
		if (_proto_.hasHourGive())
			hourGive = _proto_.getHourGive();
	}

	public static SRequest_AddModifyBuy load(byte[] bytes)
	{
		try
		{
			SRequest_AddModifyBuy obj = new SRequest_AddModifyBuy();
			obj.parse(Request_AddModifyBuy.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_AddModifyBuy load(Request_AddModifyBuy proto)
	{
		try
		{
			SRequest_AddModifyBuy obj = new SRequest_AddModifyBuy();
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
	public Request_AddModifyBuy saveToProto()
	{
		Request_AddModifyBuy.Builder _builder_ = Request_AddModifyBuy.newBuilder();
		if (userId != null && !userId.equals(Request_AddModifyBuy.getDefaultInstance().getUserId()))
			_builder_.setUserId(userId);
		if (matterId != null && !matterId.equals(Request_AddModifyBuy.getDefaultInstance().getMatterId()))
			_builder_.setMatterId(matterId);
		if (hourBuy != null && !hourBuy.equals(Request_AddModifyBuy.getDefaultInstance().getHourBuy()))
			_builder_.setHourBuy(hourBuy);
		if (hourGive != null && !hourGive.equals(Request_AddModifyBuy.getDefaultInstance().getHourGive()))
			_builder_.setHourGive(hourGive);
		return _builder_.build();
	}
}
