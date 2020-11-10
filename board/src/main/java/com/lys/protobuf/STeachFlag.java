package com.lys.protobuf;

public class STeachFlag
{
	public static final int None = 0;
	public static final int Free = 1;
	public static final int Use = 2;
	public static final int Over = 3;

	public static String name(int value)
	{
		return ProtocolScore.TeachFlag.valueOf(value).name().substring("TeachFlag_".length());
	}
}
