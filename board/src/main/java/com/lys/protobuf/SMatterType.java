package com.lys.protobuf;

public class SMatterType
{
	public static final int Class = 1; // 精品课
	public static final int Pair = 2; // 一对一

	public static String name(int value)
	{
		return ProtocolShop.MatterType.valueOf(value).name().substring("MatterType_".length());
	}
}
