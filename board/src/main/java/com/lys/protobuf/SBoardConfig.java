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
import com.lys.protobuf.ProtocolBoard.BoardConfig;

public class SBoardConfig extends SPTData<BoardConfig>
{
	private static final SBoardConfig DefaultInstance = new SBoardConfig();

	public Integer bg = 0;
	public Integer height = 0;
	public Integer result = 0;
	public List<SBoardPhoto> photos = new ArrayList<SBoardPhoto>();

	public static SBoardConfig create(Integer bg, Integer height, Integer result)
	{
		SBoardConfig obj = new SBoardConfig();
		obj.bg = bg;
		obj.height = height;
		obj.result = result;
		return obj;
	}

	public SBoardConfig clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SBoardConfig _other_)
	{
		this.bg = _other_.bg;
		this.height = _other_.height;
		this.result = _other_.result;
		this.photos = _other_.photos;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("bg"))
			bg = _json_.getInteger("bg");
		if (_json_.containsKey("height"))
			height = _json_.getInteger("height");
		if (_json_.containsKey("result"))
			result = _json_.getInteger("result");
		if (_json_.containsKey("photos"))
			photos = SBoardPhoto.loadList(_json_.getJSONArray("photos"));
	}

	public static SBoardConfig load(String str)
	{
		try
		{
			SBoardConfig obj = new SBoardConfig();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SBoardConfig load(JSONObject json)
	{
		try
		{
			SBoardConfig obj = new SBoardConfig();
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
			if (bg != null)
				_json_.put("bg", bg);
			if (height != null)
				_json_.put("height", height);
			if (result != null)
				_json_.put("result", result);
			if (photos != null)
				_json_.put("photos", SBoardPhoto.saveList(photos));
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SBoardConfig> loadList(JSONArray ja)
	{
		try
		{
			List<SBoardConfig> list = new ArrayList<SBoardConfig>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SBoardConfig item = SBoardConfig.load(jo);
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

	public static JSONArray saveList(List<SBoardConfig> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SBoardConfig item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(BoardConfig _proto_)
	{
		if (_proto_.hasBg())
			bg = _proto_.getBg();
		if (_proto_.hasHeight())
			height = _proto_.getHeight();
		if (_proto_.hasResult())
			result = _proto_.getResult();
		for (int i = 0; i < _proto_.getPhotosCount(); i++)
			photos.add(SBoardPhoto.load(_proto_.getPhotos(i)));
	}

	public static SBoardConfig load(byte[] bytes)
	{
		try
		{
			SBoardConfig obj = new SBoardConfig();
			obj.parse(BoardConfig.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SBoardConfig load(BoardConfig proto)
	{
		try
		{
			SBoardConfig obj = new SBoardConfig();
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
	public BoardConfig saveToProto()
	{
		BoardConfig.Builder _builder_ = BoardConfig.newBuilder();
		if (bg != null && !bg.equals(BoardConfig.getDefaultInstance().getBg()))
			_builder_.setBg(bg);
		if (height != null && !height.equals(BoardConfig.getDefaultInstance().getHeight()))
			_builder_.setHeight(height);
		if (result != null && !result.equals(BoardConfig.getDefaultInstance().getResult()))
			_builder_.setResult(result);
		if (photos != null)
			for (SBoardPhoto _value_ : photos)
				_builder_.addPhotos(_value_.saveToProto());
		return _builder_.build();
	}
}
