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
import com.lys.protobuf.ProtocolBoard.DrawingAnyFunc;

// 任意函数
public class SDrawingAnyFunc extends SPTData<DrawingAnyFunc>
{
	private static final SDrawingAnyFunc DefaultInstance = new SDrawingAnyFunc();

	public Integer paintColor = 0;
	public Float strokeWidth = 0F;
	public SPoint posStart = null;
	public SPoint posStop = null;
	public String formula = null;
	public String conditionFrom = null;
	public String conditionTo = null;
	public Boolean containFrom = false;
	public Boolean containTo = false;
	public Float scaleX = 0F;
	public Float scaleY = 0F;

	public static SDrawingAnyFunc create(Integer paintColor, Float strokeWidth, SPoint posStart, SPoint posStop, String formula, String conditionFrom, String conditionTo, Boolean containFrom, Boolean containTo, Float scaleX, Float scaleY)
	{
		SDrawingAnyFunc obj = new SDrawingAnyFunc();
		obj.paintColor = paintColor;
		obj.strokeWidth = strokeWidth;
		obj.posStart = posStart;
		obj.posStop = posStop;
		obj.formula = formula;
		obj.conditionFrom = conditionFrom;
		obj.conditionTo = conditionTo;
		obj.containFrom = containFrom;
		obj.containTo = containTo;
		obj.scaleX = scaleX;
		obj.scaleY = scaleY;
		return obj;
	}

