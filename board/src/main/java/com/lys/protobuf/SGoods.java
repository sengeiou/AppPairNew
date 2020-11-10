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
import com.lys.protobuf.ProtocolScore.Goods;

public class SGoods extends SPTData<Goods>
{
	private static final SGoods DefaultInstance = new SGoods();

	public String id = null;
	public String name = null;
	public String cover = null;
	public Integer score = 0;
	public Integer buyCount = 0;
	public Integer yuCount = 0;
	public Long sort = 0L;
	public Boolean invalid = false;

	public static SGoods create(String id, String name, String cover, Integer score, Integer buyCount, Integer yuCount, Long sort, Boolean invalid)
	{
		SGoods obj = new SGoods();
		obj.id = id;
		obj.name = name;
		obj.cover = cover;
		obj.score = score;
		obj.buyCount = buyCount;
		obj.yuCount = yuCount;
		obj.sort = sort;
		obj.invalid = invalid;
		return obj;
	}

	public SGoods clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SGoods _other_)
	{
		this.id = _other_.id;
		this.name = _other_.name;
		this.cover = _other_.cover;
		this.score = _other_.score;
		this.buyCount = _other_.buyCount;
		this.yuCount = _other_.yuCount;
		this.sort = _other_.sort;
		this.invalid = _other_.invalid;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("id"))
			id = _json_.getString("id");
		if (_json_.containsKey("name"))
			name = _json_.getString("name");
		if (_json_.containsKey("cover"))
			cover = _json_.getString("cover");
		if (_json_.containsKey("score"))
			score = _json_.getInteger("score");
		if (_json_.containsKey("buyCount"))
			buyCount = _json_.getInteger("buyCount");
		if (_json_.containsKey("yuCount"))
			yuCount = _json_.getInteger("yuCount");
		if (_json_.containsKey("sort"))
			sort = _json_.getLong("sort");
		if (_json_.containsKey("invalid"))
			invalid = _json_.getBoolean("invalid");
	}

	public static SGoods load(String str)
	{
		try
		{
			SGoods obj = new SGoods();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SGoods load(JSONObject json)
	{
		try
		{
			SGoods obj = new SGoods();
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
			if (name != null)
				_json_.put("name", name);
			if (cover != null)
				_json_.put("cover", cover);
			if (score != null)
				_json_.put("score", score);
			if (buyCount != null)
				_json_.put("buyCount", buyCount);
			if (yuCount != null)
				_json_.put("yuCount", yuCount);
			if (sort != null)
				_json_.put("sort", String.valueOf(sort));
			if (invalid != null)
				_json_.put("invalid", invalid);
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SGoods> loadList(JSONArray ja)
	{
		try
		{
			List<SGoods> list = new ArrayList<SGoods>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SGoods item = SGoods.load(jo);
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

	public static JSONArray saveList(List<SGoods> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SGoods item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Goods _proto_)
	{
		if (_proto_.hasId())
			id = _proto_.getId();
		if (_proto_.hasName())
			name = _proto_.getName();
		if (_proto_.hasCover())
			cover = _proto_.getCover();
		if (_proto_.hasScore())
			score = _proto_.getScore();
		if (_proto_.hasBuyCount())
			buyCount = _proto_.getBuyCount();
		if (_proto_.hasYuCount())
			yuCount = _proto_.getYuCount();
		if (_proto_.hasSort())
			sort = _proto_.getSort();
		if (_proto_.hasInvalid())
			invalid = _proto_.getInvalid();
	}

	public static SGoods load(byte[] bytes)
	{
		try
		{
			SGoods obj = new SGoods();
			obj.parse(Goods.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SGoods load(Goods proto)
	{
		try
		{
			SGoods obj = new SGoods();
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
	public Goods saveToProto()
	{
		Goods.Builder _builder_ = Goods.newBuilder();
		if (id != null && !id.equals(Goods.getDefaultInstance().getId()))
			_builder_.setId(id);
		if (name != null && !name.equals(Goods.getDefaultInstance().getName()))
			_builder_.setName(name);
		if (cover != null && !cover.equals(Goods.getDefaultInstance().getCover()))
			_builder_.setCover(cover);
		if (score != null && !score.equals(Goods.getDefaultInstance().getScore()))
			_builder_.setScore(score);
		if (buyCount != null && !buyCount.equals(Goods.getDefaultInstance().getBuyCount()))
			_builder_.setBuyCount(buyCount);
		if (yuCount != null && !yuCount.equals(Goods.getDefaultInstance().getYuCount()))
			_builder_.setYuCount(yuCount);
		if (sort != null && !sort.equals(Goods.getDefaultInstance().getSort()))
			_builder_.setSort(sort);
		if (invalid != null && !invalid.equals(Goods.getDefaultInstance().getInvalid()))
			_builder_.setInvalid(invalid);
		return _builder_.build();
	}
}
