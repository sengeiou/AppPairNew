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
import com.lys.protobuf.ProtocolPair.Request_SendTask;

// ---------------------- 发送任务 --------------------------
public class SRequest_SendTask extends SPTData<Request_SendTask>
{
	private static final SRequest_SendTask DefaultInstance = new SRequest_SendTask();

	public List<String> userIds = new ArrayList<String>();
	public List<String> taskIds = new ArrayList<String>();
	public String text = null;
	public String sendUserId = null;

	public static SRequest_SendTask create(String text, String sendUserId)
	{
		SRequest_SendTask obj = new SRequest_SendTask();
		obj.text = text;
		obj.sendUserId = sendUserId;
		return obj;
	}

	public SRequest_SendTask clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SRequest_SendTask _other_)
	{
		this.userIds = _other_.userIds;
		this.taskIds = _other_.taskIds;
		this.text = _other_.text;
		this.sendUserId = _other_.sendUserId;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("userIds"))
			userIds = AppDataTool.loadStringList(AppDataTool.getJSONArray(_json_, "userIds"));
		if (_json_.containsKey("taskIds"))
			taskIds = AppDataTool.loadStringList(AppDataTool.getJSONArray(_json_, "taskIds"));
		if (_json_.containsKey("text"))
			text = _json_.getString("text");
		if (_json_.containsKey("sendUserId"))
			sendUserId = _json_.getString("sendUserId");
	}

	public static SRequest_SendTask load(String str)
	{
		try
		{
			SRequest_SendTask obj = new SRequest_SendTask();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_SendTask load(JSONObject json)
	{
		try
		{
			SRequest_SendTask obj = new SRequest_SendTask();
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
			if (userIds != null)
				_json_.put("userIds", AppDataTool.saveStringList(userIds));
			if (taskIds != null)
				_json_.put("taskIds", AppDataTool.saveStringList(taskIds));
			if (text != null)
				_json_.put("text", text);
			if (sendUserId != null)
				_json_.put("sendUserId", sendUserId);
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SRequest_SendTask> loadList(JSONArray ja)
	{
		try
		{
			List<SRequest_SendTask> list = new ArrayList<SRequest_SendTask>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SRequest_SendTask item = SRequest_SendTask.load(jo);
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

	public static JSONArray saveList(List<SRequest_SendTask> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SRequest_SendTask item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Request_SendTask _proto_)
	{
		for (int i = 0; i < _proto_.getUserIdsCount(); i++)
			userIds.add(_proto_.getUserIds(i));
		for (int i = 0; i < _proto_.getTaskIdsCount(); i++)
			taskIds.add(_proto_.getTaskIds(i));
		if (_proto_.hasText())
			text = _proto_.getText();
		if (_proto_.hasSendUserId())
			sendUserId = _proto_.getSendUserId();
	}

	public static SRequest_SendTask load(byte[] bytes)
	{
		try
		{
			SRequest_SendTask obj = new SRequest_SendTask();
			obj.parse(Request_SendTask.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_SendTask load(Request_SendTask proto)
	{
		try
		{
			SRequest_SendTask obj = new SRequest_SendTask();
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
	public Request_SendTask saveToProto()
	{
		Request_SendTask.Builder _builder_ = Request_SendTask.newBuilder();
		if (userIds != null)
			for (String _value_ : userIds)
				_builder_.addUserIds(_value_);
		if (taskIds != null)
			for (String _value_ : taskIds)
				_builder_.addTaskIds(_value_);
		if (text != null && !text.equals(Request_SendTask.getDefaultInstance().getText()))
			_builder_.setText(text);
		if (sendUserId != null && !sendUserId.equals(Request_SendTask.getDefaultInstance().getSendUserId()))
			_builder_.setSendUserId(sendUserId);
		return _builder_.build();
	}
}
