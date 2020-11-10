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
import com.lys.protobuf.ProtocolBoard.PhotoAddParam;

public class SPhotoAddParam extends SPTData<PhotoAddParam>
{
	private static final SPhotoAddParam DefaultInstance = new SPhotoAddParam();

	public Integer x = -1;
	public Integer y = -1;
	public Boolean notEye = false;
	public Boolean lock = false;
	public Boolean doNotActive = false;

	public static SPhotoAddParam create(Integer x, Integer y, Boolean notEye, Boolean lock, Boolean doNotActive)
	{
		SPhotoAddParam obj = new SPhotoAddParam();
		obj.x = x;
		obj.y = y;
		obj.notEye = notEye;
		obj.lock = lock;
		obj.doNotActive = doNotActive;
		return obj;
	}

	public SPhotoAddParam clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SPhotoAddParam _other_)
	{
		this.x = _other_.x;
		this.y = _other_.y;
		this.notEye = _other_.notEye;
		this.lock = _other_.lock;
		this.doNotActive = _other_.doNotActive;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("x"))
			x = _json_.getInteger("x");
		if (_json_.containsKey("y"))
			y = _json_.getInteger("y");
		if (_json_.containsKey("notEye"))
			notEye = _json_.getBoolean("notEye");
		if (_json_.containsKey("lock"))
			lock = _json_.getBoolean("lock");
		if (_json_.containsKey("doNotActive"))
			doNotActive = _json_.getBoolean("doNotActive");
	}

	public static SPhotoAddParam load(String str)
	{
		try
		{
			SPhotoAddParam obj = new SPhotoAddParam();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SPhotoAddParam load(JSONObject json)
	{
		try
		{
			SPhotoAddParam obj = new SPhotoAddParam();
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
			if (x != null)
				_json_.put("x", x);
			if (y != null)
				_json_.put("y", y);
			if (notEye != null)
				_json_.put("notEye", notEye);
			if (lock != null)
				_json_.put("lock", lock);
			if (doNotActive != null)
				_json_.put("doNotActive", doNotActive);
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SPhotoAddParam> loadList(JSONArray ja)
	{
		try
		{
			List<SPhotoAddParam> list = new ArrayList<SPhotoAddParam>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SPhotoAddParam item = SPhotoAddParam.load(jo);
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

	public static JSONArray saveList(List<SPhotoAddParam> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SPhotoAddParam item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(PhotoAddParam _proto_)
	{
		if (_proto_.hasX())
			x = _proto_.getX();
		if (_proto_.hasY())
			y = _proto_.getY();
		if (_proto_.hasNotEye())
			notEye = _proto_.getNotEye();
		if (_proto_.hasLock())
			lock = _proto_.getLock();
		if (_proto_.hasDoNotActive())
			doNotActive = _proto_.getDoNotActive();
	}

	public static SPhotoAddParam load(byte[] bytes)
	{
		try
		{
			SPhotoAddParam obj = new SPhotoAddParam();
			obj.parse(PhotoAddParam.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SPhotoAddParam load(PhotoAddParam proto)
	{
		try
		{
			SPhotoAddParam obj = new SPhotoAddParam();
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
	public PhotoAddParam saveToProto()
	{
		PhotoAddParam.Builder _builder_ = PhotoAddParam.newBuilder();
		if (x != null && !x.equals(PhotoAddParam.getDefaultInstance().getX()))
			_builder_.setX(x);
		if (y != null && !y.equals(PhotoAddParam.getDefaultInstance().getY()))
			_builder_.setY(y);
		if (notEye != null && !notEye.equals(PhotoAddParam.getDefaultInstance().getNotEye()))
			_builder_.setNotEye(notEye);
		if (lock != null && !lock.equals(PhotoAddParam.getDefaultInstance().getLock()))
			_builder_.setLock(lock);
		if (doNotActive != null && !doNotActive.equals(PhotoAddParam.getDefaultInstance().getDoNotActive()))
			_builder_.setDoNotActive(doNotActive);
		return _builder_.build();
	}
}
