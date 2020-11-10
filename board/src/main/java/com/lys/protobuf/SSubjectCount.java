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
import com.lys.protobuf.ProtocolCommon.SubjectCount;

public class SSubjectCount extends SPTData<SubjectCount>
{
	private static final SSubjectCount DefaultInstance = new SSubjectCount();

	public Integer subject = 0; // 科目
	public Integer number = 0; // 科目计数

	public static SSubjectCount create(Integer subject, Integer number)
	{
		SSubjectCount obj = new SSubjectCount();
		obj.subject = subject;
		obj.number = number;
		return obj;
	}

	public SSubjectCount clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SSubjectCount _other_)
	{
		this.subject = _other_.subject;
		this.number = _other_.number;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("subject"))
			subject = _json_.getInteger("subject");
		if (_json_.containsKey("number"))
			number = _json_.getInteger("number");
	}

	public static SSubjectCount load(String str)
	{
		try
		{
			SSubjectCount obj = new SSubjectCount();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SSubjectCount load(JSONObject json)
	{
		try
		{
			SSubjectCount obj = new SSubjectCount();
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
			if (subject != null)
				_json_.put("subject", subject);
			if (number != null)
				_json_.put("number", number);
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SSubjectCount> loadList(JSONArray ja)
	{
		try
		{
			List<SSubjectCount> list = new ArrayList<SSubjectCount>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SSubjectCount item = SSubjectCount.load(jo);
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

	public static JSONArray saveList(List<SSubjectCount> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SSubjectCount item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(SubjectCount _proto_)
	{
		if (_proto_.hasSubject())
			subject = _proto_.getSubject();
		if (_proto_.hasNumber())
			number = _proto_.getNumber();
	}

	public static SSubjectCount load(byte[] bytes)
	{
		try
		{
			SSubjectCount obj = new SSubjectCount();
			obj.parse(SubjectCount.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SSubjectCount load(SubjectCount proto)
	{
		try
		{
			SSubjectCount obj = new SSubjectCount();
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
	public SubjectCount saveToProto()
	{
		SubjectCount.Builder _builder_ = SubjectCount.newBuilder();
		if (subject != null && !subject.equals(SubjectCount.getDefaultInstance().getSubject()))
			_builder_.setSubject(subject);
		if (number != null && !number.equals(SubjectCount.getDefaultInstance().getNumber()))
			_builder_.setNumber(number);
		return _builder_.build();
	}
}
