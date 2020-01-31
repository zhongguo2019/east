package com.krm.ps.util.config;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;

/**
 * 属性文件访问器。
 * 
 * @author Xu ZhiGang
 * 
 */
/**
 * <p>Title: </p> 属性文件访问器。 <p>Description: </p> 可以从指定的属性文件得到指定健的值。<br> 特点如下:<br>
 * 1、属性文件改变后再重新加载，否则不读文件，提高性能<br> 2、线程安全
 * 
 * @version 1.0
 * @author Xu ZhiGang
 */
public class PropertiesHandle
{

	/**
	 * 资源文件名
	 */
	private static File propertyFile = null;

	/**
	 * 一系列资源文件，只要访问过的资源文件，都存放在这里。 由文件名为健，值为File类型。
	 */
	private static HashMap propertyFilesMap = new HashMap();

	/**
	 * 所有访问过的资源文件的最后修改时间，原来判断资源文件是否被修改过。 由文件名为健，值为Long类型。
	 */
	private static HashMap modifiedTimesMap = new HashMap();

	/**
	 * 所有访问过的资源文件的对应的属性对象。 由文件名为健，值为Properties类型。
	 */
	private static HashMap propertiesMap = new HashMap();

	/**
	 * 访问属性资源文件的属性对象
	 */
	// private static Properties ppt = null;
	/**
	 * 获得指定资源文件中指定健的值
	 * 
	 * @param propertyFileName 指定的资源文件名。以资源文件相对本类的路径或资源文件本身的绝对路径为准。
	 * @param key 指定的健
	 * @return
	 */
	public synchronized static String getProperty(String propertyFileName,
		String key)
	{

		loadResourceStream(propertyFileName);
		if (key == null)
		{
			return null;
		}
		else
		{
			Properties ppt = (Properties) propertiesMap.get(propertyFileName);
			return ppt == null ? null : ppt.getProperty(key);
		}
	}

	/**
	 * 获得指定资源文件中所有的健值。
	 * 
	 * @param propertyFileName 指定的资源文件名。以资源文件相对本类的路径或资源文件本身的绝对路径为准。
	 * @return
	 */
	public synchronized static Enumeration getElements(String propertyFileName)
	{
		loadResourceStream(propertyFileName);
		Properties ppt = (Properties) propertiesMap.get(propertyFileName);
		return ppt == null ? null : ppt.elements();
	}

	/**
	 * 载入property文件
	 * 
	 * @param propertyFileName 指定的资源文件名
	 */
	private static void loadResourceStream(String propertyFileName)
	{
		// 对于所有访问过的资源文件，都要保存这个文件对象，以判断其是否被修改过
		if (propertyFilesMap.get(propertyFileName) == null)
		{
			File file = new File(propertyFileName);
			propertyFilesMap.put(propertyFileName, file);
			modifiedTimesMap.put(propertyFileName,
				new Long(file.lastModified()));
		}
		// 确定当前要访问的资源文件，并生成访问属性资源文件的属性对象
		propertyFile = (File) propertyFilesMap.get(propertyFileName);
		Properties ppt = (Properties) propertiesMap.get(propertyFileName);

		// 如果当前要访问的资源文件以前没有被访问过，或被修改过，要重新加载，否则不加载
		if (ppt == null)
		{
			ppt = new Properties();
			if (ppt.isEmpty()
				|| propertyFile.lastModified() > ((Long) modifiedTimesMap
					.get(propertyFileName)).longValue())
			{
				modifiedTimesMap.put(propertyFileName, new Long(propertyFile
					.lastModified()));
				ppt.clear();
				InputStream is;
				try
				{
					is = Class.forName("com.krm.util.config.PropertiesHandle")
						.getResourceAsStream(propertyFileName);
					ppt.load(is);
				}
				catch (ClassNotFoundException e1)
				{
					System.err.println("请确保属性文件" + propertyFileName + "存在!");
				}
				catch (IOException e)
				{
					System.err.println("不能读取属性文件。");
				}
				propertiesMap.put(propertyFileName, ppt);
			}
		}
	}

}
