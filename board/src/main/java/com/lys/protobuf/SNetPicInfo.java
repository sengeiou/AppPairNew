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
import com.lys.protobuf.ProtocolCommon.NetPicInfo;

public class SNetPicInfo extends SPTData<NetPicInfo>
{
	private static final SNetPicInfo DefaultInstance = new SNetPicInfo();

	public Boolean isMovie = false;
	public String type = null;
	public String name = null;
	public String smallUrl = null;
	public Integer smallWidth = 0;
	public Integer smallHeight = 0;
	public String bigUrl = null;
	public Integer bigWidth = 0;
	public Integer bigHeight = 0;
	public String videoUrl = null;
	public Long duration = 0L;

	public static SNetPicInfo create(Boolean isMovie, String type, String name, String smallUrl, Integer smallWidth, Integer smallHeight, String bigUrl, Integer bigWidth, Integer bigHeight, String videoUrl, Long duration)
	{
		SNetPicInfo obj = new SNetPicInfo();
		obj.isMovie = isMovie;
		obj.type = type;
		obj.name = name;
		obj.smallUrl = smallUrl;
		obj.smallWidth = smallWidth;
		obj.smallHeight = smallHeight;
		obj.bigUrl = bigUrl;
		obj.bigWidth = bigWidth;
		obj.bigHeight = bigHeight;
		obj.videoUrl = videoUrl;
		obj.duration = duration;
		return obj;
	}

	public SNetPicInfo clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SNetPicInfo _other_)
	{
		this.isMovie = _other_.isMovie;
		this.type = _other_.type;
		this.name = _other_.name;
		this.smallUrl = _other_.smallUrl;
		this.smallWidth = _other_.smallWidth;
		this.smallHeight = _other_.smallHeight;
		this.bigUrl = _other_.bigUrl;
		this.bigWidth = _other_.bigWidth;
		this.bigHeight = _other_.bigHeight;
		this.videoUrl = _other_.videoUrl;
		this.duration = _other_.duration;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("isMovie"))
			isMovie = _json_.getBoolean("isMovie");
		if (_json_.containsKey("type"))
			type = _json_.getString("type");
		if (_json_.containsKey("name"))
			name = _json_.getString("name");
		if (_json_.containsKey("smallUrl"))
			smallUrl = _json_.getString("smallUrl");
		if (_json_.containsKey("smallWidth"))
			smallWidth = _json_.getInteger("smallWidth");
		if (_json_.containsKey("smallHeight"))
			smallHeight = _json_.getInteger("smallHeight");
		if (_json_.containsKey("bigUrl"))
			bigUrl = _json_.getString("bigUrl");
		if (_json_.containsKey("bigWidth"))
			bigWidth = _json_.getInteger("bigWidth");
		if (_json_.containsKey("bigHeight"))
			bigHeight = _json_.getInteger("bigHeight");
		if (_json_.containsKey("videoUrl"))
			videoUrl = _json_.getString("videoUrl");
		if (_json_.containsKey("duration"))
			duration = _json_.getLong("duration");
	}

	public static SNetPicInfo load(String str)
	{
		try
		{
			SNetPicInfo obj = new SNetPicInfo();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SNetPicInfo load(JSONObject json)
	{
		try
		{
			SNetPicInfo obj = new SNetPicInfo();
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
			if (isMovie != null)
				_json_.put("isMovie", isMovie);
			if (type != null)
				_json_.put("type", type);
			if (name != null)
				_json_.put("name", name);
			if (smallUrl != null)
				_json_.put("smallUrl", smallUrl);
			if (smallWidth != null)
				_json_.put("smallWidth", smallWidth);
			if (smallHeight != null)
				_json_.put("smallHeight", smallHeight);
			if (bigUrl != null)
				_json_.put("bigUrl", bigUrl);
			if (bigWidth != null)
				_json_.put("bigWidth", bigWidth);
			if (bigHeight != null)
				_json_.put("bigHeight", bigHeight);
			if (videoUrl != null)
				_json_.put("videoUrl", videoUrl);
			if (duration != null)
				_json_.put("duration", String.valueOf(duration));
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SNetPicInfo> loadList(JSONArray ja)
	{
		try
		{
			List<SNetPicInfo> list = new ArrayList<SNetPicInfo>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SNetPicInfo item = SNetPicInfo.load(jo);
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

	public static JSONArray saveList(List<SNetPicInfo> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SNetPicInfo item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(NetPicInfo _proto_)
	{
		if (_proto_.hasIsMovie())
			isMovie = _proto_.getIsMovie();
		if (_proto_.hasType())
			type = _proto_.getType();
		if (_proto_.hasName())
			name = _proto_.getName();
		if (_proto_.hasSmallUrl())
			smallUrl = _proto_.getSmallUrl();
		if (_proto_.hasSmallWidth())
			smallWidth = _proto_.getSmallWidth();
		if (_proto_.hasSmallHeight())
			smallHeight = _proto_.getSmallHeight();
		if (_proto_.hasBigUrl())
			bigUrl = _proto_.getBigUrl();
		if (_proto_.hasBigWidth())
			bigWidth = _proto_.getBigWidth();
		if (_proto_.hasBigHeight())
			bigHeight = _proto_.getBigHeight();
		if (_proto_.hasVideoUrl())
			videoUrl = _proto_.getVideoUrl();
		if (_proto_.hasDuration())
			duration = _proto_.getDuration();
	}

	public static SNetPicInfo load(byte[] bytes)
	{
		try
		{
			SNetPicInfo obj = new SNetPicInfo();
			obj.parse(NetPicInfo.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SNetPicInfo load(NetPicInfo proto)
	{
		try
		{
			SNetPicInfo obj = new SNetPicInfo();
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
	public NetPicInfo saveToProto()
	{
		NetPicInfo.Builder _builder_ = NetPicInfo.newBuilder();
		if (isMovie != null && !isMovie.equals(NetPicInfo.getDefaultInstance().getIsMovie()))
			_builder_.setIsMovie(isMovie);
		if (type != null && !type.equals(NetPicInfo.getDefaultInstance().getType()))
			_builder_.setType(type);
		if (name != null && !name.equals(NetPicInfo.getDefaultInstance().getName()))
			_builder_.setName(name);
		if (smallUrl != null && !smallUrl.equals(NetPicInfo.getDefaultInstance().getSmallUrl()))
			_builder_.setSmallUrl(smallUrl);
		if (smallWidth != null && !smallWidth.equals(NetPicInfo.getDefaultInstance().getSmallWidth()))
			_builder_.setSmallWidth(smallWidth);
		if (smallHeight != null && !smallHeight.equals(NetPicInfo.getDefaultInstance().getSmallHeight()))
			_builder_.setSmallHeight(smallHeight);
		if (bigUrl != null && !bigUrl.equals(NetPicInfo.getDefaultInstance().getBigUrl()))
			_builder_.setBigUrl(bigUrl);
		if (bigWidth != null && !bigWidth.equals(NetPicInfo.getDefaultInstance().getBigWidth()))
			_builder_.setBigWidth(bigWidth);
		if (bigHeight != null && !bigHeight.equals(NetPicInfo.getDefaultInstance().getBigHeight()))
			_builder_.setBigHeight(bigHeight);
		if (videoUrl != null && !videoUrl.equals(NetPicInfo.getDefaultInstance().getVideoUrl()))
			_builder_.setVideoUrl(videoUrl);
		if (duration != null && !duration.equals(NetPicInfo.getDefaultInstance().getDuration()))
			_builder_.setDuration(duration);
		return _builder_.build();
	}
}
