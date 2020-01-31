package com.krm.ps.util;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ZipUtil
{
	public static void zipDir(String zipFileName, String srcDir) throws IOException
	{
		File outputFile = new File(zipFileName);
		File directory = new File(srcDir);
		Zipper zipper = new Zipper(outputFile,directory);
		zipper.zip();
	}
	
	public static void zipDir(String srcDir) throws IOException
	{
		String zipFileName = srcDir + ".zip";
		zipDir(zipFileName, srcDir);
	}
	/*
	 * add by dong
	 * 功能：打包指定目录的所有文件，并将压缩文件存放于指定的目录下
	 */
	public static void zipping(String srcDir,String destDir, String zipName) throws IOException
	{
		String zipFileName = (destDir+File.separator+zipName) + ".zip";
		File outputFile = new File(zipFileName);
		File directory = new File(srcDir);
		Zipper zipper = new Zipper(outputFile,directory);
		zipper.zip();
		//zipDir(zipFileName, destDir);
	}
	public static void unZip(InputStream inputStream, String unZipDir) throws IOException
    {
		System.out.println("Reading ZipFile... ");
		File unZipFileDir = new File(unZipDir);
		if(!unZipFileDir.exists())
		{
			unZipFileDir.mkdir();
		}
		ZipInputStream zin = new ZipInputStream(inputStream);
		ZipEntry zipEntry = zin.getNextEntry();
		while (zipEntry != null)
		{
	      	 String[] zipArrayFileName = zipEntry.getName().split("/");
	      	 String destPath = unZipDir;
	      	 for(int i=0; i<zipArrayFileName.length; i++)
	      	 {
	      	 	destPath = destPath + File.separator + zipArrayFileName[i];
	      	 	File destFile = new File(destPath);
	      	 	System.out.println("Processing " + destFile.getAbsoluteFile());
	      	 	if(zipArrayFileName[i].indexOf(".") <= 0)
	      	 	{
	      	 		if(!destFile.exists())
	      	 			destFile.mkdir();
	      	 	}
	      	 	else
	      	 	{
		            FileOutputStream fout = new FileOutputStream(destFile);
			        byte[] buf = new byte[102400];
			        int len;
			        while ( (len = zin.read(buf)) > 0)
			        {
			          fout.write(buf, 0, len);
			        }
		            fout.close();
	      	 	}
	      	 }
	         zipEntry = (ZipEntry) zin.getNextEntry();
	    }
        zin.close();
        System.out.println("UnZipFile finished... ");
	}
	
	
}
