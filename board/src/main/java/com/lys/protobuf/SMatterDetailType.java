package com.lys.protobuf;

public class SMatterDetailType
{
	public static final int Img = 1; // 
	public static final int Video = 2; // 
	public static final int Task = 3; // 

	public static String name(int value)
	{
		return ProtocolShop.MatterDetailType.valueOf(value).name().substring("MatterDetailType_".length());
	}
}
