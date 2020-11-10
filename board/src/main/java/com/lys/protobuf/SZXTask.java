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
import com.lys.protobuf.ProtocolZhixue.ZXTask;

public class SZXTask extends SPTData<ZXTask>
{
	private static final SZXTask DefaultInstance = new SZXTask();

	public String id = null;
	public String phase = null;
	public String subject = null;
	public String material = null;
	public String diff = null;
	public String area = null;
	public String year = null;
	public String currChapterPath = null;
	public Integer currPage = 0;
	public Integer totalPage = 0;
	public String deviceId = null;

	public static SZXTask create(String id, String phase, String subject, String material, String diff, String area, String year, String currChapterPath, Integer currPage, Integer totalPage, String deviceId)
	{
		SZXTask obj = new SZXTask();
		obj.id = id;
		obj.phase = phase;
		obj.subject = subject;
		obj.material = material;
		obj.diff = diff;
		obj.area = area;
		obj.year = year;
		obj.currChapterPath = currChapterPath;
		obj.currPage = currPage;
		obj.totalPage = totalPage;
		obj.deviceId = deviceId;
		return obj;
	}

	public SZXTask clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SZXTask _other_)
	{
		this.id = _other_.id;
		this.phase = _other_.phase;
		this.subject = _other_.subject;
		this.material = _other_.material;
		this.diff = _other_.diff;
		this.area = _other_.area;
		this.year = _other_.year;
		this.currChapterPath = _other_.currChapterPath;
		this.currPage = _other_.currPage;
		this.totalPage = _other_.totalPage;
		this.deviceId = _other_.deviceId;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("id"))
			id = _json_.getString("id");
		if (_json_.containsKey("phase"))
			phase = _json_.getString("phase");
		if (_json_.containsKey("subject"))
			subject = _json_.getString("subject");
		if (_json_.containsKey("material"))
			material = _json_.getString("material");
		if (_json_.containsKey("diff"))
			diff = _json_.getString("diff");
		if (_json_.containsKey("area"))
			area = _json_.getString("area");
		if (_json_.containsKey("year"))
			year = _json_.getString("year");
		if (_json_.containsKey("currChapterPath"))
			currChapterPath = _json_.getString("currChapterPath");
		if (_json_.containsKey("currPage"))
			currPage = _json_.getInteger("currPage");
		if (_json_.containsKey("totalPage"))
			totalPage = _json_.getInteger("totalPage");
		if (_json_.containsKey("deviceId"))
			deviceId = _json_.getString("deviceId");
	}

	public static SZXTask load(String str)
	{
		try
		{
			SZXTask obj = new SZXTask();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SZXTask load(JSONObject json)
	{
		try
		{
			SZXTask obj = new SZXTask();
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
			if (id != null)
				_json_.put("id", id);
			if (phase != null)
				_json_.put("phase", phase);
			if (subject != null)
				_json_.put("subject", subject);
			if (material != null)
				_json_.put("material", material);
			if (diff != null)
				_json_.put("diff", diff);
			if (area != null)
				_json_.put("area", area);
			if (year != null)
				_json_.put("year", year);
			if (currChapterPath != null)
				_json_.put("currChapterPath", currChapterPath);
			if (currPage != null)
				_json_.put("currPage", currPage);
			if (totalPage != null)
				_json_.put("totalPage", totalPage);
			if (deviceId != null)
				_json_.put("deviceId", deviceId);
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SZXTask> loadList(JSONArray ja)
	{
		try
		{
			List<SZXTask> list = new ArrayList<SZXTask>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SZXTask item = SZXTask.load(jo);
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

	public static JSONArray saveList(List<SZXTask> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SZXTask item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(ZXTask _proto_)
	{
		if (_proto_.hasId())
			id = _proto_.getId();
		if (_proto_.hasPhase())
			phase = _proto_.getPhase();
		if (_proto_.hasSubject())
			subject = _proto_.getSubject();
		if (_proto_.hasMaterial())
			material = _proto_.getMaterial();
		if (_proto_.hasDiff())
			diff = _proto_.getDiff();
		if (_proto_.hasArea())
			area = _proto_.getArea();
		if (_proto_.hasYear())
			year = _proto_.getYear();
		if (_proto_.hasCurrChapterPath())
			currChapterPath = _proto_.getCurrChapterPath();
		if (_proto_.hasCurrPage())
			currPage = _proto_.getCurrPage();
		if (_proto_.hasTotalPage())
			totalPage = _proto_.getTotalPage();
		if (_proto_.hasDeviceId())
			deviceId = _proto_.getDeviceId();
	}

	public static SZXTask load(byte[] bytes)
	{
		try
		{
			SZXTask obj = new SZXTask();
			obj.parse(ZXTask.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SZXTask load(ZXTask proto)
	{
		try
		{
			SZXTask obj = new SZXTask();
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
	public ZXTask saveToProto()
	{
		ZXTask.Builder _builder_ = ZXTask.newBuilder();
		if (id != null && !id.equals(ZXTask.getDefaultInstance().getId()))
			_builder_.setId(id);
		if (phase != null && !phase.equals(ZXTask.getDefaultInstance().getPhase()))
			_builder_.setPhase(phase);
		if (subject != null && !subject.equals(ZXTask.getDefaultInstance().getSubject()))
			_builder_.setSubject(subject);
		if (material != null && !material.equals(ZXTask.getDefaultInstance().getMaterial()))
			_builder_.setMaterial(material);
		if (diff != null && !diff.equals(ZXTask.getDefaultInstance().getDiff()))
			_builder_.setDiff(diff);
		if (area != null && !area.equals(ZXTask.getDefaultInstance().getArea()))
			_builder_.setArea(area);
		if (year != null && !year.equals(ZXTask.getDefaultInstance().getYear()))
			_builder_.setYear(year);
		if (currChapterPath != null && !currChapterPath.equals(ZXTask.getDefaultInstance().getCurrChapterPath()))
			_builder_.setCurrChapterPath(currChapterPath);
		if (currPage != null && !currPage.equals(ZXTask.getDefaultInstance().getCurrPage()))
			_builder_.setCurrPage(currPage);
		if (totalPage != null && !totalPage.equals(ZXTask.getDefaultInstance().getTotalPage()))
			_builder_.setTotalPage(totalPage);
		if (deviceId != null && !deviceId.equals(ZXTask.getDefaultInstance().getDeviceId()))
			_builder_.setDeviceId(deviceId);
		return _builder_.build();
	}
}
