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
import com.lys.protobuf.ProtocolZhixue.ZXChapterTreeNode;

public class SZXChapterTreeNode extends SPTData<ZXChapterTreeNode>
{
	private static final SZXChapterTreeNode DefaultInstance = new SZXChapterTreeNode();

	public Integer index = 0;
	public String name = null;
	public List<SZXChapterTreeNode> chapters = new ArrayList<SZXChapterTreeNode>();

	public static SZXChapterTreeNode create(Integer index, String name)
	{
		SZXChapterTreeNode obj = new SZXChapterTreeNode();
		obj.index = index;
		obj.name = name;
		return obj;
	}

	public SZXChapterTreeNode clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SZXChapterTreeNode _other_)
	{
		this.index = _other_.index;
		this.name = _other_.name;
		this.chapters = _other_.chapters;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("index"))
			index = _json_.getInteger("index");
		if (_json_.containsKey("name"))
			name = _json_.getString("name");
		if (_json_.containsKey("chapters"))
			chapters = SZXChapterTreeNode.loadList(_json_.getJSONArray("chapters"));
	}

	public static SZXChapterTreeNode load(String str)
	{
		try
		{
			SZXChapterTreeNode obj = new SZXChapterTreeNode();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SZXChapterTreeNode load(JSONObject json)
	{
		try
		{
			SZXChapterTreeNode obj = new SZXChapterTreeNode();
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
			if (index != null)
				_json_.put("index", index);
			if (name != null)
				_json_.put("name", name);
			if (chapters != null)
				_json_.put("chapters", SZXChapterTreeNode.saveList(chapters));
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SZXChapterTreeNode> loadList(JSONArray ja)
	{
		try
		{
			List<SZXChapterTreeNode> list = new ArrayList<SZXChapterTreeNode>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SZXChapterTreeNode item = SZXChapterTreeNode.load(jo);
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

	public static JSONArray saveList(List<SZXChapterTreeNode> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SZXChapterTreeNode item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(ZXChapterTreeNode _proto_)
	{
		if (_proto_.hasIndex())
			index = _proto_.getIndex();
		if (_proto_.hasName())
			name = _proto_.getName();
		for (int i = 0; i < _proto_.getChaptersCount(); i++)
			chapters.add(SZXChapterTreeNode.load(_proto_.getChapters(i)));
	}

	public static SZXChapterTreeNode load(byte[] bytes)
	{
		try
		{
			SZXChapterTreeNode obj = new SZXChapterTreeNode();
			obj.parse(ZXChapterTreeNode.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SZXChapterTreeNode load(ZXChapterTreeNode proto)
	{
		try
		{
			SZXChapterTreeNode obj = new SZXChapterTreeNode();
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
	public ZXChapterTreeNode saveToProto()
	{
		ZXChapterTreeNode.Builder _builder_ = ZXChapterTreeNode.newBuilder();
		if (index != null && !index.equals(ZXChapterTreeNode.getDefaultInstance().getIndex()))
			_builder_.setIndex(index);
		if (name != null && !name.equals(ZXChapterTreeNode.getDefaultInstance().getName()))
			_builder_.setName(name);
		if (chapters != null)
			for (SZXChapterTreeNode _value_ : chapters)
				_builder_.addChapters(_value_.saveToProto());
		return _builder_.build();
	}
}
