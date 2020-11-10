package com.lys.base.utils;

import android.os.Environment;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FsUtils
{
	public static final String SD_CARD = Environment.getExternalStorageDirectory().getAbsolutePath();

	public static long getSize(File file)
	{
		if (file.exists())
		{
			if (file.isDirectory())
			{
				final long[] size = {0};
				file.listFiles(new FileFilter()
				{
					@Override
					public boolean accept(File file)
					{
						if (file.isDirectory())
							size[0] += getSize(file);
						else
							size[0] += file.length();
						return false;
					}
				});
				return size[0];
			}
			else
			{
				return file.length();
			}
		}
		else
		{
			return 0;
		}
	}

	public static void createFolder(File file)
	{
		try
		{
			if (!file.exists())
			{
				file.mkdirs();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void delete(File file)
	{
		try
		{
			if (file.exists())
			{
				if (file.isFile())
				{
					file.delete();
				}
				else if (file.isDirectory())
				{
					File[] files = file.listFiles();
					for (int i = 0; i < files.length; i++)
					{
						delete(files[i]);
					}
					file.delete();
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static byte[] readBytes(File file)
	{
		if (file.exists())
		{
			try
			{
				FileInputStream fis = new FileInputStream(file);
				byte[] buffer = new byte[fis.available()];
				fis.read(buffer);
				fis.close();
				return buffer;
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return null;
	}

	public static void writeBytes(File file, byte[] bytes, int off, int len)
	{
		try
		{
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(bytes, off, len);
			fos.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void writeBytes(File file, byte[] bytes)
	{
		try
		{
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(bytes);
			fos.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void appendBytes(File file, byte[] bytes)
	{
		try
		{
			RandomAccessFile raf = new RandomAccessFile(file, "rw");
			raf.seek(file.length());
			raf.write(bytes);
			raf.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static String readText(File file, Charset charset)
	{
		byte[] bytes = readBytes(file);
		if (bytes != null)
			return new String(bytes, charset);
		return null;
	}

	public static void writeText(File file, String text, Charset charset)
	{
		writeBytes(file, text.getBytes(charset));
	}

	public static void appendText(File file, String text, Charset charset)
	{
		appendBytes(file, text.getBytes(charset));
	}

	public static String readText(File file)
	{
		return readText(file, Charset.forName("utf-8"));
	}

	public static void writeText(File file, String text)
	{
		writeText(file, text, Charset.forName("utf-8"));
	}

	public static void appendText(File file, String text)
	{
		appendText(file, text, Charset.forName("utf-8"));
	}

	private static void searchFilesImpl(final List<String> list, File searchDir, final String endStrGroup)
	{
		searchDir.list(new FilenameFilter()
		{
			public boolean accept(File dir, String name)
			{
				File file = new File(dir.getAbsoluteFile().toString() + "/" + name);
				if (file.isDirectory())
				{
					searchFilesImpl(list, file, endStrGroup);
				}
				else if (file.isFile())
				{
					boolean toAdd = false;
					if (endStrGroup == null)
					{
						toAdd = true;
					}
					else
					{
						String[] endStrArray = endStrGroup.split(";");
						for (String endStr : endStrArray)
						{
							if (endStr.length() > 0)
							{
								if (endStr.startsWith("*"))
									endStr = endStr.substring(1);
								if (endStr.equals(".*"))
								{
									toAdd = true;
									break;
								}
								if (name.toLowerCase().endsWith(endStr.toLowerCase()))
								{
									toAdd = true;
									break;
								}
							}
						}
					}
					if (toAdd)
					{
						list.add(file.getAbsoluteFile().toString());
					}
				}
				return false;
			}
		});
	}

	public static List<String> searchFiles(File searchDir, String endStrGroup)
	{
		List<String> list = new ArrayList<>();
		searchFilesImpl(list, searchDir, endStrGroup);
		return list;
	}

	public static void deleteDirectoryIfEmpty(File file)
	{
		if (file.getAbsolutePath().equals(FsUtils.SD_CARD))
			return;
		if (file.exists())
		{
			if (file.isDirectory())
			{
				String[] children = file.list();
				if (children == null || children.length == 0)
				{
					file.delete();
					deleteDirectoryIfEmpty(file.getParentFile());
				}
			}
		}
	}

	private static void searchFilesImpl(final List<File> files, File dir)
	{
		dir.listFiles(new FileFilter()
		{
			@Override
			public boolean accept(File file)
			{
				if (file.isFile())
				{
					files.add(file);
				}
				else
				{
					searchFilesImpl(files, file);
				}
				return false;
			}
		});
	}

	public static List<File> searchFiles(File file)
	{
		List<File> files = new ArrayList<>();
		if (file.exists())
		{
			if (file.isFile())
			{
				files.add(file);
			}
			else
			{
				searchFilesImpl(files, file);
			}
		}
		return files;
	}

	public static void copy(File srcFile, File dstFile)
	{
		if (srcFile.exists() && srcFile.isFile())
		{
			createFolder(dstFile.getParentFile());
			try
			{
				FileInputStream fis = new FileInputStream(srcFile);
				FileOutputStream fos = new FileOutputStream(dstFile);
				byte[] buffer = new byte[1024 * 16];
				int hasRead = 0;
				while ((hasRead = fis.read(buffer)) > 0)
				{
					fos.write(buffer, 0, hasRead);
				}
				fis.close();
				fos.close();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	// 拷贝文件或目录
	public static void copyPath(File srcFile, File dstFile, Map<String, File> filterMap)
	{
		if (srcFile.exists())
		{
			if (srcFile.isFile())
			{
				copy(srcFile, dstFile);
			}
			else
			{
				int len = srcFile.getAbsolutePath().length();
				for (File srcF : searchFiles(srcFile))
				{
					File dstF = new File(dstFile.getAbsolutePath() + srcF.getAbsolutePath().substring(len));
					if (filterMap == null || !filterMap.containsKey(srcF.getName()))
						copy(srcF, dstF);
				}
			}
		}
	}

}
