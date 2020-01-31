package com.krm.ps.model.flexiblequery.dao.hibernate;

import java.util.ArrayList;
import java.util.List;

import com.krm.ps.framework.dao.hibernate.BaseDAOHibernate;
import com.krm.ps.model.flexiblequery.dao.ReportRuleDAO;
import com.krm.ps.model.vo.ReportResult;
import com.krm.ps.sysmanage.reportdefine.vo.Report;
import com.krm.ps.sysmanage.reportdefine.vo.ReportType;

public class ReportRuleDAOHibernate extends BaseDAOHibernate implements
		ReportRuleDAO {

	@Override
	public List<ReportResult> getReportResultByDataId(String cstatus,
			String[] idArr, String date, String tablename, String targetids) {
		List list = new ArrayList();
		if (idArr.length == 0) {
			return list;
		}
		StringBuffer sb = new StringBuffer("select {crr.*} from  ");
		sb.append(tablename);
		sb.append(" crr where crr.keyvalue in (");
		for (int i = 0; i < idArr.length; i++) {
			if (i == idArr.length - 1) {
				sb.append("?)");
			} else {
				sb.append("?,");
			}
		}
		if (cstatus != null) {
			sb.append(" and  crr.cstatus in(" + cstatus + ") ");// ����¼�����޶�
		}
		list = list(sb.toString(),
				new Object[][] { { "crr", ReportResult.class } }, null, idArr);
		return list;
	}

	/**
	 * 根据规则编码获得规则列表
	 * 
	 * @param modelid
	 * @return
	 */
	public List getReportRuleBycode(String rulecode) {
		String hql = "from ReportRule r where r.rulecode in (" + rulecode + ")";
		return this.list(hql);
	}

	/**
	 * 获报表或都数据模型的类型
	 * 
	 * @param systemcode
	 *            系统标识 （比如人行标准化和客户风险）
	 * @param showlevel
	 *            0 代表目标层模板;1 代表目标层模型;2 代表报送层模板
	 * @return
	 */
	public List getReportType(Integer systemcode, Integer showlevel) {
		String hql = "from ReportType r where r.systemCode=" + systemcode
				+ " and r.showlevel=" + showlevel;
		return this.list(hql);
	}

	/**
	 * 获得某一类型下的所有报表
	 * 
	 * @param reportType
	 *            报表类型
	 * @return
	 */
	public List getReport(List reportType) {
		String str = "";
		for (int i = 0; i < reportType.size(); i++) {
			ReportType rt = (ReportType) reportType.get(i);
			str += rt.getPkid() + ",";
		}
		str = str.substring(0, str.length() - 1);
		String hql = "from Report  r where r.reportType in(" + str + ")";
		return this.list(hql);
	}

	@Override
	public List getReportType(Integer systemcode, Integer showlevel, Long userId) {
		String sql = "select {r.*} from code_rep_report r  where r.pkid in (select u.repid from rep_oper_contrast u where u.operid ='"+userId+"') " +
				"and r.report_type in (select t.pkid from code_rep_types t where t.system_code='"+systemcode+"' and t.showlevel='" + showlevel+"') " +
						" order by r.report_type,r.show_order";
		List ls = list(sql.toString(),
				new Object[][] { { "r", Report.class } }, null);
		return ls;
	}

}
