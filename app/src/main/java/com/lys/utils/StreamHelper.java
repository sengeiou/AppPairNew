package com.lys.utils;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;

public class StreamHelper
{
	public static byte[] readBinaryBuffer(InputStream is)
	{
		try
		{
			byte[] buffer = new byte[is.available()];
			is.read(buffer);
			return buffer;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static String readTextBuffer(InputStream is)
	{
		byte[] buffer = readBinaryBuffer(is);
		if (buffer != null)
			return new String(buffer, Charset.forName("UTF-8"));
		else
			return null;
	}

	public static void writeBinaryBuffer(OutputStream os, byte[] buffer)
	{
		try
		{
			os.write(buffer);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void writeTextBuffer(OutputStream os, String content)
	{
		try
		{
			writeBinaryBuffer(os, content.getBytes("UTF-8"));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

}
