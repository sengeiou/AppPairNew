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
import com.lys.protobuf.ProtocolTeach.Response_TeachGetList;

public class SResponse_TeachGetList extends SPTData<Response_TeachGetList>
{
	private static final SResponse_TeachGetList DefaultInstance = new SResponse_TeachGetList();

	public List<STeachRecord> teachRecords = new ArrayList<STeachRecord>();

	public static SResponse_TeachGetList create()
	{
		SResponse_TeachGetList obj = new SResponse_TeachGetList();
		return obj;
	}

	public SResponse_TeachGetList clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SResponse_TeachGetList _other_)
	{
		this.teachRecords = _other_.teachRecords;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("teachRecords"))
			teachRecords = STeachRecord.loadList(_json_.getJSONArray("teachRecords"));
	}

	public static SResponse_TeachGetList load(String str)
	{
		try
		{
			SResponse_TeachGetList obj = new SResponse_TeachGetList();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_TeachGetList load(JSONObject json)
	{
		try
		{
			SResponse_TeachGetList obj = new SResponse_TeachGetList();
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
			if (teachRecords != null)
				_json_.put("teachRecords", STeachRecord.saveList(teachRecords));
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SResponse_TeachGetList> loadList(JSONArray ja)
	{
		try
		{
			List<SResponse_TeachGetList> list = new ArrayList<SResponse_TeachGetList>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SResponse_TeachGetList item = SResponse_TeachGetList.load(jo);
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

	public static JSONArray saveList(List<SResponse_TeachGetList> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SResponse_TeachGetList item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Response_TeachGetList _proto_)
	{
		for (int i = 0; i < _proto_.getTeachRecordsCount(); i++)
			teachRecords.add(STeachRecord.load(_proto_.getTeachRecords(i)));
	}

	public static SResponse_TeachGetList load(byte[] bytes)
	{
		try
		{
			SResponse_TeachGetList obj = new SResponse_TeachGetList();
			obj.parse(Response_TeachGetList.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_TeachGetList load(Response_TeachGetList proto)
	{
		try
		{
			SResponse_TeachGetList obj = new SResponse_TeachGetList();
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
	public Response_TeachGetList saveToProto()
	{
		Response_TeachGetList.Builder _builder_ = Response_TeachGetList.newBuilder();
		if (teachRecords != null)
			for (STeachRecord _value_ : teachRecords)
				_builder_.addTeachRecords(_value_.saveToProto());
		return _builder_.build();
	}
}
