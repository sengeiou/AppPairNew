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
import com.lys.protobuf.ProtocolLive.Response_LiveGetAll;

public class SResponse_LiveGetAll extends SPTData<Response_LiveGetAll>
{
	private static final SResponse_LiveGetAll DefaultInstance = new SResponse_LiveGetAll();

	public List<SLiveTask> lives = new ArrayList<SLiveTask>();

	public static SResponse_LiveGetAll create()
	{
		SResponse_LiveGetAll obj = new SResponse_LiveGetAll();
		return obj;
	}

	public SResponse_LiveGetAll clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SResponse_LiveGetAll _other_)
	{
		this.lives = _other_.lives;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("lives"))
			lives = SLiveTask.loadList(_json_.getJSONArray("lives"));
	}

	public static SResponse_LiveGetAll load(String str)
	{
		try
		{
			SResponse_LiveGetAll obj = new SResponse_LiveGetAll();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_LiveGetAll load(JSONObject json)
	{
		try
		{
			SResponse_LiveGetAll obj = new SResponse_LiveGetAll();
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
			if (lives != null)
				_json_.put("lives", SLiveTask.saveList(lives));
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SResponse_LiveGetAll> loadList(JSONArray ja)
	{
		try
		{
			List<SResponse_LiveGetAll> list = new ArrayList<SResponse_LiveGetAll>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SResponse_LiveGetAll item = SResponse_LiveGetAll.load(jo);
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

	public static JSONArray saveList(List<SResponse_LiveGetAll> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SResponse_LiveGetAll item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Response_LiveGetAll _proto_)
	{
		for (int i = 0; i < _proto_.getLivesCount(); i++)
			lives.add(SLiveTask.load(_proto_.getLives(i)));
	}

	public static SResponse_LiveGetAll load(byte[] bytes)
	{
		try
		{
			SResponse_LiveGetAll obj = new SResponse_LiveGetAll();
			obj.parse(Response_LiveGetAll.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_LiveGetAll load(Response_LiveGetAll proto)
	{
		try
		{
			SResponse_LiveGetAll obj = new SResponse_LiveGetAll();
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
	public Response_LiveGetAll saveToProto()
	{
		Response_LiveGetAll.Builder _builder_ = Response_LiveGetAll.newBuilder();
		if (lives != null)
			for (SLiveTask _value_ : lives)
				_builder_.addLives(_value_.saveToProto());
		return _builder_.build();
	}
}
