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
import com.lys.protobuf.ProtocolPair.Response_UserLogin;

public class SResponse_UserLogin extends SPTData<Response_UserLogin>
{
	private static final SResponse_UserLogin DefaultInstance = new SResponse_UserLogin();

	public SUser user = null;

	public static SResponse_UserLogin create(SUser user)
	{
		SResponse_UserLogin obj = new SResponse_UserLogin();
		obj.user = user;
		return obj;
	}

	public SResponse_UserLogin clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SResponse_UserLogin _other_)
	{
		this.user = _other_.user;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("user"))
			user = SUser.load(_json_.getJSONObject("user"));
	}

	public static SResponse_UserLogin load(String str)
	{
		try
		{
			SResponse_UserLogin obj = new SResponse_UserLogin();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_UserLogin load(JSONObject json)
	{
		try
		{
			SResponse_UserLogin obj = new SResponse_UserLogin();
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
			if (user != null)
				_json_.put("user", user.saveToJson());
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SResponse_UserLogin> loadList(JSONArray ja)
	{
		try
		{
			List<SResponse_UserLogin> list = new ArrayList<SResponse_UserLogin>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SResponse_UserLogin item = SResponse_UserLogin.load(jo);
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

	public static JSONArray saveList(List<SResponse_UserLogin> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SResponse_UserLogin item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Response_UserLogin _proto_)
	{
		if (_proto_.hasUser())
			user = SUser.load(_proto_.getUser());
	}

	public static SResponse_UserLogin load(byte[] bytes)
	{
		try
		{
			SResponse_UserLogin obj = new SResponse_UserLogin();
			obj.parse(Response_UserLogin.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_UserLogin load(Response_UserLogin proto)
	{
		try
		{
			SResponse_UserLogin obj = new SResponse_UserLogin();
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
	public Response_UserLogin saveToProto()
	{
		Response_UserLogin.Builder _builder_ = Response_UserLogin.newBuilder();
		if (user != null)
			_builder_.setUser(user.saveToProto());
		return _builder_.build();
	}
}
