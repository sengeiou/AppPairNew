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
import com.lys.protobuf.ProtocolNote.NotePageSet;

public class SNotePageSet extends SPTData<NotePageSet>
{
	private static final SNotePageSet DefaultInstance = new SNotePageSet();

	public List<SNotePage> pages = new ArrayList<SNotePage>();

	public static SNotePageSet create()
	{
		SNotePageSet obj = new SNotePageSet();
		return obj;
	}

	public SNotePageSet clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SNotePageSet _other_)
	{
		this.pages = _other_.pages;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("pages"))
			pages = SNotePage.loadList(_json_.getJSONArray("pages"));
	}

	public static SNotePageSet load(String str)
	{
		try
		{
			SNotePageSet obj = new SNotePageSet();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SNotePageSet load(JSONObject json)
	{
		try
		{
			SNotePageSet obj = new SNotePageSet();
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
			if (pages != null)
				_json_.put("pages", SNotePage.saveList(pages));
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SNotePageSet> loadList(JSONArray ja)
	{
		try
		{
			List<SNotePageSet> list = new ArrayList<SNotePageSet>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SNotePageSet item = SNotePageSet.load(jo);
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

	public static JSONArray saveList(List<SNotePageSet> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SNotePageSet item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(NotePageSet _proto_)
	{
		for (int i = 0; i < _proto_.getPagesCount(); i++)
			pages.add(SNotePage.load(_proto_.getPages(i)));
	}

	public static SNotePageSet load(byte[] bytes)
	{
		try
		{
			SNotePageSet obj = new SNotePageSet();
			obj.parse(NotePageSet.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SNotePageSet load(NotePageSet proto)
	{
		try
		{
			SNotePageSet obj = new SNotePageSet();
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
	public NotePageSet saveToProto()
	{
		NotePageSet.Builder _builder_ = NotePageSet.newBuilder();
		if (pages != null)
			for (SNotePage _value_ : pages)
				_builder_.addPages(_value_.saveToProto());
		return _builder_.build();
	}
}
