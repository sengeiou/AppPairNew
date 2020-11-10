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
import com.lys.protobuf.ProtocolScore.Response_GetTeachList;

public class SResponse_GetTeachList extends SPTData<Response_GetTeachList>
{
	private static final SResponse_GetTeachList DefaultInstance = new SResponse_GetTeachList();

	public List<STeach> teachs = new ArrayList<STeach>();

	public static SResponse_GetTeachList create()
	{
		SResponse_GetTeachList obj = new SResponse_GetTeachList();
		return obj;
	}

	public SResponse_GetTeachList clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SResponse_GetTeachList _other_)
	{
		this.teachs = _other_.teachs;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("teachs"))
			teachs = STeach.loadList(_json_.getJSONArray("teachs"));
	}

	public static SResponse_GetTeachList load(String str)
	{
		try
		{
			SResponse_GetTeachList obj = new SResponse_GetTeachList();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_GetTeachList load(JSONObject json)
	{
		try
		{
			SResponse_GetTeachList obj = new SResponse_GetTeachList();
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
			if (teachs != null)
				_json_.put("teachs", STeach.saveList(teachs));
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SResponse_GetTeachList> loadList(JSONArray ja)
	{
		try
		{
			List<SResponse_GetTeachList> list = new ArrayList<SResponse_GetTeachList>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SResponse_GetTeachList item = SResponse_GetTeachList.load(jo);
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

	public static JSONArray saveList(List<SResponse_GetTeachList> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SResponse_GetTeachList item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Response_GetTeachList _proto_)
	{
		for (int i = 0; i < _proto_.getTeachsCount(); i++)
			teachs.add(STeach.load(_proto_.getTeachs(i)));
	}

	public static SResponse_GetTeachList load(byte[] bytes)
	{
		try
		{
			SResponse_GetTeachList obj = new SResponse_GetTeachList();
			obj.parse(Response_GetTeachList.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_GetTeachList load(Response_GetTeachList proto)
	{
		try
		{
			SResponse_GetTeachList obj = new SResponse_GetTeachList();
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
	public Response_GetTeachList saveToProto()
	{
		Response_GetTeachList.Builder _builder_ = Response_GetTeachList.newBuilder();
		if (teachs != null)
			for (STeach _value_ : teachs)
				_builder_.addTeachs(_value_.saveToProto());
		return _builder_.build();
	}
}
