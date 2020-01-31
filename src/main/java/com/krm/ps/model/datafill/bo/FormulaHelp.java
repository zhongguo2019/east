package com.krm.ps.model.datafill.bo;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.krm.ps.model.datafill.dao.FormulaDAO;




/**
 * 
 * 公式辅助类
 * <p>
 * 创建时间: 2006.4.16
 * 
 * @author nxd
 * @version v1.1 2006.4.16
 * @since 修改记录：――――――――――――――――――――――――――
 *        <p>
 *        修改时间 修改者 修改的方法 修改的原因<br>
 */

public class FormulaHelp {
	private static Map REPORT_INFO = null;
	
	public static FormulaDAO DAO;

	public void setDAO(FormulaDAO dao) {
		DAO = dao;
	}
	
	public static FormulaDAO getDAO() {
		return DAO;
	}

	/**
	 * 根据报表编码得到报表名称
	 * 
	 * @param reportid
	 *            报表编码
	 * @return String 报表名称
	 */
	public static String getPhyTable(int reportid) {
		ensureRepInfo();
		Object o = REPORT_INFO.get(new Integer(reportid));
		String phyTable = "rep_data";
		if (o != null) {
			phyTable = ((ReportInfo) o).phyTable;
		}

		return phyTable;
	}
	public static String getPhyTable1(String reportid) {
		ensureRepInfo();
		Object o = REPORT_INFO.get(new Integer(reportid));
		String phyTable = "rep_data";
		if (o != null) {
			phyTable = ((ReportInfo) o).phyTable;
		}

		return phyTable;
	}
	
	public synchronized static void ensureRepInfo() {// remove synchronized
		if (REPORT_INFO == null) {
			REPORT_INFO = new HashMap();
			List ril = DAO.getReportInfo();
			for (Iterator i = ril.iterator(); i.hasNext();) {
				ReportInfo ri = (ReportInfo) i.next();
				REPORT_INFO.put(new Integer(ri.reportId), ri);
			}
		}
	}
	
	
	
	public static class ReportInfo {
		int reportId;
		
		int reportType;

		String reportName;

		int colNum;

		String phyTable;

		int repBalanPrec;

		String frequency;

		public ReportInfo(int id,int type, String name, int col, String table,
				String freq, int balanPrec) {
			reportId = id;
			reportType = type;
			reportName = name;
			colNum = col;
			phyTable = table;
			frequency = freq;
			repBalanPrec = balanPrec;
		}
	}
}
