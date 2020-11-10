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
import com.lys.protobuf.ProtocolZhixue.Response_ZXDeviceList;

public class SResponse_ZXDeviceList extends SPTData<Response_ZXDeviceList>
{
	private static final SResponse_ZXDeviceList DefaultInstance = new SResponse_ZXDeviceList();

	public String seriveIp = null;
	public Boolean keepScreen = false;
	public List<SZXDeviceInfo> devices = new ArrayList<SZXDeviceInfo>();

	public static SResponse_ZXDeviceList create(String seriveIp, Boolean keepScreen)
	{
		SResponse_ZXDeviceList obj = new SResponse_ZXDeviceList();
		obj.seriveIp = seriveIp;
		obj.keepScreen = keepScreen;
		return obj;
	}

	public SResponse_ZXDeviceList clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SResponse_ZXDeviceList _other_)
	{
		this.seriveIp = _other_.seriveIp;
		this.keepScreen = _other_.keepScreen;
		this.devices = _other_.devices;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("seriveIp"))
			seriveIp = _json_.getString("seriveIp");
		if (_json_.containsKey("keepScreen"))
			keepScreen = _json_.getBoolean("keepScreen");
		if (_json_.containsKey("devices"))
			devices = SZXDeviceInfo.loadList(_json_.getJSONArray("devices"));
	}

	public static SResponse_ZXDeviceList load(String str)
	{
		try
		{
			SResponse_ZXDeviceList obj = new SResponse_ZXDeviceList();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_ZXDeviceList load(JSONObject json)
	{
		try
		{
			SResponse_ZXDeviceList obj = new SResponse_ZXDeviceList();
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
			if (seriveIp != null)
				_json_.put("seriveIp", seriveIp);
			if (keepScreen != null)
				_json_.put("keepScreen", keepScreen);
			if (devices != null)
				_json_.put("devices", SZXDeviceInfo.saveList(devices));
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SResponse_ZXDeviceList> loadList(JSONArray ja)
	{
		try
		{
			List<SResponse_ZXDeviceList> list = new ArrayList<SResponse_ZXDeviceList>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SResponse_ZXDeviceList item = SResponse_ZXDeviceList.load(jo);
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

	public static JSONArray saveList(List<SResponse_ZXDeviceList> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SResponse_ZXDeviceList item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Response_ZXDeviceList _proto_)
	{
		if (_proto_.hasSeriveIp())
			seriveIp = _proto_.getSeriveIp();
		if (_proto_.hasKeepScreen())
			keepScreen = _proto_.getKeepScreen();
		for (int i = 0; i < _proto_.getDevicesCount(); i++)
			devices.add(SZXDeviceInfo.load(_proto_.getDevices(i)));
	}

	public static SResponse_ZXDeviceList load(byte[] bytes)
	{
		try
		{
			SResponse_ZXDeviceList obj = new SResponse_ZXDeviceList();
			obj.parse(Response_ZXDeviceList.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_ZXDeviceList load(Response_ZXDeviceList proto)
	{
		try
		{
			SResponse_ZXDeviceList obj = new SResponse_ZXDeviceList();
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
	public Response_ZXDeviceList saveToProto()
	{
		Response_ZXDeviceList.Builder _builder_ = Response_ZXDeviceList.newBuilder();
		if (seriveIp != null && !seriveIp.equals(Response_ZXDeviceList.getDefaultInstance().getSeriveIp()))
			_builder_.setSeriveIp(seriveIp);
		if (keepScreen != null && !keepScreen.equals(Response_ZXDeviceList.getDefaultInstance().getKeepScreen()))
			_builder_.setKeepScreen(keepScreen);
		if (devices != null)
			for (SZXDeviceInfo _value_ : devices)
				_builder_.addDevices(_value_.saveToProto());
		return _builder_.build();
	}
}
