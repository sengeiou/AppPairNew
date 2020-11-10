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
import com.lys.protobuf.ProtocolPair.Response_DeleteUser;

public class SResponse_DeleteUser extends SPTData<Response_DeleteUser>
{
	private static final SResponse_DeleteUser DefaultInstance = new SResponse_DeleteUser();


	public static SResponse_DeleteUser create()
	{
		SResponse_DeleteUser obj = new SResponse_DeleteUser();
		return obj;
	}

	public SResponse_DeleteUser clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SResponse_DeleteUser _other_)
	{
	}

	@Override
	public void parse(JSONObject _json_)
	{
	}

	public static SResponse_DeleteUser load(String str)
	{
		try
		{
			SResponse_DeleteUser obj = new SResponse_DeleteUser();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_DeleteUser load(JSONObject json)
	{
		try
		{
			SResponse_DeleteUser obj = new SResponse_DeleteUser();
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

	public static List<SResponse_DeleteUser> loadList(JSONArray ja)
	{
		try
		{
			List<SResponse_DeleteUser> list = new ArrayList<SResponse_DeleteUser>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SResponse_DeleteUser item = SResponse_DeleteUser.load(jo);
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

	public static JSONArray saveList(List<SResponse_DeleteUser> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SResponse_DeleteUser item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Response_DeleteUser _proto_)
	{
	}

	public static SResponse_DeleteUser load(byte[] bytes)
	{
		try
		{
			SResponse_DeleteUser obj = new SResponse_DeleteUser();
			obj.parse(Response_DeleteUser.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_DeleteUser load(Response_DeleteUser proto)
	{
		try
		{
			SResponse_DeleteUser obj = new SResponse_DeleteUser();
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
	public Response_DeleteUser saveToProto()
	{
		Response_DeleteUser.Builder _builder_ = Response_DeleteUser.newBuilder();
		return _builder_.build();
	}
}
