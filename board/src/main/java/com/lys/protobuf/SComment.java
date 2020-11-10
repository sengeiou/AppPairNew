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
import com.lys.protobuf.ProtocolShop.Comment;

public class SComment extends SPTData<Comment>
{
	private static final SComment DefaultInstance = new SComment();

	public String id = null;
	public String matterId = null;
	public SUser user = null;
	public Integer star = 0;
	public String text = null;
	public Long time = 0L;
	public Boolean pass = false;

	public static SComment create(String id, String matterId, SUser user, Integer star, String text, Long time, Boolean pass)
	{
		SComment obj = new SComment();
		obj.id = id;
		obj.matterId = matterId;
		obj.user = user;
		obj.star = star;
		obj.text = text;
		obj.time = time;
		obj.pass = pass;
		return obj;
	}

	public SComment clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SComment _other_)
	{
		this.id = _other_.id;
		this.matterId = _other_.matterId;
		this.user = _other_.user;
		this.star = _other_.star;
		this.text = _other_.text;
		this.time = _other_.time;
		this.pass = _other_.pass;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("id"))
			id = _json_.getString("id");
		if (_json_.containsKey("matterId"))
			matterId = _json_.getString("matterId");
		if (_json_.containsKey("user"))
			user = SUser.load(_json_.getJSONObject("user"));
		if (_json_.containsKey("star"))
			star = _json_.getInteger("star");
		if (_json_.containsKey("text"))
			text = _json_.getString("text");
		if (_json_.containsKey("time"))
			time = _json_.getLong("time");
		if (_json_.containsKey("pass"))
			pass = _json_.getBoolean("pass");
	}

	public static SComment load(String str)
	{
		try
		{
			SComment obj = new SComment();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SComment load(JSONObject json)
	{
		try
		{
			SComment obj = new SComment();
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
			if (id != null)
				_json_.put("id", id);
			if (matterId != null)
				_json_.put("matterId", matterId);
			if (user != null)
				_json_.put("user", user.saveToJson());
			if (star != null)
				_json_.put("star", star);
			if (text != null)
				_json_.put("text", text);
			if (time != null)
				_json_.put("time", String.valueOf(time));
			if (pass != null)
				_json_.put("pass", pass);
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SComment> loadList(JSONArray ja)
	{
		try
		{
			List<SComment> list = new ArrayList<SComment>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SComment item = SComment.load(jo);
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

	public static JSONArray saveList(List<SComment> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SComment item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Comment _proto_)
	{
		if (_proto_.hasId())
			id = _proto_.getId();
		if (_proto_.hasMatterId())
			matterId = _proto_.getMatterId();
		if (_proto_.hasUser())
			user = SUser.load(_proto_.getUser());
		if (_proto_.hasStar())
			star = _proto_.getStar();
		if (_proto_.hasText())
			text = _proto_.getText();
		if (_proto_.hasTime())
			time = _proto_.getTime();
		if (_proto_.hasPass())
			pass = _proto_.getPass();
	}

	public static SComment load(byte[] bytes)
	{
		try
		{
			SComment obj = new SComment();
			obj.parse(Comment.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SComment load(Comment proto)
	{
		try
		{
			SComment obj = new SComment();
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
	public Comment saveToProto()
	{
		Comment.Builder _builder_ = Comment.newBuilder();
		if (id != null && !id.equals(Comment.getDefaultInstance().getId()))
			_builder_.setId(id);
		if (matterId != null && !matterId.equals(Comment.getDefaultInstance().getMatterId()))
			_builder_.setMatterId(matterId);
		if (user != null)
			_builder_.setUser(user.saveToProto());
		if (star != null && !star.equals(Comment.getDefaultInstance().getStar()))
			_builder_.setStar(star);
		if (text != null && !text.equals(Comment.getDefaultInstance().getText()))
			_builder_.setText(text);
		if (time != null && !time.equals(Comment.getDefaultInstance().getTime()))
			_builder_.setTime(time);
		if (pass != null && !pass.equals(Comment.getDefaultInstance().getPass()))
			_builder_.setPass(pass);
		return _builder_.build();
	}
}
