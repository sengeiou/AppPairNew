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
import com.lys.protobuf.ProtocolPair.Request_UserPhoneCode;

// ---------------------- 获取验证码 --------------------------
public class SRequest_UserPhoneCode extends SPTData<Request_UserPhoneCode>
{
	private static final SRequest_UserPhoneCode DefaultInstance = new SRequest_UserPhoneCode();

	public String phone = null;
	public /*SPhoneCodeType*/ Integer type = Request_UserPhoneCode.getDefaultInstance().getType().getNumber();

	public static SRequest_UserPhoneCode create(String phone, Integer type)
	{
		SRequest_UserPhoneCode obj = new SRequest_UserPhoneCode();
		obj.phone = phone;
		obj.type = type;
		return obj;
	}

	public SRequest_UserPhoneCode clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SRequest_UserPhoneCode _other_)
	{
		this.phone = _other_.phone;
		this.type = _other_.type;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("phone"))
			phone = _json_.getString("phone");
		if (_json_.containsKey("type"))
			type = _json_.getInteger("type");
	}

	public static SRequest_UserPhoneCode load(String str)
	{
		try
		{
			SRequest_UserPhoneCode obj = new SRequest_UserPhoneCode();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_UserPhoneCode load(JSONObject json)
	{
		try
		{
			SRequest_UserPhoneCode obj = new SRequest_UserPhoneCode();
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
			if (type != null)
				_json_.put("type", type);
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SRequest_UserPhoneCode> loadList(JSONArray ja)
	{
		try
		{
			List<SRequest_UserPhoneCode> list = new ArrayList<SRequest_UserPhoneCode>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SRequest_UserPhoneCode item = SRequest_UserPhoneCode.load(jo);
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

	public static JSONArray saveList(List<SRequest_UserPhoneCode> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SRequest_UserPhoneCode item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Request_UserPhoneCode _proto_)
	{
		if (_proto_.hasPhone())
			phone = _proto_.getPhone();
		if (_proto_.hasType())
			type = _proto_.getType().getNumber();
	}

	public static SRequest_UserPhoneCode load(byte[] bytes)
	{
		try
		{
			SRequest_UserPhoneCode obj = new SRequest_UserPhoneCode();
			obj.parse(Request_UserPhoneCode.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_UserPhoneCode load(Request_UserPhoneCode proto)
	{
		try
		{
			SRequest_UserPhoneCode obj = new SRequest_UserPhoneCode();
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
	public Request_UserPhoneCode saveToProto()
	{
		Request_UserPhoneCode.Builder _builder_ = Request_UserPhoneCode.newBuilder();
		if (phone != null && !phone.equals(Request_UserPhoneCode.getDefaultInstance().getPhone()))
			_builder_.setPhone(phone);
		if (type != null && Request_UserPhoneCode.getDefaultInstance().getType().getNumber() != type)
			_builder_.setType(ProtocolPair.PhoneCodeType.valueOf(type));
		return _builder_.build();
	}
}
