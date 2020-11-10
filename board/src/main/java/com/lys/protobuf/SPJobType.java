package com.lys.protobuf;

public class SPJobType
{
	public static final int None = 0;
	public static final int OnlySelect = 1;
	public static final int MultTopic = 2;

	public static String name(int value)
	{
		return ProtocolPair.PJobType.valueOf(value).name().substring("PJobType_".length());
	}
}
