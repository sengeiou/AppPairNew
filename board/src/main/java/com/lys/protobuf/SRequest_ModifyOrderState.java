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
import com.lys.protobuf.ProtocolScore.Request_ModifyOrderState;

// ---------------------- xxxxxxxx --------------------------
public class SRequest_ModifyOrderState extends SPTData<Request_ModifyOrderState>
{
	private static final SRequest_ModifyOrderState DefaultInstance = new SRequest_ModifyOrderState();

	public String orderId = null;
	public /*SOrderState*/ Integer state = Request_ModifyOrderState.getDefaultInstance().getState().getNumber();

	public static SRequest_ModifyOrderState create(String orderId, Integer state)
	{
		SRequest_ModifyOrderState obj = new SRequest_ModifyOrderState();
		obj.orderId = orderId;
		obj.state = state;
		return obj;
	}

	public SRequest_ModifyOrderState clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SRequest_ModifyOrderState _other_)
	{
		this.orderId = _other_.orderId;
		this.state = _other_.state;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("orderId"))
			orderId = _json_.getString("orderId");
		if (_json_.containsKey("state"))
			state = _json_.getInteger("state");
	}

	public static SRequest_ModifyOrderState load(String str)
	{
		try
		{
			SRequest_ModifyOrderState obj = new SRequest_ModifyOrderState();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_ModifyOrderState load(JSONObject json)
	{
		try
		{
			SRequest_ModifyOrderState obj = new SRequest_ModifyOrderState();
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
			if (orderId != null)
				_json_.put("orderId", orderId);
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

	public static List<SRequest_ModifyOrderState> loadList(JSONArray ja)
	{
		try
		{
			List<SRequest_ModifyOrderState> list = new ArrayList<SRequest_ModifyOrderState>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SRequest_ModifyOrderState item = SRequest_ModifyOrderState.load(jo);
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

	public static JSONArray saveList(List<SRequest_ModifyOrderState> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SRequest_ModifyOrderState item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Request_ModifyOrderState _proto_)
	{
		if (_proto_.hasOrderId())
			orderId = _proto_.getOrderId();
		if (_proto_.hasState())
			state = _proto_.getState().getNumber();
	}

	public static SRequest_ModifyOrderState load(byte[] bytes)
	{
		try
		{
			SRequest_ModifyOrderState obj = new SRequest_ModifyOrderState();
			obj.parse(Request_ModifyOrderState.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_ModifyOrderState load(Request_ModifyOrderState proto)
	{
		try
		{
			SRequest_ModifyOrderState obj = new SRequest_ModifyOrderState();
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
	public Request_ModifyOrderState saveToProto()
	{
		Request_ModifyOrderState.Builder _builder_ = Request_ModifyOrderState.newBuilder();
		if (orderId != null && !orderId.equals(Request_ModifyOrderState.getDefaultInstance().getOrderId()))
			_builder_.setOrderId(orderId);
		if (state != null && Request_ModifyOrderState.getDefaultInstance().getState().getNumber() != state)
			_builder_.setState(ProtocolScore.OrderState.valueOf(state));
		return _builder_.build();
	}
}
