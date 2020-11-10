package com.lys.protobuf;

public class STeachState
{
	public static final int None = 1;
	public static final int Call = 2;
	public static final int Refuse = 3;
	public static final int Agree = 4;
	public static final int Timeout = 5;
	public static final int Ready = 6;
	public static final int Quit = 7;

	public static String name(int value)
	{
		return ProtocolPair.TeachState.valueOf(value).name().substring("TeachState_".length());
	}
}
