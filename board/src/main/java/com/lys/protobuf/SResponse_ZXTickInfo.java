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
import com.lys.protobuf.ProtocolZhixue.Response_ZXTickInfo;

public class SResponse_ZXTickInfo extends SPTData<Response_ZXTickInfo>
{
	private static final SResponse_ZXTickInfo DefaultInstance = new SResponse_ZXTickInfo();

	public Boolean keepScreen = false;
	public String clientIp = null;
	public String seriveIp = null;

	public static SResponse_ZXTickInfo create(Boolean keepScreen, String clientIp, String seriveIp)
	{
		SResponse_ZXTickInfo obj = new SResponse_ZXTickInfo();
		obj.keepScreen = keepScreen;
		obj.clientIp = clientIp;
		obj.seriveIp = seriveIp;
		return obj;
	}

	public SResponse_ZXTickInfo clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SResponse_ZXTickInfo _other_)
	{
		this.keepScreen = _other_.keepScreen;
		this.clientIp = _other_.clientIp;
		this.seriveIp = _other_.seriveIp;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("keepScreen"))
			keepScreen = _json_.getBoolean("keepScreen");
		if (_json_.containsKey("clientIp"))
			clientIp = _json_.getString("clientIp");
		if (_json_.containsKey("seriveIp"))
			seriveIp = _json_.getString("seriveIp");
	}

	public static SResponse_ZXTickInfo load(String str)
	{
		try
		{
			SResponse_ZXTickInfo obj = new SResponse_ZXTickInfo();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_ZXTickInfo load(JSONObject json)
	{
		try
		{
			SResponse_ZXTickInfo obj = new SResponse_ZXTickInfo();
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
			if (keepScreen != null)
				_json_.put("keepScreen", keepScreen);
			if (clientIp != null)
				_json_.put("clientIp", clientIp);
			if (seriveIp != null)
				_json_.put("seriveIp", seriveIp);
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SResponse_ZXTickInfo> loadList(JSONArray ja)
	{
		try
		{
			List<SResponse_ZXTickInfo> list = new ArrayList<SResponse_ZXTickInfo>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SResponse_ZXTickInfo item = SResponse_ZXTickInfo.load(jo);
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

	public static JSONArray saveList(List<SResponse_ZXTickInfo> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SResponse_ZXTickInfo item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Response_ZXTickInfo _proto_)
	{
		if (_proto_.hasKeepScreen())
			keepScreen = _proto_.getKeepScreen();
		if (_proto_.hasClientIp())
			clientIp = _proto_.getClientIp();
		if (_proto_.hasSeriveIp())
			seriveIp = _proto_.getSeriveIp();
	}

	public static SResponse_ZXTickInfo load(byte[] bytes)
	{
		try
		{
			SResponse_ZXTickInfo obj = new SResponse_ZXTickInfo();
			obj.parse(Response_ZXTickInfo.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_ZXTickInfo load(Response_ZXTickInfo proto)
	{
		try
		{
			SResponse_ZXTickInfo obj = new SResponse_ZXTickInfo();
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
	public Response_ZXTickInfo saveToProto()
	{
		Response_ZXTickInfo.Builder _builder_ = Response_ZXTickInfo.newBuilder();
		if (keepScreen != null && !keepScreen.equals(Response_ZXTickInfo.getDefaultInstance().getKeepScreen()))
			_builder_.setKeepScreen(keepScreen);
		if (clientIp != null && !clientIp.equals(Response_ZXTickInfo.getDefaultInstance().getClientIp()))
			_builder_.setClientIp(clientIp);
		if (seriveIp != null && !seriveIp.equals(Response_ZXTickInfo.getDefaultInstance().getSeriveIp()))
			_builder_.setSeriveIp(seriveIp);
		return _builder_.build();
	}
}
