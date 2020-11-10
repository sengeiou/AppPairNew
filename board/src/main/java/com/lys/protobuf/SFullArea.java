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
import com.lys.protobuf.ProtocolBoard.FullArea;

public class SFullArea extends SPTData<FullArea>
{
	private static final SFullArea DefaultInstance = new SFullArea();

	public List<SPoint> points = new ArrayList<SPoint>();

	public static SFullArea create()
	{
		SFullArea obj = new SFullArea();
		return obj;
	}

	public SFullArea clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SFullArea _other_)
	{
		this.points = _other_.points;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("points"))
			points = SPoint.loadList(_json_.getJSONArray("points"));
	}

	public static SFullArea load(String str)
	{
		try
		{
			SFullArea obj = new SFullArea();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SFullArea load(JSONObject json)
	{
		try
		{
			SFullArea obj = new SFullArea();
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
			if (points != null)
				_json_.put("points", SPoint.saveList(points));
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SFullArea> loadList(JSONArray ja)
	{
		try
		{
			List<SFullArea> list = new ArrayList<SFullArea>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SFullArea item = SFullArea.load(jo);
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

	public static JSONArray saveList(List<SFullArea> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SFullArea item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(FullArea _proto_)
	{
		for (int i = 0; i < _proto_.getPointsCount(); i++)
			points.add(SPoint.load(_proto_.getPoints(i)));
	}

	public static SFullArea load(byte[] bytes)
	{
		try
		{
			SFullArea obj = new SFullArea();
			obj.parse(FullArea.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SFullArea load(FullArea proto)
	{
		try
		{
			SFullArea obj = new SFullArea();
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
	public FullArea saveToProto()
	{
		FullArea.Builder _builder_ = FullArea.newBuilder();
		if (points != null)
			for (SPoint _value_ : points)
				_builder_.addPoints(_value_.saveToProto());
		return _builder_.build();
	}
}
