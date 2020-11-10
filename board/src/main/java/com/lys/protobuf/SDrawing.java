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
import com.lys.protobuf.ProtocolBoard.Drawing;

public class SDrawing extends SPTData<Drawing>
{
	private static final SDrawing DefaultInstance = new SDrawing();

	public Integer drawingType = 0;
	public SDrawingStroke stroke = null; // 钢笔
	public SDrawingStress stress = null; // 钢笔2
	public SDrawingBrush brush = null; // 毛笔
	public SDrawingEraser eraser = null; // 橡皮擦
	public SDrawingFullLine fullLine = null; // 实线
	public SDrawingImaginaryLine imaginaryLine = null; // 虚线
	public SDrawingArrowsLine arrowsLine = null; // 箭头线
	public SDrawingCoord coord = null; // 坐标
	public SDrawingMark mark = null; // 记号笔
	public SDrawingWater water = null; // 水彩笔
	public SDrawingCircle circle = null; // 圆形
	public SDrawingOval oval = null; // 椭圆形
	public SDrawingSquare square = null; // 正方形
	public SDrawingRectangle rectangle = null; // 长方形
	public SDrawingTriangle triangle = null; // 三角形
	public SDrawingIsosceles isosceles = null; // 等腰三角形
	public SDrawingRightAngled rightAngled = null; // 直角三角形
	public SDrawingEquilateralTriangle equilateralTriangle = null; // 等边三角形
	public SDrawingCone cone = null; // 圆锥
	public SDrawingCylinder cylinder = null; // 圆柱
	public SDrawingPyramid pyramid = null; // 三棱锥
	public SDrawingUdParabola udParabola = null; // 二次函数
	public SDrawingLrParabola lrParabola = null; // 抛物线
	public SDrawingSine sine = null; // 正余弦曲线
	public SDrawingTan tan = null; // 正切曲线
	public SDrawingHyperbola hyperbola = null; // 双曲线
	public SDrawingHyperbola2 hyperbola2 = null; // 双曲线2
	public SDrawingAnyFunc anyFunc = null; // 任意函数
	public SDrawingCube cube = null; // 长方体
	public SDrawingBall ball = null; // 球

	public static SDrawing create(Integer drawingType, SDrawingStroke stroke, SDrawingStress stress, SDrawingBrush brush, SDrawingEraser eraser, SDrawingFullLine fullLine, SDrawingImaginaryLine imaginaryLine, SDrawingArrowsLine arrowsLine, SDrawingCoord coord, SDrawingMark mark, SDrawingWater water, SDrawingCircle circle, SDrawingOval oval, SDrawingSquare square, SDrawingRectangle rectangle, SDrawingTriangle triangle, SDrawingIsosceles isosceles, SDrawingRightAngled rightAngled, SDrawingEquilateralTriangle equilateralTriangle, SDrawingCone cone, SDrawingCylinder cylinder, SDrawingPyramid pyramid, SDrawingUdParabola udParabola, SDrawingLrParabola lrParabola, SDrawingSine sine, SDrawingTan tan, SDrawingHyperbola hyperbola, SDrawingHyperbola2 hyperbola2, SDrawingAnyFunc anyFunc, SDrawingCube cube, SDrawingBall ball)
	{
		SDrawing obj = new SDrawing();
		obj.drawingType = drawingType;
		obj.stroke = stroke;
		obj.stress = stress;
		obj.brush = brush;
		obj.eraser = eraser;
		obj.fullLine = fullLine;
		obj.imaginaryLine = imaginaryLine;
		obj.arrowsLine = arrowsLine;
		obj.coord = coord;
		obj.mark = mark;
		obj.water = water;
		obj.circle = circle;
		obj.oval = oval;
		obj.square = square;
		obj.rectangle = rectangle;
		obj.triangle = triangle;
		obj.isosceles = isosceles;
		obj.rightAngled = rightAngled;
		obj.equilateralTriangle = equilateralTriangle;
		obj.cone = cone;
		obj.cylinder = cylinder;
		obj.pyramid = pyramid;
		obj.udParabola = udParabola;
		obj.lrParabola = lrParabola;
		obj.sine = sine;
		obj.tan = tan;
		obj.hyperbola = hyperbola;
		obj.hyperbola2 = hyperbola2;
		obj.anyFunc = anyFunc;
		obj.cube = cube;
		obj.ball = ball;
		return obj;
	}

