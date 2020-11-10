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
import com.lys.protobuf.ProtocolPair.Request_UserReg;

// ---------------------- 注册 --------------------------
public class SRequest_UserReg extends SPTData<Request_UserReg>
{
	private static final SRequest_UserReg DefaultInstance = new SRequest_UserReg();

	public String phone = null;
	public String code = null;

	public static SRequest_UserReg create(String phone, String code)
	{
		SRequest_UserReg obj = new SRequest_UserReg();
		obj.phone = phone;
		obj.code = code;
		return obj;
	}

	public SRequest_UserReg clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SRequest_UserReg _other_)
	{
		this.phone = _other_.phone;
		this.code = _other_.code;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("phone"))
			phone = _json_.getString("phone");
		if (_json_.containsKey("code"))
			code = _json_.getString("code");
	}

	public static SRequest_UserReg load(String str)
	{
		try
		{
			SRequest_UserReg obj = new SRequest_UserReg();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_UserReg load(JSONObject json)
	{
		try
		{
			SRequest_UserReg obj = new SRequest_UserReg();
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
			if (phone != null)
				_json_.put("phone", phone);
			if (code != null)
				_json_.put("code", code);
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SRequest_UserReg> loadList(JSONArray ja)
	{
		try
		{
			List<SRequest_UserReg> list = new ArrayList<SRequest_UserReg>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SRequest_UserReg item = SRequest_UserReg.load(jo);
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

	public static JSONArray saveList(List<SRequest_UserReg> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SRequest_UserReg item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Request_UserReg _proto_)
	{
		if (_proto_.hasPhone())
			phone = _proto_.getPhone();
		if (_proto_.hasCode())
			code = _proto_.getCode();
	}

	public static SRequest_UserReg load(byte[] bytes)
	{
		try
		{
			SRequest_UserReg obj = new SRequest_UserReg();
			obj.parse(Request_UserReg.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_UserReg load(Request_UserReg proto)
	{
		try
		{
			SRequest_UserReg obj = new SRequest_UserReg();
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
	public Request_UserReg saveToProto()
	{
		Request_UserReg.Builder _builder_ = Request_UserReg.newBuilder();
		if (phone != null && !phone.equals(Request_UserReg.getDefaultInstance().getPhone()))
			_builder_.setPhone(phone);
		if (code != null && !code.equals(Request_UserReg.getDefaultInstance().getCode()))
			_builder_.setCode(code);
		return _builder_.build();
	}
}
