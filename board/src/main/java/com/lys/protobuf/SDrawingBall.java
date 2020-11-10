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
import com.lys.protobuf.ProtocolBoard.DrawingBall;

// 球
public class SDrawingBall extends SPTData<DrawingBall>
{
	private static final SDrawingBall DefaultInstance = new SDrawingBall();

	public Integer paintColor = 0;
	public Float strokeWidth = 0F;
	public SPoint pos = null;
	public Float radius = 0F;

	public static SDrawingBall create(Integer paintColor, Float strokeWidth, SPoint pos, Float radius)
	{
		SDrawingBall obj = new SDrawingBall();
		obj.paintColor = paintColor;
		obj.strokeWidth = strokeWidth;
		obj.pos = pos;
		obj.radius = radius;
		return obj;
	}

	public SDrawingBall clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SDrawingBall _other_)
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

	public static SDrawingBall load(String str)
	{
		try
		{
			SDrawingBall obj = new SDrawingBall();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SDrawingBall load(JSONObject json)
	{
		try
		{
			SDrawingBall obj = new SDrawingBall();
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

	public static List<SDrawingBall> loadList(JSONArray ja)
	{
		try
		{
			List<SDrawingBall> list = new ArrayList<SDrawingBall>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SDrawingBall item = SDrawingBall.load(jo);
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

	public static JSONArray saveList(List<SDrawingBall> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SDrawingBall item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(DrawingBall _proto_)
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

	public static SDrawingBall load(byte[] bytes)
	{
		try
		{
			SDrawingBall obj = new SDrawingBall();
			obj.parse(DrawingBall.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SDrawingBall load(DrawingBall proto)
	{
		try
		{
			SDrawingBall obj = new SDrawingBall();
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
	public DrawingBall saveToProto()
	{
		DrawingBall.Builder _builder_ = DrawingBall.newBuilder();
		if (paintColor != null && !paintColor.equals(DrawingBall.getDefaultInstance().getPaintColor()))
			_builder_.setPaintColor(paintColor);
		if (strokeWidth != null && !strokeWidth.equals(DrawingBall.getDefaultInstance().getStrokeWidth()))
			_builder_.setStrokeWidth(strokeWidth);
		if (pos != null)
			_builder_.setPos(pos.saveToProto());
		if (radius != null && !radius.equals(DrawingBall.getDefaultInstance().getRadius()))
			_builder_.setRadius(radius);
		return _builder_.build();
	}
}
