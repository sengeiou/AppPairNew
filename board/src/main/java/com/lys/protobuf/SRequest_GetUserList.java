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
import com.lys.protobuf.ProtocolPair.Request_GetUserList;

// ---------------------- 获取用户列表 --------------------------
public class SRequest_GetUserList extends SPTData<Request_GetUserList>
{
	private static final SRequest_GetUserList DefaultInstance = new SRequest_GetUserList();

	public /*SUserType*/ Integer userType = Request_GetUserList.getDefaultInstance().getUserType().getNumber();

	public static SRequest_GetUserList create(Integer userType)
	{
		SRequest_GetUserList obj = new SRequest_GetUserList();
		obj.userType = userType;
		return obj;
	}

	public SRequest_GetUserList clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SRequest_GetUserList _other_)
	{
		this.userType = _other_.userType;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("userType"))
			userType = _json_.getInteger("userType");
	}

	public static SRequest_GetUserList load(String str)
	{
		try
		{
			SRequest_GetUserList obj = new SRequest_GetUserList();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_GetUserList load(JSONObject json)
	{
		try
		{
			SRequest_GetUserList obj = new SRequest_GetUserList();
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
			if (userType != null)
				_json_.put("userType", userType);
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SRequest_GetUserList> loadList(JSONArray ja)
	{
		try
		{
			List<SRequest_GetUserList> list = new ArrayList<SRequest_GetUserList>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SRequest_GetUserList item = SRequest_GetUserList.load(jo);
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

	public static JSONArray saveList(List<SRequest_GetUserList> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SRequest_GetUserList item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Request_GetUserList _proto_)
	{
		if (_proto_.hasUserType())
			userType = _proto_.getUserType().getNumber();
	}

	public static SRequest_GetUserList load(byte[] bytes)
	{
		try
		{
			SRequest_GetUserList obj = new SRequest_GetUserList();
			obj.parse(Request_GetUserList.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_GetUserList load(Request_GetUserList proto)
	{
		try
		{
			SRequest_GetUserList obj = new SRequest_GetUserList();
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
	public Request_GetUserList saveToProto()
	{
		Request_GetUserList.Builder _builder_ = Request_GetUserList.newBuilder();
		if (userType != null && Request_GetUserList.getDefaultInstance().getUserType().getNumber() != userType)
			_builder_.setUserType(ProtocolPair.UserType.valueOf(userType));
		return _builder_.build();
	}
}
