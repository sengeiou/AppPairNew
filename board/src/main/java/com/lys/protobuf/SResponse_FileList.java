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
import com.lys.protobuf.ProtocolPair2.Response_FileList;

public class SResponse_FileList extends SPTData<Response_FileList>
{
	private static final SResponse_FileList DefaultInstance = new SResponse_FileList();

	public String root = null;
	public List<SFilePath> paths = new ArrayList<SFilePath>();

	public static SResponse_FileList create(String root)
	{
		SResponse_FileList obj = new SResponse_FileList();
		obj.root = root;
		return obj;
	}

	public SResponse_FileList clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SResponse_FileList _other_)
	{
		this.root = _other_.root;
		this.paths = _other_.paths;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("root"))
			root = _json_.getString("root");
		if (_json_.containsKey("paths"))
			paths = SFilePath.loadList(_json_.getJSONArray("paths"));
	}

	public static SResponse_FileList load(String str)
	{
		try
		{
			SResponse_FileList obj = new SResponse_FileList();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_FileList load(JSONObject json)
	{
		try
		{
			SResponse_FileList obj = new SResponse_FileList();
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
			if (root != null)
				_json_.put("root", root);
			if (paths != null)
				_json_.put("paths", SFilePath.saveList(paths));
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SResponse_FileList> loadList(JSONArray ja)
	{
		try
		{
			List<SResponse_FileList> list = new ArrayList<SResponse_FileList>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SResponse_FileList item = SResponse_FileList.load(jo);
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

	public static JSONArray saveList(List<SResponse_FileList> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SResponse_FileList item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Response_FileList _proto_)
	{
		if (_proto_.hasRoot())
			root = _proto_.getRoot();
		for (int i = 0; i < _proto_.getPathsCount(); i++)
			paths.add(SFilePath.load(_proto_.getPaths(i)));
	}

	public static SResponse_FileList load(byte[] bytes)
	{
		try
		{
			SResponse_FileList obj = new SResponse_FileList();
			obj.parse(Response_FileList.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_FileList load(Response_FileList proto)
	{
		try
		{
			SResponse_FileList obj = new SResponse_FileList();
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
	public Response_FileList saveToProto()
	{
		Response_FileList.Builder _builder_ = Response_FileList.newBuilder();
		if (root != null && !root.equals(Response_FileList.getDefaultInstance().getRoot()))
			_builder_.setRoot(root);
		if (paths != null)
			for (SFilePath _value_ : paths)
				_builder_.addPaths(_value_.saveToProto());
		return _builder_.build();
	}
}
