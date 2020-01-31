package com.krm.ps.framework.common.dao.hibernate;

import java.util.ArrayList;
import java.util.List;

import com.krm.ps.framework.dao.hibernate.BaseDAOHibernate;
import com.krm.ps.framework.common.dao.CommonQueryDAO;

public class CommonQueryDAOHibernate extends BaseDAOHibernate implements
		CommonQueryDAO {

	public List commonQuery(String sql, Object[][] entities,
			Object[][] scalaries, Object[] values) {
		// TODO Auto-generated method stub
		// 如果SQL中含有关键字delete，这直接用jdbcUpdate执行
		String lowerSql = sql.toLowerCase();
		if (lowerSql.trim().indexOf("delete") == 0) {
			List resultList = new ArrayList();
			
			
			
			resultList.add(new Integer(jdbcUpdate(sql, values)));
			return resultList;
		}
		return list(sql, entities, scalaries, values);
	}

}
