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
import com.lys.protobuf.ProtocolZhixue.Response_ZXPullAccount;

public class SResponse_ZXPullAccount extends SPTData<Response_ZXPullAccount>
{
	private static final SResponse_ZXPullAccount DefaultInstance = new SResponse_ZXPullAccount();

	public String account = null;
	public String psw = null;

	public static SResponse_ZXPullAccount create(String account, String psw)
	{
		SResponse_ZXPullAccount obj = new SResponse_ZXPullAccount();
		obj.account = account;
		obj.psw = psw;
		return obj;
	}

	public SResponse_ZXPullAccount clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SResponse_ZXPullAccount _other_)
	{
		this.account = _other_.account;
		this.psw = _other_.psw;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("account"))
			account = _json_.getString("account");
		if (_json_.containsKey("psw"))
			psw = _json_.getString("psw");
	}

	public static SResponse_ZXPullAccount load(String str)
	{
		try
		{
			SResponse_ZXPullAccount obj = new SResponse_ZXPullAccount();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_ZXPullAccount load(JSONObject json)
	{
		try
		{
			SResponse_ZXPullAccount obj = new SResponse_ZXPullAccount();
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
			if (psw != null)
				_json_.put("psw", psw);
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SResponse_ZXPullAccount> loadList(JSONArray ja)
	{
		try
		{
			List<SResponse_ZXPullAccount> list = new ArrayList<SResponse_ZXPullAccount>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SResponse_ZXPullAccount item = SResponse_ZXPullAccount.load(jo);
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

	public static JSONArray saveList(List<SResponse_ZXPullAccount> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SResponse_ZXPullAccount item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Response_ZXPullAccount _proto_)
	{
		if (_proto_.hasAccount())
			account = _proto_.getAccount();
		if (_proto_.hasPsw())
			psw = _proto_.getPsw();
	}

	public static SResponse_ZXPullAccount load(byte[] bytes)
	{
		try
		{
			SResponse_ZXPullAccount obj = new SResponse_ZXPullAccount();
			obj.parse(Response_ZXPullAccount.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_ZXPullAccount load(Response_ZXPullAccount proto)
	{
		try
		{
			SResponse_ZXPullAccount obj = new SResponse_ZXPullAccount();
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
	public Response_ZXPullAccount saveToProto()
	{
		Response_ZXPullAccount.Builder _builder_ = Response_ZXPullAccount.newBuilder();
		if (account != null && !account.equals(Response_ZXPullAccount.getDefaultInstance().getAccount()))
			_builder_.setAccount(account);
		if (psw != null && !psw.equals(Response_ZXPullAccount.getDefaultInstance().getPsw()))
			_builder_.setPsw(psw);
		return _builder_.build();
	}
}
