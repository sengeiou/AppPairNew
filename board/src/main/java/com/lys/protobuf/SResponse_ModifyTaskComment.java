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
import com.lys.protobuf.ProtocolPair.Response_ModifyTaskComment;

public class SResponse_ModifyTaskComment extends SPTData<Response_ModifyTaskComment>
{
	private static final SResponse_ModifyTaskComment DefaultInstance = new SResponse_ModifyTaskComment();


	public static SResponse_ModifyTaskComment create()
	{
		SResponse_ModifyTaskComment obj = new SResponse_ModifyTaskComment();
		return obj;
	}

	public SResponse_ModifyTaskComment clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SResponse_ModifyTaskComment _other_)
	{
	}

	@Override
	public void parse(JSONObject _json_)
	{
	}

	public static SResponse_ModifyTaskComment load(String str)
	{
		try
		{
			SResponse_ModifyTaskComment obj = new SResponse_ModifyTaskComment();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_ModifyTaskComment load(JSONObject json)
	{
		try
		{
			SResponse_ModifyTaskComment obj = new SResponse_ModifyTaskComment();
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

	public static List<SResponse_ModifyTaskComment> loadList(JSONArray ja)
	{
		try
		{
			List<SResponse_ModifyTaskComment> list = new ArrayList<SResponse_ModifyTaskComment>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SResponse_ModifyTaskComment item = SResponse_ModifyTaskComment.load(jo);
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

	public static JSONArray saveList(List<SResponse_ModifyTaskComment> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SResponse_ModifyTaskComment item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Response_ModifyTaskComment _proto_)
	{
	}

	public static SResponse_ModifyTaskComment load(byte[] bytes)
	{
		try
		{
			SResponse_ModifyTaskComment obj = new SResponse_ModifyTaskComment();
			obj.parse(Response_ModifyTaskComment.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_ModifyTaskComment load(Response_ModifyTaskComment proto)
	{
		try
		{
			SResponse_ModifyTaskComment obj = new SResponse_ModifyTaskComment();
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
	public Response_ModifyTaskComment saveToProto()
	{
		Response_ModifyTaskComment.Builder _builder_ = Response_ModifyTaskComment.newBuilder();
		return _builder_.build();
	}
}
