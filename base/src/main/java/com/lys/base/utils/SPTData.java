package com.lys.base.utils;

import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;

public abstract class SPTData<PT extends com.google.protobuf.GeneratedMessage> implements Serializable
{
	public abstract void parse(JSONObject _json_);

	public String saveToStr()
	{
		return saveToJson().toString();
	}

	public abstract JSONObject saveToJson();

	public abstract void parse(PT _proto_);

	public byte[] saveToBytes()
	{
		return saveToProto().toByteArray();
	}

	public abstract PT saveToProto();

	@Override
	public String toString()
	{
		return saveToProto().toString();
	}
}
