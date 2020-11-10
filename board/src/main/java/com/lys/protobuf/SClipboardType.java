package com.lys.protobuf;

public class SClipboardType
{
	public static final int BoardPhoto = 1;
	public static final int BoardPages = 2;

	public static String name(int value)
	{
		return ProtocolCommon.ClipboardType.valueOf(value).name().substring("ClipboardType_".length());
	}
}
