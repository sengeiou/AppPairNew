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
import com.lys.protobuf.ProtocolApp.Response_GetAppInfoList;

public class SResponse_GetAppInfoList extends SPTData<Response_GetAppInfoList>
{
	private static final SResponse_GetAppInfoList DefaultInstance = new SResponse_GetAppInfoList();

	public List<SApp> apps = new ArrayList<SApp>();

	public static SResponse_GetAppInfoList create()
	{
		SResponse_GetAppInfoList obj = new SResponse_GetAppInfoList();
		return obj;
	}

	public SResponse_GetAppInfoList clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SResponse_GetAppInfoList _other_)
	{
		this.apps = _other_.apps;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("apps"))
			apps = SApp.loadList(_json_.getJSONArray("apps"));
	}

	public static SResponse_GetAppInfoList load(String str)
	{
		try
		{
			SResponse_GetAppInfoList obj = new SResponse_GetAppInfoList();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_GetAppInfoList load(JSONObject json)
	{
		try
		{
			SResponse_GetAppInfoList obj = new SResponse_GetAppInfoList();
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
			if (apps != null)
				_json_.put("apps", SApp.saveList(apps));
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SResponse_GetAppInfoList> loadList(JSONArray ja)
	{
		try
		{
			List<SResponse_GetAppInfoList> list = new ArrayList<SResponse_GetAppInfoList>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SResponse_GetAppInfoList item = SResponse_GetAppInfoList.load(jo);
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

	public static JSONArray saveList(List<SResponse_GetAppInfoList> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SResponse_GetAppInfoList item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Response_GetAppInfoList _proto_)
	{
		for (int i = 0; i < _proto_.getAppsCount(); i++)
			apps.add(SApp.load(_proto_.getApps(i)));
	}

	public static SResponse_GetAppInfoList load(byte[] bytes)
	{
		try
		{
			SResponse_GetAppInfoList obj = new SResponse_GetAppInfoList();
			obj.parse(Response_GetAppInfoList.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_GetAppInfoList load(Response_GetAppInfoList proto)
	{
		try
		{
			SResponse_GetAppInfoList obj = new SResponse_GetAppInfoList();
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
	public Response_GetAppInfoList saveToProto()
	{
		Response_GetAppInfoList.Builder _builder_ = Response_GetAppInfoList.newBuilder();
		if (apps != null)
			for (SApp _value_ : apps)
				_builder_.addApps(_value_.saveToProto());
		return _builder_.build();
	}
}
