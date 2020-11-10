package com.lys.board.utils;

import android.graphics.Matrix;

import com.alibaba.fastjson.JSONObject;
import com.lys.protobuf.SPoint;

public class LysBoardPoint
{
	public Float x = 0F;
	public Float y = 0F;
	public Float pressure = 1F;
	public Long timestamp = 0L;

	public LysBoardPoint()
	{
	}

	public LysBoardPoint(float x, float y)
	{
		this.x = x;
		this.y = y;
	}

	public LysBoardPoint(float x, float y, float pressure)
	{
		this.x = x;
		this.y = y;
		this.pressure = pressure;
	}

	public LysBoardPoint(float x, float y, float pressure, long timestamp)
	{
		this.x = x;
		this.y = y;
		this.pressure = pressure;
		this.timestamp = timestamp;
	}

	public LysBoardPoint(LysBoardPoint other)
	{
		this.x = other.x;
		this.y = other.y;
		this.pressure = other.pressure;
		this.timestamp = other.timestamp;
	}

	public LysBoardPoint(JSONObject json)
	{
		this.x = json.getFloat("x");
		this.y = json.getFloat("y");
		this.pressure = json.getFloat("pressure");
		this.timestamp = json.getLong("timestamp");
	}

	public LysBoardPoint(SPoint proto)
	{
		this.x = proto.x;
		this.y = proto.y;
		this.pressure = proto.pressure;
		this.timestamp = proto.timestamp;
	}

	public LysBoardPoint clone()
	{
		return new LysBoardPoint(this);
	}

	public boolean equals(LysBoardPoint other)
	{
		return this.x.equals(other.x) && this.y.equals(other.y);
	}

	public float distanceX(LysBoardPoint other)
	{
		return Math.abs(this.x - other.x);
	}

	public float distanceY(LysBoardPoint other)
	{
		return Math.abs(this.y - other.y);
	}

	public float distance(LysBoardPoint other)
	{
		return (float) Math.sqrt(Math.pow(this.x - other.x, 2) + Math.pow(this.y - other.y, 2));
	}

	public LysBoardPoint middle(LysBoardPoint other)
	{
		LysBoardPoint ret = new LysBoardPoint();
		ret.x = (this.x + other.x) / 2;
		ret.y = (this.y + other.y) / 2;
		return ret;
	}

	public boolean leftOf(LysBoardPoint other)
	{
		return this.x < other.x && this.y.equals(other.y);
	}

	public boolean rightOf(LysBoardPoint other)
	{
		return this.x > other.x && this.y.equals(other.y);
	}

	public boolean topOf(LysBoardPoint other)
	{
		return this.x.equals(other.x) && this.y < other.y;
	}

	public boolean bottomOf(LysBoardPoint other)
	{
		return this.x.equals(other.x) && this.y > other.y;
	}

	public boolean leftTopOf(LysBoardPoint other)
	{
		return this.x < other.x && this.y < other.y;
	}

	public boolean leftBottomOf(LysBoardPoint other)
	{
		return this.x < other.x && this.y > other.y;
	}

	public boolean rightTopOf(LysBoardPoint other)
	{
		return this.x > other.x && this.y < other.y;
	}

	public boolean rightBottomOf(LysBoardPoint other)
	{
		return this.x > other.x && this.y > other.y;
	}

	public LysBoardPoint applyMatrix(Matrix matrix)
	{
		float[] dst = new float[2];
		float[] src = {this.x, this.y};
		matrix.mapPoints(dst, src);
		LysBoardPoint out = this.clone();
		out.x = dst[0];
		out.y = dst[1];
		return out;
	}

	public LysBoardPoint translate(float dx, float dy)
	{
		Matrix matrix = new Matrix();
		matrix.setTranslate(dx, dy);
		return applyMatrix(matrix);
	}

	public LysBoardPoint rotate(float degrees, LysBoardPoint p)
	{
		Matrix matrix = new Matrix();
		matrix.setRotate(degrees, p.x, p.y);
		return applyMatrix(matrix);
	}

	public LysBoardPoint scale(float sx, float sy, LysBoardPoint p)
	{
		Matrix matrix = new Matrix();
		matrix.setScale(sx, sy, p.x, p.y);
		return applyMatrix(matrix);
	}

	public LysBoardPoint scale(float s, LysBoardPoint p)
	{
		Matrix matrix = new Matrix();
		matrix.setScale(s, s, p.x, p.y);
		return applyMatrix(matrix);
	}

	public JSONObject saveToJson()
	{
		JSONObject json = new JSONObject(true);
		json.put("x", x);
		json.put("y", y);
		json.put("pressure", pressure);
		json.put("timestamp", timestamp.toString());
		return json;
	}

	public SPoint saveToProto()
	{
		SPoint point = new SPoint();
		point.x = x;
		point.y = y;
		point.pressure = pressure;
		point.timestamp = timestamp;
		return point;
	}
}
