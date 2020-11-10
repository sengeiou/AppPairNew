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
import com.lys.protobuf.ProtocolScore.Request_GetGoodsList;

// ---------------------- 获取物料列表 --------------------------
public class SRequest_GetGoodsList extends SPTData<Request_GetGoodsList>
{
	private static final SRequest_GetGoodsList DefaultInstance = new SRequest_GetGoodsList();

	public Boolean containInvalid = false; // 是否包含无效

	public static SRequest_GetGoodsList create(Boolean containInvalid)
	{
		SRequest_GetGoodsList obj = new SRequest_GetGoodsList();
		obj.containInvalid = containInvalid;
		return obj;
	}

	public SRequest_GetGoodsList clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SRequest_GetGoodsList _other_)
	{
		this.containInvalid = _other_.containInvalid;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("containInvalid"))
			containInvalid = _json_.getBoolean("containInvalid");
	}

	public static SRequest_GetGoodsList load(String str)
	{
		try
		{
			SRequest_GetGoodsList obj = new SRequest_GetGoodsList();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_GetGoodsList load(JSONObject json)
	{
		try
		{
			SRequest_GetGoodsList obj = new SRequest_GetGoodsList();
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
			if (containInvalid != null)
				_json_.put("containInvalid", containInvalid);
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SRequest_GetGoodsList> loadList(JSONArray ja)
	{
		try
		{
			List<SRequest_GetGoodsList> list = new ArrayList<SRequest_GetGoodsList>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SRequest_GetGoodsList item = SRequest_GetGoodsList.load(jo);
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

	public static JSONArray saveList(List<SRequest_GetGoodsList> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SRequest_GetGoodsList item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Request_GetGoodsList _proto_)
	{
		if (_proto_.hasContainInvalid())
			containInvalid = _proto_.getContainInvalid();
	}

	public static SRequest_GetGoodsList load(byte[] bytes)
	{
		try
		{
			SRequest_GetGoodsList obj = new SRequest_GetGoodsList();
			obj.parse(Request_GetGoodsList.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_GetGoodsList load(Request_GetGoodsList proto)
	{
		try
		{
			SRequest_GetGoodsList obj = new SRequest_GetGoodsList();
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
	public Request_GetGoodsList saveToProto()
	{
		Request_GetGoodsList.Builder _builder_ = Request_GetGoodsList.newBuilder();
		if (containInvalid != null && !containInvalid.equals(Request_GetGoodsList.getDefaultInstance().getContainInvalid()))
			_builder_.setContainInvalid(containInvalid);
		return _builder_.build();
	}
}
