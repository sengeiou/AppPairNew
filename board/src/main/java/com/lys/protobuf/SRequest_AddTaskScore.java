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
import com.lys.protobuf.ProtocolPair.Request_AddTaskScore;

// ---------------------- 任务加积分 --------------------------
public class SRequest_AddTaskScore extends SPTData<Request_AddTaskScore>
{
	private static final SRequest_AddTaskScore DefaultInstance = new SRequest_AddTaskScore();

	public String taskId = null;
	public Integer score = 0;

	public static SRequest_AddTaskScore create(String taskId, Integer score)
	{
		SRequest_AddTaskScore obj = new SRequest_AddTaskScore();
		obj.taskId = taskId;
		obj.score = score;
		return obj;
	}

	public SRequest_AddTaskScore clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SRequest_AddTaskScore _other_)
	{
		this.taskId = _other_.taskId;
		this.score = _other_.score;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("taskId"))
			taskId = _json_.getString("taskId");
		if (_json_.containsKey("score"))
			score = _json_.getInteger("score");
	}

	public static SRequest_AddTaskScore load(String str)
	{
		try
		{
			SRequest_AddTaskScore obj = new SRequest_AddTaskScore();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_AddTaskScore load(JSONObject json)
	{
		try
		{
			SRequest_AddTaskScore obj = new SRequest_AddTaskScore();
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
			if (score != null)
				_json_.put("score", score);
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SRequest_AddTaskScore> loadList(JSONArray ja)
	{
		try
		{
			List<SRequest_AddTaskScore> list = new ArrayList<SRequest_AddTaskScore>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SRequest_AddTaskScore item = SRequest_AddTaskScore.load(jo);
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

	public static JSONArray saveList(List<SRequest_AddTaskScore> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SRequest_AddTaskScore item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Request_AddTaskScore _proto_)
	{
		if (_proto_.hasTaskId())
			taskId = _proto_.getTaskId();
		if (_proto_.hasScore())
			score = _proto_.getScore();
	}

	public static SRequest_AddTaskScore load(byte[] bytes)
	{
		try
		{
			SRequest_AddTaskScore obj = new SRequest_AddTaskScore();
			obj.parse(Request_AddTaskScore.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_AddTaskScore load(Request_AddTaskScore proto)
	{
		try
		{
			SRequest_AddTaskScore obj = new SRequest_AddTaskScore();
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
	public Request_AddTaskScore saveToProto()
	{
		Request_AddTaskScore.Builder _builder_ = Request_AddTaskScore.newBuilder();
		if (taskId != null && !taskId.equals(Request_AddTaskScore.getDefaultInstance().getTaskId()))
			_builder_.setTaskId(taskId);
		if (score != null && !score.equals(Request_AddTaskScore.getDefaultInstance().getScore()))
			_builder_.setScore(score);
		return _builder_.build();
	}
}
