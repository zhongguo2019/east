package com.krm.ps.util;


import java.util.List;

import com.krm.ps.model.funconfig.services.FunconfigService;

public class FuncconfigUtil {


	public static String getProperty(String funkey,String defaultValue){
		FunconfigService dao=(FunconfigService) BeansUtil.getBean("funconfigService");
		List list=dao.selectfunconfig1(funkey);
		if(list.size()!=0){
			return dao.selectvalue(funkey);
		}else{
			return defaultValue;
		} 
	}



}
