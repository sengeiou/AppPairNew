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
import com.lys.protobuf.ProtocolShop.Request_DeleteComment;

// ---------------------- xxxxxxxx --------------------------
public class SRequest_DeleteComment extends SPTData<Request_DeleteComment>
{
	private static final SRequest_DeleteComment DefaultInstance = new SRequest_DeleteComment();

	public String commentId = null;

	public static SRequest_DeleteComment create(String commentId)
	{
		SRequest_DeleteComment obj = new SRequest_DeleteComment();
		obj.commentId = commentId;
		return obj;
	}

	public SRequest_DeleteComment clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SRequest_DeleteComment _other_)
	{
		this.commentId = _other_.commentId;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("commentId"))
			commentId = _json_.getString("commentId");
	}

	public static SRequest_DeleteComment load(String str)
	{
		try
		{
			SRequest_DeleteComment obj = new SRequest_DeleteComment();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_DeleteComment load(JSONObject json)
	{
		try
		{
			SRequest_DeleteComment obj = new SRequest_DeleteComment();
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
			if (commentId != null)
				_json_.put("commentId", commentId);
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SRequest_DeleteComment> loadList(JSONArray ja)
	{
		try
		{
			List<SRequest_DeleteComment> list = new ArrayList<SRequest_DeleteComment>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SRequest_DeleteComment item = SRequest_DeleteComment.load(jo);
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

	public static JSONArray saveList(List<SRequest_DeleteComment> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SRequest_DeleteComment item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Request_DeleteComment _proto_)
	{
		if (_proto_.hasCommentId())
			commentId = _proto_.getCommentId();
	}

	public static SRequest_DeleteComment load(byte[] bytes)
	{
		try
		{
			SRequest_DeleteComment obj = new SRequest_DeleteComment();
			obj.parse(Request_DeleteComment.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_DeleteComment load(Request_DeleteComment proto)
	{
		try
		{
			SRequest_DeleteComment obj = new SRequest_DeleteComment();
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
	public Request_DeleteComment saveToProto()
	{
		Request_DeleteComment.Builder _builder_ = Request_DeleteComment.newBuilder();
		if (commentId != null && !commentId.equals(Request_DeleteComment.getDefaultInstance().getCommentId()))
			_builder_.setCommentId(commentId);
		return _builder_.build();
	}
}
