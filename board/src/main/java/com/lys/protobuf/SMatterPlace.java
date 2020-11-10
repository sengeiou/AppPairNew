package com.lys.protobuf;

public class SMatterPlace
{
	public static final int Default = 1;
	public static final int Main = 2;
	public static final int Banner = 3;

	public static String name(int value)
	{
		return ProtocolShop.MatterPlace.valueOf(value).name().substring("MatterPlace_".length());
	}
}