	public SDrawing clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SDrawing _other_)
	{
		this.drawingType = _other_.drawingType;
		this.stroke = _other_.stroke;
		this.stress = _other_.stress;
		this.brush = _other_.brush;
		this.eraser = _other_.eraser;
		this.fullLine = _other_.fullLine;
		this.imaginaryLine = _other_.imaginaryLine;
		this.arrowsLine = _other_.arrowsLine;
		this.coord = _other_.coord;
		this.mark = _other_.mark;
		this.water = _other_.water;
		this.circle = _other_.circle;
		this.oval = _other_.oval;
		this.square = _other_.square;
		this.rectangle = _other_.rectangle;
		this.triangle = _other_.triangle;
		this.isosceles = _other_.isosceles;
		this.rightAngled = _other_.rightAngled;
		this.equilateralTriangle = _other_.equilateralTriangle;
		this.cone = _other_.cone;
		this.cylinder = _other_.cylinder;
		this.pyramid = _other_.pyramid;
		this.udParabola = _other_.udParabola;
		this.lrParabola = _other_.lrParabola;
		this.sine = _other_.sine;
		this.tan = _other_.tan;
		this.hyperbola = _other_.hyperbola;
		this.hyperbola2 = _other_.hyperbola2;
		this.anyFunc = _other_.anyFunc;
		this.cube = _other_.cube;
		this.ball = _other_.ball;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("drawingType"))
			drawingType = _json_.getInteger("drawingType");
		if (_json_.containsKey("stroke"))
			stroke = SDrawingStroke.load(_json_.getJSONObject("stroke"));
		if (_json_.containsKey("stress"))
			stress = SDrawingStress.load(_json_.getJSONObject("stress"));
		if (_json_.containsKey("brush"))
			brush = SDrawingBrush.load(_json_.getJSONObject("brush"));
		if (_json_.containsKey("eraser"))
			eraser = SDrawingEraser.load(_json_.getJSONObject("eraser"));
		if (_json_.containsKey("fullLine"))
			fullLine = SDrawingFullLine.load(_json_.getJSONObject("fullLine"));
		if (_json_.containsKey("imaginaryLine"))
			imaginaryLine = SDrawingImaginaryLine.load(_json_.getJSONObject("imaginaryLine"));
		if (_json_.containsKey("arrowsLine"))
			arrowsLine = SDrawingArrowsLine.load(_json_.getJSONObject("arrowsLine"));
		if (_json_.containsKey("coord"))
			coord = SDrawingCoord.load(_json_.getJSONObject("coord"));
		if (_json_.containsKey("mark"))
			mark = SDrawingMark.load(_json_.getJSONObject("mark"));
		if (_json_.containsKey("water"))
			water = SDrawingWater.load(_json_.getJSONObject("water"));
		if (_json_.containsKey("circle"))
			circle = SDrawingCircle.load(_json_.getJSONObject("circle"));
		if (_json_.containsKey("oval"))
			oval = SDrawingOval.load(_json_.getJSONObject("oval"));
		if (_json_.containsKey("square"))
			square = SDrawingSquare.load(_json_.getJSONObject("square"));
		if (_json_.containsKey("rectangle"))
			rectangle = SDrawingRectangle.load(_json_.getJSONObject("rectangle"));
		if (_json_.containsKey("triangle"))
			triangle = SDrawingTriangle.load(_json_.getJSONObject("triangle"));
		if (_json_.containsKey("isosceles"))
			isosceles = SDrawingIsosceles.load(_json_.getJSONObject("isosceles"));
		if (_json_.containsKey("rightAngled"))
			rightAngled = SDrawingRightAngled.load(_json_.getJSONObject("rightAngled"));
		if (_json_.containsKey("equilateralTriangle"))
			equilateralTriangle = SDrawingEquilateralTriangle.load(_json_.getJSONObject("equilateralTriangle"));
		if (_json_.containsKey("cone"))
			cone = SDrawingCone.load(_json_.getJSONObject("cone"));
		if (_json_.containsKey("cylinder"))
			cylinder = SDrawingCylinder.load(_json_.getJSONObject("cylinder"));
		if (_json_.containsKey("pyramid"))
			pyramid = SDrawingPyramid.load(_json_.getJSONObject("pyramid"));
		if (_json_.containsKey("udParabola"))
			udParabola = SDrawingUdParabola.load(_json_.getJSONObject("udParabola"));
		if (_json_.containsKey("lrParabola"))
			lrParabola = SDrawingLrParabola.load(_json_.getJSONObject("lrParabola"));
		if (_json_.containsKey("sine"))
			sine = SDrawingSine.load(_json_.getJSONObject("sine"));
		if (_json_.containsKey("tan"))
			tan = SDrawingTan.load(_json_.getJSONObject("tan"));
		if (_json_.containsKey("hyperbola"))
			hyperbola = SDrawingHyperbola.load(_json_.getJSONObject("hyperbola"));
		if (_json_.containsKey("hyperbola2"))
			hyperbola2 = SDrawingHyperbola2.load(_json_.getJSONObject("hyperbola2"));
		if (_json_.containsKey("anyFunc"))
			anyFunc = SDrawingAnyFunc.load(_json_.getJSONObject("anyFunc"));
		if (_json_.containsKey("cube"))
			cube = SDrawingCube.load(_json_.getJSONObject("cube"));
		if (_json_.containsKey("ball"))
			ball = SDrawingBall.load(_json_.getJSONObject("ball"));
	}

	public static SDrawing load(String str)
	{
		try
		{
			SDrawing obj = new SDrawing();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SDrawing load(JSONObject json)
	{
		try
		{
			SDrawing obj = new SDrawing();
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
			if (drawingType != null)
				_json_.put("drawingType", drawingType);
			if (stroke != null)
				_json_.put("stroke", stroke.saveToJson());
			if (stress != null)
				_json_.put("stress", stress.saveToJson());
			if (brush != null)
				_json_.put("brush", brush.saveToJson());
			if (eraser != null)
				_json_.put("eraser", eraser.saveToJson());
			if (fullLine != null)
				_json_.put("fullLine", fullLine.saveToJson());
			if (imaginaryLine != null)
				_json_.put("imaginaryLine", imaginaryLine.saveToJson());
			if (arrowsLine != null)
				_json_.put("arrowsLine", arrowsLine.saveToJson());
			if (coord != null)
				_json_.put("coord", coord.saveToJson());
			if (mark != null)
				_json_.put("mark", mark.saveToJson());
			if (water != null)
				_json_.put("water", water.saveToJson());
			if (circle != null)
				_json_.put("circle", circle.saveToJson());
			if (oval != null)
				_json_.put("oval", oval.saveToJson());
			if (square != null)
				_json_.put("square", square.saveToJson());
			if (rectangle != null)
				_json_.put("rectangle", rectangle.saveToJson());
			if (triangle != null)
				_json_.put("triangle", triangle.saveToJson());
			if (isosceles != null)
				_json_.put("isosceles", isosceles.saveToJson());
			if (rightAngled != null)
				_json_.put("rightAngled", rightAngled.saveToJson());
			if (equilateralTriangle != null)
				_json_.put("equilateralTriangle", equilateralTriangle.saveToJson());
			if (cone != null)
				_json_.put("cone", cone.saveToJson());
			if (cylinder != null)
				_json_.put("cylinder", cylinder.saveToJson());
			if (pyramid != null)
				_json_.put("pyramid", pyramid.saveToJson());
			if (udParabola != null)
				_json_.put("udParabola", udParabola.saveToJson());
			if (lrParabola != null)
				_json_.put("lrParabola", lrParabola.saveToJson());
			if (sine != null)
				_json_.put("sine", sine.saveToJson());
			if (tan != null)
				_json_.put("tan", tan.saveToJson());
			if (hyperbola != null)
				_json_.put("hyperbola", hyperbola.saveToJson());
			if (hyperbola2 != null)
				_json_.put("hyperbola2", hyperbola2.saveToJson());
			if (anyFunc != null)
				_json_.put("anyFunc", anyFunc.saveToJson());
			if (cube != null)
				_json_.put("cube", cube.saveToJson());
			if (ball != null)
				_json_.put("ball", ball.saveToJson());
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SDrawing> loadList(JSONArray ja)
	{
		try
		{
			List<SDrawing> list = new ArrayList<SDrawing>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SDrawing item = SDrawing.load(jo);
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

	public static JSONArray saveList(List<SDrawing> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SDrawing item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Drawing _proto_)
	{
		if (_proto_.hasDrawingType())
			drawingType = _proto_.getDrawingType();
		if (_proto_.hasStroke())
			stroke = SDrawingStroke.load(_proto_.getStroke());
		if (_proto_.hasStress())
			stress = SDrawingStress.load(_proto_.getStress());
		if (_proto_.hasBrush())
			brush = SDrawingBrush.load(_proto_.getBrush());
		if (_proto_.hasEraser())
			eraser = SDrawingEraser.load(_proto_.getEraser());
		if (_proto_.hasFullLine())
			fullLine = SDrawingFullLine.load(_proto_.getFullLine());
		if (_proto_.hasImaginaryLine())
			imaginaryLine = SDrawingImaginaryLine.load(_proto_.getImaginaryLine());
		if (_proto_.hasArrowsLine())
			arrowsLine = SDrawingArrowsLine.load(_proto_.getArrowsLine());
		if (_proto_.hasCoord())
			coord = SDrawingCoord.load(_proto_.getCoord());
		if (_proto_.hasMark())
			mark = SDrawingMark.load(_proto_.getMark());
		if (_proto_.hasWater())
			water = SDrawingWater.load(_proto_.getWater());
		if (_proto_.hasCircle())
			circle = SDrawingCircle.load(_proto_.getCircle());
		if (_proto_.hasOval())
			oval = SDrawingOval.load(_proto_.getOval());
		if (_proto_.hasSquare())
			square = SDrawingSquare.load(_proto_.getSquare());
		if (_proto_.hasRectangle())
			rectangle = SDrawingRectangle.load(_proto_.getRectangle());
		if (_proto_.hasTriangle())
			triangle = SDrawingTriangle.load(_proto_.getTriangle());
		if (_proto_.hasIsosceles())
			isosceles = SDrawingIsosceles.load(_proto_.getIsosceles());
		if (_proto_.hasRightAngled())
			rightAngled = SDrawingRightAngled.load(_proto_.getRightAngled());
		if (_proto_.hasEquilateralTriangle())
			equilateralTriangle = SDrawingEquilateralTriangle.load(_proto_.getEquilateralTriangle());
		if (_proto_.hasCone())
			cone = SDrawingCone.load(_proto_.getCone());
		if (_proto_.hasCylinder())
			cylinder = SDrawingCylinder.load(_proto_.getCylinder());
		if (_proto_.hasPyramid())
			pyramid = SDrawingPyramid.load(_proto_.getPyramid());
		if (_proto_.hasUdParabola())
			udParabola = SDrawingUdParabola.load(_proto_.getUdParabola());
		if (_proto_.hasLrParabola())
			lrParabola = SDrawingLrParabola.load(_proto_.getLrParabola());
		if (_proto_.hasSine())
			sine = SDrawingSine.load(_proto_.getSine());
		if (_proto_.hasTan())
			tan = SDrawingTan.load(_proto_.getTan());
		if (_proto_.hasHyperbola())
			hyperbola = SDrawingHyperbola.load(_proto_.getHyperbola());
		if (_proto_.hasHyperbola2())
			hyperbola2 = SDrawingHyperbola2.load(_proto_.getHyperbola2());
		if (_proto_.hasAnyFunc())
			anyFunc = SDrawingAnyFunc.load(_proto_.getAnyFunc());
		if (_proto_.hasCube())
			cube = SDrawingCube.load(_proto_.getCube());
		if (_proto_.hasBall())
			ball = SDrawingBall.load(_proto_.getBall());
	}

	public static SDrawing load(byte[] bytes)
	{
		try
		{
			SDrawing obj = new SDrawing();
			obj.parse(Drawing.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SDrawing load(Drawing proto)
	{
		try
		{
			SDrawing obj = new SDrawing();
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
	public Drawing saveToProto()
	{
		Drawing.Builder _builder_ = Drawing.newBuilder();
		if (drawingType != null && !drawingType.equals(Drawing.getDefaultInstance().getDrawingType()))
			_builder_.setDrawingType(drawingType);
		if (stroke != null)
			_builder_.setStroke(stroke.saveToProto());
		if (stress != null)
			_builder_.setStress(stress.saveToProto());
		if (brush != null)
			_builder_.setBrush(brush.saveToProto());
		if (eraser != null)
			_builder_.setEraser(eraser.saveToProto());
		if (fullLine != null)
			_builder_.setFullLine(fullLine.saveToProto());
		if (imaginaryLine != null)
			_builder_.setImaginaryLine(imaginaryLine.saveToProto());
		if (arrowsLine != null)
			_builder_.setArrowsLine(arrowsLine.saveToProto());
		if (coord != null)
			_builder_.setCoord(coord.saveToProto());
		if (mark != null)
			_builder_.setMark(mark.saveToProto());
		if (water != null)
			_builder_.setWater(water.saveToProto());
		if (circle != null)
			_builder_.setCircle(circle.saveToProto());
		if (oval != null)
			_builder_.setOval(oval.saveToProto());
		if (square != null)
			_builder_.setSquare(square.saveToProto());
		if (rectangle != null)
			_builder_.setRectangle(rectangle.saveToProto());
		if (triangle != null)
			_builder_.setTriangle(triangle.saveToProto());
		if (isosceles != null)
			_builder_.setIsosceles(isosceles.saveToProto());
		if (rightAngled != null)
			_builder_.setRightAngled(rightAngled.saveToProto());
		if (equilateralTriangle != null)
			_builder_.setEquilateralTriangle(equilateralTriangle.saveToProto());
		if (cone != null)
			_builder_.setCone(cone.saveToProto());
		if (cylinder != null)
			_builder_.setCylinder(cylinder.saveToProto());
		if (pyramid != null)
			_builder_.setPyramid(pyramid.saveToProto());
		if (udParabola != null)
			_builder_.setUdParabola(udParabola.saveToProto());
		if (lrParabola != null)
			_builder_.setLrParabola(lrParabola.saveToProto());
		if (sine != null)
			_builder_.setSine(sine.saveToProto());
		if (tan != null)
			_builder_.setTan(tan.saveToProto());
		if (hyperbola != null)
			_builder_.setHyperbola(hyperbola.saveToProto());
		if (hyperbola2 != null)
			_builder_.setHyperbola2(hyperbola2.saveToProto());
		if (anyFunc != null)
			_builder_.setAnyFunc(anyFunc.saveToProto());
		if (cube != null)
			_builder_.setCube(cube.saveToProto());
		if (ball != null)
			_builder_.setBall(ball.saveToProto());
		return _builder_.build();
	}
}
