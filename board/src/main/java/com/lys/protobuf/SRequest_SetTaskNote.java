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
import com.lys.protobuf.ProtocolPair.Request_SetTaskNote;

// ---------------------- 设置任务备注 --------------------------
public class SRequest_SetTaskNote extends SPTData<Request_SetTaskNote>
{
	private static final SRequest_SetTaskNote DefaultInstance = new SRequest_SetTaskNote();

	public String taskId = null;
	public String note = null;

	public static SRequest_SetTaskNote create(String taskId, String note)
	{
		SRequest_SetTaskNote obj = new SRequest_SetTaskNote();
		obj.taskId = taskId;
		obj.note = note;
		return obj;
	}

	public SRequest_SetTaskNote clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SRequest_SetTaskNote _other_)
	{
		this.taskId = _other_.taskId;
		this.note = _other_.note;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("taskId"))
			taskId = _json_.getString("taskId");
		if (_json_.containsKey("note"))
			note = _json_.getString("note");
	}

	public static SRequest_SetTaskNote load(String str)
	{
		try
		{
			SRequest_SetTaskNote obj = new SRequest_SetTaskNote();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_SetTaskNote load(JSONObject json)
	{
		try
		{
			SRequest_SetTaskNote obj = new SRequest_SetTaskNote();
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
			if (taskId != null)
				_json_.put("taskId", taskId);
			if (note != null)
				_json_.put("note", note);
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SRequest_SetTaskNote> loadList(JSONArray ja)
	{
		try
		{
			List<SRequest_SetTaskNote> list = new ArrayList<SRequest_SetTaskNote>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SRequest_SetTaskNote item = SRequest_SetTaskNote.load(jo);
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

	public static JSONArray saveList(List<SRequest_SetTaskNote> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SRequest_SetTaskNote item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Request_SetTaskNote _proto_)
	{
		if (_proto_.hasTaskId())
			taskId = _proto_.getTaskId();
		if (_proto_.hasNote())
			note = _proto_.getNote();
	}

	public static SRequest_SetTaskNote load(byte[] bytes)
	{
		try
		{
			SRequest_SetTaskNote obj = new SRequest_SetTaskNote();
			obj.parse(Request_SetTaskNote.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_SetTaskNote load(Request_SetTaskNote proto)
	{
		try
		{
			SRequest_SetTaskNote obj = new SRequest_SetTaskNote();
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
	public Request_SetTaskNote saveToProto()
	{
		Request_SetTaskNote.Builder _builder_ = Request_SetTaskNote.newBuilder();
		if (taskId != null && !taskId.equals(Request_SetTaskNote.getDefaultInstance().getTaskId()))
			_builder_.setTaskId(taskId);
		if (note != null && !note.equals(Request_SetTaskNote.getDefaultInstance().getNote()))
			_builder_.setNote(note);
		return _builder_.build();
	}
}
