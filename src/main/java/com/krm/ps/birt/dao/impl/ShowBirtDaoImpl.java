package com.krm.ps.birt.dao.impl;

import java.util.List;
import java.util.Map;

import com.krm.ps.birt.dao.ShowBirtDao;
import com.krm.ps.framework.dao.hibernate.BaseDAOHibernate;
import com.krm.ps.sysmanage.reportdefine.vo.Report;

public class ShowBirtDaoImpl extends BaseDAOHibernate implements ShowBirtDao {

	@Override
	public List<Report> getReports(Long userId) {
		String hql="from  Report r where r.reportType=212 and r.status='1' ";
		List<Report> list=list(hql);
		return list;
	}

	@Override
	public Report getReport(Long reportId) {
		String hql=" from Report where pkid=?";
	return (Report)	uniqueResult(hql, new Object[]{reportId});
		
	}
}
