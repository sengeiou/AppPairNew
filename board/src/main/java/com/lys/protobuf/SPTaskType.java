package com.lys.protobuf;

public class SPTaskType
{
	public static final int None = 0;
	public static final int Job = 1;
	public static final int Class = 2;

	public static String name(int value)
	{
		return ProtocolPair.PTaskType.valueOf(value).name().substring("PTaskType_".length());
	}
}
