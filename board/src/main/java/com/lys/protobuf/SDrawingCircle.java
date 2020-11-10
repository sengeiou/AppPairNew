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
import com.lys.protobuf.ProtocolBoard.DrawingCircle;

// 圆形
public class SDrawingCircle extends SPTData<DrawingCircle>
{
	private static final SDrawingCircle DefaultInstance = new SDrawingCircle();

	public Integer paintColor = 0;
	public Float strokeWidth = 0F;
	public SPoint pos = null;
	public Float radius = 0F;

	public static SDrawingCircle create(Integer paintColor, Float strokeWidth, SPoint pos, Float radius)
	{
		SDrawingCircle obj = new SDrawingCircle();
		obj.paintColor = paintColor;
		obj.strokeWidth = strokeWidth;
		obj.pos = pos;
		obj.radius = radius;
		return obj;
	}

	public SDrawingCircle clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SDrawingCircle _other_)
	{
		this.paintColor = _other_.paintColor;
		this.strokeWidth = _other_.strokeWidth;
		this.pos = _other_.pos;
		this.radius = _other_.radius;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("paintColor"))
			paintColor = _json_.getInteger("paintColor");
		if (_json_.containsKey("strokeWidth"))
			strokeWidth = _json_.getFloat("strokeWidth");
		if (_json_.containsKey("pos"))
			pos = SPoint.load(_json_.getJSONObject("pos"));
		if (_json_.containsKey("radius"))
			radius = _json_.getFloat("radius");
	}

	public static SDrawingCircle load(String str)
	{
		try
		{
			SDrawingCircle obj = new SDrawingCircle();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SDrawingCircle load(JSONObject json)
	{
		try
		{
			SDrawingCircle obj = new SDrawingCircle();
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
			if (pos != null)
				_json_.put("pos", pos.saveToJson());
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

	public static List<SDrawingCircle> loadList(JSONArray ja)
	{
		try
		{
			List<SDrawingCircle> list = new ArrayList<SDrawingCircle>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SDrawingCircle item = SDrawingCircle.load(jo);
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

	public static JSONArray saveList(List<SDrawingCircle> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SDrawingCircle item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(DrawingCircle _proto_)
	{
		if (_proto_.hasPaintColor())
			paintColor = _proto_.getPaintColor();
		if (_proto_.hasStrokeWidth())
			strokeWidth = _proto_.getStrokeWidth();
		if (_proto_.hasPos())
			pos = SPoint.load(_proto_.getPos());
		if (_proto_.hasRadius())
			radius = _proto_.getRadius();
	}

	public static SDrawingCircle load(byte[] bytes)
	{
		try
		{
			SDrawingCircle obj = new SDrawingCircle();
			obj.parse(DrawingCircle.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SDrawingCircle load(DrawingCircle proto)
	{
		try
		{
			SDrawingCircle obj = new SDrawingCircle();
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
	public DrawingCircle saveToProto()
	{
		DrawingCircle.Builder _builder_ = DrawingCircle.newBuilder();
		if (paintColor != null && !paintColor.equals(DrawingCircle.getDefaultInstance().getPaintColor()))
			_builder_.setPaintColor(paintColor);
		if (strokeWidth != null && !strokeWidth.equals(DrawingCircle.getDefaultInstance().getStrokeWidth()))
			_builder_.setStrokeWidth(strokeWidth);
		if (pos != null)
			_builder_.setPos(pos.saveToProto());
		if (radius != null && !radius.equals(DrawingCircle.getDefaultInstance().getRadius()))
			_builder_.setRadius(radius);
		return _builder_.build();
	}
}
