package com.krm.ps.model.reportdefine.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtils {
	public static String TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

	public static String DEFAULT_FORMAT = "yyyy-MM-dd";

	public static String thisDate(String format) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(new Date());
		return new SimpleDateFormat(format).format(calendar.getTime());
	}

	public static void intervalDate(String begindate, String enddate) {
		int bYear = Integer.parseInt(begindate.substring(0, 4));
		int bMonth = Integer.parseInt(begindate.substring(4, 6));
		int bDay = Integer.parseInt(begindate.substring(6));
		System.out.println("byear:" + bYear + " bmonth:" + bMonth + " bday:"
				+ bDay);

		int eYear = Integer.parseInt(enddate.substring(0, 4));
		int eMonth = Integer.parseInt(enddate.substring(4, 6));
		int eDay = Integer.parseInt(enddate.substring(6));
		System.out.println("eyear:" + eYear + " emonth:" + eMonth + " eday:"
				+ eDay);

	}

	private static int getBetweenDays(String t1, String t2)
			throws ParseException {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		int betweenDays = 0;
		Date d1 = format.parse(t1);
		Date d2 = format.parse(t2);
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTime(d1);
		c2.setTime(d2);
		if (c1.after(c2)) {
			c1 = c2;
			c2.setTime(d1);
		}
		int betweenYears = c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR);

		betweenDays = c2.get(Calendar.DAY_OF_YEAR)
				- c1.get(Calendar.DAY_OF_YEAR);

		for (int i = 0; i < betweenYears; i++) {
			c1.set(Calendar.YEAR, (c1.get(Calendar.YEAR) + 1));
			betweenDays += c1.getMaximum(Calendar.DAY_OF_YEAR);
		}

		return betweenDays;
	}

	/**
	 * 日期格式:yyyy-MM-dd
	 * 
	 * @param t1
	 *            起始日期
	 * @param t2
	 *            终止日期
	 * @return String数组,数组内是格式为yyyy-MM-dd的间隔日期.
	 * @throws Exception
	 */
	public static String[] getDays(String t1, String t2) throws Exception {
		int betweenDays = getBetweenDays(t1, t2);
		String[] days = new String[betweenDays + 1];
		days[0] = t1;
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = format.parse(t1);
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		for (int i = 0; i < betweenDays; i++) {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			int d = c.get(Calendar.DAY_OF_MONTH);
			++d;
			c.set(Calendar.DAY_OF_MONTH, d);
			days[i + 1] = df.format(c.getTime());
		}
		return days;
	}

	/**
	 * 计算指定日期时间之间的时间差
	 * 
	 * @param beginStr
	 *            开始日期字符串
	 * @param endStr
	 *            结束日期字符串
	 * @param f
	 *            时间差的形式0-秒,1-分种,2-小时,3--天 日期时间字符串格式:yyyy-MM-dd HH:mm:ss
	 * */
	public static String getInterval(String beginStr, String endStr, int f) {
		String time = "";
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date beginDate = df.parse(beginStr);
			Date endDate = df.parse(endStr);
			long millisecond = endDate.getTime() - beginDate.getTime(); // 日期相减得到日期差X(单位:毫秒)

			int day = (int) (millisecond / (1000 * 60 * 60 * 24));
			long milliday = (millisecond % (1000 * 60 * 60 * 24));
			int hours = (int) (milliday / (1000 * 60 * 60));
			long millihour = (milliday % (1000 * 60 * 60));
			int minutes = (int) (millihour / (1000 * 60));
			long milliminutes = (millihour % (1000 * 60));
			int second = (int) (milliminutes / 1000);
			time = day + "天" + hours + "小时" + minutes + "分钟" + second + "秒";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return time;
	}

}
