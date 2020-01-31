package com.krm.ps.sysmanage.reportdefine.dao.hibernate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Hibernate;

import com.krm.ps.framework.dao.hibernate.BaseDAOHibernate;
import com.krm.ps.framework.dao.jdbc.BaseDAOJdbc;
import com.krm.ps.sysmanage.reportdefine.dao.ReportFormatDAO;
import com.krm.ps.sysmanage.reportdefine.vo.ReportFormat;
import com.krm.ps.sysmanage.usermanage.vo.Units;

public class ReportFormatDAOHibernate extends BaseDAOHibernate implements
		ReportFormatDAO {

	protected BaseDAOJdbc jdbc;

	public void setDAOJdbc(BaseDAOJdbc jdbc) {
		this.jdbc = jdbc;
	}

	public List getReportFormats(Long reportId) {
		String hql = "";
		String rr = "";
		if (reportId.longValue() != 0) {
			rr = " and rf.reportId = " + reportId;
		}
		hql = "select new ReportFormat(rf,r.name,r.frequency)"
				+ " from ReportFormat rf, Report r where rf.reportId = r.pkid"
				+ rr + " order by r.reportType,r.showOrder";
		//========================================================
		//add by hejx 2012.7.17
		//List l = list(hql);
		List l = new ArrayList();
		try {
			l = list(hql);
		} catch (Exception e) {
			//处理DB2数据库中由于脏数据CLOB为空出现异常，而中断导出
			l = getExceptionReportFormat(reportId);
		}
		//========================================================
		return l;
	}

	/**
	 * add by hejx 2012.7.17
	 * 处理DB2数据库中由于脏数据CLOB为空出现异常，而中断导出
	 * @param reportId
	 * @return
	 */
	private List getExceptionReportFormat(Long reportId) {
		String hql = "";
		String rr = "";
		if (reportId.longValue() != 0) {
			rr = " and rf.report_id = " + reportId;
		}
		hql = "select rf.pkid,rf.report_id,rf.begin_date,rf.end_date,rf.edit_date,r.name,r.frequency,r.createId"
				+ " from rep_format rf, code_rep_report r where rf.report_id = r.pkid"
				+ rr + " order by r.report_type,r.show_order";

		Object[][] scalaries = { { "pkid", Hibernate.LONG },
				{ "report_id", Hibernate.LONG },
				{ "begin_date", Hibernate.STRING },
				{ "end_date", Hibernate.STRING },
				{ "edit_date", Hibernate.STRING },
				{ "name", Hibernate.STRING },
				{ "frequency", Hibernate.STRING },
				{ "createId", Hibernate.INTEGER}};

		List fl = list(hql, null, scalaries);
		
		List formatList=new ArrayList();
		Iterator it=fl.iterator();
		while(it.hasNext()) {
			Object[] row=(Object[])it.next();
			ReportFormat rf=new ReportFormat();
			rf.setPkId((Long)row[0]);
			rf.setReportId((Long)row[1]);
			rf.setBeginDate((String)row[2]);
			rf.setEndDate((String)row[3]);
			rf.setEditDate((String)row[4]);
			rf.setReportName((String)row[5]);
			rf.setFrequency((String)row[6]);
			rf.setCreateId((Integer)row[7]);
			//=============================
			rf.setReportFormat("");
			//=============================
			formatList.add(rf);
		}
		return formatList;
	}
	
	public List getReportFormatsByType(Long reporttype) {
		String hql = "";
		String rr = "";
		if (reporttype.longValue() != 0) {
			rr = " and r.reportType = " + reporttype;
		}
		hql = "select new ReportFormat(rf,r.name,r.frequency)"
				+ " from ReportFormat rf, Report r where rf.reportId = r.pkid"
				+ rr + " order by r.reportType,r.showOrder";
		List l = list(hql);
		return l;
	}
	
	public List getReportFormatsByType(Long reporttype,Long userId) {
		String hql = "";
		String rr = "";
		if (reporttype.longValue() != 0) {
			rr = " and r.reportType = " + reporttype;
		}
		hql = "select new ReportFormat(rf,r.name,r.frequency)"
				+ " from ReportFormat rf, Report r where rf.reportId = r.pkid"
				+ rr + " and r.pkid in (select ur.reportId from UserReport ur where ur.operId ="+userId+") "+
				" order by r.reportType,r.showOrder";
		List l = list(hql);
		return l;
	}
	

	public List getReportFormatsByTypeWithoutFormatXML(Long reporttype) {
		String hql = "";
		String rr = "";
		if (reporttype.longValue() != 0) {
			rr = " and r.report_type = " + reporttype;
		}
		hql = "select rf.pkid,rf.report_id,rf.begin_date,rf.end_date,rf.edit_date,r.name,r.frequency,r.createId"
				+ " from rep_format rf, code_rep_report r where rf.report_id = r.pkid"
				+ rr + " order by r.report_type,r.show_order";

		Object[][] scalaries = { { "pkid", Hibernate.LONG },
				{ "report_id", Hibernate.LONG },
				{ "begin_date", Hibernate.STRING },
				{ "end_date", Hibernate.STRING },
				{ "edit_date", Hibernate.STRING },
				{ "name", Hibernate.STRING },
				{ "frequency", Hibernate.STRING },
				{ "createId", Hibernate.INTEGER}};

		List fl = list(hql, null, scalaries);
		
		List formatList=new ArrayList();
		Iterator it=fl.iterator();
		while(it.hasNext()) {
			Object[] row=(Object[])it.next();
			ReportFormat rf=new ReportFormat();
			rf.setPkId((Long)row[0]);
			rf.setReportId((Long)row[1]);
			rf.setBeginDate((String)row[2]);
			rf.setEndDate((String)row[3]);
			rf.setEditDate((String)row[4]);
			rf.setReportName((String)row[5]);
			rf.setFrequency((String)row[6]);
			rf.setCreateId((Integer)row[7]);
			formatList.add(rf);
		}
		
		return formatList;
	}
	//为获取报表模板添加过滤条件，防止监管维护模板显示，影响模板的维护    2012/9/13   杨会
	public List getReportFormatsByTypeWithoutFormatXML(Long reporttype,Long userId) {
		String hql = "";
		String rr = "";
		if (reporttype.longValue() != 0) {
			rr = " and r.report_type = " + reporttype;
		}
		hql = "select rf.pkid,rf.report_id,rf.begin_date,rf.end_date,rf.edit_date,r.name,r.frequency,r.createId"
				+ " from rep_format rf, code_rep_report r where rf.report_id = r.pkid"
				+ rr + " and r.pkid in (select ro.repid from rep_oper_contrast ro where ro.operid = "+userId+") "+
				"   and rf.begin_date <> '00000000'  and rf.end_date <> '00000000'  and  rf.edit_date <> '000000'  order by r.report_type,r.show_order";

		Object[][] scalaries = { { "pkid", Hibernate.LONG },
				{ "report_id", Hibernate.LONG },
				{ "begin_date", Hibernate.STRING },
				{ "end_date", Hibernate.STRING },
				{ "edit_date", Hibernate.STRING },
				{ "name", Hibernate.STRING },
				{ "frequency", Hibernate.STRING },
				{ "createId", Hibernate.INTEGER}};

		List fl = list(hql, null, scalaries);
		
		List formatList=new ArrayList();
		Iterator it=fl.iterator();
		while(it.hasNext()) {
			Object[] row=(Object[])it.next();
			ReportFormat rf=new ReportFormat();
			rf.setPkId((Long)row[0]);
			rf.setReportId((Long)row[1]);
			rf.setBeginDate((String)row[2]);
			rf.setEndDate((String)row[3]);
			rf.setEditDate((String)row[4]);
			rf.setReportName((String)row[5]);
			rf.setFrequency((String)row[6]);
			rf.setCreateId((Integer)row[7]);
			formatList.add(rf);
		}
		
		return formatList;
	}

	public Units getReportMoneyUnit(Long reportId) {
		String hql = "select u from Report r ,Units u where r.moneyunit = u.pkid and r.pkid = "
				+ reportId;
		return (Units) uniqueResult(hql);
	}

	public ReportFormat getReportFormat(Long reportId, String date,
			String frequency) {

		if (date == null && date.equals("")) {
			return null;
		}
		String sql=null;
		// 对于监管展示 生成时，数据库中保存模板时会固定把时间设置为‘00000000’，所以 开始时间 要有=,故加一个对时间的判断   2012-04-28  杨会
		if(!date.equals("00000000")){
		 sql = "SELECT {f.*} FROM rep_format f" + " WHERE report_id = "
				+ reportId  //+ " AND frequency = " + frequency  TODO:模板频度和报表频度处理不同步。但是目前频度没有用到。所以从查询条件里去掉这个条件 by 郭跃龙 on May 18, 2007
				+ " AND begin_date < '" + date + "' AND end_date >= '" + date
				// 将 begin_date <= 改成 <,不然可能出现多个模板，导致套用错误模板，比如 一个模板的失效日期正好是另外一模板的起始日期。
				 +" ' ORDER BY create_date DESC, pkid DESC";
		}else{
			sql = "SELECT {f.*} FROM rep_format f" + " WHERE report_id = "
					+ reportId 
					+ " AND begin_date <= '" + date + "' AND end_date >= '" + date
					 +" ' ORDER BY create_date DESC, pkid DESC";
			
		}
		List l = list(sql, new Object[][] { { "f", ReportFormat.class } }, null);
		Iterator i = l.iterator();
		if (i.hasNext()) {
			return (ReportFormat) i.next();
		}
		return null;
	}

	/**
	 * 获取报表的所有单位信息列表
	 * @return List
	 */
	public List getReportUnitList() {
		String hql = "from Units u order by u.pkid";
		return list(hql);
	}
	
	
	/**
	 * 判断是否是流水表
	 * @param reportID
	 * @return boolean
	 */
	public boolean isFlowTable(String reportID) {
		StringBuffer sql = new StringBuffer();
		sql.append("select pkid from code_rep_report where pkid = ").append(reportID)
			.append(" and phy_table = 'rep_dataf'");
		Object [][] params = {{"pkid",Hibernate.INTEGER}};
		List list = list(sql.toString(), null , params);
		return (list.size() > 0);
	}
}
