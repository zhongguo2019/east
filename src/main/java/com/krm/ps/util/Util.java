package com.krm.ps.util;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Util {
	// 缺省日期格式
	static String DEFAULT_FORMAT = "yyyyMMdd";

	public final static Log log = LogFactory.getLog(Util.class);

	// mutilthread:TODO

	public static String thisDate() {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(new Date());
		return new SimpleDateFormat(DEFAULT_FORMAT).format(calendar.getTime());
	}

	/**
	 * 取得指定日期若干天后的日期．
	 * 
	 * @param date
	 *            开始日期，格式yyyyMMdd
	 * @param days
	 *            天数，可以为负
	 * @param format
	 *            日期格式
	 * @return
	 */
	public static String dateAdd(String date, int days, String format) {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		Date d = null;
		String dateStr = "";
		try {
			d = formatter.parse(date);
		} catch (ParseException e) {
			System.out.println(e);
		}
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(d);
		calendar.add(Calendar.DATE, days);
		dateStr = formatter.format(calendar.getTime());
		return dateStr;
	}

	public static String dateAdd(String date, int days) {
		return dateAdd(date, days, DEFAULT_FORMAT);
	}

	public static String yearAdd(String date, int years) {
		return yearAdd(date, years, DEFAULT_FORMAT);
	}

	/**
	 * 取得当前日期指定天数前的日期
	 * 
	 * @param days
	 * @param format
	 *            日期格式
	 * @return
	 */
	public static String getDaysBeforeNow(int days, String format) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(new Date());
		calendar.add(Calendar.DATE, -days);
		return new SimpleDateFormat(format).format(calendar.getTime());
	}

	/**
	 * 取得当前日期指定天数前的日期
	 * 
	 * @param days
	 * @return
	 */
	public static String getDaysBeforeNow(int days) {
		return getDaysBeforeNow(days, DEFAULT_FORMAT);
	}

	/**
	 * 取得指定日期若干年后的日期．
	 * 
	 * @param date
	 *            开始日期，格式yyyMMdd
	 * @param years
	 *            年数，可以为负
	 * @param format
	 *            日期格式
	 * @return
	 */
	public static String yearAdd(String date, int years, String format) {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		Date d = null;
		String dateStr = "";
		try {
			d = formatter.parse(date);
		} catch (ParseException e) {
			System.out.println(e);
		}
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(d);
		calendar.add(Calendar.YEAR, years);
		dateStr = formatter.format(calendar.getTime());
		return dateStr;
	}

	/**
	 * 返回最近days天的日期
	 * 
	 * @param days
	 * @return String数组,索引为０是当天日期．日期格式yyyyMMdd
	 */
	public static String[] gen_latestDates(int days) {
		String[] dates = new String[days];
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(new Date());
		SimpleDateFormat formatter = new SimpleDateFormat(DEFAULT_FORMAT);
		dates[0] = formatter.format(calendar.getTime());
		for (int i = 1; i < days; i++) {
			calendar.add(Calendar.DATE, -1);
			dates[i] = formatter.format(calendar.getTime());
		}
		return dates;
	}

	/**
	 * 金额大写转换
	 * 
	 * @param n
	 * @return
	 */
	public static String mTrans(double n) {

		if (n >= 1.0e8) {
			return "壹亿+";
		}

		String d[] = new String[] { "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒",
				"捌", "玖" };
		String z[] = new String[] { "仟", "佰", "拾", "万", "仟", "佰", "拾", "元",
				"角", "分" };

		String r = "";

		n = Math.round(n * 100);

		for (int i = 9; n >= 1.0; n /= 10, i--) {
			r = d[(int) (n % 10)] + z[i] + r;
		}

		r = r.replaceFirst("(零\\W)*零万", "万").replaceFirst("(零\\W)*零元", "元")
				.replaceAll("^(零\\W)+|(零\\W)+$", "").replaceAll("(零\\W)+", "零")
				.replaceAll("元$", "元整");
		if ("".equals(r)) {
			r = "零元整";
		}
		return r;
	}

	/**
	 * 根据机构体系树节点的子树编码(CODE_ORG_TREE.SUBTREETAG)判断该节点在树上的层次
	 * 
	 * @param subTreeTag
	 * @return 节点在树上的层次
	 */
	public static int getOrganLevel(String subTreeTag) {
		// subTreeTag = subTreeTag.replaceAll("(00)+$", "");

		int len = subTreeTag.length();

		return len / 2;
	}

	/**
	 * 将逗号分割的字符串的各个项目用单引号扩起来
	 * 
	 * @param old
	 *            逗号分割的字符串
	 * @return 在每个项目前后加上单引号后的结果
	 */
	public static String quotedWithQuator(String old) {
		if (null == old || old.length() == 0)
			return "";
		String[] tp = old.split(",");
		StringBuffer buf = new StringBuffer();
		int len = tp.length;
		for (int i = 0; i < len; i++) {
			buf.append("'");
			buf.append(tp[i]);
			buf.append("'");
			if (i < len - 1) {
				buf.append(",");
			}
		}
		return buf.toString();
	}

	public static String quotedNoQuator(String old) {
		if (null == old || old.length() == 0)
			return "";
		String[] tp = old.split(",");
		StringBuffer buf = new StringBuffer();
		int len = tp.length;
		for (int i = 0; i < len; i++) {
			buf.append(tp[i]);
			if (i < len - 1) {
				buf.append(",");
			}
		}
		return buf.toString();
	}

	/**
	 * 将日期的月份减1，日期变成00
	 * 
	 * @param dataDate
	 *            日期(yyyy-MM-dd或yyyyMMdd)
	 * @return
	 */
	public static String monthMinus(String dataDate) {
		String date = dataDate.replaceAll("-", "");
		int year = Integer.parseInt(date.substring(0, 4));
		int month = Integer.parseInt(date.substring(4, 6));
		month--;
		if (month == 0) {
			year--;
			month = 12;
			date = "" + year + month + "00";
		} else if (month > 9) {
			date = "" + year + month + "00";
		} else {
			date = "" + year + "0" + month + "00";
		}
		return date;
	}

	public static String encoding(String fileName) {
		if ("AIX".equals(System.getProperty("os.name").toUpperCase())
				&& SysConfig.DB == 'i' && "tongweb".equals(SysConfig.APPSERVER)) {
			try {
				fileName = new String(fileName.getBytes("gb2312"), "ISO-8859-1");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return fileName;
	}

	public static String coverAddQuotesForDB2(String value) {
		if (value != null) {
			if (value.indexOf("\'") > -1) {
				return value;
			} else {
				String[] values = value.split(",");
				for (int i = 0; i < values.length; i++) {
					values[i] = "'" + values[i] + "'";
				}
				value = "";
				for (int j = 0; j < values.length; j++) {
					value = values[j];
					if (j < values.length) {
						value = ",";
					}
				}
				return value;
			}
		} else {
			return null;
		}
	}

	public static String coverFilterQuotesForDB2(String value) {
		if (value != null) {
			if (value.indexOf("\'") > -1) {
				return value.replaceAll("\'", "");
			} else {
				return value;
			}
		} else {
			return null;
		}
	}

	public static String coverDecimalToVarchar(String valueType) {
		// return "ltrim(rtrim(char(integer(" + valueType + "))))";
		// 在DB2的环境下，修改该语法格式 2012-04-01 贡琳
		// return "ltrim(rtrim(char(integer(" + valueType +
		// "))))=ltrim(rtrim(char(integer(" + valueType + "))))";

		return "integer(ltrim(rtrim(char(integer(" + valueType + ")))))";
	}
/**
 * 把数据库字符型的转化为Decimal类型的
 * @param valueType 要转化的字段
 * @return
 * 2012-08-28 刘新华
 */
	public static String coverVarcharToDecimal(String valueType) {
		return "integer(ltrim(rtrim(" + valueType + ")))";
	}

	/**
	 * <p>
	 * 将List中的元素值拼成字符串，用,号隔开
	 * </p>
	 * 
	 * @param list
	 * @return
	 * @author 皮亮
	 * @version 创建时间：2010-3-31 下午02:34:35
	 */
	public static String listToString(List list) {
		int size = list.size();
		int flag = size - 1;
		String resultStr = "";
		for (int i = 0; i < size; i++) {
			resultStr = resultStr + list.get(i).toString();
			if (i < flag)
				resultStr += ",";
		}
		return resultStr;
	}

	/**
	 * <p>
	 * 通过StringBuffer来构造字符串
	 * </p>
	 * 
	 * @param objects
	 * @return 返回的字符串
	 * @author 皮亮
	 * @version 创建时间：2010-4-28 上午09:31:25
	 */
	public static String buildStringWithStringBuffer(Object[] objects) {
		StringBuffer stringBuffer = new StringBuffer();
		if (objects == null) {
			log.warn("!!!!!!!!!!!!!传入的对象数组为能为null");
			return "";
		}
		int objSize = objects.length;
		Object obj;
		for (int i = 0; i < objSize; i++) {
			obj = objects[i];
			if (obj == null) {
				log.warn("there is null object in the array, it will be set with empty string!!!");
				obj = "";
			}
			stringBuffer.append(obj);
		}
		return stringBuffer.toString();
	}

	/**
	 * <p>
	 * 为指定的类生成一个日志记录对象
	 * </p>
	 * 
	 * @param clazz
	 * @return
	 * @author 皮亮
	 * @version 创建时间：2010-5-12 上午10:04:16
	 */
	public static Log getLog(Class clazz) {
		if (clazz == null) {
			log.warn("为指定类产生一个记录日志的对象，必须指定一个类！");
			return null;
		}
		return LogFactory.getLog(clazz);
	}

	/**
	 * <p>
	 * 把一个对象数组转换成一个以,号分隔的字符串
	 * </p>
	 * 
	 * 每个字符串是通过调用对象的toString()方法得来
	 * 
	 * @param objects
	 * @return
	 * @author 皮亮
	 * @version 创建时间：2010-6-17 上午02:20:07
	 */
	public static String arrayToString(Object[] objects) {
		int size = objects.length;
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < size; i++) {
			buffer.append(",").append(objects[i].toString());
		}
		return buffer.toString().replaceAll("^,", "");
	}

	/**
	 * <p>
	 * 根据对象列表，得到指定对象属性的map
	 * </p>
	 * 
	 * @param objectList
	 *            对象列表
	 * @param keyProperty
	 *            指定作为key的属性
	 * @param valueProperty
	 *            指定作为value的属性
	 * @return
	 * @author 皮亮
	 * @version 创建时间：2010-7-5 下午05:25:44
	 */
	public static Map buildFieldsMap(List objectList, String keyProperty,
			String valueProperty) {
		Map fieldsMap = new HashMap();
		if (objectList == null || objectList.size() == 0) {
			return fieldsMap;
		}
		Iterator it = objectList.iterator();
		Object tmpObj = null;
		while (it.hasNext()) {
			tmpObj = it.next();
			try {
				fieldsMap.put(BeanUtils.getProperty(tmpObj, keyProperty),
						BeanUtils.getProperty(tmpObj, valueProperty));
			} catch (Exception e) {
				e.printStackTrace();
				// TODO handle it
				throw new RuntimeException("error occurs when "
						+ "get property [" + keyProperty + "] " + "or ["
						+ valueProperty + "] from the object ["
						+ tmpObj.getClass().getName() + "]");
			}
		}
		return fieldsMap;
	}

	public static String formatValue(String type, double value) {
	return null;
	}

	private static int getDicimalDigit(String formatStr) {
		int value = 0;
		 
		return value;
	}
}
