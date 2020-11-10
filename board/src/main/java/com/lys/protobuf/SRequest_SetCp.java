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
import com.lys.protobuf.ProtocolPair.Request_SetCp;

// ---------------------- xxxxxx --------------------------
public class SRequest_SetCp extends SPTData<Request_SetCp>
{
	private static final SRequest_SetCp DefaultInstance = new SRequest_SetCp();

	public String userId = null;
	public String cpId = null;

	public static SRequest_SetCp create(String userId, String cpId)
	{
		SRequest_SetCp obj = new SRequest_SetCp();
		obj.userId = userId;
		obj.cpId = cpId;
		return obj;
	}

	public SRequest_SetCp clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SRequest_SetCp _other_)
	{
		this.userId = _other_.userId;
		this.cpId = _other_.cpId;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("userId"))
			userId = _json_.getString("userId");
		if (_json_.containsKey("cpId"))
			cpId = _json_.getString("cpId");
	}

	public static SRequest_SetCp load(String str)
	{
		try
		{
			SRequest_SetCp obj = new SRequest_SetCp();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_SetCp load(JSONObject json)
	{
		try
		{
			SRequest_SetCp obj = new SRequest_SetCp();
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
			if (cpId != null)
				_json_.put("cpId", cpId);
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SRequest_SetCp> loadList(JSONArray ja)
	{
		try
		{
			List<SRequest_SetCp> list = new ArrayList<SRequest_SetCp>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SRequest_SetCp item = SRequest_SetCp.load(jo);
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

	public static JSONArray saveList(List<SRequest_SetCp> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SRequest_SetCp item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Request_SetCp _proto_)
	{
		if (_proto_.hasUserId())
			userId = _proto_.getUserId();
		if (_proto_.hasCpId())
			cpId = _proto_.getCpId();
	}

	public static SRequest_SetCp load(byte[] bytes)
	{
		try
		{
			SRequest_SetCp obj = new SRequest_SetCp();
			obj.parse(Request_SetCp.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_SetCp load(Request_SetCp proto)
	{
		try
		{
			SRequest_SetCp obj = new SRequest_SetCp();
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
	public Request_SetCp saveToProto()
	{
		Request_SetCp.Builder _builder_ = Request_SetCp.newBuilder();
		if (userId != null && !userId.equals(Request_SetCp.getDefaultInstance().getUserId()))
			_builder_.setUserId(userId);
		if (cpId != null && !cpId.equals(Request_SetCp.getDefaultInstance().getCpId()))
			_builder_.setCpId(cpId);
		return _builder_.build();
	}
}
