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
import com.lys.protobuf.ProtocolZhixue.ZXChapterTree;

public class SZXChapterTree extends SPTData<ZXChapterTree>
{
	private static final SZXChapterTree DefaultInstance = new SZXChapterTree();

	public List<SZXChapterTreeNode> chapters = new ArrayList<SZXChapterTreeNode>();
	public String phase = null;
	public String subject = null;
	public String material = null;

	public static SZXChapterTree create(String phase, String subject, String material)
	{
		SZXChapterTree obj = new SZXChapterTree();
		obj.phase = phase;
		obj.subject = subject;
		obj.material = material;
		return obj;
	}

	public SZXChapterTree clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SZXChapterTree _other_)
	{
		this.chapters = _other_.chapters;
		this.phase = _other_.phase;
		this.subject = _other_.subject;
		this.material = _other_.material;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("chapters"))
			chapters = SZXChapterTreeNode.loadList(_json_.getJSONArray("chapters"));
		if (_json_.containsKey("phase"))
			phase = _json_.getString("phase");
		if (_json_.containsKey("subject"))
			subject = _json_.getString("subject");
		if (_json_.containsKey("material"))
			material = _json_.getString("material");
	}

	public static SZXChapterTree load(String str)
	{
		try
		{
			SZXChapterTree obj = new SZXChapterTree();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SZXChapterTree load(JSONObject json)
	{
		try
		{
			SZXChapterTree obj = new SZXChapterTree();
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
				_json_.put("chapters", SZXChapterTreeNode.saveList(chapters));
			if (phase != null)
				_json_.put("phase", phase);
			if (subject != null)
				_json_.put("subject", subject);
			if (material != null)
				_json_.put("material", material);
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SZXChapterTree> loadList(JSONArray ja)
	{
		try
		{
			List<SZXChapterTree> list = new ArrayList<SZXChapterTree>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SZXChapterTree item = SZXChapterTree.load(jo);
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

	public static JSONArray saveList(List<SZXChapterTree> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SZXChapterTree item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(ZXChapterTree _proto_)
	{
		for (int i = 0; i < _proto_.getChaptersCount(); i++)
			chapters.add(SZXChapterTreeNode.load(_proto_.getChapters(i)));
		if (_proto_.hasPhase())
			phase = _proto_.getPhase();
		if (_proto_.hasSubject())
			subject = _proto_.getSubject();
		if (_proto_.hasMaterial())
			material = _proto_.getMaterial();
	}

	public static SZXChapterTree load(byte[] bytes)
	{
		try
		{
			SZXChapterTree obj = new SZXChapterTree();
			obj.parse(ZXChapterTree.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SZXChapterTree load(ZXChapterTree proto)
	{
		try
		{
			SZXChapterTree obj = new SZXChapterTree();
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
	public ZXChapterTree saveToProto()
	{
		ZXChapterTree.Builder _builder_ = ZXChapterTree.newBuilder();
		if (chapters != null)
			for (SZXChapterTreeNode _value_ : chapters)
				_builder_.addChapters(_value_.saveToProto());
		if (phase != null && !phase.equals(ZXChapterTree.getDefaultInstance().getPhase()))
			_builder_.setPhase(phase);
		if (subject != null && !subject.equals(ZXChapterTree.getDefaultInstance().getSubject()))
			_builder_.setSubject(subject);
		if (material != null && !material.equals(ZXChapterTree.getDefaultInstance().getMaterial()))
			_builder_.setMaterial(material);
		return _builder_.build();
	}
}
