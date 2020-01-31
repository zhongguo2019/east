package com.krm.ps.model.repfile.dao.Hibernate;

import java.util.List;

import com.krm.ps.framework.dao.hibernate.BaseDAOHibernate;
import com.krm.ps.model.repfile.dao.ReportRuleDAO;

public class ReportRuleDAOHibernate extends BaseDAOHibernate implements
		ReportRuleDAO {

	/**
	 * 根据模型id和flag获得规则,其中flag0为人行,1为客户风险，2为风险预警
	 * 
	 * @param modelid
	 * @return
	 */
	public List getReportRuleFlag(String modelid, String flag) {
		String hql = "from ReportRule r where r.modelid in (" + modelid
				+ ") and r.flag='" + flag + "'";
		return this.list(hql);
	}
}
