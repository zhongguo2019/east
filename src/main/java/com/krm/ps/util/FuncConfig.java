package com.krm.ps.util;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.krm.ps.framework.property.service.PropertyService;
import com.krm.ps.framework.property.vo.Prop;
import com.krm.ps.sysmanage.usermanage.services.DictionaryService;
import com.krm.ps.sysmanage.usermanage.vo.Dictionary;
//import com.krm.slsint.common.dictionary.dao.hibernate.DictionaryDAOHibernate;
import com.krm.ps.util.BeansUtil;

/**
 * <p>Title: FuncConfig</p>
 *
 * <p>Description: 功能配置属性文件应用类</p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: KRM Soft</p>
 *
 * @author 
 */
public class FuncConfig {
	
	/**
	 * <code>日志记录对象</code>
	 */
	private final Log logger = LogFactory.getLog(getClass());

	private static Properties pro = new Properties();
	
	private static Properties cnPro = new Properties();
	
	//属性配置读取方式
	private static String propertyRead_flag = null; 
	
	static {
		loadConfig();
	}

	/**
	 * 加载属性文件
	 */
	private static void loadConfig() {
		try {
			InputStream stream = FuncConfig.class
					.getResourceAsStream("/funcconfig.properties");
			InputStream cnStream = FuncConfig.class.getResourceAsStream("/ApplicationResources_zh_CN.properties");
			
			pro.load(stream);
			propertyRead_flag= pro.getProperty("property.read");
			cnPro.load(cnStream);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 查询属性的值
	 * @param proName 属性名称
	 * @return
	 */
	public static String getProperty(String proName) {
		if(isReadFromFuncconfigPropertyFile()){
			return pro.getProperty(proName);
		}else{
			return getPropertyService().getProValue(proName);
		}
	}

	/**
	 * 报表控件名#版本
	 * @return
	 */
	public static String getGoldReport() {
		String goldReport = null;
		if(isReadFromFuncconfigPropertyFile()){
			goldReport = pro.getProperty("goldReport.filenVervion");
		}else{
			goldReport = getPropertyService().getProValue("goldReport.filenVervion");
		}
		if(goldReport==null) {
			goldReport="GdReport.CAB#Version=1,0,0,9";
		}
		return goldReport;
	}

	/**
	 * 查询属性的值，若未定义该属性，返回默认值
	 * @param proName 属性名称
	 * @param defaultValue 属性默认值
	 * @return
	 */
	public static String getProperty(String proName, String defaultValue) {
		if(isReadFromFuncconfigPropertyFile()){
			return pro.getProperty(proName, defaultValue);
		}else{
			return getPropertyService().getProValue(proName, defaultValue);
		}
	}

	/**
	 * 查询中文属性的值
	 * 若未定义该属性，返回默认值；若已定义，从资源文件中查询{}中制定的的资源。
	 * 该方法要求在资源中用{}定义在资源文件中的资源名称！
	 * @param proName 属性名称
	 * @param defaultValue 属性默认值
	 * @return
	 */
	public static String getCNProperty(String proName, String defaultValue) {
		String proValue = null;
		if(isReadFromFuncconfigPropertyFile()){
			proValue = pro.getProperty(proName, defaultValue);
		}else{
			proValue = getPropertyService().getProValue(proName, defaultValue);
		}
		while(proValue.indexOf("{") >= 0){
			int startIndex = 0;
			int endIndex = 0;
			startIndex = proValue.indexOf("{"); 
			endIndex = proValue.indexOf("}");
			String tempProName = proValue.substring(startIndex + 1, endIndex);
			String tempProValue = null;
			if(isReadFromFuncconfigPropertyFile()){
				tempProValue = cnPro.getProperty(tempProName, "");
			}else{
				tempProValue = getPropertyService().getProValue(tempProName,"");
			}
			tempProName = tempProName.replaceAll("\\.", "\\\\.");
			proValue = proValue.replaceAll("\\{" + tempProName + "\\}", tempProValue);
		}
		return proValue;
	}

	private static boolean isReadFromFuncconfigPropertyFile() {//判断配置为空
		return propertyRead_flag == null || (!"".equals(propertyRead_flag)&& "mo".equals(propertyRead_flag));
	}
	
	/**
	 * 从数据库中定义的信息中加载功能配置
	 * @param svr 字典表服务
	 */
	public static void setPropertiesFromDB(DictionaryService svr){
		List pList = svr.getDics(1012);
		if(pList == null){
			return;
		}
		Iterator properties = pList.iterator();
		while(properties.hasNext()){
			Dictionary dicObj = (Dictionary)properties.next();
			if(dicObj.getDescription()==null || dicObj.getDescription().length() == 0){
				continue;
			}
			updateProEntry(dicObj.getDicname(), dicObj.getDescription());
		}
	}
	/**
	 * 从资源文件中取得资源
	 * @param proName
	 * @return
	 */
	public static String getCNProperty(String proName) {
		String proValue = null;
		if(isReadFromFuncconfigPropertyFile()){
			proValue = cnPro.getProperty(proName);
		}else{
			proValue = getPropertyService().getProValue(proName);
		}
		return proValue;
	}
	
	public static void updateProEntry(String key, String newValue){
		if(isReadFromFuncconfigPropertyFile()){
			pro.setProperty(key, newValue);
		}else{
			Prop property = null;
			property = getPropertyService().getProperty(key);
			if(property==null){
				//在数据库中新建一属性对象
				property = new Prop();
				property.setProName(key);
				property.setProValue(newValue);
				Date date = new Date();
				SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
				String time = df.format(date);
				property.setCreateDate(time);
				property.setStatus('1');
				getPropertyService().addProperty(property);
			}else{
				property.setProValue(newValue);
				Date date = new Date();
				SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
				String time = df.format(date);
				property.setEditDate(time);
				getPropertyService().updateProperty(property);
			}
		}
	}
	
	/**
	 * <p>查询指定名称的功能是否已经打开</p> 
	 *
	 * @param proName 功能配置的属性名称
	 * @return
	 * @author 皮亮
	 * @version 创建时间：2010-7-14 下午01:23:21
	 */
	public static boolean isOpenFun(String proName)
	{
		String confValue = getProperty(proName);
		return confValue != null && ("on".equals(confValue) || "yes".equals(confValue));
	}
	
	public static PropertyService getPropertyService(){
		return (PropertyService) BeansUtil.getBean("propertyService");
	}
	
	public static boolean isOpend(String proName)
	{
		String confValue = getProperty(proName);
		return confValue != null && ("true".equals(confValue));
	}
	
	
}
