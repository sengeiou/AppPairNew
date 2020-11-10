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
import com.lys.protobuf.ProtocolPair2.Response_GetTopicStyles;

public class SResponse_GetTopicStyles extends SPTData<Response_GetTopicStyles>
{
	private static final SResponse_GetTopicStyles DefaultInstance = new SResponse_GetTopicStyles();

	public List<SProblemStyle> styles = new ArrayList<SProblemStyle>();

	public static SResponse_GetTopicStyles create()
	{
		SResponse_GetTopicStyles obj = new SResponse_GetTopicStyles();
		return obj;
	}

	public SResponse_GetTopicStyles clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SResponse_GetTopicStyles _other_)
	{
		this.styles = _other_.styles;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("styles"))
			styles = SProblemStyle.loadList(_json_.getJSONArray("styles"));
	}

	public static SResponse_GetTopicStyles load(String str)
	{
		try
		{
			SResponse_GetTopicStyles obj = new SResponse_GetTopicStyles();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_GetTopicStyles load(JSONObject json)
	{
		try
		{
			SResponse_GetTopicStyles obj = new SResponse_GetTopicStyles();
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
			if (styles != null)
				_json_.put("styles", SProblemStyle.saveList(styles));
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SResponse_GetTopicStyles> loadList(JSONArray ja)
	{
		try
		{
			List<SResponse_GetTopicStyles> list = new ArrayList<SResponse_GetTopicStyles>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SResponse_GetTopicStyles item = SResponse_GetTopicStyles.load(jo);
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

	public static JSONArray saveList(List<SResponse_GetTopicStyles> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SResponse_GetTopicStyles item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Response_GetTopicStyles _proto_)
	{
		for (int i = 0; i < _proto_.getStylesCount(); i++)
			styles.add(SProblemStyle.load(_proto_.getStyles(i)));
	}

	public static SResponse_GetTopicStyles load(byte[] bytes)
	{
		try
		{
			SResponse_GetTopicStyles obj = new SResponse_GetTopicStyles();
			obj.parse(Response_GetTopicStyles.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_GetTopicStyles load(Response_GetTopicStyles proto)
	{
		try
		{
			SResponse_GetTopicStyles obj = new SResponse_GetTopicStyles();
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
	public Response_GetTopicStyles saveToProto()
	{
		Response_GetTopicStyles.Builder _builder_ = Response_GetTopicStyles.newBuilder();
		if (styles != null)
			for (SProblemStyle _value_ : styles)
				_builder_.addStyles(_value_.saveToProto());
		return _builder_.build();
	}
}
