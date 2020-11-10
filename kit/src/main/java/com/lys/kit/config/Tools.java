package com.lys.kit.config;

public class Tools
{
	public static String strScore(float score)
	{
		String str = String.format("%.1f", score);
		if (str.endsWith(".0"))
			return str.substring(0, str.length() - 2);
		else
			return str;
	}
}
