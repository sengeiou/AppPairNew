package com.lys.board.utils;

import com.alibaba.fastjson.JSONObject;
import com.lys.protobuf.SPointRadius;

public class LysBoardPointRadius extends LysBoardPoint
{
	public Float radius = 0F;

	public LysBoardPointRadius(LysBoardPoint other, float radius)
	{
		super(other);
		this.radius = radius;
	}

	public LysBoardPointRadius(JSONObject json)
	{
		super(json);
		this.radius = json.getFloat("radius");
	}

	public LysBoardPointRadius(SPointRadius proto)
	{
		super(proto.point);
		this.radius = proto.radius;
	}

	@Override
	public JSONObject saveToJson()
	{
		JSONObject json = super.saveToJson();
		json.put("radius", radius);
		return json;
	}

	public SPointRadius saveToProto2()
	{
		SPointRadius pointRadius = new SPointRadius();
		pointRadius.point = saveToProto();
		pointRadius.radius = radius;
		return pointRadius;
	}

}
