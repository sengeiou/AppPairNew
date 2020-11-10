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
import com.lys.protobuf.ProtocolPair.Request_ModifyTaskComment;

// ---------------------- 修改任务点评 --------------------------
public class SRequest_ModifyTaskComment extends SPTData<Request_ModifyTaskComment>
{
	private static final SRequest_ModifyTaskComment DefaultInstance = new SRequest_ModifyTaskComment();

	public String taskId = null;
	public String comment = null;

	public static SRequest_ModifyTaskComment create(String taskId, String comment)
	{
		SRequest_ModifyTaskComment obj = new SRequest_ModifyTaskComment();
		obj.taskId = taskId;
		obj.comment = comment;
		return obj;
	}

	public SRequest_ModifyTaskComment clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SRequest_ModifyTaskComment _other_)
	{
		this.taskId = _other_.taskId;
		this.comment = _other_.comment;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("taskId"))
			taskId = _json_.getString("taskId");
		if (_json_.containsKey("comment"))
			comment = _json_.getString("comment");
	}

	public static SRequest_ModifyTaskComment load(String str)
	{
		try
		{
			SRequest_ModifyTaskComment obj = new SRequest_ModifyTaskComment();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_ModifyTaskComment load(JSONObject json)
	{
		try
		{
			SRequest_ModifyTaskComment obj = new SRequest_ModifyTaskComment();
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
			if (comment != null)
				_json_.put("comment", comment);
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SRequest_ModifyTaskComment> loadList(JSONArray ja)
	{
		try
		{
			List<SRequest_ModifyTaskComment> list = new ArrayList<SRequest_ModifyTaskComment>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SRequest_ModifyTaskComment item = SRequest_ModifyTaskComment.load(jo);
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

	public static JSONArray saveList(List<SRequest_ModifyTaskComment> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SRequest_ModifyTaskComment item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Request_ModifyTaskComment _proto_)
	{
		if (_proto_.hasTaskId())
			taskId = _proto_.getTaskId();
		if (_proto_.hasComment())
			comment = _proto_.getComment();
	}

	public static SRequest_ModifyTaskComment load(byte[] bytes)
	{
		try
		{
			SRequest_ModifyTaskComment obj = new SRequest_ModifyTaskComment();
			obj.parse(Request_ModifyTaskComment.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_ModifyTaskComment load(Request_ModifyTaskComment proto)
	{
		try
		{
			SRequest_ModifyTaskComment obj = new SRequest_ModifyTaskComment();
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
	public Request_ModifyTaskComment saveToProto()
	{
		Request_ModifyTaskComment.Builder _builder_ = Request_ModifyTaskComment.newBuilder();
		if (taskId != null && !taskId.equals(Request_ModifyTaskComment.getDefaultInstance().getTaskId()))
			_builder_.setTaskId(taskId);
		if (comment != null && !comment.equals(Request_ModifyTaskComment.getDefaultInstance().getComment()))
			_builder_.setComment(comment);
		return _builder_.build();
	}
}
