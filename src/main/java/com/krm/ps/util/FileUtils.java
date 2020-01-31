package com.krm.ps.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import com.krm.ps.util.FuncConfig;

public class FileUtils
{
	public static synchronized void write(StringBuffer sb,File file){
		// System.out.println("@@@@WE LOG NOTHIONG HERE!!! 这里我们什么也不记录了！！！！");
		try{
			StringBuffer content = new StringBuffer();
			/**
			if(file.exists()) {
				InputStream is = new FileInputStream(file);
				BufferedReader in = new BufferedReader(new InputStreamReader(is, "UTF-8"));
				String line = null;
				while((line = in.readLine()) != null) {
					content.append(line);
					content.append("\r\n");
				}
				
				in.close();
				is.close();
			}
			*/
			// 2010-7-2 下午01:45:24 皮亮修改
			// 这里直接追加到文件未端，改变以前的写文件方式
			if (file.exists() && file.length() != 0)
				content.append("\r\n");
			content.append(sb.toString());
			//content.append("\r\n");
			FileOutputStream fos = new FileOutputStream(file,true);
			OutputStreamWriter osw = new OutputStreamWriter(fos,"UTF-8");
			osw.write(content.toString());
			osw.close();
			fos.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void delDirectoryFiles(File directory) {
		if(directory.isDirectory()) {
			File [] file = directory.listFiles();
			for(int i = 0; i < file.length; i++) {
				file[i].delete();
			}
		}
	}
	
	public static String getLogFile(String systemType) {
		String fileName = systemType + "_" + DateUtils.thisDate(DateUtils.DEFAULT_FORMAT) + ".txt";
		String pro = FuncConfig.getProperty("logger.log.path", "F:/__Temp/1105");
		File file = new File(pro);
		if(!file.exists()) {
			file.mkdirs();
		}
		return pro + File.separator + fileName;
		
	}
}
