package com.lys.protobuf;

public class SErrorCode
{
	public static final int unknown_error = -1; // "未知错误！"
	public static final int AccountNotExist = -2;
	public static final int PswError = -3;

	public static String name(int value)
	{
		return ProtocolCommon.ErrorCode.valueOf(value).name().substring("ErrorCode_".length());
	}
}
