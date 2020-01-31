package com.krm.ps.model.reportview.util;

import com.krm.ps.framework.common.web.action.BaseAction;
import com.krm.ps.model.reportview.services.ChartDefineService;
import com.krm.ps.model.vo.ChartModel;
import com.krm.ps.model.vo.DataCollect;
import com.krm.ps.model.vo.DataFormula;

public class ReportChart {
	// chart type count
	public static int CHART_TYPE_COUNT = 3;
	public static String REPORT_DATA = "reportdata";
	public static String CHART_LIST_OPTION = "listtag";
	public static String CHART_MODEL_LIST = "ch_list";
	public static String CHART_MODEL_REPORT = "ch_report";
	public static String CHART_MODEL_FORMAT = "ch_format";
	public static String CHART_MODEL_EDIT = "chartedit";
	public static String CHART_MODEL = "ch_model";
	public static String CHART_MODEL_ID = "ch_id";
	public static String CHART_ERROR = "error";
	public static String CHART_ERROR_TAG = "errorid";
	public static String CHART_ERROR_FROM = "errorurl";
	public static String CHART_ERROR_LIST = "1";
	public static String CHART_ERROR_EDIT = "2";
	public static String CHART_ERROR_DELETE = "3";
	public static String CHART_ERROR_SHOW = "4";
	public static String CHART_ERROR_SAVE = "5";
	public static String CHART_REPORT = "showchart";
	public static String CHART_ORGAN = "organId";
	public static String CHART_DATE = "dataDate";
	public static String CHART_ORIENTATION = "ch_orient";
	public static String CHART_BEGIN = "ch_begin";
	public static String CHART_END = "ch_end";
	public static String CHART_POS = "ch_pos";

	/**
	 * to judge if a model is valid
	 * 
	 * @param model
	 *            the model object to be judged
	 * @return true if the model is valid, else false
	 */
	public static boolean validChartModel(ChartModel model) {
		boolean result = false;
		if (model != null)
			result = validChartGrid(model.getChartGrid())
					&& validChartLabel(model.getChartLabel())
					&& validChartLegend(new Long(model.getChartLegend()))
					&& validChartOrientation(new Long(
							model.getChartOrientation()))
					&& validChartTable(new Long(model.getChartTable()))
					&& validChartType(new Long(model.getChartType()))
					&& model.getCatBegin() >= 0 && model.getCatIndex() >= 0
					&& model.getCatCount() >= 0 && model.getSeriBegin() >= 0
					&& model.getSeriIndex() >= 0 && model.getSeriCount() >= 0
					&& model.getReportID() > 0
					&& model.getChartID().longValue() >= 0;
		return result;
	}

	/**
	 * return a ChartDefineService for the Action
	 * 
	 * @param action
	 *            current action
	 * @return <b>ChartDefineService</b>
	 */
	public static ChartDefineService getChartDefineService(BaseAction action) {
		return (ChartDefineService) action.getBean("chartdefineService");
	}

	/**
	 * Trans data from <b>DataCollect</b> to <b>double</b> array.
	 * 
	 * @param data
	 *            Source <b>DataCollect</b> object
	 * @param rowBegin
	 *            row begin position
	 * @param colBegin
	 *            col begin position
	 * @param rowEnd
	 *            row end position
	 * @param colEnd
	 *            col end position
	 * @return a <b>double</b> array if success else <b>null</b>
	 */
	public static double[][] transData(DataCollect data, int rowBegin,
			int colBegin, int rowEnd, int colEnd) {
		double[][] Result = null;
		if (data != null) {
			DataFormula formula = null;
			for (int r = rowBegin; r < rowEnd; r++) {
				for (int c = colBegin; c < colEnd; c++) {
					formula = data.getDataFormula(r, c);
					if (formula == null)
						break;
					Result[r][c] = formula.getDataDouble();
				}
			}
		}
		return Result;
	}

	/**
	 * Return the rows and cols of the DataCollect.
	 * 
	 * @param data
	 *            a <b>DataCollect</b> object to be calculated.
	 * @return <b>RowCol</b> object saved rows and cols information.
	 */
	public static RowCol getDataRowCol(DataCollect data) {
		RowCol Result = null;
		if (data != null) {
			Result = new RowCol();
			int r, c;
			for (int i = 0; i < data.getCount(); i++) {
				DataFormula f = data.getDataFormula(i);
				if (f != null) {
					r = f.getRow();
					c = f.getCol();
					if (r > Result.getRow())
						Result.setRow(r);
					if (c > Result.getCol())
						Result.setCol(c);
				}
			}
		}
		return Result;
	}

	/**
	 * return the meaning of chart type's number
	 * 
	 * @param item
	 *            number of the chart type
	 * @return null if the type number is invalid, else the meaning
	 */
	public static String getChartTypeStr(Long item) {
		String Result = null;
		switch (item.intValue()) {
		case 0:
			Result = "��״ͼ";
			break;
		case 1:
			Result = "����ͼ";
			break;
		case 2:
			Result = "ֱ��ͼ";
			break;
		}
		return Result;
	}

