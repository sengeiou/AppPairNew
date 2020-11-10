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
import com.lys.protobuf.ProtocolNote.NoteBook;

public class SNoteBook extends SPTData<NoteBook>
{
	private static final SNoteBook DefaultInstance = new SNoteBook();

	public String bookDir = null;
	public String name = null;

	public static SNoteBook create(String bookDir, String name)
	{
		SNoteBook obj = new SNoteBook();
		obj.bookDir = bookDir;
		obj.name = name;
		return obj;
	}

	public SNoteBook clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SNoteBook _other_)
	{
		this.bookDir = _other_.bookDir;
		this.name = _other_.name;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("bookDir"))
			bookDir = _json_.getString("bookDir");
		if (_json_.containsKey("name"))
			name = _json_.getString("name");
	}

	public static SNoteBook load(String str)
	{
		try
		{
			SNoteBook obj = new SNoteBook();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SNoteBook load(JSONObject json)
	{
		try
		{
			SNoteBook obj = new SNoteBook();
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
			if (bookDir != null)
				_json_.put("bookDir", bookDir);
			if (name != null)
				_json_.put("name", name);
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SNoteBook> loadList(JSONArray ja)
	{
		try
		{
			List<SNoteBook> list = new ArrayList<SNoteBook>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SNoteBook item = SNoteBook.load(jo);
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

	public static JSONArray saveList(List<SNoteBook> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SNoteBook item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(NoteBook _proto_)
	{
		if (_proto_.hasBookDir())
			bookDir = _proto_.getBookDir();
		if (_proto_.hasName())
			name = _proto_.getName();
	}

	public static SNoteBook load(byte[] bytes)
	{
		try
		{
			SNoteBook obj = new SNoteBook();
			obj.parse(NoteBook.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SNoteBook load(NoteBook proto)
	{
		try
		{
			SNoteBook obj = new SNoteBook();
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
	public NoteBook saveToProto()
	{
		NoteBook.Builder _builder_ = NoteBook.newBuilder();
		if (bookDir != null && !bookDir.equals(NoteBook.getDefaultInstance().getBookDir()))
			_builder_.setBookDir(bookDir);
		if (name != null && !name.equals(NoteBook.getDefaultInstance().getName()))
			_builder_.setName(name);
		return _builder_.build();
	}
}
