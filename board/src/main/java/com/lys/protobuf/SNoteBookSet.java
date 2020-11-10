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
import com.lys.protobuf.ProtocolNote.NoteBookSet;

public class SNoteBookSet extends SPTData<NoteBookSet>
{
	private static final SNoteBookSet DefaultInstance = new SNoteBookSet();

	public List<SNoteBook> books = new ArrayList<SNoteBook>();

	public static SNoteBookSet create()
	{
		SNoteBookSet obj = new SNoteBookSet();
		return obj;
	}

	public SNoteBookSet clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SNoteBookSet _other_)
	{
		this.books = _other_.books;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("books"))
			books = SNoteBook.loadList(_json_.getJSONArray("books"));
	}

	public static SNoteBookSet load(String str)
	{
		try
		{
			SNoteBookSet obj = new SNoteBookSet();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SNoteBookSet load(JSONObject json)
	{
		try
		{
			SNoteBookSet obj = new SNoteBookSet();
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
			if (books != null)
				_json_.put("books", SNoteBook.saveList(books));
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SNoteBookSet> loadList(JSONArray ja)
	{
		try
		{
			List<SNoteBookSet> list = new ArrayList<SNoteBookSet>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SNoteBookSet item = SNoteBookSet.load(jo);
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

	public static JSONArray saveList(List<SNoteBookSet> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SNoteBookSet item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(NoteBookSet _proto_)
	{
		for (int i = 0; i < _proto_.getBooksCount(); i++)
			books.add(SNoteBook.load(_proto_.getBooks(i)));
	}

	public static SNoteBookSet load(byte[] bytes)
	{
		try
		{
			SNoteBookSet obj = new SNoteBookSet();
			obj.parse(NoteBookSet.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SNoteBookSet load(NoteBookSet proto)
	{
		try
		{
			SNoteBookSet obj = new SNoteBookSet();
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
	public NoteBookSet saveToProto()
	{
		NoteBookSet.Builder _builder_ = NoteBookSet.newBuilder();
		if (books != null)
			for (SNoteBook _value_ : books)
				_builder_.addBooks(_value_.saveToProto());
		return _builder_.build();
	}
}
