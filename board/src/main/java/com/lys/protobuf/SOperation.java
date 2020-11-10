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
import com.lys.protobuf.ProtocolBoard.Operation;

public class SOperation extends SPTData<Operation>
{
	private static final SOperation DefaultInstance = new SOperation();

	public /*SOperationType*/ Integer operationType = Operation.getDefaultInstance().getOperationType().getNumber();
	public String pageDir = null;
	public Integer height = 0;
	public Integer scrollY = 0;
	public Integer newPageIndex = 0;
	public String newPageDir = null;
	public List<String> deletePageDirs = new ArrayList<String>();
	public String cover = null;
	public SDrawing drawing = null;
	public byte[] bitmapData = null;
	public byte[] videoData = null;
	public byte[] parseData = null;
	public String photoName = null;
	public Integer photoX = 0;
	public Integer photoY = 0;
	public Integer photoRotation = 0;
	public Integer photoWidth = 0;
	public Integer photoHeight = 0;
	public Boolean hide = false;
	public SSelectionGroup selectionGroup = null;
	public SBoardText boardText = null;
	public SBoardPhoto photo = null;

	public static SOperation create(Integer operationType, String pageDir, Integer height, Integer scrollY, Integer newPageIndex, String newPageDir, String cover, SDrawing drawing, byte[] bitmapData, byte[] videoData, byte[] parseData, String photoName, Integer photoX, Integer photoY, Integer photoRotation, Integer photoWidth, Integer photoHeight, Boolean hide, SSelectionGroup selectionGroup, SBoardText boardText, SBoardPhoto photo)
	{
		SOperation obj = new SOperation();
		obj.operationType = operationType;
		obj.pageDir = pageDir;
		obj.height = height;
		obj.scrollY = scrollY;
		obj.newPageIndex = newPageIndex;
		obj.newPageDir = newPageDir;
		obj.cover = cover;
		obj.drawing = drawing;
		obj.bitmapData = bitmapData;
		obj.videoData = videoData;
		obj.parseData = parseData;
		obj.photoName = photoName;
		obj.photoX = photoX;
		obj.photoY = photoY;
		obj.photoRotation = photoRotation;
		obj.photoWidth = photoWidth;
		obj.photoHeight = photoHeight;
		obj.hide = hide;
		obj.selectionGroup = selectionGroup;
		obj.boardText = boardText;
		obj.photo = photo;
		return obj;
	}

