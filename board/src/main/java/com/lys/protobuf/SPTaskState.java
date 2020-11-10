package com.lys.protobuf;

public class SPTaskState
{
	public static final int None = 0;
	public static final int JobOver = 1; // 学生完成了作业
	public static final int ReadOver = 2; // 老师完成了批阅

	public static String name(int value)
	{
		return ProtocolPair.PTaskState.valueOf(value).name().substring("PTaskState_".length());
	}
}
