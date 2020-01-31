package com.krm.ps.util;

import java.io.InputStream;
import java.util.Properties;

public class SysConfig {

	public static String DATABASE = "postgres";

	public static String APPSERVER = "tomcat";

	public static String PROVINCE = "other";
	
	public static String IS_DISTRIBUTE_DATA = "false";
	
	public static String IS_DISTRIBUTE_LOG = "true";
	
	public static String DISTRIBUTE_DATA_BY_COLUM = "report_date";
	
	public static String SYSTEM_ROOT_NAME = "slsint";

	public static String JXEXPORTDIR="E:\\hxdownload\\data\\";
	
	public static String LOG4JAVASCRIPT_DISABLED = "true";
	
	public static char DB = 'p';
	
	public static String SYSPROGRAM= "GB"; //该字段默认值设置为GB（全国）

	static {
		loadConfig();
		System.out.println(DATABASE);
		System.out.println(APPSERVER);
		if("true".equals(IS_DISTRIBUTE_DATA)){
			System.out.println("depart");
		}
		if("true".equals(IS_DISTRIBUTE_LOG)){
			System.out.println("depart log");
		}
	}

/*private static void loadConfig() {

		try {
			Properties pro = new Properties();
			InputStream stream = SysConfig.class
					.getResourceAsStream("/sysconfig.properties");
			pro.load(stream);

			DATABASE = pro.getProperty("database");
			DB = DATABASE.charAt(0);
			APPSERVER=pro.getProperty("appserver");
			PROVINCE=pro.getProperty("province");
			IS_DISTRIBUTE_DATA = pro.getProperty("distributeData");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}}
	*/
	private static void loadConfig() {
	
		try {
			Properties pro = new Properties();
			InputStream stream = SysConfig.class
					.getResourceAsStream("/sysconfig.properties");
			pro.load(stream);
	
			DATABASE = pro.getProperty("database");
			DB = DATABASE.charAt(0);
			APPSERVER=pro.getProperty("appserver");
			PROVINCE=pro.getProperty("province");
			IS_DISTRIBUTE_DATA = pro.getProperty("distributeData");
			IS_DISTRIBUTE_LOG = pro.getProperty("distributeLog","");
			SYSTEM_ROOT_NAME = pro.getProperty("systemRootName", "slsint");
			JXEXPORTDIR=pro.getProperty("jxexportdir");
			SYSPROGRAM=pro.getProperty("sysProgram");
			String flag = pro.getProperty("log4javascript.disabled");
			if (flag != null && "false".equals(flag.toLowerCase()))
			{
				LOG4JAVASCRIPT_DISABLED = "false";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
