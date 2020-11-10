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
import com.lys.protobuf.ProtocolPair2.Response_TopicRecordDelete;

public class SResponse_TopicRecordDelete extends SPTData<Response_TopicRecordDelete>
{
	private static final SResponse_TopicRecordDelete DefaultInstance = new SResponse_TopicRecordDelete();


	public static SResponse_TopicRecordDelete create()
	{
		SResponse_TopicRecordDelete obj = new SResponse_TopicRecordDelete();
		return obj;
	}

	public SResponse_TopicRecordDelete clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SResponse_TopicRecordDelete _other_)
	{
	}

	@Override
	public void parse(JSONObject _json_)
	{
	}

	public static SResponse_TopicRecordDelete load(String str)
	{
		try
		{
			SResponse_TopicRecordDelete obj = new SResponse_TopicRecordDelete();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_TopicRecordDelete load(JSONObject json)
	{
		try
		{
			SResponse_TopicRecordDelete obj = new SResponse_TopicRecordDelete();
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
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SResponse_TopicRecordDelete> loadList(JSONArray ja)
	{
		try
		{
			List<SResponse_TopicRecordDelete> list = new ArrayList<SResponse_TopicRecordDelete>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SResponse_TopicRecordDelete item = SResponse_TopicRecordDelete.load(jo);
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

	public static JSONArray saveList(List<SResponse_TopicRecordDelete> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SResponse_TopicRecordDelete item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Response_TopicRecordDelete _proto_)
	{
	}

	public static SResponse_TopicRecordDelete load(byte[] bytes)
	{
		try
		{
			SResponse_TopicRecordDelete obj = new SResponse_TopicRecordDelete();
			obj.parse(Response_TopicRecordDelete.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_TopicRecordDelete load(Response_TopicRecordDelete proto)
	{
		try
		{
			SResponse_TopicRecordDelete obj = new SResponse_TopicRecordDelete();
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
	public Response_TopicRecordDelete saveToProto()
	{
		Response_TopicRecordDelete.Builder _builder_ = Response_TopicRecordDelete.newBuilder();
		return _builder_.build();
	}
}
