package com.lys.protobuf;

public class SCheckState
{
	public static final int None = 1;
	public static final int Refresh = 2;
	public static final int Diff = 3;
	public static final int Equal = 4;

	public static String name(int value)
	{
		return ProtocolPair.CheckState.valueOf(value).name().substring("CheckState_".length());
	}
}
