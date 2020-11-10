package com.lys.board.dawing;

public class LysBoardDrawingType
{
	public static final int Dummy = 0; // 占位

	public static final int Stroke = 1; // 钢笔
	public static final int Stress = 2; // 钢笔2
	public static final int Brush = 3; // 软笔
	public static final int Eraser = 4; // 橡皮擦

	public static final int FullLine = 5; // 直线
	public static final int ImaginaryLine = 6; // 虚线
	public static final int ArrowsLine = 7; // 向量

	public static final int Coord = 8; // 坐标轴
	public static final int Mark = 9; // 记号笔
	public static final int Water = 30; // 水彩笔

	public static final int Circle = 10; // 圆
	public static final int Oval = 11; // 椭圆
	public static final int Square = 12; // 正方形
	public static final int Rectangle = 13; // 长方形
	public static final int Triangle = 14; // 三角形
	public static final int Isosceles = 15; // 等腰三角形
	public static final int RightAngled = 16; // 直角三角形
	public static final int EquilateralTriangle = 17; // 等边三角形
	public static final int Cone = 18; // 圆锥
	public static final int Cylinder = 19; // 圆柱
	public static final int Pyramid = 20; // 三棱锥

	public static final int UdParabola = 21; // 二次函数
	public static final int LrParabola = 22; // 抛物线
	public static final int Sine = 23; // 正余弦函数
	public static final int Tan = 24; // 正切函数
	public static final int Hyperbola = 25; // 反比例函数
	public static final int Hyperbola2 = 26; // 双曲线
	public static final int AnyFunc = 27; // 任意函数
	public static final int Cube = 28; // 长方体
	public static final int Ball = 29; // 球体

	public static String getName(int drawingType)
	{
		switch (drawingType)
		{
		case LysBoardDrawingType.Dummy:
			return "占位";

		case LysBoardDrawingType.Stroke:
			return "钢笔";
		case LysBoardDrawingType.Stress:
			return "钢笔2";
		case LysBoardDrawingType.Brush:
			return "软笔";
		case LysBoardDrawingType.Eraser:
			return "橡皮擦";

		case LysBoardDrawingType.FullLine:
			return "直线";
		case LysBoardDrawingType.ImaginaryLine:
			return "虚线";
		case LysBoardDrawingType.ArrowsLine:
			return "向量";

		case LysBoardDrawingType.Coord:
			return "坐标轴";
		case LysBoardDrawingType.Mark:
			return "记号笔";
		case LysBoardDrawingType.Water:
			return "水彩笔";

		case LysBoardDrawingType.Circle:
			return "圆";
		case LysBoardDrawingType.Oval:
			return "椭圆";
		case LysBoardDrawingType.Square:
			return "正方形";
		case LysBoardDrawingType.Rectangle:
			return "长方形";
		case LysBoardDrawingType.Triangle:
			return "三角形";
		case LysBoardDrawingType.Isosceles:
			return "等腰三角形";
		case LysBoardDrawingType.RightAngled:
			return "直角三角形";
		case LysBoardDrawingType.EquilateralTriangle:
			return "等边三角形";
		case LysBoardDrawingType.Cone:
			return "圆锥";
		case LysBoardDrawingType.Cylinder:
			return "圆柱";
		case LysBoardDrawingType.Pyramid:
			return "三棱锥";

		case LysBoardDrawingType.UdParabola:
			return "二次函数";
		case LysBoardDrawingType.LrParabola:
			return "抛物线";
		case LysBoardDrawingType.Sine:
			return "正余弦函数";
		case LysBoardDrawingType.Tan:
			return "正切函数";
		case LysBoardDrawingType.Hyperbola:
			return "反比例函数";
		case LysBoardDrawingType.Hyperbola2:
			return "双曲线";
		case LysBoardDrawingType.AnyFunc:
			return "任意函数";
		case LysBoardDrawingType.Cube:
			return "长方体";
		case LysBoardDrawingType.Ball:
			return "球体";
		}
		return "UNKNOW";
	}

}
