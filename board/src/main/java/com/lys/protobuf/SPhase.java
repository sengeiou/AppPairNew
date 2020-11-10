package com.lys.protobuf;

// 阶段
public class SPhase
{
	public static final int Chu = 2; // 初中
	public static final int Gao = 3; // 高中

	public static String name(int value)
	{
		return ProtocolCommon.Phase.valueOf(value).name().substring("Phase_".length());
	}
}
