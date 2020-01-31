package com.krm.ps.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.i18n.LocaleContextHolder;


/**
 * Date Utility Class This is used to convert Strings to Dates and Timestamps
 * 
 * <p>
 * <a href="DateUtil.java.html"><i>View Source</i></a>
 * </p>
 * 
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a> Modified by
 *         <a href="mailto:dan@getrolling.com">Dan Kibler </a> to correct time
 *         pattern. Minutes should be mm not MM (MM is month).
 * @version $Revision: 1.2 $ $Date: 2010/05/18 03:24:43 $
 */
public class DateUtil {
	// ~ Static fields/initializers
	// =============================================

	private static Log log = LogFactory.getLog(DateUtil.class);

	private static String defaultDatePattern = null;

	private static String timePattern = "yyyy-MM-dd HH:mm:ss";

	// ~ Methods
	// ================================================================

	/**
	 * Return default datePattern (MM/dd/yyyy)
	 * 
	 * @return a string representing the date pattern on the UI
	 */
	public static synchronized String getDatePattern() {
		Locale locale = LocaleContextHolder.getLocale();
		try {
			defaultDatePattern = ResourceBundle.getBundle(Constants.BUNDLE_KEY,
					locale).getString("date.format");
		} catch (MissingResourceException mse) {
			defaultDatePattern = "MM/dd/yyyy";
		}

		return defaultDatePattern;
	}

	/**
	 * This method attempts to convert an Oracle-formatted date in the form
	 * dd-MMM-yyyy to mm/dd/yyyy.
	 * 
	 * @param aDate
	 *            date from database as a string
	 * @return formatted string for the ui
	 */
	public static final String getDate(Date aDate) {
		SimpleDateFormat df = null;
		String returnValue = "";

		if (aDate != null) {
			df = new SimpleDateFormat(getDatePattern());
			returnValue = df.format(aDate);
		}

		return (returnValue);
	}

	/**
	 * This method generates a string representation of a date/time in the
	 * format you specify on input
	 * 
	 * @param aMask
	 *            the date pattern the string is in
	 * @param strDate
	 *            a string representation of a date
	 * @return a converted Date object
	 * @see java.text.SimpleDateFormat
	 * @throws ParseException
	 */
	public static final Date convertStringToDate(String aMask, String strDate)
			throws ParseException {
		SimpleDateFormat df = null;
		Date date = null;
		df = new SimpleDateFormat(aMask);

		if (log.isDebugEnabled()) {
			log.debug("converting '" + strDate + "' to date with mask '"
					+ aMask + "'");
		}
		try {
			date = df.parse(strDate);
		} catch (ParseException pe) {
			// log.error("ParseException: " + pe);
			throw new ParseException(pe.getMessage(), pe.getErrorOffset());
		}

		return (date);
	}

	/**
	 * This method returns the current date time in the format: MM/dd/yyyy HH:MM
	 * a
	 * 
	 * @param theTime
	 *            the current time
	 * @return the current date/time
	 */
	public static String getTimeNow(Date theTime) {
		return getDateTime(timePattern, theTime);
	}

	/**
	 * This method returns the current date in the format: MM/dd/yyyy
	 * 
	 * @return the current date
	 * @throws ParseException
	 */
	public static Calendar getToday() throws ParseException {
		Date today = new Date();
		SimpleDateFormat df = new SimpleDateFormat(getDatePattern());

		// This seems like quite a hack (date -> string -> date),
		// but it works ;-)
		String todayAsString = df.format(today);
		Calendar cal = new GregorianCalendar();
		cal.setTime(convertStringToDate(todayAsString));

		return cal;
	}

	/**
	 * This method generates a string representation of a date's date/time in
	 * the format you specify on input
	 * 
	 * @param aMask
	 *            the date pattern the string is in
	 * @param aDate
	 *            a date object
	 * @return a formatted string representation of the date
	 * 
	 * @see java.text.SimpleDateFormat
	 */
	public static final String getDateTime(String aMask, Date aDate) {
		SimpleDateFormat df = null;
		String returnValue = "";

		if (aDate == null) {
			log.error("aDate is null!");
		} else {
			df = new SimpleDateFormat(aMask);
			returnValue = df.format(aDate);
		}

		return (returnValue);
	}

