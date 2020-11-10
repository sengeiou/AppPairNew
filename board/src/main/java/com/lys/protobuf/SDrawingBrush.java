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
import com.lys.protobuf.ProtocolBoard.DrawingBrush;

// 毛笔
public class SDrawingBrush extends SPTData<DrawingBrush>
{
	private static final SDrawingBrush DefaultInstance = new SDrawingBrush();

	public Integer paintColor = 0;
	public Float strokeWidth = 0F;
	public List<SPoint> points = new ArrayList<SPoint>();

	public static SDrawingBrush create(Integer paintColor, Float strokeWidth)
	{
		SDrawingBrush obj = new SDrawingBrush();
		obj.paintColor = paintColor;
		obj.strokeWidth = strokeWidth;
		return obj;
	}

	public SDrawingBrush clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SDrawingBrush _other_)
	{
		this.paintColor = _other_.paintColor;
		this.strokeWidth = _other_.strokeWidth;
		this.points = _other_.points;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("paintColor"))
			paintColor = _json_.getInteger("paintColor");
		if (_json_.containsKey("strokeWidth"))
			strokeWidth = _json_.getFloat("strokeWidth");
		if (_json_.containsKey("points"))
			points = SPoint.loadList(_json_.getJSONArray("points"));
	}

	public static SDrawingBrush load(String str)
	{
		try
		{
			SDrawingBrush obj = new SDrawingBrush();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SDrawingBrush load(JSONObject json)
	{
		try
		{
			SDrawingBrush obj = new SDrawingBrush();
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
			if (paintColor != null)
				_json_.put("paintColor", paintColor);
			if (strokeWidth != null)
				_json_.put("strokeWidth", strokeWidth);
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

	public static List<SDrawingBrush> loadList(JSONArray ja)
	{
		try
		{
			List<SDrawingBrush> list = new ArrayList<SDrawingBrush>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SDrawingBrush item = SDrawingBrush.load(jo);
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

	public static JSONArray saveList(List<SDrawingBrush> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SDrawingBrush item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(DrawingBrush _proto_)
	{
		if (_proto_.hasPaintColor())
			paintColor = _proto_.getPaintColor();
		if (_proto_.hasStrokeWidth())
			strokeWidth = _proto_.getStrokeWidth();
		for (int i = 0; i < _proto_.getPointsCount(); i++)
			points.add(SPoint.load(_proto_.getPoints(i)));
	}

	public static SDrawingBrush load(byte[] bytes)
	{
		try
		{
			SDrawingBrush obj = new SDrawingBrush();
			obj.parse(DrawingBrush.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SDrawingBrush load(DrawingBrush proto)
	{
		try
		{
			SDrawingBrush obj = new SDrawingBrush();
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
	public DrawingBrush saveToProto()
	{
		DrawingBrush.Builder _builder_ = DrawingBrush.newBuilder();
		if (paintColor != null && !paintColor.equals(DrawingBrush.getDefaultInstance().getPaintColor()))
			_builder_.setPaintColor(paintColor);
		if (strokeWidth != null && !strokeWidth.equals(DrawingBrush.getDefaultInstance().getStrokeWidth()))
			_builder_.setStrokeWidth(strokeWidth);
		if (points != null)
			for (SPoint _value_ : points)
				_builder_.addPoints(_value_.saveToProto());
		return _builder_.build();
	}
}
