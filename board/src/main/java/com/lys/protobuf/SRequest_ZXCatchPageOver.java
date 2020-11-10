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
import com.lys.protobuf.ProtocolZhixue.Request_ZXCatchPageOver;

// ---------------------- XXXXXX --------------------------
public class SRequest_ZXCatchPageOver extends SPTData<Request_ZXCatchPageOver>
{
	private static final SRequest_ZXCatchPageOver DefaultInstance = new SRequest_ZXCatchPageOver();

	public String phase = null;
	public String subject = null;
	public String material = null;
	public String diff = null;
	public String area = null;
	public String year = null;
	public String currChapterPath = null;
	public Integer currPage = 0;
	public String deviceId = null;

	public static SRequest_ZXCatchPageOver create(String phase, String subject, String material, String diff, String area, String year, String currChapterPath, Integer currPage, String deviceId)
	{
		SRequest_ZXCatchPageOver obj = new SRequest_ZXCatchPageOver();
		obj.phase = phase;
		obj.subject = subject;
		obj.material = material;
		obj.diff = diff;
		obj.area = area;
		obj.year = year;
		obj.currChapterPath = currChapterPath;
		obj.currPage = currPage;
		obj.deviceId = deviceId;
		return obj;
	}

	public SRequest_ZXCatchPageOver clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SRequest_ZXCatchPageOver _other_)
	{
		this.phase = _other_.phase;
		this.subject = _other_.subject;
		this.material = _other_.material;
		this.diff = _other_.diff;
		this.area = _other_.area;
		this.year = _other_.year;
		this.currChapterPath = _other_.currChapterPath;
		this.currPage = _other_.currPage;
		this.deviceId = _other_.deviceId;
	}

	@Override
	public void parse(JSONObject _json_)
	{
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
		if (_json_.containsKey("deviceId"))
			deviceId = _json_.getString("deviceId");
	}

	public static SRequest_ZXCatchPageOver load(String str)
	{
		try
		{
			SRequest_ZXCatchPageOver obj = new SRequest_ZXCatchPageOver();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_ZXCatchPageOver load(JSONObject json)
	{
		try
		{
			SRequest_ZXCatchPageOver obj = new SRequest_ZXCatchPageOver();
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

	public static List<SRequest_ZXCatchPageOver> loadList(JSONArray ja)
	{
		try
		{
			List<SRequest_ZXCatchPageOver> list = new ArrayList<SRequest_ZXCatchPageOver>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SRequest_ZXCatchPageOver item = SRequest_ZXCatchPageOver.load(jo);
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

	public static JSONArray saveList(List<SRequest_ZXCatchPageOver> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SRequest_ZXCatchPageOver item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Request_ZXCatchPageOver _proto_)
	{
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
		if (_proto_.hasDeviceId())
			deviceId = _proto_.getDeviceId();
	}

	public static SRequest_ZXCatchPageOver load(byte[] bytes)
	{
		try
		{
			SRequest_ZXCatchPageOver obj = new SRequest_ZXCatchPageOver();
			obj.parse(Request_ZXCatchPageOver.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_ZXCatchPageOver load(Request_ZXCatchPageOver proto)
	{
		try
		{
			SRequest_ZXCatchPageOver obj = new SRequest_ZXCatchPageOver();
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
	public Request_ZXCatchPageOver saveToProto()
	{
		Request_ZXCatchPageOver.Builder _builder_ = Request_ZXCatchPageOver.newBuilder();
		if (phase != null && !phase.equals(Request_ZXCatchPageOver.getDefaultInstance().getPhase()))
			_builder_.setPhase(phase);
		if (subject != null && !subject.equals(Request_ZXCatchPageOver.getDefaultInstance().getSubject()))
			_builder_.setSubject(subject);
		if (material != null && !material.equals(Request_ZXCatchPageOver.getDefaultInstance().getMaterial()))
			_builder_.setMaterial(material);
		if (diff != null && !diff.equals(Request_ZXCatchPageOver.getDefaultInstance().getDiff()))
			_builder_.setDiff(diff);
		if (area != null && !area.equals(Request_ZXCatchPageOver.getDefaultInstance().getArea()))
			_builder_.setArea(area);
		if (year != null && !year.equals(Request_ZXCatchPageOver.getDefaultInstance().getYear()))
			_builder_.setYear(year);
		if (currChapterPath != null && !currChapterPath.equals(Request_ZXCatchPageOver.getDefaultInstance().getCurrChapterPath()))
			_builder_.setCurrChapterPath(currChapterPath);
		if (currPage != null && !currPage.equals(Request_ZXCatchPageOver.getDefaultInstance().getCurrPage()))
			_builder_.setCurrPage(currPage);
		if (deviceId != null && !deviceId.equals(Request_ZXCatchPageOver.getDefaultInstance().getDeviceId()))
			_builder_.setDeviceId(deviceId);
		return _builder_.build();
	}
}
