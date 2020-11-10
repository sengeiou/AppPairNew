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
import com.lys.protobuf.ProtocolShop.Request_AddModifyComment;

// ---------------------- xxxxxxxx --------------------------
public class SRequest_AddModifyComment extends SPTData<Request_AddModifyComment>
{
	private static final SRequest_AddModifyComment DefaultInstance = new SRequest_AddModifyComment();

	public SComment comment = null;

	public static SRequest_AddModifyComment create(SComment comment)
	{
		SRequest_AddModifyComment obj = new SRequest_AddModifyComment();
		obj.comment = comment;
		return obj;
	}

	public SRequest_AddModifyComment clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SRequest_AddModifyComment _other_)
	{
		this.comment = _other_.comment;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("comment"))
			comment = SComment.load(_json_.getJSONObject("comment"));
	}

	public static SRequest_AddModifyComment load(String str)
	{
		try
		{
			SRequest_AddModifyComment obj = new SRequest_AddModifyComment();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_AddModifyComment load(JSONObject json)
	{
		try
		{
			SRequest_AddModifyComment obj = new SRequest_AddModifyComment();
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
			if (comment != null)
				_json_.put("comment", comment.saveToJson());
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SRequest_AddModifyComment> loadList(JSONArray ja)
	{
		try
		{
			List<SRequest_AddModifyComment> list = new ArrayList<SRequest_AddModifyComment>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SRequest_AddModifyComment item = SRequest_AddModifyComment.load(jo);
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

	public static JSONArray saveList(List<SRequest_AddModifyComment> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SRequest_AddModifyComment item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Request_AddModifyComment _proto_)
	{
		if (_proto_.hasComment())
			comment = SComment.load(_proto_.getComment());
	}

	public static SRequest_AddModifyComment load(byte[] bytes)
	{
		try
		{
			SRequest_AddModifyComment obj = new SRequest_AddModifyComment();
			obj.parse(Request_AddModifyComment.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_AddModifyComment load(Request_AddModifyComment proto)
	{
		try
		{
			SRequest_AddModifyComment obj = new SRequest_AddModifyComment();
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
	public Request_AddModifyComment saveToProto()
	{
		Request_AddModifyComment.Builder _builder_ = Request_AddModifyComment.newBuilder();
		if (comment != null)
			_builder_.setComment(comment.saveToProto());
		return _builder_.build();
	}
}
