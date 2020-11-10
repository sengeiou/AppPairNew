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
import com.lys.protobuf.ProtocolZhixue.Response_ZXGenChapterTree;

public class SResponse_ZXGenChapterTree extends SPTData<Response_ZXGenChapterTree>
{
	private static final SResponse_ZXGenChapterTree DefaultInstance = new SResponse_ZXGenChapterTree();

	public List<SChapter> chapters = new ArrayList<SChapter>();

	public static SResponse_ZXGenChapterTree create()
	{
		SResponse_ZXGenChapterTree obj = new SResponse_ZXGenChapterTree();
		return obj;
	}

	public SResponse_ZXGenChapterTree clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SResponse_ZXGenChapterTree _other_)
	{
		this.chapters = _other_.chapters;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("chapters"))
			chapters = SChapter.loadList(_json_.getJSONArray("chapters"));
	}

	public static SResponse_ZXGenChapterTree load(String str)
	{
		try
		{
			SResponse_ZXGenChapterTree obj = new SResponse_ZXGenChapterTree();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_ZXGenChapterTree load(JSONObject json)
	{
		try
		{
			SResponse_ZXGenChapterTree obj = new SResponse_ZXGenChapterTree();
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
			if (chapters != null)
				_json_.put("chapters", SChapter.saveList(chapters));
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SResponse_ZXGenChapterTree> loadList(JSONArray ja)
	{
		try
		{
			List<SResponse_ZXGenChapterTree> list = new ArrayList<SResponse_ZXGenChapterTree>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SResponse_ZXGenChapterTree item = SResponse_ZXGenChapterTree.load(jo);
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

	public static JSONArray saveList(List<SResponse_ZXGenChapterTree> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SResponse_ZXGenChapterTree item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Response_ZXGenChapterTree _proto_)
	{
		for (int i = 0; i < _proto_.getChaptersCount(); i++)
			chapters.add(SChapter.load(_proto_.getChapters(i)));
	}

	public static SResponse_ZXGenChapterTree load(byte[] bytes)
	{
		try
		{
			SResponse_ZXGenChapterTree obj = new SResponse_ZXGenChapterTree();
			obj.parse(Response_ZXGenChapterTree.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_ZXGenChapterTree load(Response_ZXGenChapterTree proto)
	{
		try
		{
			SResponse_ZXGenChapterTree obj = new SResponse_ZXGenChapterTree();
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
	public Response_ZXGenChapterTree saveToProto()
	{
		Response_ZXGenChapterTree.Builder _builder_ = Response_ZXGenChapterTree.newBuilder();
		if (chapters != null)
			for (SChapter _value_ : chapters)
				_builder_.addChapters(_value_.saveToProto());
		return _builder_.build();
	}
}
