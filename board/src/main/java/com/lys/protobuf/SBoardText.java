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
import com.lys.protobuf.ProtocolBoard.BoardText;

// 画布文本
public class SBoardText extends SPTData<BoardText>
{
	private static final SBoardText DefaultInstance = new SBoardText();

	public String text = null;
	public Integer color = -16777216; // 0xff000000
	public Integer size = 30;
	public Integer gravity = 17; // Gravity.CENTER
	public Integer textStyle = 0;
	public Integer solidColor = 0;
	public Integer strokeWidth = 0;
	public Integer strokeColor = 0;
	public List<Float> corners = new ArrayList<Float>();
	public List<Integer> paddings = new ArrayList<Integer>();
	public Float letterSpacing = 0F;
	public Float lineSpacingAdd = 0F;
	public Float lineSpacingMult = 1F;
	public Float textScaleX = 1F;
	public Integer lines = 0;
	public Integer ellipsize = 2;
	public Float shadowLayerRadius = 1F;
	public Float shadowLayerDx = 0F;
	public Float shadowLayerDy = 0F;
	public Integer shadowLayerColor = 0;

	public static SBoardText create(String text, Integer color, Integer size, Integer gravity, Integer textStyle, Integer solidColor, Integer strokeWidth, Integer strokeColor, Float letterSpacing, Float lineSpacingAdd, Float lineSpacingMult, Float textScaleX, Integer lines, Integer ellipsize, Float shadowLayerRadius, Float shadowLayerDx, Float shadowLayerDy, Integer shadowLayerColor)
	{
		SBoardText obj = new SBoardText();
		obj.text = text;
		obj.color = color;
		obj.size = size;
		obj.gravity = gravity;
		obj.textStyle = textStyle;
		obj.solidColor = solidColor;
		obj.strokeWidth = strokeWidth;
		obj.strokeColor = strokeColor;
		obj.letterSpacing = letterSpacing;
		obj.lineSpacingAdd = lineSpacingAdd;
		obj.lineSpacingMult = lineSpacingMult;
		obj.textScaleX = textScaleX;
		obj.lines = lines;
		obj.ellipsize = ellipsize;
		obj.shadowLayerRadius = shadowLayerRadius;
		obj.shadowLayerDx = shadowLayerDx;
		obj.shadowLayerDy = shadowLayerDy;
		obj.shadowLayerColor = shadowLayerColor;
		return obj;
	}

