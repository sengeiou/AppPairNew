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
import com.lys.protobuf.ProtocolPair.Request_ModifyPsw;

// ---------------------- 修改XX --------------------------
public class SRequest_ModifyPsw extends SPTData<Request_ModifyPsw>
{
	private static final SRequest_ModifyPsw DefaultInstance = new SRequest_ModifyPsw();

	public String userId = null;
	public String oldPsw = null;
	public String newPsw = null;

	public static SRequest_ModifyPsw create(String userId, String oldPsw, String newPsw)
	{
		SRequest_ModifyPsw obj = new SRequest_ModifyPsw();
		obj.userId = userId;
		obj.oldPsw = oldPsw;
		obj.newPsw = newPsw;
		return obj;
	}

	public SRequest_ModifyPsw clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SRequest_ModifyPsw _other_)
	{
		this.userId = _other_.userId;
		this.oldPsw = _other_.oldPsw;
		this.newPsw = _other_.newPsw;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("userId"))
			userId = _json_.getString("userId");
		if (_json_.containsKey("oldPsw"))
			oldPsw = _json_.getString("oldPsw");
		if (_json_.containsKey("newPsw"))
			newPsw = _json_.getString("newPsw");
	}

	public static SRequest_ModifyPsw load(String str)
	{
		try
		{
			SRequest_ModifyPsw obj = new SRequest_ModifyPsw();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_ModifyPsw load(JSONObject json)
	{
		try
		{
			SRequest_ModifyPsw obj = new SRequest_ModifyPsw();
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
			if (oldPsw != null)
				_json_.put("oldPsw", oldPsw);
			if (newPsw != null)
				_json_.put("newPsw", newPsw);
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SRequest_ModifyPsw> loadList(JSONArray ja)
	{
		try
		{
			List<SRequest_ModifyPsw> list = new ArrayList<SRequest_ModifyPsw>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SRequest_ModifyPsw item = SRequest_ModifyPsw.load(jo);
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

	public static JSONArray saveList(List<SRequest_ModifyPsw> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SRequest_ModifyPsw item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Request_ModifyPsw _proto_)
	{
		if (_proto_.hasUserId())
			userId = _proto_.getUserId();
		if (_proto_.hasOldPsw())
			oldPsw = _proto_.getOldPsw();
		if (_proto_.hasNewPsw())
			newPsw = _proto_.getNewPsw();
	}

	public static SRequest_ModifyPsw load(byte[] bytes)
	{
		try
		{
			SRequest_ModifyPsw obj = new SRequest_ModifyPsw();
			obj.parse(Request_ModifyPsw.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_ModifyPsw load(Request_ModifyPsw proto)
	{
		try
		{
			SRequest_ModifyPsw obj = new SRequest_ModifyPsw();
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
	public Request_ModifyPsw saveToProto()
	{
		Request_ModifyPsw.Builder _builder_ = Request_ModifyPsw.newBuilder();
		if (userId != null && !userId.equals(Request_ModifyPsw.getDefaultInstance().getUserId()))
			_builder_.setUserId(userId);
		if (oldPsw != null && !oldPsw.equals(Request_ModifyPsw.getDefaultInstance().getOldPsw()))
			_builder_.setOldPsw(oldPsw);
		if (newPsw != null && !newPsw.equals(Request_ModifyPsw.getDefaultInstance().getNewPsw()))
			_builder_.setNewPsw(newPsw);
		return _builder_.build();
	}
}
