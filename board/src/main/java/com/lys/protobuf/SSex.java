package com.lys.protobuf;

public class SSex
{
	public static final int Unknow = 0;
	public static final int Girl = 1;
	public static final int Boy = 2;

	public static String name(int value)
	{
		return ProtocolPair.Sex.valueOf(value).name().substring("Sex_".length());
	}
}
