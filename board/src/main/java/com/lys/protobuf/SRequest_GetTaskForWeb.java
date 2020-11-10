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
import com.lys.protobuf.ProtocolPair.Request_GetTaskForWeb;

// ---------------------- 获取任务ForWeb --------------------------
public class SRequest_GetTaskForWeb extends SPTData<Request_GetTaskForWeb>
{
	private static final SRequest_GetTaskForWeb DefaultInstance = new SRequest_GetTaskForWeb();

	public String id = null;
	public String page = null;

	public static SRequest_GetTaskForWeb create(String id, String page)
	{
		SRequest_GetTaskForWeb obj = new SRequest_GetTaskForWeb();
		obj.id = id;
		obj.page = page;
		return obj;
	}

	public SRequest_GetTaskForWeb clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SRequest_GetTaskForWeb _other_)
	{
		this.id = _other_.id;
		this.page = _other_.page;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("id"))
			id = _json_.getString("id");
		if (_json_.containsKey("page"))
			page = _json_.getString("page");
	}

	public static SRequest_GetTaskForWeb load(String str)
	{
		try
		{
			SRequest_GetTaskForWeb obj = new SRequest_GetTaskForWeb();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_GetTaskForWeb load(JSONObject json)
	{
		try
		{
			SRequest_GetTaskForWeb obj = new SRequest_GetTaskForWeb();
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
			if (id != null)
				_json_.put("id", id);
			if (page != null)
				_json_.put("page", page);
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SRequest_GetTaskForWeb> loadList(JSONArray ja)
	{
		try
		{
			List<SRequest_GetTaskForWeb> list = new ArrayList<SRequest_GetTaskForWeb>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SRequest_GetTaskForWeb item = SRequest_GetTaskForWeb.load(jo);
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

	public static JSONArray saveList(List<SRequest_GetTaskForWeb> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SRequest_GetTaskForWeb item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Request_GetTaskForWeb _proto_)
	{
		if (_proto_.hasId())
			id = _proto_.getId();
		if (_proto_.hasPage())
			page = _proto_.getPage();
	}

	public static SRequest_GetTaskForWeb load(byte[] bytes)
	{
		try
		{
			SRequest_GetTaskForWeb obj = new SRequest_GetTaskForWeb();
			obj.parse(Request_GetTaskForWeb.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_GetTaskForWeb load(Request_GetTaskForWeb proto)
	{
		try
		{
			SRequest_GetTaskForWeb obj = new SRequest_GetTaskForWeb();
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
	public Request_GetTaskForWeb saveToProto()
	{
		Request_GetTaskForWeb.Builder _builder_ = Request_GetTaskForWeb.newBuilder();
		if (id != null && !id.equals(Request_GetTaskForWeb.getDefaultInstance().getId()))
			_builder_.setId(id);
		if (page != null && !page.equals(Request_GetTaskForWeb.getDefaultInstance().getPage()))
			_builder_.setPage(page);
		return _builder_.build();
	}
}
