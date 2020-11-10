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
import com.lys.protobuf.ProtocolBoard.SelectionGroup;

// 选项组
public class SSelectionGroup extends SPTData<SelectionGroup>
{
	private static final SSelectionGroup DefaultInstance = new SSelectionGroup();

	public Integer problemType = 0; // 题目类型
	public List<String> selections = new ArrayList<String>(); // 选项列表
	public List<String> rightAnswer = new ArrayList<String>(); // 正确答案
	public List<String> answer = new ArrayList<String>(); // 作答

	public static SSelectionGroup create(Integer problemType)
	{
		SSelectionGroup obj = new SSelectionGroup();
		obj.problemType = problemType;
		return obj;
	}

	public SSelectionGroup clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SSelectionGroup _other_)
	{
		this.problemType = _other_.problemType;
		this.selections = _other_.selections;
		this.rightAnswer = _other_.rightAnswer;
		this.answer = _other_.answer;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("problemType"))
			problemType = _json_.getInteger("problemType");
		if (_json_.containsKey("selections"))
			selections = AppDataTool.loadStringList(AppDataTool.getJSONArray(_json_, "selections"));
		if (_json_.containsKey("rightAnswer"))
			rightAnswer = AppDataTool.loadStringList(AppDataTool.getJSONArray(_json_, "rightAnswer"));
		if (_json_.containsKey("answer"))
			answer = AppDataTool.loadStringList(AppDataTool.getJSONArray(_json_, "answer"));
	}

	public static SSelectionGroup load(String str)
	{
		try
		{
			SSelectionGroup obj = new SSelectionGroup();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SSelectionGroup load(JSONObject json)
	{
		try
		{
			SSelectionGroup obj = new SSelectionGroup();
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
			if (problemType != null)
				_json_.put("problemType", problemType);
			if (selections != null)
				_json_.put("selections", AppDataTool.saveStringList(selections));
			if (rightAnswer != null)
				_json_.put("rightAnswer", AppDataTool.saveStringList(rightAnswer));
			if (answer != null)
				_json_.put("answer", AppDataTool.saveStringList(answer));
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SSelectionGroup> loadList(JSONArray ja)
	{
		try
		{
			List<SSelectionGroup> list = new ArrayList<SSelectionGroup>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SSelectionGroup item = SSelectionGroup.load(jo);
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

	public static JSONArray saveList(List<SSelectionGroup> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SSelectionGroup item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(SelectionGroup _proto_)
	{
		if (_proto_.hasProblemType())
			problemType = _proto_.getProblemType();
		for (int i = 0; i < _proto_.getSelectionsCount(); i++)
			selections.add(_proto_.getSelections(i));
		for (int i = 0; i < _proto_.getRightAnswerCount(); i++)
			rightAnswer.add(_proto_.getRightAnswer(i));
		for (int i = 0; i < _proto_.getAnswerCount(); i++)
			answer.add(_proto_.getAnswer(i));
	}

	public static SSelectionGroup load(byte[] bytes)
	{
		try
		{
			SSelectionGroup obj = new SSelectionGroup();
			obj.parse(SelectionGroup.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SSelectionGroup load(SelectionGroup proto)
	{
		try
		{
			SSelectionGroup obj = new SSelectionGroup();
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
	public SelectionGroup saveToProto()
	{
		SelectionGroup.Builder _builder_ = SelectionGroup.newBuilder();
		if (problemType != null && !problemType.equals(SelectionGroup.getDefaultInstance().getProblemType()))
			_builder_.setProblemType(problemType);
		if (selections != null)
			for (String _value_ : selections)
				_builder_.addSelections(_value_);
		if (rightAnswer != null)
			for (String _value_ : rightAnswer)
				_builder_.addRightAnswer(_value_);
		if (answer != null)
			for (String _value_ : answer)
				_builder_.addAnswer(_value_);
		return _builder_.build();
	}
}
