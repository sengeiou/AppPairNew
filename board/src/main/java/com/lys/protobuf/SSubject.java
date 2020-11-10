package com.lys.protobuf;

// 科目
public class SSubject
{
	public static final int All = -1; // 全部
	public static final int Any = 0; // 未分科
	public static final int Yu = 1; // 语文
	public static final int Shu = 2; // 数学
	public static final int Wai = 3; // 英语
	public static final int Li = 4; // 物理
	public static final int Hua = 5; // 化学
	public static final int Di = 6; // 地理
	public static final int Shi = 7; // 历史
	public static final int Zhen = 8; // 政治
	public static final int Shen = 9; // 生物
	public static final int WenHua = 10; // 文化

	public static String name(int value)
	{
		return ProtocolCommon.Subject.valueOf(value).name().substring("Subject_".length());
	}
}
