package com.lys.protobuf;

// 题目类型
public class SProblemType
{
	public static final int SingleSelect = 1; // 单选
	public static final int Fill = 2; // 填空题
	public static final int Answer = 3; // 主观题
	public static final int MultiSelect = 4; // 多选
	public static final int Judge = 5; // 判断题

	public static String name(int value)
	{
		return ProtocolCommon.ProblemType.valueOf(value).name().substring("ProblemType_".length());
	}
}
