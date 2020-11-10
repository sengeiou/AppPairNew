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
import com.lys.protobuf.ProtocolPair.User;

public class SUser extends SPTData<User>
{
	private static final SUser DefaultInstance = new SUser();

	public String id = null;
	public /*SUserType*/ Integer userType = User.getDefaultInstance().getUserType().getNumber();
	public Integer useRong = 0; // 是否使用融云
	public String psw = null;
	public String name = null;
	public String head = null;
	public String token = null;
	public Long createTime = 0L;
	public /*SSex*/ Integer sex = User.getDefaultInstance().getSex().getNumber();
	public Integer grade = 0;
	public Integer vipLevel = 0;
	public Long vipTime = 0L;
	public String phone = null;
	public Integer score = 0;
	public String cpId = null;
	public String ts = null;
	public String ownerId = null;
	public /*STeachState*/ Integer teachState = User.getDefaultInstance().getTeachState().getNumber();
	public Boolean isMuteAudio = false;
	public Boolean isLockWrite = false;
	public /*SCheckState*/ Integer checkState = User.getDefaultInstance().getCheckState().getNumber();
	public String group = null; // 好友组

	public static SUser create(String id, Integer userType, Integer useRong, String psw, String name, String head, String token, Long createTime, Integer sex, Integer grade, Integer vipLevel, Long vipTime, String phone, Integer score, String cpId, String ts, String ownerId, Integer teachState, Boolean isMuteAudio, Boolean isLockWrite, Integer checkState, String group)
	{
		SUser obj = new SUser();
		obj.id = id;
		obj.userType = userType;
		obj.useRong = useRong;
		obj.psw = psw;
		obj.name = name;
		obj.head = head;
		obj.token = token;
		obj.createTime = createTime;
		obj.sex = sex;
		obj.grade = grade;
		obj.vipLevel = vipLevel;
		obj.vipTime = vipTime;
		obj.phone = phone;
		obj.score = score;
		obj.cpId = cpId;
		obj.ts = ts;
		obj.ownerId = ownerId;
		obj.teachState = teachState;
		obj.isMuteAudio = isMuteAudio;
		obj.isLockWrite = isLockWrite;
		obj.checkState = checkState;
		obj.group = group;
		return obj;
	}

