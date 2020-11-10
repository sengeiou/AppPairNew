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
import com.lys.protobuf.ProtocolShop.Response_DeleteComment;

public class SResponse_DeleteComment extends SPTData<Response_DeleteComment>
{
	private static final SResponse_DeleteComment DefaultInstance = new SResponse_DeleteComment();


	public static SResponse_DeleteComment create()
	{
		SResponse_DeleteComment obj = new SResponse_DeleteComment();
		return obj;
	}

	public SResponse_DeleteComment clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SResponse_DeleteComment _other_)
	{
	}

	@Override
	public void parse(JSONObject _json_)
	{
	}

	public static SResponse_DeleteComment load(String str)
	{
		try
		{
			SResponse_DeleteComment obj = new SResponse_DeleteComment();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_DeleteComment load(JSONObject json)
	{
		try
		{
			SResponse_DeleteComment obj = new SResponse_DeleteComment();
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
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SResponse_DeleteComment> loadList(JSONArray ja)
	{
		try
		{
			List<SResponse_DeleteComment> list = new ArrayList<SResponse_DeleteComment>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SResponse_DeleteComment item = SResponse_DeleteComment.load(jo);
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

	public static JSONArray saveList(List<SResponse_DeleteComment> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SResponse_DeleteComment item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Response_DeleteComment _proto_)
	{
	}

	public static SResponse_DeleteComment load(byte[] bytes)
	{
		try
		{
			SResponse_DeleteComment obj = new SResponse_DeleteComment();
			obj.parse(Response_DeleteComment.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_DeleteComment load(Response_DeleteComment proto)
	{
		try
		{
			SResponse_DeleteComment obj = new SResponse_DeleteComment();
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
	public Response_DeleteComment saveToProto()
	{
		Response_DeleteComment.Builder _builder_ = Response_DeleteComment.newBuilder();
		return _builder_.build();
	}
}
