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
import com.lys.protobuf.ProtocolBoard.Point;

public class SPoint extends SPTData<Point>
{
	private static final SPoint DefaultInstance = new SPoint();

	public Float x = 0F;
	public Float y = 0F;
	public Float pressure = 1F;
	public Long timestamp = 0L;

	public static SPoint create(Float x, Float y, Float pressure, Long timestamp)
	{
		SPoint obj = new SPoint();
		obj.x = x;
		obj.y = y;
		obj.pressure = pressure;
		obj.timestamp = timestamp;
		return obj;
	}

	public SPoint clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SPoint _other_)
	{
		this.x = _other_.x;
		this.y = _other_.y;
		this.pressure = _other_.pressure;
		this.timestamp = _other_.timestamp;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("x"))
			x = _json_.getFloat("x");
		if (_json_.containsKey("y"))
			y = _json_.getFloat("y");
		if (_json_.containsKey("pressure"))
			pressure = _json_.getFloat("pressure");
		if (_json_.containsKey("timestamp"))
			timestamp = _json_.getLong("timestamp");
	}

	public static SPoint load(String str)
	{
		try
		{
			SPoint obj = new SPoint();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SPoint load(JSONObject json)
	{
		try
		{
			SPoint obj = new SPoint();
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
			if (x != null)
				_json_.put("x", x);
			if (y != null)
				_json_.put("y", y);
			if (pressure != null)
				_json_.put("pressure", pressure);
			if (timestamp != null)
				_json_.put("timestamp", String.valueOf(timestamp));
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SPoint> loadList(JSONArray ja)
	{
		try
		{
			List<SPoint> list = new ArrayList<SPoint>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SPoint item = SPoint.load(jo);
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

	public static JSONArray saveList(List<SPoint> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SPoint item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Point _proto_)
	{
		if (_proto_.hasX())
			x = _proto_.getX();
		if (_proto_.hasY())
			y = _proto_.getY();
		if (_proto_.hasPressure())
			pressure = _proto_.getPressure();
		if (_proto_.hasTimestamp())
			timestamp = _proto_.getTimestamp();
	}

	public static SPoint load(byte[] bytes)
	{
		try
		{
			SPoint obj = new SPoint();
			obj.parse(Point.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SPoint load(Point proto)
	{
		try
		{
			SPoint obj = new SPoint();
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
	public Point saveToProto()
	{
		Point.Builder _builder_ = Point.newBuilder();
		if (x != null && !x.equals(Point.getDefaultInstance().getX()))
			_builder_.setX(x);
		if (y != null && !y.equals(Point.getDefaultInstance().getY()))
			_builder_.setY(y);
		if (pressure != null && !pressure.equals(Point.getDefaultInstance().getPressure()))
			_builder_.setPressure(pressure);
		if (timestamp != null && !timestamp.equals(Point.getDefaultInstance().getTimestamp()))
			_builder_.setTimestamp(timestamp);
		return _builder_.build();
	}
}
