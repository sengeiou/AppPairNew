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
import com.lys.protobuf.ProtocolShop.Matter;

public class SMatter extends SPTData<Matter>
{
	private static final SMatter DefaultInstance = new SMatter();

	public String id = null;
	public String name = null;
	public String userId = null; // 负责人
	public /*SMatterType*/ Integer type = Matter.getDefaultInstance().getType().getNumber();
	public /*SMatterPlace*/ Integer place = Matter.getDefaultInstance().getPlace().getNumber();
	public String cover = null;
	public String banner = null;
	public Integer buyCount = 0;
	public Integer moneyRaw = 0;
	public Integer money = 0;
	public List<SMatterHour> hours = new ArrayList<SMatterHour>();
	public Long sort = 0L;
	public Boolean invalid = false;
	public List<SMatterDetail> details = new ArrayList<SMatterDetail>();

	public static SMatter create(String id, String name, String userId, Integer type, Integer place, String cover, String banner, Integer buyCount, Integer moneyRaw, Integer money, Long sort, Boolean invalid)
	{
		SMatter obj = new SMatter();
		obj.id = id;
		obj.name = name;
		obj.userId = userId;
		obj.type = type;
		obj.place = place;
		obj.cover = cover;
		obj.banner = banner;
		obj.buyCount = buyCount;
		obj.moneyRaw = moneyRaw;
		obj.money = money;
		obj.sort = sort;
		obj.invalid = invalid;
		return obj;
	}

