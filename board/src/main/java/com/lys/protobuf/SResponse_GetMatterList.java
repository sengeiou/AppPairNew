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
import com.lys.protobuf.ProtocolShop.Response_GetMatterList;

public class SResponse_GetMatterList extends SPTData<Response_GetMatterList>
{
	private static final SResponse_GetMatterList DefaultInstance = new SResponse_GetMatterList();

	public List<SMatter> matters = new ArrayList<SMatter>();

	public static SResponse_GetMatterList create()
	{
		SResponse_GetMatterList obj = new SResponse_GetMatterList();
		return obj;
	}

	public SResponse_GetMatterList clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SResponse_GetMatterList _other_)
	{
		this.matters = _other_.matters;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("matters"))
			matters = SMatter.loadList(_json_.getJSONArray("matters"));
	}

	public static SResponse_GetMatterList load(String str)
	{
		try
		{
			SResponse_GetMatterList obj = new SResponse_GetMatterList();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_GetMatterList load(JSONObject json)
	{
		try
		{
			SResponse_GetMatterList obj = new SResponse_GetMatterList();
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
			if (matters != null)
				_json_.put("matters", SMatter.saveList(matters));
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SResponse_GetMatterList> loadList(JSONArray ja)
	{
		try
		{
			List<SResponse_GetMatterList> list = new ArrayList<SResponse_GetMatterList>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SResponse_GetMatterList item = SResponse_GetMatterList.load(jo);
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

	public static JSONArray saveList(List<SResponse_GetMatterList> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SResponse_GetMatterList item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Response_GetMatterList _proto_)
	{
		for (int i = 0; i < _proto_.getMattersCount(); i++)
			matters.add(SMatter.load(_proto_.getMatters(i)));
	}

	public static SResponse_GetMatterList load(byte[] bytes)
	{
		try
		{
			SResponse_GetMatterList obj = new SResponse_GetMatterList();
			obj.parse(Response_GetMatterList.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_GetMatterList load(Response_GetMatterList proto)
	{
		try
		{
			SResponse_GetMatterList obj = new SResponse_GetMatterList();
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
	public Response_GetMatterList saveToProto()
	{
		Response_GetMatterList.Builder _builder_ = Response_GetMatterList.newBuilder();
		if (matters != null)
			for (SMatter _value_ : matters)
				_builder_.addMatters(_value_.saveToProto());
		return _builder_.build();
	}
}
