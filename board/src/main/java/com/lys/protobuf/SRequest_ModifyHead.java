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
import com.lys.protobuf.ProtocolPair.Request_ModifyHead;

// ---------------------- 修改头像 --------------------------
public class SRequest_ModifyHead extends SPTData<Request_ModifyHead>
{
	private static final SRequest_ModifyHead DefaultInstance = new SRequest_ModifyHead();

	public String userId = null;
	public String head = null;

	public static SRequest_ModifyHead create(String userId, String head)
	{
		SRequest_ModifyHead obj = new SRequest_ModifyHead();
		obj.userId = userId;
		obj.head = head;
		return obj;
	}

	public SRequest_ModifyHead clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SRequest_ModifyHead _other_)
	{
		this.userId = _other_.userId;
		this.head = _other_.head;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("userId"))
			userId = _json_.getString("userId");
		if (_json_.containsKey("head"))
			head = _json_.getString("head");
	}

	public static SRequest_ModifyHead load(String str)
	{
		try
		{
			SRequest_ModifyHead obj = new SRequest_ModifyHead();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_ModifyHead load(JSONObject json)
	{
		try
		{
			SRequest_ModifyHead obj = new SRequest_ModifyHead();
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
			if (head != null)
				_json_.put("head", head);
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SRequest_ModifyHead> loadList(JSONArray ja)
	{
		try
		{
			List<SRequest_ModifyHead> list = new ArrayList<SRequest_ModifyHead>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SRequest_ModifyHead item = SRequest_ModifyHead.load(jo);
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

	public static JSONArray saveList(List<SRequest_ModifyHead> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SRequest_ModifyHead item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Request_ModifyHead _proto_)
	{
		if (_proto_.hasUserId())
			userId = _proto_.getUserId();
		if (_proto_.hasHead())
			head = _proto_.getHead();
	}

	public static SRequest_ModifyHead load(byte[] bytes)
	{
		try
		{
			SRequest_ModifyHead obj = new SRequest_ModifyHead();
			obj.parse(Request_ModifyHead.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_ModifyHead load(Request_ModifyHead proto)
	{
		try
		{
			SRequest_ModifyHead obj = new SRequest_ModifyHead();
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
	public Request_ModifyHead saveToProto()
	{
		Request_ModifyHead.Builder _builder_ = Request_ModifyHead.newBuilder();
		if (userId != null && !userId.equals(Request_ModifyHead.getDefaultInstance().getUserId()))
			_builder_.setUserId(userId);
		if (head != null && !head.equals(Request_ModifyHead.getDefaultInstance().getHead()))
			_builder_.setHead(head);
		return _builder_.build();
	}
}
