package com.krm.ps.model.repfile.dao.Hibernate;

import java.util.List;

import com.krm.ps.framework.dao.hibernate.BaseDAOHibernate;
import com.krm.ps.model.repfile.dao.DataFillDAO;
import com.krm.ps.model.vo.DicItem;

public class DataFillDAOHibernate extends BaseDAOHibernate implements
		DataFillDAO {

	public List<DicItem> getDicByPid(Long dicPid) {
		String hql = "from DicItem di where di.parentId='" + dicPid + "'";
		return list(hql);
	}

}
