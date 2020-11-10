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
import com.lys.protobuf.ProtocolPair.PageData;

public class SPageData extends SPTData<PageData>
{
	private static final SPageData DefaultInstance = new SPageData();

	public Integer index = 0;
	public String bigVideo = null;
	public Boolean hasBoard = false;
	public SNotePage page = null;
	public SBoardConfig board = null;

	public static SPageData create(Integer index, String bigVideo, Boolean hasBoard, SNotePage page, SBoardConfig board)
	{
		SPageData obj = new SPageData();
		obj.index = index;
		obj.bigVideo = bigVideo;
		obj.hasBoard = hasBoard;
		obj.page = page;
		obj.board = board;
		return obj;
	}

	public SPageData clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SPageData _other_)
	{
		this.index = _other_.index;
		this.bigVideo = _other_.bigVideo;
		this.hasBoard = _other_.hasBoard;
		this.page = _other_.page;
		this.board = _other_.board;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("index"))
			index = _json_.getInteger("index");
		if (_json_.containsKey("bigVideo"))
			bigVideo = _json_.getString("bigVideo");
		if (_json_.containsKey("hasBoard"))
			hasBoard = _json_.getBoolean("hasBoard");
		if (_json_.containsKey("page"))
			page = SNotePage.load(_json_.getJSONObject("page"));
		if (_json_.containsKey("board"))
			board = SBoardConfig.load(_json_.getJSONObject("board"));
	}

	public static SPageData load(String str)
	{
		try
		{
			SPageData obj = new SPageData();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SPageData load(JSONObject json)
	{
		try
		{
			SPageData obj = new SPageData();
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
			if (index != null)
				_json_.put("index", index);
			if (bigVideo != null)
				_json_.put("bigVideo", bigVideo);
			if (hasBoard != null)
				_json_.put("hasBoard", hasBoard);
			if (page != null)
				_json_.put("page", page.saveToJson());
			if (board != null)
				_json_.put("board", board.saveToJson());
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SPageData> loadList(JSONArray ja)
	{
		try
		{
			List<SPageData> list = new ArrayList<SPageData>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SPageData item = SPageData.load(jo);
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

	public static JSONArray saveList(List<SPageData> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SPageData item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(PageData _proto_)
	{
		if (_proto_.hasIndex())
			index = _proto_.getIndex();
		if (_proto_.hasBigVideo())
			bigVideo = _proto_.getBigVideo();
		if (_proto_.hasHasBoard())
			hasBoard = _proto_.getHasBoard();
		if (_proto_.hasPage())
			page = SNotePage.load(_proto_.getPage());
		if (_proto_.hasBoard())
			board = SBoardConfig.load(_proto_.getBoard());
	}

	public static SPageData load(byte[] bytes)
	{
		try
		{
			SPageData obj = new SPageData();
			obj.parse(PageData.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SPageData load(PageData proto)
	{
		try
		{
			SPageData obj = new SPageData();
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
	public PageData saveToProto()
	{
		PageData.Builder _builder_ = PageData.newBuilder();
		if (index != null && !index.equals(PageData.getDefaultInstance().getIndex()))
			_builder_.setIndex(index);
		if (bigVideo != null && !bigVideo.equals(PageData.getDefaultInstance().getBigVideo()))
			_builder_.setBigVideo(bigVideo);
		if (hasBoard != null && !hasBoard.equals(PageData.getDefaultInstance().getHasBoard()))
			_builder_.setHasBoard(hasBoard);
		if (page != null)
			_builder_.setPage(page.saveToProto());
		if (board != null)
			_builder_.setBoard(board.saveToProto());
		return _builder_.build();
	}
}
