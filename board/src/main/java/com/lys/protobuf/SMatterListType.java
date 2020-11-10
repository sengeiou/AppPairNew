package com.lys.protobuf;

public class SMatterListType
{
	public static final int Home = 1; // 
	public static final int Class = 2; // 
	public static final int Pair = 3; // 

	public static String name(int value)
	{
		return ProtocolShop.MatterListType.valueOf(value).name().substring("MatterListType_".length());
	}
}
