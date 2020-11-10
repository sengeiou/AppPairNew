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
import com.lys.protobuf.ProtocolZhixue.Request_ZXDeviceList;

// ---------------------- XXXXXX --------------------------
public class SRequest_ZXDeviceList extends SPTData<Request_ZXDeviceList>
{
	private static final SRequest_ZXDeviceList DefaultInstance = new SRequest_ZXDeviceList();

	public Integer keepScreen = -1; // 要设置的值

	public static SRequest_ZXDeviceList create(Integer keepScreen)
	{
		SRequest_ZXDeviceList obj = new SRequest_ZXDeviceList();
		obj.keepScreen = keepScreen;
		return obj;
	}

	public SRequest_ZXDeviceList clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SRequest_ZXDeviceList _other_)
	{
		this.keepScreen = _other_.keepScreen;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("keepScreen"))
			keepScreen = _json_.getInteger("keepScreen");
	}

	public static SRequest_ZXDeviceList load(String str)
	{
		try
		{
			SRequest_ZXDeviceList obj = new SRequest_ZXDeviceList();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_ZXDeviceList load(JSONObject json)
	{
		try
		{
			SRequest_ZXDeviceList obj = new SRequest_ZXDeviceList();
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
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SRequest_ZXDeviceList> loadList(JSONArray ja)
	{
		try
		{
			List<SRequest_ZXDeviceList> list = new ArrayList<SRequest_ZXDeviceList>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SRequest_ZXDeviceList item = SRequest_ZXDeviceList.load(jo);
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

	public static JSONArray saveList(List<SRequest_ZXDeviceList> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SRequest_ZXDeviceList item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Request_ZXDeviceList _proto_)
	{
		if (_proto_.hasKeepScreen())
			keepScreen = _proto_.getKeepScreen();
	}

	public static SRequest_ZXDeviceList load(byte[] bytes)
	{
		try
		{
			SRequest_ZXDeviceList obj = new SRequest_ZXDeviceList();
			obj.parse(Request_ZXDeviceList.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_ZXDeviceList load(Request_ZXDeviceList proto)
	{
		try
		{
			SRequest_ZXDeviceList obj = new SRequest_ZXDeviceList();
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
	public Request_ZXDeviceList saveToProto()
	{
		Request_ZXDeviceList.Builder _builder_ = Request_ZXDeviceList.newBuilder();
		if (keepScreen != null && !keepScreen.equals(Request_ZXDeviceList.getDefaultInstance().getKeepScreen()))
			_builder_.setKeepScreen(keepScreen);
		return _builder_.build();
	}
}
