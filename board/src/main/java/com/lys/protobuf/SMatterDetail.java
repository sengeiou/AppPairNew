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
import com.lys.protobuf.ProtocolShop.MatterDetail;

public class SMatterDetail extends SPTData<MatterDetail>
{
	private static final SMatterDetail DefaultInstance = new SMatterDetail();

	public /*SMatterDetailType*/ Integer type = MatterDetail.getDefaultInstance().getType().getNumber();
	public String imgUrl = null;
	public Integer imgWidth = 0;
	public Integer imgHeight = 0;
	public String videoUrl = null;
	public Long videoDuration = 0L;
	public SPTask task = null;

	public static SMatterDetail create(Integer type, String imgUrl, Integer imgWidth, Integer imgHeight, String videoUrl, Long videoDuration, SPTask task)
	{
		SMatterDetail obj = new SMatterDetail();
		obj.type = type;
		obj.imgUrl = imgUrl;
		obj.imgWidth = imgWidth;
		obj.imgHeight = imgHeight;
		obj.videoUrl = videoUrl;
		obj.videoDuration = videoDuration;
		obj.task = task;
		return obj;
	}

	public SMatterDetail clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SMatterDetail _other_)
	{
		this.type = _other_.type;
		this.imgUrl = _other_.imgUrl;
		this.imgWidth = _other_.imgWidth;
		this.imgHeight = _other_.imgHeight;
		this.videoUrl = _other_.videoUrl;
		this.videoDuration = _other_.videoDuration;
		this.task = _other_.task;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("type"))
			type = _json_.getInteger("type");
		if (_json_.containsKey("imgUrl"))
			imgUrl = _json_.getString("imgUrl");
		if (_json_.containsKey("imgWidth"))
			imgWidth = _json_.getInteger("imgWidth");
		if (_json_.containsKey("imgHeight"))
			imgHeight = _json_.getInteger("imgHeight");
		if (_json_.containsKey("videoUrl"))
			videoUrl = _json_.getString("videoUrl");
		if (_json_.containsKey("videoDuration"))
			videoDuration = _json_.getLong("videoDuration");
		if (_json_.containsKey("task"))
			task = SPTask.load(_json_.getJSONObject("task"));
	}

	public static SMatterDetail load(String str)
	{
		try
		{
			SMatterDetail obj = new SMatterDetail();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SMatterDetail load(JSONObject json)
	{
		try
		{
			SMatterDetail obj = new SMatterDetail();
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
			if (type != null)
				_json_.put("type", type);
			if (imgUrl != null)
				_json_.put("imgUrl", imgUrl);
			if (imgWidth != null)
				_json_.put("imgWidth", imgWidth);
			if (imgHeight != null)
				_json_.put("imgHeight", imgHeight);
			if (videoUrl != null)
				_json_.put("videoUrl", videoUrl);
			if (videoDuration != null)
				_json_.put("videoDuration", String.valueOf(videoDuration));
			if (task != null)
				_json_.put("task", task.saveToJson());
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SMatterDetail> loadList(JSONArray ja)
	{
		try
		{
			List<SMatterDetail> list = new ArrayList<SMatterDetail>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SMatterDetail item = SMatterDetail.load(jo);
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

	public static JSONArray saveList(List<SMatterDetail> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SMatterDetail item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(MatterDetail _proto_)
	{
		if (_proto_.hasType())
			type = _proto_.getType().getNumber();
		if (_proto_.hasImgUrl())
			imgUrl = _proto_.getImgUrl();
		if (_proto_.hasImgWidth())
			imgWidth = _proto_.getImgWidth();
		if (_proto_.hasImgHeight())
			imgHeight = _proto_.getImgHeight();
		if (_proto_.hasVideoUrl())
			videoUrl = _proto_.getVideoUrl();
		if (_proto_.hasVideoDuration())
			videoDuration = _proto_.getVideoDuration();
		if (_proto_.hasTask())
			task = SPTask.load(_proto_.getTask());
	}

	public static SMatterDetail load(byte[] bytes)
	{
		try
		{
			SMatterDetail obj = new SMatterDetail();
			obj.parse(MatterDetail.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SMatterDetail load(MatterDetail proto)
	{
		try
		{
			SMatterDetail obj = new SMatterDetail();
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
	public MatterDetail saveToProto()
	{
		MatterDetail.Builder _builder_ = MatterDetail.newBuilder();
		if (type != null && MatterDetail.getDefaultInstance().getType().getNumber() != type)
			_builder_.setType(ProtocolShop.MatterDetailType.valueOf(type));
		if (imgUrl != null && !imgUrl.equals(MatterDetail.getDefaultInstance().getImgUrl()))
			_builder_.setImgUrl(imgUrl);
		if (imgWidth != null && !imgWidth.equals(MatterDetail.getDefaultInstance().getImgWidth()))
			_builder_.setImgWidth(imgWidth);
		if (imgHeight != null && !imgHeight.equals(MatterDetail.getDefaultInstance().getImgHeight()))
			_builder_.setImgHeight(imgHeight);
		if (videoUrl != null && !videoUrl.equals(MatterDetail.getDefaultInstance().getVideoUrl()))
			_builder_.setVideoUrl(videoUrl);
		if (videoDuration != null && !videoDuration.equals(MatterDetail.getDefaultInstance().getVideoDuration()))
			_builder_.setVideoDuration(videoDuration);
		if (task != null)
			_builder_.setTask(task.saveToProto());
		return _builder_.build();
	}
}