	public SMatter clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SMatter _other_)
	{
		this.id = _other_.id;
		this.name = _other_.name;
		this.userId = _other_.userId;
		this.type = _other_.type;
		this.place = _other_.place;
		this.cover = _other_.cover;
		this.banner = _other_.banner;
		this.buyCount = _other_.buyCount;
		this.moneyRaw = _other_.moneyRaw;
		this.money = _other_.money;
		this.hours = _other_.hours;
		this.sort = _other_.sort;
		this.invalid = _other_.invalid;
		this.details = _other_.details;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("id"))
			id = _json_.getString("id");
		if (_json_.containsKey("name"))
			name = _json_.getString("name");
		if (_json_.containsKey("userId"))
			userId = _json_.getString("userId");
		if (_json_.containsKey("type"))
			type = _json_.getInteger("type");
		if (_json_.containsKey("place"))
			place = _json_.getInteger("place");
		if (_json_.containsKey("cover"))
			cover = _json_.getString("cover");
		if (_json_.containsKey("banner"))
			banner = _json_.getString("banner");
		if (_json_.containsKey("buyCount"))
			buyCount = _json_.getInteger("buyCount");
		if (_json_.containsKey("moneyRaw"))
			moneyRaw = _json_.getInteger("moneyRaw");
		if (_json_.containsKey("money"))
			money = _json_.getInteger("money");
		if (_json_.containsKey("hours"))
			hours = SMatterHour.loadList(_json_.getJSONArray("hours"));
		if (_json_.containsKey("sort"))
			sort = _json_.getLong("sort");
		if (_json_.containsKey("invalid"))
			invalid = _json_.getBoolean("invalid");
		if (_json_.containsKey("details"))
			details = SMatterDetail.loadList(_json_.getJSONArray("details"));
	}

	public static SMatter load(String str)
	{
		try
		{
			SMatter obj = new SMatter();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SMatter load(JSONObject json)
	{
		try
		{
			SMatter obj = new SMatter();
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
			if (userId != null)
				_json_.put("userId", userId);
			if (type != null)
				_json_.put("type", type);
			if (place != null)
				_json_.put("place", place);
			if (cover != null)
				_json_.put("cover", cover);
			if (banner != null)
				_json_.put("banner", banner);
			if (buyCount != null)
				_json_.put("buyCount", buyCount);
			if (moneyRaw != null)
				_json_.put("moneyRaw", moneyRaw);
			if (money != null)
				_json_.put("money", money);
			if (hours != null)
				_json_.put("hours", SMatterHour.saveList(hours));
			if (sort != null)
				_json_.put("sort", String.valueOf(sort));
			if (invalid != null)
				_json_.put("invalid", invalid);
			if (details != null)
				_json_.put("details", SMatterDetail.saveList(details));
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SMatter> loadList(JSONArray ja)
	{
		try
		{
			List<SMatter> list = new ArrayList<SMatter>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SMatter item = SMatter.load(jo);
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

	public static JSONArray saveList(List<SMatter> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SMatter item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Matter _proto_)
	{
		if (_proto_.hasId())
			id = _proto_.getId();
		if (_proto_.hasName())
			name = _proto_.getName();
		if (_proto_.hasUserId())
			userId = _proto_.getUserId();
		if (_proto_.hasType())
			type = _proto_.getType().getNumber();
		if (_proto_.hasPlace())
			place = _proto_.getPlace().getNumber();
		if (_proto_.hasCover())
			cover = _proto_.getCover();
		if (_proto_.hasBanner())
			banner = _proto_.getBanner();
		if (_proto_.hasBuyCount())
			buyCount = _proto_.getBuyCount();
		if (_proto_.hasMoneyRaw())
			moneyRaw = _proto_.getMoneyRaw();
		if (_proto_.hasMoney())
			money = _proto_.getMoney();
		for (int i = 0; i < _proto_.getHoursCount(); i++)
			hours.add(SMatterHour.load(_proto_.getHours(i)));
		if (_proto_.hasSort())
			sort = _proto_.getSort();
		if (_proto_.hasInvalid())
			invalid = _proto_.getInvalid();
		for (int i = 0; i < _proto_.getDetailsCount(); i++)
			details.add(SMatterDetail.load(_proto_.getDetails(i)));
	}

	public static SMatter load(byte[] bytes)
	{
		try
		{
			SMatter obj = new SMatter();
			obj.parse(Matter.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SMatter load(Matter proto)
	{
		try
		{
			SMatter obj = new SMatter();
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
	public Matter saveToProto()
	{
		Matter.Builder _builder_ = Matter.newBuilder();
		if (id != null && !id.equals(Matter.getDefaultInstance().getId()))
			_builder_.setId(id);
		if (name != null && !name.equals(Matter.getDefaultInstance().getName()))
			_builder_.setName(name);
		if (userId != null && !userId.equals(Matter.getDefaultInstance().getUserId()))
			_builder_.setUserId(userId);
		if (type != null && Matter.getDefaultInstance().getType().getNumber() != type)
			_builder_.setType(ProtocolShop.MatterType.valueOf(type));
		if (place != null && Matter.getDefaultInstance().getPlace().getNumber() != place)
			_builder_.setPlace(ProtocolShop.MatterPlace.valueOf(place));
		if (cover != null && !cover.equals(Matter.getDefaultInstance().getCover()))
			_builder_.setCover(cover);
		if (banner != null && !banner.equals(Matter.getDefaultInstance().getBanner()))
			_builder_.setBanner(banner);
		if (buyCount != null && !buyCount.equals(Matter.getDefaultInstance().getBuyCount()))
			_builder_.setBuyCount(buyCount);
		if (moneyRaw != null && !moneyRaw.equals(Matter.getDefaultInstance().getMoneyRaw()))
			_builder_.setMoneyRaw(moneyRaw);
		if (money != null && !money.equals(Matter.getDefaultInstance().getMoney()))
			_builder_.setMoney(money);
		if (hours != null)
			for (SMatterHour _value_ : hours)
				_builder_.addHours(_value_.saveToProto());
		if (sort != null && !sort.equals(Matter.getDefaultInstance().getSort()))
			_builder_.setSort(sort);
		if (invalid != null && !invalid.equals(Matter.getDefaultInstance().getInvalid()))
			_builder_.setInvalid(invalid);
		if (details != null)
			for (SMatterDetail _value_ : details)
				_builder_.addDetails(_value_.saveToProto());
		return _builder_.build();
	}
}
