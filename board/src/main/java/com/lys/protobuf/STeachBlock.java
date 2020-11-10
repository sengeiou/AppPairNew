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
import com.lys.protobuf.ProtocolScore.TeachBlock;

public class STeachBlock extends SPTData<TeachBlock>
{
	private static final STeachBlock DefaultInstance = new STeachBlock();

	public Integer block = 0;
	public /*STeachFlag*/ Integer flag = TeachBlock.getDefaultInstance().getFlag().getNumber();
	public String studentId = null;

	public static STeachBlock create(Integer block, Integer flag, String studentId)
	{
		STeachBlock obj = new STeachBlock();
		obj.block = block;
		obj.flag = flag;
		obj.studentId = studentId;
		return obj;
	}

	public STeachBlock clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(STeachBlock _other_)
	{
		this.block = _other_.block;
		this.flag = _other_.flag;
		this.studentId = _other_.studentId;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("block"))
			block = _json_.getInteger("block");
		if (_json_.containsKey("flag"))
			flag = _json_.getInteger("flag");
		if (_json_.containsKey("studentId"))
			studentId = _json_.getString("studentId");
	}

	public static STeachBlock load(String str)
	{
		try
		{
			STeachBlock obj = new STeachBlock();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static STeachBlock load(JSONObject json)
	{
		try
		{
			STeachBlock obj = new STeachBlock();
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
			if (block != null)
				_json_.put("block", block);
			if (flag != null)
				_json_.put("flag", flag);
			if (studentId != null)
				_json_.put("studentId", studentId);
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<STeachBlock> loadList(JSONArray ja)
	{
		try
		{
			List<STeachBlock> list = new ArrayList<STeachBlock>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				STeachBlock item = STeachBlock.load(jo);
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

	public static JSONArray saveList(List<STeachBlock> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			STeachBlock item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(TeachBlock _proto_)
	{
		if (_proto_.hasBlock())
			block = _proto_.getBlock();
		if (_proto_.hasFlag())
			flag = _proto_.getFlag().getNumber();
		if (_proto_.hasStudentId())
			studentId = _proto_.getStudentId();
	}

	public static STeachBlock load(byte[] bytes)
	{
		try
		{
			STeachBlock obj = new STeachBlock();
			obj.parse(TeachBlock.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static STeachBlock load(TeachBlock proto)
	{
		try
		{
			STeachBlock obj = new STeachBlock();
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
	public TeachBlock saveToProto()
	{
		TeachBlock.Builder _builder_ = TeachBlock.newBuilder();
		if (block != null && !block.equals(TeachBlock.getDefaultInstance().getBlock()))
			_builder_.setBlock(block);
		if (flag != null && TeachBlock.getDefaultInstance().getFlag().getNumber() != flag)
			_builder_.setFlag(ProtocolScore.TeachFlag.valueOf(flag));
		if (studentId != null && !studentId.equals(TeachBlock.getDefaultInstance().getStudentId()))
			_builder_.setStudentId(studentId);
		return _builder_.build();
	}
}
