package com.krm.ps.model.xlsinit.util;

import java.util.ArrayList;
import java.util.Collection;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 类型检查包，传递字符是否是相应类型，均为静态方法。
 * 
 * @author
 * @version
 */
public class TypeChecker {

	/**
	 * 日志文件对象
	 */
	private final static Log log = LogFactory.getLog(TypeChecker.class);

	/**
	 * 检查是否为Email，标志是中间必定包含符号。
	 * 
	 * @param str
	 *            - 待检查字符串
	 * @return java.lang.Boolean true---是 ； false---否
	 * @roseuid 3E01F9AB028B
	 */
	public static boolean isEmail(String str) {
		if (str == null || str.trim() == "") {
			return false;
		}
		return true;
	}

	/**
	 * 检查是否为整形数。
	 * 
	 * @param str
	 *            - 待检查字符串
	 * @return java.lang.Boolean true---是 ； false---否
	 * @roseuid 3E01F9BB02F2
	 */
	public static boolean isInteger(String str) {
		try {
			Integer.parseInt(str);
		} catch (Exception ex) {
			return false;
		} // end try catch
		return true;
	}

	/**
	 * 检查是否是合法的长整型。
	 * 
	 * @param str
	 *            输入的字符串
	 * @return java.lang.Boolean true---是 ； false---否
	 * @roseuid 3E70325B0297
	 */
	public static boolean isLong(String str) {
		try {
			Long.parseLong(str);
		} catch (Exception ex) {
			return false;
		} // end try catch
		return true;
	}

	/**
	 * 检查字符串是否是否为空。
	 * 
	 * @param str
	 *            待检查字符串
	 * @return java.lang.Boolean true---是 ； false---否
	 * @roseuid 3E70341A025E
	 */
	public static boolean isEmpty(String str) {
		if (str == null || str.equals("")) {
			return true;
		}
		return false;
	}

	/**
	 * 检查字符串数组是否为空。
	 * 
	 * @param str
	 *            String[] 待检查字符串数组
	 * @return boolean true---是 ； false---否
	 */
	public static boolean isEmpty(String[] str) {
		if (str == null || (str.length <= 0)) {
			return true;
		}
		return false;
	}

	/**
	 * 检查字符串数组是否为空。
	 * 
	 * @param str
	 *            String[] 待检查字符串数组
	 * @return boolean true---是 ； false---否
	 */
	public static boolean isEmpty(StringBuffer strbuffer) {
		if (strbuffer == null || (strbuffer.length() <= 0)) {
			return true;
		}
		return false;
	}

	/**
	 * 检查Map容器是否为空。
	 * 
	 * @param map
	 *            Map 待检查Map容器
	 * @return boolean true---是 ； false---否
	 */
	public static boolean isEmpty(java.util.Map map) {
		if (map == null || map.isEmpty()) {
			return true;
		}
		return false;
	}

	/**
	 * 检查List容器是否为空。
	 * 
	 * @param list
	 *            List 待检查List容器
	 * @return boolean true---是 ； false---否
	 */
	public static boolean isEmpty(java.util.List list) {
		if (list == null || list.isEmpty()) {
			return true;
		}
		return false;
	}

	/**
	 * 检查Collection容器是否为空。
	 * 
	 * @param collection
	 *            Collection 待检查collection容器
	 * @return boolean true---是 ； false---否
	 */
	public static boolean isEmpty(Collection collection) {
		if (collection == null || collection.isEmpty()) {
			return true;
		}
		return false;
	}

	/**
	 * 检查ArrayList容器是否为空。
	 * 
	 * @param arrayList
	 *            ArrayList 待检查arrayList容器
	 * @return boolean true---是 ； false---否
	 */

	public static boolean isEmpty(ArrayList arrayList) {
		if (arrayList == null || arrayList.isEmpty()) {
			return true;
		}
		return false;
	}

