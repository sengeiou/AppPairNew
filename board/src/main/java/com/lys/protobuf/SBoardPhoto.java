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
import com.lys.protobuf.ProtocolBoard.BoardPhoto;

public class SBoardPhoto extends SPTData<BoardPhoto>
{
	private static final SBoardPhoto DefaultInstance = new SBoardPhoto();

	public Integer type = 0;
	public String name = null;
	public Integer x = 0;
	public Integer y = 0;
	public Integer rotation = 0;
	public Integer width = 0;
	public Integer height = 0;
	public String cover = null;
	public String url = null;
	public Long duration = 0L;
	public SSelectionGroup selectionGroup = null;
	public SBoardText boardText = null;
	public Boolean hide = false;
	public Boolean notEye = false;
	public Boolean lock = false;

	public static SBoardPhoto create(Integer type, String name, Integer x, Integer y, Integer rotation, Integer width, Integer height, String cover, String url, Long duration, SSelectionGroup selectionGroup, SBoardText boardText, Boolean hide, Boolean notEye, Boolean lock)
	{
		SBoardPhoto obj = new SBoardPhoto();
		obj.type = type;
		obj.name = name;
		obj.x = x;
		obj.y = y;
		obj.rotation = rotation;
		obj.width = width;
		obj.height = height;
		obj.cover = cover;
		obj.url = url;
		obj.duration = duration;
		obj.selectionGroup = selectionGroup;
		obj.boardText = boardText;
		obj.hide = hide;
		obj.notEye = notEye;
		obj.lock = lock;
		return obj;
	}