	public SDrawingAnyFunc clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SDrawingAnyFunc _other_)
	{
		this.paintColor = _other_.paintColor;
		this.strokeWidth = _other_.strokeWidth;
		this.posStart = _other_.posStart;
		this.posStop = _other_.posStop;
		this.formula = _other_.formula;
		this.conditionFrom = _other_.conditionFrom;
		this.conditionTo = _other_.conditionTo;
		this.containFrom = _other_.containFrom;
		this.containTo = _other_.containTo;
		this.scaleX = _other_.scaleX;
		this.scaleY = _other_.scaleY;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("paintColor"))
			paintColor = _json_.getInteger("paintColor");
		if (_json_.containsKey("strokeWidth"))
			strokeWidth = _json_.getFloat("strokeWidth");
		if (_json_.containsKey("posStart"))
			posStart = SPoint.load(_json_.getJSONObject("posStart"));
		if (_json_.containsKey("posStop"))
			posStop = SPoint.load(_json_.getJSONObject("posStop"));
		if (_json_.containsKey("formula"))
			formula = _json_.getString("formula");
		if (_json_.containsKey("conditionFrom"))
			conditionFrom = _json_.getString("conditionFrom");
		if (_json_.containsKey("conditionTo"))
			conditionTo = _json_.getString("conditionTo");
		if (_json_.containsKey("containFrom"))
			containFrom = _json_.getBoolean("containFrom");
		if (_json_.containsKey("containTo"))
			containTo = _json_.getBoolean("containTo");
		if (_json_.containsKey("scaleX"))
			scaleX = _json_.getFloat("scaleX");
		if (_json_.containsKey("scaleY"))
			scaleY = _json_.getFloat("scaleY");
	}

	public static SDrawingAnyFunc load(String str)
	{
		try
		{
			SDrawingAnyFunc obj = new SDrawingAnyFunc();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SDrawingAnyFunc load(JSONObject json)
	{
		try
		{
			SDrawingAnyFunc obj = new SDrawingAnyFunc();
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
			if (posStart != null)
				_json_.put("posStart", posStart.saveToJson());
			if (posStop != null)
				_json_.put("posStop", posStop.saveToJson());
			if (formula != null)
				_json_.put("formula", formula);
			if (conditionFrom != null)
				_json_.put("conditionFrom", conditionFrom);
			if (conditionTo != null)
				_json_.put("conditionTo", conditionTo);
			if (containFrom != null)
				_json_.put("containFrom", containFrom);
			if (containTo != null)
				_json_.put("containTo", containTo);
			if (scaleX != null)
				_json_.put("scaleX", scaleX);
			if (scaleY != null)
				_json_.put("scaleY", scaleY);
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SDrawingAnyFunc> loadList(JSONArray ja)
	{
		try
		{
			List<SDrawingAnyFunc> list = new ArrayList<SDrawingAnyFunc>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SDrawingAnyFunc item = SDrawingAnyFunc.load(jo);
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

	public static JSONArray saveList(List<SDrawingAnyFunc> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SDrawingAnyFunc item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(DrawingAnyFunc _proto_)
	{
		if (_proto_.hasPaintColor())
			paintColor = _proto_.getPaintColor();
		if (_proto_.hasStrokeWidth())
			strokeWidth = _proto_.getStrokeWidth();
		if (_proto_.hasPosStart())
			posStart = SPoint.load(_proto_.getPosStart());
		if (_proto_.hasPosStop())
			posStop = SPoint.load(_proto_.getPosStop());
		if (_proto_.hasFormula())
			formula = _proto_.getFormula();
		if (_proto_.hasConditionFrom())
			conditionFrom = _proto_.getConditionFrom();
		if (_proto_.hasConditionTo())
			conditionTo = _proto_.getConditionTo();
		if (_proto_.hasContainFrom())
			containFrom = _proto_.getContainFrom();
		if (_proto_.hasContainTo())
			containTo = _proto_.getContainTo();
		if (_proto_.hasScaleX())
			scaleX = _proto_.getScaleX();
		if (_proto_.hasScaleY())
			scaleY = _proto_.getScaleY();
	}

	public static SDrawingAnyFunc load(byte[] bytes)
	{
		try
		{
			SDrawingAnyFunc obj = new SDrawingAnyFunc();
			obj.parse(DrawingAnyFunc.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SDrawingAnyFunc load(DrawingAnyFunc proto)
	{
		try
		{
			SDrawingAnyFunc obj = new SDrawingAnyFunc();
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
	public DrawingAnyFunc saveToProto()
	{
		DrawingAnyFunc.Builder _builder_ = DrawingAnyFunc.newBuilder();
		if (paintColor != null && !paintColor.equals(DrawingAnyFunc.getDefaultInstance().getPaintColor()))
			_builder_.setPaintColor(paintColor);
		if (strokeWidth != null && !strokeWidth.equals(DrawingAnyFunc.getDefaultInstance().getStrokeWidth()))
			_builder_.setStrokeWidth(strokeWidth);
		if (posStart != null)
			_builder_.setPosStart(posStart.saveToProto());
		if (posStop != null)
			_builder_.setPosStop(posStop.saveToProto());
		if (formula != null && !formula.equals(DrawingAnyFunc.getDefaultInstance().getFormula()))
			_builder_.setFormula(formula);
		if (conditionFrom != null && !conditionFrom.equals(DrawingAnyFunc.getDefaultInstance().getConditionFrom()))
			_builder_.setConditionFrom(conditionFrom);
		if (conditionTo != null && !conditionTo.equals(DrawingAnyFunc.getDefaultInstance().getConditionTo()))
			_builder_.setConditionTo(conditionTo);
		if (containFrom != null && !containFrom.equals(DrawingAnyFunc.getDefaultInstance().getContainFrom()))
			_builder_.setContainFrom(containFrom);
		if (containTo != null && !containTo.equals(DrawingAnyFunc.getDefaultInstance().getContainTo()))
			_builder_.setContainTo(containTo);
		if (scaleX != null && !scaleX.equals(DrawingAnyFunc.getDefaultInstance().getScaleX()))
			_builder_.setScaleX(scaleX);
		if (scaleY != null && !scaleY.equals(DrawingAnyFunc.getDefaultInstance().getScaleY()))
			_builder_.setScaleY(scaleY);
		return _builder_.build();
	}
}
