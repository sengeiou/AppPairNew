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
import com.lys.protobuf.ProtocolZhixue.Response_ZXAddTopic;

public class SResponse_ZXAddTopic extends SPTData<Response_ZXAddTopic>
{
	private static final SResponse_ZXAddTopic DefaultInstance = new SResponse_ZXAddTopic();

	public List<Integer> addIndexs = new ArrayList<Integer>();
	public List<Integer> repeatIndexs = new ArrayList<Integer>();

	public static SResponse_ZXAddTopic create()
	{
		SResponse_ZXAddTopic obj = new SResponse_ZXAddTopic();
		return obj;
	}

	public SResponse_ZXAddTopic clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SResponse_ZXAddTopic _other_)
	{
		this.addIndexs = _other_.addIndexs;
		this.repeatIndexs = _other_.repeatIndexs;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("addIndexs"))
			addIndexs = AppDataTool.loadIntegerList(AppDataTool.getJSONArray(_json_, "addIndexs"));
		if (_json_.containsKey("repeatIndexs"))
			repeatIndexs = AppDataTool.loadIntegerList(AppDataTool.getJSONArray(_json_, "repeatIndexs"));
	}

	public static SResponse_ZXAddTopic load(String str)
	{
		try
		{
			SResponse_ZXAddTopic obj = new SResponse_ZXAddTopic();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_ZXAddTopic load(JSONObject json)
	{
		try
		{
			SResponse_ZXAddTopic obj = new SResponse_ZXAddTopic();
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
			if (addIndexs != null)
				_json_.put("addIndexs", AppDataTool.saveIntegerList(addIndexs));
			if (repeatIndexs != null)
				_json_.put("repeatIndexs", AppDataTool.saveIntegerList(repeatIndexs));
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SResponse_ZXAddTopic> loadList(JSONArray ja)
	{
		try
		{
			List<SResponse_ZXAddTopic> list = new ArrayList<SResponse_ZXAddTopic>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SResponse_ZXAddTopic item = SResponse_ZXAddTopic.load(jo);
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

	public static JSONArray saveList(List<SResponse_ZXAddTopic> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SResponse_ZXAddTopic item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Response_ZXAddTopic _proto_)
	{
		for (int i = 0; i < _proto_.getAddIndexsCount(); i++)
			addIndexs.add(_proto_.getAddIndexs(i));
		for (int i = 0; i < _proto_.getRepeatIndexsCount(); i++)
			repeatIndexs.add(_proto_.getRepeatIndexs(i));
	}

	public static SResponse_ZXAddTopic load(byte[] bytes)
	{
		try
		{
			SResponse_ZXAddTopic obj = new SResponse_ZXAddTopic();
			obj.parse(Response_ZXAddTopic.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_ZXAddTopic load(Response_ZXAddTopic proto)
	{
		try
		{
			SResponse_ZXAddTopic obj = new SResponse_ZXAddTopic();
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
	public Response_ZXAddTopic saveToProto()
	{
		Response_ZXAddTopic.Builder _builder_ = Response_ZXAddTopic.newBuilder();
		if (addIndexs != null)
			for (Integer _value_ : addIndexs)
				_builder_.addAddIndexs(_value_);
		if (repeatIndexs != null)
			for (Integer _value_ : repeatIndexs)
				_builder_.addRepeatIndexs(_value_);
		return _builder_.build();
	}
}
