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
import com.lys.protobuf.ProtocolBoard.DrawingEraser;

// 橡皮擦
public class SDrawingEraser extends SPTData<DrawingEraser>
{
	private static final SDrawingEraser DefaultInstance = new SDrawingEraser();

	public List<SPointRadius> points = new ArrayList<SPointRadius>();
	public List<SFullArea> fullAreas = new ArrayList<SFullArea>();

	public static SDrawingEraser create()
	{
		SDrawingEraser obj = new SDrawingEraser();
		return obj;
	}

	public SDrawingEraser clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SDrawingEraser _other_)
	{
		this.points = _other_.points;
		this.fullAreas = _other_.fullAreas;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("points"))
			points = SPointRadius.loadList(_json_.getJSONArray("points"));
		if (_json_.containsKey("fullAreas"))
			fullAreas = SFullArea.loadList(_json_.getJSONArray("fullAreas"));
	}

	public static SDrawingEraser load(String str)
	{
		try
		{
			SDrawingEraser obj = new SDrawingEraser();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SDrawingEraser load(JSONObject json)
	{
		try
		{
			SDrawingEraser obj = new SDrawingEraser();
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
				_json_.put("points", SPointRadius.saveList(points));
			if (fullAreas != null)
				_json_.put("fullAreas", SFullArea.saveList(fullAreas));
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SDrawingEraser> loadList(JSONArray ja)
	{
		try
		{
			List<SDrawingEraser> list = new ArrayList<SDrawingEraser>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SDrawingEraser item = SDrawingEraser.load(jo);
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

	public static JSONArray saveList(List<SDrawingEraser> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SDrawingEraser item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(DrawingEraser _proto_)
	{
		for (int i = 0; i < _proto_.getPointsCount(); i++)
			points.add(SPointRadius.load(_proto_.getPoints(i)));
		for (int i = 0; i < _proto_.getFullAreasCount(); i++)
			fullAreas.add(SFullArea.load(_proto_.getFullAreas(i)));
	}

	public static SDrawingEraser load(byte[] bytes)
	{
		try
		{
			SDrawingEraser obj = new SDrawingEraser();
			obj.parse(DrawingEraser.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SDrawingEraser load(DrawingEraser proto)
	{
		try
		{
			SDrawingEraser obj = new SDrawingEraser();
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
	public DrawingEraser saveToProto()
	{
		DrawingEraser.Builder _builder_ = DrawingEraser.newBuilder();
		if (points != null)
			for (SPointRadius _value_ : points)
				_builder_.addPoints(_value_.saveToProto());
		if (fullAreas != null)
			for (SFullArea _value_ : fullAreas)
				_builder_.addFullAreas(_value_.saveToProto());
		return _builder_.build();
	}
}
