package com.lys.protobuf;

public class SOperationType
{
	public static final int Draw = 1;
	public static final int Clear = 2;
	public static final int Height = 3;
	public static final int Scroll = 4;
	public static final int TurnPage = 5;
	public static final int AddPage = 6;
	public static final int DeletePages = 7;
	public static final int AddImage = 8;
	public static final int AddVideo = 9;
	public static final int AddTopic = 11;
	public static final int AddSelectionGroup = 12;
	public static final int AddBordText = 17;
	public static final int DeletePhoto = 13;
	public static final int TopPhoto = 16;
	public static final int ModifyPhoto = 14;
	public static final int ModifySelections = 15;
	public static final int ModifyBoardText = 18;

	public static String name(int value)
	{
		return ProtocolBoard.OperationType.valueOf(value).name().substring("OperationType_".length());
	}
}
