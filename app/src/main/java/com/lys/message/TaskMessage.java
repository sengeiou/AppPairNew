package com.lys.message;

import android.os.Handler;
import android.os.Parcel;
import android.text.TextUtils;

import com.lys.base.utils.LOG;
import com.lys.protobuf.ProtocolPair;
import com.lys.protobuf.SPTask;
import com.lys.protobuf.SUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import io.rong.common.ParcelUtils;
import io.rong.imkit.RongIM;
import io.rong.imlib.IRongCallback;
import io.rong.imlib.MessageTag;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.MessageContent;

@MessageTag(value = "app:TaskMsg", flag = MessageTag.ISCOUNTED | MessageTag.ISPERSISTED)
public class TaskMessage extends MessageContent
{
	public String id = null;
	public String userId = null;
	public String sendUser_id = null;
	public /*SUserType*/ Integer sendUser_userType = ProtocolPair.User.getDefaultInstance().getUserType().getNumber();
	public String sendUser_name = null;
	public String sendUser_head = null;
	public /*SPTaskType*/ Integer type = ProtocolPair.PTask.getDefaultInstance().getType().getNumber();
	public String group = null;
	public String name = null;
	public Long createTime = 0L;
	public Integer state = 0;

	@Override
	public byte[] encode()
	{
		JSONObject jsonObj = new JSONObject();

		try
		{
			jsonObj.put("id", id);
			jsonObj.put("userId", userId);
			jsonObj.put("sendUser_id", sendUser_id);
			jsonObj.put("sendUser_userType", sendUser_userType);
			jsonObj.put("sendUser_name", sendUser_name);
			jsonObj.put("sendUser_head", sendUser_head);
			jsonObj.put("type", type);
			jsonObj.put("group", group);
			jsonObj.put("name", name);
			jsonObj.put("createTime", createTime);
			jsonObj.put("state", state);
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}

		try
		{
			return jsonObj.toString().getBytes("UTF-8");
		}
		catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}

