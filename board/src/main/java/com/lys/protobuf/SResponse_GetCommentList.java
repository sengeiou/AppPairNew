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
import com.lys.protobuf.ProtocolShop.Response_GetCommentList;

public class SResponse_GetCommentList extends SPTData<Response_GetCommentList>
{
	private static final SResponse_GetCommentList DefaultInstance = new SResponse_GetCommentList();

	public List<SComment> comments = new ArrayList<SComment>();

	public static SResponse_GetCommentList create()
	{
		SResponse_GetCommentList obj = new SResponse_GetCommentList();
		return obj;
	}

	public SResponse_GetCommentList clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SResponse_GetCommentList _other_)
	{
		this.comments = _other_.comments;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("comments"))
			comments = SComment.loadList(_json_.getJSONArray("comments"));
	}

	public static SResponse_GetCommentList load(String str)
	{
		try
		{
			SResponse_GetCommentList obj = new SResponse_GetCommentList();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_GetCommentList load(JSONObject json)
	{
		try
		{
			SResponse_GetCommentList obj = new SResponse_GetCommentList();
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
			if (comments != null)
				_json_.put("comments", SComment.saveList(comments));
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SResponse_GetCommentList> loadList(JSONArray ja)
	{
		try
		{
			List<SResponse_GetCommentList> list = new ArrayList<SResponse_GetCommentList>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SResponse_GetCommentList item = SResponse_GetCommentList.load(jo);
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

	public static JSONArray saveList(List<SResponse_GetCommentList> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SResponse_GetCommentList item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Response_GetCommentList _proto_)
	{
		for (int i = 0; i < _proto_.getCommentsCount(); i++)
			comments.add(SComment.load(_proto_.getComments(i)));
	}

	public static SResponse_GetCommentList load(byte[] bytes)
	{
		try
		{
			SResponse_GetCommentList obj = new SResponse_GetCommentList();
			obj.parse(Response_GetCommentList.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_GetCommentList load(Response_GetCommentList proto)
	{
		try
		{
			SResponse_GetCommentList obj = new SResponse_GetCommentList();
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
	public Response_GetCommentList saveToProto()
	{
		Response_GetCommentList.Builder _builder_ = Response_GetCommentList.newBuilder();
		if (comments != null)
			for (SComment _value_ : comments)
				_builder_.addComments(_value_.saveToProto());
		return _builder_.build();
	}
}
