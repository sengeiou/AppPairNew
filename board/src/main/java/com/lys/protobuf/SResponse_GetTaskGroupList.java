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
import com.lys.protobuf.ProtocolScore.Response_GetTaskGroupList;

public class SResponse_GetTaskGroupList extends SPTData<Response_GetTaskGroupList>
{
	private static final SResponse_GetTaskGroupList DefaultInstance = new SResponse_GetTaskGroupList();

	public List<STaskGroup> taskGroupList = new ArrayList<STaskGroup>();

	public static SResponse_GetTaskGroupList create()
	{
		SResponse_GetTaskGroupList obj = new SResponse_GetTaskGroupList();
		return obj;
	}

	public SResponse_GetTaskGroupList clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SResponse_GetTaskGroupList _other_)
	{
		this.taskGroupList = _other_.taskGroupList;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("taskGroupList"))
			taskGroupList = STaskGroup.loadList(_json_.getJSONArray("taskGroupList"));
	}

	public static SResponse_GetTaskGroupList load(String str)
	{
		try
		{
			SResponse_GetTaskGroupList obj = new SResponse_GetTaskGroupList();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_GetTaskGroupList load(JSONObject json)
	{
		try
		{
			SResponse_GetTaskGroupList obj = new SResponse_GetTaskGroupList();
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
			if (taskGroupList != null)
				_json_.put("taskGroupList", STaskGroup.saveList(taskGroupList));
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SResponse_GetTaskGroupList> loadList(JSONArray ja)
	{
		try
		{
			List<SResponse_GetTaskGroupList> list = new ArrayList<SResponse_GetTaskGroupList>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SResponse_GetTaskGroupList item = SResponse_GetTaskGroupList.load(jo);
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

	public static JSONArray saveList(List<SResponse_GetTaskGroupList> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SResponse_GetTaskGroupList item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Response_GetTaskGroupList _proto_)
	{
		for (int i = 0; i < _proto_.getTaskGroupListCount(); i++)
			taskGroupList.add(STaskGroup.load(_proto_.getTaskGroupList(i)));
	}

	public static SResponse_GetTaskGroupList load(byte[] bytes)
	{
		try
		{
			SResponse_GetTaskGroupList obj = new SResponse_GetTaskGroupList();
			obj.parse(Response_GetTaskGroupList.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_GetTaskGroupList load(Response_GetTaskGroupList proto)
	{
		try
		{
			SResponse_GetTaskGroupList obj = new SResponse_GetTaskGroupList();
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
	public Response_GetTaskGroupList saveToProto()
	{
		Response_GetTaskGroupList.Builder _builder_ = Response_GetTaskGroupList.newBuilder();
		if (taskGroupList != null)
			for (STaskGroup _value_ : taskGroupList)
				_builder_.addTaskGroupList(_value_.saveToProto());
		return _builder_.build();
	}
}
