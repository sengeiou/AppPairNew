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
import com.lys.protobuf.ProtocolScore.Order;

public class SOrder extends SPTData<Order>
{
	private static final SOrder DefaultInstance = new SOrder();

	public String id = null;
	public String userId = null;
	public SGoods goods = null;
	public Integer count = 0;
	public Integer score = 0;
	public Long time = 0L;
	public /*SOrderState*/ Integer state = Order.getDefaultInstance().getState().getNumber();
	public String name = null;
	public String phone = null;
	public String address = null;

	public static SOrder create(String id, String userId, SGoods goods, Integer count, Integer score, Long time, Integer state, String name, String phone, String address)
	{
		SOrder obj = new SOrder();
		obj.id = id;
		obj.userId = userId;
		obj.goods = goods;
		obj.count = count;
		obj.score = score;
		obj.time = time;
		obj.state = state;
		obj.name = name;
		obj.phone = phone;
		obj.address = address;
		return obj;
	}

	public SOrder clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SOrder _other_)
	{
		this.id = _other_.id;
		this.userId = _other_.userId;
		this.goods = _other_.goods;
		this.count = _other_.count;
		this.score = _other_.score;
		this.time = _other_.time;
		this.state = _other_.state;
		this.name = _other_.name;
		this.phone = _other_.phone;
		this.address = _other_.address;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("id"))
			id = _json_.getString("id");
		if (_json_.containsKey("userId"))
			userId = _json_.getString("userId");
		if (_json_.containsKey("goods"))
			goods = SGoods.load(_json_.getJSONObject("goods"));
		if (_json_.containsKey("count"))
			count = _json_.getInteger("count");
		if (_json_.containsKey("score"))
			score = _json_.getInteger("score");
		if (_json_.containsKey("time"))
			time = _json_.getLong("time");
		if (_json_.containsKey("state"))
			state = _json_.getInteger("state");
		if (_json_.containsKey("name"))
			name = _json_.getString("name");
		if (_json_.containsKey("phone"))
			phone = _json_.getString("phone");
		if (_json_.containsKey("address"))
			address = _json_.getString("address");
	}

	public static SOrder load(String str)
	{
		try
		{
			SOrder obj = new SOrder();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SOrder load(JSONObject json)
	{
		try
		{
			SOrder obj = new SOrder();
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
			if (userId != null)
				_json_.put("userId", userId);
			if (goods != null)
				_json_.put("goods", goods.saveToJson());
			if (count != null)
				_json_.put("count", count);
			if (score != null)
				_json_.put("score", score);
			if (time != null)
				_json_.put("time", String.valueOf(time));
			if (state != null)
				_json_.put("state", state);
			if (name != null)
				_json_.put("name", name);
			if (phone != null)
				_json_.put("phone", phone);
			if (address != null)
				_json_.put("address", address);
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SOrder> loadList(JSONArray ja)
	{
		try
		{
			List<SOrder> list = new ArrayList<SOrder>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SOrder item = SOrder.load(jo);
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

	public static JSONArray saveList(List<SOrder> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SOrder item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Order _proto_)
	{
		if (_proto_.hasId())
			id = _proto_.getId();
		if (_proto_.hasUserId())
			userId = _proto_.getUserId();
		if (_proto_.hasGoods())
			goods = SGoods.load(_proto_.getGoods());
		if (_proto_.hasCount())
			count = _proto_.getCount();
		if (_proto_.hasScore())
			score = _proto_.getScore();
		if (_proto_.hasTime())
			time = _proto_.getTime();
		if (_proto_.hasState())
			state = _proto_.getState().getNumber();
		if (_proto_.hasName())
			name = _proto_.getName();
		if (_proto_.hasPhone())
			phone = _proto_.getPhone();
		if (_proto_.hasAddress())
			address = _proto_.getAddress();
	}

	public static SOrder load(byte[] bytes)
	{
		try
		{
			SOrder obj = new SOrder();
			obj.parse(Order.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SOrder load(Order proto)
	{
		try
		{
			SOrder obj = new SOrder();
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
	public Order saveToProto()
	{
		Order.Builder _builder_ = Order.newBuilder();
		if (id != null && !id.equals(Order.getDefaultInstance().getId()))
			_builder_.setId(id);
		if (userId != null && !userId.equals(Order.getDefaultInstance().getUserId()))
			_builder_.setUserId(userId);
		if (goods != null)
			_builder_.setGoods(goods.saveToProto());
		if (count != null && !count.equals(Order.getDefaultInstance().getCount()))
			_builder_.setCount(count);
		if (score != null && !score.equals(Order.getDefaultInstance().getScore()))
			_builder_.setScore(score);
		if (time != null && !time.equals(Order.getDefaultInstance().getTime()))
			_builder_.setTime(time);
		if (state != null && Order.getDefaultInstance().getState().getNumber() != state)
			_builder_.setState(ProtocolScore.OrderState.valueOf(state));
		if (name != null && !name.equals(Order.getDefaultInstance().getName()))
			_builder_.setName(name);
		if (phone != null && !phone.equals(Order.getDefaultInstance().getPhone()))
			_builder_.setPhone(phone);
		if (address != null && !address.equals(Order.getDefaultInstance().getAddress()))
			_builder_.setAddress(address);
		return _builder_.build();
	}
}
