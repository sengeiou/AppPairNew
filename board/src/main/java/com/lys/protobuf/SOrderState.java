package com.lys.protobuf;

public class SOrderState
{
	public static final int Init = 1; // 初始状态
	public static final int Send = 2; // 已发货
	public static final int Receive = 3; // 已收到

	public static String name(int value)
	{
		return ProtocolScore.OrderState.valueOf(value).name().substring("OrderState_".length());
	}
}
