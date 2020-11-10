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
import com.lys.protobuf.ProtocolPair2.ImageMessageExtra;

public class SImageMessageExtra extends SPTData<ImageMessageExtra>
{
	private static final SImageMessageExtra DefaultInstance = new SImageMessageExtra();

	public Integer width = 0;
	public Integer height = 0;

	public static SImageMessageExtra create(Integer width, Integer height)
	{
		SImageMessageExtra obj = new SImageMessageExtra();
		obj.width = width;
		obj.height = height;
		return obj;
	}

	public SImageMessageExtra clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SImageMessageExtra _other_)
	{
		this.width = _other_.width;
		this.height = _other_.height;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("width"))
			width = _json_.getInteger("width");
		if (_json_.containsKey("height"))
			height = _json_.getInteger("height");
	}

	public static SImageMessageExtra load(String str)
	{
		try
		{
			SImageMessageExtra obj = new SImageMessageExtra();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SImageMessageExtra load(JSONObject json)
	{
		try
		{
			SImageMessageExtra obj = new SImageMessageExtra();
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
			if (width != null)
				_json_.put("width", width);
			if (height != null)
				_json_.put("height", height);
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SImageMessageExtra> loadList(JSONArray ja)
	{
		try
		{
			List<SImageMessageExtra> list = new ArrayList<SImageMessageExtra>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SImageMessageExtra item = SImageMessageExtra.load(jo);
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

	public static JSONArray saveList(List<SImageMessageExtra> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SImageMessageExtra item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(ImageMessageExtra _proto_)
	{
		if (_proto_.hasWidth())
			width = _proto_.getWidth();
		if (_proto_.hasHeight())
			height = _proto_.getHeight();
	}

	public static SImageMessageExtra load(byte[] bytes)
	{
		try
		{
			SImageMessageExtra obj = new SImageMessageExtra();
			obj.parse(ImageMessageExtra.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SImageMessageExtra load(ImageMessageExtra proto)
	{
		try
		{
			SImageMessageExtra obj = new SImageMessageExtra();
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
	public ImageMessageExtra saveToProto()
	{
		ImageMessageExtra.Builder _builder_ = ImageMessageExtra.newBuilder();
		if (width != null && !width.equals(ImageMessageExtra.getDefaultInstance().getWidth()))
			_builder_.setWidth(width);
		if (height != null && !height.equals(ImageMessageExtra.getDefaultInstance().getHeight()))
			_builder_.setHeight(height);
		return _builder_.build();
	}
}
