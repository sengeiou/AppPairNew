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
import com.lys.protobuf.ProtocolCommon.Clipboard;

public class SClipboard extends SPTData<Clipboard>
{
	private static final SClipboard DefaultInstance = new SClipboard();

	public /*SClipboardType*/ Integer type = Clipboard.getDefaultInstance().getType().getNumber();
	public String data1 = null;
	public String data2 = null;

	public static SClipboard create(Integer type, String data1, String data2)
	{
		SClipboard obj = new SClipboard();
		obj.type = type;
		obj.data1 = data1;
		obj.data2 = data2;
		return obj;
	}

	public SClipboard clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SClipboard _other_)
	{
		this.type = _other_.type;
		this.data1 = _other_.data1;
		this.data2 = _other_.data2;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("type"))
			type = _json_.getInteger("type");
		if (_json_.containsKey("data1"))
			data1 = _json_.getString("data1");
		if (_json_.containsKey("data2"))
			data2 = _json_.getString("data2");
	}

	public static SClipboard load(String str)
	{
		try
		{
			SClipboard obj = new SClipboard();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SClipboard load(JSONObject json)
	{
		try
		{
			SClipboard obj = new SClipboard();
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
			if (type != null)
				_json_.put("type", type);
			if (data1 != null)
				_json_.put("data1", data1);
			if (data2 != null)
				_json_.put("data2", data2);
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SClipboard> loadList(JSONArray ja)
	{
		try
		{
			List<SClipboard> list = new ArrayList<SClipboard>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SClipboard item = SClipboard.load(jo);
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

	public static JSONArray saveList(List<SClipboard> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SClipboard item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Clipboard _proto_)
	{
		if (_proto_.hasType())
			type = _proto_.getType().getNumber();
		if (_proto_.hasData1())
			data1 = _proto_.getData1();
		if (_proto_.hasData2())
			data2 = _proto_.getData2();
	}

	public static SClipboard load(byte[] bytes)
	{
		try
		{
			SClipboard obj = new SClipboard();
			obj.parse(Clipboard.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SClipboard load(Clipboard proto)
	{
		try
		{
			SClipboard obj = new SClipboard();
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
	public Clipboard saveToProto()
	{
		Clipboard.Builder _builder_ = Clipboard.newBuilder();
		if (type != null && Clipboard.getDefaultInstance().getType().getNumber() != type)
			_builder_.setType(ProtocolCommon.ClipboardType.valueOf(type));
		if (data1 != null && !data1.equals(Clipboard.getDefaultInstance().getData1()))
			_builder_.setData1(data1);
		if (data2 != null && !data2.equals(Clipboard.getDefaultInstance().getData2()))
			_builder_.setData2(data2);
		return _builder_.build();
	}
}
