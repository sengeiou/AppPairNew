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
import com.lys.protobuf.ProtocolLive.Response_LiveGetList;

public class SResponse_LiveGetList extends SPTData<Response_LiveGetList>
{
	private static final SResponse_LiveGetList DefaultInstance = new SResponse_LiveGetList();

	public List<SLiveTask> lives = new ArrayList<SLiveTask>();

	public static SResponse_LiveGetList create()
	{
		SResponse_LiveGetList obj = new SResponse_LiveGetList();
		return obj;
	}

	public SResponse_LiveGetList clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SResponse_LiveGetList _other_)
	{
		this.lives = _other_.lives;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("lives"))
			lives = SLiveTask.loadList(_json_.getJSONArray("lives"));
	}

	public static SResponse_LiveGetList load(String str)
	{
		try
		{
			SResponse_LiveGetList obj = new SResponse_LiveGetList();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_LiveGetList load(JSONObject json)
	{
		try
		{
			SResponse_LiveGetList obj = new SResponse_LiveGetList();
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

	public static List<SResponse_LiveGetList> loadList(JSONArray ja)
	{
		try
		{
			List<SResponse_LiveGetList> list = new ArrayList<SResponse_LiveGetList>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SResponse_LiveGetList item = SResponse_LiveGetList.load(jo);
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

	public static JSONArray saveList(List<SResponse_LiveGetList> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SResponse_LiveGetList item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Response_LiveGetList _proto_)
	{
		for (int i = 0; i < _proto_.getLivesCount(); i++)
			lives.add(SLiveTask.load(_proto_.getLives(i)));
	}

	public static SResponse_LiveGetList load(byte[] bytes)
	{
		try
		{
			SResponse_LiveGetList obj = new SResponse_LiveGetList();
			obj.parse(Response_LiveGetList.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_LiveGetList load(Response_LiveGetList proto)
	{
		try
		{
			SResponse_LiveGetList obj = new SResponse_LiveGetList();
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
	public Response_LiveGetList saveToProto()
	{
		Response_LiveGetList.Builder _builder_ = Response_LiveGetList.newBuilder();
		if (lives != null)
			for (SLiveTask _value_ : lives)
				_builder_.addLives(_value_.saveToProto());
		return _builder_.build();
	}
}
