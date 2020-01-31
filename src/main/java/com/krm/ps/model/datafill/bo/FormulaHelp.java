package com.krm.ps.model.datafill.bo;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.krm.ps.model.datafill.dao.FormulaDAO;




/**
 * 
 * ��ʽ������
 * <p>
 * ����ʱ��: 2006.4.16
 * 
 * @author nxd
 * @version v1.1 2006.4.16
 * @since �޸ļ�¼���D�D�D�D�D�D�D�D�D�D�D�D�D�D�D�D�D�D�D�D�D�D�D�D�D�D
 *        <p>
 *        �޸�ʱ�� �޸��� �޸ĵķ��� �޸ĵ�ԭ��<br>
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
	 * ���ݱ������õ���������
	 * 
	 * @param reportid
	 *            �������
	 * @return String ��������
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
