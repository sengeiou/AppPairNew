package com.lys.protobuf;

public class SUserType
{
	public static final int SupterMaster = 1;
	public static final int Master = 2;
	public static final int Teacher = 3;
	public static final int Student = 4;

	public static String name(int value)
	{
		return ProtocolPair.UserType.valueOf(value).name().substring("UserType_".length());
	}
}
