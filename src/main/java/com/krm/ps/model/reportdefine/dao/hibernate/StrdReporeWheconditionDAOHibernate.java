package com.krm.ps.model.reportdefine.dao.hibernate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.krm.ps.framework.dao.hibernate.BaseDAOHibernate;
import com.krm.ps.model.reportdefine.dao.StrdReporeWheconditionDAO;
import com.krm.ps.sysmanage.reportdefine.vo.StrdReportWhecondition;

public class StrdReporeWheconditionDAOHibernate extends BaseDAOHibernate
		implements StrdReporeWheconditionDAO {

	@Override
	public List getStrdreporeWhecondition(Long tempid) {
		String hql = "from  StrdReportWhecondition t where t.tempid = "
				+ tempid + " order by t.table desc";
		Map map = new HashMap();
		return list(hql, map);
	}

	@Override
	public void saveStrdreporeWhecondition(
			StrdReportWhecondition strdReportWhecondition) {

		this.getHibernateTemplate().saveOrUpdate(strdReportWhecondition);

	}

	@Override
	public StrdReportWhecondition getStrdreporeWhecondition(Long tempid,
			Long modelid) {
		String hql = "from  StrdReportWhecondition t where t.tempid = "
				+ tempid + " and t.modeid = " + modelid
				+ " order by t.table desc";
		Map map = new HashMap();
		List list = list(hql, map);
		StrdReportWhecondition strdReportWhecondition = null;
		for (int i = 0; i < list.size(); i++) {
			strdReportWhecondition = (StrdReportWhecondition) list.get(0);
		}
		return strdReportWhecondition;
	}

	@Override
	public void delStrdreporeWhecondition(Long tempid, Long modelid) {
		String hql = "from  StrdReportWhecondition t where t.tempid = "
				+ tempid + " and t.modeid = " + modelid
				+ " order by t.table desc";
		Map map = new HashMap();
		List list = list(hql, map);
		StrdReportWhecondition strdReportWhecondition = null;
		for (int i = 0; i < list.size(); i++) {
			strdReportWhecondition = (StrdReportWhecondition) list.get(0);
		}
		this.getHibernateTemplate().delete(strdReportWhecondition);
	}

}
