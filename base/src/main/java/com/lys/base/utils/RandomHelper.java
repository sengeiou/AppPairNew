package com.lys.base.utils;

public class RandomHelper
{
	// [0, length)
	public static int randomInt(int length)
	{
		return (int) (Math.random() * length);
	}

	// [from, to)
	public static int randomInt(int from, int to)
	{
		return randomInt(to - from) + from;
	}

	public static char RandNumber()
	{
		return (char) (randomInt('0', '9' + 1));
	}

	public static char RandLowerChar()
	{
		return (char) (randomInt('a', 'z' + 1));
	}

	public static char RandUpperChar()
	{
		return (char) (randomInt('A', 'Z' + 1));
	}

	public static String RandNumberStringWithout4(int length)
	{
		StringBuilder sb = new StringBuilder();
		while (sb.length() < length)
		{
			char ch = RandNumber();
			if (ch != '4')
			{
				sb.append(ch);
			}
		}
		return sb.toString();
	}

	public static String RandNumberString(int length)
	{
		StringBuilder sb = new StringBuilder();
		while (sb.length() < length)
		{
			char ch = RandNumber();
			sb.append(ch);
		}
		return sb.toString();
	}

	public static String RandString(int length)
	{
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++)
		{
			switch (randomInt(3))
			{
			case 0:
				sb.append(RandNumber());
				break;
			case 1:
				sb.append(RandLowerChar());
				break;
			case 2:
				sb.append(RandUpperChar());
				break;
			default:
				break;
			}
		}
		return sb.toString();
	}

	public static String RandString(int from, int to)
	{
		return RandString(randomInt(from, to));
	}
}
