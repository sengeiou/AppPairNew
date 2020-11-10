package com.lys.message;

import android.os.Parcel;

import com.lys.App;
import com.lys.base.utils.CommonUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import io.rong.common.ParcelUtils;
import io.rong.imkit.RongIM;
import io.rong.imlib.IRongCallback;
import io.rong.imlib.MessageTag;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.MessageContent;

@MessageTag(value = "app:TransMsg")
public class TransMessage extends MessageContent
{
	public static JSONArray saveStringList(List<String> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			ja.put(list.get(i));
		}
		return ja;
	}

	public static List<String> loadStringList(JSONArray ja)
	{
		List<String> list = new ArrayList<>();
		for (int i = 0; i < ja.length(); i++)
		{
			try
			{
				list.add(ja.getString(i));
			}
			catch (JSONException e)
			{
				e.printStackTrace();
			}
		}
		return list;
	}

	public static final long MaxValidTime = 4102459200411L; // 2100, 1, 1, 12, 0, 0

	private String id;
	private String from;
	private String to;
	private String evt;
	private String msg;
	private List<String> params = new ArrayList<>();
	private long validTime = 10 * 1000;
	private long ts = System.currentTimeMillis() - App.TimeOffset;

	public String getId()
	{
		return id;
	}

	public String getFrom()
	{
		return from;
	}

	public String getTo()
	{
		return to;
	}

	public String getEvt()
	{
		return evt;
	}

	public String getMsg()
	{
		return msg;
	}

	public String getParam(int index)
	{
		if (index >= 0 && index < params.size())
			return params.get(index);
		return null;
	}

	public void addParam(String param)
	{
		this.params.add(param);
	}

	public long getValidTime()
	{
		return validTime;
	}

	public void setValidTime(long validTime)
	{
		this.validTime = validTime;
	}

	public long getTs()
	{
		return ts;
	}

	// 返回剩余时间
	public long leftTime()
	{
		return validTime - (System.currentTimeMillis() - App.TimeOffset - ts);
	}

	// 判断是否有效
	public boolean isValid()
	{
		return leftTime() > 0;
	}

	@Override
	public byte[] encode()
	{
		JSONObject jsonObj = new JSONObject();

		try
		{
			jsonObj.put("id", id);
			jsonObj.put("from", from);
			jsonObj.put("to", to);
			jsonObj.put("evt", evt);
			jsonObj.put("msg", msg);
			jsonObj.put("params", saveStringList(params));
			jsonObj.put("validTime", validTime);
			jsonObj.put("ts", ts);
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

	protected TransMessage()
	{
	}

	public static TransMessage obtain(String evt, String msg, String... params)
	{
		TransMessage message = new TransMessage();
		message.id = CommonUtils.uuid();
		message.from = "";
		message.to = "";
		message.evt = evt;
		message.msg = msg;
		if (params != null && params.length > 0)
		{
			for (String param : params)
			{
				message.addParam(param);
			}
		}
		return message;
	}

	public static void send(String target, TransMessage transMessage, IRongCallback.ISendMessageCallback callback)
	{
		Message message = Message.obtain(target, Conversation.ConversationType.PRIVATE, transMessage);
		RongIM.getInstance().sendMessage(message, null, null, callback);
	}

	public TransMessage(byte[] data)
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
			if (jsonObj.has("from"))
				from = jsonObj.optString("from");
			if (jsonObj.has("to"))
				to = jsonObj.optString("to");
			if (jsonObj.has("evt"))
				evt = jsonObj.optString("evt");
			if (jsonObj.has("msg"))
				msg = jsonObj.optString("msg");
			if (jsonObj.has("params"))
				params = loadStringList(jsonObj.optJSONArray("params"));
			if (jsonObj.has("validTime"))
				validTime = jsonObj.optLong("validTime");
			if (jsonObj.has("ts"))
				ts = jsonObj.optLong("ts");
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}
	}

	public TransMessage(Parcel in)
	{
		id = ParcelUtils.readFromParcel(in);
		from = ParcelUtils.readFromParcel(in);
		to = ParcelUtils.readFromParcel(in);
		evt = ParcelUtils.readFromParcel(in);
		msg = ParcelUtils.readFromParcel(in);
		params = ParcelUtils.readListFromParcel(in, String.class);
		validTime = ParcelUtils.readLongFromParcel(in);
		ts = ParcelUtils.readLongFromParcel(in);
	}

	public static final Creator<TransMessage> CREATOR = new Creator<TransMessage>()
	{
		@Override
		public TransMessage createFromParcel(Parcel source)
		{
			return new TransMessage(source);
		}

		@Override
		public TransMessage[] newArray(int size)
		{
			return new TransMessage[size];
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
		ParcelUtils.writeToParcel(dest, from);
		ParcelUtils.writeToParcel(dest, to);
		ParcelUtils.writeToParcel(dest, evt);
		ParcelUtils.writeToParcel(dest, msg);
		ParcelUtils.writeToParcel(dest, params);
		ParcelUtils.writeToParcel(dest, validTime);
		ParcelUtils.writeToParcel(dest, ts);
	}

	@Override
	public String toString()
	{
		return String.format("TransMessage{id=%s, from=%s, to=%s, evt=%s, msg=%s, params=%s, validTime=%s, ts=%s}", id, from, to, evt, msg, params, validTime, ts);
	}
}