	public SOperation clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SOperation _other_)
	{
		this.operationType = _other_.operationType;
		this.pageDir = _other_.pageDir;
		this.height = _other_.height;
		this.scrollY = _other_.scrollY;
		this.newPageIndex = _other_.newPageIndex;
		this.newPageDir = _other_.newPageDir;
		this.deletePageDirs = _other_.deletePageDirs;
		this.cover = _other_.cover;
		this.drawing = _other_.drawing;
		this.bitmapData = _other_.bitmapData;
		this.videoData = _other_.videoData;
		this.parseData = _other_.parseData;
		this.photoName = _other_.photoName;
		this.photoX = _other_.photoX;
		this.photoY = _other_.photoY;
		this.photoRotation = _other_.photoRotation;
		this.photoWidth = _other_.photoWidth;
		this.photoHeight = _other_.photoHeight;
		this.hide = _other_.hide;
		this.selectionGroup = _other_.selectionGroup;
		this.boardText = _other_.boardText;
		this.photo = _other_.photo;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("operationType"))
			operationType = _json_.getInteger("operationType");
		if (_json_.containsKey("pageDir"))
			pageDir = _json_.getString("pageDir");
		if (_json_.containsKey("height"))
			height = _json_.getInteger("height");
		if (_json_.containsKey("scrollY"))
			scrollY = _json_.getInteger("scrollY");
		if (_json_.containsKey("newPageIndex"))
			newPageIndex = _json_.getInteger("newPageIndex");
		if (_json_.containsKey("newPageDir"))
			newPageDir = _json_.getString("newPageDir");
		if (_json_.containsKey("deletePageDirs"))
			deletePageDirs = AppDataTool.loadStringList(AppDataTool.getJSONArray(_json_, "deletePageDirs"));
		if (_json_.containsKey("cover"))
			cover = _json_.getString("cover");
		if (_json_.containsKey("drawing"))
			drawing = SDrawing.load(_json_.getJSONObject("drawing"));
		if (_json_.containsKey("bitmapData"))
			bitmapData = _json_.getBytes("bitmapData");
		if (_json_.containsKey("videoData"))
			videoData = _json_.getBytes("videoData");
		if (_json_.containsKey("parseData"))
			parseData = _json_.getBytes("parseData");
		if (_json_.containsKey("photoName"))
			photoName = _json_.getString("photoName");
		if (_json_.containsKey("photoX"))
			photoX = _json_.getInteger("photoX");
		if (_json_.containsKey("photoY"))
			photoY = _json_.getInteger("photoY");
		if (_json_.containsKey("photoRotation"))
			photoRotation = _json_.getInteger("photoRotation");
		if (_json_.containsKey("photoWidth"))
			photoWidth = _json_.getInteger("photoWidth");
		if (_json_.containsKey("photoHeight"))
			photoHeight = _json_.getInteger("photoHeight");
		if (_json_.containsKey("hide"))
			hide = _json_.getBoolean("hide");
		if (_json_.containsKey("selectionGroup"))
			selectionGroup = SSelectionGroup.load(_json_.getJSONObject("selectionGroup"));
		if (_json_.containsKey("boardText"))
			boardText = SBoardText.load(_json_.getJSONObject("boardText"));
		if (_json_.containsKey("photo"))
			photo = SBoardPhoto.load(_json_.getJSONObject("photo"));
	}

	public static SOperation load(String str)
	{
		try
		{
			SOperation obj = new SOperation();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SOperation load(JSONObject json)
	{
		try
		{
			SOperation obj = new SOperation();
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
			if (operationType != null)
				_json_.put("operationType", operationType);
			if (pageDir != null)
				_json_.put("pageDir", pageDir);
			if (height != null)
				_json_.put("height", height);
			if (scrollY != null)
				_json_.put("scrollY", scrollY);
			if (newPageIndex != null)
				_json_.put("newPageIndex", newPageIndex);
			if (newPageDir != null)
				_json_.put("newPageDir", newPageDir);
			if (deletePageDirs != null)
				_json_.put("deletePageDirs", AppDataTool.saveStringList(deletePageDirs));
			if (cover != null)
				_json_.put("cover", cover);
			if (drawing != null)
				_json_.put("drawing", drawing.saveToJson());
			if (bitmapData != null)
				_json_.put("bitmapData", bitmapData);
			if (videoData != null)
				_json_.put("videoData", videoData);
			if (parseData != null)
				_json_.put("parseData", parseData);
			if (photoName != null)
				_json_.put("photoName", photoName);
			if (photoX != null)
				_json_.put("photoX", photoX);
			if (photoY != null)
				_json_.put("photoY", photoY);
			if (photoRotation != null)
				_json_.put("photoRotation", photoRotation);
			if (photoWidth != null)
				_json_.put("photoWidth", photoWidth);
			if (photoHeight != null)
				_json_.put("photoHeight", photoHeight);
			if (hide != null)
				_json_.put("hide", hide);
			if (selectionGroup != null)
				_json_.put("selectionGroup", selectionGroup.saveToJson());
			if (boardText != null)
				_json_.put("boardText", boardText.saveToJson());
			if (photo != null)
				_json_.put("photo", photo.saveToJson());
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SOperation> loadList(JSONArray ja)
	{
		try
		{
			List<SOperation> list = new ArrayList<SOperation>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SOperation item = SOperation.load(jo);
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

	public static JSONArray saveList(List<SOperation> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SOperation item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Operation _proto_)
	{
		if (_proto_.hasOperationType())
			operationType = _proto_.getOperationType().getNumber();
		if (_proto_.hasPageDir())
			pageDir = _proto_.getPageDir();
		if (_proto_.hasHeight())
			height = _proto_.getHeight();
		if (_proto_.hasScrollY())
			scrollY = _proto_.getScrollY();
		if (_proto_.hasNewPageIndex())
			newPageIndex = _proto_.getNewPageIndex();
		if (_proto_.hasNewPageDir())
			newPageDir = _proto_.getNewPageDir();
		for (int i = 0; i < _proto_.getDeletePageDirsCount(); i++)
			deletePageDirs.add(_proto_.getDeletePageDirs(i));
		if (_proto_.hasCover())
			cover = _proto_.getCover();
		if (_proto_.hasDrawing())
			drawing = SDrawing.load(_proto_.getDrawing());
		if (_proto_.hasBitmapData())
			bitmapData = _proto_.getBitmapData().toByteArray();
		if (_proto_.hasVideoData())
			videoData = _proto_.getVideoData().toByteArray();
		if (_proto_.hasParseData())
			parseData = _proto_.getParseData().toByteArray();
		if (_proto_.hasPhotoName())
			photoName = _proto_.getPhotoName();
		if (_proto_.hasPhotoX())
			photoX = _proto_.getPhotoX();
		if (_proto_.hasPhotoY())
			photoY = _proto_.getPhotoY();
		if (_proto_.hasPhotoRotation())
			photoRotation = _proto_.getPhotoRotation();
		if (_proto_.hasPhotoWidth())
			photoWidth = _proto_.getPhotoWidth();
		if (_proto_.hasPhotoHeight())
			photoHeight = _proto_.getPhotoHeight();
		if (_proto_.hasHide())
			hide = _proto_.getHide();
		if (_proto_.hasSelectionGroup())
			selectionGroup = SSelectionGroup.load(_proto_.getSelectionGroup());
		if (_proto_.hasBoardText())
			boardText = SBoardText.load(_proto_.getBoardText());
		if (_proto_.hasPhoto())
			photo = SBoardPhoto.load(_proto_.getPhoto());
	}

	public static SOperation load(byte[] bytes)
	{
		try
		{
			SOperation obj = new SOperation();
			obj.parse(Operation.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SOperation load(Operation proto)
	{
		try
		{
			SOperation obj = new SOperation();
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
	public Operation saveToProto()
	{
		Operation.Builder _builder_ = Operation.newBuilder();
		if (operationType != null && Operation.getDefaultInstance().getOperationType().getNumber() != operationType)
			_builder_.setOperationType(ProtocolBoard.OperationType.valueOf(operationType));
		if (pageDir != null && !pageDir.equals(Operation.getDefaultInstance().getPageDir()))
			_builder_.setPageDir(pageDir);
		if (height != null && !height.equals(Operation.getDefaultInstance().getHeight()))
			_builder_.setHeight(height);
		if (scrollY != null && !scrollY.equals(Operation.getDefaultInstance().getScrollY()))
			_builder_.setScrollY(scrollY);
		if (newPageIndex != null && !newPageIndex.equals(Operation.getDefaultInstance().getNewPageIndex()))
			_builder_.setNewPageIndex(newPageIndex);
		if (newPageDir != null && !newPageDir.equals(Operation.getDefaultInstance().getNewPageDir()))
			_builder_.setNewPageDir(newPageDir);
		if (deletePageDirs != null)
			for (String _value_ : deletePageDirs)
				_builder_.addDeletePageDirs(_value_);
		if (cover != null && !cover.equals(Operation.getDefaultInstance().getCover()))
			_builder_.setCover(cover);
		if (drawing != null)
			_builder_.setDrawing(drawing.saveToProto());
		if (bitmapData != null)
			_builder_.setBitmapData(ByteString.copyFrom(bitmapData));
		if (videoData != null)
			_builder_.setVideoData(ByteString.copyFrom(videoData));
		if (parseData != null)
			_builder_.setParseData(ByteString.copyFrom(parseData));
		if (photoName != null && !photoName.equals(Operation.getDefaultInstance().getPhotoName()))
			_builder_.setPhotoName(photoName);
		if (photoX != null && !photoX.equals(Operation.getDefaultInstance().getPhotoX()))
			_builder_.setPhotoX(photoX);
		if (photoY != null && !photoY.equals(Operation.getDefaultInstance().getPhotoY()))
			_builder_.setPhotoY(photoY);
		if (photoRotation != null && !photoRotation.equals(Operation.getDefaultInstance().getPhotoRotation()))
			_builder_.setPhotoRotation(photoRotation);
		if (photoWidth != null && !photoWidth.equals(Operation.getDefaultInstance().getPhotoWidth()))
			_builder_.setPhotoWidth(photoWidth);
		if (photoHeight != null && !photoHeight.equals(Operation.getDefaultInstance().getPhotoHeight()))
			_builder_.setPhotoHeight(photoHeight);
		if (hide != null && !hide.equals(Operation.getDefaultInstance().getHide()))
			_builder_.setHide(hide);
		if (selectionGroup != null)
			_builder_.setSelectionGroup(selectionGroup.saveToProto());
		if (boardText != null)
			_builder_.setBoardText(boardText.saveToProto());
		if (photo != null)
			_builder_.setPhoto(photo.saveToProto());
		return _builder_.build();
	}
}
