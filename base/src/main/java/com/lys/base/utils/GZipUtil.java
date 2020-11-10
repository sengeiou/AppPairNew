package com.lys.base.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class GZipUtil
{
	private static final int BUFFER = 4096;

	public static void compress(InputStream is, OutputStream os)
	{
		try
		{
			GZIPOutputStream gos = new GZIPOutputStream(os);
			byte[] buffer = new byte[BUFFER];
			int hasRead = 0;
			while ((hasRead = is.read(buffer, 0, BUFFER)) != -1)
			{
				gos.write(buffer, 0, hasRead);
			}
			gos.finish();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static byte[] compress(byte[] bytes)
	{
		try
		{
			ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			compress(bais, baos);
			byte[] buf = baos.toByteArray();
			bais.close();
			baos.close();
			return buf;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static void compress(File inFile, File outFile)
	{
		try
		{
			FileInputStream fis = new FileInputStream(inFile);
			FileOutputStream fos = new FileOutputStream(outFile);
			compress(fis, fos);
			fis.close();
			fos.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	//////////////////////////////////

	public static void decompress(InputStream is, OutputStream os)
	{
		try
		{
			GZIPInputStream gis = new GZIPInputStream(is);
			byte[] buffer = new byte[BUFFER];
			int hasRead = 0;
			while ((hasRead = gis.read(buffer, 0, BUFFER)) != -1)
			{
				os.write(buffer, 0, hasRead);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static byte[] decompress(byte[] bytes)
	{
		try
		{
			ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			decompress(bais, baos);
			byte[] buf = baos.toByteArray();
			bais.close();
			baos.close();
			return buf;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static void decompress(File inFile, File outFile)
	{
		try
		{
			FileInputStream fis = new FileInputStream(inFile);
			FileOutputStream fos = new FileOutputStream(outFile);
			decompress(fis, fos);
			fis.close();
			fos.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

}
