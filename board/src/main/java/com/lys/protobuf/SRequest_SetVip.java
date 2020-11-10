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
import com.lys.protobuf.ProtocolPair.Request_SetVip;

// ---------------------- xxxxxx --------------------------
public class SRequest_SetVip extends SPTData<Request_SetVip>
{
	private static final SRequest_SetVip DefaultInstance = new SRequest_SetVip();

	public String userId = null;
	public Integer vipLevel = 0;
	public Long vipTime = 0L;

	public static SRequest_SetVip create(String userId, Integer vipLevel, Long vipTime)
	{
		SRequest_SetVip obj = new SRequest_SetVip();
		obj.userId = userId;
		obj.vipLevel = vipLevel;
		obj.vipTime = vipTime;
		return obj;
	}

	public SRequest_SetVip clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SRequest_SetVip _other_)
	{
		this.userId = _other_.userId;
		this.vipLevel = _other_.vipLevel;
		this.vipTime = _other_.vipTime;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("userId"))
			userId = _json_.getString("userId");
		if (_json_.containsKey("vipLevel"))
			vipLevel = _json_.getInteger("vipLevel");
		if (_json_.containsKey("vipTime"))
			vipTime = _json_.getLong("vipTime");
	}

	public static SRequest_SetVip load(String str)
	{
		try
		{
			SRequest_SetVip obj = new SRequest_SetVip();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_SetVip load(JSONObject json)
	{
		try
		{
			SRequest_SetVip obj = new SRequest_SetVip();
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
			if (vipLevel != null)
				_json_.put("vipLevel", vipLevel);
			if (vipTime != null)
				_json_.put("vipTime", String.valueOf(vipTime));
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SRequest_SetVip> loadList(JSONArray ja)
	{
		try
		{
			List<SRequest_SetVip> list = new ArrayList<SRequest_SetVip>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SRequest_SetVip item = SRequest_SetVip.load(jo);
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

	public static JSONArray saveList(List<SRequest_SetVip> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SRequest_SetVip item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Request_SetVip _proto_)
	{
		if (_proto_.hasUserId())
			userId = _proto_.getUserId();
		if (_proto_.hasVipLevel())
			vipLevel = _proto_.getVipLevel();
		if (_proto_.hasVipTime())
			vipTime = _proto_.getVipTime();
	}

	public static SRequest_SetVip load(byte[] bytes)
	{
		try
		{
			SRequest_SetVip obj = new SRequest_SetVip();
			obj.parse(Request_SetVip.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_SetVip load(Request_SetVip proto)
	{
		try
		{
			SRequest_SetVip obj = new SRequest_SetVip();
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
	public Request_SetVip saveToProto()
	{
		Request_SetVip.Builder _builder_ = Request_SetVip.newBuilder();
		if (userId != null && !userId.equals(Request_SetVip.getDefaultInstance().getUserId()))
			_builder_.setUserId(userId);
		if (vipLevel != null && !vipLevel.equals(Request_SetVip.getDefaultInstance().getVipLevel()))
			_builder_.setVipLevel(vipLevel);
		if (vipTime != null && !vipTime.equals(Request_SetVip.getDefaultInstance().getVipTime()))
			_builder_.setVipTime(vipTime);
		return _builder_.build();
	}
}