	public SBoardPhoto clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SBoardPhoto _other_)
	{
		this.type = _other_.type;
		this.name = _other_.name;
		this.x = _other_.x;
		this.y = _other_.y;
		this.rotation = _other_.rotation;
		this.width = _other_.width;
		this.height = _other_.height;
		this.cover = _other_.cover;
		this.url = _other_.url;
		this.duration = _other_.duration;
		this.selectionGroup = _other_.selectionGroup;
		this.boardText = _other_.boardText;
		this.hide = _other_.hide;
		this.notEye = _other_.notEye;
		this.lock = _other_.lock;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("type"))
			type = _json_.getInteger("type");
		if (_json_.containsKey("name"))
			name = _json_.getString("name");
		if (_json_.containsKey("x"))
			x = _json_.getInteger("x");
		if (_json_.containsKey("y"))
			y = _json_.getInteger("y");
		if (_json_.containsKey("rotation"))
			rotation = _json_.getInteger("rotation");
		if (_json_.containsKey("width"))
			width = _json_.getInteger("width");
		if (_json_.containsKey("height"))
			height = _json_.getInteger("height");
		if (_json_.containsKey("cover"))
			cover = _json_.getString("cover");
		if (_json_.containsKey("url"))
			url = _json_.getString("url");
		if (_json_.containsKey("duration"))
			duration = _json_.getLong("duration");
		if (_json_.containsKey("selectionGroup"))
			selectionGroup = SSelectionGroup.load(_json_.getJSONObject("selectionGroup"));
		if (_json_.containsKey("boardText"))
			boardText = SBoardText.load(_json_.getJSONObject("boardText"));
		if (_json_.containsKey("hide"))
			hide = _json_.getBoolean("hide");
		if (_json_.containsKey("notEye"))
			notEye = _json_.getBoolean("notEye");
		if (_json_.containsKey("lock"))
			lock = _json_.getBoolean("lock");
	}

	public static SBoardPhoto load(String str)
	{
		try
		{
			SBoardPhoto obj = new SBoardPhoto();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SBoardPhoto load(JSONObject json)
	{
		try
		{
			SBoardPhoto obj = new SBoardPhoto();
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
			if (name != null)
				_json_.put("name", name);
			if (x != null)
				_json_.put("x", x);
			if (y != null)
				_json_.put("y", y);
			if (rotation != null)
				_json_.put("rotation", rotation);
			if (width != null)
				_json_.put("width", width);
			if (height != null)
				_json_.put("height", height);
			if (cover != null)
				_json_.put("cover", cover);
			if (url != null)
				_json_.put("url", url);
			if (duration != null)
				_json_.put("duration", String.valueOf(duration));
			if (selectionGroup != null)
				_json_.put("selectionGroup", selectionGroup.saveToJson());
			if (boardText != null)
				_json_.put("boardText", boardText.saveToJson());
			if (hide != null)
				_json_.put("hide", hide);
			if (notEye != null)
				_json_.put("notEye", notEye);
			if (lock != null)
				_json_.put("lock", lock);
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SBoardPhoto> loadList(JSONArray ja)
	{
		try
		{
			List<SBoardPhoto> list = new ArrayList<SBoardPhoto>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SBoardPhoto item = SBoardPhoto.load(jo);
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

	public static JSONArray saveList(List<SBoardPhoto> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SBoardPhoto item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(BoardPhoto _proto_)
	{
		if (_proto_.hasType())
			type = _proto_.getType();
		if (_proto_.hasName())
			name = _proto_.getName();
		if (_proto_.hasX())
			x = _proto_.getX();
		if (_proto_.hasY())
			y = _proto_.getY();
		if (_proto_.hasRotation())
			rotation = _proto_.getRotation();
		if (_proto_.hasWidth())
			width = _proto_.getWidth();
		if (_proto_.hasHeight())
			height = _proto_.getHeight();
		if (_proto_.hasCover())
			cover = _proto_.getCover();
		if (_proto_.hasUrl())
			url = _proto_.getUrl();
		if (_proto_.hasDuration())
			duration = _proto_.getDuration();
		if (_proto_.hasSelectionGroup())
			selectionGroup = SSelectionGroup.load(_proto_.getSelectionGroup());
		if (_proto_.hasBoardText())
			boardText = SBoardText.load(_proto_.getBoardText());
		if (_proto_.hasHide())
			hide = _proto_.getHide();
		if (_proto_.hasNotEye())
			notEye = _proto_.getNotEye();
		if (_proto_.hasLock())
			lock = _proto_.getLock();
	}

	public static SBoardPhoto load(byte[] bytes)
	{
		try
		{
			SBoardPhoto obj = new SBoardPhoto();
			obj.parse(BoardPhoto.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SBoardPhoto load(BoardPhoto proto)
	{
		try
		{
			SBoardPhoto obj = new SBoardPhoto();
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
	public BoardPhoto saveToProto()
	{
		BoardPhoto.Builder _builder_ = BoardPhoto.newBuilder();
		if (type != null && !type.equals(BoardPhoto.getDefaultInstance().getType()))
			_builder_.setType(type);
		if (name != null && !name.equals(BoardPhoto.getDefaultInstance().getName()))
			_builder_.setName(name);
		if (x != null && !x.equals(BoardPhoto.getDefaultInstance().getX()))
			_builder_.setX(x);
		if (y != null && !y.equals(BoardPhoto.getDefaultInstance().getY()))
			_builder_.setY(y);
		if (rotation != null && !rotation.equals(BoardPhoto.getDefaultInstance().getRotation()))
			_builder_.setRotation(rotation);
		if (width != null && !width.equals(BoardPhoto.getDefaultInstance().getWidth()))
			_builder_.setWidth(width);
		if (height != null && !height.equals(BoardPhoto.getDefaultInstance().getHeight()))
			_builder_.setHeight(height);
		if (cover != null && !cover.equals(BoardPhoto.getDefaultInstance().getCover()))
			_builder_.setCover(cover);
		if (url != null && !url.equals(BoardPhoto.getDefaultInstance().getUrl()))
			_builder_.setUrl(url);
		if (duration != null && !duration.equals(BoardPhoto.getDefaultInstance().getDuration()))
			_builder_.setDuration(duration);
		if (selectionGroup != null)
			_builder_.setSelectionGroup(selectionGroup.saveToProto());
		if (boardText != null)
			_builder_.setBoardText(boardText.saveToProto());
		if (hide != null && !hide.equals(BoardPhoto.getDefaultInstance().getHide()))
			_builder_.setHide(hide);
		if (notEye != null && !notEye.equals(BoardPhoto.getDefaultInstance().getNotEye()))
			_builder_.setNotEye(notEye);
		if (lock != null && !lock.equals(BoardPhoto.getDefaultInstance().getLock()))
			_builder_.setLock(lock);
		return _builder_.build();
	}
}
