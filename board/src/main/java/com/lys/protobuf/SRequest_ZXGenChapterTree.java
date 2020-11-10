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
import com.lys.protobuf.ProtocolZhixue.Request_ZXGenChapterTree;

// ---------------------- XXXXXX --------------------------
public class SRequest_ZXGenChapterTree extends SPTData<Request_ZXGenChapterTree>
{
	private static final SRequest_ZXGenChapterTree DefaultInstance = new SRequest_ZXGenChapterTree();

	public SZXChapterTree chapterTree = null;

	public static SRequest_ZXGenChapterTree create(SZXChapterTree chapterTree)
	{
		SRequest_ZXGenChapterTree obj = new SRequest_ZXGenChapterTree();
		obj.chapterTree = chapterTree;
		return obj;
	}

	public SRequest_ZXGenChapterTree clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SRequest_ZXGenChapterTree _other_)
	{
		this.chapterTree = _other_.chapterTree;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("chapterTree"))
			chapterTree = SZXChapterTree.load(_json_.getJSONObject("chapterTree"));
	}

	public static SRequest_ZXGenChapterTree load(String str)
	{
		try
		{
			SRequest_ZXGenChapterTree obj = new SRequest_ZXGenChapterTree();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_ZXGenChapterTree load(JSONObject json)
	{
		try
		{
			SRequest_ZXGenChapterTree obj = new SRequest_ZXGenChapterTree();
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
			if (chapterTree != null)
				_json_.put("chapterTree", chapterTree.saveToJson());
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SRequest_ZXGenChapterTree> loadList(JSONArray ja)
	{
		try
		{
			List<SRequest_ZXGenChapterTree> list = new ArrayList<SRequest_ZXGenChapterTree>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SRequest_ZXGenChapterTree item = SRequest_ZXGenChapterTree.load(jo);
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

	public static JSONArray saveList(List<SRequest_ZXGenChapterTree> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SRequest_ZXGenChapterTree item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Request_ZXGenChapterTree _proto_)
	{
		if (_proto_.hasChapterTree())
			chapterTree = SZXChapterTree.load(_proto_.getChapterTree());
	}

	public static SRequest_ZXGenChapterTree load(byte[] bytes)
	{
		try
		{
			SRequest_ZXGenChapterTree obj = new SRequest_ZXGenChapterTree();
			obj.parse(Request_ZXGenChapterTree.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_ZXGenChapterTree load(Request_ZXGenChapterTree proto)
	{
		try
		{
			SRequest_ZXGenChapterTree obj = new SRequest_ZXGenChapterTree();
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
	public Request_ZXGenChapterTree saveToProto()
	{
		Request_ZXGenChapterTree.Builder _builder_ = Request_ZXGenChapterTree.newBuilder();
		if (chapterTree != null)
			_builder_.setChapterTree(chapterTree.saveToProto());
		return _builder_.build();
	}
}
