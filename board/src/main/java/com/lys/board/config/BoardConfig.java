package com.lys.board.config;

import com.lys.board.R;

public class BoardConfig
{
	public static final String big_video = "big_video";

	public static final int BoardBgTransparent = 0;
	public static final int BoardBgWhite = 1;
	public static final int BoardBgDefault = 2;
	public static final int BoardBgLine = 3;

	public static int getBoardBgRes(int boardBg)
	{
		switch (boardBg)
		{
		case BoardBgDefault:
			return R.drawable.board_bg_default;
		case BoardBgLine:
			return R.drawable.board_bg_line;
		}
		return 0;
	}

	public static final int BoardResultNormal = 0;
	public static final int BoardResultWrong = 1;
	public static final int BoardResultHalf = 2;
	public static final int BoardResultRight = 3;

	public static int getBoardResultRes(int boardResult)
	{
		switch (boardResult)
		{
		case BoardResultWrong:
			return R.drawable.img_big_wrong;
		case BoardResultHalf:
			return R.drawable.img_big_half;
		case BoardResultRight:
			return R.drawable.img_big_right;
		}
		return 0;
	}

}
