package com.krm.ps.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class FileUtil
{
	public static final int BUFFER_SIZE = 4096;

	private static final Log logger = LogFactory.getLog(FileUtil.class);

	public static int copy(File in, File out) throws IOException
	{
		return copy(new BufferedInputStream(new FileInputStream(in)),
			new BufferedOutputStream(new FileOutputStream(out)));
	}

	public static int copy(InputStream in, OutputStream out) throws IOException
	{
		try
		{
			int byteCount = 0;
			byte[] buffer = new byte[BUFFER_SIZE];
			int bytesRead = -1;
			while ((bytesRead = in.read(buffer)) != -1)
			{
				out.write(buffer, 0, bytesRead);
				byteCount += bytesRead;
			}
			out.flush();
			return byteCount;
		}
		finally
		{
			try
			{
				in.close();
			}
			catch (IOException ex)
			{
				logger.warn("Could not close InputStream", ex);
			}
			try
			{
				out.close();
			}
			catch (IOException ex)
			{
				logger.warn("Could not close OutputStream", ex);
			}
		}
	}

	public static int copy(String srcFile, String destDir) throws IOException
	{
		File in = new File(srcFile);
		File out = new File(makeNormalDirName(destDir) + in.getName());
		return copy(in, out);
	}

	/**
	 * 获得不带扩展名的文件名
	 * 
	 * @param fileName 文件名
	 * @return 不带扩展名的文件名
	 */
	public static String getFileNameWithoutExt(String fileName)
	{
		int idx = fileName.lastIndexOf(".");
		if (idx >= 0)
		{
			return fileName.substring(0, idx);
		}
		return fileName;
	}

	public static String getFileShortNameWithoutExt(String fileName)
	{
		return getFileNameWithoutExt(getShortFileName(fileName));
	}

	/**
	 * 获得短文件名（不包括目录）
	 * 
	 * @param fileName 文件路径
	 * @return
	 */
	public static String getShortFileName(String fileName)
	{
		fileName = fileName.replace('\\', '/');
		int idx = fileName.lastIndexOf('/') + 1;
		if (idx > 0 && idx < fileName.length())
		{
			return fileName.substring(idx);
		}
		return fileName;
	}

	public static boolean isExistsFile(String fileName)
	{
		return new File(fileName).exists();
	}

	public static void makeFile(String content, String fileName,String encode)
		throws IOException
	{
		FileOutputStream fos = new FileOutputStream(fileName);
		try
		{
			fos.write(content.getBytes(encode));
		}
		finally
		{
			try
			{
				fos.close();
			}
			catch (IOException e)
			{
				logger.warn("Could not close OutputStream", e);
			}
		}
	}

	public static String makeFileName(String fileName)
	{
		if (fileName != null)
		{
			return fileName.replaceAll("<font[^/>]*>", "").replaceAll(
				"<\\\\font>", "").replaceAll("[\\\\/:\\*\\?\"<>|]", "");
		}
		return "";
	}

	public static String makeNormalDirName(String dir)
	{
		dir = dir.replace('\\', '/');
		if (dir.charAt(dir.length() - 1) != '/')
		{
			dir += "/";
		}
		return dir;
	}
	
	/**
	   * 转换路径的字符串格式,使"\"转变成"/"。
	   *
	   * @param path 路径字符串
	   * @return returns 处理后的路径字符串
	   **/
	  public static String pathformat(String path) {
	    int i = 0;
	    int pathlength = path.length();
	    String returns = "";
	    while (i < pathlength) {
	      if (path.substring(i, (i + 1)).equals("\\")) {
		returns = returns + "/";
	      }
	      else {
		returns = returns + path.substring(i, (i + 1));
	      } // end if else
	      i = i + 1;
	    } // end while
	    return returns;
	  } // end method pathformat
}
