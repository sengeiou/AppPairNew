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
import com.lys.protobuf.ProtocolZhixue.Request_ZXReportAccount;

// ---------------------- XXXXXX --------------------------
public class SRequest_ZXReportAccount extends SPTData<Request_ZXReportAccount>
{
	private static final SRequest_ZXReportAccount DefaultInstance = new SRequest_ZXReportAccount();

	public String account = null;
	public String state = null;

	public static SRequest_ZXReportAccount create(String account, String state)
	{
		SRequest_ZXReportAccount obj = new SRequest_ZXReportAccount();
		obj.account = account;
		obj.state = state;
		return obj;
	}

	public SRequest_ZXReportAccount clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SRequest_ZXReportAccount _other_)
	{
		this.account = _other_.account;
		this.state = _other_.state;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("account"))
			account = _json_.getString("account");
		if (_json_.containsKey("state"))
			state = _json_.getString("state");
	}

	public static SRequest_ZXReportAccount load(String str)
	{
		try
		{
			SRequest_ZXReportAccount obj = new SRequest_ZXReportAccount();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_ZXReportAccount load(JSONObject json)
	{
		try
		{
			SRequest_ZXReportAccount obj = new SRequest_ZXReportAccount();
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
			if (account != null)
				_json_.put("account", account);
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

	public static List<SRequest_ZXReportAccount> loadList(JSONArray ja)
	{
		try
		{
			List<SRequest_ZXReportAccount> list = new ArrayList<SRequest_ZXReportAccount>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SRequest_ZXReportAccount item = SRequest_ZXReportAccount.load(jo);
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

	public static JSONArray saveList(List<SRequest_ZXReportAccount> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SRequest_ZXReportAccount item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Request_ZXReportAccount _proto_)
	{
		if (_proto_.hasAccount())
			account = _proto_.getAccount();
		if (_proto_.hasState())
			state = _proto_.getState();
	}

	public static SRequest_ZXReportAccount load(byte[] bytes)
	{
		try
		{
			SRequest_ZXReportAccount obj = new SRequest_ZXReportAccount();
			obj.parse(Request_ZXReportAccount.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_ZXReportAccount load(Request_ZXReportAccount proto)
	{
		try
		{
			SRequest_ZXReportAccount obj = new SRequest_ZXReportAccount();
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
	public Request_ZXReportAccount saveToProto()
	{
		Request_ZXReportAccount.Builder _builder_ = Request_ZXReportAccount.newBuilder();
		if (account != null && !account.equals(Request_ZXReportAccount.getDefaultInstance().getAccount()))
			_builder_.setAccount(account);
		if (state != null && !state.equals(Request_ZXReportAccount.getDefaultInstance().getState()))
			_builder_.setState(state);
		return _builder_.build();
	}
}
