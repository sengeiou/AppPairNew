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
import com.lys.protobuf.ProtocolPair.Request_ModifySex;

// ---------------------- 修改XX --------------------------
public class SRequest_ModifySex extends SPTData<Request_ModifySex>
{
	private static final SRequest_ModifySex DefaultInstance = new SRequest_ModifySex();

	public String userId = null;
	public /*SSex*/ Integer sex = Request_ModifySex.getDefaultInstance().getSex().getNumber();

	public static SRequest_ModifySex create(String userId, Integer sex)
	{
		SRequest_ModifySex obj = new SRequest_ModifySex();
		obj.userId = userId;
		obj.sex = sex;
		return obj;
	}

	public SRequest_ModifySex clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SRequest_ModifySex _other_)
	{
		this.userId = _other_.userId;
		this.sex = _other_.sex;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("userId"))
			userId = _json_.getString("userId");
		if (_json_.containsKey("sex"))
			sex = _json_.getInteger("sex");
	}

	public static SRequest_ModifySex load(String str)
	{
		try
		{
			SRequest_ModifySex obj = new SRequest_ModifySex();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_ModifySex load(JSONObject json)
	{
		try
		{
			SRequest_ModifySex obj = new SRequest_ModifySex();
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
			if (sex != null)
				_json_.put("sex", sex);
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SRequest_ModifySex> loadList(JSONArray ja)
	{
		try
		{
			List<SRequest_ModifySex> list = new ArrayList<SRequest_ModifySex>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SRequest_ModifySex item = SRequest_ModifySex.load(jo);
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

	public static JSONArray saveList(List<SRequest_ModifySex> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SRequest_ModifySex item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Request_ModifySex _proto_)
	{
		if (_proto_.hasUserId())
			userId = _proto_.getUserId();
		if (_proto_.hasSex())
			sex = _proto_.getSex().getNumber();
	}

	public static SRequest_ModifySex load(byte[] bytes)
	{
		try
		{
			SRequest_ModifySex obj = new SRequest_ModifySex();
			obj.parse(Request_ModifySex.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_ModifySex load(Request_ModifySex proto)
	{
		try
		{
			SRequest_ModifySex obj = new SRequest_ModifySex();
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
	public Request_ModifySex saveToProto()
	{
		Request_ModifySex.Builder _builder_ = Request_ModifySex.newBuilder();
		if (userId != null && !userId.equals(Request_ModifySex.getDefaultInstance().getUserId()))
			_builder_.setUserId(userId);
		if (sex != null && Request_ModifySex.getDefaultInstance().getSex().getNumber() != sex)
			_builder_.setSex(ProtocolPair.Sex.valueOf(sex));
		return _builder_.build();
	}
}
