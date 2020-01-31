package com.krm.ps.model.reportdefine.dao.hibernate;

import com.krm.ps.framework.dao.hibernate.BaseDAOHibernate;
import java.util.List;

import com.krm.ps.model.reportdefine.dao.DictionaryDAO;
import com.krm.ps.sysmanage.usermanage.vo.Dictionary;

public class DictionaryDAOHibernate extends BaseDAOHibernate implements
		DictionaryDAO {

	@Override
	public List getDics(int type) {
		String sql = "SELECT {dic.*} FROM code_dictionary dic WHERE parentid = "
				+ type + " AND status = '1' ORDER BY disporder";
		return this.list(sql, new Object[][] { { "dic", Dictionary.class } },
				null);
	}

	@Override
	public List getALLsystem() {
		String hql = "from Esystem where status='1' order by show_order";
		return this.list(hql);
	}

	@Override
	public List getUnits() {
		String hql = "from Units order by display_order ";
		return this.list(hql);
	}

	@Override
	public Dictionary getDictionary(int type, int dicid) {
		String hql = "from Dictionary where parentid=" + type + " and dicid="
				+ dicid + " and status<>'9'  order by disporder";
		List d = this.list(hql);
		if (d.iterator().hasNext()) {
			return (Dictionary) d.get(0);
		}
		return null;
	}

}