	public SBoardText clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SBoardText _other_)
	{
		this.text = _other_.text;
		this.color = _other_.color;
		this.size = _other_.size;
		this.gravity = _other_.gravity;
		this.textStyle = _other_.textStyle;
		this.solidColor = _other_.solidColor;
		this.strokeWidth = _other_.strokeWidth;
		this.strokeColor = _other_.strokeColor;
		this.corners = _other_.corners;
		this.paddings = _other_.paddings;
		this.letterSpacing = _other_.letterSpacing;
		this.lineSpacingAdd = _other_.lineSpacingAdd;
		this.lineSpacingMult = _other_.lineSpacingMult;
		this.textScaleX = _other_.textScaleX;
		this.lines = _other_.lines;
		this.ellipsize = _other_.ellipsize;
		this.shadowLayerRadius = _other_.shadowLayerRadius;
		this.shadowLayerDx = _other_.shadowLayerDx;
		this.shadowLayerDy = _other_.shadowLayerDy;
		this.shadowLayerColor = _other_.shadowLayerColor;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("text"))
			text = _json_.getString("text");
		if (_json_.containsKey("color"))
			color = _json_.getInteger("color");
		if (_json_.containsKey("size"))
			size = _json_.getInteger("size");
		if (_json_.containsKey("gravity"))
			gravity = _json_.getInteger("gravity");
		if (_json_.containsKey("textStyle"))
			textStyle = _json_.getInteger("textStyle");
		if (_json_.containsKey("solidColor"))
			solidColor = _json_.getInteger("solidColor");
		if (_json_.containsKey("strokeWidth"))
			strokeWidth = _json_.getInteger("strokeWidth");
		if (_json_.containsKey("strokeColor"))
			strokeColor = _json_.getInteger("strokeColor");
		if (_json_.containsKey("corners"))
			corners = AppDataTool.loadFloatList(AppDataTool.getJSONArray(_json_, "corners"));
		if (_json_.containsKey("paddings"))
			paddings = AppDataTool.loadIntegerList(AppDataTool.getJSONArray(_json_, "paddings"));
		if (_json_.containsKey("letterSpacing"))
			letterSpacing = _json_.getFloat("letterSpacing");
		if (_json_.containsKey("lineSpacingAdd"))
			lineSpacingAdd = _json_.getFloat("lineSpacingAdd");
		if (_json_.containsKey("lineSpacingMult"))
			lineSpacingMult = _json_.getFloat("lineSpacingMult");
		if (_json_.containsKey("textScaleX"))
			textScaleX = _json_.getFloat("textScaleX");
		if (_json_.containsKey("lines"))
			lines = _json_.getInteger("lines");
		if (_json_.containsKey("ellipsize"))
			ellipsize = _json_.getInteger("ellipsize");
		if (_json_.containsKey("shadowLayerRadius"))
			shadowLayerRadius = _json_.getFloat("shadowLayerRadius");
		if (_json_.containsKey("shadowLayerDx"))
			shadowLayerDx = _json_.getFloat("shadowLayerDx");
		if (_json_.containsKey("shadowLayerDy"))
			shadowLayerDy = _json_.getFloat("shadowLayerDy");
		if (_json_.containsKey("shadowLayerColor"))
			shadowLayerColor = _json_.getInteger("shadowLayerColor");
	}

	public static SBoardText load(String str)
	{
		try
		{
			SBoardText obj = new SBoardText();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SBoardText load(JSONObject json)
	{
		try
		{
			SBoardText obj = new SBoardText();
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
			if (text != null)
				_json_.put("text", text);
			if (color != null)
				_json_.put("color", color);
			if (size != null)
				_json_.put("size", size);
			if (gravity != null)
				_json_.put("gravity", gravity);
			if (textStyle != null)
				_json_.put("textStyle", textStyle);
			if (solidColor != null)
				_json_.put("solidColor", solidColor);
			if (strokeWidth != null)
				_json_.put("strokeWidth", strokeWidth);
			if (strokeColor != null)
				_json_.put("strokeColor", strokeColor);
			if (corners != null)
				_json_.put("corners", AppDataTool.saveFloatList(corners));
			if (paddings != null)
				_json_.put("paddings", AppDataTool.saveIntegerList(paddings));
			if (letterSpacing != null)
				_json_.put("letterSpacing", letterSpacing);
			if (lineSpacingAdd != null)
				_json_.put("lineSpacingAdd", lineSpacingAdd);
			if (lineSpacingMult != null)
				_json_.put("lineSpacingMult", lineSpacingMult);
			if (textScaleX != null)
				_json_.put("textScaleX", textScaleX);
			if (lines != null)
				_json_.put("lines", lines);
			if (ellipsize != null)
				_json_.put("ellipsize", ellipsize);
			if (shadowLayerRadius != null)
				_json_.put("shadowLayerRadius", shadowLayerRadius);
			if (shadowLayerDx != null)
				_json_.put("shadowLayerDx", shadowLayerDx);
			if (shadowLayerDy != null)
				_json_.put("shadowLayerDy", shadowLayerDy);
			if (shadowLayerColor != null)
				_json_.put("shadowLayerColor", shadowLayerColor);
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SBoardText> loadList(JSONArray ja)
	{
		try
		{
			List<SBoardText> list = new ArrayList<SBoardText>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SBoardText item = SBoardText.load(jo);
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

	public static JSONArray saveList(List<SBoardText> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SBoardText item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(BoardText _proto_)
	{
		if (_proto_.hasText())
			text = _proto_.getText();
		if (_proto_.hasColor())
			color = _proto_.getColor();
		if (_proto_.hasSize())
			size = _proto_.getSize();
		if (_proto_.hasGravity())
			gravity = _proto_.getGravity();
		if (_proto_.hasTextStyle())
			textStyle = _proto_.getTextStyle();
		if (_proto_.hasSolidColor())
			solidColor = _proto_.getSolidColor();
		if (_proto_.hasStrokeWidth())
			strokeWidth = _proto_.getStrokeWidth();
		if (_proto_.hasStrokeColor())
			strokeColor = _proto_.getStrokeColor();
		for (int i = 0; i < _proto_.getCornersCount(); i++)
			corners.add(_proto_.getCorners(i));
		for (int i = 0; i < _proto_.getPaddingsCount(); i++)
			paddings.add(_proto_.getPaddings(i));
		if (_proto_.hasLetterSpacing())
			letterSpacing = _proto_.getLetterSpacing();
		if (_proto_.hasLineSpacingAdd())
			lineSpacingAdd = _proto_.getLineSpacingAdd();
		if (_proto_.hasLineSpacingMult())
			lineSpacingMult = _proto_.getLineSpacingMult();
		if (_proto_.hasTextScaleX())
			textScaleX = _proto_.getTextScaleX();
		if (_proto_.hasLines())
			lines = _proto_.getLines();
		if (_proto_.hasEllipsize())
			ellipsize = _proto_.getEllipsize();
		if (_proto_.hasShadowLayerRadius())
			shadowLayerRadius = _proto_.getShadowLayerRadius();
		if (_proto_.hasShadowLayerDx())
			shadowLayerDx = _proto_.getShadowLayerDx();
		if (_proto_.hasShadowLayerDy())
			shadowLayerDy = _proto_.getShadowLayerDy();
		if (_proto_.hasShadowLayerColor())
			shadowLayerColor = _proto_.getShadowLayerColor();
	}

	public static SBoardText load(byte[] bytes)
	{
		try
		{
			SBoardText obj = new SBoardText();
			obj.parse(BoardText.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SBoardText load(BoardText proto)
	{
		try
		{
			SBoardText obj = new SBoardText();
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
	public BoardText saveToProto()
	{
		BoardText.Builder _builder_ = BoardText.newBuilder();
		if (text != null && !text.equals(BoardText.getDefaultInstance().getText()))
			_builder_.setText(text);
		if (color != null && !color.equals(BoardText.getDefaultInstance().getColor()))
			_builder_.setColor(color);
		if (size != null && !size.equals(BoardText.getDefaultInstance().getSize()))
			_builder_.setSize(size);
		if (gravity != null && !gravity.equals(BoardText.getDefaultInstance().getGravity()))
			_builder_.setGravity(gravity);
		if (textStyle != null && !textStyle.equals(BoardText.getDefaultInstance().getTextStyle()))
			_builder_.setTextStyle(textStyle);
		if (solidColor != null && !solidColor.equals(BoardText.getDefaultInstance().getSolidColor()))
			_builder_.setSolidColor(solidColor);
		if (strokeWidth != null && !strokeWidth.equals(BoardText.getDefaultInstance().getStrokeWidth()))
			_builder_.setStrokeWidth(strokeWidth);
		if (strokeColor != null && !strokeColor.equals(BoardText.getDefaultInstance().getStrokeColor()))
			_builder_.setStrokeColor(strokeColor);
		if (corners != null)
			for (Float _value_ : corners)
				_builder_.addCorners(_value_);
		if (paddings != null)
			for (Integer _value_ : paddings)
				_builder_.addPaddings(_value_);
		if (letterSpacing != null && !letterSpacing.equals(BoardText.getDefaultInstance().getLetterSpacing()))
			_builder_.setLetterSpacing(letterSpacing);
		if (lineSpacingAdd != null && !lineSpacingAdd.equals(BoardText.getDefaultInstance().getLineSpacingAdd()))
			_builder_.setLineSpacingAdd(lineSpacingAdd);
		if (lineSpacingMult != null && !lineSpacingMult.equals(BoardText.getDefaultInstance().getLineSpacingMult()))
			_builder_.setLineSpacingMult(lineSpacingMult);
		if (textScaleX != null && !textScaleX.equals(BoardText.getDefaultInstance().getTextScaleX()))
			_builder_.setTextScaleX(textScaleX);
		if (lines != null && !lines.equals(BoardText.getDefaultInstance().getLines()))
			_builder_.setLines(lines);
		if (ellipsize != null && !ellipsize.equals(BoardText.getDefaultInstance().getEllipsize()))
			_builder_.setEllipsize(ellipsize);
		if (shadowLayerRadius != null && !shadowLayerRadius.equals(BoardText.getDefaultInstance().getShadowLayerRadius()))
			_builder_.setShadowLayerRadius(shadowLayerRadius);
		if (shadowLayerDx != null && !shadowLayerDx.equals(BoardText.getDefaultInstance().getShadowLayerDx()))
			_builder_.setShadowLayerDx(shadowLayerDx);
		if (shadowLayerDy != null && !shadowLayerDy.equals(BoardText.getDefaultInstance().getShadowLayerDy()))
			_builder_.setShadowLayerDy(shadowLayerDy);
		if (shadowLayerColor != null && !shadowLayerColor.equals(BoardText.getDefaultInstance().getShadowLayerColor()))
			_builder_.setShadowLayerColor(shadowLayerColor);
		return _builder_.build();
	}
}
