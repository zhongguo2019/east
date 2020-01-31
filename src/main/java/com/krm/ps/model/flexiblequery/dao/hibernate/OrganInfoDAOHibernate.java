package com.krm.ps.model.flexiblequery.dao.hibernate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.krm.ps.framework.dao.hibernate.BaseDAOHibernate;
import com.krm.ps.model.flexiblequery.dao.OrganInfoDAO;
import com.krm.ps.sysmanage.organmanage.vo.OrganInfo;

public class OrganInfoDAOHibernate extends BaseDAOHibernate implements
		OrganInfoDAO {

	public OrganInfo getOrganByCode(String code) {
		String hql = "select new OrganInfo(o,e1.dicname,e2.dicname)from OrganInfo o,Dictionary e1,Dictionary e2 "
				+ "where o.status <> 9 and o.business_type=e2.dicid and e2.parentid=1002 "
				+ "and o.organ_type=e1.dicid and e1.parentid=1001 and o.dummy_type='1' "
				+ "and o.code=:code order by o.organOrder";
		Map map = new HashMap();
		map.put("code", code);
		List ls = list(hql, map);
		if (ls.size() > 0)
			return (OrganInfo) ls.get(0);
		return null;
	}

}