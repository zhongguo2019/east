package com.krm.ps.sysmanage.reportdefine.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/*
 * 在数据字典里没有定义
 */
public class FrequencyDefine {
	
	public static List monthReport = new ArrayList();
	
	public static Map define = new HashMap();
	
	static{
		
		//月
		monthReport.add(new Integer(1));
		
		ArrayList a = new ArrayList();
		
		a.addAll(monthReport);
		//季
		a.add(new Integer(2));
		
		ArrayList b = new ArrayList();
		
		b.addAll(a);
		//半年
		b.add(new Integer(3));
		
		ArrayList c = new ArrayList();
		
		c.addAll(b);
		//年
		c.add(new Integer(4));
		
		define.put("03",a);
		
		define.put("06",b);
		
		define.put("09",a);
		
		define.put("12",c);
		
	}
	
	public static List getFrequency(String month){
		if(define.containsKey(month)){
			return (List)define.get(month);
		}
		return monthReport;
	}
}
