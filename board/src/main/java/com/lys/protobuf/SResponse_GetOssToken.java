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
import com.lys.protobuf.ProtocolPair2.Response_GetOssToken;

public class SResponse_GetOssToken extends SPTData<Response_GetOssToken>
{
	private static final SResponse_GetOssToken DefaultInstance = new SResponse_GetOssToken();

	public String accessKeyId = null;
	public String accessKeySecret = null;
	public String securityToken = null;
	public String expiration = null;

	public static SResponse_GetOssToken create(String accessKeyId, String accessKeySecret, String securityToken, String expiration)
	{
		SResponse_GetOssToken obj = new SResponse_GetOssToken();
		obj.accessKeyId = accessKeyId;
		obj.accessKeySecret = accessKeySecret;
		obj.securityToken = securityToken;
		obj.expiration = expiration;
		return obj;
	}

	public SResponse_GetOssToken clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SResponse_GetOssToken _other_)
	{
		this.accessKeyId = _other_.accessKeyId;
		this.accessKeySecret = _other_.accessKeySecret;
		this.securityToken = _other_.securityToken;
		this.expiration = _other_.expiration;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("accessKeyId"))
			accessKeyId = _json_.getString("accessKeyId");
		if (_json_.containsKey("accessKeySecret"))
			accessKeySecret = _json_.getString("accessKeySecret");
		if (_json_.containsKey("securityToken"))
			securityToken = _json_.getString("securityToken");
		if (_json_.containsKey("expiration"))
			expiration = _json_.getString("expiration");
	}

	public static SResponse_GetOssToken load(String str)
	{
		try
		{
			SResponse_GetOssToken obj = new SResponse_GetOssToken();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_GetOssToken load(JSONObject json)
	{
		try
		{
			SResponse_GetOssToken obj = new SResponse_GetOssToken();
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
			if (accessKeyId != null)
				_json_.put("accessKeyId", accessKeyId);
			if (accessKeySecret != null)
				_json_.put("accessKeySecret", accessKeySecret);
			if (securityToken != null)
				_json_.put("securityToken", securityToken);
			if (expiration != null)
				_json_.put("expiration", expiration);
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SResponse_GetOssToken> loadList(JSONArray ja)
	{
		try
		{
			List<SResponse_GetOssToken> list = new ArrayList<SResponse_GetOssToken>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SResponse_GetOssToken item = SResponse_GetOssToken.load(jo);
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

	public static JSONArray saveList(List<SResponse_GetOssToken> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SResponse_GetOssToken item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Response_GetOssToken _proto_)
	{
		if (_proto_.hasAccessKeyId())
			accessKeyId = _proto_.getAccessKeyId();
		if (_proto_.hasAccessKeySecret())
			accessKeySecret = _proto_.getAccessKeySecret();
		if (_proto_.hasSecurityToken())
			securityToken = _proto_.getSecurityToken();
		if (_proto_.hasExpiration())
			expiration = _proto_.getExpiration();
	}

	public static SResponse_GetOssToken load(byte[] bytes)
	{
		try
		{
			SResponse_GetOssToken obj = new SResponse_GetOssToken();
			obj.parse(Response_GetOssToken.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_GetOssToken load(Response_GetOssToken proto)
	{
		try
		{
			SResponse_GetOssToken obj = new SResponse_GetOssToken();
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
	public Response_GetOssToken saveToProto()
	{
		Response_GetOssToken.Builder _builder_ = Response_GetOssToken.newBuilder();
		if (accessKeyId != null && !accessKeyId.equals(Response_GetOssToken.getDefaultInstance().getAccessKeyId()))
			_builder_.setAccessKeyId(accessKeyId);
		if (accessKeySecret != null && !accessKeySecret.equals(Response_GetOssToken.getDefaultInstance().getAccessKeySecret()))
			_builder_.setAccessKeySecret(accessKeySecret);
		if (securityToken != null && !securityToken.equals(Response_GetOssToken.getDefaultInstance().getSecurityToken()))
			_builder_.setSecurityToken(securityToken);
		if (expiration != null && !expiration.equals(Response_GetOssToken.getDefaultInstance().getExpiration()))
			_builder_.setExpiration(expiration);
		return _builder_.build();
	}
}
