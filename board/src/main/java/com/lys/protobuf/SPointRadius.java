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
import com.lys.protobuf.ProtocolBoard.PointRadius;

public class SPointRadius extends SPTData<PointRadius>
{
	private static final SPointRadius DefaultInstance = new SPointRadius();

	public SPoint point = null;
	public Float radius = 0F;

	public static SPointRadius create(SPoint point, Float radius)
	{
		SPointRadius obj = new SPointRadius();
		obj.point = point;
		obj.radius = radius;
		return obj;
	}

	public SPointRadius clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SPointRadius _other_)
	{
		this.point = _other_.point;
		this.radius = _other_.radius;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("point"))
			point = SPoint.load(_json_.getJSONObject("point"));
		if (_json_.containsKey("radius"))
			radius = _json_.getFloat("radius");
	}

	public static SPointRadius load(String str)
	{
		try
		{
			SPointRadius obj = new SPointRadius();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SPointRadius load(JSONObject json)
	{
		try
		{
			SPointRadius obj = new SPointRadius();
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
			if (point != null)
				_json_.put("point", point.saveToJson());
			if (radius != null)
				_json_.put("radius", radius);
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SPointRadius> loadList(JSONArray ja)
	{
		try
		{
			List<SPointRadius> list = new ArrayList<SPointRadius>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SPointRadius item = SPointRadius.load(jo);
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

	public static JSONArray saveList(List<SPointRadius> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SPointRadius item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(PointRadius _proto_)
	{
		if (_proto_.hasPoint())
			point = SPoint.load(_proto_.getPoint());
		if (_proto_.hasRadius())
			radius = _proto_.getRadius();
	}

	public static SPointRadius load(byte[] bytes)
	{
		try
		{
			SPointRadius obj = new SPointRadius();
			obj.parse(PointRadius.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SPointRadius load(PointRadius proto)
	{
		try
		{
			SPointRadius obj = new SPointRadius();
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
	public PointRadius saveToProto()
	{
		PointRadius.Builder _builder_ = PointRadius.newBuilder();
		if (point != null)
			_builder_.setPoint(point.saveToProto());
		if (radius != null && !radius.equals(PointRadius.getDefaultInstance().getRadius()))
			_builder_.setRadius(radius);
		return _builder_.build();
	}
}