	/**
	 * This method generates a string representation of a date based on the
	 * System Property 'dateFormat' in the format you specify on input
	 * 
	 * @param aDate
	 *            A date to convert
	 * @return a string representation of the date
	 */
	public static final String convertDateToString(Date aDate) {
		return getDateTime(getDatePattern(), aDate);
	}

	/**
	 * This method converts a String to a date using the datePattern
	 * 
	 * @param strDate
	 *            the date to convert (in format MM/dd/yyyy)
	 * @return a date object
	 * 
	 * @throws ParseException
	 */
	public static Date convertStringToDate(String strDate)
			throws ParseException {
		Date aDate = null;

		try {
			if (log.isDebugEnabled()) {
				log.debug("converting date with pattern: " + getDatePattern());
			}

			aDate = convertStringToDate(getDatePattern(), strDate);
		} catch (ParseException pe) {
			log.error("Could not convert '" + strDate
					+ "' to a date, throwing exception");
			pe.printStackTrace();
			throw new ParseException(pe.getMessage(), pe.getErrorOffset());

		}

		return aDate;
	}

	/**
	 * @return 今天的起始时间
	 */
	public static Date getTodayFrom() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	/**
	 * @return 今天的结束时间
	 */
	public static Date getTodayTo() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 999);
		return cal.getTime();
	}

	/**
	 * 格式化日期串－yyyy-MM-dd为yyyyMM00
	 * 
	 * @param strDate
	 * @return
	 */
	public static final String formatDate(String strDate) {
		if(strDate.length()<=8){
			return strDate;
		}
		String s = strDate.replaceAll("-", "");
		String returnValue = s.substring(0, 6) + "00";
		return (returnValue);
	}

	/**
	 * 格式化日期串－yyyyMMdd为yyyy-MM-dd
	 * 
	 * @param strDate
	 * @return
	 */
	public static final String formatDate2(String strDate) {
		String returnValue = strDate.substring(0, 4) + "-"
				+ strDate.substring(4, 6) + "-" + strDate.substring(6, 8);
		return (returnValue);
	}
	
	public static final String getYearLastDate(Date date) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy");
		String returnValue = df.format(date) + "-12-31";
		return (returnValue);
	}
	
	/**
	 * <p>根据给定的日期，计算推移后的日期</p> 
	 *
	 * @param date	给定的日期
	 * @param field 按什么进行日期推移，如按天{@link Calendar#DAY_OF_YEAR}
	 * 				按月{@link Calendar#MONTH}等等。
	 * @param interval 指定的时间间隔
	 * @return
	 * @author 皮亮
	 * @version 创建时间：2010-5-18 上午11:19:14
	 */
	public static final Date getPreviousDate(Date date, int field, int interval)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		log.debug("init date is " + date.toString());
		calendar.add(field, -interval);
		date = calendar.getTime();
		log.debug("after caculating, the new date is " + date.toString());
		return date;
	}
	
	/**
	 * 将指定的日期转成对应的日期格式的字符串
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 *
	 * 2011-8-3 上午11:39:35 皮亮
	 */
	public static String formatDate(Date date, String pattern) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
		return dateFormat.format(date);
	}
	/**
	 * 将指定的日期转成对应的日期格式的字符串
	 * 
	 * @param date
	 * @param pattern
	 * @returngetLastbyDate
	 *
	 * 2011-8-3 上午11:39:35 皮亮
	 */
	public static String formatStrDate(String date, String pattern) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
		return dateFormat.format(new Date());
	}
	/**
	 * 根据报表频度获取报表的日期
	 * @param frequency 报表频度
	 * @param date 日期   格式YYYYMMDD
	 * @return
	 * 2011-8-29 by ydw
	 */
	public static final String getFrequencyDate(String frequency, String date){
		
	 
		return  null;
	}
	/**
	 * 以aMark格式解析date字符串，转换成日期类型,其中date要以aMark格式才可以
	 * @param date
	 * @param aMark
	 * @return
	 */
	public static  final Date getLastbyDate(String date,String aMark) {
		Date today;
		Calendar cal;
		try {
			today = convertStringToDate(aMark,date);
			cal=Calendar.getInstance();
			cal.setTime(today);
			cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));   
			return  cal.getTime();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
	public static void main(String[] args) throws ParseException {
		
		System.out.println(""+getDate(getLastbyDate("2013-05-25","yyyy-MM-dd")));
	}
}