		return null;
	}

	protected TaskMessage()
	{
	}

	protected static TaskMessage obtain(SPTask task)
	{
		TaskMessage message = new TaskMessage();
		message.id = task.id;
		message.userId = task.userId;
		if (task.sendUser != null)
		{
			message.sendUser_id = task.sendUser.id;
			message.sendUser_userType = task.sendUser.userType;
			message.sendUser_name = task.sendUser.name;
			message.sendUser_head = task.sendUser.head;
		}
		message.type = task.type;
		message.group = task.group;
		message.name = task.name;
		message.createTime = task.createTime;
		message.state = task.state;
		return message;
	}

	public SPTask convert()
	{
		SPTask task = new SPTask();
		task.id = this.id;
		task.userId = this.userId;
		if (!TextUtils.isEmpty(this.sendUser_id))
		{
			task.sendUser = new SUser();
			task.sendUser.id = this.sendUser_id;
			task.sendUser.userType = this.sendUser_userType;
			task.sendUser.name = this.sendUser_name;
			task.sendUser.head = this.sendUser_head;
		}
		task.type = this.type;
		task.group = this.group;
		task.name = this.name;
		task.createTime = this.createTime;
		task.state = this.state;
		return task;
	}

	protected static void send(String target, TaskMessage taskMessage, IRongCallback.ISendMessageCallback callback)
	{
		Message message = Message.obtain(target, Conversation.ConversationType.PRIVATE, taskMessage);
		RongIM.getInstance().sendMessage(message, null, null, callback);
	}

	public TaskMessage(byte[] data)
	{
		String jsonStr = null;

		try
		{
			jsonStr = new String(data, "UTF-8");
		}
		catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}

		try
		{
			JSONObject jsonObj = new JSONObject(jsonStr);
			if (jsonObj.has("id"))
				id = jsonObj.optString("id");
			if (jsonObj.has("userId"))
				userId = jsonObj.optString("userId");
			if (jsonObj.has("sendUser_id"))
				sendUser_id = jsonObj.optString("sendUser_id");
			if (jsonObj.has("sendUser_userType"))
				sendUser_userType = jsonObj.optInt("sendUser_userType");
			if (jsonObj.has("sendUser_name"))
				sendUser_name = jsonObj.optString("sendUser_name");
			if (jsonObj.has("sendUser_head"))
				sendUser_head = jsonObj.optString("sendUser_head");
			if (jsonObj.has("type"))
				type = jsonObj.optInt("type");
			if (jsonObj.has("group"))
				group = jsonObj.optString("group");
			if (jsonObj.has("name"))
				name = jsonObj.optString("name");
			if (jsonObj.has("createTime"))
				createTime = jsonObj.optLong("createTime");
			if (jsonObj.has("state"))
				state = jsonObj.optInt("state");
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}
	}

	public TaskMessage(Parcel in)
	{
		id = ParcelUtils.readFromParcel(in);
		userId = ParcelUtils.readFromParcel(in);
		sendUser_id = ParcelUtils.readFromParcel(in);
		sendUser_userType = ParcelUtils.readIntFromParcel(in);
		sendUser_name = ParcelUtils.readFromParcel(in);
		sendUser_head = ParcelUtils.readFromParcel(in);
		type = ParcelUtils.readIntFromParcel(in);
		group = ParcelUtils.readFromParcel(in);
		name = ParcelUtils.readFromParcel(in);
		createTime = ParcelUtils.readLongFromParcel(in);
		state = ParcelUtils.readIntFromParcel(in);
	}

	public static final Creator<TaskMessage> CREATOR = new Creator<TaskMessage>()
	{
		@Override
		public TaskMessage createFromParcel(Parcel source)
		{
			return new TaskMessage(source);
		}

		@Override
		public TaskMessage[] newArray(int size)
		{
			return new TaskMessage[size];
		}
	};

	@Override
	public int describeContents()
	{
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags)
	{
		ParcelUtils.writeToParcel(dest, id);
		ParcelUtils.writeToParcel(dest, userId);
		ParcelUtils.writeToParcel(dest, sendUser_id);
		ParcelUtils.writeToParcel(dest, sendUser_userType);
		ParcelUtils.writeToParcel(dest, sendUser_name);
		ParcelUtils.writeToParcel(dest, sendUser_head);
		ParcelUtils.writeToParcel(dest, type);
		ParcelUtils.writeToParcel(dest, group);
		ParcelUtils.writeToParcel(dest, name);
		ParcelUtils.writeToParcel(dest, createTime);
		ParcelUtils.writeToParcel(dest, state);
	}

	@Override
	public String toString()
	{
		return String.format("TaskMessage{id=%s, userId=%s, group=%s, name=%s}", id, userId, group, name);
	}

	//--------------------------------------------------------------

	private static ConcurrentLinkedQueue<TaskMessage> mSendQueue = new ConcurrentLinkedQueue<>();
	private static boolean isSending = false;

	private static void send()
	{
		final TaskMessage taskMessage = mSendQueue.peek(); // 获取但不移除
		LOG.v("send : " + taskMessage.toString());
		send(taskMessage.userId, taskMessage, new IRongCallback.ISendMessageCallback()
		{
			@Override
			public void onAttached(Message message)
			{
			}

			@Override
			public void onSuccess(Message message)
			{
				LOG.v("onSuccess");
				mSendQueue.remove(taskMessage);
				if (!mSendQueue.isEmpty())
					send();
				else
					isSending = false;
			}

			@Override
			public void onError(Message message, RongIMClient.ErrorCode errorCode)
			{
				LOG.v("onFailed " + errorCode);
				new Handler().postDelayed(new Runnable()
				{
					@Override
					public void run()
					{
						send();
					}
				}, 100);
			}
		});
	}

	private static void startSend()
	{
		if (!isSending && !mSendQueue.isEmpty())
		{
			isSending = true;
			send();
		}
	}

	public static void sendTasks(List<SPTask> tasks)
	{
		for (SPTask task : tasks)
		{
			TaskMessage taskMessage = TaskMessage.obtain(task);
			mSendQueue.offer(taskMessage);
		}
		startSend();
	}

}
