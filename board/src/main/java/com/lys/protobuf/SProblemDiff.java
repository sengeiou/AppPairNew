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
import com.lys.protobuf.ProtocolCommon.ProblemDiff;

public class SProblemDiff extends SPTData<ProblemDiff>
{
	private static final SProblemDiff DefaultInstance = new SProblemDiff();

	public Integer diff = 0;
	public String name = null;

	public static SProblemDiff create(Integer diff, String name)
	{
		SProblemDiff obj = new SProblemDiff();
		obj.diff = diff;
		obj.name = name;
		return obj;
	}

	public SProblemDiff clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SProblemDiff _other_)
	{
		this.diff = _other_.diff;
		this.name = _other_.name;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("diff"))
			diff = _json_.getInteger("diff");
		if (_json_.containsKey("name"))
			name = _json_.getString("name");
	}

	public static SProblemDiff load(String str)
	{
		try
		{
			SProblemDiff obj = new SProblemDiff();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SProblemDiff load(JSONObject json)
	{
		try
		{
			SProblemDiff obj = new SProblemDiff();
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
			if (diff != null)
				_json_.put("diff", diff);
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

	public static List<SProblemDiff> loadList(JSONArray ja)
	{
		try
		{
			List<SProblemDiff> list = new ArrayList<SProblemDiff>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SProblemDiff item = SProblemDiff.load(jo);
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

	public static JSONArray saveList(List<SProblemDiff> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SProblemDiff item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(ProblemDiff _proto_)
	{
		if (_proto_.hasDiff())
			diff = _proto_.getDiff();
		if (_proto_.hasName())
			name = _proto_.getName();
	}

	public static SProblemDiff load(byte[] bytes)
	{
		try
		{
			SProblemDiff obj = new SProblemDiff();
			obj.parse(ProblemDiff.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SProblemDiff load(ProblemDiff proto)
	{
		try
		{
			SProblemDiff obj = new SProblemDiff();
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
	public ProblemDiff saveToProto()
	{
		ProblemDiff.Builder _builder_ = ProblemDiff.newBuilder();
		if (diff != null && !diff.equals(ProblemDiff.getDefaultInstance().getDiff()))
			_builder_.setDiff(diff);
		if (name != null && !name.equals(ProblemDiff.getDefaultInstance().getName()))
			_builder_.setName(name);
		return _builder_.build();
	}
}
