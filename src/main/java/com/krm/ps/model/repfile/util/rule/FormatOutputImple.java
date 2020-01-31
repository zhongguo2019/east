package com.krm.ps.model.repfile.util.rule;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import com.krm.ps.model.repfile.util.FormatOutput;



public class FormatOutputImple implements FormatOutput{

	/**
	 * 对指标数据标格式化处理
	 * @param organid 报表的id
	 * @param fieldname 指标名称
	 * @param offsenrule 脱敏规则
	 * @param contras 指标值
	 * @return Object
	 */

	public String formatout(Object value,String outrule,String rulesize) {
		String values = "";
		if("0".equals(outrule)){
			//0为不对数据进行格式化处理
			values = (String)value;
		}else if("1".equals(outrule) && value instanceof String){
			//1为String类型的数据格式化处理
			values = formatString((String)value,rulesize);
		}else if("2".equals(outrule) && value instanceof Integer){
			//2为Integer类型的数据格式化处理
			values = formtInteger((Integer)value,rulesize);
		}else if("3".equals(outrule) && value instanceof Double){
			//2为Double类型的数据格式化处理
			values = formatDouble((Double)value,rulesize);
		}
		return values;
	}
	
	/** 
	 * 对String类型的数据进行格式化处理
	 */
	private String formatString(String value,String rulesize) {
		int size = Integer.parseInt(rulesize);
		int length = value.length();
		if(length < size){
			return value;
		}
		return value.substring(0, size);//截取定长的字符串
	}
	
	private String formatDouble(Double value,String rulesize) {
		Integer num = Integer.parseInt(rulesize);
		NumberFormat format =NumberFormat.getNumberInstance();
		format.setMinimumFractionDigits(num);//保留两位有效数字
		return format.format(value);
	}
	
	private String formtInteger(Integer value,String rulesize){
		NumberFormat format =NumberFormat.getIntegerInstance();
		return format.format(value); //将整形格式化为231,231,111形式
	}
	/** 
	 * 对Double类型的数据百分比格式化处理
	 */
	private String formts(Double value,String rulesize){
		DecimalFormat format = (DecimalFormat)NumberFormat.getPercentInstance();
		format.applyPattern("000.000%");
		return format.format(value);
	}
	
	
}
