package com.lys.board.dawing;

import android.graphics.Canvas;

import com.alibaba.fastjson.JSONObject;
import com.lys.board.utils.LysBoardPoint;
import com.lys.protobuf.SDrawing;

public abstract class LysBoardDrawing
{
	public static LysBoardDrawing create(int drawingType)
	{
		switch (drawingType)
		{
		case LysBoardDrawingType.Stroke:
			return new LysBoardDrawingStroke();
		case LysBoardDrawingType.Stress:
			return new LysBoardDrawingStress();
		case LysBoardDrawingType.Brush:
			return new LysBoardDrawingBrush();
		case LysBoardDrawingType.Eraser:
			return new LysBoardDrawingEraser();
		case LysBoardDrawingType.FullLine:
			return new LysBoardDrawingFullLine();
		case LysBoardDrawingType.ImaginaryLine:
			return new LysBoardDrawingImaginaryLine();
		case LysBoardDrawingType.ArrowsLine:
			return new LysBoardDrawingArrowsLine();
		case LysBoardDrawingType.Coord:
			return new LysBoardDrawingCoord();
		case LysBoardDrawingType.Mark:
			return new LysBoardDrawingMark();
		case LysBoardDrawingType.Water:
			return new LysBoardDrawingWater();
		case LysBoardDrawingType.Circle:
			return new LysBoardDrawingCircle();
		case LysBoardDrawingType.Oval:
			return new LysBoardDrawingOval();
		case LysBoardDrawingType.Square:
			return new LysBoardDrawingSquare();
		case LysBoardDrawingType.Rectangle:
			return new LysBoardDrawingRectangle();
		case LysBoardDrawingType.Triangle:
			return new LysBoardDrawingTriangle();
		case LysBoardDrawingType.Isosceles:
			return new LysBoardDrawingIsosceles();
		case LysBoardDrawingType.RightAngled:
			return new LysBoardDrawingRightAngled();
		case LysBoardDrawingType.EquilateralTriangle:
			return new LysBoardDrawingEquilateralTriangle();
		case LysBoardDrawingType.Cone:
			return new LysBoardDrawingCone();
		case LysBoardDrawingType.Cylinder:
			return new LysBoardDrawingCylinder();
		case LysBoardDrawingType.Pyramid:
			return new LysBoardDrawingPyramid();
		case LysBoardDrawingType.UdParabola:
			return new LysBoardDrawingUdParabola();
		case LysBoardDrawingType.LrParabola:
			return new LysBoardDrawingLrParabola();
		case LysBoardDrawingType.Sine:
			return new LysBoardDrawingSine();
		case LysBoardDrawingType.Tan:
			return new LysBoardDrawingTan();
		case LysBoardDrawingType.Hyperbola:
			return new LysBoardDrawingHyperbola();
		case LysBoardDrawingType.Hyperbola2:
			return new LysBoardDrawingHyperbola2();
		case LysBoardDrawingType.AnyFunc:
			return new LysBoardDrawingAnyFunc();
		case LysBoardDrawingType.Cube:
			return new LysBoardDrawingCube();
		case LysBoardDrawingType.Ball:
			return new LysBoardDrawingBall();
		}
		return null;
	}

	public static LysBoardDrawing create(int drawingType, int paintColor, float strokeWidth, String anyParam)
	{
		LysBoardDrawing drawing = create(drawingType);
		drawing.init(paintColor, strokeWidth);
		drawing.setAnyParam(anyParam);
		return drawing;
	}

	public static LysBoardDrawing create(JSONObject json)
	{
		LysBoardDrawing drawing = create(json.getInteger("drawingType"));
		drawing.init(json);
		return drawing;
	}

	public static LysBoardDrawing create(SDrawing proto)
	{
		LysBoardDrawing drawing = create(proto.drawingType);
		drawing.init(proto);
		return drawing;
	}

	public abstract void init(int paintColor, float strokeWidth);

	public void setAnyParam(String anyParam) {}

	public abstract void init(JSONObject json);

	public abstract void init(SDrawing proto);

	public abstract void downPoint(LysBoardPoint point);

	public abstract void movePoint(LysBoardPoint point);

	public abstract void upPoint(LysBoardPoint point);

	public abstract void draw(Canvas canvas);

	// 绘制过程中临时显示的东西
	public abstract void drawGizmo(Canvas canvas);

	public abstract JSONObject saveToJson();

	public abstract SDrawing saveToProto();

}
