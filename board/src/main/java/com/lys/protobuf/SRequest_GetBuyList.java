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
import com.lys.protobuf.ProtocolShop.Request_GetBuyList;

// ---------------------- 获取已购买列表 --------------------------
public class SRequest_GetBuyList extends SPTData<Request_GetBuyList>
{
	private static final SRequest_GetBuyList DefaultInstance = new SRequest_GetBuyList();

	public String userId = null;
	public String matterId = null;

	public static SRequest_GetBuyList create(String userId, String matterId)
	{
		SRequest_GetBuyList obj = new SRequest_GetBuyList();
		obj.userId = userId;
		obj.matterId = matterId;
		return obj;
	}

	public SRequest_GetBuyList clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SRequest_GetBuyList _other_)
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

	public static SRequest_GetBuyList load(String str)
	{
		try
		{
			SRequest_GetBuyList obj = new SRequest_GetBuyList();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_GetBuyList load(JSONObject json)
	{
		try
		{
			SRequest_GetBuyList obj = new SRequest_GetBuyList();
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

	public static List<SRequest_GetBuyList> loadList(JSONArray ja)
	{
		try
		{
			List<SRequest_GetBuyList> list = new ArrayList<SRequest_GetBuyList>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SRequest_GetBuyList item = SRequest_GetBuyList.load(jo);
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

	public static JSONArray saveList(List<SRequest_GetBuyList> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SRequest_GetBuyList item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Request_GetBuyList _proto_)
	{
		if (_proto_.hasUserId())
			userId = _proto_.getUserId();
		if (_proto_.hasMatterId())
			matterId = _proto_.getMatterId();
	}

	public static SRequest_GetBuyList load(byte[] bytes)
	{
		try
		{
			SRequest_GetBuyList obj = new SRequest_GetBuyList();
			obj.parse(Request_GetBuyList.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_GetBuyList load(Request_GetBuyList proto)
	{
		try
		{
			SRequest_GetBuyList obj = new SRequest_GetBuyList();
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
	public Request_GetBuyList saveToProto()
	{
		Request_GetBuyList.Builder _builder_ = Request_GetBuyList.newBuilder();
		if (userId != null && !userId.equals(Request_GetBuyList.getDefaultInstance().getUserId()))
			_builder_.setUserId(userId);
		if (matterId != null && !matterId.equals(Request_GetBuyList.getDefaultInstance().getMatterId()))
			_builder_.setMatterId(matterId);
		return _builder_.build();
	}
}