	/**
	 * 检查Object对象是否为空。
	 * 
	 * @param Object
	 *            obj 待检查Object对象
	 * @return boolean true---是 ； false---否
	 */
	public static boolean isEmpty(Object obj) {
		if (obj == null) {
			return true;
		}
		return false;
	}

	/**
	 * 检查是否为浮点数。
	 * 
	 * @param str
	 *            - 待检查字符串
	 * @return java.lang.Boolean true---是 ； false---否
	 * @roseuid 3E01F9C902B6
	 */
	public static boolean isFloat(String str) {
		try {
			float tmp = Float.parseFloat(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		} // end try - catch
	}

	/**
	 * ����Ƿ�Ϊ���֡�
	 * 
	 * @param str
	 *            - �����ַ�
	 * @return java.lang.Boolean true---�� �� false---��
	 * @roseuid 3E01FA820013
	 */
	public static boolean isNumber(String str) {
		try {
			double tmp = Double.parseDouble(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		} // end try - catch
	}

	/**
	 * 检查是否为日期，必须使用2002-12-12格式，月份和天一位时必须有前导零。
	 * 
	 * @param str
	 *            - 待检查字符串
	 * @return java.lang.Boolean true---是 ； false---否
	 * @roseuid 3E01F9DC02BE
	 */
	public static boolean isDate(String str) {
		java.text.SimpleDateFormat df = new java.text.SimpleDateFormat(
				"yyyyMMdd");
		try {
			java.util.Date date = df.parse(str);
			// 是否变形
			String apple = df.format(date);
			if (!apple.equals(str)) {
				return false;
			}
		} catch (Exception ex) {
			return false;
		} // end try - catch
		return true;
	}

	  /**
	   * 检查是否为合法的日期时间，格式为2002-02-02 15:00:03。
	   *
	   * @param str -
	   *          待检查字符串
	   * @return java.lang.Boolean true---是 ； false---否
	   * @roseuid 3E01F9F20157
	   */
	public static boolean isDatetime(String str) {

		java.text.SimpleDateFormat df = new java.text.SimpleDateFormat(
				"yyyyMMddHHmmss");
		java.text.SimpleDateFormat df400 = new java.text.SimpleDateFormat(
				"yyyyMMddHHmmss");
		try {
			java.util.Date date = df.parse(str);
			  //是否变形
			String apple = df.format(date);
			if (!apple.equals(str)) {
				return false;
			}
		} catch (java.text.ParseException ex) {
			try {
				java.util.Date date2 = df400.parse(str);
				 //是否变形
				String apple1 = df400.format(date2);
				if (!apple1.equals(str)) {
					return false;
				}
			} catch (java.text.ParseException exa) {
				log.error(exa);
				return false;
			}
			return true;
		}
		return true;
	}

	  /**
	   * 检查是否为合法时间。
	   *
	   * @param str -
	   *          待检查字符串
	   * @return java.lang.Boolean true---是 ； false---否
	   * @roseuid 3E01FA12029D
	   */
	public static boolean isTime(String str) {
		return true;
	}

	  /**
	   * 检查是否为浮点类型的字符串。
	   *
	   * @param decimal -
	   *          待检查字符串
	   * @return java.lang.Boolean true---是 ； false---否
	   * @roseuid 3E01FA12029D
	   */
	public static void isDecimal(String decimal) throws Exception {
		if (isEmpty(decimal)) {
			return;
		}
		int commaPos = decimal.indexOf(",");
		if (commaPos < 1) {
			throw new Exception(decimal + "Decimal格式必须包含逗号如10,2");
		}
		int fractionValue = Integer.parseInt(decimal.substring(commaPos + 1));
		int integerValue = Integer.parseInt(decimal.substring(0, commaPos))
				- fractionValue + 1;
		if (integerValue < 1 || fractionValue < 0) {
			throw new Exception(decimal + "  Decimal格式不正确!");
		}
		if (fractionValue == 0) {
			throw new Exception("Type中decimal替换成integer");
		}
	}

}
