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
import com.lys.protobuf.ProtocolShop.Response_AddModifyComment;

public class SResponse_AddModifyComment extends SPTData<Response_AddModifyComment>
{
	private static final SResponse_AddModifyComment DefaultInstance = new SResponse_AddModifyComment();


	public static SResponse_AddModifyComment create()
	{
		SResponse_AddModifyComment obj = new SResponse_AddModifyComment();
		return obj;
	}

	public SResponse_AddModifyComment clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SResponse_AddModifyComment _other_)
	{
	}

	@Override
	public void parse(JSONObject _json_)
	{
	}

	public static SResponse_AddModifyComment load(String str)
	{
		try
		{
			SResponse_AddModifyComment obj = new SResponse_AddModifyComment();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_AddModifyComment load(JSONObject json)
	{
		try
		{
			SResponse_AddModifyComment obj = new SResponse_AddModifyComment();
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

	public static List<SResponse_AddModifyComment> loadList(JSONArray ja)
	{
		try
		{
			List<SResponse_AddModifyComment> list = new ArrayList<SResponse_AddModifyComment>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SResponse_AddModifyComment item = SResponse_AddModifyComment.load(jo);
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

	public static JSONArray saveList(List<SResponse_AddModifyComment> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SResponse_AddModifyComment item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Response_AddModifyComment _proto_)
	{
	}

	public static SResponse_AddModifyComment load(byte[] bytes)
	{
		try
		{
			SResponse_AddModifyComment obj = new SResponse_AddModifyComment();
			obj.parse(Response_AddModifyComment.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_AddModifyComment load(Response_AddModifyComment proto)
	{
		try
		{
			SResponse_AddModifyComment obj = new SResponse_AddModifyComment();
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
	public Response_AddModifyComment saveToProto()
	{
		Response_AddModifyComment.Builder _builder_ = Response_AddModifyComment.newBuilder();
		return _builder_.build();
	}
}