	public SUser clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SUser _other_)
	{
		this.id = _other_.id;
		this.userType = _other_.userType;
		this.useRong = _other_.useRong;
		this.psw = _other_.psw;
		this.name = _other_.name;
		this.head = _other_.head;
		this.token = _other_.token;
		this.createTime = _other_.createTime;
		this.sex = _other_.sex;
		this.grade = _other_.grade;
		this.vipLevel = _other_.vipLevel;
		this.vipTime = _other_.vipTime;
		this.phone = _other_.phone;
		this.score = _other_.score;
		this.cpId = _other_.cpId;
		this.ts = _other_.ts;
		this.ownerId = _other_.ownerId;
		this.teachState = _other_.teachState;
		this.isMuteAudio = _other_.isMuteAudio;
		this.isLockWrite = _other_.isLockWrite;
		this.checkState = _other_.checkState;
		this.group = _other_.group;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("id"))
			id = _json_.getString("id");
		if (_json_.containsKey("userType"))
			userType = _json_.getInteger("userType");
		if (_json_.containsKey("useRong"))
			useRong = _json_.getInteger("useRong");
		if (_json_.containsKey("psw"))
			psw = _json_.getString("psw");
		if (_json_.containsKey("name"))
			name = _json_.getString("name");
		if (_json_.containsKey("head"))
			head = _json_.getString("head");
		if (_json_.containsKey("token"))
			token = _json_.getString("token");
		if (_json_.containsKey("createTime"))
			createTime = _json_.getLong("createTime");
		if (_json_.containsKey("sex"))
			sex = _json_.getInteger("sex");
		if (_json_.containsKey("grade"))
			grade = _json_.getInteger("grade");
		if (_json_.containsKey("vipLevel"))
			vipLevel = _json_.getInteger("vipLevel");
		if (_json_.containsKey("vipTime"))
			vipTime = _json_.getLong("vipTime");
		if (_json_.containsKey("phone"))
			phone = _json_.getString("phone");
		if (_json_.containsKey("score"))
			score = _json_.getInteger("score");
		if (_json_.containsKey("cpId"))
			cpId = _json_.getString("cpId");
		if (_json_.containsKey("ts"))
			ts = _json_.getString("ts");
		if (_json_.containsKey("ownerId"))
			ownerId = _json_.getString("ownerId");
		if (_json_.containsKey("teachState"))
			teachState = _json_.getInteger("teachState");
		if (_json_.containsKey("isMuteAudio"))
			isMuteAudio = _json_.getBoolean("isMuteAudio");
		if (_json_.containsKey("isLockWrite"))
			isLockWrite = _json_.getBoolean("isLockWrite");
		if (_json_.containsKey("checkState"))
			checkState = _json_.getInteger("checkState");
		if (_json_.containsKey("group"))
			group = _json_.getString("group");
	}

	public static SUser load(String str)
	{
		try
		{
			SUser obj = new SUser();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SUser load(JSONObject json)
	{
		try
		{
			SUser obj = new SUser();
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
			if (userType != null)
				_json_.put("userType", userType);
			if (useRong != null)
				_json_.put("useRong", useRong);
			if (psw != null)
				_json_.put("psw", psw);
			if (name != null)
				_json_.put("name", name);
			if (head != null)
				_json_.put("head", head);
			if (token != null)
				_json_.put("token", token);
			if (createTime != null)
				_json_.put("createTime", String.valueOf(createTime));
			if (sex != null)
				_json_.put("sex", sex);
			if (grade != null)
				_json_.put("grade", grade);
			if (vipLevel != null)
				_json_.put("vipLevel", vipLevel);
			if (vipTime != null)
				_json_.put("vipTime", String.valueOf(vipTime));
			if (phone != null)
				_json_.put("phone", phone);
			if (score != null)
				_json_.put("score", score);
			if (cpId != null)
				_json_.put("cpId", cpId);
			if (ts != null)
				_json_.put("ts", ts);
			if (ownerId != null)
				_json_.put("ownerId", ownerId);
			if (teachState != null)
				_json_.put("teachState", teachState);
			if (isMuteAudio != null)
				_json_.put("isMuteAudio", isMuteAudio);
			if (isLockWrite != null)
				_json_.put("isLockWrite", isLockWrite);
			if (checkState != null)
				_json_.put("checkState", checkState);
			if (group != null)
				_json_.put("group", group);
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SUser> loadList(JSONArray ja)
	{
		try
		{
			List<SUser> list = new ArrayList<SUser>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SUser item = SUser.load(jo);
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

	public static JSONArray saveList(List<SUser> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SUser item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(User _proto_)
	{
		if (_proto_.hasId())
			id = _proto_.getId();
		if (_proto_.hasUserType())
			userType = _proto_.getUserType().getNumber();
		if (_proto_.hasUseRong())
			useRong = _proto_.getUseRong();
		if (_proto_.hasPsw())
			psw = _proto_.getPsw();
		if (_proto_.hasName())
			name = _proto_.getName();
		if (_proto_.hasHead())
			head = _proto_.getHead();
		if (_proto_.hasToken())
			token = _proto_.getToken();
		if (_proto_.hasCreateTime())
			createTime = _proto_.getCreateTime();
		if (_proto_.hasSex())
			sex = _proto_.getSex().getNumber();
		if (_proto_.hasGrade())
			grade = _proto_.getGrade();
		if (_proto_.hasVipLevel())
			vipLevel = _proto_.getVipLevel();
		if (_proto_.hasVipTime())
			vipTime = _proto_.getVipTime();
		if (_proto_.hasPhone())
			phone = _proto_.getPhone();
		if (_proto_.hasScore())
			score = _proto_.getScore();
		if (_proto_.hasCpId())
			cpId = _proto_.getCpId();
		if (_proto_.hasTs())
			ts = _proto_.getTs();
		if (_proto_.hasOwnerId())
			ownerId = _proto_.getOwnerId();
		if (_proto_.hasTeachState())
			teachState = _proto_.getTeachState().getNumber();
		if (_proto_.hasIsMuteAudio())
			isMuteAudio = _proto_.getIsMuteAudio();
		if (_proto_.hasIsLockWrite())
			isLockWrite = _proto_.getIsLockWrite();
		if (_proto_.hasCheckState())
			checkState = _proto_.getCheckState().getNumber();
		if (_proto_.hasGroup())
			group = _proto_.getGroup();
	}

	public static SUser load(byte[] bytes)
	{
		try
		{
			SUser obj = new SUser();
			obj.parse(User.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SUser load(User proto)
	{
		try
		{
			SUser obj = new SUser();
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
	public User saveToProto()
	{
		User.Builder _builder_ = User.newBuilder();
		if (id != null && !id.equals(User.getDefaultInstance().getId()))
			_builder_.setId(id);
		if (userType != null && User.getDefaultInstance().getUserType().getNumber() != userType)
			_builder_.setUserType(ProtocolPair.UserType.valueOf(userType));
		if (useRong != null && !useRong.equals(User.getDefaultInstance().getUseRong()))
			_builder_.setUseRong(useRong);
		if (psw != null && !psw.equals(User.getDefaultInstance().getPsw()))
			_builder_.setPsw(psw);
		if (name != null && !name.equals(User.getDefaultInstance().getName()))
			_builder_.setName(name);
		if (head != null && !head.equals(User.getDefaultInstance().getHead()))
			_builder_.setHead(head);
		if (token != null && !token.equals(User.getDefaultInstance().getToken()))
			_builder_.setToken(token);
		if (createTime != null && !createTime.equals(User.getDefaultInstance().getCreateTime()))
			_builder_.setCreateTime(createTime);
		if (sex != null && User.getDefaultInstance().getSex().getNumber() != sex)
			_builder_.setSex(ProtocolPair.Sex.valueOf(sex));
		if (grade != null && !grade.equals(User.getDefaultInstance().getGrade()))
			_builder_.setGrade(grade);
		if (vipLevel != null && !vipLevel.equals(User.getDefaultInstance().getVipLevel()))
			_builder_.setVipLevel(vipLevel);
		if (vipTime != null && !vipTime.equals(User.getDefaultInstance().getVipTime()))
			_builder_.setVipTime(vipTime);
		if (phone != null && !phone.equals(User.getDefaultInstance().getPhone()))
			_builder_.setPhone(phone);
		if (score != null && !score.equals(User.getDefaultInstance().getScore()))
			_builder_.setScore(score);
		if (cpId != null && !cpId.equals(User.getDefaultInstance().getCpId()))
			_builder_.setCpId(cpId);
		if (ts != null && !ts.equals(User.getDefaultInstance().getTs()))
			_builder_.setTs(ts);
		if (ownerId != null && !ownerId.equals(User.getDefaultInstance().getOwnerId()))
			_builder_.setOwnerId(ownerId);
		if (teachState != null && User.getDefaultInstance().getTeachState().getNumber() != teachState)
			_builder_.setTeachState(ProtocolPair.TeachState.valueOf(teachState));
		if (isMuteAudio != null && !isMuteAudio.equals(User.getDefaultInstance().getIsMuteAudio()))
			_builder_.setIsMuteAudio(isMuteAudio);
		if (isLockWrite != null && !isLockWrite.equals(User.getDefaultInstance().getIsLockWrite()))
			_builder_.setIsLockWrite(isLockWrite);
		if (checkState != null && User.getDefaultInstance().getCheckState().getNumber() != checkState)
			_builder_.setCheckState(ProtocolPair.CheckState.valueOf(checkState));
		if (group != null && !group.equals(User.getDefaultInstance().getGroup()))
			_builder_.setGroup(group);
		return _builder_.build();
	}
}
