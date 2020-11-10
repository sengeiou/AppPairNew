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
import com.lys.protobuf.ProtocolPair.Response_GetTaskForWeb;

public class SResponse_GetTaskForWeb extends SPTData<Response_GetTaskForWeb>
{
	private static final SResponse_GetTaskForWeb DefaultInstance = new SResponse_GetTaskForWeb();

	public String userId = null;
	public String id = null;
	public String urlRoot = null;
	public String name = null;
	public Integer count = 0;
	public Boolean singlePage = false;
	public String prevPage = null;
	public String nextPage = null;
	public List<SPageData> pageDatas = new ArrayList<SPageData>();

	public static SResponse_GetTaskForWeb create(String userId, String id, String urlRoot, String name, Integer count, Boolean singlePage, String prevPage, String nextPage)
	{
		SResponse_GetTaskForWeb obj = new SResponse_GetTaskForWeb();
		obj.userId = userId;
		obj.id = id;
		obj.urlRoot = urlRoot;
		obj.name = name;
		obj.count = count;
		obj.singlePage = singlePage;
		obj.prevPage = prevPage;
		obj.nextPage = nextPage;
		return obj;
	}

	public SResponse_GetTaskForWeb clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SResponse_GetTaskForWeb _other_)
	{
		this.userId = _other_.userId;
		this.id = _other_.id;
		this.urlRoot = _other_.urlRoot;
		this.name = _other_.name;
		this.count = _other_.count;
		this.singlePage = _other_.singlePage;
		this.prevPage = _other_.prevPage;
		this.nextPage = _other_.nextPage;
		this.pageDatas = _other_.pageDatas;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("userId"))
			userId = _json_.getString("userId");
		if (_json_.containsKey("id"))
			id = _json_.getString("id");
		if (_json_.containsKey("urlRoot"))
			urlRoot = _json_.getString("urlRoot");
		if (_json_.containsKey("name"))
			name = _json_.getString("name");
		if (_json_.containsKey("count"))
			count = _json_.getInteger("count");
		if (_json_.containsKey("singlePage"))
			singlePage = _json_.getBoolean("singlePage");
		if (_json_.containsKey("prevPage"))
			prevPage = _json_.getString("prevPage");
		if (_json_.containsKey("nextPage"))
			nextPage = _json_.getString("nextPage");
		if (_json_.containsKey("pageDatas"))
			pageDatas = SPageData.loadList(_json_.getJSONArray("pageDatas"));
	}

	public static SResponse_GetTaskForWeb load(String str)
	{
		try
		{
			SResponse_GetTaskForWeb obj = new SResponse_GetTaskForWeb();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_GetTaskForWeb load(JSONObject json)
	{
		try
		{
			SResponse_GetTaskForWeb obj = new SResponse_GetTaskForWeb();
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
			if (userId != null)
				_json_.put("userId", userId);
			if (id != null)
				_json_.put("id", id);
			if (urlRoot != null)
				_json_.put("urlRoot", urlRoot);
			if (name != null)
				_json_.put("name", name);
			if (count != null)
				_json_.put("count", count);
			if (singlePage != null)
				_json_.put("singlePage", singlePage);
			if (prevPage != null)
				_json_.put("prevPage", prevPage);
			if (nextPage != null)
				_json_.put("nextPage", nextPage);
			if (pageDatas != null)
				_json_.put("pageDatas", SPageData.saveList(pageDatas));
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SResponse_GetTaskForWeb> loadList(JSONArray ja)
	{
		try
		{
			List<SResponse_GetTaskForWeb> list = new ArrayList<SResponse_GetTaskForWeb>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SResponse_GetTaskForWeb item = SResponse_GetTaskForWeb.load(jo);
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

	public static JSONArray saveList(List<SResponse_GetTaskForWeb> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SResponse_GetTaskForWeb item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Response_GetTaskForWeb _proto_)
	{
		if (_proto_.hasUserId())
			userId = _proto_.getUserId();
		if (_proto_.hasId())
			id = _proto_.getId();
		if (_proto_.hasUrlRoot())
			urlRoot = _proto_.getUrlRoot();
		if (_proto_.hasName())
			name = _proto_.getName();
		if (_proto_.hasCount())
			count = _proto_.getCount();
		if (_proto_.hasSinglePage())
			singlePage = _proto_.getSinglePage();
		if (_proto_.hasPrevPage())
			prevPage = _proto_.getPrevPage();
		if (_proto_.hasNextPage())
			nextPage = _proto_.getNextPage();
		for (int i = 0; i < _proto_.getPageDatasCount(); i++)
			pageDatas.add(SPageData.load(_proto_.getPageDatas(i)));
	}

	public static SResponse_GetTaskForWeb load(byte[] bytes)
	{
		try
		{
			SResponse_GetTaskForWeb obj = new SResponse_GetTaskForWeb();
			obj.parse(Response_GetTaskForWeb.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_GetTaskForWeb load(Response_GetTaskForWeb proto)
	{
		try
		{
			SResponse_GetTaskForWeb obj = new SResponse_GetTaskForWeb();
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
	public Response_GetTaskForWeb saveToProto()
	{
		Response_GetTaskForWeb.Builder _builder_ = Response_GetTaskForWeb.newBuilder();
		if (userId != null && !userId.equals(Response_GetTaskForWeb.getDefaultInstance().getUserId()))
			_builder_.setUserId(userId);
		if (id != null && !id.equals(Response_GetTaskForWeb.getDefaultInstance().getId()))
			_builder_.setId(id);
		if (urlRoot != null && !urlRoot.equals(Response_GetTaskForWeb.getDefaultInstance().getUrlRoot()))
			_builder_.setUrlRoot(urlRoot);
		if (name != null && !name.equals(Response_GetTaskForWeb.getDefaultInstance().getName()))
			_builder_.setName(name);
		if (count != null && !count.equals(Response_GetTaskForWeb.getDefaultInstance().getCount()))
			_builder_.setCount(count);
		if (singlePage != null && !singlePage.equals(Response_GetTaskForWeb.getDefaultInstance().getSinglePage()))
			_builder_.setSinglePage(singlePage);
		if (prevPage != null && !prevPage.equals(Response_GetTaskForWeb.getDefaultInstance().getPrevPage()))
			_builder_.setPrevPage(prevPage);
		if (nextPage != null && !nextPage.equals(Response_GetTaskForWeb.getDefaultInstance().getNextPage()))
			_builder_.setNextPage(nextPage);
		if (pageDatas != null)
			for (SPageData _value_ : pageDatas)
				_builder_.addPageDatas(_value_.saveToProto());
		return _builder_.build();
	}
}
