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
import com.lys.protobuf.ProtocolPair.Response_UserReg;

public class SResponse_UserReg extends SPTData<Response_UserReg>
{
	private static final SResponse_UserReg DefaultInstance = new SResponse_UserReg();

	public String userId = null;
	public String psw = null;

	public static SResponse_UserReg create(String userId, String psw)
	{
		SResponse_UserReg obj = new SResponse_UserReg();
		obj.userId = userId;
		obj.psw = psw;
		return obj;
	}

	public SResponse_UserReg clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SResponse_UserReg _other_)
	{
		this.userId = _other_.userId;
		this.psw = _other_.psw;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("userId"))
			userId = _json_.getString("userId");
		if (_json_.containsKey("psw"))
			psw = _json_.getString("psw");
	}

	public static SResponse_UserReg load(String str)
	{
		try
		{
			SResponse_UserReg obj = new SResponse_UserReg();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_UserReg load(JSONObject json)
	{
		try
		{
			SResponse_UserReg obj = new SResponse_UserReg();
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
			if (psw != null)
				_json_.put("psw", psw);
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SResponse_UserReg> loadList(JSONArray ja)
	{
		try
		{
			List<SResponse_UserReg> list = new ArrayList<SResponse_UserReg>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SResponse_UserReg item = SResponse_UserReg.load(jo);
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

	public static JSONArray saveList(List<SResponse_UserReg> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SResponse_UserReg item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Response_UserReg _proto_)
	{
		if (_proto_.hasUserId())
			userId = _proto_.getUserId();
		if (_proto_.hasPsw())
			psw = _proto_.getPsw();
	}

	public static SResponse_UserReg load(byte[] bytes)
	{
		try
		{
			SResponse_UserReg obj = new SResponse_UserReg();
			obj.parse(Response_UserReg.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_UserReg load(Response_UserReg proto)
	{
		try
		{
			SResponse_UserReg obj = new SResponse_UserReg();
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
	public Response_UserReg saveToProto()
	{
		Response_UserReg.Builder _builder_ = Response_UserReg.newBuilder();
		if (userId != null && !userId.equals(Response_UserReg.getDefaultInstance().getUserId()))
			_builder_.setUserId(userId);
		if (psw != null && !psw.equals(Response_UserReg.getDefaultInstance().getPsw()))
			_builder_.setPsw(psw);
		return _builder_.build();
	}
}
