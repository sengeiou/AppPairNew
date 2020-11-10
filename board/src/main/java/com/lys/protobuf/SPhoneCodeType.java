package com.lys.protobuf;

public class SPhoneCodeType
{
	public static final int Reg = 1;
	public static final int Login = 2;

	public static String name(int value)
	{
		return ProtocolPair.PhoneCodeType.valueOf(value).name().substring("PhoneCodeType_".length());
	}
}
