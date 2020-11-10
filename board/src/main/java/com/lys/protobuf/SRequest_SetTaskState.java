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
import com.lys.protobuf.ProtocolPair.Request_SetTaskState;

// ---------------------- 设置任务状态 --------------------------
public class SRequest_SetTaskState extends SPTData<Request_SetTaskState>
{
	private static final SRequest_SetTaskState DefaultInstance = new SRequest_SetTaskState();

	public String taskId = null;
	public /*SPTaskState*/ Integer state = Request_SetTaskState.getDefaultInstance().getState().getNumber();

	public static SRequest_SetTaskState create(String taskId, Integer state)
	{
		SRequest_SetTaskState obj = new SRequest_SetTaskState();
		obj.taskId = taskId;
		obj.state = state;
		return obj;
	}

	public SRequest_SetTaskState clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SRequest_SetTaskState _other_)
	{
		this.taskId = _other_.taskId;
		this.state = _other_.state;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("taskId"))
			taskId = _json_.getString("taskId");
		if (_json_.containsKey("state"))
			state = _json_.getInteger("state");
	}

	public static SRequest_SetTaskState load(String str)
	{
		try
		{
			SRequest_SetTaskState obj = new SRequest_SetTaskState();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_SetTaskState load(JSONObject json)
	{
		try
		{
			SRequest_SetTaskState obj = new SRequest_SetTaskState();
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
			if (state != null)
				_json_.put("state", state);
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SRequest_SetTaskState> loadList(JSONArray ja)
	{
		try
		{
			List<SRequest_SetTaskState> list = new ArrayList<SRequest_SetTaskState>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SRequest_SetTaskState item = SRequest_SetTaskState.load(jo);
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

	public static JSONArray saveList(List<SRequest_SetTaskState> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SRequest_SetTaskState item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Request_SetTaskState _proto_)
	{
		if (_proto_.hasTaskId())
			taskId = _proto_.getTaskId();
		if (_proto_.hasState())
			state = _proto_.getState().getNumber();
	}

	public static SRequest_SetTaskState load(byte[] bytes)
	{
		try
		{
			SRequest_SetTaskState obj = new SRequest_SetTaskState();
			obj.parse(Request_SetTaskState.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_SetTaskState load(Request_SetTaskState proto)
	{
		try
		{
			SRequest_SetTaskState obj = new SRequest_SetTaskState();
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
	public Request_SetTaskState saveToProto()
	{
		Request_SetTaskState.Builder _builder_ = Request_SetTaskState.newBuilder();
		if (taskId != null && !taskId.equals(Request_SetTaskState.getDefaultInstance().getTaskId()))
			_builder_.setTaskId(taskId);
		if (state != null && Request_SetTaskState.getDefaultInstance().getState().getNumber() != state)
			_builder_.setState(ProtocolPair.PTaskState.valueOf(state));
		return _builder_.build();
	}
}
