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
import com.lys.protobuf.ProtocolPair2.Response_GetConfig;

public class SResponse_GetConfig extends SPTData<Response_GetConfig>
{
	private static final SResponse_GetConfig DefaultInstance = new SResponse_GetConfig();

	public String urlRoot = null;
	public String root = null;
	public String api = null;
	public String upload = null;
	public Long time = 0L;
	public String svnUrl = null;
	public String svnAccount = null;
	public String svnPsw = null;
	public Boolean zhixueErrorMode = false; // 智学错模式

	public static SResponse_GetConfig create(String urlRoot, String root, String api, String upload, Long time, String svnUrl, String svnAccount, String svnPsw, Boolean zhixueErrorMode)
	{
		SResponse_GetConfig obj = new SResponse_GetConfig();
		obj.urlRoot = urlRoot;
		obj.root = root;
		obj.api = api;
		obj.upload = upload;
		obj.time = time;
		obj.svnUrl = svnUrl;
		obj.svnAccount = svnAccount;
		obj.svnPsw = svnPsw;
		obj.zhixueErrorMode = zhixueErrorMode;
		return obj;
	}

	public SResponse_GetConfig clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SResponse_GetConfig _other_)
	{
		this.urlRoot = _other_.urlRoot;
		this.root = _other_.root;
		this.api = _other_.api;
		this.upload = _other_.upload;
		this.time = _other_.time;
		this.svnUrl = _other_.svnUrl;
		this.svnAccount = _other_.svnAccount;
		this.svnPsw = _other_.svnPsw;
		this.zhixueErrorMode = _other_.zhixueErrorMode;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("urlRoot"))
			urlRoot = _json_.getString("urlRoot");
		if (_json_.containsKey("root"))
			root = _json_.getString("root");
		if (_json_.containsKey("api"))
			api = _json_.getString("api");
		if (_json_.containsKey("upload"))
			upload = _json_.getString("upload");
		if (_json_.containsKey("time"))
			time = _json_.getLong("time");
		if (_json_.containsKey("svnUrl"))
			svnUrl = _json_.getString("svnUrl");
		if (_json_.containsKey("svnAccount"))
			svnAccount = _json_.getString("svnAccount");
		if (_json_.containsKey("svnPsw"))
			svnPsw = _json_.getString("svnPsw");
		if (_json_.containsKey("zhixueErrorMode"))
			zhixueErrorMode = _json_.getBoolean("zhixueErrorMode");
	}

	public static SResponse_GetConfig load(String str)
	{
		try
		{
			SResponse_GetConfig obj = new SResponse_GetConfig();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_GetConfig load(JSONObject json)
	{
		try
		{
			SResponse_GetConfig obj = new SResponse_GetConfig();
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
			if (urlRoot != null)
				_json_.put("urlRoot", urlRoot);
			if (root != null)
				_json_.put("root", root);
			if (api != null)
				_json_.put("api", api);
			if (upload != null)
				_json_.put("upload", upload);
			if (time != null)
				_json_.put("time", String.valueOf(time));
			if (svnUrl != null)
				_json_.put("svnUrl", svnUrl);
			if (svnAccount != null)
				_json_.put("svnAccount", svnAccount);
			if (svnPsw != null)
				_json_.put("svnPsw", svnPsw);
			if (zhixueErrorMode != null)
				_json_.put("zhixueErrorMode", zhixueErrorMode);
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SResponse_GetConfig> loadList(JSONArray ja)
	{
		try
		{
			List<SResponse_GetConfig> list = new ArrayList<SResponse_GetConfig>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SResponse_GetConfig item = SResponse_GetConfig.load(jo);
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

	public static JSONArray saveList(List<SResponse_GetConfig> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SResponse_GetConfig item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Response_GetConfig _proto_)
	{
		if (_proto_.hasUrlRoot())
			urlRoot = _proto_.getUrlRoot();
		if (_proto_.hasRoot())
			root = _proto_.getRoot();
		if (_proto_.hasApi())
			api = _proto_.getApi();
		if (_proto_.hasUpload())
			upload = _proto_.getUpload();
		if (_proto_.hasTime())
			time = _proto_.getTime();
		if (_proto_.hasSvnUrl())
			svnUrl = _proto_.getSvnUrl();
		if (_proto_.hasSvnAccount())
			svnAccount = _proto_.getSvnAccount();
		if (_proto_.hasSvnPsw())
			svnPsw = _proto_.getSvnPsw();
		if (_proto_.hasZhixueErrorMode())
			zhixueErrorMode = _proto_.getZhixueErrorMode();
	}

	public static SResponse_GetConfig load(byte[] bytes)
	{
		try
		{
			SResponse_GetConfig obj = new SResponse_GetConfig();
			obj.parse(Response_GetConfig.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_GetConfig load(Response_GetConfig proto)
	{
		try
		{
			SResponse_GetConfig obj = new SResponse_GetConfig();
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
	public Response_GetConfig saveToProto()
	{
		Response_GetConfig.Builder _builder_ = Response_GetConfig.newBuilder();
		if (urlRoot != null && !urlRoot.equals(Response_GetConfig.getDefaultInstance().getUrlRoot()))
			_builder_.setUrlRoot(urlRoot);
		if (root != null && !root.equals(Response_GetConfig.getDefaultInstance().getRoot()))
			_builder_.setRoot(root);
		if (api != null && !api.equals(Response_GetConfig.getDefaultInstance().getApi()))
			_builder_.setApi(api);
		if (upload != null && !upload.equals(Response_GetConfig.getDefaultInstance().getUpload()))
			_builder_.setUpload(upload);
		if (time != null && !time.equals(Response_GetConfig.getDefaultInstance().getTime()))
			_builder_.setTime(time);
		if (svnUrl != null && !svnUrl.equals(Response_GetConfig.getDefaultInstance().getSvnUrl()))
			_builder_.setSvnUrl(svnUrl);
		if (svnAccount != null && !svnAccount.equals(Response_GetConfig.getDefaultInstance().getSvnAccount()))
			_builder_.setSvnAccount(svnAccount);
		if (svnPsw != null && !svnPsw.equals(Response_GetConfig.getDefaultInstance().getSvnPsw()))
			_builder_.setSvnPsw(svnPsw);
		if (zhixueErrorMode != null && !zhixueErrorMode.equals(Response_GetConfig.getDefaultInstance().getZhixueErrorMode()))
			_builder_.setZhixueErrorMode(zhixueErrorMode);
		return _builder_.build();
	}
}
