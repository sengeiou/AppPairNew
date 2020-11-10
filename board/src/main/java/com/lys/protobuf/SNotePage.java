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
import com.lys.protobuf.ProtocolNote.NotePage;

public class SNotePage extends SPTData<NotePage>
{
	private static final SNotePage DefaultInstance = new SNotePage();

	public String pageDir = null;
	public String title = null;
	public Integer subject = 0;

	public static SNotePage create(String pageDir, String title, Integer subject)
	{
		SNotePage obj = new SNotePage();
		obj.pageDir = pageDir;
		obj.title = title;
		obj.subject = subject;
		return obj;
	}

	public SNotePage clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SNotePage _other_)
	{
		this.pageDir = _other_.pageDir;
		this.title = _other_.title;
		this.subject = _other_.subject;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("pageDir"))
			pageDir = _json_.getString("pageDir");
		if (_json_.containsKey("title"))
			title = _json_.getString("title");
		if (_json_.containsKey("subject"))
			subject = _json_.getInteger("subject");
	}

	public static SNotePage load(String str)
	{
		try
		{
			SNotePage obj = new SNotePage();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SNotePage load(JSONObject json)
	{
		try
		{
			SNotePage obj = new SNotePage();
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
			if (pageDir != null)
				_json_.put("pageDir", pageDir);
			if (title != null)
				_json_.put("title", title);
			if (subject != null)
				_json_.put("subject", subject);
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SNotePage> loadList(JSONArray ja)
	{
		try
		{
			List<SNotePage> list = new ArrayList<SNotePage>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SNotePage item = SNotePage.load(jo);
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

	public static JSONArray saveList(List<SNotePage> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SNotePage item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(NotePage _proto_)
	{
		if (_proto_.hasPageDir())
			pageDir = _proto_.getPageDir();
		if (_proto_.hasTitle())
			title = _proto_.getTitle();
		if (_proto_.hasSubject())
			subject = _proto_.getSubject();
	}

	public static SNotePage load(byte[] bytes)
	{
		try
		{
			SNotePage obj = new SNotePage();
			obj.parse(NotePage.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SNotePage load(NotePage proto)
	{
		try
		{
			SNotePage obj = new SNotePage();
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
	public NotePage saveToProto()
	{
		NotePage.Builder _builder_ = NotePage.newBuilder();
		if (pageDir != null && !pageDir.equals(NotePage.getDefaultInstance().getPageDir()))
			_builder_.setPageDir(pageDir);
		if (title != null && !title.equals(NotePage.getDefaultInstance().getTitle()))
			_builder_.setTitle(title);
		if (subject != null && !subject.equals(NotePage.getDefaultInstance().getSubject()))
			_builder_.setSubject(subject);
		return _builder_.build();
	}
}
