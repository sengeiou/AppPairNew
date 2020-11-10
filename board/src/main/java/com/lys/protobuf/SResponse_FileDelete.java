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
import com.lys.protobuf.ProtocolPair2.Response_FileDelete;

public class SResponse_FileDelete extends SPTData<Response_FileDelete>
{
	private static final SResponse_FileDelete DefaultInstance = new SResponse_FileDelete();


	public static SResponse_FileDelete create()
	{
		SResponse_FileDelete obj = new SResponse_FileDelete();
		return obj;
	}

	public SResponse_FileDelete clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SResponse_FileDelete _other_)
	{
	}

	@Override
	public void parse(JSONObject _json_)
	{
	}

	public static SResponse_FileDelete load(String str)
	{
		try
		{
			SResponse_FileDelete obj = new SResponse_FileDelete();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_FileDelete load(JSONObject json)
	{
		try
		{
			SResponse_FileDelete obj = new SResponse_FileDelete();
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

	public static List<SResponse_FileDelete> loadList(JSONArray ja)
	{
		try
		{
			List<SResponse_FileDelete> list = new ArrayList<SResponse_FileDelete>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SResponse_FileDelete item = SResponse_FileDelete.load(jo);
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

	public static JSONArray saveList(List<SResponse_FileDelete> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SResponse_FileDelete item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Response_FileDelete _proto_)
	{
	}

	public static SResponse_FileDelete load(byte[] bytes)
	{
		try
		{
			SResponse_FileDelete obj = new SResponse_FileDelete();
			obj.parse(Response_FileDelete.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_FileDelete load(Response_FileDelete proto)
	{
		try
		{
			SResponse_FileDelete obj = new SResponse_FileDelete();
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
	public Response_FileDelete saveToProto()
	{
		Response_FileDelete.Builder _builder_ = Response_FileDelete.newBuilder();
		return _builder_.build();
	}
}
