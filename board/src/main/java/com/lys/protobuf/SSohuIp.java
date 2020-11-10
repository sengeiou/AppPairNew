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
import com.lys.protobuf.ProtocolCommon.SohuIp;

public class SSohuIp extends SPTData<SohuIp>
{
	private static final SSohuIp DefaultInstance = new SSohuIp();

	public String cip = null;
	public String cid = null;
	public String cname = null;

	public static SSohuIp create(String cip, String cid, String cname)
	{
		SSohuIp obj = new SSohuIp();
		obj.cip = cip;
		obj.cid = cid;
		obj.cname = cname;
		return obj;
	}

	public SSohuIp clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SSohuIp _other_)
	{
		this.cip = _other_.cip;
		this.cid = _other_.cid;
		this.cname = _other_.cname;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("cip"))
			cip = _json_.getString("cip");
		if (_json_.containsKey("cid"))
			cid = _json_.getString("cid");
		if (_json_.containsKey("cname"))
			cname = _json_.getString("cname");
	}

	public static SSohuIp load(String str)
	{
		try
		{
			SSohuIp obj = new SSohuIp();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SSohuIp load(JSONObject json)
	{
		try
		{
			SSohuIp obj = new SSohuIp();
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
			if (cip != null)
				_json_.put("cip", cip);
			if (cid != null)
				_json_.put("cid", cid);
			if (cname != null)
				_json_.put("cname", cname);
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SSohuIp> loadList(JSONArray ja)
	{
		try
		{
			List<SSohuIp> list = new ArrayList<SSohuIp>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SSohuIp item = SSohuIp.load(jo);
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

	public static JSONArray saveList(List<SSohuIp> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SSohuIp item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(SohuIp _proto_)
	{
		if (_proto_.hasCip())
			cip = _proto_.getCip();
		if (_proto_.hasCid())
			cid = _proto_.getCid();
		if (_proto_.hasCname())
			cname = _proto_.getCname();
	}

	public static SSohuIp load(byte[] bytes)
	{
		try
		{
			SSohuIp obj = new SSohuIp();
			obj.parse(SohuIp.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SSohuIp load(SohuIp proto)
	{
		try
		{
			SSohuIp obj = new SSohuIp();
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
	public SohuIp saveToProto()
	{
		SohuIp.Builder _builder_ = SohuIp.newBuilder();
		if (cip != null && !cip.equals(SohuIp.getDefaultInstance().getCip()))
			_builder_.setCip(cip);
		if (cid != null && !cid.equals(SohuIp.getDefaultInstance().getCid()))
			_builder_.setCid(cid);
		if (cname != null && !cname.equals(SohuIp.getDefaultInstance().getCname()))
			_builder_.setCname(cname);
		return _builder_.build();
	}
}