	/**
	 * ����λ�úź���
	 * 
	 * @param item
	 *            λ�ú�
	 * @return null-��ʾ�Ƿ���λ�úţ�����Ϊָ��λ�úŵĺ���
	 */
	public static String getPositionStr(Long item) {
		String Result = null;
		switch (item.intValue()) {
		case 0:
			Result = "����ʾ";
			break;
		case 1:
			Result = "���Ϸ�";
			break;
		case 2:
			Result = "���Ϸ�";
			break;
		case 3:
			Result = "���Ϸ�";
			break;
		case 4:
			Result = "���";
			break;
		case 5:
			Result = "�ұ�";
			break;
		case 6:
			Result = "���·�";
			break;
		case 7:
			Result = "���·�";
			break;
		case 8:
			Result = "���·�";
			break;
		}
		return Result;
	}

	/**
	 * ����������ȡ����ĺ���
	 * 
	 * @param item
	 *            ����ֵ
	 * @return null-��ʾ�Ƿ��ķ���ֵ������Ϊָ���ķ���ֵ�ĺ���
	 */
	public static String getOrientationStr(Long item) {
		String Result = null;
		switch (item.intValue()) {
		case 0:
			Result = "����";
			break;
		case 1:
			Result = "����";
			break;
		}
		return Result;
	}

	/**
	 * ���ر�ǩ��ʾ���
	 * 
	 * @param item
	 *            ָ������ʾ���
	 * @return null-��ʾ�Ƿ�����ʾ�����Ϊָ������ʾ��ĺ���
	 */
	public static String getLabelDefine(Long item) {
		String Result = null;
		switch (item.intValue()) {
		case 0:
			Result = "����";
			break;
		case 1:
			Result = "����";
			break;
		case 2:
			Result = "��ֵ";
			break;
		}
		return Result;
	}

	/**
	 * ������������ʾ���
	 * 
	 * @param item
	 *            ָ������ʾ���
	 * @return null-��ʾ�Ƿ�����ʾ�����Ϊָ������ʾ��ĺ���
	 */
	public static String getGridDefine(Long item) {
		String Result = null;
		switch (item.intValue()) {
		case 0:
			Result = "x����������";
			break;
		case 1:
			Result = "x���������";
			break;
		case 2:
			Result = "y����������";
			break;
		case 3:
			Result = "y���������";
			break;
		}
		return Result;
	}

	/**
	 * �жϱ����ʾѡ��ĺϷ���
	 * 
	 * @param item
	 *            ��ʾѡ��ֵ
	 * @return �Ƿ�Ϸ�
	 */
	public static boolean validChartTable(Long item) {
		boolean Result = ((item.longValue() >= 0) && (item.longValue() <= 8));
		return Result;
	}

	/**
	 * �ж�ͼ����ʾѡ��ĺϷ���
	 * 
	 * @param item
	 *            ��ʾѡ��ֵ
	 * @return �Ƿ�Ϸ�
	 */
	public static boolean validChartLegend(Long item) {
		boolean Result = ((item.longValue() >= 0) && (item.longValue() <= 8));
		return Result;
	}

	/**
	 * �ж�������ȡ����ֵ�ĺϷ���
	 * 
	 * @param item
	 *            ��ȡ����ֵ
	 * @return �Ƿ�Ϸ�
	 */
	public static boolean validChartOrientation(Long item) {
		boolean Result = ((item.longValue() >= 0) && (item.longValue() <= 1));
		return Result;
	}

	/**
	 * �ж�������ȡ����ֵ�ĺϷ���
	 * 
	 * @param item
	 *            ��ȡ����ֵ
	 * @return �Ƿ�Ϸ�
	 */
	public static boolean validChartType(Long item) {
		boolean Result = ((item.longValue() >= 0) && (item.longValue() <= 2));
		return Result;
	}

	/**
	 * �ж����ݱ�ǩ��ʾѡ��ĺϷ���
	 * 
	 * @param item
	 *            ��ʾѡ��
	 * @return �Ƿ�Ϸ�
	 */
	public static boolean validChartLabel(String item) {
		boolean Result = true;
		if (item.length() == 3) {
			for (int i = 0; i < 3; i++) {
				char ch = item.charAt(i);
				if ((ch != '0') && (ch != '1')) {
					Result = false;
					break;
				}
			}
		} else
			Result = false;
		return Result;
	}

	/**
	 * �ж���������ʾѡ��ĺϷ���
	 * 
	 * @param item
	 *            ��ʾѡ��
	 * @return �Ƿ�Ϸ�
	 */
	public static boolean validChartGrid(String item) {
		boolean Result = true;
		if (item.length() == 4) {
			for (int i = 0; i < 4; i++) {
				char ch = item.charAt(i);
				if ((ch != '0') && (ch != '1')) {
					Result = false;
					break;
				}
			}
		} else
			Result = false;
		return Result;
	}
}
