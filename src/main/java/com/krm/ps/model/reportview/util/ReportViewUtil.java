package com.krm.ps.model.reportview.util;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.krm.ps.model.vo.DataCollect;
import com.krm.ps.model.vo.DataFormula;
import com.krm.ps.util.Constant;
import com.krm.ps.util.DateUtil;


public class ReportViewUtil {
	public static final String ATTR_REPORTVIEW_LIST = "reportViewList";

	public static final String ATTR_REPORTFILE = "reportFile";

	public static final String ATTR_VIEWRESULT = "viewResult";// 0成功 1无格式

	// 2公式出错

	public static final String ATTR_SAVERESULT = "saveResult";

	public static final String ATTR_ORGAN_LIST = "organList";

	public static final String ATTR_REPORT_LIST = "reportList";

	public static final String ATTR_REPORTTYPE_LIST = "reportTypeList";

	public static final String ATTR_BACKLIST_URL = "backUrl";

	public static final String PARAM_CELL_ROWCOL = "cellRowCol";

	public static final String PARAM_CELL_FORMULA = "cellFormula";

	public static final String PARAM_CELL_VALUE = "cellValue";

	/**
	 * 根据选择日期计算存数/取数日期
	 * 
	 * @param date
	 *            日期，格式：yyyyMMdd
	 * @param frequency
	 *            频度
	 * @return
	 */
	public static String mapDataDate(String date, String frequency) {

		if(frequency==null){ 
			return date;
		}
		if (frequency.equals(Constant.FREQUENCY_DAY)) {// 1
			return date;
		} else if (frequency.equals(Constant.FREQUENCY_FIVEDAY)) {// 5
			String ds = date.substring(6);
			int d = Integer.parseInt(ds);
			int ad = d - (d % 5) + 1;// 6,11,16,...
			String ads = (ad < 10) ? "0" + ad : "" + ad;
			return date.substring(0, 6) + ads;
		} else if (frequency.equals(Constant.FREQUENCY_WEEK)) {// 7
			try {
				Date dd = DateUtil.convertStringToDate("yyyyMMdd", date);
				Calendar c = new GregorianCalendar();
				c.setTime(dd);
				int day = c.get(Calendar.DAY_OF_WEEK);
				c.roll(Calendar.DAY_OF_WEEK, 2 - day);//周一
				return DateUtil.getDateTime("yyyyMMdd", c.getTime());
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} else if (frequency.equals(Constant.FREQUENCY_TENDAY)) {// 10
			String ds = date.substring(6);
			int d = Integer.parseInt(ds);
			String ads = "01";
			if (d >= 21) {
				ads = "21";
			} else if (d >= 11) {
				ads = "11";
			}
			return date.substring(0, 6) + ads;
		} else {
			return date.substring(0, 6) + "00";
		} 
		return date;
	}

	/**
	 * 2011-10-31周石磊修改
	 * 根据报表频度与日期判断季报、年报、半年报是否存在，如果不存在就返回null
	 * @param date
	 * @param frequency
	 * @return
	 */
	public static String mapDataDateByFreq(String date, String frequency) {
		if(frequency==null){ 
			return date;
		}
		if (frequency.equals(Constant.FREQUENCY_DAY)) {// 1
			return date;
		} else if (frequency.equals(Constant.FREQUENCY_FIVEDAY)) {// 5
			String ds = date.substring(6);
			int d = Integer.parseInt(ds);
			int ad = d - (d % 5) + 1;// 6,11,16,...
			String ads = (ad < 10) ? "0" + ad : "" + ad;
			return date.substring(0, 6) + ads;
		} else if (frequency.equals(Constant.FREQUENCY_WEEK)) {// 7
			try {
				Date dd = DateUtil.convertStringToDate("yyyyMMdd", date);
				Calendar c = new GregorianCalendar();
				c.setTime(dd);
				int day = c.get(Calendar.DAY_OF_WEEK);
				c.roll(Calendar.DAY_OF_WEEK, 2 - day);//周一
				return DateUtil.getDateTime("yyyyMMdd", c.getTime());
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} else if (frequency.equals(Constant.FREQUENCY_TENDAY)) {// 10
			String ds = date.substring(6);
			int d = Integer.parseInt(ds);
			String ads = "01";
			if (d >= 21) {
				ads = "21";
			} else if (d >= 11) {
				ads = "11";
			}
			return date.substring(0, 6) + ads;
		}else if (frequency.equals(Constant.FREQUENCY_QUARTER)) {// 90
			String mouth = date.substring(4,6);
			if(mouth.equals("03")||mouth.equals("06")||mouth.equals("09")||mouth.equals("12")){
				return date.substring(0, 6) + "00";
			}else{
				return null;
			}
		} else if (frequency.equals(Constant.FREQUENCY_HALFYEAR)) {// 180
			String mouth = date.substring(4,6);
			if(mouth.equals("06")||mouth.equals("12")){
				return date.substring(0, 6) + "00";
			}else{
				return null;
			}
		} else if (frequency.equals(Constant.FREQUENCY_YEAR)) {// 360
			String mouth = date.substring(4,6);
			if(mouth.equals("12")){
				return date.substring(0, 6) + "00";
			}else{
				return null;
			}
		}  else if (frequency.equals(Constant.FREQUENCY_MONTH)) {// 30
			return date.substring(0, 6) + "00";
		} 
		return date;
	}
	
	/**
	 * 生成公式体系用的数据集合，去除重复的公式对象
	 * 
	 * @param arrCellFormula
	 * @param arrCellValue
	 * @return
	 */
	public static DataCollect makeDataCollect(String[] arrCellFormula,
			String[] arrCellValue, long unit) {
		DataCollect dataCollect = new DataCollect();
		for (int i = 0; i < arrCellFormula.length; i++) {
			DataFormula dataFormula = new DataFormula();
			dataFormula.setDataType(DataFormula.DATA_DOUBLE);
			try {
				double doubleValue;
				boolean notMoney = arrCellFormula[i].endsWith("N");
				if (notMoney) {// 不是金额转换
					arrCellFormula[i] = arrCellFormula[i].substring(0,
							arrCellFormula[i].length() - 1);
				}
				if (arrCellValue[i].endsWith("%")) {
					String realValue = arrCellValue[i].substring(0,
							arrCellValue[i].length() - 1).trim();
					doubleValue = Double.parseDouble(realValue);
					dataFormula.setDataDouble(doubleValue / 100);
					dataFormula.setDecimalDigits(DataFormula.PROPROTION2);
				} else {
					doubleValue = Double.parseDouble(arrCellValue[i]);
					if (notMoney) {// 不是金额转换
						dataFormula.setDataDouble(doubleValue);
					} else {
						dataFormula.setDataDouble(doubleValue * unit);
					}
				}
				dataFormula.setDataString(arrCellValue[i]);
			} catch (NumberFormatException e) {
				dataFormula.setDataString(arrCellValue[i]);
				dataFormula.setDataType(DataFormula.DATA_STRING);
			}
			if (arrCellFormula[i].length() > 0) {
				dataFormula.setFormulaString(arrCellFormula[i].substring(1));
			} else {
				System.out.println("empty formula.");
				continue;
			}

			// wsx 8-31，处理6位小数问题，只是权宜之计，TODO
			String fs = dataFormula.getFormulaString().replaceAll(" ", "");
			if (fs.endsWith("/100")) {
				dataFormula.setFormulaString(fs.substring(0, fs.length() - 4));
				dataFormula.setDataDouble(dataFormula.getDataDouble() * 100);
			}

			dataCollect.add(dataFormula);
		}
		return dataCollect;
	}

}
