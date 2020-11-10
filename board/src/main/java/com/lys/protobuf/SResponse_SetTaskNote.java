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
import com.lys.protobuf.ProtocolPair.Response_SetTaskNote;

public class SResponse_SetTaskNote extends SPTData<Response_SetTaskNote>
{
	private static final SResponse_SetTaskNote DefaultInstance = new SResponse_SetTaskNote();


	public static SResponse_SetTaskNote create()
	{
		SResponse_SetTaskNote obj = new SResponse_SetTaskNote();
		return obj;
	}

	public SResponse_SetTaskNote clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SResponse_SetTaskNote _other_)
	{
	}

	@Override
	public void parse(JSONObject _json_)
	{
	}

	public static SResponse_SetTaskNote load(String str)
	{
		try
		{
			SResponse_SetTaskNote obj = new SResponse_SetTaskNote();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_SetTaskNote load(JSONObject json)
	{
		try
		{
			SResponse_SetTaskNote obj = new SResponse_SetTaskNote();
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

	public static List<SResponse_SetTaskNote> loadList(JSONArray ja)
	{
		try
		{
			List<SResponse_SetTaskNote> list = new ArrayList<SResponse_SetTaskNote>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SResponse_SetTaskNote item = SResponse_SetTaskNote.load(jo);
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

	public static JSONArray saveList(List<SResponse_SetTaskNote> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SResponse_SetTaskNote item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Response_SetTaskNote _proto_)
	{
	}

	public static SResponse_SetTaskNote load(byte[] bytes)
	{
		try
		{
			SResponse_SetTaskNote obj = new SResponse_SetTaskNote();
			obj.parse(Response_SetTaskNote.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_SetTaskNote load(Response_SetTaskNote proto)
	{
		try
		{
			SResponse_SetTaskNote obj = new SResponse_SetTaskNote();
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
	public Response_SetTaskNote saveToProto()
	{
		Response_SetTaskNote.Builder _builder_ = Response_SetTaskNote.newBuilder();
		return _builder_.build();
	}
}
