package com.lys.message;

import android.os.Parcel;

import com.lys.base.utils.CommonUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import io.rong.common.ParcelUtils;
import io.rong.imlib.MessageTag;
import io.rong.imlib.model.MessageContent;

@MessageTag(value = "app:BoardMsg")
public class BoardMessage extends MessageContent
{
	private String id;
	private int index;
	private int count;
	private String content;

	public String getId()
	{
		return id;
	}

	public int getIndex()
	{
		return index;
	}

	public int getCount()
	{
		return count;
	}

	public String getContent()
	{
		return content;
	}

	@Override
	public byte[] encode()
	{
		JSONObject jsonObj = new JSONObject();

		try
		{
			jsonObj.put("id", id);
			jsonObj.put("index", index);
			jsonObj.put("count", count);
			jsonObj.put("content", content);
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

	protected BoardMessage()
	{
	}

	private static final int block_size = 120 * 1024;

	public static List<BoardMessage> obtain(String text)
	{
		List<BoardMessage> messages = new ArrayList<>();
		if (text.length() > block_size)
		{
			int count = text.length() / block_size;
			if (text.length() % block_size > 0)
				count++;
			String id = CommonUtils.uuid();
			for (int i = 0; i < count; i++)
			{
				String block = text.substring(i * block_size, Math.min(i * block_size + block_size, text.length()));
				BoardMessage message = new BoardMessage();
				message.id = id;
				message.index = i;
				message.count = count;
				message.content = block;
				messages.add(message);
			}
		}
		else
		{
			BoardMessage message = new BoardMessage();
			message.index = 0;
			message.count = 1;
			message.content = text;
			messages.add(message);
		}
		return messages;
	}

	public BoardMessage(byte[] data)
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
			if (jsonObj.has("index"))
				index = jsonObj.optInt("index");
			if (jsonObj.has("count"))
				count = jsonObj.optInt("count");
			if (jsonObj.has("content"))
				content = jsonObj.optString("content");
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}
	}

	public BoardMessage(Parcel in)
	{
		id = ParcelUtils.readFromParcel(in);
		index = ParcelUtils.readIntFromParcel(in);
		count = ParcelUtils.readIntFromParcel(in);
		content = ParcelUtils.readFromParcel(in);
	}

	public static final Creator<BoardMessage> CREATOR = new Creator<BoardMessage>()
	{
		@Override
		public BoardMessage createFromParcel(Parcel source)
		{
			return new BoardMessage(source);
		}

		@Override
		public BoardMessage[] newArray(int size)
		{
			return new BoardMessage[size];
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
		ParcelUtils.writeToParcel(dest, index);
		ParcelUtils.writeToParcel(dest, count);
		ParcelUtils.writeToParcel(dest, content);
	}

	@Override
	public String toString()
	{
		return String.format("BoardMessage{id=%s, %s/%s, length=%s}", id, index, count, content.length());
	}
}
